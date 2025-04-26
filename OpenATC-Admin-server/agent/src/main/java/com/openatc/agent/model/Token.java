package com.openatc.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Token {
    private String context;
    // 0 从登录接口获得的token; 1 根据用户名创建token
    private int source;
    private long starttime;
    private long endtime;
}
