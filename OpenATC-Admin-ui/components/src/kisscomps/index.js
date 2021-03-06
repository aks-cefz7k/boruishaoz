
import XRDDirSelector from './components/XRDDirSelector/index'
import IntersectionWithInterface from './components/IntersectionWithInterface/index'
import IntersectionMap from './components/IntersectionMap/index'
import SchemeConfig from './components/SchemeConfig/index'
import lockingPhaselControlModal from './components/SchemeConfig/lockingPhaselControlModal/index'
import FaultDetailModal from './components/FaultDetailModal/index'
import CommonKanban from './components/CommonKanban/index'
import patternList from './components/patternList/index'
import PatternStatus from './components/PatternStatus/index'
import BoardCard from './components/BoardCard/index'
import StageStatus from './components/StageStatus/index'
import OverLap from './components/OverLap/index'

import en from '../i18n/language/en'
import zh from '../i18n/language/zh'
// 第三方插件
import VTooltip from 'v-tooltip'

const components = {
  XRDDirSelector,
  IntersectionWithInterface,
  IntersectionMap,
  SchemeConfig,
  lockingPhaselControlModal,
  FaultDetailModal,
  PatternStatus,
  BoardCard,
  OverLap,
  StageStatus,
  patternList,
  CommonKanban
}

const language = {
  en,
  zh
}

const requireAll = requireContext => requireContext.keys().map(requireContext)
const install = function (Vue) {
  if (install.installed) return
  Vue.use(VTooltip)
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
  language
}

export default API
