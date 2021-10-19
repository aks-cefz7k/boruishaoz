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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.*;
import com.openatc.agent.service.*;
import com.openatc.comm.data.MessageData;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class DevController {

    @Autowired(required = false)
    AscsDao mDao;
    @Autowired(required = false)
    AscsBaseModel ascsModel;
    @Autowired(required = false)
    private RouteDao routeDao;
    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private OrgService orgService;
    @Autowired
    private TStatDao tStatDao;
    @Autowired
    private MessageController messageController;

    @Value("${agent.server.mode.config}")
    private boolean isConfigMode;
    private Logger log = LoggerFactory.getLogger(DevController.class);

    private Gson gson = new Gson();


    @GetMapping(value = "/devs//user/{username}")
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

    @GetMapping(value = "/devs/all/{val}")
    public RESTRetBase GetAllAscsInfo(@PathVariable int val) {
        List<AscsBaseModel> ascsModels = null;
        //获取全部信号机
        if (val == 0) {
            ascsModels = mDao.getAscsInfo();
        }
        //获取在线信号机
        else if (val == 1) {
            ascsModels = mDao.getAscsInfoOnline();
        }

        return RESTRetUtils.successObj(ascsModels);
    }

    @GetMapping(value = "/devs/orgnization/{code}")
    public RESTRetBase getAscsByCode(@PathVariable String code) {
        return RESTRetUtils.successObj(mDao.getAscsByCode(code));
    }

    @GetMapping(value = "/devs/orgnization/none")
    public RESTRetBase getAscsNoCode() {
        return RESTRetUtils.successObj(mDao.getAscsNoCode());
    }

    /*
     *
     * @param id
     * @param i1
     * @return
     */
    @GetMapping(value = "/devs/info/{id}")
    public RESTRetBase GetAscsInfo(@PathVariable String id) {
        return RESTRetUtils.successObj(mDao.getAscsByID(id));
    }

    /*
     *
     * @param id
     * @param i1
     * @return
     */
    @PutMapping(value = "/devs/discovery")
    public RESTRetBase DevAscsDiscovery(@RequestBody DevCover ascsModel) throws ParseException {
        mDao.updateAscsByReport(ascsModel);
        return RESTRetUtils.successObj(ascsModel);
    }

    //得到所有设备
    @GetMapping(value = "/devs/all")
    public RESTRetBase GetDevAll() throws ParseException {
        String sql = null;
        if (isConfigMode) {
            sql = "SELECT id, agentid, protocol, geometry,type,status,descs, name,jsonparam,case (datetime('now', 'localtime') - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime, code FROM dev ORDER BY agentid";
        } else {
            sql = "SELECT id, agentid, protocol, geometry,type,status,descs, name,jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime, code FROM dev ORDER BY agentid";
        }
        List<AscsBaseModel> ascsBaseModels = mDao.getDevByPara(sql);
        return RESTRetUtils.successObj(ascsBaseModels);
    }

    @GetMapping(value = "/devs")
    public RESTRetBase GetDevs() throws ParseException {
        String sql = null;
        if (isConfigMode) {
            sql = "SELECT id, agentid, protocol, geometry,type,status,descs, name,jsonparam,case (datetime('now', 'localtime') - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime FROM dev ORDER BY agentid";
        } else {
            sql = "SELECT id, agentid, protocol, geometry,type,status,descs, name,jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime FROM dev ORDER BY agentid";
        }
        List<AscsBaseModel> ascsBaseModels = mDao.getDevByPara(sql);
        return RESTRetUtils.successObj(ascsBaseModels);
    }

    //按照列表获取设备
    @PostMapping(value = "/devs/list")
    public RESTRetBase getDevList(@RequestBody JsonObject jsonObject) {
        Gson gson = new Gson();
        List<AscsBaseModel> devList = new ArrayList<>();
        List<String> routeIntersectionIdList = gson.fromJson(jsonObject.get("routeIntersectionIdList"), List.class);
        for (String routeIntersectionId : routeIntersectionIdList) {
            devList.add(mDao.getAscsByID(routeIntersectionId));
        }
        return RESTRetUtils.successObj(devList);
    }

    //得到某一Id设备
    //todo 和GetAscsInfo有什么区别
    @GetMapping(value = "/devs/{id}")
    public RESTRetBase GetDevById(@PathVariable String id) throws ParseException {
        AscsBaseModel ascsBaseModel = null;
        String sql = null;
        if (isConfigMode) {
            sql =
                    "SELECT id,agentid,protocol, geometry,type,status,descs,name, jsonparam FROM dev WHERE agentid ='"
                            + id + "'";
        } else {
            sql =
                    "SELECT id,agentid,protocol, geometry,type,status,descs,name, jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when true then 'UP' else 'DOWN' END AS state,lastTime FROM dev WHERE agentid ='"
                            + id + "'";
        }
        List<AscsBaseModel> listAscs = mDao.getDevByPara(sql);
        if (listAscs.size() > 0) {
            ascsBaseModel = listAscs.get(0);
        }

        return RESTRetUtils.successObj(ascsBaseModel);

        //配置工具
        //return RESTRetUtils.successObj(DataPool.getInstance().GetDevicebyID(id));

    }

    //得到某类型的设备
    @GetMapping(value = "/devs/type/{ptype}")
    public RESTRetBase GetDevByType(@PathVariable String ptype) throws ParseException {
        String sql = null;
        if (isConfigMode) {
            sql =
                    "SELECT id,agentid,protocol, geometry,type,status,descs, name,jsonparam,case (datetime('now', 'localtime') - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state FROM dev WHERE type ='"
                            + ptype + "'";
        } else {
            sql =
                    "SELECT id,agentid,protocol, geometry,type,status,descs, name,jsonparam,case (LOCALTIMESTAMP - lastTime)< '5 min' when true then 'UP' else 'DOWN' END AS state FROM dev WHERE type ='"
                            + ptype + "'";
        }
        return RESTRetUtils.successObj(mDao.getDevByPara(sql));
    }

    //删除
    @DeleteMapping(value = "/devs/{id}")
    public RESTRetBase DeleteDev(@PathVariable String id) {
        AscsBaseModel as = mDao.getAscsByID(id);
        mDao.deleteDevByID(id);

        //删除协调路线的id设备
        List<Route> routes = routeDao.findAll();
        for (Route route : routes) {
            Set<RouteIntersection> intersections = route.getDevs();
            for (RouteIntersection intersection : intersections) {
                if (intersection.getAgentid().equals(id)) {
                    //在set中剔除设备
                    intersections.remove(intersection);
                }
            }
            routeDao.save(route);
        }


        return RESTRetUtils.successObj(as);
    }

    //添加设备
    @PostMapping(value = "/devs")
    public RESTRetBase InsertDev(@RequestBody AscsBaseModel ascs) {

        int count = mDao.getDevByAgentid(ascs.getAgentid());
        if (count != 0) {
            mDao.updateDev(ascs);
            return RESTRetUtils.successObj(ascs);
        }
        return RESTRetUtils.successObj(mDao.insertDev(ascs));
//            Boolean bl = DataPool.getInstance().PutDevice(ascs);
//            log.info("Current device build success," + ascs.getJsonparam().get("ip").getAsString() + "&" + ascs.getJsonparam().get("port").getAsString() + "&" + ascs.getProtocol());
//            return RESTRetUtils.successObj(bl);
    }

    //更新设备
    @PutMapping(value = "/devs")
    public RESTRetBase UpdateDev(@RequestBody AscsBaseModel ascs) {
        int temp = mDao.updateDev(ascs);
        if (temp == 0) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2002);
        } else {
            return RESTRetUtils.successObj(ascs);
        }
    }

    //获取设备优化状态参数
    @GetMapping(value = "/devs/{agentid}/optstatparam")
    public RESTRetBase getDevOptstatparam(@PathVariable String agentid) {
        List<TStat> tStats = tStatDao.findByAgentid(agentid);
        return RESTRetUtils.successObj(tStats);
    }

    //修改设备状态优化参数
    @PostMapping(value = "/devs/{agentid}/optstatparam")
    public RESTRetBase modDevOptstatparam(@PathVariable String agentid, @RequestBody JsonObject jsonObject) throws SocketException, ParseException {
        // 0 下发到信号机
        MessageData messageData = new MessageData(agentid, "set-request", "status/optstatparam", jsonObject);
        messageController.postDevsMessage(null, messageData);

        // 1 保存到数据库
        JsonArray tstats = jsonObject.get("tstats").getAsJsonArray();
        for(JsonElement tstatJson : tstats){
            TStat tStat = gson.fromJson(tstatJson, TStat.class);
            tStat.setAgentid(agentid);
            tStatDao.save(tStat);
        }
        return RESTRetUtils.successObj();
    }

}