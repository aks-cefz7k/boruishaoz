package com.openatc.agent.model;

import lombok.Data;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/12/20 13:22
 * @description：瓶颈控制路口状态
 * @modified By：
 * @version: $
 */
@Data
public class OverflowStatus {
    private String agentid;
    private int state; // 执行结果, 0：执行失败, 1：执行成功，2：恢复失败，3：恢复成功
    private int control; // 当前控制方式（-1未知）
}
