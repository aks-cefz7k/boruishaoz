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
<div class="daily-record" ref="daily-container">
  <div>
    <el-button type="primary" @click="getAllDailyRecord" size="small" style="margin-bottom: 10px;">{{$t('edge.dailyrecord.uploaddailyrecord')}}</el-button>
  </div>
  <div class="daily-table" ref="daily-table">
    <el-table
        :data="recordList"
        size="mini"
        :max-height="tableHeight"
        :default-sort = "{prop: 'starttime', order: 'descending'}"
        style="width: 100%"
        id="footerBtn">
        <el-table-column
        type="index"
        width="60px"
        align="center">
        </el-table-column>
        <el-table-column
        prop="starttime"
        :label="$t('edge.dailyrecord.data')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="infotype"
        :formatter="formatterInfotype"
        :label="$t('edge.dailyrecord.infotype')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="subject"
        :formatter="formatterSubject"
        :label="$t('edge.dailyrecord.subject')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="object"
        :formatter="formatterObject"
        :label="$t('edge.dailyrecord.object')"
        align="center">
        </el-table-column>
        <el-table-column
        :formatter="formatterStatus"
        prop="status"
        :label="$t('edge.dailyrecord.operationresults')"
        align="center">
        </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
import { getDailyRecord } from '@/api/param'
import { getMessageByCode } from '@/utils/responseMessage'
import { formatStatus, formatSubject, formatInfotype, formatObject } from '@/utils/daily.js'
export default {
  name: 'dailyRecord',
  components: {},
  data () {
    return {
      tableHeight: 700,
      schfilter: '',
      recordList: []
    }
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['daily-table'].offsetHeight
      window.onresize = function () {
        _this.tableHeight = _this.$refs['daily-table'].offsetHeight
      }
    })
  },
  destroyed () {
    if (this.dataTimeoutTimer) {
      clearTimeout(this.dataTimeoutTimer)
    }
  },
  methods: {
    formatterStatus (row) {
      return formatStatus(row)
    },
    formatterSubject (row) {
      return formatSubject(row)
    },
    formatterInfotype (row) {
      return formatInfotype(row)
    },
    formatterObject (row) {
      return formatObject(row)
    },
    getAllDailyRecord () {
      this.lockScreen()
      this.dataTimeoutTimer = setTimeout(() => {
        this.unlockScreen()
      }, 30000)
      getDailyRecord().then(data => {
        this.unlockScreen()
        if (this.dataTimeoutTimer) {
          clearTimeout(this.dataTimeoutTimer)
        }
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.recordList = this.formateDateForAllFault(res.data.operationrecord)
        // this.totalCount = res
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    formateDateForAllFault (datas) {
      for (let data of datas) {
        data.starttime = this.formateDate(data.starttime)
        data.endtime = this.formateDate(data.endtime)
      }
      return datas
    },
    formateDate (gmtdate) {
      let newDate = new Date(gmtdate * 1000)
      var y = newDate.getFullYear()
      var m = newDate.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      var d = newDate.getDate()
      d = d < 10 ? ('0' + d) : d
      var h = newDate.getHours()
      h = h < 10 ? ('0' + h) : h
      var minute = newDate.getMinutes()
      minute = minute < 10 ? ('0' + minute) : minute
      var second = newDate.getSeconds()
      second = second < 10 ? ('0' + second) : second
      return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
    },
    handleFilter () {
    },
    lockScreen () {
      this.loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    },
    unlockScreen () {
      this.loading.close()
    }
  }
}
</script>

<style lang="scss" scoped>
.daily-record {
  height: calc(100% - 20px);
  padding: 0 20px;
}
.filter-container {
  // float: right;
  text-align: right;
  margin-top: 20px;
  margin-right: 20px;
}
.daily-table {
  height: calc(100% - 42px);
}
</style>
