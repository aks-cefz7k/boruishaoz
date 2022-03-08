package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanParam {
    @JacksonXmlProperty(localName = "CrossID")
    private String crossID;
    @JacksonXmlProperty(localName = "PlanNo")
    private Integer planNo;
    @JacksonXmlProperty(localName = "CycleLen")
    private Integer cycleLen;
    @JacksonXmlProperty(localName = "CoorPhaseNo")
    private Integer coorPhaseNo;
    @JacksonXmlProperty(localName = "OffSet")
    private Integer offSet;
    @JacksonXmlElementWrapper(localName = "StageNoList")
    @JacksonXmlProperty(localName = "StageNo")
    private List<Integer> stageList;
}
