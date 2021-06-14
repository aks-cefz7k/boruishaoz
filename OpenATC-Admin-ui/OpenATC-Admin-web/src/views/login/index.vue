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
  <div class="login-container">
    <!-- <div class="switch-language">
      <el-dropdown trigger="click" @command="switchLanguage">
        <span class="el-dropdown-link">
          language<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="Ch">中文</el-dropdown-item>
          <el-dropdown-item command="En">English</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div> -->
    <el-form class="login-form" auto-complete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left">
      <h3 class="title">OpenATC-Admin</h3>
      <el-form-item prop="user_name">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input class="login-input" name="user_name" type="text" v-model="loginForm.user_name" auto-complete="on" placeholder="用户名" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"></svg-icon>
        </span>
        <el-input class="login-input" name="password" :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password" auto-complete="on"
          placeholder="密码"></el-input>
          <span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import { login } from '../../api/login'
import { SetSimuUserKey, setLanguage } from '@/utils/auth'
// import axios from 'axios'

export default {
  name: 'login',
  props: {
    loginInterface: {
      type: Object
    }
  },
  data () {
    return {
      loginForm: {
        user_name: '',
        password: '',
        vcode: '',
        vcodeKey: ''
      },
      loginRules: {
        user_name: [
          { required: true, trigger: 'blur', message: '用户名不能为空！' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '密码不能为空！' }
        ],
        vcode: [{ required: true, trigger: 'blur', message: 'Invalid VCode' }]
      },
      loading: false,
      pwdType: 'password',
      vcodeEnable: false,
      language: 'Language'
    }
  },
  created () {
    // if (getHost() === null || getHost() === undefined || getHost() === '') {
    //   axios.get('./servConfig.json').then(val => {
    //     let str = val.data.servIp
    //     if (str === undefined || str === null) {
    //       setHost('')
    //     }
    //     setHost(str)
    //   })
    // }
  },
  methods: {
    showPwd () {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin () {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          login(
            this.loginForm.user_name,
            this.loginForm.password,
            this.loginForm.vcodeKey,
            this.loginForm.vcode
          )
            .then(data => {
              if (data.data.success !== true) {
                if (data.data.code === '3009') {
                  this.$message.error('密码错误！')
                  this.loading = false
                  return
                }
                if (data.data.code === '3001') {
                  this.$message.error('用户名不存在！')
                  this.loading = false
                  return
                }
                this.$message.error(data.data.message)
                console.log(data.data.message)
                this.loading = false
                return
              }
              this.loading = false
              SetSimuUserKey(this.loginForm.user_name)
              this.loginInterface.loginSucess(data.data.data.token)
            })
            .catch(err => {
              this.loading = false
              this.$message.error(err)
              this.loginInterface.loginFailed(err)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    switchLanguage (command) {
      switch (command) {
        case 'Ch':
          this.switchToChinese()
          break
        case 'En':
          this.switchToEngLish()
          break
        default:
          console.log(command)
          break
      }
    },
    switchToChinese () {
      this.$i18n.locale = 'zh'
      setLanguage(this.$i18n.locale)
    },
    switchToEngLish () {
      this.$i18n.locale = 'en'
      setLanguage(this.$i18n.locale)
    }
  }
}
</script>

<style lang="scss">
.switch-language {
  cursor:pointer;
  margin-top: 20px;
  margin-right: 20px;
  float: right;
}
.login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      input {
        background: #ffffff;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: #333333;
        height: 47px;
        &:-webkit-autofill {
          -webkit-text-fill-color: rgb(0, 0, 0) !important;
        }
        .el-input__inner {
          -webkit-appearance: none;
          background-color: #fff;
          background-image: none;
          border-radius: 4px;
          border: 0px solid #dcdfe6;
          -webkit-box-sizing: border-box;
          box-sizing: border-box;
          color: #606266;
          display: inline-block;
          font-size: inherit;
          height: 40px;
          line-height: 40px;
          outline: 0;
          padding: 0 15px;
          -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
          transition: border-color .2s cubic-bezier(.645,.045,.355,1);
          width: 100%;
        }
      }
    }
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: #ffffff;
      border-radius: 5px;
      color: #1890ff;
    }
  }

  .login-container {
    position: fixed;
    height: 100%;
    width: 100%;
    background-color: #f2f9ff;
    .login-form {
      position: absolute;
      left: 0;
      right: 0;
      //width: 33%;
      width: 20%;
      min-width: 400px;
      padding: 35px 35px 15px 35px;
      margin: 120px auto;
    }
    .tips {
      font-size: 14px;
      color: #999999;
      margin-bottom: 10px;
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }
    .svg-container {
      padding: 6px 5px 6px 15px;
      color: #1890ff;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
      &_login {
        font-size: 20px;
      }
    }
    .title {
      font-size: 26px;
      font-weight: 400;
      color: #1890ff;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
      font-family: SourceHanSansCN-Bold;
      font-stretch: normal;
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: #889aa4;
      cursor: pointer;
      user-select: none;
    }
  }
</style>
