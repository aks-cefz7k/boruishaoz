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
  <div class="manual-control-modal">
    <div class="title" style="margin-bottom: 18px;">{{$t('openatccomponents.overview.manual')}}</div>
    <el-row>
      <el-col :span="24">
         <el-form
           ref="manual"
           label-position="left"
           :model="manualInfo"
           label-width="100px">
            <el-form-item
                :label="$t('openatccomponents.overview.controlnumber') + ':'"
                prop="shape">
                <el-input-number v-model="manualInfo.tempPatternid" :controls="false" size="mini" :max="65535" :min="0" :precision="0" :step="1" :placeholder="$t('openatccomponents.common.input')"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.overview.delay') + ':'"
                prop="intersection">
              <el-input-number v-model="manualInfo.tempDelay" :controls="false" size="mini" :max="65535" :min="0" :precision="0" :step="1" :placeholder="$t('openatccomponents.common.input')"></el-input-number>
            </el-form-item>
            <el-form-item
                :label="$t('openatccomponents.overview.duration') + ':'"
                prop="count">
              <el-input-number v-model="manualInfo.tempDuration" :controls="false" size="mini" :max="65535" :min="0" :precision="0" :step="1" :placeholder="$t('openatccomponents.common.input')"></el-input-number>
            </el-form-item>
           </el-form>
      </el-col>
    </el-row>
    <el-row>
        <div class="model-label">{{$t('openatccomponents.overview.mode')}}:</div>
        <div style="width: 100%; overflow: hidden;">
          <div class="control-model" v-for="(item, index) in modelList" :key="index">
            <div class="single-model"
            @click="selectModel(item.id)"
            :class="(preselectModel == item.id && preselectStages == -1)? 'single-model-select' : ''"
            v-if="funcSort === 'allFunc' || (funcSort === 'basicFunc'&& basicFuncControlId.indexOf(item.id) !== -1)">
              <svg-icon :icon-class="item.iconClass" className="model-icon"></svg-icon>
              <div class="single-model-name">{{$t('openatccomponents.overview.modelList' + item.id)}}</div>
            </div>
          </div>
        </div>
        <div class="stage-label">{{$t('openatccomponents.overview.stage')}}:</div>
        <div style="width: 100%; height: auto;overflow: hidden;">
          <div class="control-model" v-for="(item, index) in stagesList" :key="index">
            <div class="single-model" @click="selectStages(index + 1)" :class="preselectStages == index + 1 ? 'single-model-select' : ''">
              <xdrdirselector Width="40px" Height="40px" :showlist="item" :roadDirection="roadDirection"></xdrdirselector>
              <div style="display:flex;flex-direction:row;justify-content:center;align-items:center;">
                <div class="current-stage-num" style="width:20%;">{{index + 1}}</div>
                <div style="width:70%;">
                  <i class="iconfont icon-BRT" style="font-size:11PX;color:#606266;" v-if="item[item.length-1].controltype === 4"></i>
                  <i class="iconfont icon-feijidongche" style="font-size:11PX;color:#606266;" v-if="item[item.length-1].controltype === 6"></i>
                  <i class="iconfont icon-gongjiaoche" style="font-size:11PX;color:#606266;" v-if="item[item.length-1].controltype === 3"></i>
                  <i class="iconfont icon-youguidianche" style="font-size:11PX;color:#606266;" v-if="item[item.length-1].controltype === 5"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="stage-label" v-if="funcSort === 'allFunc'">{{$t('openatccomponents.overview.specialcontrol')}}:</div>
        <div style="width: 100%; height: auto;overflow: hidden;" v-if="funcSort === 'allFunc'">
          <div class="control-model" v-for="(item, index) in specialcontrolList" :key="index">
            <div class="single-model" @click="selectSpecialStages(item.id)">
              <svg-icon :icon-class="item.iconClass" className="closephase-icon"></svg-icon>
              <div class="single-model-name">{{$t('openatccomponents.overview.modelList' + item.id)}}</div>
            </div>
          </div>
        </div>
    </el-row>
    <div class="footer" v-if="realtimeStatusModalvisible">
        <el-button @click="handleClose()">{{$t('openatccomponents.button.Back')}}</el-button>
        <el-button type="primary" @click="handleManualControl()">{{$t('openatccomponents.overview.implement')}}</el-button>
    </div>
    <div class="footer" v-if="!realtimeStatusModalvisible">
        <el-button type="primary" @click="handleManualControl()">{{$t('openatccomponents.overview.comfirm')}}</el-button>
    </div>
  </div>
</template>

<script>
import xdrdirselector from '../../XRDDirSelector/XRDDirSelector'
export default {
  name: 'manualControl',
  components: {
    xdrdirselector
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
    realtimeStatusModalvisible: {
      type: Boolean,
      default: true
    },
    funcSort: {
      type: String,
      default: 'allFunc'
    },
    roadDirection: {
      type: String
    }
  },
  data () {
    return {
      basicFuncControlId: [0, 1, 4, 5], // 基础功能包含的控制方式： 自主控制（手动下）、黄闪、步进、定周期
      visible: this.Visible,
      manualInfo: {
        tempPatternid: 0, // 控制方式手动操作的情况下的控制编号的临时值。
        tempDelay: 0, // 控制方式手动操作的情况下的延迟时间的临时值。
        tempDuration: 300 // 控制方式手动操作的情况下的持续时间的临时值。
      }
    }
  },
  methods: {
    handleClose () {
      this.$emit('closeManualModal')
    },
    handleManualControl () {
      this.$emit('patternCommit', this.manualInfo)
    },
    selectModel (id) {
      this.$emit('selectModel', id)
    },
    selectStages (value) {
      this.$emit('selectStages', value)
    },
    selectSpecialStages (id) {
      this.$emit('selectSpecialModel', id)
    }
  },
  mounted () {
    if (this.controlData.patternid) {
      this.manualInfo.tempPatternid = this.controlData.patternid
    }
    if (this.controlData.tempDelay) {
      this.manualInfo.tempDelay = this.controlData.delay
    }
    if (this.controlData.tempDuration) {
      this.manualInfo.tempDuration = this.controlData.duration
    }
  }
}
</script>
