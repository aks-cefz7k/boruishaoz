package com.openatc.model.model;

import lombok.Data;

/**
 * @author PanLeilei
 * @Date 2021/11/9 16:23
 * @Describetion
 */
@Data
public class PlanTime {
    private Integer id;
    private Integer startHour = 0;
    private Integer startMinute = 0;
    private Integer pattern;
    private Integer control;
}
