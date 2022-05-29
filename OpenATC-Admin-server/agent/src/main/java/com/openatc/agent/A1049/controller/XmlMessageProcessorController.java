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

    /**
     * 收到客户端的1049XML协议消息，转换成OpenATC的Json协议
     * @param a1049Message
     * @return
     */
    @PostMapping(value = "/a1049/xmlmessage", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public A1049Message cXML(@RequestBody A1049Message a1049Message) {
        return xmlMessageProcessorService.postDevsMessage(a1049Message);
    }
}
