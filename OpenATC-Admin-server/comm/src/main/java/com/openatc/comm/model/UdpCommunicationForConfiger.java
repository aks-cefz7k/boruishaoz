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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

// 使用随机端口发送和监听UDP数据，适用于配置工具的直连网络，不含监听主动上报消息功能
public class UdpCommunicationForConfiger implements Communication {
    private static final int TIMEOUT = 3000;
    private static final int RECVBUFFER = 64 * 1024;
//    private static Logger logger = Logger.getLogger(UdpCommunication.class.toString());

    static DatagramSocket datagramSocket = null;

    private Message message;

    static {
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setSoTimeout(TIMEOUT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public UdpCommunicationForConfiger(Message m) {
        message = m;
    }


    @Override
    public int sendData(String agentid, PackData packData, String ip, int port, String sendmsgtype) throws IOException {
//        logger.info("communication start: " + System.currentTimeMillis());

//        if (datagramSocket == null) {
//            //创建socket对象，绑定随机端口
//            datagramSocket = new DatagramSocket();
//            //设置超时
//            datagramSocket.setSoTimeout(TIMEOUT);
//        }

        //socket的发送地址和端口
        InetSocketAddress address = new InetSocketAddress(ip, port);
        //生成发送包
        DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

//        logger.info("datagramSocket senddata start: " + System.currentTimeMillis());
        //发送数据
        datagramSocket.send(sendPacket);
//        logger.info("datagramSocket senddata end: " + System.currentTimeMillis());
//        logger.info("============= From Local: " + datagramSocket.getLocalPort() + " send to " + ip + ":" + port + " successfully!");
        return 0;
    }

    @Override
    public MessageData receiveData() throws IOException {
        byte[] dataRecv = new byte[RECVBUFFER];
        DatagramPacket recvPacket = new DatagramPacket(dataRecv, dataRecv.length);
//        logger.info("datagramSocket receivedata start:" + System.currentTimeMillis());
        datagramSocket.receive(recvPacket);
//        logger.info("datagramSocket receivedata end: " + System.currentTimeMillis());
//        logger.info("communication end: " + System.currentTimeMillis());

        return message.uppack(recvPacket.getData());
    }
}
