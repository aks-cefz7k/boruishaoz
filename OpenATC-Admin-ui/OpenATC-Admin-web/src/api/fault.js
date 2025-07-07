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

export const GetAllCurrentFault = () => {
  let api = new Authapi('GetAllCurrentFault')
  return api.Send({}, {}, [])
}

export const GetAllFault = (pageNum, pageSize) => {
  let api = new Authapi('GetAllFault')
  let param = {
    'pageNum': pageNum,
    'pageRow': pageSize
  }
  return api.Send(param, {})
}

export const GetAllHistoryFault = () => {
  let api = new Authapi('GetAllHistoryFault')
  return api.Send({}, {}, [])
}

export const GetCurrentFaultByAgentid = (id) => {
  let api = new Authapi('GetCurrentFaultByAgentid')
  let urlParamList = [id, 'current']
  return api.Send({}, {}, urlParamList)
}

export const GetAllHistoryFaultByAgentid = (id) => {
  let api = new Authapi('GetAllHistoryFaultByAgentid')
  let urlParamList = [id, 'history']
  return api.Send({}, {}, urlParamList)
}

export const DeleteFaultById = (id) => {
  let api = new Authapi('DeleteFaultById')
  let urlParamList = [id]
  return api.Send({}, {}, urlParamList)
}

export const GetAllFaultRange = (pageNum, pageSize, id, beginTime, endTime) => {
  let api = new Authapi('GetAllFaultRange')
  let param = {
    'pageNum': pageNum,
    'pageRow': pageSize,
    'agentId': id,
    'beginTime': beginTime,
    'endTime': endTime
  }
  return api.Send({}, param)
}

export default {
  GetAllCurrentFault,
  GetAllHistoryFault,
  GetCurrentFaultByAgentid,
  GetAllHistoryFaultByAgentid,
  DeleteFaultById,
  GetAllFaultRange,
  GetAllFault
}
