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
// 获取异常数据数据
import store from '../../store'
export default class Event {
  Init (setEventCall) {
    this.call = setEventCall
    this.InitSub()
  }
  UnInit () {
    this.unSub()
  }
  recvMessage = (message) => {
    let faultEventList = message
    store.dispatch('InitFaultEventData', faultEventList)
    this.call(faultEventList)
    setTimeout(() => {
      store.dispatch('InitFaultEventData', {})
    }, 60000)
  }
  InitSub () {
    let TrafficDataMgr = store.getters['kissModel'].GetMgrByKey('TrafficDataMgr')[0]
    this.GlobalData = TrafficDataMgr.GetRelTimeDataInterface().GetDataObj('global')
    // this.curSub = this.GlobalData.getData('faulteventdata', this.recvMessage, ['1001', '1002']) // 有订阅消息参数
    this.curSub = this.GlobalData.getData('faulteventdata', this.recvMessage, ['*']) // 无订阅消息参数
  }
  unSub () {
    this.GlobalData.stop('faulteventdata', this.curSub)
  }
}
