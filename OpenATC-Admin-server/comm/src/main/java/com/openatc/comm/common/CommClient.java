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

import com.openatc.comm.data.AscsBaseModel;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.*;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.comm.common.LogUtil.CreateErrorRequestData;
import static com.openatc.comm.common.LogUtil.CreateErrorResponceData;
import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplInner.E_301;
import static com.openatc.core.common.IErrorEnumImplOuter.*;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4002;


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

    // 设备通讯封装接口，根据OpenATC设备结构，发送和接收OpenATC通讯消息
    public RESTRet devMessage(MessageData requestData, AscsBaseModel ascsBaseModel){

        DevCommError devCommError = null;
        String agentid = requestData.getAgentid();
        String infotype = requestData.getInfotype();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
        String protocal = ascsBaseModel.getProtocol();
        int socketType = ascsBaseModel.getSockettype();

        //判断操作类型是否为空
        if (requestData.getOperation() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_REQUEST, infotype, E_101);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断消息类型是否为空
        if (requestData.getInfotype() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_REQUEST, infotype, E_102);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断协议是否为空
        if (protocal == null) {
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_REQUEST, infotype, E_108);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        // 判断通讯类型是设备直连还是平台转发
        int exangeType = EXANGE_TYPE_DEVICE;
        if(protocal.equals(SCP_PROTYPE) )
            exangeType = EXANGE_TYPE_CENTER;

        //增加mode字段
//        if (requestData.getOperation().equals("set-request") && requestData.getInfotype().equals("control/pattern")) {
//            requestData.getData().getAsJsonObject().addProperty("mode", 1);
//        }

        // 获取responceData
        MessageData responceData = null;
        try {
            responceData = exange(ip, port, protocal, exangeType,requestData,socketType);
        } catch (Exception e) {
            log.warning( "message exange error:" + e.getMessage());
        }

        if (responceData == null){
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_REQUEST, infotype, E_200);
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }

        if (responceData == null) {
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }

        if (responceData.getOperation() == null){
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_REQUEST, infotype, E_101);
            return RESTRetUtils.errorDetialObj(E_4006, devCommError);
        }

        //判断设备是否在线
        if (responceData.getOperation().equals("Communication Error!")) {
            devCommError = RESTRetUtils.errorObj(agentid, OPERATOER_TYPE_ERROR_RESPONSE, infotype, E_301);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError, responceData.getDelay());
        }

        //判断请求消息是否正确
        if (responceData.getOperation().equals(OPERATOER_TYPE_ERROR_REQUEST)) {
            return RESTRetUtils.errorDetialObj(E_4001, responceData.getData());
        }

        //判断应答是否成功
        if (responceData.getOperation().equals(OPERATOER_TYPE_ERROR_RESPONSE)) {
            return RESTRetUtils.errorDetialObj(E_4002, responceData.getData());
        }

        //responceData.setThirdpartyid(responceData.getAgentid());
        responceData.setAgentid(requestData.getAgentid());

        return RESTRetUtils.successObj(responceData);
    }


    // 原始通讯接口，通过设备IP和端口，发送和接收消息
    public MessageData exange(String ip, int port, String protype, int platform, MessageData sendMsg, int socketType) throws UnsupportedEncodingException {

//        long starttime = System.currentTimeMillis();
//        long endtime = 0L;
        String agentId = sendMsg.getAgentid();

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
        // packData为空，则返回消息不支持
        if (packData == null) {
            return CreateErrorRequestData(agentId,"sendMsg not support");
        }
//        endtime = System.currentTimeMillis();
//        log.info("Send Msg:" + sendMsg );
//        log.info("Pack Data time:"+ (endtime-starttime) );


//        starttime = System.currentTimeMillis();

        // 设置通讯类型
        CommunicationType commType;

        // TCP通讯
        if(socketType == COMM_SOCKET_TYPE_TCP){
            commType = COMM_TCP;
        }
        // UDP通讯
        else {
            // 配置软件
            if(commServerType == COMM_SERVER_TYPE_CONFIGER)
                commType = COMM_UDP_CONFIGER;
            // 平台
            else{
                commType = COMM_UDP_HOSTPORT;
            }
        }

        // 创建消息通讯对象
        Communication communication = factory.createCommunication(message,commType, platform);

        // 发送
        int sendrev = 0;
//        try {
//            sendrev = communication.sendData(agentId,packData, ip, port,sendmsgtype);
//        } catch (IOException e) {
//            log.warning("exange send error: " + e.getMessage() + " Message:" + sendMsg);
//            return CreateErrorResponceData(agentId,e.getMessage());
//        }

        sendrev = communication.sendData(agentId,packData, ip, port,sendmsgtype);

        if(sendrev != 0){
            log.warning("exange send error!" + sendMsg);
            return CreateErrorResponceData(agentId,"exange send error!");
        }

//        endtime = System.currentTimeMillis();
//        log.info("Send Data time:"+ (endtime-starttime) );

//        starttime = System.currentTimeMillis();
        // 接收-解析
        MessageData responceData = null;
        responceData = communication.receiveData();

        // 没有收到消息
        if(responceData == null){
            responceData = CreateErrorResponceData(agentId,"Responce Data is null");
        }
//        log.info("receive responceData: " + responceData);

//        try {
//            responceData = communication.receiveData();
//
//            // 没有收到消息
//            if(responceData == null){
//                responceData = CreateErrorResponceData(agentId,"Responce Data is null");
//            }
//
//        } catch (IOException e) {
//            log.warning("exange receive error: " + e.getMessage() + " Message:" + sendMsg);
//            return CreateErrorResponceData(agentId,e.getMessage());
//        }


//        endtime = System.currentTimeMillis();
//        responceData.setDelay(endtime-starttime);
//        log.info("Receive & Unpack Data time:"+ (endtime-starttime) );
//        log.info("Receive Msg:"+ responceData );

        return responceData;
    }

}
