package com.openatc.configserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.logging.Logger;

@Path("/")
public class FaultController {
    Gson gson = new Gson();
    Logger log = Logger.getLogger(FaultController.class.toString());

    @Path("fault/history/ftp")
    @POST
//    @Consumes(MediaType.APPLICATION_JSON) // 声明传入参数是json格式
    @Produces(MediaType.APPLICATION_JSON)
    public RESTRetBase getFault() {
//        File file = new File("/usr/log/FAULT.json");
        File file = new File("C:\\Users\\jinjunlin\\Desktop\\FAULT.json");

        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // 找不到文件
            log.info("file not found..");
            return null;
        }
        String result = null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();//捕获内存
            // 文件读取方式一
            int i = -1;
            byte[] bytes = new byte[1024];
            while ((i = fs.read(bytes)) != -1) {
                baos.write(bytes, 0, i);
            }
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            log.info(e.toString());
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                return null;
            }
        }
        JsonObject jsonFile = null;
        try {
            jsonFile = gson.fromJson(result, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return RESTRetUtils.successObj(jsonFile);
    }
}
