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
  <div class="control-charts-panel" :class="{'control-charts-panel-en': getLang() !== 'zh'}">
    <div class="control-charts" id="control-charts"></div>
    <div class="legend">
      <div class="title">特殊控制方式</div>
      <div class="control-item" v-for="(controlitem, index) in controlList" :key="controlitem.id">
        <!-- <span class="mark" :style="{'background-color': controlitem.color}"></span> -->
        <span class="mark" :style="{'background-color': Color[index % 9]}"></span>
        <span class="name">{{controlitem.name}}</span>
        <span class="num">{{controlitem.value}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import echart from 'echarts'
import { getLanguage } from '@/utils/auth'
export default {
  name: 'control-charts',
  props: {
    curDevsData: {
      type: Array
    }
  },
  watch: {
    curDevsData: {
      handler: function (val) {
        this.refresh()
      },
      deep: true
    }
  },
  data () {
    return {
      chartData: [],
      // ColorMap: new Map([[1, '#3BA272'], [2, '#FAC858'], [3, '#73C0DE'], [4, '#91CC75'], [5, '#EE6666'], [0, '#5470C6']]),
      Color: ['#3BA272', '#FAC858', '#73C0DE', '#91CC75', '#EE6666', '#5470C6', '#cccc66', '#ea7ccc', '#9a60b4'],
      controlMap: new Map(),
      controlList: [
        {
          id: 1,
          name: this.$t('edge.overview.modelList1'),
          value: 0
        }, {
          id: 2,
          name: this.$t('edge.overview.modelList4'),
          value: 0
        }, {
          id: 3,
          name: this.$t('edge.overview.modelList2'),
          value: 0
        }, {
          id: 4,
          name: this.$t('edge.overview.modelList22'),
          value: 0
        }, {
          id: 5,
          name: this.$t('edge.overview.modelList3'),
          value: 0
        }
      ]
    }
  },
  methods: {
    initChart () {
      this.controlChart = echart.init(document.getElementById('control-charts'))
      const _this = this
      // 因为引用的父组件和子组件都使用了window.onresize以至于子组件window.onresize失效。需改写为以下方式
      window.addEventListener('resize', () => {
        _this.controlChart.resize()
      }, false)
    },
    refreshChart () {
      if (this.controlList.length === 0) {
        this.controlChart.clear()
        return
      }
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: 'control',
            type: 'pie',
            radius: ['15%', '80%'],
            emphasis: {
              label: {
                show: true
              }
            },
            data: this.getData()
          }
        ]
        // ,
        // color: this.Color
      }
      this.controlChart.setOption(option)
    },
    getData () {
      let arr = []
      for (let i = 0; i < this.controlList.length; i++) {
        let obj = {}
        if (this.controlList[i].value !== 0) {
          obj.name = this.controlList[i].name
          obj.value = this.controlList[i].value
          let color = {
            color: this.Color[i % 9]
          }
          obj.itemStyle = color
          arr.push(obj)
        }
      }
      return arr
    },
    refresh () {
      this.reset()
      this.curDevsData.forEach(cross => {
        if (cross.control !== undefined) {
          let controlvalue = this.controlMap.get(cross.control)
          if (controlvalue !== undefined) {
            controlvalue = controlvalue + 1
            this.controlMap.set(cross.control, controlvalue)
          } else {
            this.controlMap.set(cross.control, 1)
          }
        }
      })
      // console.log('controlMap', this.controlMap)
      this.controlList = this.controlList.map(controlitem => {
        let value = this.controlMap.get(controlitem.id)
        if (value !== undefined) {
          controlitem.value = this.controlMap.get(controlitem.id)
        }
        return controlitem
      })
      // console.log(this.controlList)
      this.refreshChart()
    },
    getLang () {
      return getLanguage()
    },
    reset () {
      this.controlMap = new Map()
      this.controlList = this.controlList.map(ele => {
        return {
          ...ele,
          value: 0
        }
      })
    }
  },
  created () {
    // for (let i = 0; i < this.controlList.length; i++) {
    //   this.controlList[i].color = this.Color.get(this.controlList[i].id)
    // }
  },
  mounted () {
    this.initChart()
    this.refresh()
  }
}
</script>
