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

import com.openatc.comm.common.CommunicationType;
import com.openatc.comm.data.MessageData;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.OCP_PROTYPE;
import static com.openatc.comm.common.CommunicationType.SCP_PROTYPE;
import static java.util.logging.Level.WARNING;

// 使用固定21002端口发送和监听UDP数据，适应端口映射网络
public class UdpCommunicationStaticPort implements Communication {
    private static final int TIMEOUT = 2000;
    private static final int RECVBUFFER = 20 * 1024 * 1024;
    private static Logger logger = Logger.getLogger(UdpCommunicationStaticPort.class.toString());

    // 发送和接收消息的固定端口ocp-UDP对象
    private static DatagramSocket ocpSocket = null;
    // 发送和接收消息的固定端口scp-UDP对象
    private static DatagramSocket scpSocket = null;
    // 接收ocp消息的线程
    private static UdpReceiveThread ocpReceiveThread = new UdpReceiveThread(ocpSocket, new ocpMessage());
    // 接收scp消息的线程
    private static UdpReceiveThread scpReceiveThread = new UdpReceiveThread(scpSocket, new scpMessage());
    // 先把发送消息的KEY保存在map中，收到消息后，按KEY保存消息内容，再返回给客户端
    private static Map<String,UdpCommunicationStaticPort> messageMap = new HashMap();

    private String messageKey; // 当前请求的设备ID，用于多线程同步
    private Thread thread; // 当前请求的线程对象
//    private DatagramPacket receivePacket; // 当前请求的应答数据
    private MessageData responceData;
    private ReentrantLock lock; // 同步锁，将同一个设备的请求按顺序排队发送
    private DatagramSocket datagramSocket;

    private static int indexTest = 0;

    static {
//        logger.setLevel(WARNING);
        //创建socket对象,绑定固定端口
        try {
            // OCP固定21002端口
            ocpSocket = new DatagramSocket(new InetSocketAddress(21002));
            // SCP固定22002端口
            scpSocket = new DatagramSocket(new InetSocketAddress(22002));
        } catch (SocketException e) {
            logger.info(e.getMessage());
        }
        //启动接收线程
        ocpReceiveThread.start();
        scpReceiveThread.start();
    }

    public UdpCommunicationStaticPort(String protype,Message m) {
        // 设置Socket对象
        if(protype.equals(OCP_PROTYPE))
            datagramSocket = ocpSocket;
        if(protype.equals(SCP_PROTYPE))
            datagramSocket = scpSocket;
    }

    @Override
    public DatagramSocket sendData(String agentid,PackData packData, String ip, int port) throws IOException {

        synchronized(logger) {

            // 保存消息的KEY
//            messageKey = ip + port;
            messageKey = agentid;

//        logger.info("messageMap: " + messageMap.toString());

            // 按顺序进行消息通讯
            if (messageMap.containsKey(messageKey)) {
                lock = messageMap.get(messageKey).lock;
            } else {
                lock = new ReentrantLock();
            }


            lock.lock();
            logger.info("Message Lock :" + messageKey + " Index:" + (++indexTest));


            //socket的发送地址和端口
            InetSocketAddress address = new InetSocketAddress(ip, port);
            //生成发送包
            DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

            //发送数据
            datagramSocket.send(sendPacket);
            thread = Thread.currentThread();
            messageMap.put(messageKey, this);
            logger.info("Send Data Thread#" + thread.getId());
        }


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
        } catch (InterruptedException e) {
            logger.info("Receive Data Thread#" + thread.getId());
        }

        messageMap.remove(messageKey);
        lock.unlock();
        logger.info("Message unLock :" + messageKey + " Index:" + indexTest);
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
                    logger.info("Udp Receive Data:" +responceData.toString());

                    UdpCommunicationStaticPort comm = messageMap.get(responceData.getAgentid());
                    if (comm != null){
                        comm.responceData = responceData;
                        comm.thread.interrupt();
                    }
                    else{
                        logger.info("Comm is null in UdpReceiveThread :" +addressStr+":"+port);
                    }

                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
