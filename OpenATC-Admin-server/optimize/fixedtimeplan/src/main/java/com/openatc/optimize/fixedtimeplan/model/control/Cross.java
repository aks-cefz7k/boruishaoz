package com.openatc.optimize.fixedtimeplan.model.control;


import com.alibaba.fastjson.JSON;
import com.openatc.optimize.fixedtimeplan.config.cross.Barrier;
import com.openatc.optimize.fixedtimeplan.config.cross.CrossConfig;

import com.openatc.optimize.fixedtimeplan.config.cross.Phase;
import com.openatc.optimize.fixedtimeplan.config.cross.Ring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author mz
 * @version 1.0
 * @date 2021/10/26 8:52
 */
@Data
@AllArgsConstructor
@Slf4j
@Builder
public class Cross {







    private Map<Integer, Phase> phaseMap;
    private String agentid;
    private String unitid;

    private double totalY;
    private double cycle;



    //路口初始化配置
    private CrossConfig crossConfig;


    public void updatePhaseMap(){
        for(Phase phase:crossConfig.getPhases()){
            phaseMap.put(phase.getId(), phase);
        }
    }



    private double updateTotalY(){
        double y = 0;
        for(Barrier barrier:crossConfig.getBarriers()){
            calcYofBarrier(barrier);
            log.info("y of barrer {} is {}", barrier.getNum(), barrier.getY());
            y += barrier.getY();
        }
        return y;
    }

    private void calcYofBarrier(Barrier barrier){
        double Y = 0;
        for(List<Integer> ids:barrier.getPhases()){
            double tempY = 0;
            for(Integer id:ids){
                tempY += phaseMap.get(id).Y();
            }
            log.info("temp Y of barrer {} is {}", barrier.getNum(), tempY);
            if(tempY > Y){
                Y = tempY;
            }
        }
        barrier.setY(Y);
    }

    private void assignDurationOfBarrier(){
        for(Barrier barrier:crossConfig.getBarriers()){
            barrier.setDuration(barrier.getY()/totalY*cycle);
            log.info("duration of barrier {} is {}", barrier.getNum(), barrier.getDuration());
            for(List<Integer> ids:barrier.getPhases()){
                double tempY = 0;
                for(Integer id : ids){
                    tempY += phaseMap.get(id).Y();
                }
                for(Integer id : ids){
                    phaseMap.get(id).setDuration(Math.round(barrier.getDuration()*phaseMap.get(id).Y()/tempY));
                }

            }

        }
    }


    public FixedtimePlan calcFixedtimePlan(){


        totalY = updateTotalY();

        log.info("Y of agent:{} is {}", agentid, totalY);
        double L = (crossConfig.getLoss()+crossConfig.getRed()) * crossConfig.getRings().get(0).getSequence().size();
        log.info("loss time of agentid: {} is {}", agentid, L);


        totalY  = totalY > 0.95 ? 0.95 : totalY;
        if(totalY < 0.9){

            //webster模型
            cycle = Math.ceil((1.5 * L + 5) / (1 - totalY));

        }else{

            //ARRB模型
            cycle = Math.ceil((1.4 * L + 6) / (1- totalY));
        }

        log.info("cycle of {} is {}", agentid, cycle);


        assignDurationOfBarrier();
        FixedtimePlan fixedtimePlan = FixedtimePlan.builder()
                .cycle(Math.round(cycle))
                .phase(new ArrayList<>(phaseMap.values()))
                .build();




        return fixedtimePlan;
    }


}
