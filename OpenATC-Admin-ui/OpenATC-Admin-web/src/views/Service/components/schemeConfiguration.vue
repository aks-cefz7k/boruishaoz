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
  <div v-show="configurationVisible" class="configDrawer">
    <el-drawer
      :title="$t('openatc.dutyroute.dutyrouteconfig')"
      size="45%"
      :visible.sync="configurationVisible"
      direction="rtl"
      :wrapperClosable="false"
      :destroy-on-close="true"
      :before-close="handleClose"
      @opened="onDrawerOpend"
    >
      <div style="display:flex;flex-direction:row;justify-content:flex-end;">
        <el-button class="addbtn" type="primary" @click="handleAdd">{{
          $t("openatc.greenwaveoptimize.adddevice")
        }}</el-button>
      </div>
      <PatternPanel :patternData="patternData"
                    ref="patternPanel"
                    @addDevice="addDevice"
                    @deleteDevice="deleteDevice"/>
      <div class="btnGroup">
        <el-button class="btn" @click="handleClose">{{
          $t("openatc.button.Cancel")
        }}</el-button>
        <el-button class="btn" type="primary" @click="onOk">{{
          $t("openatc.button.OK")
        }}</el-button>
      </div>
    </el-drawer>
    <Messagebox
      :visible="messageboxVisible"
      :text="$t('openatc.greenwaveoptimize.isClose')"
      @cancle="cancle"
      @ok="ok"
    />
    <ChoseIntersection ref="choseIntersection" @onSureClick="onSureClick"></ChoseIntersection>
  </div>
</template>

<script>
import Messagebox from '../../../components/MessageBox/index'
import DevicePanel from '../tables/device'
import PatternPanel from '../tables/pattern'
import { getMessageByCode } from '@/utils/responseMessage'
import ChoseIntersection from '@/components/ChoseIntersection'
import {
  UpdateViproute
} from '@/api/service'
export default {
  name: 'schemeConfiguration',
  components: {
    Messagebox,
    DevicePanel,
    PatternPanel,
    ChoseIntersection
  },
  props: {
    visible: {
      type: Boolean
    },
    routeData: {
      type: Object
    }
  },
  data () {
    return {
      configurationVisible: false, // 配置界面是否显示
      activeTab: 'pattern',
      messageboxVisible: false, // 关闭界面二期确认弹窗是否显示
      configData: undefined, // 内部可修改的配置数据
      deviceIds: [], // 初始协调线路包含的设备id
      devicesData: [], // 设备面板数据
      patternData: [], // 方案面板数据
      defaultScheme: { // 默认协调方案配置参数
        sortid: 0,
        patternid: 0,
        patterndes: ''
      }
    }
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
        this.devicesData = []
        this.patternData = []
        this.configData = JSON.parse(JSON.stringify(val))
      },
      deep: true
    }
  },
  methods: {
    onDrawerOpend () {
      this.getPattern()
    },
    handleClose () {
      this.messageboxVisible = true
    },
    reset () {
      this.activeTab = 'pattern'
    },
    checkRules (reqData) {
      // let devs = reqData.devs
      // for (let inter of devs) {
      //   if (!inter.terminal || inter.terminal === 0) {
      //     this.$message.error(this.$t('openatc.greenwaveoptimize.choosepattern'))
      //     return true
      //   }
      // }
      return false
    },
    onOk () {
      let reqData = this.getReqData()
      if (this.checkRules(reqData)) { // 检验方案和相位是否为空
        return
      }
      UpdateViproute(reqData).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.$message({
          message: this.$t('openatc.common.operationsuccess'),
          type: 'success',
          duration: 1500
        })
        this.reset()
        // 关闭抽屉，重新刷新右侧数据（即重刷planContent组件）
        this.$emit('closeDrawer', 'refresh')
        this.$emit('research')
      })
    },
    getReqData () {
      let res = {
        'id': this.configData.id,
        'name': this.configData.name,
        'devs': []
      }
      // 合并数据
      let patternPanel = this.$refs.patternPanel
      let patternTableData = patternPanel.patternTableData
      for (let item of this.devicesData) {
        let agentid = item.agentid
        for (let pattern of patternTableData) {
          let pAgentid = pattern.agentid
          if (agentid === pAgentid) {
            item.patternid = pattern.patternid
            item.patterndes = pattern.patterndes
            item.patterndesc = pattern.patterndesc
            item.control = pattern.control
            item.state = pattern.state
            item.totaltime = pattern.totaltime
            item.greenflash = pattern.greenflash
            item.duration = pattern.duration
            item.yellow = pattern.yellow
            item.redclear = pattern.redclear
            item.mingreen = pattern.mingreen
            item.phases = pattern.phases
            break
          }
        }
      }
      let devs = []
      for (let dev of this.devicesData) {
        let item = {
          'id': dev.id,
          'viprouteid': this.configData.id,
          'agentid': dev.agentid,
          'name': dev.name,
          'geometry': {
            'type': 'Point',
            'coordinates': [
              0,
              0
            ]
          },
          'location': [
            0,
            0
          ],
          'control': dev.control,
          'terminal': dev.patternid,
          'terminalname': dev.patterndes,
          'value': dev.state,
          'totaltime': dev.totaltime
        }
        item.greenflash = dev.greenflash
        item.duration = dev.duration
        item.yellow = dev.yellow
        item.redclear = dev.redclear
        item.mingreen = dev.mingreen
        item.phases = dev.phases
        let geometry = dev.geometry
        let location = dev.location
        if (geometry && geometry.coordinates && geometry.coordinates.length > 0) {
          item.geometry = geometry
        }
        if (location && location.length > 0) {
          item.location = location
        }
        devs.push(item)
      }
      res.devs = devs
      return res
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
    setDeviceData () {
      // 获取设备表格信息
      if (this.devicesData.length === 0 && this.configData.devs.length > 0) {
        this.devicesData = [...this.configData.devs]
      }
    },
    getPattern () {
      // 获取方案表格信息
      this.patternData = this.devicesData.map(ele => ({
        isNew: ele.isNew, // 区分新增
        agentid: ele.agentid,
        name: ele.name,
        patterndes: ele.terminalname,
        patternid: ele.terminal,
        state: ele.value,
        control: ele.control,
        totaltime: ele.totaltime,
        greenflash: ele.greenflash,
        duration: ele.duration,
        yellow: ele.yellow,
        redclear: ele.redclear,
        mingreen: ele.mingreen,
        phases: ele.phases
      }))
    },
    addDevice (devices) {
      // 添加多个设备
      for (let dev of devices) {
        let isIn = false
        for (let item of this.devicesData) {
          if (item.agentid === dev.agentid) {
            isIn = true
            break
          }
        }
        if (!isIn) {
          dev.isNew = true
          this.devicesData.push(dev)
        }
      }
    },
    deleteDevice (agentid) {
      // 删除设备
      let index = -1
      for (let i = 0; i < this.devicesData.length; i++) {
        let item = this.devicesData[i]
        if (item.agentid === agentid) {
          index = i
          break
        }
      }
      if (index !== -1) {
        this.devicesData.splice(index, 1)
        // this.patternData = [] // 强制清空数据销毁table dom
        this.getPattern()
      }
    },
    onSureClick (list) {
      this.addDevice(list)
      this.getPattern()
    },
    handleAdd () {
      let choseIntersection = this.$refs.choseIntersection
      choseIntersection.show()
    }
  }
}
</script>

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
    align-items: center;
    .btn {
      width: 50%;
    }
  }
}
</style>
