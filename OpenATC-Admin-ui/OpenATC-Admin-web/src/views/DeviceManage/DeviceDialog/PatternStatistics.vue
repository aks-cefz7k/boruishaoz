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
  <div class="dev-pattern-detail">
    <el-dialog
      :visible.sync="dialogFormVisible"
      width="80%"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      @close='closeFormDialog'>
      <div slot="title">
        <div class="tittle">{{$t(`openatc.devicemanager.patternStatistics`)}}</div>
        <div class="timepicker">
          <div class="crossName">
            <span class="lukouIcon"><i class="iconfont icon-lukou"></i></span>
            <span>{{crossName}}</span>
          </div>
          <el-date-picker
            v-model="date"
            type="datetimerange"
            popper-class="common-date-popper"
            :picker-options="pickerOptions"
            :unlink-panels="true"
            align="right">
          </el-date-picker>
          <el-button type="primary" icon="el-icon-search" @click="onDateChange" class="searchbtn">{{$t(`openatc.common.search`)}}</el-button>
        </div>

      </div>
      <div class="content">
        <PatternContent ref="patternCharts" v-if="dialogFormVisible" :ASCID="agentid" :date="date"/>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { DeleteFaultById } from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
import PatternContent from './patternContent/index'
export default {
  name: 'PatternStatistics',
  props: {
    childTitle: {
      type: String
    }
  },
  components: {
    PatternContent
  },
  data () {
    return {
      agentid: '0',
      crossName: '',
      dialogFormVisible: false,
      pickerOptions: {
        shortcuts: [{
          text: this.$t(`openatc.devicemanager.last15min`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 15 * 60 * 1000)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last30min`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 30 * 60 * 1000)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1hour`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last2hour`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 2)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last4hour`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 4)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1day`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last2day`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 2)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.last1week`),
          onClick (picker) {
            let end = new Date()
            let start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.today`),
          onClick (picker) {
            let start = new Date(new Date().setHours(0, 0, 0, 0))
            let end = new Date(new Date().setHours(23, 59, 59, 999))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastDay`),
          onClick (picker) {
            let date = new Date()
            date.setDate(date.getDate() - 1)
            let start = new Date(date.setHours(0, 0, 0, 0))
            let end = new Date(date.setHours(23, 59, 59, 999))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.thisWeek`),
          onClick (picker) {
            let start = new Date()
            let end = new Date()
            start.setDate(start.getDate() - start.getDay() + 1)
            end.setDate(start.getDate() + 6)
            picker.$emit('pick', [new Date(start.setHours(0, 0, 0, 0)), new Date(end.setHours(23, 59, 59, 999))])
            // let now = new Date()
            // let year = now.getFullYear()
            // let month = now.getMonth()
            // let nowDay = now.getDate()
            // let nowDayOfWeek = now.getDay()
            // let day = nowDayOfWeek || 7
            // let start = new Date(year, month, nowDay + 1 - day)
            // let end = new Date(year, month, nowDay + 7 - day, 59, 59, 999)
            // picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastWeek`),
          onClick (picker) {
            let start = new Date()
            let end = new Date()
            start.setDate(start.getDate() - 7 - start.getDay() + 1)
            end.setDate(start.getDate() + 6)
            picker.$emit('pick', [new Date(start.setHours(0, 0, 0, 0)), new Date(end.setHours(23, 59, 59, 999))])
          }
        }, {
          text: this.$t(`openatc.devicemanager.thisMonth`),
          onClick (picker) {
            let now = new Date()
            let year = now.getFullYear()
            let month = now.getMonth()
            let monthStartDate = new Date(year, month, 1)
            let monthEndDate = new Date(year, month + 1, 1)
            // 获得本月天数
            let days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24)
            let start = new Date(year, month, 1)
            let end = new Date(year, month, days, 59, 59, 999)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t(`openatc.devicemanager.lastMonth`),
          onClick (picker) {
            let now = new Date()
            let start = new Date(now.getFullYear(), now.getMonth() - 1, 1, 0, 0, 0, 0)
            let end = new Date(now.getFullYear(), now.getMonth(), 0, 23, 59, 59, 999)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      date: this.getDefaultDate()
    }
  },
  mounted () {
  },
  methods: {
    getDefaultDate () {
      this.now = new Date()
      this.nowDayOfWeek = this.now.getDay() // 今天本周的第几天
      this.nowDay = this.now.getDate() // 当前日
      this.nowMonth = this.now.getMonth()
      this.nowYear = this.now.getYear()
      this.nowYear += (this.nowYear < 2000) ? 1900 : 0
      let end = new Date()
      let start = new Date()
      start.setTime(start.getTime() - 3600 * 1000)
      return [start, end]
    },
    onView (list) {
      this.agentid = list.agentid
      this.dialogFormVisible = !this.dialogFormVisible
      if (list.name !== undefined) {
        this.crossName = list.name
      }
    },
    onIgnoreClick (id) {
      let _this = this
      DeleteFaultById(id).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          this.$message({
            message: this.$t('openatc.common.deletefailed'),
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getList()
          }
        })
      })
    },
    closeFormDialog () {
      this.dialogFormVisible = false
      this.date = this.getDefaultDate()
    },
    onDateChange () {
      if (this.$refs.patternCharts === undefined) return
      this.$refs.patternCharts.refreshChart()
    }
  }
}
</script>
