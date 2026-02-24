package com.openatc.agent.controller;

import com.google.gson.JsonObject;
import com.openatc.agent.service.HistoryDataDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/18 17:39
 * @description：历史数据查询API
 * @modified By：
 * @version: $
 */

@RestController
@RequestMapping("/historydata")
public class HistoryDataController {

    @Autowired
    private HistoryDataDao historyDataDao;

    /**
     * @return 历史方案记录
     * @Date 2021/9/1 9:49
     * @Descripation 查询指定agentId范围内的历史方案记录
     * 没有上传时间范围则查询1天的历史数据
     */
    @PostMapping(value = "/pattern/range")
    public RESTRetBase getPatternRange(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentId").getAsString();
        String beginTime = jsonObject.get("beginTime").getAsString();
        String endTime = jsonObject.get("endTime").getAsString();
        return RESTRetUtils.successObj(historyDataDao.GetPatternData(agentId,beginTime,endTime));
    }

    /**
     * @return 历史流量记录
     * @Date 2021/9/1 9:49
     * @Descripation 查询指定agentId范围内的历史流量记录
     * 没有上传时间范围则查询1天的历史数据
     */
    @PostMapping(value = "/flow/range")
    public RESTRetBase getFlowRange(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentId").getAsString();
        String beginTime = jsonObject.get("beginTime").getAsString();
        String endTime = jsonObject.get("endTime").getAsString();
        return RESTRetUtils.successObj(historyDataDao.GetFlowData(agentId,beginTime,endTime));
    }
}
