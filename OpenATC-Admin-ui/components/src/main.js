// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import kissui from './kisscomps/index'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import i18n from './i18n'
import 'element-ui/lib/theme-chalk/index.css'

// 深浅主题按需加载
import('../static/styles/dark/index.scss')
// import('../static/styles/light/index.scss')

Vue.use(kissui)
Vue.use(ElementUI)
// Vue.use(chosen)
// Vue.use($)
Vue.use(ElementUI, {
  locale
})

Vue.config.productionTip = false

const urlMgr = require('./lib/publicjs/HttpurlMgr')
let data = require('../static/apiconfig.json')
urlMgr.HttpUrlMgr.init(data)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  store,
  components: { App },
  template: '<App/>'
})
