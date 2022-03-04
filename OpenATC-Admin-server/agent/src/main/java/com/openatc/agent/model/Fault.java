package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "fault", schema = "public",
        indexes = {@Index(columnList = "m_unFaultRenewTime")})
@Entity
public class Fault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agentid;
    private Long m_wFaultID;
    private Integer m_byFaultBoardType;
    private Long m_unFaultOccurTime;
    private Long m_unFaultRenewTime;
    private Long m_wFaultType;
    private Integer m_wSubFaultType;
    private int[] m_byFaultDescValue;
    private Integer m_byFaultLevel;

    public Fault setId(Long id) {
        this.id = id;
        return this;
    }
}
