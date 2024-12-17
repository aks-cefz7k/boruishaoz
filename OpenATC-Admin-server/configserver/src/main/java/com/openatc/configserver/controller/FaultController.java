package com.openatc.configserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.comm.common.PropertiesUtil;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.logging.Logger;

@Path("/")
public class FaultController {
    Gson gson = new Gson();
    Logger log = Logger.getLogger(FaultController.class.toString());


    @Path("fault/history/ftp")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRetBase getFault() {
        //获取配置文件中的文件路径
        File file = new File(PropertiesUtil.getStringProperty("flow.filepath"));
        FileInputStream fs;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.info("file not found..");
            return RESTRetUtils.successObj(IErrorEnumImplOuter.E_1000);
        }
        String result = null;
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
            log.warning(e.getMessage());
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                log.warning(e.getMessage());
                return null;
            }
        }
        JsonObject jsonFile;
        try {
            jsonFile = gson.fromJson(result, JsonObject.class);
        } catch (Exception e) {
            log.warning("文件读取结果转换Json失败:" + e.getMessage());
            return null;
        }
        return RESTRetUtils.successObj(jsonFile);
    }
}
