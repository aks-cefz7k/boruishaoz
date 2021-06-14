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
  <div class="crossImg">
    <!-- 路口底图 -->
    <div class="baseImg">
      <CrossRoadsSvg v-if="crossType === 'Crossroads'"/>
      <TShapeEastRoadsSvg v-if="crossType === 'TypeT-east'"/>
      <TShapeWestRoadsSvg v-if="crossType === 'TypeT-west'"/>
      <TShapeNorthRoadsSvg v-if="crossType === 'TypeT-north'"/>
      <TShapeSouthRoadsSvg v-if="crossType === 'TypeT-south'"/>
      <!-- 其他路口 -->
      <CustomRoadsSvg v-if="crossType === 'Customroads'"/>
    </div>
    <!-- 车道相位 -->
    <div v-if="crossType !== 'Customroads'">
      <PhaseIconSvg v-for="item in LanePhaseData" :key="item.key" :Data="item"/>
    </div>
  </div>
</div>
</template>
<script>
import PhaseIconSvg from './phaseIconSvg'
import PhaseDataModel from './utils.js'
import CrossRoadsSvg from './baseImg/CrossRoadsSvg'
import TShapeEastRoadsSvg from './baseImg/TShapeEastRoadsSvg'
import TShapeWestRoadsSvg from './baseImg/TShapeWestRoadsSvg.vue'
import TShapeNorthRoadsSvg from './baseImg/TShapeNorthRoadsSvg.vue'
import TShapeSouthRoadsSvg from './baseImg/TShapeSouthRoadsSvg.vue'
import CustomRoadsSvg from './baseImg/CustomRoadsSvg.vue'
export default {
  name: 'crossDiagram',
  components: {
    PhaseIconSvg,
    CrossRoadsSvg,
    TShapeEastRoadsSvg,
    TShapeWestRoadsSvg,
    TShapeNorthRoadsSvg,
    TShapeSouthRoadsSvg,
    CustomRoadsSvg
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
        let crossType = val.split('-')[0]
        let crossintersection = val.split('-')[1]
        this.getCrossType(crossType, crossintersection)
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
  data () {
    return {
      LanePhaseData: [], // 车道相位数据
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
