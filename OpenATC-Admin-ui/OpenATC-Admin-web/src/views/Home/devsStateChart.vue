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
<div class="devsStatePart">
 <el-row :gutter="50">
  <el-col :span="12">
    <div class="leftPart" id="leftPart" ref="leftPart">
      <div class="chartPart">
        <el-progress type="circle" :percentage="percentage" :height="chartHeight" :width="chartWidth" :stroke-width="chartRadius" color="#53c776"></el-progress>
      </div>
      <div class="lengendPart" :style="{ paddingTop: lengendPartPaddingTop}">
        <span class="upLengend"></span>
          <span>{{$t('openatc.home.online')}}</span>
          <span class="downLengend"></span>
          <span>{{$t('openatc.home.offline')}}</span>
      </div>
    </div>
  </el-col>
  <el-col :span="12">
      <div class="rightPart">
          <div class="devsUp" :style="{ height: rightPartHeight }">
              <div class="Img upImg"></div>
              <div class="desc">
                  <div class="num">
                    <span class="toJump" style="color: red;">{{chartData[2].value}}</span>
                     <!-- @click="handleJumpToCrossMgr('fault') -->
                    <span>/</span>
                    <span class="toJump" @click="handleJumpToCrossMgr('online')">{{chartData[0].value}}</span>
                  </div>
                  <div class="stateGroup">
                    <div class="state toJump">{{$t('openatc.home.fault')}}</div>
                    <div class="state toJump" style="margin: 0 5px;"> / </div>
                    <div class="state toJump" @click="handleJumpToCrossMgr('online')">{{$t('openatc.home.online')}}</div>
                  </div>
              </div>
          </div>
          <div class="devsDown" :style="{ height: rightPartHeight }">
              <div class="Img downImg"></div>
              <div class="desc">
                  <div class="num toJump" @click="handleJumpToCrossMgr('offline')">{{chartData[1].value}}</div>
                  <div class="state offlinestate toJump" @click="handleJumpToCrossMgr('offline')">{{$t('openatc.home.offline')}}</div>
              </div>
          </div>
      </div>
  </el-col>
</el-row>
</div>
</template>

<script>
import { clearCrossFilter } from '@/utils/crossFilterMgr'
export default {
  name: 'devsStateChartPanel',
  props: {
    chartData: {
      type: Array
    }
  },
  watch: {
    chartData: {
      handler: function (val, oldVal) {
        this.initData()
      },
      // 深度观察监听
      deep: true
    }
  },
  data () {
    return {
      percentage: 0,
      sum: 0,
      height: undefined,
      width: undefined,
      chartHeight: 394,
      chartWidth: 394,
      chartRadius: 44,
      lengendPartPaddingTop: '25px',
      rightPartHeight: '154px',
      rightPartPaddingTop: '88px'
    }
  },
  methods: {
    initData () {
      this.getPercentage()
      this.$refs.leftPart.getElementsByClassName('el-progress__text')[0].innerHTML = `<div class="content">
            <div class="sum">${this.sum}</div>
            <div class="text">${this.$t('openatc.home.total')}</div>
        </div>`
    },
    getPercentage () {
      this.sum = this.chartData[0].value + this.chartData[1].value
      if (!this.sum) return
      this.percentage = (this.chartData[0].value / this.sum).toFixed(2) * 100
    },
    calculateChartSize () {
      if (document.getElementById('leftPart').clientHeight - 130 < document.getElementById('leftPart').clientWidth - 30) {
        this.chartHeight = document.getElementById('leftPart').clientHeight - 130
        this.chartWidth = this.chartHeight
      } else {
        this.chartWidth = document.getElementById('leftPart').clientWidth - 30
        this.chartHeight = this.chartWidth
      }
      let viewH = document.documentElement.clientHeight - 40
      this.lengendPartPaddingTop = (25 / 1080 * viewH).toFixed(0) + 'px'
      this.rightPartHeight = (154 / 1080 * viewH).toFixed(0) + 'px'
      this.rightPartPaddingTop = (88 / 1080 * viewH).toFixed(0) + 'px'
    },
    handleJumpToCrossMgr (filter) {
      clearCrossFilter() // 清除路口管理过滤条件
      this.$router.push({
        name: 'deviceMgr',
        params: {filter}
      })
    }
  },
  created () {
    let viewH = document.documentElement.clientHeight - 40
    this.lengendPartPaddingTop = (25 / 1080 * viewH).toFixed(0) + 'px'
    this.rightPartHeight = (154 / 1080 * viewH).toFixed(0) + 'px'
    this.rightPartPaddingTop = (88 / 1080 * viewH).toFixed(0) + 'px'
  },
  beforeUpdate () {
    this.initData()
  },
  mounted () {
    this.initData()
    let viewH = document.documentElement.clientHeight - 40
    let viewW = document.documentElement.clientWidth - 40
    this.calculateChartSize()
    // 利用斜边长计算圆环半径
    let viewSide = Math.sqrt(Math.pow(viewH, 2) + Math.pow(viewW, 2))
    this.chartRadius = Number((44 / 2203 * viewSide).toFixed(0))
    window.addEventListener('resize', () => {
      this.calculateChartSize()
    }, false)
  }
}
</script>

<style lang="scss">
// .leftPart .chartPart .el-progress--circle .el-progress__text .content .sum {
//   font-size: 66px;
//   color: $--color-text-primary;
//   text-align: center;
// }
// .leftPart .chartPart .el-progress--circle .el-progress__text .content .text {
//   font-size: 14px;
//   color: $--color-text-regular;
//   text-align: center;
//   margin-top: 15px;
// }
</style>

<style lang="scss" scoped>
// .devsStatePart {
//   height: 100%;
// }
// .el-row {
//     height: 100%;
// }
// .el-col {
//   height: 100%;
// }
// .leftPart {
//   width: 100%;
//   height: 100%;
//   display: flex;
//   flex-direction: column;
//   // flex-wrap: wrap;
//   justify-content: center;
//   .chartPart {
//     text-align: center;
//   }
//   .lengendPart {
//     width: 100%;
//     height: 50px;
//     text-align: center;
//     color: $--color-text-regular
//   }
//   .upLengend {
//       display: inline-block;
//       width: 10px;
//       height: 10px;
//       background: #53c776;
//       border-radius: 50%;
//       margin-right: 5px;
//   }
//   .downLengend {
//       display: inline-block;
//       width: 10px;
//       height: 10px;
//       background: rgb(229, 233, 242);
//       border-radius: 50%;
//       margin-left: 32px;
//       margin-right: 5px;
//   }
// }
// .rightPart {
//     // padding-top: 88px;
//     height: 100%;
//     display: flex;
//     flex-direction: column;
//     // flex-wrap: wrap;
//     justify-content: center;
//     > div {
//         width: calc(100% - 50px);
//         background-color: #f8f8f8;
//         display: flex;
//         align-items: center;
//     }
//     .state {
//         font-size: 14px;
//         color: $--color-text-regular;
//     }
//     .desc {
//         text-align: center;
//     }
//     .Img {
//         height: 66px;
//         width: 66px;
//         background-size: 66px 66px;
//         background-repeat: no-repeat;
//         background-position:center center;
//         margin: 0 13.5% 0 20%;
//     }
//     .devsUp {
//         > div {
//             float: left;
//         }
//         .upImg {
//             background-image: url("../../assets/home/online.png");
//         }
//         .num {
//             font-size: 54px;
//             color: #53c776;
//         }
//     }
//     .devsDown {
//         margin-top: 46px;
//         > div {
//             float: left;
//         }
//         .downImg {
//             background-image: url("../../assets/home/offline.png");
//         }
//         .num {
//             font-size: 54px;
//             color: #b9b9b9;
//         }
//     }
// }

</style>
