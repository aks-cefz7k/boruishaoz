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
<div class="planContent" v-if="!!chooseId">
  <div class="mask" v-if="maskVisible" @click ="clickMask"></div>
  <div class="btnGroup">
    <!-- <el-button class="btn" type="primary" @click="Download">下载</el-button> -->
    <el-button class="btn" type="primary" @click="handleCreate">{{$t('openatc.greenwaveoptimize.generate')}}</el-button>
    <div class="editbtn" :style="{zIndex: zIndexObj.editZIndex}">
      <el-popover placement="top-start" trigger="manual" v-model="maskVisible" width="290">
        <div class="tipContent">
          <div>
            <i class="iconfont icon-yindaoicon1 handicon"></i>
          </div>
          <div class="text">{{$t('openatc.greenwaveoptimize.createconfig')}}</div>
        </div>
        <button slot="reference" class="btn" @click="handleEdit" ref="editbtn">{{$t('openatc.greenwaveoptimize.edit')}}</button>
      </el-popover>
    </div>
  </div>
  <SchemeConfiguration
    :visible="configurationVisible"
    :routeData="routeData"
    @closeDrawer="closeDrawer" />
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane :label="$t('openatc.greenwaveoptimize.timespace')" name="first">
      <PlanChart ref="planchart"/>
    </el-tab-pane>
    <el-tab-pane :label="$t('openatc.greenwaveoptimize.pattern')" name="second">
      <PatternTable ref="patterntable"/>
    </el-tab-pane>
  </el-tabs>
  <Messagebox :visible="messageboxVisible" :text="$t('openatc.greenwaveoptimize.confirmdownloadpattern')" @cancle="cancle" @ok="ok"/>
</div>
</template>

<script>
import SchemeConfiguration from './components/schemeConfiguration'
import PatternTable from './pattern/patternTable'
import PlanChart from './pattern/planChart/index'
import { GetRouteInfo, getAllPatternOfRoute, getAllPhaseOfRoute, generateRoute, putAllPatternOfRoute } from '@/api/route'
import Messagebox from '../../components/MessageBox/index'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'PlanContent',
  data () {
    return {
      activeName: 'first',
      maskVisible: false, // 引导遮罩层是否显示
      configurationVisible: false, // 配置界面是否显示
      messageboxVisible: false, // 关闭界面二期确认弹窗是否显示
      addNum: 0,
      mock: [],
      routeData: {}, // 单个协调路线的全部信息
      patternList: [],
      phaseList: [],
      greenwave: []
    }
  },
  components: {
    SchemeConfiguration,
    PatternTable,
    PlanChart,
    Messagebox
  },
  props: {
    schemeData: {
      type: Array
    },
    step: {
      type: Boolean
    },
    zIndexObj: {
      type: Object
    },
    chooseId: {
      type: Number
    }
  },
  watch: {
    schemeData: {
      handler: function (val) {
        if (val && !val.length && this.step) {
          this.maskVisible = true
          // 模拟一次手动点击，触发tip显示
          this.$refs.editbtn.click()
        }
      },
      deep: true
    }
  },
  methods: {
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      this.messageboxVisible = false
      this.handleDownload()
    },
    Download () {
      this.messageboxVisible = true
    },
    handleClick (tab, event) {
      if (tab.name === 'first') {
        let patterntable = this.$refs.patterntable
        let newPatternList = patterntable.newPatternList
        for (let pattern of this.patternList) {
          let agentid = pattern.agentid
          let newPattern = newPatternList.filter(npl => npl.agentid === agentid)[0]
          let patternId = newPattern.patternId
          let currPatternList = pattern.feature.patternList
          let currPattern
          if (Array.isArray(currPatternList)) {
            currPattern = currPatternList.filter(cpl => cpl.id === patternId)[0]
          } else {
            currPattern = currPatternList
          }
          currPattern.cycle = newPattern.cycle
          currPattern.offset = newPattern.offset
        }
        this.handlePlanChart()
      }
    },
    handleEdit () {
      if (this.maskVisible && this.addNum <= 1) {
        if (this.addNum === 1) {
          this.maskVisible = false
        }
        this.addNum++
      }
      if (!this.maskVisible) {
        // 获取单个协调路线的全部信息
        // (弹窗取消后，应重新获取静态数据，此处采取中间变量法重新赋值，以触发watch能监听到routeData变化，给弹窗内共有变量configData重新赋值)
        const lastRouteData = JSON.parse(JSON.stringify(this.routeData))
        this.routeData = []
        this.routeData = lastRouteData
        this.configurationVisible = true
      }
    },
    getRouteInfo () {
      if (this.chooseId === 0) return
      GetRouteInfo(this.chooseId).then(res => {
        if (!res.data.success) {
          if (res.data.code === '4003') {
            let agentid = res.data.data.agentid
            this.$message.error(this.$t('openatc.greenwaveoptimize.device') + agentid + this.$t('openatc.greenwaveoptimize.notonline'))
            return
          }
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.routeData = res.data.data
        this.getAllPatternOfRoute() // 获取该路线的所有方案
      })
    },
    handleDownload () {
      let patterntable = this.$refs.patterntable
      let newPatternList = patterntable.newPatternList
      for (let pattern of this.patternList) {
        let agentid = pattern.agentid
        let newPattern = newPatternList.filter(npl => npl.agentid === agentid)[0]
        let patternId = newPattern.id
        let currPatternList = pattern.feature.patternList
        let currPattern
        if (Array.isArray(currPatternList)) {
          currPattern = currPatternList.filter(cpl => cpl.id === patternId)[0]
        } else {
          currPattern = currPatternList
        }
        currPattern.cycle = newPattern.cycle
        currPattern.offset = newPattern.offset
      }
      let param = {}
      param.devs = this.patternList
      putAllPatternOfRoute(param).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.$alert(this.$t('openatc.greenwaveoptimize.planissuedsucc'), { type: 'success' })
      })
    },
    handleCreate () {
      let param = this.routeData
      let planchart = this.$refs.planchart
      let patterntable = this.$refs.patterntable
      let newPatternList = patterntable.newPatternList
      param.keyintsid = planchart.keyintsid
      param.upspeed = Number(planchart.upspeed)
      param.downspeed = Number(planchart.downspeed)
      param.direction = planchart.direction
      let devs = param.devs
      for (let inter of devs) {
        let feature = {}
        let agentid = inter.agentid
        // 获取patternList
        for (let newPattern of newPatternList) {
          if (newPattern.agentid === agentid) {
            feature.patternList = [newPattern]
          }
        }
        // 获取phaseList
        for (let phase of this.phaseList) {
          if (phase.agentid === agentid) {
            let tempPhaseList = []
            let list = phase.feature.phaseList
            for (let ls of list) {
              let obj = {}
              obj.id = ls.id
              obj.concurrent = ls.concurrent
              tempPhaseList.push(obj)
            }
            feature.phaseList = tempPhaseList
          }
        }
        inter.feature = feature
      }
      generateRoute(param).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.greenwave = res.data.data.greenwave
        this.patternList = res.data.data.devs
        this.handlePlanChart()
        this.handlePatternTable()
      })
    },
    closeDrawer (isRefresh) {
      this.configurationVisible = false
      if (isRefresh === 'refresh') {
        this.$emit('resetContentComponent')
      }
    },
    clickMask () {
      this.maskVisible = false
    },
    getAllPatternOfRoute () {
      if (this.chooseId === 0) return
      getAllPatternOfRoute(this.chooseId).then(res => {
        if (!res.data.success) {
          if (res.data.code === '4003') {
            let agentid = res.data.data.agentid
            this.$message.error(this.$t('openatc.greenwaveoptimize.device') + agentid + this.$t('openatc.greenwaveoptimize.notonline'))
            return
          }
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.patternList = res.data.data.devs
        this.handlePlanChart()
        this.getAllPhaseOfRouter() // 获取改路线的所有相位
      })
    },
    getAllPhaseOfRouter () {
      if (this.chooseId === 0) return
      getAllPhaseOfRoute(this.chooseId).then(res => {
        if (!res.data.success) {
          if (res.data.code === '4003') {
            let agentid = res.data.data.agentid
            this.$message.error(this.$t('openatc.greenwaveoptimize.device') + agentid + this.$t('openatc.greenwaveoptimize.notonline'))
            return
          }
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.phaseList = res.data.data.devs
        this.handlePatternTable()
        this.handleMaskVisible()
      })
    },
    handlePatternTable () {
      let patterntable = this.$refs.patterntable
      patterntable.onPatternTable(this.routeData, this.patternList, this.phaseList)
    },
    handlePlanChart () {
      let planchart = this.$refs.planchart
      planchart.onPlanChart(this.routeData, this.patternList, this.greenwave)
    },
    handleMaskVisible () {
      // 数据为空，显示蒙层
      if (this.patternList && !this.patternList.length && this.step) {
        this.maskVisible = true
        // 模拟一次手动点击，触发tip显示
        this.$refs.editbtn.click()
      }
    }
  },
  mounted () {
    this.getRouteInfo()
  }
}
</script>

<style lang="scss" scoped>
//  @import "../../styles/theme/element-variables.scss";
// .planContent {
//   position: relative;
// }
// .btnGroup {
//   overflow: hidden;
//   float: right;
//   width: 150px;
//   /* width: 230px; */
// }
// /* 蒙层样式 */
// .mask {
//   position: fixed;
//   left: 0;
//   top: 0;
//   width: 100%;
//   height: 100%;
//   background: #000;
//   opacity: 0.5;
//   z-index: 98;
// }
// /* 原生按钮样式修改 */
// .btnGroup button {
//     width: 70px;
//     height: 40px;
//     padding: 0;
//     line-height: 40px;
//     border: 1px solid #dcdfe6;
//     white-space: nowrap;
//     outline: 0;
//     transition: .1s;
//     font-size: 14px;
//     border-radius: 4px;
//     color: $--button-primary-font-color;
//     background-color: $--color-primary;
//     border-color: $--color-primary;
//     cursor: pointer;
// }
// .btnGroup button:hover {
//     color: $--button-primary-font-color;
//     background-color: $--color-primary;
//     border-color: $--color-primary;
//     opacity: 0.8;
// }
// .btnGroup > button {
//   float: left;
//   position: relative;
//   z-index: 10;
// }
// .btnGroup button {
//   height: 32px;
//   line-height: 32px;
// }
// .btnGroup .editbtn{
//     float: left;
//     position: relative;
//     /* z-index: 98; */
//     margin-left: 10px;
// }
// .btnGroup .editbtn button {
//   font-weight: 500;
// }
// /* 提示框相关 */
// .tipContent > div{
//     float: left;
// }
// .tipContent div i {
//   color: $--color-primary;
//   font-size: 30px;
// }
// .tipContent .text {
//     font-size: 14px;
//     font-weight: normal;
//     font-stretch: normal;
//     line-height: 14px;
//     letter-spacing: 1px;
//     color: $--color-primary;
//     margin-left: 7px;
//     line-height: 42px;
// }.planContent {
//   position: relative;
// }
// .btnGroup {
//   overflow: hidden;
//   float: right;
//   width: 150px;
//   /* width: 230px; */
// }
// /* 蒙层样式 */
// .mask {
//   position: fixed;
//   left: 0;
//   top: 0;
//   width: 100%;
//   height: 100%;
//   background: #000;
//   opacity: 0.5;
//   z-index: 98;
// }
// /* 原生按钮样式修改 */
// .btnGroup button {
//     width: 70px;
//     height: 40px;
//     padding: 0;
//     line-height: 40px;
//     border: 1px solid #dcdfe6;
//     white-space: nowrap;
//     outline: 0;
//     transition: .1s;
//     font-size: 14px;
//     border-radius: 4px;
//     color: $--button-primary-font-color;
//     background-color: $--color-primary;
//     border-color: $--color-primary;
//     cursor: pointer;
// }
// // .btnGroup button:hover {
// //     color: $--color-white;
// //     background-color: #66b1ff;
// //     border-color: #66b1ff;
// // }
// .btnGroup > button {
//   float: left;
//   position: relative;
//   z-index: 10;
// }
// .btnGroup button {
//   height: 32px;
//   line-height: 32px;
// }
// .btnGroup .editbtn{
//     float: left;
//     position: relative;
//     /* z-index: 98; */
//     margin-left: 10px;
// }
// .btnGroup .editbtn button {
//   font-weight: 500;
// }
// /* 提示框相关 */
// .tipContent > div{
//     float: left;
// }
// .tipContent div i {
//   color: $--color-primary;
//   font-size: 30px;
// }
// .tipContent .text {
//     font-size: 14px;
//     font-weight: normal;
//     font-stretch: normal;
//     line-height: 14px;
//     letter-spacing: 1px;
//     color: $--color-primary;
//     margin-left: 7px;
//     line-height: 42px;
// }
</style>
