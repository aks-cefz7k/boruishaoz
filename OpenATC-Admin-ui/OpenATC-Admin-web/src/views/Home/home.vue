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
    <div class="panel">
      <el-row>
        <el-col :span="24">
          <OpenATCCard width="100%" height="100%" :ishasTitle="false">
            <div slot="cardContent" class="crossStatusNum">
              <CrossStatusStatistics :chartData="chartData" />
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
    </div>
    <div class="top">
      <el-row>
        <el-col :span="14">
          <div class="cardlayout">
            <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.intersectionmode')">
            <div slot="cardContent" class="chart">
              <ModeCharts :curDevsData="curDevsData" />
            </div>
          </OpenATCCard>
          </div>
        </el-col>
        <el-col :span="10">
          <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.systemstate')">
            <div slot="cardContent" class="system-list">
              <SystemStatus />
            </div>
          </OpenATCCard>
        </el-col>
      </el-row>
    </div>
    <div class="bottom">
      <el-row>
       <el-col :span="14">
          <div class="cardlayout">
            <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.intersectioncontrol')">
              <div slot="cardContent" class="chart">
                <ControlCharts :curDevsData="curDevsData" />
              </div>
            </OpenATCCard>
          </div>
        </el-col>
        <el-col :span="10">
          <OpenATCCard width="100%" height="100%" :title="$t('openatc.home.intersectionabnormalstate')">
            <div slot="cardContent" class="list">
              <CrossAbnormal :faultList="faultList" />
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
import { GetFaultRange } from '@/api/fault'
import deviceAnim from '../../../static/lottiejson/deviceManager.json'
import userAnim from '../../../static/lottiejson/userManager.json'
import operatAnim from '../../../static/lottiejson/operationRecord.json'
import { getMessageByCode } from '@/utils/responseMessage'

import CrossStatusStatistics from './crossStatusStatistics'
import ModeCharts from './modeCharts'
import ControlCharts from './controlCharts'
import SystemStatus from './systemStatus'
import CrossAbnormal from './crossAbnormal'

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
      faultList: [],
      chartData: [{
        key: 'online',
        name: '在线',
        value: 0
      },
      {
        key: 'offline',
        name: '离线',
        value: 0
      }, {
        key: 'fault',
        name: '故障',
        value: 0
      }, {
        key: 'manual',
        name: '干预',
        value: 0
      }],
      curDevsData: [] // 当前全部设备信息
    }
  },
  components: {
    OpenATCCard,
    DevsStateChart,
    FaultList,
    CrossStatusStatistics,
    ModeCharts,
    ControlCharts,
    SystemStatus,
    CrossAbnormal
  },
  methods: {
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
      this.chartData = [{
        key: 'online',
        name: '在线',
        value: 0
      },
      {
        key: 'offline',
        name: '离线',
        value: 0
      }, {
        key: 'fault',
        name: '故障',
        value: 0
      }, {
        key: 'manual',
        name: '干预',
        value: 0
      }]
    },
    getdata () {
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
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
          if (ele.state === 'FAULT') {
            this.chartData[0].value++
            this.chartData[2].value++
          }
          if (ele.mode && ele.mode !== 0) {
            // 干预
            this.chartData[3].value++
          }
        })
        this.curDevsData = res.data.data
      })
      let reqData = {
        'isCurrentFault': true
      }
      GetFaultRange(reqData).then(res => {
        let list = {}
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        } else {
          list = res.data.data
        }
        this.faultList = list.content || []
      })
    }
  },
  mounted () {
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
</style>
