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

export function formatSubFaultType (dev) {
  let wSubFaultType = dev.m_wSubFaultType
  let res = ''
  if (wSubFaultType === 0) {
    res = ''
  } else if (wSubFaultType === 1) {
    res = i18n.t('edge.faultrecord.powerup')
  } else if (wSubFaultType === 2) {
    res = i18n.t('edge.faultrecord.powerdown')
  } else if (wSubFaultType === 3) {
    res = i18n.t('edge.faultrecord.powerno')
  } else if (wSubFaultType === 4) {
    res = i18n.t('edge.faultrecord.powerfault')
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
