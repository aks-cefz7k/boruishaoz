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
import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import globalVariable from './modules/globalVariable'
import KissModel from './modules/KissModel'
import WebsocketModel from './modules/WesocketModel'
import getters from './getters'
import Edgebase from '@openatc/edgebase-front'

Vue.use(Vuex)
const agent = Edgebase.Store.agent
const globalParam = Edgebase.Store.globalParam
const globalParamModel = Edgebase.Store.globalParamModel
const permissionRoutes = Edgebase.Store.permissionRoutes
const store = new Vuex.Store({
  modules: {
    app,
    user,
    agent,
    globalVariable,
    globalParam,
    globalParamModel,
    permissionRoutes,
    KissModel,
    WebsocketModel
  },
  getters
})

export default store
