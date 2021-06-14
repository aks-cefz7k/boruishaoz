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

export function registerMessage (agentid) {
  // var iframdevid = getIframdevid()
  if (agentid === '' || agentid === undefined) {
    agentid = 0
  }
  let api = new Authapi('registerMessage')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'login'
  // let paramList = [iframdevid, 'param', 'upload']
  return api.Send({}, data)
}

export function uploadTscParam () {
  var iframdevid = getIframdevid()
  let api = new Authapi('uploadTscParam')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'feature/all'
  // let paramList = [iframdevid, 'param', 'upload']
  return api.Send({}, data)
}

export function uploadSingleTscParam (type) {
  var iframdevid = getIframdevid()
  let api = new Authapi('uploadSingleTscParam')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'feature/' + type
  // let paramList = [iframdevid, 'param', 'upload']
  return api.Send({}, data)
}

export function uploadSelectedTscParam (uuid) {
  var iframdevid = getIframdevid()
  let api = new Authapi('uploadSelectedTscParam')
  let paramList = [iframdevid, 'param', 'upload', uuid]
  return api.Send({}, {}, paramList)
}
export const downloadTscParam = (operator, tscParams) => {
  var iframdevid = getIframdevid()
  let api = new Authapi('downloadTscParam')
  let param = JSON.stringify(tscParams)
  let params = JSON.parse(param)
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'feature/all'
  data.data = params
  // let data = {
  //   operator,
  //   params
  // }
  // let paramList = [iframdevid, 'param', 'download']
  return api.Send({}, data)
}

export const downloadSingleTscParam = (type, tscParams) => {
  var iframdevid = getIframdevid()
  let api = new Authapi('downloadSingleTscParam')
  let param = JSON.stringify(tscParams)
  let params = JSON.parse(param)
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'feature/' + type
  data.data = params
  return api.Send({}, data)
}

export function deleteTscParam (uuid) {
  var iframdevid = getIframdevid()
  let api = new Authapi('deleteTscParam')
  let paramList = [iframdevid, 'param', 'delete', uuid]
  return api.Send({}, {}, paramList)
}

export const updateFile = (file) => {
  // var iframdevid = getIframdevid()
  let api = new Authapi('updateFile')
  return api.Send({}, file, [], true, true)
}

export function checkCode (params) {
  let api = new Authapi('checkCode')
  let data = {}
  data.data = params
  return api.Send({}, data)
}

export function getAllDailyRecord () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getAllDailyRecord')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/log'
  return api.Send({}, data)
}
