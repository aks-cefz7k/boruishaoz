## OpenATC-Admin
<a href="" rel="nofollow"><img alt="JDK" src="https://img.shields.io/badge/build-passing-brightgreen" style="max-width:100%;"></a>
<a href="" rel="nofollow"><img alt="author" src="https://img.shields.io/badge/author-kedacom-green" style="max-width:100%;"></a>
<a href="https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/" rel="nofollow"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-2.1.5.RELEASE-orange" style="max-width:100%;"></a>
<a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html" rel="nofollow"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_162-brightgreen.svg" style="max-width:100%;"></a>
<a href="" rel="nofollow"><img alt="author" src="https://img.shields.io/badge/license-Mulan PSL v2-green" style="max-width:100%;"></a>

### 简介

- 这是一款开源的现代交通信号控制软件，可应用于小型、大型、特殊路口等多种场景，并且包含了灯控、检测、故障以及用户管理等多种功能，快来使用它吧！

---

### 准备工作

在进行开发之前，您需要安装以下程序：

- git
- Java 1.8+
- Maven 3.3.9+
- npm 5 or above
- Vue

---

### 文档目录
```
PROJECT_FOLDER
├─logs   //存放服务运行的日志，每次运行会删除旧日志，保存新日志
├─OpenATC-Admin-server  //服务端项目
│  │  build.sh
│  ├─codestyle
│  │      checkstyle.xml
│  ├─docker
│  │  └─stack
│  │          openatc-stack.yml     //使用docker部署服务的脚本文件
│  ├─KDAgent
│  │  │  Dockerfile
│  │  │  kdagent.iml
│  │  │  pom.xml
│  │  ├─src
│  │  │  └─main
│  │  │      ├─java
│  │  │      │  └─com
│  │  │      │      └─kedacom
│  │  │      │          └─openatc
│  │  │      │              └─kdagent
│  │  │      │                  │  KDAgentApplication.java         //KDAgent服务启动入口
│  │  │      │                  ├─config
│  │  │      │                  │      CorsConfig.java
│  │  │      │                  │      LazyInitBeanFactoryPostProcessor.java   //懒加载，减少服务启动时间
│  │  │      │                  │      ShiroConfig.java            //shiro配置文件
│  │  │      │                  ├─controller
│  │  │      │                  │      DevController.java          //设备 
│  │  │      │                  │      FileController.java         //文件上传与下载
│  │  │      │                  │      HisParamsController.java    //操作记录
│  │  │      │                  │      ManualpanelController.java  //手动面板
│  │  │      │                  │      MessageController.java      //设备通讯
│  │  │      │                  │      RoleController.java         //角色   
│  │  │      │                  │      RouteController.java        //路线
│  │  │      │                  │      SysConfigController.java    //系统配置
│  │  │      │                  │      TemplateController.java     //模板
│  │  │      │                  │      UserController.java         //用户
│  │  │      │                  ├─data
│  │  │      │                  ├─handler
│  │  │      │                  ├─http
│  │  │      │                  ├─model     //存放实体类
│  │  │      │                  ├─realm     //存放shiro配置的相关类
│  │  │      │                  │      GlobalDefaultExceptionHandler.java
│  │  │      │                  │      JwtAuthenticationFilter.java
│  │  │      │                  │      JwtToken.java
│  │  │      │                  │      MyRealm.java
│  │  │      │                  │      StatelessDefaultSubjectFactory.java
│  │  │      │                  ├─resmodel
│  │  │      │                  ├─service   //service层
│  │  │      │                  └─utils     //存放工具类
│  │  │      │
│  │  │      └─resources
│  │  │          │  application-docker.properties //docker配置文件
│  │  │          │  application.properties        //kdagent服务配置文件
│  │  │          │  banner.txt
│  │  │          │  kd014693.com.jks              //ssl证书
│  │  │          │  kdstore.p12                   //jdk证书
│  │  │          │  log4j2.xml                    //日志配置文件
│  │  │          │
│  │  │          ├─db
│  │  │          │  └─migration   //存放数据库的建表文件
│  │  │          └─static   //存放前端生成的dist文件
│  │  └─target
│  │
│  ├─algorithm   //算法包
│  ├─KDComm        //通讯包，用于服务和设备的通讯
│  └─KDCore        //异常包，存放服务的错误代码和信息
└─OpenATC-Admin-ui // 前端项目
    ├─OpenATC-Admin-web // 平台主项目
    │  ├─build // 打包配置
    │  ├─config // 项目基础配置信息
    │  ├─src // 业务逻辑块
    │  │  ├─api // 接口api
    │  │  ├─assets // 静态图片块
    │  │  │  ├─404_images
    │  │  │  ├─gis
    │  │  │  ├─home
    │  │  │  └─icon
    │  │  ├─components // 组件类
    │  │  │  ├─HorizontalChildren
    │  │  │  ├─Kanban
    │  │  │  ├─MessageBox
    │  │  │  ├─OpenATCCard
    │  │  │  ├─PatternStatus
    │  │  │  ├─SvgIcon
    │  │  │  ├─TreeTable
    │  │  │  ├─TscBottom
    │  │  │  └─XRDDirSelector
    │  │  ├─i18n // 国际化配置块
    │  │  │  └─language
    │  │  ├─icons // svg静态图片块
    │  │  │  └─svg
    │  │  ├─mock
    │  │  │  └─service
    │  │  ├─model // 基础模型块
    │  │  │  └─tree
    │  │  ├─router // 路由配置
    │  │  ├─store // 存储配置
    │  │  │  └─modules
    │  │  ├─styles // 皮肤样式配置
    │  │  │  └─theme
    │  │  ├─utils // 工具类
    │  │  └─views // 页面文件
    │  │      ├─compose
    │  │      │  └─components
    │  │      ├─DeviceManage
    │  │      │  └─DeviceDialog
    │  │      ├─GisManager
    │  │      │  └─components
    │  │      ├─GreenWaveOptimize
    │  │      │  ├─components
    │  │      │  ├─pattern
    │  │      │  │  └─planChart
    │  │      │  │      └─model
    │  │      │  └─tables
    │  │      │      └─components
    │  │      ├─Home
    │  │      │  └─lottieDemo
    │  │      ├─login
    │  │      ├─OperationRecord
    │  │      ├─Organization
    │  │      │  └─components
    │  │      ├─Service
    │  │      │  ├─components
    │  │      │  ├─pattern
    │  │      │  │  └─planChart
    │  │      │  └─tables
    │  │      │      └─components
    │  │      └─UserManage
    │  │          └─UserDialog
    │  └─static
    │      └─lottiejson
    └─OpenATC-Configer-web // 配置工具项目（该项目以组件的形式嵌套在平台主项目里）
        ├─build // 打包配置
        ├─config // 项目基础配置
        ├─src // 业务逻辑块
        │  ├─api // 接口api
        │  ├─assets // 静态图片块
        │  │  ├─404_images
        │  │  ├─icon
        │  │  ├─intersection_state
        │  │  └─intersection_type
        │  ├─components // 组件类
        │  │  ├─Breadcrumb
        │  │  ├─FloatImgBtn
        │  │  ├─Hamburger
        │  │  ├─Kanban
        │  │  ├─MessageBox
        │  │  ├─PatternStatus
        │  │  ├─StageStatus
        │  │  ├─SvgIcon
        │  │  ├─Tankuang
        │  │  ├─TreeTable
        │  │  ├─TscBottom
        │  │  └─XRDDirSelector
        │  ├─EdgeMgr // 逻辑管理类
        │  │  └─controller
        │  ├─i18n // 国际化配置块
        │  │  └─language
        │  ├─icons // svg静态图片块
        │  │  └─svg
        │  ├─lib // 模板类
        │  │  └─publicjs
        │  │      └─KissWSSub
        │  ├─packge // 项目打包配置
        │  ├─router // 路由配置
        │  ├─store // 存储配置
        │  │  └─modules
        │  ├─styles // 皮肤样式配置
        │  │  └─theme
        │  ├─ThemeStyle // element ui样式配置
        │  ├─utils // 工具类
        │  └─views // 页面文件
        │      ├─channel
        │      ├─channelLock
        │      ├─control
        │      ├─controlPanel
        │      ├─dailyRecord
        │      ├─dashboard
        │      ├─date
        │      ├─detector
        │      ├─deviceInfo
        │      ├─history
        │      │  ├─dialog
        │      │  └─table
        │      ├─importTempDialog
        │      │  └─crossDirection
        │      │      └─baseImg
        │      ├─layout
        │      │  ├─components
        │      │  │  └─Sidebar
        │      │  └─mixin
        │      ├─login
        │      ├─optimize
        │      ├─overlap
        │      ├─overView
        │      │  ├─crossDirection
        │      │  │  └─baseImg
        │      │  └─textPage
        │      ├─pattern
        │      ├─pedestrianDetector
        │      ├─phase
        │      │  └─images
        │      ├─plan
        │      ├─statistics
        │      ├─system
        │      │  ├─channelControl
        │      │  ├─manualControl
        │      │  └─systemDialog
        │      ├─tree
        │      └─user
        │          └─UserDialog
        └─static
```

---

### 前端部分

#### 技术栈

| Component | 技术 |
| -------- | -------- |
| 框架    | Vue|
| 构建工具    | npm     |
| UI    | element-ui     |
| 语法    | ES2015+     |
| HTTP客户端    | axios     |

#### 开发环境部署

    ``` bash
    # 克隆项目
    git clone https://github.com/apache/****-admin.git
	
	# 打包OpenATC-Config-web项目（该项目为平台主项目的组件项目，故需要提前打包，不然直接编译会出现找不到edgebase-front依赖包的情况。）
	npm install
	npm run package

    # 安装OpenATC-Admin-web主项目依赖
    npm install

    # 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
    npm install --registry=https://registry.npm.taobao.org

    # 启动服务
    npm run dev
    
    # 访问
    访问地址为：http://localhost:9528

    # 构建测试环境
    npm run build:stage

    # 构建生成环境
    npm run build:prod
    
    # 预览发布环境效果 + 静态资源分析
    npm run preview  --report
    ```

#### 第三方通过url跳转配置界面说明
    ``` bash
    # 跳转路径
    https://dolphin-dev.kedacom.com/openatc/#/overviewNew/index?agentid=${设备id}&isfromatc=true


---

### 后端部分

#### 技术栈

| Component | 技术 |
| -------- | -------- |
| 框架    | SpringBoot (Java)|
| 安全    | Shiro + JWT|
| 数据库    | Sqlite     |
| 持久化    | JPA     |
| 构建工具    | Maven (Java)     |

#### 配置说明

- [application.properties](http://192.168.13.118/OpenATC/OpenATC-Admin/wiki/application.properties) 

#### 开发环境部署

    ``` bash
    # Clone project
    git clone https://github.com/apache/****-admin.git

    # Maven管理KDagent
    使用IDEA打开第一步下载的项目文件，右键 OpenATC-Admin/OpenATC-Admin-server/KDagent/pom.xml文件，
    点击Add as Maven Project，将KDagent交给Maven管理

    # Maven管理kdalgorithm、KDCore、KDComm
    使用第二步的方法，依次右键....../**algorithm**/pom.xml、....../**KDCore**/pom.xml、....../**KDComm**/pom.xml，
    点击Add as Maven Project，将kdalgorithm、KDCore、KDComm三个模块交给Maven管理

    # 加载本地jar包
    点击右侧 maven -> kdcomm -> Lifecycle -> install，将kocomm模块的jar包加载的本地仓库；
    然后，同样地继续将kdalgorithm、kdcore的jar包也加载到本地仓库
    
    # 更改数据库连接配置
    配置好 OpenATC-admin-server/KDagent/src/main/resources/application.properties 中连接数据库的用户名、密码以及数据库连接地址

    # 构建
    mvn clean package

    # 启动
    mvn --projects OpenATC-admin-server spring-boot:run
    or
    cd OpenATC-admin-server/target; java -jar kdagent-1.2-SNAPSHOT.jar
    
    # 访问
    访问地址为 `http://localhost:10003` 或者 `https://localhost:10002`
    ```    
---
    
### 注意
- 为了方便开发，我们提供了这种前后端分离的部署模式，主要的好处是支持前端热部署，在这种模式下，前端会通过9528端口访问后端的restful api接口，获取数据, 这将导致跨域访问的问题。因此我们在`OpenATC-admin-ui/config/index.js`添加了支持跨域访问的配置,当前端通过`npm run dev`单独启动时，这些配置将被激活，允许跨域访问

---

### License

- Mulan PSL v2

---

### 联系我们

- 在Gitee Issues中提问
- 电子邮件：openatc@kedacom.com
