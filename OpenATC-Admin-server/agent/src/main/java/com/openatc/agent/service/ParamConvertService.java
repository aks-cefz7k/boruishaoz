package com.openatc.agent.service;

import com.openatc.model.model.Pattern;
import com.openatc.model.model.Split;
import com.openatc.model.model.StageParam;

import java.util.List;

/**
 * @Classname ParacConvertService
 * @Description 参数转换接口
 * @Date 2022/1/25 17:49
 * @Created by panleilei
 */
public interface ParamConvertService {

     List<StageParam> convertPatternRingToStage(String agentid,List<List<List<Split>>> ringsList);

     List<List<Split>> convertStageToPattern(List<StageParam> stageParamList);
}
