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
      <el-dialog title="故障信息" :visible.sync="dialogTableVisible">
        <div class="detailfault-table">
            <el-table
            :data="dialogDetailFault"
            height="350"
            stripe
            size="small"
            style="width: 100%"
            v-loading.body="listLoading"
            id="footerBtn">
            <el-table-column
            type="index"
            label="#"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_wFaultType"
            label="主类型"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_wSubFaultType"
            label="子类型"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_unFaultOccurTime"
            label="发生时间"
            sortable
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_unFaultRenewTime"
            label="恢复时间"
            sortable
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_byFaultLevel"
            label="等级"
            align="center">
            </el-table-column>
            <el-table-column
            prop="m_byFaultDescValue"
            label="值"
            align="center">
            </el-table-column>
            </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
            <!-- <el-button @click="dialogFormVisible = false">取 消</el-button> -->
            <el-button type="primary" @click="dialogTableVisible = false">取消</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'detailfault',
  props: {
    // activeName: {
    //   type: String
    // },
    dialogDetailFault: {
      type: Array
    }
  },
  data () {
    return {
      listLoading: false,
      dialogTableVisible: false,
      tableData: [{
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }, {
        'm_wFaultID': 1000,
        'm_byFaultBoardType': 1,
        'm_unFaultOccurTime': 11111,
        'm_unFaultRenewTime': 22222,
        'm_wFaultType': 203,
        'm_wSubFaultType': 1,
        'm_byFaultDescValue': [2, 3],
        'm_byFaultLevel': 3
      }]
    }
  },
  created () {
  },
  methods: {
    onShowDetailFault () {
      this.dialogTableVisible = !this.dialogTableVisible
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.detailfault-table {
    border: solid 1px #e6e6e6;
}
</style>
