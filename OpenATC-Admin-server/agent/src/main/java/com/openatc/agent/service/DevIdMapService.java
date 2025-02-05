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
    private Map<String, String> thirdidToAgentidScp = new HashMap<>();
    private Map<String, String> thirdidToAgentidOcp = new HashMap<>();


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
        thirdidToAgentidOcp.clear();
        String sql = "SELECT agentid, thirdplatformid, protocol, jsonparam  FROM dev;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for(Map<String,Object> m : maps){
            String type = (String) m.get("protocol");
            if(type.equals("ocp") || type.equals("OCP")){
                String agentid = (String)m.get("agentid");
                String thirdpartyid = (String)m.get("thirdplatformid");
                thirdidToAgentidOcp.put(thirdpartyid,agentid);
            }
            if(type.equals("scp") || type.equals("SCP")){
                String agentid = (String)m.get("agentid");
                String thirdpartyid = (String)m.get("thirdplatformid");
                thirdidToAgentidScp.put(thirdpartyid,agentid);
            }
        }
        ocpLock = 0;
    }
}


































