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
    return '主控板故障'
  } else if (type >= 201 && type <= 299) {
    return '灯控版故障'
  } else if (type >= 301 && type <= 399) {
    return '车检板故障'
  } else if (type >= 401 && type <= 499) {
    return 'I/O板故障'
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
