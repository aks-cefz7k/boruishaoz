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

const faultCodeMap = new Map([
  [101, 'can总线通信故障'],
  [102, '黄闪器故障'],
  [103, '特征参数故障'],
  [104, '故障检测板不在线'],
  [105, '继电器未吸合'],
  [201, '灯控板ID故障'],
  [202, '灯控板脱机'],
  [203, '无红灯亮起'],
  [204, '红绿同亮'],
  [205, '绿冲突'],
  [206, '红灯灯电压故障'],
  [207, '黄灯灯电压故障'],
  [208, '绿灯灯电压故障'],
  [209, '红灯灯功率故障'],
  [210, '黄灯灯功率故障'],
  [211, '绿灯灯功率故障'],
  [212, '灯组故障'],
  [213, '车检器故障'],
  [214, '灯控板插槽编码错误'],
  [215, '灯控板插头编码错误'],
  [216, '本机灯控板数量未配置'],
  [301, '车检板未初始化'],
  [302, '车检板脱机'],
  [303, '车辆检测器短路'],
  [304, '车辆检测器断路'],
  [401, 'I/O板未初始化'],
  [402, 'I/O板脱机']
])

const faultCodeMapEn = new Map([
  [101, 'CanBus Fault'],
  [102, 'Yellow Flasher Fault'],
  [103, 'TZParam Fault'],
  [104, 'FaultDet Offline'],
  [105, 'Relay Not Work'],
  [201, 'LampBoard ID Fault'],
  [202, 'LampBoard Offline'],
  [203, 'No Red Lamp Is On'],
  [204, 'Red And Green Conflict'],
  [205, 'Green Conflict'],
  [206, 'Red Lamp Voltage Fault'],
  [207, 'Yellow Lamp Voltage Fault'],
  [208, 'Green Lamp Voltage Fault'],
  [209, 'Red Lamp Lamp Power Fault'],
  [210, 'Yellow Lamp Lamp Power Fault'],
  [211, 'Green Lamp Lamp Power Fault'],
  [212, 'Lamp pack failure'],
  [213, 'Car detector failure'],
  [214, 'Lamp Control Board Slot Code Error'],
  [215, 'Code Error Of Lamp Control Board Plug'],
  [216, 'The Number Of Lamp Control Board Not be Configed for The Master'],
  [301, 'VehDetBoard Is Not Init'],
  [302, 'VehDetBoard Is Offline'],
  [303, 'VehDetector Short Circiut'],
  [304, 'VehDetector Open  Circiut'],
  [401, 'I/O Board Is Not Init'],
  [402, 'I/O Board Offline']
])
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
