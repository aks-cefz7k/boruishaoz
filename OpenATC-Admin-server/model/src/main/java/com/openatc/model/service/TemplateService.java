package com.openatc.model.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.model.model.*;
import com.openatc.model.util.MyArrayUtil;

import java.util.*;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/21 23:04
 * @description：Template
 * @modified By：
 * @version: $
 */
public class TemplateService {

    Gson gson = new Gson();

    public List<TypeAndPhase> getTemplate() {
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
        //两相位匝道路口
        typeList.add("103-001-02");  //东
        typeList.add("103-002-02");  //南
        typeList.add("103-003-02");  //西
        typeList.add("103-004-02");  //北
        //行人过街
        typeList.add("104-005-02");  //东西向两相位
        typeList.add("104-006-02");  //南北向两相位
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
            int[][] peddirection = new int[Integer.parseInt(phaseCount)][];

            if (intersectionShape.equals("100")) {
                switch (intersectionDirection) {
                    case "001":
                        direction[0] = new int[]{9, 13};
                        direction[1] = new int[]{2};
                        direction[2] = new int[]{10};
                        peddirection[0] = new int[]{1, 3};
                        peddirection[1] = new int[]{4};
                        peddirection[2] = new int[]{2};
                        break;
                    case "002":
                        direction[0] = new int[]{1, 5};
                        direction[1] = new int[]{2};
                        direction[2] = new int[]{14};
                        peddirection[0] = new int[]{2, 4};
                        peddirection[1] = new int[]{3};
                        peddirection[2] = new int[]{1};
                        break;
                    case "003":
                        direction[0] = new int[]{9, 13};
                        direction[1] = new int[]{14};
                        direction[2] = new int[]{6};
                        peddirection[0] = new int[]{1, 3};
                        peddirection[1] = new int[]{4};
                        peddirection[2] = new int[]{2};
                        break;
                    case "004":
                        direction[0] = new int[]{1, 5};
                        direction[1] = new int[]{6};
                        direction[2] = new int[]{10};
                        peddirection[0] = new int[]{2, 4};
                        peddirection[1] = new int[]{1};
                        peddirection[1] = new int[]{3};
                        break;
                    default:
                        direction[0] = new int[]{};
                        direction[0] = new int[]{};
                        direction[0] = new int[]{};
                        peddirection[0] = new int[]{};
                        peddirection[1] = new int[]{};
                        peddirection[1] = new int[]{};
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
                        peddirection[0] = new int[]{1, 3};
                        peddirection[1] = new int[]{};
                        peddirection[2] = new int[]{2, 4};
                        peddirection[3] = new int[]{};
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
                        peddirection[0] = new int[]{1};
                        peddirection[1] = new int[]{};
                        peddirection[2] = new int[]{4};
                        peddirection[3] = new int[]{};
                        peddirection[4] = new int[]{2};
                        peddirection[5] = new int[]{};
                        peddirection[6] = new int[]{3};
                        peddirection[7] = new int[]{};
                        break;
                    default:
                        break;
                }
            }

            //表示匝道
            if (intersectionShape.equals("103")) {
                switch (intersectionDirection) {
                    case "001":
                        direction[0] = new int[]{1};
                        break;
                    case "002":
                        direction[0] = new int[]{13};
                        break;
                    case "003":
                        direction[0] = new int[]{5};
                        break;
                    case "004":
                        direction[0] = new int[]{9};
                        break;
                }
                direction[1] = new int[]{};
            }

            //表示行人过街
            if(intersectionShape.equals("104")){
                switch (intersectionDirection){
                    case "005":  //东西方向
                        direction[0] = new int[]{1,5};
                        direction[1] = new int[]{};
                        peddirection[0] = new int[]{};
                        peddirection[1] = new int[]{16};
                        break;
                    case "006":
                        direction[0] = new int[]{9,13};
                        direction[1] = new int[]{};
                        peddirection[0] = new int[]{};
                        peddirection[1] = new int[]{15};
                        break;
                }

            }

            //到此方向已经设定好了
            List<SimTemPhase> simTemPhases = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(phaseCount); i++) {
                simTemPhases.add(new SimTemPhase(i + 1, direction[i], peddirection[i]));
            }

            TypeAndPhase typeAndPhase = new TypeAndPhase();

            typeAndPhase.setType(type);
            typeAndPhase.setPhaseList(simTemPhases);
            templateLists.add(typeAndPhase);

        }

        return templateLists;
    }

    public Template createTemplate(JsonObject jsonObject) {

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
        int[][] peddirection = new int[phaseCount][];
        Phase[] temPhaseArray = new Phase[phaseCount];
        Pattern[] temPatternArray = new Pattern[patternCount];
        List<List<Split>> rings = new ArrayList<>();
        Plan[] temPlanArray = new Plan[planCount];
        DateParam[] temDateArray = new DateParam[dateCount];

        // 确定每一个模板相位的id,方向,绿闪,黄灯,红灯清除
        // 1 表示T型路口
        if (intersectionShape.equals("100")) {
            switch (intersectionDirection) {
                case "001":
                    direction[0] = new int[]{9, 13};
                    direction[1] = new int[]{2};
                    direction[2] = new int[]{10};
                    peddirection[0] = new int[]{1, 3};
                    peddirection[1] = new int[]{4};
                    peddirection[2] = new int[]{2};
                    setAllTemPhase("100", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection, 0);
                    break;
                case "002":
                    direction[0] = new int[]{1, 5};
                    direction[1] = new int[]{2};
                    direction[2] = new int[]{14};
                    peddirection[0] = new int[]{2, 4};
                    peddirection[1] = new int[]{3};
                    peddirection[2] = new int[]{1};
                    setAllTemPhase("100", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "003":
                    direction[0] = new int[]{9, 13};
                    direction[1] = new int[]{14};
                    direction[2] = new int[]{6};
                    peddirection[0] = new int[]{1, 3};
                    peddirection[1] = new int[]{4};
                    peddirection[2] = new int[]{2};
                    setAllTemPhase("100", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "004":
                    direction[0] = new int[]{1, 5};
                    direction[1] = new int[]{6};
                    direction[2] = new int[]{10};
                    peddirection[0] = new int[]{2, 4};
                    peddirection[1] = new int[]{1};
                    peddirection[1] = new int[]{3};
                    setAllTemPhase("100", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
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
                    peddirection[0] = new int[]{1, 3};
                    peddirection[1] = new int[]{};
                    peddirection[2] = new int[]{2, 4};
                    peddirection[3] = new int[]{};
                    phaseCount = 4;
                    setAllTemPhase("101", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
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
                    peddirection[0] = new int[]{1};
                    peddirection[1] = new int[]{};
                    peddirection[2] = new int[]{4};
                    peddirection[3] = new int[]{};
                    peddirection[4] = new int[]{2};
                    peddirection[5] = new int[]{};
                    peddirection[6] = new int[]{3};
                    peddirection[7] = new int[]{};
                    phaseCount = 8;
                    setAllTemPhase("101",phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
            }
        }
        // 4 匝道
        if(intersectionShape.equals("103")){
            switch (intersectionDirection) {
                case "001":
                    direction[0] = new int[]{1};
                    direction[1] = new int[]{1};
                    setAllTemPhase("103", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "002":
                    direction[0] = new int[]{13};
                    direction[1] = new int[]{13};
                    setAllTemPhase("103", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "003":
                    direction[0] = new int[]{5};
                    direction[1] = new int[]{5};
                    setAllTemPhase("103", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "004":
                    direction[0] = new int[]{9};
                    direction[1] = new int[]{9};
                    setAllTemPhase("103", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
            }
        }
        //行人过街路口
        if(intersectionShape.equals("104")){
            switch (intersectionDirection){
                case "005":  //东西方向
                    direction[0] = new int[]{1,5};
                    peddirection[1] = new int[]{16};
                    setAllTemPhase("104", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;
                case "006":  //南北方向
                    direction[0] = new int[]{9,13};
                    peddirection[1] = new int[]{15};
                    setAllTemPhase("104", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
                    break;

            }
        }

        // 3 表示自定义路口
        if (intersectionShape.equals("999")) {
            for (int i = 0; i < phaseCount; i++) {
                direction[i] = new int[]{};
            }
            setAllTemPhase("999", phaseCount, temPhaseArray, direction, flashgreen, yellow, redclear, peddirection,0);
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
            if(j == 0){
                for (int i = 0; i < phaseCount; i++) {
                    rings.get(j).add(new Split(i + 1, (int) split,2));
                    if (i == 3) break;
                }
            }else if(j == 1){
                for(int i = 4; i < phaseCount; i++){
                    rings.get(j).add(new Split(i + 1, (int) split,2));
                }
                break;
            }
            j += 1;
            //phaseCount -= 4;
        }
        for (int i = 0; i < patternCount; i++) {
            temPatternArray[i] = new Pattern();
            temPatternArray[i].setId(i + 1);
            temPatternArray[i].setCycle(cycle);
            temPatternArray[i].setDesc("");
            temPatternArray[i].setOffset(0);
            temPatternArray[i].setRings(rings);
        }

        //确定计划
        PlanTime planTime = new PlanTime();
        planTime.setId(1);
        planTime.setControl(5);
        planTime.setPattern(1);

        List<PlanTime> planTimes = new ArrayList<>();
        planTimes.add(planTime);
        int planId = 0;
        for (int i = 0; i < planCount; i++) {
            temPlanArray[i] = new Plan();
            planId = i + 1;
            temPlanArray[i].setId(planId);
            temPlanArray[i].setDesc("plan " + planId);
            temPlanArray[i].setPlan(planTimes);
        }

        //确定日期
        for (int i = 0; i < dateCount; i++) {
            temDateArray[i] = new DateParam();
            temDateArray[i].setId(i + 1);
            temDateArray[i].setDesc("");
            temDateArray[i].setPlan(1);
            if (allyear) {
                temDateArray[i].setMonth(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
                temDateArray[i].setDay(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 0}));
            }
        }

        //为该模板设置phase,pattern,plan,date
        template.setDateList(temDateArray);
        template.setPatternList(temPatternArray);
        template.setPhaseList(temPhaseArray);
        template.setPlanList(temPlanArray);
        return template;
    }

    private void setAllTemPhase(String type, int phaseCount, Phase[] temPhaseArray, int[][] direction, int flashgreen, int yellow, int redclear, int[][] peddirection, int controltype) {
        for (int i = 0; i < phaseCount; i++) {
            temPhaseArray[i] = new Phase();
            Phase temPhase =  temPhaseArray[i];
            if(type.equals("103")){
                if(i == 0){
                    controltype = 0;
                }else{
                    controltype = 1;
                }
            }else if(type.equals("104")){
                if(i == 0){
                    controltype = 0;
                }else{
                    controltype = 2;
                }
            }
            List<Integer> phaseDir = MyArrayUtil.intArray2List(direction[i]);
            List<Integer> pedphaseDir = MyArrayUtil.intArray2List(peddirection[i]);

            temPhase = setTemPhase(temPhaseArray[i], phaseDir, i, flashgreen, yellow, redclear, phaseCount, controltype);
            temPhase.setPeddirection(pedphaseDir);
        }
    }

    private Phase setTemPhase(Phase temPhase, List<Integer> direction, int id, int flashgreen, int yellow, int redclear, int phaseCount, int controltype) {
        temPhase.setDirection(direction);
        temPhase.setId(id + 1);
        temPhase.setFlashgreen(flashgreen);
        temPhase.setYellow(yellow);
        temPhase.setRedclear(redclear);
        temPhase.setControltype(controltype);
        //双环从第5个开始要设为2
        if (id > 3)
            temPhase.setRing(2);
        else
            temPhase.setRing(1);

        if (phaseCount == 8) {
            switch (id) {
                case 0:
                case 1:
                    temPhase.setConcurrent(Arrays.asList(new Integer[]{5, 6}) );
                    break;
                case 2:
                case 3:
                    temPhase.setConcurrent(Arrays.asList(new Integer[]{7, 8}) );
                    break;
                case 4:
                case 5:
                    temPhase.setConcurrent(Arrays.asList(new Integer[]{1, 2}) );
                    break;
                case 6:
                case 7:
                    temPhase.setConcurrent(Arrays.asList(new Integer[]{3, 4}) );
                default:
                    break;
            }
        }
        return temPhase;
    }

    public JsonObject getIntersectionInfo(JsonObject asJsonObject) {
        //返回的路口类型
        String type = "999-000-00";

        //directionSet存放相位方向
        Set directionSet = new HashSet();

        JsonArray phaseArray = asJsonObject.get("phaseList").getAsJsonArray();
        JsonElement overlapElement = asJsonObject.get("overlaplList");
        JsonArray overlapArray = new JsonArray();
        if (overlapElement != null) {
            overlapArray = overlapElement.getAsJsonArray();
        }
        JsonArray phaseAndOverlapArray = new JsonArray();
        phaseAndOverlapArray.addAll(phaseArray);
        phaseAndOverlapArray.addAll(overlapArray);
        int phaseCount = phaseArray.size();
        //相位用两位字符串表示，不足位数补0
        String phaseCountString = String.format("%2d", phaseCount).replace(" ", "0");

        //if (!directionConflict(phaseArray) && !directionConflict(overlapArray))

        //判断是否是T型或十字型路口
        type = calTenOrType(phaseAndOverlapArray, type, directionSet, phaseCountString);
        if (type.equals("999-000-00")){
            //判断是不是人行横道
            type = calPedCrossType(phaseAndOverlapArray, type, phaseCountString);
        }
        //判断是否是匝道类型的路开口
        if (type.equals("999-000-00")) {
            type = calRampType(phaseArray, type, phaseCountString);
        }
        //返回的json对象
        JsonObject intersectionInfo = new JsonObject();
        intersectionInfo.addProperty("type", type);
        intersectionInfo.add("phaseList", phaseArray);
        intersectionInfo.add("overlaplList", overlapArray);
        return intersectionInfo;
    }


    /**
     * 判断direction时候会有冲突
     */
    private boolean directionConflict(JsonArray jsonArray) {
        Set set = new HashSet<>();
        for (JsonElement jsonElement : jsonArray) {
            int[] directions = gson.fromJson(jsonElement.getAsJsonObject().get("direction"), int[].class);
            if (directions == null || directions.length == 0) continue;
            for (double direction : directions) {
                if (set.contains(direction)) {
                    return true;
                } else {
                    set.add(direction);
                }
            }
        }
        return false;
    }

    private String calPedCrossType(JsonArray phaseArray, String type, String phaseCountString) {
        for (JsonElement phase : phaseArray) {
            if (phase.getAsJsonObject().get("controltype") == null || phase.getAsJsonObject().get("peddirection") == null)
                continue;
            int[] peddirection = gson.fromJson(phase.getAsJsonObject().get("peddirection"), int[].class);

            // 确定为人行横道
            if (phase.getAsJsonObject().get("controltype").getAsInt() == 2) {
                for (Integer direction : peddirection) {
                    if (direction == 15) return "104-005-" + phaseCountString;
                    else if (direction == 16) return "104-006-" + phaseCountString;
                    else return "999-000-00" ;
                }
            }
        }
        return type;
    }



    private String calTenOrType(JsonArray phaseAndOverlapArray, String type, Set directionSet, String phaseCountString) {
        for (JsonElement phaseAndOverlap : phaseAndOverlapArray) {
            int[] directions = gson.fromJson(phaseAndOverlap.getAsJsonObject().get("direction"), int[].class);
            if (directions == null || directions.length == 0) continue;
            for (double direction : directions) {
                directionSet.add((int) Math.ceil(direction / 4));
            }
        }
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
        return type;
    }


    //判断方向是不是全空
    public boolean allDirectionEmpty(Set<String> allStringDirections) {
        Iterator<String> iterator = allStringDirections.iterator();
        if (allStringDirections.size() == 1 && iterator.next().equals("")) {
            return true;
        } else {
            return false;
        }
    }


    //判断匝道路口类型
    public String calRampType(JsonArray phaseArray, String type, String phaseCountString) {
        Map<Integer, String> map = new HashMap<>();
        //存放所有方向
        Set<String> allStringDiretions = new HashSet<>();

        for (JsonElement phase : phaseArray) {
            // 没有方向,直接跳到下一个相位;
            if (phase.getAsJsonObject().get("direction") == null) continue;
            String stringDirections = arrayToString(gson.fromJson(phase.getAsJsonObject().get("direction"), int[].class));
            allStringDiretions.add(stringDirections);

            int controltype = phase.getAsJsonObject().get("controltype") == null ? 0 : phase.getAsJsonObject().get("controltype").getAsInt();
            // map中没有控制类型, 添加到map中
            if (!map.containsKey(controltype)) {
                map.put(controltype, stringDirections);
            }
            // 把map中的类型都取出来,必须完全相等,否则就不是闸道,直接返回原来的类型
            if (map.containsKey(0)) {
                if (!map.get(0).equals(stringDirections)) return type;
            }
            if (map.containsKey(1)) {
                if (!map.get(1).equals(stringDirections)) return type;
            }
            if(map.containsKey(99)){
                if(!map.get(99).equals(stringDirections)) return type;
            }
        }

        // 检测map, 并查看是否是匝道类型，以及匝道的方向 (其实到这里基本上判断出就是匝道类型了，主要是判断匝道方向)
        if (allDirectionEmpty(allStringDiretions)) return type;
        int direction = 1;
        if (map.containsKey(0)) {
            direction = (int) Math.ceil(Double.parseDouble(map.get(0)) / 4);
        } else if (map.containsKey(1)) {
            direction = (int) Math.ceil(Double.parseDouble(map.get(1)) / 4);
        }else if(map.containsKey(99)){
            return type;
        }
        switch (direction) {
            case 1:
                type = "103-001-" + phaseCountString;
                break;
            case 2:
                type = "103-003-" + phaseCountString;
                break;
            case 3:
                type = "103-004-" + phaseCountString;
                break;
            case 4:
                type = "103-002-" + phaseCountString;
        }
        return type;
    }

    public String arrayToString(int[] directions) {
        Arrays.sort(directions);
        StringBuilder stringDirection = new StringBuilder();
        for (Integer direction : directions) {
            stringDirection.append(direction);
        }
        return stringDirection.toString();
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
