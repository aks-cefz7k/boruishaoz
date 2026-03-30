package com.openatc.agent.model;

import lombok.Data;

/**
 * @author laoxia
 * @Date 2021/7/28 10:54
 * @Describetion
 */
@Data
public class WebSocketSub {
    private String model;
    private String infotype;
    private String[] param;
    private String subscribe;

    public String[] getParam() {
        return param.clone();
    }

    public void setParam(String[] param) {
        this.param = param.clone();
    }

}
