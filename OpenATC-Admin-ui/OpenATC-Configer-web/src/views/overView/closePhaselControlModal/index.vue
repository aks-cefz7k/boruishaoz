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
  <div class="manual-control-modal closephase-control">
    <div class="title" style="margin-bottom: 18px;">{{$t('edge.overview.phaseclosecontrol')}}</div>
    <el-row>
      <el-col :span="24">
         <el-form
           ref="manual"
           label-position="left"
           :model="manualInfo"
           label-width="100px">
            <el-form-item
                :label="$t('edge.overview.delay') + ':'"
                prop="intersection">
               <el-input v-model="manualInfo.tempDelay" size="mini" :placeholder="$t('edge.common.input')"></el-input>
            </el-form-item>
            <el-form-item
                :label="$t('edge.overview.duration') + ':'"
                prop="count">
               <el-input v-model="manualInfo.tempDuration" size="mini" :placeholder="$t('edge.common.input')"></el-input>
            </el-form-item>
           </el-form>
      </el-col>
    </el-row>
    <el-row style="height: 68%;">
        <div class="model-label">{{$t('edge.overview.mode')}}:</div>
        <div style="width: 100%; overflow: hidden;max-height: 90%;overflow-y: auto;margin-top: 20px;">
          <CommonKanban
            v-for="ring in closePhaseRings"
            :key="ring.num" class="closephasekanban"
            :list="ring.phases"
            :header-text="$t('edge.pattern.ring') + ring.num"
            :Draggable="false"
            @handleSort="handleSort">
              <template v-slot:kanbantitle>
                <div class="col-title" v-text="$t('edge.overview.closemode')"></div>
              </template>
              <template v-slot:kanbancontent="data">
                <el-select v-model="data.element.close" class="col-inner"  size="small" :placeholder="$t('edge.common.select')">
                  <el-option
                    v-for="item in closeOption"
                    :key="item.value"
                    :label="$t('edge.overview.closeOption' + item.value)"
                    :value="item.value">
                  </el-option>
                </el-select>
            </template>
          </CommonKanban>
        </div>
    </el-row>
    <div class="footer">
        <el-button @click="handleClose()">{{$t('edge.button.Back')}}</el-button>
        <el-button type="primary" @click="handleManualControl()">{{$t('edge.overview.implement')}}</el-button>
    </div>
  </div>
</template>

<script>
import CommonKanban from '../../../components/CommonKanban'
export default {
  name: 'closePhaselControl',
  components: {
    CommonKanban
  },
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
    }
  },
  computed: {
  },
  data () {
    return {
      visible: this.Visible,
      manualInfo: {
        tempDelay: 0, // 控制方式手动操作的情况下的延迟时间的临时值。
        tempDuration: 300 // 控制方式手动操作的情况下的持续时间的临时值。
      },
      closeOption: [{
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
        control: 23,
        delay: this.manualInfo.tempDelay !== undefined ? Number(this.manualInfo.tempDelay) : 0,
        duration: this.manualInfo.tempDuration !== undefined ? Number(this.manualInfo.tempDuration) : 0,
        data: {}
      }
      submitdata.data.phases = this.handleSubmitPhaseClose()

      this.$emit('closePhaseControl', submitdata)
    },
    handleSubmitPhaseClose (element) {
      let allRingsPhases = []
      for (let i = 0; i < this.closePhaseRings.length; i++) {
        let ring = this.closePhaseRings[i]
        let phase = ring.phases
        for (let j = 0; j < phase.length; j++) {
          let obj = {}
          obj.id = phase[j].id
          obj.close = phase[j].close
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
  },
  mounted () {
    if (this.controlData.tempDelay) {
      this.manualInfo.tempDelay = this.controlData.delay
    }
    if (this.controlData.tempDuration) {
      this.manualInfo.tempDuration = this.controlData.duration
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
