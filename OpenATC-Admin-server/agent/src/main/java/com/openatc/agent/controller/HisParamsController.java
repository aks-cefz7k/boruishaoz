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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openatc.agent.model.THisParams;
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.agent.service.THisParamsDao;
import com.openatc.agent.utils.PageInit;
import com.openatc.agent.model.THisParamsVO;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.DateUtil;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    Gson gson = new Gson();

    @Autowired(required = false)
    protected HisParamServiceImpl hisParamService;

    @Autowired(required = false)
    protected THisParamsDao thisParamsDao;

    @Autowired(required = false)
    AscsDao mDao;

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
        Pageable pageable = PageRequest.of(pageNum - 1, pageRow, sort); //分页设置
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
     * @return RESTRetBase
     * @Title: getHisParamsRange
     * @Description: 分页获取指定操作记录列表
     */
    @PostMapping(value = "/devs/hisparams/range")
    public RESTRetBase getHisParamsRange(@RequestBody JsonObject jsonObject) {
        if (jsonObject == null) {
            return RESTRetUtils.errorObj(false, IErrorEnumImplOuter.E_1000);
        }
        Object agentId = jsonObject.get("agentId") == null ? "" : jsonObject.get("agentId").getAsString();
        String name = jsonObject.get("name") == null ? "" : jsonObject.get("name").getAsString();
        String infotype = jsonObject.get("infotype") == null ? "" : jsonObject.get("infotype").getAsString();
        String operator = jsonObject.get("operator") == null ? "" : jsonObject.get("operator").getAsString();
        String status = jsonObject.get("status") == null ? "" : jsonObject.get("status").getAsString();
        Integer pageNum = jsonObject.get("pageNum") == null ? 0 : jsonObject.get("pageNum").getAsInt();
        Integer pageRow = jsonObject.get("pageRow") == null ? 10 : jsonObject.get("pageRow").getAsInt();
        String beginTime = jsonObject.get("beginTime") == null ? "" : jsonObject.get("beginTime").getAsString();
        String endTime = jsonObject.get("endTime") == null ? "" : jsonObject.get("endTime").getAsString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PageInit pageInit = new PageInit(pageNum, pageRow); //分页初始化
        Pageable pageable = PageRequest.of(pageInit.getPageNum(), pageInit.getPageRow());
        Specification<THisParams> queryCondition = (Specification<THisParams>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //添加查询条件
            if (!agentId.equals("")) {
                predicateList.add(criteriaBuilder.like(root.get("agentid"), "%" + agentId + "%"));
            }
            // 用户名
            if (!operator.equals("")) {
                predicateList.add(criteriaBuilder.like(root.get("operator"), "%" + operator + "%"));
            }
            // 消息类型
            if (!infotype.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("infotype"), infotype));
            }
            // 返回状态
            if (!status.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (!beginTime.equals("") && !endTime.equals("")) {
                try {
                    predicateList.add(criteriaBuilder.between(root.get("opertime"), format.parse(beginTime), format.parse(endTime)));
                } catch (ParseException e) {
                    logger.warning("时间转换异常");
                }
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Page<THisParams> tHisParams = thisParamsDao.findAll(queryCondition, pageable);
        if (tHisParams.getSize() == 0) {
            return RESTRetUtils.errorObj(E_2004);
        }
        PageOR pageOR = new PageOR();
        pageOR.setTotal(tHisParams.getTotalElements());
        List<THisParams> content = tHisParams.getContent();
        List<THisParamsVO> targetList = new ArrayList<THisParamsVO>();
//        //  路口名称
//        if (!name.equals("")) {
//            List<AscsBaseModel> deviceList = getDeviceListByName(name);
//            for (AscsBaseModel dev : deviceList) {
//                String dAgentid = dev.getAgentid();
//                for (THisParams param: content) {
//                    String cAgentid = param.getAgentid();
//                    if (dAgentid.equals(cAgentid)) {
//                        targetList.add(param);
//                    }
//                }
//            }
//        } else {
//            targetList = content;
//        }
        for (THisParams param: content) {
            AscsBaseModel ascsBaseModel = mDao.getAscsByID(param.getAgentid());
            String agentName = ascsBaseModel.getAgentid();
            if (ascsBaseModel != null) {
                agentName = ascsBaseModel.getName();
            }
            THisParamsVO vo = new THisParamsVO(param, agentName);
            targetList.add(vo);
        }
        pageOR.setContent(targetList);
        return RESTRetUtils.successObj(pageOR);
    }

    /**
     * @Author: yangyi
     * @Date: 2021/11/19 13:38
     * @Description: 根据路口名获取路口列表
     */
    public List<AscsBaseModel> getDeviceListByName (String name) {
        List<AscsBaseModel> ascsBaseModels = new ArrayList<>();
        String sql = "SELECT id, thirdplatformid, platform, gbid, firm, agentid, protocol, geometry, type, status, descs, name,jsonparam, case (LOCALTIMESTAMP - lastTime)< '5 min' when 'true' then 'UP' else 'DOWN' END AS state,lastTime,sockettype FROM dev where name like '%" + name+ "%' ORDER BY agentid";
        try {
            ascsBaseModels = mDao.getDevByPara(sql);
        } catch (Exception e){
            logger.warning("Error: getDeviceListByName");
        }
        return ascsBaseModels;
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
        //获取requestBody
        String requestBody = thisParamsDao.getRequestBodyById(uuid);
        JsonObject requsetBodyJson = new JsonParser().parse(requestBody).getAsJsonObject();
        //获取reponseBody
        String reponseBody = thisParamsDao.getResponseBodyById(uuid);
        JsonObject reponseBodyJson = new JsonParser().parse(reponseBody).getAsJsonObject();

        THisParams tHisParams = thisParamsDao.getPartThisParamsById(uuid);
        JsonObject result = new JsonObject();
        //t.id, t.operator, t.opertime, t.source, t.agentid, t.infotype, t.status
        result.addProperty("id", tHisParams.getId());
        result.addProperty("operator", tHisParams.getId());
        result.addProperty("opertime", DateUtil.date2str(tHisParams.getOpertime()));
        result.addProperty("source", tHisParams.getSource());
        result.addProperty("agentid", tHisParams.getAgentid());
        result.addProperty("infotype", tHisParams.getInfotype());
        result.addProperty("status", tHisParams.getStatus());
        result.add("requestbody", requsetBodyJson);
        result.add("responsebody", reponseBodyJson);
        return RESTRetUtils.successObj(result);
    }
}
