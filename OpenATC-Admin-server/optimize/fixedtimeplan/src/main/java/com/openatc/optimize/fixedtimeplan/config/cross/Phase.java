package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/3 14:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    private int id;
    private double flowperhour;
    private double saturation;
    private long duration;
    private List<Integer> concurrent;
    private int mingreen = 15;
    private int maxgreen = 150;
    private boolean adjusted = false;

    public double Y(){
        return flowperhour/saturation;
    }


}
