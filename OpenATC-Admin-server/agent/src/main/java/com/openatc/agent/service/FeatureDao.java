package com.openatc.agent.service;

import com.google.gson.Gson;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname FeatureDao
 * @Description
 * @Date 2022/3/29 13:33
 * @Created by panleilei
 */
@Repository
public class FeatureDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Gson gson = new Gson();

    public int saveFeatureAll(MessageData requestData) {
        String agentid = requestData.getAgentid();
        String time = requestData.getCreatetime();
        String data = gson.toJson(requestData);
        // 保证数据库中每个设备最多有一条记录
        String sql = String.format("INSERT INTO featureall (agentid,time,data) VALUES ('%s','%s','%s')",agentid,time,data);
        sql = sql + String.format("ON CONFLICT(agentid) DO UPDATE SET time='%s',data='%s'",time,data);
        int rows = jdbcTemplate.update(sql);
        return rows;
    }

    public String selectFeatureAll(String agentid) {
        String sql = String.format("select data from featureall where agentid='%s'",agentid);
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
