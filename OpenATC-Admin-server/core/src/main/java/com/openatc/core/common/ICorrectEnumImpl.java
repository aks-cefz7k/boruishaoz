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

public enum ICorrectEnumImpl implements ICorrectEnum {
    /*
     * 正确信息
     * */
    C_30001("30001","user expired");

    private String correctCode;
    private String correctMsg;

    ICorrectEnumImpl() {
    }

    ICorrectEnumImpl(String correctCode, String correctMsg) {
        this.correctCode = correctCode;
        this.correctMsg = correctMsg;
    }

    @Override
    public String getCorrectCode() {
        return correctCode;
    }

    private void setCorrectCode(String correctCode) {
        this.correctCode = correctCode;
    }

    @Override
    public String getCorrectMsg() {
        return correctMsg;
    }

    private void setCorrectMsg(String correctMsg) {
        this.correctMsg = correctMsg;
    }
}
