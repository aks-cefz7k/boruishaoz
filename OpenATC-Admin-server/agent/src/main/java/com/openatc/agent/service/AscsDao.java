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
import com.openatc.agent.model.DevCover;
import com.openatc.agent.model.StatesCollectYesterday;
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.utils.RedisTemplateUtil;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.MyGeometry;
import com.openatc.model.model.StatusPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.logging.Logger;

@Repository
public class AscsDao {

    private static Logger logger = Logger.getLogger(AscsDao.class.toString());
    public static  StatesCollectYesterday statesCollectYesterday = new StatesCollectYesterday();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplateUtil redisTemplate;

    private Map<String, String> thirdidToAgentidOcp = new HashMap<>();

    Gson gson = new Gson();

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
    public List<AscsBaseModel> getAscs() {
        String sql = "SELECT id, thirdplatformid, platform, gbid, firm, agentid, protocol, geometry, code, type, status, descs, name,jsonparam, case (LOCALTIMESTAMP - lastTime)< '1 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime,sockettype,tags FROM dev ORDER BY agentid";
        List<AscsBaseModel> ascsBaseModels = getDevByPara(sql);

        return ascsBaseModels;
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
            ascBase.setGeometry(gson.fromJson(geometry, MyGeometry.class));
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
            redisTemplate.publish("updateIdMap", "UpdateDev:" + newAgentid);
            initMap();

        } catch (Exception e) {

            return false;
        }

        return true;
    }

    public AscsBaseModel getAscsByGBID(String id) throws EnumConstantNotPresentException {

        AscsBaseModel ascsBaseModel = null;
        String sql =
                "SELECT id, thirdplatformid , platform, gbid, firm, agentid,protocol, geometry,type,status,descs,name, jsonparam,case (LOCALTIMESTAMP - lastTime)< '1 min' when true then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev WHERE gbid ='"
                        + id + "'";
        List<AscsBaseModel> listAscs = getDevByPara(sql);

        if (listAscs.size() != 1) {
            ascsBaseModel = listAscs.get(0);
        }else{
            logger.warning("Search GBID devs error :" + listAscs);
        }

        return ascsBaseModel;
    }


    public AscsBaseModel getAscsByID(String id) throws EnumConstantNotPresentException {

        AscsBaseModel ascsBaseModel = null;
        String sql =
                "SELECT id, thirdplatformid , platform, gbid, firm, agentid,protocol, geometry,type,status,descs,name, jsonparam,case (LOCALTIMESTAMP - lastTime)< '1 min' when true then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev WHERE agentid ='"
                        + id + "'";
        List<AscsBaseModel> listAscs = getDevByPara(sql);

        if (listAscs.size() > 0) {
            ascsBaseModel = listAscs.get(0);
        }
        return ascsBaseModel;
    }


    public List<AscsBaseModel> getAscsByType(String type) throws EnumConstantNotPresentException {

        String sql = "SELECT id,agentid,protocol, geometry,type,status,descs, name,jsonparam,case (LOCALTIMESTAMP - lastTime)< '1 min' when true then 'UP' else 'DOWN' END AS state FROM dev WHERE type ='" + type + "'";
        List<AscsBaseModel> listAscs = getDevByPara(sql);

        return listAscs;
    }

    /**
     * @Author: yangyi
     * @Date: 2021/12/21 10:25
     * @Description: getListByAgentids
     */ 
    public List<AscsBaseModel> getListByAgentids(List<String> list) throws EnumConstantNotPresentException {
        List<AscsBaseModel> listAscs = new ArrayList<AscsBaseModel>();
        if (list != null && list.size() > 0) {
            String agentids = getInCond(list);
            String sql =
                "SELECT id, thirdplatformid , platform, gbid, firm, agentid,protocol, geometry,type,status,descs,name, jsonparam,case (LOCALTIMESTAMP - lastTime)< '1 min' when true then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev WHERE agentid in "
                    + agentids;
            listAscs = getDevByPara(sql);
        }
        return listAscs;
    }
    /**
     * @Author: yangyi
     * @Date: 2021/12/21 13:51
     * @Description:
     */
    public static String getInCond(List<String> list) {
        String[] strArr = list.toArray(new String[list.size()]);
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < strArr.length; i++){
            if(i == strArr.length-1){
                sb.append("'"+strArr[i]+"'");
            }else{
                sb.append("'"+strArr[i]+"'"+",");
            }
        }
        return '(' +sb.toString()+ ')';
    }

    /**
     * @Author: yangyi
     * @Date: 2021/12/21 10:34
     * @Description: getDevFromList
     */ 
    public AscsBaseModel getDevFromList (List<AscsBaseModel> list, String agentid) {
        AscsBaseModel ascsBaseModel = null;
        if (list != null && list.size() > 0 && agentid != null) {
            for (AscsBaseModel dev: list) {
                if (agentid.equals(dev.getAgentid())){
                    ascsBaseModel = dev;
                }
            }
        }
        return ascsBaseModel;
    }

    /**
     * @Author: yangyi
     * @Date: 2021/12/21 14:25
     * @Description: getDevNameFromList
     */ 
    public String getDevNameFromList (List<AscsBaseModel> list, String agentid) {
        AscsBaseModel ascsBaseModel = getDevFromList(list, agentid);
        String agentName = "";
        if (ascsBaseModel != null) {
            agentName = ascsBaseModel.getName();
            if (agentName == null || agentName.equals("")) {
                agentName = ascsBaseModel.getAgentid();
            }
        }
        return  agentName;
    }


    public List<String> getFaultDev() {
        String sql = "select DISTINCT(agentid) from fault where m_un_fault_renew_time = 0";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    public List<AscsBaseModel> getDevByPara(String sql)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);
        List<AscsBaseModel> abm = new ArrayList<>();
        for (Map map : lvRet) {
            AscsBaseModel ascsBaseModel = new AscsBaseModel();
            ascsBaseModel.setId((int) map.get("id"));
            ascsBaseModel.setThirdplatformid((String) map.get("thirdplatformid"));
            ascsBaseModel.setPlatform((String) map.get("platform"));
            ascsBaseModel.setGbid((String) map.get("gbid"));
            ascsBaseModel.setFirm((String) map.get("firm"));
            ascsBaseModel.setAgentid((String) map.get("agentid"));
            ascsBaseModel.setProtocol((String) map.get("protocol"));
            ascsBaseModel.setType((String) map.get("type"));
            ascsBaseModel.setDescs((String) map.get("descs"));
            ascsBaseModel.setStatus((int) map.get("status"));
            String geometry = (String) map.get("geometry");
            ascsBaseModel.setCode((String) map.get("code"));
            ascsBaseModel.setGeometry(gson.fromJson(geometry, MyGeometry.class));
            ascsBaseModel.setState((String) map.get("state"));
            ascsBaseModel.setTags((String) map.get("tags"));
            if (map.get("lastTime") != null) {
                try {
                    ascsBaseModel.setLastTime(sdf.parse(map.get("lastTime").toString()));
                } catch (ParseException e) {
                    ascsBaseModel.setLastTime(null);
                }
            }
            ascsBaseModel.setName((String) map.get("name"));
            JsonObject jsonparam = new JsonParser().parse(map.get("jsonparam").toString()).getAsJsonObject();
            ascsBaseModel.setJsonparam(jsonparam);
            Integer integer = (Integer) map.get("sockettype");
            ascsBaseModel.setSockettype(integer == null ? 0 : integer.intValue());
            abm.add(ascsBaseModel);
            // 从Redis中获取当前控制方式
            StatusPattern statusPattern = redisTemplate.getStatusPatternFromRedis(ascsBaseModel.getAgentid());
            if(statusPattern != null){
                ascsBaseModel.setMode(statusPattern.getMode());
                ascsBaseModel.setControl(statusPattern.getControl());
            }

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

        String sql = "INSERT INTO dev(agentid,thirdplatformid,gbid,name,descs,type,protocol,sockettype,firm,platform,geometry,tags,jsonparam) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,to_json(?::json))";
        jdbcTemplate.update(sql,
                ascs.getAgentid(),
                ascs.getThirdplatformid(),
                ascs.getGbid(),
                ascs.getName(),
                ascs.getDescs(),
                ascs.getType(),
                ascs.getProtocol(),
                ascs.getSockettype(),
                ascs.getFirm(),
                ascs.getPlatform(),
                ascs.getGeometry().toString(),
                ascs.getTags(),
                ascs.getJsonparam().toString()
                );

        redisTemplate.publish("updateIdMap", "UpdateDev:" + ascs.getAgentid());
        initMap();
        
        return ascs;
    }


    public int getCountByAgentid(String agentid) {
        String sql = "SELECT count(id) FROM dev where agentid = ?";
        int count = jdbcTemplate.queryForObject(sql, int.class, agentid);
        return count;
    }

    // 查询数据库连接数
    public int getConnectCount() {
        String sql = "select count(1) from pg_stat_activity";
        int count = jdbcTemplate.queryForObject(sql, int.class);
        return count;
    }

    // 查询数据库大小
    public String getDBSize(String db) {
        String sql = "select pg_size_pretty(pg_database_size( ? ));";
        String size = jdbcTemplate.queryForObject(sql, String.class, db);
        return size;
    }


    public int updateDev(AscsBaseModel ascs) {
        //判断是否存在
        long count = getCountByAgentid(ascs.getAgentid());
        //ID已存在，更新注册信息,不存在，返回；
        if (count == 0) return 0;
        String sql = "update dev set thirdplatformid=?,gbid=?,platform=?, firm=?, name=?, type=? ,protocol=? ,descs=? ,geometry= ?,jsonparam=(to_json(?::json)),code=?,sockettype=?,tags=? where agentid=?";
        String geometry = null;
        if (ascs.getGeometry() != null) {
            geometry = ascs.getGeometry().toString();
        }
        int res = jdbcTemplate.update(sql,
                ascs.getThirdplatformid(),
                ascs.getGbid(),
                ascs.getPlatform(),
                ascs.getFirm(),
                ascs.getName(),
                ascs.getType(),
                ascs.getProtocol(),
                ascs.getDescs(),
                geometry,
                ascs.getJsonparam().toString(),
                ascs.getCode(),
                ascs.getSockettype(),
                ascs.getTags(),
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


//    public List<AscsBaseModel> alterStatus(List<AscsBaseModel> ascsBaseModels) {
//        List<String> faultDevAgentids = getFaultDev();
//        for (AscsBaseModel ascsBaseModel : ascsBaseModels) {
//            if (faultDevAgentids.contains(ascsBaseModel.getAgentid())) {
//                if (ascsBaseModel.getState().equals("DOWN")) {
//                    continue;
//                }
//                ascsBaseModel.setState("FAULT");
//            }
//        }
//        return ascsBaseModels;
//    }

    public int updateAscsByReport(DevCover devCover) {

        String devsid = devCover.getThirdpartyid(); // 设备上报的真实id
        if(devsid == null){
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
        ascsModel.setThirdplatformid(devsid);

        ascsModel.setAgentid(devCover.getAgentid());
        ascsModel.setName(devsid);

        //更新数据
        String sql = "update dev set thirdplatformid=?, type=?,status=?,protocol=?,lastTime=LOCALTIMESTAMP where agentid = ?";
        int rows = jdbcTemplate.update(sql,
                ascsModel.getThirdplatformid(),
                ascsModel.getType(),
                ascsModel.getStatus(),
                ascsModel.getProtocol(),
//                ascsModel.getJsonparam().toString(),
                ascsModel.getAgentid());

        // update失败，则说明agentid不存在，表明第一次上报，新设置agentid,并发送更新通知
        if (rows == 0) {
            sql = "INSERT INTO dev(agentid, name, thirdplatformid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
            rows = jdbcTemplate.update(sql,
                    ascsModel.getAgentid(),
                    ascsModel.getName(),
                    ascsModel.getThirdplatformid(),
                    ascsModel.getType(),
                    ascsModel.getStatus(),
                    ascsModel.getProtocol(),
                    ascsModel.getGeometry().toString(),
                    ascsModel.getJsonparam().toString());

            redisTemplate.publish("updateIdMap", "UpdateDev:" + ascsModel.getAgentid());
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
                Random rand = new Random();
                int randint = rand.nextInt(900)+ 100;// 三位随机整数
                agentid =  thirdpartyid + "-" + randint;
            }
        }

        return agentid;
    }

    /**
     * @Author zhangwenchao
     * @Description 获取分页过滤路口列表
     * @Date 2022/1/12 16:48
    */
    public PageOR<AscsBaseModel> getAscsRange(JsonObject jsonObject) {
        PageOR<AscsBaseModel> pageOR = new PageOR<>();
        if (jsonObject == null) {
            return null;
        }

        // 分页条件
        int pageNum = jsonObject.get("pageNum").getAsInt() - 1;
        int pageRow = jsonObject.get("pageRow").getAsInt();
        if (pageNum < 0)
            pageNum = 0;
        String limitCondition = String.format(" limit  %d  offset  %d ",pageRow, pageNum*pageRow );

        // 查询条件
        String whereCondition = "";
        String search = jsonObject.get("search").getAsString();
        if( !search.isEmpty()){
            String temp = String.format("( agentid like '%%%s%%' or name like '%%%s%%' or thirdplatformid like '%%%s%%' or jsonparam::text like '%%%s%%' ) ",search,search,search,search);
            whereCondition = addWhereCondition(whereCondition,temp);
        }
        String type = jsonObject.get("type").getAsString();
        if( !type.isEmpty()){
            String temp = String.format("type = '%s'",type);
            whereCondition = addWhereCondition(whereCondition,temp);
        }
        String platform = jsonObject.get("platform").getAsString();
        if( !platform.isEmpty()){
            String temp = String.format("platform = '%s'",platform);
            whereCondition = addWhereCondition(whereCondition,temp);
        }
        String protocol = jsonObject.get("protocol").getAsString();
        if( !protocol.isEmpty()){
            String temp = String.format("protocol = '%s'",protocol);
            whereCondition = addWhereCondition(whereCondition,temp);
        }
        String tags = jsonObject.get("tags").getAsString();
        if( !tags.isEmpty()){
            String temp = String.format("tags like '%%%s%%'",tags);
            whereCondition = addWhereCondition(whereCondition,temp);
        }
        String state = jsonObject.get("state").getAsString();
        if( !state.isEmpty()){
            String temp;
            if(state.equals("DOWN"))
                temp = "(LOCALTIMESTAMP - lastTime)> '1 min'";
            else
                temp = "(LOCALTIMESTAMP - lastTime)< '1 min'";
            whereCondition = addWhereCondition(whereCondition,temp);

        }

        // 获取总数
        String countsql = "SELECT count(*) FROM dev ";
        countsql = countsql.concat(whereCondition);
        int count = jdbcTemplate.queryForObject(countsql, int.class);
        pageOR.setTotal((long) count);
        if(count == 0){ //没有查询记录直接返回
            return pageOR;
        }

        // 获取分页记录
        String searchsql = "SELECT id, thirdplatformid, platform, gbid, firm, agentid, protocol, geometry, type, code, status, descs, name,jsonparam, " +
                "case (LOCALTIMESTAMP - lastTime)< '1 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime,sockettype,tags " +
                "FROM dev ";
        searchsql = searchsql.concat(whereCondition);
        searchsql = searchsql.concat(" ORDER BY agentid");
        searchsql = searchsql.concat(limitCondition);
        List<AscsBaseModel> ascsBaseModels = getDevByPara(searchsql);
        pageOR.setContent(ascsBaseModels);
        return pageOR;
    }

    /**
     * @Author zhangwenchao
     * @Description 添加where过滤条件
     * @Date 2022/1/13 10:16
    */
    private String addWhereCondition(String whereCondition, String temp) {
        if(whereCondition == null)
            whereCondition = "";

        if(whereCondition.isEmpty()) {
            whereCondition = whereCondition.concat(" where ");
            whereCondition = whereCondition.concat(temp);
        }
        else{
            whereCondition = whereCondition.concat(" and ");
            whereCondition = whereCondition.concat(temp);
        }

        return  whereCondition;
    }


    /**
     * 根据组织机构代码查询设备
     * @param ascss
     * @param orgnization_code
     * @return
     */
    public List<AscsBaseModel> getAscsByOrgCode(List<AscsBaseModel> ascss, String orgnization_code) {
        List<AscsBaseModel> ascsBaseModels = new ArrayList<>();
        // 用户不属于任何组织机构
        if (orgnization_code.equals("")){
            for (AscsBaseModel ascs : ascss){
                String code = ascs.getCode();
                if (code == null){
                    ascsBaseModels.add(ascs);
                }
            }
            return ascsBaseModels;
        }
        // 用户属于某个组织机构
        for (AscsBaseModel ascs : ascss){
            String code = ascs.getCode();
            if (orgnization_code.equals(code)){
                ascsBaseModels.add(ascs);
            }
        }
        return ascsBaseModels;

    }

}
