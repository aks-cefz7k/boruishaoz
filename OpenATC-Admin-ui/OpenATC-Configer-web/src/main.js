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
import Vue from 'vue'
import axios from 'axios'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import App from './App'
import router from './router'
import store from './store'
import Echarts from 'echarts'

import '@/icons' // icon
import '@/permission' // permission control
import './assets/icon/iconfont.css'
// import '@/styles/common.scss'
import './styles/index.scss'
import animate from 'animate.css'

import i18n from './i18n'

window.dev = process.env.NODE_ENV

Vue.use(ElementUI, {
  locale
})

/* 使用动画库 */
Vue.use(animate)

Vue.config.productionTip = false
Vue.prototype.$echarts = Echarts
Vue.use(Echarts)

axios.get('./LRRoadConfig.json').then(val => {
  // 只有配置工具项目单独项目会加载main.js，当作为包被加载时，不会走到这里
  // 读取左行 右行配置文件
  let roadDir = val.data.roadDirection
  if (val.status === 200 && roadDir !== undefined) {
    store.dispatch('SetRoadDirection', roadDir)
  } else {
    store.dispatch('SetRoadDirection', 'right')
  }
})

// mian test hg
/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  store,
  template: '<App/>',
  components: {
    App
  }
})
