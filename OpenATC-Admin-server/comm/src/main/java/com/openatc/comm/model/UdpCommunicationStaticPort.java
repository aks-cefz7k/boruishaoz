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

import com.google.gson.JsonObject;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.ICommHandler;
import com.openatc.comm.packupack.DataPackUpPack;
import com.openatc.comm.packupack.DataSchedulePackUpPack;


import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;

// 使用固定端口发送和监听UDP数据，适应端口映射网络
public class UdpCommunicationStaticPort implements Communication {
    private static final int TIMEOUT = 10 * 1000;
    private static final int RECVBUFFER = 20 * 1024 * 1024;
    private static Logger logger = Logger.getLogger(UdpCommunicationStaticPort.class.toString());

    // 发送和接收消息的固定端口ocp-UDP对象
    private static DatagramSocket ocpSocket = null;
    // 发送和接收消息的固定端口scp-UDP对象
    private static DatagramSocket scpSocket = null;
    // 接收ocp消息的线程
    private static UdpReceiveThread ocpReceiveThread;
    // 接收scp消息的线程
    private static UdpReceiveThread scpReceiveThread;
    // 先把发送消息的KEY保存在map中，收到消息后，按KEY保存消息内容，再返回给客户端
    private static Map<String,UdpCommunicationStaticPort> messageMap = new HashMap();
    // 同步锁Map
    private static Map<String,ReentrantLock> lockMap = new HashMap();

    private String messageKey; // 当前请求的设备ID，用于多线程同步
    private Thread thread; // 当前请求的线程对象
//    private DatagramPacket receivePacket; // 当前请求的应答数据
    private MessageData responceData;
    private ReentrantLock lock; // 同步锁，将同一个设备的请求按顺序排队发送
    private DatagramSocket datagramSocket;
    private int exangeType; // 当前设备的通讯平台


    private static int ocpSocketPort = 31003;


    private static int scpSocketPort = 31002;

    public static ICommHandler hanlder;

    private static ReentrantLock globalLock = new ReentrantLock();

    static {
//        logger.setLevel(WARNING);
        //创建socket对象,绑定固定端口
        try {
            // OCP接收端口
            ocpSocket = new DatagramSocket(new InetSocketAddress(ocpSocketPort));
            // SCP5接收端口
            scpSocket = new DatagramSocket(new InetSocketAddress(scpSocketPort));
        } catch (SocketException e) {
            logger.info(e.getMessage());
        }
        //启动接收线程
        ocpReceiveThread = new UdpReceiveThread(ocpSocket, new ocpMessage());
        ocpReceiveThread.start();
        scpReceiveThread = new UdpReceiveThread(scpSocket, new scpMessage());
        scpReceiveThread.start();
    }

    public UdpCommunicationStaticPort(String protype, int exangeType) {
        // 设置Socket对象
        if(protype.equals(OCP_PROTYPE))
            datagramSocket = ocpSocket;
        else if(protype.equals(SCP_PROTYPE))
            datagramSocket = scpSocket;

        this.exangeType = exangeType;
    }

    @Override
    public DatagramSocket sendData(String agentid,PackData packData, String ip, int port) throws IOException {

        // 保存消息的KEY,如果是直连到设备，用IP+端口;如果是通过平台跳转，使用设备ID
        if(exangeType == EXANGE_TYPE_DEVICE)
            messageKey = ip + port;
        else if(exangeType == EXANGE_TYPE_CENTER)
            messageKey = agentid;

        // 按顺序进行消息通讯
        globalLock.lock();
        if (lockMap.containsKey(messageKey)) {
            lock = lockMap.get(messageKey);
        } else {
            lock = new ReentrantLock();
            lockMap.put(messageKey,lock);
        }
        globalLock.unlock();


        lock.lock();
        logger.info("Message Lock : KEY:" + messageKey + "Lock id:" + lock.hashCode());

        //socket的发送地址和端口
        InetSocketAddress address = new InetSocketAddress(ip, port);
        //生成发送包
        DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

        // UDP最大发送长度64K
        if(sendPacket.getLength() > 64000){
            logger.info("Send Packet too Long! Send Data Thread#" + thread.getId() +"AgentID:" + agentid +  "IP:" + ip +"Port:" + port);
            return null;
        }

        //发送数据
        datagramSocket.send(sendPacket);
        thread = Thread.currentThread();
        messageMap.put(messageKey, this);
        logger.info("Send Data Thread#" + thread.getId() +"AgentID:" + agentid +  "IP:" + ip +"Port:" + port);


        return datagramSocket;
    }

    @Override
    public MessageData receiveData(DatagramSocket socket) throws IOException {

        if(socket == null){
            return  null;
        }

        // 此处等待消息返回
        try {
            Thread.sleep(TIMEOUT);
            logger.info("Time Out Thread#" + thread.getId());
            lock.unlock();
            return CreateErrorResponceData("Device not online!");
        } catch (InterruptedException e) {
            logger.info("Receive Data Thread#" + thread.getId());
        }

        messageMap.remove(messageKey);
        lockMap.remove(messageKey);
        lock.unlock();
        logger.info("Message unLock : KEY:" + messageKey + "Lock id:" + lock.hashCode());
        return responceData;
    }

    private static MessageData CreateErrorResponceData(String desc) {
        MessageData responceData = new MessageData();
        responceData.setOperation("Communication Error!");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("desc", desc);
        responceData.setData(jsonObject);
        return responceData;
    }


    private static class UdpReceiveThread extends Thread{

        private DatagramSocket datagramSocket;
        private Message message;

        public UdpReceiveThread(DatagramSocket ds, Message m){
            datagramSocket = ds;
            message = m;
        }

        @Override
        public void run() {
            while(datagramSocket != null){
                byte[] dataRecv = new byte[RECVBUFFER];
                DatagramPacket recvPacket = new DatagramPacket(dataRecv, dataRecv.length);
                try {
                    datagramSocket.receive(recvPacket);
                    InetAddress address = recvPacket.getAddress();
                    String addressStr = address.getHostAddress();
                    int port = recvPacket.getPort();
                    MessageData responceData = message.uppack(recvPacket);

                    logger.info("Udp Receive Info:" +addressStr+":"+port);
//                    logger.info("Udp Receive Data:" +responceData.toString());

                    //收到主动上报的消息
                    if(responceData.getOperation() == null || responceData.getOperation().equals(OPERATOER_TYPE_REPORT)){
                        // 收到信号机的注册消息并应答
                        if(responceData.getInfotype().equals("login")){
                            DataSchedulePackUpPack dataSchedulePackUpPack = new DataSchedulePackUpPack();
                            DataPackUpPack askReadDataPackUpPack = new DataPackUpPack();
                            byte[] askDataSchedule = dataSchedulePackUpPack.AskPackDataSchedule(responceData);//打包成数据表
                            byte[] packData = new byte[1024];
                            int askPackDataSize = askReadDataPackUpPack.packBuff(askDataSchedule, packData);
                            packData = Arrays.copyOfRange(packData, 0, askPackDataSize);

                            //生成发送包
                            InetSocketAddress respnseAddress = new InetSocketAddress(addressStr, port);
                            DatagramPacket sendPacket = new DatagramPacket(packData, askPackDataSize, respnseAddress);
                            //发送数据
                            datagramSocket.send(sendPacket);
                        }
                        if (hanlder != null) {
                            hanlder.process(responceData);
                        } else {
                            logger.info("Receive report message！But ICommHanlder is null!");
                        }
                        continue;
                    }


                    //收到请求的应答消息
                    String messageKey = null;
                    int exangeType = message.geyExangeType();
                    if(exangeType == EXANGE_TYPE_DEVICE)
                        messageKey = addressStr + port;
                    else if(exangeType == EXANGE_TYPE_CENTER)
                        messageKey = responceData.getAgentid();

                    UdpCommunicationStaticPort comm = messageMap.get(messageKey);
                    if (comm != null){
                        comm.responceData = responceData;
                        comm.thread.interrupt();
                    }
                    else{
                        logger.info("Communication is null in Udp Receive Thread : Key:" + messageKey);
                    }

                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
