package com.openatc.agent.service;

import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;
import com.openatc.model.model.Split;
import com.openatc.model.model.StageParam;

import java.util.List;

/**
 * @Classname FeatureService
 * @Description 参数管理接口
 * @Date 2022/1/25 17:49
 * @Created by panleilei
 */
public interface FeatureService {

     List<StageParam> convertPatternRingToStage(String agentid,List<List<List<Split>>> ringsList);

     List<List<List<Split>>> convertStageToPattern(List<List<StageParam>> stageParamList);

     RESTRet saveFeatureAll(MessageData requestData);

     RESTRet getFeatureAll(String agentid);

    RESTRet deleteFeatureByAgentid(String agentid);
}
