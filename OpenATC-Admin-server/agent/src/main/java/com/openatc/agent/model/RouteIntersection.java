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

@Data
@Table(name = "static_route_intersection", schema = "public")
@Entity
public class RouteIntersection extends RouteIntersectionBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "intersection_id")
//    private String intersectionid;

    private int forwardphaseid;

    private int[] forwardphasedirection;

    private int backphaseid;

    private int[] backphasedirection;

    private int distance;

    private int width;

    private int sortid;

    private int patternid;

    private String patterndes;

    @Column(name="route_id")
    private Long routeid;

    public int[] getForwardphasedirection() {
        return forwardphasedirection.clone();
    }

    public void setForwardphasedirection(int[] forwardphasedirection) {
        this.forwardphasedirection = forwardphasedirection.clone();
    }

    public int[] getBackphasedirection() {
        return backphasedirection.clone();
    }

    public void setBackphasedirection(int[] backphasedirection) {
        this.backphasedirection = backphasedirection.clone();
    }

    //    @Transient
//    private Interfeature feature;

}
