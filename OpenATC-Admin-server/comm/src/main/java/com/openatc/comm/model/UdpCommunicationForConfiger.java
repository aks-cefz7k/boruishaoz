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
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

// 使用随机端口发送和监听UDP数据，适用于配置工具的直连网络，不含监听主动上报消息功能
public class UdpCommunicationForConfiger implements Communication {
    private static final int TIMEOUT = 3000;
    private static final int RECVBUFFER = 64 * 1024;
    private static Logger logger = Logger.getLogger(UdpCommunicationForConfiger.class.toString());

    private DatagramSocket datagramSocket = null;

    private Message message;

//    static {
//        try {
//            datagramSocket = new DatagramSocket();
//            datagramSocket.setSoTimeout(TIMEOUT);
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//    }

    public UdpCommunicationForConfiger(Message m) {
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setSoTimeout(TIMEOUT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        message = m;
    }


    @Override
    public int sendData(String agentid, MessageData messageData, String ip, int port ) {

        // 打包
        PackData packData = null;
        try {
            packData = message.pack(messageData);
        } catch (UnsupportedEncodingException e) {
            // 打包异常，返回消息打包出错
            logger.warning("Agent ID:" + agentid + " message pack error!" + e.getMessage());
            return -2;
        }
        // packData为空，则返回消息不支持
        if (packData == null) {
            return -3;
        }

        //socket的发送地址和端口
        InetSocketAddress address = new InetSocketAddress(ip, port);
        //生成发送包
        DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

//        logger.info("datagramSocket senddata start: " + System.currentTimeMillis());
        //发送数据
        try {
            datagramSocket.send(sendPacket);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
//        logger.info("datagramSocket senddata end: " + System.currentTimeMillis());
//        logger.info("============= From Local: " + datagramSocket.getLocalPort() + " send to " + ip + ":" + port + " successfully!");
        return 0;
    }

    @Override
    public MessageData receiveData() {
//        long starttime = System.currentTimeMillis();
//        long endtime = 0L;
        MessageData md = null;

        byte[] dataRecv = new byte[RECVBUFFER];
        DatagramPacket recvPacket = new DatagramPacket(dataRecv, dataRecv.length);
        try {
            datagramSocket.receive(recvPacket);
            md = message.uppack(recvPacket.getData(),recvPacket.getLength());
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

//        endtime = System.currentTimeMillis();
//        logger.info("Receive UDP Data Time:"+ (endtime-starttime) );
//        starttime = System.currentTimeMillis();


//        endtime = System.currentTimeMillis();
//        logger.info("Uppack UDP Data time:"+ (endtime-starttime) );

        return md;
    }
}
