let errorCodeMap = new Map([
  [101, '相位编号超出限值'],
  [102, '行人绿闪时间超出限值'],
  [103, '最小绿应大于行人清空时间'],
  [104, '最大绿1应大于最小绿时间'],
  [105, '最大绿2应大于最大绿1时间'],
  [106, '单位延长绿灯时间超出限值'],
  [107, '黄灯时间超出限值'],
  [108, '全红时间超出限值'],
  [109, '绿闪时间应小于最小绿'],
  [110, '环数量超出限值'],
  [111, '相位并发配置冲突'],
  [112, '所有环不能同时配一个相位'],
  [113, '环索引应从1开始配置'],
  [114, '环索引应连续配置'],
  [201, '跟随相位数量超出限值'],
  [202, '跟随相位的母相位为空'],
  [203, '跟随相位配置未知母相位'],
  [301, '方案数量超出限值'],
  [302, '相位差应小于周期时间'],
  [303, '环内配置未知相位'],
  [307, '方案中阶段时长应大于相位过渡灯色'],
  [401, '计划数量超出限值'],
  [402, '控制方式不存在'],
  [403, '时段数量超出限值'],
  [404, '分钟超出限值'],
  [405, '小时超出限值'],
  [406, '时间顺序配置错误'],
  [407, '计划中配置未知方案'],
  [408, '计划中方案未配置'],
  [409, '存在未配置的计划'],
  [501, '调度计划数量超出限值'],
  [502, '月份超出限值'],
  [503, '星期超出限值'],
  [504, '日期值超出限值'],
  [505, '配置未知计划号'],
  [601, '通道数超出限值'],
  [602, '通道配置未知控制源'],
  [603, '通道控制源未配置'],
  [604, '通道类型未配置'],
  [605, '通道未知控制类型'],
  [701, '车辆检测器数量超出限值'],
  [702, '车辆检测器无响应时间超出限值'],
  [703, '车辆检测器最大持续时间超出限值'],
  [704, '车辆检测器最大车辆数超出限值'],
  [705, '车辆检测器失败时间超出限值'],
  [706, '车辆检测器配置未知请求相位'],
  [801, '行人检测器数量超出限值'],
  [802, '行人检测器无响应时间超出限值'],
  [803, '行人检测器最大持续时间超出限值'],
  [804, '行人检测器最大车辆数超出限值'],
  [805, '行人检测器失败时间超出限值'],
  [806, '行人检测器配置未知请求相位'],
  [901, '手动面板配置未知通道'],
  [902, '手动面板参数未配置'],
  [903, '手动面板东西直行按键通道绿冲突'],
  [904, '手动面板北向通行按键通道绿冲突'],
  [905, '手动面板东西左转按键通道绿冲突'],
  [906, '手动面板西向通行按键通道绿冲突'],
  [907, '手动面板东向通行按键通道绿冲突'],
  [908, '手动面板南北直行按键通道绿冲突'],
  [909, '手动面板南向通行按键通道绿冲突'],
  [910, '手动面板南北左转按键通道绿冲突'],
  [911, '手动面板Y1自定义按键通道绿冲突'],
  [912, '手动面板Y2自定义按键通道绿冲突'],
  [913, '手动面板Y3自定义按键通道绿冲突'],
  [914, '手动面板Y4自定义按键通道绿冲突'],
  [915, '手动面板参数持续时间应不小于最小绿时间'],
  [1003, '通道锁定时段数量超出限值'],
  [1101, 'MD5码值校验失败'],
  [1102, '特征参数同步失败'],
  [1103, '参数JSON格式解析错误'],
  [1104, '未支持的参数类型'],
  [1201, '权重因子值域应为[0, 100]'],
  [1202, '环相位权重因子和不为100'],
  [1203, '相位权重因子未配置'],
  [2001, '地址码未配置'],
  [2002, '信号机两个网卡都未配置'],
  [2003, '信号机地址码配置错误'],
  [2004, '参数JSON格式解析错误'],
  [2005, '设备参数同步失败'],
  [2006, '信号机配置非法网卡信息'],
  [2007, '设备ID不在0-65535之间'],
  [3001, 'U盘挂载失败'],
  [3002, '未找到U盘']
])

let errorCodeMapEn = new Map([
  [101, 'Phase number exceeds limit'],
  [102, 'The time of pedestrian flashing green exceeds the limit'],
  [103, 'The minimum green time should be longer than the pedestrian clear time'],
  [104, 'The maximum green 1 should be longer than the minimum green'],
  [105, 'Maximum green 2 should be greater than maximum green 1'],
  [106, 'Unit extended green time out of limit'],
  [107, 'The yellow light time exceeds the limit'],
  [108, 'The all red time exceeds limit'],
  [109, 'The time of flash green should be less than the minimum green'],
  [110, 'Number of rings exceeds limit'],
  [111, 'Phase concurrency configuration conflict'],
  [112, 'All rings cannot be equipped with only one phase'],
  [113, 'The ring index should be configured from 1'],
  [114, 'The ring index shall be configured continuously'],
  [201, 'The number of following phases exceeds the limit'],
  [202, 'The mother phase following the phase is null'],
  [203, 'Follow the phase configuration with an agnostic phase'],
  [301, 'The number of schemes exceeds the limit'],
  [302, 'The phase difference should be less than the cycle time'],
  [303, 'Unknown phase configuration in the ring'],
  [307, 'The phase duration in the scheme shall be greater than the phase transition lamp color'],
  [401, 'The number of plans exceeds the limit'],
  [402, 'Control mode does not exist'],
  [403, 'The periods number exceeds the limit'],
  [404, 'The minute exceeds limit'],
  [405, 'The hour exceeds limit'],
  [406, 'Time sequence configuration error'],
  [407, 'Unknown scheme is configured in the plan'],
  [408, 'The plan is not configured'],
  [409, 'Exist an unconfigured plan'],
  [501, 'The number of scheduling plans exceeds the limit'],
  [502, 'The month exceeds the limit'],
  [503, 'The week exceeds the limit'],
  [504, 'The date value exceeds the limit'],
  [505, 'Configure unknown plan number'],
  [601, 'The number of channels exceeds the limit'],
  [602, 'Channel configuration unknown control source'],
  [603, 'Channel control source is not configured'],
  [604, 'Channel type not configured'],
  [605, 'Channel unknown control type'],
  [701, 'The number of vehicle detectors exceeds the limit'],
  [702, 'The non-response time of the vehicle detector exceeds the limit'],
  [703, 'The maximum duration of the vehicle detector exceeds the limit'],
  [704, 'The maximum number of vehicles in the vehicle detector exceeds the limit'],
  [705, 'The vehicle detector failure time exceeds the limit'],
  [706, 'Vehicle detector configuration unknown request phase'],
  [801, 'The number of pedestrian detectors exceeds the limit'],
  [802, 'The non-response time of the pedestrian detector exceeds the limit'],
  [803, 'The maximum duration of the pedestrian detector exceeds the limit'],
  [804, 'The maximum number of vehicles in the pedestrian detector exceeds the limit'],
  [805, 'Pedestrian detector failure time exceeds the limit'],
  [806, 'Pedestrian detector configuration unknown request phase'],
  [901, 'Manual panel configuration unknown channel'],
  [902, 'Manual panel parameters are not configured'],
  [903, 'Manual panel east-west straight button channel green conflict'],
  [904, 'Manual panel northbound traffic button channel green conflict'],
  [905, 'Manual panel east-west turn left button channel green conflict'],
  [906, 'Manual panel westbound button channel green conflict'],
  [907, 'Manual panel eastbound button channel green conflict'],
  [908, 'Manual panel north-south straight button channel green conflict'],
  [909, 'Manual panel southbound key channel green conflict'],
  [910, 'Manual panel north-south turn left button channel green conflict'],
  [911, 'Manual panel Y1 custom button channel green conflict'],
  [912, 'Manual panel Y2 custom button channel green conflict'],
  [913, 'Manual panel Y3 custom button channel green conflict'],
  [914, 'Manual panel Y4 custom button channel green conflic'],
  [915, 'Duration of Manual panel parameters duration shall not be less than the minimum green'],
  [1003, 'The number of channel lock periods exceeds the limit'],
  [1101, 'MD5 code value verification failed'],
  [1102, 'Feature parameters synchronization failed'],
  [1103, 'Parsing error of parameters JSON format'],
  [1104, 'Unsupported parameter type'],
  [1201, 'Static weight range should be[0, 100]'],
  [1202, 'The sum of ring phase static weight is not 100'],
  [1203, 'Phase static weight factor not configured'],
  [2001, 'Address code is not configured'],
  [2002, 'The signal is not configured with either network card'],
  [2003, 'Address code configuration error of signal machine'],
  [2004, 'Parsing error of parameters JSON format'],
  [2005, 'Device parameters synchronization failed'],
  [2006, 'Signal configuration illegal network card information'],
  [2007, 'Intersection ID is not between 0-65535'],
  [3001, 'USB disk mount failed'],
  [3002, 'USB disk not found']
])

function getErrorMesEn (errorMes, code) {
  let errorstr = errorMes
  if (code[0] === 305) {
    errorstr = errorstr + '</br>' + 'There is an intra-ring phase concurrency conflict in scheme' + code[1]
  } else if (code[0] === 304) {
    errorstr = errorstr + '</br>' + 'The green splits should be longer than the minimum phase green + yellow light + all red, And less than the maximum green 1 + yellow light + all red in scheme' + code[1]
  } else if (code[0] === 306) {
    errorstr = errorstr + '</br>' + 'The cycle time of each ring is inconsistent in scheme' + code[1]
  } else if (code[0] === 1001) {
    errorstr = errorstr + '</br>' + 'Time period' + code[1] + 'channel lock state conflict'
  } else if (code[0] === 1002) {
    errorstr = errorstr + '</br>' + 'The control source of the locked channel in period' + code[1] + ' is not ignored'
  } else if (code[0] === 1004) {
    errorstr = errorstr + '</br>' + 'Time period' + code[1] + 'Channels other than Lane lights are configured with channel lock status'
  } else if (errorCodeMapEn.get(code[0])) {
    errorstr = errorstr + '</br>' + errorCodeMapEn.get(code[0])
  } else {
    errorstr = errorstr + '</br>' + `Error code ${code[0]}`
  }
  return errorstr
}

function getErrorMesZh (errorMes, code) {
  let errorstr = errorMes
  if (code[0] === 305) {
    errorstr = errorstr + '</br>' + '方案' + code[1] + '中存在环内相位并发冲突'
  } else if (code[0] === 304) {
    errorstr = errorstr + '</br>' + '方案' + code[1] + '中绿信比应大于相位的最小绿+黄灯+全红，并小于最大绿1+黄灯+全红'
  } else if (code[0] === 306) {
    errorstr = errorstr + '</br>' + '方案' + code[1] + '中存在各个环周期时长不一致'
  } else if (code[0] === 1001) {
    errorstr = errorstr + '</br>' + '时段' + code[1] + '通道状态锁定冲突'
  } else if (code[0] === 1002) {
    errorstr = errorstr + '</br>' + '时段' + code[1] + '锁定通道的控制源未被忽略'
  } else if (code[0] === 1004) {
    errorstr = errorstr + '</br>' + '时段' + code[1] + '车道灯以外的通道配置了通道锁定状态'
  } else if (errorCodeMap.get(code[0])) {
    errorstr = errorstr + '</br>' + errorCodeMap.get(code[0])
  } else {
    errorstr = errorstr + '</br>' + `错误码 ${code[0]}`
  }
  return errorstr
}

export {
  errorCodeMap,
  errorCodeMapEn,
  getErrorMesEn,
  getErrorMesZh
}
