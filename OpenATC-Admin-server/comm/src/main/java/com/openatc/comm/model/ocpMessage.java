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

import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.data.MessageDataMD5;
import com.openatc.comm.packupack.DataPackUpPack;
import com.openatc.comm.packupack.DataParamMD5;
import com.openatc.comm.packupack.DataSchedulePackUpPack;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;


public class ocpMessage implements Message {
    private static final String allFeature = "feature/all";
    public static final String setrequest = "set-request";
    private static final int RECVBUFFER = 20 * 1024 * 1024;

    @Override
    public PackData pack(MessageData sendMsg) throws UnsupportedEncodingException {
        DataSchedulePackUpPack m_dataSchedulePackUpPack = new DataSchedulePackUpPack();
        PackData packData = new PackData();
        String infotype = sendMsg.getInfotype();
        String opertype = sendMsg.getOperation();
        if (infotype.equals(allFeature)&&opertype.equals(setrequest)) {
            String datastr = "";
            DataParamMD5 dataMD5 = new DataParamMD5();
            //去除双引号
            JsonElement data = sendMsg.getData();
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
            MessageDataMD5 md5data = new MessageDataMD5(sendMsg);
            md5data.setAgentid(sendMsg.getAgentid());
            md5data.setInfotype(sendMsg.getInfotype());
            md5data.setOperation(sendMsg.getOperation());
            md5data.setData(sendMsg.getData());
            md5data.setMd5(datamd5value);
            byte[] m_dataSchedule = m_dataSchedulePackUpPack.PackDataSchedule(md5data);
            DataPackUpPack m_dataPackUpPack = new DataPackUpPack();
            byte[] m_packData = new byte[RECVBUFFER];
            int m_packDataSize = m_dataPackUpPack.packBuff(m_dataSchedule, m_packData);
            packData = new PackData(m_packData, m_packDataSize);
        } else {
            byte[] m_dataSchedule = m_dataSchedulePackUpPack.PackDataSchedule(sendMsg);
            if (m_dataSchedulePackUpPack.isZero(m_dataSchedule)) {
                return null;
            } else {
                DataPackUpPack m_dataPackUpPack = new DataPackUpPack();
                byte[] m_packData = new byte[RECVBUFFER];
                int m_packDataSize = m_dataPackUpPack.packBuff(m_dataSchedule, m_packData);
                packData.setM_packData(m_packData);
                packData.setM_packDataSize(m_packDataSize);
            }
        }
        return packData;
    }

    @Override
    public MessageData uppack(DatagramPacket recvPacket) throws UnsupportedEncodingException {

        if(recvPacket == null){
            return null;
        }

        MessageData responceData = new MessageData();
        byte[] dataSource = recvPacket.getData();
        byte[] m_dataSchedule = new byte[RECVBUFFER];
        DataPackUpPack m_readDataPackUpPack = new DataPackUpPack();
        int m_dataScheduleSize = m_readDataPackUpPack.upPackBuff(dataSource, m_dataSchedule);
        if (m_dataScheduleSize != 0) {
            DataSchedulePackUpPack m_readDataReceive = new DataSchedulePackUpPack();

            m_readDataReceive.ReadDataSchedule(responceData, m_dataSchedule, m_dataScheduleSize);
        }
        return responceData;
    }
}
