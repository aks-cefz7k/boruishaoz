// 这个类是一个全局的对象 会在应用一开始就会初始化 负责管理所有的业务对象
import {
  MgrCreate
} from './KissModelMgrList.js'

export default class KissModel {
  constructor () {
    this.mgrList = []
  }

  AddMgr (mgrObj) {
    this.mgrList.push(mgrObj)
  }

  Init () {
    MgrCreate(this.mgrList)
    for (let key of this.mgrList.keys()) {
      this.mgrList[key].OnInit()
    }
  }

  GetMgrByKey (key) {
    return this.mgrList.filter(val => val.key === key)
  }
}
