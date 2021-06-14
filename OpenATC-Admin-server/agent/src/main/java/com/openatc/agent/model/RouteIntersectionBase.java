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

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class RouteIntersectionBase {

    @Column(name = "intersection_id")
    private String intersectionid;

    @Transient
    private Interfeature feature;

    public RouteIntersectionBase(String intersectionid, Interfeature feature) {
        this.intersectionid = intersectionid;
        this.feature = feature;
    }

    public RouteIntersectionBase() {
    }

    public String getIntersectionid() {
        return intersectionid;
    }

    public void setIntersectionid(String intersectionid) {
        this.intersectionid = intersectionid;
    }

    public Interfeature getFeature() {
        return feature;
    }

    public void setFeature(Interfeature feature) {
        this.feature = feature;
    }
}
