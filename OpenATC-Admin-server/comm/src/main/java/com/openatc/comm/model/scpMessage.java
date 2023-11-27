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
package com.openatc.comm.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.data.MessageDataMD5;
import com.openatc.comm.packupack.DataParamMD5;

import java.io.UnsupportedEncodingException;


import static com.openatc.comm.common.CommunicationType.*;

public class scpMessage implements Message {
//    private static Logger logger = Logger.getLogger(KedacomMessage.class.toString());

    @Override
    public PackData pack(MessageData sendMsg) throws UnsupportedEncodingException {
        PackData packData = new PackData();
        Gson gson = new Gson();
        String datastr = "";
        JsonElement data = sendMsg.getData();
        if (data != null) {
            datastr = data.toString();
        }
        DataParamMD5 dataMD5 = new DataParamMD5();
        String datamd5value = dataMD5.getMD5(datastr);
        MessageDataMD5 md5data = new MessageDataMD5(sendMsg);
        md5data.setAgentid(sendMsg.getAgentid());
        md5data.setInfotype(sendMsg.getInfotype());
        md5data.setOperation(sendMsg.getOperation());
        md5data.setData(sendMsg.getData());
        md5data.setMd5(datamd5value);
        byte[] dataSend = gson.toJson(md5data).getBytes("UTF-8");
        packData.setM_packData(dataSend);
        packData.setM_packDataSize(dataSend.length);
//        logger.info("packing Kedacom MessageData: " + sendMsg);
        return packData;
    }

    @Override
    public MessageData uppack(byte[] dataSource) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String str_receive = new String(dataSource, 0, dataSource.length,"UTF-8");
        MessageData responceData = gson.fromJson(str_receive, MessageData.class);
//        logger.info("receive Kedacom responceData: " + responceData);
        return responceData;
    }

    @Override
    public int geyExangeType() {
        return EXANGE_TYPE_CENTER;
    }
}
