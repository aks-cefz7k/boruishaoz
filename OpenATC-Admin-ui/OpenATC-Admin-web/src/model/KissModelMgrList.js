// 这个类主要负责将业务对象加载到全局的mode对象
import TrafficDataMgr from './TrafficData/TrafficDataMgr'

export function MgrCreate (mgrlist) {
  /// @brief 交通数据管理
  let dataMgr = new TrafficDataMgr()
  mgrlist.push(dataMgr)
}
