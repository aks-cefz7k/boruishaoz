package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Body {
    @JacksonXmlProperty(localName = "Operation")
    private Operation operation;
}
