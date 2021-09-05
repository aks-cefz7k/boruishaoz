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
    <el-button type="primary" @click="getAllFault" size="small" style="margin-bottom: 10px;">刷新</el-button>
    <el-button type="primary" size="small" @click="leadingOutFault" style="margin-bottom: 10px;">导出</el-button>
    <el-button class="detail-fault" type="primary" size="small" @click="showLedDetailFault" style="" v-show="activeName === '2'">当前详细灯组故障</el-button>
    <el-button class="detail-fault" type="primary" size="small" @click="showVehDetDetailFault" style="" v-show="activeName === '3'">当前详细车检版故障</el-button>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane v-for="(item, index) in tabList" :key="index" :label="item.label" :name="item.value">
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
      dialogDetailFault: []
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
        let newDate = new Date(data.m_unFaultOccurTime * 1000 + 8 * 60 * 60 * 1000)
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
        data.m_unFaultOccurTime = y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
      }
      return datas
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
        debugger
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
