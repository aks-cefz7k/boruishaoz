package com.openatc.agent.service;

import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public int saveFeatureAll(MessageData requestData) {
        String sql = String.format("INSERT INTO featureall (agentid,time,data) VALUES ('%s','%s','%s')", requestData.getAgentid(),requestData.getCreatetime(),requestData.getData().toString());
        int rows = jdbcTemplate.update(sql);
        return rows;
    }
}
