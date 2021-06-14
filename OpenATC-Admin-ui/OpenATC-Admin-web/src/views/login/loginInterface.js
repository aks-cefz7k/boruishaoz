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

export function OnLoginSucess (token) {
  if (token !== undefined) {
    setToken(token)
    router.push({
      path: '/home'
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

export default {
  loginSucess: OnLoginSucess,
  loginFailed: OnLoginFailed
}
