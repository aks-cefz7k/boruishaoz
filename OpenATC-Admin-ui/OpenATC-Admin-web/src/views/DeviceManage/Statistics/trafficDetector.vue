<template>
  <div class="trafficDetector" v-loading="loading">
    <div class="top">
      <div class="lefttop">
          <div class="title">交通流量统计</div>
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
          检测器流量热力图
        </div>
        <div class="choosePanel">
        </div>
        <div class="chart2"
              id="heatmap"></div>
      </div>
      <div class="bottom-right">
        <div class="title">
          检测器流量
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
      valueTypeList: ['大型车', '中型车', '小型车'],
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
      Color: new Map([['大型车', '#007dc5'], ['中型车', '#7953b1'], ['小型车', '#cf6543']])
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
      this.historyChart = echart.init(document.getElementById('historyData'))
      // this.historyChart.on('updateAxisPointer', event => {
      //   this.onHistryDataChartUpdate(this.getXAxis(this.data)[event.dataIndex])
      // })
    },
    initHeatMapChart () {
      this.heatmapChart = echart.init(document.getElementById('heatmap'))
      this.heatmapChart.on('mouseover', event => {
        let index = event.value[0]
        let date = this.getXAxis(this.data)[index]
        this.onHeatMapUpdate(date)
      })
    },
    initStrengthChart () {
      this.strengthChart = echart.init(document.getElementById('strengthHeatMap'))
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
          label: '检测器' + item,
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
              lineStyle: {
                color: '#f1f3f8'
              }
            },
            splitArea: {
              show: true,
              areaStyle: {
                color: ['#fafafa', '#fff']
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
    onHistryDataChartUpdate (curDate) {
      if (curDate === undefined) { return }
      let dataList = this.filterCurDetector(this.data)
      this.curHistoryData = dataList.find(item => {
        return curDate === item.date
      })
      let maxValue = this.getMaxValueList()

      this.curHistoryData.value = this.curHistoryData.value.map((item, index) => {
        let max = maxValue[index]
        if (max === undefined) return item
        else return item < max.max ? item : max.max
      })
    },
    refreshHeatMapChart () {
      if (this.data.length === 0) {
        this.heatmapChart.clear()
        return
      }
      let hours = [
        '12a', '1a', '2a', '3a', '4a', '5a', '6a',
        '7a', '8a', '9a', '10a', '11a',
        '12p', '1p', '2p', '3p', '4p', '5p',
        '6p', '7p', '8p', '9p', '10p', '11p'
      ]
      // prettier-ignore
      let days = [
        'Saturday', 'Friday', 'Thursday',
        'Wednesday', 'Tuesday', 'Monday', 'Sunday'
      ]
      // prettier-ignore
      let data = [[0, 0, 5], [0, 1, 1], [0, 2, 0], [0, 3, 0], [0, 4, 0], [0, 5, 0], [0, 6, 0], [0, 7, 0], [0, 8, 0], [0, 9, 0], [0, 10, 0], [0, 11, 2], [0, 12, 4], [0, 13, 1], [0, 14, 1], [0, 15, 3], [0, 16, 4], [0, 17, 6], [0, 18, 4], [0, 19, 4], [0, 20, 3], [0, 21, 3], [0, 22, 2], [0, 23, 5], [1, 0, 7], [1, 1, 0], [1, 2, 0], [1, 3, 0], [1, 4, 0], [1, 5, 0], [1, 6, 0], [1, 7, 0], [1, 8, 0], [1, 9, 0], [1, 10, 5], [1, 11, 2], [1, 12, 2], [1, 13, 6], [1, 14, 9], [1, 15, 11], [1, 16, 6], [1, 17, 7], [1, 18, 8], [1, 19, 12], [1, 20, 5], [1, 21, 5], [1, 22, 7], [1, 23, 2], [2, 0, 1], [2, 1, 1], [2, 2, 0], [2, 3, 0], [2, 4, 0], [2, 5, 0], [2, 6, 0], [2, 7, 0], [2, 8, 0], [2, 9, 0], [2, 10, 3], [2, 11, 2], [2, 12, 1], [2, 13, 9], [2, 14, 8], [2, 15, 10], [2, 16, 6], [2, 17, 5], [2, 18, 5], [2, 19, 5], [2, 20, 7], [2, 21, 4], [2, 22, 2], [2, 23, 4], [3, 0, 7], [3, 1, 3], [3, 2, 0], [3, 3, 0], [3, 4, 0], [3, 5, 0], [3, 6, 0], [3, 7, 0], [3, 8, 1], [3, 9, 0], [3, 10, 5], [3, 11, 4], [3, 12, 7], [3, 13, 14], [3, 14, 13], [3, 15, 12], [3, 16, 9], [3, 17, 5], [3, 18, 5], [3, 19, 10], [3, 20, 6], [3, 21, 4], [3, 22, 4], [3, 23, 1], [4, 0, 1], [4, 1, 3], [4, 2, 0], [4, 3, 0], [4, 4, 0], [4, 5, 1], [4, 6, 0], [4, 7, 0], [4, 8, 0], [4, 9, 2], [4, 10, 4], [4, 11, 4], [4, 12, 2], [4, 13, 4], [4, 14, 4], [4, 15, 14], [4, 16, 12], [4, 17, 1], [4, 18, 8], [4, 19, 5], [4, 20, 3], [4, 21, 7], [4, 22, 3], [4, 23, 0], [5, 0, 2], [5, 1, 1], [5, 2, 0], [5, 3, 3], [5, 4, 0], [5, 5, 0], [5, 6, 0], [5, 7, 0], [5, 8, 2], [5, 9, 0], [5, 10, 4], [5, 11, 1], [5, 12, 5], [5, 13, 10], [5, 14, 5], [5, 15, 7], [5, 16, 11], [5, 17, 6], [5, 18, 0], [5, 19, 5], [5, 20, 3], [5, 21, 4], [5, 22, 2], [5, 23, 0], [6, 0, 1], [6, 1, 0], [6, 2, 0], [6, 3, 0], [6, 4, 0], [6, 5, 0], [6, 6, 0], [6, 7, 0], [6, 8, 0], [6, 9, 0], [6, 10, 1], [6, 11, 0], [6, 12, 2], [6, 13, 1], [6, 14, 3], [6, 15, 4], [6, 16, 0], [6, 17, 0], [6, 18, 0], [6, 19, 0], [6, 20, 1], [6, 21, 2], [6, 22, 2], [6, 23, 6]]
        .map(function (item) {
          return [item[1], item[0], item[2] || '-']
        })
      days = this.historySelectOption.map(item => item.label)
      data = []
      hours = new Set(this.data.map(item => item.date))
      hours = Array.from(hours)
      let i = 0
      let min = 999999999
      let max = 0
      for (let item of this.data) {
        i++
        let yName = '检测器' + item.id
        let xName = item.date
        let y = days.indexOf(yName)
        let x = hours.indexOf(xName)
        let v = item.smallvehnum + i
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
            show: true
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
          data: this.getDetectorList(this.curHeatMapData).map(item => '检测器' + item)
        },
        series: this.getStrengthSeries(this.curHeatMapData)
      }
      this.strengthChart.setOption(option)
    },
    getStrengthSeries (data) {
      let dataArr = data.map(item => {
        return item.smallvehnum + 10
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
        let resData = {
          agentId: '2',
          beginTime: '2021-10-20 15:40:42',
          endTime: '2021-10-21 15:50:42'
        }
        getAllTrafficData(resData).then(res => {
          if (!res.data.success) {
            console.log(res.data.message)
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
          // resData = resInfo.map(item => {
          //   return {
          //     index: item.detectorid,
          //     name: '检测器' + item.detectorid,
          //     date: moment(item['createtime']).format('MM-DD HH:mm:ss'),
          //     value: [item.intensity.toFixed(2), item.flow,
          //       item.speed.toFixed(2), item.occupancy.toFixed(2), item.queue]
          //   }
          // })
          // resolve(resData)
        }).catch(() => {
          this.unlockScreen()
        })
      })
    }
  }
}
</script>
