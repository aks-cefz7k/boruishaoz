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
  getIframdevid
} from '../utils/auth'

// 所有故障信息
export function getFault (broadid) {
  var iframdevid = getIframdevid()
  let api = new Authapi('getFault')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'status/fault'
  data.data = {
    'broadid': broadid
  }
  return api.Send({}, data)
}

// 通过ftp的方式获取信号机上的历史故障数据
export function getFaultHistoryByFtp (reqData) {
  let api = new Authapi('getFaultHistoryByFtp')
  return api.Send({}, reqData)
}
