package com.openatc.agent.service;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface FlowService {

    Map<String, JsonObject> getHistoryFlow(String agentid, String username, String password) throws ParseException, IOException;
}
