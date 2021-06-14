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

import com.openatc.agent.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserDao extends JpaRepository<User, Integer>{

    /**
     * 登录的时候，根据用户名获取用户实体
     * @param userName
     * @return
     */
    @Query(value="select * from t_user where user_name=?1",nativeQuery=true)
    public User getUserByUserName(String userName);

    //根据用户名获取用户角色
    @Query(value="select r.name from t_role r where r.id in (select ur.role_id from t_user_role ur where (select u.id from t_user u where user_name =?1) = ur.user_id)",nativeQuery=true)
    public List<String> getRoleNamesByUsername(String userName);

    //根据用户名修改表中的盐
    @Transactional
    @Modifying
    @Query("update User u set u.salt = ?1 where u.user_name = ?2")
    int modifySaltByUserName(Long salt, String userName);

    Page<User> findAll(Pageable pageable);

    @Query("select u from User u order by user_name")
    List<User> findAllUser();

    List<User> findByOrOrganization(String name);

}

