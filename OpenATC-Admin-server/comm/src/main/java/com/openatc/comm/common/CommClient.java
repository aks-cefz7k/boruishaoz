/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.comm.common;

import com.google.gson.JsonObject;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.*;
import com.openatc.comm.packupack.CosntDataDefine;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.COMM_UDP;


//@Slf4j
@Component
public class CommClient {
    private static final String ocpProtype = "ocp";
    private static final String scpProtype = "scp";
    private static CommunicationType commType = COMM_UDP;

    private Logger log = Logger.getLogger(CommClient.class.toString());

    // 设置通讯模式
    //  UDP - 随机端UDP口通讯
    //  UDP_HOSTPORT - 固定端口UDP通讯
    public void setCommunicationType(CommunicationType type) {
        commType = type;
    }

    public MessageData exange(String ip, int port, String protype, MessageData sendMsg) throws UnsupportedEncodingException {

        // 产品工厂类
        ProtocolFactory factory = new scpFactory();
        // 协议判断
        if (protype.equals(ocpProtype)) {
            factory = new ocpFactory();
        } else if (protype.equals(scpProtype)) {
            factory = new scpFactory();
        }

        // 创建消息处理对象
        Message message = factory.createMessage();
        CommunicationProxy communication = factory.createCommunication(commType);

        // 打包
        PackData packData = message.pack(sendMsg);
        // packData为空，则返回数据为空
        if (packData == null) {
            if (sendMsg.getOperation().equals(CosntDataDefine.getrequest)) {
                sendMsg.setOperation(CosntDataDefine.getresponse);
            }
            if (sendMsg.getOperation().equals(CosntDataDefine.setrequest)) {
                sendMsg.setOperation(CosntDataDefine.setresponse);
            }
            return sendMsg;
        }

        // 通讯
        DatagramPacket datagramPacket = null;
        try {
            datagramPacket = communication.exange(packData, ip, port);
        } catch (InterruptedIOException e) {//检测超时
            log.info("exange error: Time out - " + e.getMessage());
            return CreateErrorResponceData(e.getMessage());
        } catch (IOException e) {// 其他错误
            log.info("exange error: Other Error - " + e.getMessage());
            return CreateErrorResponceData(e.getMessage());
        }

        if (datagramPacket == null) {
            log.info("exange error: return packet is null");
            return CreateErrorResponceData("Socker Error, Maybe too busy!");
        }

        // 解析
        MessageData responceData = message.uppack(datagramPacket);
        return responceData;
    }

    private static MessageData CreateErrorResponceData(String desc) {
        MessageData responceData = new MessageData();
        responceData.setOperation("Communication Error!");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("desc", desc);
        responceData.setData(jsonObject);
        return responceData;
    }
}
