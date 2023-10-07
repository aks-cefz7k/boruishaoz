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
    <el-dialog
      :title="$t('edge.system.tips')"
      :visible.sync="dialogFormVisible"
      width="400px"
      @close='closeFormDialog'>
      <div>
          <form>
              <input type="file" @change="getFile($event)">
          </form>
      </div>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">{{$t('edge.common.cancel')}}</el-button>
          <el-button type="primary" @click="submitForm($event)">{{$t('edge.system.update')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/12/11
*/
import { updateFile } from '@/api/param'
import { setRemoteControl } from '@/api/system'
export default {
  data () {
    return {
      dialogFormVisible: false,
      file: '',
      username: '',
      password: ''
    }
  },
  name: 'updatefile',
  props: {},
  created () {
  },
  methods: {
    onUpdateFile (username, password) {
      this.username = username
      this.password = password
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.resetForm()
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    },
    ok () {
    },
    getFile (event) {
      this.file = event.target.files[0]
    },
    submitForm (event) {
      event.preventDefault()
      let formData = new FormData()
      formData.append('file', this.file)
      formData.append('username', this.username)
      formData.append('password', this.password)
      updateFile(formData).then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = this.$t('edge.common.updatesucess')
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
        })
        this.$confirm(this.$t('edge.system.isReboot'),
          this.$t('edge.common.alarm'), {
            confirmButtonText: this.$t('edge.common.confirm'),
            cancelButtonText: this.$t('edge.common.cancel'),
            type: 'warning'
          }).then(() => {
          this.setRemoteControl()
          this.$message({
            type: 'success',
            message: this.$t('edge.system.rebootSuccess')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.system.rebootFaile')
          })
        })
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    setRemoteControl () {
      setRemoteControl('reboot').then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = this.$t('edge.system.resetSuccess')
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
          // onClose: () => {
          //   this.$parent.getCode()
          // }
        })
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
</style>
