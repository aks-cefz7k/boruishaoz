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
import store from '@/store'
// import { uploadTscParam } from '@/api/param'
import { getPhaseDesc } from '@/utils/phasedesc.js'
import { getPedPhaseDesc } from '@/utils/pedphasedesc.js'

const compareProperty = (property) => {
  return function (a, b) {
    var value1 = a[property]
    var value2 = b[property]
    return value1 - value2
  }
}

const typeOptions = [{
  value: 2,
  label: '机动车相位'
}, {
  value: 3,
  label: '行人相位'
}, {
  value: 4,
  label: '跟随相位'
}, {
  value: 5,
  label: '行人跟随相位'
}, {
  value: 6,
  label: '车道灯'
}, {
  value: 0,
  label: '不启用'
}]

const etypeOptions = [{
  value: 2,
  label: 'phaseVehicle'
}, {
  value: 3,
  label: 'phasePedestrian'
}, {
  value: 4,
  label: 'overlap'
}, {
  value: 5,
  label: 'overlapPedestrian'
}, {
  value: 6,
  label: 'laneLights'
}, {
  value: 0,
  label: 'enable'
}]

export function getControSource (lang, phases) {
  // 生成相位描述
  let controlSources = []
  let phaseList = store.getters.globalParamModel.getParamsByType('phaseList')
  if (phaseList === undefined && phases && phases.length) {
    phaseList = phases
  }
  for (let i = 0; i < phaseList.length; i++) {
    if (phaseList[i].controltype !== 99) {
      let pattern = {}
      var patternNum = phaseList[i].id
      var patternDescription
      let phaseDesc = phaseList[i].direction ? getPhaseDesc(phaseList[i].direction, lang) : ''
      let pedphaseDesc = phaseList[i].peddirection ? getPedPhaseDesc(phaseList[i].peddirection, lang) : ''
      if (phaseDesc === '' && pedphaseDesc === '') {
        patternDescription = patternNum
      } else {
        if (phaseDesc !== '' && pedphaseDesc !== '') {
          patternDescription = patternNum + '-' + phaseDesc + '--' + pedphaseDesc
        } else if (phaseDesc === '') {
          patternDescription = patternNum + '-' + pedphaseDesc
        } else {
          patternDescription = patternNum + '-' + phaseDesc
        }
      }
      pattern.value = patternNum
      pattern.label = patternDescription
      controlSources.push(pattern)
    }
  }
  return controlSources
}

export function getOverLap (lang, overlaps) {
  // 生成跟随相位描述
  let controlTypes = []
  let overlaplList = store.getters.globalParamModel.getParamsByType('overlaplList')
  if (overlaplList === undefined && overlaps && overlaps.length) {
    overlaplList = overlaps
  }
  for (let i = 0; i < overlaplList.length; i++) {
    let overlap = {}
    var overlapNum = overlaplList[i].id
    var overlapDescription
    let phaseDesc = overlaplList[i].direction ? getPhaseDesc(overlaplList[i].direction, lang) : ''
    let pedphaseDesc = overlaplList[i].peddirection ? getPedPhaseDesc(overlaplList[i].peddirection, lang) : ''
    if (phaseDesc === '' && pedphaseDesc === '') {
      overlapDescription = overlapNum
    } else {
      if (phaseDesc !== '' && pedphaseDesc !== '') {
        overlapDescription = overlapNum + '-' + phaseDesc + '--' + pedphaseDesc
      } else if (phaseDesc === '') {
        overlapDescription = overlapNum + '-' + pedphaseDesc
      } else {
        overlapDescription = overlapNum + '-' + phaseDesc
      }
    }
    overlap.value = overlapNum
    overlap.label = overlapDescription
    controlTypes.push(overlap)
  }
  return controlTypes
}

// refreshChannelLockDesc () {
//   // 根据通道描述，更新已配置通道锁定描述
//   let channellock = this.globalParamModel.getParamsByType('channellock')
//   channellock.forEach(lockdata => {
//     for (let [key, value] of this.channelDescMap.entries()) {
//       let ids = lockdata.channellocKinfo.map(ele => ele.channelid)
//       if (ids.indexOf(key) !== -1) {
//         lockdata.channellocKinfo.forEach(info => {
//           if (info.channelid === key) {
//             info.desc = value
//           }
//         })
//       } else {
//         let obj = {}
//         obj.channelid = key
//         obj.desc = value
//         obj.lockstatus = 0
//         lockdata.channellocKinfo.push(obj)
//       }
//     }
//     // 根据通道id重新排序
//     lockdata.channellocKinfo.sort(this.compareProperty('channelid'))
//   })
//   console.log(channellock)
// }

export function refreshChannelLockDescData () {
  // 根据通道描述，更新已配置通道锁定描述
  let channellock = store.getters.globalParamModel.getParamsByType('channellock')
  let channelDescMap = store.getters.globalParam.channelDescMap
  if (channellock === undefined) return
  channellock.forEach(lockdata => {
    let locklength = lockdata.channellocKinfo.length
    let channellength = channelDescMap.size
    if (locklength <= channellength) {
      for (let [key, value] of channelDescMap.entries()) {
        let ids = lockdata.channellocKinfo.map(ele => ele.channelid)
        if (ids.indexOf(key) !== -1) {
          lockdata.channellocKinfo.forEach(info => {
            if (info.channelid === key) {
              info.desc = value
            }
          })
        } else {
          let obj = {}
          obj.channelid = key
          obj.desc = value
          obj.lockstatus = 0
          lockdata.channellocKinfo.push(obj)
        }
      }
    } else {
      if (channellength === 0) {
        // 全部通道删除
        lockdata.channellocKinfo = []
      } else {
        // 部分通道删除
        lockdata.channellocKinfo.forEach((info, index) => {
          if (channelDescMap.get(info.channelid) === undefined) {
            lockdata.channellocKinfo.splice(index, 1)
          }
        })
      }
    }
    // 根据通道id重新排序
    lockdata.channellocKinfo.sort(compareProperty('channelid'))
  })
  console.log(channellock)
}

export function refreshControlPanelDescData () {
  // 根据通道描述，更新已配置手动面板描述
  console.log(store.getters.globalParamModel.getParamsByType('manualpanel'))
  let manualpanel = store.getters.globalParamModel.getParamsByType('manualpanel').keyconfig
  let channelDescMap = store.getters.globalParam.channelDescMap
  if (manualpanel === undefined) return
  manualpanel.forEach(manualdata => {
    let manuallength = manualdata.channel.length
    let channellength = channelDescMap.size
    if (manuallength <= channellength) {
      // 通道增加或上载
      for (let [key, value] of channelDescMap.entries()) {
        let ids = manualdata.channel.map(ele => ele.channelid)
        if (ids.indexOf(key) !== -1) {
          manualdata.channel.forEach(info => {
            if (info.channelid === key) {
              info.desc = value
            }
          })
        } else {
          let obj = {}
          obj.channelid = key
          obj.desc = value
          obj.channelstatus = 1
          manualdata.channel.push(obj)
        }
      }
    } else {
      if (channellength === 0) {
        // 全部通道删除
        manualdata.channel = []
      } else {
        // 部分通道删除
        manualdata.channel.forEach((info, index) => {
          if (channelDescMap.get(info.channelid) === undefined) {
            manualdata.channel.splice(index, 1)
          }
        })
      }
    }
    // 根据通道id重新排序
    manualdata.channel.sort(compareProperty('channelid'))
  })
  console.log(manualpanel)
}

export function getTypeOptions () {
  return typeOptions
}
export function getEtypeOptions () {
  return etypeOptions
}
