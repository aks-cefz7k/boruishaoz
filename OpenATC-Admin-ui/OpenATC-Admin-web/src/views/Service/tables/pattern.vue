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
      max-height="700px"
    >
      <el-table-column type="index" label="#" align="center" width="20"> </el-table-column>
      <el-table-column
        prop="name"
        :label="$t('openatc.greenwaveoptimize.devicename')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="control"
        :label="$t('openatc.dutyroute.controlpattern')"
        :formatter="formatControl"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="patterndesc"
        :label="$t('openatc.greenwaveoptimize.pattern')"
        align="center"
      >
        <!-- <template slot-scope="scope">
          <el-select
            prop="terminal"
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
        </template> -->
      </el-table-column>
      <el-table-column
        prop="state"
        :label="$t('openatc.dutyroute.content')"
        align="center"
      >
        <template slot-scope="scope">
          {{ getContent(scope.row)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="totaltime"
        :label="$t('openatc.dutyroute.lasttime')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.operation')"
        align="center"
      >
        <template slot-scope="scope">
          <el-button type="text" @click="handleConfig(scope.row)">{{
            $t("openatc.button.config")
          }}</el-button>
          <el-button type="text" @click="handleDelete(scope.row.agentid)">{{
            $t("openatc.common.delete")
          }}</el-button>
        </template>
      </el-table-column>
    </el-table>
     <select-control
        v-show="false"
        ref="selectControl"></select-control>
        <device ref="config"
                @closePhaseControl="closePhaseControl"
                @patternCommit="patternCommit">
        </device>
  </div>
</template>
<script>
import SelectControl from '@/views/Service/components/SelectControl'
import SelectPattern from '@/views/Service/components/SelectPattern'
import { getTscPhase, getTscControl } from '@/api/route'
import { getMessageByCode } from '@/utils/responseMessage'
import xdrdirselector from '@/components/XRDDirSelector'
import device from './device'
import { getTheme } from '@/utils/auth'
// import { getTscControl } from '@/api/route'
export default {
  name: 'patterns',
  components: {
    device,
    SelectControl,
    SelectPattern,
    xdrdirselector
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
      row: {},
      dirshow: [
        {
          id: 1,
          color: 'yellow'
        }, {
          id: 16,
          color: 'red'
        }
      ],
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
      let _this = this
      return new Promise((resolve, reject) => {
        getTscControl(agentid).then(res => {
          if (!res.data.success) {
            let msg = getMessageByCode(res.data.code, _this.$i18n.locale)
            if (res.data.data) {
              let errorCode = res.data.data.errorCode
              if (errorCode) {
                msg = msg + ' - ' + getMessageByCode(errorCode, _this.$i18n.locale)
              }
            }
            _this.$message.error(msg)
            return
          }
          let options = []
          let list = res.data.data.data.patternList
          _this.patternStatus = JSON.parse(JSON.stringify(list[_this.row.patternid]))
          for (let item of list) {
            let res = {
              ...item,
              agentid: this.agentid,
              pattern: item,
              patternList: list,
              patternid: item.id,
              patterndes: item.desc,
              patterndesc: item.desc === '' ? `${_this.$t('openatc.greenwaveoptimize.pattern')}${item.id}` : item.desc
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
    getContent (row) {
      let control = row.control
      let res = ''
      if (control === 22) {
        res = this.$t('openatc.greenwaveoptimize.phase')
        let phases = row.phases
        if (phases) {
          let phaseIdArr = []
          for (let dir of phases) {
            if (dir.type !== 0) {
              let phaseId = dir.id
              phaseIdArr.push(phaseId)
            }
          }
          res = res + ' ' + phaseIdArr.join('、')
        }
      } else if (control === 4) {
        let value = row.value ? row.value : 1
        res = this.$t('openatc.dutyroute.stage') + ' ' + value
      }
      return res
    },
    getShowList (row) {
      let phases = row.phases
      let res = []
      let color = 'black'
      if (getTheme() === 'dark') {
        color = 'white'
      }
      if (phases) {
        for (let dir of phases) {
          if (dir.type !== 0) {
            let phaseId = dir.id
            let phase = this.phaseList.filter(ph => ph.id === phaseId)[0]
            if (!phase) {
              continue
            }
            let direction = phase.direction
            for (let directionId of direction) {
              let item = {
                id: directionId,
                color: color
              }
              res.push(item)
            }
          }
        }
      }
      return res
    },
    async handleConfig (row) {
      console.log(row)
      this.row = row
      await this.getCurPhase(row.agentid)
      await this.getCurPattern(row.agentid)
      this.$refs.config.handleAdd(this.phaseList, this.patternStatus)
      // let data = {
      //   'greenflash': 6,
      //   'duration': 600,
      //   'yellow': 3,
      //   'redclear': 2,
      //   'mingreen': 15,
      //   'phases': [
      //     {
      //       'id': 1,
      //       'type': 0
      //     },
      //     {
      //       'id': 2,
      //       'type': 0
      //     },
      //     {
      //       'id': 3,
      //       'type': 0
      //     },
      //     {
      //       'id': 4,
      //       'type': 0
      //     },
      //     {
      //       'id': 5,
      //       'type': 1
      //     },
      //     {
      //       'id': 6,
      //       'type': 0
      //     },
      //     {
      //       'id': 7,
      //       'type': 0
      //     },
      //     {
      //       'id': 8,
      //       'type': 0
      //     }
      //   ]
      // }
      // // item.data = data
      // row.greenflash = data.greenflash
      // row.duration = data.duration
      // row.yellow = data.yellow
      // row.redclear = data.redclear
      // row.mingreen = data.mingreen
      // row.phases = data.phases
    },
    handleDelete (agentid) {
      let comfirmMsg = this.$t('openatc.greenwaveoptimize.deletedevice') + agentid
      let infoMsg = this.$t('openatc.common.tipsmodaltitle')
      this.$confirm(comfirmMsg, infoMsg, {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      }).then(() => {
        this.$emit('deleteDevice', agentid)
        this.$message.success(this.$t('openatc.common.deletesuccess'))
      }).catch(() => {
        this.$message.warning(this.$t('openatc.common.canceloperate'))
      })
      // this.$emit('deleteDevice', agentid)
    },
    formatControl (row) {
      let res = row.control
      let selectControl = this.$refs.selectControl
      if (selectControl) {
        res = selectControl.getNameById(row.control)
      }
      return res
    },
    async formatTerminal (row) {
      let res = row.terminal

      return res
    },
    closePhaseBack () {
    },
    async patternCommit (data) {
      let row = this.row
      row.control = data.control
      row.terminal = data.terminal
      row.delay = data.delay
      row.duration = data.duration
      row.value = data.value
      row.content = this.getContent(row)
      await this.getCurPattern(row.agentid)
    },
    async closePhaseControl (res) {
      let data = res.data
      let row = this.row
      row.greenflash = data.greenflash
      row.duration = data.duration
      row.yellow = data.yellow
      row.redclear = data.redclear
      row.mingreen = data.mingreen
      row.phases = data.phases
      console.log(this.patternTableData)
      console.log(this.row)
      row.content = this.getContent(row)
      await this.getCurPattern(row.agentid)
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
