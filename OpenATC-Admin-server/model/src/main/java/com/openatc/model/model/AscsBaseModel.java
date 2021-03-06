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
package com.openatc.model.model;

import com.google.gson.JsonObject;
import lombok.Data;

import java.util.Date;

@Data
public class AscsBaseModel {

    private int id;
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
    private String tags;
    private Integer mode;
    private Integer control;
}
