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
 * @Description: 该类实现了websocket订阅服务
 * @Author: chengcheng
 * @Date: Create in 10:02 2018/12/21
 * @Modified By: chengcheng 2019-03-29
 */
import WebSocketAsPromised from 'websocket-as-promised'
import SubChannel from './SubChannel'
// const HeartMessageType = 'HeartType'
export default class KissWsSubByType {
  /**
   * 构造函数
   * @param {in} wsurl websocket 连接url ws:127.0.0.1:8888
   * @param {in} name 标识模块
   */
  constructor (wsurl, name, reconnectTimeout = 2000, reconnectTimes = 50) {
    this._url = wsurl
    this._name = name
    this._ws = null
    this._sub_map = new Map()

    // 重连时间
    this.reconnectTimeout = reconnectTimeout
    // 重连次数 默认0 表示无限重连
    this.reconnectTimes = reconnectTimes
    /// 记录重连次数
    this.count = 0
    /// 重连定时器
    this.ReconnectTimerId = null
    this.forbidReconnect = false
  }

  /**
   * 发送原生数据
   */
  SendPackage (msg) {
    this._ws.sendPacked(msg)
  }
  /**
   * 启动订阅服务
   */
  async start () {
    if (this._ws !== null && this._ws.isOpened) return
    this._ws = new WebSocketAsPromised(this._url, {
      packMessage: data => JSON.stringify(data),
      unpackMessage: message => JSON.parse(message)
    })
    this._ws.onClose.addListener(() => {
      if (this.forbidReconnect) return
      this.Reconnect()
    })
    this._ws.onOpen.addListener(() => {
      this.count = 0
    })
    await this._ws.open()
    this._ws.onUnpackedMessage.addListener((message) => {
      this.dispatchMessage(message)
    })
    console.log('start the kisssub .............')
  }

  /**
   * 停止订阅服务
   */
  async stop () {
    if (this._ws.isOpening) {
      await this._ws.close()
      // 如果手动关闭连接，不再重连
      this.forbidReconnect = true
    } else {
      console.log('The websocket not run!')
    }
  }

  /**
   * 重新连接
   */
  async Reconnect () {
    this.count++
    console.log(`reconnection.... ${this.count} times!`)
    if (this.count >= this.reconnectTimes || this._ws.isOpened) {
      clearTimeout(this.ReconnectTimerId)
    } else {
      // 已经关闭了与服务器的连接
      if (this._ws.isClosed) {
        await this._ws.open()
      }
      // 正尝试与服务器建立连接,2正在关闭与服务器的连接
      this.ReconnectTimerId = setTimeout(() => {
        this.Reconnect()
      }, this.reconnectTimeout)
    }
  }

  /**
   * 分发消息
   * @param {in} message
   */
  dispatchMessage (message) {
    if (message.infotype !== undefined) {
      if (this._sub_map.has(message.infotype)) {
        let subChannel = this._sub_map.get(message.infotype)
        subChannel.emitHandleSignal(message)
      }
    }
  }
  /**
   * 创建一个订阅 指定订阅类型
   * @param {in} type 订阅类型标识 唯一 不能重复
   * @returns 如果已经存在返回已存在的对象 未存在创建
   */
  createSub (type) {
    if (this._sub_map.has(type)) {
      return this._sub_map[type]
    }
    let sub = new SubChannel(type, this._ws)
    this._sub_map.set(type, sub)
    return sub
  }
  /**
   * 获取已经存在的订阅根据订阅类型
   * @param {*} type 订阅消息类型
   * @return subObject or null
   */
  getSubByType (type) {
    if (this._sub_map.has(type)) {
      return this._sub_map.get(type)
    }
    return this.createSub(type)
  }
  /**
   * 取消该消息类型的订阅
   * @param {*} type 订阅消息类型
   */
  removeSubByType (type) {
    if (this._sub_map.has(type)) {
      this._sub_map.get(type).removeAllCares()
      this._sub_map.delete(type)
    }
  }
}
