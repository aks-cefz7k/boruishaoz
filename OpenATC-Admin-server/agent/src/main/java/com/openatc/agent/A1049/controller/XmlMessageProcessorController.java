package com.openatc.agent.A1049.controller;

import com.openatc.agent.A1049.model.*;
import com.openatc.agent.A1049.service.XmlMessageProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class XmlMessageProcessorController {

    @Autowired
    XmlMessageProcessorService xmlMessageProcessorService;

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public A1049Message XML() {
        A1049Message a1049Message = new A1049Message();
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
        return a1049Message;
    }

    @PostMapping(value = "/xml", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public A1049Message cXML(@RequestBody A1049Message a1049Message) {
        return xmlMessageProcessorService.postDevsMessage(a1049Message);
    }
}
