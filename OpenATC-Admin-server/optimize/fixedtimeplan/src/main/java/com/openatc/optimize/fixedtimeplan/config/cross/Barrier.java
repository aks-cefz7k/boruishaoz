package com.openatc.optimize.fixedtimeplan.config.cross;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author mz
 * @version 1.0
 * @date 2021/11/3 15:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Barrier {
    private int num;
    private List<BarrierItem> items;
    private double y;
    private double duration;

    public void adjustPhaseDuration(Map<Integer, Phase> phaseMap){
        boolean adjusted = false;
        /**
         * 设置最大最小绿
         * */
        for(BarrierItem barrierItem: items){

            for(Integer id : barrierItem.getData()){
                if(phaseMap.get(id).getDuration() < phaseMap.get(id).getMingreen()){
                    phaseMap.get(id).setDuration(phaseMap.get(id).getMingreen());
                    phaseMap.get(id).setAdjusted(true);
                    barrierItem.setAdjusted(true);
                    adjusted = true;
                }else if(phaseMap.get(id).getDuration() > phaseMap.get(id).getMaxgreen()){
                    phaseMap.get(id).setDuration(phaseMap.get(id).getMaxgreen());
                    phaseMap.get(id).setAdjusted(true);
                    barrierItem.setAdjusted(true);
                    adjusted = true;
                }
            }
        }


        if(!adjusted){
            return;
        }
        /**
         * 重新计算barrier长度
         * */
        int newduration = 0 ;
        for(BarrierItem barrierItem: items){
            int tempduration = 0;
            for(Integer id : barrierItem.getData()){
                tempduration += phaseMap.get(id).getDuration();
            }
            barrierItem.setDuration(tempduration);
            if(tempduration > newduration){
                newduration = tempduration;
            }
        }




        /**
         * 重新计算相位长度
         * */
        for(BarrierItem item:items){
            List<Integer> ids = item.getData();
            if(!item.isAdjusted()){

                double tempY = 0;
                for(Integer id : ids){
                    tempY += phaseMap.get(id).Y();
                }
                for(Integer id : ids){
                    phaseMap.get(id).setDuration(Math.round(newduration * phaseMap.get(id).Y()/tempY));
                }
            }else{
                for(Integer id : ids){
                    if(phaseMap.get(id).isAdjusted()){
                        continue;
                    }else{
                        long diff = newduration - item.getDuration();
                        long curduration = phaseMap.get(id).getDuration();
                        phaseMap.get(id).setDuration(curduration + diff);
                        break;
                    }
                }
            }

        }

        duration = newduration;



    }



}
