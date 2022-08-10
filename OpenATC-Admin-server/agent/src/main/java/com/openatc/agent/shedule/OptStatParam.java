//package com.openatc.agent.shedule;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.openatc.agent.controller.MessageController;
//import com.openatc.agent.model.TStat;
//import com.openatc.agent.service.AscsDao;
//import com.openatc.agent.service.TStatDao;
//import com.openatc.comm.data.MessageData;
//import com.openatc.core.model.DevCommError;
//import com.openatc.core.model.RESTRet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.net.SocketException;
//import java.text.ParseException;
//import java.util.List;
//
//@Component
//public class OptStatParam {
//    @Autowired
//    MessageController messageController;
//    @Autowired
//    AscsDao ascsDao;
//    @Autowired
//    private TStatDao tStatDao;
//    private Gson gson = new Gson();
//
//
//    @Scheduled(cron = "0  1  0 * * ?")
//    public void getOptStatParam() throws SocketException, ParseException {
//        List<String> agentids = ascsDao.getAllAgentids();
//        for (String agentid : agentids) {
//            MessageData messageData = new MessageData(agentid, "get-request", "system/optstatparam", new JsonObject());
//            RESTRet restRet = messageController.postDevsMessage(null, messageData);
//            if (restRet.getData() instanceof DevCommError) continue;
//            MessageData md = (MessageData) restRet.getData();
//            JsonObject data = (JsonObject) md.getData();
//            JsonArray tstats = data.get("tstats").getAsJsonArray();
//            for (JsonElement tstatJson : tstats) {
//                TStat tStat = gson.fromJson(tstatJson, TStat.class);
//                tStat.setAgentid(agentid);
//                tStatDao.save(tStat);
//            }
//        }
//    }
//}
