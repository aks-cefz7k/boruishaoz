package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author PanLeilei
 * @Date 2021/11/9 17:31
 * @Describetion  DateParam
 */
@Data
public class DateParam {
    private Integer id;
    private String desc;
    private Integer plan;
    private List<Integer> month;
    private List<Integer> day;
    private List<Integer> date;

}
