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
import com.openatc.comm.common.UdpServer;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author
 * @ClassName: KDAgent
 * @Description: TODO
 * @date 2019年10月19日 下午6:50:52
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({FileProperties.class})
@ComponentScan({"com.openatc.agent", "com.openatc.comm", "com.openatc.core"})
public class AgentApplication implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(AgentApplication.class.toString());

    @Autowired
    private UdpServer udpServer;  //主动上报消息监听类

    @Autowired
    private AgentHandler agentHandler;  //主动上报消息处理类


    public static boolean shiroOpen;
    @Value("${agent.server.shiro}")
    private void setShiroOpen(Boolean shiroOpen){
        AgentApplication.shiroOpen = shiroOpen;
    }
    public static List<String> tokenlist = null;

    /**
     * @param args args
     * @Title: main
     * @Description: 入口函数
     */
    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);
    }

    //输出springboot加载的所有beans
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }

    @Override
    public void run(String... args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            tokenlist = JwtFileUtil.initList();
        } catch (IOException e) {
            logger.info("token.txt not found...");
//            e.printStackTrace();
        }
//        new Thread(new UDPServer()).start();
        udpServer.setHanlder(agentHandler);
        udpServer.run();

    }

    @Value("${server.http.port}")
    private Integer httpPort;

    /* SpringBoot 2.x版本(以及更高版本) 使用下面的代码 */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        return tomcat;
    }

    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(httpPort);
        return connector;
    }
}
