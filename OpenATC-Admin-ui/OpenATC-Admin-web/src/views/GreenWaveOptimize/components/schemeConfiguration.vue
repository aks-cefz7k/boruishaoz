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
<div v-if="configurationVisible" class="configDrawer">
  <Messagebox :visible="messageboxVisible" :text="$t('openatc.greenwaveoptimize.isClose')" @cancle="cancle" @ok="ok"/>
  <el-drawer
    :title="$t('openatc.greenwaveoptimize.coordinateparamconfig')"
    size="41%"
    :visible.sync="configurationVisible"
    direction="rtl"
    :wrapperClosable="false"
    :destroy-on-close="true"
    :before-close="handleClose">
    <el-tabs v-model="activeTab" type="card" @tab-click="handleClickTab">
      <el-tab-pane :label="$t('openatc.greenwaveoptimize.device')" name="device">
        <DevicePanel :devicesData="devicesData"
          @addDevice="addDevice"
          @deleteDevice="deleteDevice"
          @handleSortRow="handleSortRow" />
      </el-tab-pane>
      <el-tab-pane :label="$t('openatc.greenwaveoptimize.phase')" name="phase">
        <PhasePanel :phaseData="phaseData" @changeData="changePhase"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('openatc.greenwaveoptimize.distance')" name="distance">
        <DistancePanel :distanceData="distanceData" @changeData="changeData"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('openatc.greenwaveoptimize.pattern')" name="pattern">
        <PatternPanel :patternData="patternData" @changeData="changePattern"/>
      </el-tab-pane>
    </el-tabs>
    <div class="btnGroup">
      <el-button class="btn" @click="handleClose">{{$t('openatc.button.Cancel')}}</el-button>
      <el-button class="btn" type="primary" @click="onOk">{{$t('openatc.button.OK')}}</el-button>
    </div>
  </el-drawer>
</div>
</template>

<script>
import Messagebox from '../../../components/MessageBox/index'
import DevicePanel from '../tables/device'
import DistancePanel from '../tables/distance'
import PatternPanel from '../tables/pattern'
import PhasePanel from '../tables/phase'
import { GetDeviceByIds } from '@/api/device'
import { UpdateRoute } from '@/api/route'
export default {
  name: 'schemeConfiguration',
  data () {
    return {
      configurationVisible: false, // 配置界面是否显示
      activeTab: 'device',
      messageboxVisible: false, // 关闭界面二期确认弹窗是否显示
      configData: undefined, // 内部可修改的配置数据
      deviceIds: [], // 初始协调线路包含的设备id
      devicesData: [], // 设备面板数据
      distanceData: [], // 距离面板数据
      patternData: [], // 方案面板数据
      phaseData: [], // 相位面板数据
      defaultScheme: { // 默认协调方案配置参数
        distance: 0,
        width: 0,
        forwardphaseid: 0,
        forwardphasedirection: [],
        backphaseid: 0,
        backphasedirection: [],
        sortid: 0,
        patternid: 0,
        patterndes: ''
      }
    }
  },
  components: {
    Messagebox,
    DevicePanel,
    DistancePanel,
    PatternPanel,
    PhasePanel
  },
  watch: {
    visible: {
      handler: function (val) {
        this.configurationVisible = val
      },
      deep: true
    },
    routeData: {
      handler: function (val) {
        this.configData = JSON.parse(JSON.stringify(val))
        this.getDeviceByIds()
      },
      deep: true
    }
  },
  props: {
    visible: {
      type: Boolean
    },
    routeData: {
      type: Object
    }
    // phaseList: {
    //   type: Array
    // }
  },
  methods: {
    handleClose () {
      this.messageboxVisible = true
    },
    reset () {
      this.activeTab = 'device'
    },
    handleClickTab (tab, event) {
      switch (tab.name) {
        case 'device': this.getDeviceByIds()
          break
        case 'phase': this.getPhase()
          break
        case 'distance': this.getDistance()
          break
        case 'pattern': this.getPattern()
          break
      }
    },
    checkRules (devs) {
      for (let inter of devs) {
        if (inter.forwardphaseid === 0 || inter.backphaseid === 0) {
          this.$message.error(this.$t('openatc.greenwaveoptimize.choosephase'))
          return true
        }
        if (inter.patternid === 0) {
          this.$message.error(this.$t('openatc.greenwaveoptimize.choosepattern'))
          return true
        }
      }
      return false
    },
    onOk () {
      if (this.checkRules(this.configData.devs)) { // 检验方案和相位是否为空
        return
      }
      this.configData.devs.forEach(ele => {
        // if (ele.agentid) {
        //   delete ele.agentid
        // }
        if (ele.allPatterns) {
          delete ele.allPatterns
        }
        if (ele.allForwardPhase) {
          delete ele.allForwardPhase
        }
        if (ele.allBackPhase) {
          delete ele.allBackPhase
        }
        if (ele.forwardshowlist) {
          delete ele.forwardshowlist
        }
        if (ele.backshowlist) {
          delete ele.backshowlist
        }
      })
      // 传sordid排序
      this.configData.devs = this.configData.devs.map((ele, index) => {
        return {
          ...ele,
          sortid: index + 1
        }
      })
      UpdateRoute(this.configData).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.$message({
          message: this.$t('openatc.greenwaveoptimize.configsuccess'),
          type: 'success',
          duration: 1500
        })
        this.reset()
        // 关闭抽屉，重新刷新右侧数据（即重刷planContent组件）
        this.$emit('closeDrawer', 'refresh')
      })
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      this.messageboxVisible = false
      this.reset()
      // 只关闭抽屉，不重刷组件
      this.$emit('closeDrawer')
    },
    getDeviceByIds () {
      // 获取设备表格信息
      this.deviceIds = this.configData.devs.map(ele => ele.agentid)
      GetDeviceByIds(this.deviceIds).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.devicesData = res.data.data
      })
    },
    getDistance () {
      // 获取距离表格信息
      this.distanceData = this.configData.devs.map(ele => ({
        agentid: ele.agentid,
        distance: ele.distance,
        width: ele.width
      }))
    },
    getPattern () {
      // 获取方案表格信息
      this.patternData = this.configData.devs.map(ele => ({
        agentid: ele.agentid,
        patterndes: ele.patterndes,
        patternid: ele.patternid,
        allPatterns: ele.allPatterns || []
      }))
    },
    getPhase () {
      // 获取相位表格信息
      this.phaseData = this.configData.devs.map(ele => ({
        agentid: ele.agentid,
        forwardphaseid: ele.forwardphaseid,
        forwardphasedirection: ele.forwardphasedirection,
        // forwardphasedirection: this.getPhasedirection(ele.agentid, ele.forwardphaseid),
        allForwardPhase: ele.allForwardPhase || [],
        backphaseid: ele.backphaseid,
        backphasedirection: ele.backphasedirection,
        // backphasedirection: this.getPhasedirection(ele.agentid, ele.backphaseid),
        allBackPhase: ele.allBackPhase || []
      }))
    },
    // getPhasedirection (agentid, phaseid) {
    //   let allPhase = this.phaseList.filter(ele => ele.agentid === agentid)
    //   let phaseList = allPhase[0].feature.phaseList
    //   let currPhase = phaseList.filter(ele => ele.id === phaseid)
    //   return currPhase[0].direction
    // },
    addDevice (devices) {
      // 添加多个设备
      for (let dev of devices) {
        if (this.deviceIds.includes(dev.agentid)) continue
        this.configData.devs.push(JSON.parse(JSON.stringify({
          ...this.defaultScheme,
          routeid: this.routeData.id,
          agentid: dev.agentid
        })))
      }
      this.getDeviceByIds()
    },
    deleteDevice (agentid) {
      // 删除设备
      this.configData.devs = this.configData.devs.filter(ele => ele.agentid !== agentid)
      this.getDeviceByIds()
    },
    changePhase (changedField) {
      // 修改上行/下行相位
      this.changeData(changedField)
      this.getPhase()
    },
    changePattern (changedField) {
      // 修改方案
      this.changeData(changedField)
      this.getPattern()
    },
    changeData (changedField) {
      // 对应字段修改内部维护的配置数据
      this.configData.devs.forEach((ele, index) => {
        if (ele.agentid === changedField.agentid) {
          this.configData.devs[index] = JSON.parse(JSON.stringify({
            ...ele,
            ...changedField
          }))
        }
      })
    },
    handleSortRow (sortAgentid) {
      // sortId是排序规则
      this.configData.devs.sort(function (a, b) {
        return sortAgentid.indexOf(a.agentid) - sortAgentid.indexOf(b.agentid)
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.configDrawer .el-drawer__header {
  text-align: left;
  color: #666666;
}
.configDrawer .el-drawer__body {
  padding: 0 16px 30px 16px;
}
</style>
<style lang="scss" rel="stylesheet/scss" scoped>
.configDrawer {
  position: relative;
  .btnGroup {
    position: absolute;
    bottom: 30px;
    left: 0;
    width: 100%;
    padding: 0 16px;
    display: flex;
    align-items:center;
    .btn {
      width: 50%;
    }
  }
}
</style>
