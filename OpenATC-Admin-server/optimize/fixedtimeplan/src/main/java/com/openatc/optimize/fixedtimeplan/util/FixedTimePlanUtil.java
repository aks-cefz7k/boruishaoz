package com.openatc.optimize.fixedtimeplan.util;

import com.openatc.optimize.fixedtimeplan.config.cross.CrossConfig;
import com.openatc.optimize.fixedtimeplan.model.control.Cross;
import com.openatc.optimize.fixedtimeplan.model.control.FixedtimePlan;



import java.util.HashMap;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/24 16:18
 */
public class FixedTimePlanUtil {

    public static  FixedtimePlan calcFixedTimePlan(CrossConfig crossConfig){
        Cross cross = Cross.builder()
                .agentid(crossConfig.getAgentid())
                .crossConfig(crossConfig)
                .phaseMap(new HashMap<>())
                .build();
        cross.updatePhaseMap();
        return cross.calcFixedtimePlan();

    }
}
