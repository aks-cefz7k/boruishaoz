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

import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.packupack.DataParamMD5;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;


@Path("/")
public class DataParamMD5Controller {

    @Path("md5")
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRet setMD5(MessageData messageData) throws UnsupportedEncodingException {
        JsonElement data = messageData.getData();
        String datastr = null;
        DataParamMD5 dataMD5 = new DataParamMD5();
        if (data != null) {
            String datastr1 = data.toString();
            char stchar = '"';
            char stchar1 =' ';
            char stchar2= '\0';
            char stchar3= '\t';
            char stchar4= '\r';
            char stchar5= '\n';
            char stchar6= '\b';
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < datastr1.length(); i++) {
                if (datastr1.charAt(i) != stchar&&datastr1.charAt(i) != stchar1&&datastr1.charAt(i) != stchar2&&datastr1.charAt(i) != stchar3&&datastr1.charAt(i) != stchar4&&datastr1.charAt(i) != stchar5&&datastr1.charAt(i) != stchar6) {
                    stringBuffer.append(datastr1.charAt(i));
                }
            }
            datastr = stringBuffer.toString();
        }
        String datamd5value = dataMD5.getMD5(datastr);
        return RESTRetUtils.successObj(datamd5value);
    }

}