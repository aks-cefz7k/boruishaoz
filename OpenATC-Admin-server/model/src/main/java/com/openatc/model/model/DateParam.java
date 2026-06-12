package com.openatc.model.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author PanLeilei
 * @Date 2021/11/9 17:31
 * @Describetion  openATC日期类
 */
@Data
public class DateParam {
    private Integer id;
    private String desc;
    private Integer plan;
    private List<Integer> month;
    private List<Integer> day;
    private Date date;

}
