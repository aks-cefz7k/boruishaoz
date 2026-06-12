package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;





@Entity
@Table(name = "overflow_detector")
@Data
public class OverflowDetector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String overflowDetectorId;
    private String type;                                            //类型
    private String status;                                          //状态
    private String description;                                     //描述
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patternid")
    private Set<Overflow> overflows;
}




