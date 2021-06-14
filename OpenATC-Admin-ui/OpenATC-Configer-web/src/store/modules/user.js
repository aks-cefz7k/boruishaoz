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
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken, getHost, setHost } from '@/utils/auth'
// import axios from 'axios'

const urlMgr = require('../../lib/publicjs/HttpurlMgr')
let data = require('../../../static/apiconfig.json')
urlMgr.HttpUrlMgr.init(data)
if (getHost() === null || getHost() === undefined || getHost() === '') {
  let serv = require('../../../static/servConfig.json')
  setHost(serv.servIp)
  // axios.get('./servConfig.json').then(val => {
  //   console.log(val.data.servIp)
  //   let str = val.data.servIp
  //   if (str === undefined || str === null) {
  //     setHost('')
  //   }
  //   setHost(str)
  // })
}

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    userInfo: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_USER: (state, userInfo) => {
      state.userInfo = userInfo
      // state.menus = userInfo.menuList;
      // state.permissions = userInfo.permissionList;
    },
    RESET_USER: (state) => {
      state.userInfo = {}
      // state.menus = []
      // state.permissions = []
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      // const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(userInfo.user_name, userInfo.password, userInfo.vcodeKey, userInfo.vcode).then(response => {
          const data = response.data
          setToken(data.token)
          commit('SET_TOKEN', data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetInfo ({commit, state}) {
      return new Promise((resolve, reject) => {
        getInfo().then(data => {
          if (!data.data.success) {
            reject(data.data.message)
            return
          }
          if (data.data.data === null || data.data.data === undefined) {
            resolve(data.data.data)
            return
          }
          commit('SET_USER', data.data.data)
          commit('SET_ROLES', data.data.data.roleNames)
          resolve(data.data.data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut ({
      commit
    }) {
      return new Promise((resolve, reject) => {
        logout().then(data => {
          if (!data.data.success) {
            reject(data.data.message)
          }
          commit('RESET_USER')
          removeToken()
          resolve(data.data)
        }).catch(() => {
          commit('RESET_USER')
          removeToken()
        })
      })
    },

    // 前端 登出
    FedLogOut ({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
