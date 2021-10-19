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
package com.openatc.configserver;

import com.openatc.configserver.utils.NoLogging;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;

public class Application {
    public static void main(String[] args) throws Exception {
        Log.setLog(new NoLogging());
        Server jettyServer = new Server();
        HttpConfiguration http_config = new HttpConfiguration();
        /**
         *http_config可以对服务器进行配置，比如设置https,BufferSize等等
         *        http_config.setSecureScheme("https");
         *        http_config.setSecurePort(8443);
         *        http_config.setOutputBufferSize(32768);
         *        http_config.setRequestHeaderSize(8192);
         *        http_config.setResponseHeaderSize(8192);
         */
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);

        /**
         * 新建http连接来设置访问端口，超时时间等等。
         */
        ServerConnector httpServer = new ServerConnector(jettyServer,
                new HttpConnectionFactory(http_config));
        httpServer.setPort(8012);
        httpServer.setIdleTimeout(120000);
        jettyServer.addConnector(httpServer);


        /**
         * 设置整个web服务的根url，/ 表示 localhost:8012/  之后地址的是可访问的
         */
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        /**
         * 添加动态servlet路径，处理我们自己写的动态的servlet
         */
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/openatc/*");
        jerseyServlet.setInitOrder(1);
        // Tells the Jersey Servlet which REST api/class to load.设置动态servlt加载的包
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.openatc.configserver.controller");
        //也可单独设置加载某个类，
//        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames","UploadFileService;org.glassfish.jersey.media.multipart.MultiPartFeature");


        /**
         * 添加默认的servlet路径，处理不在动态servlet路径中的地址，一般都是一些可供访问的静态html,css,js资源文件
         */
        ServletHolder staticServlet = context.addServlet(DefaultServlet.class, "/*");
        staticServlet.setInitParameter("resourceBase", "./dist");
        staticServlet.setInitParameter("pathInfoOnly", "true");


        /**
         * Embedded Jetty还可以直接当作服务器，在上面部署已经发布的war包，这方面的资料国内挺多的，就不累述
         *
         * 其次，在我们的项目中是没有用到web.xml文件来进行webappde的配置，因为上面的设置并不能使得服务器访问web.xml，
         * 如果需要用到web.xml，则需要new一个WebAppContext,并对其进行配置，同时在下面的handlers中加上webAppContext
         *  WebAppContext webAppContext = new WebAppContext();
         *  设置描述符位置
         *  webAppContext.setDescriptor("./web/WEB-INF/web.xml");
         *  设置Web内容上下文路径
         *  webAppContext.setResourceBase("./web");
         *  设置上下文路径
         *  webAppContext.setContextPath("/");
         *  webAppContext.setParentLoaderPriority(true);
         */

        HandlerCollection handlers = new HandlerCollection();
        // handlers.setHandlers(new Handler[]{context,webAppContext});
        handlers.setHandlers(new Handler[]{context});
        jettyServer.setHandler(handlers);

//        /**
//         * 添加过滤器
//         */
//        ServletHandler handler = new ServletHandler();
//        FilterHolder fh = handler.addFilterWithMapping((Class<? extends Filter>) OpenatcFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
//        context.addFilter(fh, "/*", EnumSet.of(DispatcherType.REQUEST));
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}