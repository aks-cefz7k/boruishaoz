package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.VipRoute;
import com.openatc.agent.model.VipRouteDevice;
import com.openatc.agent.model.VipRouteDeviceOnline;
import com.openatc.agent.model.VipRouteDeviceStatus;
import com.openatc.agent.model.VipRouteDeviceVO;
import com.openatc.agent.model.VipRouteVO;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.VipRouteDao;
import com.openatc.agent.service.VipRouteDeviceDao;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.StatusPattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.openatc.core.common.IErrorEnumImplOuter.*;

@Slf4j
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
    @Autowired
    AscsDao ascsDao;

    private String agenttype = "asc";
    private static final String ASC_VIPROUTE_STATUS = "asc:viproute/status:";
    private static final String ZEROSECONDS = "00:00";
    private Sort.Order order = Sort.Order.asc("id");
    private Sort sort = Sort.by(order);
    private Gson gson = new Gson();
    private List onExcuteDevlist = new ArrayList();

    // 获取所有的勤务路线的全部信息
    @GetMapping(value = "/viproute")
    public RESTRetBase getVipRoute() throws ParseException {
        List<VipRoute> vipRoutes = vipRouteDao.findAll(sort);
//        for (VipRoute vipRoute : vipRoutes) {
//            addGeometryToVipRoute(vipRoute);
//        }
        return RESTRetUtils.successObj(vipRoutes);
    }

    // 获取单个勤务路线的全部信息
    @GetMapping(value = "/viproute/{id}")
    public RESTRetBase getVipRouteById(@PathVariable int id) {
        VipRoute vipRoute = vipRouteDao.findById(id);
        List<VipRouteDeviceVO> vipRouteDeviceVOList = new ArrayList<>();
        List<VipRouteDeviceStatus> stateList = this.getVipRouteList(id);
        for (VipRouteDevice device : vipRoute.getDevs()) {
            for (VipRouteDeviceStatus status: stateList) {
                if (device.getAgentid().equals(status.getAgentid())) {
                    VipRouteDeviceVO vo = new VipRouteDeviceVO(device, status);
                    vipRouteDeviceVOList.add(vo);
                    break;
                }
            }
        }
        VipRouteVO vipRouteVO = new VipRouteVO(vipRoute, vipRouteDeviceVOList);
        return RESTRetUtils.successObj(vipRouteVO);
    }


//    private void addGeometryToVipRoute(VipRoute vipRoute)  {
//        Set<VipRouteDevice> vipRouteDevs = vipRoute.getDevs();
//        if(vipRouteDevs == null){
//            return;
//        }
//        for (VipRouteDevice vipRouteDev : vipRouteDevs) {
//            if(vipRouteDev == null){
//                return;
//            }
//            String agentid = vipRouteDev.getAgentid();
//            AscsBaseModel ascsBaseModel = ascsDao.getAscsByID(agentid);
//            if(ascsBaseModel == null) return;
//            vipRouteDev.setGeometry(ascsBaseModel.getGeometry());
//        }
//    }


    // 查询所有勤务路线的简略信息
    @GetMapping(value = "/viproute/simple")
    public RESTRetBase getVipRouteByName() {
        return RESTRetUtils.successObj(vipRouteDao.getSimpleInfoForVipRoute());
    }

    // 新增勤务路线
    @PostMapping(value = "/viproute")
    public RESTRetBase addVipRoutes(@RequestBody VipRoute routeEntity) throws ParseException {
        VipRoute hasNoDevsRoute = new VipRoute();
        hasNoDevsRoute.setName(routeEntity.getName());
        VipRoute r = vipRouteDao.findByName(routeEntity.getName());
        //校验路线名称是否重复
        if (r != null) {
            return RESTRetUtils.errorObj(E_6001);
        }
        VipRoute dbRoute = vipRouteDao.save(hasNoDevsRoute);
        // 拿到id后更新
        int dbRouteId = dbRoute.getId();
        routeEntity.setId(dbRouteId);
        return updateVipRoute(routeEntity);
    }

    // 更新勤务路线
    @PutMapping(value = "/viproute")
    public RESTRetBase updateVipRoute(@RequestBody VipRoute routeEntity) {
        int id = routeEntity.getId();
        // 0 执行前先判断一下是否存在执行中的设备，如果存在，应答错误
        List<VipRouteDevice> vipRouteDevices = vipRouteDeviceDao.findByViprouteid(id);
        for (VipRouteDevice vipRouteDevice : vipRouteDevices) {
            String vrStatus = stringRedisTemplate.opsForValue().get(ASC_VIPROUTE_STATUS + id + ":" + vipRouteDevice.getAgentid());
            if (vrStatus == null) continue;
            VipRouteDeviceStatus vipRouteDeviceStatus = gson.fromJson(vrStatus, VipRouteDeviceStatus.class);
            if (vipRouteDeviceStatus.getState() == 1) return RESTRetUtils.errorObj(E_6002);
        }
        // 1 首先删除这条路线之前的设备
        vipRouteDeviceDao.deleteByViprouteid(id);

        // 2 保存路线
        Set<VipRouteDevice> devs = routeEntity.getDevs();
        if (devs != null && devs.size() != 0) {
            // 3 将设备信息更新到redis中
            for (VipRouteDevice vipRouteDevice : devs) {
                stringRedisTemplate.delete(ASC_VIPROUTE_STATUS + vipRouteDevice.getViprouteid() + ":*");
                VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(vipRouteDevice.getAgentid(), 0, "00:00");
                stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + vipRouteDevice.getViprouteid() + ":" + vipRouteDevice.getAgentid(), gson.toJson(vipRouteDeviceStatus));
            }
        }

        // 4 保存到数据库中
        VipRoute dbRoute = vipRouteDao.save(routeEntity);

//        addGeometryToVipRoute(dbRoute);

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
        vipRouteDeviceDao.deleteByViprouteid(id);
        vipRouteDao.deleteById(id);
        return RESTRetUtils.successObj();
    }

    //执行勤务路线
    @PostMapping(value = "/viproute/execute")
    public RESTRetBase executeVipRoutes(@RequestBody JsonObject jsonObject) {
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
        Thread thread1 = null;
        // 执行勤务路线
        if (operation == 1) {
            if (onExcuteDevlist.contains(agentid))
                return RESTRetUtils.errorObj(E_6002);

            MessageData messageData = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.ControlPattern, data);
            RESTRet restRet = messageController.postDevsMessage(null, messageData);
            // 应答错误处理
            if (restRet.getData() instanceof InnerError) {
                log.warn("viproute execute error:" + restRet);
                return restRet;
            } else {
                messageData = (MessageData) restRet.getData();
                JsonElement succeess = messageData.getData().getAsJsonObject().get("success");
                if(succeess == null || succeess.getAsInt() != 0){
                    return restRet;
                }
            }

            onExcuteDevlist.add(agentid);
            // 2 开启一个线程后台计算剩余时间
            thread1 = new Thread(() -> {
                int totaltime = vrDevice.getTotaltime();
                if(totaltime == 0){
                    // 回自主控制
                    backSelfControl(agentid);
                    onExcuteDevlist.remove(agentid);
                }

                while (totaltime > 0 && onExcuteDevlist.contains(agentid)) {
                    try {
                        totaltime -=1;
                        // 将时间更新到redis中
                        if (totaltime > 0) {
                            //相位用两位字符串表示，不足位数补0
                            String min = String.format("%2d", totaltime / 60).replace(" ", "0");
                            String sec = String.format("%2d", totaltime % 60).replace(" ", "0");
                            String resttime = min + ":" + sec;
                            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 1, resttime);
                            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
                        } else {
                            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 0, ZEROSECONDS);
                            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
                            onExcuteDevlist.remove(agentid);
                            log.info("Vip road thread end! agentid:" + agentid);
                            // 回自主控制
                            backSelfControl(agentid);
                            break;
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.warn("Vip road thread interrupted! agentid:" + agentid);
                        break;
                    }
                }
            });
            thread1.start();
        }
        // 取消勤务路线
        else if (operation == 0) {
            // 回自主控制
            RESTRet restRet = backSelfControl(agentid);
            VipRouteDeviceStatus vipRouteDeviceStatus = new VipRouteDeviceStatus(agentid, 0, ZEROSECONDS);
            stringRedisTemplate.opsForValue().set(ASC_VIPROUTE_STATUS + viprouteid + ":" + agentid, gson.toJson(vipRouteDeviceStatus));
            log.info("取消执行，存入redis");
            onExcuteDevlist.remove(agentid);

            if (restRet.getData() instanceof InnerError)
                return restRet;
        }
        return RESTRetUtils.successObj();
    }

    private RESTRet backSelfControl(String agentid) {
        JsonObject selfControl = new JsonObject();
        selfControl.addProperty("control", 0);
        MessageData selfMessage = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.ControlPattern, selfControl);
        return messageController.postDevsMessage(null, selfMessage);
    }

    // 查询勤务路线路口状态
    @GetMapping(value = "/viproute/{id}/status")
    public RESTRetBase getVipRouteStatus(@PathVariable int id) {

        // 获取路线状态
        List<VipRouteDeviceStatus> vriss = getVipRouteList(id);
        return RESTRetUtils.successObj(vriss);
    }

    /**
     * @Author: yangyi
     * @Date: 2022/1/5 17:45
     * @Description: get vip route detail
     */
    public List<VipRouteDeviceStatus> getVipRouteList (int id) {
        List<VipRouteDeviceStatus> vriss = new ArrayList<>();
        String fuzzykeys = ASC_VIPROUTE_STATUS + id + ":*";
        Set<String> vrstatuskeys = stringRedisTemplate.keys(fuzzykeys);
        List<String> agentIdList = new ArrayList<>();
        for (String vrstatus : vrstatuskeys) {
            String s = stringRedisTemplate.opsForValue().get(vrstatus);
            VipRouteDeviceStatus vipRouteDeviceStatus = gson.fromJson(s, VipRouteDeviceStatus.class);
            vriss.add(vipRouteDeviceStatus);
            agentIdList.add(vipRouteDeviceStatus.getAgentid());
        }
        // 查询路线中的路口信息，并修改状态
        List<VipRouteDeviceOnline> VipRouteDevices =  vipRouteDao.findVipRouteWithDevStateById(id);
        for(VipRouteDeviceOnline vipRouteDeviceOnline : VipRouteDevices){
            String agentid = vipRouteDeviceOnline.getAgentid();
            VipRouteDeviceStatus vipRouteDeviceStatus = GetVipRouteDeviceStatusbyID(vriss, agentid);
            String online = vipRouteDeviceOnline.getState();
            vipRouteDeviceStatus.setStateName(online);
            if(online.equals("DOWN")) {
                vipRouteDeviceStatus.setControl(-1);
                continue;
            }
//            StatusPattern statusPattern = messageController.GetStatusPattern(agentid);
            StatusPattern statusPattern = getDevStateFromCache(agentid);
            if(statusPattern == null){ // 无法获取到方案状态，设备不在线
                vipRouteDeviceStatus.setControl(-1);
            } else{ // 设备在线,设置设备当前状态
                int curControl = statusPattern.getControl();
                vipRouteDeviceStatus.setControl(curControl);
            }
        }
        return vriss;
    }

    /**
     * @Author: yangyi
     * @Date: 2022/1/5 9:42
     * @Description: get current pattern from cache
     */
    public StatusPattern getDevStateFromCache (String agentId) {
        StatusPattern statusPattern = new StatusPattern();
        String key = agenttype + ":" + "status/pattern" + ":" + agentId;
        String s = stringRedisTemplate.opsForValue().get(key);
        MessageData messageData = gson.fromJson(s, MessageData.class);
        statusPattern = gson.fromJson(messageData.getData().getAsJsonObject(),StatusPattern.class);
        return statusPattern;
    }

    /**
     * 根据路口ID，从vip路线中，获取路口的状态
     * @author: zhangwenchao
     * @param agentid
     * @return 单个路口的状态
     */
    private VipRouteDeviceStatus GetVipRouteDeviceStatusbyID(List<VipRouteDeviceStatus> vriss ,String agentid){
        VipRouteDeviceStatus vipRouteDeviceStatus = null;
        for(VipRouteDeviceStatus deviceStatus: vriss ){
            if(deviceStatus.getAgentid().equals(agentid)){
                vipRouteDeviceStatus = deviceStatus;
                break;
            }
        }
        return vipRouteDeviceStatus;

    }


//    /**
//     * 经度转墨卡托
//     *
//     * @param x 经度
//     * @return 墨卡托经度
//     */
//    public static double getMercatorLon(double x) {
//        double mx = x * 20037508.34 / 180;
//        return mx;
//    }
//
//    /**
//     * 纬度转墨卡托
//     *
//     * @param y 纬度
//     * @return 墨卡托纬度
//     */
//    public static double getMercatorLat(double y) {
//        double my = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
//        my = my * 20037508.34 / 180;
//        return my;
//    }
}
