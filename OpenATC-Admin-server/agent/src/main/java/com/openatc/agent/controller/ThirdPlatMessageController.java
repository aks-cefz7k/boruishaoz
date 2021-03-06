package com.openatc.agent.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;

import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.ParseException;

import static com.openatc.comm.common.CommunicationType.COMM_SOCKET_TYPE_UDP;


@RestController
@CrossOrigin
public class ThirdPlatMessageController {

//    @Autowired
    private CommClient commClient = new CommClient();

    @Value("${agent.thirdplat.ip}")
    private String adapterIP;

    @Value("${agent.thirdplat.port}")
    private Integer adapterPort;

    private String protocolType = "scp";


    @PostMapping(value = "/centeradapter/custom")
    public RESTRet postDevsMessage(@RequestBody JsonObject jsonObject) throws SocketException, ParseException, UnsupportedEncodingException {

        //当收到第三方的post请求时，直接将该请求发送给centeradapter python脚本
        Gson gson = new Gson();
        MessageData message = new MessageData();
        message.setInfotype("control/pattern");
        message.setOperation("set-request");
        message.setData(jsonObject);
        MessageData responceData = null;

        try {
            responceData = commClient
                    .exange(adapterIP, adapterPort, protocolType, 1, message,COMM_SOCKET_TYPE_UDP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RESTRetUtils.successObj(responceData);
    }

}
