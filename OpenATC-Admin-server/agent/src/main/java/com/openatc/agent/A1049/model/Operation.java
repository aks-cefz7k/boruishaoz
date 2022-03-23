package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class Operation {
    @JacksonXmlProperty(localName = "order")
    private String order;
    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "TSCCmd")
    private TSCCmd tscCmd;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DetParam")
    private List<DetParam> detList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "LaneParam")
    private List<LaneParam> laneList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PhaseParam")
    private List<PhaseParam> phaseList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "StageParam")
    private List<StageParam> stageList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PlanParam")
    private List<PlanParam> planList;
}
