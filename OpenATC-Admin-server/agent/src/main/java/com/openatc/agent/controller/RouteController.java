/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.agent.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.resmodel.DevList;
import com.openatc.agent.service.RouteDao;
import com.openatc.agent.service.RouteIntersectionDao;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.model.DevCommError;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.agent.model.*;
import algorithm.Greenwave;
import algorithm.Kdalgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplOuter.E_4003;
import static com.openatc.core.common.IErrorEnumImplOuter.E_5001;

/**
 * @author kedacom
 * @ClassName: RouteController
 * @Description: TODO
 * @date 2019年12月4日 下午16:34:17
 */
@RestController
@CrossOrigin
public class RouteController {
    private Logger logger = Logger.getLogger(RouteController.class.toString());

    @Autowired(required = false)
    private RouteDao routeDao;

    @Autowired(required = false)
    private RouteIntersectionDao routeIntersectionDao;

    @Autowired(required = false)
    private UserController userController;

    // @Autowired(required = false)
    private Kdalgorithm kdalgorithm = new Kdalgorithm();

    @Autowired
    private MessageController messageController;

    Sort.Order order = Sort.Order.asc("id");
    Sort sort = Sort.by(order);

    /**
     * @return RESTRetBase
     * @Title: getRoutes
     * @Description: 查询所有协调路线的全部信息
     */
    @GetMapping(value = "/route")
    public RESTRetBase getRoute() {
        logger.info("getRoute");
        //获取全部路线
        return RESTRetUtils.successObj(routeDao.findAll(sort));
    }

    /**
     * @return RESTRetBase
     * @Title: getRoutes
     * @Description: 查询单个协调路线的全部信息
     */
    @GetMapping(value = "/route/{id}")
    public RESTRetBase getRouteById(@PathVariable Long id) {
        logger.info("getRouteById");
        return RESTRetUtils.successObj(routeDao.findById(id));
    }

    /**
     * @return RESTRetBase
     * @Title: getRoutes
     * @Description: 查询全部协调路线的简略信息
     */
    @GetMapping(value = "/route/simple")
    public RESTRetBase getRouteByName() {
        return RESTRetUtils.successObj(routeDao.getSimpleInfoForRoute());
    }

    /**
     * @return RESTRetBase
     * @Title: addRoutes
     * @Description: 新增协调路线
     */
    @PostMapping(value = "/route")
    public RESTRetBase addRoutes(@RequestBody Route routeEntity) {
        Route r = routeDao.findByName(routeEntity.getName());
        //校验路线名称是否重复
        if (r != null) {
            return RESTRetUtils.errorObj(E_5001);
        }
        Route dbRoute = routeDao.save(routeEntity);
        return RESTRetUtils.successObj(dbRoute);
    }

    /**
     * @return RESTRetBase
     * @Title: updateRoute
     * @Description: 更新协调路线
     */
    @PutMapping(value = "/route")
    public RESTRetBase updateRoute(@RequestBody Route routeEntity) {
        Long id = routeEntity.getId();
        routeIntersectionDao.deleteByRouteid(id);
        Route dbRoute = routeDao.save(routeEntity);
        return RESTRetUtils.successObj(dbRoute);
    }

    /**
     * @return RESTRetBase
     * @Title: updateRoute
     * @Description: 更新协调路线名称
     */
    @PutMapping(value = "/route/name")
    public RESTRetBase updateRouteName(@RequestBody Route routeEntity) {
        Long id = routeEntity.getId();
        Route route = routeDao.findById(id);
        route.setName(routeEntity.getName());
        try {
            routeDao.save(route);
        } catch (Exception e) {
            return RESTRetUtils.errorObj(E_5001);
        }
        return RESTRetUtils.successObj(route);
    }

    /**
     * @return RESTRetBase
     * @Title: deleteRoute
     * @Description: 删除单个协调路线
     */
    @DeleteMapping(value = "/route/{id}")
    public RESTRetBase deleteRoute(@PathVariable Long id) {
        routeDao.deleteById(id);
        logger.info("deleteRoute");
        return RESTRetUtils.successObj();
    }

    /**
     * @return RESTRetBase
     * @Title: generate
     * @Description: 算法生成
     */
    @PostMapping(value = "/route/generate")
    public RESTRetBase generate(@RequestBody RoutePara routePara) {
        RouteOpt routeOpt = new RouteOpt();
        routeOpt.setDevs(routePara.getDevs());
        //计算优化
        double intslenth[] = new double[routePara.getDevs().size()]; //保存路口长度的值
        int intssplit[] = new int[routePara.getDevs().size()]; //保存路口协调相位绿信比的值

        int intsoffset[] = new int[routePara.getDevs().size()]; //保存优化后各路口的相位差的值
        int intsabs[] = new int[routePara.getDevs().size()];//各路口协调相位所属周期时间位置，用来计算绝对相位差

        try {
            double intsvelup = 0.00;
            double intsveldown = 0.00;

            intsvelup = routePara.getUpspeed();
            intsveldown = routePara.getDownspeed();

            int phaseno = 0;
            int cycle = 100;
            List<Device> deviceList = routePara.getDevs();
            for (Device device : deviceList) {
                //得到距离
                intslenth[device.getSortid() - 1] = device.getDistance();

                //得到周期
                cycle = device.getFeature().getPatternList().getCycle();
                //根据相位号得到协调相位值
                if (routePara.getDirection().equals("up"))
                    phaseno = device.getForwardphaseid();
                else if (routePara.getDirection().equals("down"))
                    phaseno = device.getBackphaseid();
                else
                    phaseno = device.getForwardphaseid();

                List<Ring>[] rings = device.getFeature().getPatternList().getRings();

                label:
                for (int i = 0; i < rings.length; i++) {
                    List<Ring> ringList = rings[i];
                    for (Ring ring : ringList) {
                        if (ring.getId() == phaseno) {
                            intssplit[device.getSortid() - 1] = ring.getValue();
                            break label;
                        } else {
                            intsabs[device.getSortid() - 1] += ring.getValue();
                        }
                    }
                }

                while (intsabs[device.getSortid() - 1] >= cycle) {
                    intsabs[device.getSortid() - 1] -= cycle;
                }
            }

            kdalgorithm.setAftcycle(cycle);
            if (routePara.getDirection().equals("up"))
                intsoffset = kdalgorithm.offsetOpt(routePara.getDirection(), intslenth, intsvelup, intssplit);
            else if (routePara.getDirection().equals("down"))
                intsoffset = kdalgorithm.offsetOpt(routePara.getDirection(), intslenth, intsveldown, intssplit);
            else
                intsoffset = kdalgorithm.offsetByBiDirection(intslenth, intsvelup, intsveldown, intssplit);

            List<Device> routeOptList = routeOpt.getDevs();

            for (Device device : routeOptList) {

                //把每个路口相位差转成绝对相位差，start位置也会变化
                intsoffset[device.getSortid() - 1] -= intsabs[device.getSortid() - 1];
                if (intsoffset[device.getSortid() - 1] < 0)
                    intsoffset[device.getSortid() - 1] += cycle;

                device.getFeature().getPatternList().setOffset(intsoffset[device.getSortid() - 1]);
            }

            List<Greenwave> greenwaveList = new LinkedList<>();
            if (routePara.getDirection().equals("up"))
                greenwaveList.add(kdalgorithm.getGwup());
            else if (routePara.getDirection().equals("down"))
                greenwaveList.add(kdalgorithm.getGwdown());
            else {
                greenwaveList.add(kdalgorithm.getGwup());
                greenwaveList.add(kdalgorithm.getGwdown());
            }
            routeOpt.setGreenwave(greenwaveList);

            return RESTRetUtils.successObj(routeOpt);
        } catch (Exception e) {
            logger.info(e.toString());
            return RESTRetUtils.successObj(routeOpt);
        }
    }

    /**
     * @return RESTRetBase
     * @Title: uploadConfigure
     * @Description: 路线整体上传方案
     */
    @GetMapping(value = "/route/{feature}/upload/{id}")
    public RESTRetBase uploadConfigure(@PathVariable String feature, @PathVariable Long id) throws SocketException, ParseException {
//        定义该接口的返回对象
        DevList devList = new DevList();
        //定义该对象的值为list列表
        List<RouteIntersectionBase> devs = new ArrayList<>();
        //拿到各个列表的id，再根据id进行请求获得数据后，存放到list当中
        Route rr = routeDao.findById(id);
        Set<RouteIntersection> routeIntersections = rr.getDevs();

        //为每一个设备设置id和feature
        for (RouteIntersection r : routeIntersections) {
            //创建返回的设备
            RouteIntersectionBase eachDev = new RouteIntersectionBase();
            //为设备设置id
            eachDev.setAgentid(r.getAgentid());
            //为设备设置feature
            MessageData messageData = new MessageData(r.getAgentid(), CosntDataDefine.getrequest, "feature/" + feature, new JsonObject());
            RESTRet<MessageData> retBase = null;
            retBase = messageController.postDevsMessage(null, messageData);

            if (retBase.getMessage().equals("Device not online!")) {
                DevCommError devCommError = RESTRetUtils.errorObj(r.getAgentid(), CosntDataDefine.errorrequest, "feature/" + feature, IErrorEnumImplInner.E_301);
                return RESTRetUtils.errorDetialObj(E_4003, devCommError);
            }
            if (retBase.getMessage().equals("error request!")) {
                return retBase;
            }

            Interfeature intersectionFeature = new Interfeature();
            if (feature.equals("pattern")) {
                intersectionFeature.setPatternList(retBase.getData().getData().getAsJsonObject().get("patternList"));
            }
            if (feature.equals("phase")) {
                intersectionFeature.setPhaseList(retBase.getData().getData().getAsJsonObject().get("phaseList"));
            }
            eachDev.setFeature(intersectionFeature);
            devs.add(eachDev);
        }
        devList.setDevs(devs);
        return RESTRetUtils.successObj(devList);
    }

    /**
     * @return RESTRetBase
     * @Title: downloadConfigure
     * @Description: 路线整体下载方案
     */
    @PostMapping(value = "/route/{feature}/download")
    public RESTRetBase downloadConfigure(@PathVariable String feature, @RequestBody JsonObject jsonObject) throws SocketException, ParseException {

        //先拿到pattern的jsonObject对象feature
        JsonArray devlistJsonArray = jsonObject.get("devs").getAsJsonArray();
        String intersectionid;
        JsonObject featureList;
        for (JsonElement device : devlistJsonArray) {
            intersectionid = device.getAsJsonObject().get("agentid").getAsString();
            //featureList就是要发送的patternList
            featureList = device.getAsJsonObject().get("feature").getAsJsonObject();
            MessageData messageData = new MessageData(intersectionid, CosntDataDefine.setrequest, "feature/" + feature, featureList);
            RESTRet<MessageData> retBase = null;
            retBase = messageController.postDevsMessage(null, messageData);
            if (retBase.getMessage().equals("Device not online!")) {
                DevCommError devCommError = RESTRetUtils.errorObj(intersectionid, CosntDataDefine.errorrequest, "feature/" + feature, IErrorEnumImplInner.E_301);
                return RESTRetUtils.errorDetialObj(E_4003, devCommError);
            }
        }
        return RESTRetUtils.successObj();
    }

}




