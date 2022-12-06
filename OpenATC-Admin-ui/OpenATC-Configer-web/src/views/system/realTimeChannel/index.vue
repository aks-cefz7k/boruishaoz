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
<div class="channel-panel">
  <div class="channels">
    <!-- <div v-for="(item,index) in data" :key="item.name" class="single-channel" :style="index === 5 ? 'clear:both;' : ''" :class="{'twoRow': index >= 5}"> -->
    <div v-for="item in data" :key="item.name" class="single-channel">
      <div class="name">{{item.name}}</div>
      <div class="content">
        <div v-for="channel in item.channels" :key="channel.channelnum" class="channel">
          <div v-for="lamp in channel.lamps" :key="lamp.lampcolor" class="lamps">
            <div class="lamp"
              :class="{'redlamp': lamp.lampcolor === 1 && !lamp.selected,
                'yellowlamp': lamp.lampcolor === 2 && !lamp.selected,
                'greenlamp': lamp.lampcolor === 3 && !lamp.selected,
                'activeRedlamp': lamp.lampcolor === 1 && lamp.selected,
                'activeYellowlamp': lamp.lampcolor === 2 && lamp.selected,
                'activeGreenlamp': lamp.lampcolor === 3 && lamp.selected}"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import { getRealTimeChannel } from '@/api/system'
export default {
  name: 'realtimechannel',
  components: {},
  data () {
    return {
      lamps: [{
        lampcolor: 1,
        name: '红灯',
        selected: false
      }, {
        lampcolor: 2,
        name: '黄灯',
        selected: false
      }, {
        lampcolor: 3,
        name: '绿灯',
        selected: false
      }],
      data: [],
      list: [],
      loading: false,
      realTimeChannelTimer: null // 定时器
    }
  },
  mounted () {
    this.handleCreateLampCtrboard()
  },
  //   beforeUpdate () {
  //     this.getRealTimeChannel()
  //   },
  methods: {
    handleCreateLampCtrboard () {
      this.data = []
      for (let i = 1; i <= 10; i++) {
        let lamp = {}
        lamp.lampctrboardnum = i
        // lamp.name = `灯控板${i}`
        lamp.name = this.$t('edge.system.lampcontrolpanel') + i

        lamp.channels = []
        for (let j = 1; j <= 4; j++) {
          let channel = {}
          channel.channelnum = j
          channel.lamps = JSON.parse(JSON.stringify(this.lamps))
          lamp.channels.push(channel)
        }
        this.data.push(lamp)
      }
    },
    handleExclusive () {
      // 排他，保证单选
      this.data.forEach(ele => {
        ele.channels.forEach(channel => {
          channel.lamps.forEach(lamp => {
            lamp.selected = false
          })
        })
      })
    },
    initData () {
      this.realTimeChannelTimer = setInterval(() => {
        // if (this.intervalFlag) {
        //   this.initData()
        // }
        this.getRealTimeChannel()
      }, 1000)
      this.getRealTimeChannel()
    },
    clearChannelInterval () {
      if (this.realTimeChannelTimer !== null) {
        clearInterval(this.realTimeChannelTimer) // 清除定时器
        this.realTimeChannelTimer = null
      }
    },
    getRealTimeChannel () {
      getRealTimeChannel().then(data => {
        let res = data.data
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.handleRealTimeChannelData(res.data.data.channellamp)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handleRealTimeChannelData (channelList) {
      this.handleExclusive()
      for (let channel of channelList) {
        let light = channel.light
        if (light === 0) continue
        let id = channel.id
        let num = parseInt(id / 4)
        let index = (id % 4) === 0 ? 4 : (id % 4)
        this.data[num].channels[index - 1].lamps[light - 1].selected = true
      }
    }
  },
  destroyed () {
    this.clearChannelInterval() // 清除定时器
  }
}
</script>

<style lang="scss" scoped>
.channel-panel {
  padding-left: 30px;
  .channels {
    overflow: hidden;
  }
  .controlbtn {
    margin-top: 27px;
  }
}
.twoRow {
  margin-top: 40px;
}
.single-channel {
  width: 148px;
  height: 240px;
  background-color: #858585;
  border-radius: 4px;
  border: solid 1px #dcdcdc;
  float: left;
  margin-right: 20px;
  // .name {
  //   height: 40px;
  //   background: #f8fbff;
  //   line-height: 40px;
  //   text-align: center;
  //   font-family: SourceHanSansCN-Regular;
  //   font-size: 14px;
  //   font-weight: normal;
  //   font-stretch: normal;
  //   letter-spacing: 0px;
  //   color: #333333;
  // }
  .content {
    height: calc(100% - 42px);
    padding: 0 10px;
    .channel {
      display: flex;
      align-items:center;
      justify-content: center;
      height: calc(25%);
    }
    .lamps {
      flex: 1;
      .lamp {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin: 0 auto;
        cursor: pointer;
      }
      .redlamp {
        background-color: #660b00;
        box-shadow: 0px 2px 2px 0px
        rgba(140, 14, 0, 0.32);
      }
      .yellowlamp {
        background-color: #605800;
        box-shadow: 0px 2px 2px 0px
        rgba(126, 127, 27, 0.32);
      }
      .greenlamp {
        background-color: #005e0a;
        box-shadow: 0px 2px 2px 0px
        rgba(9, 96, 0, 0.32);
      }
      .activeRedlamp {
        background-color: #ff1a00;
        box-shadow: 0px 2px 2px 0px
        rgba(140, 14, 0, 0.32);
      }
      .activeYellowlamp {
        background-color: #ffea06;
        box-shadow: 0px 2px 2px 0px
        rgba(126, 127, 27, 0.32);
      }
      .activeGreenlamp {
        background-color: #08eb18;
        box-shadow: 0px 2px 2px 0px
        rgba(9, 96, 0, 0.32);
      }
    }
  }
}
// .list {
//   margin-top: 30px;
//   .title {
//     margin-bottom: 10px;
//   }
// }
 /* 显示横向滚动条 */
 .list .el-table--scrollable-x .el-table__body-wrapper {
   padding: 0 0 5px 0;
   margin: 0 0 5px 0;
   overflow-x: auto;
 }
 /* 滚动条的滑块样式 */
 .list .el-table .el-table__body-wrapper::-webkit-scrollbar-thumb {
   background-color: #ccc !important;
   border-radius: 30px !important;
 }
 /* 去除右侧固定定位阴影多出来的像素 */
 .list .el-table__fixed-right{
   height: calc(100% - 27px) !important;
 }
</style>
<style rel="stylesheet/scss" lang="scss">
</style>
