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

export function setCrossFilter (filter) {
  localStorage.setStorage('deviceFilter', filter)
}

export function setCrossTag (filter) {
  localStorage.setStorage('selectTag', filter)
}

export function setCrossStates (filter) {
  localStorage.setStorage('states', filter)
}

export function setCrossProtocols (filter) {
  localStorage.setStorage('protocols', filter)
}

export function setCrossPlats (filter) {
  localStorage.setStorage('plats', filter)
}

export function setCrossDevicetypes (filter) {
  localStorage.setStorage('devicetypes', filter)
}

export function getCrossFilter () {
  let ret = localStorage.getStorage('deviceFilter')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function getCrossTag () {
  let ret = localStorage.getStorage('selectTag')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function getCrossStates () {
  let ret = localStorage.getStorage('states')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function getCrossProtocols () {
  let ret = localStorage.getStorage('protocols')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function getCrossPlats () {
  let ret = localStorage.getStorage('plats')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function getCrossDevicetypes () {
  let ret = localStorage.getStorage('devicetypes')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function clearCrossFilter () {
  localStorage.setStorage('deviceFilter', '')
}
