package com.openatc.agent.service;

import com.alibaba.fastjson.JSONObject;
import com.openatc.agent.model.*;
import com.openatc.core.model.RESTRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String devsuri;


    public OptDev<ControlMsg> AutoControl(String agentid) {
        //定周期是用于切方案，恢复自主控制无法切换方案，恢复信号机本来运行的方案
        OptDev<ControlMsg> controlMsgOptDev = new OptDev<>();
        controlMsgOptDev.setAgentid(agentid);
        controlMsgOptDev.setInfotype("control/pattern");
        controlMsgOptDev.setOperation("set-request");
        controlMsgOptDev.setUser("admin");
        controlMsgOptDev.setSource("center");
        ControlMsg controlMsg = new ControlMsg();
        controlMsg.setControl(0);
        controlMsg.setTerminal(1);
        controlMsg.setValue(0);
        controlMsgOptDev.setData(controlMsg);
        return controlMsgOptDev;
    }

    //@brief 根据pattern对象得到干预对象方案
    //@overflowList：一组待优化路口相位
    public OptDev<OptProgram> GetInterruptPattern(AscPattern pattern, String agentid)
    {
        //String agentid = pattern.getAgentid();
        List<OptRings> rings = pattern.getRings();
        List<AscPhase> phases = pattern.getPhase();
        int cycle = pattern.getCycle();
        OptDev optDev = new OptDev();
        optDev.setAgentid(agentid);
        optDev.setUser("admin");
        optDev.setInfotype("control/interrupt");
        optDev.setSource("center");
        optDev.setOperation("set-request");
        OptProgram optProgram = new OptProgram();

        List<List<OptRing>> ringlst = new ArrayList<>();

        for(OptRings r: rings)
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
        optProgram.setCycle(cycle);
        optProgram.setOffset(0);
        optProgram.setRings(ringlst);
        optDev.setData(optProgram);
        return optDev;
    }



    public AscPattern OptPattern(Overflow overflow)
    {
        String agentid = overflow.getIntersectionid().toString();   //设备id
        Integer phaseid = Integer.valueOf(overflow.getPhaseid());   //相位id
        int controltime = overflow.getControltime();                //相位控制时间
        AscPattern tempPattern = GetPattern(agentid);
        if(tempPattern == null){
            return null;
        }
        List<List<Integer>> stages = tempPattern.getStages();
        List<AscPhase> phases = tempPattern.getPhase();
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
        int optCycle = tempPattern.getCycle();
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

        for(AscPhase p : phases) {
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
    public void OptPatterns(List<Overflow> overflowList)
    {
        for(int i = 0; i < overflowList.size(); i++)
        {
            Overflow temp = overflowList.get(i);
            OptPattern(temp);
        }
    }



    public AscPattern GetPattern(String devid)
    {
        JSONObject js = new JSONObject();
        js.put("agentid", devid);
        js.put("infotype", "status/pattern");
        js.put("operation", "get-request");

        RESTRet response = HttpPost(devsuri, js);
        if(response.isSuccess() == false)
            return null;
        Map data = (Map) ((Map) response.getData()).get("data");

        return JSONObject.toJavaObject(new JSONObject(data), AscPattern.class);
    }


    public RESTRet HttpPost(String uri, JSONObject js)
    {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.ALL_VALUE);

        //封装请求行和请求体
        HttpEntity<String> entity = new HttpEntity<>(js.toString(), headers);
        //发送请求
        String url = "http://localhost:" + uri + "/openatc/devs/message";
        RESTRet res =  restTemplate.postForObject(url, entity, RESTRet.class);
        return res;
    }


}











