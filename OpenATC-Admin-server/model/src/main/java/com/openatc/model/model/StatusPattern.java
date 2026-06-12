package com.openatc.model.model;

import lombok.Data;

import java.util.List;

@Data
public class StatusPattern {
    private Integer                  mode;
    private String                   name;
    private Integer                  control;
    private Integer                  patternid;
    private Integer                  cycle;
    private Integer                  offset;
    private Integer                  curTime;
    private Integer                  syncTime;

    private List<StatusPatternPhase>       phase;
    private List<List<Integer>>      stages;

    private Integer                  total_stages;
    private Integer                  current_stage;
    private List<Integer>            current_phase;
    private Integer                  next_stage;
    private List<Integer>            next_phase;
    private List<StatusRing>         rings;

}
