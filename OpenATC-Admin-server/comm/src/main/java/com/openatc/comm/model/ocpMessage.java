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

import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.OcpDataEscape;
import com.openatc.comm.ocp.OcpDataPackUpPack;

import java.io.UnsupportedEncodingException;

import static com.openatc.comm.common.CommunicationType.EXANGE_TYPE_DEVICE;

public class ocpMessage implements Message {
    private static final int RECVBUFFER = 64 * 1024;
    @Override
    public PackData pack(MessageData sendMsg) throws UnsupportedEncodingException {
        PackData packData;
        // 将OpenATC消息转换为OCP协议
        OcpDataPackUpPack ocpDataPackUpPack = new OcpDataPackUpPack();
        byte[] dataSchedule = ocpDataPackUpPack.PackDataSchedule(sendMsg);
        if (ocpDataPackUpPack.isZero(dataSchedule)) {
            packData = null;
        } else {
            // 进行OCP协议字符转义
            OcpDataEscape ocpDataEscape = new OcpDataEscape();
            byte[] packDataBuff = new byte[RECVBUFFER];
            int packBuffLen = ocpDataEscape.packBuff(dataSchedule, packDataBuff);
            packData = new PackData(packDataBuff, packBuffLen);
        }
        return packData;
    }




    @Override
    public MessageData uppack(byte[] dataSource,int length) throws UnsupportedEncodingException {
        if (dataSource == null) {
            return null;
        }
        MessageData responseData = new MessageData();
        byte[] dataSchedule = new byte[RECVBUFFER];
        OcpDataEscape ocpDataEscape = new OcpDataEscape();
        int upPackBuffLen = ocpDataEscape.upPackBuff(dataSource, dataSchedule);
        if (upPackBuffLen != 0) {
            OcpDataPackUpPack ocpDataPackUpPack = new OcpDataPackUpPack();
            ocpDataPackUpPack.ReadDataSchedule(responseData, dataSchedule, upPackBuffLen);
        }
        return responseData;
    }

    @Override
    public int geyExangeType() {
        return EXANGE_TYPE_DEVICE;
    }

}
