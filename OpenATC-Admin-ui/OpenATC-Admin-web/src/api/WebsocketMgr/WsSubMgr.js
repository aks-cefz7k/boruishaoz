/**
 * @Description: 这个类主要负责管理websocket订阅模块
 * @Author: chengcheng
 * @Date: Create in 15:07 2018/12/25
 * @Modified By:
 */
import KissWsSubByType from '../wsmodel/KissWsSubByType'
const getHost = () => {
  let host = location.host
  if (process.env.NODE_ENV === 'development') {
    return `ws://${host}/socket/openatc/ws`
    // return 'ws://192.168.13.103:10003/openatc/ws'
  }
  return `ws://${host}/openatc/ws`
}
export default class WsSubMgr {
  constructor () {
    this._WsSubMap = new Map()
  }

  CreateSub (wshost, name) {
    return new KissWsSubByType(wshost, name, 5000, 50)
  }

  closeAllSub () {
    /// 对当前已经存在得socket进行关闭
    for (let item of this._WsSubMap.values()) {
      item.stop()
    }
  }

  ReloadSub () {
    /// 关闭和重新创建
    this.closeAllSub()
    return this.createnew('CrossState', getHost())
  }

  async createnew (name, wshost) {
    let sub = this.CreateSub(wshost, name)
    await sub.start()
    this._WsSubMap.set(name, sub)
    return sub
  }

  getByName (name) {
    if (this._WsSubMap.has(name)) {
      return this._WsSubMap.get(name)
    }
  }
  removeByName (name) {
    let sub = this._WsSubMap.get(name)
    if (sub === undefined) return

    sub.stop().then(() => {
      this._WsSubMap.delete(name)
    })
  }
}
