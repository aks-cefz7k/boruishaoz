package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class TSCCmd {
    @JacksonXmlProperty(localName = "ObjName")
    private String objname;
    @JacksonXmlProperty(localName = "ID")
    private String id;
    @JacksonXmlProperty(localName = "No")
    private String no;
}
