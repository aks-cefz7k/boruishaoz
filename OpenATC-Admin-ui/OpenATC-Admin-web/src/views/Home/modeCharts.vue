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
  <div class="mode-charts-panel" :class="{'mode-charts-panel-en': getLang() !== 'zh'}">
    <div class="mode-charts" id="mode-charts"></div>
    <div class="legend">
      <div class="mode-item" v-for="(modeitem, index) in modeList" :key="modeitem.id">
        <!-- <span class="mark" :style="{'background-color': modeitem.color}"></span> -->
        <span class="mark" :style="{'background-color': Color[index % 9]}"></span>
        <span class="name">{{modeitem.name}}</span>
        <span class="num">{{modeitem.value}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import echart from 'echarts'
import { getLanguage } from '@/utils/auth'
export default {
  name: 'mode-charts',
  props: {
    curDevsData: {
      type: Array
    }
  },
  watch: {
    curDevsData: {
      handler: function () {
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
      modeMap: new Map(),
      modeList: [
        {
          id: 1,
          name: this.$t('openatc.patternstatistic.mode1'),
          value: 0
        }, {
          id: 2,
          name: this.$t('openatc.patternstatistic.mode2'),
          value: 0
        }, {
          id: 3,
          name: this.$t('openatc.patternstatistic.mode3'),
          value: 0
        }, {
          id: 4,
          name: this.$t('openatc.patternstatistic.mode4'),
          value: 0
        }, {
          id: 5,
          name: this.$t('openatc.patternstatistic.mode5'),
          value: 0
        }, {
          id: 6,
          name: this.$t('openatc.patternstatistic.mode6'),
          value: 0
        }, {
          id: 7,
          name: this.$t('openatc.patternstatistic.mode7'),
          value: 0
        }, {
          id: 8,
          name: this.$t('openatc.patternstatistic.mode8'),
          value: 0
        }, {
          id: 0,
          name: this.$t('openatc.patternstatistic.mode0'),
          value: 0
        }
      ]
    }
  },
  methods: {
    initChart () {
      this.modeChart = echart.init(document.getElementById('mode-charts'))
      const _this = this
      // 因为引用的父组件和子组件都使用了window.onresize以至于子组件window.onresize失效。需改写为以下方式
      window.addEventListener('resize', () => {
        _this.modeChart.resize()
      }, false)
    },
    refreshChart () {
      if (this.modeList.length === 0) {
        this.modeChart.clear()
        return
      }
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: 'mode',
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
      this.modeChart.setOption(option)
    },
    getData () {
      let arr = []
      for (let i = 0; i < this.modeList.length; i++) {
        let obj = {}
        if (this.modeList[i].value !== 0) {
          obj.name = this.modeList[i].name
          obj.value = this.modeList[i].value
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
        if (cross.mode !== undefined) {
          let modevalue = this.modeMap.get(cross.mode)
          if (modevalue !== undefined) {
            modevalue = modevalue + 1
            this.modeMap.set(cross.mode, modevalue)
          } else {
            this.modeMap.set(cross.mode, 1)
          }
        }
      })
      // console.log('modeMap', this.modeMap)
      this.modeList = this.modeList.map(modeitem => {
        let value = this.modeMap.get(modeitem.id)
        if (value !== undefined) {
          modeitem.value = this.modeMap.get(modeitem.id)
        }
        return modeitem
      })
      console.log(this.modeList)
      this.refreshChart()
    },
    getLang () {
      return getLanguage()
    },
    reset () {
      this.modeMap = new Map()
      this.modeList = this.modeList.map(ele => {
        return {
          ...ele,
          value: 0
        }
      })
    }
  },
  created () {
    // for (let i = 0; i < this.modeList.length; i++) {
    //   this.modeList[i].color = this.ColorMap.get(this.modeList[i].id)
    // }
  },
  mounted () {
    this.initChart()
    this.refresh()
  }
}
</script>
