package com.openatc.agent.model;


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

    private List<AscPhase>             phase;
    private List<OptRings>             rings;

    private List<List<Integer>>     stages;
}





