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
import {
  GetPassswordEncode
} from './passwdAssest'

export const GetUsrInfoList = () => {
  let api = new Authapi('getUsr')

  // let param = {
  //   'pageNum': pageNum,
  //   'pageRow': pageSize
  // }

  return api.Send({}, {})
}

export const AddUsr = (usrName, nickName, email, phone, passwd, roles, organization) => {
  let api = new Authapi('addUsrs')

  let param = {
    'user_name': usrName,
    'nick_name': nickName,
    'email': email,
    'mobile_phone': phone,
    'organization': organization,
    'status': 1,
    'ext_infos': '',
    'roleNames': roles,
    'password': GetPassswordEncode(usrName, passwd)
  }

  return api.Send({}, param)
}

export const GetUsrInfo = (usrName) => {
  let api = new Authapi('getUsr')

  let paramList = [usrName]

  return api.Send({}, {}, paramList)
}

export const UpdateUsr = (usrName, param) => {
  let api = new Authapi('putUsr')

  let paramList = [usrName]
  if (param.password !== undefined) {
    param.password = GetPassswordEncode(usrName, param.password)
  }

  return api.Send({}, param, paramList)
}

export const DeleteUsr = (usrName) => {
  let api = new Authapi('delUsr')

  let paramList = [usrName]

  return api.Send({}, {}, paramList)
}

export const getRoles = () => {
  let api = new Authapi('getAllRoles')

  return api.Send({}, {})
}
export const AuthorizeToken = (usrName, startTime, endTime) => {
  let api = new Authapi('authorizeToken')

  let param = {
    'user_name': usrName,
    'start_time': startTime,
    'end_time': endTime
  }

  return api.Send({}, param)
}
export default {
  GetUsrInfoList,
  AddUsr,
  GetUsrInfo,
  UpdateUsr,
  DeleteUsr,
  getRoles,
  AuthorizeToken
}
