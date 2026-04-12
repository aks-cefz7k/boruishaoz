/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;

import java.io.*;
import java.util.Properties;

public class FileUtil {
    public static String getUserFilePath(){
        Properties properties = new Properties();
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("user.filepath");
    }

    public static void writeObject(String filePath, Object object){

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(filePath)));
            objectOutputStream.writeObject(object);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Object readObject(String filePath){
        File file = new File(filePath);
        FileInputStream fs = null;

        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // 找不到文件
            return null;
        }

        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(fs);
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * @return
     * @Date 2021/9/3 16:21
     * 读取指定文件
     */
    public static RESTRetBase readFile(String filename) {
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
