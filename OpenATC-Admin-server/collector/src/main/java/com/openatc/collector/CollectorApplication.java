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
package com.openatc.collector;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

/**
 * @author
 * @ClassName: CollectorApplication
 * @Description:
 * @date 2021年10月9日 下午6:50:52
 */
@SpringBootApplication
public class CollectorApplication implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(CollectorApplication.class.toString());

    @Value("${spring.influx.enable}")
    private String influxdbOpen;

    @Value("${agent.version}")
    private String serviceVersion;
    // 打包时间
    @Value("${agent.build.time}")
    private String serviceBuildDate;


    /**
     * @param args args
     * @Title: main
     * @Description: 入口函数
     */
    public static void main(String[] args) {
        SpringApplication.run(CollectorApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

        logger.warning("=================Project=================");
        logger.warning("service version:" + serviceVersion);
        logger.warning(String.format("service build date:%s",serviceBuildDate));
        logger.warning("InfluxDB Config：" + influxdbOpen);
        logger.warning("Current Path：" + System.getProperty("user.dir"));
        logger.warning("=================Project=================");

    }
}
