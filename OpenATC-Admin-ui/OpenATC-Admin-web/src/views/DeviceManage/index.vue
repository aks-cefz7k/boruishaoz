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
  <div class="device-part">
    <!-- <el-button
        class="add-btn"
        type="primary"
        icon="el-icon-plus"
        @click="handleAdd">{{$t('openatc.common.add')}}</el-button>
    <ChoseIntersection ref="choseIntersection" @onSureClick="onSureClick"></ChoseIntersection> -->
    <DeviceList :isShowCheckbox="isShowCheckbox"
                :isShowOperation="isShowOperation"
                :isShowTag="isShowTag"
                :isShowAdd="isShowAdd"
                :tableHeight="tableHeight"></DeviceList>
  </div>
</template>
<script>
import ChoseIntersection from '@/components/ChoseIntersection'
import DeviceList from './deviceList'
export default {
  name: 'device',
  components: { DeviceList, ChoseIntersection },
  data () {
    return {
      isShowCheckbox: false,
      isShowOperation: true,
      isShowTag: true,
      isShowAdd: true,
      tableHeight: window.innerHeight * 0.8
    }
  },
  methods: {
    onSureClick (list) {
      console.log(list)
    },
    handleAdd () {
      let choseIntersection = this.$refs.choseIntersection
      choseIntersection.show()
    }
  }
}
</script>
