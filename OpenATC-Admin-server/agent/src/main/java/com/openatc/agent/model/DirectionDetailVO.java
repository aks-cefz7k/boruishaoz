package com.openatc.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDetailVO {
    private Integer id;
    private Integer type;

    public DirectionDetailVO (DirectionDetail detail) {
        this.id = detail.getId();
        this.type = detail.getType();
    }

    public List<DirectionDetailVO> getDirectionDetailVOList (List<DirectionDetail> detailList) {
        List<DirectionDetailVO> voList = new ArrayList<>();
        for (DirectionDetail detail : detailList) {
            DirectionDetailVO vo = new DirectionDetailVO(detail);
            voList.add(vo);
        }
        return voList;
    }
}
