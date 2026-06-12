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
package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_role_permission")
public class RolePermission {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; // 角色

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission; //权限
}
