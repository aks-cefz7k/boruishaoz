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
package com.openatc.comm.common;

import com.openatc.comm.model.Communication;
import com.openatc.comm.model.PackData;
import com.openatc.comm.model.UdpCommunication;
import com.openatc.comm.model.UdpCommunicationStaticPort;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.COMM_UDP;
import static com.openatc.comm.common.CommunicationType.COMM_UDP_HOSTPORT;

public class CommunicationProxy {
    private static Logger logger = Logger.getLogger(CommClient.class.toString());
    Communication communication;

    public CommunicationProxy(CommunicationType communicationType) {
        // UDP固定端口
        if (communicationType == COMM_UDP_HOSTPORT) {
            communication = new UdpCommunicationStaticPort();
        }
        // UDP随机端口
        else if (communicationType == COMM_UDP) {
            communication = new UdpCommunication();
        }
    }

    public DatagramPacket exange(PackData packData, String ip, int port) throws IOException {

        DatagramPacket datagramPacket = null;
        DatagramSocket socket = communication.sendData(packData, ip, port);
        datagramPacket = communication.receiveData(socket);
        return datagramPacket;
    }


}
