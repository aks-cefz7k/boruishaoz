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
      title="修改密码"
      :visible.sync="dialogFormVisible"
      width="600px"
      height="300px"
      @close='closeFormDialog'>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="旧密码" prop="oldPass">
            <el-input type="password" v-model="ruleForm.oldPass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
        </el-form-item>
        </el-form>
      <div
        slot="footer"
        class="dialog-footer">
          <el-button @click="resetForm('tempUser')">取消</el-button>
          <el-button type="primary" @click="commit()">确定</el-button>
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
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
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
        this.$message.error('请输入旧密码！')
        return
      }
      if (this.ruleForm.pass === '') {
        this.$message.error('请输入新密码！')
        return
      }
      if (this.ruleForm.pass === this.ruleForm.oldPass) {
        this.$message.error('新密码不能与旧密码一致！')
        return
      }
      if (this.ruleForm.pass !== this.ruleForm.checkPass) {
        this.$message.error('两次输入密码不一致！')
        return
      }
      UpdateCurUsrPassWd(
        this.user_name,
        this.ruleForm.oldPass,
        this.ruleForm.pass
      ).then(data => {
        if (data.data.success !== true) {
          if (data.data.code === '3006') {
            this.$message.error('原密码错误!')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '修改成功'
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
