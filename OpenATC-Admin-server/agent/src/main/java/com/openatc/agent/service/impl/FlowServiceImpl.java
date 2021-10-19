package com.openatc.agent.service.impl;

import com.google.gson.JsonObject;
import com.openatc.agent.controller.DevController;
import com.openatc.agent.model.AscsBaseModel;
import com.openatc.agent.service.FlowService;
import com.openatc.agent.utils.FtpFileSystemUtil;
import com.openatc.core.model.RESTRet;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlowServiceImpl implements FlowService {
    @Autowired(required = false)
    protected DevController devController;

    @Value("${ftpclient.port}")
    private int port;

    @Value("${flow.filepath}")
    private String flowFilePath;

    @Override
    public Map<String, JsonObject> getHistoryFlow(String agentid, String username, String password) throws ParseException, IOException {
        Map<String, JsonObject> result = new HashMap<>(32);
        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentid);
        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();
        if (ascsBaseModel == null) return null;
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();

        FTPClient ftpClient = FtpFileSystemUtil.login(ip, port, username, password);
        List<String> fileNames = FtpFileSystemUtil.getFileNames(ftpClient, flowFilePath);
        for (String fileName : fileNames) {
            JsonObject jsonFile = FtpFileSystemUtil.getJsonFile(ftpClient, fileName);
            result.put(fileName.replace(flowFilePath, ""), jsonFile);
        }
        return result;
    }
}
