package com.openatc.agent.model;

import lombok.Data;

@Data
public class ControlMsg {


    //control 控制方式
    private int control;
    private int terminal;
    private int value;
}