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
import { getMainFaultType, getMainFaultTypeEn } from '@/model/EventModal/utils.js'
export const BoardType = [{
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
}]
export function formatBoardType (dev) {
  let boardType = dev.m_byFaultBoardType
  let res = ''
  if (boardType === 1) {
    res = i18n.t('openatc.faultrecord.maincontrolboard')
  } else if (boardType === 2) {
    res = i18n.t('openatc.faultrecord.lightcontrolversion')
  } else if (boardType === 3) {
    res = i18n.t('openatc.faultrecord.carinspectionboard')
  } else if (boardType === 4) {
    res = i18n.t('openatc.faultrecord.ioboard')
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
      res = i18n.t('openatc.faultrecord.channel') + res
    } else if (boardType === 3) {
      res = i18n.t('openatc.faultrecord.detector') + res
    } else if (boardType === 4) {
      res = i18n.t('openatc.faultrecord.port') + res
    }
  }
  return res
}
export function formatEnumerate (dev) {
  let enumerate = dev.enumerate
  let res = ''
  if (enumerate === '0') {
    res = i18n.t('openatc.faultrecord.untreated')// 未处理
  } else if (enumerate === '1') {
    res = i18n.t('openatc.faultrecord.ignored')// 忽略
  } else if (enumerate === '2') {
    res = i18n.t('openatc.faultrecord.confirmed')// 确认
  }
  return res
}
export function formatSubFaultType (dev) {
  let wSubFaultType = dev.m_wSubFaultType
  let res = ''
  if (wSubFaultType === 0) {
    res = ''
  } else if (wSubFaultType === 1) {
    res = i18n.t('openatc.faultrecord.powerup')
  } else if (wSubFaultType === 2) {
    res = i18n.t('openatc.faultrecord.powerdown')
  } else if (wSubFaultType === 3) {
    res = i18n.t('openatc.faultrecord.powerno')
  } else if (wSubFaultType === 4) {
    res = i18n.t('openatc.faultrecord.powerfault')
  }
  return res
}
export function formatFaultLevel (dev) {
  let byFaultLevel = dev.m_byFaultLevel
  let res = ''
  if (byFaultLevel === 1) {
    res = i18n.t('openatc.faultrecord.general')
  } else if (byFaultLevel === 2) {
    res = i18n.t('openatc.faultrecord.degradation')
  } else if (byFaultLevel === 3) {
    res = i18n.t('openatc.faultrecord.serious')
  }
  return res
}
export function formatFaultTypes (dev, column) {
  // 故障主类型需要显示具体类型，不要按范围判断
  let faultType = dev.m_wFaultType
  let res = ''
  // if (faultType >= 101 && faultType <= 199) {
  //   res = i18n.t('openatc.faultrecord.maincontrolboardfault')
  // } else if (faultType >= 201 && faultType <= 299) {
  //   res = i18n.t('openatc.faultrecord.lightcontrolversionfault')
  // } else if (faultType >= 301 && faultType <= 399) {
  //   res = i18n.t('openatc.faultrecord.carinspectionboardfault')
  // } else if (faultType >= 401 && faultType <= 499) {
  //   res = i18n.t('openatc.faultrecord.ioboardfault')
  // }
  if (i18n.locale === 'en') {
    res = getMainFaultTypeEn(faultType)
  } else {
    res = getMainFaultType(faultType)
  }
  return res
}
