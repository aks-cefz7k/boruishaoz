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
    <el-dialog
        :title="$t('edge.overview.manual')"
        :visible.sync="visible"
        width="843px"
        :modal-append-to-body="false"
        :close-on-click-modal="false"
        @close='handleClose'>
        <el-row>
          <el-col :span="24">
             <el-form
               ref="manual"
               label-position="left"
               :model="manualInfo"
               label-width="100px">
                <el-form-item
                    :label="$t('edge.overview.controlnumber') + ':'"
                    prop="shape">
                    <el-input v-model="manualInfo.tempPatternid" size="mini" :placeholder="$t('edge.common.select')"></el-input>
                </el-form-item>
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
        <el-row>
            <div class="model-label">{{$t('edge.overview.mode')}}:</div>
            <div style="width: 100%; overflow: hidden;">
              <div class="control-model" v-for="(item, index) in modelList" :key="index">
                <div class="single-model"
                @click="selectModel(item.id)"
                :class="preselectModel == item.id ? 'single-model-select' : ''"
                v-if="FuncSort === 'allFunc' || (FuncSort === 'basicFunc'&& basicFuncControlId.indexOf(item.id) !== -1)">
                  <svg-icon :icon-class="item.iconClass" className="model-icon"></svg-icon>
                  <div class="single-model-name">{{$t('edge.overview.modelList' + item.id)}}</div>
                </div>
              </div>
            </div>
            <div class="stage-label">{{$t('edge.overview.stage')}}:</div>
            <div style="width: 100%; height: auto;">
              <div class="control-model" v-for="(item, index) in stagesList" :key="index">
                <div class="single-model" @click="selectStages(index + 1)" :class="preselectStages == index + 1 ? 'single-model-select' : ''">
                  <xdrdirselector Width="40px" Height="40px" :showlist="item"></xdrdirselector>
                  <div class="current-stage-num">{{index + 1}}</div>
                </div>
              </div>
            </div>
        </el-row>
        <div
        slot="footer"
        class="footer">
            <el-button @click="handleClose()">{{$t('edge.button.Cancel')}}</el-button>
            <el-button type="primary" @click="handleManualControl()">{{$t('edge.button.OK')}}</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import xdrdirselector from '@/components/XRDDirSelector'
import { mapState } from 'vuex'
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
    }
  },
  computed: {
    ...mapState({
      FuncSort: state => state.globalParam.FuncSort
    })
  },
  data () {
    return {
      basicFuncControlId: [0, 1, 4, 5], // 基础功能包含的控制方式： 自主控制（手动下）、黄闪、步进、定周期
      visible: this.Visible,
      manualInfo: {
        tempPatternid: 0, // 控制方式手动操作的情况下的控制编号的临时值。
        tempDelay: 0, // 控制方式手动操作的情况下的延迟时间的临时值。
        tempDuration: 0 // 控制方式手动操作的情况下的持续时间的临时值。
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
    }
  },
  mounted () {
    this.manualInfo.tempPatternid = this.controlData.patternid
    this.manualInfo.tempDelay = this.controlData.delay
    this.manualInfo.tempDuration = this.controlData.duration
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.manual-control-modal .el-form-item__label {
    font-size: 14px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #606266;
}
.manual-control-modal .el-dialog__footer {
  padding: 10px 72px 38px 0;
}
.manual-control-modal .el-dialog__header {
  padding: 19px 30px;
}
.manual-control-modal .el-dialog__body {
  line-height: 0px;
  padding: 30px 43px 30px 52px;
}
</style>
<style lang="scss" rel="stylesheet/scss" scoped>
.manual-control-modal {
  .el-select {
    width: 75px;
  }
  .footer {
    button {
      width: 56px;
      height: 32px;
      padding: 0;
      text-align: center;
      line-height: 30px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      font-weight: normal;
      font-stretch: normal;
    }
  }
}
</style>
