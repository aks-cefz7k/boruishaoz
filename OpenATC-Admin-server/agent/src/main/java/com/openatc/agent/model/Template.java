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
public class Template {
    private TemPhase[] phaseList;
    private TemPattern[] patternList;
    private TemPlan[] planList;
    private TemDate[] dateList;

    public TemPhase[] getPhaseList() {
        return phaseList.clone();
    }

    public void setPhaseList(TemPhase[] phaseList) {
        this.phaseList = phaseList.clone();
    }

    public TemPattern[] getPatternList() {
        return patternList.clone();
    }

    public void setPatternList(TemPattern[] patternList) {
        this.patternList = patternList.clone();
    }

    public TemPlan[] getPlanList() {
        return planList.clone();
    }

    public void setPlanList(TemPlan[] planList) {
        this.planList = planList.clone();
    }

    public TemDate[] getDateList() {
        return dateList.clone();
    }

    public void setDateList(TemDate[] dateList) {
        this.dateList = dateList.clone();
    }
}
