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
  <div class="TokenList">
    <el-dialog
      :title="$t('openatc.token.tokenManage')"
      :visible.sync="dialogFormVisible"
      width="70%"
      @close='closeFormDialog'>
        <el-table :data="list">
          <el-table-column
            type="index"
            width="50"
            label="#"></el-table-column>
          <el-table-column property="relateUser" :label="$t('openatc.token.username')" width="100" align="center"></el-table-column>
          <el-table-column property="description" :label="$t('openatc.systemsettings.desc')" align="center"></el-table-column>
          <el-table-column property="startTime" :label="$t('openatc.token.timeStart')"  width="180" align="center"></el-table-column>
          <el-table-column property="endTime" :label="$t('openatc.token.timeEnd')"  width="180" align="center"></el-table-column>
          <el-table-column property="isValid" :label="$t('openatc.token.isValid')" width="100" align="center">
            <template slot-scope="scope">
              <el-switch
                style="padding-left: 5px;"
                v-model="scope.row.isValid"
                active-color="#409EFF"
                :disabled="true">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column :label="$t('openatc.usermanager.operation')" width="150" align="center">
          <template slot-scope="scope">
              <el-button type="text" @click="onEditClick(scope.row)">{{$t('openatc.common.edit')}}</el-button>
              <el-button type="text" @click="onDeleteClick(scope.row.id)">{{$t('openatc.common.delete')}}</el-button>
          </template>
          </el-table-column>
        </el-table>
    </el-dialog>
    <updateToken ref ="updateToken"></updateToken>
  </div>
</template>

<script>
import {
  UpdateToken, GetTokenList, DeleteToken
} from '../../../api/token'
import { getMessageByCode } from '@/utils/responseMessage'
import updateToken from './updateToken'
export default {
  name: 'TokenList',
  components: { updateToken },
  data () {
    return {
      dialogFormVisible: false,
      list: []
    }
  },
  mounted () {
  },
  methods: {
    show () {
      this.dialogFormVisible = true
      this.getTokenList()
    },
    getTokenList () {
      GetTokenList().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.list = res.data.data
      })
    },
    update (params) {
      let _this = this
      UpdateToken(params).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let msg = this.$t('openatc.common.updatesuccess')
        this.dialogFormVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getTokenList()
          }
        })
      })
    },
    closeFormDialog () {
    },
    onEditClick (row) {
      this.$refs.updateToken.show(row)
      // let params = {
      //   id: row.id,
      //   description: row.description,
      //   isValid: row.isValid
      // }
      // this.update(params)
    },
    onDeleteClick (id) {
      let _this = this
      this.$confirm(this.$t('openatc.common.sureToDelete'), this.$t('openatc.common.tipsmodaltitle'), {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      }).then(() => {
        _this.deleteToken(id)
      }).catch(() => {
        _this.$message.info(this.$t('openatc.common.deletecancel'))
      })
    },
    deleteToken (id) {
      DeleteToken(id).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        }
        this.$message.success(this.$t('openatc.common.deletesuccess'))
        this.getTokenList()
      })
    }
  }
}
</script>
