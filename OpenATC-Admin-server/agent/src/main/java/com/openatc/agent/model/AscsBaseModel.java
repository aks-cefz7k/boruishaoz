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
package com.openatc.agent.model;

import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Data
@Component
public class AscsBaseModel {

    private int id;
    @Temporal(TemporalType.DATE)
    private Date lastTime;
    private String descs;
    private String type;
    private int status;
    private String state;
    private MyGeometry geometry;
    private JsonObject jsonparam;
    private String agentid;
    protected String protocol; //协议
    private String name;
    private String code;
    private String gbid;
    private String firm;
    private String platform;
    private String thirdplatformid;
    private int sockettype;
}
