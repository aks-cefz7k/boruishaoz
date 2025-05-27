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
<div style="width: 100%;">
  <div class="right-dir-road" v-if="roadDirection === 'right'">
    <div class="crossImg">
      <!-- 路口底图 -->
      <div class="baseImg">
        <CrossRoadsSvg v-if="crossType === 'Crossroads'" Width="439px" Height="328px" :showPed="true" />
        <TShapeEastRoadsSvg v-if="crossType === 'TypeT-east'" Width="439px" Height="328px" :showPed="true" />
        <TShapeWestRoadsSvg v-if="crossType === 'TypeT-west'" Width="439px" Height="328px" :showPed="true" />
        <TShapeNorthRoadsSvg v-if="crossType === 'TypeT-north'" Width="439px" Height="328px" :showPed="true" />
        <TShapeSouthRoadsSvg v-if="crossType === 'TypeT-south'" Width="439px" Height="328px" :showPed="true" />
         <!-- 匝道 -->
        <RampEastRoadsSvg v-if="crossType === 'ramp-east'" Width="439px" Height="328px" />
        <RampWestRoadsSvg v-if="crossType === 'ramp-west'" Width="439px" Height="328px" />
        <RampNorthRoadsSvg v-if="crossType === 'ramp-north'" Width="439px" Height="328px" />
        <RampSouthRoadsSvg v-if="crossType === 'ramp-south'" Width="439px" Height="328px" />
        <!-- 路段行人过街 -->
        <PedSectionSNSvg v-if="crossType === 'ped-section-south-north'" Width="439px" Height="328px" :showSidewalk="true" :showPhaseIcon="true" />
        <PedSectionEWSvg v-if="crossType === 'ped-section-east-west'" Width="439px" Height="328px" :showSidewalk="true" :showPhaseIcon="true" />
        <!-- 其他路口 -->
        <CustomRoadsSvg v-if="crossType === 'Customroads'"/>
      </div>
      <!-- 车道相位 -->
      <div v-if="mainType === '100' || mainType === '101'">
        <PhaseIconSvg v-for="item in LanePhaseData" :key="item.key" :Data="item" IconLengh="18px" IconWdith="13px"/>
      </div>
    </div>
  </div>

  <div class="left-dir-road" v-if="roadDirection === 'left'">
    <div class="crossImg">
      <!-- 路口底图 -->
      <div class="baseImg">
        <LCrossRoadsSvg v-if="crossType === 'Crossroads'" Width="439px" Height="328px" :showPed="true" />
        <LTShapeEastRoadsSvg v-if="crossType === 'TypeT-east'" Width="439px" Height="328px" :showPed="true" />
        <LTShapeWestRoadsSvg v-if="crossType === 'TypeT-west'" Width="439px" Height="328px" :showPed="true" />
        <LTShapeNorthRoadsSvg v-if="crossType === 'TypeT-north'" Width="439px" Height="328px" :showPed="true" />
        <LTShapeSouthRoadsSvg v-if="crossType === 'TypeT-south'" Width="439px" Height="328px" :showPed="true" />
        <!-- 其他路口 -->
        <CustomRoadsSvg v-if="mainType !== '100' && mainType !== '101'"/>
      </div>
      <!-- 左行车道相位 -->
      <div v-if="mainType === '100' || mainType === '101'">
        <LPhaseIconSvg v-for="item in LanePhaseData" :key="item.key" :Data="item" IconLengh="18px" IconWdith="13px"/>
      </div>
    </div>
  </div>
</div>
</template>
<script>
import PhaseIconSvg from '../../overView/crossDirection/phaseIcon/phaseIconSvg'
import PhaseDataModel from './utils.js'
import CrossRoadsSvg from '../../overView/crossDirection/baseImg/CrossRoadsSvg'
import TShapeEastRoadsSvg from '../../overView/crossDirection/baseImg/TShapeEastRoadsSvg'
import TShapeWestRoadsSvg from '../../overView/crossDirection/baseImg/TShapeWestRoadsSvg.vue'
import TShapeNorthRoadsSvg from '../../overView/crossDirection/baseImg/TShapeNorthRoadsSvg.vue'
import TShapeSouthRoadsSvg from '../../overView/crossDirection/baseImg/TShapeSouthRoadsSvg.vue'
import CustomRoadsSvg from './baseImg/CustomRoadsSvg.vue'
import LCrossRoadsSvg from '../../overView/crossDirection/baseImg/leftroad/LCrossRoadsSvg'
import LTShapeEastRoadsSvg from '../../overView/crossDirection/baseImg/leftroad/LTShapeEastRoadsSvg'
import LTShapeWestRoadsSvg from '../../overView/crossDirection/baseImg/leftroad/LTShapeWestRoadsSvg.vue'
import LTShapeNorthRoadsSvg from '../../overView/crossDirection/baseImg/leftroad/LTShapeNorthRoadsSvg.vue'
import LTShapeSouthRoadsSvg from '../../overView/crossDirection/baseImg/leftroad/LTShapeSouthRoadsSvg.vue'
import LPhaseIconSvg from '../../overView/crossDirection/phaseIcon/LphaseIconSvg'
import RampEastRoadsSvg from '../../overView/crossDirection/baseImg/RampEastSvg'
import RampWestRoadsSvg from '../../overView/crossDirection/baseImg/RampWestSvg'
import RampNorthRoadsSvg from '../../overView/crossDirection/baseImg/RampNorthSvg'
import RampSouthRoadsSvg from '../../overView/crossDirection/baseImg/RampSouthSvg'
import RampPhaseIconSvg from '../../overView/crossDirection/phaseIcon/rampPhaseIconSvg'
import PedSectionEWSvg from '../../overView/crossDirection/baseImg/PedSectionEWSvg'
import PedSectionSNSvg from '../../overView/crossDirection/baseImg/PedSectionSNSvg'
import { mapState } from 'vuex'

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
    LCrossRoadsSvg,
    LTShapeEastRoadsSvg,
    LTShapeWestRoadsSvg,
    LTShapeNorthRoadsSvg,
    LTShapeSouthRoadsSvg,
    LPhaseIconSvg,
    RampEastRoadsSvg,
    RampWestRoadsSvg,
    RampNorthRoadsSvg,
    RampSouthRoadsSvg,
    RampPhaseIconSvg,
    PedSectionEWSvg,
    PedSectionSNSvg
  },
  props: {
    phaseList: { // 相位列表
      type: Array
    },
    intersection: { // type
      type: String
    }
  },
  watch: {
    intersection: {
      handler: function (val) {
        this.mainType = val.split('-')[0]
        let crossintersection = val.split('-')[1]
        this.getCrossType(this.mainType, crossintersection)
      }
    },
    phaseList: {
      handler: function (val) {
        if (val) {
          this.phase = JSON.parse(JSON.stringify(val))
          this.getPhasePos()
        }
      }
    }
  },
  computed: {
    ...mapState({
      roadDirection: state => state.globalParam.roadDirection
    })
  },

  data () {
    return {
      LanePhaseData: [], // 车道相位数据
      mainType: '101', // 路口形状编号
      crossType: '', // 路口底图类型
      phase: [] // 模版对应的相位列表
    }
  },
  methods: {
    getIntersectionInfo () {
      this.getPhasePos()
    },
    getPhasePos () {
      if (!this.phase.length) return
      if (this.LanePhaseData.length) {
        this.LanePhaseData = []
      }
      this.phase.forEach((ele, i) => {
        ele.direction.forEach((dir, index) => {
          this.LanePhaseData.push({
            key: `${i}-${index}`,
            phaseid: ele.id, // 相位id，用于对应相位状态
            id: dir, // 接口返回的dir字段，对应前端定义的相位方向id，唯一标识
            name: this.PhaseDataModel.getPhase(dir).name,
            left: this.PhaseDataModel.getPhase(dir).x,
            top: this.PhaseDataModel.getPhase(dir).y
          })
        })
      })
    },
    getCrossType (type, intersection) {
      if (type === '101') {
        this.crossType = 'Crossroads'
      }
      if (type === '100') {
        switch (intersection) {
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
      if (type === '103') {
        // 匝道
        switch (intersection) {
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
      if (type === '104') {
        // 路段行人过街
        switch (intersection) {
          case '005': this.crossType = 'ped-section-east-west'
            break
          case '006': this.crossType = 'ped-section-south-north'
            break
        }
      }
      if (type === '999') {
        this.crossType = 'Customroads'
      }
    }
  },
  mounted () {
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
    width: 439px;
    height: 328px;
    margin: 0 auto;
}
.centerText {
  position: absolute;
  width: 150px;
  height: 150px;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  text-align: center;
  z-index: 9;
}
.centerText .text {
  display: inline-block;
  color: #409eff;
  margin-top: 20px;
}
.baseImg {
    width: 100%;
    height: 328px;
    position: relative;
}
</style>
