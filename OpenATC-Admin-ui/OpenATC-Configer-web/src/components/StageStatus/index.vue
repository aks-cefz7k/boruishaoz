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
    },
    contrloType: {
      type: Boolean
    },
    localPatternList: {
      type: Array
    },
    controlData: {
      type: Object
    }
  },
  watch: {
    controlData: {
      handler: function (val, oldVal) {
        // this.handleStages() // 计算屏障高度
        if (this.contrloType) {
          this.getStageLine()
        } else {
          this.getPhaseId(this.controlData)
        }
      },
      // 深度观察监听
      deep: true
    },
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
    }
    if (this.localPatternList) {
      this.getStageLine()
    }
  },
  mounted () {
  },
  beforeUpdate () {
    if (this.patternStatusList && this.patternStatusList.length > 1) {
      this.getPhaseId(this.patternStatusList)
    } else if (this.patternStatusList && this.patternStatusList.length === 1) {
      this.handleStages()
    }
  },
  methods: {
    getStageLine () {
      for (let i = 0; i < this.localPatternList.length; i++) {
        if (this.controlData.patternid === this.localPatternList[i].id) {
          let stageCycleList = this.localPatternList[i].stagesList.map(item => {
            return item.stageSplit ? item.stageSplit : 0
          })
          let stageMaxCyle = stageCycleList.reduce((a, b) => {
            return a + b
          }, 0)
          this.addList(stageCycleList, stageMaxCyle)
          this.stageLists = this.localPatternList[i].stagesList.map(item => {
            return {
              ...item,
              greenWidth: (item.green / stageMaxCyle * 100).toFixed(3) + '%',
              yellowWidth: (item.yellow / stageMaxCyle * 100).toFixed(3) + '%',
              redWidth: (item.red / stageMaxCyle * 100).toFixed(3) + '%'
            }
          })
        }
      }
    },
    addList (baseArr, maxCycle) {
      this.narr = []
      this.stageLineStatus = true
      let setArr = []
      let currNum = 0
      baseArr.forEach((item, index) => {
        if (index === 0) {
          setArr.push(baseArr[index])
        } else {
          if (currNum) {
            currNum = baseArr[index] + currNum
          } else {
            currNum = baseArr[index] + baseArr[index - 1]
          }
          setArr.push(currNum)
        }
      })
      this.numList = setArr.map(item => {
        return (item / maxCycle * 100).toFixed(3) + '%'
      })
      this.numList.unshift(0)
      for (let i = this.numList.length - 1; i >= 1; i--) {
        this.narr.push((Number.parseFloat(this.numList[i]) - Number.parseFloat(this.numList[i - 1])) / 2 + Number.parseFloat(this.numList[i - 1]) - 1 + '%')
      }
      this.narr = JSON.parse(JSON.stringify(this.narr.reverse()))
    },
    getControl (newList) { // 总揽实时数据
      let currentIds = ''
      let lastCurrentIds = ''
      this.stateList = [0]
      this.narr = []
      for (let j = 0; j <= this.max; j++) { // 指针长度
        for (let i = 0; i < newList.length; i++) { // 环列表
          let ring = newList[i]// 每个环对象
          let sum = 0
          for (let n = 0; n < ring.length; n++) { // 相位累计长度
            if (ring[n].mode !== 7) {
              sum = sum + ring[n].split
              if (j < sum) {
                let phaseId = ring[n].id
                currentIds = currentIds + '-' + phaseId
                break
              }
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
    },
    getPhaseId (rings) { // 画阶段方法
      if (!rings.rings) return
      rings = JSON.parse(JSON.stringify(rings))
      if (rings.length === 0) {
        this.stageLineStatus = false
        return
      }
      this.stageLineStatus = true
      if (this.controlData) {
        let mapAdd = rings.phase.map(item => {
          return {
            id: item.id,
            split: item.split
          }
        })
        let newRings = rings.rings.map(j => {
          return j.sequence
        })
        let newList = newRings.map(item => {
          let ret = []
          item.map(i => {
            const find = mapAdd.find(j => j.id === i)
            if (find) {
              ret.push(find)
            }
          })
          return ret
        })
        let mapAdds = newList.map(item => {
          return item.map(val => {
            return val.split
          })
        })
        let maxCycle = mapAdds.length > 0 ? mapAdds.map(item => {
          return item.length > 0 ? item.reduce((a, b) => {
            return a + b
          }) : 0
        }) : 0
        this.max = Math.max(...maxCycle)// 每个环的周期最大值
        this.getControl(newList)
      } else {
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
        this.max = Math.max(...maxCycle)// 每个环的周期最大值
        this.getControl(rings)
      }
      for (let i = this.numList.length - 1; i >= 1; i--) {
        this.narr.push((Number.parseFloat(this.numList[i]) - Number.parseFloat(this.numList[i - 1])) / 2 + Number.parseFloat(this.numList[i - 1]) - 1 + '%')
      }
      this.narr = JSON.parse(JSON.stringify(this.narr.reverse()))
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
      let stageLength = 0
      let stageStatusLength = 0
      for (let i = 0; i < firstPatternStatusList.length; i++) {
        let tempLength = Number.parseFloat(firstPatternStatusList[i].greenWidth) + Number.parseFloat(firstPatternStatusList[i].redWidth) + Number.parseFloat(firstPatternStatusList[i].yellowWidth)
        stageStatusLength = stageLength + tempLength / 2 - 1
        stageLength = stageLength + tempLength
        this.numList.push(stageLength + '%')
        this.narr.push(stageStatusLength + '%')
      }
    }
  },
  computed: {
  }
}
</script>
<style lang="scss" scoped>

</style>
