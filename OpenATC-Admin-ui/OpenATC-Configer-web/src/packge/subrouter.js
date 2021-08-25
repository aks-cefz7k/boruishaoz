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
// const _import = require('../router/_import_' + process.env.NODE_ENV)
import Layout from '../views/layout/Layout'
import LoginFunc from '../views/login/loginInterface'

const subRouter = [{
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
  redirect: '/overview/index',
  hidden: true,
  children: [{
    name: 'Overview',
    path: 'overview',
    component: () => import('@/views/overView/index')
  }]
},
{
  path: '/dashboard',
  component: Layout,
  name: 'Dashboard',
  hidden: true,
  children: [{
    path: 'dashboard',
    component: () => import('@/views/dashboard/index')
  }]
},
{
  path: '/edge',
  component: Layout,
  name: 'Edge',
  hidden: true,
  children: [{
    path: 'dashboard',
    component: () => import('@/views/dashboard/index')
  }]
},
{
  path: '/overview',
  component: Layout,
  children: [{
    path: 'index',
    component: () => import('@/views/overView/index'),
    meta: {
      title: 'edge.route.overview',
      icon: 'overview'
    }
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
  children: [{
    path: 'phase',
    name: 'Phase',
    component: () => import('@/views/phase/index'),
    meta: {
      title: 'edge.route.phase',
      icon: 'phase'
    }
  },
  {
    path: 'overlap',
    name: 'Overlap',
    component: () => import('@/views/overlap/index'),
    meta: {
      title: 'edge.route.overlap',
      icon: 'overlap'
    }
  },
  {
    path: 'pattern',
    name: 'Pattern',
    component: () => import('@/views/pattern/index'),
    meta: {
      title: 'edge.route.pattern',
      icon: 'pattern'
    }
  },
  {
    path: 'plan',
    name: 'Plan',
    component: () => import('@/views/plan/index'),
    meta: {
      title: 'edge.route.plan',
      icon: 'plan'
    }
  },
  {
    path: 'date',
    name: 'Date',
    component: () => import('@/views/date/index'),
    meta: {
      title: 'edge.route.date',
      icon: 'date'
    }
  },
  {
    path: 'channel',
    name: 'Channel',
    component: () => import('@/views/channel/index'),
    meta: {
      title: 'edge.route.channel',
      icon: 'channel'
    }
  },
  {
    path: 'channelLock',
    name: 'ChannelLock',
    component: () => import('@/views/channelLock/index'),
    meta: {
      title: 'edge.route.channelLock',
      icon: 'channellock'
    }
  },
  {
    path: 'detector',
    name: 'Detector',
    component: () => import('@/views/detector/index'),
    meta: {
      title: 'edge.route.detector',
      icon: 'detector'
    }
  },
  {
    path: 'pedestrianDetector',
    name: 'pedestrianDetector',
    component: () => import('@/views/pedestrianDetector/index'),
    meta: {
      title: 'edge.route.pedestrianDetector',
      icon: 'peddetector'
    }
  },
  {
    path: 'deviceInfo',
    name: 'deviceInfo',
    component: () => import('@/views/deviceInfo/index'),
    meta: {
      title: 'edge.route.deviceInfo',
      icon: 'deviceinfo'
    }
  },
  {
    path: 'controlPanel',
    name: 'controlPanel',
    component: () => import('@/views/controlPanel/index'),
    meta: {
      title: 'edge.route.controlPanel',
      icon: 'controlpanel'
    }
  }
  ]
},
{
  path: '/system',
  component: Layout,
  children: [{
    path: 'index',
    name: 'System',
    component: () => import('@/views/system/index'),
    meta: {
      title: 'edge.route.system',
      icon: 'system'
    }
  }]
},
{
  path: '/optimize',
  component: Layout,
  children: [{
    path: 'index',
    name: 'Optimize',
    component: () => import('@/views/optimize/index'),
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
  path: '/userInfo',
  component: Layout,
  children: [{
    path: 'index',
    name: 'User',
    component: () => import('@/views/user/index'),
    meta: {
      title: 'edge.route.user',
      icon: 'usermanager'
    }
  }]
},
{
  path: '/dailyRecord',
  component: Layout,
  children: [{
    path: 'index',
    name: 'DailyRecord',
    component: () => import('@/views/dailyRecord/index'),
    meta: {
      title: 'edge.route.dailyRecord',
      icon: 'dailyrecord'
    }
  }]
},
{
  path: '/statistics',
  component: Layout,
  children: [{
    path: 'index',
    name: 'Statistics',
    component: () => import('@/views/statistics/index'),
    meta: {
      title: 'edge.route.statistics',
      icon: 'statistics'
    }
  }]
}
]

export default subRouter
