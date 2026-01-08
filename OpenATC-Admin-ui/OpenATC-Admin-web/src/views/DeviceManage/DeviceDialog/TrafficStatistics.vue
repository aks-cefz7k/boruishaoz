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
  <div class="dev-traffic-detail"
       v-if= "isDialogShow">
    <el-dialog
      :title="$t(`openatc.devicemanager.trafficStatistics`)"
      :visible.sync="dialogFormVisible"
      width="90%"
      top="5vh"
      :close-on-click-modal="false"
      @close='closeFormDialog'>
      <div class="header">
        <div class="crossName">
          <span class="lukouIcon"><i class="iconfont icon-lukou"></i></span>
          <span>{{crossName}}</span>
        </div>
        <div class="dateChoosed">
          <el-date-picker v-model="date"
                          popper-class="common-date-popper"
                          :type="dateType"
                          :picker-options="pickerOptions"
                          align="right"
                          :unlink-panels="true"
                          >
          </el-date-picker>
        </div>
          <el-button type="primary"
                    icon="el-icon-search"
                    class="searchbtn"
                    @click="doSearch">{{$t(`openatc.common.search`)}}</el-button>
      </div>
      <div class="content">
        <trafficDetector :date="date"
                        :curascid="curascid"
                        ref="trafficDetector"></trafficDetector>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import trafficDetector from '../Statistics/trafficDetector'
import moment from 'moment'
export default {
  name: 'TrafficStatistics',
  components: { trafficDetector },
  props: {
    childTitle: {
      type: String
    }
  },
  data () {
    return {
      pickerOptions: {
        shortcuts: [{
          text: this.$t(`openatc.devicemanager.last15min`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 / 4 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last30min`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 / 2 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1hour`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last2hour`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 2 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last4hour`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 4 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1day`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last2day`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 2)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1week`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1month`),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.today`),
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('day')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.thisWeek`),
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('week')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.thisMonth`),
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('month')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastDay`),
          onClick (picker) {
            const end = moment().subtract(1, 'day').endOf('day')
            const start = moment().subtract(1, 'day').startOf('day')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastWeek`),
          onClick (picker) {
            const end = moment().subtract(1, 'week').endOf('week')
            const start = moment().subtract(1, 'week').startOf('week')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastMonth`),
          onClick (picker) {
            const end = moment().subtract(1, 'month').endOf('month')
            const start = moment().subtract(1, 'month').startOf('month')
            picker.$emit('pick', [start, end])
          }
        }]
      },
      dateType: 'datetimerange', // 日期选择器类型，进入方案评价后，日期选择器按天选择
      curascid: '',
      isDialogShow: true,
      dialogFormVisible: false,
      crossName: '',
      date: [new Date().getTime() - 3600 * 1000 * 1, new Date()]
    }
  },
  methods: {
    onView (row) {
      this.isDialogShow = true
      this.dialogFormVisible = true
      this.curascid = row.agentid
      if (row.name) {
        this.crossName = row.name
      }
    },
    doSearch () {
      this.$refs.trafficDetector.refreshChart(this.curascid)
    },
    closeFormDialog () {
      this.dialogFormVisible = false
      this.isDialogShow = false
    }
  }
}
</script>
