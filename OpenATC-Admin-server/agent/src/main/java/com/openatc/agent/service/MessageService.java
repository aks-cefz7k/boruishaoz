package com.openatc.agent.service;

import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;

/**
 * @Classname MessageService
 * @Description
 * @Date 2022/3/28 14:01
 * @Created by panleilei
 */
public interface MessageService {
    /**
     * 从redis中获取方案状态
     * @param agentid 路口id
     * @return
     */
    RESTRet getStatusPatternFromRedis(String agentid);


    /**
     * 将方案状态保存至redis
     * @param responseData
     */
    void saveStatusPatternToRedis(MessageData responseData);
}
