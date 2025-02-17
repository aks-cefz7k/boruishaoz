package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "fault")
@Entity
@IdClass(FaultKey.class)
public class Fault {
    @Id
    private String agentid;
    @Id
    private Long m_wFaultID;
    private Integer m_byFaultBoardType;
    private Long m_unFaultOccurTime;
    private Long m_unFaultRenewTime;
    private Long m_wFaultType;
    private Integer m_wSubFaultType;
    private int[] m_byFaultDescValue;
    private Integer m_byFaultLevel;


}

@Data
class FaultKey implements Serializable  {
    private String agentid;
    private Long m_wFaultID;
}