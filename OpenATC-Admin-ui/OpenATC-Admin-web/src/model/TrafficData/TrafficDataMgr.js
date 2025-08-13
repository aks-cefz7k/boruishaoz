// 交通数据管理
import KissModelBase from '../KissModelBase'
import ReltimeData from './RelTimeData'

export default class TrafficDataMgr extends KissModelBase {
  constructor () {
    super('TrafficDataMgr')
    this.reltimeInterface = new ReltimeData()
  }

  Init () {
    this.reltimeInterface.Init()
  }

  /// @brief 获取实时数据接口
  GetRelTimeDataInterface () {
    return this.reltimeInterface
  }
}
