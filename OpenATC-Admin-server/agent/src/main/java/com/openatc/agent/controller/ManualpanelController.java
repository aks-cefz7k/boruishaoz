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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.Manualpanel;
import com.openatc.model.service.ManualpanelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManualpanelController {

    ManualpanelService manualpanelService = new ManualpanelService();


    /**
     * @return RESTRetBase
     * @Title: getDefaultConfigByConfig
     * @Description: 根据设备生成默认配置
     */
    @PostMapping(value = "/manualpanel/config")
    public RESTRetBase getDefaultConfigByConfig(@RequestBody JsonObject jsonObject) {
        JsonArray phaseArray = jsonObject.get("phaseList").getAsJsonArray();
        JsonArray channelArray = jsonObject.get("channelList").getAsJsonArray();
        Manualpanel manualpanel = manualpanelService.getManualpanel(phaseArray, channelArray);
        return RESTRetUtils.successObj(manualpanel);
    }

}
