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
  <div class="gis-planchart" style="width: 100%; height: 600px;">
    <div class="title">协调路线名称</div>
    <div class="planchart-name">实时时距图</div>
    <div id="echarts" style="width: 100%; height: 90%;"></div>
</div>
</template>

<script>
import echarts from 'echarts'
import CDTModel from '../../../compose/components/planChart/coordinationModel.js'
// import { RouteOper } from '../../../service/RouteBaseApi.js'
export default {
  name: 'echartsStyle',
  components: {},
  props: {
    greenWaveData: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      direction: '',
      routerData: {},
      isShowUpCard: false,
      isShowDownCard: false,
      green: [],
      routeId: -1
    }
  },
  mounted () {
    var dom = document.getElementById('echarts')
    this.myChart = echarts.init(dom)
    this.CDTModel = new CDTModel()
    const _this = this
    window.onresize = function () {
      _this.myChart.resize()
    }
    // _this.routeId = _this.greenWaveData.id
    // _this.getAllPatternOfRoute()
    _this.initPlanChart()
  },
  created () {
  },
  methods: {
    getAllPatternOfRoute () {
    //   if (this.routeId === -1) return
    //   RouteOper.getAllPatternOfRoute(this.routeId).then(res => {
    //     if (!res.data.success) {
    //       if (res.data.code === '4003') {
    //         let agentid = res.data.data.agentid
    //         this.$message.error('设备' + agentid + '不在线!')
    //         return
    //       }
    //       this.$message.error(res.data.message)
    //       return
    //     }
    //     this.patternList = res.data.data.devs
    //     this.initPlanChart()
    //   })
    },
    initPlanChart () {
      let greenwave = []
      //   let routeData = this.greenWaveData
      //   let patternList = this.patternList
      let routeData = {
        devs: [{
          agentid: '10040',
          backphasedirection: [1, 2, 3],
          backphaseid: 1,
          distance: 300,
          forwardphasedirection: [9, 11, 13, 14],
          forwardphaseid: 1,
          id: 171,
          patterndes: '',
          patternid: 1,
          routeid: 250,
          sortid: 1,
          width: 20
        }, {
          agentid: '10041',
          backphasedirection: [1, 2, 3],
          backphaseid: 1,
          distance: 300,
          forwardphasedirection: [9, 11, 13, 14],
          forwardphaseid: 1,
          id: 172,
          patterndes: '',
          patternid: 1,
          routeid: 250,
          sortid: 2,
          width: 20
        }],
        direction: 'up',
        downspeed: 30,
        enable: true,
        id: 250,
        keyintsid: '10040',
        name: '28',
        routegroupid: 1,
        type: 0,
        upspeed: 30
      }
      let patternList = [{
        agentid: '10040',
        feature: {
          patternList: [{
            cycle: 90,
            desc: '',
            id: 1,
            offset: 0,
            rings: [[{
              desc: [{id: 13}, {id: 14}],
              id: 1,
              mode: 2,
              name: 'phase1',
              value: 30
            }, {
              desc: [{id: 13}, {id: 14}],
              id: 1,
              mode: 2,
              name: 'phase1',
              value: 30
            }, {
              desc: [{id: 9}, {id: 11}],
              id: 3,
              mode: 2,
              name: 'phase3',
              value: 30
            }], [], [], []]
          }]
        }
      }, {
        agentid: '10041',
        feature: {
          patternList: [{
            cycle: 120,
            desc: '',
            id: 1,
            offset: 0,
            rings: [[{
              desc: [{id: 1}, {id: 2}, {id: 3}],
              id: 1,
              mode: 2,
              name: 'phase1',
              value: 30
            }, {
              desc: [{id: 13}, {id: 14}, {id: 15}],
              id: 1,
              mode: 2,
              name: 'phase1',
              value: 30
            }, {
              desc: [{id: 5}, {id: 6}, {id: 7}],
              id: 3,
              mode: 2,
              name: 'phase3',
              value: 30
            }, {
              desc: [{id: 9}, {id: 10}, {id: 11}],
              id: 4,
              mode: 2,
              name: 'phase4',
              value: 30
            }], [], [], []]
          }]
        }
      }]
      if (greenwave.length === 0) {
        this.isShowUpCard = false
        this.isShowDownCard = false
      } else {
        this.green = greenwave
        if (this.direction === 'up') {
          this.isShowUpCard = true
        } else if (this.direction === 'down') {
          this.isShowDownCard = true
        } else if (this.direction === 'all') {
          this.isShowUpCard = true
          this.isShowDownCard = true
        }
        this.CDTModel.greenwave = greenwave
      }
      if (patternList.length === 0) {
        // this.$message.error('方案为空！')
        return
      }
      // this.direction = routeData.direction;
      // this.keyintsid = routeData.keyintsid;
      // this.upspeed = routeData.upspeed;
      // this.downspeed = routeData.downspeed;
      let devs = routeData.devs
      for (let inter of devs) {
        // let obj = {};
        // obj.label = inter.agentid;
        // obj.value = inter.agentid;
        // this.keyintsidOptions.push(obj);
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
    }
  }
}
</script>

<style lang="scss" scoped>
.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 5px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  // .box-card {
  //   background-color: #fbfbfb;
  //   border-radius: 4px;
  //   border: solid 1px #EBEEF5;
  // }
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
