package com.openatc.agent.model;


import lombok.Data;

import javax.persistence.*;






@Entity
@Data
@Table(name = "overflow")
public class Overflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long  patternid;        //瓶颈控制方案索引
    private String  intersectionid;      //路口id
    private String phaseid;           //相位id
    private Integer[] description;    //描述
    private String updowndirection;   //上下游
    private int controltime;          //时间
    private int level;                //等级
    private Boolean isopen;           //是否开启
}











