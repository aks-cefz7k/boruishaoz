package com.openatc.agent.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openatc.agent.controller.DevController;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.FlowService;
import com.openatc.agent.utils.FtpFileSystemUtil;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FlowServiceImpl implements FlowService {

    @Autowired
    AscsDao ascsDao;

    @Value("${ftpclient.port}")
    private int port;

    @Value("${flow.filepath}")
    private String flowFilePath;

    @Override
    public RESTRetBase getHistoryFlow(String agentid, String username, String password) throws ParseException, IOException {
        AscsBaseModel ascsBaseModel = ascsDao.getAscsByID(agentid);
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        // 登录信号机
        FTPClient ftpClient = FtpFileSystemUtil.login(ip, port, username, password);
        if (ftpClient == null) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        } else if (!ftpClient.isConnected()) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        }

        JsonArray jsonFile = FtpFileSystemUtil.getJsonFile(ftpClient, flowFilePath);

        return RESTRetUtils.successObj(jsonFile);
    }
}
