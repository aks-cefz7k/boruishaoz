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

import com.openatc.comm.common.LogUtil;
import com.openatc.comm.common.PropertiesUtil;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.ICommHandler;
import com.openatc.comm.ocp.OcpDataEscape;
import com.openatc.comm.ocp.OcpDataPackUpPack;
import com.openatc.core.model.InnerError;
import com.openatc.core.util.RESTRetUtils;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.comm.common.LogUtil.CreateErrorRequestData;
import static com.openatc.comm.common.LogUtil.CreateErrorResponceData;
import static com.openatc.core.common.IErrorEnumImplInner.*;

// 使用固定端口发送和监听UDP数据，适用于平台的端口映射网络
public class UdpCommunicationStaticPort implements Communication {
    // 全局变量
    private static int TIMEOUT = PropertiesUtil.getIntProperty("agent.comm.timeout");
    private static int ocpSocketPort = PropertiesUtil.getIntProperty("agent.comm.port.ocp");
    private static int scpSocketPort = PropertiesUtil.getIntProperty("agent.comm.port.scp");
    private static int RECVBUFFER = 64 * 1024;
    private static Logger logger = Logger.getLogger(UdpCommunicationStaticPort.class.toString());
    // 发送和接收消息的固定端口ocp-UDP对象
    private static DatagramSocket ocpSocket = null;
    // 发送和接收消息的固定端口scp-UDP对象
    private static DatagramSocket scpSocket = null;
    // 先把发送消息的KEY保存在map中，收到消息后，按KEY保存消息内容，再返回给客户端
    private static Map<String, UdpCommunicationStaticPort> messageMap = new HashMap();
//    private static Map<String, ReentrantLock> lockMap = new HashMap();     // 同步锁Map
    public static ICommHandler hanlder;
//    private static ReentrantLock globalLock = new ReentrantLock();

    // 成员变量
    private String agentid; // 当前请求的设备KEY
    private String messageKey; // 当前请求的设备KEY，用于多线程同步
    private Thread thread; // 当前请求的线程对象
    private MessageData responceData = null;
    private DatagramSocket datagramSocket;
    private int exangeType; // 当前设备的通讯平台
    private Message message;

    static {
        //创建socket对象,绑定固定端口
        LogUtil.SetLogLevelfromProp(logger);

        logger.warning("Ocp Socket bind Port :" + ocpSocketPort);
        logger.warning("Scp Socket bind Port :" + scpSocketPort);

        try {
            // OCP接收端口
            ocpSocket = new DatagramSocket(new InetSocketAddress(ocpSocketPort));
            // SCP5接收端口
            scpSocket = new DatagramSocket(new InetSocketAddress(scpSocketPort));
        } catch (SocketException e) {
            logger.warning(e.getMessage());
        }

        //启动OCP接收线程
        int threadcount = PropertiesUtil.getIntProperty("agent.comm.thread.count.ocp");
        logger.warning("Ocp thread count :" + threadcount);
        for (int i = 0; i < threadcount; i++) {
            new UdpReceiveThread(ocpSocket, new ocpMessage()).start();
        }
        //启动SCP接收线程
        threadcount = PropertiesUtil.getIntProperty("agent.comm.thread.count.scp");
        logger.warning("scp thread count :" + threadcount);
        for (int i = 0; i < threadcount; i++) {
            new UdpReceiveThread(scpSocket, new scpMessage()).start();
        }
    }

    // 由于ocp和scp的固定端口号不同，此处根据protype区分发送的socket
    public UdpCommunicationStaticPort(Message m, String protype, int exangeType) {
        message = m;
        // 设置Socket对象
        if (protype.equals(OCP_PROTYPE))
            datagramSocket = ocpSocket;
        else if (protype.equals(SCP_PROTYPE))
            datagramSocket = scpSocket;

        this.exangeType = exangeType;
    }

    @Override
    public int sendData(String agentid, MessageData messageData, String ip, int port) {

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

        // 保存消息的KEY,如果是直连到设备，用IP+端口;如果是通过平台跳转，使用设备ID
        this.agentid = agentid;
        String sendmsgtype = messageData.getOperation() + messageData.getInfotype();
        if (exangeType == EXANGE_TYPE_DEVICE)
            messageKey = ip + port + sendmsgtype;
        else if (exangeType == EXANGE_TYPE_CENTER)
            messageKey = agentid + sendmsgtype;

//        this.sendmsgtype = sendmsgtype;
        thread = Thread.currentThread();

        // 按顺序进行消息通讯
//        globalLock.lock();
//        if (lockMap.containsKey(messageKey)) {
//            lock = lockMap.get(messageKey);
//        } else {
//            lock = new ReentrantLock();
//        }
//        globalLock.unlock();

//        logger.info("Message Lock : Lock id:" + lock.hashCode() + " KEY:" + messageKey);
//        lock.lock();
//        lockMap.put(messageKey, lock);

        try {
            //socket的发送地址和端口
            InetSocketAddress address = new InetSocketAddress(ip, port);
            //生成发送包
            DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

            // UDP最大发送长度64K
            if (sendPacket.getLength() > 64000) {
                logger.warning("Send Packet too Long! Thread#" + thread.getId() + " AgentID:" + agentid + " IP:" + ip + " Port:" + port + " KEY:" + messageKey);
                return -1;
            }

            //发送数据
            datagramSocket.send(sendPacket);
        } catch (Exception e) {
            logger.warning("Send Packet Error! Thread#" + thread.getId() + " AgentID:" + agentid + " IP:" + ip + " Port:" + port + " Msg:" + e.getMessage() + " KEY:" + messageKey);
        }
        messageMap.put(messageKey, this);
//        logger.info("Udp Send Data Thread#" + thread.getId() + " KEY:" + messageKey + " AgentID:" + agentid + " IP:" + ip + " Port:" + port + " Length：" + sendPacket.getLength() + " MsgType：" + sendmsgtype);


        return 0;
    }

    @Override
    public MessageData receiveData() {

        if(responceData == null)
        {
            // 此处等待消息返回
            try {
                Thread.sleep(TIMEOUT);
                InnerError devCommError = RESTRetUtils.innerErrorObj(agentid,E_109,null);
                responceData = CreateErrorRequestData(agentid, devCommError);

                logger.warning("Receive Time Out ! Thread#" + thread.getId() + " KEY:" + messageKey);
            } catch (InterruptedException e) {// 触发中断则说明消息已接收到
                if(responceData == null){
                    logger.warning("Udp Receive Null Response ! Thread#" + thread.getId() + " KEY:" + messageKey);
                }
//                else
//                    logger.info("Udp Receive Response Thread#" + thread.getId() + " KEY:" + messageKey + " Agentid:" + responceData.getAgentid() + " Devid:" + responceData.getThirdpartyid() + " Infotype:" + responceData.getInfotype());
            }
        }


        messageMap.remove(messageKey);
//        lockMap.remove(messageKey);
//        lock.unlock();
//        logger.info("Message unLock : Lock id:" + lock.hashCode() + " KEY:" + messageKey);
        return responceData;
    }


    // 消息接收线程
    private static class UdpReceiveThread extends Thread {

        private DatagramSocket datagramSocket;
        private Message message;

        public UdpReceiveThread(DatagramSocket ds, Message m) {
            datagramSocket = ds;
            message = m;
        }

        @Override
        public void run() {
            // 等待初始化完成
            try {
                Thread.sleep(1000 * 5);
                logger.warning("UdpReceiveThread Started:" + datagramSocket.getLocalPort());
            } catch (InterruptedException e) {
                logger.warning("UdpReceiveThread sleep error: " + e.getMessage());
            }

            // 开始监听UDP端口
            while (datagramSocket != null) {
                byte[] dataRecv = new byte[RECVBUFFER];
                DatagramPacket recvPacket = new DatagramPacket(dataRecv, dataRecv.length);
                try {
                    datagramSocket.receive(recvPacket);
                    InetAddress address = recvPacket.getAddress();
                    String addressStr = address.getHostAddress();
                    int port = recvPacket.getPort();
                    int len = recvPacket.getLength();
                    MessageData responceData = message.uppack(recvPacket.getData(), len);

                    // 收到不正确的消息
                    if (responceData.getOperation() == null) {
                        logger.warning("Udp Receive Incorrect Data: " + addressStr + " : " + port + " Data:" + responceData);
                        continue;
                    }
                    //收到主动上报的消息
                    if (responceData.getOperation().equals(OPERATOER_TYPE_REPORT)) {
//                        logger.info("Udp Receive Report Data: " + addressStr + " : " + port + " Length: " + recvPacket.getLength() + " id:" + responceData.getAgentid() + " - " + responceData.getThirdpartyid() + " Infotype:" + responceData.getInfotype());
                        if (hanlder == null) {
                            logger.warning("Receive report message！But ICommHanlder is null!");
                            continue;
                        }
                        // 收到信号机的注册消息并应答
                        if (responceData.getInfotype().equals("login")) {
                            OcpDataPackUpPack ocpDataPackUpPack = new OcpDataPackUpPack();
                            OcpDataEscape askReadOcpDataEscape = new OcpDataEscape();
                            byte[] askDataSchedule = ocpDataPackUpPack.AskPackDataSchedule(responceData);//打包成数据表
                            byte[] packData = new byte[1024];
                            int askPackDataSize = askReadOcpDataEscape.packBuff(askDataSchedule, packData);
                            packData = Arrays.copyOfRange(packData, 0, askPackDataSize);

                            //生成发送包
                            InetSocketAddress respnseAddress = new InetSocketAddress(addressStr, port);
                            DatagramPacket sendPacket = new DatagramPacket(packData, askPackDataSize, respnseAddress);
                            //发送数据
                            datagramSocket.send(sendPacket);
                        }
                        hanlder.process(responceData);
                    }
                    //收到请求的应答消息
                    else {
//                        logger.info("Udp Receive Response Data: " + addressStr + ":" + port + " Length:" + recvPacket.getLength() + " Agentid:" + responceData.getAgentid() + " Devid:" + responceData.getThirdpartyid() + " Infotype:" + responceData.getInfotype());
                        String operation = responceData.getOperation();
                        String keyoperation = "";
                        // 将应答的操作类型转换为请求的类型，和发送的key对应上
                        if(operation.equals(OPERATOER_TYPE_GET_RESPONSE))
                            keyoperation = OPERATOER_TYPE_GET_REQUEST;
                        else if(operation.equals(OPERATOER_TYPE_SET_RESPONSE))
                            keyoperation = OPERATOER_TYPE_SET_REQUEST;
                        else if(operation.equals(OPERATOER_TYPE_ERROR_RESPONSE))
                            keyoperation = OPERATOER_TYPE_SET_REQUEST;

                        String msgtype = keyoperation + responceData.getInfotype();
                        if (msgtype == null){
                            logger.warning("Udp Receive InfoType is null: " + responceData);
                            continue;
                        }

                        // 根据协议类型，生成消息Key
                        String messageKey = null;
                        int exangeType = message.geyExangeType();
                        // 设备应答的消息
                        if (exangeType == EXANGE_TYPE_DEVICE)
                            messageKey = addressStr + port + msgtype;
                        // 平台应答的消息
                        else if (exangeType == EXANGE_TYPE_CENTER)
                            messageKey = responceData.getAgentid() + msgtype;

                        // 查找应答对应的请求消息Key
                        UdpCommunicationStaticPort comm = messageMap.get(messageKey);

                        if (comm == null) {
//                            InnerError devCommError = RESTRetUtils.innerErrorObj(comm.agentid,E_206,null );
//                            comm.responceData = CreateErrorResponceData(comm.agentid,  devCommError);
                            logger.warning("Can not find UdpCommunication for Receive Msg : Key:" + messageKey);
                            continue;
                        }

                        comm.responceData = responceData;
                        comm.thread.interrupt();
                    }
                } catch (Exception e) {
                    logger.warning("Udp Receive Thread Exception:" + e.getMessage() + "Receive Data:" + recvPacket.getData());
                }
            }
        }
    }
}
