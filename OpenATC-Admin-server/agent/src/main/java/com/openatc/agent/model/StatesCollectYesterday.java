package com.openatc.agent.model;

import lombok.Data;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2022/4/12 22:44
 * @description：昨日设备状态统计
 * @modified By：
 * @version: $
 */
@Data
public class StatesCollectYesterday {
    Integer online = null;
    Integer offline = null;
    Integer fault = null;
    Integer manual  = null;
}
