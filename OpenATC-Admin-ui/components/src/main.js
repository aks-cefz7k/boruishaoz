// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import kissui from './kisscomps/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import chosen from 'chosen-js'
Vue.use(kissui)
Vue.use(ElementUI)
// Vue.use(chosen)
// Vue.use($)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
