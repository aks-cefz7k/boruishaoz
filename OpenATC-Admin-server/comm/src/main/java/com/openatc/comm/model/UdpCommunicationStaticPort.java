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

// 使用固定端口发送和监听UDP数据，适用于平台的端口映射网络
public class UdpCommunicationStaticPort implements Communication {
    private static int TIMEOUT = PropertiesUtil.getIntProperty("agent.comm.timeout");
    private static int RECVBUFFER = 64 * 1024;
    private static Logger logger = Logger.getLogger(UdpCommunicationStaticPort.class.toString());

    // 发送和接收消息的固定端口ocp-UDP对象
    private static DatagramSocket ocpSocket = null;
    // 发送和接收消息的固定端口scp-UDP对象
    private static DatagramSocket scpSocket = null;
    // 先把发送消息的KEY保存在map中，收到消息后，按KEY保存消息内容，再返回给客户端
    private static Map<String,UdpCommunicationStaticPort> messageMap = new HashMap();
    // 同步锁Map
    private static Map<String,ReentrantLock> lockMap = new HashMap();

    private String messageKey; // 当前请求的设备ID，用于多线程同步
    private Thread thread; // 当前请求的线程对象
    private MessageData responceData;
    private ReentrantLock lock; // 同步锁，将同一个设备的请求按顺序排队发送
    private DatagramSocket datagramSocket;
    private int exangeType; // 当前设备的通讯平台
    private String sendmsgtype;

    private static int ocpSocketPort = PropertiesUtil.getIntProperty("agent.comm.port.ocp");
    private static int scpSocketPort = PropertiesUtil.getIntProperty("agent.comm.port.scp");

    public static ICommHandler hanlder;

    private static ReentrantLock globalLock = new ReentrantLock();

    static {
//        logger.setLevel(WARNING);
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
        for(int i = 0;i<threadcount;i++){
            new UdpReceiveThread(ocpSocket, new ocpMessage()).start();
        }
        //启动SCP接收线程
        threadcount = PropertiesUtil.getIntProperty("agent.comm.thread.count.scp");
        logger.warning("scp thread count :" + threadcount);
        for(int i = 0;i<threadcount;i++){
            new UdpReceiveThread(scpSocket, new scpMessage()).start();
        }
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
    public DatagramSocket sendData(String agentid, PackData packData, String ip, int port, String sendmsgtype) throws IOException {

        // 保存消息的KEY,如果是直连到设备，用IP+端口;如果是通过平台跳转，使用设备ID
        if(exangeType == EXANGE_TYPE_DEVICE)
            messageKey = ip + port;
        else if(exangeType == EXANGE_TYPE_CENTER)
            messageKey = agentid;

        this.sendmsgtype = sendmsgtype;

        // 按顺序进行消息通讯
        globalLock.lock();
        if (lockMap.containsKey(messageKey)) {
            lock = lockMap.get(messageKey);
        } else {
            lock = new ReentrantLock();
        }
        globalLock.unlock();


        lock.lock();
        lockMap.put(messageKey,lock);
        logger.warning("Message Lock : KEY:" + messageKey + "Lock id:" + lock.hashCode());

        //socket的发送地址和端口
        InetSocketAddress address = new InetSocketAddress(ip, port);
        //生成发送包
        DatagramPacket sendPacket = new DatagramPacket(packData.getM_packData(), packData.getM_packDataSize(), address);

        // UDP最大发送长度64K
        if(sendPacket.getLength() > 64000){
            logger.warning("Send Packet too Long! Send Data Thread#" + thread.getId() +"AgentID:" + agentid +  "IP:" + ip +"Port:" + port);
            return null;
        }

        //发送数据
        datagramSocket.send(sendPacket);
        thread = Thread.currentThread();
        messageMap.put(messageKey, this);
        logger.warning("Udp Send Data Thread#" + thread.getId() +" AgentID:" + agentid +  " IP:" + ip +" Port:" + port + " Length：" + sendPacket.getLength() + " MsgType：" + sendmsgtype);


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
            logger.warning("Time Out ！ Thread#" + thread.getId());
        } catch (InterruptedException e) {
            logger.warning("Receive Data ！ Thread#" + thread.getId());
        }

        messageMap.remove(messageKey);
        lockMap.remove(messageKey);
        lock.unlock();
        logger.warning("Message unLock : KEY:" + messageKey + "Lock id:" + lock.hashCode());
        return responceData;
    }

    // 消息接收线程
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

                    // 收到不正确的消息
                    if(responceData.getOperation() == null){
                        logger.warning("Udp Receive Incorrect Data: " +addressStr+" : "+port + " Length: " + recvPacket.getLength() + " Data:" + responceData );
                        continue;
                    }
                    //收到主动上报的消息
                    if(responceData.getOperation().equals(OPERATOER_TYPE_REPORT)){
                        logger.info("Udp Receive Report Data: " +addressStr+" : "+port + " Length: " + recvPacket.getLength() + " Data:" + responceData );
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
                            logger.warning("Receive report message！But ICommHanlder is null!");
                        }
                    }
                    //收到请求的应答消息
                    else{
                        logger.warning("Udp Receive Response Data: " +addressStr+" : "+port + " Length: " + recvPacket.getLength() + " Data:" + responceData );

                        String messageKey = null;
                        int exangeType = message.geyExangeType();
                        // 设备应答的消息
                        if(exangeType == EXANGE_TYPE_DEVICE)
                            messageKey = addressStr + port;
                        // 平台应答的消息
                        else if(exangeType == EXANGE_TYPE_CENTER)
                            messageKey = responceData.getAgentid();

                        // 查找应答对应的请求消息Key
                        UdpCommunicationStaticPort comm = messageMap.get(messageKey);
                        String responceInfoType = responceData.getInfotype();
                        if (comm != null){
                            if( responceInfoType == null || comm.sendmsgtype.equals(responceInfoType)){
                                comm.responceData = responceData;
                                comm.thread.interrupt();
                            }
                            else{
                                logger.warning("Udp Receive MsgType error:" + responceInfoType);
                            }

                        }
                        else{
                            logger.warning("Can not find UdpCommunication Fro Receive Msg : Key:" + messageKey);
                        }
                    }
                } catch (Exception e) {
                    logger.warning("Udp Receive Thread Exception:" + e.getMessage());
                }
            }
        }
    }
}
