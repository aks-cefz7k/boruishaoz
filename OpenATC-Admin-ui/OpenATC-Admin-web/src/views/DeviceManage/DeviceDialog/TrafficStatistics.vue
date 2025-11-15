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
      <div style="float:right;margin-top: -70px;margin-right:50px;">
        <div class="dateChoosed">
          <el-date-picker v-model="date"
                          :type="dateType"
                          :picker-options="pickerOptions"
                          align="right"
                          :unlink-panels="true"
                          >
          </el-date-picker>
        </div>
          <el-button type="primary"
              icon="el-icon-search"
              @click="doSearch">查询</el-button>
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
          text: '最近15分钟',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 / 4 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近30分钟',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 / 2 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近1小时',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近2小时',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 2 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近4小时',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 4 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近1天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近2天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 2)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '今天',
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('day')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '本周',
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('week')
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '本月',
          onClick (picker) {
            const end = new Date()
            const start = moment().startOf('month')
            picker.$emit('pick', [start, end])
          }
        }]
      },
      dateType: 'datetimerange', // 日期选择器类型，进入方案评价后，日期选择器按天选择
      curascid: '',
      isDialogShow: true,
      date: [new Date().getTime() - 3600 * 1000 * 24, new Date()]
    }
  },
  methods: {
    onView (row) {
      this.isDialogShow = true
      this.dialogFormVisible = true
      this.curascid = row.agentid
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
