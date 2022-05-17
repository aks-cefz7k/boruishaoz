package com.openatc.agent.controller;

import com.openatc.agent.model.DeviceVedio;
import com.openatc.agent.service.DeviceVedioDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceVedioController {

    @Autowired
    private DeviceVedioDao deviceVedioDao;

    /**
     * 查询所有的视频
     * @return
     */
    @GetMapping(value = "/vedio")
    public RESTRetBase getVedios() {
        return RESTRetUtils.successObj(deviceVedioDao.findAll());
    }

    /**
     * 查询某个设备下的所有视频
     * @return
     */
    @GetMapping(value = "/vedio/{agentid}")
    public RESTRetBase getVediosByAgentid(@PathVariable String agentid) {
        return RESTRetUtils.successObj(deviceVedioDao.findAllByAgentid(agentid));
    }

    /**
     * 删除单个视频
     * @return
     */
    @DeleteMapping(value = "/vedio/single/{id}")
    public RESTRetBase deleteVedios(@PathVariable Long id){
        deviceVedioDao.deleteById(id);
        return RESTRetUtils.successObj();
    }

    /**
     * 删除某个设备下的所有视频
     * @return
     */
    @DeleteMapping(value = "/vedio/{agentid}")
    public RESTRetBase deleteVedios(@PathVariable String agentid){
        Integer integer = deviceVedioDao.deleteByAgentid(agentid);
        System.out.println(integer+"--------------------------------------");
        if (integer == 1){
            return RESTRetUtils.successObj(true);
        }else{
            return RESTRetUtils.successObj(false);

        }

    }


    @PutMapping(value = "/vedio")
    public RESTRetBase updateVedios(@RequestBody DeviceVedio deviceVedio) {
        return RESTRetUtils.successObj(deviceVedioDao.save(deviceVedio));
    }

    @PostMapping(value = "/vedio")
    public RESTRetBase addVedios(@RequestBody DeviceVedio deviceVedio) {
        return RESTRetUtils.successObj(deviceVedioDao.save(deviceVedio));
    }

}
