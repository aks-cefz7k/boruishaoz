package com.openatc.agent.model;

import com.openatc.agent.gateway.WebSocketServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoxia
 * @Date 2021/7/28 16:41
 * @Describetion
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWebSocketServer {
    private String name;
    private WebSocketServer webSocketServer;
}
