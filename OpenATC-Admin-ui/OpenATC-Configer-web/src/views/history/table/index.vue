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
  <div>
    <!-- <el-button type="primary" @click="refreshData" size="small" style="margin-bottom: 10px;">刷新</el-button> -->
    <div class="atc-table">
    <el-table
        :data="tableData"
        stripe
        size="small"
        :max-height="tableHeight"
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
      screenHeight: window.innerHeight, // 屏幕高度
      listLoading: false
    }
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      // this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      // 后面的50：根据需求空出的高度，自行调整
      _this.tableHeight =
                window.innerHeight -
                document.querySelector('#footerBtn').offsetTop -
                200
      window.onresize = function () {
        // 定义窗口大小变更通知事件
        _this.screenHeight = window.innerHeight // 窗口高度
      }
    })
  },
  watch: {
    screenHeight: function () {
      // 监听屏幕高度变化
      this.tableHeight =
                window.innerHeight -
                document.querySelector('#footerBtn').offsetTop -
                200
    }
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
.atc-table {
  top: 150px;
  left: 20px;
  right: 20px;
  border: solid 1px #e6e6e6;
  overflow: auto;
}
.edit-button {
  float: left;
  // margin-left: 10px;
  margin-top: 10px;
}
</style>
