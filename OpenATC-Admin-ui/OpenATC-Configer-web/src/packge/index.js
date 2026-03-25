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
import edgeLogin from '../views/login/index'
import svgIcon from '../components/SvgIcon/index'
// import store from '../store/index'
import app from '../store/modules/app'
import user from '../store/modules/user'
import globalParam from '../store/modules/globalParam'
import globalParamModel from '../store/modules/globalParamModel'
import getters from '../store/getters'
import agent from '../store/modules/saveAgent'
import permissionRoutes from '../store/modules/permissionRoutes'
// import api from '../api/index'
import phase from '../views/phase/index'
import overlap from '../views/overlap/index'
import pattern from '../views/pattern/index'
import plan from '../views/plan/index'
import date from '../views/date/index'
import channel from '../views/channel/index'
import channelLock from '../views/channelLock/index'
import detector from '../views/detector/index'
import pedDetector from '../views/pedestrianDetector/index'
// import deviceInfo from '../views/deviceInfo/index'
import adaptiveParameter from '../views/adaptiveParameter/index'
import controlPanel from '../views/controlPanel/index'
import en from '../i18n/language/en'
import zh from '../i18n/language/zh'
import Layout from '../views/layout/Layout'
// import LoginFunc from '../views/login/loginInterface'
import optimize from '../views/optimize/index'
import control from '../views/control/index'
import system from '../views/system/index'
import overview from '../views/overView/index'
import history from '../views/history/index'
import usermgr from '../views/user/index'
import dailyRecord from '../views/dailyRecord/index'
import statistics from '../views/statistics/index'
import dashboard from '../views/dashboard/index'
// import '@/styles/common.scss'
// import '@/styles/index.scss'
import '@/assets/icon/iconfont.css'

const components = {
  edgeLogin,
  svgIcon,
  phase,
  overlap,
  pattern,
  plan,
  date,
  channel,
  detector,
  channelLock,
  pedDetector,
  // deviceInfo,
  adaptiveParameter,
  controlPanel,
  Layout,
  // LoginFunc,
  optimize,
  control,
  system,
  history,
  dashboard,
  overview,
  dailyRecord,
  usermgr,
  statistics
}

const language = {
  en,
  zh
}
const Store = {
  app,
  user,
  getters,
  agent,
  globalParam,
  globalParamModel,
  permissionRoutes
}
const subrouter = [
//   {
//   path: '/login',
//   component: () => import('@/views/login/index'),
//   hidden: true,
//   props: {
//     loginInterface: LoginFunc
//   }
// },
// {
//   path: '/404',
//   component: () => import('@/views/404'),
//   hidden: true
// },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: dashboard
    }]
  },
  {
    path: '/dashboard',
    component: Layout,
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: dashboard
    }]
  },
  {
    path: 'edge',
    component: Layout,
    hidden: true,
    children: [{
      path: 'dashboard',
      component: dashboard
    }]
  },
  {
    path: '/overview',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Overview',
      component: overview,
      meta: {
        title: 'edge.route.overview',
        icon: 'overview'
      }
    }]
  },
  {
    path: '/overviewNew',
    component: Layout,
    children: [{
      path: 'index',
      name: 'OverviewNew',
      component: overview,
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
      component: phase,
      meta: {
        title: 'edge.route.phase',
        icon: 'phase'
      }
    },
    {
      path: 'overlap',
      name: 'Overlap',
      component: overlap,
      meta: {
        title: 'edge.route.overlap',
        icon: 'overlap'
      }
    },
    {
      path: 'pattern',
      name: 'Pattern',
      component: pattern,
      meta: {
        title: 'edge.route.pattern',
        icon: 'pattern'
      }
    },
    {
      path: 'plan',
      name: 'Plan',
      component: plan,
      meta: {
        title: 'edge.route.plan',
        icon: 'plan'
      }
    },
    {
      path: 'date',
      name: 'Date',
      component: date,
      meta: {
        title: 'edge.route.date',
        icon: 'date'
      }
    },
    {
      path: 'channel',
      name: 'Channel',
      component: channel,
      meta: {
        title: 'edge.route.channel',
        icon: 'channel'
      }
    },
    {
      path: 'detector',
      name: 'Detector',
      component: detector,
      meta: {
        title: 'edge.route.detector',
        icon: 'detector'
      }
    },
    {
      path: 'pedestrianDetector',
      name: 'pedestrianDetector',
      component: pedDetector,
      meta: {
        title: 'edge.route.pedestrianDetector',
        icon: 'peddetector'
      }
    },
    // {
    //   path: 'deviceInfo',
    //   name: 'deviceInfo',
    //   component: deviceInfo,
    //   meta: {
    //     title: 'edge.route.deviceInfo',
    //     icon: 'deviceinfo'
    //   }
    // },
    {
      path: 'adaptiveParameter',
      name: 'adaptiveParameter',
      component: adaptiveParameter,
      meta: {
        title: 'edge.route.adaptiveParameter',
        icon: 'adaptiveParameter'
      }
    },
    {
      path: 'channelLock',
      name: 'ChannelLock',
      component: channelLock,
      meta: {
        title: 'edge.route.channelLock',
        icon: 'channellock'
      }
    },
    {
      path: 'controlPanel',
      name: 'controlPanel',
      component: controlPanel,
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
      component: system,
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
      component: optimize,
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
      component: history,
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
      component: usermgr,
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
      component: dailyRecord,
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
      component: statistics,
      meta: {
        title: 'edge.route.statistics',
        icon: 'statistics'
      }
    }]
  }
]

const requireAll = requireContext => requireContext.keys().map(requireContext)
const install = function (Vue) {
  if (install.installed) return
  // components.map(component => Vue.component(component.name, component))
  Object.keys(components).forEach(key => {
    Vue.component(components[key].name, components[key])
  })

  const req = require.context('../icons/svg', false, /\.svg$/)
  requireAll(req)

  const urlMgr = require('../lib/publicjs/HttpurlMgr')
  if (urlMgr.HttpUrlMgr.urlMap === undefined) {
    let data = require('../../static/apiconfig.json')
    urlMgr.HttpUrlMgr.init(data)
  }
}

if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue)
}

const API = {
  install,
  ...components,
  Store,
  // api,
  subrouter,
  language
}

export default API
