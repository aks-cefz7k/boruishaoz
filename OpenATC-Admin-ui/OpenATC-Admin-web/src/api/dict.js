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

class Dict {
  getDictList = () => {
    let api = new Authapi('getDict')
    return api.Send({}, {}, [])
  }
  getDictListByTag = (param) => {
    let api = new Authapi('getDictByTag')
    return api.Send({}, {}, [param])
  }
  // 添加字典
  addDict = (data) => {
    let api = new Authapi('addDict')
    return api.Send({}, data, [])
  }
  updateDict = (data) => {
    let api = new Authapi('updateDict')
    return api.Send({}, data, [])
  }
  deleteDict = (data) => {
    let api = new Authapi('deleteDict')
    return api.Send({}, data, [])
  }
}
export const DictApi = new Dict()
