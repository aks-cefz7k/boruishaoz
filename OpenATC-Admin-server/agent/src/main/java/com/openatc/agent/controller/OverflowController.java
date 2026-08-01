package com.openatc.agent.controller;


import com.openatc.agent.model.Overflow;
import com.openatc.agent.model.OverflowDetector;
import com.openatc.agent.model.OverflowStatus;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.OptService;
import com.openatc.agent.service.OverflowDetectorRepository;
import com.openatc.agent.service.OverflowRepository;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.StatusPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.openatc.core.common.IErrorEnumImplOuter.E_9003;
import static com.openatc.core.common.IErrorEnumImplOuter.E_9004;


@RestController
public class OverflowController {

    private final String OverflowDetOn = "1";
    private final String OverflowDetOff = "2";

    private Logger logger = LoggerFactory.getLogger(OverflowController.class);

    @Autowired
    private OverflowDetectorRepository overflowDetectorRepository;

    @Autowired
    private OverflowRepository overflowRepository;

    @Autowired
    private OptService optService;

    @Autowired
    AscsDao ascsDao;

    //查询整个区域
    @GetMapping(value = "/overflowdetector")
    public RESTRetBase GetOverflowdetetor() {
        List<OverflowDetector> OverflowDetectors = overflowDetectorRepository.findAll(new Sort(Sort.Direction.ASC, "id"));

        List<AscsBaseModel> ascs = ascsDao.getAscs();

        // 查询路口名和路口状态
        for (OverflowDetector OverflowDetector : OverflowDetectors) {
            for (Overflow overflow : OverflowDetector.getOverflows()) {
                for (AscsBaseModel asc : ascs) {
                    if (asc.getAgentid().equals(overflow.getIntersectionid())) {
                        overflow.setIntersectionname(asc.getName());
                        overflow.setIntersectionstate(asc.getState());
                        break;
                    }
                }
            }
        }
        return RESTRetUtils.successObj(OverflowDetectors);
    }

    //更新区域信息
    @PutMapping(value = "/overflowdetector")
    public RESTRetBase UpdateOverflowdetector(@RequestBody OverflowDetector ofdEntity) {
        OverflowDetector rr = null;
        Optional<OverflowDetector> byId = overflowDetectorRepository.findById(ofdEntity.getId());
        if (ofdEntity.getOverflows() == null) {
            OverflowDetector OverflowDetector = byId.get();
            OverflowDetector.setOverflowDetectorId(ofdEntity.getOverflowDetectorId());
            OverflowDetector.setType(ofdEntity.getType());
            OverflowDetector.setStatus(ofdEntity.getStatus());
            OverflowDetector.setDescription(ofdEntity.getDescription());
            rr = overflowDetectorRepository.save(OverflowDetector);
        } else {
            rr = overflowDetectorRepository.save(ofdEntity);
        }
        return RESTRetUtils.successObj(rr);
    }

    //添加区域信息
    @PostMapping(value = "/overflowdetector")
    public RESTRetBase AddOverflowdetector(@RequestBody OverflowDetector ofdEntity) {
        // 查找是否有重命名
        List<Integer> ids = overflowDetectorRepository.findOverflowDetbyName(ofdEntity.getDescription());
        if (ids.size() > 0)
            return RESTRetUtils.errorObj(E_9004);

        OverflowDetector rr = overflowDetectorRepository.save(ofdEntity);
        return RESTRetUtils.successObj(rr);
    }

    //删除
    @DeleteMapping(value = "/overflowdetector/{id}")
    public RESTRetBase DeleteOverflowdetector(@PathVariable Long id) {
        Optional<OverflowDetector> tt = overflowDetectorRepository.findById(id);
        OverflowDetector aa = tt.get();
        if (aa.getStatus() != null && aa.getStatus().equals(OverflowDetOn))
            return RESTRetUtils.errorObj(E_9003);

        overflowDetectorRepository.deleteById(id);
        return RESTRetUtils.successObj(aa);
    }


    //得到某检测器id的所有路口相位信息
    @GetMapping(value = "/overflowdetectorinfo/{detectorid}")
    public RESTRetBase GetOverflowdetectorinfo(@PathVariable Long detectorid) {
        logger.info("GetOverflowdetectorinfo");
        //获取某区域所有的配置信息
        return RESTRetUtils.successObj(overflowRepository.findByPatternid(detectorid));
    }


    //添加区域id的路口相位信息
//    @PostMapping(value = "/overflowdetectorinfo")
//    public RESTRetBase AddOverflow(@RequestBody Overflow ofdEntity) {
//        Overflow rr = overflowRepository.save(ofdEntity);
//        return RESTRetUtils.successObj(rr);
//    }

    //更新某区域下所有的路口相位信息
    @PutMapping(value = "/overflow/{detectionid}")
    public RESTRetBase UpdateOverflow(@RequestBody List<Overflow> ofdEntity, @PathVariable Long detectionid) {
        //更新操作，前端每次更新一个瓶颈控制id下的一系列路口相位
        //每次更新，应删除所属瓶颈控制下的所有信息，然后再添加信息
        List<Overflow> tt = overflowRepository.findByPatternid(detectionid);
        for (Overflow of : tt) {
            Long id = of.getId();
            overflowRepository.deleteById(id);
        }
        List<Overflow> rg = ofdEntity;
        //System.out.println("11");
        return RESTRetUtils.successObj(overflowRepository.saveAll(rg));
    }

    //开启溢出控制
    @PostMapping(value = "/overflow/control/{id}")
    public RESTRetBase OverflowControl(@PathVariable long id) {
        // 判断是否为空方案
        List<Overflow> overflowList = overflowRepository.findByPatternid(id);
        if (overflowList.size() == 0)
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9005);

        Map<String, StatusPattern> statusPatternMap = new HashMap<>();
        //根据id开启溢出控制
        for (Overflow ov : overflowList) {
            String agentid = ov.getIntersectionid();
            StatusPattern statusPattern = optService.OptStatusPattern(ov);
            if (statusPattern == null) {
                //没有取到方案
                logger.warn("Can not get pattern, agentid = " + agentid);
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9001);
            }
            statusPatternMap.put(agentid, statusPattern);
        }

        // 状态获取成功，开启控制,state: 执行结果, 0：执行失败, 1：执行成功，2：恢复失败，3：恢复成功
        for (Map.Entry<String, StatusPattern> entry : statusPatternMap.entrySet()) {
            RESTRet restRet = optService.InterruptPattern(entry.getValue(), entry.getKey());
            if (restRet.isSuccess() == false) {
                logger.warn("Device overflow control failed, agentid = " + entry.getKey());
                overflowRepository.updateIsopenByIntersectionid(entry.getKey(), 0);
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9001);
            } else {
                overflowRepository.updateIsopenByIntersectionid(entry.getKey(), 1);
            }
        }

        // 设置方案开启状态
        overflowDetectorRepository.updateStatusById(id, OverflowDetOn);
        return RESTRetUtils.successObj();
    }


    //恢复自主控制
    @PostMapping(value = "/overflow/control/off/{id}")
    public RESTRetBase OffOverflowControl(@PathVariable long id) {
        // 判断是否为空方案
        List<Overflow> overflowList = overflowRepository.findByPatternid(id);
        if (overflowList.size() == 0)
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9005);

        //根据id恢复自主控制
        for (Overflow ov : overflowList) {
            // 已开启的路口发送恢复控制,state: 执行结果, 0：执行失败, 1：执行成功，2：恢复失败，3：恢复成功
            if (ov.getState() == 1) {
                String agentid = ov.getIntersectionid();
                RESTRet restRet = optService.AutoControl(agentid);
                if (restRet.isSuccess() == false) {
                    overflowRepository.updateIsopenByIntersectionid(agentid, 2);
                    return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9002);
                }
                overflowRepository.updateIsopenByIntersectionid(agentid, 3);
            }
        }

        // 设置方案关闭状态
        overflowDetectorRepository.updateStatusById(id, OverflowDetOff);
        return RESTRetUtils.successObj();
    }

    /**
     * 获取当前瓶颈控制的路口状态
     * @param id 瓶颈方案ID
     * @return
     * @author: zhangwenchao
     */
    @GetMapping(value = "/overflow/status/{id}")
    public RESTRetBase getVipRouteStatus(@PathVariable long id) {
        List<OverflowStatus> overflowStatusList = new ArrayList<>();

        // 判断是否为空方案
        List<Overflow> overflowList = overflowRepository.findByPatternid(id);
        if (overflowList.size() == 0)
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9005);

        // 根据实时状态更新路口状态
        for (Overflow ov : overflowList) {
            OverflowStatus overflowStatus = new OverflowStatus();
            String agentid = ov.getIntersectionid();
            overflowStatus.setAgentid(agentid);

            AscsBaseModel devs = ascsDao.getAscsByID(agentid);
            if(devs.getState().equals("DOWN")) {
                overflowStatus.setControl(-1);
            }
            else{
                StatusPattern statusPattern = optService.curStatusPattern(agentid);
                if (statusPattern == null) {
                    //没有取到方案
                    overflowStatus.setControl(-1);
                }
                else{ // 设置当前控制方式
                    overflowStatus.setControl(statusPattern.getControl());
                }
            }

            overflowStatusList.add(overflowStatus);
        }

        return RESTRetUtils.successObj(overflowStatusList);
    }
}
