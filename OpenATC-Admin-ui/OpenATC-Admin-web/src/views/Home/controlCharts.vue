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
    <el-row>
      <el-col :span="10">
        <div class="control-charts" id="control-charts"></div>
      </el-col>
      <el-col :span="14">
        <el-row class="legend">
          <div class="title">{{$t('openatc.home.specialcontrolmode')}}</div>
          <el-col v-if="controlitem.special" :span="12" class="control-item" v-for="(controlitem, index) in allControlList" :key="controlitem.id">
            <span class="mark" :style="{'background-color': Color[index % 9]}"></span>
            <span class="name">{{controlitem.name}}</span>
            <span class="num">{{controlitem.value}}</span>
          </el-col>
        </el-row>
      </el-col>
     </el-row>
  </div>
</template>

<script>
import echart from 'echarts'
import { getLanguage, getTheme } from '@/utils/auth'
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
      allControl: [1, 2, 3, 4, 22, 0, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24, 99, 100],
      specialControl: [1, 2, 3, 4, 22],
      allControlList: []
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
      if (this.allControlList.length === 0) {
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
            radius: ['28%', '80%'],
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
      for (let i = 0; i < this.allControlList.length; i++) {
        let obj = {}
        if (this.allControlList[i].value !== 0) {
          obj.name = this.allControlList[i].name
          obj.value = this.allControlList[i].value
          let itemstyle = {
            color: this.Color[i % 9]
          }
          obj.itemStyle = itemstyle
          arr.push(obj)
        }
      }
      if (arr.length > 1) {
        // 数据大于一个，显示饼图间隔
        for (let j = 0; j < arr.length; j++) {
          arr[j].itemStyle = {
            ...arr[j].itemStyle,
            borderWidth: 1,
            borderColor: getTheme() === 'light' ? '#fff' : '#202940'
          }
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
      this.allControlList = this.allControlList.map(controlitem => {
        let value = this.controlMap.get(controlitem.id)
        if (value !== undefined) {
          controlitem.value = this.controlMap.get(controlitem.id)
        }
        return controlitem
      })
      // console.log(this.allControlList)
      this.refreshChart()
    },
    getLang () {
      return getLanguage()
    },
    reset () {
      this.controlMap = new Map()
      this.allControlList = this.allControlList.map(ele => {
        return {
          ...ele,
          value: 0
        }
      })
    },
    createAllControlList () {
      this.allControlList = []
      let specialindex = 0
      for (let i = 0; i < this.allControl.length; i++) {
        let obj = {
          id: this.allControl[i],
          name: this.$t('edge.overview.modelList' + this.allControl[i]),
          value: 0
        }
        if (this.specialControl.indexOf(this.allControl[i]) !== -1) {
          obj.special = true
          obj.specialindex = specialindex++
        }
        this.allControlList.push(obj)
      }
      console.log(this.allControlList)
    }
  },
  created () {
    // for (let i = 0; i < this.allControlList.length; i++) {
    //   this.allControlList[i].color = this.Color.get(this.allControlList[i].id)
    // }
    this.createAllControlList()
  },
  mounted () {
    this.initChart()
    this.refresh()
  }
}
</script>
