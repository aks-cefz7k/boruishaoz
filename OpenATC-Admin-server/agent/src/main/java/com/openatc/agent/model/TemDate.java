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
public class TemDate {
    private int id;
    private String desc;
    private int[] month;
    private int[] day;
    private int plan;

    public int[] getMonth() {
        return month.clone();
    }

    public void setMonth(int[] month) {
        this.month = month.clone();
    }

    public int[] getDay() {
        return day.clone();
    }

    public void setDay(int[] day) {
        this.day = day.clone();
    }
}
