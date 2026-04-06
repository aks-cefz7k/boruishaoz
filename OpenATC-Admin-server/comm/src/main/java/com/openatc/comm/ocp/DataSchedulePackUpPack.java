package com.openatc.comm.ocp;
/**
 * @ClassName: DataSchedulePackUpPack
 * @Description: TODO
 * @author liangting
 * @date 2019年10月30日 上午10:16:06
 */


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.data.MessageDataMD5;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.openatc.comm.ocp.CosntDataDefine.*;

public class DataSchedulePackUpPack { //数据表内容宏定义


    private static final int MAX_DATA_SCHEDULE = 14;         //数据表大小
    private static final int ReadDataScheduleSuccess = 1;
    private static final int ReadDataScheduleFault = 3;
    private static final int SCHEDULEBUFFER = 10 * 1024;

    byte[] dataSchdule = new byte[MAX_DATA_SCHEDULE];

    Gson gson = new Gson();

    public void DataSchedulePackUpPack() {

    }

    //操作类型字符串
    public String StringOperatorType(byte operatorNumber) {
        if (operatorNumber == -128) {
            return getrequest;
        }
        if (operatorNumber == -125) {
            return getresponse;
        }
        if (operatorNumber == -127) {
            return setrequest;
        }
        if (operatorNumber == -124) {
            return setresponse;
        }
        if (operatorNumber == -126) {
            return report;
        }
        if (operatorNumber == -123) {
            return erroresponse;
        }
        return null;
    }

    public String StringOperatorObj(byte operatorObj) {
        if (operatorObj == 0x01 || operatorObj == 0x51) {
            return login;
        }
        if (operatorObj == 0x02) {
            return volume;
        }
        if (operatorObj == 0x03) {
            return workstatus;
        }
        if (operatorObj == 0x04) {
            return lampcolor;
        }
        if (operatorObj == 0x05) {
            return time;
        }
        if (operatorObj == 0x06) {
            return signalgroup;
        }
        if (operatorObj == 0x07) {
            return phase;
        }
        if (operatorObj == 0x08) {
            return timepattern;
        }
        if (operatorObj == 0x09) {
            return scheduleplan;
        }
        if (operatorObj == 0x0A) {
            return workmode;
        }
        if (operatorObj == 0x0B) {
            return falut;
        }
        if (operatorObj == 0x0C) {
            return atcversion;
        }
        if (operatorObj == 0x0D) {
            return paramversion;
        }
        if (operatorObj == 0x0E) {
            return code;
        }
        if (operatorObj == 0x0F) {
            return remote;
        }
        if (operatorObj == 0x10) {
            return detector;
        }
        if (operatorObj == 0x24 || operatorObj == 0x23 || operatorObj == 0x18) {
            return allfeature;
        }
        if (operatorObj == -94) {
            return paramip;
        }
        if (operatorObj == -93) {
            return serialport;
        }
        if (operatorObj == -92) {
            return manualpanel;
        }
        if (operatorObj == -91) {
            return updatedisk;
        }
        if (operatorObj == -90) {
            return systemremote;
        }
        if (operatorObj == -89) {
            return systemlog;
        }
        if (operatorObj == -88) {
            return channelcheck;
        }
        if (operatorObj == -87) {
            return volumelog;
        }
        if (operatorObj == -86) {
            return interrupt;
        }
        if (operatorObj == -85) {
            return channelstatus;
        }
        if (operatorObj == -84) {
            return channellampstatus;
        }
        if (operatorObj == -83) {
            return systemcustom;
        }
        return null;
    }


    /**
     * @return
     * @throws
     * @author laoxia
     * description 设置第八个字节位数据
     * create time 2021/8/23 13:51
     */
    private byte dataScheduleEight(String operationType, String infoType) {
        byte eight = (byte) 0x00;
        switch (operationType) {
            case getrequest:
                switch (infoType) {
                    case login: //上位机发login请求
                        eight = INFO_TYPE_LOGIN;
                        //数据内容:上位机联机请求
                        break;
                    case volume: //上位机查询交通流请求
                        eight = INFO_TYPE_TRAFFIC_FLOW;
                        //数据内容:上位机查询交通流
                        break;
                    case workstatus://工作状态查询
                        eight = INFO_TYPE_WORK_STATE;
                        break;
                    case lampcolor://灯色状态查询
                        eight = INFO_TYPE_LAMP_COLOR;
                        break;
                    case time://查询信号机时间
                        eight = INFO_TYPE_CURRENT_TIME;
                        break;
                    case signalgroup://查询信号灯组信息
                        eight = INFO_TYPE_SIGNAL_GROUP;
                        break;
                    case phase://查询相位信息
                        eight = INFO_TYPE_PHASE;
                        break;
                    case timepattern://查询信号配时方案
                        eight = INFO_TYPE_SCHEME_PARTTERN;
                        break;
                    case scheduleplan://查询方案调度计划
                        eight = INFO_TYPE_SCHEDUL_PLAN;
                        break;
                    case workmode://查询工作方式
                        eight = INFO_TYPE_WORK_MODE;
                        break;
                    case falut://查询信号机故障
                        eight = INFO_TYPE_SIGNAL_ERROR;
                        break;
                    case atcversion://查询信号机版本
                        eight = INFO_TYPE_ATC_VERSION;
                        break;
                    case paramversion://查询特征参数版本
                        eight = INFO_TYPE_PARAM_VERSION;
                        break;
                    case code://信号机识别码
                        eight = INFO_TYPE_IDENTIFY_CODE;
                        break;
                    case detector://检测器
                        eight = INFO_TYPE_DETECTOR;
                        break;
                    case allfeature://查询整体参数feature/all
                        eight = CFG_ASK_ASKREAD;
                        break;
                    case manualpanel:// 查询手动面板
                        eight = INFO_TYPE_MANUALPANEL;
                        break;
                    case systemremote:// 查询远程调试
                        eight = INFO_TYPE_SYSTEM_REMOTE;
                        break;
                    case systemlog:// 查询操作日志
                        eight = INFO_TYPE_SYSTEM_LOG;
                        break;
                    case channelstatus:// 查询通道状态（电压电流）
                        eight = INFO_TYPE_CHANNEL_STATUS;
                        break;
                    case channellampstatus:// 查询通道灯色状态
                        eight = INFO_TYPE_CHANNEL_LAMP_STATUS;
                        break;
                    case systemcustom:// 查询设备参数
                        eight = INFO_TYPE_SYSTEM_CUSTOM;
                        break;
                    default:
                }
                break;
            case setrequest:
                switch (infoType) {
                    case time://设置时间
                        eight = INFO_TYPE_CURRENT_TIME;
                        break;
                    case signalgroup://设置信号灯组
                        eight = INFO_TYPE_SIGNAL_GROUP;
                        break;
                    case phase://相位设置
                        eight = INFO_TYPE_PHASE;
                        break;
                    case timepattern://配时方案设置
                        eight = INFO_TYPE_SCHEME_PARTTERN;
                        break;
                    case scheduleplan://方案调度计划
                        eight = INFO_TYPE_SCHEDUL_PLAN;
                        break;
                    case workmode://工作方式设置
                        eight = INFO_TYPE_WORK_MODE;
                        break;
                    case paramversion://特征参数版本设置
                        eight = INFO_TYPE_PARAM_VERSION;
                        break;
                    case remote://远程控制设置
                        eight = INFO_TYPE_REMOTE_CONTROL;
                        break;
                    case detector://检测器设置
                        eight = INFO_TYPE_DETECTOR;
                        break;
                    case allfeature://下载整体参数
                        eight = CFG_ASK_SENDDATA;
                        break;
                    case manualpanel://手动面板设置
                        eight = INFO_TYPE_MANUALPANEL;
                        break;
                    case updatedisk://U盘更新
                        eight = INFO_TYPE_UPDATEUDISK;
                        break;
                    case systemremote://远程调试
                        eight = INFO_TYPE_SYSTEM_REMOTE;
                        break;
                    case channelcheck://通道检测
                        eight = INFO_TYPE_SYSTEM_CHANNEL_CHECK;
                        break;
                    case volumelog://流量日志获取
                        eight = INFO_TYPE_VOLUMELOG;
                        break;
                    case interrupt://方案干预
                        eight = INFO_TYPE_PATTERN_INTERRUPT;
                        break;
                    case systemcustom://下载设备参数
                        eight = INFO_TYPE_SYSTEM_CUSTOM;
                        break;
                    case systemupdate://设备更新
                        eight = INFO_TYPE_SYSTEM_UPDATE;
                        break;
                    default:
                }
                break;
            case getresponse://设置应答
                eight = INFO_TYPE_ONLINE;
                break;
            case setresponse://查询应答
                eight = INFO_TYPE_ONLINE;
                break;
            default:
        }
        return eight;
    }

    /**
     * @return
     * @throws
     * @author laoxia
     * description 设置第七个字节位数据
     * create time 2021/8/23 13:51
     */
    private byte dataScheduleSeven(String operationType, String infoType) {
        byte seven = (byte) 0x00;
        switch (operationType) {
            case getrequest:
                switch (infoType) {
                    case login:
                        seven = OPERATE_TYPE_SET_REQUEST;
                        break;
                    default:
                        seven = OPERATE_TYPE_QUERY_REQUEST;
                }
                break;
            case setrequest:
                seven = OPERATE_TYPE_SET_REQUEST;
                break;
            case getresponse:
                seven = OPERATE_TYPE_SET_ANSWER;
                break;
            case setresponse:
                seven = OPERATE_TYPE_SET_REQUEST;
                break;
            default:
        }
        return seven;
    }

    /**
     * @return
     * @throws
     * @author laoxia
     * description 设置第三个字节位数据
     * create time 2021/8/23 13:51
     */
    private byte dataScheduleThree(String operationType, String infoType) {
        byte three = (byte) 0x00;
        switch (operationType) {
            case getrequest:
                switch (infoType) {
                    case login: //上位机发login请求
                        three = DATA_LINK_CONTROL;
                        //数据内容:上位机联机请求
                        break;
                    case volume: //上位机查询交通流请求
                        three = DATA_LINK_CONTROL;//链路5
                        //数据内容:上位机查询交通流
                        break;
                    case workstatus://工作状态查询
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case lampcolor://灯色状态查询
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case time://查询信号机时间
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case signalgroup://查询信号灯组信息
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case phase://查询相位信息
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case timepattern://查询信号配时方案
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case scheduleplan://查询方案调度计划
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case workmode://查询工作方式
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case falut://查询信号机故障
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case atcversion://查询信号机版本
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case paramversion://查询特征参数版本
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case code://信号机识别码
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case detector://检测器
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case allfeature://查询整体参数feature/all
                        three = DATA_LINK_CONFIG;//配置软件和信号机的参数命令
                        break;
                    case manualpanel:// 查询手动面板
                        three = DATA_LINK_CONTROL;
                        break;
                    case systemremote:// 查询远程调试
                        three = DATA_LINK_CONTROL;
                        break;
                    case systemlog:// 查询操作日志
                        three = DATA_LINK_CONTROL;
                        break;
                    case channelstatus:// 查询通道状态（电压电流）
                        three = DATA_LINK_CONTROL;
                        break;
                    case channellampstatus:// 查询通道灯色状态
                        three = DATA_LINK_CONTROL;
                        break;
                    case systemcustom:// 查询设备参数
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    default:
                }
                break;
            case setrequest:
                switch (infoType) {
                    case time://设置时间
                        three = DATA_LINK_BASIC_INFO;
                        break;
                    case signalgroup://设置信号灯组
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case phase://相位设置
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case timepattern://配时方案设置
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case scheduleplan://方案调度计划
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case workmode://工作方式设置
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case paramversion://特征参数版本设置
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case remote://远程控制设置
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case detector://检测器设置
                        three = DATA_LINK_INTER_ORDER;
                        break;
                    case allfeature://下载整体参数
                        three = DATA_LINK_CONFIG;
                        break;
                    case manualpanel://手动面板设置
                        three = DATA_LINK_CONTROL;
                        break;
                    case updatedisk://U盘更新
                        three = DATA_LINK_CONTROL;
                        break;
                    case systemremote://远程调试
                        three = DATA_LINK_CONTROL;
                        break;
                    case channelcheck://通道检测
                        three = DATA_LINK_CONTROL;
                        break;
                    case volumelog://流量日志获取
                        three = DATA_LINK_CONTROL;
                        break;
                    case interrupt://方案干预
                        three = DATA_LINK_CONTROL;
                        break;
                    case systemcustom://下载设备参数
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    case systemupdate://设备更新
                        three = DATA_LINK_PARAM_TRAMFER;
                        break;
                    default:
                }
                break;
            case getresponse://设置应答
                three = DATA_LINK_COMMU_SPE;
                break;
            case setresponse://查询应答
                three = DATA_LINK_COMMU_SPE;
                break;
            default:
        }
        return three;
    }


    private void operationType(MessageData sendData) throws UnsupportedEncodingException {
        String operationType = sendData.getOperation();
        String infoType = sendData.getInfotype();
        switch (operationType) {
            case getrequest:
                AddMsgToData(sendData);
                break;
            case setrequest://设置请求
                switch (infoType) {
                    case time://设置时间
                        //数据内容:据格林尼治时间
                        AddMsgToData(sendData);
                        break;
                    case signalgroup://设置信号灯组
                        AddMsgToData(sendData);
                        break;
                    case phase://相位设置
                        AddMsgToData(sendData);
                        break;
                    case timepattern://配时方案设置
                        AddMsgToData(sendData);
                        break;
                    case scheduleplan://方案调度计划
                        AddMsgToData(sendData);
                        break;
                    case workmode://工作方式设置
                        AddMsgToData(sendData);
                        break;
                    case paramversion://特征参数版本设置
                        AddMsgToData(sendData);
                        break;
                    case remote://远程控制设置
                        AddMsgToData(sendData);
                        break;
                    case detector://检测器设置
                        AddMsgToData(sendData);
                        break;
                    case allfeature://下载整体参数
                        //数据内容
                        byte[] dataSizeFlag = new byte[4];
                        int dataSendCount = 0;
                        String dataSetAllParam = gson.toJson(sendData);
                        MessageData messageData = gson.fromJson(dataSetAllParam, MessageData.class);
                        String datastr = null;
                        JsonElement data = messageData.getData();
                        if (data != null) {
                            String datastr1 = data.toString();
                            char stchar = '"';
                            char stchar1 = ' ';
                            char stchar2 = '\0';
                            char stchar3 = '\t';
                            char stchar4 = '\r';
                            char stchar5 = '\n';
                            char stchar6 = '\b';
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < datastr1.length(); i++) {
                                if (datastr1.charAt(i) != stchar && datastr1.charAt(i) != stchar1 && datastr1.charAt(i) != stchar2 && datastr1.charAt(i) != stchar3 && datastr1.charAt(i) != stchar4 && datastr1.charAt(i) != stchar5 && datastr1.charAt(i) != stchar6) {
                                    stringBuffer.append(datastr1.charAt(i));
                                }
                            }
                            datastr = stringBuffer.toString();
                        }
                        DataParamMD5 dataMD5 = new DataParamMD5();
                        String datamd5value = dataMD5.getMD5(datastr);
                        MessageDataMD5 md5data = new MessageDataMD5(messageData);
                        md5data.setMd5(datamd5value);
                        dataSetAllParam = gson.toJson(md5data);
                        if (dataSetAllParam != null) {
                            byte[] dataSend = dataSetAllParam.getBytes("UTF-8");
                            dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        for (int i = 0; i < 4; i++) {
                            dataSizeFlag[i] = (byte) ((dataSendCount >> (i * 8)) & 0xFF);
                            dataSchdule[i + 10] = dataSizeFlag[i];
                        }
                        break;
                    case manualpanel://手动面板设置
                        AddMsgToData(sendData);
                        break;
                    case updatedisk://U盘更新
                        AddMsgToData(sendData);
                        break;
                    case systemremote://远程调试
                        AddMsgToData(sendData);
                        break;
                    case channelcheck://通道检测
                        AddMsgToData(sendData);
                        break;
                    case volumelog://流量日志获取
                        AddMsgToData(sendData);
                        break;
                    case interrupt://方案干预
                        AddMsgToData(sendData);
                        break;
                    case systemcustom://下载设备参数
                        String dataSetSystemCustomParam = null;
                        dataSizeFlag = new byte[4];
                        dataSendCount = 0;
                        JsonElement strSetSystemCustomParam = sendData.getData();
                        if (strSetSystemCustomParam != null) {
                            dataSetSystemCustomParam = strSetSystemCustomParam.toString();
                            if (dataSetSystemCustomParam != null) {
                                byte[] dataSend = dataSetSystemCustomParam.getBytes("UTF-8");
                                dataSendCount = dataSend.length;
                                dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                                System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                            }
                            for (int i = 0; i < 4; i++) {
                                dataSizeFlag[i] = (byte) ((dataSendCount >> (i * 8)) & 0xFF);
                                dataSchdule[i + 10] = dataSizeFlag[i];
                            }
                        }
                        break;
                    case systemupdate://设备更新
                        //数据内容
                        String systemupdateStr = null;
                        dataSizeFlag = new byte[4];
                        dataSendCount = 0;
                        JsonElement systemupdateJson = sendData.getData();
                        if (systemupdateJson != null) {
                            systemupdateStr = systemupdateJson.toString();
                            if (systemupdateStr != null) {
                                byte[] dataSend = systemupdateStr.getBytes("UTF-8");
                                dataSendCount = dataSend.length;
                                dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                                System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                            }
                            for (int i = 0; i < 4; i++) {
                                dataSizeFlag[i] = (byte) ((dataSendCount >> (i * 8)) & 0xFF);
                                dataSchdule[i + 10] = dataSizeFlag[i];
                            }
                        }
                        break;
                    default:
                }
                break;
            case getresponse://设置应答
                if (infoType.equals(login)) {
                    //无数据统一留一个字节
                    byte[] nodata14 = new byte[1];
                    nodata14[0] = 0x00;
                    dataSchdule = Arrays.copyOf(dataSchdule, 15);
                    System.arraycopy(nodata14, 0, dataSchdule, 14, 1);
                }
                break;
            case setresponse://查询应答
                if (infoType.equals(login)) {
                    //无数据统一留一个字节
                    byte[] nodata15 = new byte[1];
                    nodata15[0] = 0x00;
                    dataSchdule = Arrays.copyOf(dataSchdule, 15);
                    System.arraycopy(nodata15, 0, dataSchdule, 14, 1);
                }
                break;
            default:
        }
    }

    /**
     * 重复代码提取
     * 将数据填充到dataSchdule
     *
     * @param sendData
     */
    public void AddMsgToData(MessageData sendData) throws UnsupportedEncodingException {
        String data;
        JsonElement dataLoginObject = sendData.getData();
        if (dataLoginObject != null) {
            data = dataLoginObject.toString();
            byte[] dataSend = data.getBytes("UTF-8");
            int dataSendCount = dataSend.length;
            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
        }
    }

    /**
     * @param sendData 发给信号机的数据
     * @return byte[] 转换为数据表的结果
     * @Title: PackDataSchedule
     * @Description: 根据国标对发送数据进行协议转化（转换为数据表的结构）
     */
    public byte[] PackDataSchedule(MessageData sendData) throws UnsupportedEncodingException {
        String operationType = sendData.getOperation();
        String infoType = sendData.getInfotype();
        Arrays.fill(dataSchdule, (byte) 0);
        dataSchdule[0] = CB_VERSION_FLAG;
        dataSchdule[1] = CB_RECEIVE_FLAG;
        dataSchdule[2] = CB_SEND_FLAG;
        dataSchdule[3] = dataScheduleThree(operationType, infoType);
        dataSchdule[7] = dataScheduleSeven(operationType, infoType);
        dataSchdule[8] = dataScheduleEight(operationType, infoType);
        dataSchdule[9] = RESERVE_FLAG;
        dataSchdule[10] = 0x00;
        dataSchdule[11] = 0x00;
        dataSchdule[12] = 0x00;
        dataSchdule[13] = 0x00;
        operationType(sendData);
        return dataSchdule.clone();
    }

    //应答信号的数据表
    public byte[] AskPackDataSchedule(MessageData sendData) {
        String operationtype = sendData.getOperation();
        String infotype = sendData.getInfotype();
        dataSchdule[0] = CB_VERSION_FLAG;
        dataSchdule[1] = CB_RECEIVE_FLAG;
        dataSchdule[2] = CB_SEND_FLAG;
        dataSchdule[9] = RESERVE_FLAG;
        dataSchdule[10] = 0x00;
        dataSchdule[11] = 0x00;
        dataSchdule[12] = 0x00;
        dataSchdule[13] = 0x00;
        switch (operationtype) {
            case setrequest://设置应答
                if (infotype.equals(login)) {
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_ANSWER;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
                }
                break;
            case report:
                //查询应答
                if (infotype.equals(login)) {
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_ANSWER;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
//                    //无数据统一留一个字节
//                    byte[] nodata15=new byte[1];
//                    nodata15[0]=0x00;
//                    dataSchdule=Arrays.copyOf(dataSchdule,15);
//                    System.arraycopy(nodata15,0,dataSchdule,14,1);
                }
                break;
            default:

        }
        return dataSchdule.clone();//待思考
    }

    /**
     * @param recvData 还上位机参数
     * @param tempData
     */
    public void SetDataTorecvData(MessageData recvData, String tempData ) {
        if (tempData != null) {
            JsonElement obj = gson.fromJson(tempData, JsonElement.class);
            recvData.setData(obj);
        }
        if (tempData == null) {
            tempData = "{\n" +
                    "\t\"sucess\": 1\n" +
                    "}";
            JsonElement obj = gson.fromJson(tempData, JsonElement.class);
            recvData.setData(obj);
        }
    }


    /**
     * @return 1->成功  3->失败
     * @Description 数据检查
     * @Date 2021/8/23 15:27
     */
    private int checkDataLink(byte chDataLink, byte chOperateType, byte chInfoType, String tempData, MessageData recvData) {
        if (chDataLink == DATA_LINK_CONTROL)//配置软件和信号的命令链路
        {
            //信号机应答联机kedacom
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_LOGIN_REQUEST)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答操作日志
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_LOG)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答通道状态(电压、电流)
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_CHANNEL_STATUS)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答通道灯色
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_CHANNEL_LAMP_STATUS)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答通道可检测
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CHANNEL_CHECK)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答方案干预
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PATTERN_INTERRUPT)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答流量日志获取环境状态
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_VOLUMELOG)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答当前交通流量查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_TRAFFIC_FLOW)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答手动面板参数设置
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_MANUALPANEL)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答手动面板参数设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_MANUALPANEL)) {
                return ReadDataScheduleSuccess;
            }
            //配工具应答U盘更新
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_UPDATEUDISK)) {
                return ReadDataScheduleSuccess;
            }

            //信号机应答远程调试设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_REMOTE)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答远程调试查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_REMOTE)) {
                return ReadDataScheduleSuccess;
            }
        }

        if (chDataLink == DATA_LINK_CONFIG)//配置软件和信号机的参数链路
        {
            //信号机应答整体参数查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == CFG_ACK_ASKSEND)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答整体参数设置成功
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == CFG_ACK_SENDDATA_OK)) {
                return ReadDataScheduleSuccess;
            }
            //信号机应答整体参数设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == CFG_ACK_SENDDATA_FAILED)) {
                recvData.setOperation(StringOperatorType(OPERATE_TYPE_ERROR_ANSWER));
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_COMMU_SPE) {
            //联机请求
            if ((chOperateType == OPERATE_TYPE_SET_REQUEST) && (chInfoType == INFO_TYPE_ONLINE)) {
                recvData.setOperation(StringOperatorType(OPERATE_TYPE_REPORT));
                return ReadDataScheduleSuccess;
            }
            //联机查询
            if ((chOperateType == OPERATE_TYPE_QUERY_REQUEST) && (chInfoType == INFO_TYPE_ONLINE)) {
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_BASIC_INFO) {///////链路2 主动上传
            //交通流信息主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_TRAFFIC_FLOW)) {
                if (tempData != null) {
                    JsonElement obj = gson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机工作状态主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_WORK_STATE)) {
                if (tempData != null) {
                    JsonElement obj = gson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //灯色状态主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_LAMP_COLOR)) {
                if (tempData != null) {
                    JsonElement obj = gson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机工作状态查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_WORK_STATE)) {
                return ReadDataScheduleSuccess;
            }
            //灯色状态查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_LAMP_COLOR)) {
                return ReadDataScheduleSuccess;
            }
            //时间查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == 0x05)) {
                return ReadDataScheduleSuccess;
            }
            //时间设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == 0x05)) {
                return ReadDataScheduleSuccess;
            }
            //信号机故障查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_ERROR)) {
                return ReadDataScheduleSuccess;
            }
            //信号机故障主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_SIGNAL_ERROR)) {
                if (tempData != null) {
                    JsonElement obj = gson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机版本查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_ATC_VERSION)) {
                return ReadDataScheduleSuccess;
            }
            //信号机版本主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_ATC_VERSION)) {
                if (tempData != null) {
                    JsonElement obj = gson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_PARAM_TRAMFER) {
            //信号灯组查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_GROUP)) {
                return ReadDataScheduleSuccess;
            }
            //信号灯组设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_GROUP)) {
                return ReadDataScheduleSuccess;
            }

            //设备参数查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CUSTOM)) {
                return ReadDataScheduleSuccess;
            }
            //设备参数设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CUSTOM)) {
                return ReadDataScheduleSuccess;
            }
            //设备参数设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_UPDATE)) {
                return ReadDataScheduleSuccess;
            }
            //相位查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_PHASE)) {
                return ReadDataScheduleSuccess;
            }
            //相位设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PHASE)) {
                return ReadDataScheduleSuccess;
            }
            //信号配时方案查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SCHEME_PARTTERN)) {
                return ReadDataScheduleSuccess;
            }
            //信号配时方案设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SCHEME_PARTTERN)) {
                return ReadDataScheduleSuccess;
            }
            //方案调度计划查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SCHEDUL_PLAN)) {
                return ReadDataScheduleSuccess;
            }
            //方案调度计划设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SCHEDUL_PLAN)) {
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_INTER_ORDER) {
            //工作方式查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_WORK_MODE)) {
                return ReadDataScheduleSuccess;
            }
            //工作方式设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_WORK_MODE)) {
                return ReadDataScheduleSuccess;
            }
            //特征参数版本查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_PARAM_VERSION)) {
                return ReadDataScheduleSuccess;
            }
            //特征参数版本设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PARAM_VERSION)) {
                return ReadDataScheduleSuccess;
            }
            //信号机识别码查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_IDENTIFY_CODE)) {
                return ReadDataScheduleSuccess;
            }
            //远程控制查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_REMOTE_CONTROL)) {
                return ReadDataScheduleSuccess;
            }
            //远程控制设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_REMOTE_CONTROL)) {
                return ReadDataScheduleSuccess;
            }
            //检测器查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_DETECTOR)) {
                return ReadDataScheduleSuccess;
            }
            //检测器设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_DETECTOR)) {
                return ReadDataScheduleSuccess;
            }
        }
        return ReadDataScheduleFault;
    }

    /**
     * @param recvData 返还上位机参数,pSource 待解析数据表数据, SourceLength 数据表有效长度
     * @return int  数据表解析状态
     * @Title: ReadDataSchedule
     * @Description: 对数据表进行解析，并将解析内容转发配置软件
     */
    public int ReadDataSchedule(MessageData recvData, byte[] dataSchedule, int dataScheduleSize) throws UnsupportedEncodingException {
        byte[] pDest;
        byte[] pRoadID = new byte[4];
        String roadID;
        String tempData = null;
        if (dataScheduleSize > 14) {
            pDest = new byte[dataScheduleSize - 14];
            for (int i = 14; i < dataScheduleSize; i++) {
                pDest[i - 14] = dataSchedule[i];  //取出有效数据data
            }
            tempData = new String(pDest, "UTF-8");
        }
        pRoadID[0] = dataSchedule[5];
        pRoadID[1] = dataSchedule[6];
        int int1 = pRoadID[0] & 0xff;
        int int2 = (pRoadID[1] & 0xff) << 8;
        int int3 = (pRoadID[2] & 0xff) << 16;
        int int4 = (pRoadID[3] & 0xff) << 24;
        int roadValue = int1 | int2 | int3 | int4;
        roadID = String.valueOf(roadValue);
        byte chDataLink = dataSchedule[3];
        byte chOperateType = dataSchedule[7];
        byte chInfoType = dataSchedule[8];

        recvData.setOperation(StringOperatorType(chOperateType));
        recvData.setThirdpartyid(roadID);
        recvData.setInfotype(StringOperatorObj(chInfoType));
        SetDataTorecvData(recvData, tempData);

        return checkDataLink(chDataLink, chOperateType, chInfoType, tempData, recvData);
    }

    public boolean isZero(byte[] dataSchedule) {
        for (int i = 0; i < dataSchedule.length; i++) {
            if (dataSchedule[i] != 0) {
                return false;
            }
        }
        return true;
    }
}

