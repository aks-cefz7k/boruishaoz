package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.openatc.agent.service.FeatureService;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.Message;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.Split;
import com.openatc.model.model.StageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openatc.core.common.IErrorEnumImplOuter.E_1001;


/**
 * @Classname FeatureController
 * @Description 参数管理API
 * @Date 2022/1/25 17:10
 * @Created by panleilei
 */
@RestController
public class FeatureController {
    @Autowired
    FeatureService featureService;
    private Gson gson = new Gson();

    /**
     * 环转阶段
     * @param agentid
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/feature/ringtostage/{agentid}")
    public RESTRet convertRingToStage(@PathVariable String agentid, @RequestBody JsonObject jsonObject){
//        List<Pattern> patternList = gson.fromJson(jsonObject.get("patternList"), new TypeToken<List<Pattern>>(){}.getType());
        List<List<List<Split>>> ringsList = gson.fromJson(jsonObject.get("ringsList"),new TypeToken<List<List<List<Split>>>>(){}.getType());
        if (ringsList == null){
            return RESTRetUtils.errorObj(false,E_1001);
        }else {
            return RESTRetUtils.successObj(featureService.convertPatternRingToStage(agentid,ringsList));
        }
    }

    /**
     * 阶段转环
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/feature/stagetoring")
    public RESTRet convertStageToRing(@RequestBody JsonObject jsonObject){
        List<List<StageParam>> stageParamList = gson.fromJson(jsonObject.get("stageParamList"), new TypeToken<List<List<StageParam>>>() {}.getType());
        if (stageParamList == null){
            return RESTRetUtils.errorObj(false,E_1001);
        }else {
            return RESTRetUtils.successObj(featureService.convertStageToPattern(stageParamList));
        }
    }

    /**
     * 保存参数到数据库
     * @param requestData
     * @return
     */
    @PostMapping(value = "/feature/savefeature")
    public RESTRet saveFeature(@RequestBody MessageData requestData){
        return featureService.saveFeatureAll(requestData);
    }


    /**
     * 根据设备id获取参数
     * @param agentid
     * @return
     */
    @GetMapping(value = "/feature/getfeature/{agentid}")
    public RESTRet getFeature(@PathVariable String agentid){
        return featureService.getFeatureAll(agentid);
    }

}



















