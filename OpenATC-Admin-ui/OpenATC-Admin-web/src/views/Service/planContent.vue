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
  <div class="serviceroute-plancontent" v-if="!!chooseId">
    <div class="mask" v-if="maskVisible" @click="clickMask"></div>
    <div class="btnGroup">
      <div class="editbtn" :style="{ zIndex: zIndexObj.editZIndex }">
        <el-popover
          placement="top-start"
          trigger="manual"
          v-model="maskVisible"
          width="290"
        >
          <div class="tipContent">
            <div>
              <i class="iconfont icon-yindaoicon1 handicon"></i>
            </div>
            <div class="text">
              {{ $t("openatc.greenwaveoptimize.createconfig") }}
            </div>
          </div>
          <button
            slot="reference"
            class="btn"
            @click="handleEdit"
            ref="editbtn"
          >
            {{ $t("openatc.common.edit") }}
          </button>
        </el-popover>
      </div>
    </div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <!-- <el-tab-pane :label="$t('openatc.dutyroute.routePreview')" name="first">
      </el-tab-pane> -->
      <el-tab-pane :label="$t('openatc.dutyroute.routeIntersectionList')" name="second">
      </el-tab-pane>
      <PlanChart
        ref="planchart"
        :route="route"
        :tabName="activeName"
        @research="research"
      />
    </el-tabs>
    <SchemeConfiguration
      :visible="configurationVisible"
      :routeData="routeData"
      ref="config"
      @closeDrawer="closeDrawer"
      @research="research"
    />
    <select-control v-show="false" ref="selectControl"></select-control>
  </div>
</template>

<script>
/*eslint-disable*/
import SchemeConfiguration from './components/schemeConfiguration'
import PatternTable from './pattern/patternTable'
import PlanChart from './pattern/planChart/routePreview'
import {
  GetSingleViproute,
  GetViprouteStatus
} from '@/api/service'
import { GetDeviceByIds } from '@/api/device'
import SelectControl from '@/views/Service/components/SelectControl'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'PlanContent',
  components: {
    SchemeConfiguration,
    PatternTable,
    PlanChart,
    SelectControl
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
  data () {
    return {
      activeName: 'second',
      maskVisible: false, // 引导遮罩层是否显示
      configurationVisible: false, // 配置界面是否显示
      messageboxVisible: false, // 关闭界面二期确认弹窗是否显示
      addNum: 0,
      routeData: {}, // 单个协调路线的全部信息
      patternList: [],
      phaseList: [],
      greenwave: [],
      node: {},
      route: {},
      stateList: [],
      devicesData: [],
      deviceIds: []
    }
  },
  watch: {
    chooseId (val) {
      this.setRoute()
    }
  },
  methods: {
    getControlName (control) {
      let res
      res = this.$refs.selectControl.getNameById(control)
      return res
    },
    getSingleViproute () {
      let _this = this
      return new Promise((resolve, reject) => {
        GetSingleViproute(_this.chooseId).then(res => {
          if (!res.data.success) {
            _this.$message.error(getMessageByCode(res.data.code, _this.$i18n.locale))
            return
          }
          _this.routeData = res.data.data
          if (!_this.routeData || _this.routeData.length === 0) {
            _this.maskVisible = true
            // 模拟一次手动点击，触发tip显示
            _this.$refs.editbtn.click()
          }
          resolve(_this.routeData)
        })
      })
    },
    getViprouteStatus () {
      let _this = this
      return new Promise((resolve, reject) => {
        GetViprouteStatus(_this.chooseId).then(res => {
          if (!res.data.success) {
            _this.$message.error(getMessageByCode(res.data.code, _this.$i18n.locale))
            resolve()
          }
          let stateList = res.data.data
          _this.stateList = stateList
          resolve(stateList)
        })
      })
    },
    getDeviceByIds () {
      // 获取设备表格信息
      this.devicesData = []
      let _this = this
      return new Promise((resolve, reject) => {
        _this.deviceIds = _this.routeData.devs.map(ele => ele.agentid)
        GetDeviceByIds(_this.deviceIds).then(res => {
          if (!res.data.success) {
            _this.$message.error(getMessageByCode(res.data.code, _this.$i18n.locale))
            resolve()
          }
          _this.devicesData = res.data.data
          resolve(_this.devicesData)
        })
      })
    },
    async setRoute () {
      if (!this.chooseId || this.chooseId === 0) {
        return false
      }
      await this.getSingleViproute()
      // await this.getViprouteStatus()
      // await this.getDeviceByIds()
      for (let item of this.routeData.devs) {
        // for (let state of this.stateList) {
          // if (item.agentid === state.agentid) {
            // item.state = state.state
            // item.resttime = state.resttime
            // item.currentControl = state.control ? state.control : -1
            item.currentControlName = this.getControlName(item.currentControl)
            let controlName = this.getControlName(item.control)
            item.controlName = controlName
            if (!item.terminalname && item.terminal > 0) {
              item.terminalname = this.$t('openatc.greenwaveoptimize.pattern') + item.terminal
            }
            // for (let dev of this.devicesData) {
            //   if (item.agentid === dev.agentid) {
            //     item.stateName = dev.state
            //     break
            //   }
            // }
            // item.stateName = state.stateName
            // break
          // }
        // }
      }
      if (!this.routeData || this.routeData.devs.length === 0) {
        this.maskVisible = true
        // 模拟一次手动点击，触发tip显示
        this.$refs.editbtn.click()
      }
      this.route = this.routeData
    },
    handleClick (tab, event) {
      this.activeName = tab.name
    },
    checkEdit () {
      let res = true
      for (let item of this.route.devs) {
        if (item.state === 1) {
          res = false
          this.$message.warning(this.$t('openatc.dutyroute.notAllowedToEdit'))
          break
        }
      }
      return res
    },
    handleEdit () {
      if (!this.checkEdit()) {
        return false
      }
      if (this.maskVisible && this.addNum <= 1) {
        if (this.addNum === 1) {
          this.maskVisible = false
        }
        this.addNum++
      } else {
        if (this.maskVisible) {
          this.addNum = 1
        }
      }
      if (!this.maskVisible) {
        // 获取单个协调路线的全部信息
        // (弹窗取消后，应重新获取静态数据，此处采取中间变量法重新赋值，以触发watch能监听到routeData变化，给弹窗内共有变量configData重新赋值)
        const lastRouteData = JSON.parse(JSON.stringify(this.routeData))
        this.routeData = []
        this.routeData = lastRouteData
        this.configurationVisible = true
        let _this = this
        this.$nextTick(() => {
          _this.$refs.config.setDeviceData()
        })
      }
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
    handleMaskVisible () {
      // 数据为空，显示蒙层
      if (this.patternList && !this.patternList.length && this.step) {
        this.maskVisible = true
        // 模拟一次手动点击，触发tip显示
        this.$refs.editbtn.click()
      }
    },
    research () {
      this.setRoute()
    }
  }
}
</script>
