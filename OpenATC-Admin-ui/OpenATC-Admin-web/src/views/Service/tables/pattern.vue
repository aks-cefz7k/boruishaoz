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
        :label="$t('openatc.dutyroute.content')"
        align="center"
      >
        <template slot-scope="scope">
          <div v-show="scope.row.control === 4">
            <el-input-number
              size="small"
              v-model="scope.row.state"
              :min="0"
              :max="99"
              :controls="true"
            ></el-input-number>
          </div>
          <div v-show="scope.row.control === 22">
            <xdrdirselector Width="40px" Height="40px" :showlist="dirshow"></xdrdirselector>
          </div>
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
  </div>
</template>
<script>
import SelectControl from '@/views/Service/components/SelectControl'
import SelectPattern from '@/views/Service/components/SelectPattern'
import { getTscPhase, getTscControl } from '@/api/route'
import { getMessageByCode } from '@/utils/responseMessage'
import xdrdirselector from '@/components/XRDDirSelector'
// import { getTscControl } from '@/api/route'
export default {
  name: 'patterns',
  components: {
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
    handleConfig (row) {
      console.log(row)
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
