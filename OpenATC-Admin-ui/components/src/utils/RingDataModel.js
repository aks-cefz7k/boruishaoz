/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use i18n software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
import i18n from '../i18n/index.js'
import PhaseDataModel from '../kisscomps/components/IntersectionMap/crossDirection/utils.js'
import CrossDiagramMgr from '../EdgeMgr/controller/crossDiagramMgr.js'
export default class RingDataModel {
  constructor (crossStatusData, phaseList) {
    this.crossStatusData = crossStatusData
    this.phaseList = phaseList
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
  }

  initRingPhaseData () {
    // 环信息从单独上载相位信息里获取，以免相位锁定后，方案状态数据里没有rings，导致相位锁定控制列表无法显示
    let phaseRings = []
    let map = {}
    let dest = []
    for (let i = 0; i < this.phaseList.length; i++) {
      let ai = this.phaseList[i]
      if (!map[ai.ring]) {
        let addphse = this.addPhaseInfo(ai)
        dest.push({
          num: ai.ring,
          phases: [{...ai, ...addphse}]
        })
        map[ai.ring] = ai
      } else {
        for (var j = 0; j < dest.length; j++) {
          var dj = dest[j]
          if (dj.num === ai.ring) {
            let addphse = this.addPhaseInfo(ai)
            dj.phases.push({...ai, ...addphse})
            break
          }
        }
      }
    }
    phaseRings = JSON.parse(JSON.stringify(dest))
    return phaseRings
  }

  addPhaseInfo (phase) {
    let addphse = {}
    addphse.name = i18n.t('openatccomponents.overview.phase') + phase.id
    addphse.desc = this.getPhaseDescription(phase.direction)
    // 相位锁定选项默认都按照解锁状态显示
    addphse.locktype = 0
    addphse.close = 0
    if (this.crossStatusData !== null && this.crossStatusData.phase) {
      // 如果方案状态相位有close字段，这边就需要对应close状态进相位关断控制的选项里
      let phaseStatus = this.crossStatusData.phase.filter(ele => ele.id === phase.id)[0]
      addphse = {...addphse, ...phaseStatus}
    }
    return addphse
  }

  getPhaseDescription (phaseList) {
    let list = []
    for (let id of phaseList) {
      let obj = {}
      obj.id = id
      obj.color = '#454545'
      list.push(obj)
    }
    return list
  }

  getPedPhasePos () {
    // 行人相位信息
    this.sidewalkPhaseData = []
    this.phaseList.forEach((ele, i) => {
      if (ele.peddirection) {
        ele.peddirection.forEach((dir, index) => {
        // 行人相位
          if (this.PhaseDataModel.getSidePos(dir)) {
            this.sidewalkPhaseData.push({
              key: this.CrossDiagramMgr.getUniqueKey('pedphase'),
              phaseid: ele.id, // 相位id，用于对应相位状态
              id: dir,
              name: this.PhaseDataModel.getSidePos(dir).name
            })
          }
        })
      }
    })
    return this.sidewalkPhaseData
  }
}
