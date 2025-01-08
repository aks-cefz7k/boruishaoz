package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.service.impl.FaultServiceImpl;
import com.openatc.agent.utils.DateUtil;
import com.openatc.agent.utils.MyHttpUtil;
import com.openatc.agent.utils.PageInit;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class FaultController {
    private Logger logger = LoggerFactory.getLogger(FaultController.class);

    @Autowired
    FaultDao faultDao;

    @Autowired
    FaultServiceImpl faultService;

    @Autowired
    private DevController devController;

    Gson gson = new Gson();

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
        return RESTRetUtils.successObj(faults);
    }

    // 查询单个当前故障故障
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
    @DeleteMapping(value = "/fault/{id}")
    public RESTRetBase deleteFault(@PathVariable Long id) {
        Optional<Fault> optFault = faultDao.findById(id);
        Fault fault = optFault.get();
        if (fault != null) fault.setM_unFaultRenewTime(System.currentTimeMillis() / 1000);
        faultDao.save(fault);
        return RESTRetUtils.successObj();
    }

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
        if (fault.getM_unFaultOccurTime() != 0) {
            jsonObject.addProperty("m_unFaultOccurTime", DateUtil.longToString(fault.getM_unFaultOccurTime() * 1000));
        } else {
            jsonObject.addProperty("m_unFaultOccurTime", 0);
        }
        if (fault.getM_unFaultRenewTime() != 0) {
            jsonObject.addProperty("m_unFaultRenewTime", DateUtil.longToString(fault.getM_unFaultRenewTime() * 1000));
        } else {
            jsonObject.addProperty("m_unFaultRenewTime", 0);
        }
        return jsonObject;
    }

    @PostMapping(value = "/fault/history")
    public RESTRetBase getHistoryFault(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentId").getAsString();
        RESTRet<AscsBaseModel> restRet = null;
        try {
            restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentId);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        AscsBaseModel ascsBaseModel = restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        String url = "http://" + ip + ":8012/openatc/fault/history"; //读取历史故障文件
        String json = MyHttpUtil.doGet(url);
        return gson.fromJson(json, RESTRet.class);
    }





    @PostMapping(value = "/operation/history")
    public RESTRetBase getHistoryOperation(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentId").getAsString();
        RESTRet<AscsBaseModel> restRet = null;
        try {
            restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentId);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        AscsBaseModel ascsBaseModel = restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        String url = "http://" + ip + ":8012/openatc/operation/history"; //读取操作日志文件
        String json = MyHttpUtil.doGet(url);
        return gson.fromJson(json, RESTRet.class);
    }


    /**
     * @return
     * @throws
     * @Date 2021/9/1 9:49
     * @Descripation 查询指定agentId范围内的故障记录
     * 没有上传时间范围则查询所有范围内的故障记录
     */
    @PostMapping(value = "/fault/range")
    public RESTRetBase getRangeFault(@RequestBody JsonObject jsonObject) {
        if (jsonObject == null) {
            return RESTRetUtils.errorObj(false, IErrorEnumImplOuter.E_1000);
        }
        if (jsonObject.get("agentId") == null) {
            return RESTRetUtils.errorObj(false, IErrorEnumImplOuter.E_1000);
        }
        String agentId = jsonObject.get("agentId").getAsString();
        Integer pageNum = jsonObject.get("pageNum") == null ? 0 : jsonObject.get("pageNum").getAsInt();
        Integer pageRow = jsonObject.get("pageRow") == null ? 10 : jsonObject.get("pageRow").getAsInt();
        String beginTime = jsonObject.get("beginTime") == null ? null : jsonObject.get("beginTime").getAsString();
        String endTime = jsonObject.get("endTime") == null ? null : jsonObject.get("endTime").getAsString();
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
            predicateList.add(criteriaBuilder.equal(root.get("agentid"), agentId));
            predicateList.add(criteriaBuilder.between(root.get("m_unFaultOccurTime"), l[0], l[1]));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Page<Fault> faultList = faultDao.findAll(queryCondition, pageable);
        return RESTRetUtils.successObj(faultList);
    }
}
