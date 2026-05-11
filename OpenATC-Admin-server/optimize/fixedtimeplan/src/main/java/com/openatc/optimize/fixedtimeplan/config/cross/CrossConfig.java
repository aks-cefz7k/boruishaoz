package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/8/20 14:34
 */
@Data
@NoArgsConstructor
public class CrossConfig {
    private String name;
    private String agentid;
    private int red = 2;
    private int yellow = 3;
    private List<Ring> rings;
    private int loss = 3;
    private List<Barrier> barriers;
    private List<Phase> phases;



}
