package com.openatc.configserver.service;

import com.openatc.core.model.RESTRetBase;

import java.io.IOException;
import java.text.ParseException;

public interface FlowService {

    RESTRetBase getHistoryFlow(String agentid, String username, String password) throws ParseException, IOException;
}
