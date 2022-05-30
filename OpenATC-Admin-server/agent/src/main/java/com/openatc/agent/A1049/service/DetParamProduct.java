package com.openatc.agent.A1049.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.A1049.model.A1049Message;
import com.openatc.agent.A1049.model.DetParam;
import com.openatc.comm.data.MessageData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class DetParamProduct extends InfotypeProduct {

    @Override
    public A1049Message changeJsonToXml(A1049Message a1049Message, MessageData messageData) {
        List<DetParam> detParamList = new ArrayList<>();
        JsonArray detectorList = ((JsonObject) messageData.getData()).get("detectorList").getAsJsonArray();
        for (JsonElement detectorJsonElement : detectorList) {
            JsonObject detector = (JsonObject) detectorJsonElement;
            DetParam detParam = new DetParam();
            detParam.setDetID(detector.get("id").getAsString());
            detParam.setCrossID(messageData.getAgentid());
            detParam.setDistance(detector.get("inthresh").getAsFloat());
            detParamList.add(detParam);
        }
        a1049Message.getBody().getOperation().setTscCmd(null);
        a1049Message.getBody().getOperation().setDetList(detParamList);
        a1049Message.setTo("TCIP");
        a1049Message.setType("RESPONCE");
        return a1049Message;
    }

    @Override
    public JsonElement packData(A1049Message a1049Message) {
        Gson gson = new Gson();
        List<DetParam> detList = a1049Message.getBody().getOperation().getDetList();
        JsonObject jsonObject = new JsonObject();
        JsonArray detlistArray = gson.fromJson(detList.toString(), JsonArray.class);
        jsonObject.add("detectorList", detlistArray);
        return jsonObject;
    }
}
