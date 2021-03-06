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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.ControlInterrupt;
import com.openatc.agent.model.DictConfig;
import com.openatc.agent.model.THisParams;
import com.openatc.agent.model.User;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.DictConfigRepository;
import com.openatc.agent.service.HisParamServiceImpl;
import com.openatc.agent.service.MessageService;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.handler.IMsgPostHandler;
import com.openatc.comm.handler.IMsgPreHandler;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.comm.ocp.DataParamMD5;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import com.openatc.model.model.ControlPattern;
import com.openatc.model.model.StatusPattern;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;
import com.alibaba.fastjson.JSONObject;

import static com.openatc.agent.utils.MyHttpUtil.getIpAddress;
import static com.openatc.comm.common.CommunicationType.COMM_SERVER_TYPE_CENTER;
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

//  @Autowired
    protected CommClient commClient = new CommClient();

    @Autowired
    protected AscsDao mDao;

    @Autowired
    private DictConfigRepository dictConfigRepository;


    @Autowired
    private IMsgPostHandler msgPostHandler;

    @Autowired
    private IMsgPreHandler msgPreHandler;

    Gson gson = new Gson();

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
        RESTRet ret = msgPreHandler.process(requestData);
        if (ret.getData() != null){
            return ret;
        }
        AscsBaseModel ascsBaseModel = mDao.getAscsByID(requestData.getAgentid());
        return postDevsMessageByAscsBaseModel(ascsBaseModel,httpServletRequest,requestData);
    }

    /**
     * @param requestData 发送给设备的请求消息
     * @return RESTRetBase
     * @Title: postDevsMessagegbid
     * @Description: 通过gbid发送设备消息
     */
    @PostMapping(value = "/devs/messagegbid")
    public RESTRet postDevsMessagegbid(HttpServletRequest httpServletRequest, @RequestBody MessageData requestData) {
        AscsBaseModel ascsBaseModel = mDao.getAscsByGBID(requestData.getAgentid());
        return postDevsMessageByAscsBaseModel(ascsBaseModel,httpServletRequest,requestData);
    }

    /**
     * @param requestData 发送给设备的请求消息
     * @return RESTRet
     * @Title: postDevsMessageByAscsBaseModel
     * @Description: 发送设备消息
     */
    private RESTRet postDevsMessageByAscsBaseModel(AscsBaseModel ascsBaseModel,HttpServletRequest httpServletRequest, MessageData requestData){
        //获取主机ip，如果没有传入httpServletRequest，则设置ip为localhost
        String OperatorIp = null;
        if (httpServletRequest == null) {
            OperatorIp = "localhost";
        } else {
            OperatorIp = getIpAddress(httpServletRequest);
        }
        if (ascsBaseModel == null) {
            logger.info("GetDevById is null, request = " + requestData.toString());
            return RESTRetUtils.errorObj(false,E_8001);
        }

        // 判断设备是否平台对接，如果是平台对接的设备，将平台地址填入通行IP
        String platform = ascsBaseModel.getPlatform();
        if(platform != null){
            if(!platform.isEmpty() && !platform.equals("OpenATC")){ // 目前平台配置为OpenATC的设备，也认为是直连的
                // 配置了平台类型，找到该平台的信息，并填入设备IP中
                List<DictConfig> DictConfigList = dictConfigRepository.findByConfigtypeAndKey("platform",platform);
                // 找不到平台信息
                if(DictConfigList.size() == 0){
                    logger.info("Cannot find DictConfig! " + platform + " requestData:" + requestData.toString());
                    return RESTRetUtils.errorObj(false,E_8005);

                }
                // 平台信息找到，但大于1个
                else if(DictConfigList.size() > 1){
                    logger.info("Platform is not unique! " + platform + " requestData:" + requestData.toString());
                    return RESTRetUtils.errorObj(false,E_8006);

                }
                // 找到了唯一的平台信息
                else{
                    String platformIPStr = DictConfigList.get(0).getValue();
                    if(platformIPStr == null){
                        logger.info("IP and port of platform is null!" + platform + " DictConfigList:" + DictConfigList);
                        return RESTRetUtils.errorObj(false,E_8007);

                    }
                    String[] array = platformIPStr.split("_");
                    String ip = array[0];
                    int port = Integer.parseInt(array[1]);
                    JsonObject ascsParam = ascsBaseModel.getJsonparam();
                    ascsParam.addProperty("ip",ip);
                    ascsParam.addProperty("port",port);
                }
            }
        }


        // 发送请求，并把应答返回
        RESTRet responceData = commClient.devMessage(requestData, ascsBaseModel);

        // 处理应答后的请求
        RESTRet ret = msgPostHandler.process(requestData, responceData);
        if (ret.getData() != null) {
            responceData = ret;
        }


        // 把设置请求set-request的操作保存到历史记录中
        if (requestData.getOperation().equals("set-request")) {
            String token = null;
            User subject = null;
            if (httpServletRequest != null) {
                token = httpServletRequest.getHeader("Authorization");
                if (token != null) {
                    subject = (User)SecurityUtils.getSubject().getPrincipal();
                }
            }
            THisParams tParams = CreateHisParam(requestData, (RESTRet) responceData, OperatorIp, subject);
            hisParamService.insertHisParam(tParams);
            return responceData;

        }
        return responceData;
    }

    /**
     * @param agentid  请求路口
     * @return StatusPattern 实时方案状态
     * @Title: GetStatusPattern
     * @Description: 获取方案状态
     */
    public StatusPattern GetStatusPattern(String agentid)
    {
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.workstatus);
        RESTRet<MessageData> restRet = postDevsMessage(null, messageData);

        if( !restRet.isSuccess() )
            return null;

        return gson.fromJson(restRet.getData().getData(),StatusPattern.class);
    }

    /**
     * @param agentid  设置路口
     * @param controlPattern  设置方案
     * @return RESTRet 返回执行应答
     * @Title: SetControlPattern
     * @Description: 设置控制方式
     */
    public RESTRet SetControlPattern(String agentid, ControlPattern controlPattern)
    {
        MessageData messageData = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.ControlPattern, gson.toJsonTree(controlPattern));
        RESTRet restRet = postDevsMessage(null, messageData);
        return restRet;
    }

    /**
     * @param agentid  设置路口
     * @param controlInterrupt  设置优化方案
     * @return RESTRet 返回执行应答
     * @Title: SetControlInterrupt
     * @Description: 设置控制方式
     */
    public RESTRet SetControlInterrupt(String agentid, ControlInterrupt controlInterrupt) {
        MessageData messageData = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.interrupt, gson.toJsonTree(controlInterrupt));
        RESTRet restRet = postDevsMessage(null, messageData);
        return restRet;    }

    @PostMapping(value = "/md5")
    public RESTRet getmd5(@RequestBody MessageData messageData) throws UnsupportedEncodingException {
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
    private THisParams CreateHisParam(MessageData requestData, RESTRet res, String ip, User user) {
//        logger.info( "Create History Param - requestData： " + requestData + " user：" + user);

        THisParams hisParams = new THisParams();

        //操作者
        if(user == null){
            hisParams.setOperator("");
        } else {
            hisParams.setOperator(user.getUser_name());
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
                //响应内容 todo 这里不一定有值，可能是空指针
                if(responceData.getData() != null)
                    hisParams.setResponsebody(responceData.getData().toString());
                //消息子类型
                int subInfoType = 0;
                if (operation.equals("set-response")) {
                    //控制消息，需要判断子类型 todo 这里不一定有值，可能是空指针
                    if(requestData.getInfotype().equals(CosntDataDefine.ControlPattern))
                        subInfoType = requestData.getData().getAsJsonObject().get("control").getAsInt();
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
