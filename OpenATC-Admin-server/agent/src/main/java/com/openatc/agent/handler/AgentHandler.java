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
import com.openatc.agent.model.DevCover;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.HistoryDataDao;
import com.openatc.agent.service.impl.FaultServiceImpl;
import com.openatc.agent.utils.DateUtil;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.ICommHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
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

    @Autowired
    public StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FaultServiceImpl faultService;
    @Autowired
    private AscsDao ascsDao;
    @Autowired
    private HistoryDataDao historyDataDao;
//    @Autowired
//    DevIdMapService devIdMapService;

    private String agenttype = "asc";

    private Gson gson = new Gson();

    // todo: 此处删除同步锁，观察是否有影响
    @Override
    public /*synchronized*/ void process(MessageData msg) throws ParseException {

        // 如果消息为空，则返回
        if (msg == null) {
            logger.warning("AgentHandler process: MessageData is null");
            return;
        }

        // 检查消息时间
        if (msg.getCreatetime() == null) {
            msg.setCreatetime(DateUtil.date2esstr(new Date()));
        }

        // 获取消息类型
        String infotype = msg.getInfotype();
        if (infotype == null) {
            logger.warning("AgentHandler process: infoType is null");
            return;
        }

        // 获取操作类型
        String operation = msg.getOperation();

        String agentid = msg.getAgentid();
        // 若Agentid为空，则是信号机上报的注册消息。对接服务上报的注册消息，都会带上agentid
        if (agentid == null) {
            agentid = ascsDao.getAgentidFromThirdPartyid(msg.getThirdpartyid());
            msg.setAgentid(agentid);
        }

        String key = agenttype + ":" + infotype + ":" + agentid;
        msg.setModel(agenttype);
        String value = null;

        // 如果开启Redis，则将消息存入Redis
        if (isRedisEnable) {
            value = gson.toJson(msg);
            stringRedisTemplate.opsForValue().set(key, value);
            stringRedisTemplate.convertAndSend(key, value);
        }

        // ** 以下为消息的特殊处理 **

        // 收到注册消息，更新设备信息
        if (infotype.equals("login") && operation.equals("report")) {
            DevCover ascsModel = gson.fromJson(msg.getData(), DevCover.class);
            ascsModel.setAgentid(msg.getAgentid());
            ascsModel.setThirdpartyid(msg.getThirdpartyid());
            ascsDao.updateAscsByReport(ascsModel);
        }
        // 收到方案消息，保存到数据库中
        else if (infotype.equals("status/pattern")) {
            historyDataDao.SavePatternData(msg);
        }
        // 收到故障消息，发布故障消息，并保存到数据库中
        else if (infotype.equals("status/fault")) {
            // todo: 写入数据前，判断故障是否已处理或者已忽略，若是则不写入Redis,直接从MSg对象中删除这部分数据
            msg = faultService.filterMsg(msg);
            faultService.processFaultMessage(msg);
        }
        // 收到流量消息，保存到数据库中
        else if (infotype.equals("status/currentvolume")) {
            historyDataDao.SaveFlowData(msg);
        }

    }
}
