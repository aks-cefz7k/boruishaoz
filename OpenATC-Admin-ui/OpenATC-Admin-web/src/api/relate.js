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
/* eslint-disable new-cap */
import authapi from './authapi'

export const GetNoneRelatedDevices = () => {
  let api = new authapi('getNoneRelatedDevices')

  return api.Send({}, {})
}

export const GetDevicesByOrgCode = (code) => {
  let api = new authapi('getDevicesByOrgCode')

  let urlParamList = [code]

  return api.Send({}, {}, urlParamList)
}

export const BatchUpdateDevice = (code, idList) => {
  let api = new authapi('batchUpdateDevice')

  let param = idList

  let urlParamList = [code, 'devs']

  return api.Send({}, param, urlParamList)
}

export default {
  GetNoneRelatedDevices,
  GetDevicesByOrgCode,
  BatchUpdateDevice
}
