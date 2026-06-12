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
package com.openatc.agent.service;

import com.openatc.agent.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PermissionDao extends JpaRepository<Permission, Integer>{

    @Query(nativeQuery=true,value="select p.* from t_user u,t_user_role ur,t_role_permission rp,t_permission p where u.id=ur.user_id and ur.role_id=rp.role_id and rp.permission_id=p.id and u.user_name=?1")
    List<Permission> getPermissionsByUserName(String userName);

    Page<Permission> findAll(Pageable pageable);
}