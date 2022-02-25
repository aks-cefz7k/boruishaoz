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
  <div class="modify-passwd">
    <el-dialog
      :title="$t('openatc.main.changepass')"
      :visible.sync="dialogFormVisible"
      width="600px"
      height="300px"
      @close='closeFormDialog'>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item :label="$t('openatc.usermanager.oldpass')" prop="oldPass">
            <el-input type="password" v-model="ruleForm.oldPass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item :label="$t('openatc.usermanager.newpassword')" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item :label="$t('openatc.usermanager.confirmpass')" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
        </el-form-item>
        </el-form>
      <div
        slot="footer"
        class="dialog-footer">
          <el-button @click="resetForm('tempUser')">{{$t('openatc.button.Cancel')}}</el-button>
          <el-button type="primary" @click="commit()">{{$t('openatc.button.OK')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/11/01
*/
import { UpdateCurUsrPassWd } from '../../../api/base'
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(this.$t('openatc.usermanager.enterpassword')))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(this.$t('openatc.usermanager.enputpassagain')))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error(this.$t('openatc.usermanager.twopasswordsdiff')))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      ruleForm: {
        oldPass: '',
        pass: '',
        checkPass: ''
      },
      rules: {
        oldPass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      user_name: ''
    }
  },
  name: 'modifypasswd',
  props: {},
  watch: {
    deep: true
  },
  methods: {
    updatePassData (pass) {
      this.tempUser.password = pass
    },
    closeFormDialog () {
      this.resetForm()
    },
    onPasswdClick (userName) {
      this.user_name = userName
      this.dialogFormVisible = !this.dialogFormVisible
    },
    commit () {
      if (this.ruleForm.oldPass === '') {
        this.$message.error(this.$t('openatc.usermanager.enteroldpass'))
        return
      }
      if (this.ruleForm.pass === '') {
        this.$message.error(this.$t('openatc.usermanager.enternewepass'))
        return
      }
      if (this.ruleForm.pass === this.ruleForm.oldPass) {
        this.$message.error(this.$t('openatc.usermanager.newpassdiffoldpass'))
        return
      }
      if (this.ruleForm.pass !== this.ruleForm.checkPass) {
        this.$message.error(this.$t('openatc.usermanager.twopasswordsdiff'))
        return
      }
      UpdateCurUsrPassWd(
        this.user_name,
        this.ruleForm.oldPass,
        this.ruleForm.pass
      ).then(data => {
        if (data.data.success !== true) {
          if (data.data.code === '3006') {
            this.$message.error(this.$t('openatc.usermanager.originalpassworderror'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = this.$t('openatc.usermanager.modificationsucc')
        this.dialogFormVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.closeFormDialog()
          }
        })
        this.$store.dispatch('LogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      })
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
      this.$refs.ruleForm.resetFields() // 表单重置
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
</style>
