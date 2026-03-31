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

import com.openatc.agent.model.THisParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface THisParamsDao extends JpaRepository<THisParams, Integer> {

    Page<THisParams> findAll(Specification<THisParams> specification, Pageable pageable);

    @Query("select new THisParams(t.id, t.operator, t.opertime, t.source, t.agentid, t.infotype, t.status) from THisParams t")
    List<THisParams> findAllThisParams();

    //根据id获取requestBody
    @Query("select t.requestbody from THisParams t where t.id = ?1")
    String getRequestBodyById(int id);

    //根据id获取reponseBody
    @Query("select t.responsebody from THisParams t where t.id = ?1")
    String getResponseBodyById(int id);

    @Query("select new THisParams(t.id, t.operator, t.opertime, t.source, t.agentid, t.infotype, t.status) from THisParams t where t.id = ?1")
    THisParams getPartThisParamsById(int id);

    @Query("select new THisParams(t.id, t.operator, t.opertime, t.source, t.agentid, t.infotype, t.status) from THisParams t")
    Page<THisParams> findPageThisParams(Pageable pageable);
}