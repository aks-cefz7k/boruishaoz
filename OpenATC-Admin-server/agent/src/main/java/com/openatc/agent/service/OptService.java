package com.openatc.agent.service;

import com.google.gson.JsonObject;
import com.openatc.agent.controller.MessageController;
import com.openatc.agent.controller.OverflowController;
import com.openatc.agent.model.ControlInterrupt;
import com.openatc.agent.model.OptRing;
import com.openatc.agent.model.Overflow;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;
import com.openatc.model.model.ControlPattern;
import com.openatc.model.model.StatusPattern;
import com.openatc.model.model.StatusPatternPhase;
import com.openatc.model.model.StatusRing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptService {

    private Logger logger = LoggerFactory.getLogger(OptService.class);

    @Autowired
    private MessageController messageController;

    // 执行瓶颈恢复控制
    public RESTRet AutoControl(String agentid) {
        //定周期是用于切方案，恢复自主控制无法切换方案，恢复信号机本来运行的方案
        ControlPattern controlMsg = new ControlPattern();
        controlMsg.setControl(0);
        controlMsg.setTerminal(1);
        controlMsg.setValue(0);
        return messageController.SetControlPattern(agentid,controlMsg);
    }

    // 根据pattern对象得到干预对象方案
    // overflowList：一组待优化路口相位
    public RESTRet InterruptPattern(StatusPattern pattern, String agentid)
    {
        List<StatusRing> rings = pattern.getRings();
        List<StatusPatternPhase> phases = pattern.getPhase();
        int cycle = pattern.getCycle();

        ControlInterrupt controlInterrupt = new ControlInterrupt();

        List<List<OptRing>> ringlst = new ArrayList<>();

        for(StatusRing r: rings)
        {
            List<OptRing> ring = new ArrayList<>();
            List<Integer> seq = r.getSequence();
            for(Integer id : seq)
            {
                OptRing optRing = new OptRing();
                optRing.setId(id);
                optRing.setValue(phases.get(id - 1).getSplit());
                optRing.setMode(0);
                ring.add(optRing);
            }
            ringlst.add(ring);
        }
        controlInterrupt.setCycle(cycle);
        controlInterrupt.setOffset(0);
        controlInterrupt.setRings(ringlst);

        RESTRet restRet = messageController.SetControlInterrupt(agentid,controlInterrupt);
        if(restRet.isSuccess()){
            MessageData messageData = (MessageData) restRet.getData();
            JsonObject data = messageData.getData().getAsJsonObject();
            if(data.get("return")!= null){
                if(data.get("return").getAsString().equals("success")){
                    JsonObject responsedata = new JsonObject();
                    responsedata.addProperty("success",0);
                    messageData.setData(responsedata);
                }
            }

        }

        return restRet;
    }

    // 获取实时的路口方案
    public StatusPattern curStatusPattern(String agentid)
    {
        return messageController.GetStatusPattern(agentid);
    }

    // 获取优化后的路口方案
    public StatusPattern OptStatusPattern(Overflow overflow)
    {
        String agentid = overflow.getIntersectionid().toString();   //设备id
        Integer phaseid = Integer.valueOf(overflow.getPhaseid());   //相位id
        int controltime = overflow.getControltime();                //相位控制时间


        StatusPattern tempPattern = messageController.GetStatusPattern(agentid) ;
        if(tempPattern == null){
            logger.warn("GetStatusPattern is Null! Overflow:" + overflow);
            return null;
        }

        List<List<Integer>> stages = tempPattern.getStages();
        if(stages == null){
            logger.warn("Stages is Null! Overflow:" + overflow + "StatusPattern:" + tempPattern);
            return null;
        }

        List<StatusPatternPhase> phases = tempPattern.getPhase();
        double factor = 1.0;
        double param = 0.0;

        Map<Integer, Integer> phasesAddTime = new HashMap<>();
        for(List<Integer> lst:stages)
        {
            if(lst.contains(phaseid)){
                param += 1;
                for(Integer p:lst)
                {
                    phasesAddTime.put(p,0);
                }
            }
        }

        factor = factor / param;
        Integer optCycle = tempPattern.getCycle();
        if(optCycle == null){
            logger.warn("Cycle is Null! Overflow:" + overflow + "StatusPattern:" + tempPattern);
            return null;
        }

        for(List<Integer> lst:stages)
        {
            if(lst.contains(phaseid)){
                optCycle += (int)(controltime * factor);
                for(Integer p : lst)
                {
                    Integer addTime = phasesAddTime.get(p);
                    addTime += (int)(controltime * factor);
                    phasesAddTime.put(p,addTime);
                }
            }
        }
        tempPattern.setCycle(optCycle);

        for(StatusPatternPhase p : phases) {
            int id = p.getId();
            if(phasesAddTime.containsKey(id))
            {
                int split = p.getSplit() + phasesAddTime.get(id);
                p.setSplit(split);
            }
        }
        return tempPattern;
    }


    //@brief 对瓶颈id下的一组路口进行溢出优化
    //@overflowList：一组待优化路口相位
//    public void OptPatterns(List<Overflow> overflowList)
//    {
//        for(int i = 0; i < overflowList.size(); i++)
//        {
//            Overflow temp = overflowList.get(i);
//            OptStatusPattern(temp);
//        }
//    }


//
//
//    public RESTRet HttpPost(String uri, JSONObject js)
//    {
//        //设置请求头
//        HttpHeaders headers = new HttpHeaders();
//        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(mediaType);
//        headers.add("Accept", MediaType.ALL_VALUE);
//
//        //封装请求行和请求体
//        HttpEntity<String> entity = new HttpEntity<>(js.toString(), headers);
//        //发送请求
//        String url = "http://localhost:" + uri + "/ openatc/devs/message";
//        RESTRet res =  restTemplate.postForObject(url, entity, RESTRet.class);
//        return res;
//    }


}











