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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openatc.agent.utils.DateToLongSerializer;
//import com.kedacom.openatc.kdagent.utils.JsonbType;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.jackson.JsonObjectSerializer;


@Data
@Entity
@Table(name = "t_user", schema = "public")
//@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String tokenip;

    @Column
    private String user_name;

    @Column
    private String password;

    @Column
    private String nick_name;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false)
    @JsonSerialize(using = DateToLongSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    @Column(nullable = false)
    private short status = 1;

    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleNames;

    @JsonSerialize(using = JsonObjectSerializer.class)
    @Column
    private String ext_infos;

    @Column
    private String email;

    @Column
    private String mobile_phone;

    @Column
    private String login_ip_limit = "*";

    @Column
    private String organization;

    @Column
    private long salt;

    @Column
    private String token;

    @Column
    private Date expiration_time;

    @JsonSerialize(using = JsonObjectSerializer.class)
    public String getExt_infos() {
        return ext_infos;
    }

}
