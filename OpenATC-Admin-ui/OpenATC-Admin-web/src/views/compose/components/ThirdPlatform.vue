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
  <div class="platform">
    <el-dialog
      :title="$t('openatc.devicemanager.thirdPlatform')"
      :visible.sync="dialogTableVisible"
      width="43%"
      height="700px"
      @close='closeTableDialog'>
      <el-button
        class="add-btn"
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd">{{$t('openatc.common.add')}}</el-button>
      <el-dialog
        width="33%"
        :title="isEdit ? $t('openatc.common.edit') : $t('openatc.common.add')"
        :visible.sync="innerVisible"
        append-to-body>
        <el-form ref="configUpdate" :model="innerForm">
          <el-form-item :label="$t('openatc.devicemanager.platform')" label-width="15%">
            <el-input v-model="innerForm.key" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('openatc.devicemanager.IP')" label-width="15%">
            <el-input v-model="innerForm.ip" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('openatc.devicemanager.port')" label-width="15%">
            <el-input v-model="innerForm.port" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer">
              <el-button @click="resetInnerForm()">{{$t('openatc.button.Cancel')}}</el-button>
              <el-button type="primary" @click="submitConfig()">{{$t('openatc.button.OK')}}</el-button>
          </div>
      </el-dialog>
      <div class="config-table">
        <el-table
        :data="settingList"
        style="width: 100%"
        max-height="540"
        >
        <el-table-column
          type="index"
          label="#"
          align="center"
          min-width="5%">
        </el-table-column>
        <el-table-column
          prop="key"
          :label="$t('openatc.devicemanager.platform')"
          align="center"
          min-width="25%">
        </el-table-column>
        <el-table-column
          prop="ip"
          :label="$t('openatc.devicemanager.IP')"
          align="center"
          min-width="17%">
        </el-table-column>
        <el-table-column
          prop="port"
          :label="$t('openatc.devicemanager.port')"
          align="center"
          min-width="22%">
        </el-table-column>
        <el-table-column
          :label="$t('openatc.systemsettings.operate')"
          min-width="11%"
          align="center">
          <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{
                $t("openatc.common.edit")
              }}</el-button>
              <el-button type="text" @click="handleDelete(scope.row)">{{
                $t("openatc.common.delete")
              }}</el-button>
            </template>
        </el-table-column>
      </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { DictApi } from '@/api/dict.js'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'ThirdPlatform',
  data () {
    return {
      dialogTableVisible: false,
      settingList: [],
      innerVisible: false,
      isEdit: false, // 区别新增、编辑
      innerForm: {
        type: 'platform',
        key: '',
        value: '',
        ip: '',
        port: ''
      }
    }
  },
  methods: {
    closeTableDialog () {
      this.dialogTableVisible = false
    },
    show () {
      this.getAllConfig()
      this.dialogTableVisible = true
    },
    getAllConfig () {
      let tag = 'platform'
      DictApi.getDictListByTag(tag).then((data) => {
        let res = data.data
        if (!res.success) {
          console.log('datas:' + res)
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return false
        }
        let list = data.data.data
        for (let item of list) {
          this.setIpPort(item)
        }
        this.settingList = list
      })
    },
    setIpPort (item) {
      let port = ''
      let ip = ''
      if (item.value.includes('_')) {
        let arr = item.value.split('_')
        ip = arr[0]
        port = arr[1]
      }
      item.ip = ip
      item.port = port
    },
    setValue (item) {
      item.value = item.ip + '_' + item.port
    },
    handleAdd () {
      this.isEdit = false
      this.innerVisible = true
      this.innerForm = {
        type: 'platform',
        key: '',
        value: '',
        ip: '',
        port: ''
      }
    },
    handleEdit (row) {
      this.isEdit = true
      this.innerVisible = true
      this.setIpPort(row)
      this.innerForm = JSON.parse(JSON.stringify(row))
    },
    handleDelete (id) {
      this.$confirm(this.$t('openatc.systemsettings.deleteconfig'), this.$t('openatc.common.tipsmodaltitle'), {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      }).then(() => {
        this.deleteConfig(id)
      })
    },
    submitConfig () {
      this.setValue(this.innerForm)
      if (this.isEdit) {
        this.updateConfig(this.innerForm)
      } else {
        this.addConfig(this.innerForm)
      }
    },
    resetInnerForm () {
      this.innerVisible = false
      this.innerForm = {
        type: 'platform',
        key: '',
        value: '',
        ip: '',
        port: ''
      }
    },
    deleteConfig (id) {
      DictApi.deleteDict(id).then((data) => {
        let res = data.data
        if (!res.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return false
        }
        this.$message.success(this.$t('openatc.common.deletesuccess'))
        this.getAllConfig()
      })
    },
    addConfig (config) {
      DictApi.addDict(config).then((data) => {
        let res = data.data
        if (!res.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return false
        }
        this.innerVisible = false
        this.$message.success(this.$t('openatc.common.addsuccess'))
        this.getAllConfig()
      })
    },
    updateConfig (config) {
      DictApi.updateDict(config).then((data) => {
        let res = data.data
        if (!res.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return false
        }
        this.innerVisible = false
        this.$message.success(this.$t('openatc.common.updatesuccess'))
        this.getAllConfig()
      })
    },
    formatIsValid (row) {
      let res = this.$t('openatc.token.no')
      if (row.isValid) {
        res = this.$t('openatc.token.yes')
      }
      return res
    }
  }
}
</script>
<style lang="scss">
.platform .el-dialog__body {
  padding-top: 13px;
  padding-bottom: 43px;
}
</style>

<style lang="scss" scoped>
.config-table {
  padding: 0 12px;
}
.add-btn {
  float: right;
  width: 70px;
  height: 36px;
  background-color: #409eff;
  border-radius: 4px;
  padding: 0;
  text-align: center;
  margin-bottom: 12px;
}
</style>
