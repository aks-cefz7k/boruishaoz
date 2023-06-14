package com.opentc.overflowopt.model;


import lombok.Data;

import java.util.List;

@Data
public class AscPattern {
    //private String                  agentid;
    private int                     mode;
    private int                     control;
    private int                     patternid;
    private String                  name;
    private int                     cycle;
    private int                     offset;
    private int                     curtime;
    private int                     synctime;

    private List<Phase>             phase;
    private List<Rings>             rings;

    private List<List<Integer>>     stages;
}





