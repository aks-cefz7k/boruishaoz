
import XRDDirSelector from './components/XRDDirSelector/index'
import IntersectionWithInterface from './components/IntersectionWithInterface/index'
import IntersectionMap from './components/IntersectionMap/index'

import en from '../i18n/language/en'
import zh from '../i18n/language/zh'
// 第三方插件
import VTooltip from 'v-tooltip'

const components = {
  XRDDirSelector,
  IntersectionWithInterface,
  IntersectionMap
}

const language = {
  en,
  zh
}

const install = function (Vue) {
  if (install.installed) return
  Vue.use(VTooltip)
  // components.map(component => Vue.component(component.name, component))
  Object.keys(components).forEach(key => {
    Vue.component(components[key].name, components[key])
  })
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
