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
      title="添加用户"
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
            type="text"
            v-model="tempUser.user_name"
            placeholder="">
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
          <el-button type="primary" @click="createUser">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { AddUsr, getRoles } from '../../../api/user'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
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
    // var checkDate = (rule, value, callback) => {
    //   if (value === null) {
    //     callback(new Error('请选择密码到期时间！'))
    //   }
    // }
    return {
      dialogFormVisible: false,
      email_status: true,
      phone_status: true,
      roles: [],
      roleNames: ['ordinary user'],
      tempUser: {
        user_name: '',
        nick_name: '',
        email: '',
        mobile_phone: '',
        organization: '',
        expirationDate: null
      },
      rules: {
        user_name: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        // roles: [
        //   { required: true, message: '请至少选择一个角色', trigger: 'blur' }
        // ],
        email: [{ validator: checkEmail, trigger: 'blur' }],
        mobile_phone: [{ validator: checkPhone, trigger: 'blur' }]
        // ,
        // expirationDate: [{ validator: checkDate, trigger: 'blur' }]
      },
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now()
        }
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
      AddUsr(
        this.tempUser.user_name,
        this.tempUser.nick_name,
        this.tempUser.email,
        this.tempUser.mobile_phone,
        this.roleNames,
        this.tempUser.organization,
        this.tempUser.expirationDate
      ).then(data => {
        if (!data.data.success) {
          if (data.data.code === '3003') {
            this.$message.error('用户名已存在！')
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
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
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
      this.$refs.tempUser.resetFields() // 表单重置
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.user-add .el-input__inner {
  height: 34px !important;
}
// 自定义校验规则的labal加上*号
.user-add .dateLabel .el-form-item__label:before{
    content: '*';
    color: #f56c6c;
    margin-right: 4px;
}
</style>
