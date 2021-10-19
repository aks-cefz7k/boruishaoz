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
package com.openatc.configserver.controller;

import com.google.gson.Gson;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.packupack.CosntDataDefine;
import com.openatc.configserver.model.AscsBaseModel;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplOuter.*;


@Path("/")
public class DeviceController {
    Logger logger = Logger.getLogger(DeviceController.class.getName());

    protected CommClient commClient = new CommClient();

    static AscsBaseModel ascsBaseModel;
    static Gson gson = new Gson();

    @Path("devs")
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRetBase insertDev(AscsBaseModel ascs) {

        logger.info("InsertDev：" + ascs);
        ascsBaseModel = ascs;
        return RESTRetUtils.successObj();
    }

    @Path("devs/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRetBase getDevs(@PathParam("id") int id) {

        return RESTRetUtils.successObj(ascsBaseModel);
    }

    @Path("devs/message")
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRet postDevsMessage(MessageData requestData) {

        DevCommError devCommError = null;
        String agentid = requestData.getAgentid();
        String infotype = requestData.getInfotype();
        String errorquest = CosntDataDefine.errorrequest;
        String errorresponse = CosntDataDefine.erroresponse;
        //判断设备是否为空
        if (ascsBaseModel == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_100);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断操作类型是否为空
        if (requestData.getOperation() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_101);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }
        //判断消息类型是否为空
        if (requestData.getInfotype() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_102);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }
        //判断协议是否为空
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
        String protocol = ascsBaseModel.getProtocol();
        if (protocol == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_108);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //增加mode字段,代表配置工具模式
        if (requestData.getOperation().equals("set-request") && requestData.getInfotype().equals("control/pattern")) {
            requestData.getData().getAsJsonObject().addProperty("mode", 2);
        }

        // 获取responceData
        MessageData responceData = null;
        try {
            responceData = commClient
                    .exange(ip, port, protocol, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (responceData == null) {
            return RESTRetUtils.errorDetialObj(E_4004, devCommError);
        }

        if (responceData.getOperation() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }

        //判断设备是否在线
        if (responceData.getOperation().equals("Communication Error!")) {
            devCommError = RESTRetUtils.errorObj(agentid, errorresponse, infotype, E_301);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError);
        }

        //判断应答是否成功
        if (responceData.getOperation().equals("error-response")) {
            return RESTRetUtils.errorDetialObj(E_4002, responceData.getData());
        }
        return RESTRetUtils.successObj(responceData);
    }

}