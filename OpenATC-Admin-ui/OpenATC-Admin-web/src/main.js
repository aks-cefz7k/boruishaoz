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
/* eslint-disable no-new */
import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
// import './styles/index.scss'
// import 'element-ui/lib/theme-chalk/index.css'
// import './styles/theme/element-variables.scss'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import Echarts from 'echarts'

import App from './App'
// import './styles/index.scss'
// import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission' // permission control
import './assets/icon/iconfont.css'
import 'leaflet/dist/leaflet.css'

import Edgebase from 'edgebase-front'
// import './styles/index.scss'

import router from './router'
import i18n from './i18n'
import VueKonva from 'vue-konva'
import './mock'
import { getTheme } from '@/utils/auth'

window.dev = process.env.NODE_ENV

Vue.use(ElementUI, {
  locale
})

// Vue.use(kissui)

Vue.use(Edgebase)
Vue.prototype.$echarts = Echarts
Vue.use(Echarts)
Vue.use(VueKonva)
Vue.config.productionTip = false

if (getTheme() === 'dark') {
  // require('./styles/dark/index.scss')
  import('./styles/dark/index.scss')
} else {
  // require('./styles/light/index.scss')
  import('./styles/light/index.scss')
}

// mian test hg
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
