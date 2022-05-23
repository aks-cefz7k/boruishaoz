//package com.openatc.configserver.controller;
//
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//@Path("/")
//public class FileController {
//
//    @Path("system/update")
//    @POST
////    @Consumes(MediaType.MULTIPART_FORM_DATA)
////    @Produces(MediaType.MULTIPART_FORM_DATA)
//    public String updateSystem(HttpServletRequest request) throws IOException {
////        ServletInputStream inputStream = request.getInputStream();
////        System.out.println(inputStream);
////        DiskFileItemFactory factory = new DiskFileItemFactory();
////        factory.setSizeThreshold(10240);
////        factory.setRepository(factory.getRepository());
////        ServletFileUpload upload = new ServletFileUpload(factory);
////        upload.setSizeMax(10*1024*1024);// 最大上传文件，不超过10M
////        List<FileItem> items;
////        items = upload.parseRequest((RequestContext) request);
////        System.out.println(items);
//        //获取文件集合
//        List<FileItem> items = getUploadInputSteam(request, "utf-8");
//        //取第一个做实验
//        FileItem item = items.get(0);
//
//        //上传的文件名
//        String fileName = item.getName();
//        fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//
//        //数据流获取,获得数据流你可以自由解析 , 保存 等操作
//        InputStream in = item.getInputStream();
//
//        //关闭流
//        in.close();
//
//        //最后一定要删除,item的临时文件
//        item.delete();
//        return "success!";
//    }
//
//    /**
//     * 从 request 中获取上传的文件
//     * @warn 后续需要 删除处理文件上传时
//     *  生成的临时文件 ,使用item.delete();
//     * @param request	http请求
//     * @param encoding  解码方式,传null默认 utf-8
//     * @return 返回的是一个 FileItem 集合
//     */
//    public List<FileItem> getUploadInputSteam(HttpServletRequest request,String encoding) {
//        if (encoding == null || "".equals(encoding)) {
//            encoding = "UTF-8";
//        }
//        List<FileItem> items = new ArrayList<FileItem>();
//        //初始化需要解析文件的几个类
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setHeaderEncoding(encoding);
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return items;
//        }
//        try {
//            //使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合
//            //每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest((RequestContext) request);
//            for (FileItem item : list) {
//                //如果fileitem中封装的是普通输入项的数据
//                if (item.isFormField()) {
////						String name = item.getFieldName();
////						 //解决普通输入项的数据的中文乱码问题
////						String value = item.getString(encoding);
////						System.out.println(name +"="+value);
//                } else {
//                    String fileName = item.getName();
//                    if (fileName == null || fileName.trim().equals("")) {
//                        continue;
//                    }
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，
//                    //如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
////					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//                    items.add(item);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return items;
//        }
//        return items;
//    }
//}
