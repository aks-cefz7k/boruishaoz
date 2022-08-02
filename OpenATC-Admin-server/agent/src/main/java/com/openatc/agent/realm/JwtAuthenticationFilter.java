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
package com.openatc.agent.realm;

import com.google.gson.JsonObject;
import com.openatc.agent.AgentApplication;
import com.openatc.core.common.Constants;
import com.openatc.core.common.IErrorEnumImplOuter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import static com.openatc.core.common.IErrorEnumImplOuter.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends AuthenticatingFilter {

    private static final String TOKEN = "token";
    private static final String AUTH = "Authorization";
    private Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.toString());

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        WebUtils.toHttp(response);
        // 先从Header里面获取
        String token = httpRequest.getHeader(AUTH);
        String remoteIP = getRemortIP(httpRequest);
        if (token == null || token.length() == 0) {
            // 获取不到再从Parameter中拿
            token = httpRequest.getParameter(TOKEN);
            // 还是获取不到再从Cookie中拿
            if (token == null || token.length() == 0) {
                return JwtToken.builder().ip(remoteIP).build();
            }
        }

//        boolean flag = tokenUtil.validateToken(token);
//        if(!flag){
//            //从token中拿用户名
//            String username = tokenUtil.getUsernameFromToken(token);
//            httpResponse.setHeader("token",tokenUtil.generateToken(username, System.currentTimeMillis()));
//        }
        return JwtToken.builder()
                .token(token)
                .ip(remoteIP)
                .build();
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {

        //读取本地文件， 若token和本地文件的token也相同，则放行
        String checktoken = ((JwtToken) token).getToken();

        if (AgentApplication.tokenlist != null) {
            for (String s : AgentApplication.tokenlist) {
                if (s.equals(checktoken)) return true;
            }
        }
//        //调试模式直接返回true
//        if (!AgentApplication.shiroOpen) return true;

        HttpServletResponse servletResponse = (HttpServletResponse) response;

        JsonObject jsonObject = null;

        if (ae.getMessage().equals("Account has been deactivated!")) {
            jsonObject = errorToJson(E_3013);
        } else if (ae.getMessage().equals("Token is expired!")) {
            jsonObject = errorToJson(E_3014);
        } else if (ae.getMessage().equals("access ip is inconsistent with user ip!")) {
            jsonObject = errorToJson(E_3018);
        } else {
            jsonObject = errorToJson(E_3015);
        }

        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
        }
        return false;
    }

    private JsonObject errorToJson(IErrorEnumImplOuter iErrorEnumImplOuter) {
        JsonObject errorJson = new JsonObject();
        errorJson.addProperty(Constants.CODE, iErrorEnumImplOuter.getErrorCode());
        errorJson.addProperty(Constants.MESSAGE, iErrorEnumImplOuter.getErrorMsg());
        errorJson.addProperty("success", false);
        return errorJson;
    }
}
