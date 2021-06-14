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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author zs
 * @ClassName: ParamServiceImpl
 * @Description: 历史操作记录实现类
 * @date 2019年10月19日 下午5:43:40
 */
@Repository
public class HisParamServiceImpl {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private THisParamsDao thisParamsDao;

    /**
     * @param tParams 历史记录
     * @return RESTRetBase
     * @Title: putTscParam
     * @Description: 添加一条操作记录
     */
    public int insertHisParam(THisParams tParams) {
        try {
            thisParamsDao.save(tParams);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @return RESTRetBase
     * @Title: putTscParam
     * @Description: 获取所有的操作记录
     */
    public List<THisParams> getAllHisList() {
        return null;
    }


    /**
     * @param uuid 记录ID
     * @return RESTRetBase
     * @Title: putTscParam
     * @Description: 获取一条操作记录
     */
    public THisParams getHisList(String uuid) {
        THisParams hisParams = null;
        return hisParams;
    }


    /**
     * @param uuid 记录ID
     * @return RESTRetBase
     * @Title: putTscParam
     * @Description: 删除一条操作记录
     */
    public int deleteParams(String uuid) {
        return 0;
    }

}
