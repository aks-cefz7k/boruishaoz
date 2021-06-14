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

/**
   * iframdevid 设备ID
   * override = 0 未开启自学习功能
   * override = 1 开启自学习功能
   * model 数学模型
   * optimize [{
   *  'agentid': '22', // 设备id
   *  'phaseid': 1, // 相位id
   *  'maxflow': 32.0, // 饱和流量（辆）
   *  'maxspeed': 52.0, // 最大速度（千米/小时）
   *  'maxqueue': 10.0, // 饱和排队长度（米）
   *  'maxoccupancy': 21.0, // 饱和占有率
   *  }]
   */
export const downloadOptParam = (optimize, override) => {
  var iframdevid = getIframdevid()
  let api = new Authapi('downloadOptParam')
  let data = {
    'agentid': iframdevid,
    'override': override,
    'strategytype': 'model',
    'jsonparam': optimize
  }
  let paramList = [iframdevid, 'opt', 'download']
  return api.Send({}, data, paramList)
}

export function uploadOptParam () {
  var iframdevid = getIframdevid()
  let api = new Authapi('uploadOptParam')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'status/opts'
  // let paramList = [iframdevid, 'opt', 'upload']
  return api.Send({}, data)
}

export const runStrategyType = () => {
  var iframdevid = getIframdevid()
  let api = new Authapi('downloadOptParam')
  let data = {}
  let paramList = [iframdevid, 'opt', 'run', 'model']
  return api.Send({}, data, paramList)
}
