package com.openatc.agent.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/9/14 10:38
 * @description：信号机保存的交通流信息
 * @modified By：
 * @version: 1.0.0$
 */

@Data
public class Volume {
    String time;
    List<VolumeDetector> detector;
}
