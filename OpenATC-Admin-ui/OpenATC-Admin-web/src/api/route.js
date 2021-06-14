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

export const GetSimpleRoute = () => {
  let api = new Authapi('getSimpleRoute')

  return api.Send({}, {}, [])
}

export const GetRouteInfo = (id) => {
  let api = new Authapi('getRouteInfo')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export const AddRoute = (data) => {
  let api = new Authapi('addRoute')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const UpdateRoute = (data) => {
  let api = new Authapi('updateRoute')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const DeleteRoute = (id) => {
  let api = new Authapi('deleteRoute')

  let paramList = [id]

  return api.Send({}, {}, paramList)
}

export function getTscControl (agentid) {
  let api = new Authapi('getTscControl')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'feature/pattern'
  return api.Send({}, data)
}

export function getTscPhase (agentid) {
  let api = new Authapi('getTscControl')
  let data = {}
  data.agentid = agentid
  data.operation = 'get-request'
  data.infotype = 'feature/phase'
  return api.Send({}, data)
}

export const getAllPatternOfRoute = (id) => {
  let api = new Authapi('getAllPatternOfRoute')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export const getAllPhaseOfRoute = (id) => {
  let api = new Authapi('getAllPhaseOfRoute')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export const generateRoute = (param) => {
  let api = new Authapi('generateRoute')
  return api.Send({}, param)
}

export const putAllPatternOfRoute = (param) => {
  let api = new Authapi('putAllPatternOfRoute')
  return api.Send({}, param)
}

export const UpdateRouteName = (data) => {
  let api = new Authapi('updateRouteName')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}
