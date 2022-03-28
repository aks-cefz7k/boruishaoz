package com.openatc.agent.service;


import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DeviceService {

    private final String JWTTOKEN = "jwt-token";

    @Value("${kiss.unifieddev.uri}")
    private String devServerUri;

    @Autowired
    RestTemplate restTemplate;


    public String getUsernameByToken(String jwtToken){
        String url = devServerUri + "/username";
        //headers
        //header
        HttpHeaders requestheaders = new HttpHeaders();
        requestheaders.set("Content-Type","application/json; charset=utf-8");
        requestheaders.set(JWTTOKEN, jwtToken);
        HttpEntity<String> entity = new HttpEntity(requestheaders);

        //HttpEntity
        ResponseEntity<JsonObject> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonObject.class);

        JsonObject responseBody = response.getBody();
        String username = responseBody.get("data").getAsString();
        return username;
    }
}
