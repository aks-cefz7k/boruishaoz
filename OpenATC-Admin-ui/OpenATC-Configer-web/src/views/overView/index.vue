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
    <FloatImgBtn @onFloatBtnClicked="onFloatBtnClicked" v-if="!hideMenu && !graphicMode">
      <div slot="icon" class="sloat-icon">
        <i class="iconfont icon-tuxingjiemian" style="color: #ffffff;" v-show="!isShowGui"></i>
        <i class="iconfont icon-wenzijiemian" style="color: #ffffff;" v-show="isShowGui"></i>
      </div>
    </FloatImgBtn>
    <ManualControlModal v-if="isOperation"
      :Visible="isOperation"
      :controlData="controlData"
      :modelList="modelList"
      :stagesList="stagesList"
      :currModel="currModel"
      :preselectModel="preselectModel"
      :currentStage="currentStage"
      :preselectStages="preselectStages"
      @closeManualModal="closeManualModal"
      @selectModel="selectModel"
      @selectStages="selectStages"
      @patternCommit="patternCommit" />
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
                  <div class="curr-grade">{{controlData.name}}</div>
                  <div class="curr-num">{{$t('edge.overview.patternname')}}</div>
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
                    <el-select v-model="form.control" :placeholder="$t('edge.common.select')">
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
                    <el-input v-model="controlNum" style="width: 70%" :disabled="form.control!=='999'"></el-input>
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
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" size="small">{{$t('edge.common.setup')}}</el-button>
                    <el-button type="primary" @click="onGet" size="small">{{$t('edge.common.query')}}</el-button>
                </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
      <div class="tuxingjiemian" v-show="isShowGui" :class="{'minifont': curBodyWidth <= 650}">
        <div class="tuxing-left" :class="{'changeWidth': graphicMode}">
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
            <div class="pattern-message">({{$t('edge.overview.cycle')}}: {{controlData.cycle}}  {{$t('edge.overview.phasedifference')}}: {{controlData.offset}})</div>
            <span class="pattern-explain">：{{$t('edge.overview.phasesplit')}}</span>
            <span class="pattern-explain" style="margin-right: 15px;">P{{$t('edge.overview.phase')}}</span>
            <StageStatus style="margin-top: 10px;" :patternStatusList="patternStatusList"></StageStatus>
            <PatternStatus style="margin-top: 30px;"
                          :cycle="crossStatusData ? crossStatusData.cycle : 0"
                          :syncTime="crossStatusData ? crossStatusData.syncTime : 0"
                          :patternStatusList="patternStatusList"
                          :barrierList="barrierList"></PatternStatus>
          </div>
        </div>
        <div class="tuxing-right" v-if="!graphicMode">
          <div class="cross-mess" style="margin-bottom: 18px;">{{$t('edge.overview.crossinfo')}}</div>
          <div class="cross-module">
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.crossname')}}:</div><div style="margin-left: 85px;" class="cross-value">{{agentName}}</div></div>
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.divicestate')}}:</div>
              <div v-show="devStatus===3" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.online')}}</div>
              <div v-show="devStatus===2" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.offline')}}</div>
              <div v-show="devStatus===1" style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.onlineing')}}</div>
            </div>
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.protocoltype')}}:</div><div style="margin-left: 85px;" class="cross-value">{{protocol}}</div></div>
            <!-- <div class="cross-content"><div style="float: left;" class="cross-name">信号机型号:</div><div style="margin-left: 85px;" class="cross-value">XHJ-CW-GA-KSS100</div></div> -->
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.signalID')}}:</div><div style="margin-left: 85px;" class="cross-value">{{agentId}}</div></div>
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.signalIP')}}:</div><div style="margin-left: 85px;" class="cross-value">{{ip}}</div></div>
            <div class="cross-content" v-if="platform"><div style="float: left;" class="cross-name">{{$t('edge.overview.platform')}}:</div><div style="margin-left: 85px;" class="cross-value">{{platform}}</div></div>
          </div>
          <div>
            <div class="cross-mess" style="float: left;margin-top: 40px;margin-bottom: 18px;">{{$t('edge.overview.controlmode')}}</div>
            <el-button type="primary" style="float: right; margin-right: 40px;margin-top: 40px;" size="mini" @click="changeStatus">{{$t('edge.overview.manual')}}</el-button>
            <!-- <el-button type="primary" style="float: right; margin-right: 40px;" size="mini" @click="changeStatus" v-show="isOperation">{{$t('edge.overview.exitmanual')}}</el-button> -->
          </div>
          <div class="cross-module">
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.controlmodel')}}:</div><div style="margin-left: 85px;" class="cross-value">{{controlData.mode}}</div></div>
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.patternname')}}:</div><div style="margin-left: 85px;" class="cross-value">{{controlData.name}}</div></div>
            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.controlnumber')}}:</div>
              <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.patternid}}</div>
            </div>

            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.delay')}}:</div>
              <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.delay}}</div>
            </div>

            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.duration')}}:</div>
              <div style="margin-left: 85px;" class="cross-value" v-show="!isOperation">{{controlData.duration}}</div>
            </div>

            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.curModel')}}:</div>
              <div style="margin-left: 85px;" class="cross-value">{{$t('edge.overview.modelList' + currModel)}}</div>
            </div>

            <div class="cross-content"><div style="float: left;" class="cross-name">{{$t('edge.overview.curStage')}}:</div>
              <div style="margin-left: 85px;" class="cross-value">{{currentStage}}</div>
            </div>
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
import PatternStatus from '@/components/PatternStatus'
import StageStatus from '@/components/StageStatus'
import xdrdirselector from '@/components/XRDDirSelector'
import CurVolume from './textPage/currentVolume'
import CurPhase from './textPage/currentPhase'
import ManualControlModal from './manualControlModal'
export default {
  name: 'overview',
  components: {
    FloatImgBtn,
    CrossDiagram,
    PatternStatus,
    StageStatus,
    xdrdirselector,
    CurVolume,
    CurPhase,
    ManualControlModal
  },
  data () {
    return {
      controlData: {},
      form: {
        control: '',
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
      ParamsMap: new Map([['控制模式', 'mode'], ['周期', 'cycle'], ['控制方式', 'control'], ['相位差', 'offset'], ['当前时间', 'curTime'], ['剩余时间', 'syncTime']]),
      ParamsMode: new Map([[0, '系统控制'], [1, '平台控制'], [2, '配置工具控制'], [3, '手动面板控制']]),
      ParamsModeEn: new Map([[0, 'System Control'], [1, 'Platform Control'], [2, 'Configuration Control'], [3, 'Manual Panel Control']]),
      ParamsControl: new Map([[0, '自主控制'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '无电缆控制'], [11, '有电缆控制'], [12, '行人过街控制']]),
      ParamsControlEn: new Map([[0, 'Auto Control'], [1, 'Yellow Flash Control'], [2, 'Red Control'], [3, 'Dark Control'], [4, 'Step'], [5, 'Fixed_Cycle Control'], [6, 'Free Control'], [7, 'Coordinated Induction Control'], [8, 'Pattern Selection Control'], [9, 'Adaptive Control'], [10, '无电缆控制'], [11, 'Cable Control'], [12, 'Pedestrian Crossing Control']]),
      phaseType: new Map([[1, '红'], [2, '黄'], [3, '绿']]), // phaseType表示红，黄，绿
      phaseTypeEn: new Map([[1, 'Red'], [2, 'Yellow'], [3, 'Green']]), // phaseType表示红，黄，绿
      ip: '--',
      port: '--',
      protocol: '--',
      devStatus: 1,
      mode: '2',
      loading: {},
      agentId: '0',
      agentName: '--',
      platform: undefined,
      isShowGui: true,
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
      stagesList: [],
      isOperation: false, // 是否为手动可操作状态
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
      patternStatusList: [], // 显示方案状态的相关数据集合
      barrierList: [], // 方案状态中屏障的数据集合
      intervalFlag: true,
      shrink: 1,
      basicFuncControlId: [0, 1, 4, 5], // 基础功能包含的控制方式： 自主控制（手动下）、黄闪、步进、定周期
      isResend: true
    }
  },
  computed: {
    ...mapState({
      curBodyWidth: state => state.globalParam.curBodyWidth,
      curBodyHeight: state => state.globalParam.curBodyHeight,
      FuncSort: state => state.globalParam.FuncSort,
      hideMenu: state => state.globalParam.hideMenu,
      graphicMode: state => state.globalParam.graphicMode
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
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
      this.agentId = this.$route.query.agentid
      setIframdevid(this.agentId)
      // this.ip = this.$route.query.IP
      // this.port = this.$route.query.port
      // this.protocol = this.$route.query.protocol
      this.resetCrossDiagram()
      this.registerMessage() // 注册消息
    } else {
      // setIframdevid('10602')
      this.queryDevParams() // 查询设备信息
    }
    // this.registerMessage() // 注册消息
  },
  mounted () {
    this.getPlatform()
    if (this.$route.query.shrink) {
      this.shrink = Number(this.$route.query.shrink)
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
        let res = data.data.data.data
        let obj = {
          ip: '',
          port: '',
          protocol: ''
        }
        let newRes = {...obj, ...res}
        this.devStatus = 3
        this.ip = newRes.ip
        this.port = newRes.port
        this.protocol = newRes.protocol
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
    initData () {
      this.intervalFlag = false
      getTscControl(this.agentId).then((data) => {
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
          this.$message.error(data.data.message)
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
        this.handlePatternData() // 计算方案状态展示数据
        this.handleList(this.controlData)
        this.handleTableData(this.controlData)
        // this.handlePatternData() // 计算方案状态展示数据
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handleStageData (data) {
      this.stagesList = []
      this.currentStage = data.current_stage
      let stages = data.stages
      for (let stage of stages) {
        let tempList = []
        let directionList = []
        for (let stg of stage) {
          let currPhase = this.phaseList.filter((item) => {
            return item.id === stg
          })[0]
          if (currPhase !== undefined) {
            directionList = [...currPhase.direction, ...directionList]
          }
          // directionList = [...currPhase.direction, ...directionList]
        }
        directionList = [...new Set(directionList)]
        tempList = directionList.map(dir => ({
          id: dir,
          color: '#606266'
        }))
        this.stagesList.push(tempList)
      }
    },
    onSubmit () {
      if (this.form.control === '999' && this.controlNum === '') {
        this.$message.error(this.$t('edge.overview.controlnumerrormess'))
        return
      }
      this.lockScreen()
      // let control = { ...this.controlData }
      let control = {}
      control.control = Number(this.form.control === '999' ? this.controlNum : this.form.control)
      // control.pattern = Number(this.form.pattern)
      control.terminal = Number(this.form.terminal)
      control.value = Number(this.form.value)
      control.delay = Number(this.form.delay)
      control.duration = Number(this.form.duration)
      // let controlObj = this.handlePutData(control)
      putTscControl(control).then(data => {
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
    onGet () {
      this.lockScreen()
      getTscPattern(this.agentId).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          this.$message.error(data.data.message)
          return
        }
        this.$message.success(this.$t('edge.common.querysucess'))
        let patternData = data.data.data.data
        let patternList = [0, 1, 2, 4, 5, 6, 10, 12, 14, 19]
        if (patternList.includes(patternData.control)) {
          this.form.control = String(patternData.control)
        } else {
          this.form.control = '999'
          this.controlNum = String(patternData.control)
        }
        // this.form.control = String(patternData.control)
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
    onFloatBtnClicked () {
      this.isShowGui = !this.isShowGui
    },
    selectModel (value) {
      if (!this.isOperation) return
      if (this.preselectModel !== -1) {
        this.preselectStages = -1
        if (this.preselectModel === value) {
          this.preselectModel = -1
        } else {
          this.preselectModel = value
        }
      } else {
        this.preselectModel = value
      }
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
      this.isOperation = true
      let autonomyControl = {
        id: 0,
        iconClass: 'zizhukongzhi',
        iconName: '自主控制'
      }
      this.modelList.push(autonomyControl)
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
        if (!data.data.success) {
          that.$message.error(data.data.message)
          return
        }
        if ((that.currModel === 5 || that.currModel === 6 || that.currModel === 10 || that.currModel === 12) && (that.preselectModel === 6 || that.preselectModel === 10 || that.preselectModel === 12)) {
          that.$alert(this.$t('edge.overview.nextcycleeffic'), { type: 'success' })
          return
        }
        if (that.preselectModel === 4) {
          that.$alert(this.$t('edge.overview.transitioneffic'), { type: 'success' })
          return
        }
        this.isOperation = false
        that.$alert(that.$t('edge.common.download'), { type: 'success' })
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
          this.$message.error(res.data.message)
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
          this.$message.error(res.message)
          return
        }
        this.phaseList = res.data.data.phaseList
      })
    },
    handlePatternData () {
      this.patternStatusList = []
      this.barrierList = []
      if (Object.keys(this.controlData).length === 0 || this.phaseList.length === 0) return
      if (!this.controlData.phase) return
      let cycle = this.controlData.cycle
      for (let rings of this.controlData.rings) {
        let list = []
        let phase = this.controlData.phase
        for (let sequ of rings.sequence) {
          let obj = {}
          obj.id = sequ
          let split = phase.filter((item) => {
            return item.id === sequ
          })[0].split
          let currPhase = this.phaseList.filter((item) => {
            return item.id === sequ
          })[0]
          obj.redWidth = (currPhase.redclear / cycle * 100).toFixed(3) + '%'
          obj.yellowWidth = (currPhase.yellow / cycle * 100).toFixed(3) + '%'
          obj.greenWidth = ((split - currPhase.redclear - currPhase.yellow) / cycle * 100).toFixed(3) + '%'
          obj.split = split
          obj.direction = currPhase.direction.map(item => {
            return {
              id: item,
              color: '#454545'
            }
          })
          list.push(obj)
        }
        this.patternStatusList.push(list)
      }
      this.handleBarrier(this.patternStatusList, this.phaseList)
    },
    handleBarrier (patternStatusList, phaseList) {
      if (patternStatusList.length < 2) return
      let tempList = []
      let barrierWidth = 0
      let firstPatternStatus = patternStatusList[0]
      for (let patternStatus of firstPatternStatus) {
        let concurrent = phaseList.filter((item) => {
          return item.id === patternStatus.id
        })[0].concurrent
        if (concurrent.length === 0) {
          this.barrierList = []
          return
        }
        if (!this.isEqualsForArray(tempList, concurrent)) {
          tempList = concurrent
          this.barrierList.push(barrierWidth)
        }
        barrierWidth = Number.parseFloat(barrierWidth) + Number.parseFloat(patternStatus.redWidth) + Number.parseFloat(patternStatus.yellowWidth) + Number.parseFloat(patternStatus.greenWidth) + '%'
      }
      this.barrierList.push(barrierWidth) // 添加末尾处的屏障
    },
    isEqualsForArray (listA, listB) {
      return listA.length === listB.length && listA.every(a => listB.some(b => a === b)) && listB.every(_b => listA.some(_a => _a === _b))
    },
    getPlatform () {
      queryDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
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
    this.getPlatform()
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// .container-main {
//   width: 100%;
//   height: 880px;
//   min-width: 1250px;
//   background: #ffffff;
// }
// .container-main .iconfont {
//     font-family: "iconfont" !important;
//     font-size: 34px;
//     text-align: center;
//     font-weight: 500;
//     color: #000000;
//     font-style: normal;
//     -webkit-font-smoothing: antialiased;
//     -moz-osx-font-smoothing: grayscale;
// }
// .container-left {
//   float: left;
//   margin-top: 20px;
//   margin-left: 20px;
//   width: 76.8%;
//   height: 840px;
//   background-color: #ffffff;
//   border: solid 1px #e6e6e6;
// }
// .container-right {
//   float: left;
//   margin-top: 20px;
//   margin-left: 20px;
//   width: 20%;
//   height: 840px;
//   background-color: #ffffff;
//   border: solid 1px #e6e6e6;
// }
// .container-left-top {
//   width: 100%;
//   height: 420px;
// }
// .container-left-bottom {
//   width: 100%;
//   height: 400px;
//   background-color: #ffffff;
// }
// .curr-order {
//   float: left;
//   margin-left: 20px;
//   margin-top: 1px;
// }
// .control-right {
//   margin-top: 40px;
//   margin-left: 20px;
// }
// .agent-div {
//   float: left;
//   width: 28%;
//   height: 420px;
//   border-bottom: solid 0.5px #e6e6e6;
// }
// .other-div {
//   float: left;
//   width: 24%;
//   height: 210px;
//   border-left: solid 1px #e6e6e6;
//   border-bottom: solid 1px #e6e6e6;
// }
// .agent-icon {
//   position:relative;
//   float: left;
//   width: 50%;
//   height: 210px;
// }
// .agent-num {
//   float: left;
//   width: 50%;
//   height: 210px;
// }
// .lianji-success {
//   position:relative;
//   float: right;
//   right: 30px;
//   top: 25px;
//   text-align: center;
//   width: 81px;
//   height: 34px;
//   background-color: #409eff;
//   border-radius: 4px;
//   font-size: 14px;
//   padding:10px 0;
//   color: #ffffff;
// }
// .lianji-fail {
//   position:relative;
//   float: right;
//   right: 30px;
//   top: 25px;
//   text-align: center;
//   width: 81px;
//   height: 34px;
//   background-color: #909399;
//   border-radius: 4px;
//   font-size: 14px;
//   padding:10px 0;
//   color: #ffffff;
// }
// .lianji-wait {
//   position:relative;
//   float: right;
//   right: 30px;
//   top: 25px;
//   text-align: center;
//   width: 110px;
//   height: 34px;
//   background-color: #e6a23c;
//   border-radius: 4px;
//   font-size: 14px;
//   padding:10px 0;
//   color: #ffffff;
// }
// .agent-id {
//   margin-top: 20px;
//   margin-right: 30px;
//   text-align: right;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #999999;
// }
// .agent-number {
//   margin-top: 10px;
//   margin-right: 30px;
//   text-align: right;
//   font-family: ArialMT;
//   font-size: 16px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #333333;
// }
// .agent-port {
//   margin-top: 20px;
//   margin-right: 30px;
//   text-align: right;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #999999;
// }
// .port-number {
//   margin-top: 10px;
//   margin-right: 30px;
//   text-align: right;
//   font-family: ArialMT;
//   font-size: 16px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #333333;
// }
// .model-name {
//   float: left;
//   margin-top: 32px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #666666;
//   margin-right: 10px;
// }
// .model-tupian {
//   float: left;
//   margin-top: 20px;
//   margin-left: 20px;
// }
// .to-detail {
//   float: left;
//   margin-top: 35px;
//   line-height: 10px;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #409eff;
//   padding-left: 10px;
//   border-left: 1px solid #409eff;
//   cursor: pointer;
// }
// .model-icon {
//   margin-top: 6px;
//   width: 26px;
//   height: 26px;
// }
// .control-center {
//   // float: left;
//   text-align: center;
//   margin-top: 40px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 30px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 20px;
//   letter-spacing: 0px;
//   color: #333333;
// }
// .curr-grade {
//   margin-top: 30px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 24px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   color: #666666;
// }
// .curr-num {
//   margin-top: 20px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   color: #999999;
// }
// .icon-ziyuan:before {
//   content: "\e670";
//   position:absolute;
//   z-index:2;
//   left: 45px;
//   top: 72px;
//   // color: #409eff;
// }
// .yuanxing {
//   position:absolute;
//   left: 20px;
//   top: 50px;
//   z-index:1;
//   width: 90px;
//   height: 90px;
//   // background-color: #459ffc;
//   opacity: 0.2;
//   border-radius: 50%;
// }
// .dev-status {
//   position:absolute;
//   // text-align: center;
//   // margin-top: 150px;
//   // left: 30px;
//   top: 150px;
//   height: 21px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 22px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   // color: #409eff;
// }
// .tuxing-left {
//   float: left;
//   margin-top: 20px;
//   width: 63%;
//   height: 840px;
// }
// .crossDirection-display {
//   height: 650px;
// }
// /*当屏幕小于等于1680px的屏幕样式*/
// @media only screen and (max-width: 1680px){
//   .crossDirection-display{
//         transform: scale(0.9);
//         transform-origin: center top;
//     }
//   .centerText .text {
//     font-size: 14px;
//   }
//  }
// /*当屏幕小于等于1440px的屏幕样式*/
// @media only screen and (max-width: 1440px){
//   .crossDirection-display{
//         transform: scale(0.8);
//         /* transform-origin: center top; */
//     }
//   .centerText .text {
//     font-size: 18px;
//   }
//  }
//  /*当屏幕小于等于1280px的屏幕样式*/
// @media only screen and (max-width: 1280px){
//   .crossDirection-display{
//         transform: scale(0.7);
//         transform-origin: center top;
//     }
//   .centerText .text {
//     font-size: 20px;
//   }
//  }
// .pattern-status {
//   height: 200px;
//   margin-left: 20px;
// }
// .pattern-name {
//   display: inline;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 20px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #303133;
// }
// .pattern-message {
//   display: inline;
//   margin-left: 10px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #606266;
// }
// .tuxing-right {
//   float: left;
//   margin-top: 20px;
//   width: 35%;
//   height: 840px;
// }
// .cross-mess {
//   margin-left: 5px;
//   height: 20px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 20px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #303133;
// }
// .cross-module {
//   float: left;
//   margin-top: 10px;
//   width: 95%;
//   min-width: 600px;
//   // height: 180px;
//   background-color: #f9fcff;
// }
// .cross-name {
//   // height: 13px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #606266;
// }
// .cross-value {
//   width: 180px;
//   height: 22px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #303133;
// }
// .control-model {
//   float: left;
//   margin-top: 10px;
//   // margin-left: 15px;
// }
// .single-model {
//   margin-right: 10px;
//   text-align: center;
//   cursor:pointer;
//   width: 70px;
//   height: 65px;
//   background-color: #edf6ff;
//   border-radius: 6px;
// }
// .single-model-select {
//   margin-right: 10px;
//   text-align: center;
//   cursor:pointer;
//   width: 70px;
//   height: 65px;
//   background-color: #c1e0ff;
//   border-radius: 6px;
//   // border: solid 1px #409eff;
// }
// .single-model-name {
//   margin-top: 3px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 12px;
//   font-weight: normal;
//   font-stretch: normal;
//   // line-height: 22px;
//   letter-spacing: 0px;
//   color: #606266;
// }
// .current-stage-num {
//   margin-top: 3px;
//   font-family: MicrosoftYaHei;
//   font-size: 12px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #303133;
// }
// .sloat-icon {
//   cursor:pointer;
//   margin-top: 15px;
// }
// .control-time {
//   margin-top: 40px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 30px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 20px;
//   letter-spacing: 0px;
//   color: #333333;
// }
// .control-time .time {
//   width: 50%;
//   display: inline-block;
//   text-align: center;
// }
// .control-time span:first-of-type {
//   border-right: 1px solid #e5e5e5;
// }
// .pattern-explain {
//     float: right;
//   }
</style>
<style rel="stylesheet/scss" lang="scss">
// .container-right .el-form-item__label {
//     text-align: right;
//     float: left;
//     font-size: 14px;
//     line-height: 40px;
//     padding: 0 12px 0 0;
//     -webkit-box-sizing: border-box;
//     box-sizing: border-box;
//     font-family: SourceHanSansCN-Regular;
//     font-weight: normal;
//     font-stretch: normal;
//     letter-spacing: 0px;
//     color: #999999;
// }
// .container-right .el-select {
//   width: 70%;
// }
</style>
