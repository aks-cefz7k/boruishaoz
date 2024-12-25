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
package com.openatc.core.common;


/**
 * @author kedacom
 * @ClassName: IErrorEnumImplOuter
 * @Description: 返回码第一层（包装的一层）错误信息
 * @date 2019年10月19日 下午5:03:04
 */
public enum IErrorEnumImplOuter implements IErrorEnum {
    /*
     * 错误信息
     * */
    E_1000("10000", "Parameter is null!"),
    E_1001("10001", "Parameter length is not correct!"),
    E_1002("1002", "URL is not correct!"),
    E_2002("10002", "The record is not exit!"),
    E_2003("20003", "The id is not exit or equal 0 !"),
    E_2004("20004", "No Param Record !"),
    E_2005("20005", "Not found devices id for udp!"),
    E_2006("20006", "Data stream close failed!"),
    E_2007("20007", "Json Type conversion failed!"),
    E_2008("20008", "File read failed!"),
    E_2009("20009", "File does not exist!"),
    E_2010("20010", "I/O close mistake!"),


    //用户管理相关
    E_3001("3001", "user_name does not exist!"),
    E_3002("3002", "Authentication failed!"),
    E_3003("3003", "Duplicate user_name!"),
    E_3004("3004", "newpassword is the same as oldpassword!"),
    E_3005("3005", "Password can not be empty!"),
    E_3006("3006", "oldPassword error!"),
    E_3007("3007", "No user logged in!"),
    E_3008("3008", "Subject does not have permissions！"),
    E_3009("3009", "Password error!"),
    E_3010("3010", "Superadmin roles can not be modified"),
    E_3011("3011", "username and password error"),
    E_3012("3012", "user expired"),
    E_3013("3013", "Account has been deactivated!"),
    E_3014("3014", "Token is expired!"),
    E_3015("3015", "Invaid token!"),
    E_3016("3016", "user organization is null"),
    E_3017("3017", "organization does not exist"),
    E_3018("3018", "access ip is inconsistent with user ip!"),

    // 消息通讯错误
    E_4001("4001", "error request!"),
    E_4002("4002", "error response!"),
    E_4003("4003", "Device not online!"),
    E_4004("4004", "Duplicate agentid!"),
    E_4005("4005", "responceData is null"),
    E_4006("4006", "responce operation is null"),

    //协调路线错误
    E_5001("5001", "Duplicate Route name!"),

    //勤务路线错误
    E_6001("6001", "Duplicate vipRoute name!"),
    E_6002("6002", "Device is in execution!"),

    //历史流量错误
    E_7001("7001", "ftpClient connect failed!"),

    //设备管理错误
    E_8001("8001", "device not found By Agentid"),

    //控制相关
    E_9001("9001", "Overflow control failed"),
    E_9002("9002", "Auto control failed"),


    //全局异常错误
    E_0001("0001", "NullPointerException"),
    E_0002("0002", "HttpRequestMethodNotSupportedException"),
    E_0003("0003", "TypeMismatchException"),
    E_0004("0004", "MissingServletRequestParameterException"),
    E_0005("0005", "ValidationException or MethodArgumentNotValidException"),
    E_0006("0006", "system error");

    private String errorCode;

    private String errorMsg;


    IErrorEnumImplOuter() {
    }

    IErrorEnumImplOuter(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    private void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
