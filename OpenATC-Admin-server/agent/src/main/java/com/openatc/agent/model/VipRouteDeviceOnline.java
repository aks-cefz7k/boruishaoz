package com.openatc.agent.model;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/12/24 9:22
 * @description：勤务预案路口在线情况
 * @modified By：
 * @version: $
 */
public interface VipRouteDeviceOnline {
    String getAgentid();
    int getStatus();
    String getState();
}
