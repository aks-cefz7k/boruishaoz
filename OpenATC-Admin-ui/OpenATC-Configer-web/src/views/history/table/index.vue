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
  <div class="history-table" ref="history-table">
    <!-- <el-button type="primary" @click="refreshData" size="small" style="margin-bottom: 10px;">刷新</el-button> -->
    <div class="atc-table">
    <el-table
        :data="tableData"
        size="small"
        style="width: 100%"
        v-loading.body="listLoading"
        :default-sort = "{prop: 'm_unFaultOccurTime', order: 'descending'}"
        id="footerBtn">
        <el-table-column
        type="index"
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_wFaultType"
        :label="$t('edge.fault.maintype')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_wSubFaultType"
        :label="$t('edge.fault.subtype')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_unFaultOccurTime"
        :label="$t('edge.fault.occurtime')"
        sortable
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_unFaultRenewTime"
        :label="$t('edge.fault.recoverytime')"
        sortable
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_byFaultLevel"
        :label="$t('edge.fault.grade')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="m_byFaultDescValue"
        :formatter="stateFormat"
        :label="$t('edge.fault.value')"
        align="center">
        </el-table-column>
    </el-table>
  </div>
  <!-- <div class="edit-button">
    <el-button type="primary" @click="update" size="small">更新</el-button>
  </div> -->
  </div>
</template>

<script>
// import { getFault } from '@/api/fault'
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
export default {
  name: 'bordertable',
  props: {
    activeName: {
      type: String
    },
    tableData: {
      type: Array
    }
  },
  data () {
    return {
      tableHeight: 700,
      listLoading: false
    }
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['history-table'].offsetHeight - 117
      window.onresize = function () {
        _this.tableHeight = _this.$refs['history-table'].offsetHeight - 117
      }
    })
  },
  created () {
    // this.update()
    // this.handlerFaultData()
  },
  methods: {
    refreshData () {
      this.$emit('getAllFault')
    },
    stateFormat (row, column) {
      return row.m_byFaultDescValue + ''
    },
    exportExcel () {
      /* generate workbook object from table */
      var xlsxParam = { raw: true } // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(document.querySelector('#footerBtn'), xlsxParam)

      /* get binary string as output */
      var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), 'fault.xlsx')
      } catch (e) {
        if (typeof console !== 'undefined') {
          console.log(e, wbout)
        }
      }
      return wbout
    }
    // handlerFaultData () {
    //   let filterId = Number(this.activeName)
    //   this.tableData = this.allFault.filter(function (obj) {
    //     return obj.boardid === filterId
    //   })
    // },
    // update () {
    //   this.listLoading = true
    //   getFault(0).then(data => {
    //     if (data.data.success !== true) {
    //       this.listLoading = false
    //       this.$message.error(data.data.message)
    //       console.log(data.data.message)
    //       return
    //     }
    //     this.listLoading = false
    //     this.allFault = data.data.data.data.fault
    //     this.handlerFaultData()
    //   })
    // },
    // handleData (datas) {
    //   this.tableData = []
    //   for (let data of datas) {
    //     this.tableData.push(data)
    //   }
    // }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
// .atc-table {
//   top: 150px;
//   left: 20px;
//   right: 20px;
//   border: solid 1px #e6e6e6;
//   overflow: auto;
// }
.edit-button {
  float: left;
  // margin-left: 10px;
  margin-top: 10px;
}
</style>
