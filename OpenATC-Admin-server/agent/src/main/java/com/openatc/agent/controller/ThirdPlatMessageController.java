package com.openatc.agent.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.InnerError;
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

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.comm.common.CommunicationType.OPERATOER_TYPE_ERROR_RESPONSE;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4001;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4002;


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
        InnerError devCommError;

        MessageData responceData = commClient
                    .exange(adapterIP, adapterPort, protocolType, EXANGE_TYPE_CENTER, message,COMM_SOCKET_TYPE_UDP);

        //判断请求消息是否正确
        if (responceData.getOperation().equals(OPERATOER_TYPE_ERROR_REQUEST)) {
            devCommError = gson.fromJson(responceData.getData(), InnerError.class);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断应答是否成功
        if (responceData.getOperation().equals(OPERATOER_TYPE_ERROR_RESPONSE)) {
            devCommError = gson.fromJson(responceData.getData(), InnerError.class);
            return RESTRetUtils.errorDetialObj(E_4002, devCommError);
        }

        return RESTRetUtils.successObj(responceData);
    }

}
