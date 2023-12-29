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

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

// 使用随机端口发送和监听UDP数据，适用于配置工具的直连网络，不含监听主动上报消息功能
public class TcpCommunication implements Communication {
    private static final int TIMEOUT = 3000;
    private static final int RECVBUFFER = 64 * 1024;
    private static Logger logger = Logger.getLogger(TcpCommunication.class.toString());

    private Socket socket = null;
    private OutputStream out = null;
    private Message message = null;;

    public TcpCommunication(Message m) {
        message = m;
    }

    @Override
    public int sendData(String agentid, PackData packData, String ip, int port, String sendmsgtype){

        socket = new Socket();
        int ret = 0;

        try {
            socket.setSoTimeout(TIMEOUT);
            //socket的发送地址和端口
            socket.connect(new InetSocketAddress(ip, port), TIMEOUT);
            //生成发送包
            out = socket.getOutputStream();
            out.write(packData.getM_packData());
            out.flush();
        } catch (IOException e) {
            logger.warning("TCP Send Data Error:" + e.getMessage()+ " AgentID:" + agentid +" IP:" + ip +" Prot:" + port );
            ret = -1;
        }

        // 发送消息失败，注销Socket
        if(ret != 0){
            try {
                socket.close();
            } catch (IOException e) {
                logger.warning("TCP Send Socket Close Error:" + e.getMessage());
            }
        }

        return ret;
    }

    @Override
    public MessageData receiveData(){
        byte[] dataRecv = new byte[RECVBUFFER];
        int readBytes = 0;
        MessageData md = null;

        InputStream in= null;
        try {
            in = socket.getInputStream();
            int read = in.read(dataRecv, readBytes, RECVBUFFER - readBytes);
            readBytes += read;
            // 将剩余的内容读取完
            while (in.available() != 0) {
                read = in.read(dataRecv, readBytes, RECVBUFFER - readBytes);
                readBytes += read;

            }
            socket.close();
            md = message.uppack(dataRecv,readBytes);

        } catch (IOException e) {
            logger.warning("TCP Receive Data Error:" + e.getMessage());
        }
        // 接收消息失败，销毁Socket
        if( ! socket.isClosed() ){
            try {
                socket.close();
            } catch (IOException e) {
                logger.warning("TCP Receive Socket Close Error:" + e.getMessage());
            }
        }

//        logger.info("datagramSocket receivedata start:" + System.currentTimeMillis());
//        logger.info("datagramSocket receivedata end: " + System.currentTimeMillis());
//        logger.info("communication end: " + System.currentTimeMillis());

        return md;
    }
}
