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
package com.openatc.agent.controller;

import com.openatc.agent.model.AscsBaseModel;
import com.openatc.agent.model.THisParams;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.DeviceService;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.common.CommunicationType;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.packupack.CosntDataDefine;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.text.ParseException;
import java.util.logging.Logger;

import static com.openatc.comm.common.CommunicationType.*;
import static com.openatc.core.common.IErrorEnumImplInner.*;
import static com.openatc.core.common.IErrorEnumImplOuter.*;


/**
 * @author kedacom
 * @ClassName: SplitController
 * @Description: 设备消息通讯接口类
 * @date 2019年10月19日 下午5:24:30
 */
@RestController
@CrossOrigin
public class MessageController {

    private Logger logger = Logger.getLogger(MessageController.class.toString());

    @Autowired(required = false)
    protected HisParamServiceImpl hisParamService;

    @Autowired(required = false)
    protected DevController devController;

    @Autowired
    protected CommClient commClient;

    @Value("${agent.server.shiro}")
    private boolean shiroOpen;

    @Autowired
    DeviceService deviceService;

    @Autowired(required = false)
    protected AscsDao mDao;

    @PostConstruct
    public void init(){
        // 设置通讯模式为UDP固定端口
        commClient.setCommunicationType(CommunicationType.COMM_UDP_HOSTPORT);
    }

    /**
     * @param requestData 发送给设备的请求消息
     * @return RESTRetBase
     * @Title: postDevsMessage
     * @Description: TODO
     */
    @PostMapping(value = "/devs/message")
    public RESTRet postDevsMessage(HttpServletRequest httpServletRequest, @RequestBody MessageData requestData) throws SocketException, ParseException {
        //System.out.println("测试");
        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(requestData.getAgentid());
        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();
        
        //获取主机ip，如果没有传入httpServletRequest，则设置ip为localhost
        String OperatorIp = null;
        if (httpServletRequest == null) {
            OperatorIp = "localhost";
        } else {
            OperatorIp = getIpAddress(httpServletRequest);
        }

        DevCommError devCommError = null;
        String agentid = requestData.getAgentid();
        String infotype = requestData.getInfotype();
        String errorquest = CosntDataDefine.errorrequest;
        String errorresponse = CosntDataDefine.erroresponse;
        //判断设备是否为空
        if (ascsBaseModel == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_100);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断设备是否存在
        if (mDao.getDevByAgentid(requestData.getAgentid()) == 0) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_105);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断操作类型是否为空
        if (requestData.getOperation() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_101);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断消息类型是否为空
        if (requestData.getInfotype() == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_102);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        //判断协议是否为空
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
        String protocol = ascsBaseModel.getProtocol();
        if (protocol == null) {
            devCommError = RESTRetUtils.errorObj(agentid, errorquest, infotype, E_108);
            return RESTRetUtils.errorDetialObj(E_4001, devCommError);
        }

        // 判断通讯类型是设备直连还是平台转发
        String platform = ascsBaseModel.getPlatform();
        int exangeType = EXANGE_TYPE_DEVICE;
        if(platform != null)
            exangeType = EXANGE_TYPE_CENTER;

        //增加mode字段
        if (requestData.getOperation().equals("set-request") && requestData.getInfotype().equals("control/pattern")) {
            requestData.getData().getAsJsonObject().addProperty("mode", 1);
        }

        // 获取responceData
        MessageData responceData = null;
        try {
            responceData = commClient
                    .exange(ip, port, protocol, exangeType,requestData);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        // 把设置请求的操作保存到历史记录中
//        String token = httpServletRequest.getParameter("jwt-token");
//        if (requestData.getOperation().equals("set-request") && token != null ) {
//            logger.info("=============Send set-request to " + requestData.getAgentid() + ":" + ip + ":" + port + ":" + protocol + ":" + requestData.getInfotype());
//            hisParamService.insertHisParam(CreateHisParam(requestData, responceData, OperatorIp,token));
//        }

        if (responceData == null){
            return RESTRetUtils.errorDetialObj(E_4005, devCommError);
        }

        if (responceData.getOperation() == null){
            return RESTRetUtils.errorDetialObj(E_4006, devCommError);
        }

        //判断设备是否在线
        if (responceData.getOperation().equals("Communication Error!")) {
            devCommError = RESTRetUtils.errorObj(agentid, errorresponse, infotype, E_301);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError);
        }

        //判断应答是否成功
        if (responceData.getOperation().equals("error-response")) {
            return RESTRetUtils.errorDetialObj(E_4002, responceData.getData());
        }

        return RESTRetUtils.successObj(responceData);
    }


    /**
     * @param requestData  请求消息
     * @param responceData 应答消息
     * @return THisParams 操作记录
     * @Title: CreateHisParam
     * @Description: 生成一条操作记录
     */
    private THisParams CreateHisParam(MessageData requestData, MessageData responceData, String ip, String token) {
        THisParams hisParams = new THisParams();
        String username = deviceService.getUsernameByToken(token);
        //操作者
        try {
            hisParams.setOperator(username);
        } catch (Exception e) {
            logger.info("get no user from subject");
        }
        //操作时间自动生成
        //操作源地址
        hisParams.setSource(ip);
        //设备id
        hisParams.setAgentid(requestData.getAgentid());
        //消息类型
        hisParams.setInfotype(requestData.getInfotype());
        //消息描述
        hisParams.setStatus(responceData.getOperation());
        //请求内容
        try {
            hisParams.setRequestbody(requestData.getData().toString());
        } catch (Exception e) {
            hisParams.setRequestbody("{}");
        }
        //响应内容
        hisParams.setResponsebody(responceData.getData().toString());
        return hisParams;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
