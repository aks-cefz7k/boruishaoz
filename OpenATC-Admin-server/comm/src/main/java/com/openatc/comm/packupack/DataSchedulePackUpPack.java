package com.openatc.comm.packupack;
/**
 * @ClassName: DataSchedulePackUpPack
 * @Description: TODO
 * @author liangting
 * @date 2019年10月30日 上午10:16:06
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.openatc.comm.data.MessageData;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.openatc.comm.packupack.CosntDataDefine.*;

public class DataSchedulePackUpPack { //数据表内容宏定义

    private static final int MAX_DATA_SCHEDULE = 14;         //数据表大小
    private static final int ReadDataScheduleSuccess = 1;
    private static final int ReadDataScheduleFault = 3;
    private static final int SCHEDULEBUFFER = 10 * 1024;


    byte[] dataSchdule = new byte[MAX_DATA_SCHEDULE];

    public void DataSchedulePackUpPack() {

    }

    //操作类型值
    public int NumberOperatorType(String operatortype) {
        if (operatortype.equals(getrequest)) {
            return 1;
        }
        if (operatortype.equals(setrequest)) {
            return 2;
        }
        if (operatortype.equals(getresponse)) {
            return 3;
        }
        if (operatortype.equals(setresponse)) {
            return 4;
        }
        if (operatortype.equals(report)) {
            return 5;
        }
        return 0;
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

    //操作对象值
    public int NuberOperatorObj(String operatorObj) {
        if (operatorObj.equals(login)) {
            return 1;
        }
        if (operatorObj.equals(volume)) {
            return 2;
        }
        if (operatorObj.equals(workstatus)) {
            return 3;
        }
        if (operatorObj.equals(lampcolor)) {
            return 4;
        }
        if (operatorObj.equals(time)) {
            return 5;
        }
        if (operatorObj.equals(signalgroup)) {
            return 6;
        }
        if (operatorObj.equals(phase)) {
            return 7;
        }
        if (operatorObj.equals(timepattern)) {
            return 8;
        }
        if (operatorObj.equals(scheduleplan)) {
            return 9;
        }
        if (operatorObj.equals(workmode)) {
            return 10;
        }
        if (operatorObj.equals(falut)) {
            return 11;
        }
        if (operatorObj.equals(atcversion)) {
            return 12;
        }
        if (operatorObj.equals(paramversion)) {
            return 13;
        }
        if (operatorObj.equals(code)) {
            return 14;
        }
        if (operatorObj.equals(remote)) {
            return 15;
        }
        if (operatorObj.equals(detector)) {
            return 16;
        }
        if (operatorObj.equals(allfeature)) {
            return 17;
        }
        if (operatorObj.equals(paramip)) {
            return 19;
        }
        if (operatorObj.equals(serialport)) {
            return 20;
        }
        if (operatorObj.equals(manualpanel)) {
            return 21;
        }
        if (operatorObj.equals(updatedisk)) {
            return 22;
        }
        if (operatorObj.equals(systemremote)) {
            return 23;
        }
        if (operatorObj.equals(systemlog)) {
            return 24;
        }
        if (operatorObj.equals(channelcheck)) {
            return 25;
        }
        if (operatorObj.equals(volumelog)) {
            return 26;
        }
        if (operatorObj.equals(interrupt)) {
            return 27;
        }
        if (operatorObj.equals(channelstatus)) {
            return 28;
        }
        if (operatorObj.equals(channellampstatus)) {
            return 29;
        }
		if (operatorObj.equals(systemcustom)) {
            return 30;
        }
        return 0;
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
        if (operatorObj == 0x24 || operatorObj == 0x23||operatorObj == 0x18) {
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
     * @param  sendData 发给信号机的数据
     * @return byte[] 转换为数据表的结果
     * @Title: PackDataSchedule
     * @Description: 根据国标对发送数据进行协议转化（转换为数据表的结构）
     */
    public byte[] PackDataSchedule(MessageData sendData) throws UnsupportedEncodingException {
        String operationtype = sendData.getOperation();
        String infotype = sendData.getInfotype();
        switch (NumberOperatorType(operationtype)) {
            case 1://查询请求
                switch (NuberOperatorObj(infotype)) {
                    case 1: //上位机发login请求
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_LOGIN;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容:上位机联机请求
                        String dataLogin = null;
                        JsonElement dataLoginObject = sendData.getData();
                        if (dataLoginObject != null) {
                            dataLogin = dataLoginObject.toString();
                            byte[] dataSend = dataLogin.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 2: //上位机查询交通流请求
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;//链路5
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_TRAFFIC_FLOW;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容:上位机查询交通流
                        String dataVolume = null;
                        JsonElement dataVolumeObject = sendData.getData();
                        if (dataVolumeObject != null) {
                            dataVolume = dataVolumeObject.toString();
                            byte[] dataSend = dataVolume.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 3://工作状态查询
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_WORK_STATE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataWorkStatus = null;
                        JsonElement dataWorkStatusObject = sendData.getData();
                        if (dataWorkStatusObject != null) {
                            dataWorkStatus = dataWorkStatusObject.toString();
                            byte[] dataSend = dataWorkStatus.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 4://灯色状态查询
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_LAMP_COLOR;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataLampColor = null;
                        JsonElement dataLampColorObject = sendData.getData();
                        if (dataLampColorObject != null) {
                            dataLampColor = dataLampColorObject.toString();
                            byte[] dataSend = dataLampColor.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 5://查询信号机时间
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_CURRENT_TIME;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataAscTime = null;
                        JsonElement dataAscTimeObject = sendData.getData();
                        if (dataAscTimeObject != null) {
                            dataAscTime = dataAscTimeObject.toString();
                            byte[] dataSend = dataAscTime.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 6://查询信号灯组信息
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SIGNAL_GROUP;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataSignalGroup = null;
                        JsonElement dataSignalGroupObject = sendData.getData();
                        if (dataSignalGroupObject != null) {
                            dataSignalGroup = dataSignalGroupObject.toString();
                            byte[] dataSend = dataSignalGroup.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 7://查询相位信息
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_PHASE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPhase = null;
                        JsonElement dataPhaseObject = sendData.getData();
                        if (dataPhaseObject != null) {
                            dataPhase = dataPhaseObject.toString();
                            byte[] dataSend = dataPhase.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 8://查询信号配时方案
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SCHEME_PARTTERN;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPattern = null;
                        JsonElement dataPatternObject = sendData.getData();
                        if (dataPatternObject != null) {
                            dataPattern = dataPatternObject.toString();
                            byte[] dataSend = dataPattern.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 9://查询方案调度计划
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SCHEDUL_PLAN;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPlan = null;
                        JsonElement dataPlanObject = sendData.getData();
                        if (dataPlanObject != null) {
                            dataPlan = dataPlanObject.toString();
                            byte[] dataSend = dataPlan.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 10://查询工作方式
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_WORK_MODE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataWorkMode = null;
                        JsonElement dataWorkModeObject = sendData.getData();
                        if (dataWorkModeObject != null) {
                            dataWorkMode = dataWorkModeObject.toString();
                            byte[] dataSend = dataWorkMode.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 11://查询信号机故障
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SIGNAL_ERROR;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataAscError = null;
                        JsonElement dataAscErrorObject = sendData.getData();
                        if (dataAscErrorObject != null) {
                            dataAscError = dataAscErrorObject.toString();
                            byte[] dataSend = dataAscError.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 12://查询信号机版本
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_ATC_VERSION;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataAscVersion = null;
                        JsonElement dataAscVersionObject = sendData.getData();
                        if (dataAscVersionObject != null) {
                            dataAscVersion = dataAscVersionObject.toString();
                            byte[] dataSend = dataAscVersion.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 13://查询特征参数版本
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_PARAM_VERSION;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataParamVersion = null;
                        JsonElement dataParamVersionObject = sendData.getData();
                        if (dataParamVersionObject != null) {
                            dataParamVersion = dataParamVersionObject.toString();
                            byte[] dataSend = dataParamVersion.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 14://信号机识别码
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_IDENTIFY_CODE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataIdentifyCode = null;
                        JsonElement dataIdentifyCodeObject = sendData.getData();
                        if (dataIdentifyCodeObject != null) {
                            dataIdentifyCode = dataIdentifyCodeObject.toString();
                            byte[] dataSend = dataIdentifyCode.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 16://检测器
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_DETECTOR;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataDetector = null;
                        JsonElement dataDetectorObject = sendData.getData();
                        if (dataDetectorObject != null) {
                            dataDetector = dataDetectorObject.toString();
                            byte[] dataSend = dataDetector.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 17://查询整体参数feature/all
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONFIG;//配置软件和信号机的参数命令
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = CFG_ASK_ASKREAD;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataAllFeature = null;
                        JsonElement dataAllFeatureObject = sendData.getData();
                        if (dataAllFeatureObject != null) {
                            dataAllFeature = dataAllFeatureObject.toString();
                            byte[] dataSend = dataAllFeature.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 18:// 查询方案状态
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONFIG;// 配置软件和信号机的参数命令
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_FEATURE_STATUS;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPatternStatus = null;
                        JsonElement dataPatternStatusObject = sendData.getData();
                        if (dataPatternStatusObject != null) {
                            dataPatternStatus = dataPatternStatusObject.toString();
                            byte[] dataSend = dataPatternStatus.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 21:// 查询手动面板
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_MANUALPANEL;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataMannelPanel = null;
                        JsonElement dataMannelPanelObject = sendData.getData();
                        if (dataMannelPanelObject != null) {
                            dataMannelPanel = dataMannelPanelObject.toString();
                            byte[] dataSend = dataMannelPanel.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 23:// 查询远程调试
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_REMOTE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataSystemRemote = null;
                        JsonElement dataSystemRemoteObject = sendData.getData();
                        if (dataSystemRemoteObject != null) {
                            dataSystemRemote = dataSystemRemoteObject.toString();
                            byte[] dataSend = dataSystemRemote.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 24:// 查询操作日志
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_LOG;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataSystemLog = null;
                        JsonElement dataSystemLogObject = sendData.getData();
                        if (dataSystemLogObject != null) {
                            dataSystemLog = dataSystemLogObject.toString();
                            byte[] dataSend = dataSystemLog.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 28:// 查询通道状态（电压电流）
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_CHANNEL_STATUS;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataChannelStatus = null;
                        JsonElement dataChannelStatusObject = sendData.getData();
                        if (dataChannelStatusObject != null) {
                            dataChannelStatus = dataChannelStatusObject.toString();
                            byte[] dataSend = dataChannelStatus.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 29:// 查询通道灯色状态
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_CHANNEL_LAMP_STATUS;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataChannelLampStatus = null;
                        JsonElement dataChannelLampStatusObject = sendData.getData();
                        if (dataChannelLampStatusObject != null) {
                            dataChannelLampStatus = dataChannelLampStatusObject.toString();
                            byte[] dataSend = dataChannelLampStatus.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
					case 30:// 查询设备参数
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_QUERY_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_CUSTOM;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataSystemCustom = null;
                        JsonElement dataSystemCustomObject = sendData.getData();
                        if (dataSystemCustomObject != null) {
                            dataSystemCustom = dataSystemCustomObject.toString();
                            byte[] dataSend = dataSystemCustom.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    default:
                        Arrays.fill(dataSchdule, (byte) 0);
                        //异常处理
                }
                break;
            case 2://设置请求
                switch (NuberOperatorObj(infotype)) {
                    case 5://设置时间
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_BASIC_INFO;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_CURRENT_TIME;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容:据格林尼治时间
                        String dataTime = null;
                        JsonElement dataTimeObject = sendData.getData();
                        if (dataTimeObject != null) {
                            dataTime = dataTimeObject.toString();
                            byte[] dataSend = dataTime.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 6://设置信号灯组
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SIGNAL_GROUP;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataChannel = null;
                        JsonElement dataChannelObject = sendData.getData();
                        if (dataChannelObject != null) {
                            dataChannel = dataChannelObject.toString();
                            byte[] dataSend = dataChannel.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 7://相位设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_PHASE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPhase = null;
                        JsonElement dataPhaseObject = sendData.getData();
                        if (dataPhaseObject != null) {
                            dataPhase = dataPhaseObject.toString();
                            byte[] dataSend = dataPhase.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 8://配时方案设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SCHEME_PARTTERN;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataPattern = null;
                        JsonElement dataPatternObject = sendData.getData();
                        if (dataPatternObject != null) {
                            dataPattern = dataPatternObject.toString();
                            byte[] dataSend = dataPattern.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 9://方案调度计划
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SCHEDUL_PLAN;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //
                        String dataPlan = null;
                        JsonElement dataPlanObject = sendData.getData();
                        if (dataPlanObject != null) {
                            dataPlan = dataPlanObject.toString();
                            byte[] dataSend = dataPlan.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 10://工作方式设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_WORK_MODE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        String dataMode = null;
                        JsonElement dataModeObject = sendData.getData();
                        if (dataModeObject != null) {
                            dataMode = dataModeObject.toString();
                            byte[] dataSend = dataMode.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 13://特征参数版本设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_PARAM_VERSION;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String dataParamVersion = null;
                        JsonElement dataParamVersionObject = sendData.getData();
                        if (dataParamVersionObject != null) {
                            dataParamVersion = dataParamVersionObject.toString();
                            byte[] dataSend = dataParamVersion.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 15://远程控制设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_REMOTE_CONTROL;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String dataRemote = null;
                        JsonElement dataRemotenObject = sendData.getData();
                        if (dataRemotenObject != null) {
                            dataRemote = dataRemotenObject.toString();
                            byte[] dataSend = dataRemote.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 16://检测器设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_INTER_ORDER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_DETECTOR;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String dataDetector = null;
                        JsonElement dataDetectorObject = sendData.getData();
                        if (dataDetectorObject != null) {
                            dataDetector = dataDetectorObject.toString();
                            byte[] dataSend = dataDetector.getBytes("UTF-8");
                            int dataSendCount = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendCount + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendCount);
                        }
                        break;
                    case 17://下载整体参数
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONFIG;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = CFG_ASK_SENDDATA;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        byte[] dataSizeFlag = new byte[4];
                        int dataSendCount = 0;
                        Gson gson = new Gson();
                        String dataSetAllParam = gson.toJson(sendData);

//                        String AllParamJson= toFormat(dataSetAllParam,true,true);

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
                    case 21://手动面板设置
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_MANUALPANEL;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String manualPanel = null;
                        JsonElement manualPanelObject = sendData.getData();
                        if (manualPanelObject != null) {
                            manualPanel = manualPanelObject.toString();
                            byte[] dataSend = manualPanel.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 22://U盘更新
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_UPDATEUDISK;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String udiskupdate = null;
                        JsonElement udiskupdateObject = sendData.getData();
                        if (udiskupdateObject != null) {
                            udiskupdate = udiskupdateObject.toString();
                            byte[] dataSend = udiskupdate.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 23://远程调试
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_REMOTE;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String systemRemote = null;
                        JsonElement systemRemoteObject = sendData.getData();
                        if (systemRemoteObject != null) {
                            systemRemote = systemRemoteObject.toString();
                            byte[] dataSend = systemRemote.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 25://通道检测
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_CHANNEL_CHECK;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String strChannelCheck = null;
                        JsonElement strChannelCheckObject = sendData.getData();
                        if (strChannelCheckObject != null) {
                            strChannelCheck = strChannelCheckObject.toString();
                            byte[] dataSend = strChannelCheck.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 26://流量日志获取
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_VOLUMELOG;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String strVolumeLog = null;
                        JsonElement strVolumeLogObject = sendData.getData();
                        if (strVolumeLogObject != null) {
                            strVolumeLog = strVolumeLogObject.toString();
                            byte[] dataSend = strVolumeLog.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 27://方案干预
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_CONTROL;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_PATTERN_INTERRUPT;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        String strInterrupt = null;
                        JsonElement strInterruptObject = sendData.getData();
                        if (strInterruptObject != null) {
                            strInterrupt = strInterruptObject.toString();
                            byte[] dataSend = strInterrupt.getBytes("UTF-8");
                            int dataSendSize = dataSend.length;
                            dataSchdule = Arrays.copyOf(dataSchdule, dataSendSize + 14);
                            System.arraycopy(dataSend, 0, dataSchdule, 14, dataSendSize);
                        }
                        break;
                    case 30://下载设备参数
                        Arrays.fill(dataSchdule, (byte) 0);
                        dataSchdule[0] = CB_VERSION_FLAG;
                        dataSchdule[1] = CB_RECEIVE_FLAG;
                        dataSchdule[2] = CB_SEND_FLAG;
                        dataSchdule[3] = DATA_LINK_PARAM_TRAMFER;
                        dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                        dataSchdule[8] = INFO_TYPE_SYSTEM_CUSTOM;
                        dataSchdule[9] = RESERVE_FLAG;
                        dataSchdule[10] = 0x00;
                        dataSchdule[11] = 0x00;
                        dataSchdule[12] = 0x00;
                        dataSchdule[13] = 0x00;
                        //数据内容
                        dataSizeFlag = new byte[4];
                        dataSendCount = 0;
                        gson = new Gson();
                        String dataSetSystemCustomParam = gson.toJson(sendData);

//                        String AllParamJson= toFormat(dataSetAllParam,true,true);
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
                        break;
                    default:
                        Arrays.fill(dataSchdule, (byte) 0);
                }
                break;
            case 3://设置应答
                if (infotype.equals(login)) {
                    Arrays.fill(dataSchdule, (byte) 0);
                    dataSchdule[0] = CB_VERSION_FLAG;
                    dataSchdule[1] = CB_RECEIVE_FLAG;
                    dataSchdule[2] = CB_SEND_FLAG;
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_ANSWER;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
                    dataSchdule[9] = RESERVE_FLAG;
                    dataSchdule[10] = 0x00;
                    dataSchdule[11] = 0x00;
                    dataSchdule[12] = 0x00;
                    dataSchdule[13] = 0x00;
                    //无数据统一留一个字节
                    byte[] nodata14 = new byte[1];
                    nodata14[0] = 0x00;
                    dataSchdule = Arrays.copyOf(dataSchdule, 15);
                    System.arraycopy(nodata14, 0, dataSchdule, 14, 1);
                }
                break;
            case 4://查询应答
                if (infotype.equals(login)) {
                    Arrays.fill(dataSchdule, (byte) 0);
                    dataSchdule[0] = CB_VERSION_FLAG;
                    dataSchdule[1] = CB_RECEIVE_FLAG;
                    dataSchdule[2] = CB_SEND_FLAG;
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_REQUEST;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
                    dataSchdule[9] = RESERVE_FLAG;
                    dataSchdule[10] = 0x00;
                    dataSchdule[11] = 0x00;
                    dataSchdule[12] = 0x00;
                    dataSchdule[13] = 0x00;
                    //无数据统一留一个字节
                    byte[] nodata15 = new byte[1];
                    nodata15[0] = 0x00;
                    dataSchdule = Arrays.copyOf(dataSchdule, 15);
                    System.arraycopy(nodata15, 0, dataSchdule, 14, 1);
                }
                break;
            default:
                Arrays.fill(dataSchdule, (byte) 0);
        }
        return dataSchdule.clone();//待思考
    }

    //应答信号的数据表
    public byte[] AskPackDataSchedule(MessageData sendData) {
        String operationtype = sendData.getOperation();
        String infotype = sendData.getInfotype();
        switch (NumberOperatorType(operationtype)) {

            case 2://设置应答
                if (infotype.equals(login)) {
                    Arrays.fill(dataSchdule, (byte) 0);
                    dataSchdule[0] = CB_VERSION_FLAG;
                    dataSchdule[1] = CB_RECEIVE_FLAG;
                    dataSchdule[2] = CB_SEND_FLAG;
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_ANSWER;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
                    dataSchdule[9] = RESERVE_FLAG;
                    dataSchdule[10] = 0x00;
                    dataSchdule[11] = 0x00;
                    dataSchdule[12] = 0x00;
                    dataSchdule[13] = 0x00;
                }
                break;
            case 5:
                //查询应答
                if (infotype.equals(login)) {
                    Arrays.fill(dataSchdule, (byte) 0);
                    dataSchdule[0] = CB_VERSION_FLAG;
                    dataSchdule[1] = CB_RECEIVE_FLAG;
                    dataSchdule[2] = CB_SEND_FLAG;
                    dataSchdule[3] = DATA_LINK_COMMU_SPE;
                    dataSchdule[7] = OPERATE_TYPE_SET_ANSWER;
                    dataSchdule[8] = INFO_TYPE_ONLINE;
                    dataSchdule[9] = RESERVE_FLAG;
                    dataSchdule[10] = 0x00;
                    dataSchdule[11] = 0x00;
                    dataSchdule[12] = 0x00;
                    dataSchdule[13] = 0x00;
//                    //无数据统一留一个字节
//                    byte[] nodata15=new byte[1];
//                    nodata15[0]=0x00;
//                    dataSchdule=Arrays.copyOf(dataSchdule,15);
//                    System.arraycopy(nodata15,0,dataSchdule,14,1);
                }
                break;
            default:
                Arrays.fill(dataSchdule, (byte) 0);
        }
        return dataSchdule.clone();//待思考
    }

    /**
     * @param  recvData 返还上位机参数，pSource 待解析数据表数据， SourceLength 数据表有效长度
     * @return int  数据表解析状态
     * @Title: ReadDataSchedule
     * @Description: 对数据表进行解析，并将解析内容转发配置软件
     */
    public int ReadDataSchedule(MessageData recvData, byte[] dataSchedule, int dataScheduleSize) throws UnsupportedEncodingException {
        byte[] pDest = null;
        byte[] pRoadID = new byte[4];
        String roadID = null;
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
        if (chDataLink == DATA_LINK_CONTROL)//配置软件和信号的命令链路
        {
            //信号机应答联机kedacom
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_LOGIN_REQUEST)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答操作日志
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_LOG)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }

            //信号机应答通道状态(电压、电流)
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_CHANNEL_STATUS)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答通道灯色
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_CHANNEL_LAMP_STATUS)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答通道可检测
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CHANNEL_CHECK)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }

            //信号机应答方案干预
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PATTERN_INTERRUPT)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }

            //信号机应答流量日志获取环境状态
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_VOLUMELOG)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setAgentid(roadID);
                recvData.setInfotype(StringOperatorObj(chInfoType));
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答当前交通流量查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_TRAFFIC_FLOW)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答手动面板参数设置
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_MANUALPANEL)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答手动面板参数设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_MANUALPANEL)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //配工具应答U盘更新
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_UPDATEUDISK)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }

                return ReadDataScheduleSuccess;
            }

            //信号机应答远程调试设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_REMOTE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答远程调试查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_REMOTE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_CONFIG)      //配置软件和信号机的参数链路
        {
            //信号机应答整体参数查询
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == CFG_ACK_ASKSEND)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答整体参数设置成功
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == CFG_ACK_SENDDATA_OK)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机应答整体参数设置
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == CFG_ACK_SENDDATA_FAILED)) {
                recvData.setOperation(StringOperatorType(OPERATE_TYPE_ERROR_ANSWER));
                recvData.setInfotype(StringOperatorObj(CFG_ACK_SENDDATA_FAILED));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_COMMU_SPE) {
            //联机请求
            if ((chOperateType == OPERATE_TYPE_SET_REQUEST) && (chInfoType == INFO_TYPE_ONLINE)) {
                recvData.setOperation(StringOperatorType(OPERATE_TYPE_REPORT));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //联机查询
            if ((chOperateType == OPERATE_TYPE_QUERY_REQUEST) && (chInfoType == INFO_TYPE_ONLINE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_BASIC_INFO) {///////链路2 主动上传
            //交通流信息主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_TRAFFIC_FLOW)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机工作状态主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_WORK_STATE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //灯色状态主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_LAMP_COLOR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机工作状态查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_WORK_STATE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //灯色状态查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_LAMP_COLOR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //时间查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == 0x05)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj((byte) 0x05));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //时间设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == 0x05)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj((byte) 0x05));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机故障查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_ERROR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机故障主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_SIGNAL_ERROR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机版本查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_ATC_VERSION)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机版本主动上传
            if ((chOperateType == OPERATE_TYPE_REPORT) && (chInfoType == INFO_TYPE_ATC_VERSION)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_PARAM_TRAMFER) {
            //信号灯组查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_GROUP)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号灯组设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SIGNAL_GROUP)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }

            //设备参数查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CUSTOM)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //设备参数设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SYSTEM_CUSTOM)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //相位查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_PHASE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //相位设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PHASE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号配时方案查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SCHEME_PARTTERN)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号配时方案设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SCHEME_PARTTERN)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //方案调度计划查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_SCHEDUL_PLAN)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //方案调度计划设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_SCHEDUL_PLAN)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        if (chDataLink == DATA_LINK_INTER_ORDER) {
            //工作方式查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_WORK_MODE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //工作方式设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_WORK_MODE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //特征参数版本查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_PARAM_VERSION)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //特征参数版本设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_PARAM_VERSION)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //信号机识别码查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_IDENTIFY_CODE)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //远程控制查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_REMOTE_CONTROL)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //远程控制设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_REMOTE_CONTROL)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //检测器查询应答
            if ((chOperateType == OPERATE_TYPE_QUERY_ANSWER) && (chInfoType == INFO_TYPE_DETECTOR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
            //检测器设置应答
            if ((chOperateType == OPERATE_TYPE_SET_ANSWER) && (chInfoType == INFO_TYPE_DETECTOR)) {
                recvData.setOperation(StringOperatorType(chOperateType));
                recvData.setInfotype(StringOperatorObj(chInfoType));
                recvData.setAgentid(roadID);
                Gson recvDataJson = new Gson();
                if (tempData != null) {
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                if (tempData == null) {
                    tempData = "{\n" +
                            "\t\"sucess\": 1\n" +
                            "}";
                    JsonElement obj = recvDataJson.fromJson(tempData, JsonElement.class);
                    recvData.setData(obj);
                }
                return ReadDataScheduleSuccess;
            }
        }
        return ReadDataScheduleFault;
    }

    public boolean isZero(byte[] dataschdule) {
        for (int i = 0; i < dataSchdule.length; i++) {
            if (dataSchdule[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public byte[] GenCheckData(byte[] sendDataSchedule) {
        boolean backStatus = false;
        int checkflag = 0xff;
        for (int i = 1; i < sendDataSchedule.length - 2; i++) {
            checkflag = checkflag + sendDataSchedule[i];
        }
        byte checkValue = (byte) (checkflag & 0xff);
        byte[] sendCheckData = new byte[sendDataSchedule.length + 1];
        switch (checkValue) {
            case CB_HEADTAIL:
                for (int j = 0; j < sendDataSchedule.length - 2; j++) {
                    sendCheckData[j] = sendDataSchedule[j];
                }
                sendCheckData[sendDataSchedule.length - 2] = CB_ESCAPE1;
                sendCheckData[sendDataSchedule.length - 1] = CB_ESCAPE2;
                sendCheckData[sendDataSchedule.length] = sendDataSchedule[sendDataSchedule.length - 1];
                backStatus = true;
                break;
            case CB_ESCAPE1:
                for (int j = 0; j < sendDataSchedule.length - 2; j++) {
                    sendCheckData[j] = sendDataSchedule[j];
                }
                sendCheckData[sendDataSchedule.length - 2] = CB_ESCAPE1;
                sendCheckData[sendDataSchedule.length - 1] = CB_ESCAPE3;
                sendCheckData[sendDataSchedule.length] = sendDataSchedule[sendDataSchedule.length - 1];
                backStatus = true;
                break;
            default:
                sendDataSchedule[sendDataSchedule.length - 2] = checkValue;
                backStatus = false;
        }
        if (backStatus) {
            return sendCheckData;
        } else {
            return sendDataSchedule;
        }
    }

    /**
     * @param content 要格式化的json字符串,indent 是否进行缩进(false时压缩为一行) colonWithSpace key冒号后是否加入空格
     * @return json格式化输出
     * @Title: toFormat
     * @Description: json字符串格式化输出
     * liangting  2020/6/9
     */
    public static String toFormat(String content, boolean indent, boolean colonWithSpace) {
        if (content == null) return null;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        boolean inString = false;
        String tab = "\t";
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            switch (ch) {
                case '{':
                case '[':
                    sb.append(ch);
                    if (!inString) {
                        if (indent) {
                            sb.append("\n");
                            count++;
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        }

                    }
                    break;
                case '\uFEFF': //非法字符
                    if (inString) sb.append(ch);
                    break;
                case '}':
                case ']':
                    if (!inString) {
                        if (indent) {
                            count--;
                            sb.append("\n");
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        }

                        sb.append(ch);
                    } else {
                        sb.append(ch);
                    }
                    break;
                case ',':
                    sb.append(ch);
                    if (!inString) {
                        if (indent) {
                            sb.append("\n");
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        } else {
                            if (colonWithSpace) {
                                sb.append(' ');
                            }
                        }
                    }
                    break;
                case ':':
                    sb.append(ch);

                    if (!inString) {
                        if (colonWithSpace) {  //key名称冒号后加空格
                            sb.append(' ');
                        }
                    }
                    break;
                case ' ':
                case '\n':
                case '\t':
                    if (inString) {
                        sb.append(ch);
                    }
                    break;
                case '"':
                    if (i > 0 && content.charAt(i - 1) != '\\') {
                        inString = !inString;
                    }
                    sb.append(ch);
                    break;
                default:
                    sb.append(ch);
                    break;
            }
        }
        return sb.toString();
    }
}

