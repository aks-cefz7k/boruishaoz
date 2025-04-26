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

@Data
public class Device {
    private int id;
    private String name;
    private String agentid;
    private int routeid;
    private int forwardphaseid;
    private int[] forwardphasedirection;
    private int backphaseid;
    private int[] backphasedirection;
    private int distance;
    private int width;
    private int sortid;
    private int patternid;
    private String patterndes;
    private Feature feature;

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
}
