package com.openatc.comm.packupack;

public final class CosntDataDefine {

    public static final byte                CB_HEADTAIL                 = -64;          //0xc0数据帧的开始或结束标识
    public static final byte                CB_ESCAPE1                  = -37;          //0xDB
    public static final byte                CB_ESCAPE2                  = -36;          //0xDC
    public static final byte                CB_ESCAPE3                  = -35;          //0xDD

    //GB25286  相关数据定义
    public static final byte                CB_VERSION_FLAG             = 0x10;         //版本号
    public static final byte                CB_SEND_FLAG                = 0x10;         //发送方标识
    public static final byte                CB_RECEIVE_FLAG             = 0x20;         //接收方标识
    //数据链路
    public static final byte                DATA_LINK_COMMU_SPE         = 0x01;         //通信规格
    public static final byte                DATA_LINK_BASIC_INFO        = 0x02;         //基本信息链路
    public static final byte                DATA_LINK_PARAM_TRAMFER     = 0x03;         //特征参数一般交互链路
    public static final byte                DATA_LINK_INTER_ORDER       = 0x04;         //干预指令链路
    public static final byte                DATA_LINK_CONTROL           = 0x05;         //和配置软件的命令链路
    public static final byte                DATA_LINK_CONFIG            = 0x06;         //和配置软件的参数链路
    //预留位
    public static final byte                RESERVE_FLAG                = 0x01;         //预留位

    //操作类型
    public static final byte                OPERATE_TYPE_QUERY_REQUEST  = -128;          //0x80查询请求
    public static final byte                OPERATE_TYPE_QUERY_ANSWER   = -125;          //0x83查询应答
    public static final byte                OPERATE_TYPE_SET_REQUEST    = -127;          //0x81设置请求
    public static final byte                OPERATE_TYPE_SET_ANSWER     = -124;          //0x84设置应答
    public static final byte                OPERATE_TYPE_REPORT         = -126;          //0x82主动上报
    public static final byte                OPERATE_TYPE_ERROR_ANSWER   = -123;          //0x85出错应答
    //对象标识
    public static final byte                INFO_TYPE_LOGIN             = 0x50;          //登录
    public static final byte                INFO_TYPE_LOGIN_REQUEST     = 0x51;          //信号机应答配置软件登录


    public static final byte                INFO_TYPE_ONLINE            = 0x01;           //联机
    public static final byte                INFO_TYPE_TRAFFIC_FLOW      = 0x02;           //交通流
    public static final byte                INFO_TYPE_WORK_STATE        = 0x03;           //工作状态
    public static final byte                INFO_TYPE_LAMP_COLOR        = 0x04;           //灯色状态
    public static final byte                INFO_TYPE_CURRENT_TIME      = 0x05;           //当前时间
    public static final byte                INFO_TYPE_SIGNAL_GROUP      = 0x06;           //信号灯组
    public static final byte                INFO_TYPE_PHASE             = 0x07;           //相位
    public static final byte                INFO_TYPE_SCHEME_PARTTERN   = 0x08;           //信号配时方案
    public static final byte                INFO_TYPE_SCHEDUL_PLAN      = 0x09;           //方案调度计划
    public static final byte                INFO_TYPE_WORK_MODE         = 0x0A;           //工作方式
    public static final byte                INFO_TYPE_SIGNAL_ERROR      = 0x0B;           //信号机故障
    public static final byte                INFO_TYPE_ATC_VERSION       = 0x0C;           //信号机版本
    public static final byte                INFO_TYPE_PARAM_VERSION     = 0x0D;           //特征参数版本
    public static final byte                INFO_TYPE_IDENTIFY_CODE     = 0x0E;           //信号机识别码
    public static final byte                INFO_TYPE_REMOTE_CONTROL    = 0x0F;           //远程控制
    public static final byte                INFO_TYPE_DETECTOR          = 0x10;           //检测器
    public static final byte                INFO_TYPE_ALL_FEATURE       = -96;            //整体参数0xA0
    public static final byte                INFO_TYPE_FEATURE_STATUS    = -95;            //方案状态0xA1
    public static final byte                INFO_TYPE_IP                = -94;            //IP 0xA2
    public static final byte                INFO_TYPE_SERIALPORT        = -93;            //SERIALPORT 0xA3
    public static final byte                INFO_TYPE_MANUALPANEL       = -92;            //手动面板0xA4
    public static final byte                INFO_TYPE_UPDATEUDISK       = -91;            //u盘更新0xA5
    public static final byte                INFO_TYPE_SYSTEM_REMOTE     = -90;            //远程调试0xA6
    public static final byte                INFO_TYPE_SYSTEM_LOG        = -89;            //操作日志0xA7
    public static final byte                INFO_TYPE_SYSTEM_CHANNEL_CHECK = -88;         //通道可检测 0xA8
    public static final byte                INFO_TYPE_VOLUMELOG         = -87;            //流量日志 0xA9
    public static final byte                INFO_TYPE_PATTERN_INTERRUPT = -86;            //方案干预 0xAA

    public static final byte                CFG_ACK_ASKSEND             = 0x18;           //主机回应配置软件请求发送数据
    public static final byte                CFG_ASK_ASKREAD             = 0x19;           //配置软件请求读数据
    public static final byte                CFG_ACK_ASKREAD_OK          = 0x20;           //主机回应配置软件请求读数据成功
    public static final byte                CFG_ASK_SENDDATA            = 0x22;           //配置软件下载数据
    public static final byte                CFG_ACK_SENDDATA_OK         = 0x23;           //配置软件下载数据成功
    public static final byte                CFG_ACK_SENDDATA_FAILED     = 0x24;           //配置软件下载数据成功

//////////////////////////////////////////////////////////////////////////////////////////////////

    //操作类型字符串
    public static final         String      getrequest      = "get-request";
    public static final         String      setrequest      = "set-request";
    public static final         String      getresponse     = "get-response";
    public static final         String      setresponse     = "set-response";
    public static final         String      report          = "report";
    public static final         String      erroresponse    = "error-response";
    public static final         String      errorrequest    = "error-request";

    //操作对象字符串定义
    public static final         String      login           = "login";                  //
    public static final         String      volume          = "status/currentvolume";   //交通流,当前流量
    public static final         String      workstatus      = "status/pattern";         //工作状态
    public static final         String      lampcolor       = "status/channel";         //
    public static final         String      time            = "system/time";            //当前时间
    public static final         String      signalgroup     = "feature/channel";        //信号灯组
    public static final         String      phase           = "feature/phase";          //
    public static final         String      timepattern     = "feature/pattern";        //配时方案
    public static final         String      scheduleplan    = "feature/plan";           //方案调度计划
    public static final         String      workmode        = "control/pattern";        //
    public static final         String      falut           = "status/fault";           //信号机故障
    public static final         String      atcversion      = "system/version";         //信号机版本
    public static final         String      paramversion    = "system/paramversion";    //
    public static final         String      code            = "system/code";            //识别码
    public static final         String      remote          = "control/remote";         //远程控制
    public static final         String      detector        = "feature/detecter";       //

    public static final         String      allfeature      = "feature/all";            //整体参数
    public static final         String      paramip         = "system/ip";              //IP
    public static final         String      serialport      = "system/serialport";      //串口
    public static final         String      manualpanel     = "system/manualpanel";     //手动面板设置
    public static final         String      channel         = "feature/channel";
    public static final         String      updatedisk      = "system/udiskupdate";     //u盘更新
    public static final         String      systemremote    = "system/remote";          //远程调试
    public static final         String      systemlog       = "system/log";             //操作日志
    public static final         String      channelcheck    = "system/channelcheck";    //通道可检测
    public static final         String      volumelog       = "system/volumelog";       //流量日志
    public static final         String      interrupt       = "control/interrupt";      //方案干预

}
