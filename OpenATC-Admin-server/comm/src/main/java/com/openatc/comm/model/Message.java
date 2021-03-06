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
package com.openatc.comm.model;

import com.openatc.comm.data.MessageData;

import java.io.UnsupportedEncodingException;


/**
 * @ClassName Message.java
 * @Auther zhangwenchao
 * @CreateTime 2019/11/13 13:48
 * @Description 通讯消息处理接口，各个协议需实现自己的打包和解包方法
 */

public interface Message {
    PackData pack(MessageData sendMsg) throws UnsupportedEncodingException;
    MessageData uppack(byte[] dataSource,int length) throws UnsupportedEncodingException;


    int geyExangeType();

}
