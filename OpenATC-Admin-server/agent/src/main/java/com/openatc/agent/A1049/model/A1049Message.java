package com.openatc.agent.A1049.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Message")
public class A1049Message {
    @JacksonXmlProperty(localName = "Version")
    private String version;
    @JacksonXmlProperty(localName = "Token")
    private String token;
    @JacksonXmlProperty(localName = "From")
    private String from;
    @JacksonXmlProperty(localName = "To")
    private String to;
    @JacksonXmlProperty(localName = "Type")
    private String type;
    @JacksonXmlProperty(localName = "Seq")
    private String seq;
    @JacksonXmlProperty(localName = "Body")
    private Body body;
}
