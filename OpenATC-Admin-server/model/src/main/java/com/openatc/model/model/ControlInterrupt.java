package com.openatc.model.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/25 20:48
 * @description：control/interrupt
 * @modified By：
 * @version: $
 */
@Data
public class ControlInterrupt {
    int offset;
    int cycle;
    List<List<Split>> rings;

    public void addRing(List<Split> splitList){
        if(rings == null){
            rings = new ArrayList<>();
        }
        rings.add(splitList);
    }
}
