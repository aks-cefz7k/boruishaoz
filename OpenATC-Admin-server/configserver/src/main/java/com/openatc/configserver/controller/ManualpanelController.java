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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.Manualpanel;
import com.openatc.model.service.ManualpanelService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManualpanelController {

    ManualpanelService manualpanelService = new ManualpanelService();

    /**
     * @return RESTRetBase
     * @Title: getDefaultConfigByConfig
     * @Description: 根据设备生成默认配置
     */
    @Path("manualpanel/config")
    @POST
    public RESTRetBase getDefaultConfigByConfig(JsonObject jsonObject) {
        JsonArray phaseArray = jsonObject.get("phaseList").getAsJsonArray();
        JsonArray channelArray = jsonObject.get("channelList").getAsJsonArray();
        Manualpanel manualpanel = manualpanelService.getManualpanel(phaseArray, channelArray);
        return RESTRetUtils.successObj(manualpanel);
    }

}
