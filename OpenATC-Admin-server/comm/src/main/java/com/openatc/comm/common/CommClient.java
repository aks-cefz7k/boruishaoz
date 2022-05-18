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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;


@Component
public class CommClient {

    private static CommunicationType commType = COMM_UDP_CONFIGER;

    private Logger log = Logger.getLogger(CommClient.class.toString());

    // 设置通讯模式
    //  UDP - 随机端UDP口通讯
    //  UDP_HOSTPORT - 固定端口UDP通讯
    public void setCommunicationType(CommunicationType type) {
        commType = type;
    }

    public MessageData exange(String ip, int port, String protype, int platform, MessageData sendMsg) throws UnsupportedEncodingException {

        // 产品工厂类
        ProtocolFactory factory = new scpFactory();
        // 协议判断
        if (protype.equals(OCP_PROTYPE)) {
            factory = new ocpFactory();
        } else if (protype.equals(SCP_PROTYPE)) {
            factory = new scpFactory();
        }

        // 创建消息处理对象
        Message message = factory.createMessage();
//        CommunicationProxy communication = factory.createCommunication(commType);

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

        // 创建消息通讯对象
        Communication communication = factory.createCommunication(message,commType, platform);

        // 发送
        DatagramSocket socket = null;
        Long starttime = System.currentTimeMillis();
        try {
            socket = communication.sendData(sendMsg.getAgentid(),packData, ip, port);
        } catch (IOException e) {
            log.info("exange send error: " + e.getMessage());
            return CreateErrorResponceData(e.getMessage());
        }

        // 接收-解析
        MessageData responceData = null;
        Long endtime = 0L;
        try {
            responceData = communication.receiveData(socket);
            endtime = System.currentTimeMillis();
            responceData.setDelay(endtime-starttime);
            log.info("receive responceData: " + responceData);
        } catch (IOException e) {
            log.info("exange receive error: " + e.getMessage());
            return CreateErrorResponceData(e.getMessage());
        }

        // 解析
//        MessageData responceData = message.uppack(datagramPacket);

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
