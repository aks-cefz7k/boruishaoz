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


import com.openatc.agent.model.SysOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrgService extends JpaRepository<SysOrg, Integer> {

    void deleteById(Long id);


    //根据角色名获取角色
    @Query(value="select * from t_orgnization where position(?1 in orgnization_code) = 1",nativeQuery=true)
    List<SysOrg> findByOrgnizationCodeLike(String name);

    //根据角色名获取角色
    @Query(value="select  orgnization_code from t_orgnization where id = ?1",nativeQuery=true)
    String findOrgNameById(int id);

    //根据组织code查询
    @Query(value="select * from t_orgnization where orgnization_code = ?1",nativeQuery=true)
    List<SysOrg> findByOrgnization_codeEquals(String code);

    //    public RESTRetBase getUsersByOrganization(String permPath) {
//
//        Set<String>  permPathSet = getParentPathArray(permPath);
//
//        T_SysUser u = SYS_USER.as("u");
//        T_SysRoleUser ru = SYS_ROLE_USER.as("ru");
//        T_SysRolePermission rp = Tables.SYS_ROLE_PERMISSION.as("rp");
//        T_SysPermission p = Tables.SYS_PERMISSION.as("p");
//
//        JsonArray rolesList = Record2JSON.result2JsonArray(
//                dsl.selectDistinct( (u.ID), u.USER_NAME, u.STATUS ).from(u)
//                        .join(ru).on(u.ID.eq(ru.USER_ID))
//                        .join(rp).on(ru.ROLE_ID.eq(rp.ROLE_ID))
//                        .where(rp.PERMISSION_ID.in(
//                                dsl.select(p.ID).from(p)
//                                        .where(p.PERMISSION_CODE.in(permPathSet))
//                        ))
//                        .fetch());
//
//        return RESTRetUtils.successObj(rolesList);    }

//    public RESTRetBase getRolesByOrganization(String permPath) {
//
//        Set<String>  permPathSet = getParentPathArray(permPath);
//
//        T_SysRole r = Tables.SYS_ROLE.as("r");
//        T_SysRolePermission rp = Tables.SYS_ROLE_PERMISSION.as("rp");
//        T_SysPermission p = Tables.SYS_PERMISSION.as("p");
//
//        JsonArray rolesList = Record2JSON.result2JsonArray(
//                dsl.selectDistinct( (r.ID), r.NAME, r.STATUS ).from(r)
//                        .join(rp).on(r.ID.eq(rp.ROLE_ID))
//                        .where(rp.PERMISSION_ID.in(
//                                dsl.select(p.ID).from(p)
//                                        .where(p.PERMISSION_CODE.in(permPathSet))
//                        ))
//                        .fetch());
//
//        return RESTRetUtils.successObj(rolesList);
//    }

//    // 获取父节点路径数组
//    private Set<String> getParentPathArray(String permPath){
//        Set<String>  permPathSet = new HashSet<>();
//        permPathSet.add(permPath);
//
//        while(! permPath.isEmpty()){
//            permPath = getParentPath(permPath);
//            if(permPath.isEmpty()){
//                permPathSet.add(permPath + "*");
//            }
//            else{
//                permPathSet.add(permPath + ":*");
//            }
//        }
//        return permPathSet;
//    }
//
//    // 获取父节点路径
//    private String getParentPath(String permissionCode){
//        // 查找最末尾的“：”，即父节点的位置
//        int index = permissionCode.lastIndexOf(":");
//        if (index == -1 || index == 0){
//            return "";
//        }
//
//        String parentPath = permissionCode.substring(0, index);
//        return parentPath;
//    }
//
//    // 获取最后一个字符
//    private String getLastPermissionCodeChar(String str){
//        int strLen = str.length();
//        String lastStr = str.substring(strLen-1, strLen) ;
//        return lastStr;
//    }

}
