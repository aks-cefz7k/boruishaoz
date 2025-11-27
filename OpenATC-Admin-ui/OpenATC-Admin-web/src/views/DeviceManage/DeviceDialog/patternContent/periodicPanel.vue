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
  <div class="periodicPanel">
<div class="title">{{$t('openatc.patternstatistic.periodicdistribution')}}</div>
      <div id="rangchart" class="chart"></div>
  </div>
</template>
<script>
import echart from 'echarts'
export default {
  name: 'periodicPanel',
  props: {
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '100%'
    },
    rangdata: {
      type: Object,
      default: undefined
    },
    periodicSelet: {
      type: Object,
      default: undefined
    }
  },
  data () {
    return {
    }
  },
  watch: {
    periodicSelet (val) {
      this.refreshCharts()
    },
    rangdata (val) {
      this.refreshCharts()
    }
  },
  methods: {
    initCharts () {
      this.phaseChart = echart.init(document.getElementById('rangchart'))
      const _this = this
      // 因为引用的父组件和子组件都使用了window.onresize以至于子组件window.onresize失效。需改写为以下方式
      window.addEventListener('resize', () => {
        _this.phaseChart.resize()
      }, false)
    },
    refreshCharts () {
      this.refreshRangChart()
    },
    refreshRangChart () {
      // 渲染百分比环图
      let strOption = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        grid: {
          containLabel: true,
          height: '100%',
          width: '100%',
          left: '18',
          bottom: '20'
        },
        title: {
          textStyle: {
            color: '#999999',
            fontSize: 12,
            top: '15',
            left: '5'
          }
        },
        series: this.getRangSeries()
      }
      this.phaseChart.setOption(strOption)
    },
    getRangSeries () {
      // 获取百分比环状图的series
      if (this.rangdata === undefined) return []
      let series = []
      let data = JSON.parse(JSON.stringify(this.rangdata))
      if (this.periodicSelet) {
        // lengend控制显示
        const periodicSelet = JSON.parse(JSON.stringify(this.periodicSelet))
        Object.keys(periodicSelet).forEach(key => {
          if (!periodicSelet[key]) {
            data.rang1.forEach((ele, index) => {
              if (ele.phasename === key) {
                data.rang1.splice(index, 1)
              }
            })
            data.rang2.forEach((ele, index) => {
              if (ele.phasename === key) {
                data.rang2.splice(index, 1)
              }
            })
          }
        })
      }
      if (data.rang1) {
        series.push({
          name: 'rang1',
          type: 'pie',
          radius: ['55%', '60%'],
          data: data.rang1.map(item => {
            return {
              name: item.phasename,
              value: item.time,
              itemStyle: {
                color: item.color
              }
            }
          })
        })
      }

      if (data.rang2) {
        series.push({
          name: 'rang2',
          type: 'pie',
          radius: ['45%', '50%'],
          data: data.rang2.map(item => {
            return {
              name: item.phasename,
              value: item.time,
              itemStyle: {
                color: item.color
              }
            }
          })
        })
      }

      return series
    }
  },
  mounted () {
    this.initCharts()
    this.refreshCharts()
  }
}
</script>
