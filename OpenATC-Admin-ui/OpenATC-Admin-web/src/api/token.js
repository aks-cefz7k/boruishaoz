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
import Authapi from './authapi'

export const GetTokenList = () => {
  let api = new Authapi('getTokenList')
  return api.Send({}, [])
}

export const UpdateToken = (data) => {
  let api = new Authapi('updateToken')
  return api.Send({}, data, [])
}

export const DeleteToken = (id) => {
  let api = new Authapi('deleteToken')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export default {
  GetTokenList,
  UpdateToken,
  DeleteToken
}
