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

import lombok.Data;

/**
 * @author
 * @ClassName: LSCheckList
 * @Description: 手动控制面板中，灯状态检索对应表
 * @date 2019年10月19日 下午5:15:09
 */
@Data
public class LSCheck {
    private int id;
    private int controlsource;
    private int[] direction;

    public LSCheck(int id, int controlsource, int[] direction) {
        this.id = id;
        this.controlsource = controlsource;
        this.direction = direction.clone();
    }

    public int[] getDirection() {
        return direction.clone();
    }

    public void setDirection(int[] direction) {
        this.direction = direction.clone();
    }

    public LSCheck() {
    }
}
