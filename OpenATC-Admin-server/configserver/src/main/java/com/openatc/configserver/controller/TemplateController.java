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
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.service.TemplateService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.SocketException;
import java.text.ParseException;

import static com.openatc.core.common.IErrorEnumImplOuter.*;

/**
 * @author kedacom
 * @ClassName: TemplateController
 * @Description:
 * @date 2019年12月4日 下午16:34:17
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TemplateController {

    private TemplateService templateService = new TemplateService();

    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 查询已有模板
     */
    DeviceController deviceController = new DeviceController();
    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 查询已有模板
     */
    @Path("template")
    @GET
    public RESTRetBase getTemplate() {

        return RESTRetUtils.successObj(templateService.getTemplate());
    }

    /**
     * @return RESTRetBase
     * @Title: addTemplate
     * @Description: 生成模板
     */
    @Path("template")
    @POST
    public RESTRetBase addTemplate(JsonObject jsonObject) {

        return RESTRetUtils.successObj(templateService.createTemplate(jsonObject));
    }

    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 根据设备id获取路口类型和相位
     */
    @Path("intersection/info/{agentid}")
    @GET
    public RESTRetBase getTemplate(@PathParam("agentid") String agentid) throws SocketException, ParseException {

        //使用设备通讯接口获取相位
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.allfeature);
        RESTRet<MessageData> retBase = null;
        retBase = deviceController.postDevsMessage(messageData);
        if (retBase.getCode().equals(E_4002.getErrorCode())) {
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_200, retBase.getData() );
            return RESTRetUtils.errorDetialObj(E_4002, devCommError);
        }
        if (retBase.getCode().equals(E_4003.getErrorCode())) {
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_301, retBase.getData());
            return RESTRetUtils.errorDetialObj(E_4003, devCommError);
        }
        if (retBase.getCode().equals(E_4005.getErrorCode())) {
            InnerError devCommError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_200, retBase.getData());
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }
        if (retBase.getData() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, new InnerError());
        }
        if (retBase.getData().getData() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, new InnerError());
        }

        //返回获取路口类型和相位
        JsonObject intersectionInfo = templateService.getIntersectionInfo(retBase.getData().getData().getAsJsonObject());
        return RESTRetUtils.successObj(intersectionInfo);
    }
}








