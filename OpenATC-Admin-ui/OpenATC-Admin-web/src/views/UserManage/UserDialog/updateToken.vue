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
  <div class="update-token">
    <el-dialog
        :title="$t('openatc.common.edit')"
        :visible.sync="dialogFormVisible"
        width="560px">
        <div>
            <span>{{$t('openatc.usermanager.authorizetime')}}</span>
            <el-date-picker
                v-model="value"
                size="small"
                popper-class="common-date-popper"
                type="datetimerange"
                :disabled="true"
                :range-separator="$t('openatc.usermanager.to')"
                :start-placeholder="$t('openatc.usermanager.starttime')"
                :end-placeholder="$t('openatc.usermanager.endtime')">
            </el-date-picker>
        </div>
        <div class="authorize-token">
            <span>{{$t('openatc.token.isValid')}}：</span>
            <el-switch
              style="padding-left: 5px;"
              v-model="isValid"
              active-color="#409EFF">
            </el-switch>
        </div>
        <div class="authorize-token">
            <div>{{$t('openatc.systemsettings.desc')}}：</div>
            <div style="margin-top: 10px;">
              <el-input type="textarea" v-model="description" :rows="3"></el-input>
            </div>
        </div>
        <div class="authorize-token">
            <div>Token：</div>
            <div style="margin-top: 10px;">
              <el-input type="textarea" id="copy" v-model="token" :rows="3" ></el-input>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="onCopyClick()">{{$t('openatc.common.copy')}}</el-button>
          <el-button type="primary" @click="onEditClick()">{{$t('openatc.button.OK')}}</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { getMessageByCode } from '@/utils/responseMessage'
import {
  UpdateToken
} from '../../../api/token'
export default {
  name: 'updateToken',
  data () {
    return {
      dialogFormVisible: false,
      id: '',
      value: '',
      token: '',
      description: '',
      isValid: false
    }
  },
  props: {},
  methods: {
    show (row) {
      this.dialogFormVisible = true
      this.value = [row.startTime, row.endTime]
      this.token = row.token
      this.description = row.description
      this.isValid = row.isValid
      this.id = row.id
    },
    onEditClick () {
      let params = {
        id: this.id,
        description: this.description,
        isValid: this.isValid
      }
      this.update(params)
    },
    onCopyClick () {
      let input = document.getElementById('copy')
      input.select()
      document.execCommand('copy')
      this.$message.success(this.$t('openatc.common.copySuccess'))
    },
    update (params) {
      let _this = this
      UpdateToken(params).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let msg = this.$t('openatc.common.updatesuccess')
        this.dialogFormVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getTokenList()
          }
        })
      })
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
