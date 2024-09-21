package com.openatc.agent.model;

import com.openatc.agent.utils.JsonbType;
import com.openatc.comm.data.MyGeometry;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Table(name = "vip_route_device", schema = "public")
@Entity
@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
public class VipRouteDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int viprouteid;

    private String agentid;

    private String name;

    @Transient
//    @Column(columnDefinition = "jsonb")
//    @Type(type = "JsonbType")
    private MyGeometry geometry;

    private double[] location;

    private int control;

    private int terminal;

    private String terminalname;

    private int value;

    private int totaltime;
}
