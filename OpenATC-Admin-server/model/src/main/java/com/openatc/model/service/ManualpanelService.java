package com.openatc.model.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.openatc.model.model.*;
import com.openatc.model.util.MyArrayUtil;

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

        List<ChannelofSource> channelofSourceList = createChannelofSourceList(phaseArray, channelArray);

        // channelLockListForKeys 表示12个键的List<ChannelLock>的集合
        List<List<ChannelLock>> channelLockListForKeys = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            channelLockListForKeys.add(new ArrayList<ChannelLock>());
        }
        // 手动控制面板上八个按钮的相位方向数组
        int[][] buttonPhaseArray = {ewSPhase, nPPhase, ewLPhase, wPPhase, ePPhase, snSPhase, sPPhase, snLPhase};
        // 设置channelList
        for (ChannelofSource channelofSource : channelofSourceList) {
            for (int i = 0; i < 12; i++) {
                if (i < 8) { // 前8个固定方向按钮
                    setChannelList(channelLockListForKeys.get(i), channelofSource, buttonPhaseArray[i], phaseArray);
                } else { // 后4个自定义按钮
                    channelLockListForKeys.get(i).add(new ChannelLock(channelofSource.getId(), 0));
                }
            }
        }
        //设置keyconfigList
        List<Keyconfig> keyconfigList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            keyconfigList.add(new Keyconfig(i + 1, "", channelLockListForKeys.get(i)));
        }
        manualpanel.setKeyconfig(keyconfigList);
        return manualpanel;
    }

    // 将路口的交通流锁定消息转换为信号机的方向锁定控制
    public ControlPattern LockDirection2ControlPattern(JsonArray phaseArray, JsonArray channelArray, LockDirection lockDirection) {

        // 生成方向锁定通道信息
        List<ChannelLock> channelLocks = new ArrayList<>();
        for (ChannelofSource channelofSource : createChannelofSourceList(phaseArray, channelArray)) {
            setChannelList(channelLocks, channelofSource, MyArrayUtil.intList2Array(lockDirection.getDirection()), phaseArray);
        }

        // 设置方向锁定消息
        ControlPattern controlPattern = new ControlPattern();
        LockDirectionForControlPattern lockDirectionForControlPattern = new LockDirectionForControlPattern(lockDirection);
        controlPattern.setControl(16);  // 16 - 方向锁定控制
        lockDirectionForControlPattern.setChannellocKinfo(channelLocks);
        controlPattern.setData(lockDirectionForControlPattern);

        return controlPattern;
    }

    // 使用获取的相位和通道生成灯状态检查表  [LSCheck(id=1, controlsource=1, direction=[9, 11, 13, 15]), LSCheck(id=2, controlsource=2, direction=[10, 14]), LSCheck(id=3, controlsource=3, direction=[1, 3, 5, 7]), LSCheck(id=4, controlsource=4, direction=[2, 6])]
    private List<ChannelofSource> createChannelofSourceList(JsonArray phaseArray, JsonArray channelArray){
        // List<Channel> [{"channelid":1,"channelstatus":1},{"channelid":2,"channelstatus":1},{"channelid":3,"channelstatus":3},{"channelid":4,"channelstatus":1}]

        List<ChannelofSource> channelofSourceList = new ArrayList<>();
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
            channelofSourceList.add(new ChannelofSource(channelid, channelcontrolsource, channeldirection));
        }
        return channelofSourceList;
    }

    public void setChannelList(List<ChannelLock> channelLockList, ChannelofSource channelofSource, int[] derictions, JsonArray phaseArray) {

        //ChannelofSource 所有通道对应相位的组合
        //derictions 表示要执行的方向

        int channelstatus = 1; // 默认 1 - 红灯
        // 查找通道中所有的控制源方向
        int[] phasedirections = channelofSource.getDirection();
        for (int deriction : derictions) {
            for(int phasedirection : phasedirections){
                // 控制源中查询到锁定的方向，则该通道设为放行，绿灯
                if ( deriction == phasedirection){
                    channelstatus = 3;// 3 - 绿灯
                    break;
                }
            }

        }
        channelLockList.add(new ChannelLock(channelofSource.getId(), channelstatus));

//        if (isMatch) {
//            // 这里要多一层校验
//            // 第一个按钮，东西执行（1、5）
//            check(channelLockList, channelofSource, phaseArray, gson, derictions);
//        } else { // 通道中没有找到执行方向
//            channelLockList.add(new ChannelLock(channelofSource.getId(), 1)); // 1 - 红灯
//        }
    }

    public void check(List<ChannelLock> channelLockList, ChannelofSource channelofSource, JsonArray phaseArray, Gson gson, int[] derictions) {
        int x = 0;
        for (int k = 0; k < phaseArray.size(); k++) {
            if (x != 0) break;
            int[] directions0 = gson.fromJson(phaseArray.get(k).getAsJsonObject().get("direction"), int[].class);
            int[] concurrent0 = gson.fromJson(phaseArray.get(k).getAsJsonObject().get("concurrent"), int[].class);
            int id = phaseArray.get(k).getAsJsonObject().get("id").getAsInt();
            for (int s : derictions) {
                if (x != 0) break;
                if (Arrays.asList(directions0).contains(s)) {
                    for (int u = 0; u < phaseArray.size(); u++) {
                        if (u == k) continue;
                        int[] directions1 = gson.fromJson(phaseArray.get(u).getAsJsonObject().get("direction"), int[].class);
                        int[] concurrent2 = gson.fromJson(phaseArray.get(u).getAsJsonObject().get("concurrent"), int[].class);
                        int id2 = phaseArray.get(u).getAsJsonObject().get("id").getAsInt();
                        for (int m : derictions) {
                            if (Arrays.asList(directions1).contains(m)) {
                                if (!(Arrays.asList(concurrent2).contains(id) && Arrays.asList(concurrent0).contains(id2))) {
                                    channelLockList.add(new ChannelLock(channelofSource.getId(), 1));
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
            channelLockList.add(new ChannelLock(channelofSource.getId(), 3));
        }
    }
}
