## 简介

[OpenATC-Admin](http://) 是一个开源信号控制管理平台综合解决方案，它前端基于 vue 和 element-ui实现，后端基于spring-boot实现。

## 前序准备

你需要在本地安装 [node](http://nodejs.org/) 和 [git](https://git-scm.com/)。本项目技术栈基于 [ES2015+](http://es6.ruanyifeng.com/)、[vue](https://cn.vuejs.org/index.html)、[vuex](https://vuex.vuejs.org/zh-cn/)、[vue-router](https://router.vuejs.org/zh-cn/) 、[vue-cli](https://github.com/vuejs/vue-cli) 、[axios](https://github.com/axios/axios) 和 [element-ui](https://github.com/ElemeFE/element)


## 功能

```
- 登录 / 注销

- 主页 

- 用户管理
  - 新建
  - 编辑
  - 权限配置
  - 查询
  - 删除

- 设备配置（组件）
  - 设备管理
  - 特征参数配置
  - 设备状态查看和控制
  - 设备历史数据

- 操作历史记录

```

## 开发

```bash
# 克隆项目
git clone https://***********************

# 进入项目目录
cd ***********

# 安装依赖
npm install

# 启动服务
npm run dev
```

浏览器访问 http://localhost:9080

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

## 其它

```bash
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```


## Changelog

Detailed changes for each release are documented in the [release notes](https://**********/releases).

## Online Demo

[在线 Demo](https://***********************)


## Browsers support

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Safari |
| --------- | --------- | --------- | --------- |
| IE10, IE11, Edge| last 2 versions| last 2 versions| last 2 versions

## License

[MIT](https://**********************LICENSE)

## CodeStyle

[前端代码评审 Checklist 清单](https://github.com/fengshi123/blog/issues/1)
