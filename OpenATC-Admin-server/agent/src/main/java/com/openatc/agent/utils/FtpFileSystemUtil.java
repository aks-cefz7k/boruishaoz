package com.openatc.agent.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FtpFileSystemUtil {
    private static Gson gson = new Gson();

    public static FTPClient login(String host, int port, String userName, String password) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(1000 * 15);//设置连接超时时间
            ftpClient.connect(host, port);// 连接FTP服务器
            ftpClient.login(userName, password);// 登陆FTP服务器
            ftpClient.setControlEncoding("UTF-8");// 中文支持
            // 设置文件类型为二进制（如果从FTP下载或上传的文件是压缩文件的时候，不进行该设置可能会导致获取的压缩文件解压失败）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();//开启被动模式，否则文件上传不成功，也不报错
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("连接FTP失败，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功!");
            }
        } catch (Exception e) {
            log.info("登陆FTP失败，请检查FTP相关配置信息是否正确！" + e);
            return null;
        }
        return ftpClient;
    }

    public static boolean store(FTPClient ftpClient, MultipartFile file) {
        try {

            ftpClient.enterLocalPassiveMode();
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            boolean b = ftpClient.changeWorkingDirectory("/usr/config");
            if (b) log.info("check success");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getFileNames(FTPClient ftpClient, String filePath) throws IOException {
        List<String> arFiles = new ArrayList<>();
        if (filePath.startsWith("/") && filePath.endsWith("/")) {
            //更换目录到当前目录
            ftpClient.changeWorkingDirectory(filePath);
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    arFiles.add(filePath + file.getName());
                } else if (file.isDirectory()) {
                    // 是目录直接跳出本次递归
                    continue;
                }
            }
        }
        return arFiles;
    }

    public static JsonObject getFault(FTPClient ftpClient, String fileName){
        InputStream is = null;
        String result = null;
        try {
            ftpClient.enterLocalPassiveMode();
            is = ftpClient.retrieveFileStream(fileName);// 获取ftp上的文件
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//捕获内存
            // 文件读取方式一
            int i = -1;
            byte[] bytes = new byte[1024];
            while ((i = is.read(bytes)) != -1) {
                baos.write(bytes, 0, i);
            }
            result = new String(baos.toByteArray());
            ftpClient.completePendingCommand();
            log.info("FTP文件下载成功！");
        } catch (Exception e) {
            log.error("FTP文件下载失败！" + e);
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                log.error("下载流关闭失败" + e);
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
        return jsonFile;
    }

    public static JsonArray getJsonFile(FTPClient ftpClient, String fileName) {
        InputStream is = null;
        String result = null;
        try {
            ftpClient.enterLocalPassiveMode();
            is = ftpClient.retrieveFileStream(fileName);// 获取ftp上的文件
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//捕获内存
            // 文件读取方式一
            int i = -1;
            byte[] bytes = new byte[1024];
            while ((i = is.read(bytes)) != -1) {
                baos.write(bytes, 0, i);
            }
            result = new String(baos.toByteArray());
            ftpClient.completePendingCommand();
            log.info("FTP文件下载成功！");
        } catch (Exception e) {
            log.error("FTP文件下载失败！" + e);
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                log.error("下载流关闭失败" + e);
                return null;
            }
        }
        JsonObject jsonFile = null;
        JsonArray flowInfo = null;
        try {
            jsonFile = gson.fromJson(result, JsonObject.class);
            flowInfo = jsonFile.get("flowInfo").getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return flowInfo;
    }
}
