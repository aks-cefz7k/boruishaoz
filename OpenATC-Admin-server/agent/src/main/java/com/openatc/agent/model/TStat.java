package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Table(name = "t_stat")
@Entity
@IdClass(TStatKey.class)
public class TStat {
    @Id
    private String agentid;
    @Id
    private int phaseid;
    private double maxflow;
    private double maxoccupancy;
    private double maxqueue;
    private double maxspeed;
    private String stattime;
}
