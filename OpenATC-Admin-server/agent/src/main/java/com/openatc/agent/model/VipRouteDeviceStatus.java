package com.openatc.agent.model;

import lombok.Data;

@Data
public class VipRouteDeviceStatus {
    private String agentid;
    private int state;
    private String resttime;

    public VipRouteDeviceStatus(String agentid, int state, String resttime) {
        this.agentid = agentid;
        this.state = state;
        this.resttime = resttime;
    }

    public VipRouteDeviceStatus() {
    }
}
