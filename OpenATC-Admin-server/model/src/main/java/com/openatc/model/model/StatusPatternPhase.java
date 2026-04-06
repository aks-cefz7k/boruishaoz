package com.openatc.model.model;


import lombok.Data;

import java.util.List;

@Data
public class StatusPatternPhase {
    private Integer             id;
    private List<Integer>       conphase;
    private Integer             split;
    private Integer             type;
    private Integer             countdown;
    private Integer             pedtype;
    private Integer             pedcountdown;
}
