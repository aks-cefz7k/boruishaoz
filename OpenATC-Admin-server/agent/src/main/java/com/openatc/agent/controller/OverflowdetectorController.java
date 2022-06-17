package com.openatc.agent.controller;


import com.openatc.agent.model.Overflow;
import com.openatc.agent.model.OverflowDetector;
import com.openatc.agent.service.OverflowDetectorRepository;
import com.openatc.agent.service.OverflowRepository;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
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
        return RESTRetUtils.successObj(overflowRepository.findByPatternid(detectorid));
    }




    //添加瓶颈检测器id的路口相位信息
//    @PostMapping(value = "/overflowdetectorinfo")
//    public RESTRetBase AddOverflow(@RequestBody Overflow ofdEntity) {
//        Overflow rr = overflowRepository.save(ofdEntity);
//        return RESTRetUtils.successObj(rr);
//    }



    //更新某瓶颈检测器下所有的路口相位信息
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


}
