package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.openatc.agent.service.ParamConvertService;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.Pattern;
import com.openatc.model.model.StageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openatc.core.common.IErrorEnumImplOuter.E_1001;


/**
 * @Classname ParamConvertController
 * @Description 参数转换API
 * @Date 2022/1/25 17:10
 * @Created by panleilei
 */
@RestController
public class ParamConvertController {
    @Autowired
    ParamConvertService paramConvertService;
    private Gson gson = new Gson();

    @PostMapping(value = "/param/ringtostage/{agentid}")
    public RESTRet convertRingToStage(@PathVariable String agentid, @RequestBody JsonObject jsonObject){
        List<Pattern> patternList = gson.fromJson(jsonObject.get("patternList"), new TypeToken<List<Pattern>>(){}.getType());
        if (patternList == null){
            return RESTRetUtils.errorObj(false,E_1001);
        }else {
            return RESTRetUtils.successObj(paramConvertService.convertPatternRingToStage(agentid,patternList));
        }
    }

    @PostMapping(value = "/param/stagetoring")
    public RESTRet convertStageToRing(@RequestBody JsonObject jsonObject){
        List<StageParam> stageParamList = gson.fromJson(jsonObject.get("stageParamList"), new TypeToken<List<StageParam>>() {}.getType());
        if (stageParamList == null){
            return RESTRetUtils.errorObj(false,E_1001);
        }else {
            return RESTRetUtils.successObj(paramConvertService.convertStageToPattern(stageParamList));
        }
    }


}



















