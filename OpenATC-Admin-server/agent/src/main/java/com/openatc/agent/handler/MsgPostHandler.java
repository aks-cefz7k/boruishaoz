package com.openatc.agent.handler;

import com.openatc.agent.service.FeatureService;
import com.openatc.agent.service.MessageService;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.IMsgPostHandler;
import com.openatc.core.model.RESTRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

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
    private MessageService messageService;

    @Autowired
    private FeatureService featureService;

    @Override
    public void process(MessageData msg) {
        // 获取消息类型
        String infotype = msg.getInfotype();
        String agentid = msg.getAgentid();
        String operation = msg.getOperation();

        if (agentid == null){
            logger.info("MsgPostHandler process: agentid is null");
            return;
        }

        // 设置请求
        if (operation.equals("set-request")){
            // 全部参数下载
            if (infotype.equals("feature/all")){
                RESTRet restRet = featureService.saveFeatureAll(msg);
                if (!restRet.isSuccess()){
                    logger.info("MsgPostHandler process: save feature failed!");
                }
                return;
            }
            // 单独下载参数，清除该设备在数据库中的参数记录
            // 单独下载相位参数
            else if (infotype.equals("feature/phase")){
                RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                if (!restRet.isSuccess()) {
                    logger.info("MsgPostHandler process: clear feature failed!");
                }
                return;
            }
            // 单独下载方案参数
            else if (infotype.equals("feature/pattern")){
                RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                if (!restRet.isSuccess()) {
                    logger.info("MsgPostHandler process: clear feature failed!");
                }
                return;
            }
            // 单独下载跟随相位参数
            else if (infotype.equals("feature/overlap")){
                RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                if (!restRet.isSuccess()) {
                    logger.info("MsgPostHandler process: clear feature failed!");
                }
                return;
            }
            // 单独下载计划参数
            else if (infotype.equals("feature/plan")){
                RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                if (!restRet.isSuccess()) {
                    logger.info("MsgPostHandler process: clear feature failed!");
                }
                return;
            }
            // 单独下载日期参数
            else if (infotype.equals("feature/date")){
                RESTRet restRet = featureService.deleteFeatureByAgentid(agentid);
                if (!restRet.isSuccess()) {
                    logger.info("MsgPostHandler process: clear feature failed!");
                }
                return;
            }
        }
        // 应答请求
        else if (operation.equals("get-response")){
            // 获取方案状态
            if (infotype.equals("status/pattern")){
                // 将消息保存到redis，并设置过期时间1S
                messageService.saveStatusPatternToRedis(msg);
                return;
            }
            // 获取所有参数
            else if (infotype.equals("feature/all")){
                // 成功上载参数后，将参数保存到数据库
                RESTRet restRet = featureService.saveFeatureAll(msg);
                if (!restRet.isSuccess()){
                    logger.info("MsgPostHandler process: save feature failed!");
                }
                return;
            }

        }


    }
}

















