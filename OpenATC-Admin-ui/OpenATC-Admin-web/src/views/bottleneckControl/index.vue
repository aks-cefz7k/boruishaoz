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
  <div class="overflow-detector-page">
    <div class="detector-list">
      <div class="content">
        <DetectorList
          ref="overflowDetectorList"
          :listTableData="listTableData"
          :curDetectorDevs="curDetectorDevs"
          @handleClickDetector="handleClickDetector"
          @refresh="refresh"
        ></DetectorList>
      </div>
    </div>
    <div class="device-list">
      <div class="title">{{curChoosedId}}</div>
      <el-button type="primary" class="modify-btn" @click="handleModify">{{!isModify ? $t('openatc.button.modify') : $t('openatc.button.Cancel')}}</el-button>
      <DeviceList
        v-if="resetflag"
        ref="overflowDeviceList"
        :curDetectorDevs="curDetectorDevs"
        :isModify="isModify"
        @refresh="refresh"
      ></DeviceList>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from 'vuex'
import DeviceList from './content/DeviceList'
import DetectorList from './content/DetectorList'

const { mapState } = createNamespacedHelpers('kissStore')

export default {
  data () {
    return {
      curDetectorDevs: null,
      listTableData: [],
      curChoosedId: '',
      curChoosedCrossname: '',
      isModify: false,
      resetflag: true
    }
  },
  computed: {
    ...mapState({
      kissModel: state => state.KissModelObject
    })
  },
  components: {
    DeviceList,
    DetectorList
  },
  methods: {
    handleClickDetector (choosedRow) {
      if (this.isModify) {
        this.$confirm(
          this.$t('openatc.bottleneckcontrol.switchsavereminder'),
          this.$t('openatc.common.tipsmodaltitle'),
          {
            confirmButtonText: this.$t('openatc.button.OK'),
            cancelButtonText: this.$t('openatc.button.Cancel'),
            type: 'warning'
          }
        )
          .then(() => {
            this.setDeviceList(choosedRow)
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: this.$t('openatc.common.canceloperate')
            })
          })
        return
      }
      this.setDeviceList(choosedRow)
    },
    setDeviceList (choosedRow) {
      // if (choosedRow.overflowDetectorId === this.curChoosedId) return;
      this.resetRightComponent()
      if (JSON.stringify(choosedRow) === '{}' || choosedRow === undefined) { return }
      this.curDetectorDevs = choosedRow
      this.curChoosedId = choosedRow.overflowDetectorId
    },
    refresh () {
      this.$refs.overflowDetectorList.getData().then(res => {
        let curData = res.filter(
          ele => ele.overflowDetectorId === this.curChoosedId
        )[0]
        this.setDeviceList(curData)
      })
    },
    handleModify () {
      this.isModify = !this.isModify
    },
    resetRightComponent () {
      this.resetflag = false
      this.curChoosedId = ''
      this.curDetectorDevs = {}
      this.isModify = false
      this.$nextTick(() => {
        this.resetflag = true
      })
    }
  }
}
</script>
