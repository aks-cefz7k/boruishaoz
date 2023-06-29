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
  <div class="system-settings">
    <el-dialog
      :title="$t('openatc.main.systemsettings')"
      :visible.sync="dialogTableVisible"
      width="53%"
      height="700px"
      @close='closeTableDialog'>
      <el-button
        class="add-btn"
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd">{{$t('openatc.common.add')}}</el-button>
      <el-dialog
          width="33%"
          :title="isEdit ? $t('openatc.systemsettings.updateconfig') : $t('openatc.systemsettings.addconfig')"
          :visible.sync="innerVisible"
          append-to-body>
          <el-form ref="configUpdate" :model="innerForm">
            <el-form-item :label="$t('openatc.systemsettings.module')" label-width="15%">
              <el-input v-model="innerForm.module" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$t('openatc.systemsettings.name')" label-width="15%">
              <el-input v-model="innerForm.key" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$t('openatc.systemsettings.content')" label-width="15%">
              <el-input v-model="innerForm.value" autocomplete="off"></el-input>
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
        :default-sort = "{prop: 'm_unFaultOccurTime', order: 'descending'}"
        >
        <el-table-column
          prop="id"
          :label="$t('openatc.systemsettings.id')"
          min-width="18%">
        </el-table-column>
        <el-table-column
          prop="module"
          :label="$t('openatc.systemsettings.module')"
          min-width="19%">
        </el-table-column>
        <el-table-column
          prop="key"
          :label="$t('openatc.systemsettings.name')"
          min-width="23%">
        </el-table-column>
        <el-table-column
          prop="value"
          :label="$t('openatc.systemsettings.content')"
          min-width="26%">
        </el-table-column>
        <el-table-column
          :label="$t('openatc.systemsettings.operate')"
          min-width="14%"
          align="center">
          <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{
                $t("openatc.common.edit")
              }}</el-button>
              <el-button type="text" @click="handleDelete(scope.row.id)">{{
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
import { SystemconfigApi } from '@/api/systemconfig.js'
export default {
  name: 'systemsettings',
  data () {
    return {
      dialogTableVisible: false,
      settingList: [],
      innerVisible: false,
      isEdit: false, // 区别新增、编辑
      innerForm: {
        module: '',
        key: '',
        value: ''
      }
    }
  },
  methods: {
    closeTableDialog () {
      this.dialogTableVisible = false
    },
    show () {
      this.dialogTableVisible = true
    },
    getAllConfig () {
      SystemconfigApi.GetAllSystemconfig().then((data) => {
        let res = data.data
        if (!res.success) {
          console.log('datas:' + res)
          throw new Error('get system config error')
        }
        this.settingList = data.data.data
      })
    },
    handleAdd () {
      this.isEdit = false
      this.innerVisible = true
    },
    handleEdit (row) {
      this.isEdit = true
      this.innerVisible = true
      this.innerForm = row
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
      if (this.isEdit) {
        this.updateConfig(this.innerForm)
      } else {
        this.addConfig(this.innerForm)
      }
    },
    resetInnerForm () {
      this.innerVisible = false
      this.innerForm = {
        module: '',
        key: '',
        value: ''
      }
    },
    deleteConfig (id) {
      SystemconfigApi.DeleteSystemconfigById(id).then((data) => {
        let res = data.data
        if (!res.success) {
          console.log('datas:' + res)
          throw new Error('delete system config error')
        }
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000
        })
        this.getAllConfig()
      })
    },
    addConfig (config) {
      SystemconfigApi.AddSystemconfig(config).then((data) => {
        let res = data.data
        if (!res.success) {
          console.log('datas:' + res)
          throw new Error('add system config error')
        }
        this.innerVisible = false
        this.$message({
          message: this.$t('openatc.common.addsuccess'),
          type: 'success',
          duration: 1 * 1000
        })
        this.getAllConfig()
      })
    },
    updateConfig (config) {
      SystemconfigApi.UpdateSystemconfig(config).then((data) => {
        let res = data.data
        if (!res.success) {
          console.log('datas:' + res)
          throw new Error('add system config error')
        }
        this.innerVisible = false
        this.$message({
          message: this.$t('openatc.common.updatesuccess'),
          type: 'success',
          duration: 1 * 1000
        })
        this.getAllConfig()
      })
    }
  },
  mounted () {
    this.getAllConfig()
  }
}
</script>
<style lang="scss">
.system-settings .el-dialog__body {
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
