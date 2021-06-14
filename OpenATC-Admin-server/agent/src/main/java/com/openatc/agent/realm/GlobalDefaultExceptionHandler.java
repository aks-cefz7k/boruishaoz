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


import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.hibernate.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

import static com.openatc.core.common.IErrorEnumImplOuter.*;


/**
 * shiro全局异常捕获
 * 全局异常捕获类
 *
 * @author kedacom
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public RESTRetBase UnauthorizedExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_3008);
    }

//    @ExceptionHandler(NullPointerException.class)
//    @ResponseBody
//    public RESTRetBase NullPointerExceptionHandler(HttpServletRequest req, Exception e) {
//        return RESTRetUtils.errorObj(E_0001);
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public RESTRetBase HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_0002);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public RESTRetBase TypeMismatchExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_0003);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public RESTRetBase MissingServletRequestParameterExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_0004);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RESTRetBase MethodArgumentNotValidExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_0005);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public RESTRetBase ValidationExceptionHandler(HttpServletRequest req, Exception e) {
        return RESTRetUtils.errorObj(E_0005);
    }

}
