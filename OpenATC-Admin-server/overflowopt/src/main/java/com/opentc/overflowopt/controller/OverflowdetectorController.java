package com.opentc.overflowopt.controller;


import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.opentc.overflowopt.entity.Overflow;
import com.opentc.overflowopt.entity.OverflowDetector;
import com.opentc.overflowopt.repository.OverflowDetectorRepository;
import com.opentc.overflowopt.repository.OverflowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class OverflowdetectorController {

    private Logger logger = LoggerFactory.getLogger(OverflowoptController.class);

    @Autowired
    private OverflowDetectorRepository overflowDetectorRepository;

    @Autowired
    private OverflowRepository overflowRepository;

    //查询整个瓶颈检测器
    @GetMapping(value = "/overflowdetector")
    public RESTRetBase GetOverflowdetetor(){
        System.out.println("/overflowdetector");
        List<OverflowDetector> all = overflowDetectorRepository.findAll();
        return RESTRetUtils.successObj(all);
    }

    //更新瓶颈检测器信息
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

    //添加瓶颈检测器信息
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
        overflowDetectorRepository.deleteById(id);
        return RESTRetUtils.successObj(aa);
    }

    //得到某检测器id的所有路口相位信息
    @GetMapping(value = "/overflowdetectorinfo/{detectorid}")
    public RESTRetBase GetOverflowdetectorinfo(@PathVariable Long detectorid){
        logger.info("GetOverflowdetectorinfo");
        //获取某瓶颈检测器所有的配置信息
        return RESTRetUtils.successObj(overflowRepository.findByDetectionid(detectorid));
    }

    //添加瓶颈检测器id的路口相位信息
    @PostMapping(value = "/overflowdetectorinfo")
    public RESTRetBase AddOverflow(@RequestBody Overflow ofdEntity) {
        Overflow rr = overflowRepository.save(ofdEntity);
        return RESTRetUtils.successObj(rr);
    }

    //更新某瓶颈检测器下所有的路口相位信息
    @PutMapping(value = "/overflow")
    public RESTRetBase UpdateOverflow(@RequestBody List<Overflow> ofdEntity){
        Iterable<Overflow> rg = ofdEntity;
        //System.out.println("11");
        return RESTRetUtils.successObj(overflowRepository.saveAll(rg));
    }

    //删除某检测器下的某id的路口相位信息
    @DeleteMapping(value = "/overflowdetectorinfo/{id}")
    public RESTRetBase DeleteOverflowdetectorinfo(@PathVariable Long id){
        Optional<Overflow> tt = overflowRepository.findById(id);
        Overflow aa = tt.get();
        overflowRepository.deleteById(id);
        return RESTRetUtils.successObj(aa);
    }
}