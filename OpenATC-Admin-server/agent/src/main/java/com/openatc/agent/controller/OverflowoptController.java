package com.openatc.agent.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openatc.agent.model.AscPattern;
import com.openatc.agent.model.ControlMsg;
import com.openatc.agent.model.OptDev;
import com.openatc.agent.model.Overflow;
import com.openatc.agent.service.OptService;
import com.openatc.agent.service.OverflowDetectorRepository;
import com.openatc.agent.service.OverflowRepository;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OverflowoptController {

    private static Logger logger = LoggerFactory.getLogger(OverflowoptController.class.toString());

    @Autowired
    private OverflowDetectorRepository overflowDetectorRepository;

    @Autowired
    private OverflowRepository overflowRepository;

    @Autowired
    private OptService optService;

    @Resource
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String devUri;

    //开启溢出控制
    @PostMapping (value = "/overflow/control/{id}")
    public RESTRetBase OverflowControl(@PathVariable String id)
    {
        //根据id开启溢出控制
        List<Overflow>  overflowList = new ArrayList<>();
        overflowList = overflowRepository.findByPatternid(Long.valueOf(id));
        List<String> error_offlines = new ArrayList<>();
        List<String> error_fails = new ArrayList<>();


        for(Overflow ov: overflowList)
        {
            String agentid = String.valueOf(ov.getIntersectionid());
            AscPattern ascPattern = optService.OptPattern(ov);
            if(ascPattern == null){
                //没有取到方案
                error_offlines.add(agentid);
                logger.warn("Device not on line, agentid = " + agentid);
                continue;
            }

            OptDev optDev = optService.GetInterruptPattern(ascPattern, agentid);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(optDev);
            //System.out.println(jsonObject);
            RESTRet restRet = optService.HttpPost(devUri, jsonObject);
            if(restRet.isSuccess() == false){
                logger.warn("Device overflow control failed, agentid = " + agentid);

                error_fails.add(agentid);
            }else{
                overflowRepository.updateIsopenByIntersectionid(agentid,true);
            }
            System.out.println(restRet);
        }

        List<DevCommError> errors = new ArrayList<>();
        if(error_offlines.isEmpty() && error_fails.isEmpty()){
            overflowDetectorRepository.updateStatusById(Long.valueOf(id),"1");
            return RESTRetUtils.successObj();
        }else{
            for(String agentid : error_offlines){
                DevCommError devCommError = new DevCommError();
                devCommError.setAgentid(agentid);
                devCommError.setInfortype("status/pattern");
                devCommError.setOperation("get-request");
                Map<String, Object> data = new HashMap<>();
                data.put("code","301");
                data.put("message", "Device not online!");
                devCommError.setData(data);
                errors.add(devCommError);
            }

            for(String agentid : error_fails){
                DevCommError devCommError = new DevCommError();
                devCommError.setAgentid(agentid);
                devCommError.setInfortype("control/interrupt");
                devCommError.setOperation("set-request");
                Map<String, Object> data = new HashMap<>();
                data.put("message","interrupt control failed!");
                devCommError.setData(data);
                errors.add(devCommError);
            }

            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_9001, errors);
        }
        //optService.OptPatterns(overflowList);
        //optService.GetPattern("10095");
        //return RESTRetUtils.successObj(overflowList);
    }


    //恢复自主控制
    @PostMapping(value = "/overflow/control/off/{id}")
    public RESTRetBase OffOverflowControl(@PathVariable String id)
    {
        //根据id恢复自主控制
        List<Overflow>  overflowList = new ArrayList<>();
        List<String> error_fails = new ArrayList<>();
        overflowList = overflowRepository.findByPatternid(Long.valueOf(id));
        for(Overflow ov: overflowList)
        {
            String agentid = String.valueOf(ov.getIntersectionid());
            OptDev<ControlMsg> controlMsgOptDev = optService.AutoControl(agentid);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(controlMsgOptDev);
            System.out.println(jsonObject);
            RESTRet restRet = optService.HttpPost(devUri, jsonObject);
            if(restRet.isSuccess() == false){
                error_fails.add(agentid);
            }
        }

        List<DevCommError> errors = new ArrayList<>();
        if(!error_fails.isEmpty()){
            for(String agentid : error_fails) {
                DevCommError devCommError = new DevCommError();
                devCommError.setAgentid(agentid);
                devCommError.setInfortype("control/interrupt");
                devCommError.setOperation("set-request");
                Map<String, Object> data = new HashMap<>();
                data.put("message", "auto control failed!");
                devCommError.setData(data);
                errors.add(devCommError);
            }
            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_9002,errors);
        }else{
            //全部恢复自主控制，则返回成功，并修改状态
            overflowDetectorRepository.updateStatusById(Long.valueOf(id),"2");

            return RESTRetUtils.successObj();
        }


    }
}
