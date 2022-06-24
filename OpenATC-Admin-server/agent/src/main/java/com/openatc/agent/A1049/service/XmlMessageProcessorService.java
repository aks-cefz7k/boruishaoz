package com.openatc.agent.A1049.service;

import com.openatc.agent.A1049.model.A1049Message;
import com.openatc.agent.A1049.model.Infotype;
import com.openatc.agent.controller.MessageController;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.text.ParseException;

@Service
@Slf4j
public class XmlMessageProcessorService {

    @Autowired
    private MessageController messageController;

    public A1049Message postDevsMessage(A1049Message a1049Message) {
        // 查询是哪一种请求, name取值Get、Set
        String name = a1049Message.getBody().getOperation().getName();
        // 查看是XML中定义的哪种消息类型, 如DetParam
        String jsonInfotype = Infotype.getJsonInfotype(a1049Message.getBody().getOperation().getTscCmd().getObjname());
        // 获取该消息类型对应的产品类
        InfotypeProduct infotypeProduct = InfotypeFactory.createInfotypeProduct(jsonInfotype);
        // 根据name的取值决定创建哪种请求
        MessageData messageData = null;
        if ("Set".equals(name)) {
            messageData = createSetRequestData(a1049Message);
        } else if ("Get".equals(name)) {
            messageData = createGetRequestData(a1049Message);
        }
        // 发送请求
        try {
            RESTRet restRet = messageController.postDevsMessage(null, messageData);
            if (restRet.getData() instanceof DevCommError) return null;
            //将信号机返回的json消息转换为xml
            a1049Message = infotypeProduct.changeJsonToXml(a1049Message, (MessageData) restRet.getData());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a1049Message;
    }

    public MessageData createGetRequestData(A1049Message a1049Message) {
        String objname = a1049Message.getBody().getOperation().getTscCmd().getObjname();
        String agentid = a1049Message.getBody().getOperation().getTscCmd().getId();
        String jsonInfotype = Infotype.getJsonInfotype(objname);
        MessageData messageData = new MessageData(agentid, "get-request", jsonInfotype);
        return messageData;
    }

    public MessageData createSetRequestData(A1049Message a1049Message) {
        String objname = a1049Message.getBody().getOperation().getTscCmd().getObjname();
        String agentid = a1049Message.getBody().getOperation().getTscCmd().getId();
        String jsonInfotype = Infotype.getJsonInfotype(objname);
        InfotypeProduct infotypeProduct = InfotypeFactory.createInfotypeProduct(jsonInfotype);
        MessageData requestData = new MessageData(agentid, "set-request", jsonInfotype);
        requestData.setData(infotypeProduct.packData(a1049Message));
        return requestData;
    }
}
