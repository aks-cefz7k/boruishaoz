package com.openatc.agent.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openatc.agent.controller.DevController;
import com.openatc.comm.data.MessageData;
import com.openatc.model.model.AscsBaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/18 17:44
 * @description：历史数据数据库操作类
 * @modified By：
 * @version: $
 */

@Repository
public class HistoryDataDao {

    private static Logger logger = Logger.getLogger(HistoryDataDao.class.toString());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AscsDao mDao;

    @Autowired
    FaultDao faultDao;

    // 保存历史流量数据
    public int SaveFlowData(MessageData msg){
        String sql = String.format("INSERT INTO flow (agentid,time,data) VALUES ('%s','%s','%s')", msg.getAgentid(),msg.getCreatetime(),msg.getData().toString());
        int rows = jdbcTemplate.update(sql);
        return rows;
    }

    // 保存历史方案数据
    public int SavePatternData(MessageData msg){
        JsonObject msgdata = msg.getData().getAsJsonObject();

        try{
            msgdata.remove("overlap");
            JsonArray phases = msgdata.get("phase").getAsJsonArray();
            for (JsonElement phase : phases) {
                JsonObject phosedata = phase.getAsJsonObject();
                phosedata.remove("conphase");
                phosedata.remove("type");
                phosedata.remove("countdown");
                phosedata.remove("pedtype");
                phosedata.remove("pedcountdown");
                phosedata.remove("close");
            }
        }catch (Exception e){
            logger.warning(e.getMessage());
            return 0;
        }

        String sql = String.format("INSERT INTO pattern (agentid,time,data) VALUES ('%s','%s','%s')", msg.getAgentid(),msg.getCreatetime(),msgdata.toString());
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

    // 定时清理历史数据并统计设备状态数据
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearHistoryData() {
        logger.warning("Clearing Schedule Start!");
        // 历史方案数据默认保存7天
        String sql = String.format("delete from pattern where time < (now() - interval '7 day')");
        jdbcTemplate.execute(sql);
        logger.warning("Claer Pattern Data From DB Finished!");

        // 历史流量数据默认保存7天
        sql = String.format("delete from flow where time < (now() - interval '7 day')");
        jdbcTemplate.execute(sql);
        logger.warning("Claer Flow Data From DB Finished!");


        // 历史操作记录默认保存30天
        sql = String.format("delete from operation_record where opertime < (now() - interval '30 day')");
        jdbcTemplate.execute(sql);
        logger.warning("Claer Operation Record From DB Finished!");

        // 统计设备在线数
        List<AscsBaseModel> ascsBaseModels = mDao.getAscs();
        int online = 0;
        int offline = 0;
        for(AscsBaseModel ascsBaseModel : ascsBaseModels){
            if(ascsBaseModel.getState().equals("UP"))
                online++;
            else if(ascsBaseModel.getState().equals("DOWN"))
                offline++;

        }
        mDao.statesCollectYesterday.setOnline(online);
        mDao.statesCollectYesterday.setOffline(offline);

        // 统计设备故障数
        int fault = faultDao.getCurrentFaultDevCount();
        mDao.statesCollectYesterday.setFault(fault);
    }

    class HistoryData {
        public String time;
        public JsonObject data;
    }
}
