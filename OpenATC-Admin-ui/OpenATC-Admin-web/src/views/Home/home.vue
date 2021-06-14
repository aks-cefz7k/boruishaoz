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
  <div class="homeLayout" :style="{height: layoutHeight}">
      <el-row :gutter="20">
        <el-col :span="16">
          <OpenATCCard width="100%" :height="topRowHeight" title="设备状态">
            <div slot="cardContent">
              <DevsStateChart :chartData="chartData"/>
            </div>
          </OpenATCCard>
        </el-col>
        <el-col :span="8">
          <OpenATCCard width="100%" :height="topRowHeight" title="故障列表">
            <div slot="cardContent">
              <FaultList :faultList="faultList"/>
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8" v-for="(card, index) in toPagePart" :key="index">
          <OpenATCCard width="100%" :height="bottomRowHeight" :ishasTitle="false">
            <div slot="cardContent">
              <div class="imagebox">
                <div :class="card.className" :style="{height: imgHeight, backgroundSize: imgWidth + ' ' + imgHeight}"></div>
              </div>
              <div class="toPage">
                <div :style="{ marginTop: toPageMarginTop, marginBottom: toPageMarginBottom }">{{card.name}}</div>
                <i class="iconfont icon-qianwang" @click="handleClickToPage(card.name)"></i>
              </div>
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
  </div>
</template>
<script>
import OpenATCCard from '@/components/OpenATCCard/index'
import DevsStateChart from './devsStateChart'
import FaultList from './faultList'
import router from '@/router'
import { GetAllDevice } from '@/api/device'
export default {
  data () {
    return {
      getDevsDataTimer: Object,
      layoutHeight: '1030px',
      topRowHeight: '573px',
      bottomRowHeight: '402px',
      imgHeight: '236px',
      imgWidth: '340px',
      toPageMarginTop: '17px',
      toPageMarginBottom: '20px',
      toPagePart: [{
        className: 'device-manage',
        name: '设备管理'
      }, {
        className: 'user-manage',
        name: '用户管理'
      }, {
        className: 'operation-record',
        name: '操作记录'
      }],
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
    FaultList
  },
  methods: {
    calculateHeight () {
      // 浏览器可视区域的高
      let viewH = document.documentElement.clientHeight - 10
      let viewW = document.documentElement.clientWidth
      this.layoutHeight = (1030 / 1080 * viewH).toFixed(0) + 'px'
      this.topRowHeight = (573 / 1080 * viewH).toFixed(0) + 'px'
      this.bottomRowHeight = (402 / 1080 * viewH).toFixed(0) + 'px'
      this.imgHeight = (236 / 1080 * viewH).toFixed(0) + 'px'
      this.imgWidth = (340 / 1920 * viewW).toFixed(0) + 'px'
      this.toPageMarginTop = (17 / 1080 * viewH).toFixed(0) + 'px'
      this.toPageMarginBottom = (17 / 1080 * viewH).toFixed(0) + 'px'
    },
    handleClickToPage (name) {
      switch (name) {
        case '设备管理': router.push({ path: '/device' })
          break
        case '用户管理': router.push({ path: '/user' })
          break
        case '操作记录': router.push({ path: '/operaterecord' })
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
        const fault = new Map()
        this.resetData()
        res.data.data.forEach(ele => {
          if (ele.state === 'UP') {
            this.chartData[0].value++
          }
          if (ele.state === 'DOWN') {
            this.chartData[1].value++
          }
          if (ele.status !== 0) {
            let key = this.faultTypeMap.get(ele.status)
            this.faultList.maxValue++
            if (fault.has(key)) {
              fault.set(key, fault.get(key) + 1)
            } else {
              fault.set(key, 1)
            }
          }
        })
        this.faultList.data = fault
      })
    }
  },
  mounted () {
    this.calculateHeight()
    this.getdata()
    this.getDevsDataTimer = setInterval(() => {
      this.getdata()
    }, 60000)
  },
  destroyed () {
    clearInterval(this.getDevsDataTimer)
  }
}
</script>

<style lang="scss" scoped>
.homeLayout {
  padding: 19px;
  background-color: #eff1f4;
  // height: calc(100vh - 50px);
}
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
}
.imagebox {
  margin: 0 auto;
  padding-top: 50px;
}
.toPage {
  width: 72px;
  text-align: center;
  margin: 0 auto;
  color: #666;
  font-family: MicrosoftYaHei;
  .iconfont {
    cursor: pointer;
  }
}
.icon-qianwang {
  color: #409eff;
  font-size: 20px;
}
.device-manage {
  // height: 236px;
  background: url("../../assets/home/deviceManage.png");
  // background-size: 340px 236px;
  background-repeat: no-repeat;
  background-position:center center;
}
.user-manage {
  background: url("../../assets/home/usersManage.png");
  background-repeat: no-repeat;
  background-position:center center;
}
.operation-record {
  background: url("../../assets/home/operateRecord.png");
  background-repeat: no-repeat;
  background-position:center center;
}
</style>
