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
package com.openatc.configserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.packupack.CosntDataDefine;
import com.openatc.configserver.model.*;
import com.openatc.core.common.IErrorEnumImplInner;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.SocketException;
import java.text.ParseException;
import java.util.*;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4003;
import static com.openatc.core.common.IErrorEnumImplOuter.E_4005;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TemplateController {

    DeviceController deviceController = new DeviceController();
    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 查询已有模板
     */
    @Path("template")
    @GET
    public RESTRetBase getTemplate() {
        //首先定义一个类型列表
        List<String> typeList = new ArrayList<>();
        //三相位丁字路口
        typeList.add("100-001-03");
        typeList.add("100-002-03");
        typeList.add("100-003-03");
        typeList.add("100-004-03");
        //无方向十字路口
        typeList.add("101-000-04");
        typeList.add("101-000-08");
        //自定义路口
        typeList.add("999-000-00");

        // 最终返回的数据集
        List<TypeAndPhase> templateLists = new ArrayList<>();


        //遍历typeList，向其中添加数据
        for (String type : typeList) {
            String[] intersectionInfo = type.split("-");
            //路口形状
            String intersectionShape = intersectionInfo[0];
            //路口方向
            String intersectionDirection = intersectionInfo[1];
            //相位数量
            String phaseCount = intersectionInfo[2];

            int[][] direction = new int[Integer.parseInt(phaseCount)][];

            if (intersectionShape.equals("100")) {
                switch (intersectionDirection) {
                    case "001":
                        direction[0] = new int[]{9, 13};
                        direction[1] = new int[]{2};
                        direction[2] = new int[]{10};
                        break;
                    case "002":
                        direction[0] = new int[]{1, 5};
                        direction[1] = new int[]{2};
                        direction[2] = new int[]{14};
                        break;
                    case "003":
                        direction[0] = new int[]{9, 13};
                        direction[1] = new int[]{14};
                        direction[2] = new int[]{6};
                        break;
                    case "004":
                        direction[0] = new int[]{1, 5};
                        direction[1] = new int[]{6};
                        direction[2] = new int[]{10};
                    default:
                        direction[0] = new int[]{};
                        direction[0] = new int[]{};
                        direction[0] = new int[]{};
                        break;
                }
            }
            // 2 表示十字型路口
            if (intersectionShape.equals("101")) {
                switch (phaseCount) {
                    case "04":
                        direction[0] = new int[]{1, 5};
                        direction[1] = new int[]{2, 6};
                        direction[2] = new int[]{9, 13};
                        direction[3] = new int[]{10, 14};
                        break;
                    case "08":
                        direction[0] = new int[]{1};
                        direction[1] = new int[]{5};
                        direction[2] = new int[]{2};
                        direction[3] = new int[]{6};
                        direction[4] = new int[]{13};
                        direction[5] = new int[]{9};
                        direction[6] = new int[]{14};
                        direction[7] = new int[]{10};
                        break;
                    default:
                        break;
                }
            }  //到此方向已经设定好了

            List<SimTemPhase> simTemPhases = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(phaseCount); i++) {
                simTemPhases.add(new SimTemPhase(i + 1, direction[i]));
            }

            TypeAndPhase typeAndPhase = new TypeAndPhase();

            typeAndPhase.setType(type);
            typeAndPhase.setPhaseList(simTemPhases);
            templateLists.add(typeAndPhase);

        }

        return RESTRetUtils.successObj(templateLists);
    }

    /**
     * @return RESTRetBase
     * @Title: addTemplate
     * @Description: 生成模板
     */
    @Path("template")
    @POST
    public RESTRetBase addTemplate(JsonObject jsonObject) {

        String type = jsonObject.get("template").getAsJsonObject().get("type").getAsString();
        int phaseCount = jsonObject.get("phase").getAsJsonObject().get("count").getAsInt();
        int flashgreen = jsonObject.get("phase").getAsJsonObject().get("flashgreen").getAsInt();
        int yellow = jsonObject.get("phase").getAsJsonObject().get("yellow").getAsInt();
        int redclear = jsonObject.get("phase").getAsJsonObject().get("redclear").getAsInt();
        int patternCount = jsonObject.get("pattern").getAsJsonObject().get("count").getAsInt();
        long split = jsonObject.get("pattern").getAsJsonObject().get("split").getAsLong();
        int planCount = jsonObject.get("plan").getAsJsonObject().get("count").getAsInt();
        int dateCount = jsonObject.get("date").getAsJsonObject().get("count").getAsInt();
        boolean allyear = jsonObject.get("date").getAsJsonObject().get("allyear").getAsBoolean();

        String[] intersectionInfo = type.split("-");
        String intersectionShape = intersectionInfo[0];
        String intersectionDirection = intersectionInfo[1];

        //声明8相位的相位方向及其相位
        Template template = new Template();
        int[][] direction = new int[phaseCount][];
        TemPhase[] temPhaseArray = new TemPhase[phaseCount];
        TemPattern[] temPatternArray = new TemPattern[patternCount];
        List<List<Ring>> rings = new ArrayList<>();
        TemPlan[] temPlanArray = new TemPlan[planCount];
        TemDate[] temDateArray = new TemDate[dateCount];

        // 确定每一个模板相位的id,方向,绿闪,黄灯,红灯清除
        // 1 表示T型路口
        if (intersectionShape.equals("100")) {
            switch (intersectionDirection) {
                case "001":
                    direction[0] = new int[]{9, 13};
                    direction[1] = new int[]{2};
                    direction[2] = new int[]{10};
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                    break;
                case "002":
                    direction[0] = new int[]{1, 5};
                    direction[1] = new int[]{2};
                    direction[2] = new int[]{14};
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                    break;
                case "003":
                    direction[0] = new int[]{9, 13};
                    direction[1] = new int[]{14};
                    direction[2] = new int[]{6};
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                    break;
                case "004":
                    direction[0] = new int[]{1, 5};
                    direction[1] = new int[]{6};
                    direction[2] = new int[]{10};
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                default:
                    break;
            }
        }
        // 2 表示十字型路口
        if (intersectionShape.equals("101")) {
            switch (phaseCount) {
                case 4:
                    direction[0] = new int[]{1, 5};
                    direction[1] = new int[]{2, 6};
                    direction[2] = new int[]{9, 13};
                    direction[3] = new int[]{10, 14};
                    phaseCount = 4;
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                    break;
                case 8:
                    direction[0] = new int[]{1};
                    direction[1] = new int[]{6};
                    direction[2] = new int[]{9};
                    direction[3] = new int[]{14};
                    direction[4] = new int[]{5};
                    direction[5] = new int[]{2};
                    direction[6] = new int[]{13};
                    direction[7] = new int[]{10};
                    phaseCount = 8;
                    setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
                default:
                    break;
            }
        }

        // 3 表示自定义路口
        if (intersectionShape.equals("999")) {
            for (int i = 0; i < phaseCount; i++) {
                direction[i] = new int[]{};
            }
            setAllTemPhase(phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear);
        }

        //确定每一个模板方案
        //确定rings, rings由四个ring组成,ring01,ring02,ring03,ring04
        //环的情况由相位数量决定
        int cycle = Math.min(phaseCount, 4) * (int) split;
        for (int i = 0; i < 4; i++) {
            rings.add(new ArrayList<>());
        }
        int j = 0;
        while (0 < phaseCount) {
            for (int i = 0; i < phaseCount; i++) {
                rings.get(j).add(new Ring(i + 1, "", (int) split));
                if (i == 3) break;
            }
            j += 1;
            phaseCount -= 4;
        }
        for (int i = 0; i < patternCount; i++) {
            temPatternArray[i] = new TemPattern();
            temPatternArray[i].setId(i + 1);
            temPatternArray[i].setCycle(cycle);
            temPatternArray[i].setDesc("");
            temPatternArray[i].setOffset(0);
            temPatternArray[i].setRings(rings);
        }

        //确定计划
        Plan plan = new Plan();
        List<Plan> plans = new ArrayList<>();
        plans.add(plan);
        int planId = 0;
        for (int i = 0; i < planCount; i++) {
            temPlanArray[i] = new TemPlan();
            planId = i + 1;
            temPlanArray[i].setId(planId);
            temPlanArray[i].setDesc("plan " + planId);
            temPlanArray[i].setPlan(plans);
        }

        //确定日期
        for (int i = 0; i < dateCount; i++) {
            temDateArray[i] = new TemDate();
            temDateArray[i].setId(i + 1);
            temDateArray[i].setDesc("");
            temDateArray[i].setPlan(1);
            if (allyear) {
                temDateArray[i].setMonth(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
                temDateArray[i].setDay(new int[]{1, 2, 3, 4, 5, 6, 7});
            }
        }

        //为该模板设置phase,pattern,plan,date
        template.setDateList(temDateArray);
        template.setPatternList(temPatternArray);
        template.setPhaseList(temPhaseArray);
        template.setPlanList(temPlanArray);
        return RESTRetUtils.successObj(template);
    }

    private void setTemPhase(TemPhase temPhase, int[] direction, int id, int flashgreen, int yellow, int redclear, int phaseCount) {
        temPhase.setDirection(direction);
        temPhase.setId(id + 1);
        temPhase.setFlashgreen(flashgreen);
        temPhase.setYellow(yellow);
        temPhase.setRedclear(redclear);
        //双环从第5个开始要设为2
        if (id > 3) {
            temPhase.setRing(2);
        }
        if (phaseCount == 8) {
            switch (id) {
                case 0:
                case 1:
                    temPhase.setConcurrent(new int[]{5,6});
                    break;
                case 2:
                case 3:
                    temPhase.setConcurrent(new int[]{7,8});
                    break;
                case 4:
                case 5:
                    temPhase.setConcurrent(new int[]{1,2});
                    break;
                case 6:
                case 7:
                    temPhase.setConcurrent(new int[]{3,4});
                default:
//                    break;
            }
        }
    }

    private void setAllTemPhase(int phaseCount, TemPhase[] temPhaseArray, int[][] direction, int flashgreen, int yellow, int redclear) {
        for (int i = 0; i < phaseCount; i++) {
            temPhaseArray[i] = new TemPhase();
            setTemPhase(temPhaseArray[i], direction[i], i, flashgreen, yellow, redclear, phaseCount);
        }
    }

    /**
     * @return RESTRetBase
     * @Title: getTemplate
     * @Description: 根据设备id获取路口类型和相位
     */
    @Path("intersection/info/{agentid}")
    @GET
    public RESTRetBase getTemplate(@PathParam("agentid") String agentid) throws SocketException, ParseException {

        //返回的路口类型
        String type = "999-000-00";

        //directionSet存放相位方向
        Set directionSet = new HashSet();
        //没有做处理的set集合,用于检查direction是否存在重复项
        Set alldirections = new HashSet();

        //使用设备通讯接口获取相位
        MessageData messageData = new MessageData(agentid, CosntDataDefine.getrequest, CosntDataDefine.phase);
        RESTRet<MessageData> retBase = null;
        retBase = deviceController.postDevsMessage( messageData);
        if (retBase.getMessage().equals("Device not online!")) {
            DevCommError devCommError = RESTRetUtils.errorObj(agentid, CosntDataDefine.errorrequest, CosntDataDefine.phase, IErrorEnumImplInner.E_301);
            return RESTRetUtils.errorDetialObj(E_4003, devCommError);
        }
        if (retBase.getData() == null) {
            return RESTRetUtils.errorDetialObj(E_4005, new DevCommError());
        }
        JsonArray phaseArray = retBase.getData().getData().getAsJsonObject().get("phaseList").getAsJsonArray();
        int phaseCount = 0;
        int directionsCount = 0;
        Gson gson = new Gson();
        for (JsonElement phase : phaseArray) {
            int[] directions = gson.fromJson(phase.getAsJsonObject().get("direction"), int[].class);
            directionsCount = directionsCount + directions.length;
            phaseCount = phase.getAsJsonObject().get("id").getAsInt();
            for (double direction : directions) {
                alldirections.add(direction);
                directionSet.add((int) Math.ceil(direction / 4));
            }
        }

        //相位用两位字符串表示，不足位数补0
        String phaseCountString = String.format("%2d", phaseCount).replace(" ", "0");

        //判断是什么路口类型
        if (isSetEqual(directionSet, new HashSet<>(Arrays.asList(1, 2, 3, 4)))) {
            type = "101-000-" + phaseCountString;
        }
        if (isSetEqual(directionSet, new HashSet<>(Arrays.asList(1, 2, 3)))) {
            type = "100-004-" + phaseCountString;
        }
        if (isSetEqual(directionSet, new HashSet<>(Arrays.asList(1, 2, 4)))) {
            type = "100-002-" + phaseCountString;
        }
        if (isSetEqual(directionSet, new HashSet<>(Arrays.asList(1, 3, 4)))) {
            type = "100-001-" + phaseCountString;
        }
        if (isSetEqual(directionSet, new HashSet<>(Arrays.asList(2, 3, 4)))) {
            type = "100-003-" + phaseCountString;
        }
        //如果direction中存在重复项，type仍然是999
        if (alldirections.size() != directionsCount) {
            type = "999-000-00";
        }

        //返回的json对象
        JsonObject intersectionInfo = new JsonObject();
        intersectionInfo.addProperty("type", type);
        intersectionInfo.add("phaseList", phaseArray);
        return RESTRetUtils.successObj(intersectionInfo);
    }

    public boolean isSetEqual(Set set1, Set set2) {

        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
            }
        }

        return isFullEqual;
    }
}
