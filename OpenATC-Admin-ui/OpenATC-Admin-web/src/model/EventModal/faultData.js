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
    let faultEventList = message.data
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
    this.curSub = this.GlobalData.getData('faulteventdata', this.recvMessage) // 无订阅消息参数
  }
  unSub () {
    this.GlobalData.stop('faulteventdata', this.curSub)
  }
}
