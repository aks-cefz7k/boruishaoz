package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @Classname StageForStageMode
 * @Description 阶段配置模式中的阶段参数
 * @Date 2022/3/22 11:32
 * @Created by panleilei
 */
@Data
public class StageForStageMode {
    private int key;
    private int green;  // 阶段绿灯
    private int yellow; // 阶段黄灯
    private int red;    // 阶段红灯
    private int split;
    private int stageSplit; // 阶段绿性比
    private List<Integer> phases;   // 阶段配置模式下该阶段包含的相位
    private List<Integer> stages;   // 环模式下该阶段包含的相位
}
