package com.openatc.agent.model;

import lombok.Data;

import java.util.List;

@Data
public class OptProgram {

    private Integer                 offset;      //绝对相位差
    private Integer                 cycle;       //路口理想周期
    private List<List<OptRing>>        rings;

}
