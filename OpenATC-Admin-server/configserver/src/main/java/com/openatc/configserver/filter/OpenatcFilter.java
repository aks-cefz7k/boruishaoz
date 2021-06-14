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
//package com.openatc.configserver.filter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.openatc.configserver.utils.TokenUtil;
//import com.openatc.core.common.IErrorEnumImplOuter;
//import com.openatc.core.model.RESTRetBase;
//import com.openatc.core.util.RESTRetUtils;
//import lombok.SneakyThrows;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// * Openatc过滤器实现
// */
//public class OpenatcFilter implements Filter {
//    private static final String AUTHORIZATION = "Authorization";
//    private static final String TOKEN = "token";
//    //定义String数组，存放被放行的url
//    private final String[] allowedUrls = {"/auth/login", ".jsp", ".css", ".html", ".svg", ".jpg", ".png", ".ico", ".json", "woff", ".eot", ".tff", ".jpeg"};
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @SneakyThrows
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        try {
//            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//            // HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//            // httpResponse.setCharacterEncoding("utf-8");
//
//            // 1 获取请求的url
//            String url = httpRequest.getRequestURI();
//
//            System.out.println(url);
//            // 2 获取请求的token
//            String token = httpRequest.getHeader(AUTHORIZATION);
//            if (token == null || token.length() == 0) {
//                // header中获取不到再从Parameter中拿
//                token = httpRequest.getParameter(TOKEN);
//            }
//
//            // 3 如果存在token, 认证后放行
//            if (token != null) {
//                boolean result = TokenUtil.verify(token);
//                if (result) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//
//            // 4 如果不存在token, 判断是否是静态资源, 如果是静态资源放行
//            for (String allowedurl : allowedUrls) {
//                if (url.endsWith(allowedurl)) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//
//            // 5 如果请求一直没有通过，则抛出自定义异常
//            throw new ServletException("自定义异常");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ServletException e) {
//            // 自定义异常的类，用户返回给客户端相应的JSON格式的信息
//            RESTRetBase errorObj = RESTRetUtils.errorObj(IErrorEnumImplOuter.E_3015);
//            servletResponse.setContentType("application/json; charset=utf-8");
//            servletResponse.setCharacterEncoding("UTF-8");
//            String userJson = convertObjectToJson(errorObj);
//            OutputStream out = servletResponse.getOutputStream();
//            out.write(userJson.getBytes("UTF-8"));
//            out.flush();
//        }
//    }
//
//    public String convertObjectToJson(Object object) throws JsonProcessingException {
//        if (object == null) {
//            return null;
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(object);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
