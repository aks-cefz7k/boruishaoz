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
 * 数组去重
 * @param {*} array
 */
export function kdistinct (array) {
  return Array.from(new Set(array))
}
/**
 * 求数组并集
 * @param {*} array1
 * @param {*} array2
 */
export function kconcat (array1, array2) {
  return array1.concat(array2.filter(v => !array1.includes(v)))
}

/**
 * 求交集
 */
export function kintersection (array1, array2) {
  return array1.filter(v => array2.includes(v))
}

// 差集
export function kdiff (array1, array2) {
  return array1.concat(array2).filter(v => array1.includes(v) && !array2.includes(v))
}
