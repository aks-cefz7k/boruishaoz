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
    <div :style="bgStyle" :ref="domName">
        <div class="horizontalconimg">
          <slot name="leftslot"></slot>
        </div>
        <div class="horizontalcontext">
          <div class="horizontalContextBox" v-for="(data, index) in dataList" :key="index">
            <div class="horizontal">
              <el-progress :color="data.color" :percentage="data.horizontalWidth" :show-text="false"></el-progress>
            </div>
            <div class="hor_num"><span :style="rightNum">{{data.value}}</span></div>
          </div>
          <div class="hor_text" v-if="isShowText">{{this.name}}</div>
        </div>
    </div>
</template>
<script>
export default {
  name: 'horizontal-children',
  props: {
    dataList: {
      type: Array,
      default: () => {
        return []
      }
    },
    name: {
      type: String,
      default: null
    },
    horizontalDistance: { // 上下两个的间距
      type: String,
      default: '3px'
    },
    mainwidth: {
      type: String,
      default: '400px'
    },
    mainheight: {
      type: String,
      default: '25px'
    },
    display: { // 是否展示右侧数值
      type: Boolean,
      default: true
    },
    backColor: { // 背景色。默认没有背景，可用rgba形式加背景色的透明度。
      type: String,
      default: 'rgba(0, 0, 0, 0)'
    },
    isShowText: { // 默认显示text
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      bgStyle: {
        'position': 'relative',
        'marginBottom': '',
        'width': '',
        'height': ''
      },
      rightNum: {
        'line-height': '80%',
        'color': '#666',
        'display': 'block',
        'font-size': '13px'
      },
      horizontalWidth: 0,
      domName: 'progress'
    }
  },
  watch: {
    dataList: {
      handler: function (val) {
        this.getDatalist()
      },
      deep: true
    }
  },
  methods: {
    isShowrightnum () {
      if (this.display) return
      this.rightNum.display = 'none'
    },
    getDatalist () {
      if (!this.dataList.length) return
      this.dataList.forEach(ele => {
        if (ele.value > ele.maxNum) {
          ele.horizontalWidth = 100
        } else {
          ele.horizontalWidth = ele.value / ele.maxNum * 100
        }
        if (ele.color === undefined) {
          ele.color = '#43b4cf'
        }
      })
    },
    changeBackColor () {
      const domName = this.domName
      // 设置背景色
      const clsObjs = this.$refs[domName].getElementsByClassName('el-progress-bar__outer')
      for (var i = 0; i < clsObjs.length; i++) {
        clsObjs[i].style.backgroundColor = this.backColor
      }
    }
  },
  created () {
    this.domName = `progress_${Math.random()}`
  },
  mounted () {
    this.bgStyle.width = this.mainwidth
    this.bgStyle.height = this.mainheight
    this.bgStyle.marginBottom = this.horizontalDistance
    this.changeBackColor()
    this.getDatalist()
    this.isShowrightnum()
  }
}
</script>
<style scoped>
.horizontalstyle {
  float: left;
  margin: 0px auto;
  padding: 0px;
  /* width: 20%; */
  height: 15%;
  /* background: red; */
  overflow: hidden;
  margin-top: 0%;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
  width:100px;
  transition: width 1s;
  -moz-transition: width 1s; /* Firefox 4 */
  -webkit-transition: width 1s; /* Safari 和 Chrome */
  -o-transition: width 1s; /* Opera */
}
.horizontalcontext {
  float: left;
  width: 85%;
}
.horizontalconimg {
  float: left;
  margin-right: 6px;
  /* min-width: 34px;
  min-height: 30px; */
}
.horizontal {
  float: left;
  width: 92%;
}
.horizontalContextBox {
  overflow: hidden;
  margin-bottom: 5px;
  position: relative;
  min-height:10px;
}
.hor_num {
  /*float: left;
  width: 10%;*/
  margin-left: 15px;
  position: absolute;
  right: 0;
}
.hor_text {
  margin-top: -5px;
  color: #bbb;
  font-size: 10px;
  margin-top: 2px;
  text-align: left;
}
</style>
