//package com.openatc.configserver.controller;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.openatc.comm.data.MessageData;
//import com.openatc.configserver.model.AscsBaseModel;
//import com.openatc.core.common.IErrorEnumImplOuter;
//import com.openatc.core.model.DevCommError;
//import com.openatc.core.model.RESTRet;
//import com.openatc.core.model.RESTRetBase;
//import com.openatc.core.util.RESTRetUtils;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Path("/")
//public class FlowController {
//    DeviceController deviceController = new DeviceController();
//
//    @Path("flow/history")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
//    @Produces(MediaType.APPLICATION_JSON)
//    public RESTRetBase getHistoryFlow(JsonObject jsonObject) throws IOException, ParseException {
//        // 1 获取信号机的用户名和密码
//        String username = jsonObject.get("username").getAsString();
//        String password = jsonObject.get("password").getAsString();
//
//        // 2 包装信号机的messageData
//        String agentid = jsonObject.get("agentid").getAsString();
//        JsonObject data = new JsonObject();
//        data.addProperty("udiskset",1);
//        data.addProperty("gainstatus",1);
//        MessageData messageData = new MessageData(agentid,"set-request","status/volumelog", data);
//        RESTRet restRet = deviceController.postDevsMessage(messageData);
//        if (restRet.getData() instanceof DevCommError) return restRet;
//        RESTRetBase historyFlow = flowService.getHistoryFlow(agentid, username, password);
//        return historyFlow;
//    }
//
//    public RESTRetBase getHistoryFlow(String agentid, String username, String password) throws ParseException, IOException {
//        Map<String, JsonObject> result = new HashMap<>(32);
//        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentid);
//        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();
//        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
//        JsonArray jsonArray = new JsonArray();
//        // 登录信号机
//        FTPClient ftpClient = FtpFileSystemUtil.login(ip, port, username, password);
//        if (ftpClient == null) {
//            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
//        } else if (!ftpClient.isConnected()) {
//            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_7001);
//        }
//
//        List<String> fileNames = FtpFileSystemUtil.getFileNames(ftpClient, flowFilePath);
//        for (String fileName : fileNames) {
//            JsonArray jsonFile = FtpFileSystemUtil.getJsonFile(ftpClient, fileName);
//            if (jsonFile == null) continue;
//            jsonArray.addAll(jsonFile);
//        }
//
//        return RESTRetUtils.successObj(jsonArray);
//    }
//}
