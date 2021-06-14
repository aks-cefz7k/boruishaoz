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
import Router from 'vue-router'

/* Layout */
import Layout from '../views/layout/Layout'
import LoginFunc from '../views/login/loginInterface'
import subRouter from '../packge/subrouter'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [{
  path: '/login',
  component: () => import('@/views/login/index'),
  hidden: true,
  props: {
    loginInterface: LoginFunc
  }
},
{
  path: '/404',
  component: () => import('@/views/404'),
  hidden: true
},

{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  name: 'Dashboard',
  hidden: true,
  children: [{
    path: 'dashboard',
    component: () => import('@/views/dashboard/index')
  }]
},
{
  path: '/example',
  component: Layout,
  redirect: '/example/phase',
  name: 'TSC',
  meta: {
    title: 'edge.route.edge',
    icon: 'example'
  },
  children: subRouter
},
{
  path: '/system',
  component: Layout,
  children: [{
    path: 'index',
    name: 'System',
    component: () => import('@/views/system/index'),
    meta: {
      title: 'edge.route.optimize',
      icon: 'optimize'
    }
  }]
},
{
  path: '/history',
  component: Layout,
  children: [{
    path: 'index',
    name: 'History',
    component: () => import('@/views/history/index'),
    meta: {
      title: 'edge.route.history',
      icon: 'history'
    }
  }]
},
{
  path: '/control',
  component: Layout,
  children: [{
    path: 'index',
    name: 'Control',
    component: () => import('@/views/control/index'),
    meta: {
      title: 'edge.route.control',
      icon: 'control'
    }
  }]
},

{
  path: '*',
  redirect: '/404',
  hidden: true
}

// {
//   path: '/example',
//   component: Layout,
//   redirect: '/example/phase',
//   name: 'TSC',
//   meta: {
//     title: 'edge.route.edge',
//     icon: 'example'
//   },
//   children: [{
//     path: 'phase',
//     name: 'Phase',
//     component: () => import('@/views/phase/index'),
//     meta: {
//       title: 'edge.route.phase',
//       icon: 'phase'
//     }
//   },
//   {
//     path: 'overlap',
//     name: 'Overlap',
//     component: () => import('@/views/overlap/index'),
//     meta: {
//       title: 'edge.route.overlap',
//       icon: 'overlap'
//     }
//   },
//   {
//     path: 'pattern',
//     name: 'Pattern',
//     component: () => import('@/views/pattern/index'),
//     meta: {
//       title: 'edge.route.pattern',
//       icon: 'pattern'
//     }
//   },
//   {
//     path: 'plan',
//     name: 'Plan',
//     component: () => import('@/views/plan/index'),
//     meta: {
//       title: 'edge.route.plan',
//       icon: 'plan'
//     }
//   },
//   {
//     path: 'date',
//     name: 'Date',
//     component: () => import('@/views/date/index'),
//     meta: {
//       title: 'edge.route.date',
//       icon: 'date'
//     }
//   },
//   {
//     path: 'channel',
//     name: 'Channel',
//     component: () => import('@/views/channel/index'),
//     meta: {
//       title: 'edge.route.channel',
//       icon: 'channel'
//     }
//   },
//   {
//     path: 'detector',
//     name: 'Detector',
//     component: () => import('@/views/detector/index'),
//     meta: {
//       title: 'edge.route.detector',
//       icon: 'detector'
//     }
//   }
//     // {
//     //   path: 'tree',
//     //   name: 'Tree',
//     //   component: () => import('@/views/tree/index'),
//     //   meta: { title: 'Tree', icon: 'tree' }
//     // }
//   ]
// },

// {
//   path: '/system',
//   component: Layout,
//   children: [{
//     path: 'index',
//     name: 'System',
//     component: () => import('@/views/system/index'),
//     meta: {
//       title: 'edge.route.optimize',
//       icon: 'optimize'
//     }
//   }]
// },
// {
//   path: '/history',
//   component: Layout,
//   children: [{
//     path: 'index',
//     name: 'History',
//     component: () => import('@/views/history/index'),
//     meta: {
//       title: 'edge.route.history',
//       icon: 'history'
//     }
//   }]
// },
// {
//   path: '/control',
//   component: Layout,
//   children: [{
//     path: 'index',
//     name: 'Control',
//     component: () => import('@/views/control/index'),
//     meta: {
//       title: 'edge.route.control',
//       icon: 'control'
//     }
//   }]
// },

// {
//   path: '*',
//   redirect: '/404',
//   hidden: true
// }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRouterMap
})
