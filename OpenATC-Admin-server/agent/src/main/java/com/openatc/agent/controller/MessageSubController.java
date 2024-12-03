package com.openatc.agent.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author laoxia
 * @Date 2021/7/28 14:08
 * @Describetion
 */
@RestController
public class MessageSubController {
    // jdk层面的缓存
    public Map<String, Set<String>> receiveAddrMap = new HashMap<>();

    public void addReceiveAddrMap(String key, Map<String, Set<String>> receiveAddrMap, Set<String> agentids) {
        Set<String> ids = receiveAddrMap.get(key);
        if (ids == null) {
            receiveAddrMap.put(key, agentids);
        } else {
            ids.addAll(agentids);
            receiveAddrMap.put(key, ids);
        }
    }

}
