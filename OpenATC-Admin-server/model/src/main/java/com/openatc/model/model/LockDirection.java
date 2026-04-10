package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/22 9:28
 * @description：锁定交通流
 * @modified By：
 * @version: $
 */
@Data
public class LockDirection {
    private int duration; //锁定绿灯持续时间
    private int greenflash; //过渡绿闪时间
    private int yellow; //过渡黄灯时间
    private int redclear; //过渡全红时间
    private int mingreen; //最小绿时间
    private List<Integer> direction; //锁定交通流方向，方向取值同相位车道方向
    private List<Integer> peddirection; // 锁定的行人方向，方向取值同相位行人方向
}
