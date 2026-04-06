package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/2 22:17
 * @description：Pattern
 * @modified By：
 * @version: $
 */
@Data
public class Pattern {
    int id;
    int offset;
    int cycle;
    int forbiddenstage;
    int screenstage;
    int coordinatestage;
    String desc;
    List<List<Split>> rings;

}
