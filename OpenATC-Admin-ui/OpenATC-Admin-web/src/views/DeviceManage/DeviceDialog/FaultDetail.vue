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
          width="180">
        </el-table-column>
        <el-table-column
          prop="m_byFaultBoardType"
          :formatter="formatterBoardType"
          :label="$t('openatc.devicemanager.boardCardType')"
          sortable
          width="150">
        </el-table-column>
        <el-table-column
          prop="m_wFaultType"
          :formatter="m_wFaultTypes"
          :label="$t('openatc.devicemanager.faultMaintype')"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          prop="m_wSubFaultType"
          :formatter="m_wSubFaultType"
          :label="$t('openatc.devicemanager.faultSubtype')"
          sortable
          width="160">
        </el-table-column>
        <el-table-column
          prop="m_byFaultDescValue"
          :formatter="m_byFaultDescValue"
          :label="$t('openatc.devicemanager.faultValue')"
          sortable
          width="100">
        </el-table-column>
        <el-table-column
          prop="m_byFaultLevel"
          :formatter="m_byFaultLevel"
          :label="$t('openatc.devicemanager.faultGrade')"
          sortable
          width="120">
        </el-table-column>
        <el-table-column
          sortable
          prop="enumerate"
          :label="$t('openatc.faultrecord.enumerate')"
          align="center">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.enumerate"
                    :type="scope.row.enumerate === '1'?'info':scope.row.enumerate === '2'?'success':scope.row.enumerate === '0'?'':''">
              {{formatterEnumerate(scope.row)}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="$t('openatc.devicemanager.operation')" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="onButtonClick(scope.row, '2')">{{$t('openatc.faultrecord.confirm')}}</el-button>
            <el-button type="text" @click="onButtonClick(scope.row, '1')">{{$t('openatc.button.ignore')}}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import {enumerateCheck} from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
import { formatFaultDescValue, formatBoardType, formatEnumerate, formatSubFaultType, formatFaultLevel, formatFaultTypes } from '@/utils/fault.js'
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
      faultList: []
    }
  },
  methods: {
    onViewFaultClick (list) {
      this.dialogFormVisible = !this.dialogFormVisible
      this.faultList = list
    },
    onButtonClick (row, enumerate) {
      let _this = this
      let id = row.m_wFaultID
      enumerateCheck(row.agentid, id, enumerate).then(res => {
        if (!res.data.success) {
          _this.$message.error(getMessageByCode(res.data.code, _this.$i18n.locale))
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.operationsuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.handleFault(row)
          }
        })
      })
    },
    formatterBoardType (row) {
      return formatBoardType(row)
    },
    formatterEnumerate (row) {
      return formatEnumerate(row)
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
    m_byFaultDescValue (row) {
      return formatFaultDescValue(row)
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
