// 全局数据
import store from '../../../../store/index'

const subKey = 'CrossState' /// <订阅的关键字
const typeList = [ /// <可订阅的数据类型
  'faulteventdata'
]
const typekeyMap = new Map([
  ['faulteventdata', 'status/fault']
])
export default class GlobalData {
  constructor () {
    this.initFuncMap()
  }

  initFuncMap () {
    this.funcMap = new Map()
    for (let type of typeList) {
      this.funcMap.set(type, [])
    }
  }

  /**
   * @param {in} type 可订阅类型中的一个
   * @param {in} params 订阅消息参数
   * @param {in} func 回调函数  func(message) 参数为订阅返回数据l
   */
  getData (type, func, params) {
    if (typeList.find(val => val === type) === undefined) return

    let funcList = this.funcMap.get(type)
    funcList.push(func)

    this.Run(type, params)

    return funcList.length - 1
  }

  stop (type, index, params) {
    if (typeList.find(val => val === type) === undefined) return

    let funcList = this.funcMap.get(type)
    if (index > funcList.length - 1) return

    funcList.splice(index, 1)

    if (funcList.length === 0) {
      // let simuid = this.getStartId(type)
      let subcirl = this.getSubCirl(type)
      subcirl.stop(params)
    }
  }

  callBack (message) {
    let typedir = message.infotype
    let type
    for (let [key, value] of typekeyMap.entries()) {
      if (value === typedir) {
        type = key
        break
      }
    }
    if (type === undefined) return
    let funcList = this.funcMap.get(type)
    if (funcList === undefined) return
    for (let func of funcList) {
      if (func === undefined || func.constructor !== Function) continue
      func(message)
    }
  }

  /**
   * 按类型开始订阅数据
   * @param {in} type
   * @param {in} params 订阅消息参数
   */
  Run (type, params) {
    let subcirl = this.getSubCirl(type)
    subcirl.start(params)
    subcirl.addCare(this.callBack, this)
  }

  getSubCirl (type) {
    let sub = store.getters['wsSubMgr'].getByName(subKey)
    if (sub === undefined) return
    let infotype = typekeyMap.get(type)
    let subcirl = sub.getSubByType(infotype, 'asc')
    return subcirl
  }

  // getStartId (type) {
  //   let key = typekeyMap.get(type)
  //   return `asc:${key}`
  // }
}
