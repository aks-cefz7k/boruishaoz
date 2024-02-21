
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
    this.Init()
  }
  Init () {
    console.log(store.getters.roadDirection)
    let phaseJson = require('./posJson/phasePos.json')
    let sideJson = require('./posJson/sidePos.json')
    let rampJson = require('./posJson/rampPos.json')
    if (store.getters.roadDirection === 'left') {
      phaseJson = require('./posJson/phasePos.left.json')
    }
    phaseJson.phaseList.forEach(phase => {
      let value = {
        name: phase.name,
        x: phase.x,
        y: phase.y
      }
      this.PhasePosMap.set(phase.id, value)
    })
    sideJson.sideList.forEach(side => {
      let value = {
        name: side.name,
        x: side.x,
        y: side.y
      }
      this.SidePosMap.set(side.id, value)
    })
    rampJson.rampMainPhaseList.forEach(phase => {
      let value = {
        name: phase.name,
        x: phase.x,
        y: phase.y
      }
      this.RampMainPosMap.set(phase.id, value)
    })
    rampJson.rampSidePhaseList.forEach(phase => {
      let value = {
        name: phase.name,
        x: phase.x,
        y: phase.y
      }
      this.RampSidePosMap.set(phase.id, value)
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
}
