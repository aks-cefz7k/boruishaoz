package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.openatc.agent.model.DeviceVedio;
import com.openatc.agent.service.DeviceVedioDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class DeviceVedioController {

    @Autowired
    private DeviceVedioDao deviceVedioDao;

    /**
     * 查询所有的视频
     * @return
     */
    @GetMapping(value = "/video")
    public RESTRetBase getVedios() {
        return RESTRetUtils.successObj(deviceVedioDao.findAll());
    }

    /**
     * 查询某个设备下的所有视频
     * @return
     */
    @GetMapping(value = "/video/{agentid}")
    public RESTRetBase getVediosByAgentid(@PathVariable String agentid) {
        return RESTRetUtils.successObj(deviceVedioDao.findAllByAgentid(agentid));
    }

    /**
     * 根据设备列表获取视频
     */
    @PostMapping(value = "/video/list")
    public RESTRetBase getDevList(@RequestBody JsonObject jsonObject) {
        Gson gson = new Gson();
        List<String> videolist = gson.fromJson(jsonObject.get("videoList"), List.class);
        List<DeviceVedio> deviceVedios = deviceVedioDao.findByAgentidIn(videolist);
        return RESTRetUtils.successObj(deviceVedios);
    }

    /**
     * 删除单个视频
     * @return
     */
    @DeleteMapping(value = "/video/single/{id}")
    public RESTRetBase deleteVedios(@PathVariable Long id){
        deviceVedioDao.deleteById(id);
        return RESTRetUtils.successObj();
    }

    /**
     * 删除某个设备下的所有视频
     * @return
     */
    @DeleteMapping(value = "/video/{agentid}")
    public RESTRetBase deleteVedios(@PathVariable String agentid){
        Integer integer = deviceVedioDao.deleteByAgentid(agentid);
        if (integer == 1){
            return RESTRetUtils.successObj(true);
        }else{
            return RESTRetUtils.successObj(false);
        }
    }


    @PutMapping(value = "/video")
    public RESTRetBase updateVedios(@RequestBody DeviceVedio deviceVedio) {
        return RESTRetUtils.successObj(deviceVedioDao.save(deviceVedio));
    }

    @PostMapping(value = "/video")
    public RESTRetBase addVedios(@RequestBody DeviceVedio deviceVedio) {
        return RESTRetUtils.successObj(deviceVedioDao.save(deviceVedio));
    }

}
