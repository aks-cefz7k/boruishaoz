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

import com.openatc.core.common.IErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** 
* @ClassName: RESTRetBase 
* @Description:
* @author 
* @date 2019年10月19日 下午5:53:54 
*  
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RESTRetBase {

    private boolean success;
    private String code;
    private String message;
    private String path;
    private Integer took;
    private Long timestamp = Instant.now().toEpochMilli();

    public RESTRetBase(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    /**
    * @Title: setErrorInfo 
    * @Description:
    * @param err err
    * @throws 
    */
    public void setErrorInfo(IErrorEnum err) {
        this.setCode(err.getErrorCode());
        this.setMessage(err.getErrorMsg());
    }
}
