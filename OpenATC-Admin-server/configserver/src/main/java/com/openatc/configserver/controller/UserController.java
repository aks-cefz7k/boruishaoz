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
package com.openatc.configserver.controller;

import com.google.gson.JsonObject;
import com.openatc.configserver.model.User;
import com.openatc.configserver.utils.SerializeUtil;
import com.openatc.configserver.utils.TokenUtil;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

import static com.openatc.core.common.IErrorEnumImplOuter.E_3011;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private static final String TOKEN = "token";
    private static final String EXPIRE = "expire";
    private static User user= (User)SerializeUtil.readObject();

    /**
     * 登录
     * @param loginUser
     * @return
     */
    @Path("auth/login")
    @POST
    public RESTRetBase login(JsonObject loginUser) {

        // 请求头中的用户名和密码 该密码是数据库中的密码和时间戳加密后得到的加密密码
        String userName = loginUser.get("user_name").getAsString();
        String password = loginUser.get("password").getAsString();
        Long timestamp = loginUser.get("timestamp").getAsLong();

        // 判断用户信息
        if(user != null){
            String secretPassword = Base64.getEncoder().encodeToString(DigestUtils.md5(user.getPassword() + timestamp));
            if (!password.equals(secretPassword))
                return RESTRetUtils.errorObj(E_3011);
            System.out.println("user is not null");
        }else{
            System.out.println("user is null");
        }


        // 生成token返回给前端
        String token = TokenUtil.generateToken(userName, timestamp);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put(TOKEN, token);
        tokenMap.put(EXPIRE, false);

        return RESTRetUtils.successObj(tokenMap);
        //return RESTRetUtils.successObj();
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @Path("/auth/info")
    @GET
    public RESTRetBase info() {
        Map<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        list.add("超级管理员");
        map.put("roleNames", list);
        if(user != null){
            map.put("user_name",user.getUser_name());
            map.put("password",user.getPassword());
        }


        return RESTRetUtils.successObj(map);
    }

    /**
     * 登出
     * @return
     */
    @Path("auth/logout")
    @GET
    public RESTRetBase logout() {
        return RESTRetUtils.successObj();
    }

    /**
     * 修改用户信息
     * @param userName
     * @param user
     * @return
     */
    @Path("auth/user/{userName}")
    @PUT
    public RESTRetBase updateUserInfo(@PathParam("userName") String userName, User user) {
        SerializeUtil.writeObject(user);
        return RESTRetUtils.successObj(user);
    }



}
