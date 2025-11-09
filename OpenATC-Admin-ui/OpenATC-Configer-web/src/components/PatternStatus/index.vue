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
        <div v-for="(item,index2) in list" :key="index2" :class="cycles && item.direction.length===0?'direction': ''">
          <div class="first-1" :style="{'width':item.greenWidth,'height':'34px','background':'#7ccc66'}">
              <el-tooltip placement="top-start" effect="light">
                <div slot="content">P{{item.id}}:{{item.split}}</div>
                <div style="cursor:pointer;">
                  <div class="ring-phase">
                    <xdrdirselector Width="36px" Height="34px" :showlist="item.direction"></xdrdirselector>
                  </div>
                </div>
              </el-tooltip>
              <div class="box">
                <div class="ring-nums">P{{item.id}}</div>
                <div class="ring-nums">{{item.split}}</div>
              </div>
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
          }, 10)
          this.handleBarrierHeight()
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    // console.log(this.patternStatusList, this.cycles, 111)
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
      this.pattern = []
      if (val === null) return
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      for (let rings of val) {
        if (rings.length === 0) continue
        let list = []
        for (let ring of rings) {
          if (ring.value === 0) continue
          let obj = {}
          let split = ring.value
          obj.id = ring.id
          obj.split = split
          obj.direction = ring.desc.map(item => { // 虚相位desc为空
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
          if (mode !== 7) { // 忽略相位不显示
            list.push(obj)
          }
        }
        this.pattern.push(list)
        // console.log(list, this.cycles, 888)
      }
      this.handleBarrier(this.pattern, phaseList)
    },
    tranform (arr) { // 分barrier
      let newMap = new Map()
      arr.forEach(({id, current}) => {
        const find = newMap.get(current.join())
        if (find) newMap.get(current.join()).push(id)
        else newMap.set(current.join(), [id])
      })
      let ret = []
      newMap.forEach((value, key) => {
        ret.push(Array.from(new Set(key.split(',').map(Number).concat(value))))
      })
      return ret
    },
    step1 (list, arr) { // 各个环包含的相位
      const ret = []
      const listObj = {}
      list.forEach(l => {
        listObj[l.id] = l.ring
      })
      arr.forEach(a => {
        const retItem = []
        a.forEach(b => {
          const find = retItem.find(r => r.ring === listObj[b])
          if (find) find.data.push(b)
          else retItem.push({ring: listObj[b], data: [b]})
        })
        ret.push(retItem)
      })
      return ret
    },
    step2 (newArr, pattern) { // 不同环并发相位做比较
      let ret = []
      const patternObj = {}
      pattern.forEach(l => {
        patternObj[l.id] = l.split
      })
      newArr.forEach((na, index) => {
        let max = 0
        na.forEach(n => {
          const total = n.data.reduce((pre, cur) => pre + patternObj[cur], 0)
          if (total > max) max = total
        })
        while (index > 0 && max < this.cycles) {
          index--
          max += ret[index]
        }
        ret.push(max)
      })
      return ret
    },
    handleBarrier (pattern, phaseList) {
      if (pattern.length < 2) return
      let newPattern = []
      pattern.map(i => {
        newPattern.push(...i)
      })
      this.barrierList = []
      let currentArr = []
      let firstPatternStatus = pattern[0]
      for (let patternStatus of firstPatternStatus) {
        let concurrent = phaseList.filter((item) => {
          return item.id === patternStatus.id // patternStatus.id当前相位id concurrent当前相位的并发相位
        })[0].concurrent// 当前相位的并发相位
        let obj = {
          id: patternStatus.id,
          current: concurrent.sort()
        }
        currentArr.push(obj)
      }
      let newCurrent = this.tranform(currentArr)
      let ringTeam = this.step1(phaseList, newCurrent)
      let barrier = this.step2(ringTeam, newPattern)
      this.barrierList = barrier.map(j => {
        return (j / this.cycles * 100) + '%'
      })
      this.barrierList.unshift(0)
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
.direction {
  opacity: 0.6;
}
.first-1 {
  float: left;
  // text-align: center;
}
</style>
