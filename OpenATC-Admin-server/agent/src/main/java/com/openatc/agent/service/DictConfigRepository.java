package com.openatc.agent.service;

import com.openatc.agent.model.DictConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

/**
 * @author ：zhangwenchao
 * @date ：Created in 2022/1/4 16:43
 * @description：字典配置服务
 * @modified By：
 * @version: $
 */



public interface DictConfigRepository extends JpaRepository<DictConfig, Long> {

    @Query(nativeQuery=true,value=" SELECT * from sysconfig where configtype = :configtype ")
    List<DictConfig> findByConfigtype(int configtype);
}