package com.openatc.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.openatc.agent.controller.MessageController;
import com.openatc.agent.service.FeatureDao;
import com.openatc.agent.service.FeatureService;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.ocp.CosntDataDefine;
import com.openatc.core.common.IErrorEnum;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.InnerError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Classname FeatureServiceImpl
 * @Description 参数管理
 * @Date 2022/1/25 17:50
 * @Created by panleilei
 */
@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    MessageController messageController;

    @Autowired
    FeatureDao featureDao;

    private Gson gson = new Gson();
    /**
     * 环转阶段
     * @param agentid
     * @param ringsList
     * @return
     */
    public List<StageParam> convertPatternRingToStage(String agentid, List<List<List<Split>>> ringsList) {
        // 将相位保存起来，供后面取阶段中实际相位信息使用
//        Map<Integer,Phase> phaseMap = new HashMap<>();
        // 获取相位信息
        List<Phase> phases = GetPhase(agentid);
        int yellow = 3;
        int redclear = 2;
        if (phases != null){
//            for (Phase phase : phases){
//                phaseMap.put(phase.getId(),phase);
//            }
            // 暂时获取第一个相位的红灯和黄灯
            yellow = phases.get(0).getYellow();
            redclear = phases.get(0).getRedclear();
        }

        List<StageParam> stageParamList = new ArrayList<>();
        for (List<List<Split>> rings : ringsList){
//            List<List<Split>> rings = pattern.getRings();
                // 保存环数据
                Map<Integer,List<Split>> splitListMap = new HashMap<>();
                for (int i = 0; i < rings.size(); i++){
                    if (!rings.get(i).isEmpty()){   // 如果环中有数据，把环的数据加入到map中
                        splitListMap.put(i + 1,rings.get(i));
                    }
                }
                int stageNo = 0;
                // 当第一个环不为空
                while (!splitListMap.get(1).isEmpty()){
                    stageNo++;
                    StageParam stageParam = new StageParam();
                    stageParam.setCrossID(agentid);
                    stageParam.setStageNo(stageNo);
                    stageParam.setStageName("S" + stageNo);
                    stageParam.setAttribute(0); // 默认为一般
                    List<Integer> splitValue = new ArrayList<>();
                    for (int i = 0; i < splitListMap.size(); i++){
                        // 将所有的阶段的绿信比添加到一个集合
                        splitValue.add(splitListMap.get(i + 1).get(0).getValue());
                    }
                    Collections.sort(splitValue);
                    // 求该阶段的绿信比
                    int value = splitValue.get(0);  // 最小绿信比
                    // 获取最小绿信比对应的相位
                    // 获取该相位的黄灯和红灯
                    stageParam.setGreen(value - redclear - yellow);
                    stageParam.setRedYellow(0); // 相位实体类中没有redyellow属性，暂设0
                    stageParam.setYellow(yellow);
                    stageParam.setAllRed(redclear);

                    List<PhaseNo> phaseNos = new ArrayList<>();
                    // 更新绿信比
                    for (int i = 0; i < splitListMap.size(); i++){
                        phaseNos.add(new PhaseNo(String.valueOf(splitListMap.get(i + 1).get(0).getId())));
                        int newValue = splitListMap.get(i + 1).get(0).getValue() - value;
                        splitListMap.get(i + 1).get(0).setValue(newValue);
                        if (newValue == 0){
                            splitListMap.get(i + 1).remove(0);
                        }
                    }
                    stageParam.setPhaseNoList(phaseNos);
                    stageParamList.add(stageParam);

                }


        }
        return stageParamList;
    }

    /**
     * 阶段转环
     * @param stageParamLists
     * @return rings
     */
    @Override
    public List<List<List<Split>>> convertStageToPattern(List<List<StageParam>> stageParamLists) {
        List<List<List<Split>>> ringsList = new ArrayList<>();
        for (List<StageParam> stageParamList : stageParamLists){
            List<List<Split>> rings = new ArrayList<>();
            List<Integer> SplitNumList = new ArrayList<>(); // 用于确定环数
            List<List<PhaseNo>> pList = new ArrayList<>();  // 保存每个阶段中的相位列表
            Map<Integer,Integer> splitValueMap = new HashMap<>();   // 保存每个相位的绿信比
            for (int i = 0; i < stageParamList.size(); i++){
                // 所有阶段包含的相位列表
                SplitNumList.add(stageParamList.get(i).getPhaseNoList().size());
                pList.add(stageParamList.get(i).getPhaseNoList());

                // 确定每个相位的绿信比
                int value = stageParamList.get(i).getAllRed() + stageParamList.get(i).getGreen() + stageParamList.get(i).getYellow();
                for (int j = 0; j < stageParamList.get(i).getPhaseNoList().size(); j++){
                    if (stageParamList.get(i).getPhaseNoList().get(j).getId() == null){
                        continue;
                    }
                    String id = stageParamList.get(i).getPhaseNoList().get(j).getId();
                    // 先从map中取数据
                    if (splitValueMap.get(Integer.valueOf(id)) != null){
                        int newValue = value + splitValueMap.get(Integer.valueOf(id));
                        splitValueMap.put(Integer.valueOf(id),newValue);
                    }else {
                        splitValueMap.put(Integer.valueOf(id),value);
                    }
                }
            }

            // 确定环的数量
            Collections.sort(SplitNumList);
            int ringNum = SplitNumList.get(SplitNumList.size() - 1);
            for (int i = 0; i < ringNum; i++){  // 控制环的数量
                List<Split> ring = new ArrayList<>();
                List<Integer> idList = new ArrayList<>();
                for (int k = 0; k < pList.size(); k++){ // 控制相位的数量
                    int id = Integer.valueOf(pList.get(k).get(i).getId());
                    int value = splitValueMap.get(id);
                    int mode = 2;
                    if (!idList.contains(id)){  // id重复则不创建Split
                        Split split = new Split(id,value,mode);
                        List<Integer> options = new ArrayList<>();
                        options.add(0);
                        options.add(0);
                        options.add(0);
                        split.setOptions(options);
                        ring.add(split);
                    }
                    idList.add(id);
                }

                rings.add(ring);
            }
            // 固定返回四个环
            if (ringNum < 4){
                for (int i = ringNum; i < 4; i++){
                    List<Split> ring = new ArrayList<>();
                    rings.add(ring);
                }
            }
            ringsList.add(rings);
        }

        return ringsList;
    }

    /**
     * 保存参数到数据库
     * @param requestData
     * @return
     */
    @Override
    public RESTRet saveFeatureAll(MessageData requestData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createTime = sdf.format(date);
        requestData.setCreatetime(createTime);

        int row = featureDao.saveFeatureAll(requestData);
        if (row == 0){
            InnerError innerError = RESTRetUtils.innerErrorObj(requestData.getAgentid(), IErrorEnumImplInner.E_207, requestData);
            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_4001,innerError);
        }else {
            return RESTRetUtils.successObj("success");
        }

    }

    /**
     * 从数据库中获取参数 todo
     * @param agentid
     * @return
     */
    @Override
    public RESTRet getFeatureAll(String agentid) {
        String data;
        //数据库中不存在数据（可优化为捕获具体异常，分情况处理）
        try {
            data = featureDao.selectFeatureAll(agentid);
        } catch (Exception e){
            InnerError innerError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_109, null);
            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_2002,innerError);
        }

        try {
            MessageData messageData = gson.fromJson(data, MessageData.class);
            return RESTRetUtils.successObj(messageData);
        }catch (Exception e){
            InnerError innerError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_104, null);
            return RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_4005,innerError);
        }
    }

    /**
     * 根据设备id删除数据库中该设备的参数
     * @param agentid
     */
    @Override
    public RESTRet deleteFeatureByAgentid(String agentid) {
        int rows = featureDao.deleteFeatureByAgentid(agentid);
        if (rows == 0){
            InnerError innerError = RESTRetUtils.innerErrorObj(agentid, IErrorEnumImplInner.E_105, null);
            RESTRetUtils.errorDetialObj(IErrorEnumImplOuter.E_2002,innerError);
        }
        return RESTRetUtils.successObj("success");
    }


    // 获取相位
    private List<Phase> GetPhase(String agentid) {
//        Gson gson = new Gson();
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.phase);
        RESTRet<MessageData> restRet = messageController.postDevsMessage(null, messageData);

        if( !restRet.isSuccess() )
            return null;
        JsonElement data = restRet.getData().getData();
        Type type = new TypeToken<Feature>() {}.getType();
        Feature feature = gson.fromJson(data, type);
        return feature.getPhaseList();
    }
}
