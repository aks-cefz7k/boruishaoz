<template>
  <div id="progressBar">
    <!-- 进度条 -->
    <div class="bar">
        <span class="curProcess" :style="{width:percentage+'%'}"></span>
    </div>
    <!-- 圆 -->
    <span v-for="(item, index) in circleNomal" class="circle" :key="`circleNomal-${index}`" :class="item.className" :style="{left:item.left}"></span>
    <!-- 菱形 -->
    <span v-for="(item, index) in timeLine" class="diamond" :key="`timeLine-${index}`" :class="item.className" :style="{left:item.left}"></span>
    <!-- 当前时间标记 -->
    <div id="curTime" :style="{left:percentage+'%'}">
      <div>{{curTime}}</div>
      <span class="curTimeIcon"></span>
    </div>
    <!-- 时间节点 -->
    <div style="width: 100%; position: relative;">
      <span v-for="(item, index) in timeLine" :key="index" class="time" :style="{left:item.left}">
        {{item.time}}
      </span>
    </div>
    <div class="curText">已完成:{{percentage}}%</div>
  </div>
</template>
<script>
import moment from 'moment'
export default {
  name: 'kiss-simulation-progress',
  data () {
    return {
      timeLine: [{ // 目前时间写死为一天五等分
        left: '0%',
        className: 'normalDiamond'
      }, {
        left: '25%',
        className: 'normalDiamond'
      }, {
        left: '50%',
        className: 'normalDiamond'
      }, {
        left: '75%',
        className: 'normalDiamond'
      }, {
        left: '100%',
        className: 'normalDiamond'
      }],
      circleNomal: [],
      percentage: 0,
      curTime: 0
    }
  },
  props: {
    totalseconds: {
      type: Number,
      default: 0
    },
    curseconds: {
      type: Number,
      default: 0
    },
    starttime: {
      type: String,
      default: '2019-01-01 00:00:00'
    },
    endtime: {
      type: String,
      default: '2019-01-01 00:00:00'
    }
  },
  watch: {
    curseconds: function (val) {
      let mseconds = val + '000'
      this.curstamp = Number(this.startstamp) + Number(mseconds)
      this.curTime = this.stampToTime(this.curstamp)
      this.percentage = (this.curseconds / this.totalseconds * 100).toFixed(0)
      // this.createCircle()
      this.changeProgressStyle()
    }
  },
  directives: {
    drag: function (el) {
      let dragBox = el
      dragBox.onmousedown = e => {
        // 算出鼠标相对元素的位置
        let disX = e.clientX - dragBox.offsetLeft
        document.onmousemove = e => {
          // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          let left = e.clientX - disX
          // 移动当前元素
          dragBox.style.left = left + 'px'
        }
        document.onmouseup = e => {
          // 鼠标弹起来的时候不再移动
          document.onmousemove = null
          // 预防鼠标弹起来后还会循环(即预防鼠标放上去的时候还会移动)
          document.onmouseup = null
        }
      }
    }
  },
  methods: {
    createCircle () {
      this.circleNomal = []
      let curCircleLeft = 0
      while (curCircleLeft < 100) {
        if (curCircleLeft % 25 !== 0) {
          this.circleNomal.push({
            left: `${curCircleLeft}%`,
            className: 'normalCircle'
          })
        }
        curCircleLeft = curCircleLeft + 6.25
      }
    },
    changeProgressStyle () {
      this.circleNomal.forEach(ele => {
        if (Number(ele.left.split('%')[0]) > this.percentage) {
          ele.className = 'abnormalCircle'
        } else {
          ele.className = 'normalCircle'
        }
      })
      this.timeLine.forEach(ele => {
        if (Number(ele.left.split('%')[0]) >= this.percentage) {
          ele.className = 'abnormalDiamond'
        } else {
          ele.className = 'normalDiamond'
        }
      })
    },
    createTimeLine () {
      // 根据起始时间，计算时间线（默认5个时间节点）
      this.startstamp = this.timeToStamp(this.starttime)
      this.endstamp = this.timeToStamp(this.endtime)
      const step = (this.endstamp - this.startstamp) / 4
      for (let i = 0; i < 5; i++) {
        this.timeLine[i].time = this.computedDateTime(this.startstamp, step * i)
      }
    },
    computedDateTime (startstamp, step) {
      // 根据开始时间戳和可变步长，计算当前时间
      const nowstamp = Number(startstamp) + Number(step)
      const nowTime = this.stampToTime(nowstamp)
      return nowTime
    },
    timeToStamp (timeString) {
      // 将日期格式字符串转换为时间戳
      const time = new Date(timeString)
      return moment(time).valueOf()
    },
    stampToTime (stamp) {
      // 将时间戳转为日期格式字符串
      return moment(stamp).format('MM-DD HH:mm:ss')
      // return moment(stamp).format('HH:mm:ss')
    }
  },
  mounted () {
    this.createTimeLine()
    this.createCircle()
  }
}
</script>
<style scoped>
#progressBar{
  width: 80%;
  height: 50px;
  position: relative;
  margin: 20px 0 0 30px;
}
#progressBar .bar{
  width: 100%;
  height: 3px;
  position: absolute;
  top:50%;
  left: 0;
  margin-top:-30px;
  background: #0096ba;
  z-index: 1;
}
#progressBar .bar  .curProcess{
  position: absolute;
  display: inline-block;
  background: #42daff;
  height: 3px;
  z-index: 1;
  transition: width 700ms linear;
  -moz-transition: width 700ms linear; /* Firefox 4 */
  -webkit-transition: width 700ms linear; /* Safari 和 Chrome */
  -o-transition: width 700ms linear; /* Opera */
}

/* 圆 */
.circle {
  position: absolute;
  top:0;
  margin-top: -9px;
  width: 11px;
  height: 11px;
  border-radius: 50%;
  margin-left: -5px;
  color:#fff;
  z-index: 9;
  box-sizing: border-box;
  transition: background 600ms;
  -moz-transition: background 600ms; /* Firefox 4 */
  -webkit-transition: background 600ms; /* Safari 和 Chrome */
  -o-transition: background 600ms; /* Opera */
}
.normalCircle{
  border:2px solid #42daff;
  background: #42daff;
}
.abnormalCircle {
  border: 2px solid #0096ba;
  background:#181818;
}
/* 菱形 */
#progressBar>.diamond {
  position: absolute;
  top:0;
  z-index: 10;
  width: 10px;
  height: 10px;
  /* Rotate */
  -webkit-transform: rotate(-45deg);
  transform: rotate(-45deg);
  -webkit-transform-origin: 0 100%;
  transform-origin: 0 100%;
  border-radius: 0;
  border: none;
  margin-top: -7px;
  margin-left: 0px;
  transition: background 700ms linear;
  -moz-transition: background 700ms linear; /* Firefox 4 */
  -webkit-transition: background 700ms linear; /* Safari 和 Chrome */
  -o-transition: background 700ms linear; /* Opera */
}
#progressBar>.normalDiamond {
  background: #42daff;
}
#progressBar>.abnormalDiamond {
  background: #0096ba;
}
/* 时间节点 */
#progressBar .time {
  position: absolute;
  top: 0;
  width: 48px;
  height: 11px;
  font-size: 12px;
  font-family: 'MicrosoftYaHei';
  line-height: 35px;
  color: #999;
  width: 100px;
  white-space: nowrap;
  height: 40px;
  transform: translateX(-50%);
}
/* 当前时间 */
#progressBar #curTime {
  position: absolute;
  top: -52px;
  z-index: 20;
  line-height: 35px;
  color: #fff;
  font-size: 14px;
  transition: left 700ms linear;
  -moz-transition: left 700ms linear; /* Firefox 4 */
  -webkit-transition: left 700ms linear; /* Safari 和 Chrome */
  -o-transition: left 700ms linear; /* Opera */
}
#progressBar #curTime>div {
  /* min-width: 100px; */
  transform: translateX(-50%);
  white-space: nowrap;
}
#progressBar .curTimeIcon {
  display: inline-block;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #42daff;
  border: 2px solid #0096ba;
  transform: translateX(-50%);
}
#progressBar .curText {
  position: absolute;
  top: -16px;
  right: -100px;
  font-size: 14px;
  color: #fff;
  font-family: 'MicrosoftYaHei';
}
</style>
