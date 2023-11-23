package com.openatc.configserver.controller;

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


@Path("/")
@Singleton
public class FileController {


    @Path("system/update")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RESTRetBase verUpload(@FormDataParam("file") InputStream inputStream,
                                 @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {

        String fileName = new String(fileMetaData.getFileName().getBytes("iso8859-1"),"utf-8");
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream("/usr/config/"+fileName);
        //FileOutputStream downloadFile = new FileOutputStream("C:\\gitProject\\openatc-back-dev\\sysconfig\\"+fileName);

        while ((index = inputStream.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        inputStream.close();
        return RESTRetUtils.successObj();
    }
}