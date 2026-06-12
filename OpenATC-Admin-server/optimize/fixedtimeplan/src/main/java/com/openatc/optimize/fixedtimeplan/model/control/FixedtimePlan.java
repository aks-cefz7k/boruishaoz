package com.openatc.optimize.fixedtimeplan.model.control;

import com.openatc.optimize.fixedtimeplan.config.cross.Phase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/3 14:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedtimePlan {
    private long cycle;
    private List<Phase> phase;


}
