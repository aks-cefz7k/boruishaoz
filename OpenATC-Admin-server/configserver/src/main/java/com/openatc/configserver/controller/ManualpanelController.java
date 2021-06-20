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
import com.openatc.configserver.model.Channel;
import com.openatc.configserver.model.Keyconfig;
import com.openatc.configserver.model.LSCheck;
import com.openatc.configserver.model.Manualpanel;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManualpanelController {

    private int[] ewSPhase = new int[]{1, 5}; //东西直行
    private int[] nPPhase = new int[]{9, 10, 11, 12}; //北向通行
    private int[] ewLPhase = new int[]{2, 6}; //东西左转
    private int[] wPPhase = new int[]{5, 6, 7, 8}; //西向通行
    private int[] ePPhase = new int[]{1, 2, 3, 4}; //东向通行
    private int[] snSPhase = new int[]{9, 13}; //南北直行
    private int[] sPPhase = new int[]{13, 14, 15, 16}; //南向通行
    private int[] snLPhase = new int[]{10, 14}; //南北左转


    /**
     * @return RESTRetBase
     * @Title: getDefaultConfigByConfig
     * @Description: 根据设备生成默认配置
     */
    @Path("manualpanel/config")
    @POST
    public RESTRetBase getDefaultConfigByConfig(JsonObject jsonObject) {
        JsonArray phaseArray = jsonObject.get("phaseList").getAsJsonArray();
        JsonArray channelArray = jsonObject.get("channelList").getAsJsonArray();
        Manualpanel manualpanel = getManualpanel(phaseArray, channelArray);
        return RESTRetUtils.successObj(manualpanel);
    }

    private Manualpanel getManualpanel(JsonArray phaseArray, JsonArray channelArray) {
        Manualpanel manualpanel = new Manualpanel();
        // 3 使用获取的相位和通道生成灯状态检查表  [LSCheck(id=1, controlsource=1, direction=[9, 11, 13, 15]), LSCheck(id=2, controlsource=2, direction=[10, 14]), LSCheck(id=3, controlsource=3, direction=[1, 3, 5, 7]), LSCheck(id=4, controlsource=4, direction=[2, 6])]
        List<LSCheck> lsCheckList = new ArrayList<>();
        Gson gson = new Gson();
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
        List<List<Channel>> channelList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            channelList.add(new ArrayList<Channel>());
        }
        // 手动控制面板上八个按钮的相位方向数组
        int[][] buttonPhaseArray = {ewSPhase, nPPhase, ewLPhase, wPPhase, ePPhase, snSPhase, sPPhase, snLPhase};
        // 设置channelList
        for (LSCheck lsCheck : lsCheckList) {
            for (int i = 0; i < 12; i++) {
                if (i < 8) {
                    setChannelList(channelList, lsCheck, buttonPhaseArray[i], i);
                } else {
                    channelList.get(i).add(new Channel(lsCheck.getId(), 0));
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

    private void setChannelList(List<List<Channel>> channelList, LSCheck lsCheck, int[] arr, int i) {
        int[] direction = lsCheck.getDirection();
        Set directionSet = new HashSet<>(Arrays.asList(direction));
        boolean result = false;
        for (int j : arr) {
            if (directionSet.contains(j)) {
                result = true;
                break;
            }
        }
        if (result) {
            channelList.get(i).add(new Channel(lsCheck.getId(), 3));
        } else {
            channelList.get(i).add(new Channel(lsCheck.getId(), 1));
        }
    }
}