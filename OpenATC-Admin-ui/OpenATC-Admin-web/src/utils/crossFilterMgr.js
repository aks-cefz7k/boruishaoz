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

export function getCrossFilter () {
  let ret = localStorage.getStorage('deviceFilter')
  if (ret === undefined || ret === null) {
    return ''
  }
  return ret
}

export function clearCrossFilter () {
  localStorage.setStorage('deviceFilter', '')
}
