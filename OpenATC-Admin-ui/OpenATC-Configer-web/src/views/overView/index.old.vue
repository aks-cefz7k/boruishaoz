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
  <div class="container-main">
    <FaultDetailModal ref="faultDetail" :agentId="agentId" @refreshFault="getFaultById"></FaultDetailModal>
    <div :style="{'transform': `scale(${shrink})`, 'transform-origin': 'left top', 'height': '100%'}">
      <div class="wenzijiemian" v-show="!isShowGui">
        <div class="container-left">
          <div class="container-left-top">
            <div class="agent-div">
              <div style="height: 210px;">
                <div class="agent-icon">
                  <div v-show="devStatus===3"><div class="yuanxing" style="background: rgba(64, 158, 255, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #409eff;"></div><div class="dev-status" style="color: #409eff; left: 40px;">{{$t('edge.overview.online')}}</div></div>
                  <div v-show="devStatus===2"><div class="yuanxing" style="background: rgba(179, 179, 179, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #b3b3b3;"></div><div class="dev-status" style="color: #b3b3b3; left: 40px;">{{$t('edge.overview.offline')}}</div></div>
                  <div v-show="devStatus===1"><div class="yuanxing" style="background: rgba(230, 162, 60, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #e6a23c;"></div><div class="dev-status" style="color: #e6a23c; left: 30px;">{{$t('edge.overview.onlineing')}}</div></div>
                </div>
                <div class="agent-num">
                  <div class="agent-id">{{$t('edge.overview.ipaddress')}}</div>
                  <div class="agent-number">{{ip}}</div>
                  <div class="agent-port">{{$t('edge.overview.deviceport')}}</div>
                  <div class="port-number">{{port}}</div>
                  <div class="agent-port">{{$t('edge.overview.agreement')}}</div>
                  <div class="port-number">{{protocol}}</div>
                </div>
              </div>
              <div style="height: 210px;">
                <div style="float: left; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.current_stage}}</div>
                  <div class="curr-num">{{$t('edge.overview.currentstage')}}</div>
                </div>
                <div style="float: right; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.total_stages}}</div>
                  <div class="curr-num">{{$t('edge.overview.allstagesnum')}}</div>
                </div>
                <div style="float: left; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.patternid}}</div>
                  <div class="curr-num">{{$t('edge.overview.patternnum')}}</div>
                </div>
                <div style="float: right; height: 50%; width: 50%; text-align: center;">
                  <!-- <div class="curr-grade">{{controlData.name}}</div>
                  <div class="curr-num">{{$t('edge.overview.patternname')}}</div> -->
                </div>
              </div>
            </div>
            <div class="other-div" v-for="(item, index) in showList" :key="index">
              <div style="height: 70px;">
                <div class="model-tupian"><svg-icon :icon-class="item.iconClass" className="model-icon"></svg-icon></div><div class="model-name">{{$t('edge.overview.showlist' + item.label)}}</div>
                <div class="to-detail" v-if="item.name === '当前/剩余时间' || item.name === '实时流量'" @click="handleChangeTable(item.name)">{{$t('edge.overview.details')}}</div>
              </div>
              <div>
                <div class="control-center" v-if="!item.insertHtml">{{item.value}}</div>
                <div class="control-time" v-if="item.insertHtml">
                  <span class="time">{{curTime}}</span><span class="time">{{syncTime}}</span>
                </div>
              </div>
            </div>
          </div>
          <!-- 实时相位表格 -->
          <div class="container-left-bottom" v-if="curTable === 'phase'">
            <CurPhase ref="currentPhase" :controlData="controlData" :tableData="tableData"/>
          </div>
          <!-- 实时流量表格 -->
          <div class="container-left-bottom" v-if="curTable === 'currentvolume'">
            <CurVolume :volumeData="volumeData"/>
          </div>
        </div>
        <div class="container-right">
          <div class="control-right">
            <el-form ref="form" :model="form" label-width="110px">
              <el-form-item :label="$t('edge.control.mode_style')" v-if="form.mode != ''">
                  <div>{{form.mode}}</div>
                </el-form-item>
                <el-form-item :label="$t('edge.control.control_style')">
                    <el-select v-model="control" :placeholder="$t('edge.common.select')">
                        <el-option :label="$t('edge.overview.autocontrol')" value="0"></el-option>
                        <el-option :label="$t('edge.overview.yellowflash')" value="1"></el-option>
                        <el-option :label="$t('edge.overview.allred')" value="2"></el-option>
                        <el-option :label="$t('edge.overview.step')" value="4"></el-option>
                        <el-option :label="$t('edge.overview.fixedperiod')" value="5"></el-option>
                        <el-option :label="$t('edge.overview.inductioncontrol')" value="6"></el-option>
                        <el-option :label="$t('edge.overview.selfadaption')" value="9"></el-option>
                        <el-option :label="$t('edge.overview.nocablecoordination')" value="10"></el-option>
                        <el-option :label="$t('edge.overview.phasewalk')" value="12"></el-option>
                        <el-option :label="$t('edge.overview.websteroptimization')" value="14"></el-option>
                        <el-option :label="$t('edge.overview.inductivePedestrianCrossControl')" value="19"></el-option>
                        <el-option :label="$t('edge.overview.custom')" value="999"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.controlmodevalue')">
                    <el-input v-model="controlNum" style="width: 70%" :disabled="control!=='999'"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.control.pattern')">
                    <el-input v-model="form.terminal" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.control.value')">
                    <el-input v-model="form.value" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.delay')">
                    <el-input v-model="form.delay" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.duration')">
                    <el-input v-model="form.duration" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.extendedContent')">
                    <el-input class="jsontextarea" type="textarea" v-model="form.data" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" size="small">{{$t('edge.common.setup')}}</el-button>
                    <el-button type="primary" @click="onGet" size="small">{{$t('edge.common.query')}}</el-button>
                </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
      <div class="tuxingjiemian" v-show="isShowGui" :class="{'minifont': curBodyWidth <= 650}">
        <div class="tuxing-left" :class="{'changeWidth': graphicMode}" ref="tuxingLeft">
          <div class="crossDirection-display" :class="{'superlargeCrossImg': curBodyWidth <= 1680 && curBodyWidth > 1440,
            'largeCrossImg': curBodyWidth <= 1440 && curBodyWidth > 1280,
            'middleCrossImg': curBodyWidth <= 1280 && curBodyWidth > 960,
            'smallCrossImg': curBodyWidth <= 960 && curBodyWidth > 720,
            'miniCrossImg': curBodyWidth <= 720 && curBodyWidth > 650,
            'superminiCrossImg': curBodyWidth <= 650 && curBodyWidth > 350,
            'minimumCrossImg': curBodyWidth <= 350,
            'changePaddingBottom': graphicMode }">
            <CrossDiagram v-if="reset" :crossStatusData="crossStatusData" :agentId="agentId" :devStatus="devStatus"/>
          </div>
          <div class="pattern-status" v-if="!graphicMode">
            <div class="pattern-name cross-mess">{{$t('edge.overview.patternstate')}}</div>
            <div class="pattern-message">({{$t('edge.overview.cycle')}}: {{controlData.cycle}}  {{$t('edge.overview.patternoffset')}}: {{controlData.patternoffset}} {{$t('edge.overview.coordinationtime')}}: {{controlData.offset}})</div>
            <span class="pattern-explain">：{{$t('edge.overview.phasesplit')}}</span>
            <span class="pattern-explain" style="margin-right: 15px;">P{{$t('edge.overview.phase')}}</span>
            <BoardCard
            ref="boardCard"
            :cycle="crossStatusData ? crossStatusData.cycle : 0"
            :syncTime="crossStatusData ? crossStatusData.syncTime : 0"
            :controlData="controlData"
            :phaseList="phaseList"
            :isPhase="true"
            :agentId="agentId"
              >
            </BoardCard>
          </div>
        </div>
        <div class="tuxing-right" v-if="!graphicMode" ref="tuxingRight">
          <div class="scheme-config">
          <transition name="fade-right" mode="out-in"
          :enter-active-class="toPage === 1 ? 'animated fadeInRight' : 'animated fadeInLeft'"
          :leave-active-class="toPage === 1 ? 'animated fadeOutRight' : 'animated fadeOutLeft' ">
            <div style="position: absolute;width: 100%;"  v-show="(isOperation && !isClosePhase)">
             <ManualControlModal
               :controlData="controlData"
               :modelList="modelList"
               :stagesList="stagesList"
               :specialcontrolList="specialcontrolList"
               :currModel="currModel"
               :preselectModel="preselectModel"
               :currentStage="currentStage"
               :preselectStages="preselectStages"
               @closeManualModal="closeManualModal"
               @selectModel="selectModel"
               @selectStages="selectStages"
               @patternCommit="patternCommit"
               @selectSpecialModel="selectSpecialModel" />
            </div>
          </transition>
          <transition name="fade-left" mode="out-in"
          enter-active-class="animated fadeInRight"
          leave-active-class="animated fadeOutRight">
            <div style="position: absolute;width: 100%;" v-show="(isOperation && isClosePhase)">
              <ClosePhaseControlModal
                v-if="specialPage === 'closephase'"
                :controlData="controlData"
                :closePhaseRings="phaseRings"
                @closePhaseBack="closePhaseBack"
                @closePhaseControl= "closePhaseControl" />
              <LockingPhaseControlModal
                v-if="specialPage === 'lockingphase'"
                :controlData="controlData"
                :closePhaseRings="phaseRings"
                @closePhaseBack="closePhaseBack"
                @closePhaseControl= "closePhaseControl" />
            </div>
          </transition>

          <transition name="fade-left" mode="out-in"
          enter-active-class="animated fadeInLeft"
          leave-active-class="animated fadeOutLeft">

            <div class="right-control-part" style="position: absolute;width: 100%;" v-show="!isOperation">
              <div class="cross-mess" style="margin-bottom: 18px;">{{$t('edge.overview.crossinfo')}}</div>
              <div class="cross-module">
                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.crossname')}}:</div><div style="margin-left: 85px;" class="cross-value">{{agentName}}</div></div>
                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.divicestate')}}:</div>
                  <div v-show="devStatus===3" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.connected')}}</div>
                  <div v-show="devStatus===2" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.offline')}}</div>
                  <div v-show="devStatus===1" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.onlineing')}}</div>
                </div>
                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.protocoltype')}}:</div><div style="margin-left: 85px;" class="cross-value">{{protocol}}</div></div> -->
                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">信号机型号:</div><div style="margin-left: 85px;" class="cross-value">XHJ-CW-GA-KSS100</div></div> -->
                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.signalID')}}:</div><div style="margin-left: 85px;" class="cross-value">{{agentId}}</div></div>
                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.signalIP')}}:</div><div style="margin-left: 85px;" class="cross-value">{{ip}}</div></div>
                <div class="cross-content" v-if="platform"><div style="float: left;" class="cross-name">{{$t('edge.overview.platform')}}:</div><div style="margin-left: 85px;" class="cross-value">{{platform}}</div></div>
                <div class="cross-content">
                  <div style="float: left;" class="cross-name">{{$t('edge.overview.faultinfo')}}:</div>
                  <div style="margin-left: 85px;" v-if="curFaultList.length">
                    <el-tag v-if="confirmedFault.length" type="success">{{$t('edge.overview.confirmed')}}<span style="margin: 0 2px;">{{confirmedFault.length}}</span></el-tag>
                    <el-tag v-if="untreatedFault.length">{{$t('edge.overview.untreated')}}<span style="margin: 0 2px;">{{untreatedFault.length}}</span></el-tag>
                    <el-tag v-if="ignoredFault.length" type="info">{{$t('edge.overview.ignored')}}<span style="margin: 0 2px;">{{ignoredFault.length}}</span></el-tag>
                    <span class="fault-detail-btn" @click="showFaultDetail">{{$t('edge.overview.details')}} >></span>
                  </div>
                  <div style="margin-left: 85px;" class="cross-value" v-if="!curFaultList.length">{{$t('edge.overview.nofault')}}</div>
                </div>
              </div>
              <div class="control-bottom">
                <div class="cross-mess" style="float: left;margin-top: 40px;margin-bottom: 18px;">{{$t('edge.overview.controlmode')}}</div>
                <el-button type="primary" style="float: right; margin-right: 20px;margin-top: 40px;" size="mini" @click="changeStatus">{{$t('edge.overview.manual')}}</el-button>
                <!-- <el-button type="primary" style="float: right; margin-right: 40px;" size="mini" @click="changeStatus" v-show="isOperation">{{$t('edge.overview.exitmanual')}}</el-button> -->
              </div>
              <div class="cross-module">
                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.controlmodel')}}:</div><div style="margin-left: 85px;" class="cross-value">{{controlData.mode}}</div></div>
                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.curModel')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value">{{currModel > -1 ? $t('edge.overview.modelList' + currModel) : ''}}</div>
                </div> -->
                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.patternname')}}:</div><div style="margin-left: 85px;" class="cross-value">{{controlData.name}}</div></div> -->
                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.controlnumber')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.patternid}}</div>
                </div> -->

                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.delay')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.delay}}</div>
                </div>

                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.duration')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.duration}}</div>
                </div> -->

                <!-- <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.curStage')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value">{{currentStage}}</div>
                </div>

                <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.responseTime')}}:</div>
                  <div style="margin-left: 85px;" class="cross-value">{{responseTime + ' ms'}}</div>
                </div> -->

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
                  <div style="margin-left: 85PX;" >
                    <div style="width: 100%; height: auto;">
                      <div class="control-model" v-for="(item, index) in stagesList" :key="index">
                        <div style="position:relative;" class="single-model" :class="currentStage == index + 1 ? 'single-model-select' : ''">
                          <PatternWalkSvg class="patternWalk" v-if="item[0].peddirection.includes(side.id)" v-for="(side, index) in sidewalkPhaseData" :key="side.key + '-' + index" :Data="side" :Width="'55'" :Height="'55'" />
                          <xdrdirselector Width="40PX" Height="40PX" :showlist="item"></xdrdirselector>
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
                  </div>
                </div>

                <div class="cross-content" v-show="closePhase && closePhase.length > 0">
                  <div style="float: left;margin-right: 16px;" class="cross-name">{{$t('edge.overview.phaseclose')}}:</div>
                  <el-tag type="danger" size="small" v-for="(phase, index) in closePhase" :key="index">{{phase.typename + $t('edge.overview.phase') + phase.id + $t('edge.overview.close')}}</el-tag>
                </div>

              </div>
            </div>
          </transition>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getTscControl, putTscControl, getTscPattern, getTscCurrentVolume, queryDevice } from '@/api/control'
import { registerMessage, uploadSingleTscParam } from '@/api/param'
import { setIframdevid } from '@/utils/auth'
import FloatImgBtn from '@/components/FloatImgBtn'
import CrossDiagram from './crossDirection/crossDiagram'
// import PatternStatus from '@/components/PatternStatus'
// import StageStatus from '@/components/StageStatus'
import BoardCard from '@/components/BoardCard'
import CurVolume from './textPage/currentVolume'
import CurPhase from './textPage/currentPhase'
import ManualControlModal from './manualControlModal'
import ClosePhaseControlModal from './closePhaselControlModal'
import LockingPhaseControlModal from './lockingPhaselControlModal'
import { getFaultMesZh, getFaultMesEn } from '../../utils/faultcode.js'
import { getMessageByCode } from '../../utils/responseMessage'
import { GetAllFaultRange } from '@/api/fault'
import FaultDetailModal from '@/components/FaultDetailModal'
import xdrdirselector from '@/components/XRDDirSelector'
import PatternWalkSvg from '@/views/overView/crossDirection/baseImg/PatternWalkSvg'
import PhaseDataModel from '@/views/overView/crossDirection/utils'
import CrossDiagramMgr from '@/EdgeMgr/controller/crossDiagramMgr'
export default {
  name: 'overview',
  components: {
    FloatImgBtn,
    CrossDiagram,
    PatternWalkSvg,
    // PatternStatus,
    // StageStatus,
    BoardCard,
    CurVolume,
    CurPhase,
    ManualControlModal,
    ClosePhaseControlModal,
    LockingPhaseControlModal,
    FaultDetailModal,
    xdrdirselector
  },
  data () {
    return {
      controlData: {},
      control: '',
      sidewalkPhaseData: [],
      form: {
        // control: '',
        terminal: '',
        mode: '',
        value: '',
        delay: '',
        duration: ''
      },
      controlNum: '',
      spanArr: [],
      list: [{
        iconClass: 'model',
        name: '控制模式',
        value: '--'
      }, {
        iconClass: 'cycle',
        name: '周期',
        value: '--'
      }, {
        iconClass: 'time',
        name: '当前时间',
        value: '--'
      }, {
        iconClass: 'maincontrol',
        name: '控制方式',
        value: '--'
      }, {
        iconClass: 'phasediff',
        name: '相位差',
        value: '--'
      }, {
        iconClass: 'time',
        name: '剩余时间',
        value: '--'
      }],
      tableData: [],
      phaseControlTimer: null, // 定时器
      registerMessageTimer: null, // 延时器
      volumeControlTimer: null, // 流量定时器
      faultTimer: null, // 当前故障定时器
      ParamsMap: new Map([['控制模式', 'mode'], ['周期', 'cycle'], ['控制方式', 'control'], ['相位差', 'offset'], ['当前时间', 'curTime'], ['剩余时间', 'syncTime']]),
      ParamsMode: new Map([[0, '自主控制'], [1, '本地手动'], [2, '平台控制'], [3, '配置软件控制'], [4, '遥控器控制'], [5, '黄闪器控制'], [6, '降级控制'], [7, '脚本控制'], [8, '算法控制']]),
      ParamsModeEn: new Map([[0, 'Autonomous Control'], [1, 'Local Manual'], [2, 'Platform Control'], [3, 'Configuration Software Control'], [4, 'Remote Control'], [5, 'Yellow Flasher Control'], [6, 'Degradation Control'], [7, 'Script Control'], [8, 'Algorithm Control']]),
      ParamsControl: new Map([[0, '多时段'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '无电缆控制'], [11, '有电缆控制'], [12, '行人过街控制'], [13, '方案恢复过渡'], [14, '相位驻留'], [15, '通道检测'], [16, '方向锁定'], [17, '韦伯斯特单点控制'], [18, '预留18'], [19, '感应式行人过街'], [20, '方案干预'], [100, '方案干预'], [21, '预留21'], [22, '相位锁定'], [23, '相位放行控制'], [24, '紧急控制'], [99, '设备维护']]),
      ParamsControlEn: new Map([[0, 'Multi Period'], [1, 'Yellow Flash Control'], [2, 'Red Control'], [3, 'Dark Control'], [4, 'Step'], [5, 'Fixed_Cycle Control'], [6, 'Free Control'], [7, 'Coordinated Induction Control'], [8, 'Pattern Selection Control'], [9, 'Adaptive Control'], [10, 'Wireless Control'], [11, 'Cable Control'], [12, 'Pedestrian Crossing Control'], [13, 'Pattern recovery'], [14, 'Phase dwell'], [15, 'Channel Detection'], [16, 'Direction lock'], [17, 'Webster Single Point Coordination'], [18, 'Reserving18'], [19, 'Inductive Pedestrian Crossing Control'], [20, 'Program Intervention'], [100, 'Program Intervention'], [21, 'Reserving21'], [22, 'Priority Control'], [23, 'Close Phase'], [24, 'Emergency Control'], [99, 'Device Mantenance']]),
      phaseType: new Map([[1, '红'], [2, '黄'], [3, '绿']]), // phaseType表示红，黄，绿
      phaseTypeEn: new Map([[1, 'Red'], [2, 'Yellow'], [3, 'Green']]), // phaseType表示红，黄，绿
      ip: '--',
      port: '--',
      protocol: '--',
      faultArr: [], // 故障信息
      devStatus: 1,
      mode: '2',
      loading: {},
      agentId: '0',
      agentName: '--',
      platform: undefined,
      modelList: [{
        id: 1,
        iconClass: 'huangshan',
        iconName: '黄闪'
      }, {
        id: 2,
        iconClass: 'quanhong',
        iconName: '全红'
      }, {
        id: 3,
        iconClass: 'guandeng',
        iconName: '关灯'
      }, {
        id: 4,
        iconClass: 'bujin',
        iconName: '步进'
      }, {
        id: 5,
        iconClass: 'dingzhouqi',
        iconName: '定周期'
      }, {
        id: 6,
        iconClass: 'ganyingkongzhi',
        iconName: '感应控制'
      }, {
        id: 9,
        iconClass: 'zishiying',
        iconName: '自适应控制'
      }, {
        id: 10,
        iconClass: 'wuxianlan',
        iconName: '无电缆协调'
      }, {
        id: 12,
        iconClass: 'xingrenguojie',
        iconName: '行人过街'
      }, {
        id: 19,
        iconClass: 'ganyingshixingrenguojie',
        iconName: '感应式行人过街控制'
      }],
      currModel: -1,
      preselectModel: -1, // 预选方案
      preselectStages: -1, // 预选阶段
      crossStatusData: null, // 路口状态数据
      reset: false,
      currentStage: 0,
      responseTime: 0,
      closePhase: [],
      stagesList: [],
      isOperation: false, // 是否为手动可操作状态
      isdalayshow: true,
      showList: [{
        iconClass: 'model',
        name: '控制模式',
        value: '--',
        label: '1'
      }, {
        iconClass: 'cycle',
        name: '周期',
        value: '--',
        label: '2'
      }, {
        iconClass: 'time',
        name: '当前/剩余时间',
        insertHtml: true,
        label: '3'
      },
      {
        iconClass: 'maincontrol',
        name: '控制方式',
        value: '--',
        label: '4'
      }, {
        iconClass: 'phasediff',
        name: '相位差',
        value: '--',
        label: '5'
      }, {
        iconClass: 'currentvolume',
        name: '实时流量',
        value: '--',
        label: '6'
      }],
      curTime: '--',
      syncTime: '--',
      volumeData: null,
      curTable: 'phase',
      tempPatternid: 0, // 控制方式手动操作的情况下的控制编号的临时值。
      tempDelay: 0, // 控制方式手动操作的情况下的延迟时间的临时值。
      tempDuration: 0, // 控制方式手动操作的情况下的持续时间的临时值。
      phaseList: [], // 当前相位集合
      // patternStatusList: [], // 显示方案状态的相关数据集合
      stageStatusList: [], // 实时阶段状态的相关数据集合
      barrierList: [], // 方案状态中屏障的数据集合
      intervalFlag: true,
      shrink: 1,
      basicFuncControlId: [0, 1, 4, 5], // 基础功能包含的控制方式： 自主控制（手动下）、黄闪、步进、定周期
      isResend: true,
      commonHeight: undefined, // 左右侧面板的高度值
      faultvisible: false,
      isClosePhase: false, // 是否在相位关断
      toPage: 1, // 与哪一个页面交互，1 代表路口信息页面，3代表 相位关断页面
      specialcontrolList: [{ // 特殊控制
        id: 23,
        iconClass: 'closephase'
      }, {
        id: 22,
        iconClass: 'lockingphase'
      }],
      phaseRings: [],
      curFaultList: [],
      confirmedFault: [],
      ignoredFault: [],
      untreatedFault: [],
      specialPage: '' // 哪一个特殊控制页面
    }
  },
  computed: {
    ...mapState({
      curBodyWidth: state => state.globalParam.curBodyWidth,
      curBodyHeight: state => state.globalParam.curBodyHeight,
      FuncSort: state => state.globalParam.FuncSort,
      hideMenu: state => state.globalParam.hideMenu,
      graphicMode: state => state.globalParam.graphicMode,
      isShowGui: state => state.globalParam.isShowGui
    })
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        if (val.query !== undefined) {
          if (this.$route.query.shrink) {
            this.shrink = Number(this.$route.query.shrink)
          }
          this.agentId = this.$route.query.agentid
          setIframdevid(this.agentId)
          // this.ip = this.$route.query.IP
          // this.port = this.$route.query.port
          // this.protocol = this.$route.query.protocol
          this.resetCrossDiagram()
          this.getPlatform()
          this.getFaultById()
        }
      },
      // 深度观察监听
      deep: true
    },
    phaseList: {
      handler: function (val, oldVal) {
        this.getPedPhasePos()
      },
      // 深度观察监听
      deep: true
    },
    control: {
      handler: function (val, oldVal) {
        if (val !== oldVal) {
          if (val !== '999') {
            this.controlNum = val
          } else {
            this.controlNum = ''
          }
        }
      }
    }
  },
  created () {
    // this.getPedPhasePos()
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
    if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
      this.agentId = this.$route.query.agentid
      setIframdevid(this.agentId)
      // this.ip = this.$route.query.IP
      // this.port = this.$route.query.port
      // this.protocol = this.$route.query.protocol
      this.resetCrossDiagram()
      this.registerMessage() // 注册消息
    } else {
      // setIframdevid('23080400311210000088')
      // setIframdevid('8011')
      this.queryDevParams() // 查询设备信息
    }
    // this.registerMessage() // 注册消息
  },
  mounted () {
    this.getPlatform()
    this.getFault()
    if (this.$route.query.shrink) {
      this.shrink = Number(this.$route.query.shrink)
    }
  },
  updated () {
    if (this.$refs.tuxingLeft.offsetHeight !== this.commonHeight) {
      // 根据左侧面板动态变化的高度，同步更新右侧面板高度
      this.commonHeight = this.$refs.tuxingLeft.offsetHeight
      this.$refs.tuxingRight.style.height = this.commonHeight + 'px'
    }
  },
  methods: {
    handleChangeTable (name) {
      switch (name) {
        case '当前/剩余时间':
          this.curTable = 'phase'
          break
        case '实时流量':
          this.curTable = 'currentvolume'
          break
        default: break
      }
    },
    getFault () {
      this.getFaultById()
      this.faultTimer = setInterval(() => {
        this.getFaultById()
      }, 30000)
    },
    getPedPhasePos () {
      // 行人相位信息
      this.sidewalkPhaseData = []
      this.phaseList.forEach((ele, i) => {
        if (ele.peddirection) {
          ele.peddirection.forEach((dir, index) => {
          // 行人相位
            if (this.PhaseDataModel.getSidePos(dir)) {
              this.sidewalkPhaseData.push({
                key: this.CrossDiagramMgr.getUniqueKey('pedphase'),
                phaseid: ele.id, // 相位id，用于对应相位状态
                id: dir,
                name: this.PhaseDataModel.getSidePos(dir).name
              })
            }
          })
        }
      })
    },
    getVolume () {
      getTscCurrentVolume(this.agentId).then((data) => {
        // 与每秒获取信号机状态共用一套断线重连机制，此处不做清空定时器的处理
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(this.$t('edge.errorTip.abnormalcommunication'))
          return
        }
        if (data.data.data.data) {
          this.volumeData = JSON.parse(JSON.stringify(data.data.data.data))
          this.getVolumeSum()
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
      // console.log('获取实时流量')
      // const Data = require('./test.json')
      // this.volumeData = JSON.parse(JSON.stringify(Data))
      // this.getVolumeSum()
    },
    getVolumeSum () {
      let sumVolume = 0
      if (!this.volumeData.detector) return
      this.volumeData.detector.forEach(currentvolume => {
        sumVolume = sumVolume + currentvolume.largevehnum + currentvolume.middlevehnum + currentvolume.smallvehnum
      })
      this.showList.forEach(item => {
        if (item.name === '实时流量') {
          item.value = sumVolume
        }
      })
    },
    registerMessage () {
      registerMessage(this.agentId).then(data => {
        if (!data.data.success) {
          this.devStatus = 2
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            if (this.isResend) {
              this.reSend()
            }
            return
          }
          this.$message.error(this.$t('edge.errorTip.abnormalcommunication'))
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        // let res = data.data.data.data
        // let obj = {
        //   ip: '',
        //   port: '',
        //   protocol: ''
        // }
        // let newRes = {...obj, ...res}
        this.devStatus = 3
        // this.ip = newRes.ip
        // this.port = newRes.port
        // this.protocol = newRes.protocol
        // if (newRes.fault) {
        //   this.faultArr = this.getFaultMes(newRes.fault)
        // }
        this.clearPatternInterval() // 清除其他定时器
        this.clearVolumeInterval()
        this.phaseControlTimer = setInterval(() => {
          if (this.intervalFlag) {
            this.initData()
          }
          // this.initData()
        }, 1000)
        this.getVolume()
        this.volumeControlTimer = setInterval(() => {
          this.getVolume()
        }, 300000)
        this.getPhase()
      })
    },
    getFaultMes (codeList) {
      if (this.$i18n.locale === 'en') {
        return getFaultMesEn(codeList)
      } else {
        return getFaultMesZh(codeList)
      }
    },
    reSend () { // 设备掉线重连机制
      this.devStatus = 1
      this.clearRegisterMessageTimer()
      this.registerMessageTimer = setTimeout(() => {
        this.reconnectionDev()
      }, 5000)
    },
    clearPatternInterval () {
      if (this.phaseControlTimer !== null) {
        clearInterval(this.phaseControlTimer) // 清除定时器
        this.phaseControlTimer = null
      }
    },
    clearVolumeInterval () {
      if (this.volumeControlTimer !== null) {
        clearInterval(this.volumeControlTimer) // 清除流量定时器
        this.volumeControlTimer = null
      }
    },
    clearRegisterMessageTimer () {
      if (this.registerMessageTimer !== null) {
        clearTimeout(this.registerMessageTimer) // 清除延时器
        this.registerMessageTimer = null
      }
    },
    clearFaultInterval () {
      if (this.faultTimer !== null) {
        clearInterval(this.faultTimer) // 清除流量定时器
        this.faultTimer = null
      }
    },
    initData () {
      this.intervalFlag = false
      let startTime = new Date().getTime()
      getTscControl(this.agentId).then((data) => {
        let endTime = new Date().getTime()
        let diffTime = endTime - startTime
        this.responseTime = diffTime
        this.intervalFlag = true
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.devStatus = 2
            this.clearPatternInterval() // 清除其他定时器
            this.clearVolumeInterval()
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            if (this.isResend) {
              this.reSend()
            }
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          this.clearPatternInterval() // 清除其他定时器
          this.clearVolumeInterval()
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        // let param = JSON.parse(data.data.data)
        this.crossStatusData = JSON.parse(JSON.stringify(data.data.data.data))
        let TscData = JSON.parse(JSON.stringify(data.data.data.data))
        this.currModel = TscData.control
        this.handleStageData(TscData) // 处理阶段（驻留）stage数据
        this.controlData = this.handleGetData(TscData)
        // this.handlePatternData() // 计算方案状态展示数据
        // this.getStageStatusData()
        this.handleList(this.controlData)
        this.handleTableData(this.controlData)
        // this.handlePatternData() // 计算方案状态展示数据
        this.handleGetPhaseClose()
      }).catch(error => {
        // this.$message.error(error)
        console.log(error)
      })
    },
    handleStageData (data) {
      this.stagesList = []
      let boardCard = this.$refs.boardCard
      let busPhaseData = []
      if (boardCard) {
        busPhaseData = boardCard.$children[1].busPhaseData
      }
      this.currentStage = data.current_stage
      let stages = data.stages
      if (!stages) return
      for (let stage of stages) {
        let tempList = []
        let directionList = []
        let stageControType = 0
        let peddirections = []
        for (let stg of stage) {
          let currPhase = this.phaseList.filter((item) => {
            return item.id === stg
          })[0]
          if (currPhase !== undefined) {
            directionList = [...currPhase.direction, ...directionList]
          }
          for (let walk of this.sidewalkPhaseData) {
            if (stg === walk.phaseid) {
              peddirections.push(...currPhase.peddirection)
              peddirections = Array.from(new Set(peddirections))
            }
          }
          for (let busPhase of busPhaseData) {
            if (stg === busPhase.phaseid) {
              stageControType = busPhase.controltype
            }
          }
        }
        directionList = [...new Set(directionList)]
        tempList = directionList.map(dir => ({
          id: dir,
          color: '#606266',
          controltype: stageControType,
          peddirection: peddirections
        }))
        this.stagesList.push(tempList)
      }
    },
    onSubmit () {
      if (this.form.data && !this.isJsonString(this.form.data)) {
        this.$message.error(this.$t('edge.overview.JSONFormatError'))
        return
      }
      if (this.control === '999' && this.controlNum === '') {
        this.$message.error(this.$t('edge.overview.controlnumerrormess'))
        return
      }
      this.lockScreen()
      // let control = { ...this.controlData }
      let control = {}
      control.control = Number(this.control === '999' ? this.controlNum : this.control)
      // control.pattern = Number(this.form.pattern)
      control.terminal = Number(this.form.terminal)
      control.value = Number(this.form.value)
      control.delay = Number(this.form.delay)
      control.duration = Number(this.form.duration)
      if (this.form.data) {
        // eslint-disable-next-line no-useless-escape
        control.data = JSON.parse(this.form.data)
      }
      // let controlObj = this.handlePutData(control)
      putTscControl(control).then(data => {
        this.unlockScreen()
        let success = 0
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        if (data.data.data && data.data.data.data) {
          success = data.data.data.data.success
          if (success !== 0) {
            let errormsg = 'edge.overview.putTscControlError' + success
            this.$message.error(this.$t(errormsg))
            return
          }
        }
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    onGet () {
      this.lockScreen()
      getTscPattern(this.agentId).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$message.success(this.$t('edge.common.querysucess'))
        let patternData = data.data.data.data
        let patternList = [0, 1, 2, 4, 5, 6, 10, 12, 14, 19]
        if (patternList.includes(patternData.control)) {
          this.control = String(patternData.control)
        } else {
          this.control = '999'
          this.controlNum = String(patternData.control)
        }
        this.form.terminal = String(patternData.terminal)
        if (this.$i18n.locale === 'en') {
          this.form.mode = this.ParamsModeEn.get(patternData.mode)
        } else if (this.$i18n.locale === 'zh') {
          this.form.mode = this.ParamsMode.get(patternData.mode)
        }
        // this.form.mode = this.ParamsMode.get(patternData.mode)
        // this.form.mode = this.ParamsModeEn.get(patternData.mode)
        this.form.value = patternData.value
        this.form.delay = patternData.delay
        this.form.duration = patternData.duration
        this.form.data = patternData.data
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    handleGetData (data) {
      let that = this
      if (data.name === '') {
        if (that.$i18n.locale === 'en') {
          data.name = 'Pattern' + data.patternid
        } else if (that.$i18n.locale === 'zh') {
          data.name = '方案' + data.patternid
        }
      }
      Object.keys(data).forEach(function (key) {
        if (that.$i18n.locale === 'en') {
          if (key === 'mode') {
            if (data[key] > 0 && data[key] < 6) {
              data[key] = that.ParamsModeEn.get(data[key])
            } else {
              data[key] = that.ParamsModeEn.get(0)
            }
          }
          if (key === 'control') {
            data[key] = that.ParamsControlEn.get(data[key])
          }
          if (key === 'phase') {
            for (let val of data[key]) {
              val.type = that.phaseTypeEn.get(val.type)
            }
          }
        } else if (that.$i18n.locale === 'zh') {
          if (key === 'mode') {
            if (data[key] > 0 && data[key] < 6) {
              data[key] = that.ParamsMode.get(data[key])
            } else {
              data[key] = that.ParamsMode.get(0)
            }
          }
          if (key === 'control') {
            data[key] = that.ParamsControl.get(data[key])
          }
          if (key === 'phase') {
            for (let val of data[key]) {
              val.type = that.phaseType.get(val.type)
            }
          }
        }
      })
      return data
    },
    handleList (value) {
      for (let item of this.list) {
        let paramValue = this.ParamsMap.get(item.name)
        item.value = value[paramValue]
      }
      this.getShowList()
    },
    getShowList () {
      this.list.forEach(ele => {
        if (ele.name === '当前时间') {
          this.curTime = ele.value
        }
        if (ele.name === '剩余时间') {
          this.syncTime = ele.value
        }
        this.showList.forEach(item => {
          if (ele.name === item.name) {
            item.value = ele.value
          }
        })
      })
    },
    handleTableData (value) {
      this.tableData = []
      if (!value.phase) return
      if (!value.rings) return
      let phaseList = value.phase
      let rings = value.rings
      for (let ring of rings) {
        // let list = ring.sequence.split(' ')
        let list = ring.sequence
        for (let item of list) {
          let obj = {}
          obj.ring = ring.num
          obj.phase = item
          for (let phase of phaseList) {
            let phaseId = phase.id
            if (item === phaseId) {
              obj.countdown = phase.countdown
              obj.split = phase.split
              obj.countdown = phase.countdown
              obj.id = phase.id
              obj.type = phase.type
              obj.light_countdown = phase.light_countdown
            }
          }
          this.tableData.push(obj)
        }
      }
      this.$refs.currentPhase.handleObjectSpan()
      // this.handleObjectSpan()
    },
    lockScreen () {
      this.loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.5)'
      })
    },
    unlockScreen () {
      this.loading.close()
    },
    reconnectionDev () {
      this.registerMessage()
      // registerMessage(this.agentId).then(data => {
      //   if (!data.data.success) {
      //     if (this.isResend) {
      //       this.reSend()
      //     }
      //     return
      //   }
      //   this.devStatus = 3
      //   this.clearPatternInterval() // 清除其他定时器
      //   this.clearVolumeInterval()
      //   this.phaseControlTimer = setInterval(() => {
      //     if (this.intervalFlag) {
      //       this.initData()
      //     }
      //     // this.initData()
      //   }, 1000)
      //   this.getVolume()
      //   this.volumeControlTimer = setInterval(() => {
      //     this.getVolume()
      //   }, 300000)
      //   this.getPhase()
      // })
    },
    selectModel (value) {
      if (!this.isOperation) return
      // if (this.preselectModel !== -1) {
      //   this.preselectStages = -1
      //   if (this.preselectModel === value) {
      //     this.preselectModel = -1
      //   } else {
      //     this.preselectModel = value
      //   }
      // } else {
      //   this.preselectModel = value
      // }
      this.preselectStages = -1
      this.preselectModel = value
    },
    selectStages (value) {
      if (!this.isOperation) return
      if (this.preselectStages !== -1) {
        if (this.preselectStages === value) {
          this.preselectStages = -1
        } else {
          this.preselectStages = value
          this.preselectModel = 4 // 如果选了阶段，默认控制方式为步进
        }
      } else {
        this.preselectStages = value
        this.preselectModel = 4 // 如果选了阶段，默认控制方式为步进
      }
    },
    changeStatus () {
      this.toPage = 1
      this.isOperation = true
      if (this.modelList.filter(ele => ele.id === 0).length === 0) {
        let autonomyControl = {
          id: 0,
          iconClass: 'zizhukongzhi',
          iconName: '自主控制'
        }
        this.modelList.push(autonomyControl)
      }
    },
    closeManualModal () {
      this.isOperation = false
      this.preselectModel = -1
      this.preselectStages = -1
      this.tempPatternid = 0
      this.tempDelay = 0
      this.tempDuration = 0
      this.modelList = this.modelList.filter((item) => {
        return item.id !== 0
      })
      this.toPage = 1
    },
    patternCommit (manualInfo) {
      let that = this
      this.lockScreen()
      let control = {}
      that.ParamsMode.forEach(function (value, key, map) {
        if (that.controlData.mode === value) {
          control.mode = key
        }
      })
      control.control = that.preselectModel === -1 ? that.currModel : that.preselectModel
      control.terminal = Number(manualInfo.tempPatternid)
      control.delay = Number(manualInfo.tempDelay)
      control.duration = Number(manualInfo.tempDuration)
      control.value = that.preselectStages === -1 ? 0 : that.preselectStages
      putTscControl(control).then(data => {
        that.unlockScreen()
        let success = 0
        if (!data.data.success) {
          that.$message.error(getMessageByCode(data.data.code, that.$i18n.locale))
          return
        }
        if (data.data.data && data.data.data.data) {
          success = data.data.data.data.success
          if (success !== 0) {
            let errormsg = 'edge.overview.putTscControlError' + success
            that.$message.error(this.$t(errormsg))
            return
          }
        }
        // this.closeManualModal()
        if ((that.currModel === 5 || that.currModel === 6 || that.currModel === 10 || that.currModel === 12) && (that.preselectModel === 6 || that.preselectModel === 10 || that.preselectModel === 12)) {
          that.$message.success(this.$t('edge.overview.nextcycleeffic'))
          return
        }
        if (that.preselectModel === 4) {
          that.$message.success(this.$t('edge.overview.transitioneffic'))
          return
        }
        if (success === 0) {
          that.$message.success(this.$t('edge.common.download'))
        }
      }).catch(error => {
        that.unlockScreen()
        that.$message.error(error)
        console.log(error)
      })
    },
    resetCrossDiagram () {
      this.reset = false
      this.$nextTick(() => {
        this.reset = true
      })
    },
    queryDevParams () {
      queryDevice().then(res => {
        // res.data.data = {
        //   'id': 192,
        //   'lastTime': '2021-01-11 15:56:58',
        //   'type': 'v-atc',
        //   'status': 0,
        //   'state': 'UP',
        //   'jsonparam': {
        //     'ip': '192.168.14.189',
        //     'port': 10030,
        //     'model': 'OpenATC_Software_Version 1.0.0'
        //   },
        //   'agentid': '30031',
        //   'protocol': 'ocp',
        //   'comtype': 0
        // }
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        let devParams = res.data.data.jsonparam
        this.ip = devParams.ip
        this.port = devParams.port
        this.protocol = res.data.data.protocol
        this.agentId = res.data.data.agentid
        if (res.data.data.name) {
          this.agentName = res.data.data.name
        }
        this.platform = res.data.data.platform
        // setIframdevid(this.agentId)
        this.resetCrossDiagram()
        this.registerMessage() // 注册消息
      })
    },
    getPhase () {
      uploadSingleTscParam('phase').then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.phaseList = res.data.data.phaseList
      })
    },
    getPlatform () {
      queryDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }

        let devParams = res.data.data.jsonparam
        this.ip = devParams.ip
        this.port = devParams.port
        this.protocol = res.data.data.protocol
        this.agentId = res.data.data.agentid
        if (res.data.data.name) {
          this.agentName = res.data.data.name
        }
        this.platform = res.data.data.platform
        let func = 'allFunc'
        if (this.platform === 'OpenATC') {
          func = 'allFunc'
        }
        if (this.platform === 'SCATS' || this.platform === 'HUATONG') {
          func = 'basicFunc'
        }
        this.$store.dispatch('SaveFunctionLevel', func)
      })
    },
    isJsonString (str) {
      try {
        JSON.parse(str)
        return true
      } catch (err) {
        return false
      }
    },
    handleFaultsVisible () {
      this.faultvisible = !this.faultvisible
    },
    handleGetPhaseClose () {
      if (this.crossStatusData.phase) {
        this.crossStatusData.phase = this.crossStatusData.phase.map(ele => {
          return {
            ...ele,
            close: ele.close || 0,
            locktype: 0 // 默认所有相位显示解锁状态
          }
        })
        // 相位关断标签
        this.closePhase = []
        this.crossStatusData.phase.forEach(phase => {
          if (phase.close !== undefined && phase.close !== 0) {
            let typename
            switch (phase.close) {
              case 1: typename = ''
                break
              case 2: typename = this.$t('edge.overview.vehicle')
                break
              case 3: typename = this.$t('edge.overview.pedestrian')
                break
              default:typename = ''
            }
            this.closePhase.push({
              id: phase.id,
              typename: typename
            })
          }
        })
      }
    },
    selectSpecialModel (id) {
      if (id === 23) {
        this.toPage = 3
        this.isClosePhase = true
        this.initRingPhaseData()
        this.specialPage = 'closephase'
      } else if (id === 22) {
        this.toPage = 3
        this.isClosePhase = true
        this.initRingPhaseData()
        this.specialPage = 'lockingphase'
      } else {
        this.isClosePhase = false
        this.specialPage = ''
      }
    },
    closePhaseBack () {
      this.toPage = 3
      this.isClosePhase = false
    },
    closePhaseControl (controldata) {
      this.lockScreen()
      putTscControl(controldata).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          this.$message.error(data.data.message)
          return
        }
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    initRingPhaseData () {
      // 环信息从单独上载相位信息里获取，以免相位锁定后，方案状态数据里没有rings，导致相位锁定控制列表无法显示
      this.phaseRings = []
      let map = {}
      let dest = []
      for (let i = 0; i < this.phaseList.length; i++) {
        let ai = this.phaseList[i]
        if (!map[ai.ring]) {
          let addphse = this.addPhaseInfo(ai)
          dest.push({
            num: ai.ring,
            phases: [{...ai, ...addphse}]
          })
          map[ai.ring] = ai
        } else {
          for (var j = 0; j < dest.length; j++) {
            var dj = dest[j]
            if (dj.num === ai.ring) {
              let addphse = this.addPhaseInfo(ai)
              dj.phases.push({...ai, ...addphse})
              break
            }
          }
        }
      }
      this.phaseRings = JSON.parse(JSON.stringify(dest))
    },
    addPhaseInfo (phase) {
      let addphse = {}
      addphse.name = this.$t('edge.overview.phase') + phase.id
      addphse.desc = this.getPhaseDescription(phase.direction)
      if (this.crossStatusData !== null) {
        // 如果方案状态相位有close字段，这边就需要对应close状态进相位关断控制的选项里
        let phaseStatus = this.crossStatusData.phase.filter(ele => ele.id === phase.id)[0]
        addphse = {...addphse, ...phaseStatus}
      }
      // 相位锁定选项默认都按照解锁状态显示
      addphse.locktype = 0
      return addphse
    },
    // initRingPhaseData () {
    // 从方案状态里获取环信息，对应显示相位关断控制列表
    //   if (this.crossStatusData === null) return
    //   this.phaseRings = []
    //   if (!this.crossStatusData.rings) return
    //   this.crossStatusData.rings.forEach(ring => {
    //     let obj = {}
    //     obj.num = ring.num
    //     obj.phases = []
    //     for (let i = 0; i < ring.sequence.length; i++) {
    //       let phaseid = ring.sequence[i]
    //       let originphase = this.crossStatusData.phase.filter(phase => phase.id === phaseid)[0]
    //       let addphse = this.getClosePhasesInfo(phaseid)
    //       obj.phases.push({...originphase, ...addphse})
    //     }
    //     this.phaseRings.push(obj)
    //   })
    // },
    // getClosePhasesInfo (phaseid) {
    //   if (this.phaseList === undefined || this.phaseList.filter(phase => phase.id === phaseid).length === 0) return
    //   let phaseinfo = this.phaseList.filter(phase => phase.id === phaseid)[0]
    //   phaseinfo.name = this.$t('edge.overview.phase') + phaseinfo.id
    //   phaseinfo.desc = this.getPhaseDescription(phaseinfo.direction)
    //   return phaseinfo
    // },
    getPhaseDescription (phaseList) {
      let list = []
      for (let id of phaseList) {
        let obj = {}
        obj.id = id
        obj.color = '#454545'
        list.push(obj)
      }
      return list
    },
    getFaultById () {
      // GetCurrentFaultByAgentid(this.agentId).then(res => {
      //   if (!res.data.success) {
      //     this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
      //     return false
      //   } else {
      //     this.curFaultList = res.data.data
      //     this.confirmedFault = this.curFaultList.filter(ele => ele.enumerate === '2')
      //     this.ignoredFault = this.curFaultList.filter(ele => ele.enumerate === '1')
      //   }
      // })
      let param = {
        agentId: this.agentId,
        isCurrentFault: true
      }
      GetAllFaultRange(param).then(res => {
        if (res.data.success !== true) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.curFaultList = res.data.data.content
        this.confirmedFault = this.curFaultList.filter(ele => ele.enumerate === '2')
        this.ignoredFault = this.curFaultList.filter(ele => ele.enumerate === '1')
        this.untreatedFault = this.curFaultList.filter(ele => ele.enumerate === '0')
      })
    },
    showFaultDetail () {
      this.$refs.faultDetail.onViewFaultClick()
    }
  },
  // beforeDestroy () {
  //   this.clearPatternInterval() // 清除定时器
  //   this.clearVolumeInterval()
  //   this.clearRegisterMessageTimer() // 清除定时器
  // },
  destroyed () {
    this.isResend = false
    this.clearPatternInterval() // 清除定时器
    this.clearVolumeInterval()
    this.clearRegisterMessageTimer() // 清除定时器
    this.clearFaultInterval()
    this.getPlatform()
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
<style rel="stylesheet/scss" lang="scss">
</style>
