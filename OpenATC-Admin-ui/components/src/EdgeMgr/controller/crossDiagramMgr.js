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
// 该类是路口图的业务逻辑
import EdgeModelBase from '../../EdgeMgr/EdgeModelBase'

export default class crossDiagramMgr extends EdgeModelBase {
  constructor () {
    super('CrossDiagramMgr')
  }

  compare (arr1, arr2, field) {
    // 对比数据算法：相同direction（peddirection），即同方向的情况下，需要综合考虑相位和跟随相位的状态。
    // 以相位数据为基准，如果跟随相位是绿灯，相位是绿闪或者黄灯或红灯，那就取跟随相位绿灯的状态。此状态是相对概念，比对存在优先级。
    // 取值优先级： 绿灯(3) > 绿闪(4) > 黄灯(2) > 红灯(1)
    if (!arr1.length && !arr2.length) return []
    if (arr1.length && !arr2.length) {
      return arr1
    }
    if (!arr1.length && arr2.length) {
      return arr2
    }
    let arr1Ids = arr1.map(ele => ele.id)
    let arr2Ids = arr2.map(ele => ele.id)
    let concatarr = []
    for (let i = 0; i < arr1.length; i++) {
      let obj = arr1[i]
      let num = obj.id
      for (let j = 0; j < arr2.length; j++) {
        let aj = arr2[j]
        let n = aj.id
        let laneobj = {}
        if (n === num) {
          // 相同方向，进行相位和跟随相位的比对算法
          laneobj = this.handlePhasePriority(obj, aj, field)
          concatarr.push(laneobj)
          continue
        }
        if (arr1Ids.indexOf(n) === -1) {
          // 有跟随相位，但是没有相位，则取跟随相位的状态数据
          let concatarrIds = concatarr.map(ele => ele.id)
          if (concatarrIds.indexOf(n) === -1) {
            concatarr.push(aj)
          }
        }
        if (arr2Ids.indexOf(num) === -1) {
          // 有相位，但是没有跟随相位，则取相位的状态数据
          let concatarrIds = concatarr.map(ele => ele.id)
          if (concatarrIds.indexOf(num) === -1) {
            concatarr.push(obj)
          }
        }
      }
    }
    return concatarr
  }
  handlePhasePriority (phase, overlap, field) {
    // 根据 绿灯(3) > 绿闪(4) > 黄灯(2) > 红灯(1)的优先级，比对相位、跟随相位，返回优先级高的数据
    let phasetype = phase[field]
    let overlaptype = overlap[field]
    if (phasetype === 3) {
      return phase
    } else if (overlaptype === 3) {
      return overlap
    } else if (phasetype === 4) {
      return phase
    } else if (overlaptype === 4) {
      return overlap
    } else if (phasetype === 2) {
      return phase
    } else if (overlaptype === 2) {
      return overlap
    } else if (phasetype === 1) {
      return phase
    } else if (overlaptype === 1) {
      return overlap
    } else {
      console.log('优先级无法判断')
    }
  }
  getUniqueKey (key) {
    // 生成唯一的key值，防止渲染报错
    let date = Date.now()
    let rund = Math.ceil(Math.random() * 1000)
    let id = key + date + '' + rund
    return id
  }

  compareRepeatDirection (data, field, from) {
    let ids = [] // 记录数据id（即direction），用于判断是否有重复的direction
    let uniqueLanePhaseData = []
    for (let i = 0; i < data.length; i++) {
      if (ids.indexOf(data[i].id) !== -1) {
        // 比较重复的direction下的状态
        let indexi
        let lastData = uniqueLanePhaseData.filter((ele, index) => {
          if (ele.id === data[i].id) {
            indexi = index
            return true
          }
          return false
        })[0]
        let curData = data[i]
        uniqueLanePhaseData[indexi] = this.compare([lastData], [curData], field)[0]
      } else {
        ids.push(data[i].id)
        uniqueLanePhaseData.push(data[i])
      }
    }
    return uniqueLanePhaseData
  }
}
