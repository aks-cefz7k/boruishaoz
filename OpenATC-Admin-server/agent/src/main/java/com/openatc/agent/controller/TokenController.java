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

import com.openatc.agent.model.TokenModel;
import com.openatc.agent.service.TokenDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/findAll")
    public RESTRetBase findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<TokenModel> list =  tokenDao.findAll(sort);
        return RESTRetUtils.successObj(list);
    }

    @PostMapping("/save")
    public RESTRetBase save(@RequestBody TokenModel tokenModel) {
        TokenModel record = tokenDao.save(tokenModel);
        return RESTRetUtils.successObj(record);
    }

    @DeleteMapping("/deleteById/{id}")
    public RESTRetBase deleteById (@PathVariable Integer id) {
        tokenDao.deleteById(id);
        return RESTRetUtils.successObj();
    }
}
