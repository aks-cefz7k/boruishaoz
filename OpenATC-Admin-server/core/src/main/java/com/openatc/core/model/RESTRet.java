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
package com.openatc.core.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.openatc.core.common.Constants.SUCCESS_CODE;
import static com.openatc.core.common.Constants.SUCCESS_MSG;

/** 
* @ClassName: RESTRet 
* @Description: TODO
* @author 
* @date 2019年10月19日 下午5:53:43 
* 
* @param <T> 
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class RESTRet<T> extends RESTRetBase {

    public RESTRet(Integer tookMesc, String path) {
        setCode(SUCCESS_CODE);
        setMessage(SUCCESS_MSG);
        setSuccess(true);
        setTook(tookMesc);
        setPath(path);
    }

    public RESTRet() {
        setCode(SUCCESS_CODE);
        setMessage(SUCCESS_MSG);
        setSuccess(true);
    }


    private T data;

    public RESTRet(boolean success, String code, String message, T data) {
        super(success, code, message);
        this.data = data;
    }

    public RESTRet(boolean success, String code, String message) {
        super(success, code, message);
    }
}
