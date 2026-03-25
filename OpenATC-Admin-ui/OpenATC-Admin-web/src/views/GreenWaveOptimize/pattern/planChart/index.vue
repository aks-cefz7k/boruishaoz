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
<div style="overflow: auto;" class="openatc-planchart">
  <div class="planchart-top">
    <div class="coordination-name">{{$t('openatc.greenwaveoptimize.coordinationstrategy')}}</div>
    <div class="coordination-select">
      <el-select v-model="optstrategy" :placeholder="$t('openatc.common.placeholder')" size="small">
        <el-option
          v-for="item in optstrategyOptions"
          :key="item.value"
          :label="$t('openatc.greenwaveoptimize.optstrategyoption' + item.id)"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div class="coordination-name" style="margin-left: 40px;">{{$t('openatc.greenwaveoptimize.coordinationdirection')}}</div>
    <div class="coordination-select">
      <el-select v-model="direction" :placeholder="$t('openatc.common.placeholder')" size="small" @change="handleDirection">
        <el-option
          v-for="item in directionOptions"
          :key="item.value"
          :label="$t('openatc.greenwaveoptimize.directionOption' + item.id)"
          :disabled="optstrategy === 'red' && item.value === 'all'"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div class="coordination-name" style="margin-left: 40px;">{{$t('openatc.greenwaveoptimize.keyintersection')}}</div>
    <div class="coordination-select">
      <el-select v-model="keyintsid" :placeholder="$t('openatc.common.placeholder')" size="small">
        <el-option
          v-for="item in keyintsidOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div class="coordination-name" style="margin-left: 40px;">{{$t('openatc.greenwaveoptimize.upspeed')}}</div>
    <div class="coordination-select">
      <el-input v-model="upspeed" size="small" @change="handleUpSpeed"></el-input>
    </div>
    <div class="coordination-name" style="margin-left: 40px;">{{$t('openatc.greenwaveoptimize.downspeed')}}</div>
    <div class="coordination-select">
      <el-input v-model="downspeed" size="small" @change="handleDownSpeed"></el-input>
    </div>
  </div>
  <div class="planchart-bottom">
    <div class="up-card" v-if="isShowUpCard" style="float: left;">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span class="header-text">{{$t('openatc.greenwaveoptimize.up')}}</span>
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.duration') + green[0].width + 's' }}
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.speed') + green[0].speed + 'km/h' }}
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.vehicle') + green[0].vehicle }}
        </div>
      </el-card>
    </div>
    <div class="up-card" v-if="isShowDownCard" style="float: right;">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span class="header-text">{{$t('openatc.greenwaveoptimize.down')}}</span>
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.duration') + green[0].width + 's' }}
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.speed') + green[0].speed + 'km/h' }}
        </div>
        <div class="text item">
          {{$t('openatc.greenwaveoptimize.vehicle') + green[0].vehicle }}
        </div>
      </el-card>
    </div>
    <div id="echarts" class="echartsStyle"></div>
  </div>
</div>
</template>

<script>
import CDTModel from '../../../compose/components/planChart/coordinationModel.js'
export default {
  name: 'echartsStyle',
  components: {},
  data () {
    return {
      optstrategy: 'green',
      direction: '',
      keyintsid: '',
      upspeed: 0,
      downspeed: 0,
      keyintsidOptions: [],
      directionOptions: [{
        value: 'up',
        label: '上行',
        id: '1'
      }, {
        value: 'down',
        label: '下行',
        id: '2'
      }, {
        value: 'all',
        label: '双向',
        id: '3'
      }],
      optstrategyOptions: [{
        value: 'green',
        label: '绿波优化',
        id: '1'
      }, {
        value: 'red',
        label: '红波优化',
        id: '2'
      }],
      routerData: {},
      isShowUpCard: false,
      isShowDownCard: false,
      green: [],
      cardStyle: {
        'float': 'left'
      },
      directionName: ''
    }
  },
  mounted () {
    var dom = document.getElementById('echarts')
    this.myChart = this.$echarts.init(dom)
    this.CDTModel = new CDTModel()
    const _this = this
    window.onresize = function () {
      _this.myChart.resize()
    }
    // window.addEventListener('resize', function () {
    //   this.myChart.resize()
    // })
  },
  created () {
  },
  methods: {
    onPlanChart (routeData, patternList, greenwave) {
      this.keyintsidOptions = []
      if (greenwave.length === 0) {
        this.isShowUpCard = false
        this.isShowDownCard = false
      } else {
        this.green = greenwave
        if (this.direction === 'up') {
          this.isShowUpCard = true
          this.directionName = '上行'
          // this.cardStyle.float = 'left'
        } else if (this.direction === 'down') {
          this.isShowDownCard = true
          this.directionName = '下行'
          // this.cardStyle.float = 'right'
        } else if (this.direction === 'all') {
          this.isShowUpCard = true
          this.isShowDownCard = true
          this.directionName = '双向'
          // this.cardStyle.float = 'right'
        }
        this.CDTModel.greenwave = greenwave
      }
      if (patternList.length === 0) {
        // this.$message.error('方案为空！')
        return
      }
      this.optstrategy = routeData.optstrategy
      this.direction = routeData.direction
      this.keyintsid = routeData.keyintsid
      this.upspeed = routeData.upspeed
      this.downspeed = routeData.downspeed
      let devs = routeData.devs
      for (let inter of devs) {
        let obj = {}
        obj.label = inter.agentid
        obj.value = inter.agentid
        this.keyintsidOptions.push(obj)
        inter.patternList = []
        let id = inter.agentid
        let patternId = inter.patternid
        let pattern = patternList.filter(pat => pat.agentid === id)[0]
        if (pattern.feature !== undefined && pattern.feature.patternList.length !== 0) {
          let allPatternList = pattern.feature.patternList
          if (Array.isArray(allPatternList)) {
            let currPatternList = allPatternList.filter(apl => apl.id === patternId)
            inter.patternList = currPatternList
          } else {
            let tempList = []
            tempList.push(allPatternList)
            inter.patternList = tempList
          }
        }
      }
      this.CDTModel.rourte = routeData
      this.routerData = routeData
      this.clearMyChart()
      this.myChart.setOption(this.CDTModel.RenderOption())
    },
    clearMyChart () {
      if (this.myChart.getOption() !== undefined) {
        this.myChart.clear()
      }
    },
    handleUpSpeed (val) {
      this.routerData.upspeed = Number(val)
      this.CDTModel.rourte = this.routerData
      this.myChart.clear()
      this.myChart.setOption(this.CDTModel.RenderOption())
    },
    handleDownSpeed (val) {
      this.routerData.downspeed = Number(val)
      this.CDTModel.rourte = this.routerData
      this.myChart.clear()
      this.myChart.setOption(this.CDTModel.RenderOption())
    },
    handleDirection (val) {
      this.routerData.direction = val
      this.CDTModel.rourte = this.routerData
      this.CDTModel.greenwave = []
      this.isShowUpCard = false
      this.isShowDownCard = false
      this.myChart.clear()
      this.myChart.setOption(this.CDTModel.RenderOption())
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
<style rel="stylesheet/scss" lang="scss">
  .up-card .el-card__header {
    padding: 10px 20px;
    border-bottom: 1px solid #EBEEF5;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  .up-card .el-card__body {
    padding: 10px;
  }
</style>
