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

export const GetUntreated = (pageNum, pageSize, enumerate) => {
  let api = new Authapi('GetUntreated')
  let param = {
    'pageNum': pageNum,
    'pageRow': pageSize,
    'enumerate': enumerate
  }
  return api.Send(param, {})
}

export const searchRoadName = (id) => {
  let api = new Authapi('searchRoadName')
  let paramList = [id]
  return api.Send({}, {}, paramList)
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

export const DeleteFault = (delObj) => {
  // 删除故障记录
  let api = new Authapi('DeleteFault')
  let param = {
    agentid: delObj.agentid,
    id: delObj.id
  }
  return api.Send({}, param)
}

export const enumerateCheck = (agentid, id, enumerate) => {
  let api = new Authapi('enumerateCheck')
  let param = {
    'agentid': agentid,
    'id': id,
    'enumerate': enumerate
  }
  return api.Send({}, param)
}

export const GetAllFaultRange = (pageNum, pageSize, id, beginTime, endTime, faultBoardType, faultType, enumerate, type) => {
  let api = new Authapi('GetAllFaultRange')
  let param = {
    'pageNum': pageNum,
    'pageRow': pageSize
  }
  if (id && id !== '') {
    param.agentId = id
  }
  if (beginTime) {
    param.beginTime = beginTime
  }
  if (endTime) {
    param.endTime = endTime
  }
  if (faultBoardType && faultBoardType !== '') {
    param.m_byFaultBoardType = faultBoardType
  }
  if (faultType && faultType !== '') {
    param.m_wFaultType = faultType
  }
  if (enumerate && enumerate !== '') {
    param.enumerate = enumerate
  }
  return api.Send({}, param)
}

export const GetFaultRange = (param) => {
  let api = new Authapi('GetAllFaultRange')
  return api.Send({}, param)
}

export default {
  GetFaultRange,
  GetAllCurrentFault,
  GetAllHistoryFault,
  GetCurrentFaultByAgentid,
  GetAllHistoryFaultByAgentid,
  DeleteFaultById,
  GetAllFaultRange,
  GetAllFault,
  GetUntreated,
  searchRoadName,
  enumerateCheck
}
