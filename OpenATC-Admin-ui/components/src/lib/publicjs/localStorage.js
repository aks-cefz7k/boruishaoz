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
/*
@brief 浏览器缓存
@auth caijiadong
@date 2018-08-13
*/
import Cookies from 'js-cookie'
import localStorage from 'localStorage'
export const isObject = val => {
  return val !== null && typeof val === 'object'
}

export const setStorage = (key, val, type = 'localStorage') => {
  if (type === 'localStorage') {
    localStorage.setItem(key, val)
  } else if (type === 'sessionStorage') {
    Cookies.set(key, val)
  } else {
    if (isObject(val)) window[type].setItem(key, JSON.stringify(val))
    else window[type].setItem(key, val)
  }
}

const listToString = list => {
  return JSON.stringify(list)
}

const StringToList = string => {
  return JSON.parse(string)
}

export const setListStorage = (key, list, type = 'localStorage') => {
  let data = listToString(list)

  setStorage(key, data, type)
}

export const getListStorage = (key, type = 'localStorage') => {
  let data = getStorage(key, type)

  return StringToList(data)
}

export const getStorage = (key, type = 'localStorage') => {
  if (type === 'localStorage') {
    return localStorage.getItem(key)
  } else if (type === 'sessionStorage') {
    return Cookies.get(key)
  } else {
    let val = window[type].getItem(key)
    try {
      return JSON.parse(val)
    } catch (e) {
      return val
    }
  }
}

export const setSessionStore = (key, val) => {
  setStorage(key, val, 'sessionStorage')
}

export const getSessionStore = key => {
  return getStorage(key, 'sessionStorage')
}

export const getSessionObj = () => {
  return Cookies
}

export class TempCache {
  constructor () {
    this.cacheMap = new Map()
  }

  init () {
    this.cacheMap.clear()
  }

  setData (key, value) {
    this.cacheMap.set(key, value)
  }

  getData (key) {
    return this.cacheMap.get(key)
  }

  hasData (key) {
    return this.cacheMap.has(key)
  }
}
export const tempCache = new TempCache()

export default {
  isObject,
  setStorage,
  getStorage,
  setSessionStore,
  getSessionStore,
  tempCache,
  getSessionObj
}
