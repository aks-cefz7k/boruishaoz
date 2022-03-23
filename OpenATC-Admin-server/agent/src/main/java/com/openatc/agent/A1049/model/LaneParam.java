package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class LaneParam {
    @JacksonXmlProperty(localName = "CrossID")
    private String crossID;
    @JacksonXmlProperty(localName = "LaneNo")
    private Integer laneNo;
    @JacksonXmlProperty(localName = "Direction")
    private String direction;
    @JacksonXmlProperty(localName = "Attribute")
    private String attribute;
    @JacksonXmlProperty(localName = "Movement")
    private String movement;
    @JacksonXmlProperty(localName = "Feature")
    private String feature;
}
