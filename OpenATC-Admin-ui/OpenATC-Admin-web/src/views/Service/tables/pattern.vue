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
  <div class="serviceroute-pattern">
    <el-table
      highlight-current-row
      ref="patternTable"
      class="patternTable"
      :data="patternTableData"
      style="width: 100%"
      @current-change="handleCurrentChange"
      max-height="700px"
    >
      <el-table-column type="index" label="#" align="center"> </el-table-column>
      <el-table-column
        prop="agentid"
        :label="$t('openatc.greenwaveoptimize.deviceid')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        :label="$t('openatc.dutyroute.controlpattern')"
        align="center"
      >
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.control"
            collapse-tags
            clearable
            filterable
            :placeholder="$t('openatc.common.placeholder')"
          >
            <el-option
              v-for="(item, index) in controlOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.pattern')"
        align="center"
      >
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.patternid"
            collapse-tags
            clearable
            filterable
            :placeholder="$t('openatc.common.placeholder')"
            @click.native="onPatternSelectClick(scope.row)"
          >
            <el-option
              v-for="(item, index) in scope.row.patternOptions"
              :key="index"
              :label="item.patterndesc"
              :value="item.patternid"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        prop="state"
        :label="$t('openatc.dutyroute.stage')"
        align="center"
      >
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
      <el-table-column
        prop="totaltime"
        :label="$t('openatc.dutyroute.lasttime')"
        align="center"
      >
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
      <span class="pattern-explain"
        >：{{ $t("openatc.dutyroute.greenpercent") }}</span
      >
      <span class="pattern-explain" style="margin-right: 15px"
        >P{{ $t("openatc.greenwaveoptimize.phase") }}</span
      >
      <div style="margin-top:5px;">
        <PatternStatus
          style="margin-bottom: 70px"
          :patternStatusList="patternStatusList"
          :barrierList="barrierList"
          ></PatternStatus>
      </div>
    </div>
     <select-control
        v-show="false"
        ref="selectControl"></select-control>
  </div>
</template>
<script>
import SelectControl from '@/views/Service/components/SelectControl'
import SelectPattern from '@/views/Service/components/SelectPattern'
import PatternStatus from '@/components/PatternStatus'
import { getTscPhase, getTscControl } from '@/api/route'
import { getMessageByCode } from '@/utils/responseMessage'
// import { getTscControl } from '@/api/route'
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
  mounted () {
    this.controlOptions = this.$refs.selectControl.options
  },
  data () {
    return {
      controlOptions: [],
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
            pattern[i].patterndesc = pattern[i].patterndes === '' ? `${this.$t('openatc.greenwaveoptimize.pattern')}${pattern[i].patternid}` : pattern[i].patterndes
          } else {
            pattern[i].patterndesc = ''
          }
          pattern[i].patternid = pattern[i].patternid === '' ? 0 : pattern[i].patternid
          pattern[i].patternOptions = [
            {
              patternid: pattern[i].patternid,
              patterndesc: pattern[i].patterndesc
            }
          ]
        }
        this.patternTableData = pattern
        this.isShowPatternStatus = false
        this.barrierList = []
        this.patternStatusList = []
      },
      deep: true
    }
  },
  methods: {
    async onPatternSelectClick (row) {
      let options = await this.getCurPattern(row.agentid)
      if (options && options.length > 0) {
        row.patternOptions = options
      }
    },
    getCurPattern (agentid) {
      // 获取当前设备所有可选方案
      return new Promise((resolve, reject) => {
        getTscControl(agentid).then(res => {
          if (!res.data.success) {
            let msg = getMessageByCode(res.data.code, this.$i18n.locale)
            if (res.data.data) {
              let errorCode = res.data.data.errorCode
              if (errorCode) {
                msg = msg + ' - ' + getMessageByCode(errorCode, this.$i18n.locale)
              }
            }
            this.$message.error(msg)
            return
          }
          let options = []
          let list = res.data.data.data.patternList
          for (let item of list) {
            let res = {
              ...item,
              agentid: this.agentid,
              pattern: item,
              patternList: list,
              patternid: item.id,
              patterndes: item.desc,
              patterndesc: item.desc === '' ? `${this.$t('openatc.greenwaveoptimize.pattern')}${item.id}` : item.desc
            }
            options.push(res)
          }
          resolve(options)
        })
      })
    },
    getCurPhase (agentid, key) {
      // 获取当前上行/下行相位选项
      this.loading = true
      return new Promise((resolve, reject) => {
        getTscPhase(agentid).then(res => {
          this.loading = false
          if (!res.data.success) {
            let msg = getMessageByCode(res.data.code, this.$i18n.locale)
            if (res.data.data) {
              let errorCode = res.data.data.errorCode
              if (errorCode) {
                msg = msg + ' - ' + getMessageByCode(errorCode, this.$i18n.locale)
              }
            }
            this.$message.error(msg)
            return
          }
          this.phaseList = res.data.data.data.phaseList
          resolve(this.phaseList)
        })
      })
    },
    async handleCurrentChange (row) {
      this.isShowPatternStatus = false
      if (row.isNew) { // 避免新增数据触发
        return false
      }
      let phaseList = await this.getCurPhase(row.agentid)
      let patternList = await this.getCurPattern(row.agentid)
      let patterns = patternList.filter(item => item.patternid === row.patternid)
      row.pattern = patterns.length > 0 ? patterns[0] : []
      if (!row.patterndesc || row.patterndesc === '') {
        this.currPatternName = `${this.$t('openatc.greenwaveoptimize.pattern')}${row.patternid}`
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
          let currPhase = phaseList.filter(p => p.id === phase.id)[0]
          if (currPhase) {
            phase.desc = currPhase.direction
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
          obj.redWidth = (currPhase.redclear / cycle * 100).toFixed(3) + '%'
          obj.yellowWidth = (currPhase.yellow / cycle * 100).toFixed(3) + '%'
          obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow) / cycle * 100).toFixed(3) + '%'
          list.push(obj)
        }
        this.patternStatusList.push(list)
      }
      if (this.patternStatusList.length > 0) {
        this.isShowPatternStatus = true
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
.serviceroute-pattern .el-table td,
.el-table th {
  padding: 7px 0;
}
.serviceroute-pattern .el-table .cell {
  line-height: 32px;
}
</style>
