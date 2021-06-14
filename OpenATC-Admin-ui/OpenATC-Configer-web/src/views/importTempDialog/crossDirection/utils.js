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
export default class PhaseDataModel {
  constructor () {
    this.PhasePosMap = new Map()
    this.Init()
  }
  Init () {
    const phaseJson = require('./phasePos.json')
    phaseJson.phaseList.forEach(phase => {
      let value = {
        name: phase.name,
        x: phase.x,
        y: phase.y
      }
      this.PhasePosMap.set(phase.id, value)
    })
  }

  getPhase (id) {
    return this.PhasePosMap.get(id)
  }
}
