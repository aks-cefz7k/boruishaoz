package com.openatc.agent.controller;


import com.openatc.agent.model.Overflow;
import com.openatc.agent.model.OverflowDetector;
import com.openatc.agent.service.OptService;
import com.openatc.agent.service.OverflowDetectorRepository;
import com.openatc.agent.service.OverflowRepository;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.StatusPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.openatc.core.common.IErrorEnumImplOuter.E_9003;


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

    //查询整个区域
    @GetMapping(value = "/overflowdetector")
    public RESTRetBase GetOverflowdetetor(){
        List<OverflowDetector> all = overflowDetectorRepository.findAll();
        return RESTRetUtils.successObj(all);
    }

    //更新区域信息
    @PutMapping(value = "/overflowdetector")
    public RESTRetBase UpdateOverflowdetector(@RequestBody OverflowDetector ofdEntity){
        OverflowDetector rr = null;
        Optional<OverflowDetector> byId = overflowDetectorRepository.findById(ofdEntity.getId());
        if(ofdEntity.getOverflows() == null)
        {
            OverflowDetector overflowDetector = byId.get();
            overflowDetector.setOverflowDetectorId(ofdEntity.getOverflowDetectorId());
            overflowDetector.setType(ofdEntity.getType());
            overflowDetector.setStatus(ofdEntity.getStatus());
            overflowDetector.setDescription(ofdEntity.getDescription());
            rr = overflowDetectorRepository.save(overflowDetector);
        }else{
            rr = overflowDetectorRepository.save(ofdEntity);
        }
        return RESTRetUtils.successObj(rr);
    }

    //添加区域信息
    @PostMapping(value = "/overflowdetector")
    public RESTRetBase AddOverflowdetector(@RequestBody OverflowDetector ofdEntity){
        OverflowDetector rr = overflowDetectorRepository.save(ofdEntity);
        return RESTRetUtils.successObj(rr);
    }

    //删除
    @DeleteMapping(value = "/overflowdetector/{id}")
    public RESTRetBase DeleteOverflowdetector(@PathVariable Long id){
        Optional<OverflowDetector> tt = overflowDetectorRepository.findById(id);
        OverflowDetector aa = tt.get();
        if(aa.getStatus() != null && aa.getStatus().equals(OverflowDetOn))
            return RESTRetUtils.errorObj(E_9003);

        overflowDetectorRepository.deleteById(id);
        return RESTRetUtils.successObj(aa);
    }



    //得到某检测器id的所有路口相位信息
    @GetMapping(value = "/overflowdetectorinfo/{detectorid}")
    public RESTRetBase GetOverflowdetectorinfo(@PathVariable Long detectorid){
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
    public RESTRetBase UpdateOverflow(@RequestBody List<Overflow> ofdEntity,@PathVariable Long detectionid){
        //更新操作，前端每次更新一个瓶颈控制id下的一系列路口相位
        //每次更新，应删除所属瓶颈控制下的所有信息，然后再添加信息
        List<Overflow> tt = overflowRepository.findByPatternid(detectionid);
        for(Overflow of : tt)
        {
            Long id = of.getId();
            overflowRepository.deleteById(id);
        }
        List<Overflow> rg = ofdEntity;
        //System.out.println("11");
        return RESTRetUtils.successObj(overflowRepository.saveAll(rg));
    }

    //开启溢出控制
    @PostMapping (value = "/overflow/control/{id}")
    public RESTRetBase OverflowControl(@PathVariable long id)
    {
        //根据id开启溢出控制
        List<Overflow>  overflowList = new ArrayList<>();
        overflowList = overflowRepository.findByPatternid(id);
        List<String> error_offlines = new ArrayList<>();
        List<String> error_fails = new ArrayList<>();


        for(Overflow ov: overflowList)
        {
            String agentid = ov.getIntersectionid();
            StatusPattern statusPattern = optService.OptStatusPattern(ov);
            if(statusPattern == null){
                //没有取到方案
                error_offlines.add(agentid);
                logger.warn("Device not on line, agentid = " + agentid);
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9001);
            }

            RESTRet restRet = optService.InterruptPattern(statusPattern, agentid);
            if(restRet.isSuccess() == false){
                logger.warn("Device overflow control failed, agentid = " + agentid);
                error_fails.add(agentid);
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9001);
            }else{
                overflowRepository.updateIsopenByIntersectionid(agentid,true);
            }

        }

        // todo 设备控制需要重构为单路口方式
        overflowDetectorRepository.updateStatusById(id,OverflowDetOn);
        return RESTRetUtils.successObj();
    }


    //恢复自主控制
    @PostMapping(value = "/overflow/control/off/{id}")
    public RESTRetBase OffOverflowControl(@PathVariable long id)
    {
        //根据id恢复自主控制
        List<Overflow>  overflowList = new ArrayList<>();
        List<String> error_fails = new ArrayList<>();
        overflowList = overflowRepository.findByPatternid(id);
        for(Overflow ov: overflowList)
        {
            String agentid = ov.getIntersectionid();
            RESTRet restRet = optService.AutoControl(agentid);
            if(restRet.isSuccess() == false){
                error_fails.add(agentid);
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_9002);
            }
        }

        // todo 设备控制需要重构为单路口方式
        overflowDetectorRepository.updateStatusById(id,OverflowDetOff);
        return RESTRetUtils.successObj();
    }
}
