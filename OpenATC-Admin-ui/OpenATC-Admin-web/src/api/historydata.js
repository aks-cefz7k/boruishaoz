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
export function getHistoryPatternData (param) {
  let api = new Authapi('getHistoryPatternData')
  let data = {}
  // data.agentId = param.agentId
  // data.beginTime = param.beginTime
  // data.endTime = param.endTime
  // data.beginTime = '2021-10-27 15:50:00'
  // data.endTime = '2021-10-27 15:59:45'
  data.agentId = '1634181198793'
  data.beginTime = '2021-10-25 08:40:42'
  data.endTime = '2021-10-25 08:41:04'
  return api.Send({}, data)
}
