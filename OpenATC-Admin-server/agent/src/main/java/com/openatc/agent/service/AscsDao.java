/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.agent.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openatc.agent.model.*;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.comm.data.MyGeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;

@Repository
public class AscsDao {

    private static Logger logger = Logger.getLogger(AscsDao.class.toString());

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Map<String, String> thirdidToAgentidOcp = new HashMap<>();

    /**
     * 初始化OCP设备的真实ID和平台ID的映射关系
     * 此处不映射SCP设备的真实ID和平台ID的映射关系，由各个对接服务负责
     */
    @PostConstruct
    public void initMap(){
        thirdidToAgentidOcp.clear();
        String sql = "SELECT agentid, thirdplatformid, protocol, jsonparam  FROM dev;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for(Map<String,Object> m : maps){
            String type = (String) m.get("protocol");
            if(type.equals("ocp")){
                String agentid = (String)m.get("agentid");
                String thirdpartyid = (String)m.get("thirdplatformid");
                thirdidToAgentidOcp.put(thirdpartyid,agentid);
            }
        }
    }

    public List<AscsBaseModel> getAscsByCode(String code) {
        String sql = "SELECT id, agentid,protocol, geometry, lastTime, descs,type,status,jsonparam, code FROM dev where code  = '" + code + "'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return convertAscs(maps);
    }

    public List<String> getAscsidByCode(String code) {
        String sql = "SELECT agentid FROM dev where code  = '" + code + "'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<String> agentids = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            agentids.add(map.get("agentid").toString());
        }
        return agentids;
    }

    public List<AscsBaseModel> getAscsNoCode() {
        String sql = "SELECT id, agentid,protocol, geometry, lastTime, descs,type,status,jsonparam,code FROM dev where code is null ";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return convertAscs(maps);
    }

    public List<AscsBaseModel> getAscsInfoOnline() {

        String sql = "SELECT id, geometry, lastTime, descs,type,status,jsonparam FROM dev WHERE LOCALTIMESTAMP - lastTime < '5 min'";

        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);

        return convertAscs(lvRet);
    }

    public List<AscsBaseModel> convertAscs(List<Map<String, Object>> lvRet) {
        List<AscsBaseModel> pl = new ArrayList<>();
        Gson gs = new Gson();
        for (Map map : lvRet) {

            AscsBaseModel ascBase = new AscsBaseModel();

            ascBase.setId((int) map.get("id"));
            ascBase.setAgentid((String) map.get("agentid"));
            ascBase.setProtocol((String) map.get("protocol"));
            ascBase.setStatus((int) map.get("status"));
            ascBase.setDescs((String) map.get("descs"));
            ascBase.setType((String) map.get("type"));
            ascBase.setLastTime((Date) map.get("lastTime"));
            ascBase.setCode((String) map.get("code"));
            String geometry = (String) map.get("geometry");
            ascBase.setGeometry(gs.fromJson(geometry, MyGeometry.class));
            JsonObject jsonparam = new JsonParser().parse(map.get("jsonparam").toString()).getAsJsonObject();
            ascBase.setJsonparam(jsonparam);
            pl.add(ascBase);
        }
        return pl;
    }

    public boolean isTableExist(String tableName) {
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData data = conn.getMetaData();
            String[] types = {"TABLE"};
            rs = data.getTables(null, null, tableName, types);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Transactional
    public boolean modifyAgentid(String oldAgentid, String newAgentid) {
        try {
            if (isTableExist("dev")) {
                String devSql = "update dev set agentid=? where agentid=?";
                jdbcTemplate.update(devSql, newAgentid, oldAgentid);
            }

            if (isTableExist("fault")) {
                String faultSql = "update fault set agentid=? where agentid=?";
                jdbcTemplate.update(faultSql, newAgentid, oldAgentid);
            }


            if (isTableExist("third_dev")) {
                String ht_deviceSql = "update third_dev set agentid=? where agentid=?";
                jdbcTemplate.update(ht_deviceSql, newAgentid, oldAgentid);
            }

            if (isTableExist("intersection_dev")) {
                String intersection_devSql = "update intersection_dev set agentid=? where agentid=?";
                jdbcTemplate.update(intersection_devSql, newAgentid, oldAgentid);
            }

            if (isTableExist("operation_record")) {
                String operation_record = "update operation_record set agentid=? where agentid=?";
                jdbcTemplate.update(operation_record, newAgentid, oldAgentid);
            }


            if (isTableExist("t_control")) {
                String t_control = "update t_control set agentid=? where agentid=?";
                jdbcTemplate.update(t_control, newAgentid, oldAgentid);
            }

            if (isTableExist("t_stat")) {
                String t_stat = "update t_stat set agentid=? where agentid=?";
                jdbcTemplate.update(t_stat, newAgentid, oldAgentid);
            }

            if (isTableExist("t_params")) {
                String t_params = "update t_params set agentid=? where agentid=?";
                jdbcTemplate.update(t_params, newAgentid, oldAgentid);
            }

            if (isTableExist("trafficincident")) {
                String trafficincident = "update trafficincident set agentid=? where agentid=?";
                jdbcTemplate.update(trafficincident, newAgentid, oldAgentid);
            }

            if (isTableExist("vip_route_device")) {
                String vip_route_device = "update vip_route_device set agentid=? where agentid=?";
                jdbcTemplate.update(vip_route_device, newAgentid, oldAgentid);
            }

            if (isTableExist("static_route_intersection")) {
                String static_route_intersection = "update static_route_intersection set agentid=? where agentid=?";
                jdbcTemplate.update(static_route_intersection, newAgentid, oldAgentid);
            }

            if (isTableExist("devs_video")) {
                String devs_video = "update devs_video set agentid=? where agentid=?";
                jdbcTemplate.update(devs_video, newAgentid, oldAgentid);
            }
            redisTemplate.convertAndSend("updateIdMap", "UpdateDev:" + newAgentid);
            initMap();

        } catch (Exception e) {

            return false;
        }

        return true;
    }


    public AscsBaseModel getAscsByID(String id) throws EnumConstantNotPresentException {

        AscsBaseModel ascsBaseModel = null;
        String sql =
                "SELECT id, thirdplatformid , platform, gbid, firm, agentid,protocol, geometry,type,status,descs,name, jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when true then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev WHERE agentid ='"
                        + id + "'";
        List<AscsBaseModel> listAscs = null;
        try {
            listAscs = getDevByPara(sql);
        } catch (ParseException e) {
            logger.warning("getAscsByID error :" + e.getMessage());
        }
        if (listAscs.size() > 0) {
            ascsBaseModel = listAscs.get(0);
        }
        return ascsBaseModel;
    }


    public int updatePattern(Params ps) {
        //判断是否存在该agentid的记录
        String sql = "SELECT count(id) FROM parameters where agentid = ?";
        long count = jdbcTemplate.queryForObject(sql, Long.class, ps.getAgentid());
        if (count != 0) {
            sql = "update parameters set name=?,operator=?,params=(to_json(?::json)),opertime=LOCALTIMESTAMP where agentid=?";
            int rows = jdbcTemplate.update(sql,
                    ps.getName(),
                    ps.getOperator(),
                    ps.getParams().toString(),
                    ps.getAgentid());
            return rows;

        } else {
            sql = "INSERT INTO parameters(name,agentid,operator,params,opertime) VALUES (?,?,?,to_json(?::json),LOCALTIMESTAMP)";
            int rows = jdbcTemplate.update(sql,
                    ps.getName(),
                    ps.getAgentid(),
                    ps.getOperator(),
                    ps.getParams().toString());
            return rows;
        }
    }

    public List<Params> getDevPatternByPara(String sql) {

        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);
        List<Params> abm = new ArrayList<>();
        for (Map map : lvRet) {
            Params tt = new Params();
            tt.setId((int) map.get("id"));
            tt.setName((String) map.get("name"));
            tt.setAgentid((int) map.get("agentid"));
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String str = sdf.format(map.get("opertime"));
            tt.setOpertime(str);
            tt.setOperator((String) map.get("operator"));
            JsonObject param = new JsonParser().parse(map.get("params").toString()).getAsJsonObject();
            tt.setParams(param);
            abm.add(tt);
        }
        return abm;
    }

    public List<String> getFaultDev() {
        String sql = "select DISTINCT(agentid) from fault where m_un_fault_renew_time = 0";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    public List<AscsBaseModel> getDevByPara(String sql) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //使用Sqlite数据库时，两个请求同时访问会将数据库锁定，因此添加此处添加一个锁
        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);
        List<AscsBaseModel> abm = new ArrayList<>();
        Gson gs = new Gson();
        for (Map map : lvRet) {
            AscsBaseModel tt = new AscsBaseModel();
            tt.setId((int) map.get("id"));
            tt.setThirdplatformid((String) map.get("thirdplatformid"));
            tt.setPlatform((String) map.get("platform"));
            tt.setGbid((String) map.get("gbid"));
            tt.setFirm((String) map.get("firm"));
            tt.setAgentid((String) map.get("agentid"));
            tt.setProtocol((String) map.get("protocol"));
            tt.setType((String) map.get("type"));
            tt.setDescs((String) map.get("descs"));
            tt.setStatus((int) map.get("status"));
            String geometry = (String) map.get("geometry");
            tt.setCode((String) map.get("code"));
            tt.setGeometry(gs.fromJson(geometry, MyGeometry.class));
            tt.setState((String) map.get("state"));
            if (map.get("lastTime") != null) {
                tt.setLastTime(sdf.parse(map.get("lastTime").toString()));
            }
            tt.setName((String) map.get("name"));
            JsonObject jsonparam = new JsonParser().parse(map.get("jsonparam").toString()).getAsJsonObject();
            tt.setJsonparam(jsonparam);
            Integer integer = (Integer) map.get("sockettype");
            tt.setSockettype(integer == null ? 0 : integer.intValue());
            abm.add(tt);
        }
        return abm;
    }

    @Transactional
    public int deleteDevByID(String id) {
        String sql = "DELETE FROM dev WHERE agentid ='" + id + "'";
        int rows = jdbcTemplate.update(sql);
        return rows;
    }


    public AscsBaseModel insertDev(AscsBaseModel ascs) {

        int id = ascs.getId();
        String sql = "INSERT INTO dev(platform, gbid, firm, agentid,protocol,type,descs,status,geometry,jsonparam,name,sockettype,lasttime) VALUES (?, ?,?,?,?,?,?,?,?,to_json(?::json),?,?,LOCALTIMESTAMP)";
        if (id == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String finalSql = sql;
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(finalSql, new String[]{"id"});
                    ps.setObject(1, ascs.getPlatform());
                    ps.setObject(2, ascs.getGbid());
                    ps.setObject(3, ascs.getFirm());
                    ps.setObject(4, ascs.getAgentid());
                    ps.setObject(5, ascs.getProtocol());
                    ps.setObject(6, ascs.getType());
                    ps.setObject(7, ascs.getDescs());
                    ps.setObject(8, ascs.getStatus());
                    if (ascs.getGeometry() != null) {
                        ps.setObject(9, ascs.getGeometry().toString());
                    } else {
                        ps.setObject(9, null);
                    }
                    ps.setObject(10, ascs.getJsonparam().toString());
                    ps.setObject(11, ascs.getName());
                    ps.setObject(12, ascs.getSockettype());
                    return ps;
                }
            }, keyHolder);
            ascs.setId(keyHolder.getKey().intValue());
        } else {
            sql = "INSERT INTO dev(gbid,firm,name,id,agentid,protocol,type,descs,status,geometry,jsonparam,sockettype,lasttime) VALUES (?,?,?,?,?,?,?,?,?,?,to_json(?::json),?,LOCALTIMESTAMP)";
            jdbcTemplate.update(sql,
                    ascs.getGbid(),
                    ascs.getFirm(),
                    ascs.getName(),
                    ascs.getId(),
                    ascs.getAgentid(),
                    ascs.getProtocol(),
                    ascs.getType(),
                    ascs.getDescs(),
                    ascs.getStatus(),
                    ascs.getGeometry().toString(),
                    ascs.getJsonparam().toString(),
                    ascs.getSockettype());
        }

        redisTemplate.convertAndSend("updateIdMap", "UpdateDev:" + ascs.getAgentid());
        initMap();
        
        return ascs;
    }


    public int getDevByAgentid(String agentid) {
        String sql = "SELECT count(id) FROM dev where agentid = ?";
        int count = jdbcTemplate.queryForObject(sql, int.class, agentid);
        return count;
    }


    public int updateDev(AscsBaseModel ascs) {
        //判断是否存在
        String sql = "SELECT count(id) FROM dev where agentid = ?";
        long count = jdbcTemplate.queryForObject(sql, Long.class, ascs.getAgentid());
        //ID已存在，更新注册信息,不存在，返回；
        if (count == 0) return 0;
        sql = "update dev set thirdplatformid=?,platform=?, gbid=?, firm=?, name=?, type=? ,protocol=? ,descs=? ,status=? ,geometry= ?,jsonparam=(to_json(?::json)),code=?,sockettype=? where agentid=?";
        String geometry = null;
        if (ascs.getGeometry() != null) {
            geometry = ascs.getGeometry().toString();
        }
        int res = jdbcTemplate.update(sql,
                ascs.getThirdplatformid(),
                ascs.getPlatform(),
                ascs.getGbid(),
                ascs.getFirm(),
                ascs.getName(),
                ascs.getType(),
                ascs.getProtocol(),
                ascs.getDescs(),
                ascs.getStatus(),
                geometry,
                ascs.getJsonparam().toString(),
                ascs.getCode(),
                ascs.getSockettype(),
                ascs.getAgentid()
        );
        return res;
    }


    public void updateDevByCode(String code, String agentid) {
        String sql = "update dev set code=? where agentid=? ";
        jdbcTemplate.update(sql, code, agentid);
    }


    public List<String> getAllAgentids() {

        List<String> agentids = jdbcTemplate.query("select distinct agentid from dev", new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });
        return agentids;
    }


    public List<AscsBaseModel> alterStatus(List<AscsBaseModel> ascsBaseModels) {
        List<String> faultDevAgentids = getFaultDev();
        for (AscsBaseModel ascsBaseModel : ascsBaseModels) {
            if (faultDevAgentids.contains(ascsBaseModel.getAgentid())) {
                if (ascsBaseModel.getState().equals("DOWN")) {
                    continue;
                }
                ascsBaseModel.setState("FAULT");
            }
        }
        return ascsBaseModels;
    }

    public int updateAscsByReport(DevCover devCover) {

        String login_thirpartyid = devCover.getThirdpartyid(); // 设备上报的真实id
        if(login_thirpartyid == null){
            logger.warning("Report thirpartyid = null, devCover = " + devCover.toString());
            return 0;
        }

        AscsBaseModel ascsModel = new AscsBaseModel();
        ascsModel.setType(devCover.getType());
        ascsModel.setStatus(devCover.getStatus());

        // 设备地理信息
        MyGeometry myGeometry = new MyGeometry();
        myGeometry.setType("Point");
        myGeometry.setCoordinates(new double[]{devCover.getLat(), devCover.getLng()});
        ascsModel.setGeometry(myGeometry);

        // 设备拓展参数
        JsonObject jo = new JsonObject();
        jo.addProperty("ip", devCover.getIp());
        jo.addProperty("port", devCover.getPort());
        if (devCover.getModel() != null)
            jo.addProperty("model", devCover.getModel());
        ascsModel.setJsonparam(jo);
        ascsModel.setProtocol(devCover.getProtocol());
        ascsModel.setThirdplatformid(login_thirpartyid);

        ascsModel.setAgentid(devCover.getAgentid());

        //更新数据
        String sql = "update dev set thirdplatformid=?, type=?,status=?,protocol=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where agentid = ?";
        int rows = jdbcTemplate.update(sql,
                ascsModel.getThirdplatformid(),
                ascsModel.getType(),
                ascsModel.getStatus(),
                ascsModel.getProtocol(),
                ascsModel.getJsonparam().toString(),
                ascsModel.getAgentid());

        // update失败，则说明agentid不存在，表明第一次上报，新设置agentid,并发送更新通知
        if (rows == 0) {
            sql = "INSERT INTO dev(agentid, thirdplatformid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
            rows = jdbcTemplate.update(sql,
                    ascsModel.getAgentid(),
                    ascsModel.getThirdplatformid(),
                    ascsModel.getType(),
                    ascsModel.getStatus(),
                    ascsModel.getProtocol(),
                    ascsModel.getGeometry().toString(),
                    ascsModel.getJsonparam().toString());

            redisTemplate.convertAndSend("updateIdMap", "UpdateDev:" + ascsModel.getAgentid());
        }
        return rows;
    }

    public String getAgentidFromThirdPartyid(String thirdpartyid) {
        String agentid = thirdidToAgentidOcp.get(thirdpartyid);

        if(agentid == null){
            // 检查一下是不是有新的设备加入数据库，缓存没有更新
            initMap();
            agentid = thirdidToAgentidOcp.get(thirdpartyid);
            if(agentid == null){
                // 还是没有agentid，则表明是新的设备
                agentid = String.valueOf(System.currentTimeMillis());
            }
        }

        return agentid;
    }
}
