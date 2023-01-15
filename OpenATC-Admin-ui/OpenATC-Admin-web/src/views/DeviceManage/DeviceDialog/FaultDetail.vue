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
  <div class="dev-fault-detail">
    <el-dialog
      :title="$t(`openatc.devicemanager.${[childTitle]}`)"
      :visible.sync="dialogFormVisible"
      width="60%"
      :close-on-click-modal="false"
      @close='closeFormDialog'>
    <div class="content">
      <el-table
        :data="faultList"
        style="width: 100%"
        :height="600"
        :default-sort = "{prop: 'm_unFaultOccurTime', order: 'descending'}"
       >
        <el-table-column
          prop="m_unFaultOccurTime"
          :label="$t('openatc.devicemanager.faultOccurtime')"
          sortable
          width="200">
        </el-table-column>
        <el-table-column
          prop="m_byFaultBoardType"
          :label="$t('openatc.devicemanager.boardCardType')"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          prop="m_wFaultType"
          :label="$t('openatc.devicemanager.faultMaintype')"
          sortable
          width="170">
        </el-table-column>
        <el-table-column
          prop="m_wSubFaultType"
          :label="$t('openatc.devicemanager.faultSubtype')"
          sortable
          width="160">
        </el-table-column>
        <el-table-column
          prop="m_byFaultDescValue"
          :label="$t('openatc.devicemanager.faultValue')"
          sortable
          width="100">
        </el-table-column>
        <el-table-column
          prop="m_byFaultLevel"
          :label="$t('openatc.devicemanager.faultGrade')"
          sortable
          width="120">
        </el-table-column>
        <el-table-column :label="$t('openatc.devicemanager.operation')" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="onIgnoreClick(scope.row.id)">{{$t('openatc.button.ignore')}}</el-button>
        </template>
        </el-table-column>
      </el-table>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import { DeleteFaultById } from '@/api/fault'
export default {
  name: 'FaultDetail',
  props: {
    childTitle: {
      type: String
    }
  },
  data () {
    return {
      dialogFormVisible: false,
      deviceInfo: {},
      faultList: [],
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
  methods: {
    onViewFaultClick (list) {
      this.dialogFormVisible = !this.dialogFormVisible
      this.formatDeviceInfo(list)
      this.faultList = list
    },
    formatDeviceInfo (list) {
      this.formateDateForAllFault(list)
    },
    formatBoardType (dev) {
      let typecode = dev.m_byFaultBoardType
      let type
      switch (typecode) {
        case 1:
          type = this.$t('edge.fault.tab1')
          break
        case 2:
          type = this.$t('edge.fault.tab2')
          break
        case 3:
          type = this.$t('edge.fault.tab3')
          break
        case 4:
          type = this.$t('edge.fault.tab4')
          break
        default:
          type = ''
      }
      dev.m_byFaultBoardType = type
    },
    formateDateForAllFault (datas) {
      for (let data of datas) {
        this.formatBoardType(data)
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
          data.m_wFaultType = this.faultCodeMap.get(data.m_wFaultType)
          data.m_byFaultLevel = this.faultLevelMap.get(data.m_byFaultLevel)
        }
      }
      return datas
    },
    onIgnoreClick (id) {
      let _this = this
      DeleteFaultById(id).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          this.$message({
            message: this.$t('openatc.common.deletefailed'),
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getList()
          }
        })
      })
    },
    closeFormDialog () {

    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.dev-update .el-dialog__body {
  padding: 30px 72px 30px 0;
}
.el-dialog__footer {
  padding: 10px 72px 38px 0;
}
</style>
