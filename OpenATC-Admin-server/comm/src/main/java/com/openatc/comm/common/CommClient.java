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

import com.google.gson.Gson;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.model.*;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.comm.common.LogUtil.CreateErrorRequestData;
import static com.openatc.comm.common.LogUtil.CreateErrorResponceData;
import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplOuter.*;


public class CommClient {

    // 服务的类型，分为平台和配置软件
    private static int commServerType = COMM_SERVER_TYPE_CONFIGER;

    private static Logger log = Logger.getLogger(CommClient.class.toString());

    private Gson gson = new Gson();
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

        InnerError devCommError = null;
        String agentid = requestData.getAgentid();
        String infotype = requestData.getInfotype();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
        String protocal = ascsBaseModel.getProtocol();
        int socketType = ascsBaseModel.getSockettype();

        //判断操作类型是否为空
        if (requestData.getOperation() == null) {
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_101, requestData);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断消息类型是否为空
        if (requestData.getInfotype() == null) {
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_102, requestData);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断协议是否为空
        if (protocal == null) {
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_108, requestData );
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
        MessageData responceData = exange(ip, port, protocal, exangeType,requestData,socketType);

        if (responceData == null){
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_200, null);
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }

        if (responceData.getOperation() == null){
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_101, responceData );
            return RESTRetUtils.errorDetialObj(E_4006, devCommError);
        }

        //判断设备是否在线
        if (responceData.getOperation().equals("Communication Error!")) {
            devCommError = RESTRetUtils.innerErrorObj(agentid, E_301, responceData);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError, responceData.getDelay());
        }

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

        responceData.setAgentid(requestData.getAgentid());

        return RESTRetUtils.successObj(responceData);
    }


    // 原始通讯接口，通过设备IP和端口，发送和接收消息
    public MessageData exange(String ip, int port, String protype, int exangeType, MessageData sendMsg, int socketType) {

        // 产品工厂类
        ProtocolFactory factory = null;
        // 协议判断
        if (protype.equals(OCP_PROTYPE)) {
            factory = new ocpFactory();
        } else if (protype.equals(SCP_PROTYPE)) {
            factory = new scpFactory();
        }

        // 创建消息处理对象
        Message message = factory.createMessage();
        // 设置平台与设备之间的通讯方式，分为TCP，配置软件UDP和平台UDP
        // 配置软件UDP使用随机端口，平台UDP使用固定端口
        CommunicationType commType;
        // TCP通讯
        if(socketType == COMM_SOCKET_TYPE_TCP)
            commType = COMM_TCP;
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
        Communication communication = factory.createCommunication(message,commType, exangeType);

        // 发送-打包
        String agentId = sendMsg.getAgentid();
        int sendrev = communication.sendData(agentId,sendMsg, ip, port);
        // 发送异常处理
        if(sendrev == -2){
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentId,E_208,null );;
            return CreateErrorResponceData(agentId,devCommError);
        }
        else if(sendrev == -3){
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentId, E_107, null);
            return CreateErrorRequestData(agentId,devCommError);
        }
        else if(sendrev == -4){
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentId, E_110, null);
            return CreateErrorRequestData(agentId,devCommError);
        }
        else if(sendrev != 0){
            log.warning("Comm Send Data error!" + sendMsg);
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentId,E_204,null );
            return CreateErrorResponceData(agentId,devCommError);
        }

        // 接收-解析
        MessageData responceData = null;
        responceData = communication.receiveData();

        // 接收异常处理
        if(responceData == null){
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentId,E_200,null );;
            responceData = CreateErrorResponceData(agentId,devCommError);
        }

        return responceData;
    }
}
