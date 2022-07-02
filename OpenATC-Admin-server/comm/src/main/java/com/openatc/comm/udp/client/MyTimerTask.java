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
//package com.openatc.comm.udp.client;
//
//import com.openatc.comm.packupack.CosntDataDefine;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetSocketAddress;
//import java.util.Arrays;
//import java.util.TimerTask;
//import java.util.logging.Logger;
//
///**
// * @author zs
// * @ClassName: MyTimerTask
// * @Description: 定时类，用于定时发送登录指令以维持与信号机直接的通信链接
// * @date 2019年10月19日 下午6:16:06
// */
//public class MyTimerTask extends TimerTask {
//   private DatagramSocket socket;
//   private  String ip;
//   private int port;
//   private static Logger logger = Logger.getLogger(MyTimerTask.class.toString());
//
//
//   public  MyTimerTask(DatagramSocket socket,String ip,int port) {
//      this.ip=ip;
//      this.socket=socket;
//      this.port=port;
//   }
//
//   @Override
//   public void run(){
//      byte[] myDataSchdule=new byte[17];
//      Arrays.fill(myDataSchdule,(byte)0);
//      myDataSchdule[0]= CosntDataDefine.CB_HEADTAIL;
//      myDataSchdule[1]= CosntDataDefine.CB_VERSION_FLAG;
//      myDataSchdule[2]= CosntDataDefine.CB_RECEIVE_FLAG;
//      myDataSchdule[3]= CosntDataDefine.CB_SEND_FLAG;
//      myDataSchdule[4]= CosntDataDefine.DATA_LINK_CONTROL;
//      myDataSchdule[8]= CosntDataDefine.OPERATE_TYPE_SET_REQUEST;
//      myDataSchdule[9]= CosntDataDefine.INFO_TYPE_HEART_BERAT;
//      myDataSchdule[10]= CosntDataDefine.RESERVE_FLAG;
//      myDataSchdule[11]=0x00;
//      myDataSchdule[12]=0x00;
//      myDataSchdule[13]=0x00;
//      myDataSchdule[14]=0x00;
//      myDataSchdule[15]=0x00;
//      myDataSchdule[16]= CosntDataDefine.CB_HEADTAIL;
//      InetSocketAddress address = new InetSocketAddress(ip, port);
//      DatagramPacket sendPacket = new DatagramPacket(myDataSchdule, 17, address);
//      try {
//         socket.send(sendPacket);
////           logger.info("=============send the heart package to " + ip + ":" + port + " successfully!" );
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }
//}
