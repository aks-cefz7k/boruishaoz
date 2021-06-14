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
/* eslint-disable no-tabs */
import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login', '/greenWaveOptimizeNew', '/deviceNew', '/operaterecordNew'] // 不重定向白名单
router.beforeEach((to, from, next) => {
  if (from.path === '/overview/index' && from.params.flag) {
    Message.error('您当前正处于手动状态，请先退出手动！')
  } else {
    NProgress.start()
    if (getToken()) {
      if (to.path === '/login') {
        next({ path: '/home' })
        NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
      } else {
        if (whiteList.indexOf(to.path) !== -1 || whiteList.indexOf(store.getters.devicePath) !== -1) {
        // iframe父页面回退至首页和跳转至设备详情时，不作下面逻辑处理
          next()
          return
        }
        if (Object.keys(store.getters.userInfo).length === 0) {
          store.dispatch('GetInfo').then(res => { // 拉取用户信息
            next()
          }).catch((err) => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err || 'Verification failed, please login again')
              next({ path: '/home' })
            })
          })
        } else {
          next()
        }
      }
    } else {
      if (whiteList.indexOf(to.path) !== -1) {
        next()
      } else {
        next('/login')
        NProgress.done()
      }
    }
  }
  // NProgress.start()
  // if (getToken()) {
  //   if (to.path === '/login') {
  //     next({ path: '/home' })
  //     NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
  //   } else {
  //     if (whiteList.indexOf(to.path) !== -1 || whiteList.indexOf(store.getters.devicePath) !== -1) {
  //       // iframe父页面回退至首页和跳转至设备详情时，不作下面逻辑处理
  //       next()
  //       return
  //     }
  //     if (Object.keys(store.getters.userInfo).length === 0) {
  //       store.dispatch('GetInfo').then(res => { // 拉取用户信息
  //         next()
  //       }).catch((err) => {
  //         store.dispatch('FedLogOut').then(() => {
  //           Message.error(err || 'Verification failed, please login again')
  //           next({ path: '/home' })
  //         })
  //       })
  //     } else {
  //       next()
  //     }
  //   }
  // } else {
  //   if (whiteList.indexOf(to.path) !== -1) {
  //     next()
  //   } else {
  //     next('/login')
  //     NProgress.done()
  //   }
  // }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
