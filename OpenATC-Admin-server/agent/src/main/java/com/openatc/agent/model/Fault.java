//package com.openatc.agent.model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Data
//@Table(name = "fault", schema = "public")
//@Entity
//public class Fault {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String agentid;
//
//    private String operation;
//
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="id")
//    private FaultDeque m_FaultDeque;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "route_id")
//    @OrderBy("sortid ASC")
//    private Set<FaultD> data;
//}
