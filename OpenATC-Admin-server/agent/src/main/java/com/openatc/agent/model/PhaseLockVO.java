package com.openatc.agent.model;

import com.openatc.model.model.MyGeometry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhaseLockVO {

    private int greenflash;

    private int duration;

    private int yellow;

    private int redclear;

    private int mingreen;

    private List<DirectionDetailVO> phases;

    public PhaseLockVO(VipRouteDevice device) {
        this.greenflash = device.getGreenflash();
        this.duration = device.getDuration();
        this.yellow = device.getYellow();
        this.redclear = device.getRedclear();
        this.mingreen = device.getMingreen();
        List<DirectionDetail> detailList = device.getPhases();
        List<DirectionDetailVO> voList = new DirectionDetailVO().getDirectionDetailVOList(detailList);
        this.phases = voList;
    }
}
