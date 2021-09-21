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
  <div class="patternPanel">
    <el-table
      stripe
      highlight-current-row
      ref="patternTable"
      class="patternTable"
      :data="patternTableData"
      style="width: 100%"
      @current-change="handleCurrentChange"
    >
      <el-table-column type="index" label="#" align="center"> </el-table-column>
      <el-table-column prop="agentid" label="设备ID" align="center">
      </el-table-column>
      <el-table-column label="控制方式" align="center">
        <template slot-scope="scope">
          <select-control
            :defaultValue="scope.row.control"
            :agentid="scope.row.agentid"
            ref="selectControl"
            @onChange="onSelectControlChange"
          ></select-control>
        </template>
      </el-table-column>
      <el-table-column label="方案" align="center">
        <template slot-scope="scope">
          <select-pattern
            :agentid="scope.row.agentid"
            :defaultValue="scope.row.patternid"
            @onChange="onSelectPatternChange"
            ref="selectPattern"
          ></select-pattern>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="阶段" align="center">
        <template slot-scope="scope">
          <el-input-number
            size="small"
            v-model="scope.row.state"
            :min="0"
            :max="99"
            :controls="true"
          ></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="totaltime" label="持续时间(秒)" align="center">
        <template slot-scope="scope">
          <el-input-number
            size="small"
            v-model="scope.row.totaltime"
            :min="0"
            :step="60"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>
    <div class="pattern-figure" v-show="isShowPatternStatus">
      <div class="pattern-status">{{ currPatternName }}</div>
      <span class="pattern-explain">：绿信比</span>
      <span class="pattern-explain" style="margin-right: 15px">P相位</span>
      <PatternStatus
        style="margin-bottom: 50px"
        :patternStatusList="patternStatusList"
        :barrierList="barrierList"
      ></PatternStatus>
    </div>
  </div>
</template>
<script>
import SelectControl from '@/views/Service/components/SelectControl'
import SelectPattern from '@/views/Service/components/SelectPattern'
import PatternStatus from '@/components/PatternStatus'
import { getTscPhase } from '@/api/route'
export default {
  name: 'patterns',
  components: {
    SelectControl,
    SelectPattern,
    PatternStatus
  },
  props: {
    patternData: {
      type: Array
    }
  },
  data () {
    return {
      patternTableData: [], // 方案数据
      loading: false,
      phaseList: [],
      patternStatusList: [],
      barrierList: [],
      isShowPatternStatus: false,
      currPatternName: '--'
    }
  },
  watch: {
    patternData: {
      handler: function (val) {
        let pattern = JSON.parse(JSON.stringify(val))
        for (let i = 0; i < pattern.length; i++) {
          if (pattern[i].patternid !== 0) {
            pattern[i].patterndesc = pattern[i].patterndes === '' ? `方案${pattern[i].patternid}` : pattern[i].patterndes
          } else {
            pattern[i].patterndesc = ''
          }
        }
        this.patternTableData = pattern
      },
      deep: true
    }
  },
  methods: {
    onSelectControlChange (control, resAgentid) {
      let index = 0
      for (let i = 0; i < this.patternData.length; i++) {
        let agentid = this.patternData[i].agentid
        if (agentid === resAgentid) {
          index = i
          break
        }
      }
      let row = Object.assign({}, this.patternTableData[index])
      row.control = control
      this.patternTableData.splice(index, 1, row)
    },
    onSelectPatternChange (val) {
      let res = val
      let index = 0
      for (let i = 0; i < this.patternData.length; i++) {
        let agentid = this.patternData[i].agentid
        if (res.agentid === agentid) {
          index = i
          break
        }
      }
      let row = Object.assign({}, this.patternTableData[index])
      row.patternid = res.patternid
      row.patterndes = res.patterndes
      row.patterndesc = res.patterndesc
      row.pattern = res.pattern
      row.patternList = res.patternList
      this.patternTableData.splice(index, 1, row)
    },
    getCurPhase (agentid, key) {
      // 获取当前上行/下行相位选项
      this.loading = true
      return new Promise((resolve, reject) => {
        getTscPhase(agentid).then(res => {
          this.loading = false
          if (!res.data.success) {
            if (res.data.code === '4003') {
              this.$message.error('设备不在线！')
              return
            }
            this.$message.error(res.data.message)
            return
          }
          this.phaseList = res.data.data.data.phaseList
          resolve(this.phaseList)
        })
      })
    },
    async handleCurrentChange (row) {
      await this.getCurPhase(row.agentid)
      this.patternList = row.patternList
      if (!this.patternList) {
        this.$message.warning('获取方案失败!')
        return false
      }
      this.isShowPatternStatus = true
      if (row.patterndesc === '') {
        this.currPatternName = 'pattern' + row.patternid
      } else {
        this.currPatternName = row.patterndesc
      }
      this.patternStatusList = []
      let cycle = row.pattern.cycle
      let ringList = row.pattern.rings
      for (let ringPhaseList of ringList) {
        if (ringPhaseList.length === 0) {
          continue
        }
        let list = []
        for (let phase of ringPhaseList) {
          if (phase.value === 0) {
            continue
          }
          let obj = {}
          let split = phase.value
          obj.id = phase.id
          obj.split = split
          obj.direction = phase.desc.map(item => {
            return {
              id: item.id,
              color: '#454545'
            }
          })
          let currPhase = this.phaseList.filter((item) => {
            return item.id === phase.id
          })[0]
          obj.redWidth = (currPhase.redclear / cycle * 100).toFixed(3) + '%'
          obj.yellowWidth = (currPhase.yellow / cycle * 100).toFixed(3) + '%'
          obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow) / cycle * 100).toFixed(3) + '%'
          list.push(obj)
        }
        this.patternStatusList.push(list)
      }
      this.handleBarrier(this.patternStatusList, this.phaseList)
    },
    handleBarrier (patternStatusList, phaseList) {
      this.barrierList = []
      if (patternStatusList.length < 2) return
      let tempList = []
      let barrierWidth = 0
      let firstPatternStatus = patternStatusList[0]
      for (let patternStatus of firstPatternStatus) {
        let concurrent = phaseList.filter((item) => {
          return item.id === patternStatus.id
        })[0].concurrent
        if (concurrent.length === 0) {
          this.barrierList = []
          return
        }
        if (!this.isEqualsForArray(tempList, concurrent)) {
          tempList = concurrent
          this.barrierList.push(barrierWidth)
        }
        barrierWidth = Number.parseFloat(barrierWidth) + Number.parseFloat(patternStatus.redWidth) + Number.parseFloat(patternStatus.yellowWidth) + Number.parseFloat(patternStatus.greenWidth) + '%'
      }
      this.barrierList.push(barrierWidth) // 添加末尾处的屏障
    },
    isEqualsForArray (listA, listB) {
      return listA.length === listB.length &&
        listA.every(a => listB.some(b => a === b)) &&
        listB.every(_b => listA.some(_a => _a === _b))
    }
  }
}
</script>

<style>
.patternPanel .el-table td,
.el-table th {
  padding: 7px 0;
}
.patternPanel .el-table .cell {
  line-height: 32px;
}
</style>

<style scoped>
.patternPanel {
  border: solid 1px #e6e6e6;
}
.pattern-figure {
  position: fixed;
  width: 88%;
  bottom: 30px;
}
.pattern-status {
  display: inline;
  font-family: SourceHanSansCN-Regular;
  font-size: 20px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 22px;
  letter-spacing: 0px;
  color: #303133;
}
.pattern-explain {
  float: right;
}
</style>
