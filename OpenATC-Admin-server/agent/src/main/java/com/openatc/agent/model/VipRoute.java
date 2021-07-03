package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Table(name = "vip_route", schema = "public")
@Entity
public class VipRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "viprouteid")
//    @OrderBy("sortid ASC")
    private Set<VipRouteDevice> devs;

    public VipRoute() {
    }

    public VipRoute(int id, String name){
        this.id = id;
        this.name = name;
    }


}
