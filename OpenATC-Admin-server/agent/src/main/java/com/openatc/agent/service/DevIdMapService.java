package com.openatc.agent.service;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Data
public class DevIdMapService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(DevIdMapService.class.toString());

    //thirdpartyid到agentid的映射
    //thirdpartyid时ocp协议中主动上报的id，agentid为平台用户设置的id

    private Map<String, String> thirdidToAgentidOcp = new HashMap<>();

    /**
     * 初始化OCP设备的真实ID和平台ID的映射关系
     * 此处不映射SCP设备的真实ID和平台ID的映射关系，由各个对接服务负责
     */
    @PostConstruct
    public void initMap(){
        thirdidToAgentidOcp.clear();
        String sql = "SELECT agentid, thirdplatformid, protocol, jsonparam  FROM dev;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for(Map<String,Object> m : maps){
            String type = (String) m.get("protocol");
            if(type.equals("ocp")){
                String agentid = (String)m.get("agentid");
                String thirdpartyid = (String)m.get("thirdplatformid");
                thirdidToAgentidOcp.put(thirdpartyid,agentid);
            }
        }
    }

    public String getAgentidFromThirdPartyid(String thirdpartyid) {
        return thirdidToAgentidOcp.get(thirdpartyid);
    }
}


































