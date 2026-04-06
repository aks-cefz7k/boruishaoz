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
package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.*;
import com.openatc.model.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.text.ParseException;
import java.util.*;

import static com.openatc.core.common.IErrorEnumImplOuter.*;

/**
 * @author kedacom
 * @ClassName: TemplateController
 * @Description: TODO
 * @date 2019年12月4日 下午16:34:17
 */

@RestController
@CrossOrigin
public class TemplateController {
    @Autowired
    private MessageController messageController;

    private TemplateService templateService = new TemplateService();

    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 查询已有模板
     */
    @GetMapping(value = "/template")
    public RESTRetBase getTemplate() {
        return RESTRetUtils.successObj(templateService.getTemplate());
    }

    /**
     * @return RESTRetBase
     * @Title: addTemplate
     * @Description: 生成模板
     */
    @PostMapping(value = "/template")
    public RESTRetBase createTemplate(@RequestBody JsonObject jsonObject) {
        return RESTRetUtils.successObj(templateService.createTemplate(jsonObject));
    }

    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 根据设备id获取路口类型和相位
     */
    @GetMapping(value = "/intersection/info/{agentid}")
    public RESTRetBase getTemplate(@PathVariable String agentid) throws SocketException, ParseException {

        //使用设备通讯接口获取相位
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.allfeature);
        RESTRet<MessageData> retBase = null;
        retBase = messageController.postDevsMessage(null, messageData);
        if (retBase.getCode().equals(E_4002.getErrorCode())) {
            DevCommError devCommError = RESTRetUtils.errorObj(agentid, CosntDataDefine.errorrequest, CosntDataDefine.allfeature, IErrorEnumImplInner.E_200);
            return RESTRetUtils.errorDetialObj(E_4002, devCommError);
        }
        if (retBase.getCode().equals(E_4003.getErrorCode())) {
            DevCommError devCommError = RESTRetUtils.errorObj(agentid, CosntDataDefine.errorrequest, CosntDataDefine.allfeature, IErrorEnumImplInner.E_301);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError);
        }
        if (retBase.getCode().equals(E_4005.getErrorCode())) {
            DevCommError devCommError = RESTRetUtils.errorObj(agentid, CosntDataDefine.errorrequest, CosntDataDefine.allfeature, IErrorEnumImplInner.E_200);
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }
        if (retBase.getData() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, new DevCommError());
        }
        if (retBase.getData().getData() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, new DevCommError());
        }

        //返回获取路口类型和相位
        JsonObject intersectionInfo = templateService.getIntersectionInfo(retBase.getData().getData().getAsJsonObject());
        return RESTRetUtils.successObj(intersectionInfo);
    }

}
