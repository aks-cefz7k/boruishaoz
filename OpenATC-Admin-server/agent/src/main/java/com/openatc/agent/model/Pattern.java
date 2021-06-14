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
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class Pattern {
    private int id;
    private String desc;
    private Integer offset;
    private int cycle;
    private List<Ring>[] rings;

    public List<Ring>[] getRings() {
        return rings.clone();
    }

    public void setRings(List<Ring>[] rings) {
        this.rings = rings.clone();
    }
}
