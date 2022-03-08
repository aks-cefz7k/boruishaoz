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

@Service
@Slf4j
public class XmlMessageProcessorService {

    @Autowired
    private MessageController messageController;

    public A1049Message postDevsMessage(A1049Message a1049Message) {
        String name = a1049Message.getBody().getOperation().getName();
        String jsonInfotype = Infotype.getJsonInfotype(a1049Message.getBody().getOperation().getTscCmd().getObjname());
        InfotypeProduct infotypeProduct = InfotypeFactory.createInfotypeProduct(jsonInfotype);
        try {
            if ("Set".equals(name)) {
                MessageData setRequestData = createSetRequestData(a1049Message);
                RESTRet setRestRet = messageController.postDevsMessage(null, setRequestData);
                if (setRestRet.getData() instanceof DevCommError) return null;
            }
            MessageData getRequestData = createGetRequestData(a1049Message);
            RESTRet getRestRet = messageController.postDevsMessage(null, getRequestData);
            if (getRestRet.getData() instanceof DevCommError) return null;
            a1049Message = infotypeProduct.changeJsonToXml(a1049Message, (MessageData) getRestRet.getData());
        } catch (Exception e) {
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
