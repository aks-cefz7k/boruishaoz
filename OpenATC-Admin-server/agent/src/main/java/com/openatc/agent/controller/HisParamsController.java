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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openatc.agent.model.THisParams;
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.agent.service.THisParamsDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplOuter.E_2004;


/**
 * @author: kedacom
 * @ClassName: HisParamsController
 * @Description: 操作记录接口类
 * @date 2019年10月19日 下午5:15:09
 */
@RestController
@CrossOrigin
public class HisParamsController {

    private static Logger logger = Logger.getLogger(HisParamsController.class.toString());

    @Autowired(required = false)
    protected HisParamServiceImpl hisParamService;

    @Autowired(required = false)
    protected THisParamsDao thisParamsDao;


    /**
     * @return RESTRetBase
     * @Title: getHisParamsList
     * @Description: 获取操作记录列表
     */
    @GetMapping(value = "/devs/hisparams")
    public RESTRetBase getHisParamsList(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageRow) {

        //不分页获取
        if (pageNum == null || pageRow == null) {
            List<THisParams> tHisParamsList = thisParamsDao.findAllThisParams();
            logger.info(tHisParamsList.toString());
            if (tHisParamsList.size() == 0) {
                return RESTRetUtils.errorObj(E_2004);
            }
            return RESTRetUtils.successObj(tHisParamsList);
        }

        //分页获取
        Sort sort = new Sort(Sort.Direction.DESC, "opertime");
        Pageable pageable = new PageRequest(pageNum - 1, pageRow, sort); //分页设置
        Page<THisParams> tHisParams = thisParamsDao.findPageThisParams(pageable);
        if (tHisParams.getSize() == 0) {
            return RESTRetUtils.errorObj(E_2004);
        }
        PageOR pageOR = new PageOR();
        pageOR.setTotal(tHisParams.getTotalElements());
        pageOR.setContent(tHisParams.getContent());
        return RESTRetUtils.successObj(pageOR);
    }

    /**
     * @param request request
     * @param uuid    uuid
     * @param id      id
     * @return RESTRetBase
     * @Title: getTscParamByUUID
     * @Description: 获取一条操作记录
     */
    @GetMapping(value = "/devs/hisparams/{uuid}")
    public RESTRetBase getTscParamByUUID(HttpServletRequest request,
                                         @PathVariable String uuid, @PathVariable String id) {

        logger.info("/devs/hisparams/upload/{uuid}");

        THisParams tParams = hisParamService.getHisList(uuid);
        return RESTRetUtils.successObj(tParams);
    }

    /**
     * @param uuid    uuid
     * @param request request
     * @param id      id
     * @return RESTRetBase
     * @Title: deleteTscParamByUUID
     * @Description: 删除操作记录
     */
    @DeleteMapping(value = "/devs/hisparams/{uuid}")
    public RESTRetBase deleteTscParamByUUID(@PathVariable String uuid,
                                            HttpServletRequest request, @PathVariable String id) {
        logger.info("/devs/param/delete/{uuid}");
        hisParamService.deleteParams(uuid);
        return RESTRetUtils.successObj();
    }

    @GetMapping(value = "/devs/hisparams/{uuid}/export/request")  //对String作序列化处理了
    public RESTRetBase getOperationRecordReport(@PathVariable int uuid) {
        String requestBody = thisParamsDao.getRequestBodyById(uuid);
        JsonObject returnData = new JsonParser().parse(requestBody).getAsJsonObject();
        return RESTRetUtils.successObj(returnData);
    }
}
