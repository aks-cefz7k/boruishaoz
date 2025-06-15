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
  <div class="app-container" ref="pattern-container">
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-table :data="patternList" :max-height="tableHeight" highlight-current-row  @expand-change="expandChange" ref="singleTable" id="footerBtn">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-tabs v-model="activeList[scope.$index]" type="card" @tab-click="handleClick">
            <el-tab-pane :label="$t('edge.pattern.ringConfig')" name="ring">
              <el-row :gutter="20">
                <el-col :span="12" >
                  <div class="components-container board" >
                    <Kanban v-for="n in ringCount"
                            :key="n" class="kanban todo"
                            :list="scope.row.rings[n-1]"
                            :options="scope.row.options"
                            :header-text="$t('edge.pattern.ring')+n"
                            :index="scope.$index"
                            @handleSplit="handleSplit"/>
                  </div>
                </el-col>
                <el-col :span="12">
                  <FollowPhase>

                  </FollowPhase>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane :label="$t('edge.pattern.stageConfig')" name="stage">
              <el-scrollbar :vertical="false">
                <div class="stage-panel-contener">
                  <StageKanban v-for="(stage,index) in stagesList"
                    class="kanban todo"
                    :key="index"
                    :stage="stage"
                    :options="scope.row.options"
                    :header-text="$t('edge.pattern.stage') + Number(index + 1)"
                    :rowIndex="scope.$index"
                    :subIndex="index"
                    @onStageSplitChange="onStageSplitChange"
                    @onStageDelaystartChange="onStageDelaystartChange"
                    @onStageAdvanceendChange="onStageAdvanceendChange"
                    />
                </div>
              </el-scrollbar>
            </el-tab-pane>
            <el-tab-pane :label="$t('edge.pattern.parameters')" name="parame">
              <el-row :gutter="20">
                <el-col :span="12">
                 <div class="components-container board">
                  <ExpendConfig class="kanban todo"
                    v-for="(j,index) in ringCounts"
                    :key="index"
                    :header-text="$t('edge.pattern.ring')+j"
                    :list="scope.row.rings[j-1]"
                    :options="scope.row.options"
                    />
                </div>
                </el-col>
                <el-col :span="12">
                  <div class="stage-item" style="margin: 30px 50px;">
                    <el-row style="margin-top:10px">
                      <el-col :span="8">
                        {{$t('edge.pattern.forbiddenstage')}}
                        <el-input class="stage-value" size="small" v-model="scope.row.forbiddenstage"></el-input>
                      </el-col>
                    </el-row>
                    <el-row style="margin-top:10px">
                      <el-col :span="8">
                        {{$t('edge.pattern.screenstage')}}
                        <el-input class="stage-value" size="small" v-model="scope.row.screenstage"></el-input>
                      </el-col>
                    </el-row>
                    <el-row style="margin-top:10px">
                      <el-col :span="8">
                        {{$t('edge.pattern.coordinatestage')}}
                        <el-input class="stage-value" size="small" v-model="scope.row.coordinatestage"></el-input>
                      </el-col>
                    </el-row>
                  </div>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-table-column>
      <el-table-column align="center" label="No" minWidth="40">
        <template slot-scope="scope">
          <span>{{scope.$index+1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="ID" minWidth="40">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pattern.desc')" prop="desc">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"></el-input>
        </template>
      </el-table-column>
      <el-table-column  align="center" :label="$t('edge.pattern.offset')" prop="offset">
        <template slot-scope="scope">
          <el-input size="small" v-model.number="scope.row.offset" @blur="checkOffset(scope.row.offset, scope.row)"></el-input>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pattern.cycle')" prop="cycle">
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pattern.plan')" prop="plan" min-width="200px">
        <template slot-scope="scope">
            <div class="pattern-figure">
              <BoardCard
              :patternStatusList="scope.row.rings"
              :cycles="scope.row.cycle"
              :isPhase="false"
              >
              </BoardCard>
            </div>
         </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pattern.operation')" width="120">
        <template slot-scope="scope">
          <el-button type="text"  @click="handleDelete(scope.$index, scope.row)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Kanban from '@/components/Kanban'
import StageKanban from '@/views/pattern/StageKanban'
import BoardCard from '@/components/BoardCard'
import FollowPhase from '@/components/FollowPhase'
import ExpendConfig from '@/components/ExpendConfig'
import { mapState } from 'vuex'
import { getTscControl } from '@/api/control'
import { getIframdevid } from '@/utils/auth'
export default {
  name: 'patterns',
  components: {
    Kanban,
    BoardCard,
    FollowPhase,
    ExpendConfig,
    StageKanban
  },
  data () {
    return {
      tableHeight: 760,
      ringCount: 1,
      ringCounts: 1,
      addId: 1,
      options: {
        group: 'pattern'
      },
      id: 1,
      barrierList: [],
      currPatternName: '--',
      patternStatusIndex: -1,
      // activeName: 'ring',
      stagesList: [],
      concurrentList: [],
      barrId: [],
      hideWidth: '',
      newBarrid: [],
      max: '',
      stateList: [],
      numList: [],
      narr: [],
      redux: ''
    }
  },
  computed: {
    ...mapState({
      patternList: state => state.globalParam.tscParam.patternList
    }),
    activeList () {
      return this.patternList.map(i => 'ring')
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.initData()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['pattern-container'].offsetHeight - 80
      window.onresize = function () {
        _this.tableHeight = _this.$refs['pattern-container'].offsetHeight - 80
      }
    })
  },
  watch: {
    patternList: function (val) {
      console.log(val)
      if (!val.length) return
      this.initData()
    }
  },
  methods: {
    initData () {
      // 判断有几个环，就创建几个看板
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let rings = []
      if (phaseList.length === 0) {
        this.$store.getters.tscParam.patternList = []
        return
      }
      for (let phase of phaseList) {
        rings.push(phase.ring)
      }
      this.ringCount = Array.from(new Set(rings)) // 去除数组重复的元素
      this.ringCount = this.ringCount.sort(this.sortNumbers) // 把数组中的值按照从小到大的顺序重新排序
      this.ringCounts = Array.from(new Set(rings)) // 去除数组重复的元素
      this.ringCounts = this.ringCounts.sort(this.sortNumbers) // 把数组中的值按照从小到大的顺序重新排序
      this.increaseId()
      this.getCycle()
      this.updatePhaseDescription()
      this.getOptionsOfRing()
    },
    sortNumbers (a, b) {
      return a - b
    },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let patternIdList = patternList.map(ele => ele.id)
      let i = patternList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 108; j++) {
          if (!patternIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleDelete (index, value) {
      this.$confirm(this.$t('edge.pattern.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        if (value.id === this.patternStatusIndex) {
          // this.isShowPatternStatus = false
          this.patternStatusIndex = -1
        }
        this.globalParamModel.deleteParamsByType('patternList', index, 1)
        this.$message({
          type: 'success',
          message: this.$t('edge.common.deletesucess')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.common.deletecancel')
        })
      })
    },
    addPattern () {
      this.increaseId()
      let Pattern = {
        id: this.id,
        desc: '',
        offset: 0,
        cycle: 0,
        rings: [[], [], [], []]
      }
      var newPattern = JSON.parse(JSON.stringify(Pattern))
      const phaseList = JSON.parse(JSON.stringify(this.globalParamModel.getParamsByType('phaseList')))
      for (let phase of phaseList) {
        let ring = {}
        ring.name = 'Phase ' + phase.id
        ring.desc = this.getPhaseDescription(phase.direction)
        ring.id = phase.id
        ring.value = 30
        ring.mode = 2
        ring.options = []
        ring.delaystart = 0
        ring.advanceend = 0
        if (phase.ring === 1) {
          newPattern.rings[0].push(ring)
        } else if (phase.ring === 2) {
          newPattern.rings[1].push(ring)
        } else if (phase.ring === 3) {
          newPattern.rings[2].push(ring)
        } else if (phase.ring === 4) {
          newPattern.rings[3].push(ring)
        }
        // pahseIndex++
      }
      return newPattern
    },
    onAdd () {
      if (this.globalParamModel.getParamLength('phaseList') === 0) {
        this.$message.error(
          // 'Please create phase first !'
          this.$t('edge.pattern.firstphase')
        )
        return
      }
      if (this.globalParamModel.getParamLength('patternList') >= 32) {
        this.$message.error(
          // 'There are at most 100 data !'
          this.$t('edge.pattern.mostdata')
        )
        return
      }
      // this.globalParamModel.getParamsByType('patternList').push(this.addPattern())
      this.globalParamModel.addParamsByType('patternList', this.addPattern())
      this.getCycle()
      // 重新排序相位数组
      let patternList = this.globalParamModel.getParamsByType('patternList')
      patternList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    handleEdit (index, row) {
    },
    getCycle () {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      for (let pattern of patternList) {
        for (let rings of pattern.rings) {
          let num = 0
          for (let i = 0; i < rings.length; i++) {
            if (rings[i].length !== 0) {
              num = num + Number(rings[i].value)
              let id = rings[i].id
              rings[i].name = this.getDescription(id)
            }
          }
          if (num !== 0) {
            pattern.cycle = num
            break
          }
        }
      }
    },
    getOptionsOfRing () {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      for (let pattern of patternList) {
        for (let rings of pattern.rings) {
          for (let ring of rings) {
            ring.options = this.getDecimalSystem(ring.options)
          }
        }
      }
    },
    getDecimalSystem (list) {
      if (!list) return
      let arr = []
      // if (list === null || list === undefined || list.length === 0) return arr
      if (list[0] === 1) arr.push(1)
      if (list[1] === 1) arr.push(2)
      if (list[2] === 1) arr.push(4)
      return arr
    },
    // 更新已有的pattern的箭头模型
    updatePhaseDescription () {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      for (let pattern of patternList) {
        for (let phase of phaseList) {
          if (phase.ring === 1) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[0]) {
              if (obj.id === phase.id) {
                obj.desc = list
                // obj.mode = 2
              }
            }
          }
          if (phase.ring === 2) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[1]) {
              if (obj.id === phase.id) {
                obj.desc = list
                // obj.mode = 2
              }
            }
          }
          if (phase.ring === 3) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[2]) {
              if (obj.id === phase.id) {
                obj.desc = list
                // obj.mode = 2
              }
            }
          }
          if (phase.ring === 4) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[3]) {
              if (obj.id === phase.id) {
                obj.desc = list
                // obj.mode = 2
              }
            }
          }
        }
      }
    },
    getDescription (id) {
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      for (let phase of phaseList) {
        if (phase.id === id) {
          if (phase.desc !== '' && phase.desc !== undefined) {
            return phase.desc
          } else {
            return 'phase' + id
          }
        }
      }
    },
    getPhaseDescription (phaseList) {
      if (!phaseList) return
      let list = []
      for (let id of phaseList) {
        let obj = {}
        obj.id = id
        obj.color = '#454545'
        list.push(obj)
      }
      return list
    },
    checkOffset (offset, val) {
      let cycle = val.cycle
      if (offset < 0) {
        this.$message.error('相位差不能小于0！')
      }
      if (offset > cycle) {
        this.$message.error('相位差不能大于周期！')
      }
    },
    expandChange (val1, val2) {
      if (val1.desc === '') {
        if (this.$i18n.locale === 'en') {
          this.currPatternName = 'pattern' + val1.id
        } else {
          this.currPatternName = '方案' + val1.id
        }
        // this.currPatternName = 'pattern' + val1.id
      } else {
        this.currPatternName = val1.desc
      }
      // this.handleCurrentChange(val1)
      if (val2.length > 0) { // 此种情况为收起看板
        this.getRowStages(val1.rings)
      }
    },
    handleSplit (index) {
      let currPattern = this.patternList[index]
      // this.handleCurrentChange(currPattern)
      // this.currentPattern = this.patternList[index]
      this.getRowStages(currPattern.rings)
    },
    handleClick (tab, event) {
      if (tab.paneName === 'stage') {
      }
    },
    getRowStages (rings) {
      let agentId = getIframdevid()
      // agentId = '40001'
      if (!agentId) {
        this.$message.warning(this.$t('edge.pattern.agentidError'))
        return false
      }
      getTscControl(agentId).then((data) => {
        this.intervalFlag = true
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let TscData = JSON.parse(JSON.stringify(data.data.data.data))
        this.handleStageData(TscData, rings) // 处理阶段（驻留）stage数据
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handleStageData (data, rings) {
      let stagesList = []
      let stages = data.stages
      for (let i = 0; i < stages.length; i++) {
        let stage = stages[i]
        let stageItem = this.getStageItem(stage, rings)
        stagesList.push(stageItem)
      }
      this.stagesList = stagesList
    },
    getStageItem (stageArr, ringsList) {
      let res = {
        split: 0, // 阶段绿性比
        stages: stageArr,
        delaystart: 0,
        advanceend: 0
      }
      let splitArr = []
      let delaystartArr = []
      let advanceendArr = []
      for (let rings of ringsList) {
        for (let ring of rings) {
          if (stageArr.includes(ring.id)) {
            let split = ring.value
            let delaystart = ring.delaystart
            let advanceend = ring.advanceend
            splitArr.push(split)
            delaystartArr.push(delaystart)
            advanceendArr.push(advanceend)
          }
        }
      }
      splitArr.sort(function (a, b) { return a - b })
      delaystartArr.sort(function (a, b) { return b - a })
      advanceendArr.sort(function (a, b) { return a - b })
      res.split = splitArr.length > 0 ? splitArr[0] : 0
      res.delaystart = delaystartArr.length > 0 ? delaystartArr[0] : 0
      res.advanceend = advanceendArr.length > 0 ? advanceendArr[0] : 0
      return res
    },
    onStageSplitChange (diff, rowIndex, subIndex) {
      let stageArr = this.stagesList[subIndex].stages
      let row = this.patternList[rowIndex]
      let ringsList = row.rings
      for (let rings of ringsList) {
        for (let ring of rings) {
          if (stageArr.includes(ring.id)) {
            ring.value = (ring.value ? ring.value : 0) + diff
            continue
          }
        }
      }
    },
    onStageDelaystartChange (diff, rowIndex, subIndex) {
      let stageArr = this.stagesList[subIndex].stages
      let row = this.patternList[rowIndex]
      let ringsList = row.rings
      for (let rings of ringsList) {
        for (let ring of rings) {
          if (stageArr.includes(ring.id)) {
            ring.delaystart = (ring.delaystart ? ring.delaystart : 0) + diff
            continue
          }
        }
      }
    },
    onStageAdvanceendChange (diff, rowIndex, subIndex) {
      let stageArr = this.stagesList[subIndex].stages
      let row = this.patternList[rowIndex]
      let ringsList = row.rings
      for (let rings of ringsList) {
        for (let ring of rings) {
          if (stageArr.includes(ring.id)) {
            ring.advanceend = (ring.advanceend ? ring.advanceend : 0) + diff
            continue
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .board {
    width: 100%;
    margin-left: 10px;
    display: flex;
    justify-content: center;
    flex-direction: row;
    align-items: flex-start;
  }
  /deep/.el-table .cell {
    overflow: unset;
  }
  .kanban {
    &.todo {
      .board-column-header {
        background: #4A9FF9;
      }
    }
  }
  .stage-panel-contener {
    display: flex;
    flex-direction: row;
    align-content:flex-start;
  }
</style>
