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
      <!-- 环模式true -->
      <div v-if="!contrloType">
        <div class="ring-first" v-for="(list, index1) in patternInfo" :key="index1">
          <div v-for="(item,index2) in list" :key="index2" :class="item.controltype===99?'direction': ''">
            <div class="first-1" :style="{'width':item.greenWidth,'height':'34px','background':'#7ccc66'}">
                <el-tooltip placement="top-start" effect="light">
                  <div slot="content">P{{item.id}}:{{item.split}}</div>
                  <div style="cursor:pointer;">
                    <div class="ring-phase">
                      <div v-for="(side, index) in sidewalkPhaseData" :key="side.key + '-' + index">
                        <PatternWalkSvg v-if="item.peddirection.includes(side.id)"  :Data="side" :Width="'32'" :Height="'34'" />
                      </div>
                      <xdrdirselector  Width="36px" Height="34px" :showlist="item.direction"></xdrdirselector>
                    </div>
                    <div class="box">
                      <div class="ring-nums">P{{item.id}}</div>
                      <div class="ring-nums">{{item.split}}</div>
                    </div>
                  </div>
                </el-tooltip>
                <div style="position:relative; width:50px;" v-for="(bus,index3) in busPhaseData" :key="index3">
                  <i class="iconfont icon-BRT" style="position: absolute;top: 8px;font-size:12px;color:#454545;" v-if="bus.controltype === 4 && bus.phaseid===item.id"></i>
                  <i class="iconfont icon-feijidongche" style="position: absolute;top: 8px;font-size:12px;color:#454545;" v-if="bus.controltype === 6 && bus.phaseid===item.id"></i>
                  <i class="iconfont icon-gongjiaoche" style="position: absolute;top: 8px;font-size:12px;color:#454545;" v-if="bus.controltype === 3 && bus.phaseid===item.id"></i>
                  <i class="iconfont icon-youguidianche" style="position: absolute;top: 8px;font-size:12px;color:#454545;" v-if="bus.controltype === 5 && bus.phaseid===item.id"></i>
                </div>
              </div>
              <div class="first-1" :style="{'width':item.flashgreen,'height':'34px','float':'left','background': 'linear-gradient(to right, #ffffff 50%, #7ccc66 0)','background-size': '4px 100%'}"></div>
              <div class="first-1" :style="{'width':item.yellowWidth,'height':'34px','background':'#f9dc6a'}"></div>
              <div class="first-1" :style="{'width':item.redWidth,'height':'34px','background':'#f27979'}"></div>
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
      <div v-if="contrloType">
        <div v-for="(list, index1) in stageLists" :key="index1">
          <!-- <div> -->
            <div class="first-1" :style="{'width':list.greenWidth,'height':'34px','background':'#7ccc66'}">
                <el-tooltip placement="top-start" effect="light">
                  <div slot="content">
                    <span class="ring-nums" v-for="(pha,index) in list.phases" :key="index">
                      P:{{pha}}
                      </span>
                  </div>
                  <div style="cursor:pointer;">
                    <div class="ring-phase">
                      <div v-for="(side, index) in sidewalkPhaseData" :key="side.key + '-' + index">
                        <PatternWalkSvg v-if="list.peddirection.includes(side.id)"  :Data="side" :Width="'32'" :Height="'34'" />
                      </div>
                      <xdrdirselector  Width="36px" Height="34px" :showlist="list.direction"></xdrdirselector>
                    </div>
                    <div class="box" style="line-height:28px">
                      <span class="ring-nums" v-for="(pha,index) in list.phases" :key="index">
                    P:{{pha}}
                      </span>
                    </div>
                  </div>
                </el-tooltip>
              </div>
              <!-- <div class="first-1" :style="{'width':item.flashgreen,'height':'34px','float':'left','background': 'linear-gradient(to right, #ffffff 50%, #7ccc66 0)','background-size': '4px 100%'}"></div> -->
              <div class="first-1" :style="{'width':list.yellowWidth,'height':'34px','background':'#f9dc6a'}"></div>
              <div class="first-1" :style="{'width':list.redWidth,'height':'34px','background':'#f27979'}"></div>
            </div>
          </div>
        <!-- </div> -->
      </div>
</template>
<script>
import xdrdirselector from '../XRDDirSelector/XRDDirSelector'
import PatternWalkSvg from '../IntersectionMap/crossDirection/baseImg/PatternWalkSvg'
import PhaseDataModel from '../IntersectionMap/crossDirection/utils.js'
import CrossDiagramMgr from '../../../EdgeMgr/controller/crossDiagramMgr.js'
export default {
  name: 'pattern-list',
  components: {
    PatternWalkSvg,
    xdrdirselector
  },
  data () {
    return {
      barrierHeight: '',
      barrierList: [],
      newCycle: this.cycles,
      patternIds: this.patternId,
      newPatterns: [],
      newList: [],
      sidewalkPhaseData: [],
      // controlDatas: this.controlData,
      max: '',
      stageLists: [],
      busPhaseData: [], // 公交相位数据
      patternInfo: []
    }
  },
  props: {
    stagesChange: {
      type: Array
    },
    contrloType: {
      type: Boolean
    },
    phaseList: {
      type: Array
    },
    localPatternList: {
      type: Array
    },
    controlData: {
      type: Object
    },
    cycle: {
      type: Number
    },
    agentId: {
      type: String
    },
    cycles: {
      type: Number
    },
    patternId: {
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
    controlData: {
      handler: function (val, oldVal) {
        if (this.contrloType) {
          this.getIndexStage()
        }
        // this.controlDatas = this.controlData
        this.handlePatternData()
      },
      // 深度观察监听
      deep: true
    },
    phaseList: {
      handler: function (val, oldVal) {
        this.getPedPhasePos()
        this.getBusPos()
        this.getStage()
      },
      // 深度观察监听
      deep: true
    },
    agentId: {
      handler: function (val, oldVal) {
        this.getBusPos()
      },
      // 深度观察监听
      deep: true
    },
    patternId: {
      handler: function (val, oldVal) {
        this.patternIds = this.patternId
      },
      // 深度观察监听
      deep: true
    },
    cycles: {
      handler: function (val, oldVal) {
        this.newCycle = this.cycles
      },
      // 深度观察监听
      deep: true
    },
    contrloType: {
      handler: function (val, oldVal) {
        if (this.contrloType) {
          this.getStage()
        }
      },
      // 深度观察监听
      deep: true
    },
    stagesChange: {
      handler: function (val, oldVal) {
        if (this.contrloType) {
          this.getStage()
        }
      },
      // 深度观察监听
      deep: true
    },
    patternStatusList: {
      handler: function (val, oldVal) {
        this.handleBarrierHeight() // 计算屏障高度
        if (this.patternStatusList && this.newCycle) {
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
    if (this.patternStatusList && this.newCycle && !this.controlData) {
      setTimeout(() => {
        this.handleCurrentChange(this.patternStatusList)
        this.handleBarrierHeight()
      }, 200)
    } else {
      this.handlePatternData()
    }
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
    this.getPedPhasePos()
    this.getBusPos()
    if (this.contrloType) {
      this.getStage()
    }
  },
  methods: {
    getPed (data) {
      let ped = []
      for (let stg of data) {
        let peddirections = []
        let currPhase = this.phaseList.filter((item) => {
          return item.id === stg
        })[0]
        for (let walk of this.sidewalkPhaseData) {
          if (stg === walk.phaseid) {
            peddirections.push(...currPhase.peddirection)
            peddirections = Array.from(new Set(peddirections))
          }
        }
        ped.push(...peddirections)
      }
      return ped
    },
    handleStageData (data) {
      if (!data) return
      let stagesTemp = []
      let tempList = []
      for (let stg of data) {
        let directionList = []
        let currPhase = this.phaseList.filter((item) => {
          return item.id === stg
        })[0]
        if (currPhase !== undefined) {
          directionList.push(...currPhase.direction)
          directionList = Array.from(new Set(directionList))
        }
        // if (directionList.length === 0) return
        tempList = directionList.map(dir => ({
          id: dir,
          color: '#606266'
        }))
        stagesTemp.push(...tempList)
      }
      return stagesTemp
    },
    getIndexStage () {
      for (let i = 0; i < this.localPatternList.length; i++) {
        if (this.controlData.patternid === this.localPatternList[i].id) {
          let stageCycleList = this.localPatternList[i].stagesList.map(item => {
            return item.stageSplit ? item.stageSplit : 0
          })
          let stageMaxCyle = stageCycleList.reduce((a, b) => {
            return a + b
          }, 0)
          this.stageLists = this.localPatternList[i].stagesList.map(item => {
            return {
              ...item,
              peddirection: this.getPed(item.phases ? item.phases : item.stages),
              direction: this.handleStageData(item.phases ? item.phases : item.stages),
              greenWidth: (item.green / stageMaxCyle * 100).toFixed(3) + '%',
              yellowWidth: (item.yellow / stageMaxCyle * 100).toFixed(3) + '%',
              redWidth: (item.red / stageMaxCyle * 100).toFixed(3) + '%'
            }
          })
        }
      }
    },
    getStage () {
      if (!this.stagesChange) return
      let stageCycleList = this.stagesChange.map(item => {
        return item.stageSplit ? item.stageSplit : 0
      })
      let stageMaxCyle = stageCycleList.reduce((a, b) => {
        return a + b
      }, 0)
      this.stageLists = this.stagesChange.map(item => {
        return {
          ...item,
          peddirection: this.getPed(item.phases ? item.phases : item.stages),
          direction: this.handleStageData(item.phases ? item.phases : item.stages),
          greenWidth: (item.green / stageMaxCyle * 100).toFixed(3) + '%',
          yellowWidth: (item.yellow / stageMaxCyle * 100).toFixed(3) + '%',
          redWidth: (item.red / stageMaxCyle * 100).toFixed(3) + '%'
        }
      })
    },
    getBusPos () {
      // 公交相位信息
      this.busPhaseData = []
      this.phaseList.forEach((ele, i) => {
        if (ele.controltype >= 3 && ele.controltype <= 5) {
          ele.direction.forEach((dir, index) => {
          // 车道相位
            this.busPhaseData.push({
              phaseid: ele.id, // 相位id，用于对应相位状态
              id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
              name: this.PhaseDataModel.getBusPhasePos(dir).name,
              controltype: ele.controltype
            })
          })
        }
      })
      let result = []
      let obj = {}
      for (var i = 0; i < this.busPhaseData.length; i++) {
        if (!obj[this.busPhaseData[i].phaseid]) {
          result.push(this.busPhaseData[i])
          obj[this.busPhaseData[i].phaseid] = true
        }
      }
      this.busPhaseData = result
    },
    getPedPhasePos () {
      // 行人相位信息
      this.sidewalkPhaseData = []
      this.phaseList.forEach((ele, i) => {
        if (ele.peddirection) {
          ele.peddirection.forEach((dir, index) => {
          // 行人相位
            if (this.PhaseDataModel.getSidePos(dir)) {
              this.sidewalkPhaseData.push({
                key: this.CrossDiagramMgr.getUniqueKey('pedphase'),
                phaseid: ele.id, // 相位id，用于对应相位状态
                id: dir,
                name: this.PhaseDataModel.getSidePos(dir).name
              })
            }
          })
        }
      })
    },
    handlePatternData () {
      this.newList = []
      if (Object.keys(this.controlData).length === 0 || this.phaseList.length === 0) return
      if (!this.controlData.phase) return
      let cycle = this.controlData.cycle
      if (!this.controlData.rings) return
      for (let rings of this.controlData.rings) {
        let phase = this.controlData.phase
        let list = []
        for (let sequ of rings.sequence) {
          let obj = {}
          obj.id = sequ
          let split = phase.filter((item) => {
            return item.id === sequ
          })[0].split
          let currPhase = this.phaseList.filter((item) => {
            if (item.id === sequ && item.controltype === 99) {
              obj.controltype = item.controltype
            }
            return item.id === sequ
          })[0]
          if (currPhase) {
            obj.redWidth = (currPhase.redclear / cycle * 100).toFixed(3) + '%'
            obj.yellowWidth = (currPhase.yellow / cycle * 100).toFixed(3) + '%'
            obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow - currPhase.flashgreen) / cycle * 100).toFixed(3) + '%'
            obj.flashgreen = (currPhase.flashgreen / cycle * 100).toFixed(3) + '%'
            obj.peddirection = currPhase.peddirection
            obj.split = split
            obj.direction = currPhase.direction.map(item => {
              return {
                id: item,
                color: '#454545'
              }
            })
            list.push(obj)
          }
        }
        this.newList.push(list)
        this.patternInfo = [...this.newList]
      }
    },
    handleBarrierHeight () { // 屏障高度
      if (!this.patternInfo) return
      let patternLength = this.patternInfo.length
      this.barrierHeight = (patternLength * 35 + 21) + 'px'
    },
    handleCurrentChange (val) { // 两个ring的数据
      if (val === null || val.length === 0) return
      this.patternInfo = []
      this.barrierList = []
      let currentArr = []
      let newPattern = []
      val.map(i => {
        newPattern.push(...i)
      })
      if (this.phaseList.length === 0) return
      for (let patternStatus of val[0]) {
        if (patternStatus.mode !== 7) {
          let concurrent = this.phaseList.filter((item) => {
            return item.id === patternStatus.id // patternStatus.id当前相位id concurrent当前相位的并发相位
          })[0].concurrent// 当前相位的并发相位
          if (concurrent) {
            let obj = {
              id: patternStatus.id,
              current: concurrent.sort()
            }
            currentArr.push(obj)
          }
        }
      }
      if (currentArr.length !== 0) {
        let newCurrent = this.tranform(currentArr)
        let ringTeam = this.step1(this.phaseList, newCurrent)
        this.setBarrier(ringTeam, val)
        this.fillGap(ringTeam, val)
        let barrier = this.step2(ringTeam, newPattern)
        this.barrierList = barrier.map(j => {
          return (j / (this.max ? this.max : this.newCycle) * 100) + '%'
        })
        // this.barrierList.unshift(0)
      }
      for (let rings of val) {
        if (rings.length === 0) continue
        let list = []
        for (let ring of rings) {
          if (ring.value === 0) continue
          let obj = {}
          let split = ring.value
          obj.id = ring.id
          // obj.split = split
          let currPhase = this.phaseList.filter((item) => {
            if (item.id === ring.id && item.controltype === 99) {
              obj.controltype = item.controltype
            }
            return item.id === ring.id
          })[0]
          if (ring.desc) {
            obj.direction = ring.desc.map(item => { // 虚相位desc为空
              return {
                id: item.id,
                color: '#454545'
              }
            })
          } else {
            obj.direction = currPhase.direction.map(item => {
              return {
                id: item,
                color: '#454545'
              }
            })
          }
          if (ring.sum) {
            obj.split = split + ring.sum
            obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow - currPhase.flashgreen + ring.sum) / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
            // obj.hideWidth = (ring.sum / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
          } else {
            obj.split = split
            obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow - currPhase.flashgreen) / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
          }
          obj.flashgreen = (currPhase.flashgreen / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
          obj.redWidth = (currPhase.redclear / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
          obj.yellowWidth = (currPhase.yellow / (this.max ? this.max : this.newCycle) * 100).toFixed(3) + '%'
          obj.peddirection = currPhase.peddirection
          // 忽略相位不显示
          let mode = ring.mode
          if (mode !== 7) { // 忽略相位不显示
            list.push(obj)
          }
        }
        this.patternInfo.push(list)
      }
    },
    setBarrier (ringTeam, val) { // 添加特征参数barrier
      // let patternList = this.globalParamModel.getParamsByType('patternList')
      this.patternStatusList.map(item => {
        if (item.id === this.patternIds) {
          const patternObjs = {}
          val.forEach(l => {
            l.map(k => {
              patternObjs[k.id] = k.value
            })
          })
          let ret = ringTeam.map((y, index) => {
            y.map(n => {
              n.length = n.data.length > 1 ? n.data.reduce((pre, cur) => pre + patternObjs[cur], 0) : patternObjs[n.data[0]]
            })
            return {
              barrier: index + 1,
              length: y[0].length,
              items: y.map(j => {
                return {
                  ring: j.ring,
                  data: j.data
                }
              })
            }
          })
          item.barriers = ret
        }
      })
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
      const result = []
      for (const a of ret) {
        let merged = false
        for (const r of result) {
          if (this.check([...r], a)) {
            a.forEach(item => r.add(item))
            merged = true
          }
        }
        if (!merged) {
          result.push(new Set(a))
        }
        merged = false
      }
      return result.map(s => [...s])
    },
    check (arr1, arr2) {
      return arr1.some(a1 => arr2.some(a2 => a1 === a2))
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
          if (listObj[b]) {
            const find = retItem.find(r => r.ring === listObj[b])
            if (find) find.data.push(b)
            else retItem.push({ring: listObj[b], data: [b]})
          }
        })
        ret.push(retItem)
      })
      return ret
    },
    step2 (newArr, pattern) { // 不同环并发相位做比较
      let ret = []
      const patternObj = {}
      pattern.forEach(l => {
        if (l.mode !== 7) {
          patternObj[l.id] = l.value
        }
      })
      newArr.forEach((na, index) => {
        let max = 0
        na.forEach(n => {
          const total = n.data.reduce((pre, cur) => pre + patternObj[cur], 0)
          if (total > max) {
            max = total
          }
        })
        while (index > 0 && max < this.newCycle) { // && max < _this.newCycle
          index--
          max += ret[index]
        }
        ret.push(max)
      })
      return ret
    },
    fillGap (newArr, pattern) {
      this.newPatterns = []
      const patternObj = {}
      pattern.forEach(l => {
        l.map(k => {
          if (k.mode !== 7) {
            patternObj[k.id] = k.value
          }
        })
      })
      let newMax = []
      let newMin = []
      newArr.forEach((na, index) => {
        na.map(n => {
          n.length = n.data.length > 1 ? n.data.reduce((pre, cur) => pre + patternObj[cur], 0) : patternObj[n.data[0]]
        })
        let maxNum = Math.max.apply(Math, na.map(item => { return item.length }))
        let minNum = Math.min.apply(Math, na.map(item => { return item.length }))
        newMax.push(maxNum)
        newMin.push(minNum)
        let newmaxNum = Math.max.apply(Math, newMax)// 每组最大值
        let newminNum = Math.min.apply(Math, newMin)
        if (newmaxNum === newminNum) {
          this.newCycle = newmaxNum + newminNum
          this.max = newmaxNum + newminNum
          pattern.map(d => {
            d.map(r => {
              if (r.sum && r.mode !== 7) {
                delete r.sum
              }
            })
          })
        }
        if (maxNum === minNum) {
          na.map(n => {
            pattern.map(h => {
              h.map(d => {
                if (d.id === n.data[1] && d.sum && d.mode !== 7) {
                  delete d.sum
                }
              })
            })
          })
        } else {
          na.map(n => {
            if (n.length === maxNum) {
              pattern.map(h => {
                h.map(d => {
                  if (n.data.length > 1) {
                    if (d.id === n.data[1] && d.mode !== 7) {
                      delete d.sum
                    }
                  } else {
                    if (d.id === n.data[0] && d.mode !== 7) {
                      delete d.sum
                    }
                  }
                })
              })
            }
          })
        }
        na.forEach((value, index, array) => {
          if (value.length !== maxNum) {
            let newNa = []
            newNa.push(value)
            newNa.forEach(m => {
              let sum = Number(maxNum - m.length)
              pattern.filter((i) => {
                i.map(j => {
                  if (m.data.length > 1) {
                    if (j.id === m.data[1] && j.mode !== 7) {
                      j.sum = sum
                    }
                  } else {
                    if (j.id === m.data[0] && j.mode !== 7) {
                      j.sum = sum
                    }
                  }
                })
              })
            })
          }
          let mapAdd = pattern.map(item => {
            return item.map(val => {
              return val.mode === 7 ? 0 : val.value + (val.sum ? val.sum : 0)
            })
          })
          let maxCycle = mapAdd.length > 0 ? mapAdd.map(item => {
            return item.length > 0 ? item.reduce((a, b) => {
              return a + b
            }) : 0
          }) : 0
          this.max = Math.max(...maxCycle)// 每个环的周期最大值
        })
      })
    }
  }
}
</script>
<style lang="scss">
    // el-tooltip样式
.el-tooltip__popper.is-light {
  background: #409EFF !important;
  border: 1px solid #409EFF !important;
  color: #FFFFFF !important;
}
.el-tooltip__popper.is-light[x-placement^="top"] .popper__arrow {
  border-top-color: #409EFF !important;
}
.el-tooltip__popper.is-light[x-placement^="top"] .popper__arrow::after {
  border-top-color: #409EFF !important;
}
</style>
<style lang="scss" scoped>
.main-patternstatus {
  position: relative;
}
.main-patternstatus {
  position: relative;
  .ring-first {
    margin-top: 1px;
    width: 100%;
    height: 34px;
  }
  .first-1 {
    float: left;
    // text-align: center;
  }
  // .ring-num {
  //   float: left;
  //   margin-top:7px;
  //   margin-left: 5px;
  //   font-size: 14px;
  //   font-weight: normal;
  //   font-stretch: normal;
  //   line-height: 22px;
  //   letter-spacing: 0px;
  //   color: #ffffff;
  // }
  .box {
    float: left;
    margin-left: 6px;
    margin-right: 10px;
  }
  .ring-nums {
    // float: left;
    // margin-top:7px;
    // margin-left: 5px;
    font-size: 12px;
    height: 14px;
    font-weight: normal;
    font-stretch: normal;
    // line-height: 22px;
    letter-spacing: 0px;
    color: #333333;
  }
  .ring-phase {
    position: relative;
    float: left;
    // margin-left: 5px;
    height: 34px;
    z-index: 999;
    background: rgba(30, 96, 12, 0.2);
    // margin-top:1px;
  }
  .ring-phase::after {
    position: absolute;
    display: block;
    content: '';
    height: 0;
    width: 0;
    top: 11px;
    left: 36px;
    border: 5px solid transparent;
    border-left-color: rgba(30, 96, 12, 0.2);
  }
  .divider {
    position: absolute;
    top: -10px;
    // left: 370px;
    width: 3px;
    // height: 99px;
    z-index: 999;
    background-color:#B9BABF;
  }
  .curTimeLine {
    position: absolute;
    top: -10px;
    width: 2px;
    background-color: #409EFF;
  }
  .curTimeDiv {
    position: absolute;
    z-index:50;
    top: -21px;
    margin-left: -30px;
    text-align:center;
    vertical-align: middle;
    width: 60px;
    height: 19px;
    color:#FFFFFF;
    background-color: #409EFF;
  }
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
