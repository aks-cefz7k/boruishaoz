package com.openatc.agent.utils;

import lombok.Data;

/**
 * @author laoxia
 * @Date 2021/9/2 11:23
 * @Describetion
 */
@Data
public class PageInit {
    private Integer pageNum;
    private Integer pageRow;

    public  PageInit(Integer pageNum, Integer pageRow) {
        this.pageNum = pageNum == null || pageNum <= 0 ? 0 : pageNum - 1;
        this.pageRow = pageRow == null || pageRow <= 0 ? 10 : pageRow;
    }
}
