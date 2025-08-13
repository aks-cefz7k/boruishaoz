// 实时数据管理模块

import GlobalData from './global/globalData'

export default class RelTimeData {
  constructor () {
    this.dataObjs = new Map()
  }

  Init () {
    /// @brief 添加管理的数据类
    this.dataObjs.set('global', new GlobalData())
  }

  GetDataObj (key) {
    return this.dataObjs.get(key)
  }
}
