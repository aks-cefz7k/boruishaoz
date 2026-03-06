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
    private String model; //上报模块
    private String eventType;//时间类型
    private String operator; //故障记录操作者
    private Long operationTime; //操作事件
    private String enumerate;  //0:未处理 1:已忽略 2:已处理

}

@Data
class FaultKey implements Serializable {
    private String agentid;
    private Long m_wFaultID;
}