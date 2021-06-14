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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFileUtil {

    private static String tokenFilePath;

    @Value("${agent.tokenfile.path}")
    public void setTokenFilePath(String tokenFilePath){
        JwtFileUtil.tokenFilePath = tokenFilePath;
    }

    public static List<String> initList() throws IOException {
        List<String> tokenlist = new ArrayList<>();

        Resource resource = new ClassPathResource(tokenFilePath);
        InputStream inputStream = resource.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String line = ""; // 每一行的内容
        while ((line = br.readLine()) != null) {
            tokenlist.add(line.trim());
        }
        reader.close();
        br.close();
        return tokenlist;
    }
}
