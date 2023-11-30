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

import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.*;
import com.openatc.comm.packupack.CosntDataDefine;
<<<<<<< HEAD

=======
>>>>>>> 4aefc9a9e6f47b0eaeeaeec1b0662a8e39a04f77

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.comm.common.LogUtil.CreateErrorResponceData;


public class CommClient {

    // 服务的类型，分为平台和配置软件
    private static int commServerType = COMM_SERVER_TYPE_CONFIGER;

    private static Logger log = Logger.getLogger(CommClient.class.toString());

    static{
        LogUtil.SetLogLevelfromProp(log);
    }

    // 设置服务通讯模式
    //  UDP - 随机端UDP口通讯，用于配置软件服务
    //  UDP_HOSTPORT - 固定端口UDP通讯，用于平台服务
    public void setCommunicationServerType(int type) {
        commServerType = type;
    }

    public MessageData exange(String ip, int port, String protype, int platform, MessageData sendMsg,int socketType) throws UnsupportedEncodingException {

//        long starttime = System.currentTimeMillis();
//        long endtime = 0L;

        // 产品工厂类
        ProtocolFactory factory = new scpFactory();
        // 协议判断
        if (protype.equals(OCP_PROTYPE)) {
            factory = new ocpFactory();
        } else if (protype.equals(SCP_PROTYPE)) {
            factory = new scpFactory();
        }

        String sendmsgtype = sendMsg.getInfotype();

        // 创建消息处理对象
        Message message = factory.createMessage();

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
//        endtime = System.currentTimeMillis();
//        log.info("Send Msg:" + sendMsg );
//        log.info("Pack Data time:"+ (endtime-starttime) );


//        starttime = System.currentTimeMillis();
        // 设置通讯类型
        CommunicationType commType;
        if(socketType == COMM_SOCKET_TYPE_UDP && commServerType == COMM_SERVER_TYPE_CONFIGER){
            commType = COMM_UDP_CONFIGER;
        }
        else if(socketType == COMM_SOCKET_TYPE_TCP)
            commType = COMM_TCP;
        else
            commType = COMM_UDP_HOSTPORT;

//        commType = COMM_TCP;

        // 创建消息通讯对象
        Communication communication = factory.createCommunication(message,commType, platform);

        // 发送
        int sendrev = 0;
        String agentId = sendMsg.getAgentid();
        try {
            sendrev = communication.sendData(agentId,packData, ip, port,sendmsgtype);
        } catch (IOException e) {
            log.warning("exange send error: " + e.getMessage() + " Message:" + sendMsg);
            return CreateErrorResponceData(agentId,e.getMessage());
        }

        if(sendrev != 0){
            log.warning("exange send error: socket return null!" + " Message:" + sendMsg);
            return CreateErrorResponceData(agentId,"exange send error: socket return null");
        }

//        endtime = System.currentTimeMillis();
//        log.info("Send Data time:"+ (endtime-starttime) );

//        starttime = System.currentTimeMillis();
        // 接收-解析
        MessageData responceData = null;
        try {
            responceData = communication.receiveData();

            // 没有收到消息
            if(responceData == null){
                responceData = CreateErrorResponceData(agentId,"Responce Data is null");
            }

//            log.info("receive responceData: " + responceData);
        } catch (IOException e) {
            log.warning("exange receive error: " + e.getMessage() + " Message:" + sendMsg);
            return CreateErrorResponceData(agentId,e.getMessage());
        }


//        endtime = System.currentTimeMillis();
//        responceData.setDelay(endtime-starttime);
//        log.info("Receive & Unpack Data time:"+ (endtime-starttime) );
//        log.info("Receive Msg:"+ responceData );

        return responceData;
    }

}
