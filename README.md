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
| shrink    | 否 | number | 首页主内容区域缩放比例（取值为0~1之间） | 0.8 |
|  graphicMode  | 否 | boolean (default: false) | 控制首页只显示路口图 | true |


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


#### 系统配置参数

##### 可用参数说明

| 模块 | 名称 | 说明 | 可选值 |
| -------- | -------- | -------- | -------- |
| theme | theme | 系统默认加载的主题，dark代表加载深色系主题，不填或light加载浅色主题 | dark/light |
| language  | language | 默认加载的语言：zh代表中文，en代表英文 | zh/en |
| media  | mediaWebsocketUrl | 视频ws流媒体地址 | wss://192.168.7.183/ |
| jupyter  | url | jupyter跳转地址 | http://192.168.14.168:8888/tree |
| gis  | zoom | 地图默认缩放等级，推荐12 | 0～18 |
| gis  | maxZoom | 地图最大缩放等级，推荐18 | 0～18 |
| gis  | minZoom | 地图最小缩放等级，推荐12  | 0～18|
| gis  | center | 地图默认中心点，可填：[31.22784056, 121.68148040] | [Lat, Lng] |
| gis  | gisNormal | 2D地图本地服务资源地址 | http://192.168.14.168:7080/PBS/rest/services/MyPBSService1/MapServer/tile/{z}/{y}/{x} |
| gis  | gisStatellite | 卫星图本地服务资源地址 | http://192.168.14.168:7081/PBS/rest/services/MyPBSService2/MapServer/tile/{z}/{y}/{x} |
| gis  | gisBoundLeftTop | 地图边界的左上角纬度经度，可填：[31.36360615, 121.30622863] | [Lat, Lng] |
| gis  | gisBoundRightBottom | 地图边界的右下角纬度经度，可填：[31.11040156, 121.95270538] | [Lat, Lng] |
| gis  | isShowVideo | gis勤务路线是否显示视频：true代表显示视频（前提是有视频资源），不填或false代表不显示视频 | true/false |
| gis  | isShowGisMenu | 菜单栏是否显示地图菜单：true代表显示地图菜单，不填或false代表不显示地图菜单 | true/false |


#### 界面展示

![Image text](https://gitee.com/openatc/openatc-doc/raw/master/OpenATCAdminManual_cn/docs/imgs/ch01-01.png)




![Image text](https://gitee.com/openatc/openatc-doc/raw/user/OpenATCconfigerManual_cn/docs/imgs/ch01-01.png)




![Image text](https://gitee.com/openatc/openatc-doc/raw/user/OpenATCAdminManual_cn/docs/imgs/ch03-14.png)
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
    访问地址为 `http://localhost:10003`
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
