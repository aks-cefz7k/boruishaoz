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

export function getSupportedTemplate () {
  let api = new Authapi('getSupportedTemplate')
  return api.Send({}, {})
}
export function createTemplate (param) {
  let api = new Authapi('createTemplate')
  return api.Send({}, param, [])
}
export function getIntersectionInfo (agentid) {
  // 获取路口类型和相位信息
  let api = new Authapi('getIntersectionInfo')
  let paramList = [agentid]
  return api.Send({}, {}, paramList)
}
