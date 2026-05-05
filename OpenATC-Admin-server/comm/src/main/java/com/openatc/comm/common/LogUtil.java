package com.openatc.comm.common;

import com.google.gson.Gson;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.InnerError;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.OPERATOER_TYPE_ERROR_REQUEST;
import static com.openatc.comm.common.CommunicationType.OPERATOER_TYPE_ERROR_RESPONSE;

// 日志处理类
public class LogUtil {

    private static Gson gson = new Gson();

    // 根据配置文件设置comm模块的打印类型
    public static void SetLogLevelfromProp(Logger log){
        String level = PropertiesUtil.getStringProperty("logging.level.root");
        if (level == null)
            return;
        else if(level.equals("info")){
            log.setLevel(Level.INFO);
        }
        else if(level.equals("warning")) {
            log.setLevel(Level.WARNING);
        }
    }

    public static MessageData CreateErrorResponceData(String agentId, InnerError devCommError) {
        MessageData responceData = new MessageData();
        responceData.setAgentid(agentId);
        responceData.setOperation(OPERATOER_TYPE_ERROR_RESPONSE);
        responceData.setData(gson.toJsonTree(devCommError));
        return responceData;
    }

    public static MessageData CreateErrorRequestData(String agentId, InnerError devCommError) {
        MessageData responceData = new MessageData();
        responceData.setAgentid(agentId);
        responceData.setOperation(OPERATOER_TYPE_ERROR_REQUEST);
        responceData.setData(gson.toJsonTree(devCommError));
        return responceData;
    }
}
