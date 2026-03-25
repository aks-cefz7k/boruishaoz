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
            :label="$t('openatc.devicemanager.IP')"
            prop="login_ip_limit">
            <el-input
            type="text"
            v-model="tempUser.login_ip_limit"
            @keyup.enter.native="createUser">
            </el-input>
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
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  components: {chooseOrganizationDialog},
  data () {
    var checkIp = (rule, value, callback) => {
      // if (value === '') {
      //   return callback(
      //     new Error(this.$t('openatc.devicemanager.enterIp'))
      //   )
      // }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value)) {
        this.ip_status = true
        callback()
      } else {
        this.ip_status = false
        return callback(
          new Error(this.$t('openatc.devicemanager.correctIp'))
        )
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(this.$t('openatc.usermanager.enterpass')))
      } else if (value !== this.tempUser.newpass) {
        callback(new Error(this.$t('openatc.usermanager.enterpassdiffpass')))
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
            new Error(this.$t('openatc.usermanager.correctphone'))
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
          callback(new Error(this.$t('openatc.usermanager.correctemail')))
        }
      }, 100)
    }
    var checkName = (rule, value, callback) => {
      if (!value.match(/^[a-zA-Z0-9_]{1,}$/)) {
        callback(new Error(this.$t('openatc.usermanager.lettersnumbersunderscores')))
      } else if (value === '') {
        callback(new Error(this.$t('openatc.usermanager.enterusername')))
      }
    }
    return {
      dialogFormVisible: false,
      email_status: true,
      phone_status: true,
      roles: [],
      roleNames: ['ordinary user'],
      tempUser: {
        user_name: '',
        newpass: '',
        checkpass: '',
        nick_name: '',
        login_ip_limit: '',
        email: '',
        mobile_phone: '',
        organization: ''
      },
      rules: {
        user_name: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        login_ip_limit: [
          { validator: checkIp, trigger: 'blur' }
        ],
        // roles: [
        //   { required: true, message: '请至少选择一个角色', trigger: 'blur' }
        // ],
        newpass: [
          { required: true, message: this.$t('openatc.usermanager.enterpassword'), trigger: 'blur' }
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
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
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
        this.$message.error(this.$t('openatc.usermanager.nameandpasswordnotempty'))
        return
      }
      if (!this.phone_status || !this.email_status) {
        this.$message.error(this.$t('openatc.usermanager.wrongphoneemail'))
        return
      }
      if (this.tempUser.newpass !== this.tempUser.checkpass) {
        this.$message.error(this.$t('openatc.usermanager.enterpassdiffpass'))
        return
      }
      if (this.roleNames.length === 0) {
        this.$message.error(this.$t('openatc.usermanager.selectonerole'))
        return
      }
      if (!this.tempUser.user_name.match(/^[a-zA-Z0-9_]{1,}$/)) {
        this.$message.error(this.$t('openatc.usermanager.lettersnumbersunderscores'))
        return
      }
      AddUsr(
        this.tempUser.user_name,
        this.tempUser.nick_name,
        this.tempUser.email,
        this.tempUser.mobile_phone,
        this.tempUser.newpass,
        this.roleNames,
        this.tempUser.organization,
        this.tempUser.login_ip_limit
      ).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let msg = this.$t('openatc.common.addsuccess')
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
