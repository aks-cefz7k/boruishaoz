import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: (resolve) => {
        require(['@/views/home'], resolve)
      }
    },
    {
      path: '/cross',
      name: 'cross',
      component: (resolve) => {
        require(['@/views/intersection'], resolve)
      }
    },
    {
      path: '/config',
      name: 'config',
      component: (resolve) => {
        require(['@/views/schemeconfig'], resolve)
      }
    }
  ]
})
