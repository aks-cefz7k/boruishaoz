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
 * @Description: ???????????????????????????
 * @date 2019???10???19??? ??????5:24:30
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
        // ?????????????????????UDP????????????
        commClient.setCommunicationServerType(COMM_SERVER_TYPE_CENTER);
    }


    /**
     * @param requestData ??????????????????????????????
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
     * @param requestData ??????????????????????????????
     * @return RESTRetBase
     * @Title: postDevsMessagegbid
     * @Description: ??????gbid??????????????????
     */
    @PostMapping(value = "/devs/messagegbid")
    public RESTRet postDevsMessagegbid(HttpServletRequest httpServletRequest, @RequestBody MessageData requestData) {
        AscsBaseModel ascsBaseModel = mDao.getAscsByGBID(requestData.getAgentid());
        return postDevsMessageByAscsBaseModel(ascsBaseModel,httpServletRequest,requestData);
    }

    /**
     * @param requestData ??????????????????????????????
     * @return RESTRet
     * @Title: postDevsMessageByAscsBaseModel
     * @Description: ??????????????????
     */
    private RESTRet postDevsMessageByAscsBaseModel(AscsBaseModel ascsBaseModel,HttpServletRequest httpServletRequest, MessageData requestData){
        //????????????ip?????????????????????httpServletRequest????????????ip???localhost
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

        // ?????????????????????????????????????????????????????????????????????????????????????????????IP
        String platform = ascsBaseModel.getPlatform();
        if(platform != null){
            if(!platform.isEmpty() && !platform.equals("OpenATC")){ // ?????????????????????OpenATC?????????????????????????????????
                // ??????????????????????????????????????????????????????????????????IP???
                List<DictConfig> DictConfigList = dictConfigRepository.findByConfigtypeAndKey("platform",platform);
                // ?????????????????????
                if(DictConfigList.size() == 0){
                    logger.info("Cannot find DictConfig! " + platform + " requestData:" + requestData.toString());
                    return RESTRetUtils.errorObj(false,E_8005);

                }
                // ??????????????????????????????1???
                else if(DictConfigList.size() > 1){
                    logger.info("Platform is not unique! " + platform + " requestData:" + requestData.toString());
                    return RESTRetUtils.errorObj(false,E_8006);

                }
                // ??????????????????????????????
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


        // ?????????????????????????????????
        RESTRet responceData = commClient.devMessage(requestData, ascsBaseModel);

        // ????????????????????????
        RESTRet ret = msgPostHandler.process(requestData, responceData);
        if (ret.getData() != null) {
            responceData = ret;
        }


        // ???????????????set-request?????????????????????????????????
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
     * @param agentid  ????????????
     * @return StatusPattern ??????????????????
     * @Title: GetStatusPattern
     * @Description: ??????????????????
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
     * @param agentid  ????????????
     * @param controlPattern  ????????????
     * @return RESTRet ??????????????????
     * @Title: SetControlPattern
     * @Description: ??????????????????
     */
    public RESTRet SetControlPattern(String agentid, ControlPattern controlPattern)
    {
        MessageData messageData = new MessageData(agentid, CosntDataDefine.setrequest, CosntDataDefine.ControlPattern, gson.toJsonTree(controlPattern));
        RESTRet restRet = postDevsMessage(null, messageData);
        return restRet;
    }

    /**
     * @param agentid  ????????????
     * @param controlInterrupt  ??????????????????
     * @return RESTRet ??????????????????
     * @Title: SetControlInterrupt
     * @Description: ??????????????????
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
     * @param requestData  ????????????
     * @param res ????????????
     * @return THisParams ????????????
     * @Title: CreateHisParam
     * @Description: ????????????????????????
     */
    private THisParams CreateHisParam(MessageData requestData, RESTRet res, String ip, User user) {
//        logger.info( "Create History Param - requestData??? " + requestData + " user???" + user);

        THisParams hisParams = new THisParams();

        //?????????
        if(user == null){
            hisParams.setOperator("");
        } else {
            hisParams.setOperator(user.getUser_name());
        }
        //????????????????????????
        //???????????????
        hisParams.setSource(ip);
        //??????id
        hisParams.setAgentid(requestData.getAgentid());
        //????????????
        hisParams.setInfotype(requestData.getInfotype());
        //????????????
        try {
            hisParams.setRequestbody(requestData.getData().toString());
        } catch (Exception e) {
            hisParams.setRequestbody("{}");
        }
        if (res.isSuccess()) {
            MessageData responceData = (MessageData)res.getData();
            if(responceData != null){
                //????????????
                String operation = responceData.getOperation();
                hisParams.setStatus(operation);
                //???????????? todo ??????????????????????????????????????????
                if(responceData.getData() != null)
                    hisParams.setResponsebody(responceData.getData().toString());
                //???????????????
                int subInfoType = 0;
                if (operation.equals("set-response")) {
                    //???????????????????????????????????? todo ??????????????????????????????????????????
                    if(requestData.getInfotype().equals(CosntDataDefine.ControlPattern))
                        subInfoType = requestData.getData().getAsJsonObject().get("control").getAsInt();
                }
                hisParams.setSubInfoType(subInfoType);
                //???????????????
                String responseCode = res.getCode();
                hisParams.setResponseCode(responseCode);
                //?????????????????????
                int deviceErrorCode = 0;
                if (operation.equals("error-response")) {
                    deviceErrorCode = responceData.getData().getAsJsonObject().get("code").getAsInt();
                }
                hisParams.setDeviceErrorCode(deviceErrorCode);
            }
        } else {
            InnerError innerError = (InnerError)res.getData();
            if(innerError != null){
                //????????????
                String operation = requestData.getOperation();
                hisParams.setStatus(operation);
                //????????????
                String responseBodey = JSONObject.toJSONString(innerError.getContent());
                hisParams.setResponsebody(responseBodey);
                //???????????????
                int subInfoType = 0;
                hisParams.setSubInfoType(subInfoType);
                //???????????????
                String responseCode = res.getCode();
                hisParams.setResponseCode(responseCode);
                //?????????????????????
                String innerErrorCode = innerError.getErrorCode();
                hisParams.setInnerErrorCode(innerErrorCode);
                //?????????????????????
                int deviceErrorCode = 0;
                hisParams.setDeviceErrorCode(deviceErrorCode);
            }
        }
        return hisParams;
    }
}
