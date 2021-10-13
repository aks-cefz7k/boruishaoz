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

// 设置信号机挂载U盘
export function setVolumelog (param) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setVolumelog')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/volumelog'
  data.data = param
  return api.Send({}, data)
}
