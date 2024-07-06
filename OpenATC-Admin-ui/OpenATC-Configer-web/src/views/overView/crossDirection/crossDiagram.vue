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
  <div class="crossImg">
    <!-- 右行道路 B-->
    <div class="right-dir-road" v-if="roadDir === 'right'">
      <div class="centerText" v-if="crossType !== 'Customroads'" :class="{'countdownBg': isLoaded}">
      <!-- 相位倒计时 -->
      <div class="phaseCountdown" v-if="devStatus === 3 && isLoaded && isHasPhase">
        <div v-for="curPhase in phaseCountdownList" :key="curPhase.id" :style="{color: curPhase.phaseCountdownColor}">
          <span style="float: left;font-size: 20px;color: #fff;width: 70px;">{{$t('edge.overview.phase')}}{{curPhase.id}}:</span>
          <span style="float: left;">{{curPhase.phaseCountdown}}</span>
        </div>
      </div>
      <!-- 手动刷新 -->
      <div v-if="!isLoaded">
        <RefreshSvg @click.native="refresh"/>
        <span class="text">{{$t('edge.overview.getintersectionmapagain')}}</span>
      </div>
    </div>
      <!-- 路口底图 -->
      <div class="baseImg">
        <!-- 城市道路 -->
        <CrossRoadsSvg v-if="crossType === 'Crossroads'"/>
        <TShapeEastRoadsSvg v-if="crossType === 'TypeT-east'"/>
        <TShapeWestRoadsSvg v-if="crossType === 'TypeT-west'"/>
        <TShapeNorthRoadsSvg v-if="crossType === 'TypeT-north'"/>
        <TShapeSouthRoadsSvg v-if="crossType === 'TypeT-south'"/>
        <!-- 其他路口 -->
        <CustomRoadsSvg v-if="crossType === 'Customroads'"/>
        <!-- 匝道 -->
        <RampEastRoadsSvg v-if="crossType === 'ramp-east'" />
        <RampWestRoadsSvg v-if="crossType === 'ramp-west'" />
        <RampNorthRoadsSvg v-if="crossType === 'ramp-north'" />
        <RampSouthRoadsSvg v-if="crossType === 'ramp-south'" />
        <!-- 路段行人过街 -->
        <PedSectionSNSvg v-if="crossType === 'ped-section-south-north'" />
        <PedSectionEWSvg v-if="crossType === 'ped-section-east-west'" />
      </div>
      <!-- 城市道路状态-->
      <div v-if="mainType === '100' || mainType === '101' || mainType === '104'">
        <!-- 人行道 -->
        <div class="sidewalk" v-if="resetflag && isLoaded">
          <SidewalkSvg v-for="(side, index) in compSidewalkPhaseData" :key="side.key + '-' + index" :Data="side" :crossType="crossType" />
        </div>
        <!-- 车道相位 -->
        <div v-if="resetflag" class="phaseIcon">
          <PhaseIconSvg v-for="(item, index) in compLanePhaseData" :key="item.key + '-' + index" :Data="item"/>
        </div>
         <!-- 公交相位 -->
        <div v-if="resetflag" class="busIcon">
          <BusMapSvg v-for="(item, index) in busPhaseData" :key="item.key + '-' + index" :Data="item" />
          <PhaseIconSvg v-for="(item, index) in busPhaseData" :key="item.key + '-' + index" :Data="item"/>
        </div>
      </div>
      <!-- 匝道状态 -->
        <!-- 车道相位 -->
      <div v-if="resetflag && mainType === '103'">
        <RampPhaseIconSvg v-for="(item, index) in LanePhaseData" :key="item.key + '-' + index" :Data="item" />
      </div>
    </div>
    <!-- 右行道路 E-->

    <!-- 左行道路 B-->
    <div class="left-dir-road" v-if="roadDir === 'left'">
      <div class="centerText" v-if="mainType === '100' || mainType === '101'" :class="{'countdownBg': isLoaded}">
        <!-- 相位倒计时 -->
        <div class="phaseCountdown" v-if="devStatus === 3 && isLoaded && isHasPhase">
          <div v-for="curPhase in phaseCountdownList" :key="curPhase.id" :style="{color: curPhase.phaseCountdownColor}">
            <span style="float: left;font-size: 20px;color: #fff;width: 70px;">{{$t('edge.overview.phase')}}{{curPhase.id}}:</span>
            <span style="float: left;">{{curPhase.phaseCountdown}}</span>
          </div>
        </div>
        <!-- 手动刷新 -->
        <div v-if="!isLoaded">
          <RefreshSvg @click.native="refresh"/>
          <span class="text">{{$t('edge.overview.getintersectionmapagain')}}</span>
        </div>
      </div>
      <!-- 路口底图 -->
      <div class="baseImg">
        <!-- 城市道路 -->
        <LCrossRoadsSvg v-if="crossType === 'Crossroads'"/>
        <LTShapeEastRoadsSvg v-if="crossType === 'TypeT-east'"/>
        <LTShapeWestRoadsSvg v-if="crossType === 'TypeT-west'"/>
        <LTShapeNorthRoadsSvg v-if="crossType === 'TypeT-north'"/>
        <LTShapeSouthRoadsSvg v-if="crossType === 'TypeT-south'"/>
        <!-- 其他路口 -->
        <CustomRoadsSvg v-if="mainType !== '100' && mainType !== '101'"/>
      </div>
      <!-- 城市道路状态-->
      <div v-if="mainType === '100' || mainType === '101'">
        <!-- 人行道 -->
        <div class="sidewalk" v-if="resetflag && isLoaded">
          <SidewalkSvg v-for="side in compSidewalkPhaseData" :key="side.key" :Data="side" :crossType="crossType" />
        </div>
        <!-- 车道相位 -->
        <div v-if="resetflag" class="phaseIcon">
          <LPhaseIconSvg v-for="item in compLanePhaseData" :key="item.key" :Data="item"/>
        </div>
         <!-- 公交相位 -->
        <div v-if="resetflag" class="busIcon">
          <BusMapSvg v-for="(item, index) in busPhaseData" :key="'Busmap-' + item.key + '-' + index" :Data="item" />
          <LPhaseIconSvg v-for="(item, index) in busPhaseData" :key="item.key + '-' + index" :Data="item"/>
        </div>
      </div>
    </div>
    <!-- 左行道路 E-->
  </div>
</template>
<script>
import PhaseIconSvg from './phaseIcon/phaseIconSvg'
import PhaseDataModel from './utils.js'
import { getIntersectionInfo } from '@/api/template'
import CrossRoadsSvg from './baseImg/CrossRoadsSvg'
import TShapeEastRoadsSvg from './baseImg/TShapeEastRoadsSvg'
import TShapeWestRoadsSvg from './baseImg/TShapeWestRoadsSvg.vue'
import TShapeNorthRoadsSvg from './baseImg/TShapeNorthRoadsSvg.vue'
import TShapeSouthRoadsSvg from './baseImg/TShapeSouthRoadsSvg.vue'
import CustomRoadsSvg from './baseImg/CustomRoadsSvg.vue'
import RefreshSvg from './baseImg/refreshSvg'
import SidewalkSvg from './baseImg/SidewalkSvg'
import RampEastRoadsSvg from './baseImg/RampEastSvg'
import RampWestRoadsSvg from './baseImg/RampWestSvg'
import RampNorthRoadsSvg from './baseImg/RampNorthSvg'
import RampSouthRoadsSvg from './baseImg/RampSouthSvg'
import RampPhaseIconSvg from './phaseIcon/rampPhaseIconSvg'
import PedSectionEWSvg from './baseImg/PedSectionEWSvg'
import PedSectionSNSvg from './baseImg/PedSectionSNSvg'
import { mapState } from 'vuex'
import LCrossRoadsSvg from './baseImg/leftroad/LCrossRoadsSvg'
import LTShapeEastRoadsSvg from './baseImg/leftroad/LTShapeEastRoadsSvg'
import LTShapeWestRoadsSvg from './baseImg/leftroad/LTShapeWestRoadsSvg.vue'
import LTShapeNorthRoadsSvg from './baseImg/leftroad/LTShapeNorthRoadsSvg.vue'
import LTShapeSouthRoadsSvg from './baseImg/leftroad/LTShapeSouthRoadsSvg.vue'
import LPhaseIconSvg from './phaseIcon/LphaseIconSvg'
import CrossDiagramMgr from '@/EdgeMgr/controller/crossDiagramMgr'
import BusMapSvg from './busIcon/busMapSvg'
export default {
  name: 'crossDiagram',
  components: {
    PhaseIconSvg,
    CrossRoadsSvg,
    TShapeEastRoadsSvg,
    TShapeWestRoadsSvg,
    TShapeNorthRoadsSvg,
    TShapeSouthRoadsSvg,
    CustomRoadsSvg,
    RefreshSvg,
    SidewalkSvg,
    RampEastRoadsSvg,
    RampWestRoadsSvg,
    RampNorthRoadsSvg,
    RampSouthRoadsSvg,
    RampPhaseIconSvg,
    PedSectionEWSvg,
    PedSectionSNSvg,
    LCrossRoadsSvg,
    LTShapeEastRoadsSvg,
    LTShapeWestRoadsSvg,
    LTShapeNorthRoadsSvg,
    LTShapeSouthRoadsSvg,
    LPhaseIconSvg,
    BusMapSvg
  },
  props: {
    crossStatusData: {
      type: Object
    },
    agentId: {
      type: String
    },
    devStatus: {
      type: Number
    }
  },
  computed: {
    ...mapState({
      roadDirection: state => state.globalParam.roadDirection
    })
  },
  watch: {
    tempType: {
      handler: function (val) {
        this.getCrossType()
      }
    },
    crossStatusData: {
      handler: function (val, oldVal) {
        // 路口状态数据
        this.statusData = JSON.parse(JSON.stringify(val))
        this.phaseStatusList = val.phase
        this.overlapStatusList = val.overlap
        // 默认显示相位数据（包括黄闪、全红、关灯状态下，或者匝道，均不做比对跟随相位的处理）
        this.compLanePhaseData = JSON.parse(JSON.stringify(this.LanePhaseData))
        this.compSidewalkPhaseData = JSON.parse(JSON.stringify(this.sidewalkPhaseData))
        if (!val.phase && !this.overlapStatusList) {
          // 黄闪、全红、关灯状态下，相位字段(跟随相位字段)不存在，此处根据control字段控制车道相位颜色
          switch (val.control) {
            case 1: this.handlePhaseStatus('黄闪')
              break
            case 2: this.handlePhaseStatus('全红')
              break
            case 3: this.handlePhaseStatus('关灯')
              break
            default: this.handlePhaseStatus('默认')
          }
          this.isHasPhase = false
          return
        }

        this.curPhase = val.current_phase
        this.isHasPhase = true
        // 正常情况下，获取车道相位、车道跟随相位、相位倒计时、行人相位、行人跟随相位 的状态
        this.getPhaseStatus()
        this.getOverlapPhaseStatus()
        this.getCurPhaseCountdown()
        if (this.mainType === '100' || this.mainType === '101' || this.mainType === '104') {
          // 城市道路和路段行人过街才显示人行道状态
          this.getpedStatus()
          this.getOverlapPedStatus()
          // 算法对比行人相位与行人跟随相位的状态
          this.comparePedStatus()
          // 算法对比车道相位与车道跟随相位的状态
          this.comparePhaseStatus()
        }
        // console.log('this.phaseStatusMap 相位状态映射', this.phaseStatusMap)
        // console.log('LanePhaseData 车道相位', this.LanePhaseData)
        // console.log('sidewalkPhaseData 行人相位', this.sidewalkPhaseData)
        // console.log('overlapPhaseStatusMap 跟随相位状态映射', this.overlapPhaseStatusMap)
        // console.log('overlapLanePhaseData 车道跟随相位', this.overlapLanePhaseData)
        // console.log('this.overlapsidewalkPhaseData 行人跟随相位', this.overlapsidewalkPhaseData)
      },
      // 深度观察监听
      deep: true
    },
    devStatus: {
      handler: function (val) {
        if (val === 3) return
        this.handleDefaultStatus()
      }
    }
  },
  data () {
    return {
      roadDir: 'right', // 道路行车方向，默认右行
      phaseCountdownList: [], // 相位倒计时列表
      statusData: null, // 信号机状态
      LanePhaseData: [], // 车道相位数据
      overlapLanePhaseData: [], // 车道跟随相位数据
      curPhase: [], // 当前相位列表
      phaseStatusList: [], // 相位状态列表
      phaseStatusMap: new Map(), // 相位状态映射
      overlapPhaseStatusMap: new Map(), // 跟随相位状态映射
      ColorMap: new Map([[0, '#828282'], [1, '#ff2828'], [2, '#f7b500'], [3, '#77fb65'], [4, '#77fb65'], [5, '#f7b500']]), // 当前相位状态 --- 0：关灯, 1：红, 2：黄,  3：绿, 4：绿闪, 5：黄闪
      SidewalkColorMap: new Map([[0, '#828282'], [1, '#e24b4b'], [3, '#7bd66b']]),
      tempType: '', // 模版类型
      mainType: '101', // 路口形状
      mainDirection: '000', // 路口方向
      crossType: '', // 路口底图类型
      isLoaded: false, // 是否成功加载底图
      isHasPhase: true, // 是否有相位状态数据
      phaseControlColorMap: new Map([['黄闪', '#f7b500'], ['全红', '#ff2828'], ['关灯', '#828282'], ['默认', '#fff']]),
      sidewalkPhaseData: [], // 行人相位
      overlapsidewalkPhaseData: [], // 行人跟随相位
      resetflag: true, // 离线后，控制行人相位、车道相位reset标识
      compLanePhaseData: [], // 对比车道相位和车道跟随相位后，显示的数据
      compSidewalkPhaseData: [], // // 对比行人相位和车道跟随相位后，显示的数据
      comdirePhaseData: [], // 对比相同方向车道相位数据后，被删减的唯一direction的数组
      comdireOverlapPhaseData: [], // 对比相同方向车道跟随相位数据后，被删减的唯一direction的数组
      busPhaseData: [] // 公交相位数据
    }
  },
  methods: {
    comparePhaseStatus () {
      // 对比车道： 跟随相位和相位的状态数据（此处判断是为了保证被比较的数据direction都是唯一的）
      if (!this.comdirePhaseData && !this.comdireOverlapPhaseData) {
        this.compLanePhaseData = this.CrossDiagramMgr.compare(this.LanePhaseData, this.overlapLanePhaseData, 'type')
      } else if (!this.comdireOverlapPhaseData) {
        this.compLanePhaseData = this.CrossDiagramMgr.compare(this.comdirePhaseData, this.overlapLanePhaseData, 'type')
      } else if (!this.comdirePhaseData) {
        this.compLanePhaseData = this.CrossDiagramMgr.compare(this.LanePhaseData, this.comdireOverlapPhaseData, 'type')
      } else {
        this.compLanePhaseData = this.CrossDiagramMgr.compare(this.comdirePhaseData, this.comdireOverlapPhaseData, 'type')
      }
    },
    comparePedStatus () {
      // 对比人行道： 跟随相位和相位的状态数据
      this.compSidewalkPhaseData = this.CrossDiagramMgr.compare(this.sidewalkPhaseData, this.overlapsidewalkPhaseData, 'pedtype')
    },
    resetPhaseStatus () {
      // 车道相位、行人相位恢复默认状态
      this.resetflag = false
      this.$nextTick(() => {
        this.resetflag = true
      })
    },
    handleDefaultStatus () {
      // 恢复默认状态
      if (this.compLanePhaseData.length) {
        this.phaseCountdown = ''
        this.phaseCountdownColor = '#fff'
        this.compLanePhaseData.forEach(data => {
          data.color = '#fff'
        })
      }
      if (this.compSidewalkPhaseData.length) {
        this.compSidewalkPhaseData.forEach(data => {
          data.color = '#fff'
        })
      }
      this.phaseCountdownList = []
      this.resetPhaseStatus()
    },
    handlePhaseStatus (Control) {
      this.resetPhaseStatus()
      // 控制黄闪、全红、关灯、默认情况下的车道相位颜色和倒计时颜色
      if (Control === '默认') {
        // 倒计时恢复默认颜色
        this.phaseCountdownList.forEach(item => {
          item.phaseCountdown = ''
          item.id = ''
          item.phaseCountdownColor = '#fff'
        })
      }
      if (this.compLanePhaseData.length) {
        // if (this.mainType === '100' || this.mainType === '101') {
        //   // 车道相位设置对应颜色
        //   this.getPhasePos()
        // }
        // if (this.mainType === '103') {
        //   this.getRampPhasePos()
        // }
        const compLanePhaseData = this.compLanePhaseData.map(data => ({
          ...data,
          color: this.phaseControlColorMap.get(Control)
        }))
        this.compLanePhaseData = JSON.parse(JSON.stringify(compLanePhaseData))
      }
      if (this.compSidewalkPhaseData.length) {
        const compSidewalkPhaseData = this.compSidewalkPhaseData.map(data => ({
          ...data,
          color: this.phaseControlColorMap.get(Control)
        }))
        this.compSidewalkPhaseData = JSON.parse(JSON.stringify(compSidewalkPhaseData))
      }
    },
    getPhaseStatus () {
      // 得到车道相位状态（颜色）
      this.comdirePhaseData = []
      this.phaseStatusList.map(phase => {
        let phaseId = phase.id
        let phaseInfo = {
          type: phase.type,
          phaseCountdown: phase.countdown,
          pedtype: phase.pedtype
        }
        this.phaseStatusMap.set(phaseId, phaseInfo)
      })
      let curLanePhaseData = []
      for (let i = 0; i < this.LanePhaseData.length; i++) {
        let curPhaseStatus = this.phaseStatusMap.get(this.LanePhaseData[i].phaseid)
        if (!curPhaseStatus) continue
        const data = {
          ...this.LanePhaseData[i],
          type: curPhaseStatus.type,
          color: this.ColorMap.get(curPhaseStatus.type),
          phaseCountdown: curPhaseStatus.phaseCountdown,
          flag: 'phase' // 车道相位数据标识
        }
        curLanePhaseData.push(data)
      }
      this.LanePhaseData = JSON.parse(JSON.stringify(curLanePhaseData))
      // 处理相位数据中，方向direction重复的情况：相同direction下，按照状态的优先级显示该方向的灯色：绿灯(3) > 绿闪(4) > 黄灯(2) > 红灯(1)
      // 如果有相同direction，处理后会改变原数组长度，导致第二次无法正确比较状态，因此需要中间变量存储
      this.comdirePhaseData = JSON.parse(JSON.stringify(this.CrossDiagramMgr.compareRepeatDirection(this.LanePhaseData, 'type', 'phase')))
    },
    getOverlapPhaseStatus () {
      // 得到车道跟随相位状态（颜色）
      this.comdireOverlapPhaseData = []
      if (this.overlapStatusList) {
        this.overlapStatusList.map(phase => {
          let phaseId = phase.id
          let phaseInfo = {
            type: phase.type,
            phaseCountdown: phase.countdown,
            pedtype: phase.pedtype
          }
          this.overlapPhaseStatusMap.set(phaseId, phaseInfo)
        })
      }
      let curLanePhaseData = []
      for (let i = 0; i < this.overlapLanePhaseData.length; i++) {
        let curPhaseStatus = this.overlapPhaseStatusMap.get(this.overlapLanePhaseData[i].phaseid)
        if (!curPhaseStatus) continue
        const data = {
          ...this.overlapLanePhaseData[i],
          type: curPhaseStatus.type,
          color: this.ColorMap.get(curPhaseStatus.type),
          phaseCountdown: curPhaseStatus.phaseCountdown,
          flag: 'overlapphase' // 车道跟随相位数据标识
        }
        curLanePhaseData.push(data)
      }
      this.overlapLanePhaseData = JSON.parse(JSON.stringify(curLanePhaseData))
      // 处理跟随相位数据中，方向direction重复的情况：相同direction下，按照状态的优先级显示该方向的灯色：绿灯(3) > 绿闪(4) > 黄灯(2) > 红灯(1)
      // 如果有相同direction，处理后会改变原数组长度，导致第二次无法正确比较状态，因此需要中间变量存储
      this.comdireOverlapPhaseData = JSON.parse(JSON.stringify(this.CrossDiagramMgr.compareRepeatDirection(this.overlapLanePhaseData, 'type', 'overlapphase')))
    },
    getCurPhaseCountdown () {
      // 获取当前相位倒计时颜色
      this.phaseCountdownList = []
      this.curPhase.forEach(curP => {
        this.phaseStatusList.forEach(phaseInfo => {
          if (phaseInfo.id === curP) {
            let countdownObj = {}
            countdownObj.id = phaseInfo.id
            countdownObj.phaseCountdown = phaseInfo.countdown
            countdownObj.phaseCountdownColor = this.ColorMap.get(phaseInfo.type)
            this.phaseCountdownList.push(countdownObj)
          }
        })
      })
    },
    getIntersectionInfo () {
      // 获取路口信息
      const agentid = this.agentId || '0'
      getIntersectionInfo(agentid).then(res => {
        if (!res.data.success) {
          this.isLoaded = false
          if (res.data.code === '4003') {
            this.$message.error(this.$t('edge.common.deviceoffline'))
            return
          }
          this.$message.error(res.data.message)
          return
        }
        this.isLoaded = true
        this.crossInfo = res.data.data
        this.tempType = this.crossInfo.type
        // 获取车道相位、行人相位信息（坐标、名称）
        this.mainType = this.tempType.split('-')[0]
        this.mainDirection = this.tempType.split('-')[1]
        if (this.mainType === '100' || this.mainType === '101' || this.mainType === '104') {
          // 城市道路加载车道相位坐标和人行道坐标
          this.getPhasePos()
          this.getOverlapPhasePos()
          this.getPedPhasePos()
          this.getOverlapPedPhasePos()
          // this.getBusPos()
        }
        if (this.mainType === '103') {
          // 获取匝道道路的主路和支路的相位坐标
          this.getRampPhasePos()
        }
      })
    },
    getBusPos () {
      // 公交相位信息
      this.busPhaseData = []
      this.crossInfo.phaseList.forEach((ele, i) => {
        ele.direction.forEach((dir, index) => {
          // 车道相位
          this.busPhaseData.push({
            key: this.CrossDiagramMgr.getUniqueKey('busphase'),
            phaseid: ele.id, // 相位id，用于对应相位状态
            id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
            name: this.PhaseDataModel.getBusPhasePos(dir).name,
            left: this.PhaseDataModel.getBusPhasePos(dir).x,
            top: this.PhaseDataModel.getBusPhasePos(dir).y,
            busleft: this.PhaseDataModel.getBusMapPos(dir).x,
            bustop: this.PhaseDataModel.getBusMapPos(dir).y,
            controltype: this.createRandomType() // mock
          })
        })
      })
      console.log(this.busPhaseData)
    },
    createRandomType () {
      for (var i = 3; i <= 5; i++) {
        return Math.floor(Math.random() * (5 - 3)) + 3
      }
    },
    getPhasePos () {
      // 车道相位信息
      this.LanePhaseData = []
      this.crossInfo.phaseList.forEach((ele, i) => {
        ele.direction.forEach((dir, index) => {
          // 车道相位
          this.LanePhaseData.push({
            key: this.CrossDiagramMgr.getUniqueKey('phase'),
            phaseid: ele.id, // 相位id，用于对应相位状态
            id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
            name: this.PhaseDataModel.getPhase(dir).name,
            left: this.PhaseDataModel.getPhase(dir).x,
            top: this.PhaseDataModel.getPhase(dir).y
          })
        })
      })
    },
    getOverlapPhasePos () {
      // 车道跟随相位信息
      if (!this.crossInfo.overlaplList) return
      this.overlapLanePhaseData = []
      this.crossInfo.overlaplList.forEach((ele, i) => {
        if (ele.direction) {
          ele.direction.forEach((dir, index) => {
            this.overlapLanePhaseData.push({
              key: this.CrossDiagramMgr.getUniqueKey('overlapphase'),
              phaseid: ele.id, // 相位id，用于对应相位状态
              id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
              name: this.PhaseDataModel.getPhase(dir).name,
              left: this.PhaseDataModel.getPhase(dir).x,
              top: this.PhaseDataModel.getPhase(dir).y
            })
          })
        }
      })
    },
    getRampPhasePos () {
      // 匝道车道相位信息
      this.LanePhaseData = []
      this.crossInfo.phaseList.forEach((ele, i) => {
        ele.direction.forEach((dir, index) => {
          if (ele.controltype === 0) {
            this.handlePhasePosData(`${i}-${index}`, ele, dir, this.PhaseDataModel.getMainPhasePos)
          }
          if (ele.controltype === 1) {
            this.handlePhasePosData(`${i}-${index}`, ele, dir, this.PhaseDataModel.getSidePhasePos)
          }
        })
      })
    },
    handlePhasePosData (key, phase, dir) {
      let posInfo = phase.controltype === 0 ? this.PhaseDataModel.getMainPhasePos(dir) : this.PhaseDataModel.getSidePhasePos(dir)
      this.LanePhaseData.push({
        key,
        controlType: phase.controltype,
        phaseid: phase.id, // 相位id，用于对应相位状态
        id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
        name: posInfo.name,
        left: posInfo.x,
        top: posInfo.y
      })
    },
    getPedPhasePos () {
      // 行人相位信息
      this.sidewalkPhaseData = []
      this.crossInfo.phaseList.forEach((ele, i) => {
        if (ele.peddirection) {
          ele.peddirection.forEach((dir, index) => {
          // 行人相位
            if (this.PhaseDataModel.getSidePos(dir)) {
              this.sidewalkPhaseData.push({
                key: this.CrossDiagramMgr.getUniqueKey('pedphase'),
                phaseid: ele.id, // 相位id，用于对应相位状态
                id: dir,
                name: this.PhaseDataModel.getSidePos(dir).name,
                left: this.PhaseDataModel.getSidePos(dir).x,
                top: this.PhaseDataModel.getSidePos(dir).y
              })
            }
          })
        }
      })
      // 行人相位无，也要显示人行横道，与车道相位显示与否逻辑不同
      // this.handleCompleteSidewalkPhase()
    },
    getOverlapPedPhasePos () {
      // 行人跟随相位信息
      if (!this.crossInfo.overlaplList) return
      this.overlapsidewalkPhaseData = []
      this.crossInfo.overlaplList.forEach((ele, i) => {
        if (ele.peddirection) {
          ele.peddirection.forEach((dir, index) => {
            if (this.PhaseDataModel.getSidePos(dir)) {
              this.overlapsidewalkPhaseData.push({
                key: this.CrossDiagramMgr.getUniqueKey('overlappedphase'),
                phaseid: ele.id, // 相位id，用于对应相位状态
                id: dir,
                name: this.PhaseDataModel.getSidePos(dir).name,
                left: this.PhaseDataModel.getSidePos(dir).x,
                top: this.PhaseDataModel.getSidePos(dir).y
              })
            }
          })
        }
      })
    },
    // handleCompleteSidewalkPhase () {
    //   // 没有相位状态的车道也要显示，默认白色
    //   let hasDirIds = this.sidewalkPhaseData.map(data => data.id)
    //   let allIds = []
    //   for (let key of this.PhaseDataModel.SidePosMap.keys()) {
    //     allIds.push(key)
    //   }
    //   let diffIds = allIds.filter(function (val) { return hasDirIds.indexOf(val) === -1 })
    //   diffIds.forEach(id => {
    //     this.sidewalkPhaseData.push({
    //       key: `${this.sidewalkPhaseData.length + 1}`,
    //       phaseid: undefined, // 相位id，用于对应相位状态
    //       id,
    //       name: this.PhaseDataModel.getSidePos(id).name,
    //       left: this.PhaseDataModel.getSidePos(id).x,
    //       top: this.PhaseDataModel.getSidePos(id).y
    //     })
    //   })
    // },
    getCrossType () {
      // 路口类型对应底图决策
      if (this.mainType === '101') {
        // 十字路口
        this.crossType = 'Crossroads'
      }
      if (this.mainType === '100') {
        // T型路口
        switch (this.mainDirection) {
          case '001': this.crossType = 'TypeT-east'
            break
          case '002': this.crossType = 'TypeT-south'
            break
          case '003': this.crossType = 'TypeT-west'
            break
          case '004': this.crossType = 'TypeT-north'
            break
        }
      }
      if (this.mainType === '103') {
        // 匝道
        switch (this.mainDirection) {
          case '001': this.crossType = 'ramp-east'
            break
          case '002': this.crossType = 'ramp-south'
            break
          case '003': this.crossType = 'ramp-west'
            break
          case '004': this.crossType = 'ramp-north'
            break
        }
      }
      if (this.mainType === '104') {
        // 路段行人过街
        switch (this.mainDirection) {
          case '006': this.crossType = 'ped-section-east-west'
            break
          case '005': this.crossType = 'ped-section-south-north'
            break
        }
      }
      if (this.mainType === '999') {
        // 其他路口
        this.crossType = 'Customroads'
      }
    },
    refresh () {
      this.getIntersectionInfo()
    },
    getpedStatus () {
      // 行人相位状态
      let curPedStatus = []
      for (let i = 0; i < this.sidewalkPhaseData.length; i++) {
        if (this.sidewalkPhaseData[i].phaseid) {
          let curPhaseStatus = this.phaseStatusMap.get(this.sidewalkPhaseData[i].phaseid)
          if (!curPhaseStatus) continue
          const data = {
            ...this.sidewalkPhaseData[i],
            pedtype: curPhaseStatus.pedtype,
            color: this.SidewalkColorMap.get(curPhaseStatus.pedtype),
            flag: 'ped' // 行人相位数据标识
          }
          curPedStatus.push(data)
        } else {
          // 无状态的行人道
          const data = {
            ...this.sidewalkPhaseData[i],
            pedtype: undefined
          }
          curPedStatus.push(data)
        }
      }
      this.sidewalkPhaseData = JSON.parse(JSON.stringify(curPedStatus))
    },
    getOverlapPedStatus () {
      // 行人跟随相位状态
      let curPedStatus = []
      for (let i = 0; i < this.overlapsidewalkPhaseData.length; i++) {
        if (this.overlapsidewalkPhaseData[i].phaseid) {
          let curPhaseStatus = this.overlapPhaseStatusMap.get(this.overlapsidewalkPhaseData[i].phaseid)
          if (!curPhaseStatus) {
            // 无状态的行人道,也显示出来
            const data = {
              ...this.overlapsidewalkPhaseData[i],
              pedtype: undefined
            }
            curPedStatus.push(data)
            continue
          }
          const data = {
            ...this.overlapsidewalkPhaseData[i],
            pedtype: curPhaseStatus.pedtype,
            color: this.SidewalkColorMap.get(curPhaseStatus.pedtype),
            flag: 'overlapped' // 行人跟随相位数据标识
          }
          curPedStatus.push(data)
        } else {
          // 无状态的行人道
          const data = {
            ...this.overlapsidewalkPhaseData[i],
            pedtype: undefined
          }
          curPedStatus.push(data)
        }
      }
      this.overlapsidewalkPhaseData = JSON.parse(JSON.stringify(curPedStatus))
    },
    getRoadDirection () {
      // 获取行车方向（从平台或配置工具的配置文件中读取）
      this.roadDir = this.roadDirection
    }
  },
  mounted () {
    this.CrossDiagramMgr = new CrossDiagramMgr()
    this.getRoadDirection()
    this.PhaseDataModel = new PhaseDataModel()
    this.getIntersectionInfo() // 获取路口信息
  }
}
</script>
<style scoped>
.invisible {
  visibility: hidden;
}
.crossImg{
    position: relative;
    width: 870px;
    height: 650px;
    left: 50%;
    transform: translateX(-50%);
}
.centerText {
  position: absolute;
  width: 140px;
  height: 140px;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  /* text-align: center; */
  z-index: 9;
  display: flex;
  align-items: center;
  padding-left: 16px;
}
.phaseCountdown {
  line-height: 40px;
  font-size: 30px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #fff;
  width: 150px;
  margin: 0 auto;
}
.countdownBg {
  border-radius: 50%;
  background-color: rgba(94, 90, 90, 0.8);
}
.centerText .text {
  display: inline-block;
  color: #409eff;
  margin-top: 20px;
}

.baseImg {
    width: 100%;
    height: 650px;
    position: relative;
}
</style>
