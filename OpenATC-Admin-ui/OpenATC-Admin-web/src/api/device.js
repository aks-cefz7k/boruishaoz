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

export const GetAllDevice = () => {
  let api = new Authapi('getAllDevice')

  return api.Send({}, {}, [])
}

export const getDict = () => {
  let api = new Authapi('getDict')

  return api.Send({}, {}, [])
}
// 添加字典
export const addDict = (data) => {
  let api = new Authapi('addDict')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const GetDeviceByIds = (ids) => {
  let api = new Authapi('getDeviceByIds')
  const params = {
    routeIntersectionIdList: ids
  }
  return api.Send({}, params, [])
}

export const getDeviceRange = (data) => {
  let api = new Authapi('getDeviceRange')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const AddDevice = (data) => {
  let api = new Authapi('addDevice')
  const params = JSON.parse(JSON.stringify(data))
  params.jsonparam = {}
  params.jsonparam.ip = params.ip
  params.jsonparam.port = params.port
  delete params.ip
  delete params.port
  return api.Send({}, params, [])
}

export const UpdateDevice = (data) => {
  let api = new Authapi('updateDevice')
  const params = JSON.parse(JSON.stringify(data))
  params.jsonparam.ip = params.ip
  params.jsonparam.port = params.port
  delete params.ip
  delete params.port
  return api.Send({}, params, [])
}

export const UpdateDeviceId = (data) => {
  let api = new Authapi('updateDeviceId')
  return api.Send({}, data, [])
}

export const DeleteDevice = (id) => {
  let api = new Authapi('deleteDevice')

  let paramList = [id]

  return api.Send({}, {}, paramList)
}

export const getAllTrafficData = (reqData) => {
  let api = new Authapi('getAllTrafficData')
  return api.Send({}, reqData, [])
}

export const getAllPatternData = (reqData) => {
  let api = new Authapi('getAllPatternData')
  return api.Send({}, reqData, [])
}

export const getYesterdayStatusCollect = () => {
  let api = new Authapi('getYesterdayStatusCollect')
  return api.Send({}, {}, [])
}

export default {
  GetAllDevice,
  AddDevice,
  UpdateDevice,
  DeleteDevice,
  UpdateDeviceId,
  getAllTrafficData,
  getAllPatternData,
  getDeviceRange,
  getDict,
  addDict,
  getYesterdayStatusCollect
}
