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
var localStorage = require('@openatc/edgebase-front/src/lib/publicjs/localStorage')

const LoginKey = 'token'
const LoginHost = 'kisshost'
const typeKey = 'type'
const type = 'edge'
const simuKey = 'simu_key'
const language = 'intl_language'
const kisspro = 'kiss_pro'
const theme = 'kiss_theme'

export function getToken () {
  return localStorage.getStorage(LoginKey)
}

export function setToken (token) {
  return localStorage.setStorage(LoginKey, token)
}

export function SetSimuUserKey (key) {
  return localStorage.setStorage(simuKey, key)
}

export function removeToken () {
  return localStorage.setStorage(LoginKey, '')
}

export function getHost () {
  let ret = localStorage.getStorage(LoginHost)
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function setHost (host) {
  return localStorage.setStorage(LoginHost, host)
}

export function getLanguage () {
  let intl = localStorage.getStorage(language)
  if (intl === undefined || intl === null || intl === '') {
    return ''
  }
  return intl
}

export function setLanguage (lan) {
  return localStorage.setStorage(language, lan)
}

export function getTheme () {
  let th = localStorage.getStorage(theme)
  if (th === undefined || th === null || th === '') {
    return 'dark'
  }
  return th
}

export function setTheme (th) {
  return localStorage.setStorage(theme, th)
}

export const setType = () => {
  window[typeKey] = type
}

export const getType = () => {
  return window[typeKey]
}
// 从缓存获取设备ID
export const getIframdevid = () => {
  var iframdevid = localStorage.getStorage('iframdevid')
  if (iframdevid === '' || iframdevid === null || iframdevid === undefined) {
    iframdevid = 0
  }
  return iframdevid
}

export const setIframdevid = (v) => {
  return localStorage.setStorage('iframdevid', v)
}

export function setKiss (v) {
  return localStorage.setStorage(kisspro, v)
  // return localStorage.setItem(LoginUserName, username)
}

export function getKiss () {
  return localStorage.getStorage(kisspro)
}
