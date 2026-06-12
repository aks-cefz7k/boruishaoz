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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openatc.core.common.Constants;
import com.openatc.core.common.IErrorEnum;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;

import java.time.Instant;
import java.util.List;

/**
 * @author
 * @ClassName: RESTRetUtils
 * @Description:
 * @date 2019年10月19日 下午6:35:06
 */
public final class RESTRetUtils {


    private RESTRetUtils() {

    }


    /**
     * @return RESTRetBase
     * @Title: successObj
     * @Description: 返回一个returnData为空对象的成功消息的json
     */
    public static RESTRetBase successObj() {
        return RESTRetBase.builder()
                .code(Constants.SUCCESS_CODE)
                .message(Constants.SUCCESS_MSG)
                .success(true)
                .build();
    }


    /**
     * @param tookMesc tookMesc
     * @param path     path
     * @return RESTRetBase
     * @Title: successObj
     * @Description: 返回一个returnData为空对象的成功消息的json 含时间以及uri
     */
    public static RESTRetBase successObj(Integer tookMesc, String path) {
        return RESTRetBase.builder()
                .code(Constants.SUCCESS_CODE)
                .message(Constants.SUCCESS_MSG)
                .success(true)
                .path(path)
                .took(tookMesc)
                .build();
    }


    /**
     * @param startTime startTime
     * @param path      path
     * @return RESTRetBase
     * @Title: successObj
     * @Description: 返回一个returnData为空对象的成功消息的json 含时间以及uri
     */
    public static RESTRetBase successObj(long startTime, String path) {
        return RESTRetBase.builder()
                .code(Constants.SUCCESS_CODE)
                .message(Constants.SUCCESS_MSG)
                .success(true)
                .path(path)
                .took((int) (Instant.now().toEpochMilli() - startTime))
                .build();
    }

    /**
     * @param <T>        <T>
     * @param returnData returnData
     * @return RESTRet
     * @Title: successObj
     * @Description:
     */
    public static <T> RESTRet<T> successObj(T returnData) {
        RESTRet<T> resultJson = new RESTRet<>();
        resultJson.setData(returnData);
        return resultJson;
    }

    /**
     * @param <T>        <T>
     * @param returnData json里的主要内容
     * @param tookMesc   tookMesc
     * @param path       path
     * @return RESTRet
     * @Title: successObj
     * @Description: 返回一个返回码为0的json
     */
    public static <T> RESTRet<T> successObj(T returnData, Integer tookMesc, String path) {

        RESTRet<T> resultJson = new RESTRet<>(tookMesc, path);
        resultJson.setData(returnData);
        return resultJson;
    }

    /**
     * @param <T>        <T>
     * @param returnData json里的主要内容
     * @param startTime  tookMesc
     * @param path       path
     * @return RESTRet
     * @Title: successObj
     * @Description: 返回一个返回码为0的json
     */
    public static <T> RESTRet<T> successObj(T returnData, long startTime, String path) {
        int took = (int) (Instant.now().toEpochMilli() - startTime);
        RESTRet<T> resultJson = new RESTRet<>(took, path);
        resultJson.setData(returnData);
        return resultJson;
    }

    /**
     * @param errorEnum 错误码的errorEnum
     * @return RESTRetBase
     * @Title: errorObj
     * @Description: 返回错误信息JSON
     */
    public static RESTRetBase errorObj(IErrorEnum errorEnum) {

        return RESTRetBase.builder()
                .code(errorEnum.getErrorCode())
                .message(errorEnum.getErrorMsg())
                .success(false)
                .build();
    }

    /**
     * @param errorEnum 错误码的errorEnum
     * @param path      path
     * @param tookMesc  tookMesc
     * @return RESTRetBase
     * @Title: errorObj
     * @Description: 返回错误信息JSON 含path 和 操作时间
     */
    public static RESTRetBase errorObj(IErrorEnum errorEnum, String path, Integer tookMesc) {

        return RESTRetBase.builder()
                .code(errorEnum.getErrorCode())
                .message(errorEnum.getErrorMsg())
                .success(false)
                .path(path)
                .took(tookMesc)
                .build();
    }

    /**
     * @param code     code
     * @param message  message
     * @param path     path
     * @param tookMesc tookMesc
     * @return RESTRetBase
     * @Title: errorObj
     * @Description: 返回错误信息JSON 含path 和 操作时间
     */
    public static RESTRetBase errorObj(String code, String message, String path, Integer tookMesc) {

        return RESTRetBase.builder()
                .code(code)
                .message(message)
                .success(false)
                .path(path)
                .took(tookMesc)
                .build();
    }

    /**
     * @param list 查询分页对象list
     * @return RESTRet
     * @Description:
     */
    public static RESTRet<JsonObject> successList(JsonArray list) {
        RESTRet<JsonObject> resultJson = new RESTRet<>();

        JsonObject returnData = new JsonObject();
        returnData.add("list", list);
        resultJson.setData(returnData);
        return resultJson;
    }

    /**
     * @param list     查询分页对象list
     * @param path     path
     * @param tookMesc tookMesc
     * @return RESTRet
     * @Description: 生成分页数据
     */
    @Deprecated
    public static RESTRet<JsonObject> successPage(JsonArray list, String path, Integer tookMesc) {
        RESTRet<JsonObject> resultJson = new RESTRet<>(tookMesc, path);

        JsonObject returnData = new JsonObject();
        returnData.add("list", list);
        resultJson.setData(returnData);
        return resultJson;
    }

    public static RESTRet errorObj(boolean isSuccess, IErrorEnumImplOuter iErrorEnum) {
        return new RESTRet(isSuccess,iErrorEnum.getErrorCode(),iErrorEnum.getErrorMsg());
    }


    //用于两层结构的一些方法
    public static RESTRetBase errorObj(IErrorEnumImplOuter iErrorEnum) {
        return RESTRetBase.builder()
                .code(iErrorEnum.getErrorCode())
                .message(iErrorEnum.getErrorMsg())
                .success(false)
                .build();
    }
    public static RESTRet errorDetialObj(IErrorEnumImplOuter iErrorEnum, List<InnerError> devCommErrors) {
        return new RESTRet(false,iErrorEnum.getErrorCode(),iErrorEnum.getErrorMsg(),devCommErrors);
    }

    public static RESTRet errorDetialObj(IErrorEnumImplOuter iErrorEnum, InnerError devCommError) {
        return new RESTRet(false,iErrorEnum.getErrorCode(),iErrorEnum.getErrorMsg(),devCommError);
    }

    public static RESTRet errorDetialObj(IErrorEnumImplOuter iErrorEnum, InnerError devCommError, Long delay) {
        return new RESTRet(false,iErrorEnum.getErrorCode(),iErrorEnum.getErrorMsg(),devCommError, delay);
    }

//    public static RESTRet errorDetialObj(IErrorEnumImplOuter iErrorEnum, JsonElement jsonElement) {
//        return new RESTRet(false,iErrorEnum.getErrorCode(),iErrorEnum.getErrorMsg(),jsonElement);
//    }

    //返回被包含的具体类型的错误信息，e.g. 101
    public static InnerError errorDevCommObj(String agentid, IErrorEnumImplInner iErrorEnumImplInner, Object content) {
        return InnerError.builder()
                .agentid(agentid)
                .content(content)
                .errorCode(iErrorEnumImplInner.getErrorCode())
                .errorMsg(iErrorEnumImplInner.getErrorMsg())
                .build();
    }
}
