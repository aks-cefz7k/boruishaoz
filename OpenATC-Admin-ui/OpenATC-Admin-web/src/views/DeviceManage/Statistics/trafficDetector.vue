<template>
  <div class="trafficDetector" v-loading="loading">
    <div class="top">
      <div class="lefttop">
          <div class="title">{{$t('openatc.devicemanager.trafficFlowStatistic')}}</div>
          <div class="select">
            <el-select v-model="curDetectorName"
                       size="mini"
                       @change="refreshHistoryDataChart()">
              <el-option v-for="item in historySelectOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </div>
          <div class="chart1"
               id="historyData"></div>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom-left">
        <div class="title">
          {{$t(`openatc.devicemanager.detectorFlowStatistics`)}}
        </div>
        <div class="choosePanel">
        </div>
        <div class="chart2"
              id="heatmap"></div>
      </div>
      <div class="bottom-right">
        <div class="title">
          {{$t(`openatc.devicemanager.detectorFlow`)}}
        </div>
          <div class="chartright"
               id="strengthHeatMap"></div>
      </div>
    </div>
  </div>
</template>
<script>
import echart from 'echarts'
import { getAllTrafficData } from '@/api/device'
import { getMessageByCode } from '@/utils/responseMessage'
import { getTheme } from '@/utils/auth'
import moment from 'moment'
export default {
  name: 'trafficDetector',
  props: {
    date: {
      type: Array
    },
    curascid: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      ascid: '',
      loading: false,
      valueTypeList: [this.$t(`openatc.devicemanager.largeVehicle`), this.$t(`openatc.devicemanager.middleVehicle`), this.$t(`openatc.devicemanager.smallVehicle`)],
      heatmapColorList: [
        '#0064be',
        '#0874d4',
        '#4c9ee6',
        '#a4d0f7',
        '#cee3f6'
      ],
      heatmapValueType: 0,
      curDetectorName: 1,
      historySelectOption: [],
      curHistoryData: {},
      curHeatMapData: [],
      maxValueList: [],
      Color: new Map([[this.$t(`openatc.devicemanager.largeVehicle`), '#007dc5'], [this.$t(`openatc.devicemanager.middleVehicle`), '#7953b1'], [this.$t(`openatc.devicemanager.smallVehicle`), '#cf6543']])
    }
  },
  mounted () {
    this.initChart()
    this.ascid = this.curascid
    this.refreshChart(this.curascid)
  },
  methods: {
    initChart () {
      this.initHistoryDataChart()
      this.initHeatMapChart()
      this.initStrengthChart()
      const _this = this
      window.onresize = function () {
        _this.historyChart.resize()
        _this.heatmapChart.resize()
        _this.radarChart.resize()
        _this.strengthChart.resize()
      }
    },
    initHistoryDataChart () {
      this.historyChart = echart.init(document.getElementById('historyData'), getTheme())
    },
    initHeatMapChart () {
      this.heatmapChart = echart.init(document.getElementById('heatmap'), getTheme())
      this.heatmapChart.on('mouseover', event => {
        let index = event.value[0]
        let date = this.getXAxis(this.data)[index]
        this.onHeatMapUpdate(date)
      })
    },
    initStrengthChart () {
      this.strengthChart = echart.init(document.getElementById('strengthHeatMap'), getTheme())
    },
    lockScreen () {
      this.loading = true
    },
    unlockScreen () {
      this.loading = false
    },
    makeSelectOption (data) {
      this.historySelectOption = this.detectorIdList.map(item => {
        return {
          label: this.$t(`openatc.devicemanager.detector`) + item,
          value: item
        }
      })
    },
    filterCurDetector (data) {
      let res
      res = data.filter(item => {
        return item.id === this.curDetectorName
      })
      return res
    },
    async refreshChart (ascId) {
      this.ascid = ascId
      // this.lockScreen()
      this.data = await this.GetData()
      this.initDetectorList(this.data)
      this.makeSelectOption(this.data)
      this.refreshHistoryDataChart()
      this.refreshHeatMapChart()
      // this.unlockScreen()
    },
    refreshHistoryDataChart () {
      if (this.data.length === 0) {
        this.historyChart.clear()
        return
      }
      let option = {
        grid: {
          containLabel: true,
          height: '80%',
          width: '95%',
          left: '18',
          bottom: '20'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'right',
          data: this.valueTypeList
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: [
          {
            splitLine: {
              show: false,
              lineStyle: {
                color: '#f1f3f8'
              }
            },
            splitArea: {
              show: true,
              areaStyle: {
                color: getTheme() === 'light' ? ['#fafafa', '#fff'] : ['#202940', '#1a2338']
              }
            }
          },
          {
            position: 'right',
            splitLine: {
              show: false
            }
          }
        ],
        series: []
      }
      option.legend.data = this.valueTypeList
      option.xAxis.data = this.getXAxis(this.data)
      option.series = this.getHistoryDataSeries(this.data)
      this.historyChart.setOption(option)
    },
    getXAxis (data) {
      let res = []
      if (data === undefined) return res
      res = [...new Set(data.map(item => {
        return item.date
      }))]
      return res
    },
    getHistoryDataSeries (data) {
      let series = []
      let dataList = this.filterCurDetector(data)
      for (let valueIndex = 0; valueIndex < this.valueTypeList.length; ++valueIndex) {
        let seriesInfo = {
          name: '',
          type: 'line',
          smooth: true,
          yAxisIndex: valueIndex === 0 || valueIndex === 3 ? 1 : 0,
          lineStyle: {
            width: '1'
          },
          itemStyle: {
            color: ''
          },
          data: []
        }
        seriesInfo.name = this.valueTypeList[valueIndex]
        seriesInfo.itemStyle.color = this.Color.get(this.valueTypeList[valueIndex])
        seriesInfo.data = dataList.map(item => {
          let keys = ['largevehnum', 'middlevehnum', 'smallvehnum']
          let key = keys[valueIndex]
          return item[key]
        })
        series.push(seriesInfo)
      }
      return series
    },
    refreshHeatMapChart () {
      if (this.data.length === 0) {
        this.heatmapChart.clear()
        return
      }
      let hours = []
      let days = []
      let data = []
      days = this.historySelectOption.map(item => item.label)
      data = []
      hours = new Set(this.data.map(item => item.date))
      hours = Array.from(hours)
      let min = 999999999
      let max = 0
      // let i = 0
      for (let item of this.data) {
        // i++
        let yName = this.$t(`openatc.devicemanager.detector`) + item.id
        let xName = item.date
        let y = days.indexOf(yName)
        let x = hours.indexOf(xName)
        // let v = item.smallvehnum + i
        let v = item.smallvehnum
        let rec = [y, x, v]
        data.push(rec)
        min = v < min ? v : min
        max = v > max ? v : max
      }
      data = data.map(function (item) {
        return [item[1], item[0], item[2] || '-']
      })
      let option = {
        tooltip: {
          position: 'top'
        },
        grid: {
          height: '50%',
          top: '10%'
        },
        xAxis: {
          type: 'category',
          data: hours,
          splitArea: {
            show: true,
            areaStyle: {
              color: getTheme() === 'light' ? ['#fafafa', '#fff'] : ['#202940', '#1a2338']
            }
          }
        },
        yAxis: {
          type: 'category',
          data: days,
          splitArea: {
            show: true
          }
        },
        visualMap: {
          min: min,
          max: max,
          calculable: true,
          orient: 'horizontal',
          left: 'center',
          bottom: '15%'
        },
        series: [
          {
            name: 'Small Vehicle',
            type: 'heatmap',
            data: data,
            label: {
              show: true
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      this.heatmapChart.setOption(option)
      this.onHeatMapUpdate(hours[0])
    },
    getValueLeave (typeIndex, value) {
      let mask = this.maxValueList[typeIndex] / 5
      return Math.floor(value / mask)
    },
    initDetectorList (data) {
      if (data && data.length > 0) {
        this.detectorIdList = this.getDetectorList(data)
        this.idMap = new Map()
        for (let index = 0; index < this.detectorIdList.length; ++index) {
          this.idMap.set(this.detectorIdList[index], index)
        }
      }
    },
    getDetectorList (data) {
      let detectorIdList = [...new Set(data.map(item => { return item.id }))]
      // detectorIdList.sort((item1, item2) => {
      //   let num1 = item1.replace(/[^0-9]/ig, '')
      //   let num2 = item2.replace(/[^0-9]/ig, '')
      //   return parseInt(num1) - parseInt(num2)
      // })
      return detectorIdList
    },
    getHeatMapSeries (data) {
      if (data === undefined) return []
      let dataList = data.map(item => {
        let leavel = this.getValueLeave(this.heatmapValueType, item.value[this.heatmapValueType])
        return {
          name: item.name + ' ' + item.date,
          value: [item.date, this.idMap.get(item.name), leavel]
        }
      })
      return [{
        type: 'heatmap',
        data: dataList,
        // label: {
        // normal: {
        //  show: true  //这里设置格子上面是否要显示数据
        // }
        // },
        itemStyle: {
          borderWidth: 0.3,
          borderColor: '#fff'
        }
      }]
    },
    onHeatMapUpdate (date) {
      this.curHeatMapData = this.data.filter(item => {
        return item.date === date
      })
      this.refreshStrengthChart()
    },
    refreshStrengthChart () {
      if (this.curHeatMapData.length === 0) {
        this.strengthChart.clear()
        return
      }
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          containLabel: true,
          height: '80%',
          width: '95%',
          left: '0',
          top: '10%'
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          splitLine: {// 去除网格线
            show: false
          }
        },
        yAxis: {
          type: 'category',
          axisLabel: {
            rotate: 15
          },
          data: this.getDetectorList(this.curHeatMapData).map(item => this.$t(`openatc.devicemanager.detector`) + item)
        },
        series: this.getStrengthSeries(this.curHeatMapData)
      }
      this.strengthChart.setOption(option)
    },
    getStrengthSeries (data) {
      let dataArr = data.map(item => {
        // return item.smallvehnum + 10
        return item.smallvehnum
      })
      let series = [
        {
          label: {
            show: true,
            position: 'right',
            color: '#333333'
          },
          type: 'bar',
          itemStyle: {
            color: '#0874d4'
          },
          barWidth: '6',
          data: dataArr
        }
      ]
      return series
    },
    getMaxValueList () {
      let maxValueList = []
      let maxValue = this.maxValueList
      for (let index = 0; index < maxValue.length; ++index) {
        maxValueList.push({
          name: this.valueTypeList[index],
          max: maxValue[index]
        })
      }
      return maxValueList
    },
    GetData () {
      return new Promise((resolve, reject) => {
        let reqData = {}
        let from = moment(this.date[0]).format('YYYY-MM-DD HH:mm:ss')
        let to = moment(this.date[1]).format('YYYY-MM-DD HH:mm:ss')
        reqData = {
          agentId: this.curascid,
          beginTime: from,
          endTime: to
        }
        getAllTrafficData(reqData).then(res => {
          if (!res.data.success) {
            this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
            reject(res.data.message)
          }
          let resInfo = res.data.data
          let resArr = []
          for (let item of resInfo) {
            let time = moment(item.time).format('MM-DD HH:mm:ss')
            let detectors = item.data.detector
            for (let dete of detectors) {
              let record = {
                time: time,
                date: time,
                id: dete.id,
                largevehnum: dete.largevehnum,
                middlevehnum: dete.middlevehnum,
                occupyrate: dete.occupyrate,
                smallvehnum: dete.smallvehnum,
                totalvehtime: dete.totalvehtime
              }
              resArr.push(record)
            }
          }
          resolve(resArr)
        }).catch(() => {
          this.unlockScreen()
        })
      })
    }
  }
}
</script>
