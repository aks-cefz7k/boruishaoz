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
import com.openatc.core.model.InnerError;
import com.openatc.core.util.RESTRetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.EXANGE_TYPE_CENTER;
import static com.openatc.comm.common.CommunicationType.EXANGE_TYPE_DEVICE;
import static com.openatc.comm.common.LogUtil.CreateErrorRequestData;
import static com.openatc.comm.common.LogUtil.CreateErrorResponceData;
import static com.openatc.core.common.IErrorEnumImplInner.E_107;
import static com.openatc.core.common.IErrorEnumImplInner.E_208;

// 使用随机端口发送和监听UDP数据，适用于配置工具的直连网络，不含监听主动上报消息功能
public class TcpCommunication implements Communication {
    private static final int TIMEOUT = 3000;
    private static final int RECVBUFFER = 64 * 1024;
    private static Logger logger = Logger.getLogger(TcpCommunication.class.toString());

    private Socket socket = null;
    private OutputStream out = null;
    private Message message = null;
    private int exangeType; // 当前设备的是直连还是转发
    private String agentid; // 当前请求的设备KEY

    public TcpCommunication(Message m, int type) {
        message = m;
        exangeType = type;
    }

    @Override
    public int sendData(String agentid, MessageData messageData, String ip, int port, String sendmsgtype){

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

        this.agentid = agentid;
        // 判断当前设备的是直连还是转发
        if (exangeType == EXANGE_TYPE_DEVICE)
            ;
        else if (exangeType == EXANGE_TYPE_CENTER)
            ;

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
