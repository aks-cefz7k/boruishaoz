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
      :title="$t(`edge.faultrecord.faultDetail`)"
      :visible.sync="dialogFormVisible"
      width="60%"
      :close-on-click-modal="false">
    <div class="content">
      <el-table
        :data="faultList"
        style="width: 100%"
        :height="600"
        :default-sort = "{prop: 'm_unFaultOccurTime', order: 'descending'}"
       >
        <el-table-column
          prop="m_unFaultOccurTime"
          :label="$t('edge.faultrecord.faultOccurtime')"
          sortable
          width="200">
        </el-table-column>
        <el-table-column
          prop="m_byFaultBoardType"
          :formatter="formatterBoardType"
          :label="$t('edge.faultrecord.boardCardType')"
          sortable>
        </el-table-column>
        <el-table-column
          prop="m_wFaultType"
          :formatter="m_wFaultTypes"
          :label="$t('edge.faultrecord.faultMaintype')"
          sortable>
        </el-table-column>
        <el-table-column
          prop="m_wSubFaultType"
          :formatter="m_wSubFaultType"
          :label="$t('edge.faultrecord.faultSubtype')"
          sortable>
        </el-table-column>
        <el-table-column
          prop="m_byFaultDescValue"
          :formatter="m_byFaultDescValue"
          :label="$t('edge.faultrecord.faultValue')"
          sortable>
        </el-table-column>
        <el-table-column
          prop="m_byFaultLevel"
          :formatter="m_byFaultLevel"
          :label="$t('edge.faultrecord.faultGrade')"
          sortable
          width="120">
        </el-table-column>
        <el-table-column
          prop="enumerate"
          :label="$t('edge.faultrecord.enumerate')"
          align="center">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.enumerate" :type="scope.row.enumerate === '1'?'info':scope.row.enumerate === '2'?'success':scope.row.enumerate === '0'?'':''">{{formatterEnumerate(scope.row)}}</el-tag>
          </template>
          </el-table-column>
        <el-table-column :label="$t('edge.faultrecord.operation')" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleEnumerateCheck(scope.row, '2')">{{$t('edge.button.confirm')}}</el-button>
          <el-button type="text" @click="handleEnumerateCheck(scope.row, '1')">{{$t('edge.button.ignore')}}</el-button>
        </template>
        </el-table-column>
      </el-table>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import {enumerateCheck, GetAllFaultRange} from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
import { formatFaultDescValue, formatBoardType, formatEnumerate, formatSubFaultType, formatFaultLevel, formatFaultTypes } from '@/utils/fault.js'
export default {
  name: 'FaultDetail',
  props: {
    childTitle: {
      type: String
    },
    agentId: {
      type: String
    }
  },
  data () {
    return {
      dialogFormVisible: false,
      deviceInfo: {},
      faultList: []
    }
  },
  methods: {
    getFaultById () {
      let param = {
        agentId: this.agentId,
        isCurrentFault: true
      }
      GetAllFaultRange(param).then(res => {
        if (res.data.success !== true) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.faultList = res.data.data.content
      })
    },
    onViewFaultClick () {
      this.dialogFormVisible = !this.dialogFormVisible
      this.getFaultById()
    },
    formatterBoardType (row) {
      return formatBoardType(row)
    },
    m_byFaultDescValue (row) {
      return formatFaultDescValue(row)
    },
    m_wSubFaultType (row) {
      return formatSubFaultType(row)
    },
    m_byFaultLevel (row) {
      return formatFaultLevel(row)
    },
    m_wFaultTypes (row) {
      return formatFaultTypes(row)
    },
    handleEnumerateCheck (row, enumerate) {
      enumerateCheck(row.agentid, row.m_wFaultID, enumerate).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('edge.common.operationsuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.getFaultById()
            this.$emit('refreshFault')
          }
        })
      })
    },
    formatterEnumerate (row) {
      return formatEnumerate(row)
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
