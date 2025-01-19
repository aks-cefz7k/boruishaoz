package com.openatc.agent.model;

import lombok.Data;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/9/14 10:59
 * @description：检测器数据
 * @modified By：
 * @version: 1.0.0$
 */

@Data
public class VolumeDetector {
    int id;
    int largevehnum;
    int middlevehnum;
    int smallvehnum;
    int totalvehtime;
    int occupyrate;
}
