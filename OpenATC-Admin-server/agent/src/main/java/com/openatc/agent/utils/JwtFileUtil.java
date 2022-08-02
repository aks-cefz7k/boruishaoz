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
package com.openatc.agent.utils;

import com.openatc.comm.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFileUtil {

//    public void setTokenFilePath(String tokenFilePath){
//        JwtFileUtil.tokenFilePath = tokenFilePath;
//    }

    // 读取token文件的每一行数据
    public static List<String> initList() throws IOException {

        String tokenFilePath = PropertiesUtil.getStringProperty("agent.tokenfile.path");
        if(tokenFilePath == null)
            return null;

        List<String> tokenlist = new ArrayList<>();


//        String currentpath = System.getProperty("user.dir");
//        String resPath = currentpath + "/" + tokenFilePath;

        FileReader fr = new FileReader(tokenFilePath);
        BufferedReader bf = new BufferedReader(fr);
        String str;

        while ((str = bf.readLine()) != null) {
            tokenlist.add(str);
        }
        bf.close();
        fr.close();


        return tokenlist;
    }
}
