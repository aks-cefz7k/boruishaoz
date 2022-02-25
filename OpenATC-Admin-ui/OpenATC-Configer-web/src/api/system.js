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
import Authapi from './authapi'
import {
  getIframdevid
} from '../utils/auth'

// 获取信号机版本
export function getSignVersion () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getSignVersion')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/version'
  // let paramList = [iframdevid, 'param', 'upload']
  return api.Send({}, data)
}

// 设置信号机版本val1表示软件版本，val2表示硬件版本
export function setSignVersion (val1, val2) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setSignVersion')
  let param = {}
  param.software = val1
  param.hardware = val2
  let data = {}
  data.agentid = iframdevid
  data.operation = 'report'
  data.infotype = 'system/version'
  data.data = param
  return api.Send({}, data)
}

// 获取当前系统时间
export function getSystemTime () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getSystemTime')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/time'
  return api.Send({}, data)
}
// 设置当前系统时间
export function setSystemTime (time) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setSystemTime')
  let param = {}
  param.time = time
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/time'
  data.data = param
  return api.Send({}, data)
}
// 获取特征参数版本
export function getParamVersion () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getParamVersion')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/paramversion'
  return api.Send({}, data)
}

// 设置特征参数版本
export function setParamVersion (version) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setParamVersion')
  let param = {}
  param.version = version
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/paramversion'
  data.data = param
  return api.Send({}, data)
}
// 获取识别码
export function getCode () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getCode')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/code'
  return api.Send({}, data)
}

// 设置识别码
export function setCode (code) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setCode')
  let param = {}
  param.code = code
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/code'
  data.data = param
  return api.Send({}, data)
}
// 获取IP数据
export function getSignIp () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getSignIp')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/ip'
  // let paramList = [iframdevid, 'param', 'upload']
  return api.Send({}, data)
}

// 设置IP数据
export function setSignIp (ip, subnetmask, gateway) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setSignIp')
  let param = {}
  param.ip = ip
  param.subnetmask = subnetmask
  param.gateway = gateway
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/ip'
  data.data = param
  return api.Send({}, data)
}
// 获取串口数据
export function getSerialPort () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getSerialPort')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/serialport'
  return api.Send({}, data)
}

// 设置串口数据
export function setSerialPort (Baudrate, databit, stopbit, paritybit) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setSerialPort')
  let param = {}
  param.Baudrate = Baudrate
  param.databit = databit
  param.stopbit = stopbit
  param.paritybit = paritybit
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/serialport'
  data.data = param
  return api.Send({}, data)
}

// 设置远程控制
export function setRemoteControl (type) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setRemoteControl')
  let param = {}
  param.type = type
  param.value = 0
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'control/remote'
  data.data = param
  return api.Send({}, data)
}

// 获取远程调试数据
export function getRemoteDebug () {
  var iframdevid = getIframdevid()
  let api = new Authapi('getRemoteDebug')
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'system/remote'
  return api.Send({}, data)
}

// 设置远程控制
export function setRemoteDebug (remoteInfo) {
  var iframdevid = getIframdevid()
  let api = new Authapi('setRemoteDebug')
  let param = {}
  param.status = remoteInfo.status
  param.duration = remoteInfo.duration
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/remote'
  data.data = param
  return api.Send({}, data)
}

// 更新优盘数据
export const udiskupdate = () => {
  let api = new Authapi('udiskupdate')
  var iframdevid = getIframdevid()
  let param = {
    'value': 1
  }
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/udiskupdate'
  data.data = param
  return api.Send({}, data)
}

// 通道检测
export const channelcheck = (param) => {
  let api = new Authapi('channelcheck')
  var iframdevid = getIframdevid()
  let data = {}
  data.agentid = iframdevid
  data.operation = 'set-request'
  data.infotype = 'system/channelcheck'
  data.data = param
  return api.Send({}, data)
}

// 获取通道电流电压状态
export const channeltest = () => {
  let api = new Authapi('channeltest')
  var iframdevid = getIframdevid()
  let data = {}
  data.agentid = iframdevid
  data.operation = 'get-request'
  data.infotype = 'status/channeltest'
  return api.Send({}, data)
}
