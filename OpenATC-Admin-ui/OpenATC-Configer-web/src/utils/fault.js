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
import i18n from '@/i18n/index.js'
import {faultCodeMap,
  faultCodeMapEn,
  TZParamSubtypeMap,
  TZParamSubtypeMapEn,
  greenLampSubtypeMap,
  greenLampSubtypeMapEn,
  lampPowerSubtypeMap,
  lampPowerSubtypeMapEn,
  LampGroupSubtypeMap,
  LampGroupSubtypeMapEn
} from './faultcode.js'

export function getFaultType (type) {
  if (type >= 101 && type <= 199) {
    return i18n.t('edge.faultrecord.maincontrolboardfault')
  } else if (type >= 201 && type <= 299) {
    return i18n.t('edge.faultrecord.lightcontrolversionfault')
  } else if (type >= 301 && type <= 399) {
    return i18n.t('edge.faultrecord.carinspectionboardfault')
  } else if (type >= 401 && type <= 499) {
    return i18n.t('edge.faultrecord.ioboardfault')
  } else {
    return undefined
  }
}

export function getMainFaultType (type) {
  return faultCodeMap.get(type)
}

export function getMainFaultTypeEn (type) {
  return faultCodeMapEn.get(type)
}

export function getAllMainFaultTypeArr (keyfield, valuefield) {
  let typeArr = []
  let label = 'label'
  let val = 'value'
  if (keyfield && keyfield !== '') {
    label = keyfield
  }
  if (valuefield && valuefield !== '') {
    val = valuefield
  }
  if (i18n.locale === 'en') {
    for (let [key, value] of faultCodeMapEn.entries()) {
      typeArr.push({
        [label]: key,
        [val]: value
      })
    }
  } else {
    for (let [key, value] of faultCodeMap) {
      typeArr.push({
        [label]: key,
        [val]: value
      })
    }
  }
  return typeArr
}
export function getBoardType () {
  let BoardType = [{
    label: i18n.t('edge.fault.tab1'),
    value: 1
  }, {
    label: i18n.t('edge.fault.tab2'),
    value: 2
  }, {
    label: i18n.t('edge.fault.tab3'),
    value: 3
  }, {
    label: i18n.t('edge.fault.tab4'),
    value: 4
  }, {
    label: i18n.t('edge.fault.tab5'),
    value: 5
  }, {
    label: i18n.t('edge.fault.tab6'),
    value: 6
  }]
  return BoardType
}
export function formatBoardType (dev) {
  let boardType = dev.m_byFaultBoardType
  let res = ''
  if (boardType === 1) {
    res = i18n.t('edge.faultrecord.maincontrolboard')
  } else if (boardType === 2) {
    res = i18n.t('edge.faultrecord.lightcontrolversion')
  } else if (boardType === 3) {
    res = i18n.t('edge.faultrecord.carinspectionboard')
  } else if (boardType === 4) {
    res = i18n.t('edge.faultrecord.ioboard')
  } else if (boardType === 5) {
    res = i18n.t('edge.faultrecord.faultboard')
  } else if (boardType === 6) {
    res = i18n.t('edge.faultrecord.characteristicparams')
  }
  return res
}
export function formatFaultDescValue (dev) {
  let res = ''
  let faultDesc = dev.m_byFaultDescValue
  let boardType = dev.m_byFaultBoardType
  if (faultDesc && faultDesc.length) {
    res = faultDesc.join(',')
    if (boardType === 2) {
      res = i18n.t('edge.faultrecord.channel') + res
    } else if (boardType === 3) {
      res = i18n.t('edge.faultrecord.detector') + res
    } else if (boardType === 4) {
      res = i18n.t('edge.faultrecord.port') + res
    }
  }
  return res
}
export function formatEnumerate (dev) {
  let enumerate = dev.enumerate
  let res = ''
  if (enumerate === '0') {
    res = i18n.t('edge.faultrecord.untreated')// 未处理
  } else if (enumerate === '1') {
    res = i18n.t('edge.faultrecord.ignored')// 忽略
  } else if (enumerate === '2') {
    res = i18n.t('edge.faultrecord.confirmed')// 确认
  }
  return res
}
export function formatSubFaultType (dev) {
  let wFaultType = dev.m_wFaultType
  let wSubFaultType = dev.m_wSubFaultType
  let res = ''
  if (i18n.locale === 'en') {
    if (wFaultType === 601) {
      res = TZParamSubtypeMapEn.get(wSubFaultType)
    } else if (wFaultType === 208 || wFaultType === 207 || wFaultType === 206) {
      res = greenLampSubtypeMapEn.get(wSubFaultType)
    } else if (wFaultType === 211 || wFaultType === 210 || wFaultType === 209) {
      res = lampPowerSubtypeMapEn.get(wSubFaultType)
    } else if (wFaultType === 212) {
      res = LampGroupSubtypeMapEn.get(wSubFaultType)
    } else {
      res = ''
    }
  } else {
    if (wFaultType === 601) {
      res = TZParamSubtypeMap.get(wSubFaultType)
    } else if (wFaultType === 208 || wFaultType === 207 || wFaultType === 206) {
      res = greenLampSubtypeMap.get(wSubFaultType)
    } else if (wFaultType === 211 || wFaultType === 210 || wFaultType === 209) {
      res = lampPowerSubtypeMap.get(wSubFaultType)
    } else if (wFaultType === 212) {
      res = LampGroupSubtypeMap.get(wSubFaultType)
    } else {
      res = ''
    }
  }
  return res
}

export function formatStatus (dev) {
  let wSubFaultType = dev.status
  let res = ''
  if (wSubFaultType === 0) {
    res = i18n.t('edge.dailyrecord.fault')
  } else if (wSubFaultType === 1) {
    res = i18n.t('edge.dailyrecord.success')
  }
  return res
}
export function formatSubject (dev) {
  let wSubFaultType = dev.subject
  let res = ''
  if (wSubFaultType === 0) {
    res = i18n.t('edge.dailyrecord.platform')
  } else if (wSubFaultType === 1) {
    res = i18n.t('edge.dailyrecord.configurationsoftware')
  } else if (wSubFaultType === 2) {
    res = i18n.t('edge.dailyrecord.getmanualpanel')
  }
  return res
}
export function formatObject (dev) {
  let subject = dev.object
  let res = ''
  if (subject === 1) {
    res = i18n.t('edge.dailyrecord.signal')
  }
  return res
}
export function formatInfotype (dev) {
  let infotype = dev.infotype
  let res = ''
  if (infotype === 0) {
    res = i18n.t('edge.dailyrecord.localpanelcontrol')
  } else if (infotype === 1) {
    res = i18n.t('edge.dailyrecord.systemmanualcontrol')
  } else if (infotype === 2) {
    res = i18n.t('edge.dailyrecord.systemdownloadcharacteristic')
  } else if (infotype === 3) {
    res = i18n.t('edge.dailyrecord.systemdownload')
  } else if (infotype === 4) {
    res = i18n.t('edge.dailyrecord.systemuploadparameters')
  } else if (infotype === 5) {
    res = i18n.t('edge.dailyrecord.systemrestartsignal')
  } else if (infotype === 6) {
    res = i18n.t('edge.dailyrecord.systemdownloadplan')
  } else if (infotype === 7) {
    res = i18n.t('edge.dailyrecord.systemuploadloadplan')
  } else if (infotype === 8) {
    res = i18n.t('edge.dailyrecord.systemdownloadschedulingplan')
  } else if (infotype === 9) {
    res = i18n.t('edge.dailyrecord.systemuploadloadschedulingplan')
  } else if (infotype === 10) {
    res = i18n.t('edge.dailyrecord.systemdownloaddate')
  } else if (infotype === 11) {
    res = i18n.t('edge.dailyrecord.systemuploadloaddate')
  } else if (infotype === 12) {
    res = i18n.t('edge.dailyrecord.systemchanneldetection')
  } else if (infotype === 13) {
    res = i18n.t('edge.dailyrecord.systemsolutionintervention')
  } else if (infotype === 14) {
    res = i18n.t('edge.dailyrecord.systemsetuptime')
  }
  return res
}
export function formatFaultLevel (dev) {
  let byFaultLevel = dev.m_byFaultLevel
  let res = ''
  if (byFaultLevel === 1) {
    res = i18n.t('edge.faultrecord.general')
  } else if (byFaultLevel === 2) {
    res = i18n.t('edge.faultrecord.degradation')
  } else if (byFaultLevel === 3) {
    res = i18n.t('edge.faultrecord.serious')
  }
  return res
}
export function formatFaultTypes (dev, column) {
  // 故障主类型需要显示具体类型，不要按范围判断
  let faultType = dev.m_wFaultType
  let res = ''
  if (i18n.locale === 'en') {
    res = getMainFaultTypeEn(faultType)
  } else {
    res = getMainFaultType(faultType)
  }
  return res
}
