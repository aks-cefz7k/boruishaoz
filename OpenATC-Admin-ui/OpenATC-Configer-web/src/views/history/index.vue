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
  <div class="app-container">
    <el-button type="primary" @click="getAllFault" size="small" style="margin-bottom: 10px;">{{$t('edge.fault.refresh')}}</el-button>
    <el-button type="primary" size="small" @click="leadingOutFault" style="margin-bottom: 10px;">{{$t('edge.fault.export')}}</el-button>
    <el-button class="detail-fault" type="primary" size="small" @click="showLedDetailFault" style="" v-show="activeName === '2'">{{$t('edge.fault.faultofcurrentdetailedlightgroup')}}</el-button>
    <el-button class="detail-fault" type="primary" size="small" @click="showVehDetDetailFault" style="" v-show="activeName === '3'">{{$t('edge.fault.faultofcurrentdetailedvehicleinspectionversion')}}</el-button>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane v-for="(item, index) in tabList" :key="index" :label="$t('edge.fault.tab' + item.value)" :name="item.value">
      <boardTable ref="boardtable" :activeName="activeName" :tableData="tableData" @getAllFault="getAllFault"></boardTable>
    </el-tab-pane>
  </el-tabs>
  <detailFault ref="detailfault" :dialogDetailFault="dialogDetailFault"></detailFault>
  </div>
</template>

<script>
import boardTable from './table/index'
import { getFault } from '@/api/fault'
import detailFault from './dialog/index'
export default {
  name: 'history',
  components: { boardTable, detailFault },
  data () {
    return {
      activeName: '0',
      tabList: [{
        label: '总览',
        value: '0'
      }, {
        label: '主控板',
        value: '1'
      }, {
        label: '灯控板',
        value: '2'
      }, {
        label: '车检板',
        value: '3'
      }, {
        label: 'I/O板',
        value: '4'
      }],
      listLoading: false,
      allFault: [],
      tableData: [],
      dialogDetailFault: [],
      faultCodeMap: new Map([[101, 'can总线通信故障'], [102, '黄闪器故障'], [103, '特征参数故障'], [104, '故障检测板不在线'], [105, '继电器未吸合'], [201, '灯控板ID故障'], [202, '灯控板脱机'], [203, '无红灯亮起'], [204, '红绿同亮'], [205, '绿冲突'], [206, '红灯灯电压故障'], [207, '黄灯灯电压故障'], [208, '绿灯灯电压故障'], [209, '红灯灯功率故障'], [210, '黄灯灯功率故障'], [211, '绿灯灯功率故障'], [212, '灯组故障'], [213, '车检器故障'], [214, '灯控板插槽编码错误'], [215, '灯控板插头编码错误'], [216, '本机灯控板数量未配置'], [301, '车检板未初始化'], [302, '车检板脱机'], [303, '车辆检测器短路'], [304, '车辆检测器断路'], [401, 'I/O板未初始化'], [402, 'I/O板脱机']]),
      faultCodeMapEn: new Map([[101, 'CanBus Fault'], [102, 'Yellow Flasher Fault'], [103, 'TZParam Fault'], [104, 'FaultDet Offline'], [105, 'Relay Not Work'], [201, 'LampBoard ID Fault'], [202, 'LampBoard Offline'], [203, 'No Red Lamp Is On'], [204, 'Red And Green Conflict'], [205, 'Green Conflict'], [206, 'Red Lamp Voltage Fault'], [207, 'Yellow Lamp Voltage Fault'], [208, 'Green Lamp Voltage Fault'], [209, 'Red Lamp Lamp Power Fault'], [210, 'Yellow Lamp Lamp Power Fault'], [211, 'Green Lamp Lamp Power Fault'], [212, 'Lamp pack failure'], [213, 'Car detector failure'], [214, 'Lamp Control Board Slot Code Error'], [215, 'Code Error Of Lamp Control Board Plug'], [216, 'The Number Of Lamp Control Board Not be Configed for The Master'], [301, 'VehDetBoard Is Not Init'], [302, 'VehDetBoard Is Offline'], [303, 'VehDetector Short Circiut'], [304, 'VehDetector Open  Circiut'], [401, 'I/O Board Is Not Init'], [402, 'I/O Board Offline']]),
      faultLevelMap: new Map([[1, '一般故障'], [2, '降级故障'], [3, '严重故障']]),
      faultLevelMapEn: new Map([[1, 'General failure'], [2, 'Degradation failure'], [3, 'Serious failure']]),
      TZParamSubtypeMap: new Map([[0, ''], [1, '特征参数不存在'], [2, '特征参数文件不可读'], [3, '特征参数人为修改'], [4, '特征参数文件打开失败'], [5, '特征参数文件更新失败'], [6, '信号机地址码校验失败'], [7, '特征参数内容格式错误'], [8, 'USB挂载失败']]),
      TZParamSubtypeMapEn: new Map([[0, ''], [1, 'Non-existent'], [2, 'File Is Unreadable'], [3, 'File Artificial Changes'], [4, 'File Open Fail'], [5, 'File Update Fail'], [6, 'File Check SiteID Fail'], [7, 'Format Error'], [8, 'USB Mount Fail']]),
      greenLampSubtypeMap: new Map([[0, ''], [1, '未输出有效电压'], [2, '输出电压低于输入电压过多'], [3, '输出电压高于输入电压'], [4, '关闭输出但实际电压仍然输出'], [5, '关闭输出但实际电压部分输出'], [6, '线路残留电压过高']]),
      greenLampSubtypeMapEn: new Map([[0, ''], [1, 'Output Volatage Is Fail'], [2, 'Output Volatage Is Low'], [3, 'Output Volatage Is High'], [4, 'Off Output Volatage Is high'], [5, 'Off Output Volatage Is low'], [6, 'Residual Voltage Is Over-High']]),
      lampPowerSubtypeMap: new Map([[0, ''], [1, '功率异常增加'], [2, '功率异常减少'], [3, '功率无输出'], [4, '关闭状态有功率输出']]),
      lampPowerSubtypeMapEn: new Map([[0, ''], [1, 'Output Power Is Up'], [2, 'Output Power Is Down'], [3, 'Output Power Is Zero'], [4, 'Off Output Power Is High']])
    }
  },
  created () {
    this.getAllFault()
  },
  methods: {
    getAllFault () {
      this.listLoading = true
      // let faultType = Number(this.activeName)
      getFault(0).then(data => {
        if (data.data.success !== true) {
          this.listLoading = false
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        this.listLoading = false
        this.allFault = this.formateDateForAllFault(data.data.data.data.m_FaultDeque)
        this.handlerFaultData()
      })
    },
    handlerFaultData () {
      if (this.activeName === '0') {
        this.tableData = this.allFault
        return
      }
      let filterId = Number(this.activeName)
      this.tableData = this.allFault.filter(function (obj) {
        return obj.m_byFaultBoardType === filterId
      })
    },
    formateDateForAllFault (datas) {
      for (let data of datas) {
        if (this.$i18n.locale === 'en') {
          if (data.m_wFaultType === 103) {
            data.m_wSubFaultType = this.TZParamSubtypeMapEn.get(data.m_wSubFaultType)
          } else if (data.m_wFaultType === 208 || data.m_wFaultType === 207 || data.m_wFaultType === 206) {
            data.m_wSubFaultType = this.greenLampSubtypeMapEn.get(data.m_wSubFaultType)
          } else if (data.m_wFaultType === 211 || data.m_wFaultType === 210 || data.m_wFaultType === 209) {
            data.m_wSubFaultType = this.lampPowerSubtypeMapEn.get(data.m_wSubFaultType)
          } else {
            data.m_wSubFaultType = ''
          }
          data.m_wFaultType = this.faultCodeMapEn.get(data.m_wFaultType)
          data.m_byFaultLevel = this.faultLevelMapEn.get(data.m_byFaultLevel)
        } else {
          if (data.m_wFaultType === 103) {
            data.m_wSubFaultType = this.TZParamSubtypeMap.get(data.m_wSubFaultType)
          } else if (data.m_wFaultType === 208 || data.m_wFaultType === 207 || data.m_wFaultType === 206) {
            data.m_wSubFaultType = this.greenLampSubtypeMap.get(data.m_wSubFaultType)
          } else if (data.m_wFaultType === 211 || data.m_wFaultType === 210 || data.m_wFaultType === 209) {
            data.m_wSubFaultType = this.lampPowerSubtypeMap.get(data.m_wSubFaultType)
          } else {
            data.m_wSubFaultType = ''
          }
        }
        data.m_wFaultType = this.faultCodeMap.get(data.m_wFaultType)
        data.m_byFaultLevel = this.faultLevelMap.get(data.m_byFaultLevel)
        data.m_unFaultOccurTime = this.formateDate(data.m_unFaultOccurTime)
        data.m_unFaultRenewTime = this.formateDate(data.m_unFaultRenewTime)
        // let newDate = new Date(data.m_unFaultOccurTime * 1000 + 8 * 60 * 60 * 1000)
        // let newDate = new Date(data.m_unFaultOccurTime * 1000)
        // var y = newDate.getFullYear()
        // var m = newDate.getMonth() + 1
        // m = m < 10 ? ('0' + m) : m
        // var d = newDate.getDate()
        // d = d < 10 ? ('0' + d) : d
        // var h = newDate.getHours()
        // h = h < 10 ? ('0' + h) : h
        // var minute = newDate.getMinutes()
        // minute = minute < 10 ? ('0' + minute) : minute
        // var second = newDate.getSeconds()
        // second = second < 10 ? ('0' + second) : second
        // data.m_unFaultOccurTime = y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
      }
      return datas
    },
    formateDate (value) {
      if (value === 0) {
        return ''
      }
      let newDate = new Date(value * 1000)
      var y = newDate.getFullYear()
      var m = newDate.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      var d = newDate.getDate()
      d = d < 10 ? ('0' + d) : d
      var h = newDate.getHours()
      h = h < 10 ? ('0' + h) : h
      var minute = newDate.getMinutes()
      minute = minute < 10 ? ('0' + minute) : minute
      var second = newDate.getSeconds()
      second = second < 10 ? ('0' + second) : second
      return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
    },
    handleClick (tab, event) {
      this.handlerFaultData()
    },
    leadingOutFault () {
      let content = new Blob([JSON.stringify(this.allFault)])
      // 生成url对象
      let urlObject = window.URL || window.webkitURL || window
      let url = urlObject.createObjectURL(content)
      // 生成<a></a>DOM元素
      let el = document.createElement('a')
      // 链接赋值
      el.href = url
      el.download = 'fault.txt'
      // 必须点击否则不会下载
      el.click()
      // 移除链接释放资源
      urlObject.revokeObjectURL(url)
    },
    showLedDetailFault () {
      this.showDetailFault(5)
    },
    showVehDetDetailFault () {
      this.showDetailFault(6)
    },
    showDetailFault (val) {
      getFault(val).then(data => {
        if (data.data.success !== true) {
          this.listLoading = false
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        this.listLoading = false
        if (data.data.data.data === undefined || data.data.data.data.m_FaultDetailedInfo === undefined) {
          this.dialogDetailFault = []
        } else {
          this.dialogDetailFault = this.formateDateForAllFault(data.data.data.data.m_FaultDetailedInfo)
        }
        let detailFault = this.$refs.detailfault
        detailFault.onShowDetailFault()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.detail-fault {
  position: relative;
  float: right;
  margin-top: 40px;
  z-index: 99;
}
</style>
