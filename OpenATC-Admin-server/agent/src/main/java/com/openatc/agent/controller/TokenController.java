/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.agent.controller;

import com.google.gson.JsonObject;
import com.openatc.agent.model.Token;
import com.openatc.agent.model.TokenModel;
import com.openatc.agent.service.TokenDao;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    protected TokenDao tokenDao;
    @Autowired
    protected TokenUtil tokenUtil;

    /**
     * @Author: yangyi
     * @Date: 2022/2/11 13:34
     * @Description: 初始化数据，把数据库里的有效token都读入缓存的Map里
     */
    @PostConstruct
    public void init() {
        List<TokenModel> list =  tokenDao.findAll();
        for (TokenModel tokenModel: list) {
            if (tokenModel.getIsValid()) {
                String token = tokenModel.getToken();
                Long enddate = tokenModel.getEndTime().getTime();
                Long startdate = tokenModel.getStartTime().getTime();
                tokenUtil.tokenMap.put(token, new Token(token, 1, startdate, enddate));
            }
        }
    }

    /**
     * @Author: yangyi
     * @Date: 2022/1/4 11:08
     * @Description: 
     */
    @GetMapping("/findAll")
    @RequiresPermissions({"sys:auth:role:view"})
    public RESTRetBase findAll() {
        Sort sort = new Sort(Direction.DESC, "id");
        List<TokenModel> list =  tokenDao.findAll(sort);
        return RESTRetUtils.successObj(list);
    }

    /**
     * @Author: yangyi
     * @Date: 2022/1/4 11:07
     * @Description:
     */ 
    @PostMapping("/modify")
    @RequiresPermissions({"sys:auth:role:view"})
    public RESTRetBase modify(@RequestBody JsonObject jsonObject) {
        Integer id = jsonObject.get("id").getAsInt();
        String description = jsonObject.get("description").getAsString();
        Boolean isValid = jsonObject.get("isValid").getAsBoolean();
        TokenModel tokenModel = tokenDao.getOne(id);
        if (tokenModel == null) {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2002);
        }
        tokenModel.setDescription(description);
        tokenModel.setIsValid(isValid);
        TokenModel record = tokenDao.save(tokenModel);
        //更新缓存
        if (tokenModel.getIsValid() == false) {
            tokenUtil.tokenMap.remove(tokenModel.getToken());
        } else {
            Date enddate = tokenModel.getEndTime();
            Date startdate = tokenModel.getStartTime();
            String token = tokenModel.getToken();
            tokenUtil.tokenMap.put(token, new Token(token, 1, startdate.getTime(), enddate.getTime()));
        }
        return RESTRetUtils.successObj(record);
    }

    /**
     * @Author: yangyi
     * @Date: 2022/1/4 11:07
     * @Description: 
     */ 
    @DeleteMapping("/deleteById/{id}")
    @RequiresPermissions({"sys:auth:role:view"})
    public RESTRetBase deleteById (@PathVariable Integer id) {
        TokenModel tokenModel = tokenDao.getOne(id);
        if (tokenModel != null) {
            tokenDao.deleteById(id);
            //更新缓存
            tokenUtil.tokenMap.remove(tokenModel.getToken());
        } else {
            return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_2002);
        }
        return RESTRetUtils.successObj();
    }
}
