package com.openatc.agent.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Data;

@Data
public class VipRouteVO {
    private int id;
    private String name;

    private List<VipRouteDeviceVO> devs;

    public VipRouteVO() {
    }

    public VipRouteVO(VipRoute vipRoute, List<VipRouteDeviceVO> devs){
        this.id = vipRoute.getId();
        this.name = vipRoute.getName();
        this.devs = devs;
    }


}
