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
        title="编辑用户"
        :visible.sync="dialogFormVisible"
        width="700px"
        @close='closeFormDialog'>
        <el-form
        class="dialog-footer"
        ref="tempUser"
        :model="tempUser"
        :rules="rules"
        label-width="15%">
        <el-form-item
          label="用户名称"
          prop="user_name">
          <el-input
            disabled
            type="text"
            v-model="tempUser.user_name">
          </el-input>
        </el-form-item>
        <el-form-item
            label="角色"
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
            label="真实姓名"
            prop="nick_name">
            <el-input
            type="text"
            v-model="tempUser.nick_name">
            </el-input>
        </el-form-item>
        <el-form-item
            label="组织机构"
            prop="organization">
            <el-input
            type="text"
            v-model="tempUser.organization">
            </el-input>
        </el-form-item>
        <el-form-item
            label="电话"
            prop="mobile_phone">
            <el-input
            type="text"
            v-model="tempUser.mobile_phone">
            </el-input>
        </el-form-item>
        <el-form-item
            label="邮箱"
            prop="email">
            <el-input
            type="text"
            v-model="tempUser.email">
            </el-input>
        </el-form-item>
        <el-form-item
            label="到期时间"
            prop="expirationDate">
            <el-date-picker
              style="width: 100%;"
              v-model="tempUser.expirationDate"
              align="right"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions">
            </el-date-picker>
        </el-form-item>
        </el-form>
        <div
        slot="footer"
        class="dialog-footer">
            <el-button @click="resetForm('tempUser')">取消</el-button>
            <el-button type="primary" @click="updateUser">确定</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  UpdateUsr, getRoles
} from '../../../api/user'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  components: {
  },
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
    var checkName = (rule, value, callback) => {
      if (!value.match(/^[a-zA-Z0-9_]{1,}$/)) {
        callback(new Error('用户名只能输入字母、数字或下划线！'))
      } else if (value === '') {
        callback(new Error('请输入用户名！'))
      }
    }
    // var checkDate = (rule, value, callback) => {
    //   if (value === null) {
    //     callback(new Error('请选择密码到期时间！'))
    //   }
    // }
    return {
      dialogFormVisible: false,
      email_status: true,
      phone_status: true,
      user: {}, // 修改之前的用户信息
      roles: [],
      roleNames: [],
      tempUser: {
        user_name: '',
        organization: '',
        nick_name: '',
        mobile_phone: '',
        email: '',
        expirationDate: null
      },
      rules: {
        user_name: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        email: [{ validator: checkEmail, trigger: 'blur' }],
        mobile_phone: [{ validator: checkPhone, trigger: 'blur' }]
        // ,
        // expirationDate: [{ validator: checkDate, trigger: 'blur' }]
      },
      hidePassContain: true,
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now()
        }
      },
      oldUserName: ''
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
      if (this.roleNames.length === 0) {
        this.$message.error('请至少选择一个角色！')
        return
      }
      if (!this.tempUser.user_name.match(/^[a-zA-Z0-9_]{1,}$/)) {
        this.$message.error('用户名只能输入字母、数字或下划线！')
        return
      }
      // if (this.tempUser.expirationDate == null) {
      //   this.$message.error('请选择密码到期时间！')
      //   return
      // }
      if (this.tempUser.user_name === this.user.user_name && this.tempUser.nick_name === this.user.nick_name && this.tempUser.email === this.user.email && this.tempUser.mobile_phone === this.user.mobile_phone && this.tempUser.organization === this.user.organization && this.roleNames === this.user.roleNames && this.tempUser.expirationDate === this.user.expirationDate) {
        this.$message.error('没有任何修改！')
        return
      }
      let params = this.getParams()
      UpdateUsr(
        this.oldUserName,
        params
      ).then(data => {
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
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
      if (this.tempUser.user_name !== this.user.user_name) {
        params.user_name = this.tempUser.user_name
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
      if (this.tempUser.expirationDate !== this.user.expirationDate) {
        params.expiration_time = this.tempUser.expirationDate
      }
      params.roleNames = this.roleNames
      return params
    },
    closeFormDialog () {
      this.resetForm('tempUser')
    },
    onUpdateClick (user) {
      this.user = user
      this.oldUserName = user.user_name
      this.tempUser.email = user.email
      this.tempUser.mobile_phone = user.mobile_phone
      this.tempUser.nick_name = user.nick_name
      this.tempUser.organization = user.organization
      this.tempUser.user_name = user.user_name
      this.tempUser.expirationDate = user.expirationDate
      this.roleNames = user.roleNames
      this.getAllRoles()
    },
    getAllRoles () {
      getRoles().then(res => {
        if (!res.data.success) {
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
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
      this.$refs[formData].resetFields() // 表单重置
      this.hidePassContain = true
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.user-update .el-input__inner {
  height: 34px !important;
}
// 自定义校验规则的labal加上*号
.user-update .dateLabel .el-form-item__label:before{
    content: '*';
    color: #f56c6c;
    margin-right: 4px;
}
</style>
