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
  <div class="trafficStrength" v-loading="loading">
    <div class="left">
      <div class="lefttop">
        <div class="title">{{$t('openatc.patternstatistic.patterninfo')}}</div>
          <div class="choosePanel">
            <span v-for="(item, index) in chooseButtons"
                  :key="index">
              <span v-on:click="()=>updateStrengthChart(item.key)"
                    class="btn"
                    :class="{'btnFocus': item.active === true}">{{item.value}}</span>
            </span>
          </div>
          <div class="chart1"
               id="strength"></div>

      </div>
      <div class="leftbottom">
        <div class="title">{{$t('openatc.patternstatistic.historicalcycle')}}</div>
        <div class="chart2" id="timing"></div>
      </div>
    </div>
    <div class="right">
      <strengthPanel ref="strengthPanel"
                     :curChart="curChart"
                     :strengthdata="curStrengthData"
                     :strengthSelet="strengthSeleted"></strengthPanel>
      <periodicPanel ref="periodicPanel"
                     :rangdata="curTimeData"
                     :periodicSelet="periodicSeleted"></periodicPanel>
    </div>
  </div>
</template>
<script>
import echart from 'echarts'
import strengthPanel from './strengthPanel'
import periodicPanel from './periodicPanel'
import { getHistoryPatternData } from '../../../../api/historydata.js'
import moment from 'moment'
import TrafficDataDecisionModel from './trafficDataDecision.js'
import { getTheme } from '@/utils/auth'
export default {
  name: 'detailed-trafficstrength',
  components: {
    strengthPanel,
    periodicPanel
  },
  props: {
    date: {
      type: Array
    },
    ASCID: undefined
  },
  data () {
    return {
      curTimeData: {},
      curStrengthData: {},
      timeSelIndex: -1,
      strSelIndex: -1,
      loading: false,
      chooseButtons: [
        {
          key: 'model',
          value: this.$t('openatc.patternstatistic.controlmodel'),
          active: true
        },
        {
          key: 'control',
          value: this.$t('openatc.patternstatistic.controltype'),
          active: false
        },
        {
          key: 'patternid',
          value: this.$t('openatc.patternstatistic.controlscheme'),
          active: false
        },
        {
          key: 'cycle',
          value: this.$t('openatc.patternstatistic.cycle'),
          active: false
        },
        {
          key: 'offset',
          value: this.$t('openatc.patternstatistic.phasedifference'),
          active: false
        },
        {
          key: 'syncTime',
          value: this.$t('openatc.patternstatistic.synctime'),
          active: false
        },
        {
          key: 'curTime',
          value: this.$t('openatc.patternstatistic.coordinationtime'),
          active: false
        }
      ],
      curChart: {
        key: 'model',
        value: this.$t('openatc.patternstatistic.controlmodel')
      },
      periodicSeleted: undefined,
      strengthSeleted: undefined,
      Color: new Map([[0, '#43b4cf'], [1, '#c6bf72'], [2, '#5085d1'], [3, '#cc7966'], [4, '#537896'], [5, '#d39351'], [6, '#45ab89'], [7, '#a25768']])
    }
  },
  mounted () {
    this.initChart()
    this.refreshChart()
  },
  methods: {
    changeBtnStyle (key) {
      // 选中按钮高亮
      this.chooseButtons.forEach(button => {
        button.active = false
        if (button.key === key) {
          button.active = true
        }
      })
    },
    updateStrengthChart (key) {
      this.changeBtnStyle(key)
      const curData = this.decisionModel.getData(key)
      this.strengthData = curData
      this.renderStrengthChart()
      // 更新相位分布环图小标题
      this.curChart = this.chooseButtons.filter(btn => btn.key === key)[0]
    },
    lockScreen () {
      this.loading = true
    },
    unlockScreen () {
      this.loading = false
    },
    initChart () {
      this.strengthChart = echart.init(document.getElementById('strength'))
      this.timingChart = echart.init(document.getElementById('timing'))
      this.InitStrengthChartEvent()
      this.InitTimeChartEvent()
      const _this = this
      window.onresize = function () {
        _this.strengthChart.resize()
        _this.timingChart.resize()
      }
      // if (this.ASCID) {
      //   this.refreshChart(this.ASCID)
      // }
    },
    ShowPanelStrength (selIndex) {
      if (this.strengthData === undefined || selIndex === undefined) return
      this.curStrengthData = this.alldata[selIndex]
    },
    ShowPanelRang (selIndex) {
      if (this.TimingData === undefined ||
      this.$refs.periodicPanel === undefined || selIndex === undefined) return
      this.curTimeData = this.TimingData[selIndex]
      this.$refs.periodicPanel.refreshRangChart()
    },
    InitStrengthChartEvent () {
      this.strengthChart.on('updateAxisPointer', event => {
        this.ShowPanelStrength(event.dataIndex)
      })

      this.strengthChart.on('click', event => {
        if (this.strengthData === undefined) return
        this.timingChart.dispatchAction({
          type: 'downplay',
          dataIndex: this.timeSelIndex
        })
        this.timeSelIndex = this.findIndexInTimeData(event.name)
        this.timingChart.dispatchAction({
          type: 'highlight',
          dataIndex: this.timeSelIndex
        })

        this.ShowPanelRang(this.timeSelIndex)
      })

      this.strengthChart.on('legendselectchanged', event => {
        // 点击图例取消某相位显示，对应的相位分布图也要取消该相位
        this.strengthSeleted = event.selected
      })
    },
    InitTimeChartEvent () {
      this.timingChart.on('updateAxisPointer', event => {
        this.ShowPanelRang(event.dataIndex)
      })

      this.timingChart.on('click', event => {
        if (this.TimingData === undefined) return
        this.curTimeData = this.TimingData[event.dataIndex]

        this.timingChart.dispatchAction({
          type: 'downplay',
          dataIndex: this.timeSelIndex
        })

        let date = event.name
        this.strengthChart.dispatchAction({
          type: 'downplay',
          dataIndex: this.strSelIndex
        })
        this.strSelIndex = this.findIndexInStrengthData(date)
        this.strengthChart.dispatchAction({
          type: 'highlight',
          dataIndex: this.strSelIndex
        })

        this.ShowPanelStrength(this.strSelIndex)
      })
      this.timingChart.on('legendselectchanged', event => {
        // 点击图例取消某相位显示，对应的周期分布图也要取消该相位
        this.periodicSeleted = event.selected
      })
    },
    findIndexInTimeData (date) {
      let XAxisList = this.getTimingXAxis(this.TimingData)
      // XAxisList = XAxisList.sort((val1, val2) => {
      //   return new Date(val1).getTime() > new Date(val2).getTime()
      // })
      for (let index = XAxisList.length - 1; index >= 0; --index) {
        let strDate = new Date(date)
        let timeDate = new Date(XAxisList[index])
        if (timeDate.getTime() < strDate.getTime()) { return index - 1 }
      }
      return -1
    },
    findIndexInStrengthData (date) {
      let XAxisList = this.getStrXAxis(this.strengthData)
      for (let index = 0; index < XAxisList.length; ++index) {
        let timeDate = new Date(date)
        let strDate = new Date(XAxisList[index])
        if (timeDate.getTime() < strDate.getTime()) { return index }
      }

      return -1
    },
    async refreshChart () {
      this.lockScreen()
      await this.getHistoryPattern()
      this.refreshStrengthChart()
      this.refreshTimingChart()
      this.unlockScreen()
      this.changeBtnStyle('model')
    },
    renderStrengthChart () {
      // 渲染
      if (this.strengthData.length === 0) {
        this.strengthChart.clear()
        return
      }
      // 取最新时刻的相位数据，赋值给curStrengthData
      this.curStrengthData = this.alldata[this.alldata.length - 1]
      let strOption = {
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          containLabel: true,
          height: '80%',
          width: '95%',
          left: '18',
          bottom: '20'
        },
        xAxis: {
          boundaryGap: false,
          axisLabel: {
            textStyle: {
              color: getTheme() === 'light' ? '#666666' : '#B9BABF'
            }
          },
          data: this.getStrXAxis(this.strengthData)
        },
        yAxis: {
          splitLine: {
            lineStyle: {
              color: getTheme() === 'light' ? '#DCDFE6' : '#30384d'
            }
          },
          splitArea: {
            show: true,
            areaStyle: {
              color: ['#fafafa', '#fff']
            }
          },
          axisLabel: {
            textStyle: {
              color: getTheme() === 'light' ? '#666666' : '#B9BABF'
            }
          }
        },
        series: this.getStrSeries(this.strengthData)
      }
      this.strengthChart.setOption(strOption)
    },
    refreshStrengthChart () {
      return this.getStrengthData().then(data => {
        this.alldata = JSON.parse(JSON.stringify(data))
        this.decisionModel = new TrafficDataDecisionModel(data)
        const curData = this.decisionModel.getData('mode')
        this.strengthData = curData
        this.renderStrengthChart()
      })
    },
    getStrXAxis (dataList) {
      let xAxisList = []
      for (let data of dataList) {
        xAxisList = [...xAxisList, data.date]
      }
      return [...new Set(xAxisList)]
    },
    getStrSeries (dataList) {
      let series = {
        type: 'line',
        itemStyle: {
          color: '#75B1E6'
        },
        areaStyle: {
          normal: {
            color: new echart.graphic.LinearGradient(
              0, 0, 0, 1,
              [
                {offset: 0, color: '#77b3e8'},
                {offset: 0.2, color: '#7cb5e9'},
                {offset: 0.4, color: '#95c5ed'},
                {offset: 0.6, color: '#b1d3f1'},
                {offset: 0.8, color: '#c9e0f5'},
                {offset: 1, color: '#cee3f6'}
              ]
            ) // 区域的颜色
          }
        }
      }
      series.data = dataList.map(item => {
        return item.value
      })
      return [series]
    },
    refreshTimingChart () {
      return this.getTimingData().then(data => {
        this.TimingData = data
        if (this.TimingData.length === 0) {
          this.timingChart.clear()
          return
        }
        this.curTimeData = data[data.length - 1]
        let strOption = {
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            containLabel: true,
            height: '80%',
            width: '95%',
            left: '18',
            bottom: '20'
          },
          legend: {
            left: 'right',
            textStyle: {
              color: getTheme() === 'light' ? '#666666' : '#B9BABF'
            },
            data: this.getTimingLegend(this.TimingData)
          },
          xAxis: {
            axisLabel: {
              textStyle: {
                color: getTheme() === 'light' ? '#666666' : '#B9BABF'
              }
            },
            data: this.getTimingXAxis(this.TimingData)
          },
          yAxis: {
            axisLabel: {
              textStyle: {
                color: getTheme() === 'light' ? '#666666' : '#B9BABF'
              }
            },
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
          series: this.getTimingSeries(this.TimingData)
        }

        this.timingChart.setOption(strOption)
      })
    },
    getTimingLegend (dataList) {
      let lergeList = []

      for (let data of dataList) {
        let rang1Data = data.rang1.map(item => {
          return item.phasename
        })
        let rang2Data = data.rang2.map(item => {
          return item.phasename
        })
        lergeList = [...lergeList, ...rang1Data, ...rang2Data]
      }

      return [...new Set(lergeList)]
    },
    getTimingXAxis (dataList) {
      return dataList.map(item => {
        return item.date
      })
    },
    getTimingSeries (dataList) {
      let series = []
      for (let data of dataList) {
        for (let i = 0; i < data.rang1.length; i++) {
          let phase = data.rang1[i]
          let ser = series.find(val => val.name === phase.phasename && val.stack === 'rang1')
          if (ser === undefined) {
            series.push({
              name: phase.phasename,
              type: 'bar',
              stack: 'rang1',
              barMaxWidth: 20,
              itemStyle: {
                color: this.Color.get(i)
              },
              data: [phase.time]
            })
          } else {
            ser.data.push(phase.time)
          }
        }

        for (let i = 0; i < data.rang2.length; i++) {
          let phase = data.rang2[i]
          let ser = series.find(val => val.name === phase.phasename && val.stack === 'rang2')
          if (ser === undefined) {
            series.push({
              name: phase.phasename,
              type: 'bar',
              stack: 'rang2',
              barMaxWidth: 20,
              data: [phase.time],
              itemStyle: {
                color: this.Color.get(i)
              }
            })
          } else {
            ser.data.push(phase.time)
          }
        }
      }
      return series
    },
    getHistoryPattern () {
      let params = {
        beginTime: moment(this.date[0]).format('YYYY-MM-DD HH:mm:ss'),
        endTime: moment(this.date[1]).format('YYYY-MM-DD HH:mm:ss'),
        agentId: this.ASCID
      }
      return new Promise((resolve, reject) => {
        getHistoryPatternData(params).then(res => {
          if (!res.data.success) {
            reject(res.data.message)
          }
          this.HistoryPatternData = res
          resolve()
        }).catch(() => {
          console.log('getHistoryPatternData error')
          this.unlockScreen()
        })
      })
    },
    getStrengthData () {
      return new Promise((resolve, reject) => {
        let res = JSON.parse(JSON.stringify(this.HistoryPatternData))
        let resData = []
        for (let data of res.data.data) {
          let date = moment(data['time']).format('MM-DD HH:mm:ss')
          let phaseData = {
            date,
            mode: data.data.mode,
            control: data.data.control,
            patternid: data.data.patternid,
            cycle: data.data.cycle,
            offset: data.data.offset,
            syncTime: data.data.syncTime,
            curTime: data.data.curTime
          }
          resData.push(phaseData)
        }
        resolve(resData)
      })
    },

    getRingId (phaseId, ringList) {
      let ringNum = -1
      for (let ringInfo of ringList) {
        if (ringInfo.sequence.indexOf(phaseId) > -1) {
          ringNum = ringInfo.num
          break
        }
      }

      return ringNum
    },

    getTimingData () {
      return new Promise((resolve, reject) => {
        let res = JSON.parse(JSON.stringify(this.HistoryPatternData))
        let resData = []
        for (let data of res.data.data) {
          let curTime = moment(data['time']).format('MM-DD HH:mm:ss')
          let timeData = resData.find(val => curTime === val.date)
          if (timeData === undefined) {
            timeData = {
              date: curTime,
              rang1: [],
              rang2: []
            }
            resData.push(timeData)
          }
          if (data.data.phase === undefined) continue
          for (let phaseData of data.data.phase) {
            let info = {
              phasename: '相位' + phaseData.id,
              time: phaseData.split
            }
            let ringIndex = this.getRingId(phaseData.id, data.data.rings)
            if (ringIndex === 1) timeData.rang1.push(info)
            if (ringIndex === 2) timeData.rang2.push(info)
          }
          timeData.rang1.sort((val1, val2) => {
            return val1.phasename > val2.phasename
          })
          timeData.rang2.sort((val1, val2) => {
            return val1.phasename > val2.phasename
          })
          // 生成按环重复的颜色
          for (let i = 0; i < timeData.rang1.length; i++) {
            timeData.rang1[i].color = this.Color.get(i)
          }
          for (let i = 0; i < timeData.rang2.length; i++) {
            timeData.rang2[i].color = this.Color.get(i)
          }
        }
        resolve(resData)
      })
    }
  }
}
</script>
