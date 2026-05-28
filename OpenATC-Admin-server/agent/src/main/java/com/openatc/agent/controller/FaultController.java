package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.service.impl.FaultServiceImpl;
import com.openatc.agent.utils.DateUtil;
import com.openatc.agent.utils.PageInit;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class FaultController {
    private Logger logger = LoggerFactory.getLogger(FaultController.class);

    @Autowired
    FaultDao faultDao;

    @Autowired
    FaultServiceImpl faultService;

    @Autowired
    AscsDao ascsDao;

    @Autowired
    protected TokenUtil tokenUtil;

    Gson gson = new Gson();


    /**
     * @param pageNum:页码 pageRow:pageRow
     * @descripation 获取所有的故障
     * @Date 2021/9/16 14:16
     **/
    @GetMapping(value = "/fault/all")
    public RESTRetBase getAllFault(@RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer pageRow) {
        PageInit pageInit = new PageInit(pageNum, pageRow);
        Pageable pageRequest = PageRequest.of(pageInit.getPageNum(), pageInit.getPageRow());
        Specification<Fault> query = null;
        Page<Fault> faults = faultDao.findAll(query, pageRequest);
        PageOR<JsonObject> pageOR = new PageOR<>();
        pageOR.setTotal(faults.getTotalElements());
        pageOR.setContent(transformFaultList(faults.getContent()));
        return RESTRetUtils.successObj(pageOR);
    }


    /**
     * @param pageNum 页码
     * @param pageRow 每页数目
     * @return RESTRetBase
     * @throws
     * @deprecated 查询所有当前故障故障
     */
    @GetMapping(value = "/fault/current")
    public RESTRetBase getCurrentFaults(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageRow) {
        PageInit pageInit = new PageInit(pageNum, pageRow);
        Pageable pageRequest = PageRequest.of(pageInit.getPageNum(), pageInit.getPageRow());
        Specification<Fault> queryCondition = (Specification<Fault>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("m_unFaultOccurTime"), 0));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Page<Fault> faults = faultDao.findAll(queryCondition, pageRequest);
        PageOR<JsonObject> pageOR = new PageOR<>();
        pageOR.setTotal(faults.getTotalElements());
        pageOR.setContent(transformFaultList(faults.getContent()));
        return RESTRetUtils.successObj(pageOR);
    }

    // 查询单个设备当前故障故障
    @GetMapping(value = "/fault/{agentid}/current")
    public RESTRetBase getCurrentFaults(@PathVariable String agentid) {
        return RESTRetUtils.successObj(transformFaultList(faultDao.selectCurrentFaults(agentid)));
    }

    // 查询所有历史故障
    @GetMapping(value = "/fault/history")
    public RESTRetBase getHistoryFaults() {
        return RESTRetUtils.successObj(transformFaultList(faultDao.selectHistoryFaults()));
    }

    // 查询所有历史故障
    @GetMapping(value = "/fault/{agentid}/history")
    public RESTRetBase getHistoryFaults(@PathVariable String agentid) {
        return RESTRetUtils.successObj(transformFaultList(faultDao.selectHistoryFaults(agentid)));
    }

    // 删除故障
    @DeleteMapping(value = "/fault/del")
    public RESTRetBase deleteFault(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentid").getAsString();
        long id = jsonObject.get("id").getAsLong();
        faultDao.deleteByMwFaultIdAndAgentId(id, agentId);
        return RESTRetUtils.successObj();
    }


    /**
     * @return RESTRetBase
     * @Date 2021/9/1 9:49
     * @Descripation 查询指定agentId范围内的故障记录
     * 没有上传时间范围则查询所有范围内的故障记录
     */
    @PostMapping(value = "/fault/range")
    public RESTRetBase getRangeFault(@RequestBody JsonObject jsonObject) {
        PageOR<JsonObject> pageOR = new PageOR<>();
        if (jsonObject == null) {
            return RESTRetUtils.errorObj(false, IErrorEnumImplOuter.E_1000);
        }
        String agentId = jsonObject.get("agentId") == null ? "" : jsonObject.get("agentId").getAsString();
        String enumerate = jsonObject.get("enumerate") == null ? "" : jsonObject.get("enumerate").getAsString();
        int m_byFaultBoardType = jsonObject.get("m_byFaultBoardType") == null ? 0 : jsonObject.get("m_byFaultBoardType").getAsInt();
        int m_wFaultType = jsonObject.get("m_wFaultType") == null ? 0 : jsonObject.get("m_wFaultType").getAsInt();
        Integer pageNum = jsonObject.get("pageNum") == null ? 0 : jsonObject.get("pageNum").getAsInt();
        Integer pageRow = jsonObject.get("pageRow") == null ? 10 : jsonObject.get("pageRow").getAsInt();
        String beginTime = jsonObject.get("beginTime") == null ? "0" : jsonObject.get("beginTime").getAsString();
        String endTime = jsonObject.get("endTime") == null ? "0" : jsonObject.get("endTime").getAsString();
        long bTime;
        long eTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            bTime = format.parse(beginTime).getTime() / 1000;
            eTime = format.parse(endTime).getTime() / 1000;
        } catch (ParseException e) {
            bTime = 0;
            eTime = System.currentTimeMillis();
            logger.warn("日期格式不正确：" + e.getMessage());
            logger.warn("默认查询所有范围内的故障记录");
        }
        //将bTime和eTime加入到数组中是因为在lambda表达式中变量应该是final类型
        long[] l = new long[2];
        l[0] = bTime;
        l[1] = eTime;
        PageInit pageInit = new PageInit(pageNum, pageRow); //分页初始化
        Pageable pageable = PageRequest.of(pageInit.getPageNum(), pageInit.getPageRow());
        Specification<Fault> queryCondition = (Specification<Fault>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //添加查询条件
            if (!agentId.equals("")) {
                predicateList.add(criteriaBuilder.like(root.get("agentid"), "%" + agentId + "%"));
            }
            // 确认结果
            if (!enumerate.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("enumerate"), enumerate));
            }
            // 板卡类型
            if (m_byFaultBoardType != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("m_byFaultBoardType"), m_byFaultBoardType));
            }
            // 主故障类型
            if (m_wFaultType != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("m_wFaultType"), m_wFaultType));
            }
            predicateList.add(criteriaBuilder.equal(root.get("deleteFlag"), "0"));
            predicateList.add(criteriaBuilder.between(root.get("m_unFaultOccurTime"), l[0], l[1]));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Page<Fault> faultList = faultDao.findAll(queryCondition, pageable);
        List<JsonObject> list = transformFaultList(faultList.getContent());
        pageOR.setContent(list);
        pageOR.setTotal(faultList.getTotalElements());
        return RESTRetUtils.successObj(pageOR);
    }


    /**
     * @param enumerate 0:未处理 1:已忽略 2:已处理
     *                  pageNum
     *                  pageRow
     * @return RESTRetBase 返回所有未确认的故障记录
     * @descripation 获取对应状态的故障记录
     * @Date 2021/10/14 16:05
     **/
    @GetMapping("/fault/uncheck")
    public RESTRetBase getUnCheckedList(@RequestParam(value = "enumerate", defaultValue = "0") String enumerate,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageRow", defaultValue = "10") Integer pageRow) {

        if (!enumerate.equals("0") && enumerate.equals("1") && enumerate.equals("2")) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_1001);
        }
        PageOR<JsonObject> pageOR = new PageOR<>();
        PageInit pageInit = new PageInit(pageNum, pageRow); //分页初始化
        Pageable pageable = PageRequest.of(pageInit.getPageNum(), pageInit.getPageRow());
        Specification<Fault> queryCondition = (Specification<Fault>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //添加查询条件
            predicateList.add(criteriaBuilder.equal(root.get("enumerate"), enumerate));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Page<Fault> faultList = faultDao.findAll(queryCondition, pageable);
        List<JsonObject> list = transformFaultList(faultList.getContent());
        pageOR.setContent(list);
        pageOR.setTotal(faultList.getTotalElements());
        return RESTRetUtils.successObj(pageOR);
    }


    /**
     * @param jsonObject id->故障id  enumerate-> 0:未处理 1:已忽略 2:已处理
     * @return 返回操作结果 成功、失败
     * @descripation 对操作记录进行操作 确认/忽略
     * @Date 2021/10/14 16:48
     **/
    @PostMapping("fault/check")
    public RESTRetBase checkFault(HttpServletRequest httpServletRequest, @RequestBody JsonObject jsonObject) {
        String enumerate = jsonObject.get("enumerate").getAsString();
        if (!enumerate.equals("0") && enumerate.equals("1") && enumerate.equals("2")) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_1001);
        }
        Long id = jsonObject.get("id").getAsLong();
        String agentId = jsonObject.get("agentid").getAsString();
        String token = null;
        if (httpServletRequest != null) {
            token = httpServletRequest.getHeader("Authorization");
        }
        faultDao.updateFault(enumerate, tokenUtil.getUsernameFromToken(token), System.currentTimeMillis() / 1000, id, agentId);
        return RESTRetUtils.successObj();
    }


    /**
     * 故障时间处理方法
     */
    public List<JsonObject> transformFaultList(List<Fault> faults) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        for (Fault fault : faults) {
            JsonObject jsonObject = transformFault(fault);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    private JsonObject transformFault(Fault fault) {
        JsonObject jsonObject = gson.fromJson(gson.toJson(fault), JsonObject.class);
        jsonObject.addProperty("m_unFaultOccurTime", fault.getM_unFaultOccurTime() == 0 ? "0" : DateUtil.longToString(fault.getM_unFaultOccurTime() * 1000));
        jsonObject.addProperty("m_unFaultRenewTime", fault.getM_unFaultRenewTime() == 0 ? "0" : DateUtil.longToString(fault.getM_unFaultRenewTime() * 1000));
        jsonObject.addProperty("operationTime", (fault.getOperationTime() == null || fault.getOperationTime() == 0) ? "0" : DateUtil.longToString(fault.getOperationTime() * 1000));
        return jsonObject;
    }
    /**
     * 故障时间处理方法
     */

}
