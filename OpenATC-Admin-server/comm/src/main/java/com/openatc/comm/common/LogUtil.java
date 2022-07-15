package com.openatc.comm.common;

import java.util.logging.Level;
import java.util.logging.Logger;

// 日志处理类
public class LogUtil {

    // 根据配置文件设置comm模块的打印类型
    public static void SetLogLevelfromProp(Logger log){
        String level = PropertiesUtil.getStringProperty("logging.level.root");
        if(level.equals("info")){
            log.setLevel(Level.INFO);
        }
        else if(level.equals("warning")) {
            log.setLevel(Level.WARNING);
        }
    }
}
