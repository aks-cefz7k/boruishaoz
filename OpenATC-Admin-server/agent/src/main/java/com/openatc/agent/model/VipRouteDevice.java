package com.openatc.agent.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openatc.agent.utils.JsonbType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.boot.jackson.JsonObjectSerializer;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbType")
    private Map<String,Object> geometry = new HashMap<>();

    private double[] location;

    private int control;

    private int terminal;

    private String terminalname;

    private int value;

    private int totaltime;
}
