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

import com.google.gson.JsonElement;
import com.openatc.agent.model.THisParams;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.DataParamMD5;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static com.openatc.agent.utils.MyHttpUtil.getIpAddress;
import static com.openatc.comm.common.CommunicationType.COMM_SERVER_TYPE_CENTER;
import static com.openatc.core.common.IErrorEnumImplOuter.E_8001;


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

//  @Autowired
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
     * @Description:
     */
    @PostMapping(value = "/devs/message")
    public RESTRet postDevsMessage(HttpServletRequest httpServletRequest, @RequestBody MessageData requestData) {

        AscsBaseModel ascsBaseModel = mDao.getAscsByID(requestData.getAgentid());

        //获取主机ip，如果没有传入httpServletRequest，则设置ip为localhost
        String OperatorIp = null;
        if (httpServletRequest == null) {
            OperatorIp = "localhost";
        } else {
            OperatorIp = getIpAddress(httpServletRequest);
        }

        // 发送请求，并把应答返
        if (ascsBaseModel == null) {
            logger.info("GetDevById is null, request = " + requestData.toString());
            return RESTRetUtils.errorObj(false,E_8001);
        }
//        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
//        int port = ascsBaseModel.getJsonparam().get("port").getAsInt();
//        String protocol = ascsBaseModel.getProtocol();

        RESTRet responceData = commClient.devMessage(requestData, ascsBaseModel);

        // 把设置请求的操作保存到历史记录中
        String token = null;
        if (httpServletRequest != null) {
            token = httpServletRequest.getHeader("Authorization");
        }

        if (requestData.getOperation().equals("set-request")) {
            if (token == null) {
                logger.warning("token of set-request is null;");
            }
//            logger.info("=============Send set-request to " + requestData.getAgentid() + ":" + ip + ":" + port + ":" + protocol + ":" + requestData.getInfotype());
            try {
                THisParams tParams = CreateHisParam(requestData, (RESTRet) responceData, OperatorIp, token);
                hisParamService.insertHisParam(tParams);
            } catch (Exception e) {
                logger.warning(e.toString());
                return responceData;
            }
        }
        return responceData;
    }

    @PostMapping(value = "/md5")
    public RESTRet postDevsMessage(@RequestBody MessageData messageData) throws UnsupportedEncodingException {
        JsonElement data = messageData.getData();
        DataParamMD5 dataMD5 = new DataParamMD5();
        String datamd5value = null;
        if (data != null) {
            datamd5value = dataMD5.getMD5(data.toString());
        }
        return RESTRetUtils.successObj(datamd5value);
    }
    /**
     * @param requestData  请求消息
     * @param res 应答消息
     * @return THisParams 操作记录
     * @Title: CreateHisParam
     * @Description: 生成一条操作记录
     */
    private THisParams CreateHisParam(MessageData requestData, RESTRet res, String ip, String token) {
        logger.info( "Create History Param - requestData： " + requestData + " token：" + token);

        THisParams hisParams = new THisParams();
        String username = tokenUtil.getUsernameFromToken(token);

        //操作者
        if(username == null){
            hisParams.setOperator("");
        } else {
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
        if (res.isSuccess()) {
            MessageData responceData = (MessageData)res.getData();
            if(responceData != null){
                //消息描述
                String operation = responceData.getOperation();
                hisParams.setStatus(operation);
                //响应内容
                hisParams.setResponsebody(responceData.getData().toString());
                //消息子类型
                int subInfoType = 0;
                if (operation.equals("set-response")) {//控制消息
                    subInfoType = responceData.getData().getAsJsonObject().get("control").getAsInt();
                }
                hisParams.setSubInfoType(subInfoType);
                //请求错误码
                String responseCode = res.getCode();
                hisParams.setResponseCode(responseCode);
                //特征参数错误码
                int deviceErrorCode = 0;
                if (operation.equals("error-response")) {
                    deviceErrorCode = responceData.getData().getAsJsonObject().get("code").getAsInt();
                }
                hisParams.setDeviceErrorCode(deviceErrorCode);
            }
        } else {
            InnerError innerError = (InnerError)res.getData();
            if(innerError != null){
                //消息描述
                String operation = requestData.getOperation();
                hisParams.setStatus(operation);
                //响应内容
                String responseBodey = JSONObject.toJSONString(innerError.getContent());
                hisParams.setResponsebody(responseBodey);
                //消息子类型
                int subInfoType = 0;
                hisParams.setSubInfoType(subInfoType);
                //请求错误码
                String responseCode = res.getCode();
                hisParams.setResponseCode(responseCode);
                //请求内部错误码
                String innerErrorCode = innerError.getErrorCode();
                hisParams.setInnerErrorCode(innerErrorCode);
                //特征参数错误码
                int deviceErrorCode = 0;
                hisParams.setDeviceErrorCode(deviceErrorCode);
            }
        }
        return hisParams;
    }
}
