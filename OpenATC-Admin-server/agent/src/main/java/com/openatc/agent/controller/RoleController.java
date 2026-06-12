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

import com.openatc.agent.model.Role;
import com.openatc.agent.service.RoleDao;
import com.openatc.agent.service.UserDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kedacom
 * @ClassName: UserController
 * @Description:
 * @date 2019年11月3日 下午9:00:48
 */
@RestController
@CrossOrigin
public class RoleController {

    @Autowired(required = false)
    protected UserDao userDao;

    @Autowired(required = false)
    protected RoleDao roleDao;

    /**
     * @return RESTRetBase
     * @throws
     * @Title: getRoles
     * @Description:
     */
    @RequiresPermissions({"sys:auth:role:view"})
    @GetMapping(value = "/auth/role")    //查询所有角色
    public RESTRetBase getRoles() {
        List<Role> roles = roleDao.findAll();
        return RESTRetUtils.successObj(roles);
    }
}
