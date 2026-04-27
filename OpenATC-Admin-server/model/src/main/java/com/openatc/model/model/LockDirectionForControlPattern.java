package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/22 9:28
 * @description：控制方式 - 16，方向锁定消息定义
 * @modified By：
 * @version: $
 */
@Data
public class LockDirectionForControlPattern {
    private int duration; //锁定绿灯持续时间
    private int greenflash; //过渡绿闪时间
    private int yellow; //过渡黄灯时间
    private int redclear; //过渡全红时间
    private int mingreen; //最小绿时间
    private List<ChannelLock> channellocKinfo; //锁定交通流方向，方向取值同相位车道方向

    public LockDirectionForControlPattern(LockDirection lockDirection){
        duration = lockDirection.getDuration();
        greenflash = lockDirection.getGreenflash();
        yellow = lockDirection.getYellow();
        redclear = lockDirection.getRedclear();
        mingreen = lockDirection.getMingreen();
    }
}
