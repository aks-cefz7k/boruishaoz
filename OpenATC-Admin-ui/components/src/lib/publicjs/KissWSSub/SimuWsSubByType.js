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
/**
 * @brief 仿真websocket封装
 * @author caijiadong
 * @date 2019-08-05
 */

import KissWsSubByType from './KissWsSubByType'

export default class SimuWsSubByType extends KissWsSubByType {
  constructor (simuid, wsurl, name, reconnectTimeout = 2000, reconnectTimes = 50) {
    super(wsurl, name, reconnectTimeout, reconnectTimes)
    this.simuId = simuid
    this._url += '?' + this.ObjToUrlParam({
      scenestype: 'simu',
      scenesid: this.simuId
    })
  }

  ObjToUrlParam (data) {
    try {
      var tempArr = []
      for (var i in data) {
        var key = encodeURIComponent(i)
        var value = encodeURIComponent(data[i])
        tempArr.push(key + '=' + value)
      }
      var urlParamsStr = tempArr.join('&')
      return urlParamsStr
    } catch (err) {
      return ''
    }
  }
}
