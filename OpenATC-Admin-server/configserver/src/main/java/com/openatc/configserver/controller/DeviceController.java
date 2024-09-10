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

import com.google.gson.JsonObject;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.packupack.CosntDataDefine;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplOuter.*;


@Path("/")
public class DeviceController {

    protected CommClient commClient = new CommClient();
    static AscsBaseModel ascsBaseModel = new AscsBaseModel();

    static {
        ascsBaseModel.setId(0);
        ascsBaseModel.setProtocol("ocp");
        ascsBaseModel.setType("asc");
        JsonObject json = new JsonObject();
        json.addProperty("ip","localhost");
        json.addProperty("port","8880");
        ascsBaseModel.setJsonparam(json);
    }

    public DeviceController() {
    }

    @Path("devs")
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRetBase insertDev(AscsBaseModel ascs) {

        ascsBaseModel = ascs;
        return RESTRetUtils.successObj(ascsBaseModel);
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

        return commClient.devMessage(requestData,ascsBaseModel);
    }

}