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
package com.openatc.comm.data;

import com.google.gson.JsonElement;
import lombok.Data;
//import org.springframework.stereotype.Component;

/**
 * @ClassName MessageData.java
 * @Auther zhangwenchao
 * @CreateTime 2019/11/13 13:48
 * @Description 通讯消息实体
 */
@Data
//@Component
public class MessageData {

  String agentid;
  String thirdpartyid;   //这是信号机上报的id
  String operation;
  String infotype;
  String model;
  String createtime;
  JsonElement data;
  Long delay;

  public MessageData(String agentid, String operation, String infotype, JsonElement data) {
    this.agentid = agentid;
    this.operation = operation;
    this.infotype = infotype;
    this.data = data;
  }

  public MessageData(String agentid, String operation, String infotype) {
    this.agentid = agentid;
    this.operation = operation;
    this.infotype = infotype;
  }

  public MessageData() {
  }
}
