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
  <div class="manual-control-modal lockingphase-control">
    <div class="title" style="margin-bottom: 18px;">{{$t('openatccomponents.overview.phaselocking')}}</div>
    <el-row>
      <el-col :span="24">
         <el-form
          :inline="true"
           ref="manual"
           label-position="left"
           :model="manualInfo"
           label-width="100px">
           <el-form-item
                :label="$t('openatccomponents.phase.greenclear') + ':'"
                prop="intersection">
              <el-input-number :min="0" :max="65535" :precision="0" :step="1" :controls="false" v-model.number="manualInfo.tempGreenflash" size="mini"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.overview.duration') + ':'"
                prop="count">
              <el-input-number :min="0" :max="65535" :precision="0" :step="1" :controls="false" v-model.number="manualInfo.tempDuration" size="mini"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.overview.yellowflash') + ':'"
                prop="intersection">
              <el-input-number :min="0" :max="65535" :precision="0" :step="1" :controls="false" v-model.number="manualInfo.tempYellow" size="mini"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.overview.allred') + ':'"
                prop="intersection">
              <el-input-number :min="0" :max="65535" :precision="0" :step="1" :controls="false" v-model.number="manualInfo.tempRedclear" size="mini"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.phase.mingreen') + ':'"
                prop="count">
              <el-input-number :min="0" :max="65535" :precision="0" :step="1" :controls="false" v-model.number="manualInfo.tempMingreen" size="mini"></el-input-number>
            </el-form-item>
           </el-form>
      </el-col>
    </el-row>
    <el-row>
        <div class="model-label">{{$t('openatccomponents.overview.mode')}}:</div>
        <div style="width: 100%; overflow: hidden;margin-top: 20px;">
          <common-kanban
            v-for="ring in closePhaseRings"
            :key="ring.num" class="closephasekanban"
            :list="ring.phases"
            :header-text="$t('openatccomponents.pattern.ring') + ring.num"
            :Draggable="false"
            @handleSort="handleSort">
              <template v-slot:kanbantitle>
                <div class="col-title" v-text="$t('openatccomponents.overview.closemode')"></div>
              </template>
              <template v-slot:kanbancontent="data">
                <el-select v-model="data.element.locktype" class="col-inner"  size="small" :placeholder="$t('openatccomponents.common.select')">
                  <el-option
                    v-for="item in lockingOption"
                    :key="item.value"
                    :label="$t('openatccomponents.overview.lockingOption' + item.value)"
                    :value="item.value">
                  </el-option>
                </el-select>
            </template>
          </common-kanban>
        </div>
    </el-row>
    <div class="footer" v-if="realtimeStatusModalvisible">
        <el-button @click="handleClose()">{{$t('openatccomponents.button.Back')}}</el-button>
        <el-button type="primary" @click="handleManualControl()">{{$t('openatccomponents.overview.implement')}}</el-button>
    </div>
    <div class="footer" v-if="!realtimeStatusModalvisible">
        <el-button @click="handleClose()">{{$t('openatccomponents.button.Back')}}</el-button>
        <el-button type="primary" @click="handleManualControl()">{{$t('openatccomponents.overview.comfirm')}}</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'closePhaselControl',
  props: {
    Visible: {
      type: Boolean
    },
    controlData: {
      type: Object
    },
    modelList: {
      type: Array
    },
    stagesList: {
      type: Array
    },
    currModel: {
      type: Number
    },
    preselectModel: {
      type: Number
    },
    currentStage: {
      type: Number
    },
    preselectStages: {
      type: Number
    },
    specialcontrolList: {
      type: Array
    },
    closePhaseRings: {
      type: Array
    },
    realtimeStatusModalvisible: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      visible: this.Visible,
      manualInfo: {
        tempGreenflash: 6,
        tempDuration: 600, // 控制方式手动操作的情况下的持续时间的临时值。
        tempYellow: 3,
        tempRedclear: 2,
        tempMingreen: 15
      },
      lockingOption: [{
        value: 0
      }, {
        value: 1
      }, {
        value: 2
      }, {
        value: 3
      }]
    }
  },
  methods: {
    handleClose () {
      this.$emit('closePhaseBack')
    },
    handleManualControl () {
      let submitdata = {
        control: 22,
        data: {}
      }
      if (this.manualInfo.tempGreenflash !== undefined) {
        submitdata.data.greenflash = Number(this.manualInfo.tempGreenflash)
      }
      if (this.manualInfo.tempDuration !== undefined) {
        submitdata.data.duration = Number(this.manualInfo.tempDuration)
      }
      if (this.manualInfo.tempYellow !== undefined) {
        submitdata.data.yellow = Number(this.manualInfo.tempYellow)
      }
      if (this.manualInfo.tempRedclear !== undefined) {
        submitdata.data.redclear = Number(this.manualInfo.tempRedclear)
      }
      if (this.manualInfo.tempMingreen !== undefined) {
        submitdata.data.mingreen = Number(this.manualInfo.tempMingreen)
      }
      submitdata.data.phases = this.handleSubmitPhaseLocking()
      this.$emit('closePhaseControl', submitdata)
    },
    handleSubmitPhaseLocking () {
      let allRingsPhases = []
      for (let i = 0; i < this.closePhaseRings.length; i++) {
        let ring = this.closePhaseRings[i]
        let phase = ring.phases
        for (let j = 0; j < phase.length; j++) {
          let obj = {}
          obj.id = phase[j].id
          obj.type = phase[j].locktype
          allRingsPhases.push(obj)
        }
      }
      return allRingsPhases
    },
    handleSort (sortedlist) {
      // 重新排序数据
      let ringid = sortedlist[0].ring
      for (let i = 0; i < this.closePhaseRings.length; i++) {
        if (ringid === this.closePhaseRings[i].num) {
          this.closePhaseRings[i].phases = sortedlist
        }
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.col-inner {
  width: 100%;
  float: left;
}
.col-title {
  width: 100%;
  float: left;
}
</style>
