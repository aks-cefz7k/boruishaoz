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

export function getTscControl (agentid) {
  // var iframdevid = getIframdevid()
  if (agentid === '' || agentid === undefined) {
    agentid = 0
  }
  let api = new Authapi('getTscControl')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'status/pattern'
  return api.Send({}, data)
}
export function putTscControl (param) {
  var iframdevid = getIframdevid()
  let api = new Authapi('putTscControl')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'control/pattern'
  data.data = param
  return api.Send({}, data)
}
export function AddDevice (data) {
  let api = new Authapi('addDevice')
  const params = JSON.parse(JSON.stringify(data))
  params.jsonparam = {}
  params.jsonparam.ip = params.ip
  params.jsonparam.port = params.port
  delete params.ip
  delete params.port
  return api.Send({}, params, [])
}
export function queryDevice () {
  var iframdevid = getIframdevid()
  let api = new Authapi('queryDevice')
  let paramList = [iframdevid]
  return api.Send({}, {}, paramList)
}
export function getTscPattern (agentid) {
  if (agentid === '' || agentid === undefined) {
    agentid = 0
  }
  let api = new Authapi('getTscControl')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'control/pattern'
  return api.Send({}, data)
}
export function getTscCurrentVolume (agentid) {
  if (agentid === '' || agentid === undefined) {
    agentid = 0
  }
  let api = new Authapi('getTscCurrentVolume')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'status/currentvolume'
  return api.Send({}, data)
}
