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
package com.openatc.agent.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.openatc.agent.controller.DevController;
import com.openatc.agent.model.DevCover;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.ICommHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * @ClassName AgentHandler
 * @Auther zhangwenchao
 * @CreateTime 2019/11/5 16:09
 * @Description
 */
@Component
public class AgentHandler extends ICommHandler {

    private static Logger logger = Logger.getLogger(AgentHandler.class.toString());

    @Autowired(required = false)
    DevController devController;
    @Value("${spring.redis.enable}")
    private boolean isRedisEnable;
    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    private String agenttype = "asc";

    @Override
    public synchronized void process(MessageData msg) throws ParseException {
        Gson gson = new Gson();
        if (msg == null) {
            logger.info("AgentHandler/process: MessageData is null");
            return;
        }

        if (msg.getInfotype() == null) {
            logger.info("AgentHandler/process: MessageData.operation()/.infotype is null");
            return;
        }

        // 收到注册消息
        if (msg.getInfotype().equals("login") && msg.getOperation().equals("report")) {
            JsonElement data = msg.getData();
            DevCover ascsModel = gson.fromJson(data, DevCover.class);
            ascsModel.setAgentid(msg.getAgentid());
            // 更新设备信息
            devController.DevAscsDiscovery(ascsModel);
        }
//     收到其他消息
        else if (isRedisEnable) {
            String key = agenttype + ":" + msg.getInfotype() + ":" + msg.getAgentid();
            //收到方案消息
            if (msg.getInfotype().equals("status/pattern")) {
                stringRedisTemplate.opsForValue().set(key, gson.toJson(msg));
                stringRedisTemplate.convertAndSend(agenttype + ":" + msg.getInfotype(), gson.toJson(msg));
            }
            //收到其他消息
            else {
                stringRedisTemplate.opsForValue().set(key, gson.toJson(msg));
                stringRedisTemplate.convertAndSend(agenttype + ":" + msg.getInfotype(), gson.toJson(msg));
            }
        } else {
            logger.info(gson.toJson(msg));
        }
    }
}
