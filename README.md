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
│  ├─agent
│  │  │  pom.xml
│  │  ├─src
│  │  │  └─main
│  │  │      ├─java
│  │  │      │  └─com
│  │  │      │      └─openatc
│  │  │      │             └─agent
│  │  │      │                  │  agentApplication.java         //agent服务启动入口
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

##### 跳转url

    `https://dolphin-dev.kedacom.com/openatc/#/overviewNew/index`


##### url传参说明

| 参数名 | 必选 | 类型 | 说明 | 可选项 |
| -------- | -------- | -------- | -------- | -------- |
| agentid    | 是 | number | 设备id | 10001 |
| isfromatc    | 否 | boolean (default: false) | 控制国际化切换、登出、修改密码功能是否可见,传true则不可见 | true |
| hideMenu    | 否 | boolean (default: false) | 控制左侧菜单栏以及切换到文字界面的图标是否可见，传true则不可见 | true |
| shrink    | 否 | number | 首页主内容区域缩放比例（取值为0~1之间） |0.8 |


##### 举例

    `https://dolphin-dev.kedacom.com/openatc/#/overviewNew/index?agentid=10001&isfromatc=true&hideMenu=true`


#### chrome浏览器下，脚本页面无法正常登陆解决方法

##### 原因
    Chrome浏览器默认限制了第三方Cookie，这将影响正常登录，需要更改浏览器的隐私设置，解除限制后重试

##### 解决方法
    chrome://flags/   把这句复制到浏览器，回车
    SameSite by default cookies
    Cookies without SameSite must be secure
    找到上面这两两项设置成 Disable即可

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

```
	# 项目名称
	spring.application.name=agent  

	# https的端口号
	server.port = 10004
	# http的端口号
	server.http.port = 10003

	# 监听服务发送的端口
	agent.send.port=0
	# 监听设备主动上报消息的端口
	agent.subscribe.port=21003

	# agent配置工具服务模式，设为true时，为中心模式，需配置数据库；设为false时，需打开spring.autoconfigure.exclude配置
	agent.server.mode.center=true

	# token的密码及过期时间
	jwt.token.secret = kedacom
	jwt.token.expiration = 86400000

	# 重置用户后的密码 123456
	default.user.password=123456

	# Preferred JSON mapper to use for HTTP message conversion.
	spring.http.converters.preferred-json-mapper=gson

	# 试图给一个new的RouteIntersection对象的某一个属性赋一个已经Persistent对象,导致最后save或者mergeRouterIntersection对象报这个错误,因此需要增加这个配置
	spring.jpa.properties.hibernate.event.merge.entity_copy_observer=allow

	# 配置工具版本使用此配置
	#spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

	# 平台版本使用数据库配置
	spring.datasource.url=jdbc:postgresql://192.168.13.112:5432/openatc?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=Asia/Shanghai
	spring.datasource.username=postgres
	spring.datasource.password=password
	spring.datasource.driverClassName=org.postgresql.Driver
	spring.jpa.database-platform = com.example.jpajsonb.support.JsonbPostgresDialect
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
	spring.jpa.properties.hibernate.hbm2ddl.auto=update
	spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false
	spring.flyway.baseline-on-migrate=true

	##  Hikari 连接池配置
	## 最小空闲连接数量
	spring.datasource.hikari.minimum-idle=1
	## 空闲连接存活最大时间，默认600000（10分钟）
	spring.datasource.hikari.idle-timeout=180000
	## 连接池最大连接数，默认是10
	spring.datasource.hikari.maximum-pool-size=5
	## 此属性控制从池返回的连接的默认自动提交行为,默认值：true
	spring.datasource.hikari.auto-commit=true
	## 连接池名称
	spring.datasource.hikari.pool-name=HikariOpenATC
	## 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
	spring.datasource.hikari.max-lifetime=1800000
	## 数据库连接超时时间,默认30秒，即30000
	spring.datasource.hikari.connection-timeout=30000

	## 文件上传配置
	# 开启 multipart 上传功能
	spring.servlet.multipart.enabled=true
	# 文件写入磁盘的阈值
	spring.servlet.multipart.file-size-threshold=2KB
	# 最大文件大小
	spring.servlet.multipart.max-file-size=200MB
	# 最大请求大小
	spring.servlet.multipart.max-request-size=215MB
	# 文件存储目录
	file.upload-dir=./uploads

	# 秘钥证书库文件所在位置
	#server.ssl.key-store = classpath:kd014693.com.jks   #ssl证书
	server.ssl.key-store = classpath:kdstore.p12         #jdk证书
	# 密码
	server.ssl.key-store-password = kedacom123
	# 秘钥证书库类型
	#server.ssl.keyStoreType = JKS   #ssl证书类型
	server.ssl.keyStoreType = PKCS12 #jdk证书类型
	# 别名条目
	server.ssl.keyAlias= kd

```

#### 开发环境部署

    ``` bash
    # Clone project
    git clone https://gitee.com/openatc/open-atc-admin.git

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
