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
export function getMainFaultType (type) {
  return faultCodeMap.get(type)
}