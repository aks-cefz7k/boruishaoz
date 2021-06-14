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
package com.openatc.configserver.utils;

import com.openatc.configserver.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.Base64;
import java.util.Properties;

public class SerializeUtil {
    public static String getUserFilePath(){
        Properties properties = new Properties();
        InputStream in = SerializeUtil.class.getResourceAsStream("/config/application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("user.filepath");
    }

    public static void writeObject(Object object){

        String filePath = getUserFilePath();

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

    public static Object readObject(){
        String filePath = getUserFilePath();
        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)));
            try {
                object = objectInputStream.readObject();
                objectInputStream.close();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

//    public static void main(String[] args) {
//        User user1 = new User();
//        user1.setUser_name("admin");
//        String dbPassword = Base64.getEncoder().encodeToString(DigestUtils.md5("admin" + "123456"));
//        user1.setPassword(dbPassword);
//        SerializeUtil.writeObject("user",user1);
//
//
//        User user = (User) SerializeUtil.readObject("user");
//        System.out.println(user.getPassword());
//        System.out.println(user.getUser_name());
//    }
}
