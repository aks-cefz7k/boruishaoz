/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
<template>
    <div class="main-statgestatus">
        <div class="stage-line" v-show="stageLineStatus"></div>
        <div v-for="(item, index) in numList" :key="index + '1'">
          <div class="stage-verticalline" :style="{'margin-left':item}"></div>
        </div>
        <div v-for="(item, index) in narr" :key="index + '2'">
          <div class="stage-text" :style="{'left':item}"><span class="stage-span">S{{index + 1}}</span></div>
        </div>
    </div>
</template>
<script>

export default {
  name: 'stagestatus',
  components: {},
  data () {
    return {
      // stageList: [],
      numList: [],
      stateList: [],
      narr: [],
      max: '',
      stageStatusList: [],
      stageLineStatus: false
    }
  },
  props: {
    patternStatusList: {
      type: Array
    }
  },
  watch: {
    patternStatusList: {
      handler: function (val, oldVal) {
        // this.handleStages() // 计算屏障高度
        this.getPhaseId(this.patternStatusList)
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    if (this.patternStatusList && this.patternStatusList.length > 1) {
      this.getPhaseId(this.patternStatusList)
      // console.log(this.patternStatusList, 7878)
    } else {
      console.log(this.patternStatusList, 8888)
    }
  },
  mounted () {
    // this.getPhaseId(this.patternStatusList)
  },
  beforeUpdate () {
    if (this.patternStatusList && this.patternStatusList.length > 1) {
      this.getPhaseId(this.patternStatusList)
      // console.log(this.patternStatusList, 7878)
    } else if (this.patternStatusList && this.patternStatusList.length === 1) {
      this.handleStages()
      // console.log(this.patternStatusList, 8888)
    }
  },
  updated () {
    // debugger
    // if (this.patternStatusList && this.patternStatusList.length > 1) {
    //   this.getPhaseId(this.patternStatusList)
    //   // console.log(this.patternStatusList, 7878)
    // } else if (this.patternStatusList && this.patternStatusList.length === 1) {
    //   this.handleStages()
    //   // console.log(this.patternStatusList, 8888)
    // }
  },
  methods: {
    getPhaseId (rings) { // 画阶段方法
      // debugger
      rings = JSON.parse(JSON.stringify(rings))
      // console.log(rings, 3232)
      if (rings.length === 0) {
        this.stageLineStatus = false
        return
      }
      this.stageLineStatus = true
      let mapAdd = rings.map(item => {
        return item.map(val => {
          return val.value
        })
      })
      let maxCycle = mapAdd.length > 0 ? mapAdd.map(item => {
        return item.length > 0 ? item.reduce((a, b) => {
          return a + b
        }) : 0
      }) : 0
      // console.log(maxCycle, 23232)
      this.max = Math.max(...maxCycle)// 每个环的周期最大值
      // console.log(this.max, 'max')
      this.stateList = [0]
      this.narr = []
      let currentIds = ''
      let lastCurrentIds = ''
      for (let j = 0; j <= this.max; j++) { // 指针长度
        for (let i = 0; i < rings.length; i++) { // 环列表
          let ring = rings[i]// 每个环对象
          let sum = 0
          for (let n = 0; n < ring.length; n++) { // 相位累计长度
            sum = sum + ring[n].value
            if (j < sum) {
              let phaseId = ring[n].id
              currentIds = currentIds + '-' + phaseId
              break
            }
          }
        }
        if (lastCurrentIds !== currentIds && lastCurrentIds !== '') { // 当前相位id和上一个相比不同说明相位变化了
          this.stateList.push(j)// 阶段累计长度的集合
        }
        lastCurrentIds = currentIds
        currentIds = ''
        this.numList = this.stateList.map(item => {
          return (item / this.max * 100).toFixed(3) + '%'
        })
      }
      for (let i = this.numList.length - 1; i >= 1; i--) {
        this.narr.push((Number.parseFloat(this.numList[i]) - Number.parseFloat(this.numList[i - 1])) / 2 + Number.parseFloat(this.numList[i - 1]) - 1 + '%')
      }
      this.narr = JSON.parse(JSON.stringify(this.narr.reverse()))
      console.log(this.narr, 766767)
      // console.log(this.numList, 12323)
    },
    handleStages () {
      this.numList = [0]
      this.narr = []
      if (this.patternStatusList.length === 0) {
        this.stageLineStatus = false
        return
      }
      this.stageLineStatus = true
      let firstPatternStatusList = this.patternStatusList[0]
      // this.numList.push(0)
      let stageLength = 0
      let stageStatusLength = 0
      for (let i = 0; i < firstPatternStatusList.length; i++) {
        let tempLength = Number.parseFloat(firstPatternStatusList[i].greenWidth) + Number.parseFloat(firstPatternStatusList[i].redWidth) + Number.parseFloat(firstPatternStatusList[i].yellowWidth)
        stageStatusLength = stageLength + tempLength / 2 - 1
        stageLength = stageLength + tempLength
        this.numList.push(stageLength + '%')
        this.narr.push(stageStatusLength + '%')
        // console.log(this.numList, this.narr, 9999)
      }
    }
  },
  computed: {
  }
}
</script>
<style lang="scss" scoped>
// .main-statgestatus {
//   position: relative;
// }
// .stage-line {
//   position: relative;
//   top: 7px;
//   width: 100%;
//   height: 1px;
//   background-color: #999999;
//   z-index:1;
// }
// .stage-verticalline {
//   position: absolute;
//   float: left;
//   width: 1px;
//   height: 14px;
//   background-color: #999999;
//   z-index:2;
// }
// .stage-text {
//   position: absolute;
//   float: left;
//   width: 25px;
//   background-color: #ffffff;
//   z-index:3;
// }
// .stage-span {
//   position: relative;
//   bottom: 3px;
//   left: 5px;
//   width: 15px;
//   height: 14px;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 20px;
//   letter-spacing: 0px;
//   background-color: #ffffff;
//   color: #333333;
// }
</style>
