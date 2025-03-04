package com.openatc.agent.utils;

import com.google.gson.Gson;
import com.openatc.agent.model.*;
import com.openatc.comm.data.MessageData;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author     ：zhangwenchao
 * @date       ：Created in 2021-9-14
 * @description：InfluxDB操作类
 * @modified By：
 * @version:     1.0.0
 */

@Component
public class InfluxDbUtils {

    private Logger log = Logger.getLogger(InfluxDbUtils.class.toString());

    @Value("${spring.influx.database}")
    private String database;

    // InfluxDB实例
    @Autowired
    private InfluxDB influxDB;

    Gson gson = new Gson();

    @PostConstruct
    public void init(){
        influxDB.setDatabase(database).enableBatch(10000,30000,TimeUnit.MILLISECONDS);
    }

    // 记录流量消息
    public int insertVolume(MessageData md){
        try{
            Volume volume = gson.fromJson(md.getData().getAsJsonObject(),Volume.class);

            if(volume == null)
                return 2;
            if(volume.getTime() == null)
                return 3;
            if(volume.getDetector() == null)
                return 4;

            long curtime = System.currentTimeMillis();
            for (VolumeDetector detector : volume.getDetector()) {
                Point point = Point.measurement("volume")
                        .tag("agentid",md.getAgentid())
                        .tag("detector", String.valueOf(detector.getId()))
                        .addField("largeveh",detector.getLargevehnum())
                        .addField("middleveh",detector.getMiddlevehnum())
                        .addField("smallveh",detector.getSmallvehnum())
                        .addField("totalvehtime",detector.getTotalvehtime())
                        .addField("occupyrate",detector.getOccupyrate())
                        .addField("volumetime",volume.getTime())
                        .time(curtime,TimeUnit.MILLISECONDS)
                        .build();
                influxDB.write(point);
            }
        }catch (Exception e){
            log.warning("InfluxDB insert Volume error:" + e.getMessage());
            return 1;
        }
        return 0;
    }

    // 记录灯色消息
    public int insertChannelLamp(MessageData md){
        try{
            ChannelLampMsg channelLampMsg = gson.fromJson(md.getData().getAsJsonObject(),ChannelLampMsg.class);

            if(channelLampMsg == null)
                return 2;
            if(channelLampMsg.getChannellamp() == null)
                return 3;

            long curtime = System.currentTimeMillis();

            for (ChannelLamp channelLamp : channelLampMsg.getChannellamp()) {
                Point point = Point.measurement("channellamp")
                        .tag("agentid",md.getAgentid())
                        .tag("channel", String.valueOf(channelLamp.getId()))
                        .addField("light",channelLamp.getLight())
                        .time(curtime,TimeUnit.MILLISECONDS)
                        .build();

                influxDB.write(point);
            }
        }catch (Exception e){
            log.warning("InfluxDB insert channellamp error:" + e.getMessage());
            return 1;
        }
        return 0;
    }

    // 记录上报方案
    public int insertPattern(MessageData md){
        try{
            AscPattern ascPattern = gson.fromJson(md.getData().getAsJsonObject(),AscPattern.class);

            if(ascPattern == null)
                return 2;

            // 判断周期剩余时间是否小于最后可变时间，如果小于的话，则代表周期即将结束，将方案存入数据库
            String phase = "";
            String stages = "";
            if(ascPattern.getSyncTime() > 3){
                for (AscPhase ascPhase : ascPattern.getPhase()) {
                    phase += "P" + ascPhase.getId() + ":" + ascPhase.getSplit() + ", ";

                }
                stages = ascPattern.getStages().toString();
            }


            Point point = Point.measurement("pattern")
                    .tag("agentid",md.getAgentid())
                    .addField("mode",ascPattern.getMode())
                    .addField("control",ascPattern.getControl())
                    .addField("patternid",ascPattern.getPatternid())
                    .addField("cycle",ascPattern.getCycle())
                    .addField("curTime",ascPattern.getCurTime())
                    .addField("syncTime",ascPattern.getSyncTime())
                    .addField("offset",ascPattern.getOffset())
                    .addField("total_stages",ascPattern.getTotal_stages())
                    .addField("stages",stages)
                    .addField("phase",phase)
                    .time(System.currentTimeMillis(),TimeUnit.MILLISECONDS)
                    .build();
            influxDB.write(point);

        }catch (Exception e){
            log.warning("InfluxDB insert pattern error:" + e.getMessage());
            return 1;
        }
        return 0;
    }

    // 记录测试
    public int   inserttest(){
        try{
            Point point = Point.measurement("volume")
                    .tag("agentid","1001")
                    .addField("detector",1)
                    .addField("largeveh",10)
                    .addField("middleveh",20)
                    .addField("smallveh",30)
                    .addField("totalvehtime",40)
                    .addField("occupyrate",50)
                    .addField("volumetime",60)
                    .time(System.currentTimeMillis(),TimeUnit.MILLISECONDS)
                    .build();

            influxDB.write(point);
        }catch (Exception e){
            log.warning("InfluxDB insert volume error:" + e.getMessage());
            return 1;
        }
        return 0;
    }


    // 查询
//    public Result getMeasurements(){
//        //构建查询语句，返回QueryResult
//        QueryResult rs = influxDB.query(new Query("select * from cpu_load_short", "mydb"));
//        logger.info("query result => {}", rs);
//        if (!rs.hasError() && !rs.getResults().isEmpty()) {
//            rs.getResults().forEach(System.out::println);
//        }
//        return  Result.ok(rs.getResults());
//    }
}
