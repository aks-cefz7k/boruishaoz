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
      <div class="title">{{curChoosedCrossname}}</div>
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
import { OverflowDecApi } from '@/api/overflowDetector.js'
import { getMessageByCode } from '@/utils/responseMessage'
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
      resetflag: true,
      stateMap: new Map(),
      intervalFlag: true
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
            this.IsUpdateStatus(choosedRow.id)
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
      this.IsUpdateStatus(choosedRow.id)
    },
    IsUpdateStatus (areaid, refresh) {
      if (refresh) {
        // 如果是刷新，就不能按照重复id判断，因此优先级最高
        this.GetCrossStatusTimer(areaid)
        return
      }
      if (areaid !== this.lastChoosedId) {
        // 非刷新操作，防止重复点击
        this.firstGet(areaid)
      }
    },
    GetCrossStatusTimer (areaid) {
      if (this.timer) {
        clearInterval(this.timer)
      }
      this.timer = setInterval(() => {
        if (this.intervalFlag) {
          this.GetStatusData(areaid)
        }
      }, 1000)
    },
    firstGet (areaid) {
      if (areaid === '') return
      if (areaid !== this.lastChoosedId && this.timer) {
        clearInterval(this.timer)
      }
      this.lastChoosedId = this.curChoosedId
      if (this.curDetectorDevs.overflows === undefined || this.curDetectorDevs.overflows.length === 0) return
      OverflowDecApi.GetOverflowsExecuteStatus(areaid)
        .then(data => {
          if (this.curChoosedId === areaid) {
            // 由于异步，所以只处理当前页数据请求
            if (!data.data.success) {
              this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
              return
            }
            this.statusList = data.data.data
            this.statusList.forEach(ele => {
              this.stateMap.set(ele.agentid, ele)
            })
            let areaOverflows = JSON.parse(JSON.stringify(this.curDetectorDevs.overflows))
            if (areaOverflows && areaOverflows.length) {
              for (let i = 0; i < areaOverflows.length; i++) {
                let crossState = this.stateMap.get(areaOverflows[i].intersectionid)
                if (crossState) {
                  areaOverflows[i].statedata = crossState
                }
              }
            }
            this.curDetectorDevs.overflows = JSON.parse(JSON.stringify(areaOverflows))
            this.GetCrossStatusTimer(areaid)
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    GetStatusData (areaid, firstget) {
      if (areaid === '') return
      this.intervalFlag = false
      if (this.curDetectorDevs.overflows === undefined || this.curDetectorDevs.overflows.length === 0) return
      OverflowDecApi.GetOverflowsExecuteStatus(areaid)
        .then(data => {
          this.intervalFlag = true
          if (!data.data.success) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          this.statusList = data.data.data
          this.statusList.forEach(ele => {
            this.stateMap.set(ele.agentid, ele)
          })
          let areaOverflows = JSON.parse(JSON.stringify(this.curDetectorDevs.overflows))
          if (areaOverflows && areaOverflows.length) {
            for (let i = 0; i < areaOverflows.length; i++) {
              let crossState = this.stateMap.get(areaOverflows[i].intersectionid)
              if (crossState) {
                areaOverflows[i].statedata = crossState
              }
            }
          }
          this.curDetectorDevs.overflows = JSON.parse(JSON.stringify(areaOverflows))
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    setDeviceList (choosedRow) {
      this.resetRightComponent()
      if (JSON.stringify(choosedRow) === '{}' || choosedRow === undefined) {
        this.curChoosedCrossname = ''
        return
      }
      this.curDetectorDevs = choosedRow
      this.curChoosedId = choosedRow.id
      this.curChoosedCrossname = choosedRow.description
    },
    refresh () {
      this.$refs.overflowDetectorList.getData().then(res => {
        let curData = res.filter(
          ele => ele.id === this.curChoosedId
        )[0]
        this.setDeviceList(curData)
        this.IsUpdateStatus(this.curChoosedId, 'refresh')
      })
    },
    handleModify () {
      if (!this.curDetectorDevs) return
      if (!this.isModify && this.isHasExecutingCross()) {
        this.$message.error(this.$t('openatc.bottleneckcontrol.hasexecutecross'))
        return
      }
      this.isModify = !this.isModify
    },
    isHasExecutingCross () {
      // 判断是否有正在执行的路口
      let isHasExecute = false
      let areaOverflows = JSON.parse(JSON.stringify(this.curDetectorDevs.overflows))
      if (areaOverflows && areaOverflows.length) {
        areaOverflows.forEach(cross => {
          if (cross.statedata) {
            if (cross.statedata.control === 20 || cross.statedata.control === 13) {
              isHasExecute = true
            }
          }
        })
      }
      return isHasExecute
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
  },
  destroyed () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>
