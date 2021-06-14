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
/* eslint-disable camelcase */
/*
@brief 基本树模型
@auth caijiadong
@date 2018-08-20
*/
export class treeNode {
  constructor (id, name, expand = false, checked = 0, parent = undefined, description, code) {
    this.parent = parent
    this.id = id
    this.name = name
    this.expand = expand
    this.checked = checked
    this.children = []
    this.description = description
    this.code = code
  }

  parent () {
    return this.parent
  }

  addchild (treeNode) {
    treeNode.parent = this
    this.children.push(treeNode)
  }

  getChild (id) {
    for (let child of this.children) {
      if (child.id === id) {
        return child
      }
    }
    return undefined
  }

  travel (func, inparam, outparam) {
    func(this, inparam, outparam)
    for (let child of this.children) {
      child.travel(func, inparam, outparam)
    }
  }

  g_travel () {
    function* g_travelfunc (node) {
      yield node
      for (let child of node.children) {
        for (let func of g_travelfunc(child)) {
          yield func
        }
      }
    }
    return g_travelfunc(this)
  }
}
