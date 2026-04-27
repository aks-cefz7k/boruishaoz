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
 * @ClassName: IErrorEnumImplInner
 * @Description: 错误类型子类型，返回码第二层（被包装的一层）错误信息
 * @author kedacom
 * @date 2019年12月28日 下午5:03:04
 *
 */
public enum IErrorEnumImplInner implements IErrorEnum {

    // error-request
    E_100("4100","agentid is null!"),
    E_101("4101","operation is null!"),
    E_102("4102","infotype is null!"),
    E_103("4103","infodata is null!"),
    E_104("4104","JSON format is incorrect!"),
    E_105("4105","agentid not exist!"),
    E_106("4106","Unknown operation type!"),
    E_107("4107","Unknown infotype!"),
    E_108("4108","protocol is null!"),
    E_109("4109", "Receive Time Out or Receive Incorrect Data!"),

    //error-response
    E_200("4200","response is null!"),
    E_201("4201","response format error!"),
    E_203("4203","Unknown request instruction!"),
    E_204("4204","Comm Send Data error!"),
    E_205("4205","Udp Receive InfoType error by Send InfoType"),
    E_206("4206","Can not find UdpCommunication for Receive Msg"),

    //device not online
    E_301("4301","Device not online!")
    ;



    private String errorCode;

    private String errorMsg;

    IErrorEnumImplInner(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    IErrorEnumImplInner() {
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
