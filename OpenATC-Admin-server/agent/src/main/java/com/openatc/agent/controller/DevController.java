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
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.service.*;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.ControlPattern;
import com.openatc.model.model.LockDirection;
import com.openatc.model.service.ManualpanelService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplOuter.E_8004;
import static com.openatc.core.common.IErrorEnumImplOuter.E_8009;

@RestController
@CrossOrigin
public class DevController {
    private static Logger logger = Logger.getLogger(DevController.class.toString());

    @Autowired(required = false)
    AscsDao mDao;

    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private OrgService orgService;
    @Autowired
    VipRouteDeviceDao vipRouteDeviceDao;
    @Autowired
    OverflowRepository overflowRepository;
    @Autowired
    RouteIntersectionDao routeIntersectionDao;
    @Autowired
    private MessageController messageController;

    @Autowired
    private DevService devService;

    private Gson gson = new Gson();
    private Logger log = Logger.getLogger(DevController.class.toString());

    // 按用户名查询设备
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

    // 按组织机构查询设备
    @GetMapping(value = "/devs/orgnization/{code}")
    public RESTRetBase getAscsByCode(@PathVariable String code) {
        return RESTRetUtils.successObj(mDao.getAscsByCode(code));
    }

    // 查询所有没有组织机构的设备
    @GetMapping(value = "/devs/orgnization/none")
    public RESTRetBase getAscsNoCode() {
        return RESTRetUtils.successObj(mDao.getAscsNoCode());
    }

    //得到所有设备
    @GetMapping(value = { "/devs" ,  "/devs/all"})
    public RESTRet GetDevs() {
        List<AscsBaseModel> ascsBaseModels = mDao.getAscs();
        return devService.getDevs(ascsBaseModels);
////        mDao.alterStatus(ascsBaseModels);
//        return RESTRetUtils.successObj(ascsBaseModels);
    }

    // 按ID查询设备
    @GetMapping(value = { "/devs/{id}" , "/devs/info/{id}" })
    public RESTRetBase GetAscsInfo(@PathVariable String id) {
        return RESTRetUtils.successObj(mDao.getAscsByID(id));
    }

    // 按类型查询设备
    @GetMapping(value = "/devs/type/{type}")
    public RESTRetBase GetDevByType(@PathVariable String type) {
        return RESTRetUtils.successObj(mDao.getAscsByType(type));
    }

    @PostMapping(value = "/devs/range")
    public RESTRetBase getDevsRange(@RequestBody JsonObject jsonObject) {
//        return devService.getRangeDevs(jsonObject);
        return RESTRetUtils.successObj(mDao.getAscsRange(jsonObject));
    }
    // 按列表中的路口ID获取路口
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
//        mDao.alterStatus(devList);
        return RESTRetUtils.successObj(devList);
    }

    // 按ID删除设备
    @DeleteMapping(value = "/devs/{id}")
    public RESTRetBase DeleteDev(@PathVariable String id) {
        AscsBaseModel as = mDao.getAscsByID(id);
        //check oldAgentid if in use
        String oldAgentid = as.getAgentid();
        IErrorEnumImplOuter iErrorEnumImplOuter = checkDevInUse(oldAgentid);
        if(iErrorEnumImplOuter !=null) {
            return RESTRetUtils.errorObj(false,iErrorEnumImplOuter);
        }
        mDao.deleteDevByID(id);
        return RESTRetUtils.successObj(as);
    }

    /**
     * @Author: yangyi
     * @Date: 2022/2/16 14:48
     * @Description: checkDevInUse
     */
    public IErrorEnumImplOuter checkDevInUse (String oldAgentid) {
        List<Overflow> overflowList = overflowRepository.findByIntersectionid(oldAgentid);
        if(overflowList !=null && overflowList.size() > 0) {
            return IErrorEnumImplOuter.E_8008;
        }
        List<VipRouteDevice> vipRouteDeviceList =  vipRouteDeviceDao.findByAgentid(oldAgentid);
        if(vipRouteDeviceList !=null && vipRouteDeviceList.size() > 0) {
            return IErrorEnumImplOuter.E_8003;
        }
        List<RouteIntersection> routeIntersectionList =  routeIntersectionDao.findByAgentid(oldAgentid);
        if(routeIntersectionList !=null && routeIntersectionList.size() > 0) {
            return IErrorEnumImplOuter.E_8002;
        }
        return null;
    }

    // 添加设备
    @PostMapping(value = "/devs")
    public RESTRetBase InsertDev(@RequestBody AscsBaseModel ascs) {
//        String name = ascs.getName();
//        if (name == null || name.equals("")) {
//            ascs.setName(ascs.getAgentid());
//        }
        int count = mDao.getCountByAgentid(ascs.getAgentid());
        if (count != 0) {
            return RESTRetUtils.errorObj(E_8004);
        }
        return RESTRetUtils.successObj(mDao.insertDev(ascs));
    }

    // 更新设备
    @PutMapping(value = "/devs")
    public RESTRetBase UpdateDev(@RequestBody AscsBaseModel ascs) {
        int temp = mDao.updateDev(ascs);
        if (temp == 0) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2002);
        } else {
            return RESTRetUtils.successObj(ascs);
        }
    }

    // 信号机的注册消息处理
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
        //check oldAgentid if in use
        IErrorEnumImplOuter iErrorEnumImplOuter = checkDevInUse(oldAgentid);
        if(iErrorEnumImplOuter !=null) {
            return RESTRetUtils.errorObj(false,iErrorEnumImplOuter);
        }
        AscsBaseModel dev = mDao.getAscsByID(newAgentid);
        if (dev != null) {
            return RESTRetUtils.errorObj(false,IErrorEnumImplOuter.E_8004);
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

    /**
     * 获取昨日0至24点设备统计状态，只返回管理员的数据
     * @author: zhangwenchao
     * @return
     */
    @GetMapping(value = { "/devs/statuscollect/yesterday"})
    public RESTRetBase GetDevsStatuscollectYesterday() {
        // 管理员列表
        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("superadmin");
        adminRoles.add("admin");

        // 获取当前登录用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 未开启shiro
        if (user == null){
            // 返回所有设备
            return RESTRetUtils.successObj(mDao.statesCollectYesterday);
        }

        // 获取用户名
        String user_name = user.getUser_name();
        List<String> roles = userDao.getRoleNamesByUsername(user_name);
        adminRoles.retainAll(roles);
        // 非管理员
        if (adminRoles.size() == 0){
            return RESTRetUtils.errorObj(E_8009);
        }
        return RESTRetUtils.successObj(mDao.statesCollectYesterday);

    }
}