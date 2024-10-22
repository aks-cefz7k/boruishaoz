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
          <span>{{$t('openatc.about.version')}}：</span><span>{{version}}</span>
      </div>
      <div class="banben" style="margin-top: 10px;">
          <span>{{$t('openatc.about.copyright')}}©1995-2021 {{$t('openatc.about.company')}}</span>
      </div>
      <div class="banben" style="margin-top: 10px;">
          <span>{{$t('openatc.about.packedtime')}}：{{ packedtime }}</span>
      </div>
      <div class="divider"></div>
      <div class="open-source">
          <span class="agreement" @click="handleAgreement">{{$t('openatc.about.opensourceagreement')}}</span>
    </div>
    </el-dialog>
    <licenseinfo ref="licenseinfoChild"></licenseinfo>
  </div>
</template>

<script>
import licenseinfo from './licenseInfo'
export default {
  data () {
    return {
      dialogFormVisible: false,
      version: '',
      packedtime: '' // 打包时间
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
    if (PACKEDTIME) {
      this.packedtime = PACKEDTIME
    }
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
</style>
