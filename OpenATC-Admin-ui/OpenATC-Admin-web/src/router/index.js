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
/* eslint-disable import/first */
import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

import compose from '../views/compose/compose'
import composeNew from '../views/compose/composeNew'
import LoginFunc from '../views/login/loginInterface'
import Edgebase from 'edgebase-front'

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
// const edgeRouter = Edgebase.subrouter.filter((item, index) => index !== 0)
// console.log(edgeRouter)
export const constantRouterMapOfLogin = [{
  path: '/login',
  component: () => import('@/views/login/index'),
  hidden: true,
  props: {
    loginInterface: LoginFunc
  }
},
{
  path: '/',
  component: compose,
  redirect: '/home',
  hidden: true
},
{
  path: '/',
  component: compose,
  name: 'Home',
  hidden: true,
  children: [{
    path: '/home',
    component: () => import('@/views/Home/home')
  }]
},
{
  path: '/',
  component: compose,
  name: 'Gis',
  hidden: true,
  children: [{
    path: '/gis',
    component: () => import('@/views/GisManager/index')
  }]
},
{
  path: '/',
  component: compose,
  name: 'Device',
  hidden: true,
  children: [{
    path: '/device',
    component: () => import('@/views/DeviceManage/index')
  }]
},
{
  path: '/',
  component: compose,
  name: 'Detail',
  children: [{
    path: 'detail',
    component: () => import('@/views/DeviceManage/deviceDetail'),
    children: Edgebase.subrouter.filter(subr => subr.path !== '/overviewNew')
  }]
},
{
  path: '/',
  component: composeNew,
  name: 'OverviewNew',
  hidden: true,
  children: Edgebase.subrouter.filter(subr => subr.path === '/overviewNew')
},
{
  path: '/',
  component: compose,
  name: 'GreenWaveOptimize',
  hidden: true,
  children: [{
    path: '/greenWaveOptimize',
    component: () => import('@/views/GreenWaveOptimize/index')
  }]
},
{
  path: '/user',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/UserManage/user')
  }]
},
{
  path: '/operaterecord',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/OperationRecord/record')
  }]
},
{
  path: '/organization',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/Organization/organization')
  }]
},
{
  path: '/',
  component: composeNew,
  name: 'GreenWaveOptimizeNew',
  hidden: true,
  children: [{
    path: '/greenWaveOptimizeNew',
    component: () => import('@/views/GreenWaveOptimize/index')
  }]
},
{
  path: '/',
  component: composeNew,
  name: 'DeviceNew',
  hidden: true,
  children: [{
    path: '/deviceNew',
    component: () => import('@/views/DeviceManage/index')
  }]
},
{
  path: '/operaterecordNew',
  component: composeNew,
  children: [{
    path: '',
    component: () => import('@/views/OperationRecord/record')
  }]
},
{
  path: '/dutyroute',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/Service/service')
  }]
},
{
  path: '/jupyter',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/Jupyter/index')
  }]
},
{
  path: '/bottleneckControl',
  component: compose,
  children: [{
    path: '',
    component: () => import('@/views/bottleneckControl/index')
  }]
},
{
  path: '/404',
  component: () => import('@/views/404'),
  hidden: true
},
{
  path: '*',
  redirect: '/404',
  hidden: true
}
]

// export const constantRouterMap = constantRouterMapOfLogin.concat(Edgebase.subrouter)
// console.log(constantRouterMapOfLogin)

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRouterMapOfLogin
})
