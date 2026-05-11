package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/3 14:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ring implements Comparable<Ring>{
    private int num;
    private List<Integer> sequence;
    private double y;

    @Override
    public int compareTo(Ring o) {
        if(this.getY() > o.getY()){
            return -1;
        }
        return 1;
    }
}
