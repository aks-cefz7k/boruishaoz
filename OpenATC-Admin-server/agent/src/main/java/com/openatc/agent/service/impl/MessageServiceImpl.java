package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.openatc.agent.service.MessageService;
import com.openatc.comm.data.MessageData;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Classname MessageServiceImpl
 * @Description
 * @Date 2022/3/28 14:02
 * @Created by panleilei
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    @Override
    public RESTRet getStatusPatternFromRedis(MessageData requestData) {
        String agentid = requestData.getAgentid();
        String key = "response:status/pattern:" + agentid;
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null){
            InnerError innerError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_109, requestData);
            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_4001,innerError);
        }
        MessageData responseData = gson.fromJson(value,MessageData.class);
        return RESTRetUtils.successObj(responseData);
    }

    @Override
    public void saveStatusPatternToRedis(MessageData responseData) {
        String key = "response:status/pattern:" + responseData.getAgentid();
        int expire = 720;
        String value = gson.toJson(responseData);
        stringRedisTemplate.opsForValue().set(key,value,expire, TimeUnit.MILLISECONDS);
    }


}
