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
            <div class="lamp" @click="handleClickLamp(item.lampctrboardnum, channel.channelnum, lamp.lampcolor, lamp)"
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
  <el-button class="controlbtn" type="primary" @click="toAutoControl">{{$t('edge.system.recovery')}}</el-button>
</div>
</template>

<script>
// import { putTscControl } from '@/api/control'
import { channelcheck } from '@/api/system'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'channelcontrol',
  components: {},
  props: {
    channelList: {
      type: Array
    }
  },
  watch: {
    channelList: {
      handler: function () {
        this.handleCreateLampCtrboard()
      },
      deep: true
    }
  },
  data () {
    return {
      lampctrboardNum: 10,
      channelNum: 4,
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
      loading: false
    }
  },
  mounted () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.handleCreateLampCtrboard()
  },
  beforeUpdate () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.handleUpdateLampCtrboard()
  },
  methods: {
    handleCreateLampCtrboard () {
      // let channel = this.globalParamModel.getParamsByType('channelList')
      let channel = this.channelList
      let channelIdList = channel.map(ele => ele.id)
      this.data = []
      if (channel.length === 0) return
      for (let i = 1; i <= 10; i++) {
        if (!this.isHasLampCtrboard(i, channelIdList)) continue
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
      console.log(this.data)
    },
    isHasLampCtrboard (i, channelIdList) {
      let index = i * 4
      if (channelIdList.includes(index) || channelIdList.includes(index - 1) || channelIdList.includes(index - 2) || channelIdList.includes(index - 3)) {
        return true
      }
      return false
    },
    handleUpdateLampCtrboard () {
      this.data.forEach((ele, index) => {
        ele.name = this.$t('edge.system.lampcontrolpanel') + Number(index + 1)
      })
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
    handleClickLamp (lampctrboard, channel, lampcolor, lamp) {
      this.handleExclusive()
      lamp.selected = true
      this.curClickedLampInfo = {
        lampctrboard,
        channel,
        lampcolor,
        lamp
      }
      this.handleCheckChannel(this.curClickedLampInfo.lampctrboard, this.curClickedLampInfo.channel, this.curClickedLampInfo.lampcolor, this.curClickedLampInfo.lamp)
    },
    handleCheckChannel (lampctrboard, channel, lampcolor, lamp) {
      const params = {
        lampctrboard,
        channel,
        lampcolor,
        control: 15
      }
      channelcheck(params).then(data => {
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    toAutoControl () {
      this.handleExclusive()
      // let control = {}
      // control.control = 0
      // putTscControl(control).then(data => {
      //   if (!data.data.success) {
      //     this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
      //     return
      //   }
      //   this.$alert(this.$t('edge.channelControl.recoverysuccess'), { type: 'success' })
      // }).catch(error => {
      //   this.$message.error(error)
      //   console.log(error)
      // })
      const params = {
        lampctrboard: 0,
        channel: 0,
        lampcolor: 0,
        control: 0
      }
      channelcheck(params).then(data => {
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$alert(this.$t('edge.channelControl.recoverysuccess'), { type: 'success' })
        this.curClickedLampInfo = undefined
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    }
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
  // width: 148px;
  // height: 240px;
  // background-color: #858585;
  // border-radius: 4px;
  // border: solid 1px #dcdcdc;
  // float: left;
  // margin-right: 20px;
  // .name {
  //   height: 40px;
  //   background: #f8fbff;
  //   line-height: 40px;
  //   text-align: center;
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
</style>
<style rel="stylesheet/scss" lang="scss">
</style>
