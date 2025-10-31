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
// 历史数据决策模型
export default class TrafficDataDecisionModel {
  constructor (data) {
    // data是所有数据
    this.data = data
  }

  getData (type) {
    // 根据type类型从所有数据里取对应数据
    const curData = JSON.parse(JSON.stringify(this.data))
    for (let i = 0; i < curData.length; i++) {
      curData[i] = {
        date: curData[i].date,
        value: curData[i][type]}
    }
    return curData
  }
}
