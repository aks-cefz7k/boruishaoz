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
  <div class="phasePanel">
    <el-table
      highlight-current-row
      ref="phaseTable"
      class="phaseTable"
      :data="phaseTableData"
      style="width: 100%">
      <el-table-column
        type="index"
        label="#"
        align="center">
      </el-table-column>
      <el-table-column
        prop="agentid"
        :label="$t('openatc.greenwaveoptimize.deviceid')"
        align="center">
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.forward')"
        align="center">
        <template slot-scope="scope">
          <div class="XRDDir">
            <XRDDirSelector Width="32px" Height="32px" :showlist="scope.row.forwardshowlist" v-if="scope.row.forwardphaseid" />
          </div>
          <el-select
           v-model="scope.row.forwarddesc"
           size="small"
           :loading="loading"
           @focus="getCurPhase(scope.row.agentid, 'allForwardPhase')"
           @change="changeCurPhase(scope.row, $event, 'forward')">
              <el-option v-for="(option, index) in (scope.row.allForwardPhase)" :key="index" :label="option.forwarddesc" :value="option.forwardphaseid">
                <span>{{ option.forwarddesc }}</span>
              </el-option>
          </el-select>
          <span></span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.back')"
        align="center">
        <template slot-scope="scope">
          <div class="XRDDir">
            <XRDDirSelector Width="32px" Height="32px" :showlist="scope.row.backshowlist" v-if="scope.row.backphaseid" />
          </div>
          <el-select
           v-model="scope.row.backdesc"
           size="small"
           @focus="getCurPhase(scope.row.agentid, 'allBackPhase')"
           @change="changeCurPhase(scope.row, $event, 'back')">
              <el-option v-for="option in (scope.row.allBackPhase)" :key="option.backphaseid" :label="option.backdesc" :value="option.backphaseid">
                <span>{{ option.backdesc }}</span>
              </el-option>
          </el-select>
          <span></span>
        </template>
      </el-table-column>
  </el-table>
</div>
</template>
<script>
import { getTscPhase } from '@/api/route'
import { images } from './utils.js'
import XRDDirSelector from '../../../components/XRDDirSelector/index'
export default {
  name: 'phase',
  props: {
    phaseData: {
      type: Array
    }
  },
  components: {
    XRDDirSelector
  },
  data () {
    return {
      phaseTableData: [],
      loading: false
    }
  },
  watch: {
    phaseData: {
      handler: function (val) {
        let phases = JSON.parse(JSON.stringify(val))
        for (let i = 0; i < phases.length; i++) {
          if (phases[i].forwardphaseid !== 0) {
            const forwardobj = this.getPhaseName(phases[i].forwardphasedirection)
            phases[i].forwarddesc = `${this.$t('openatc.greenwaveoptimize.phase')}${phases[i].forwardphaseid} ${forwardobj.name}`
            phases[i].forwardshowlist = this.getPhaseDescription(phases[i].forwardphasedirection)
          }
          if (phases[i].backphaseid !== 0) {
            const backobj = this.getPhaseName(phases[i].backphasedirection)
            phases[i].backdesc = `${this.$t('openatc.greenwaveoptimize.phase')}${phases[i].backphaseid} ${backobj.name}`
            phases[i].backshowlist = this.getPhaseDescription(phases[i].backphasedirection)
          }
        }
        this.phaseTableData = phases
      },
      deep: true
    }
  },
  methods: {
    changeCurPhase (row, curPhaseid, key) {
      // 改变上行/下行相位
      let data
      if (key === 'forward') {
        const desc = row.allForwardPhase.filter(ele => ele.forwardphaseid === curPhaseid)[0].forwardphasedirection
        data = {
          agentid: row.agentid,
          forwardphaseid: curPhaseid,
          forwardphasedirection: desc,
          allForwardPhase: row.allForwardPhase,
          forwardshowlist: this.getPhaseDescription(desc)
        }
      }
      if (key === 'back') {
        const desc = row.allBackPhase.filter(ele => ele.backphaseid === curPhaseid)[0].backphasedirection
        data = {
          agentid: row.agentid,
          backphaseid: curPhaseid,
          backphasedirection: desc,
          allBackPhase: row.allBackPhase,
          backshowlist: this.getPhaseDescription(desc)
        }
      }
      this.$emit('changeData', data)
    },
    getCurPhase (agentid, key) {
      // 获取当前上行/下行相位选项
      this.loading = true
      getTscPhase(agentid).then(res => {
        this.loading = false
        if (!res.data.success) {
          if (res.data.code === '4003') {
            this.$message.error(this.$t('openatc.common.devicenotonline'))
            return
          }
          this.$message.error(res.data.message)
          return
        }
        this.phaseData.forEach(ele => {
          if (ele.agentid === agentid) {
            let options
            options = res.data.data.data.phaseList.map(ele => {
              if (key === 'allForwardPhase') {
                return {
                  forwardphasedirection: ele.direction,
                  forwarddesc: `${this.$t('openatc.greenwaveoptimize.phase')}${ele.id} ${this.getPhaseName(ele.direction).name}`,
                  forwardphaseid: ele.id
                }
              }
              if (key === 'allBackPhase') {
                return {
                  backphasedirection: ele.direction,
                  backdesc: `${this.$t('openatc.greenwaveoptimize.phase')}${ele.id} ${this.getPhaseName(ele.direction).name}`,
                  backphaseid: ele.id
                }
              }
            })
            ele[key] = options
          }
        })
      })
    },
    getName (status) {
      let name = ''
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        // name = name + ',' + images[i].name
        name = name + ',' + this.$t(images[i].name)
      }
      if (name !== '') {
        const obj = {
          name: name.substr(1)
        }
        return obj
      } else {
        return {
          name: ''
        }
      }
    },
    getPhaseName (desc) {
      // 根据相位描述数组得到相位名称
      let status = []
      for (let obj of images) {
        if (desc.length > 0 && desc.includes(obj.id)) {
          status.push(1)
        } else {
          status.push(0)
        }
      }
      return this.getName(status)
    },
    getPhaseDescription (phaseList) {
      // 根据相位描述数组得到相位图标list
      let list = []
      for (let id of phaseList) {
        let obj = {}
        obj.id = id
        obj.color = '#000'
        list.push(obj)
      }
      return list
    }
  }
}
</script>

<style>
.phasePanel .el-table td, .el-table th {
    padding: 7px 0;
}
.phasePanel .el-table .cell {
    line-height: 32px;
}
.phasePanel .el-select {
  width: 70%;
  float: left;
}
</style>

<style lang="scss" scoped>
// .phasePanel {
//   border: solid 1px $--border-color-lighter;
// }
// .XRDDir {
//   float: left;
//   width: 32px;
//   height: 32px;
//   margin-right: 8px;
// }
</style>
