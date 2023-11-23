package com.opentc.overflowopt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.opentc.overflowopt.entity.Overflow;
import com.opentc.overflowopt.model.AscPattern;
import com.opentc.overflowopt.model.OptDev;
import com.opentc.overflowopt.repository.OverflowRepository;
import com.opentc.overflowopt.service.OptService;
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
import java.util.List;

@RestController
public class OverflowoptController {

    private Logger logger = LoggerFactory.getLogger(OverflowoptController.class);

    @Autowired
    private OverflowRepository overflowRepository;


    @Autowired
    private OptService optService;

    @Resource
    private RestTemplate restTemplate;



    @Value("${openatc.devs.uri}")
    private String devUri;


    @PostMapping (value = "/overflow/control/{id}")
    public RESTRetBase OverflowControl(@PathVariable String id)
    {
        //根据detectionid开启溢出控制
        List<Overflow>  overflowList = new ArrayList<>();
        overflowList = overflowRepository.findByDetectionid(Long.valueOf(id));
        for(Overflow ov: overflowList)
        {
            String agentid = String.valueOf(ov.getIntersectionid());
            AscPattern ascPattern = optService.OptPattern(ov);
            OptDev optDev = optService.GetInterruptPattern(ascPattern, agentid);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(optDev);
            System.out.println(jsonObject);
            RESTRet restRet = optService.HttpPost(devUri, jsonObject);
        }

        //optService.OptPatterns(overflowList);
        //optService.GetPattern("10095");
        return RESTRetUtils.successObj(overflowList);
    }
}