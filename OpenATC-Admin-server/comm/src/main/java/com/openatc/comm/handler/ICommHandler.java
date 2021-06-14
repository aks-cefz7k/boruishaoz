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
package com.openatc.comm.handler;

import com.openatc.comm.data.MessageData;

import java.text.ParseException;

/**
 * @ClassName ICommHandler.java
 * @Auther zhangwenchao
 * @CreateTime 2019/11/5 15:58
 * @Description 主动上报消息处理的接口，需要处理的主动上报消息的地方，实现此接口
 */
public abstract class ICommHandler {
  public abstract void process(MessageData msg) throws ParseException;
//  public abstract void process_fso(JSONObject recvObj);
}
