package com.openatc.agent.shedule;

import com.google.gson.JsonObject;
import com.openatc.agent.controller.MessageController;
import com.openatc.agent.service.AscsDao;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.text.ParseException;
import java.util.List;

@Component
public class OptStatParam {
    @Autowired
    MessageController messageController;
    @Autowired
    AscsDao ascsDao;

    @Scheduled(cron = "0  1  0 * * ?")
    public void getOptStatParam() throws SocketException, ParseException {
        List<String> agentids = ascsDao.getAllAgentids();
        for (String agentid : agentids) {
            MessageData messageData = new MessageData(agentid, "get-request", "status/optstatparam", new JsonObject());
            messageController.postDevsMessage(null, messageData);
        }
    }
}
