package com.openatc.agent.A1049.service;


import com.google.gson.JsonElement;
import com.openatc.agent.A1049.model.A1049Message;
import com.openatc.comm.data.MessageData;
import org.apache.coyote.http2.HpackDecoder;
import org.springframework.stereotype.Component;

@Component
public abstract class InfotypeProduct {

    /**
     * 将Openatc应答的json转会为xml消息
     * @param a1049Message 返回给客户端的xml消息
     * @param messageData 信号机的应答消息
     * @return
     */
    public abstract A1049Message changeJsonToXml(A1049Message a1049Message, MessageData messageData);

    /**
     * 从xml消息中提取出MessageData中的data消息
     * @param a1049Message 客户端发送的xml消息
     * @return
     */
    public abstract JsonElement packData(A1049Message a1049Message);
}
