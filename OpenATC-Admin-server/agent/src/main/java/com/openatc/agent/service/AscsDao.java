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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class AscsDao {
    @Value("${agent.server.mode.config}")
    private boolean isConfigMode;
    //@Resource
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

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

    public List<AscsBaseModel> getAscsInfo() {
        String sql = "SELECT id,agentid,protocol, geometry, lastTime, descs,type,status,jsonparam FROM dev ";

        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);

        return convertAscs(lvRet);
    }

    public AscsBaseModel getAscsByID(String id) throws EnumConstantNotPresentException {
        String sql = "SELECT id,name,agentid,protocol descs, geometry,type,status,jsonparam FROM dev WHERE agentid ='" + id + "'";
        Map<String, Object> lvRet = jdbcTemplate.queryForMap(sql);
        AscsBaseModel ascBase = new AscsBaseModel();
        Gson gs = new Gson();
        ascBase.setId((int) lvRet.get("id"));
        ascBase.setAgentid((String) lvRet.get("agentid"));
        ascBase.setProtocol((String) lvRet.get("protocol"));
        ascBase.setStatus((int) lvRet.get("status"));
        ascBase.setDescs((String) lvRet.get("descs"));
        ascBase.setType((String) lvRet.get("type"));
        ascBase.setName((String) lvRet.get("name"));
        String geometry = (String) lvRet.get("geometry");
        ascBase.setGeometry(gs.fromJson(geometry, MyGeometry.class));
        JsonObject jsonparam = new JsonParser().parse(lvRet.get("jsonparam").toString()).getAsJsonObject();
        ascBase.setJsonparam(jsonparam);
        return ascBase;
    }

    public int updateAscs(DevCover devCover) {

        AscsBaseModel ascsModel = new AscsBaseModel();
        ascsModel.setType(devCover.getType());
        ascsModel.setStatus(devCover.getStatus());

        double lat = devCover.getLat();
        double lng = devCover.getLng();
        double[] str;
        str = new double[]{lng, lat};
        MyGeometry myGeometry = new MyGeometry();
        myGeometry.setType("Point");
        myGeometry.setCoordinates(str);

        ascsModel.setGeometry(myGeometry);

        JsonObject jo = new JsonObject();
        jo.addProperty("ip", devCover.getIp());
        jo.addProperty("port", devCover.getPort());
        if (devCover.getModel() != null)
            jo.addProperty("model", devCover.getModel());

        ascsModel.setJsonparam(jo);
        ascsModel.setAgentid(devCover.getAgentid());
        ascsModel.setProtocol(devCover.getProtocol());
        Gson gson = new Gson();
        int updateCount = 0;
        int rows = 0;
        //判断是否存在该devid的记录
        if (devCover.getAgentid() == null) {
            String sqlIdAndJsonparamList = "SELECT id,jsonparam FROM dev";
            List<Map<String, Object>> idAndJsonparamList = jdbcTemplate.queryForList(sqlIdAndJsonparamList);
            for (Map<String, Object> idAndJson : idAndJsonparamList) {
                Map jsonparamMap = gson.fromJson(idAndJson.get("jsonparam").toString(), Map.class);
                int id = (int) idAndJson.get("id");
                int port = 0;
                if (!jsonparamMap.get("port").toString().equals("")) {
                    port = Double.valueOf(jsonparamMap.get("port").toString()).intValue();
                }
                if (jsonparamMap.get("ip").equals(devCover.getIp()) && port == devCover.getPort()) {
                    String sql = null;
                    if (isConfigMode) {
                        sql = "update dev set name=?, type=?,status=?,protocol=?,geometry=?,jsonparam=?,lastTime=datetime('now', 'localtime') where id = ?";
                    } else {
                        sql = "update dev set name=?, type=?,status=?,protocol=?,geometry=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where id = ?";
                    }
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getName(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getGeometry().toString(),
                            ascsModel.getJsonparam().toString(),
                            id);
                    updateCount++;
                }
            }
            if (updateCount == 0) {
                String sql = null;
                if (isConfigMode) {
                    sql = "INSERT INTO dev(name,agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,?,datetime('now', 'localtime'))";
                } else {
                    sql = "INSERT INTO dev(name,agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                }
                rows = jdbcTemplate.update(sql,
                        ascsModel.getName(),
                        System.currentTimeMillis() + "",
                        ascsModel.getType(),
                        ascsModel.getStatus(),
                        ascsModel.getProtocol(),
                        ascsModel.getGeometry() == null ? null : ascsModel.getGeometry().toString(),
                        ascsModel.getJsonparam() == null ? null : ascsModel.getJsonparam().toString());
            }
            return rows;
        } else {
            String sql = "SELECT count(id) FROM dev where agentid = ?";
            long count = jdbcTemplate.queryForObject(sql, Long.class, devCover.getAgentid());
            if (count != 0) {
                if (lat != 0 || lng != 0) {
                    sql = "update dev set name=?, type=?,status=?,protocol=?,geometry=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where agentid = ?";
                    String strGeo = ascsModel.getGeometry().toString();
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getName(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            strGeo,
                            ascsModel.getJsonparam().toString(),
                            devCover.getAgentid());
                } else {
                    sql = "update dev set name=?, type=?,status=?,protocol=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where agentid = ?";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getName(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getJsonparam().toString(),
                            devCover.getAgentid());
                }
                return rows;
            } else {
                if (lat != 0 || lng != 0) {
                    sql = "INSERT INTO dev(name,agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getName(),
                            ascsModel.getAgentid(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getGeometry().toString(),
                            ascsModel.getJsonparam().toString());
                } else {
                    sql = "INSERT INTO dev(name,agentid,type,status,protocol,jsonparam,lastTime) VALUES (?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getName(),
                            ascsModel.getAgentid(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getJsonparam().toString());
                }

                return rows;
            }
        }
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
            tt.setAgentid((String) map.get("agentid"));
            tt.setProtocol((String) map.get("protocol"));
            tt.setType((String) map.get("type"));
            tt.setDescs((String) map.get("descs"));
            tt.setStatus((int) map.get("status"));
            String geometry = (String) map.get("geometry");
            tt.setCode((String) map.get("code"));
            tt.setGeometry(gs.fromJson(geometry, MyGeometry.class));
            if (!isConfigMode) {
                tt.setState((String) map.get("state"));
                if (map.get("lastTime") != null) {
                    tt.setLastTime(sdf.parse(map.get("lastTime").toString()));
                }
            }
            tt.setName((String) map.get("name"));
            JsonObject jsonparam = new JsonParser().parse(map.get("jsonparam").toString()).getAsJsonObject();
            tt.setJsonparam(jsonparam);
            abm.add(tt);
        }
        return abm;
    }

    public int deleteDevByID(String id) {
        String sql = "DELETE FROM dev WHERE agentid ='" + id + "'";
        int rows = jdbcTemplate.update(sql);
        return rows;
    }

    public AscsBaseModel insertDev(AscsBaseModel ascs) {

        int id = ascs.getId();
        String sql = null;
        if (id == 0) {
            if (isConfigMode) {
                sql = "INSERT INTO dev(agentid,protocol,type,descs,status,geometry,jsonparam,name) VALUES (?,?,?,?,?,?,?,?)";
            } else {
                sql = "INSERT INTO dev(agentid,protocol,type,descs,status,geometry,jsonparam,name) VALUES (?,?,?,?,?,?,to_json(?::json),?)";
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            String finalSql = sql;
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(finalSql, new String[]{"id"});
                    ps.setObject(1, ascs.getAgentid());
                    ps.setObject(2, ascs.getProtocol());
                    ps.setObject(3, ascs.getType());
                    ps.setObject(4, ascs.getDescs());
                    ps.setObject(5, ascs.getStatus());
                    if (ascs.getGeometry() != null) {
                        ps.setObject(6, ascs.getGeometry().toString());
                    } else {
                        ps.setObject(6, null);
                    }
                    ps.setObject(7, ascs.getJsonparam().toString());
                    ps.setObject(8, ascs.getName());
                    return ps;
                }
            }, keyHolder);

            ascs.setId(keyHolder.getKey().intValue());
        } else {
            if (isConfigMode) {
                sql = "INSERT INTO dev(name,id,agentid,protocol,type,descs,status,geometry,jsonparam) VALUES (?,?,?,?,?,?,?,?,?)";
            } else {
                sql = "INSERT INTO dev(name,id,agentid,protocol,type,descs,status,geometry,jsonparam) VALUES (?,?,?,?,?,?,?,?,to_json(?::json))";
            }
            jdbcTemplate.update(sql,
                    ascs.getName(),
                    ascs.getId(),
                    ascs.getAgentid(),
                    ascs.getProtocol(),
                    ascs.getType(),
                    ascs.getDescs(),
                    ascs.getStatus(),
                    ascs.getGeometry().toString(),
                    ascs.getJsonparam().toString());
        }

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
        if (count == 0)
            return 0;
        if (isConfigMode) {
            sql = "update dev set name=?, type=? ,protocol=? ,descs=? ,status=? ,geometry=?,jsonparam=?, code=? where agentid=?";
        } else {
            sql = "update dev set name=?, type=? ,protocol=? ,descs=? ,status=? ,geometry= ?,jsonparam=(to_json(?::json)),code=? where agentid=?";
        }
        String geometry = null;
        if (ascs.getGeometry() != null) {
            geometry = ascs.getGeometry().toString();
        }
        jdbcTemplate.update(sql,
                ascs.getName(),
                ascs.getType(),
                ascs.getProtocol(),
                ascs.getDescs(),
                ascs.getStatus(),
                geometry,
                ascs.getJsonparam().toString(),
                ascs.getCode(),
                ascs.getAgentid()
        );

        return 1;
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

    public int updateAscsByReport(DevCover devCover) {

        AscsBaseModel ascsModel = new AscsBaseModel();
        ascsModel.setType(devCover.getType());
        ascsModel.setStatus(devCover.getStatus());

        double lat = devCover.getLat();
        double lng = devCover.getLng();
        double[] str;
        str = new double[]{lng, lat};
        MyGeometry myGeometry = new MyGeometry();
        myGeometry.setType("Point");
        myGeometry.setCoordinates(str);

        ascsModel.setGeometry(myGeometry);

        JsonObject jo = new JsonObject();
        jo.addProperty("ip", devCover.getIp());
        jo.addProperty("port", devCover.getPort());
        if (devCover.getModel() != null)
            jo.addProperty("model", devCover.getModel());

        ascsModel.setJsonparam(jo);
        ascsModel.setAgentid(devCover.getAgentid());
        ascsModel.setProtocol(devCover.getProtocol());
        Gson gson = new Gson();
        int updateCount = 0;
        int rows = 0;
        //判断是否存在该devid的记录
        if (devCover.getAgentid() == null) {
            String sqlIdAndJsonparamList = "SELECT id,jsonparam FROM dev";
            List<Map<String, Object>> idAndJsonparamList = jdbcTemplate.queryForList(sqlIdAndJsonparamList);
            for (Map<String, Object> idAndJson : idAndJsonparamList) {
                Map jsonparamMap = gson.fromJson(idAndJson.get("jsonparam").toString(), Map.class);
                int id = (int) idAndJson.get("id");
                int port = 0;
                if (!jsonparamMap.get("port").toString().equals("")) {
                    port = Double.valueOf(jsonparamMap.get("port").toString()).intValue();
                }
                if (jsonparamMap.get("ip").equals(devCover.getIp()) && port == devCover.getPort()) {
                    String sql = null;
                    if (isConfigMode) {
                        sql = "update dev set type=?,status=?,protocol=?,geometry=?,jsonparam=?,lastTime=datetime('now', 'localtime') where id = ?";
                    } else {
                        sql = "update dev set type=?,status=?,protocol=?,geometry=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where id = ?";
                    }
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getGeometry().toString(),
                            ascsModel.getJsonparam().toString(),
                            id);
                    updateCount++;
                }
            }
            if (updateCount == 0) {
                String sql = null;
                if (isConfigMode) {
                    sql = "INSERT INTO dev(agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,datetime('now', 'localtime'))";
                } else {
                    sql = "INSERT INTO dev(agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                }
                rows = jdbcTemplate.update(sql,
                        System.currentTimeMillis() + "",
                        ascsModel.getType(),
                        ascsModel.getStatus(),
                        ascsModel.getProtocol(),
                        ascsModel.getGeometry().toString(),
                        ascsModel.getJsonparam().toString());
            }
            return rows;
        } else {
            String sql = "SELECT count(id) FROM dev where agentid = ?";
            long count = jdbcTemplate.queryForObject(sql, Long.class, devCover.getAgentid());
            if (count != 0) {
                if (lat != 0 || lng != 0) {
                    sql = "update dev set type=?,status=?,protocol=?,geometry=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where agentid = ?";
                    String strGeo = ascsModel.getGeometry().toString();
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            strGeo,
                            ascsModel.getJsonparam().toString(),
                            devCover.getAgentid());
                } else {
                    sql = "update dev set type=?,status=?,protocol=?,jsonparam=(to_json(?::json)),lastTime=LOCALTIMESTAMP where agentid = ?";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getJsonparam().toString(),
                            devCover.getAgentid());
                }
                return rows;
            } else {
                if (lat != 0 || lng != 0) {
                    sql = "INSERT INTO dev(agentid,type,status,protocol,geometry,jsonparam,lastTime) VALUES (?,?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getAgentid(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getGeometry().toString(),
                            ascsModel.getJsonparam().toString());
                } else {
                    sql = "INSERT INTO dev(agentid,type,status,protocol,jsonparam,lastTime) VALUES (?,?,?,?,?,to_json(?::json),LOCALTIMESTAMP)";
                    rows = jdbcTemplate.update(sql,
                            ascsModel.getAgentid(),
                            ascsModel.getType(),
                            ascsModel.getStatus(),
                            ascsModel.getProtocol(),
                            ascsModel.getJsonparam().toString());
                }

                return rows;
            }
        }
    }
}