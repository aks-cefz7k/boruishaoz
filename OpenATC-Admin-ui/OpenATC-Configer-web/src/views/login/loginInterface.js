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
/*
@brief 登录操作接口
@date 2018-10-18
@author caijiadong
*/
import router from '@/router'
import {
  setToken
} from '@/utils/auth'
import {
  setStorage,
  getStorage
} from '../../lib/publicjs/localStorage.js'

export function OnLoginSucess (token) {
  if (token !== undefined) {
    setToken(token)
    console.log(router)
    router.push({
      path: '/overview/index'
    })
    // console.log(this.$router)
    // this.$router.push({ path: '/' })
  } else {
    throw new Error('Username or Password Error!')
  }
}

export function OnLoginFailed (error) {
  if (error) {
    console.log(error)
  }
}

// 存储登陆信息
export const setLoginInfo = (info) => {
  let logininfo = {
    user_name: info.user_name,
    ip: info.ip,
    port: info.port,
    protocol: info.protocol
  }
  return setStorage('logininfo', JSON.stringify(logininfo))
}
// 从缓存中获取登陆信息
export const getLoginInfo = () => {
  let info = getStorage('logininfo')
  if (info === '' || info === null || info === undefined || info === 'undefined') {
    return undefined
  }
  return info
}

export default {
  loginSucess: OnLoginSucess,
  loginFailed: OnLoginFailed,
  setLoginInfo,
  getLoginInfo
}
