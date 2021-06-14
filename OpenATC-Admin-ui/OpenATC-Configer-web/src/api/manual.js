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

export function getManualpanel () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getManualpanel')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/manualpanel'
  return api.Send({}, data)
}

export function setManualpanel (param) {
  var iframdevid = getIframdevid()
  let api = new Authapi('putTscControl')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/manualpanel'
  data.data = param
  return api.Send({}, data)
}

export function getChannel () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getChannel')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'feature/channel'
  return api.Send({}, data)
}

export function getDefaultManualpanel (param) {
  let api = new Authapi('getDefaultManualpanel')
  return api.Send({}, param)
}
