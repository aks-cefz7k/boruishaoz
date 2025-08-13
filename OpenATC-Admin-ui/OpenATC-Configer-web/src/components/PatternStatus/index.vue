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
    <div class="main-patternstatus">
      <div class="ring-first" v-for="(list, index1) in pattern" :key="index1">
        <div v-for="(item,index2) in list" :key="index2">
          <div class="first-1" :style="{'width':item.greenWidth,'height':'34px','background':'#7ccc66'}">
            <div class="ring-phase">
                <xdrdirselector Width="36px" Height="34px" :showlist="item.direction"></xdrdirselector>
              </div>
              <el-tooltip placement="top-start" effect="light">
                <div slot="content">P{{item.id}}:{{item.split}}</div>
                <div style="cursor:pointer;">
                  <div class="box">
                    <div class="ring-nums">P{{item.id}}</div>
                    <div class="ring-nums">{{item.split}}</div>
                  </div>
                </div>
              </el-tooltip>
            </div>
            <div class="first-1" :style="{'width':item.yellowWidth,'height':'34px','background':'#f9dc6a'}"></div>
            <div class="first-1" :style="{'width':item.redWidth,'height':'34px','background':'#f27979'}"></div>
          <!-- <div class="first-1" v-show="pattern.length > 1" :style="{'width':item.hideWidth,'height':'34px','background':'#191F34'}"></div> -->
          </div>
        </div>
      <div v-for="(item, index) in barrierList" :key="index + '1'">
        <div class="divider" :style="{'left':item, 'height':barrierHeight}"></div>
      </div>
      <div v-show="syncTime && cycle && cycle > 0">
        <div class="curTimeDiv" :style="{'left':paddingLeft, 'background-color': '#409EFF'}">{{ timeNumDevide }}</div>
        <div class="curTimeLine" :style="{'left':paddingLeft, 'height':barrierHeight}"></div>
      </div>
    </div>
</template>
<script>

import xdrdirselector from '@/components/XRDDirSelector'
export default {
  name: 'patternstatus',
  components: {
    xdrdirselector
  },
  data () {
    return {
      barrierHeight: '',
      barrierList: [],
      hideWidth: '',
      // newB: '',
      // max: '',
      pattern: this.patternStatusList
    }
  },
  props: {
    cycle: {
      type: Number
    },
    cycles: {
      type: Number
    },
    patternStatusList: {
      type: Array
    },
    syncTime: {
      type: Number
    }
  },
  computed: {
    paddingLeft () {
      let res = '0%'
      if (this.cycle && this.cycle > 0) {
        let curPercent = (this.cycle - this.syncTime) / this.cycle
        res = curPercent * 100 + '%'
      }
      return res
    },
    timeNumDevide () {
      let res = (this.cycle - this.syncTime) + '/' + this.cycle
      return res
    }
  },
  watch: {
    patternStatusList: {
      handler: function (val, oldVal) {
        // this.pattern = []
        this.handleBarrierHeight() // 计算屏障高度
        if (this.patternStatusList && this.cycles) {
          setTimeout(() => {
            this.handleCurrentChange(this.patternStatusList)
          }, 100)
          this.handleBarrierHeight()
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    if (this.patternStatusList && this.cycles) {
      this.handleCurrentChange(this.patternStatusList)
      this.handleBarrierHeight()
    }
  },
  mounted () {
  },
  updated () {
    if (this.patternStatusList && this.cycle) {
      this.$nextTick(() => {
        this.handleBarrierHeight()
        this.pattern = this.patternStatusList
      })
    }
  },
  methods: {
    handleBarrierHeight () { // 屏障高度
      let patternLength = this.pattern.length
      this.barrierHeight = (patternLength * 35 + 21) + 'px'
    },
    handleCurrentChange (val) { // 两个ring的数据
      // this.hideWidth = ''
      // this.barrId = []
      // this.newBarrid = []
      this.pattern = []
      if (val === null) return
      // let allPhase = val.reduce(function (a, b) { return a.concat(b) })// 所以相位值id
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      // this.concurrentList = phaseList.map(item => { // 每个相位对应的并发相位
      //   return {
      //     concurrent: item.concurrent,
      //     id: item.id
      //   }
      // })
      // this.concurrentList1 = phaseList.map(item => {
      //   return {
      //     concurrent: item.concurrent
      //   }
      // })
      // let newCon = this.concurrentList1.map(item => {
      //   return item.concurrent
      // })
      // console.log(newCon, 88888)
      // let hash = {}
      // let res = []
      // for (let i = 0; i < newCon.length; i++) {
      //   if (!hash[newCon[i]]) {
      //     res.push(newCon[i])
      //     hash[newCon[i]] = true
      //   }
      // }
      // let newRes = JSON.parse(JSON.stringify(res))
      // console.log(newRes, 999)
      // newRes.map(item => {
      //   if (item.length === 0) return
      //   let newId = this.concurrentList.filter(value => {
      //     return value.id === item[0]
      //   })[0].concurrent
      //   debugger
      //   console.log(newId, 89)
      // let newArr2 = [...newId.sort(), ...item.sort()]
      // console.log(newArr2, 888)
      // let newList = []
      // newArr2.map(i => {
      //   for (let rings of val) {
      //     for (let ring of rings) {
      //       if (i === ring.id) {
      //         newList.push(ring.value)
      //       }
      //     }
      //   }
      // })
      // console.log(newList, 76)
      // this.redux = ((allPhase[newArr2[0][0] - 1].value) + (allPhase[newArr2[0][1] - 1].value)) - ((allPhase[newArr2[1][0] - 1].value) + (allPhase[newArr2[1][1] - 1].value))
      // if (this.redux < 0) { // 每组最小的
      //   this.barrId = newArr2[0][1]
      //   // this.newBarrid.push(this.barrId)
      //   // if (this.newBarrid.length > 1) {
      //   this.hideWidth = (Math.abs(this.redux) / this.cycles * 100).toFixed(3) + '%'
      //   // } else if (this.newBarrid.length === 1) {
      //   //   this.barrId = newArr2[0][1]
      //   //   this.hideWidth = (Math.abs(this.redux) / cycle * 100).toFixed(3) + '%'
      //   // }
      // } else if (this.redux > 0) {
      //   // this.barrId = newArr2[1][1]
      //   this.hideWidth = (Math.abs(this.redux) / this.cycles * 100).toFixed(3) + '%'
      // }
      // })
      // for (let split of val) {
      //   this.splitList = split.map(i => {
      //     return {
      //       id: i.id,
      //       value: i.value
      //     }
      //   })
      //   // console.log(this.splitList, 80)
      // }
      for (let rings of val) {
        if (rings.length === 0) continue
        let list = []
        for (let ring of rings) {
          // ring.id ===
          // let valueList = rings.map(i => {
          //   return {
          //     id: i.id,
          //     value: i.value
          //   }
          // })
          // console.log(valueList, 8989)
          if (ring.value === 0) continue
          let obj = {}
          let split = ring.value
          obj.id = ring.id
          obj.split = split
          obj.direction = ring.desc.map(item => {
            return {
              id: item.id,
              color: '#454545'
            }
          })
          let currPhase = phaseList.filter((item) => {
            return item.id === ring.id
          })[0]
          obj.redWidth = (currPhase.redclear / this.cycles * 100).toFixed(3) + '%'
          obj.yellowWidth = (currPhase.yellow / this.cycles * 100).toFixed(3) + '%'
          obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow) / this.cycles * 100).toFixed(3) + '%'
          // 忽略相位不显示
          let mode = ring.mode
          if (mode !== 7) {
            list.push(obj)
          }
        }
        this.pattern.push(list)
      }
      this.handleBarrier(this.pattern, phaseList)
    },
    handleBarrier (pattern, phaseList) {
      // debugger
      if (pattern.length < 2) return
      this.barrierList = []
      // this.newB = []
      let tempList = []
      let currentArr = []
      // let newA = []
      let barrierWidth = 0
      let firstPatternStatus = pattern[0]
      for (let patternStatus of firstPatternStatus) {
        let concurrent = phaseList.filter((item) => {
          return item.id === patternStatus.id // patternStatus.id当前相位id concurrent当前相位的并发相位
        })[0].concurrent// 当前相位的并发相位
        if (concurrent.length === 0) {
          this.barrierList = []
          return
        }
        if (!this.isEqualsForArray(tempList, concurrent)) {
          tempList = concurrent
          this.barrierList.push(barrierWidth)
        }
        let obj = {
          id: patternStatus.id,
          current: concurrent
        }
        currentArr.push(obj)
        console.log(currentArr, 88889)
        // this.newB = [patternStatus.id + ',' + concurrent]
        // newA.push(this.newB)
        // console.log(newA, 377)
        barrierWidth = Number.parseFloat(barrierWidth) + Number.parseFloat(patternStatus.hideWidth ? patternStatus.hideWidth : 0) + Number.parseFloat(patternStatus.redWidth) + Number.parseFloat(patternStatus.yellowWidth) + Number.parseFloat(patternStatus.greenWidth) + '%'
      }
      let map = {}
      let arr1 = []
      for (let i = 0; i < currentArr.length; i++) {
        let ai = currentArr
        if (!map[ai.current]) {
          arr1.push({
            id: ai.id,
            current: ai.current
          })
        } else {
          for (let j = 0; j < arr1.length; j++) {
            let dj = arr1[j]
            if (dj.current === ai.current) {
              dj.current = dj.current + ai.current
              break
            }
          }
        }
      }
      console.log(arr1, 90)
      this.barrierList.push('100%')// 添加末尾处的屏障
      console.log(this.barrierList, 33333)
    },
    isEqualsForArray (listA, listB) {
      return listA.length === listB.length && listA.every(a => listB.some(b => a === b)) && listB.every(_b => listA.some(_a => _a === _b))// 判断两个数组包含的值是否完全相同
    }
  }
}
</script>
<style lang="scss" scoped>
.main-patternstatus {
  position: relative;
}
.ring-first {
  margin-top: 1px;
  width: 100%;
  height: 34px;
}
.first-1 {
  float: left;
  // text-align: center;
}
</style>
