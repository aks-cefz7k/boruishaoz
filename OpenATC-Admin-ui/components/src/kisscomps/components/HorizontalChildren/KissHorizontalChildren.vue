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
          <div class="hor_text">{{this.name}}</div>
        </div>
    </div>
</template>
<script>
export default {
  name: 'kiss-horizontalchildren',
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
    // value: {
    //   type: Number,
    //   default: 0
    // },
    // color: {
    //   type: String,
    //   default: '#43b4cf'
    // },
    // maxNum: { // 最大数值
    //   type: Number,
    //   default: 1
    // },
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
        'color': '#fff',
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
      if (this.display) {
        if (!this.dataList.length) return
        this.dataList.forEach(ele => {
          if (ele.value > ele.maxNum) {
            ele.horizontalWidth = 80
          } else {
            ele.horizontalWidth = ele.value / ele.maxNum * 80 // 如果展示右侧数字的话，横条的最大长度占80%
          }
          if (ele.color === undefined) {
            ele.color = '#43b4cf'
          }
        })
      } else {
        if (!this.dataList.length) return
        this.dataList.forEach(ele => {
          if (ele.value > ele.maxNum) {
            ele.horizontalWidth = 100
          } else {
            ele.horizontalWidth = ele.value / ele.maxNum * 100 // 如果不展示右侧数字的话，横条的最大长度占100%
          }
          if (ele.color === undefined) {
            ele.color = '#43b4cf'
          }
        })
      }
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
  width: 80%;
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
