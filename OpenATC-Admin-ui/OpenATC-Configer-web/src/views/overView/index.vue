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
    <div :style="{'transform': `scale(${shrink})`, 'transform-origin': 'left top', 'height': '100%'}">
      <div class="wenzijiemian" v-show="!isShowGui">
        <TextPage
          ref="textPage"
          :agentId="agentId"
          :ip="ip"
          :port="port"
          :protocol="protocol"
          :devStatus="devStatus"
          :originControlData="controlData" />
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
              <!-- <span class="pattern-explain">：{{$t('edge.overview.phasesplit')}}</span> -->
              <span class="pattern-explain"><el-checkbox v-model="checked">{{$t('edge.pattern.overLap')}}</el-checkbox></span>
              <!-- P{{$t('edge.overview.phase')}} -->
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
            <OverLap
            :checked="checked"
            :overlap="overlap"
            :stageList="stagesListOver"
            :cycle="controlData.cycle"
            :controlData="controlData"
            />
          </div>
        </div>

        <div ref="tuxingRight" class="tuxing-right" >
          <RightPanel
            ref="rightpanel"
            :statusData="crossStatusData"
            :agentName="agentName"
            :responseTime="responseTime"
            :devStatus="devStatus"
            :agentId="agentId"
            :ip="ip"
            :platform="platform" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getTscControl, queryDevice } from '@/api/control'
import { registerMessage, uploadSingleTscParam } from '@/api/param'
import { setIframdevid } from '@/utils/auth'
import FloatImgBtn from '@/components/FloatImgBtn'
import CrossDiagram from './crossDirection/crossDiagram'
import BoardCard from '@/components/BoardCard'

import OverLap from '@/components/OverLap'
// import { getFaultMesZh, getFaultMesEn } from '../../utils/faultcode.js'
import { getMessageByCode } from '../../utils/responseMessage'
import PhaseDataModel from '@/views/overView/crossDirection/utils'
import CrossDiagramMgr from '@/EdgeMgr/controller/crossDiagramMgr'
import RightPanel from '@/components/SchemeConfig'
import TextPage from './textPage/index'
export default {
  name: 'overview',
  components: {
    FloatImgBtn,
    CrossDiagram,
    BoardCard,
    RightPanel,
    OverLap,
    TextPage
  },
  data () {
    return {
      controlData: {},
      checked: false,
      overlap: [],
      narr: [],
      stagesListOver: [],
      sidewalkPhaseData: [],
      phaseControlTimer: null, // 定时器
      registerMessageTimer: null, // 延时器
      volumeControlTimer: null, // 流量定时器
      ParamsMode: new Map([[0, '自主控制'], [1, '本地手动'], [2, '平台控制'], [3, '配置软件控制'], [4, '遥控器控制'], [5, '黄闪器控制'], [6, '降级控制'], [7, '脚本控制'], [8, '算法控制']]),
      ParamsModeEn: new Map([[0, 'Autonomous Control'], [1, 'Local Manual'], [2, 'Platform Control'], [3, 'Configuration Software Control'], [4, 'Remote Control'], [5, 'Yellow Flasher Control'], [6, 'Degradation Control'], [7, 'Script Control'], [8, 'Algorithm Control']]),
      ParamsControl: new Map([[0, '多时段'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '无电缆控制'], [11, '有电缆控制'], [12, '行人过街控制'], [13, '方案恢复过渡'], [14, '相位驻留'], [15, '通道检测'], [16, '方向锁定'], [17, '韦伯斯特单点控制'], [18, '预留18'], [19, '感应式行人过街'], [20, '方案干预'], [100, '方案干预'], [21, '预留21'], [22, '相位锁定'], [23, '相位放行控制'], [24, '紧急控制'], [99, '设备维护']]),
      ParamsControlEn: new Map([[0, 'Multi Period'], [1, 'Yellow Flash Control'], [2, 'Red Control'], [3, 'Dark Control'], [4, 'Step'], [5, 'Fixed_Cycle Control'], [6, 'Free Control'], [7, 'Coordinated Induction Control'], [8, 'Pattern Selection Control'], [9, 'Adaptive Control'], [10, 'Wireless Control'], [11, 'Cable Control'], [12, 'Pedestrian Crossing Control'], [13, 'Pattern recovery'], [14, 'Phase dwell'], [15, 'Channel Detection'], [16, 'Direction lock'], [17, 'Webster Single Point Coordination'], [18, 'Reserving18'], [19, 'Inductive Pedestrian Crossing Control'], [20, 'Program Intervention'], [100, 'Program Intervention'], [21, 'Reserving21'], [22, 'Priority Control'], [23, 'Close Phase'], [24, 'Emergency Control'], [99, 'Device Mantenance']]),
      phaseType: new Map([[1, '红'], [2, '黄'], [3, '绿']]), // phaseType表示红，黄，绿
      phaseTypeEn: new Map([[1, 'Red'], [2, 'Yellow'], [3, 'Green']]), // phaseType表示红，黄，绿
      ip: '--',
      port: '--',
      protocol: '--',
      devStatus: 1,
      mode: '2',
      agentId: '0',
      agentName: '--',
      platform: undefined,
      crossStatusData: {}, // 路口状态数据
      reset: false,
      responseTime: 0,
      phaseList: [], // 当前相位集合
      intervalFlag: true,
      shrink: 1,
      isResend: true,
      commonHeight: undefined // 左右侧面板的高度值
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
          this.resetCrossDiagram()
          this.getPlatform()
          this.$refs.rightpanel.getFaultById()
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
    }
  },
  created () {
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
    this.globalParamModel = this.$store.getters.globalParamModel
    this.overlap = this.globalParamModel.getParamsByType('overlaplList')
    if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
      this.agentId = this.$route.query.agentid
      setIframdevid(this.agentId)
      this.resetCrossDiagram()
      this.registerMessage() // 注册消息
    } else {
      this.queryDevParams() // 查询设备信息
    }
  },
  mounted () {
    this.getPlatform()
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
    registerMessage () {
      registerMessage(this.agentId).then(data => {
        if (!data.data.success) {
          this.devStatus = 2
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        this.devStatus = 3
        this.clearPatternInterval() // 清除其他定时器
        this.clearVolumeInterval()
        this.phaseControlTimer = setInterval(() => {
          if (this.intervalFlag) {
            this.initData()
          }
        }, 1000)
        this.$refs.textPage.getVolume()
        this.volumeControlTimer = setInterval(() => {
          this.$refs.textPage.getVolume()
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
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            if (this.isResend) {
              this.reSend()
            }
            return
          }
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          this.clearPatternInterval() // 清除其他定时器
          this.clearVolumeInterval()
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        this.crossStatusData = JSON.parse(JSON.stringify(data.data.data.data))
        let TscData = JSON.parse(JSON.stringify(data.data.data.data))
        // this.handleStageData(TscData) // 处理阶段（驻留）stage数据
        this.controlData = this.handleGetData(TscData)
        this.checkStage(this.controlData)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getControl (newList) { // 总揽实时数据
      let currentIds = ''
      let lastCurrentIds = ''
      this.stateList = [0]
      this.narr = []
      let overstagesList = []
      let phaseList = []
      for (let j = 0; j <= this.max; j++) { // 指针长度
        for (let i = 0; i < newList.length; i++) { // 环列表
          let ring = newList[i]// 每个环对象
          let sum = 0
          for (let n = 0; n < ring.length; n++) { // 相位累计长度
            if (ring[n].mode !== 7) {
              sum = sum + ring[n].split
              if (j < sum) {
                let phaseId = ring[n].id
                currentIds = currentIds + '-' + phaseId
                break
              }
            }
          }
        }
        if (lastCurrentIds !== currentIds && lastCurrentIds !== '') { // 当前相位id和上一个相比不同说明相位变化了
          phaseList.push(lastCurrentIds)
          this.stateList.push(j)// 阶段累计长度的集合
        }
        lastCurrentIds = currentIds
        currentIds = ''
      }
      let newPhaselist = []
      phaseList.forEach(i => {
        let rangeArr = i.split('-').map(Number)
        if (rangeArr.length > 2) {
          newPhaselist.push([
            JSON.parse(JSON.stringify(rangeArr[1])),
            JSON.parse(JSON.stringify(rangeArr[2]))
          ])
        } else {
          newPhaselist.push([
            JSON.parse(JSON.stringify(rangeArr[1]))
          ])
        }
      })
      for (let i = this.stateList.length - 1; i >= 1; i--) {
        this.narr.push(this.stateList[i] - this.stateList[i - 1])
      }
      this.narr.reverse()// 阶段差
      for (let i = 0; i < newPhaselist.length; i++) {
        let stage = JSON.parse(JSON.stringify(newPhaselist[i]))
        let stageItem = this.getStageItem(stage, newList, i)
        overstagesList.push(JSON.parse(JSON.stringify(stageItem)))
      }
      this.stagesListOver = JSON.parse(JSON.stringify(overstagesList))
    },
    getStageItem (stageArr, ringsList, i) {
      let res = {
        key: i,
        split: this.narr[i], // 阶段绿性比
        stages: stageArr,
        delaystart: 0,
        advanceend: 0
      }
      // let splitArr = []
      let delaystartArr = []
      let advanceendArr = []
      for (let rings of ringsList) {
        for (let ring of rings) {
          if (stageArr.includes(ring.id)) {
            // let split = ring.value
            let delaystart = ring.delaystart
            let advanceend = ring.advanceend
            // splitArr.push(split)
            delaystartArr.push(delaystart)
            advanceendArr.push(advanceend)
          }
        }
      }
      // splitArr.sort(function (a, b) { return a - b })
      delaystartArr.sort(function (a, b) { return b - a })
      advanceendArr.sort(function (a, b) { return a - b })
      // res.split = splitArr.length > 0 ? splitArr[0] : 0
      res.delaystart = delaystartArr.length > 0 ? delaystartArr[0] : 0
      res.advanceend = advanceendArr.length > 0 ? advanceendArr[0] : 0
      return res
    },
    checkStage (rings) {
      if (!rings.phase || !rings.rings) return
      let mapAdd = rings.phase.map(item => {
        return {
          id: item.id,
          split: item.split
        }
      })
      let newRings = rings.rings.map(j => {
        return j.sequence
      })
      let newList = newRings.map(item => {
        let ret = []
        item.map(i => {
          const find = mapAdd.find(j => j.id === i)
          if (find) {
            ret.push(find)
          }
        })
        return ret
      })
      let mapAdds = newList.map(item => {
        return item.map(val => {
          return val.split
        })
      })
      let maxCycle = mapAdds.length > 0 ? mapAdds.map(item => {
        return item.length > 0 ? item.reduce((a, b) => {
          return a + b
        }) : 0
      }) : 0
      this.max = Math.max(...maxCycle)// 每个环的周期最大值
      this.getControl(newList)
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
    reconnectionDev () {
      this.registerMessage()
    },
    resetCrossDiagram () {
      this.reset = false
      this.$nextTick(() => {
        this.reset = true
      })
    },
    queryDevParams () {
      queryDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        let devParams = res.data.data.jsonparam
        this.ip = devParams.ip
        this.port = String(devParams.port)
        this.protocol = res.data.data.protocol
        this.agentId = res.data.data.agentid
        if (res.data.data.name) {
          this.agentName = res.data.data.name
        }
        this.platform = res.data.data.platform
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
        this.port = String(devParams.port)
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
    }
  },
  destroyed () {
    this.isResend = false
    this.clearPatternInterval() // 清除定时器
    this.clearVolumeInterval()
    this.clearRegisterMessageTimer() // 清除定时器
    this.getPlatform()
  }
}
</script>
