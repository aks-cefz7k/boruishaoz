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
  <div>
    <el-dialog
      :title="$t('edge.system.modifysystemtime')"
      :visible.sync="dialogFormVisible"
      width="600px"
      @close='closeFormDialog'>
      <div style="text-align: center;">
           <el-date-picker
            v-model="dateValue"
            type="date"
            :placeholder="$t('edge.system.selectdate')">
            </el-date-picker>
          <el-time-picker
                v-model="timeValue"
                :placeholder="$t('edge.system.anytimepoint')">
            </el-time-picker>
      </div>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">{{$t('edge.common.cancel')}}</el-button>
          <el-button type="primary" @click="ok">{{$t('edge.common.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/12/11
*/
import {
  formatDate, dateToString
} from '@/utils/dateFormat.js'
import { setSystemTime } from '@/api/system'
export default {
  data () {
    return {
      dialogFormVisible: false,
      timeValue: new Date(),
      dateValue: new Date()
    }
  },
  name: 'systemtime',
  props: {},
  created () {
  },
  methods: {
    onEditSystem (time) {
      let ss = this.getNowFormatDate()
      // let ss = '2019-11-12 12:32:25'
      if (time !== '--') {
        this.dateValue = dateToString(ss)
        this.timeValue = dateToString(ss)
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.resetForm()
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    },
    ok () {
      let str = formatDate(this.dateValue, this.timeValue)
      setSystemTime(str).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$parent.getSystemTime()
          }
        })
      })
    },
    getNowFormatDate () {
      var date = new Date()
      var seperator1 = '-'
      var seperator2 = ':'
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var strDate = date.getDate()
      var hh = date.getHours()
      var mm = date.getMinutes()
      var ss = date.getSeconds()
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      if (hh >= 1 && hh <= 9) {
        hh = '0' + hh
      }
      if (mm >= 0 && mm <= 9) {
        mm = '0' + mm
      }
      if (ss >= 0 && ss <= 9) {
        ss = '0' + ss
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate + ' ' + hh + seperator2 + mm + seperator2 + ss
      return currentdate
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
</style>
