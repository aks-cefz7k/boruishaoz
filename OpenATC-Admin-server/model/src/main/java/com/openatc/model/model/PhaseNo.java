package com.openatc.model.model;

import lombok.Data;

/**
 * @Description 相位序号
 * @Date 2022/1/26 13:15
 * @Created by panleilei
 */
@Data
public class PhaseNo {
    String id;
    public PhaseNo(String id){
        this.id = id;
    }
}
