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
  <div class="app-container" ref="adapt-container">
    <el-button style="margin-bottom:10px" type="primary" @click="onAddClick">{{$t('edge.common.add')}}</el-button>
    <el-table :data="singleoptim"
              :max-height="tableHeight"
              highlight-current-row
              ref="singleTable"
              id="footerBtn">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <div class="components-container board">
            <Kanban v-for="n in ringCount"
                    class="kanban todo"
                    :key="n"
                    :rowIndex="scope.$index"
                    :subIndex="n - 1"
                    :list="getKanbanList(scope.$index, n-1)"
                    :factorList="getFactorList(scope.row, n-1)"
                    :header-text="$t('edge.pattern.ring')+ n"
                    @setSubList="setSubList"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="No" minWidth="40">
        <template slot-scope="scope">
          <span>{{scope.$index+1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.plan.pattern')" minWidth="40">
        <template slot-scope="scope">
          <span>{{scope.row.pattern}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.singleoptim.lossTimeOfGreenBeginning')">
        <template slot-scope="scope">
          <el-input-number size="small" :min="0" :max="65535" :controls="false" v-model="scope.row.greenstartuplosstime"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column  align="center" :label="$t('edge.singleoptim.lossTimeOfYellowEnding')">
        <template slot-scope="scope">
          <el-input-number size="small" :min="0" :max="65535" :controls="false" v-model.number="scope.row.yellowendlosstime"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.singleoptim.saturationFlow')">
        <template slot-scope="scope">
          <el-input-number size="small" :min="0" :max="65535" :controls="false" v-model.number="scope.row.maxflow"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column  align="center" :label="$t('edge.singleoptim.selfLearningModeOnOrOff')">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.selflearning"
            :active-value="1"
            :inactive-value="0">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column  align="center" :label="$t('edge.singleoptim.staticWeight')">
        <template slot-scope="scope">
          <el-input-number size="small" :max="1" :min="0" :controls="false" v-model.number="scope.row.staticweight"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column  align="center" :label="$t('edge.singleoptim.cycleAdjustmentFactor')">
        <template slot-scope="scope">
          <el-input-number size="small" :min="0" :max="65535" :controls="false" v-model.number="scope.row.cycleadjustfactor"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pattern.operation')" width="120">
        <template slot-scope="scope">
          <el-button type="text"  @click="onDeleteClick(scope.$index, scope.row)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Kanban from '@/views/adaptiveParameter/Kanban'
import { mapState } from 'vuex'
export default {
  name: 'patterns',
  components: {
    Kanban
  },
  data () {
    return {
      tableHeight: 760,
      ringCount: 1,
      id: 1
    }
  },
  computed: {
    ...mapState({
      singleoptim: state => state.globalParam.tscParam.singleoptim
    })
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.initData()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['adapt-container'].offsetHeight - 80
      window.onresize = function () {
        _this.tableHeight = _this.$refs['adapt-container'].offsetHeight - 80
      }
    })
  },
  watch: {
    singleoptim: function (val) {
      if (!val.length) return
      this.initData()
    }
  },
  methods: {
    getKanbanList (rowIndex, ringIndex) {
      let res = []
      let patternList = this.globalParamModel.getParamsByType('patternList')
      if (patternList && patternList.length > rowIndex) {
        let pattern = patternList[rowIndex]
        let rings = pattern.rings
        if (rings && rings.length > ringIndex) {
          res = pattern.rings[ringIndex]
        }
      }
      return res
    },
    getFactorList (row, ringIndex) {
      let res = []
      if (row.phasestaticweight && row.phasestaticweight.length > ringIndex) {
        res = row.phasestaticweight[ringIndex]
      }
      return res
    },
    initData () {
      // 判断有几个环，就创建几个看板
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let rings = []
      if (phaseList.length === 0) {
        this.$store.getters.tscParam.singleoptim = []
        return
      }
      for (let phase of phaseList) {
        rings.push(phase.ring)
      }
      this.ringCount = Array.from(new Set(rings)) // 去除数组重复的元素
      this.ringCount = this.ringCount.sort(this.sortNumbers) // 把数组中的值按照从小到大的顺序重新排序
      this.getCycle()
      this.updatePhaseDescription()
    },
    sortNumbers (a, b) {
      return a - b
    },
    onDeleteClick (index, value) {
      this.$confirm(this.$t('edge.singleoptim.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('singleoptim', index, 1)
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
    addSingleoptim () {
      let item = {
        pattern: 1,
        selflearning: 0,
        greenstartuplosstime: 3,
        yellowendlosstime: 1,
        cycleadjustfactor: 100,
        staticweight: 0,
        maxflow: 1600,
        phasestaticweight: []
      }
      let index = this.singleoptim.length
      let patternList = this.globalParamModel.getParamsByType('patternList')
      item.pattern = patternList[index].id
      return item
    },
    onAddClick () {
      if (this.globalParamModel.getParamLength('phaseList') === 0) {
        this.$message.error(
          // 'Please create phase first !'
          this.$t('edge.pattern.firstphase')
        )
        return
      }
      if (this.globalParamModel.getParamLength('patternList') >= 108) {
        this.$message.error(
          // 'There are at most 100 data !'
          this.$t('edge.pattern.mostdata')
        )
        return
      }

      let index = this.singleoptim.length
      let patternList = this.globalParamModel.getParamsByType('patternList')
      if (patternList.length === 0 || patternList.length - 1 < index) {
        this.$message.warning(this.$t('edge.singleoptim.createPatternFirst'))
        return false
      }
      this.globalParamModel.addParamsByType('singleoptim', this.addSingleoptim())
      this.getCycle()
      // 重新排序相位数组
      let singleoptim = this.globalParamModel.getParamsByType('singleoptim')
      singleoptim.sort(this.compareProperty('pattern'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
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
              }
            }
          }
          if (phase.ring === 2) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[1]) {
              if (obj.id === phase.id) {
                obj.desc = list
              }
            }
          }
          if (phase.ring === 3) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[2]) {
              if (obj.id === phase.id) {
                obj.desc = list
              }
            }
          }
          if (phase.ring === 4) {
            let list = this.getPhaseDescription(phase.direction)
            for (let obj of pattern.rings[3]) {
              if (obj.id === phase.id) {
                obj.desc = list
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
      let list = []
      for (let id of phaseList) {
        let obj = {}
        obj.id = id
        obj.color = '#454545'
        list.push(obj)
      }
      return list
    },
    setSubList (rowIndex, subIndex, list) {
      this.singleoptim[rowIndex].phasestaticweight.splice(subIndex, 1, list)
    }
  }
}
</script>

<style lang="scss" scoped>
  .board {
    width: 100%;
    margin-left: 10px;
    display: flex;
    justify-content: space-around;
    flex-direction: row;
    align-items: flex-start;
  }
</style>
