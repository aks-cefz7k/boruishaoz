let faultCodeMap = new Map([
  [101, 'can总线通信故障'],
  [102, '黄闪器故障'],
  [103, '继电器未吸合'],
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
  [213, '检测器故障'],
  [214, '灯控板插槽编码错误'],
  [215, '灯控板插头编码错误'],
  [216, '本机灯控板数量未配置'],
  [301, '车检板未初始化'],
  [302, '车检板脱机'],
  [303, '车辆检测器短路'],
  [304, '车辆检测器断路'],
  [401, 'I/O板未初始化'],
  [402, 'I/O板脱机'],
  [501, '故障板脱机'],
  [601, '特征参数故障'],
  [602, '设备参数异常']
])

let faultCodeMapEn = new Map([
  [101, 'CanBus Fault'],
  [102, 'Yellow Flasher Fault'],
  [103, 'Relay Not Work'],
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
  [402, 'I/O Board Offline'],
  [501, 'Fault Det Board'],
  [601, 'Fault TZParam'],
  [602, 'Fault HWParam']
])

const TZParamSubtypeMap = new Map([
  [0, ''],
  [1, '特征参数文件不存在'],
  [2, '特征参数文件不可读'],
  [3, '特征参数人为修改'],
  [4, '特征参数文件打开失败'],
  [5, '特征参数文件更新失败'],
  [6, '信号机地址码校验失败'],
  [7, '特征参数内容格式错误'],
  [8, 'USB挂载失败'],
  [9, 'U盘没找到'],
  [10, '设备参数异常']
])
const TZParamSubtypeMapEn = new Map([
  [0, ''],
  [1, 'Non-existent'],
  [2, 'File Is Unreadable'],
  [3, 'File Artificial Changes'],
  [4, 'File Open Fail'],
  [5, 'File Update Fail'],
  [6, 'File Check SiteID Fail'],
  [7, 'Format Error'],
  [8, 'USB Mount Fail'],
  [9, 'USB Not Find'],
  [10, 'HWParam Check Fault']
])
const greenLampSubtypeMap = new Map([
  [0, ''],
  [1, '未输出有效电压'],
  [2, '输出电压低于输入电压过多'],
  [3, '输出电压高于输入电压'],
  [4, '关闭输出但实际电压仍然输出'],
  [5, '关闭输出但实际电压部分输出'],
  [6, '线路残留电压过高']
])
const greenLampSubtypeMapEn = new Map([
  [0, ''],
  [1, 'Output Volatage Is Fail'],
  [2, 'Output Volatage Is Low'],
  [3, 'Output Volatage Is High'],
  [4, 'Off Output Volatage Is high'],
  [5, 'Off Output Volatage Is low'],
  [6, 'Residual Voltage Is Over-High']
])
const lampPowerSubtypeMap = new Map([
  [0, ''],
  [1, '功率异常增加'],
  [2, '功率异常减少'],
  [3, '功率无输出'],
  [4, '关闭状态有功率输出']
])
const lampPowerSubtypeMapEn = new Map([
  [0, ''],
  [1, 'Output Power Is Up'],
  [2, 'Output Power Is Down'],
  [3, 'Output Power Is Zero'],
  [4, 'Off Output Power Is High']
])
const LampGroupSubtypeMap = new Map([
  [0, ''],
  [1, '红灯故障'],
  [2, '黄灯故障'],
  [3, '绿灯故障']
])
const LampGroupSubtypeMapEn = new Map([
  [0, ''],
  [1, 'Red Lamp Fault'],
  [2, 'Yellow Lamp Fault'],
  [3, 'Green Lamp Fault']
])
const faultLevelMap = new Map([
  [1, '一般故障'],
  [2, '降级故障'],
  [3, '严重故障']
])
const faultLevelMapEn = new Map([
  [1, 'General failure'],
  [2, 'Degradation failure'],
  [3, 'Serious failure']
])

function getFaultMesEn (codeList) {
  let faultArr = []
  for (let data of codeList) {
    let strArr = []
    if (data[0] === 103) {
      strArr[1] = TZParamSubtypeMapEn.get(data[1])
    } else if (data[0] === 208 || data[0] === 207 || data[0] === 206) {
      strArr[1] = greenLampSubtypeMapEn.get(data[1])
    } else if (data[0] === 211 || data[0] === 210 || data[0] === 209) {
      strArr[1] = lampPowerSubtypeMapEn.get(data[1])
    } else if (data[0] === 212) {
      strArr[1] = LampGroupSubtypeMapEn.get(data[1])
    } else {
      strArr[1] = ''
    }
    strArr[0] = faultCodeMapEn.get(data[0])
    if (data[1] !== 0) {
      faultArr.push(`${strArr[0]}--${strArr[1]}`)
    } else {
      faultArr.push(`${strArr[0]}`)
    }
  }
  return faultArr
}

function getFaultMesZh (codeList) {
  let faultArr = []
  for (let data of codeList) {
    let strArr = []
    if (data[0] === 103) {
      strArr[1] = TZParamSubtypeMap.get(data[1])
    } else if (data[0] === 208 || data[0] === 207 || data[0] === 206) {
      strArr[1] = greenLampSubtypeMap.get(data[1])
    } else if (data[0] === 211 || data[0] === 210 || data[0] === 209) {
      strArr[1] = lampPowerSubtypeMap.get(data[1])
    } else if (data[0] === 212) {
      strArr[1] = LampGroupSubtypeMap.get(data[1])
    } else {
      strArr[1] = ''
    }
    strArr[0] = faultCodeMap.get(data[0])
    if (data[1] !== 0) {
      faultArr.push(`${strArr[0]}--${strArr[1]}`)
    } else {
      faultArr.push(`${strArr[0]}`)
    }
  }
  return faultArr
}

export {
  faultCodeMap,
  faultCodeMapEn,
  getFaultMesEn,
  getFaultMesZh,
  faultLevelMap,
  faultLevelMapEn,
  TZParamSubtypeMap,
  TZParamSubtypeMapEn,
  greenLampSubtypeMap,
  greenLampSubtypeMapEn,
  lampPowerSubtypeMap,
  lampPowerSubtypeMapEn,
  LampGroupSubtypeMap,
  LampGroupSubtypeMapEn
}
