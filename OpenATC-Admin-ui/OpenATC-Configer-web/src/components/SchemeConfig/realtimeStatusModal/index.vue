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
<div>
  <div class="cross-mess" style="margin-bottom: 18px;">{{$t('edge.overview.crossinfo')}}</div>
  <div class="cross-module">
    <div class="cross-content">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.crossname')}}:</div>
      <div style="margin-left: 85px;" class="cross-value">{{agentName}}</div>
    </div>
    <div class="cross-content">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.divicestate')}}:</div>
      <div
        v-show="devStatus===3"
        style="margin-left: 85px;"
        class="cross-value"
      >{{$t('edge.overview.connected')}}</div>
      <div
        v-show="devStatus===2"
        style="margin-left: 85px;"
        class="cross-value"
      >{{$t('edge.overview.offline')}}</div>
      <div
        v-show="devStatus===1"
        style="margin-left: 85px;"
        class="cross-value"
      >{{$t('edge.overview.onlineing')}}</div>
    </div>
    <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.protocoltype')}}:</div><div style="margin-left: 85px;" class="cross-value">{{protocol}}</div></div> -->
    <!-- <div class="cross-content"><div style="float: left;" class="cross-name">信号机型号:</div><div style="margin-left: 85px;" class="cross-value">XHJ-CW-GA-KSS100</div></div> -->
    <div class="cross-content">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.signalID')}}:</div>
      <div style="margin-left: 85px;" class="cross-value">{{agentId}}</div>
    </div>
    <div class="cross-content">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.signalIP')}}:</div>
      <div style="margin-left: 85px;" class="cross-value">{{ip}}</div>
    </div>
    <div class="cross-content" v-if="platform">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.platform')}}:</div>
      <div style="margin-left: 85px;" class="cross-value">{{platform}}</div>
    </div>
    <div class="cross-content">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.faultinfo')}}:</div>
      <div style="margin-left: 85px;" v-if="curFaultList.length">
        <el-tag v-if="confirmedFault.length" type="success">
          {{$t('edge.overview.confirmed')}}
          <span style="margin: 0 2px;">{{confirmedFault.length}}</span>
        </el-tag>
        <el-tag v-if="untreatedFault.length">
          {{$t('edge.overview.untreated')}}
          <span style="margin: 0 2px;">{{untreatedFault.length}}</span>
        </el-tag>
        <el-tag v-if="ignoredFault.length" type="info">
          {{$t('edge.overview.ignored')}}
          <span style="margin: 0 2px;">{{ignoredFault.length}}</span>
        </el-tag>
        <span class="fault-detail-btn" @click="showFaultDetail">{{$t('edge.overview.details')}} >></span>
      </div>
      <div
        style="margin-left: 85px;"
        class="cross-value"
        v-if="!curFaultList.length"
      >{{$t('edge.overview.nofault')}}</div>
    </div>
  </div>
  <div class="control-bottom">
    <div
      class="cross-mess"
      style="float: left;margin-top: 40px;margin-bottom: 18px;"
    >{{$t('edge.overview.controlmode')}}</div>
    <el-button
      type="primary"
      style="float: right; margin-right: 20px;margin-top: 40px;"
      size="mini"
      @click="changeStatus"
    >{{$t('edge.overview.manual')}}</el-button>
    <!-- <el-button type="primary" style="float: right; margin-right: 40px;" size="mini" @click="changeStatus" v-show="isOperation">{{$t('edge.overview.exitmanual')}}</el-button> -->
  </div>
  <div class="cross-module">
    <!-- 信号机控制状态模块 Start-->
    <div class="controlStatePart">
      <div class="bigPart">
        <el-row :gutter="7">
          <el-col :span="12">
            <div class="grid-content bg-base">
              <div class="value">{{controlData.mode}}</div>
              <div class="name">{{$t('edge.overview.controlmodel')}}</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content bg-base">
              <div class="value">{{currModel > -1 ? $t('edge.overview.modelList' + currModel) : ''}}</div>
              <div class="name">{{$t('edge.overview.curModel')}}</div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="smallPart">
        <el-row :gutter="7">
          <el-col :span="8" v-if="controlData.patternid !== undefined">
            <div class="grid-content bg-base">
              <div class="value">{{controlData.patternid}}</div>
              <div class="name">{{$t('edge.overview.controlnumber')}}</div>
            </div>
          </el-col>
          <el-col :span="8" v-if="responseTime !== undefined">
            <div class="grid-content bg-base">
              <div class="value">{{responseTime + ' ms'}}</div>
              <div class="name">{{$t('edge.overview.responseTime')}}</div>
            </div>
          </el-col>
          <el-col :span="8" v-if="controlData.duration !== undefined">
            <div class="grid-content bg-base">
              <div class="value">{{controlData.duration + ' s'}}</div>
              <div class="name">{{$t('edge.overview.duration')}}</div>
            </div>
          </el-col>
          <el-col :span="8" v-if="controlData.delay !== undefined">
            <div class="grid-content bg-base">
              <div class="value">{{controlData.delay}}</div>
              <div class="name">{{$t('edge.overview.delay')}}</div>
            </div>
          </el-col>
          <el-col :span="8" v-if="currentStage !== undefined">
            <div class="grid-content bg-base">
              <div class="value">{{currentStage}}</div>
              <div class="name">{{$t('edge.overview.curStage')}}</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 信号机控制状态模块 End-->

    <div class="cross-content" v-show="stagesList && stagesList.length > 0">
      <div style="float: left;" class="cross-name">{{$t('edge.overview.currentstage')}}:</div>
      <div style="margin-left: 85PX;">
        <div style="width: 100%; height: auto;">
          <div class="control-model" v-for="(item, index) in stagesList" :key="index">
            <div
              style="position:relative;"
              class="single-model"
              :class="currentStage == index + 1 ? 'single-model-select' : ''"
            >
              <PatternWalkSvg
                class="patternWalk"
                v-if="item[0].peddirection.includes(side.id)"
                v-for="(side, index) in sidewalkPhaseData"
                :key="side.key + '-' + index"
                :Data="side"
                :Width="'44'"
                :Height="'44'"
              />
              <xdrdirselector Width="40PX" Height="40PX" :showlist="item"></xdrdirselector>
              <div
                style="display:flex;flex-direction:row;justify-content:center;align-items:center;"
              >
                <div class="current-stage-num" style="width:20%;">{{index + 1}}</div>
                <div style="width:70%;">
                  <i
                    class="iconfont icon-BRT"
                    style="font-size:11PX;color:#606266;"
                    v-if="item[item.length-1].controltype === 4"
                  ></i>
                  <i
                    class="iconfont icon-feijidongche"
                    style="font-size:11PX;color:#606266;"
                    v-if="item[item.length-1].controltype === 6"
                  ></i>
                  <i
                    class="iconfont icon-gongjiaoche"
                    style="font-size:11PX;color:#606266;"
                    v-if="item[item.length-1].controltype === 3"
                  ></i>
                  <i
                    class="iconfont icon-youguidianche"
                    style="font-size:11PX;color:#606266;"
                    v-if="item[item.length-1].controltype === 5"
                  ></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="cross-content" v-show="closePhase && closePhase.length > 0">
      <div
        style="float: left;margin-right: 16px;"
        class="cross-name"
      >{{$t('edge.overview.phaseclose')}}:</div>
      <el-tag
        type="danger"
        size="small"
        v-for="(phase, index) in closePhase"
        :key="index"
      >{{phase.typename + $t('edge.overview.phase') + phase.id + $t('edge.overview.close')}}</el-tag>
    </div>
  </div>
</div>
</template>

<script>
import xdrdirselector from '@/components/XRDDirSelector'
import PatternWalkSvg from '@/views/overView/crossDirection/baseImg/PatternWalkSvg'
export default {
  name: 'realtimestatus',
  components: {
    xdrdirselector,
    PatternWalkSvg
  },
  props: {
    Visible: {
      type: Boolean
    },
    controlData: {
      type: Object
    },
    agentName: {
      type: String
    },
    devStatus: {
      type: Number
    },
    agentId: {
      type: String
    },
    ip: {
      type: String
    },
    platform: {
      type: String
    },
    curFaultList: {
      type: Array
    },
    currModel: {
      type: Number
    },
    responseTime: {
      type: Number
    },
    stagesList: {
      type: Array
    },
    currentStage: {
      type: Number
    },
    preselectStages: {
      type: Number
    },
    closePhase: {
      type: Array
    },
    sidewalkPhaseData: {
      type: Array
    },
    confirmedFault: {
      type: Array
    },
    ignoredFault: {
      type: Array
    },
    untreatedFault: {
      type: Array
    }
  },
  data () {
    return {
    }
  },
  methods: {
    changeStatus () {
      this.$emit('changeStatus')
    },
    showFaultDetail () {
      this.$emit('showFaultDetail')
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
