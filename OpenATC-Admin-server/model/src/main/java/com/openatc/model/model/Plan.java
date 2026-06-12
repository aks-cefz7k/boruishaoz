package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author PanLeilei
 * @Date 2021/11/9 14:58
 * @Describetion
 */
@Data
public class Plan {

//    private String index;   //后台调用的数据有该属性，API文档中没有
    private int id;
    private String desc;
    private List<PlanTime> plan;
}
