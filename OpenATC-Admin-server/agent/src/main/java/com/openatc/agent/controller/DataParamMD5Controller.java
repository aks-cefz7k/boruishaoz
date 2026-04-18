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

import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.DataParamMD5;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
public class DataParamMD5Controller {
    @PostMapping(value = "/md5")
    public RESTRet postDevsMessage(@RequestBody MessageData messageData) throws UnsupportedEncodingException {
        JsonElement data = messageData.getData();
        DataParamMD5 dataMD5 = new DataParamMD5();
        String datamd5value = null;
        if (data != null) {
            datamd5value = dataMD5.getMD5(data.toString());
        }
        return RESTRetUtils.successObj(datamd5value);
    }
}
