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
package com.openatc.configserver.utils;

import com.openatc.comm.common.PropertiesUtil;
import com.openatc.comm.ocp.CosntDataDefine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author zs
 * @ClassName: MyTimerTask
 * @Description: 定时类，用于定时发送登录指令以维持与信号机直接的通信链接
 * @date 2019年10月19日 下午6:16:06
 */
public class HeartbeatThread extends Thread {
    private DatagramSocket socket;
    private InetSocketAddress address;
    private static Logger logger = Logger.getLogger(HeartbeatThread.class.toString());

    public HeartbeatThread() {
        String ip = PropertiesUtil.getStringProperty("heartbeat.asc.ip");
        int port = PropertiesUtil.getIntProperty("heartbeat.asc.port");

        try {
            socket = new DatagramSocket();
            address = new InetSocketAddress(ip, port);

        } catch (SocketException e) {
            logger.warning("Heartbeat Init error:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        byte[] myDataSchedule = new byte[17];
        Arrays.fill(myDataSchedule, (byte) 0);
        myDataSchedule[0] = CosntDataDefine.CB_HEADTAIL;
        myDataSchedule[1] = CosntDataDefine.CB_VERSION_FLAG;
        myDataSchedule[2] = CosntDataDefine.CB_RECEIVE_FLAG;
        myDataSchedule[3] = CosntDataDefine.CB_SEND_FLAG;
        myDataSchedule[4] = CosntDataDefine.DATA_LINK_CONTROL;
        myDataSchedule[8] = CosntDataDefine.OPERATE_TYPE_SET_REQUEST;
        myDataSchedule[9] = CosntDataDefine.INFO_TYPE_HEART_BERAT;
        myDataSchedule[10] = CosntDataDefine.RESERVE_FLAG;
        myDataSchedule[11] = 0x00;
        myDataSchedule[12] = 0x00;
        myDataSchedule[13] = 0x00;
        myDataSchedule[14] = 0x00;
        myDataSchedule[15] = 0x00;
        myDataSchedule[16] = CosntDataDefine.CB_HEADTAIL;
        DatagramPacket sendPacket = new DatagramPacket(myDataSchedule, 17, address);

        while (socket != null) {
            try {
                socket.send(sendPacket);
                Thread.sleep(20000);
            } catch (IOException e) {
                logger.warning("Heartbeat socket error:" + e.getMessage());
            } catch (InterruptedException e) {
                logger.warning("Heartbeat thread error:" + e.getMessage());
            }
        }
    }
}
