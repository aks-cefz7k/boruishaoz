/**
 * @Description: 该类实现一个基本的sub模块，该模块可以支持订阅频道和取消频道
 * @Author: chengcheng
 * @Date: Create in 10:26 2018/12/21
 * @Modified By:
 */
import Channel from 'chnl'
// import {
//   kdiff,
//   kconcat
// } from './ArryListUtil'
export default class SubChannel {
  /**
   *
   * @param {in} name sub标识
   */
  constructor (name, model, ws) {
    this._name = name // 订阅的消息类型
    this._model = model // 订阅的上报模块
    this._channel = new Channel()
    this.setMessageListener()
    this._ws = ws
    this._curCareWholist = []
  }
  /**
   * 开始订阅,需要指定订阅哪些对象的该类型数据
   * @param {*} wholist 订阅消息参数
   */
  start (wholist) {
    let wsbody = {
      'model': this._model,
      'infotype': this._name,
      subscribe: 'up'
    }
    if (wholist) {
      wsbody.param = wholist
    }
    // let difflist = kdiff(wholist, this._curCareWholist)
    // this._curCareWholist = kconcat(this._curCareWholist, wholist)
    // if (difflist.length === 0) return
    this._ws.sendPacked(wsbody)
  }
  /**
   * 停止订阅，需要指定取消哪些对象的该类型数据的订阅
   * @param {*} wholist 订阅消息参数
   */
  stop (wholist) {
    // this._curCareWholist = kdiff(this._curCareWholist, wholist)
    let wsbody = {
      'model': this._model,
      'infotype': this._name,
      subscribe: 'down'
    }
    if (wholist) {
      wsbody.param = wholist
    }
    this._ws.sendPacked(wsbody)
  }
  /**
   * 处理来的消息 分发给所有关心者
   * @param {*} message
   */
  handleMessage (message) {
    this._channel.dispatchAsync(message)
  }
  /**
   * 设置消息监听，一旦发现是自己的消息就处理自己的消息
   */
  setMessageListener () {
    this._emitter = new Channel.EventEmitter()
    // listen 'myEvent'
    this._emitter.on(name, (message) => {
      this.handleMessage(message)
    })
  }
  /**
   * 发射处理消息的消息让自己处理
   * @param {*} data 处理的数据
   */
  emitHandleSignal (data) {
    this._emitter.emit(name, data)
  }
  /**
   * 添加回调
   * @param {*} call 回调函数
   * @param {*} context  上下文
   */
  addCare (call, context) {
    this._channel.addListener(call, context)
  }
  /**
   * 添加一次响应的回调，只会关心一次
   * @param {*} call 回调函数
   * @param {*} context  上下文
   */
  addOnceCare (call, context) {
    this._channel.addOnceListener(call, context)
  }
  /**
   * 删除已经存在的回调
   * @param {*} call 回调函数
   * @param {*} context  上下文
   */
  removeCare (call, context) {
    if (this._channel.hasListener(call, context)) {
      this._channel.removeListener(call, context)
    }
  }
  /**
   * 删除所有的关心该消息的回调
   */
  removeAllCares () {
    if (this._channel.hasListeners()) {
      this._channel.removeAllListeners()
    }
  }
}
