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
    <div class="main-patternstatus">
      <div class="ring-first" v-for="(list, index1) in patternStatusList" :key="index1">
        <div v-for="(item,index2) in list" :key="index2">
          <div class="first-1" :style="{'width':item.greenWidth,'height':'34px','background':'#7ccc66'}">
            <div class="ring-phase"><xdrdirselector Width="36px" Height="34px" :showlist="item.direction"></xdrdirselector></div>
            <el-tooltip placement="top-start" effect="light">
              <div slot="content">P{{item.id}}:{{item.split}}</div>
              <div style="cursor:pointer;">
                <div class="ring-num">P{{item.id}}:</div>
                <div class="ring-num">{{item.split}}</div>
              </div>
            </el-tooltip>
            <!-- <div class="ring-num">P:{{item.id}}</div>
            <div class="ring-num">S:{{item.split}}</div> -->
          </div>
          <div class="first-1" :style="{'width':item.yellowWidth,'height':'34px','background':'#f9dc6a'}"></div>
          <div class="first-1" :style="{'width':item.redWidth,'height':'34px','background':'#f27979'}"></div>
        </div>
      </div>
      <div v-for="(item, index) in barrierList" :key="index + '1'">
        <div class="divider" :style="{'left':item, 'height':barrierHeight}"></div>
      </div>
      <div class="curTimeDiv" v-show="cycle && cycle > 0" :style="{'left':paddingLeft, 'background-color': '#409EFF'}">{{ timeNumDevide }}</div>
      <div class="curTimeLine" :style="{'left':paddingLeft, 'height':barrierHeight}"></div>
    </div>
</template>
<script>

import xdrdirselector from '@/components/XRDDirSelector'
export default {
  name: 'patternstatus',
  components: {
    xdrdirselector
  },
  data () {
    return {
      barrierHeight: ''
    }
  },
  props: {
    patternStatusList: {
      type: Array
    },
    barrierList: {
      type: Array
    },
    cycle: {
      type: Number
    },
    syncTime: {
      type: Number
    }
  },
  computed: {
    paddingLeft () {
      let res = '0%'
      if (this.cycle && this.cycle > 0) {
        let curPercent = (this.cycle - this.syncTime) / this.cycle
        res = curPercent * 100 + '%'
      }
      return res
    },
    timeNumDevide () {
      let res = (this.cycle - this.syncTime) + '/' + this.cycle
      return res
    }
  },
  watch: {
    patternStatusList: {
      handler: function (val, oldVal) {
        this.handleBarrierHeight() // 计算屏障高度
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    handleBarrierHeight () {
      let patternLength = this.patternStatusList.length
      this.barrierHeight = (patternLength * 35 + 21) + 'px'
    }
  }
}
</script>
<style lang="scss" scoped>
// .main-patternstatus {
//   position: relative;
// }
// .ring-first {
//   margin-top: 1px;
//   width: 100%;
//   height: 34px;
// }
// .first-1 {
//   float: left;
//   // text-align: center;
// }
// .ring-num {
//   float: left;
//   margin-top:7px;
//   margin-left: 5px;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 22px;
//   letter-spacing: 0px;
//   color: #ffffff;
// }
// .ring-phase {
//   float: left;
//   margin-left: 5px;
//   margin-top:1px;
// }
// .divider {
//   position: absolute;
//   top: -10px;
//   // left: 370px;
//   width: 2px;
//   // height: 99px;
//   background-color: #787878;
// }
// .curTimeLine {
//   position: absolute;
//   top: -10px;
//   width: 2px;
//   background-color: #409EFF;
// }
// .curTimeDiv {
//   position: absolute;
//   z-index:50;
//   top: -21px;
//   margin-left: -30px;
//   text-align:center;
//   vertical-align: middle;
//   width: 60px;
//   height: 19px;
//   color:#FFFFFF;
//   background-color: #409EFF;
// }
.main-patternstatus {
  position: relative;
}
.ring-first {
  margin-top: 1px;
  width: 100%;
  height: 34px;
}
.first-1 {
  float: left;
  // text-align: center;
}
</style>
