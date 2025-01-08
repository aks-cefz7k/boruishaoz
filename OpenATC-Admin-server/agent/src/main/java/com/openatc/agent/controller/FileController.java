package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

@RestController
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private DevController devController;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/system/update")
    public RESTRetBase uploadFile(@RequestParam(value = "file") MultipartFile file,
                                  @RequestParam(value = "agentid") String agentid) {
        RESTRet<AscsBaseModel> restRet = null;
        RESTRetBase result;
        try {
            restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentid);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        AscsBaseModel ascsBaseModel = restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        String url = "http://" + ip + ":8012" + "/openatc/system/update";
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.ALL_VALUE);
        //封装请求行和请求体
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            InputStreamResource resource = new InputStreamResource(inputStream);
            params.add("file", resource);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(params, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String body = responseEntity.getBody();
            Gson gson = new Gson();
            result = gson.fromJson(body, RESTRetBase.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2008);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2010);
            }
        }
        return result;
    }

}
