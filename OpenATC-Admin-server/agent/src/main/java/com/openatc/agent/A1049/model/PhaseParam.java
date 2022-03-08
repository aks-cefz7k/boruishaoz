package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class PhaseParam {
    @JacksonXmlProperty(localName = "CrossID")
    private String crossID;
    @JacksonXmlProperty(localName = "PhaseNo")
    private Integer phaseNo;
    @JacksonXmlProperty(localName = "PhaseName")
    private String phaseName;
    @JacksonXmlProperty(localName = "Attribute")
    private String attribute;
    @JacksonXmlElementWrapper(localName = "LaneNoList")
    @JacksonXmlProperty(localName = "LaneNo")
    private List<Integer> laneList;
    @JacksonXmlElementWrapper(localName = "PedDirList")
    @JacksonXmlProperty(localName = "Direction")
    private List<Integer> pedDirList;
}
