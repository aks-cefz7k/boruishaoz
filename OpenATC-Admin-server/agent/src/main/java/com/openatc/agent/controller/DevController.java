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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openatc.agent.model.*;
import com.openatc.agent.service.*;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.ControlPattern;
import com.openatc.model.model.LockDirection;
import com.openatc.model.service.ManualpanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplOuter.*;

@RestController
@CrossOrigin
public class DevController {
    private static Logger logger = Logger.getLogger(DevController.class.toString());
    @Autowired(required = false)
    AscsDao mDao;

    @Autowired(required = false)
    private RouteDao routeDao;
    @Autowired
    private VipRouteDao vipRouteDao;
    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private OrgService orgService;
    @Autowired
    private MessageController messageController;

    private Gson gson = new Gson();
    private Logger log = Logger.getLogger(DevController.class.toString());

    @GetMapping(value = "/devs/user/{username}")
    public RESTRetBase GetAllAscsByUsername(@PathVariable String username) {
        User user = userDao.getUserByUserName(username);
        String organizationName = user.getOrganization();
        if (organizationName == null) return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_3016);
        List<SysOrg> sysOrgs = orgService.findByOrgnizationCodeLike(organizationName);
        if (sysOrgs == null) return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_3017);
        List<AscsBaseModel> devices = new ArrayList<>();
        for (SysOrg sysOrg : sysOrgs) {
            List<AscsBaseModel> ascsByCode = mDao.getAscsByCode(sysOrg.getOrgnization_code());
            devices.addAll(ascsByCode);
        }
        return RESTRetUtils.successObj(devices);
    }

    @GetMapping(value = "/devs/orgnization/{code}")
    public RESTRetBase getAscsByCode(@PathVariable String code) {
        return RESTRetUtils.successObj(mDao.getAscsByCode(code));
    }

    @GetMapping(value = "/devs/orgnization/none")
    public RESTRetBase getAscsNoCode() {
        return RESTRetUtils.successObj(mDao.getAscsNoCode());
    }

    //得到所有设备
    @GetMapping(value = { "/devs" ,  "/devs/all"})
    public RESTRetBase GetDevs() throws ParseException {
        String sql = "SELECT id, thirdplatformid, platform, gbid, firm, agentid, protocol, geometry, type, status, descs, name,jsonparam, case (LOCALTIMESTAMP - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev ORDER BY agentid";
        List<AscsBaseModel> ascsBaseModels = mDao.getDevByPara(sql);
        mDao.alterStatus(ascsBaseModels);
        return RESTRetUtils.successObj(ascsBaseModels);
    }

    @PostMapping(value = "/devs/list")
    public RESTRetBase getDevList(@RequestBody JsonObject jsonObject) {
        Gson gson = new Gson();
        List<AscsBaseModel> devList = new ArrayList<>();
        List<String> routeIntersectionIdList = gson.fromJson(jsonObject.get("routeIntersectionIdList"), List.class);
        for (String routeIntersectionId : routeIntersectionIdList) {
            AscsBaseModel device = null;
            try {
                device = mDao.getAscsByID(routeIntersectionId);
                if(device == null)
                    return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_8001);

            } catch (EmptyResultDataAccessException e) {
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_8001);
            }
            devList.add(device);
        }
        mDao.alterStatus(devList);
        return RESTRetUtils.successObj(devList);
    }

    //得到某一Id设备
    @GetMapping(value = { "/devs/{id}" , "/devs/info/{id}" })
    public RESTRetBase GetAscsInfo(@PathVariable String id) {
        return RESTRetUtils.successObj(mDao.getAscsByID(id));
    }

    //得到某类型的设备
    @GetMapping(value = "/devs/type/{ptype}")
    public RESTRetBase GetDevByType(@PathVariable String ptype) throws ParseException {
        String sql = "SELECT id,agentid,protocol, geometry,type,status,descs, name,jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when true then 'UP' else 'DOWN' END AS state FROM dev WHERE type ='" + ptype + "'";
        return RESTRetUtils.successObj(mDao.getDevByPara(sql));
    }

    //删除
    @DeleteMapping(value = "/devs/{id}")
    public RESTRetBase DeleteDev(@PathVariable String id) {

        //删除设备时，应通知所有服务更新映
        //删除协调路线的id设备
        List<Route> routes = routeDao.findAll();
        for (Route route : routes) {
            Set<RouteIntersection> intersections = route.getDevs();
            Iterator<RouteIntersection> intersectionIterator = intersections.iterator();
            while (intersectionIterator.hasNext()) {
                RouteIntersection next = intersectionIterator.next();
                if (next.getAgentid().equals(id)) {
//                    intersectionIterator.remove();
                    InnerError devCommError = RESTRetUtils.errorDevCommObj(id, IErrorEnumImplInner.E_8101, next);
                    return RESTRetUtils.errorDetialObj(E_8002,devCommError);
                }
            }
        }

        //删除勤务路线的设备
        List<VipRoute> vipRoutes = vipRouteDao.findAll();
        for (VipRoute vipRoute : vipRoutes) {
            Set<VipRouteDevice> devs = vipRoute.getDevs();
            Iterator<VipRouteDevice> vipRouteDeviceIterator = devs.iterator();
            while (vipRouteDeviceIterator.hasNext()) {
                VipRouteDevice next = vipRouteDeviceIterator.next();
                if (next.getAgentid().equals(id)) {
//                    vipRouteDeviceIterator.remove();
                    InnerError devCommError = RESTRetUtils.errorDevCommObj(id, IErrorEnumImplInner.E_8101, next);

                    return RESTRetUtils.errorDetialObj(E_8003,devCommError);
                }
            }
        }

        AscsBaseModel as = mDao.getAscsByID(id);
        mDao.deleteDevByID(id);
        return RESTRetUtils.successObj(as);
    }

    //添加设备
    @PostMapping(value = "/devs")
    public RESTRetBase InsertDev(@RequestBody AscsBaseModel ascs) {
        //check name
        String name = ascs.getName();
        if (name == null || name.equals("")) {
            ascs.setName(ascs.getAgentid());
        }
        int count = mDao.getDevByAgentid(ascs.getAgentid());
        if (count != 0) {
            return RESTRetUtils.errorObj(E_8004);
        }
        return RESTRetUtils.successObj(mDao.insertDev(ascs));
    }

    //更新设备
    @PutMapping(value = "/devs")
    public RESTRetBase UpdateDev(@RequestBody AscsBaseModel ascs) {
        int temp = mDao.updateDev(ascs);
        //check name
        String name = ascs.getName();
        if (name == null || name.equals("")) {
            ascs.setName(ascs.getAgentid());
        }
        if (temp == 0) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2002);
        } else {
            return RESTRetUtils.successObj(ascs);
        }
    }

    // 注册设备消息处理
    @PutMapping(value = "/devs/discovery")
    public RESTRetBase DevAscsDiscovery(@RequestBody DevCover ascsModel){
        mDao.updateAscsByReport(ascsModel);
//        mDao.updateAscs(ascsModel);
        return RESTRetUtils.successObj(ascsModel);
    }

    // 修改设备ID
    @PostMapping(value = "/devs/agentid")
    public RESTRetBase modifyAgentid(@RequestBody JsonObject jsonObject) {
        String oldAgentid = jsonObject.get("oldAgentid").getAsString();
        String newAgentid = jsonObject.get("newAgentid").getAsString();
        AscsBaseModel dev = mDao.getAscsByID(newAgentid);
        if (dev != null) {
            return RESTRetUtils.errorObj(false,IErrorEnumImplOuter.E_8002);
        }
        boolean result = mDao.modifyAgentid(oldAgentid, newAgentid);
        return RESTRetUtils.successObj(result);
    }

    // 锁定交通流
    @PostMapping(value = "/devs/{agentid}/lockdirection")
    public RESTRetBase DevAscsDiscovery(@PathVariable String agentid, @RequestBody LockDirection lockDirection){

        // 获取路口相位和通道参数
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.allfeature);
        RESTRet<MessageData> restRet = messageController.postDevsMessage(null, messageData);
        if(restRet.isSuccess()){

            JsonObject feature = restRet.getData().getData().getAsJsonObject();
            JsonArray phaseArray = feature.get("phaseList").getAsJsonArray();
            JsonArray channelArray = feature.get("channelList").getAsJsonArray();

            // 将锁定交通流参数转换为设备方向锁定消息
            ManualpanelService manualpanelService = new ManualpanelService();
            ControlPattern data = manualpanelService.LockDirection2ControlPattern(phaseArray,channelArray,lockDirection);
            messageData = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.ControlPattern, gson.toJsonTree(data));
            String str = gson.toJson(messageData);
            log.info("Lock Direction AgentID:" + agentid + " " + str);
            restRet = messageController.postDevsMessage(null, messageData);
        }

        return restRet;
    }
}