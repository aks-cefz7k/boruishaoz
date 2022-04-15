///**
// * Copyright (c) 2020 kedacom
// * OpenATC is licensed under Mulan PSL v2.
// * You can use this software according to the terms and conditions of the Mulan PSL v2.
// * You may obtain a copy of Mulan PSL v2 at:
// * http://license.coscl.org.cn/MulanPSL2
// * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
// * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
// * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
// * See the Mulan PSL v2 for more details.
// **/
//package com.openatc.comm.common;
//
//
//import com.google.gson.Gson;
//import com.openatc.comm.data.MessageData;
//import com.openatc.comm.handler.ICommHandler;
//import com.openatc.comm.packupack.DataPackUpPack;
//import com.openatc.comm.packupack.DataSchedulePackUpPack;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.FixedRecvByteBufAllocator;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.DatagramPacket;
//import io.netty.channel.socket.nio.NioDatagramChannel;
//
//import java.io.UnsupportedEncodingException;
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.logging.Logger;
//
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * 上位机接收设备主动上报的消息，收到后调用外部回调方法，具体处理方式需外部实现
// */
//
///**
// * @author zhangwenchao
// * @ClassName: UdpServer
// * @Description: 上位机接收设备主动上报的消息，收到后调用外部回调方法，具体处理方式需外部实现
// * @date 2019年11月12日 下午6:16:06
// */
//
//@Component
//@Data
//public class UdpServer {
//
//    private static final int MAXPACKET = 65535;
//    private static final int ASKRECVBUFFER = 10 * 1024;
//    private static Logger logger =  Logger.getLogger(UdpServer.class.toString());
//    private ICommHandler hanlder;
//
//    private int udpport = 21003;
//
//    public void run() {
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            Bootstrap b = new Bootstrap();
//            //由于使用UDP通信，在创建Channel的时候需要通过NioDatagramChannel来创建
//            b.group(group).channel(NioDatagramChannel.class)
//                    //随后设置Socket广播参数
//                    .option(ChannelOption.SO_BROADCAST, false)
//                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(MAXPACKET))
//                    .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
//
//                        @Override
//                        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws ParseException, UnsupportedEncodingException {
//                            MessageData responceData = new MessageData();
//                            ByteBuf recvbytebufs = packet.content();
//                            byte[] dataRecv = ByteBufUtil.getBytes(recvbytebufs);
//                            if (dataRecv[0] == -64) {//国标协议
//                                byte[] m_AskdataSchedule = new byte[ASKRECVBUFFER];
//                                DataPackUpPack m_AskReadDataPackUpPack = new DataPackUpPack();
//                                int askDataScheduleSize = m_AskReadDataPackUpPack.upPackBuff(dataRecv, m_AskdataSchedule);
//                                if (askDataScheduleSize != 0) {
//                                    DataSchedulePackUpPack m_readDataReceive = new DataSchedulePackUpPack();
//                                    int readDataScheduleStatus = m_readDataReceive.ReadDataSchedule(responceData, m_AskdataSchedule, askDataScheduleSize);
//                                }
//                                //应答信号机消息
//                                if (dataRecv[9] == 0x01) {
//                                    DataSchedulePackUpPack m_dataSchedulePackUpPack = new DataSchedulePackUpPack();
//                                    byte[] m_askDataSchedule = m_dataSchedulePackUpPack.AskPackDataSchedule(responceData);//打包成数据表
//                                    byte[] m_packData = new byte[ASKRECVBUFFER];
//                                    int m_askPackDataSize = m_AskReadDataPackUpPack.packBuff(m_askDataSchedule, m_packData);
//                                    m_packData = Arrays.copyOfRange(m_packData, 0, m_askPackDataSize);
//                                    DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(m_packData), packet.sender());
//                                    ctx.writeAndFlush(data);//向客户端发送消息
//                                }
//                            }
//                            if (dataRecv[0] != -64) {//kedacom协议
//                                String recvString = new String(dataRecv,"UTF-8");
//                                Gson gson = new Gson();
//                                responceData = gson.fromJson(recvString, MessageData.class);
//                            }
//                            // todo 把接收到的设备消息转换为API所需的对象
//                            // 如果没有设置上报消息处理函数，则不进行处理
//                            logger.info("Receive from Host Address: " + packet.sender().getAddress().getHostAddress());
//                            logger.info("Receive report msg: " + responceData);
//                            if (hanlder != null) {
////                                hanlder.process_fso();
//                                hanlder.process(responceData);
//                            } else {
//                                logger.info("UdpServer hanlder is null!");
//                            }
//                        }
//                    });
////            logger.info("注册详情页面的监听端口: " + udpport);
//            b.bind(udpport).sync().channel().closeFuture().await();
//        } catch (InterruptedException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
//}