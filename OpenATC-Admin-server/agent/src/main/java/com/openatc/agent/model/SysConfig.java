
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

/**
 * @author ：zhangwenchao
 * @date ：Created in 2022/1/4 16:46
 * @description：字典配置实体
 * @modified By：
 * @version: $
 */

package com.openatc.agent.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sysconfig")
@Data
public class SysConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String configcode;
    private String configname;
    private int configtype;
    private int sort;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ceattime;

    private String creatby;

}
