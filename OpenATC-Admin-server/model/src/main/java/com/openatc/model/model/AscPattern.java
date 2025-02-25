package com.openatc.model.model;


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
    private int                     curTime;
    private int                     syncTime;
    private int                     total_stages;
    private List<List<Integer>>     stages;

    private List<AscPhase>             phase;
    private List<OptRings>             rings;

}





