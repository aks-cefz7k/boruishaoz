package com.openatc.agent.model;

import com.openatc.model.model.MyGeometry;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VipRouteDeviceVO {
    private int id;

    private int viprouteid;

    private String agentid;

    private String name;

    private MyGeometry geometry;

    private double[] location;

    private int control;

    private int terminal;

    private String terminalname;

    private int value;

    private int totaltime;

    private int greenflash;

    private int duration;

    private int yellow;

    private int redclear;

    private int mingreen;

    private List<DirectionDetail> phases;

    private int state; // 0待执勤, 1执勤中
    private String resttime;
    private int currentControl; // 当前控制方式（-1未知）
    private String stateName; // 设备状态 DOWB/UP/FAULT

    public VipRouteDeviceVO (VipRouteDevice device, VipRouteDeviceStatus status) {
        this.id = device.getId();
        this.viprouteid = device.getViprouteid();
        this.agentid = device.getAgentid();
        this.name = device.getName();
        this.geometry = device.getGeometry();
        this.location = device.getLocation();
        this.control = device.getControl();
        this.terminal = device.getTerminal();
        this.terminalname = device.getTerminalname();
        this.value = device.getValue();
        this.totaltime = device.getTotaltime();
        this.phases = device.getPhases();
        this.greenflash = device.getGreenflash();
        this.duration = device.getDuration();
        this.yellow = device.getYellow();
        this.redclear = device.getRedclear();
        this.mingreen = device.getMingreen();
        this.state = status.getState();
        this.resttime = status.getResttime();
        this.currentControl = status.getControl();
        this.stateName = status.getStateName();
    }
}
