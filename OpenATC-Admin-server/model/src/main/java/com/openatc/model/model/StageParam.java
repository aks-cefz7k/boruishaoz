package com.openatc.model.model;

import lombok.Data;

import java.util.List;

/**
 * @Classname StageParam
 * @Description 1049阶段参数
 * @Date 2022/1/25 18:13
 * @Created by panleilei
 */
@Data
public class StageParam {
    String CrossID;
    Integer StageNo;
    String StageName;
    Integer Attribute;
    Integer Green;
    Integer RedYellow;
    Integer Yellow;
    Integer AllRed;
    List<PhaseNo> PhaseNoList;
}
