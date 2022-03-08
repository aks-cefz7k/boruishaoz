package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DetParam {
    @JacksonXmlProperty(localName = "DetID")
    private String detID;
    @JacksonXmlProperty(localName = "Distance")
    private float distance;
    @JacksonXmlProperty(localName = "CrossID")
    private String crossID;
    @JacksonXmlElementWrapper(localName = "LaneNoList")
    @JacksonXmlProperty(localName = "LaneNo")
    private List<Integer> lanenoList;
}
