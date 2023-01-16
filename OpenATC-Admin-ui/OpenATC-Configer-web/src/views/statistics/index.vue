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
<div class="statistics">
    <div class="one">
      <div class="statistics-name">{{$t('edge.statistics.username')}}</div>
      <div class="statistics-input">
        <el-input v-model="userName" :placeholder="$t('edge.statistics.usernameplaceholder')" size="small"></el-input>
      </div>
      <div class="statistics-name">{{$t('edge.statistics.pass')}}</div>
      <div class="statistics-input">
        <el-input v-model="password" :placeholder="$t('edge.statistics.passplaceholder')" size="small" show-password></el-input>
      </div>
      <div class="statistics-bottom">
        <el-button type="primary" @click="getStatisticsData" size="small" style="margin-left: 10px;">{{$t('edge.statistics.getdevicedata')}}</el-button>
      </div>
        <!-- <el-button type="primary" @click="getStatisticsData" size="small" style="margin-left: 10px;">获取设备流量数据</el-button> -->
        <!-- <el-button type="primary" @click="testSftp" size="small">导入本地流量文件</el-button>
        <a href="ftp://admin:kedacomIPC@192.168.15.11:21/mnt/TrafficFlowLog/2020-09-22%2000-00.json" download="">FTP下载 </a> -->
        <div class="statistics-button">
            <el-radio-group v-model="radio" @change="handleDetector" v-for="(item,index) in detectorList" :key="index">
                <el-radio-button :label="index">{{$t('edge.statistics.detector') + item.id}}</el-radio-button>
            </el-radio-group>
        </div>
    </div>
    <div class="two">
        <div class="flow-echarts" id="historyFlowEcharts"></div>
    </div>
    <div class="three">
        <div class="statistics-table">
            <el-table
            :data="tableData"
            height="380"
            size="small"
            style="width: 100%"
            v-loading.body="listLoading"
            id="footerBtn">
            <el-table-column
            type="index"
            label="#"
            width="110px"
            align="center">
            </el-table-column>
            <el-table-column
            prop="time"
            :label="$t('edge.statistics.time')"
            align="center">
            </el-table-column>
            <el-table-column
            prop="smallvehnum"
            :label="$t('edge.statistics.flow')"
            align="center">
            </el-table-column>
            <el-table-column
            prop="occupyrate"
            :label="$t('edge.statistics.occupyrate')"
            align="center">
            </el-table-column>
            <el-table-column
            prop="totalVehtime"
            :label="$t('edge.statistics.occupytime')"
            align="center">
            </el-table-column>
            </el-table>
        </div>
    </div>
</div>
</template>

<script>
import { setVolumelog, getHistoryFlow } from '@/api/statistics'
import echart from 'echarts'
// import Ftp from 'vinyl-ftp'
export default {
  name: 'statistics',
  components: {},
  data () {
    return {
      listLoading: false,
      radio: 0,
      allFlowData: [],
      tableData: [],
      dateData: [],
      flowData: [],
      occupancyData: [],
      detectorList: [],
      loading: {},
      userName: '',
      password: ''
    }
  },
  watch: {
  },
  mounted () {
    this.initEcharts()
    this.getEchartsData()
  },
  created () {
  },
  methods: {
    getStatisticsData1 () {
      let param = {
        'udiskset': 1,
        'gainstatus': 1
      }
      setVolumelog(param).then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.software = res.data.data.software
        this.hardware = res.data.data.hardware
      }).catch(error => {
        this.$message.error(error.message)
        console.log(error)
      })
    },
    handleDetector (val) {
      this.radio = val
      this.handleData(this.allFlowData)
    },
    getStatisticsData () {
      let username = this.userName
      let password = this.password
      if (username === '' || password === '') {
        this.$message.error(this.$t('edge.statistics.userpassnotnull'))
        return
      }
      this.lockScreen()
      getHistoryFlow(username, password).then((data) => {
        let res = data.data
        if (!res.success) {
          this.unlockScreen()
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.unlockScreen()
        if (res.data.length === 0) {
          this.$message.error(this.$t('edge.statistics.historicalisempty'))
          return
        }
        this.detectorList = res.data[0].detector
        this.allFlowData = res.data
        this.handleData(this.allFlowData)
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error.message)
        console.log(error)
      })
    },
    handleData (flows) {
      let index = this.radio
      for (let flow of flows) {
        let tableObj = {}
        tableObj.time = flow.time
        tableObj.smallvehnum = flow.detector[index].smallvehnum
        tableObj.totalVehtime = flow.detector[index].totalVehtime
        tableObj.occupyrate = flow.detector[index].occupyrate
        this.dateData.push(flow.time)
        this.tableData.push(tableObj)
        this.flowData.push(flow.detector[index].totalVehtime)
        this.occupancyData.push(flow.detector[index].occupyrate)
      }
      this.getEchartsData()
    },
    initEcharts () {
      this.historyFlowEcharts = echart.init(document.getElementById('historyFlowEcharts'))
      let _this = this
      window.onresize = function () {
        _this.historyFlowEcharts.resize()
      }
    },
    getEchartsData () {
      let _this = this
      let option = {
        legend: {
          data: [_this.$t('edge.statistics.flow'), _this.$t('edge.statistics.occupyrate')],
          right: '2%'
          // align: 'right'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '2%',
          right: '2%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: _this.dateData
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: _this.$t('edge.statistics.flow'),
          data: _this.flowData,
          type: 'line',
          smooth: true
        }, {
          name: _this.$t('edge.statistics.occupyrate'),
          data: _this.occupancyData,
          type: 'line',
          smooth: true
        }]
      }
      this.historyFlowEcharts.setOption(option)
    },
    lockScreen () {
      this.loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    },
    unlockScreen () {
      this.loading.close()
    }
  }
}
</script>

<style lang="scss" scoped>
// .statistics {
//     height: 100%;
// }
// .one {
//     height: 6%;
// }
// .two {
//     height: 47%;
// }
// .flow-echarts {
//     height: 100%;
// }
// .three {
//     height: 47%;
//     text-align: center;
// }
// .statistics-table {
//     width: 96%;
//     margin-left: 2%;
//     border: solid 1px #e6e6e6;
// }
// .statistics-button {
//     float: right;
//     margin-right: 2%;
// }
// .statistics-input {
//   float: left;
//   // margin-left: 20px;
// }
// .statistics-bottom {
//   float: left;
// }
// .statistics-name {
//   float: left;
//   margin-top: 8px;
//   margin-left: 10px;
//   height: 14px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   color: #999999;
// }
</style>
