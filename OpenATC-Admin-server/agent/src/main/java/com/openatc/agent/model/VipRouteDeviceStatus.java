package com.openatc.agent.model;

import lombok.Data;

@Data
public class VipRouteDeviceStatus {
    private String agentid;
    private int state; // 0待执勤, 1执勤中
    private String resttime;
    private int control; // 当前控制方式（-1未知）
    private String stateName; // 设备状态 DOWB/UP/FAULT

    public VipRouteDeviceStatus(String agentid, int state, String resttime ) {
        this.agentid = agentid;
        this.state = state;
        this.resttime = resttime;
        this.control = -1;
    }
    public VipRouteDeviceStatus(String agentid, int state, String resttime, String stateName) {
        this.agentid = agentid;
        this.state = state;
        this.resttime = resttime;
        this.control = -1;
        this.stateName = stateName;
    }

    public VipRouteDeviceStatus() {
    }
}
