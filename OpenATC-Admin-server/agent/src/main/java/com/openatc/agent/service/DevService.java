package com.openatc.agent.service;

import com.google.gson.JsonObject;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.model.model.AscsBaseModel;

import java.util.List;

/**
 * @Classname DevService
 * @Description
 * @Date 2022/4/8 18:41
 * @Created by panleilei
 */
public interface DevService {
    RESTRet getDevs(List<AscsBaseModel> ascsBaseModels);

    RESTRetBase getRangeDevs(JsonObject jsonObject);
}
