package com.openatc.agent.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TStatKey implements Serializable {
    private String agentid;
    private int phaseid;
}
