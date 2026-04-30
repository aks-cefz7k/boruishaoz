package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.FaultDao;
import com.openatc.comm.data.MessageData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class FaultServiceImpl {

    private Logger log = Logger.getLogger(FaultServiceImpl.class.toString());

    @Autowired
    FaultDao faultDao;

    @Autowired(required = false)
    AscsDao ascsDao;

    @Value("${fault.filepath}")
    private String faultFilePath;

    @Value("${ftpclient.port}")
    private int port;

    Gson gson = new Gson();


    public void processFaultMessage(MessageData msg) {
        String agentid = msg.getAgentid();
        Fault[] m_faultDeque = gson.fromJson(msg.getData().getAsJsonObject().getAsJsonArray("m_FaultDeque"), Fault[].class);

        for (Fault fault : m_faultDeque) {
            fault.setEnumerate("0");
            fault.setAgentid(agentid);
            fault.setModel("asc");
            fault.setEventType("status/fault");
            fault.setDeleteFlag("0");
            faultDao.save(fault);
        }
    }
    /**
     * @Author: yangyi
     * @Date: 2021/11/23 10:56
     * @Description: 重组msg,去掉不需要的msg部分
     */
    public MessageData filterMsg(MessageData msg) {
        String agentid = msg.getAgentid();
        Fault[] m_faultDeque = gson.fromJson(msg.getData().getAsJsonObject().getAsJsonArray("m_FaultDeque"), Fault[].class);
        List<Fault> faultList = new ArrayList<Fault>();
        for (Fault fault : m_faultDeque) {
            Long m_wFaultID = fault.getM_wFaultID();
            List<Fault> list = faultDao.findByAgentidAndM_wFaultID(agentid,m_wFaultID);
            if (list != null && list.size() > 0) { // 记录已存在
                Fault fa = list.get(0);
                String enuneRate = fa.getEnumerate(); //
                if (enuneRate.equals("1") || enuneRate.equals("2")) { //已处理、已忽略
                    continue;
                } else {
                    faultList.add(fault);
                }
            } else {
                faultList.add(fault);
            }
        }
        Fault[] targetFaultList = faultList.toArray(new Fault[0]);
        // 重新组装msg
        JsonElement jeData = msg.getData().deepCopy();
        jeData.getAsJsonObject().remove("m_FaultDeque");
        jeData.getAsJsonObject().add("m_FaultDeque", gson.toJsonTree(targetFaultList, Fault[].class));
        msg.setData(jeData);
        return msg;
    }
}
