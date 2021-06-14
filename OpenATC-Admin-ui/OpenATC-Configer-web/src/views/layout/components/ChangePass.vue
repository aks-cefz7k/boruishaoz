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
  <div class="app-container">
    <el-dialog
      title="设置新密码"
      :visible.sync="dialogFormVisible"
      width="600px"
      @close='closeFormDialog'
    >
      <el-form
        class="dialog-footer"
        ref="tempPassword"
        :model="PasswordData"
        :rules="rules"
        label-position="right"
        label-width="45%"
        style='width: 70%; margin-left:50px;'
      >
        <el-form-item
          label="旧密码"
          prop="pass"
        >
          <el-input
            type="password"
            v-model="PasswordData.pass"
            @keyup.enter.native="updateUser"
            onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
          >
          </el-input>
        </el-form-item>
        <passCheck
          ref="checkPass"
          :newpassdata="PasswordData.newpass"
          @updateData="updatePassData"
        ></passCheck>

        <el-form-item
          label="确认新密码"
          prop="checknewpass"
        >
          <el-input
            type="password"
            v-model="PasswordData.checknewpass"
            placeholder=""
            @keyup.enter.native="updateUser"
            onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <!-- <center> -->
          <el-button @click="resetForm">取消</el-button>
          <el-button
            type="primary"
            @click="updateUser"
          >确定</el-button>
        <!-- </center> -->
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2020/3/16-13:59
*/
import { UpdateCurUsrPassWd } from '../../../api/base'
import passCheck from './passwordCheck.vue'
export default {
  components: {
    passCheck
  },
  data () {
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.PasswordData.newpass) {
        callback(new Error('两次输入密码不一致 !'))
      } else {
        callback()
      }
    }
    // var validatePass = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('Please input the password !'))
    //   } else if (!this.check_length || !this.check_char) {
    //     callback(new Error('Not Satisfiable !'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      dialogFormVisible: false,
      user_name: '',
      PasswordData: {
        user_name: '',
        pass: '',
        newpass: '',
        checknewpass: ''
      },
      rules: {
        pass: [{ required: true, message: '请输入密码' }],
        newpass: [{ required: true, message: '请输入新密码' }],
        checknewpass: [
          {
            required: true,
            validator: validatePass2,
            trigger: 'blur'
          }
        ]
      }
    }
  },
  name: 'ChangePass',
  props: {},
  computed: {
    pokerHistory () {
      return this.PasswordData.newpass
    }
  },
  watch: {
    deep: true
  },
  methods: {
    updatePassData (pass) {
      this.PasswordData.newpass = pass
    },
    updateUser () {
      if (this.PasswordData.pass === '') {
        this.$message.error('请输入旧密码！')
        return
      }
      if (this.PasswordData.newpass === '') {
        this.$message.error('请输入新密码！')
        return
      }
      if (this.PasswordData.newpass !== this.PasswordData.checknewpass) {
        this.$message.error('两次输入密码不一致！')
        return
      }
      if (this.PasswordData.newpass === this.PasswordData.pass) {
        this.$message.error('新密码不能与旧密码一致！')
        return
      }
      // if (!this.check_length || !this.check_char) {
      //   this.$message.error("Not Satisfiable !");
      //   return;
      // }
      // 校验新密码是否符合安全规则，是否为空
      let passwordCheck = this.$refs.checkPass
      let state = passwordCheck.checkNewPass()
      if (!state) {
        return
      }
      UpdateCurUsrPassWd(
        this.user_name,
        this.PasswordData.pass,
        this.PasswordData.newpass
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
        // this.strong = 'common.none'
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.closeFormDialog()
            this.$store.dispatch('LogOut').then(() => {
              location.reload() // 为了重新实例化vue-router对象 避免bug
            })
          }
        })
      })
    },
    resetForm () {
      // 取消重置表单
      this.dialogFormVisible = false
      this.closeFormDialog()
    },
    closeFormDialog () {
      this.$refs.checkPass.reset()
      this.$refs.tempPassword.resetFields() // 表单重置
    },
    onChangePassClick (userName) {
      this.user_name = userName
      this.dialogFormVisible = !this.dialogFormVisible
    }
  }
}
</script>
