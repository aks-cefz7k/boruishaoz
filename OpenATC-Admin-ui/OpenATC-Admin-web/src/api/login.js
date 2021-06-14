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
import {
  GetTimestampEncode,
  GetPassswordEncode
} from './passwdAssest'
import Authapi from './authapi'

export const login = (user, pass, vcodeKey, vcode) => {
  let api = new Authapi('login')
  let obj = GetTimestampEncode(GetPassswordEncode(user, pass))
  let param = {
    'user_name': user,
    'password': obj.data,
    'timestamp': obj.timestamp,
    'vcodeKey': vcodeKey,
    'vcode': vcode
  }
  return api.Send({}, param, [], false)
}

export const getInfo = () => {
  let api = new Authapi('getCurInfo')

  return api.Send({}, {})
}

export const logout = () => {
  let api = new Authapi('logout')

  return api.Send({}, {})
}

export const GetCaptcha = () => {
  let api = new Authapi('getCaptcha')
  return api.Send({}, {})
}
