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
  <div class="versioninfo">
    <el-dialog
      title="OpenATC-Admin"
      :visible.sync="dialogFormVisible"
      width="30%"
      height="300px"
      @close='closeFormDialog'>
      <div class="banben">
          <span>版本：</span><span>{{version}}</span>
      </div>
      <div class="banben" style="margin-top: 10px;">
          <span>版权所有©1995-2020苏州科达科技股份有限公司</span>
      </div>
      <div class="divider"></div>
      <div class="open-source">
          <span class="agreement" @click="handleAgreement">开源协议</span>
    </div>
    </el-dialog>
    <licenseinfo ref="licenseinfoChild"></licenseinfo>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2020/09/10
*/
import licenseinfo from './licenseInfo'
export default {
  data () {
    return {
      dialogFormVisible: false,
      version: ''
    }
  },
  name: 'versioninfo',
  components: { licenseinfo },
  props: {},
  watch: {
    deep: true
  },
  created () {
    let json = require('../../../../static/version.json')
    this.version = json.version
  },
  methods: {
    showMessage () {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.dialogFormVisible = false
    },
    handleAgreement () {
      let licenseInfoChild = this.$refs.licenseinfoChild
      licenseInfoChild.showMessage()
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.versioninfo .el-dialog__headerbtn .el-dialog__close {
    color: #ffffff;
}
.versioninfo .el-dialog__header {
    padding: 50px 30px 10px;
    height: 109px;
    background-color: $--color-primary;
    border-radius: 4px 4px 0px 0p
}
.versioninfo .el-dialog__title {
    width: 291px;
    height: 38px;
    margin-top: 40px;
    font-family: MicrosoftYaHei;
    font-size: 36px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 14px;
    letter-spacing: 0px;
    color: #ffffff;
}
.banben {
    font-family: MicrosoftYaHei;
    font-size: 14px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 14px;
    letter-spacing: 0px;
    color: #666666;
}
.divider {
    // width: 483px;
    height: 1px;
    margin-top: 15px;
    background-color: #eeeeee;
}
.open-source {
    margin-top: 25px;
    font-family: MicrosoftYaHei;
    font-size: 14px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 14px;
    letter-spacing: 0px;
    color: $--color-primary;
}
.agreement {
    cursor: pointer;
}
</style>
