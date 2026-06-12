package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/25 20:53
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class Split {
    int id;
    int value;
    int mode;
    int delaystart;
    int advanceend;
    List<Integer> options;

    public Split(int id, int value, int mode) {
        this.id = id;
        this.value = value;
        this.mode = mode;

    }
}
