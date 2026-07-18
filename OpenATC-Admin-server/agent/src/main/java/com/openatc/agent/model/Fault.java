package com.openatc.agent.model;

import javax.naming.Name;
import javax.persistence.Column;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
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

    @Column(name = "m_un_fault_occur_time")
    private Long mUnFaultOccurTime;

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
    private String deleteFlag = "0";  //0:未删除 1:已删除

}

@Data
class FaultKey implements Serializable {
    private String agentid;
    private Long m_wFaultID;
}