package com.openatc.comm.handler;

import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;

/**
 * @Classname IMsgPreHandler
 * @Description 向设备发送请求前，需要做处理的地方实现此接口
 * @Date 2022/4/6 14:39
 * @Created by panleilei
 */
public interface IMsgPreHandler {
    RESTRet process(MessageData requestData);
}
