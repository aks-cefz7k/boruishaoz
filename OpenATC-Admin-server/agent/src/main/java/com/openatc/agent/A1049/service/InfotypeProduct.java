package com.openatc.agent.A1049.service;


import com.google.gson.JsonElement;
import com.openatc.agent.A1049.model.A1049Message;
import com.openatc.comm.data.MessageData;
import org.apache.coyote.http2.HpackDecoder;
import org.springframework.stereotype.Component;

@Component
public abstract class InfotypeProduct {
    public abstract A1049Message changeJsonToXml(A1049Message a1049Message, MessageData messageData);

    public abstract JsonElement packData(A1049Message a1049Message);
}
