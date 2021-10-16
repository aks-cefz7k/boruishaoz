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
        <el-button type="primary" @click="getStatisticsData" size="small" style="margin-left: 10px;">获取设备流量数据</el-button>
        <el-button type="primary" @click="testSftp" size="small">导入本地流量文件</el-button>
        <a href="ftp://admin:kedacomIPC@192.168.15.11:21/mnt/TrafficFlowLog/2020-09-22%2000-00.json" download="">FTP下载 </a>
        <div class="statistics-button">
            <el-radio-group v-model="radio">
                <el-radio-button label="检测器1"></el-radio-button>
                <el-radio-button label="检测器2"></el-radio-button>
                <el-radio-button label="检测器3"></el-radio-button>
                <el-radio-button label="检测器4"></el-radio-button>
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
            stripe
            size="small"
            style="width: 100%"
            v-loading.body="listLoading"
            id="footerBtn">
            <el-table-column
            type="index"
            label="#"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_wFaultType"
            label="主类型"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_wSubFaultType"
            label="子类型"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_unFaultOccurTime"
            label="发生时间"
            sortable
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_unFaultRenewTime"
            label="恢复时间"
            sortable
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_byFaultLevel"
            label="等级"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_byFaultDescValue"
            label="值"
            align="center">
            </el-table-column>
            </el-table>
        </div>
    </div>
</div>
</template>

<script>
import { setVolumelog } from '@/api/statistics'
import echart from 'echarts'
// import Ftp from 'vinyl-ftp'
export default {
  name: 'statistics',
  components: {},
  data () {
    return {
      listLoading: false,
      radio: '检测器1',
      tableData: [{
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }]
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
    getStatisticsData () {
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
        this.$message.error(error)
        console.log(error)
      })
    },
    initEcharts () {
      this.historyFlowEcharts = echart.init(document.getElementById('historyFlowEcharts'))
      let _this = this
      window.onresize = function () {
        _this.historyFlowEcharts.resize()
      }
    },
    getEchartsData () {
      let option = {
        legend: {
          data: ['流量', '占有率'],
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
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '流量',
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line',
          smooth: true
        }, {
          name: '占有率',
          data: [800, 902, 981, 904, 1090, 1300, 1300],
          type: 'line',
          smooth: true
        }]
      }
      this.historyFlowEcharts.setOption(option)
    },
    testSftp () {
    }
  }
}
</script>

<style lang="scss" scoped>
.statistics {
    height: 100%;
}
.one {
    height: 6%;
}
.two {
    height: 47%;
}
.flow-echarts {
    height: 100%;
}
.three {
    height: 47%;
    text-align: center;
}
.statistics-table {
    width: 96%;
    margin-left: 2%;
    border: solid 1px #e6e6e6;
}
.statistics-button {
    float: right;
    margin-right: 2%;
}
</style>
