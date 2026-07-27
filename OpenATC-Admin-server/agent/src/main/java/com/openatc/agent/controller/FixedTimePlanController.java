package com.openatc.agent.controller;


import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.optimize.fixedtimeplan.config.cross.CrossConfig;
import com.openatc.optimize.fixedtimeplan.model.control.Cross;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/24 13:10
 */
@RestController
@RequestMapping("/fixedtimeplan")
@Slf4j
public class FixedTimePlanController {

    @PostMapping("")
    /*
    @ApiOperation(value = "post 根据流量计算最优固定配时方案",notes = "根据流量计算最优固定配时方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "路口名称", dataType = "String", paramType = "path", required = false),
            @ApiImplicitParam(name = "agentid", value = "信号机编号", dataType = "String", paramType = "path", required = false),
            @ApiImplicitParam(name = "red", value = "红灯时间, 默认2秒", dataType = "int", paramType = "path", defaultValue = "2", required = true),
            @ApiImplicitParam(name = "yellow", value = "黄灯时间, 默认3秒", dataType = "int", paramType = "path", defaultValue = "3", required = true),
            @ApiImplicitParam(name = "loss", value = "车辆启动损失时间, 默认3秒", dataType = "int", paramType = "path", defaultValue = "3", required = false),
            @ApiImplicitParam(name = "rings", value = "环描述 num: 1 sequence: [1, 2, 5, 6]， 环编号和相序", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "barrier", value = "屏障描述，id: 1 phases: [[1, 2], [3, 4]]， 屏障编号和所属相位" , dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "phases", value = "相位描述  id: 5 flowperhour: 200 saturation: 1650 相位编号, 小时流量, 饱和流量", dataType = "String", paramType = "path", required = true),


    })

     */
    public RESTRetBase calcFixedTimePlan(@RequestBody CrossConfig crossConfig){
        log.info("CrossConfig:{}", crossConfig);
        Cross cross = Cross.builder()
                .agentid(crossConfig.getAgentid())
                .crossConfig(crossConfig)
                .phaseMap(new HashMap<>())
                .build();
        cross.updatePhaseMap();
        return RESTRetUtils.successObj(cross.calcFixedtimePlan());

    }

}
