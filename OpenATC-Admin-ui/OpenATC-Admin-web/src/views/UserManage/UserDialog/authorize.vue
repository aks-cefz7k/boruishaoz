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
  <div class="user-authorize">
    <el-dialog
        :title="$t('openatc.usermanager.authorizetitle')"
        :visible.sync="dialogFormVisible"
        width="560px">
        <div>
            <span>{{$t('openatc.usermanager.authorizetime')}}</span>
            <el-date-picker
                v-model="value"
                size="small"
                popper-class="common-date-popper"
                type="datetimerange"
                :range-separator="$t('openatc.usermanager.to')"
                :start-placeholder="$t('openatc.usermanager.starttime')"
                :end-placeholder="$t('openatc.usermanager.endtime')">
            </el-date-picker>
        </div>
        <div class="authorize-token">
            <div>{{$t('openatc.systemsettings.desc')}}：</div>
            <div style="margin-top: 10px;">
              <el-input type="textarea" v-model="description" :rows="3"></el-input>
            </div>
        </div>
        <div class="authorize-generate">
            <el-button type="primary" @click="generate" size="small">{{$t('openatc.usermanager.generate')}}</el-button>
        </div>
        <div class="authorize-token">
            <div>Token：</div>
            <div style="margin-top: 10px;">
              <el-input type="textarea" v-model="token" :rows="3"></el-input>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <!-- <el-button @click="dialogVisible = false">取 消</el-button> -->
            <el-button type="primary" @click="dialogFormVisible = false">{{$t('openatc.button.OK')}}</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2020/10/16
*/
import { AuthorizeToken } from '../../../api/user'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  components: {},
  data () {
    return {
      dialogFormVisible: false,
      value: '',
      token: '',
      description: '',
      user_name: ''
    }
  },
  name: 'authorize',
  props: {},
  methods: {
    onAuthorizeClick (user) {
      this.token = ''
      this.description = ''
      this.user_name = user.user_name
      this.dialogFormVisible = !this.dialogFormVisible
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    //   this.$refs[formData].resetFields() // 表单重置
    //   this.hidePassContain = true
    },
    generate () {
      if (this.value === '' || this.value === null) {
        this.$message.error(this.$t('openatc.usermanager.Authorization'))
        return
      }
      let startDate = this.formateDate(this.value[0])
      let endDate = this.formateDate(this.value[1])
      let param = {
        'user_name': this.user_name,
        'description': this.description,
        'start_time': startDate,
        'end_time': endDate
      }
      AuthorizeToken(param).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.token = res.data.data.token
      })
    },
    formateDate (newDate) {
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
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.authorize-generate {
  margin-top: 25px;
}
.authorize-token {
  margin-top: 25px;
}
.el-date-editor .el-range-separator {
    padding: 0 5px;
    line-height: 32px;
    width: 10%;
    color: #303133;
}
</style>
