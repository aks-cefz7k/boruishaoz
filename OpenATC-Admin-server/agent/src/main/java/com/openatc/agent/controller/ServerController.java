package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.AgentApplication;
import com.openatc.agent.model.ServerState;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.utils.RedisTemplateUtil;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author laoxia
 * @Date 2021/10/11 9:30
 * @Describetion 获取服务器系统信息
 */
@RestController
public class ServerController {

    @Autowired
    RedisTemplateUtil redisTemplate;

    @Autowired
    AscsDao ascDao;

    @Value("${agent.version}")
    private String serviceVersion;
    // 打包时间
    @Value("${agent.build.time}")
    private String serviceBuildDate;

    private Gson gson = new Gson();

    /**
     * @param
     * @return
     * @descripation 从Redis中，获取服务状态
     **/
    @GetMapping(value = "/system/status/server/all")
    public RESTRetBase GetServerState() {

        Set<String> keyset =  redisTemplate.getKeys("server:status:*");
        Set<ServerState> ValueSet = new HashSet();
        for(String key : keyset){
            String value = redisTemplate.getValue(key);
            if(value == null)
                continue;

            ServerState serverState = gson.fromJson(value,ServerState.class);
            ValueSet.add(serverState);
        }
        return RESTRetUtils.successObj(ValueSet);
    }

    // 每5分钟，更新服务信息
    @Scheduled(cron = "0 0/5 * * * ?")
    public void UpdateServerStatus() {
        // 服务信息
        UpdateMainServerStaus();

        // 数据库信息
        UpdateDBStaus();

        // Redis信息
        UpdateRedisStaus();
    }

    // 更新Redis信息
    private void UpdateRedisStaus() {
        ServerState serverState = new ServerState();
        serverState.setServer("redis");
        serverState.setType("cache");
        Date day=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        serverState.setUpdatetime(sdf.format(day));
        serverState.setData(redisTemplate.GetRedisInfo() );

        redisTemplate.setValue("server:status:redis",gson.toJson(serverState));
    }

    // 更新数据库信息
    private void UpdateDBStaus() {
        ServerState serverState = new ServerState();
        serverState.setServer("postgresql");
        serverState.setType("db");
        Date day=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        serverState.setUpdatetime(sdf.format(day));

        int count = ascDao.getConnectCount();
        String size = ascDao.getDBSize("openatc");

        JsonObject jo = new JsonObject();
        jo.addProperty("Connect Count",String.valueOf(count) );
        jo.addProperty("DB Size" ,size);
        serverState.setData(jo);

        redisTemplate.setValue("server:status:postgresql",gson.toJson(serverState));
    }

    // 更新服务系统信息
    private void UpdateMainServerStaus() {
        ServerState serverState = new ServerState();
        serverState.setServer("OpenATC-Admin");
        serverState.setType("main");
        Date day=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        serverState.setUpdatetime(sdf.format(day));
        JsonObject jo = new JsonObject();

        // 服务版本
        jo.addProperty("Version",serviceVersion);
        jo.addProperty("Build Time" ,serviceBuildDate);

        // 内存信息
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        jo.addProperty("Total RAM",mem.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024 + "GB");
        jo.addProperty("Available　RAM" ,mem.getFreePhysicalMemorySize() / 1024 / 1024 / 1024 + "GB");

        // CPU信息
        OperatingSystemMXBean cpu = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double processCpuLoad = cpu.getProcessCpuLoad();
        double systemCpuLoad = cpu.getSystemCpuLoad();
//        long processCpuTime = cpu.getProcessCpuTime();
        jo.addProperty("Process Cpu Load",String.format("%.2f",processCpuLoad));
        jo.addProperty("System Cpu Load", String.format("%.2f",systemCpuLoad));
//        jo.addProperty("Process Cpu Time",processCpuTime );

        // 磁盘信息
        int diskNum=1;
        File[] disks = File.listRoots();
        for (File file : disks) {
            long free = file.getUsableSpace() / 1024 / 1024 / 1024;
            long total = file.getTotalSpace() / 1024 / 1024 / 1024;
            jo.addProperty("Free Space Disk "+diskNum,free + "G");
            jo.addProperty("Used Space Disk " + diskNum,(total - free) + "G");
            jo.addProperty("Total Space Disk " + diskNum,total + "G");
            diskNum++;
        }
        serverState.setData(jo);

        redisTemplate.setValue("server:status:openatc-admin",gson.toJson(serverState));
    }


}
