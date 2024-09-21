package com.openatc.agent.controller;

import com.openatc.agent.utils.FtpFileSystemUtil;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

@RestController
public class FileController {
    @Autowired
    private DevController devController;

    @PostMapping(value = "/system/update")
    public RESTRetBase uploadFile(@RequestParam(value = "file") MultipartFile file,
                                  @RequestParam(value = "username") String name,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "agentid") String agentid) throws IOException, ParseException {

        //通过agentid获取ip
        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentid);
        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();


        InputStream inputStream = file.getInputStream();
        // 登录信号机
        FTPClient ftpClient = FtpFileSystemUtil.login(ip, 21, name, password);
        if (ftpClient == null) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        } else if (!ftpClient.isConnected()) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
        }
        boolean result = FtpFileSystemUtil.store(ftpClient, file);
        inputStream.close();
        ftpClient.logout();
        return RESTRetUtils.successObj(result);
    }
}
