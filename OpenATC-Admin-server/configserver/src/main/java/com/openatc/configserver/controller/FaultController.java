package com.openatc.configserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;

@Path("/")
public class FaultController {

    /**
     * @return
     * @throws
     * @Date 2021/9/3 11:27
     * @deprecated 获取历史流量文件
     */
    @Path("flow/history")
    @GET
    public RESTRetBase getFlowHistory(JsonObject jsonObject) {
        return readFile("/usr/log/TRAFFICFLOW.json");
    }

    /**
     * @return
     * @throws
     * @Date 2021/9/3 11:27
     * @deprecated 获取历史故障文件
     */
    @Path("fault/history")
    @GET
    public RESTRetBase getFaultHistory(JsonObject jsonObject) {
        return readFile("/usr/log/FAULT.json");
    }


    /**
     * @return
     * @throws
     * @Date 2021/9/3 11:27
     * @deprecated 获取历史操作记录文件
     */
    @Path("operation/history")
    @GET
    public RESTRetBase getOperationHistory(JsonObject jsonObject) {
        return readFile("/usr/log/OPERATIONRECORD.json");
    }

    /**
     * @return
     * @Date 2021/9/3 16:21
     * @deprecated 读取指定文件
     */
    public RESTRetBase readFile(String filename) {
        File file = new File(filename);
        FileInputStream fs;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2009);
        }
        String result;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();//捕获内存
            //文件读取
            int i;
            byte[] bytes = new byte[1024];
            while ((i = fs.read(bytes)) != -1) {
                bos.write(bytes, 0, i);
            }
            result = new String(bos.toByteArray());
        } catch (Exception e) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2008);
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2006);
            }
        }
        JsonObject jsonFile;
        try {
            Gson gson = new Gson();
            jsonFile = gson.fromJson(result, JsonObject.class);
        } catch (Exception e) {
            return RESTRetUtils.successObj(IErrorEnumImplOuter.E_2007);
        }
        return RESTRetUtils.successObj(jsonFile);
    }
}
