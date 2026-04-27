package com.openatc.agent.model;

import lombok.Data;

import java.util.Date;

@Data
public class THisParamsVO {
    private int id;

    private String operator; // 操作者

    private Date opertime; // 操作时间

    private String source;   // 源地址

    private String agentid;  // 设备ID

    private String infotype; //消息类型

    private String status;// 返回状态

    private String requestbody;    // 操作请求内容

    private String responsebody;   // 操作返回内容

    private String extend_01;  // 预留

    private String extend_02;

    private String name; //路口名

    private Integer subInfoType; //消息子类型

    private String responseCode; //请求错误码

    private Integer deviceErrorCode; //设备错误码

    public THisParamsVO(THisParams tHisParams, String name) {
        this.id = tHisParams.getId();
        this.operator = tHisParams.getOperator();
        this.opertime = (Date)tHisParams.getOpertime().clone();
        this.source = tHisParams.getSource();
        this.agentid = tHisParams.getAgentid();
        this.infotype = tHisParams.getInfotype();
        this.subInfoType = tHisParams.getSubInfoType();
        this.responseCode = tHisParams.getResponseCode();
        this.deviceErrorCode = tHisParams.getDeviceErrorCode();
        this.status = tHisParams.getStatus();
        this.name = name;
    }

    public THisParamsVO() {
    }
}
