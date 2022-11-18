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
  <div class="homeLayout">
    <div class="top">
      <el-row :gutter="20">
        <el-col :span="16">
          <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.devicestatus')">
            <div slot="cardContent" class="chart">
              <DevsStateChart :chartData="chartData"/>
            </div>
          </OpenATCCard>
        </el-col>
        <el-col :span="8">
          <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.faultlist')">
            <div slot="cardContent" class="list">
              <FaultList :faultList="faultList"/>
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
    </div>
    <div class="bottom">
      <el-row :gutter="20">
        <el-col :span="8" v-for="(card, index) in toPagePart" :key="index">
          <OpenATCCard width="100%" height="100%" :ishasTitle="false">
            <div slot="cardContent" class="animationPart">
              <div class="imagebox" id="imagebox">
                <LottieAnim v-if="Visible" :Width="Width" :Height="Height" :Options="card.options" />
                <!-- <div :class="card.className" :style="{height: imgHeight, backgroundSize: imgWidth + ' ' + imgHeight}"></div> -->
              </div>
              <div class="toPage">
                <div :style="{ marginTop: toPageMarginTop, marginBottom: toPageMarginBottom }">{{$t(`openatc.home.${[card.name]}`)}}</div>
                <i class="iconfont icon-qianwang" @click="handleClickToPage(card.name)"></i>
              </div>
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import OpenATCCard from '@/components/OpenATCCard/index'
import DevsStateChart from './devsStateChart'
import FaultList from './faultList'
import router from '@/router'
import { GetAllDevice } from '@/api/device'
import { GetAllCurrentFault } from '@/api/fault'
import LottieAnim from './lottieDemo/index'
import deviceAnim from '../../../static/lottiejson/deviceManager.json'
import userAnim from '../../../static/lottiejson/userManager.json'
import operatAnim from '../../../static/lottiejson/operationRecord.json'

export default {
  data () {
    return {
      Width: 340,
      Height: 236,
      Visible: false,
      getDevsDataTimer: Object,
      toPageMarginTop: '17px',
      toPageMarginBottom: '20px',
      toPagePart: [{
        className: 'device-manage',
        name: 'devicemaneger',
        options: {
          animationData: deviceAnim, loop: false, autoplay: false
        }
      }, {
        className: 'user-manage',
        name: 'usermanager',
        options: {
          animationData: userAnim, loop: false, autoplay: false
        }
      }, {
        className: 'operation-record',
        name: 'operationrecord',
        options: {
          animationData: operatAnim, loop: false, autoplay: false
        }
      }
      ],
      // faultTypeMap 故障种类，前端写死的假数据
      // faultTypeMap: new Map([[1, '检测器报警'], [2, '灯故障'], [3, '断电故障'], [4, '通讯故障']]),
      faultTypeMap: new Map([[201, '灯控板在线个数异常'], [202, '灯组红绿同亮'], [203, '所有灯组红灯全灭'], [204, '绿冲突'], [1, '检测器报警'], [2, '灯故障'], [3, '断电故障'], [4, '通讯故障']]),
      faultList: {
        maxValue: 0,
        data: new Map()
      },
      chartData: [{
        name: '在线',
        value: 0
      },
      {
        name: '离线',
        value: 0
      }]
    }
  },
  components: {
    OpenATCCard,
    DevsStateChart,
    FaultList,
    LottieAnim
  },
  methods: {
    calculateHeight () {
      // 浏览器可视区域的高
      let viewH = document.documentElement.clientHeight - 10
      this.toPageMarginTop = (17 / 1080 * viewH).toFixed(0) + 'px'
      this.toPageMarginBottom = (17 / 1080 * viewH).toFixed(0) + 'px'
      this.Width = document.getElementById('imagebox').clientWidth
      this.Height = document.getElementById('imagebox').clientHeight
      this.Visible = true
    },
    handleClickToPage (name) {
      switch (name) {
        case 'devicemaneger': router.push({ path: '/device' })
          break
        case 'usermanager': router.push({ path: '/user' })
          break
        case 'operationrecord': router.push({ path: '/operaterecord' })
          break
        default: router.push({ path: '/' })
      }
    },
    resetData () {
      this.faultList.maxValue = 0
      this.chartData = [{
        name: '在线',
        value: 0
      },
      {
        name: '离线',
        value: 0
      }]
    },
    getdata () {
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.resetData()
        res.data.data.forEach(ele => {
          if (ele.state === 'UP') {
            this.chartData[0].value++
          }
          if (ele.state === 'DOWN') {
            this.chartData[1].value++
          }
        })
      })
      GetAllCurrentFault().then(res => {
        let list = []
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return false
        } else {
          list = res.data.data
        }
        this.faultList = list
      })
    }
  },
  mounted () {
    this.calculateHeight()
    this.getdata()
    this.getDevsDataTimer = setInterval(() => {
      this.getdata()
    }, 60000)
    const _this = this
    window.onresize = function () {
      // 动画大小自适应
      _this.Width = document.getElementById('imagebox').clientWidth
      _this.Height = document.getElementById('imagebox').clientHeight
      _this.Visible = false
      _this.$nextTick(() => {
        _this.Visible = true
      })
    }
  },
  destroyed () {
    clearInterval(this.getDevsDataTimer)
  }
}
</script>

<style lang="scss" scoped>
</style>
