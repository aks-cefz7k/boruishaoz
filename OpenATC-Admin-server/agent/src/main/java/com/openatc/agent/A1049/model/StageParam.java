package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class StageParam {
    @JacksonXmlProperty(localName = "CrossID")
    private String crossID;
    @JacksonXmlProperty(localName = "StageNo")
    private Integer stageNo;
    @JacksonXmlProperty(localName = "StageName")
    private String stageName;
    @JacksonXmlProperty(localName = "Attribute")
    private String attribute;
    @JacksonXmlProperty(localName = "Green")
    private Integer green;
    @JacksonXmlProperty(localName = "RedYellow")
    private Integer redYellow;
    @JacksonXmlProperty(localName = "Yellow")
    private Integer yellow;
    @JacksonXmlProperty(localName = "AllRed")
    private Integer allRed;
    @JacksonXmlElementWrapper(localName = "PhaseNoList")
    @JacksonXmlProperty(localName = "PhaseNo")
    private List<Integer> phaseList;
}
