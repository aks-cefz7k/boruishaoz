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
 * @Modified By:
 */
import WebSocketAsPromised from 'websocket-as-promised'
import Sub from './Sub'
export default class KissWsSub {
  /**
   * 构造函数
   * @param {in} wsurl websocket 连接url ws:127.0.0.1:8888
   * @param {in} name 标识模块
   */
  constructor (wsurl, name) {
    this._url = wsurl
    this._name = name
    this._ws = null
    this._sub_map = new Map()
  }
  /**
   * 启动订阅服务
   */
  async start () {
    if (this._ws !== null && this._ws.isOpened) return
    this._ws = new WebSocketAsPromised(this.url, {
      packMessage: data => JSON.stringify(data),
      unpackMessage: message => JSON.parse(message)
    })
    await this._ws.open()
    this._ws.onUnpackedMessage.addListener(this.dispatchMessage)
  }
  /**
   * 停止订阅服务
   */
  async stop () {
    if (this._ws.isOpening) {
      await this._ws.close()
    } else {
      console.log('The websocket not run!')
    }
  }
  /**
   * 分发消息
   * @param {in} message
   */
  dispatchMessage (message) {
    for (let sub of this._sub_map.values()) {
      sub.emitHandleSignal(message)
    }
  }
  /**
   * 创建一个订阅 指定订阅类型
   * @param {in} type 订阅类型标识 唯一 不能重复
   */
  createSub (type) {
    if (this._sub_map.has(type)) {
      return this._sub_map[type]
    }
    let sub = new Sub(type)
    this._sub_map.set(type, sub)
    return sub
  }
  /**
   * 获取已经存在的订阅根据订阅类型
   * @param {*} type 订阅类型
   * @return subObject or null
   */
  getSubByType (type) {
    if (this._sub_map.has(type)) {
      return this._sub_map.get(type)
    }
    return null
  }
  stopSubByType (type) {
    if (this._sub_map.has(type)) {
      return this._sub_map.get(type).stop()
    }
  }
}
