package com.openatc.agent.A1049.controller;


import com.openatc.agent.A1049.model.*;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Slf4j
public class JsonMessageProcessorController {
    static A1049Message a1049Message = null;
    static {
        a1049Message = new A1049Message();
        Body body = new Body();
        Operation operation = new Operation();

        TSCCmd tscCmd = new TSCCmd();

        tscCmd.setId("01");
        tscCmd.setNo("01");
        tscCmd.setObjname("信号机");

        DetParam detParam1 = new DetParam();
        detParam1.setDistance(500F);
        detParam1.setCrossID("1001");
        detParam1.setDetID("1001");

        DetParam detParam2 = new DetParam();
        detParam2.setDistance(500F);
        detParam2.setCrossID("1002");
        detParam2.setDetID("1002");
        List<DetParam> detList = new ArrayList<>();
        detList.add(detParam1);
        detList.add(detParam2);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        detParam1.setLanenoList(list);

        operation.setDetList(detList);

        operation.setTscCmd(tscCmd);
        operation.setOrder("X");
        operation.setName("Get");
        body.setOperation(operation);
        a1049Message.setBody(body);
        a1049Message.setFrom("TCIP");
        a1049Message.setToken("令牌");
        a1049Message.setTo("目的地址");
        a1049Message.setType("REQUEST");
        a1049Message.setSeq("序列号");
    }
    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/json")
    public A1049Message processJson(@RequestBody MessageData requestData) {


        String url = "http://localhost:10003/openatc/xml";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<A1049Message> formEntity = new HttpEntity<>(a1049Message, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, formEntity, String.class);
        System.out.println(stringResponseEntity);
        return null;
    }
}
