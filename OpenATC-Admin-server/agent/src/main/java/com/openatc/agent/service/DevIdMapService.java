package com.openatc.agent.service;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Data
public class DevIdMapService implements CommandLineRunner {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(DevIdMapService.class.toString());

    //thirdpartyid到agentid的映射
    //thirdpartyid时ocp协议中主动上报的id，agentid为平台用户设置的id
    private Map<String, String> agentidToThirdidScp = new HashMap<>();
    private Map<String, String> thirdidToAgentidScp = new HashMap<>();
    private Map<String, String> thirdidToAgentidOcp = new HashMap<>();
    private Map<String, String> OCPIDMAP = new HashMap<>();
    //ocpLock为1时，表明OCPIDMAP不允许被访问


    private int ocpLock;
    //构造函数、依赖注入后执行
    @Override
    public void run(String... args) throws Exception {
        /**
         * 服务启动时，生成一个thirdpartyid和agentid的设备映射map
         *     当主动上报的thirdpartyid不在设备映射map中
         *     当上报的thirdpartyid不在设备映射map中，则忽略设备
         */
        initMap();
    }

    /**
     * ocp : key=ipport, value=agentid:thirdpartyid
     * scp : key=ipport:agentid, value=thirdpartyid
     */
    public void initMap(){
        String sql = "SELECT agentid, thirdplatformid, protocol, jsonparam  FROM dev;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Gson gson = new Gson();

        for(Map<String,Object> m : maps){
            String type = (String) m.get("protocol");
            if(type.equals("ocp") || type.equals("OCP")){
                Map jsonparamMap = gson.fromJson(m.get("jsonparam").toString(), Map.class);
                String ip = (String)jsonparamMap.get("ip");

                String port = "";
                if(jsonparamMap.get("port") instanceof String){
                    port = (String)jsonparamMap.get("port");
                }else if(jsonparamMap.get("port") instanceof Double){
                    port = Integer.toString(((Double)jsonparamMap.get("port")).intValue());
                }else if(jsonparamMap.get("port") instanceof Integer){
                    port = Integer.toString(((Integer) jsonparamMap.get("port")).intValue());
                }
                String agentid = (String)m.get("agentid");
                String thirdpartyid = (String)m.get("thirdplatformid");
                String key = ip + port;
                String value = agentid + ":" + thirdpartyid;
                OCPIDMAP.put(key, value);
                thirdidToAgentidOcp.put(thirdpartyid,agentid);
            }
        }
        ocpLock = 0;
    }
}


































