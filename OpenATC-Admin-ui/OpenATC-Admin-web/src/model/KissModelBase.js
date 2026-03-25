export default class KissModelBase {
  constructor (key) {
    this.key = key
  }

  OnInit () {
    this.Init()
  }

  Init () {
    // console.log(`${this.key} init!`)
  }
}
