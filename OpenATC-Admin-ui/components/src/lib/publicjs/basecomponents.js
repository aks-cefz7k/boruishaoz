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
@brief 队列容器
@date 2018-08-16
*/
export class Queue {
  constructor () {
    this.items = []
  }

  enqueue () {
    let len = arguments.length
    if (len === 0) {
      return
    }
    for (var i = 0; i < len; i++) {
      this.items.push(arguments[i])
    }
    this.mutex = false
  }

  dequeue () {
    let result = this.items.shift()
    this.mutex = false
    // return typeof result !== 'undefined' ? result : undefined
    return result
  }

  front () {
    return this.items[this.items.length - 1]
  }

  isEmpty () {
    return this.items.length === 0
  }

  size () {
    return this.items.length
  }

  clear () {
    this.items = []
    this.mutex = false
  }

  show () {
    return this.items
  }
}

export const mapcopy = (map1, map2) => {
  for (let [key, value] of map1) {
    map2.set(key, value)
  }
}
