package com.openatc.agent.model;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2022/4/13 10:16
 * @description：服务状态
 * @modified By：
 * @version: $
 */
@Data
public class ServerState {
    String server;
    String type;
    String updatetime;
    JsonObject data;
}
