package com.openatc.agent.A1049.model;

import javax.persistence.criteria.CriteriaBuilder;

public enum Infotype {
    SYSINFO("SysInfo", "SysInfo"), //系统参数
    REGIONPARAM("RegionParam", "RegionParam"), //区域参数
    SUBREGIONPARAM("SubRegionParam", "SubRegionParam"), //子区参数
    CROSSPARAM("CrossParam", "CrossParam"), //路口参数
    SIGNALCONTROLER("SignalControler", "SignalControler"), //信号机参数
    LAMPGROUP("LampGroup", "LampGroup"), // 信号灯组参数
    DETPARAM("feature/detecter", "DetParam"), //检测器参数
    LANEPARAM("LaneParam", "LaneParam"), //车道参数
    PHASEPARAM("PhaseParam", "PhaseParam"), //相位参数
    STAGEPARAM("StageParam", "StageParam"), //阶段参数
    PLANPARAM("PlanParam", "PlanParam"), //配时方案参数
    SYSSTATE("SysState", "SysState"), //系统状态
    REGIONSTATE("RegionState", "RegionState"), //区域状态
    CROSSSTATE("CrossState", "CrossState"), //路口状态
    SINGALCONTROLERERROR("SignalControlerError", "SignalControlerError"), //信号机故障
    CONTROLMODE("ControlMode", "ControlMode"), //路口控制方式
    CROSSCYCLE("CrossCycle", "CrossCycle"), //路口周期
    CROSSSTAGE("CrossStage", "CrossStage"), //路口阶段
    CROSSPLAN("CrossPlan", "CrossPlan"), //路口控制方案
    CROSSTRAFFICDATA("CrossTrafficData", "CrossTrafficData"), //路口交通流数据
    LOCKSTAGE("LockStage", "LockStage"), //锁定阶段
    UNLOCKSTAGE("UnLockStage", "UnLockStage"), //解锁阶段
    SYNCDATETIME("SyncDateTime", "SyncDateTime"), //对时
    CROSSCYCLESTAGEREPORT("CrossCycleStageReport", "CrossCycleStageReport"); //路口周期和阶段上传设置

    /**
     * login 登录
     * control/pattern 控制方式
     * control/interrupt 方案干预控制
     * control/preempt 优先控制
     * control/channelcheck 通道检测
     * control/remote 远程控制
     * system/centerip 修改中心IP地址
     * system/ip1 修改设备IP地址1
     * system/ip2 修改设备IP地址2
     * system/serialport 修改串口信息
     * system/time 当前时间
     * system/remote 远程调试
     * system/update 系统更新
     * system/paramversion 参数版本
     * system/optstatparam 优化统计参数
     * system/log 日志信息
     * system/fault 故障
     * system/faultclear 故障清除
     * system/version 信号机版本
     * system/checkcode 参数校验码
     * system/udiskupdate 更新优盘数据
     * system/volumelog 获取交通流量历史数据
     * status/currentvolume 交通流
     * status/fso 流量
     * status/pattern 方案状态
     * status/channellamp 通道灯色状态
     * status/channeltest 通道电压电流状态
     * feature/channel 通道
     * feature/phase 相位
     * feature/overlap 跟随相位
     * feature/pattern 方案
     * feature/plan 计划
     * feature/date 日期
     * feature/detecter 机动车检测器
     * feature/peddetecter 行人检测器
     * feature/devinfo 设备信息
     * feature/manualpanel 手动面板配置
     * feature/channellock 通道灯色锁定参数配置
     */
    private String xmlInfotype;
    private String jsonInfotype;

    Infotype(String jsonInfotype, String xmlInfotype) {
        this.jsonInfotype = jsonInfotype;
        this.xmlInfotype = xmlInfotype;
    }

    public static String getJsonInfotype(String xmlInfotype) {
        for (Infotype infotype : Infotype.values()) {
            if (infotype.xmlInfotype.equals(xmlInfotype)) return infotype.jsonInfotype;
        }
        return null;
    }

    public static String getXmlInfotype(String jsonInfotype){
        for (Infotype infotype : Infotype.values()){
            if (infotype.jsonInfotype.equals(jsonInfotype)) return  infotype.xmlInfotype;
        }
        return null;
    }
}
