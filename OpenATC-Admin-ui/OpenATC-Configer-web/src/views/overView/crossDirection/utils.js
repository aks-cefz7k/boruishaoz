
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
import store from '../../../store'
export default class PhaseDataModel {
  constructor () {
    this.PhasePosMap = new Map()
    this.SidePosMap = new Map()
    this.RampMainPosMap = new Map() // 匝道主路坐标
    this.RampSidePosMap = new Map() // 匝道支路坐标
    this.BusMapMap = new Map() // 公交相位底图坐标
    this.BusPhaseMap = new Map() // 公交相位坐标
    this.Init()
  }
  Init () {
    let phaseJson = require('./posJson/phasePos.json').phaseList
    let sideJson = require('./posJson/sidePos.json').sideList
    let rampJson = require('./posJson/rampPos.json')
    let busMapJson = require('./posJson/busPos.json').busMap
    let busPhaseJson = require('./posJson/busPos.json').busphases
    if (store.getters.roadDirection === 'left') {
      phaseJson = require('./posJson/phasePos.left.json').phaseList
      busMapJson = require('./posJson/busPos.left.json').busMap
      busPhaseJson = require('./posJson/busPos.left.json').busphases
    }
    this.handlePos(phaseJson, this.PhasePosMap)
    this.handlePos(sideJson, this.SidePosMap)
    this.handlePos(rampJson.rampMainPhaseList, this.RampMainPosMap)
    this.handlePos(rampJson.rampSidePhaseList, this.RampSidePosMap)
    this.handlePos(busMapJson, this.BusMapMap)
    this.handlePos(busPhaseJson, this.BusPhaseMap)
  }

  handlePos (phaseJson, phaseMap) {
    phaseJson.forEach(phase => {
      let value = {
        name: phase.name,
        x: phase.x,
        y: phase.y
      }
      phaseMap.set(phase.id, value)
    })
  }

  getPhase (id) {
    return this.PhasePosMap.get(id)
  }

  getSidePos (id) {
    return this.SidePosMap.get(id)
  }

  getMainPhasePos (id) {
    return this.RampMainPosMap.get(id)
  }

  getSidePhasePos (id) {
    return this.RampSidePosMap.get(id)
  }

  getBusMapPos (id) {
    return this.BusMapMap.get(id)
  }

  getBusPhasePos (id) {
    return this.BusPhaseMap.get(id)
  }
}
