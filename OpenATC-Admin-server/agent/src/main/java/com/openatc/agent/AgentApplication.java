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
package com.openatc.agent;


import com.openatc.agent.handler.AgentHandler;
import com.openatc.agent.model.FileProperties;
import com.openatc.agent.utils.JwtFileUtil;
import com.openatc.comm.model.UdpCommunicationStaticPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author
 * @ClassName: KDAgent
 * @Description:
 * @date 2019年10月19日 下午6:50:52
 */
@EnableScheduling
@EnableConfigurationProperties({FileProperties.class})
@ComponentScan({"com.openatc.agent", "com.openatc.comm", "com.openatc.core"})
@EnableTransactionManagement
@SpringBootApplication
public class AgentApplication implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(AgentApplication.class.toString());

    @Autowired
    private AgentHandler agentHandler;  //主动上报消息处理类

    @Value("${agent.server.shiro}")
    private String shiroOpen;

    @Value("${spring.redis.enable}")
    private String redisOpen;

    @Value("${agent.version}")
    private String serviceVersion;
    // 打包时间
    @Value("${agent.build.time}")
    private String serviceBuildDate;


    //    private void setShiroOpen(Boolean shiroOpen){
//        AgentApplication.shiroOpen = shiroOpen;
//    }
    public static List<String> tokenlist = null;

    /**
     * @param args args
     * @Title: main
     * @Description: 入口函数
     */
    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

        logger.warning("=================Project=================");
        logger.warning("service version:" + serviceVersion);
        logger.warning(String.format("service build date:%s",serviceBuildDate));
        logger.warning("Shiro Config：" + shiroOpen);
        logger.warning("Redis Config：" + redisOpen);
        logger.warning("Current Path：" + System.getProperty("user.dir"));
        logger.warning("=================Project=================");

        try {
            tokenlist = JwtFileUtil.initList();
        } catch (IOException e) {
            logger.warning("token.txt not found...");
        }

        logger.warning("token list：" + tokenlist);

        // 设置主动上报的消息处理函数
        UdpCommunicationStaticPort.hanlder = agentHandler;
    }

}
