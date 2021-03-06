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

export function getHisParamsList (pageNum, pageSize) {
  let api = new Authapi('getHisParamsList')
  let param = {
    'pageNum': pageNum,
    'pageRow': pageSize
  }
  return api.Send(param, {})
}
export function exportHisParams (uuid) {
  let api = new Authapi('exportHisParams')
  let paramList = [uuid, 'export', 'request']
  return api.Send({}, {}, paramList)
}

export function getHisParamsRange (param) {
  let api = new Authapi('getHisParamsRange')
  return api.Send({}, param)
}
