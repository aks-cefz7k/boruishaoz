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
 * @Description: 该类实现一个基本的sub模块，该模块可以支持订阅频道和取消频道
 * @Author: chengcheng
 * @Date: Create in 10:26 2018/12/21
 * @Modified By:
 */
import Channel from 'chnl'
export default class Sub {
  /**
   *
   * @param {in} name sub标识
   */
  constructor (name) {
    this._name = name
    this._channel_map = new Map()
    this.setMessageListener()
  }
  /**
   * 处理来的消息
   * @param {*} message
   */
  handleMessage (message) {
    console.log(message)
  }
  /**
   * 设置消息监听，一旦发现是自己的消息就处理自己的消息
   */
  setMessageListener () {
    this._emitter = new Channel.EventEmitter()
    // listen 'myEvent'
    this._emitter.on(name, this.handleMessage)
  }
  /**
   * 发射处理消息的消息让自己处理
   * @param {*} data 处理的数据
   */
  emitHandleSignal (data) {
    this._emitter.emit(name, data)
  }
}
