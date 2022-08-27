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

//        InputStream resourceAsStream = JwtFileUtil.class.getResourceAsStream(tokenFilePath);
//        BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));

        String currentpath = System.getProperty("user.dir");
        String resPath = currentpath + tokenFilePath;

        FileReader fr = new FileReader(resPath);
        BufferedReader br = new BufferedReader(fr);
        String str;

        while ((str = br.readLine()) != null) {
            tokenlist.add(str);
        }
        br.close();
        fr.close();


        return tokenlist;
    }
}
