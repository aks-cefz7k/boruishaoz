/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
@Table(name = "static_route", schema = "public")
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String keyintsid;

    private String direction = "up";

    private String optstrategy = "green";

    private int upspeed = 60;

    private int downspeed = 50;

    private int type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @OrderBy("sortid ASC")
    private Set<RouteIntersection> devs;

    private Long areaid;

    private Long routegroupid;

    @Column(nullable = true)
    private Boolean enable;

    @Transient
    private Boolean flag; //过渡判断路线是否有路线组;

    public Route(Long id, String name, String keyintsid, String direction, String opstrategy, int upspeed, int downspeed, int type, Boolean enable) {
        this.id = id;
        this.name = name;
        this.keyintsid = keyintsid;
        this.direction = direction;
        this.optstrategy = opstrategy;
        this.upspeed = upspeed;
        this.downspeed = downspeed;
        this.type = type;
        this.enable = enable;
    }

    public Route() {
    }
}
