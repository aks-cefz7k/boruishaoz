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
package com.openatc.agent.resmodel;


import com.openatc.agent.model.THisParams;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @ClassName: PageOR
 * @Description: 分页操作记录类，用于返回分页操作记录的结果
 * @date 2019年12月31日 下午18:33:18
 */

@Data
public class PageOR {
    private Long total;
    private List<THisParams> content;
}
