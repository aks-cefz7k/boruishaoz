/**
 * 数组去重
 * @param {*} array
 */
export function kdistinct (array) {
  return Array.from(new Set(array))
}
/**
 * 求数组并集
 * @param {*} array1
 * @param {*} array2
 */
export function kconcat (array1, array2) {
  return array1.concat(array2.filter(v => !array1.includes(v)))
}

/**
 * 求交集
 */
export function kintersection (array1, array2) {
  return array1.filter(v => array2.includes(v))
}

// 差集
export function kdiff (array1, array2) {
  return array1.concat(array2).filter(v => array1.includes(v) && !array2.includes(v))
}
