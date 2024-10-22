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
  <div class="edgeversioninfo">
    <el-dialog
      title="OpenATC-Configer"
      :visible.sync="dialogFormVisible"
      width="30%"
      height="300px"
      @close='closeFormDialog'>
      <div class="banben">
          <span>{{$t('edge.about.version')}}：</span><span>{{version}}</span>
      </div>
      <div class="banben" style="margin-top: 10px;">
          <span>{{$t('edge.about.copyright')}}©1995-2021 {{$t('edge.about.company')}}</span>
      </div>
      <div class="banben" style="margin-top: 10px;">
          <span>{{$t('edge.about.packedtime')}}：{{ packedtime }}</span>
      </div>
      <div class="divider"></div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dialogFormVisible: false,
      version: '',
      packedtime: '' // 打包时间
    }
  },
  name: 'edgeversioninfo',
  components: { },
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
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
</style>
