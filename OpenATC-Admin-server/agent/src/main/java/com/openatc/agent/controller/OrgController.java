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

import com.openatc.agent.model.AscsBaseModel;
import com.openatc.agent.model.SysOrg;
import com.openatc.agent.model.User;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.OrgService;
import com.openatc.agent.service.UserDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
//@RequiresPermissions("sys:auth:permission:view")
public class OrgController {

    @Autowired
    private OrgService orgService;
    @Autowired(required = false)
    AscsDao mDao;
    @Autowired(required = false)
    protected UserDao userDao;

    @GetMapping("")
    public RESTRetBase listPermission() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<SysOrg> list =  orgService.findAll(sort);
        return RESTRetUtils.successObj(list);
    }

    @PostMapping("/{code}/devs")
    public RESTRetBase updateDevCode(@PathVariable String code, @RequestBody List<String> agentids) {
        //先查询code下关联的设备集合
        List<String> ascsidByCode1 = mDao.getAscsidByCode(code);
        ascsidByCode1.removeAll(agentids);
        for (String agentid : ascsidByCode1){
            mDao.updateDevByCode(null, agentid);
        }
        for (String agentid : agentids){
            mDao.updateDevByCode(code, agentid);
        }
        return RESTRetUtils.successObj();
    }

    @DeleteMapping("/{id}")
    //@RequiresPermissions("sys:auth:permission:modify")
    public RESTRetBase delOrgnization(@PathVariable int id) {
        // 查找该组织和所有子组织
        String orgnizationCode = orgService.findOrgNameById(id);
        List<SysOrg> sysOrgs = orgService.findByOrgnizationCodeLike(orgnizationCode);
        for(SysOrg sysOrg : sysOrgs){
            List<AscsBaseModel> ascsByCode = mDao.getAscsByCode(sysOrg.getOrgnization_code());
            // 将待删除组织相关联的设备组织置为空
            for(AscsBaseModel ascsBaseModel : ascsByCode){
                ascsBaseModel.setCode(null);
                mDao.updateDev(ascsBaseModel);
            }
            // 将待删除组织相关的人员组织信息置为空
            List<User> users = userDao.findByOrOrganization(sysOrg.getOrgnization_code());
            for(User user : users){
                user.setOrganization(null);
                userDao.save(user);
            }
            orgService.delete(sysOrg);
        }
//        orgService.deleteById(id);
        return RESTRetUtils.successObj();
    }

    @GetMapping("/1")
    public RESTRetBase listPermissn() {
        List<SysOrg> kedacom = orgService.findByOrgnizationCodeLike("kedacom");
        return RESTRetUtils.successObj(kedacom);
    }

    @GetMapping("/{id}")
    public RESTRetBase getOrgnization(@PathVariable int id) {
        return RESTRetUtils.successObj(orgService.findById(id));
    }

    @PostMapping("")
    public RESTRetBase addOrgnization(@RequestBody SysOrg sysOrg) {
        SysOrg org = orgService.save(sysOrg);
        return RESTRetUtils.successObj(org);
    }

//    @PutMapping("/{id}")
//    //@RequiresPermissions("sys:auth:permission:modify")
//    public RESTRetBase updateOrgnization(@PathVariable int id, @RequestBody JsonObject jsonObject) {
//        return orgService.updateOrganization(id, jsonObject);
//    }

//    @GetMapping("/{path}/users")
//    public RESTRetBase getUsersByPermission(@PathVariable String path) {
//        return orgService.getUsersByOrganization(path);
//    }

//    @GetMapping("/{path}/roles")
//    public RESTRetBase getRolesByPermission(@PathVariable String path) {
//        return orgService.getRolesByOrganization(path);
//    }
}
