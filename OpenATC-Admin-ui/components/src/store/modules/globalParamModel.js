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
const GlobalParamModel = {
  state: {
    GlobalParamModeObject: Object,
    devParams: Object
  },
  mutations: {
    SET_GLOBAL_PARAM_MODEL: (state, obj) => {
      state.GlobalParamModeObject = obj
    },
    SAVE_DEVPARAMS: (state, info) => {
      state.devParams = info
    }
  },
  actions: {
    InitGlobalParamModel ({ commit }, obj) {
      commit('SET_GLOBAL_PARAM_MODEL', obj)
    },
    SaveDevParams ({ commit }, info) {
      commit('SAVE_DEVPARAMS', info)
    }
  }
}
export default GlobalParamModel
