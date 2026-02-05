package com.openatc.agent.model;

import com.openatc.comm.data.MyGeometry;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Table(name = "vip_route_device", schema = "public")
@Entity
public class VipRouteDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int viprouteid;

    private String agentid;

    private String name;

    @Transient
    private MyGeometry geometry;

    private double[] location;

    private int control;

    private int terminal;

    private String terminalname;

    private int value;

    private int totaltime;
}
