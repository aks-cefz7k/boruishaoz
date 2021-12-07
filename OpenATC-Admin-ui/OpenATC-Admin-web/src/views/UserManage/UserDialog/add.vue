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
  <div class="user-add">
    <el-dialog
      :title="$t('openatc.usermanager.adduser')"
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
          :label="$t('openatc.usermanager.username')"
          prop="user_name">
          <el-input
            type="text"
            v-model="tempUser.user_name"
            @keyup.enter.native="createUser"
            placeholder="">
          </el-input>
        </el-form-item>
        <el-form-item
        :label="$t('openatc.usermanager.password')"
        prop="newpass">
        <el-input
          type="password"
          v-model="tempUser.newpass"
          @keyup.enter.native="createUser"
          onkeyup="this.value=this.value.replace(/\s+/g,'')">
        </el-input>
      </el-form-item>
        <el-form-item
          :label="$t('openatc.usermanager.confirmpassword')"
          prop="checkpass">
          <el-input
            type="password"
            v-model="tempUser.checkpass"
            @keyup.enter.native="createUser"
            placeholder=""
            onkeyup="this.value=this.value.replace(/\s+/g,'')">
          </el-input>
        </el-form-item>
        <el-form-item
        :label="$t('openatc.usermanager.role')"
        required>
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
          @keyup.enter.native="createUser">
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('openatc.usermanager.organization')"
        prop="organization">
        <el-input
          type="text"
          v-model="tempUser.organization"
          @focus="relateOrganization(tempUser.organization)"
          @keyup.enter.native="createUser">
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('openatc.usermanager.telno')"
        prop="mobile_phone">
        <el-input
          type="text"
          v-model="tempUser.mobile_phone"
          @keyup.enter.native="createUser">
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('openatc.usermanager.email')"
        prop="email">
        <el-input
          type="text"
          v-model="tempUser.email"
          @keyup.enter.native="createUser">
        </el-input>
      </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer">
          <el-button @click="resetForm('tempUser')">{{$t('openatc.button.Cancel')}}</el-button>
          <el-button type="primary" @click="createUser">{{$t('openatc.button.OK')}}</el-button>
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
import { AddUsr, getRoles } from '../../../api/user'
import chooseOrganizationDialog from '@/views/Organization/components/chooseOrganizationDialog'
export default {
  components: {chooseOrganizationDialog},
  data () {
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码！'))
      } else if (value !== this.tempUser.newpass) {
        callback(new Error('确认密码与密码不同！'))
      } else {
        callback()
      }
    }
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
        // return callback(new Error("邮箱不能为空"))
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          this.email_status = true
          callback()
        } else {
          this.email_status = false
          callback(new Error('请输入正确的电子邮箱！'))
        }
      }, 100)
    }
    var checkName = (rule, value, callback) => {
      if (!value.match(/^[a-zA-Z0-9_]{1,}$/)) {
        callback(new Error('用户名只能输入字母、数字或下划线！'))
      } else if (value === '') {
        callback(new Error('请输入用户名！'))
      }
    }
    return {
      dialogFormVisible: false,
      email_status: true,
      phone_status: true,
      roles: [],
      roleNames: ['普通用户'],
      tempUser: {
        user_name: '',
        newpass: '',
        checkpass: '',
        nick_name: '',
        email: '',
        mobile_phone: '',
        organization: ''
      },
      rules: {
        user_name: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        // roles: [
        //   { required: true, message: '请至少选择一个角色', trigger: 'blur' }
        // ],
        newpass: [
          { required: true, message: '请输入密码！', trigger: 'blur' }
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
      }
    }
  },
  name: 'add',
  props: {},
  watch: {
    deep: true
  },
  created () {
    // this.getAllRoles()
  },
  methods: {
    getAllRoles () {
      getRoles().then(res => {
        if (!res.data.success) {
          if (res.data.code === '3008') {
            this.$message.error(this.$t('openatc.common.authtip'))
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
    createUser () {
      // 添加新用户
      if (
        this.tempUser.user_name === '' ||
        this.tempUser.newpass === ''
      ) {
        this.$message.error('用户名密码不能为空！')
        return
      }
      if (!this.phone_status || !this.email_status) {
        this.$message.error('错误的电话号码或者电子邮箱！')
        return
      }
      if (this.tempUser.newpass !== this.tempUser.checkpass) {
        this.$message.error('确认密码与密码不同！')
        return
      }
      if (this.roleNames.length === 0) {
        this.$message.error('请至少选择一个角色！')
        return
      }
      if (!this.tempUser.user_name.match(/^[a-zA-Z0-9_]{1,}$/)) {
        this.$message.error('用户名只能输入字母、数字或下划线！')
        return
      }
      AddUsr(
        this.tempUser.user_name,
        this.tempUser.nick_name,
        this.tempUser.email,
        this.tempUser.mobile_phone,
        this.tempUser.newpass,
        this.roleNames,
        this.tempUser.organization
      ).then(data => {
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        let msg = '新增成功！'
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$parent.getAllUser()
          }
        })
      })
    },
    closeFormDialog () {
      this.resetForm()
    },
    onAddClick () {
      this.getAllRoles()
      // this.dialogFormVisible = !this.dialogFormVisible
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
      this.$refs.tempUser.resetFields() // 表单重置
    },
    relateOrganization (orgPath) {
      let chooseOrganization = this.$refs.chooseOrganization
      chooseOrganization.onClick(orgPath)
    },
    getCheckedPath (path) {
      this.tempUser.organization = path
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.user-add .el-input__inner {
  height: 34px !important;
}
</style>
