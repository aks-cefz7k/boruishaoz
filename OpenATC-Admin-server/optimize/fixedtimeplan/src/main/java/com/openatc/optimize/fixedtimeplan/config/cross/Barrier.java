package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/3 15:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Barrier {
    private int num;
    private List<List<Integer>> phases;
    private double y;
    private double duration;

    public void assignDrationToPhases(){

    }

}
