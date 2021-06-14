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
public class Ring {
    private int id;
    private String name;
    private int value;
    private int minSplit;
    private int mode;
    public Ring(int id, String name, int value,int  minSplit,int mode) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.minSplit=minSplit;
        this.mode=mode;
    }

    public Ring() {
    }
}
