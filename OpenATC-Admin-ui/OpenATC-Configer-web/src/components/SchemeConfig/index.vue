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
  <div class="scheme-config" style="height: 100%;">
    <FaultDetailModal ref="faultDetail" :agentId="agentId" @refreshFault="getFaultById"></FaultDetailModal>
        <div style="height: 100%;">
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
               :realtimeStatusModalvisible="realtimeStatusModalvisible"
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
                :sidewalkPhaseData="sidewalkPhaseData"
                :realtimeStatusModalvisible="realtimeStatusModalvisible"
                @closePhaseBack="closePhaseBack"
                @closePhaseControl= "closePhaseControl" />
              <LockingPhaseControlModal
                v-if="specialPage === 'lockingphase'"
                :controlData="controlData"
                :closePhaseRings="phaseRings"
                :sidewalkPhaseData="sidewalkPhaseData"
                :realtimeStatusModalvisible="realtimeStatusModalvisible"
                @closePhaseBack="closePhaseBack"
                @closePhaseControl= "closePhaseControl" />
            </div>
          </transition>

          <transition name="fade-left" mode="out-in"
          enter-active-class="animated fadeInLeft"
          leave-active-class="animated fadeOutLeft">
            <div class="right-control-part" style="position: absolute;width: 100%;" v-show="!isOperation">
              <RealtimeStatusModal
                :controlData="controlData"
                :currModel="currModel"
                :ip="ip"
                :platform="platform"
                :responseTime="responseTime"
                :curFaultList="curFaultList"
                :confirmedFault="confirmedFault"
                :ignoredFault="ignoredFault"
                :untreatedFault="untreatedFault"
                :currentStage="currentStage"
                :closePhase="closePhase"
                :agentName="agentName"
                :devStatus="devStatus"
                :agentId="agentId"
                :stagesList="stagesList"
                :sidewalkPhaseData="sidewalkPhaseData"
                @changeStatus="changeStatus"
                @showFaultDetail="showFaultDetail"/>
            </div>
          </transition>
        </div>
  </div>
</template>

<script>
import { putTscControl } from '@/api/control'
import { uploadSingleTscParam } from '@/api/param'
import RealtimeStatusModal from './realtimeStatusModal'
import ManualControlModal from './manualControlModal'
import ClosePhaseControlModal from './closePhaselControlModal'
import LockingPhaseControlModal from './lockingPhaselControlModal'
// import { getFaultMesZh, getFaultMesEn } from '../../utils/faultcode.js'
import { getMessageByCode } from '../../utils/responseMessage'
import { GetAllFaultRange } from '@/api/fault'
import FaultDetailModal from '@/components/FaultDetailModal'
import PhaseDataModel from '@/views/overView/crossDirection/utils'
import CrossDiagramMgr from '@/EdgeMgr/controller/crossDiagramMgr'
import { setToken } from '@/utils/auth'
export default {
  name: 'scheme-config',
  components: {
    RealtimeStatusModal,
    ManualControlModal,
    ClosePhaseControlModal,
    LockingPhaseControlModal,
    FaultDetailModal
  },
  props: {
    responseTime: {
      type: Number,
      default: 0
    },
    statusData: {
      type: Object,
      required: true
    },
    agentName: {
      type: String,
      default: ''
    },
    devStatus: {
      type: Number,
      default: 0
    },
    agentId: {
      type: String,
      default: '0'
    },
    ip: {
      type: String
    },
    platform: {
      type: String
    },
    realtimeStatusModalvisible: {
      type: Boolean,
      default: true
    },
    Token: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      crossStatusData: null,
      controlData: {},
      control: '',
      sidewalkPhaseData: [],
      stagesList: [],
      currentStage: 0,
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
      faultTimer: null, // 当前故障定时器
      ParamsMode: new Map([[0, '自主控制'], [1, '本地手动'], [2, '平台控制'], [3, '配置软件控制'], [4, '遥控器控制'], [5, '黄闪器控制'], [6, '降级控制'], [7, '脚本控制'], [8, '算法控制']]),
      ParamsModeEn: new Map([[0, 'Autonomous Control'], [1, 'Local Manual'], [2, 'Platform Control'], [3, 'Configuration Software Control'], [4, 'Remote Control'], [5, 'Yellow Flasher Control'], [6, 'Degradation Control'], [7, 'Script Control'], [8, 'Algorithm Control']]),
      ParamsControl: new Map([[0, '多时段'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '无电缆控制'], [11, '有电缆控制'], [12, '行人过街控制'], [13, '方案恢复过渡'], [14, '相位驻留'], [15, '通道检测'], [16, '方向锁定'], [17, '韦伯斯特单点控制'], [18, '预留18'], [19, '感应式行人过街'], [20, '方案干预'], [100, '方案干预'], [21, '预留21'], [22, '相位锁定'], [23, '相位放行控制'], [24, '紧急控制'], [99, '设备维护']]),
      ParamsControlEn: new Map([[0, 'Multi Period'], [1, 'Yellow Flash Control'], [2, 'Red Control'], [3, 'Dark Control'], [4, 'Step'], [5, 'Fixed_Cycle Control'], [6, 'Free Control'], [7, 'Coordinated Induction Control'], [8, 'Pattern Selection Control'], [9, 'Adaptive Control'], [10, 'Wireless Control'], [11, 'Cable Control'], [12, 'Pedestrian Crossing Control'], [13, 'Pattern recovery'], [14, 'Phase dwell'], [15, 'Channel Detection'], [16, 'Direction lock'], [17, 'Webster Single Point Coordination'], [18, 'Reserving18'], [19, 'Inductive Pedestrian Crossing Control'], [20, 'Program Intervention'], [100, 'Program Intervention'], [21, 'Reserving21'], [22, 'Priority Control'], [23, 'Close Phase'], [24, 'Emergency Control'], [99, 'Device Mantenance']]),
      phaseType: new Map([[1, '红'], [2, '黄'], [3, '绿']]), // phaseType表示红，黄，绿
      phaseTypeEn: new Map([[1, 'Red'], [2, 'Yellow'], [3, 'Green']]), // phaseType表示红，黄，绿
      mode: '2',
      loading: {},
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
      closePhase: [],
      isOperation: false, // 是否为手动可操作状态
      tempPatternid: 0, // 控制方式手动操作的情况下的控制编号的临时值。
      tempDelay: 0, // 控制方式手动操作的情况下的延迟时间的临时值。
      tempDuration: 0, // 控制方式手动操作的情况下的持续时间的临时值。
      phaseList: [], // 当前相位集合
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
  watch: {
    statusData: {
      handler: function (val) {
        this.initData()
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
    Token: {
      handler: function (val) {
        this.setPropsToken(val)
      }
    }
  },
  created () {
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
    if (this.realtimeStatusModalvisible === false) {
      this.changeStatus()
    }
  },
  async mounted () {
    this.setPropsToken(this.Token)
    await this.getPhase()
    this.getFault()
    this.initData()
  },
  methods: {
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
    clearFaultInterval () {
      if (this.faultTimer !== null) {
        clearInterval(this.faultTimer) // 清除流量定时器
        this.faultTimer = null
      }
    },
    initData () {
      this.crossStatusData = JSON.parse(JSON.stringify(this.statusData))
      let TscData = JSON.parse(JSON.stringify(this.crossStatusData))
      this.currModel = TscData.control
      this.handleStageData(TscData) // 处理阶段（驻留）stage数据
      this.controlData = this.handleGetData(TscData)
      this.handleGetPhaseClose()
    },
    getBusPos () {
      // 公交相位信息
      this.busPhaseData = []
      this.phaseList.forEach((ele, i) => {
        if (ele.controltype >= 3 && ele.controltype <= 5) {
          ele.direction.forEach((dir, index) => {
          // 车道相位
            this.busPhaseData.push({
              // key: this.CrossDiagramMgr.getUniqueKey('busphase'),
              phaseid: ele.id, // 相位id，用于对应相位状态
              id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
              name: this.PhaseDataModel.getBusPhasePos(dir).name,
              controltype: ele.controltype
            })
          })
        }
      })
      let result = []
      let obj = {}
      for (var i = 0; i < this.busPhaseData.length; i++) {
        if (!obj[this.busPhaseData[i].phaseid]) {
          result.push(this.busPhaseData[i])
          obj[this.busPhaseData[i].phaseid] = true
        }
      }
      this.busPhaseData = result
    },
    handleStageData (data) {
      this.getBusPos()
      this.stagesList = []
      let busPhaseData = this.busPhaseData
      this.currentStage = data.current_stage
      let stages = data.stages
      if (!stages) return
      let stagesTemp = []
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
        if (directionList.length === 0) return
        tempList = directionList.map(dir => ({
          id: dir,
          color: '#606266',
          controltype: stageControType,
          peddirection: peddirections
        }))
        stagesTemp.push(tempList)
        this.stagesList = JSON.parse(JSON.stringify(stagesTemp))
      }
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
    selectModel (value) {
      if (!this.isOperation) return
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
      if (control.control === 0) {
        // 恢复自主控制（多时段）时，value为当前控制方式
        control.value = this.currModel
      }
      if (!this.realtimeStatusModalvisible) {
        console.log(control)
        return
      }
      this.lockScreen()
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
        console.log(error)
      })
    },
    getPhase () {
      let _this = this
      return new Promise(function (resolve, reject) {
        uploadSingleTscParam('phase').then(data => {
          let res = data.data
          if (!res.success) {
            if (res.code === '4003') {
              _this.$message.error(_this.$t('edge.errorTip.devicenotonline'))
              return
            }
            _this.$message.error(getMessageByCode(data.data.code, _this.$i18n.locale))
            return
          }
          _this.phaseList = res.data.data.phaseList
          resolve()
        })
      })
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
        let closePhase = []
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
            closePhase.push({
              id: phase.id,
              typename: typename
            })
          }
        })
        this.closePhase = JSON.parse(JSON.stringify(closePhase))
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
      if (!this.realtimeStatusModalvisible) {
        console.log(controldata)
        return
      }
      this.lockScreen()
      putTscControl(controldata).then(data => {
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
      // 相位锁定选项默认都按照解锁状态显示
      addphse.locktype = 0
      addphse.close = 0
      if (this.crossStatusData !== null && this.crossStatusData.phase) {
        // 如果方案状态相位有close字段，这边就需要对应close状态进相位关断控制的选项里
        let phaseStatus = this.crossStatusData.phase.filter(ele => ele.id === phase.id)[0]
        addphse = {...addphse, ...phaseStatus}
      }
      return addphse
    },
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
    },
    setPropsToken (token) {
      // 获取组件外传入的token，便于独立组件调用接口
      if (token && token !== '') {
        setToken(token)
      }
    }
  },
  destroyed () {
    this.clearFaultInterval()
  }
}
</script>
