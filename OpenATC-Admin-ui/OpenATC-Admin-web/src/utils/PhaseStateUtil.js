
export function getPhaseControlName (type) {
  if (type === 0) {
    return '自主'
  } else if (type === 1) {
    return '黄闪'
  } else if (type === 2) {
    return '全红'
  } else if (type === 3) {
    return '关灯'
  } else if (type === 4) {
    return '步进'
  } else if (type === 5) {
    return '定周期'
  } else if (type === 6) {
    return '单点感应'
  } else if (type === 7) {
    return '协调感应'
  } else if (type === 8) {
    return '方案选择'
  } else if (type === 9) {
    return '自适应'
  } else if (type === 10) {
    return '无电缆'
  } else if (type === 11) {
    return '有缆线'
  } else if (type === 12) {
    return '行人过街'
  } else if (type === 13) {
    return '通道锁定'
  } else if (type === 14) {
    return '相位锁定'
  } else if (type === 100) {
    return '方案干预'
  } else if (type === 101) {
    return '通道检测'
  } else {
    return 'NULL'
  }
}
export function getControlModelColor (type) {
  if (type === 1) {
    return '#5084cf'
  } else if (type === 2) {
    return '#45ab89'
  } else if (type === 3) {
    return '#537896'
  } else if (type === 4) {
    return '#c6bf72'
  } else if (type === 5) {
    return '#d48265'
  } else {
    return '#537896'
  }
}
