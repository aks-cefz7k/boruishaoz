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
        <el-input class="login-input" name="user_name" type="text" v-model="loginForm.user_name" auto-complete="on" :placeholder="$t('openatc.login.username')" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"></svg-icon>
        </span>
        <el-input class="login-input" name="password" :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password" auto-complete="on"
          :placeholder="$t('openatc.login.password')"></el-input>
          <span class="show-pwd" @click="showPwd">
             <svg-icon :icon-class="pwdType === 'password' ? 'eye' : 'eye-open'" />
          </span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
          {{$t('openatc.login.login')}}
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import { login } from '../../api/login'
import { SetSimuUserKey, setLanguage, getLanguage, setTheme } from '@/utils/auth'
import { SystemconfigApi } from '@/api/systemconfig.js'
import { getMessageByCode } from '@/utils/responseMessage'
// import axios from 'axios'
import { clearCrossFilter } from '@/utils/crossFilterMgr'

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
          { required: true, trigger: 'blur', message: this.$t('openatc.login.usernamenotnull') }
        ],
        password: [
          { required: true, trigger: 'blur', message: this.$t('openatc.login.passnotnull') }
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
                  this.$message.error(this.$t('openatc.login.passerror'))
                  this.loading = false
                  return
                }
                if (data.data.code === '3001') {
                  this.$message.error(this.$t('openatc.login.usernamenotexist'))
                  this.loading = false
                  return
                }
                this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
                this.loading = false
                return
              }
              this.loading = false
              SetSimuUserKey(this.loginForm.user_name)
              this.loginInterface.loginSucess(data.data.data.token)
              this.loadDefaultConfig()
              clearCrossFilter() // 清除路口管理过滤条件
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
    loadLanguageConfig () {
      // 从接口获取默认语言并设置
      SystemconfigApi.GetSystemconfigByModule('language').then((data) => {
        let res = data.data
        if (!res.success) {
          throw new Error('get language error')
        } else {
          for (let config of data.data.data) {
            if (config['value'] === 'zh') {
              this.$i18n.locale = 'zh'
            } else if (config['value'] === 'en') {
              this.$i18n.locale = 'en'
            } else {
              this.setLanguageFromStorage()
            }
            setLanguage(this.$i18n.locale)
          }
        }
      })
    },
    setLanguageFromStorage () {
      // 从浏览器存储中读取语言配置
      let language = getLanguage()
      if (language === 'zh') {
        this.$i18n.locale = 'zh'
      } else if (language === 'en') {
        this.$i18n.locale = 'en'
      }
    },
    loadThemeConfig () {
      // 从接口获取默认皮肤并设置
      SystemconfigApi.GetSystemconfigByModule('theme').then((data) => {
        let res = data.data
        if (!res.success) {
          throw new Error('get theme error')
        } else {
          for (let config of data.data.data) {
            if (config['value'] === 'dark') {
              setTheme('dark')
              require('../../styles/dark/index.scss')
            } else if (config['value'] === 'light') {
              setTheme('light')
              require('../../styles/light/index.scss')
            }
          }
        }
      })
    },
    loadMenuConfig () {
      // 从接口获取是否显示地图菜单（可扩展为其他菜单是否显示）
      SystemconfigApi.GetSystemconfigByModule('gis').then((data) => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let controlMenu = {
          gis: false
        }
        for (let config of data.data.data) {
          if (config['key'] === 'isShowGisMenu') {
            if (config['value'] === 'true') {
              controlMenu.gis = true
            } else {
              controlMenu.gis = false
            }
          }
        }
        this.$store.dispatch('SetMenuVisible', controlMenu)
      })
    },
    loadDefaultConfig  () {
      // 加载系统默认配置
      this.loadLanguageConfig()
      this.loadThemeConfig()
      this.loadMenuConfig()
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
// .switch-language {
//   cursor:pointer;
//   margin-top: 20px;
//   margin-right: 20px;
//   float: right;
// }
// .login-container {
//     .el-input {
//       display: inline-block;
//       height: 47px;
//       width: 85%;
//       input {
//         background: $--color-white;
//         border: 0px;
//         -webkit-appearance: none;
//         border-radius: 0px;
//         padding: 12px 5px 12px 15px;
//         color: $--color-text-primary;
//         height: 47px;
//         &:-webkit-autofill {
//           -webkit-text-fill-color: rgb(0, 0, 0) !important;
//         }
//         .el-input__inner {
//           -webkit-appearance: none;
//           background-color: $--color-white;
//           background-image: none;
//           border-radius: 4px;
//           border: 0px solid #dcdfe6;
//           -webkit-box-sizing: border-box;
//           box-sizing: border-box;
//           color: $--color-text-regular;
//           display: inline-block;
//           font-size: inherit;
//           height: 40px;
//           line-height: 40px;
//           outline: 0;
//           padding: 0 15px;
//           -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
//           transition: border-color .2s cubic-bezier(.645,.045,.355,1);
//           width: 100%;
//         }
//       }
//     }
//     .el-form-item {
//       border: 1px solid rgba(255, 255, 255, 0.1);
//       background: $--color-white;
//       border-radius: 5px;
//       color: $--color-primary;
//     }
//   }

//   .login-container {
//     position: fixed;
//     height: 100%;
//     width: 100%;
//     background-color: #f2f9ff;
//     .login-form {
//       position: absolute;
//       left: 0;
//       right: 0;
//       //width: 33%;
//       width: 20%;
//       min-width: 400px;
//       padding: 35px 35px 15px 35px;
//       margin: 120px auto;
//     }
//     .tips {
//       font-size: 14px;
//       color: $--color-text-secondary;
//       margin-bottom: 10px;
//       span {
//         &:first-of-type {
//           margin-right: 16px;
//         }
//       }
//     }
//     .svg-container {
//       padding: 6px 5px 6px 15px;
//       color: $--color-primary;
//       vertical-align: middle;
//       width: 30px;
//       display: inline-block;
//       &_login {
//         font-size: 20px;
//       }
//     }
//     .title {
//       font-size: 26px;
//       font-weight: 400;
//       color: $--color-primary;
//       margin: 0px auto 40px auto;
//       text-align: center;
//       font-weight: bold;
//       font-stretch: normal;
//     }
//     .show-pwd {
//       position: absolute;
//       right: 10px;
//       top: 7px;
//       font-size: 16px;
//       color: #889aa4;
//       cursor: pointer;
//       user-select: none;
//     }
//   }
</style>
