package com.openatc.configserver.controller;

import com.google.gson.JsonObject;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.openatc.comm.common.AgentInfoType.System_Update;
import static com.openatc.comm.common.CommunicationType.OPERATOER_TYPE_SET_REQUEST;
import static com.openatc.configserver.controller.DeviceController.ascsBaseModel;


@Path("/")
@Singleton
public class FileController {


    @Path("system/update")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RESTRetBase verUpload(@FormDataParam("file") InputStream inputStream,
                                 @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {

//        String fileName = new String(fileMetaData.getFileName().getBytes("iso8859-1"),"utf-8");
        String fileName = fileMetaData.getFileName();
        int index;
        byte[] bytes = new byte[1024];
//        FileOutputStream downloadFile = new FileOutputStream("/usr/"+fileName);
        FileOutputStream downloadFile = new FileOutputStream("./"+fileName);

        while ((index = inputStream.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        inputStream.close();
        // 通知信号机更新系统
        MessageData md = new MessageData();
        md.setAgentid(ascsBaseModel.getAgentid());
        md.setOperation(OPERATOER_TYPE_SET_REQUEST);
        md.setInfotype(System_Update.toString());

        JsonObject data = new JsonObject();
        data.addProperty("filename", fileName);
        md.setData(data);

        return new CommClient().devMessage(md,ascsBaseModel);
    }
}