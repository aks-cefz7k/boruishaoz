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

import java.util.List;

import com.openatc.agent.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoleDao extends JpaRepository<Role, Integer>{

    //根据用户名获取用户角色
    @Query(value="select r.* from t_user u,t_user_role ur,t_role r where u.id=ur.role_id and ur.role_id=r.id and u.user_name=?1",nativeQuery=true)
    List<Role> getRolesByUserName(String userName);

    //根据角色名获取角色
    @Query(value="select * from t_role where name=?1",nativeQuery=true)
    public Role getRoleByUserName(String name);
//    @Query(value="select * from t_role r where r.id in (select ur.role_id from t_user_role ur where (select u.id from t_user u where user_name = ?1) = ur.user_id)",nativeQuery=true)
//    List<Role> getRolesByUserName(String userName);

}