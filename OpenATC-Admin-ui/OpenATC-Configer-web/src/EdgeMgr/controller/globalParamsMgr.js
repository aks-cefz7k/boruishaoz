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
// 该类是对全局对象tscParam操作的管理
import EdgeModelBase from '@/EdgeMgr/EdgeModelBase'
import store from '@/store'

export default class GlobalParamsMgr extends EdgeModelBase {
  constructor () {
    super('GlobalParamsMgr')
  }

  Init () {
    store.dispatch('InitGlobalParamModel', this)
  }

  setGlobalParams (globalData) {
    // 设置全局变量，操作store
    store.dispatch('SaveTscParam', globalData)
  }

  setSingleParam (param) {
    store.dispatch('SaveSingleParam', param)
  }

  getGlobalParams () {
    return store.getters.tscParam
  }

  getParamsByType (key) {
    // 获取全局对象的某个字段的数据
    return store.getters.tscParam[key]
  }

  getParamLength (key) {
    // 获取全局对象的某个字段数据的长度
    return store.getters.tscParam[key].length
  }

  addParamsByType (key, payload) {
    store.getters.tscParam[key].push(payload)
  }

  deleteParamsByType (key, index, num) {
    store.getters.tscParam[key].splice(index, num)
  }

  reset () {
    store.dispatch('ResetTscParam')
  }
}
