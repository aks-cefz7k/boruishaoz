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
      <h3 class="title">信号机设备配置</h3>
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
          placeholder="密码" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"></el-input>
          <span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>
      </el-form-item>
      <div>
        <el-checkbox class="expand-text" v-model="isExpand">本地登陆</el-checkbox>
        <!-- <div style="display: inline;">本地登陆</div> -->
        <!-- <div style="display: inline;">
          <svg-icon icon-class="xiala" v-show="!isExpand"></svg-icon>
          <svg-icon icon-class="shanghe" v-show="isExpand"></svg-icon>
        </div> -->
      </div>
      <div v-show="!isExpand">
        <el-form-item prop="ip">
          <span class="text-container">IP</span>
          <el-input class="login-input" name="IP" type="text" v-model="loginForm.ip" auto-complete="on" placeholder="" />
        </el-form-item>
        <el-form-item prop="port">
          <span class="text-container">端口</span>
          <el-input class="login-input" name="port" type="text" v-model="loginForm.port" auto-complete="on" placeholder="" />
        </el-form-item>
        <el-form-item prop="protocol">
          <span class="text-container">协议</span>
          <el-select v-model="loginForm.protocol" placeholder="" style="width:85%">
            <el-option label="scp" value="scp"></el-option>
            <el-option label="ocp" value="ocp"></el-option>
          </el-select>
          <!-- <el-input class="login-input" name="protocol" type="text" v-model="loginForm.protocol" auto-complete="on" placeholder="" /> -->
        </el-form-item>
        <el-form-item prop="agentid">
          <span class="text-container">编号</span>
          <el-input class="login-input" name="agentid" type="text" v-model="loginForm.agentid" auto-complete="on" placeholder="" />
        </el-form-item>
        <div class="expand-text" style="margin-left: 245px;" @click="resetParams">
            恢复默认参数
        </div>
      </div>
      <el-form-item>
        <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
  <!--修改密码弹框-->
  <changePass ref="changepassChild"></changePass>
</div>
</template>

<script>
import { login } from '../../api/login'
import { SetSimuUserKey, setLanguage, setToken, setIframdevid } from '@/utils/auth'
import changePass from '../layout/components/ChangePass'
import { AddDevice } from '@/api/control'
// import axios from 'axios'

export default {
  name: 'login',
  components: {
    changePass
  },
  props: {
    loginInterface: {
      type: Object
    }
  },
  data () {
    var checkIp = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入IP')
        )
      }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value) || value === 'localhost') {
        this.ip_status = true
        callback()
      } else {
        this.ip_status = false
        return callback(
          new Error('请输入合法的IP！')
        )
      }
    }
    var checkPort = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入端口')
        )
      }
      const portReg = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/
      setTimeout(() => {
        if (portReg.test(value)) {
          this.port_status = true
          callback()
        } else {
          this.port_status = false
          callback(
            new Error('请输入正确的端口！')
          )
        }
      }, 100)
    }
    return {
      loginForm: {
        user_name: '',
        password: '',
        ip: 'localhost',
        port: '8880',
        protocol: 'ocp',
        agentid: '0'
      },
      loginRules: {
        user_name: [
          { required: true, trigger: 'blur', message: '用户名不能为空！' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '密码不能为空！' }
        ],
        protocol: [
          { required: true, message: '请选择协议', trigger: 'blur' }
        ],
        ip: [
          { validator: checkIp, trigger: 'blur' }
        ],
        port: [
          { validator: checkPort, trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: '请输入设备ID', trigger: 'blur' }
        ]
      },
      loading: false,
      pwdType: 'password',
      language: 'Language',
      isExpand: true
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
            this.loginForm.password
          )
            .then(data => {
              if (data.data.success !== true) {
                this.$message.error('登陆错误!')
                this.loading = false
                return
              }
              this.loading = false
              if (data.data.data.expire && data.data.data.token !== undefined) {
                setToken(data.data.data.token)
                this.modifyPasswd()
                return
              }
              SetSimuUserKey(this.loginForm.user_name)
              this.loginInterface.loginSucess(data.data.data.token)
              this.addDevice() // 注册设备
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
    },
    modifyPasswd () {
      let changepassChild = this.$refs.changepassChild
      changepassChild.onChangePassClick(this.loginForm.user_name)
    },
    handleExpand () {
      this.isExpand = !this.isExpand
    },
    resetParams () {
      this.loginForm.ip = 'localhost'
      this.loginForm.port = '8880'
      this.loginForm.protocol = 'ocp'
      this.loginForm.agentid = '0'
    },
    addDevice () {
      let deviceInfo = {}
      deviceInfo.protocol = this.loginForm.protocol
      deviceInfo.ip = this.loginForm.ip
      deviceInfo.port = this.loginForm.port
      deviceInfo.type = 'asc'
      deviceInfo.agentid = this.loginForm.agentid
      setIframdevid(this.loginForm.agentid)
      AddDevice(deviceInfo).then(res => {
        if (!res.data.success) {
          console.log(new Error(res.data.message))
          this.$message({
            message: res.data.message,
            type: 'error',
            duration: 1 * 1000
          })
        }
      })
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
    .text-container {
      padding: 6px 0px 0px 15px;
      font-family: SourceHanSansCN-Regular;
      font-size: 14px;
      font-weight: normal;
      font-stretch: normal;
      letter-spacing: 0px;
      color: #1890ff;
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
  .expand-text {
    position: relative;
    top: -10px;
    // margin-left: 280px;
    font-family: SourceHanSansCN-Regular;
    font-size: 13px;
    font-weight: normal;
    font-stretch: normal;
    letter-spacing: 0px;
    color: #1890ff;
    cursor: pointer;
  }
</style>
