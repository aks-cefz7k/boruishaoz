package com.openatc.agent.handler;

import com.google.gson.Gson;
import com.openatc.agent.service.FeatureService;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.IMsgPostHandler;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplInner.E_109;
import static com.openatc.core.common.IErrorEnumImplInner.E_110;

/**
 * @Classname MsgPostHandler
 * @Description
 * @Date 2022/3/30 23:17
 * @Created by panleilei
 */
@Component
public class MsgPostHandler implements IMsgPostHandler {
    private static Logger logger = Logger.getLogger(MsgPostHandler.class.toString());


    @Autowired
    private FeatureService featureService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String keyType = "response:status/pattern:";

    private Gson gson = new Gson();

    @Override
    public RESTRet process(MessageData request, RESTRet response) {
        // 获取消息类型
        String infotype = request.getInfotype();
        String agentid = request.getAgentid();
        String operation = request.getOperation();

        if (agentid == null){
            logger.info("MsgPostHandler process: agentid is null");
            return RESTRetUtils.successObj(null);
        }

        // 应答成功的请求
        if (response.isSuccess()){
            MessageData res = (MessageData) response.getData();
            // 成功应答不应出现null
            if (res == null){
                return RESTRetUtils.successObj(null);
            }
            // 请求
            if (operation.equals("get-request")){
                // 成功应答的方案状态请求
                if (infotype.equals("status/pattern")){
                    // 保存到redis
                    String key = keyType + agentid;
                    res.setCreatetime(String.valueOf(System.currentTimeMillis()));
                    stringRedisTemplate.opsForValue().set(key,gson.toJson(res));
                }
                // 所有参数上载成功
                else if (infotype.equals("feature/all")){
                    RESTRet restRet = featureService.saveFeatureAll(res);
                    if (!restRet.isSuccess()){
                        logger.info("MsgPostHandler process: save feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
            }
            // 设置（set-request）
            else {
                // 全部参数下载
                if (infotype.equals("feature/all")){
                    RESTRet restRet = featureService.saveFeatureAll(request);
                    if (!restRet.isSuccess()){
                        logger.info("MsgPostHandler process: save feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
                // 单独下载参数，清除该设备在数据库中的参数记录
                // 单独下载相位参数
                else if (infotype.equals("feature/phase")){
                    RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                    if (!restRet.isSuccess()) {
                        logger.info("MsgPostHandler process: clear feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
                // 单独下载方案参数
                else if (infotype.equals("feature/pattern")){
                    RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                    if (!restRet.isSuccess()) {
                        logger.info("MsgPostHandler process: clear feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
                // 单独下载跟随相位参数
                else if (infotype.equals("feature/overlap")){
                    RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                    if (!restRet.isSuccess()) {
                        logger.info("MsgPostHandler process: clear feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
                // 单独下载计划参数
                else if (infotype.equals("feature/plan")){
                    RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                    if (!restRet.isSuccess()) {
                        logger.info("MsgPostHandler process: clear feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
                // 单独下载日期参数
                else if (infotype.equals("feature/date")){
                    RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                    if (!restRet.isSuccess()) {
                        logger.info("MsgPostHandler process: clear feature failed!");
                    }
                    return RESTRetUtils.successObj(null);
                }
            }
        }
        // 应答失败的请求
        else {
            // 请求
            if (operation.equals("get-request")){
                if (infotype.equals("status/pattern")){
                    InnerError innerError = (InnerError) response.getData();
                    // 该设备的上次请求还未结束
                    if (innerError.getErrorCode().equals(E_110.getErrorCode())){
                        String key = keyType + agentid;
                        String value = stringRedisTemplate.opsForValue().get(key);
                        MessageData messageData = gson.fromJson(value, MessageData.class);
                        return RESTRetUtils.successObj(messageData);
                    }
                    // 请求失败
                    else if (innerError.getErrorCode().equals(E_109.getErrorCode())){
                        // 清空redis中的数据
                        String key = keyType + innerError.getAgentid();
                        stringRedisTemplate.delete(key);
                    }
                }
            }
            // 设置
            else {

            }
        }

        return RESTRetUtils.successObj(null);
    }
}

















