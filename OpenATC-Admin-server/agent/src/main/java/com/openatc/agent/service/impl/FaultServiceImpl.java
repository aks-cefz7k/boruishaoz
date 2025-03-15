package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.utils.FtpFileSystemUtil;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.comm.data.MessageData;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.net.ftp.FTPClient;
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
            fault.setAgentid(agentid);
            faultDao.save(fault);
        }
    }


    public RESTRetBase getHistoryFault(String agentid, String username, String password) {
        AscsBaseModel ascsBaseModel = ascsDao.getAscsByID(agentid);
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
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
