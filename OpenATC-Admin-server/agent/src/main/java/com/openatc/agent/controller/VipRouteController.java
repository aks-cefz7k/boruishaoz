package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.*;
import com.openatc.agent.service.VipRouteDao;
import com.openatc.agent.service.VipRouteDeviceDao;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.openatc.core.common.IErrorEnumImplOuter.E_5001;
import static com.openatc.core.common.IErrorEnumImplOuter.E_6001;
import static com.openatc.core.common.IErrorEnumImplOuter.E_6002;

@RestController
public class VipRouteController {
    @Autowired
    VipRouteDao vipRouteDao;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    VipRouteDeviceDao vipRouteDeviceDao;

    @Autowired
    MessageController messageController;

    private static final String ASC_VIPROUTE_STATUS = "asc:viproute/status:";
    private Sort.Order order = Sort.Order.asc("id");
    private Sort sort = Sort.by(order);
    private Gson gson = new Gson();

    // 获取所有的勤务路线的全部信息
    @GetMapping(value = "/viproute")
    public RESTRetBase getVipRoute() {
        return RESTRetUtils.successObj(vipRouteDao.findAll(sort));
    }

    // 获取单个勤务路线的全部信息
    @GetMapping(value = "/viproute/{id}")
    public RESTRetBase getVipRouteById(@PathVariable int id) {
        return RESTRetUtils.successObj(vipRouteDao.findById(id));
    }

    // 查询所有勤务路线的简略信息
    @GetMapping(value = "/viproute/simple")
    public RESTRetBase getVipRouteByName() {
        return RESTRetUtils.successObj(vipRouteDao.getSimpleInfoForVipRoute());
    }

    // 新增勤务路线
    @PostMapping(value = "/viproute")
    public RESTRetBase addVipRoutes(@RequestBody VipRoute routeEntity) {
        VipRoute r = vipRouteDao.findByName(routeEntity.getName());
        //校验路线名称是否重复
        if (r != null) {
            return RESTRetUtils.errorObj(E_6001);
        }
        VipRoute dbRoute = vipRouteDao.save(routeEntity);
        return RESTRetUtils.successObj(dbRoute);
    }

    // 更新勤务路线
    @PutMapping(value = "/viproute")
    public RESTRetBase updateVipRoute(@RequestBody VipRoute routeEntity) {
        int id = routeEntity.getId();
        // 0 执行前先判断一下是否存在执行中的设备，如果存在，抛出错误
        List<VipRouteDevice> vipRouteDevices = vipRouteDeviceDao.findByViprouteid(id);
        for (VipRouteDevice vipRouteDevice : vipRouteDevices) {
            String vrStatus = stringRedisTemplate.opsForValue().get(ASC_VIPROUTE_STATUS + id + ":" + vipRouteDevice.getAgentid());
            if (vrStatus == null) continue;
            VipRouteDeviceStatus vipRouteDeviceStatus = gson.fromJson(vrStatus, VipRouteDeviceStatus.class);
            if (vipRouteDeviceStatus.getState() == 1) return RESTRetUtils.errorObj(E_6002);
        }
        // 1 首先删除这条路线之前的设备

        vipRouteDeviceDao.deleteByViprouteid(id);
        double[] location = null;
        // 2 保存路线之前，要先计算墨卡托坐标
        //   如果routeEntity中存在location字段，则表示不是第一次保存设备，不需要计算location(坐标)，直接更新即可
        Set<VipRouteDevice> devs = routeEntity.getDevs();
        if (devs != null) {
            VipRouteDevice dev = devs.iterator().next();
            location = dev.getLocation();
        }
        //   第一次，需要进行坐标转换
        if (location == null) {
            for (VipRouteDevice device : devs) {
                Map<String, Object> geometry = device.getGeometry();
                List<Double> coordinates = (ArrayList) geometry.get("coordinates");
                double[] devlocation = new double[]{getMercatorLon(coordinates.get(0)), getMercatorLat(coordinates.get(1))};
                device.setLocation(devlocation);
            }
            routeEntity.setDevs(devs);
        }
        // 3 将设备信息更新到redis中
        for (VipRouteDevice vipRouteDevice : devs) {
            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(vipRouteDevice.getAgentid(), 0, "00:00");
            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + vipRouteDevice.getViprouteid() + ":" + vipRouteDevice.getAgentid(), gson.toJson(vipRouteDeviceStatus));
        }
        // 4 保存到数据库中
        VipRoute dbRoute = vipRouteDao.save(routeEntity);
        return RESTRetUtils.successObj(dbRoute);
    }

    // 更新勤务路线名称
    @PutMapping(value = "/viproute/name")
    public RESTRetBase updateVipRouteName(@RequestBody VipRoute routeEntity) {
        int id = routeEntity.getId();
        VipRoute route = vipRouteDao.findById(id);
        route.setName(routeEntity.getName());
        try {
            vipRouteDao.save(route);
        } catch (Exception e) {
            return RESTRetUtils.errorObj(E_5001);
        }
        return RESTRetUtils.successObj(route);
    }

    //删除勤务路线
    @DeleteMapping(value = "/viproute/{id}")
    public RESTRetBase deleteVipRoute(@PathVariable int id) {
        vipRouteDao.deleteById(id);
        return RESTRetUtils.successObj();
    }

    //执行勤务路线
    @PostMapping(value = "/viproute/execute")
    public RESTRetBase executeVipRoutes(@RequestBody JsonObject jsonObject) throws SocketException, ParseException {
        int viprouteid = jsonObject.get("viprouteid").getAsInt();
        String agentid = jsonObject.get("agentid").getAsString();
        int operation = jsonObject.get("operation").getAsInt();
        // 0 根据前端传过来的数据找到数据库中的vip路线设备
        VipRouteDevice vrDevice = vipRouteDeviceDao.findByViprouteidAndAgentid(viprouteid, agentid);
        // 1 将控制方式等发送给信号机
        JsonObject data = new JsonObject();
        data.addProperty("control", vrDevice.getControl());
        data.addProperty("terminal", vrDevice.getTerminal());
        data.addProperty("value", vrDevice.getValue());
        // 执行勤务路线
        if (operation == 1) {
            AtomicInteger totaltime = new AtomicInteger(vrDevice.getTotaltime());
            MessageData messageData = new MessageData(agentid, "set-request", "control/pattern", data);
            messageController.postDevsMessage(null, messageData);
            // 2 开启一个线程后台计算剩余时间
            new Thread(() -> {
                while (totaltime.get() > 0) {
                    try {
                        Thread.sleep(2000);
                        totaltime.addAndGet(-2);
                        // 将时间更新到redis中
                        if (totaltime.get() > 0) {
                            String resttime = totaltime.get() / 60 + ":" + totaltime.get() % 60;
                            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 1, resttime);
                            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
                        } else {
                            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 0, "00:00");
                            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        // 取消勤务路线
        if (operation == 0) {
            MessageData messageData = new MessageData(agentid, "get-request", "control/pattern", data);
            RESTRet restRet = messageController.postDevsMessage(null, messageData);
            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 0, "00:00");
            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
        }


        return RESTRetUtils.successObj();
    }

    // 查询勤务路线路口状态
    @GetMapping(value = "/viproute/{id}/status")
    public RESTRetBase getVipRouteStatus(@PathVariable int id) {

        List<VipRouteDeviceStatus> vriss = new ArrayList<>();
        String fuzzykeys = ASC_VIPROUTE_STATUS + id + ":*";
        Set<String> vrstatuskeys = stringRedisTemplate.keys(fuzzykeys);
        for (String vrstatus : vrstatuskeys) {
            String s = stringRedisTemplate.opsForValue().get(vrstatus);
            VipRouteDeviceStatus vipRouteDeviceStatus = gson.fromJson(s, VipRouteDeviceStatus.class);
            vriss.add(vipRouteDeviceStatus);
        }
        return RESTRetUtils.successObj(vriss);
    }

    /**
     * 经度转墨卡托
     *
     * @param x 经度
     * @return 墨卡托经度
     */
    public static double getMercatorLon(double x) {
        double mx = x * 20037508.34 / 180;
        return mx;
    }

    /**
     * 纬度转墨卡托
     *
     * @param y 纬度
     * @return 墨卡托纬度
     */
    public static double getMercatorLat(double y) {
        double my = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
        my = my * 20037508.34 / 180;
        return my;
    }
}
