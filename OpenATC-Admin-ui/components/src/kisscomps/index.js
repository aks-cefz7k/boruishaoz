import Button from './components/button/index'
import KissMenu from './components/menu/index'
import KissInput from './components/input/index'
import KissSearchInput from './components/KissSearchInput/index'
import KissSelect from './components/select/index'
import KissTips from './components/tip/index'
import KissMutipleTips from './components/mutipletips/index'
import KissDatePicker from './components/timectrl/index'
import KissCompass from './components/compass3D/index'
// import KissCircleMenu from './components/CircleMenu/index'
// import CircleMenu from './components/KissCircleMenu/index'
import KissDragResize from './components/TragResize/index'
import XRDDirSelector from './components/XRDDirSelector/index'
import SatausBar from './components/StatusBar/index'
import DashBoard from './components/DashBoard/index'
import KissPanel from './components/panel/index'
import KissTableButton from './components/tablebutton/index'
import KissHead from './components/header/index'
import KissHorizontal from './components/Horizontal/index'
import KissHorizontalChildren from './components/HorizontalChildren/index'
import KissHorizontalMenu from './components/horizontalMenu/index'
import KissSimulationProgress from './components/KissSimulationProgress/index'
import KissMessageBox from './components/KissMessageBox/index'
import en from '../i18n/language/en'
import zh from '../i18n/language/zh'
// 第三方插件
import VTooltip from 'v-tooltip'

const components = {
  Button,
  KissMenu,
  KissInput,
  KissSearchInput,
  KissSelect,
  KissTips,
  KissMutipleTips,
  KissDatePicker,
  KissCompass,
  // KissCircleMenu,
  // CircleMenu,
  KissDragResize,
  XRDDirSelector,
  SatausBar,
  DashBoard,
  KissPanel,
  KissTableButton,
  KissHead,
  KissHorizontal,
  KissHorizontalChildren,
  KissHorizontalMenu,
  KissSimulationProgress,
  KissMessageBox
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
