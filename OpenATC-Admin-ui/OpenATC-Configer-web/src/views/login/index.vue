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
      <h3 class="title">{{$t('edge.login.title')}}</h3>
      <el-form-item prop="user_name">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input class="login-input" name="user_name" type="text" v-model="loginForm.user_name" auto-complete="on" :placeholder="$t('edge.login.username')" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"></svg-icon>
        </span>
        <el-input class="login-input" name="password" :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password" auto-complete="on"
          :placeholder="$t('edge.login.password')" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"></el-input>
          <span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>
      </el-form-item>
      <div>
        <el-checkbox class="expand-text" v-model="isExpand">{{$t('edge.login.networkparam')}}</el-checkbox>
        <!-- <div style="display: inline;">本地登陆</div> -->
        <!-- <div style="display: inline;">
          <svg-icon icon-class="xiala" v-show="!isExpand"></svg-icon>
          <svg-icon icon-class="shanghe" v-show="isExpand"></svg-icon>
        </div> -->
      </div>
      <div v-show="isExpand">
        <el-form-item prop="ip">
          <span class="text-container">IP</span>
          <el-input class="login-input" name="IP" type="text" v-model="loginForm.ip" auto-complete="on" placeholder="" />
        </el-form-item>
        <el-form-item prop="port">
          <span class="text-container">{{$t('edge.login.port')}}</span>
          <el-input class="login-input" name="port" type="text" v-model="loginForm.port" auto-complete="on" placeholder="" />
        </el-form-item>
        <el-form-item prop="protocol">
          <span class="text-container">{{$t('edge.login.protocol')}}</span>
          <el-select v-model="loginForm.protocol" placeholder="">
            <el-option label="scp" value="scp"></el-option>
            <el-option label="ocp" value="ocp"></el-option>
          </el-select>
          <!-- <el-input class="login-input" name="protocol" type="text" v-model="loginForm.protocol" auto-complete="on" placeholder="" /> -->
        </el-form-item>
        <!-- <el-form-item prop="agentid">
          <span class="text-container">编号</span>
          <el-input class="login-input" name="agentid" type="text" v-model="loginForm.agentid" auto-complete="on" placeholder="" />
        </el-form-item> -->
        <div class="expand-text" style="float: right;" @click="resetParams">
            {{$t('edge.login.restoredefaultparameters')}}
        </div>
      </div>
        <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
          {{$t('edge.login.login')}}
        </el-button>
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
          new Error(this.$t('edge.login.enterip'))
        )
      }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value) || value === 'localhost') {
        this.ip_status = true
        callback()
      } else {
        this.ip_status = false
        return callback(
          new Error(this.$t('edge.login.enterlegalip'))
        )
      }
    }
    var checkPort = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error(this.$t('edge.login.enterpport'))
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
            new Error(this.$t('edge.login.entercorrectport'))
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
          { required: true, trigger: 'blur', message: this.$t('edge.login.usernamenotnull') }
        ],
        password: [
          { required: true, trigger: 'blur', message: this.$t('edge.login.passnotnull') }
        ],
        protocol: [
          { required: true, message: this.$t('edge.login.selectagreement'), trigger: 'blur' }
        ],
        ip: [
          { validator: checkIp, trigger: 'blur' }
        ],
        port: [
          { validator: checkPort, trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: this.$t('edge.login.enterdeviceid'), trigger: 'blur' }
        ]
      },
      loading: false,
      pwdType: 'password',
      language: 'Language',
      isExpand: false
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
  mounted () {
    this.showLoginInfo()
  },
  methods: {
    showLoginInfo () {
      // 从存储中获取登陆相关信息，并显示在表单
      let info = this.loginInterface.getLoginInfo()
      if (info) {
        info = JSON.parse(info)
        this.loginForm.user_name = info.user_name
        this.loginForm.ip = info.ip
        this.loginForm.port = info.port
        this.loginForm.protocol = info.protocol
      }
    },
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
                this.$message.error(this.$t('edge.login.loginerror'))
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
              this.loginInterface.setLoginInfo(this.loginForm) // 存储登陆信息
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
</style>
