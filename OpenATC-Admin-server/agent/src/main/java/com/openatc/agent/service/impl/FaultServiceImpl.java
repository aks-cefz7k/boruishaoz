package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.controller.DevController;
import com.openatc.agent.model.AscsBaseModel;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.utils.DateUtil;
import com.openatc.agent.utils.FtpFileSystemUtil;
import com.openatc.comm.data.MessageData;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class FaultServiceImpl {

    private Logger log = Logger.getLogger(FaultServiceImpl.class.toString());

    @Autowired
    FaultDao faultDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DevController devController;

    @Value("${fault.filepath}")
    private String faultFilePath;

    Gson gson = new Gson();

    @Value("${ftpclient.port}")
    private int port;

    public void processFaultMessage(MessageData msg) {
        JsonObject messageJson = gson.fromJson(gson.toJson(msg), JsonObject.class);
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

    public RESTRetBase getHistoryFault(String agentid, String username, String password) throws ParseException, IOException {
        Map<String, JsonObject> result = new HashMap<>(32);
        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentid);
        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        JsonArray jsonArray = new JsonArray();
        // 登录信号机
        FTPClient ftpClient = FtpFileSystemUtil.login(ip, port, username, password);
        if (ftpClient == null) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        } else if (!ftpClient.isConnected()) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        }
        JsonObject fault = FtpFileSystemUtil.getFault(ftpClient, faultFilePath);
        return RESTRetUtils.successObj(fault);
    }
}
