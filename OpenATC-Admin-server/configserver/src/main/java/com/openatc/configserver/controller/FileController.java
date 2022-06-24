package com.openatc.configserver.controller;

import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
//        FileOutputStream downloadFile = new FileOutputStream("C:\\Users\\jinjunlin\\Documents\\OpenATC-Admin\\open-atc-admin\\OpenATC-Admin-server\\configserver\\"+fileName);

        while ((index = inputStream.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        inputStream.close();
        return RESTRetUtils.successObj();
    }
}