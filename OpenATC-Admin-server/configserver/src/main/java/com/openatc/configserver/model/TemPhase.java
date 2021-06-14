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
package com.openatc.configserver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemPhase extends Phase {
    private int[] direction;
    private int mingreen = 15;
    private int max1 = 30;
    private int max2 = 45;
    private int passage = 0;
    private int pedclear = 12;
    private int yellow;
    private int redclear;
    private int flashgreen;
    private int redyellow = 3;
    private int ring = 1;

    public int[] getDirection() {
        return direction.clone();
    }

    public void setDirection(int[] direction) {
        this.direction = direction.clone();
    }
}
