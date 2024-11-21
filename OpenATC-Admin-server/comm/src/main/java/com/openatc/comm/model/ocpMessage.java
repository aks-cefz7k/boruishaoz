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
import com.openatc.comm.ocp.DataPackUpPack;
import com.openatc.comm.ocp.DataSchedulePackUpPack;

import java.io.UnsupportedEncodingException;

import static com.openatc.comm.common.CommunicationType.EXANGE_TYPE_DEVICE;

public class ocpMessage implements Message {
    private static final int RECVBUFFER = 64 * 1024;
    @Override
    public PackData pack(MessageData sendMsg) throws UnsupportedEncodingException {
        DataSchedulePackUpPack dataSchedulePackUpPack = new DataSchedulePackUpPack();
        PackData packData;
        byte[] m_dataSchedule = dataSchedulePackUpPack.PackDataSchedule(sendMsg);
        if (dataSchedulePackUpPack.isZero(m_dataSchedule)) {
            packData = null;
        } else {
            DataPackUpPack m_dataPackUpPack = new DataPackUpPack();
            byte[] m_packData = new byte[RECVBUFFER];
            int m_packDataSize = m_dataPackUpPack.packBuff(m_dataSchedule, m_packData);
            packData = new PackData(m_packData, m_packDataSize);
        }
        return packData;
    }




    @Override
    public MessageData uppack(byte[] dataSource,int length) throws UnsupportedEncodingException {
        if (dataSource == null) {
            return null;
        }
        MessageData responseData = new MessageData();
        byte[] m_dataSchedule = new byte[RECVBUFFER];
        DataPackUpPack m_readDataPackUpPack = new DataPackUpPack();
        int m_dataScheduleSize = m_readDataPackUpPack.upPackBuff(dataSource, m_dataSchedule);
        if (m_dataScheduleSize != 0) {
            DataSchedulePackUpPack m_readDataReceive = new DataSchedulePackUpPack();
            m_readDataReceive.ReadDataSchedule(responseData, m_dataSchedule, m_dataScheduleSize);
        }
        return responseData;
    }

    @Override
    public int geyExangeType() {
        return EXANGE_TYPE_DEVICE;
    }

}
