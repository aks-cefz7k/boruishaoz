package com.openatc.agent.handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.openatc.agent.service.MessageService;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.IMsgPreHandler;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4001;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4002;

/**
 * @Classname MsgPreHandler
 * @Description
 * @Date 2022/4/6 14:42
 * @Created by panleilei
 */
@Component
public class MsgPreHandler implements IMsgPreHandler {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String keyType = "response:status/pattern:";

    private Gson gson = new Gson();

    @Override
    public RESTRet process(MessageData requestData) {
        String infotype = requestData.getInfotype();
        String operation = requestData.getOperation();
        String agentid = requestData.getAgentid();
        if (infotype == null){
            InnerError innerError = RESTRetUtils.innerErrorObj(requestData.getAgentid(), E_102, requestData);
            RESTRetUtils.errorDetialObj(E_4001,innerError);
        }

        if (operation == null){
            InnerError innerError = RESTRetUtils.innerErrorObj(requestData.getAgentid(), E_101, requestData);
            RESTRetUtils.errorDetialObj(E_4001,innerError);
        }

        // 请求参数
        if (operation.equals("get-request")){
            // 方案状态请求
            if (infotype.equals("status/pattern")){
                String key = keyType + agentid;
                String value = stringRedisTemplate.opsForValue().get(key);
                if (value == null){
                    // 没有拿到数据，向设备请求
                    return RESTRetUtils.successObj(null);
                }
                MessageData responseData;
                try {
                     responseData = gson.fromJson(value, MessageData.class);
                } catch (JsonSyntaxException e){
                    InnerError innerError = RESTRetUtils.innerErrorObj(agentid, E_104, requestData);
                    return RESTRetUtils.errorDetialObj(E_4002,innerError);
                }

                Long currentTime = System.currentTimeMillis();
                Long createTime = Long.valueOf(responseData.getCreatetime());

                // redis中数据时间和当前时间相差大于720ms，向设备请求
                if (currentTime - createTime < 700){
                    return RESTRetUtils.successObj(responseData);
                }
            }
        }

        // 无需处理的请求
        return RESTRetUtils.successObj(null);
    }
}
