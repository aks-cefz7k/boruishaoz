package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/2 22:16
 * @description：Feature
 * @modified By：
 * @version: $
 */
@Data
public class Feature {
    List<Phase> phaseList;
    List<Pattern> patternList;
    List<Plan> planList;
    List<DateParam> dateList;
}
