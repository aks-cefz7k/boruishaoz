package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/9/15 9:16
 * @description：灯色实时上报消息
 * @modified By：
 * @version: 1.0.0$
 */

@Data
public class ChannelLampMsg {
    List<ChannelLamp> channellamp;
}
