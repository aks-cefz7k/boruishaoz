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
  <el-dialog
    :title="$t('openatc.organization.relatedevice')"
    :visible.sync="dialogRolesVisible"
    width="700px"
    @close="closeDialog"
  >
    <div style="text-align: center">
      <el-transfer
        v-model="value"
        :data="allDevice"
        :props="{ key: 'agentid', label: 'name' }"
        :titles="[
          $t('openatc.organization.unrelateddevice'),
          $t('openatc.organization.relateddevice'),
        ]"
        style="text-align: left; display: inline-block"
      ></el-transfer>
    </div>
    <template slot="footer" class="dialog-footer">
      <el-button @click="onCancelClick()">{{
        $t("edge.common.cancel")
      }}</el-button>
      <el-button type="primary" @click="onConfirmClick()">{{
        $t("edge.common.confirm")
      }}</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {
  GetDevicesByOrgCode,
  BatchUpdateDevice
} from '@/api/relate'
import {
  GetAllDevice
} from '@/api/device'
export default {
  name: 'relateDevice',
  data () {
    return {
      allDevice: [],
      listLoading: false, // 数据加载等待动画
      dialogRolesVisible: false,
      value: [],
      orgCode: ''
    }
  },
  methods: {
    onConfirmClick () {
      this.listLoading = true
      BatchUpdateDevice(this.orgCode, this.value).then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          return
        }
        let msg = this.$t('openatc.common.operationsuccess')
        this.listLoading = false
        this.dialogRolesVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
        })
      })
    },
    show (row) {
      this.dialogRolesVisible = true
      this.orgCode = row.code
      this.getDevicesByOrgCode(this.orgCode)
      this.getAllDevice()
    },
    getAllDevice () {
      let _this = this
      GetAllDevice().then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          _this.$message.error(data.data.message)
          return
        }
        for (let obj of data.data.data) {
          let code = obj.code
          if (code && code !== this.orgCode) {
            obj.disabled = true
            _this.allDevice.push(obj)
          } else {
            _this.allDevice.unshift(obj)
          }
        }
      })
    },
    getDevicesByOrgCode (code) {
      let _this = this
      GetDevicesByOrgCode(code).then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          _this.$message.error(data.data.message)
          return
        }
        for (let obj of data.data.data) {
          _this.value.push(obj.agentid)
        }
      })
    },
    closeDialog () {
      this.allDevice = []
      this.value = []
    },
    onCancelClick () {
      this.allDevice = []
      this.value = []
      this.orgCode = ''
      this.dialogRolesVisible = false
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
</style>
