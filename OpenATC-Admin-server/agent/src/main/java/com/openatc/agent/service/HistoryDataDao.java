package com.openatc.agent.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/18 17:44
 * @description：历史数据数据库操作类
 * @modified By：
 * @version: $
 */

@Repository
public class HistoryDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 保存历史流量数据
    public int SaveFlowData(MessageData msg){
        String sql = String.format("INSERT INTO flow (agentid,time,data) VALUES ('%s','%s','%s')", msg.getAgentid(),msg.getCreatetime(),msg.getData().toString());
        int rows = jdbcTemplate.update(sql);
        return rows;
    }

    // 保存历史方案数据
    public int SavePatternData(MessageData msg){
        String sql = String.format("INSERT INTO pattern (agentid,time,data) VALUES ('%s','%s','%s')", msg.getAgentid(),msg.getCreatetime(),msg.getData().toString());
        int rows = jdbcTemplate.update(sql);
        return rows;
    }

    // 查询历史流量数据
    public List<HistoryData> GetFlowData(String agentId, String beginTime, String endTime){
        String sql = String.format("SELECT time,data FROM flow where agentid = '%s' and time between '%s' and '%s' order by time asc ", agentId,beginTime,endTime);
        List<Map<String, Object>> lvRet =  jdbcTemplate.queryForList(sql);
        return convertHistoryData(lvRet);
    }

    // 查询历史方案数据
    public List<HistoryData> GetPatternData(String agentId, String beginTime, String endTime) {
        String sql = String.format("SELECT time,data FROM pattern where agentid = '%s' and time between '%s' and '%s' order by time asc ", agentId, beginTime, endTime);
        List<Map<String, Object>> lvRet = jdbcTemplate.queryForList(sql);
        return convertHistoryData(lvRet);
    }

    // 将查询结果转换为历史数据对象
    private List<HistoryData> convertHistoryData(List<Map<String, Object>> lvRet){
        List<HistoryData> hdList = new ArrayList<>();
        for (Map map : lvRet) {
            HistoryData hd = new HistoryData();
            hd.time = map.get("time").toString();
            hd.data = new JsonParser().parse(map.get("data").toString()).getAsJsonObject();
            hdList.add(hd);
        }
        return hdList;
    }

    class HistoryData {
        public String time;
        public JsonObject data;
    }
}
