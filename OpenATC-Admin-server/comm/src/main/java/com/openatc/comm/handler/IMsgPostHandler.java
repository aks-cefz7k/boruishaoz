package com.openatc.comm.handler;

import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;

/**
 * @Classname IMsgPostHandler
 * @Description 需要在设备应答请求后做处理的实现此接口
 * @Date 2022/3/30 23:12
 * @Created by panleilei
 */
public interface IMsgPostHandler {
    RESTRet process(MessageData request, RESTRet response);
}
