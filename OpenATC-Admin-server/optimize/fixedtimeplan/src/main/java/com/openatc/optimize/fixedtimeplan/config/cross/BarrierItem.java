package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/12/6 15:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarrierItem {
    private int ring;
    private List<Integer> data;
    private boolean adjusted = false;
    private int duration;
}
