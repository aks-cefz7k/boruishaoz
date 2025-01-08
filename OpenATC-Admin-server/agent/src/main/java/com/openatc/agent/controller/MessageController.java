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

import com.openatc.agent.model.THisParams;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.DevIdMapService;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired(required = false)
    DevIdMapService devIdMapService;

//    @Autowired
    protected CommClient commClient = new CommClient();

    @Autowired(required = false)
    protected AscsDao mDao;

    @Autowired
    protected TokenUtil tokenUtil;

    @PostConstruct
    public void init(){
        // 设置通讯模式为UDP固定端口
        commClient.setCommunicationServerType(COMM_SERVER_TYPE_CENTER);
    }

    /**
     * @param requestData 发送给设备的请求消息
     * @return RESTRetBase
     * @Title: postDevsMessage
     * @Description: TODO
     */
    @PostMapping(value = "/devs/message")
    public RESTRet postDevsMessage(HttpServletRequest httpServletRequest, @RequestBody MessageData requestData) throws SocketException, ParseException {
        RESTRet<AscsBaseModel> restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(requestData.getAgentid());
        AscsBaseModel ascsBaseModel = (AscsBaseModel) restRet.getData();


        //获取主机ip，如果没有传入httpServletRequest，则设置ip为localhost
        String OperatorIp = null;
        if (httpServletRequest == null) {
            OperatorIp = "localhost";
        } else {
            OperatorIp = getIpAddress(httpServletRequest);
        }

        // 发送请求，并把应答返
        if( ascsBaseModel == null){
            logger.info("GetDevById is null, request = " + requestData.toString());
            return new RESTRet();
        }
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
        String protocol = ascsBaseModel.getProtocol();


        RESTRet responceData = commClient.devMessage(requestData,ascsBaseModel);

        // 把设置请求的操作保存到历史记录中
        String token = null;
        if (httpServletRequest != null) {
            token = httpServletRequest.getHeader("Authorization");
        }

        if (requestData.getOperation().equals("set-request")) {
            if(token == null){
                logger.warning("token of set-request is null;");
            }
            logger.info("=============Send set-request to " + requestData.getAgentid() + ":" + ip + ":" + port + ":" + protocol + ":" + requestData.getInfotype());
            try{
                hisParamService.insertHisParam(CreateHisParam(requestData, (MessageData) responceData.getData(), OperatorIp, token));
            }catch (Exception e){
                logger.warning(e.toString());
                return responceData;
            }
        }

        return responceData;
    }




    /**
     * @param requestData  请求消息
     * @param responceData 应答消息
     * @return THisParams 操作记录
     * @Title: CreateHisParam
     * @Description: 生成一条操作记录
     */
    private THisParams CreateHisParam(MessageData requestData, MessageData responceData, String ip, String token) {

        logger.info( "Create History Param - requestData： " + requestData + " token：" + token);

        THisParams hisParams = new THisParams();
        String username = tokenUtil.getUsernameFromToken(token);

        //操作者
        if(username == null){
            hisParams.setOperator("");
        }
        else{
            hisParams.setOperator(username);
        }
        //操作时间自动生成
        //操作源地址
        hisParams.setSource(ip);
        //设备id
        hisParams.setAgentid(requestData.getAgentid());

        //消息类型
        hisParams.setInfotype(requestData.getInfotype());
        //请求内容
        try {
            hisParams.setRequestbody(requestData.getData().toString());
        } catch (Exception e) {
            hisParams.setRequestbody("{}");
        }
        if(responceData != null){
            //消息描述
            hisParams.setStatus(responceData.getOperation());
            //响应内容
            hisParams.setResponsebody(responceData.getData().toString());
        }
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
