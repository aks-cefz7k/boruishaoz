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
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.DevIdMapService;
import com.openatc.agent.service.impl.FaultServiceImpl;
import com.openatc.agent.utils.DateUtil;
import com.openatc.agent.utils.InfluxDbUtils;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.ICommHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
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

    @Value("${spring.redis.enable}")
    private boolean isRedisEnable;
    @Value("${spring.influx.enable}")
    private boolean isInfluxDBEnable;
    @Autowired
    public StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FaultServiceImpl faultService;
    @Autowired
    private InfluxDbUtils influxDbUtils;
    @Autowired
    AscsDao ascsDao;
    @Autowired
    DevIdMapService devIdMapService;

    private String agenttype = "asc";

    private Gson gson = new Gson();

    // todo: 此处删除同步锁，观察是否有影响
    @Override
    public /*synchronized*/ void process(MessageData msg) throws ParseException {

        // 如果消息为空，则返回
        if (msg == null) {
            logger.warning("AgentHandler/process: MessageData is null");
            return;
        }

        // 检查消息时间
        if (msg.getCreatetime() == null) {
            msg.setCreatetime(DateUtil.date2esstr(new Date()));
        }

        // 获取消息类型
        String infotype = msg.getInfotype();
        if (infotype == null) {
            logger.warning("AgentHandler/process: MessageData.operation()/.infoType is null");
            return;
        }

        // 获取操作类型
        String operation = msg.getOperation();

        // 根据设备真实ID，设置平台的agentidID,此处的agentid不是设备的agentid，是平台在数据库中的唯一ID
        String agentid = devIdMapService.getAgentidFromThirdPartyid(msg.getThirdpartyid());
        msg.setAgentid(agentid);

        String key = agenttype + ":" + infotype + ":" + agentid;

        // 如果开启Redis，则将消息存入Redis
        if (isRedisEnable) {
            stringRedisTemplate.opsForValue().set(key, gson.toJson(msg));
        }

        // ** 以下为消息的特殊处理 **

        // 收到注册消息，更新设备信息
        if (infotype.equals("login") && operation.equals("report")) {
            // 反序列化注册消息对象
            JsonElement data = msg.getData();
            DevCover ascsModel = gson.fromJson(data, DevCover.class);
            // 更新设备信息,把设备的真实ID替换为平台的设备ID
            ascsModel.setAgentid(agentid);
            ascsModel.setThirdpartyid(msg.getThirdpartyid());
            ascsDao.updateAscsByReport(ascsModel);
        }
        // 收到方案消息，往Redis通道里发布消息
        else if (msg.getInfotype().equals("status/pattern")) {
            if(isRedisEnable)
                stringRedisTemplate.convertAndSend(agenttype + ":" + msg.getInfotype(), gson.toJson(msg));
            // 将方案信息保存到InfluxDB中
            if(isInfluxDBEnable)
                influxDbUtils.insertPattern(msg);
        }
        // 收到故障消息，保存到数据库中
        else if (msg.getInfotype().equals("status/fault")) {
            faultService.processFaultMessage(msg);
        }
        // 收到流量消息，保存到InfluxDB中
        else if (msg.getInfotype().equals("status/currentvolume")) {
            if(isInfluxDBEnable)
                influxDbUtils.insertVolume(msg);
        }
        // 收到灯色消息，保存到InfluxDB中
        else if (msg.getInfotype().equals("status/channel")) {
            if(isInfluxDBEnable)
                influxDbUtils.insertChannelLamp(msg);
        }
    }

}
