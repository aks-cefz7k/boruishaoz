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
  <div class="serviceroute-addDeviceDrawer" style="border:0px solid red;">
    <div class="devs-container">
      <!-- <LockPhaselControl
        :phaseList="phaseList"
        :patternStatus="patternStatus"
        :lockPhaseBtnName="lockPhaseBtnName"
        @closePhaseBack="closePhaseBack"
        @closePhaseControl="closePhaseControl" /> -->
      <scheme-config
        ref="rightpanel"
        :agentId="agentId"
        :lockPhaseBtnName="lockPhaseBtnName"
        :statusData="patternStatus"
        :realtimeStatusModalvisible="false"
        @patternCommit="patternCommit"
        @closePhaseBack="closePhaseBack"
        @closePhaseControl="closePhaseControl" />
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
// import { getToken } from '@/utils/auth' // 验权
export default {
  name: 'deviceAdd',
  props: {
    phaseList: {
      type: Array
    },
    patternStatus: {
      type: Object
    },
    choosedDevice: {
      type: Array
    }
  },
  data () {
    return {
      lockPhaseBtnName: this.$t('openatccomponents.overview.comfirm'),
      agentId: '10001-928',
      Token: 'eyJraWQiOiIxNjQ5NDI2MzAyMTc5IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNTgyNjMwMiwiaWF0IjoxNjQ5NDI2MzAyfQ.Oj1S7wABnH7nvX4l3omhGbr7weLRieczMVN9WkdNEuY',
      boxVisible: false,
      dialogWidth: '80%',
      crossStatusData: {}, // 路口状态数据,
      devsfilter: '',
      devsData: [], // 所有设备
      tableData: [], // 过滤后表格显示的设备
      multipleSelection: [], // 选中项
      listLoading: false // 数据加载等待动画
    }
  },
  created () {
  },
  methods: {
    patternCommit (control) {
      this.$emit('patternCommit', control)
    },
    closePhaseBack () {
      this.$emit('closeAddDrawer')
    },
    closePhaseControl (data) {
      debugger
      this.$emit('closePhaseControl', data)
      this.$emit('closeAddDrawer')
    }
  }
}
</script>

<style>
.serviceroute-addDeviceDrawer .devs-table .el-table th > .cell {
  padding-left: 14px;
  padding-right: 14px;
}
</style>

<style lang="scss" scoped>
</style>
