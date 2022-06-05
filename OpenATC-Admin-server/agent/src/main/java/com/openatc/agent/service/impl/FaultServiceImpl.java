package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.utils.DateUtil;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.logging.Logger;

@Service
public class FaultServiceImpl {

    private Logger log = Logger.getLogger(FaultServiceImpl.class.toString());

    @Autowired
    FaultDao faultDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    Gson gson = new Gson();

    public void processFaultMessage(MessageData msg) {
        JsonObject messageJson = gson.fromJson(msg.toString(), JsonObject.class);
        String agentid = messageJson.get("agentid").getAsString();
        JsonArray m_faultDeque = messageJson.getAsJsonObject("data").getAsJsonArray("m_FaultDeque");

        for (JsonElement faultJson : m_faultDeque) {
            Fault fault = gson.fromJson(faultJson.toString(), Fault.class);

            fault.setAgentid(agentid);
            Long id = faultDao.selectByAgentidAndMwFaultID(agentid, fault.getM_wFaultID());
            Fault dbFault = (id == null) ? faultDao.save(fault) : faultDao.save(fault.setId(id));
            log.info(gson.toJson(transformFault(dbFault)));
            stringRedisTemplate.convertAndSend("asc:event/faultdata", gson.toJson(transformFault(dbFault)));
        }
    }

    private JsonObject transformFault(Fault fault) {
        JsonObject jsonObject = gson.fromJson(gson.toJson(fault), JsonObject.class);
        if (fault.getM_unFaultOccurTime() != 0) {
            jsonObject.addProperty("m_unFaultOccurTime", DateUtil.longToString(fault.getM_unFaultOccurTime() * 1000));
        } else {
            jsonObject.addProperty("m_unFaultOccurTime", 0);
        }
        if (fault.getM_unFaultRenewTime() != 0) {
            jsonObject.addProperty("m_unFaultRenewTime", DateUtil.longToString(fault.getM_unFaultRenewTime() * 1000));
        } else {
            jsonObject.addProperty("m_unFaultRenewTime", 0);
        }
        JsonObject result = new JsonObject();
        result.add("data", jsonObject);
        result.addProperty("infotype", "event/faultdata");
        result.addProperty("agentid", fault.getAgentid());
        return result;
    }
}
