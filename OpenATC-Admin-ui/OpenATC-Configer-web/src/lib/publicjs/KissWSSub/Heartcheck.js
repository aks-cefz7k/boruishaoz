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
 * `WebsocketHeartbeatJs` constructor.
 *
 * @param {Object} opts
 * {
 *  url                  websocket链接地址
 *  pingTimeout 未收到消息多少秒之后发送ping请求，默认15000毫秒
    pongTimeout  发送ping之后，未收到消息超时时间，默认10000毫秒
    reconnectTimeout
    pingMsg
 * }
 * @api public
 */

function WebsocketHeartbeatJs ({
  url,
  pingTimeout = 15000,
  pongTimeout = 10000,
  reconnectTimeout = 2000,
  pingMsg = 'heartbeat'
}) {
  this.opts = {
    url: url,
    pingTimeout: pingTimeout,
    pongTimeout: pongTimeout,
    reconnectTimeout: reconnectTimeout,
    pingMsg: pingMsg
  }
  this.ws = null // websocket实例

  // override hook function
  this.onclose = () => {}
  this.onerror = () => {}
  this.onopen = () => {}
  this.onmessage = () => {}
  this.onreconnect = () => {}

  this.createWebSocket()
}
WebsocketHeartbeatJs.prototype.createWebSocket = function () {
  try {
    this.ws = new WebSocket(this.opts.url)
    this.initEventHandle()
  } catch (e) {
    this.reconnect()
    throw e
  }
}

WebsocketHeartbeatJs.prototype.initEventHandle = function () {
  this.ws.onclose = () => {
    this.onclose()
    this.reconnect()
  }
  this.ws.onerror = () => {
    this.onerror()
    this.reconnect()
  }
  this.ws.onopen = () => {
    this.onopen()
    // 心跳检测重置
    this.heartCheck()
  }
  this.ws.onmessage = (event) => {
    this.onmessage(event)
    // 如果获取到消息，心跳检测重置
    // 拿到任何消息都说明当前连接是正常的
    this.heartCheck()
  }
}

WebsocketHeartbeatJs.prototype.reconnect = function () {
  if (this.lockReconnect || this.forbidReconnect) return
  this.lockReconnect = true
  this.onreconnect()
  // 没连接上会一直重连，设置延迟避免请求过多
  setTimeout(() => {
    this.createWebSocket()
    this.lockReconnect = false
  }, this.opts.reconnectTimeout)
}

WebsocketHeartbeatJs.prototype.send = function (msg) {
  this.ws.send(msg)
}
// 心跳检测
WebsocketHeartbeatJs.prototype.heartCheck = function () {
  this.heartReset()
  this.heartStart()
}
WebsocketHeartbeatJs.prototype.heartStart = function () {
  if (this.forbidReconnect) return // 不再重连就不再执行心跳
  this.pingTimeoutId = setTimeout(() => {
    // 这里发送一个心跳，后端收到后，返回一个心跳消息，
    // onmessage拿到返回的心跳就说明连接正常
    this.ws.send(this.opts.pingMsg)
    // 如果超过一定时间还没重置，说明后端主动断开了
    this.pongTimeoutId = setTimeout(() => {
      // 如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
      this.ws.close()
    }, this.opts.pongTimeout)
  }, this.opts.pingTimeout)
}
WebsocketHeartbeatJs.prototype.heartReset = function () {
  clearTimeout(this.pingTimeoutId)
  clearTimeout(this.pongTimeoutId)
}

WebsocketHeartbeatJs.prototype.close = function () {
  // 如果手动关闭连接，不再重连
  this.forbidReconnect = true
  this.heartReset()
  this.ws.close()
}

if (window) window.WebsocketHeartbeatJs = WebsocketHeartbeatJs
export default WebsocketHeartbeatJs
