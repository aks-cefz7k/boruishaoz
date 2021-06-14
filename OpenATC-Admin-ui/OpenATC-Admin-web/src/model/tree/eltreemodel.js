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
/* eslint-disable new-cap */
import {
  treeNode
} from './treeBase'

export class elTreeModel extends treeNode {
  constructor (id, name, expand = true, checked = 0, parent = undefined, description, code) {
    super(id, name, expand, checked, parent, description, code)
    this.label = name
  }
  static GetNewRootNode (description) {
    return new elTreeModel(1, 'all', false, 0, undefined, '*', '*')
  }

  static CreateTreeByPath (dataList) {
    let rootNode = this.GetNewRootNode()
    for (let data of dataList) {
      elTreeModel.RefreshNodeByPath(data.id, data.orgnization_code, rootNode.children, data.description)
    }
    let nodeList = []
    nodeList.push(rootNode)
    return nodeList
  }

  static RefreshNodeByPath (id, path, nodeList, description) {
    let nodeNameList = path.split(':')
    let curNodeList = nodeList
    let parent
    let curDescription = description
    for (let name of nodeNameList) {
      if (name === '*' || name === '') continue
      let curNode = curNodeList.find((node) => {
        return node.name === name
      })
      if (curNode === undefined) {
        curNode = new elTreeModel(name, name, false, 0, parent, curDescription)
        curNodeList.push(curNode)
      }
      curNodeList = curNode.children
      parent = curNode
    }
    if (parent !== undefined) {
      parent.id = id
      parent.code = path
    }
  }

  static GetTreeCheckLeafPathList (nodeList) {
    let pathList = []
    for (let node of nodeList) {
      pathList.push(elTreeModel.GetNodePath(node))
    }
    return pathList
  }

  static GetNodePath (node) {
    if (node === undefined) {
      return ''
    }
    let path = `${node.name}`
    let curNode = node.parent
    while (curNode !== undefined) {
      path = `${curNode.name}/${path}`
      curNode = curNode.parent
    }
    return path
  }
}
