package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.FaultDao;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class FaultServiceImpl {

    private Logger log = Logger.getLogger(FaultServiceImpl.class.toString());

    @Autowired
    FaultDao faultDao;

    @Autowired(required = false)
    AscsDao ascsDao;

    @Value("${fault.filepath}")
    private String faultFilePath;

    @Value("${ftpclient.port}")
    private int port;

    Gson gson = new Gson();


    public void processFaultMessage(MessageData msg) {
        String agentid = msg.getAgentid();
        Fault[] m_faultDeque = gson.fromJson(msg.getData().getAsJsonObject().getAsJsonArray("m_FaultDeque"), Fault[].class);

        for (Fault fault : m_faultDeque) {
            fault.setEnumerate("0");
            fault.setAgentid(agentid);
            faultDao.save(fault);
        }
    }
}
