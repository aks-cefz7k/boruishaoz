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
import com.openatc.comm.data.MessageData;

import java.io.UnsupportedEncodingException;

import static com.openatc.comm.common.CommunicationType.EXANGE_TYPE_CENTER;

public class scpMessage implements Message {
//    private static Logger logger = Logger.getLogger(KedacomMessage.class.toString());
    Gson gson = new Gson();


    @Override
    public PackData pack(MessageData sendMsg) throws UnsupportedEncodingException {
        PackData packData = new PackData();

        byte[] dataSend = gson.toJson(sendMsg).getBytes("UTF-8");
        packData.setM_packData(dataSend);
        packData.setM_packDataSize(dataSend.length);
//        logger.info("packing Kedacom MessageData: " + sendMsg);
        return packData;
    }

    @Override
    public MessageData uppack(byte[] dataSource, int length) throws UnsupportedEncodingException {
//        int len = 0;
//        for(int i = 0; i < dataSource.length; i++)
//        {
//            if(dataSource[i] == '\0'){
//                len = i;
//                break;
//            }
//        }
        String str_receive = new String(dataSource, 0, length,"UTF-8");
        MessageData responceData = gson.fromJson(str_receive, MessageData.class);

        //        logger.info("receive Kedacom responceData: " + responceData);
        return responceData;
    }



    @Override
    public int geyExangeType() {
        return EXANGE_TYPE_CENTER;
    }
}
