package com.openatc.comm.common;

import com.google.gson.JsonObject;
import com.openatc.comm.data.MessageData;

import java.util.logging.Level;
import java.util.logging.Logger;

// 日志处理类
public class LogUtil {

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

    public static MessageData CreateErrorResponceData(String agentId, String desc) {
        MessageData responceData = new MessageData();
        responceData.setAgentid(agentId);
        responceData.setOperation("error-response");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("desc", desc);
        responceData.setData(jsonObject);
        return responceData;
    }
}
