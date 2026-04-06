package com.openatc.model.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.openatc.model.model.ChannelLock;
import com.openatc.model.model.Keyconfig;
import com.openatc.model.model.LSCheck;
import com.openatc.model.model.Manualpanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/21 22:59
 * @description：Manualpanel
 * @modified By：
 * @version: $
 */
public class ManualpanelService {
    private int[] ewSPhase = new int[]{1, 5}; //东西直行
    private int[] nPPhase = new int[]{9, 10, 11, 12}; //北向通行
    private int[] ewLPhase = new int[]{2, 6}; //东西左转
    private int[] wPPhase = new int[]{5, 6, 7, 8}; //西向通行
    private int[] ePPhase = new int[]{1, 2, 3, 4}; //东向通行
    private int[] snSPhase = new int[]{9, 13}; //南北直行
    private int[] sPPhase = new int[]{13, 14, 15, 16}; //南向通行
    private int[] snLPhase = new int[]{10, 14}; //南北左转

    Gson gson = new Gson();


    public Manualpanel getManualpanel(JsonArray phaseArray, JsonArray channelArray) {
        Manualpanel manualpanel = new Manualpanel();
        // 3 使用获取的相位和通道生成灯状态检查表  [LSCheck(id=1, controlsource=1, direction=[9, 11, 13, 15]), LSCheck(id=2, controlsource=2, direction=[10, 14]), LSCheck(id=3, controlsource=3, direction=[1, 3, 5, 7]), LSCheck(id=4, controlsource=4, direction=[2, 6])]
        List<LSCheck> lsCheckList = new ArrayList<>();
        for (JsonElement channel : channelArray) {
            int channelid = channel.getAsJsonObject().get("id").getAsInt();
            int channelcontrolsource = channel.getAsJsonObject().get("controlsource").getAsInt();
            int[] channeldirection = null;
            for (JsonElement phase : phaseArray) {
                int phaseid = phase.getAsJsonObject().get("id").getAsInt();
                if (channelcontrolsource == phaseid) {
                    channeldirection = gson.fromJson(phase.getAsJsonObject().get("direction"), int[].class);
                }
            }
            lsCheckList.add(new LSCheck(channelid, channelcontrolsource, channeldirection));
        }
        // List<Channel> [{"channelid":1,"channelstatus":1},{"channelid":2,"channelstatus":1},{"channelid":3,"channelstatus":3},{"channelid":4,"channelstatus":1}]
        // channelList 表示如上List<Channel>的集合
        List<List<ChannelLock>> channelList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            channelList.add(new ArrayList<ChannelLock>());
        }
        // 手动控制面板上八个按钮的相位方向数组
        int[][] buttonPhaseArray = {ewSPhase, nPPhase, ewLPhase, wPPhase, ePPhase, snSPhase, sPPhase, snLPhase};
        // 设置channelList
        for (LSCheck lsCheck : lsCheckList) {
            for (int i = 0; i < 12; i++) {
                if (i < 8) {
                    setChannelList(channelList, lsCheck, buttonPhaseArray[i], i, phaseArray);
                } else {
                    channelList.get(i).add(new ChannelLock(lsCheck.getId(), 0));
                }
            }
        }
        //设置keyconfigList
        List<Keyconfig> keyconfigList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            keyconfigList.add(new Keyconfig(i + 1, "", channelList.get(i)));
        }
        manualpanel.setKeyconfig(keyconfigList);
        return manualpanel;
    }

    private void setChannelList(List<List<ChannelLock>> channelList, LSCheck lsCheck, int[] arr, int i, JsonArray phaseArray) {
        //channelList中第一个元素，指的是第一个按钮，东西执行中所有channel的数据
        //lsCheck用来对比arr,设置什么灯色
        //arr[0] 就是[1,5]表示东西执行的方向
        //i表示第几个按钮
        int[] direction = lsCheck.getDirection();
        boolean result = false;
        for (int j : arr) {
            if (Arrays.asList(direction).contains(j)) {
                result = true;
                break;
            }
        }
        if (result) {
            // 这里要多一层校验
            // 第一个按钮，东西执行（1、5）
            check(channelList, lsCheck, i, phaseArray, gson, arr);
        } else {
            channelList.get(i).add(new ChannelLock(lsCheck.getId(), 1)); //1红灯，3绿灯
        }
    }

    public void check(List<List<ChannelLock>> channelList, LSCheck lsCheck, int i, JsonArray phaseArray, Gson gson, int[] arr) {
        int x = 0;
        for (int k = 0; k < phaseArray.size(); k++) {
            if (x != 0) break;
            int[] directions0 = gson.fromJson(phaseArray.get(k).getAsJsonObject().get("direction"), int[].class);
            int[] concurrent0 = gson.fromJson(phaseArray.get(k).getAsJsonObject().get("concurrent"), int[].class);
            int id = phaseArray.get(k).getAsJsonObject().get("id").getAsInt();
            for (int s : arr) {
                if (x != 0) break;
                if (Arrays.asList(directions0).contains(s)) {
                    for (int u = 0; u < phaseArray.size(); u++) {
                        if (u == k) continue;
                        int[] directions1 = gson.fromJson(phaseArray.get(u).getAsJsonObject().get("direction"), int[].class);
                        int[] concurrent2 = gson.fromJson(phaseArray.get(u).getAsJsonObject().get("concurrent"), int[].class);
                        int id2 = phaseArray.get(u).getAsJsonObject().get("id").getAsInt();
                        for (int m : arr) {
                            if (Arrays.asList(directions1).contains(m)) {
                                if (!(Arrays.asList(concurrent2).contains(id) && Arrays.asList(concurrent0).contains(id2))) {
                                    channelList.get(i).add(new ChannelLock(lsCheck.getId(), 1));
                                    x++;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (x == 0) {
            channelList.get(i).add(new ChannelLock(lsCheck.getId(), 3));
        }
    }
}
