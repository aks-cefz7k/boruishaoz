package com.openatc.agent.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.openatc.model.model.MyGeometry;
import java.util.List;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "vip_route_device")
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "viprouteid")
    @OrderBy("detailId ASC")
    private List<DirectionDetail> directions;
}
