package com.openatc.model.model;

import lombok.Data;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/25 0:07
 * @description：control/pattern
 * @modified By：
 * @version: $
 */
@Data
public class ControlPattern<T> {
    int control;
    int terminal;
    int value;
    int delay;
    int duration;
    T data;
}
