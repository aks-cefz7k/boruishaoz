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
  <div class="user-update">
    <el-dialog
        :title="$t('openatc.usermanager.edituser')"
        :visible.sync="dialogFormVisible"
        width="700px"
        @close='closeFormDialog'>
        <el-form
        class="dialog-footer"
        ref="tempUser"
        :model="tempUser"
        :rules="rules"
        :label-width="$t('openatc.usermanager.labelwidth')">
        <el-form-item
            :label="$t('openatc.usermanager.newpassword')"
            prop="newpass">
            <el-input
            type="password"
            v-model="tempUser.newpass"
            @keyup.enter.native="updateUser"
            onkeyup="this.value=this.value.replace(/\s+/g,'')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.usermanager.confirmpassword')"
            v-if="tempUser.newpass!==''"
            prop="checkpass">
            <el-input
            type="password"
            v-model="tempUser.checkpass"
            @keyup.enter.native="updateUser"
            placeholder=""
            onkeyup="this.value=this.value.replace(/\s+/g,'')">
            </el-input>
        </el-form-item>
        <!-- <el-form-item
            label="用户名称"
            prop="user_name">
            <el-input
            type="text"
            v-model="tempUser.user_name"
            @keyup.enter.native="updateUser">
            </el-input>
        </el-form-item> -->
        <el-form-item
            :label="$t('openatc.usermanager.role')">
            <el-select v-model="roleNames" multiple placeholder="" style="width:100%">
              <el-option
                v-for="item in roles"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.usermanager.realname')"
            prop="nick_name">
            <el-input
            type="text"
            v-model="tempUser.nick_name"
            @keyup.enter.native="updateUser">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.usermanager.organization')"
            prop="organization">
            <el-input
            type="text"
            v-model="tempUser.organization"
            @focus="relateOrganization(tempUser.organization)"
            @keyup.enter.native="updateUser">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.usermanager.telno')"
            prop="mobile_phone">
            <el-input
            type="text"
            v-model="tempUser.mobile_phone"
            @keyup.enter.native="updateUser">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.usermanager.email')"
            prop="email">
            <el-input
            type="text"
            v-model="tempUser.email"
            @keyup.enter.native="updateUser">
            </el-input>
        </el-form-item>
        </el-form>
        <div
        slot="footer"
        class="dialog-footer">
            <el-button @click="resetForm('tempUser')">{{$t('openatc.button.Cancel')}}</el-button>
            <el-button type="primary" @click="updateUser">{{$t('openatc.button.OK')}}</el-button>
        </div>
    </el-dialog>
    <choose-organization-dialog
      ref="chooseOrganization"
      @getCheckedPath = "getCheckedPath"
    ></choose-organization-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/11/01
*/
import {
  UpdateUsr, getRoles
} from '../../../api/user'
import {
  GetPassswordEncode
} from '../../../api/passwdAssest'
import chooseOrganizationDialog from '@/views/Organization/components/chooseOrganizationDialog'
export default {
  components: {chooseOrganizationDialog},
  data () {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        this.phone_status = true
      } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (reg.test(value)) {
          this.phone_status = true
          callback()
        } else {
          this.phone_status = false
          return callback(
            new Error('请输入正确的电话号码！')
          )
        }
      }
    }
    var checkEmail = (rule, value, callback) => {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        this.email_status = true
        return
        // return callback(new Error("邮箱不能为空"));
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          this.email_status = true
          callback()
        } else {
          this.email_status = false
          callback(
            new Error('请输入正确的电子邮箱！')
          )
        }
      }, 100)
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码！'))
      } else if (value !== this.tempUser.newpass) {
        callback(new Error('确认密码与密码不同！'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      email_status: true,
      phone_status: true,
      user: {}, // 修改之前的用户信息
      roles: [],
      roleNames: [],
      tempUser: {
        user_name: '',
        newpass: '',
        checkpass: '',
        organization: '',
        nick_name: '',
        mobile_phone: '',
        email: ''
      },
      rules: {
        user_name: [
          { message: '请输入', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        email: [{ validator: checkEmail, trigger: 'blur' }],
        mobile_phone: [{ validator: checkPhone, trigger: 'blur' }],
        checkpass: [
          {
            required: true,
            validator: validatePass2,
            trigger: 'blur'
          }
        ]
      },
      hidePassContain: true
    }
  },
  name: 'update',
  props: {},
  methods: {
    updateUser () {
      // 修改用户信息
      let _vue = this
      if (!this.phone_status || !this.email_status) {
        this.$message.error('错误的电话号码或者电子邮箱！')
        return
      }
      if (this.tempUser.newpass !== '') {
        if (this.tempUser.newpass !== this.tempUser.checkpass) {
          this.$message.error('确认密码与密码不同！')
          return
        }
        // 校验新密码与老密码是否一致
        let encodePass = GetPassswordEncode(this.tempUser.user_name, this.tempUser.newpass)
        if (encodePass === this.user.password) {
          this.$message.error('新密码不能与旧密码一致！')
          return
        }
      }
      if (this.roleNames.length === 0) {
        this.$message.error('请至少选择一个角色！')
        return
      }
      if (this.tempUser.newpass === '' && this.tempUser.nick_name === this.user.nick_name && this.tempUser.email === this.user.email && this.tempUser.mobile_phone === this.user.mobile_phone && this.tempUser.organization === this.user.organization && this.roleNames === this.user.roleNames) {
        this.$message.error('没有任何修改！')
        return
      }
      let params = this.getParams()
      UpdateUsr(
        this.tempUser.user_name,
        params
      ).then(data => {
        if (data.data.success !== true) {
          if (data.data.code === '3010') {
            this.$message.error('超级管理员角色不能被修改！')
            console.log(data.data.message)
            return
          }
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        let msg = '编辑成功！'
        this.dialogFormVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getAllUser()
          }
        })
      })
    },
    getParams () {
      let params = {}
      if (this.tempUser.newpass !== '') {
        params.password = this.tempUser.newpass
        this.tempUser.newpass = ''
      }
      if (this.tempUser.nick_name !== this.user.nick_name) {
        params.nick_name = this.tempUser.nick_name
      }
      if (this.tempUser.email !== this.user.email) {
        params.email = this.tempUser.email
      }
      if (this.tempUser.mobile_phone !== this.user.mobile_phone) {
        params.mobile_phone = this.tempUser.mobile_phone
      }
      if (this.tempUser.organization !== this.user.organization) {
        params.organization = this.tempUser.organization
      }
      params.roleNames = this.roleNames
      return params
    },
    closeFormDialog () {
      this.resetForm('tempUser')
    },
    onUpdateClick (user) {
      this.user = user
      this.tempUser.email = user.email
      this.tempUser.mobile_phone = user.mobile_phone
      this.tempUser.nick_name = user.nick_name
      this.tempUser.organization = user.organization
      this.tempUser.user_name = user.user_name
      this.roleNames = user.roleNames
      this.getAllRoles()
      // this.dialogFormVisible = !this.dialogFormVisible
    },
    getAllRoles () {
      getRoles().then(res => {
        if (!res.data.success) {
          if (res.data.code === '3008') {
            this.$message.error('没有权限访问!')
            return
          }
          this.$message.error(res.data.message)
          return
        }
        let rolesData = res.data.data
        if (rolesData !== undefined && rolesData.length !== 0) {
          this.roles = rolesData.map(obj => obj.name)
        }
        this.dialogFormVisible = !this.dialogFormVisible
      })
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
      this.$refs[formData].resetFields() // 表单重置
      this.hidePassContain = true
    },
    relateOrganization (path) {
      let chooseOrganization = this.$refs.chooseOrganization
      chooseOrganization.onClick(path)
    },
    getCheckedPath (path) {
      this.tempUser.organization = path
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.user-update .el-input__inner {
  height: 34px !important;
}
</style>
