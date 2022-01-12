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
  <div v-if="isShow" class="openatc-navbar">
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" active-text-color='$--color-primary' @select="handleSelect">
    <div class="openatc-main">OpenATC-Admin</div>
    <div class="openatc-line"></div>
    <div class="openatc-menu">
      <el-menu-item index="home" style="display:inline">{{$t('openatc.main.homepage')}}</el-menu-item>
      <el-menu-item index="gis" style="display:inline">{{$t('openatc.main.gis')}}</el-menu-item>
      <el-menu-item index="device" style="display:inline">{{$t('openatc.main.devicemanager')}}</el-menu-item>
      <el-menu-item index="user" style="display:inline">{{$t('openatc.main.usermanager')}}</el-menu-item>
      <el-menu-item index="greenwave" style="display:inline">{{$t('openatc.main.greenwaveoptimize')}}</el-menu-item>
      <el-menu-item index="operate" style="display:inline">{{$t('openatc.main.operationrecord')}}</el-menu-item>
      <el-menu-item index="organization" style="display:inline">{{$t('openatc.main.organization')}}</el-menu-item>
      <el-menu-item index="dutyroute" style="display:inline">{{$t('openatc.main.dutyroute')}}</el-menu-item>
    </div>
    <div class="openatc-operate">
      <div class="admin">
        <el-dropdown trigger="click" @command="handleCommand" @visible-change="showInfo">
          <span class="el-dropdown-link">
            {{userInfo.user_name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item disabled>
              <div class="user-name">{{userInfo.user_name}}</div>
              <div style="margin-top: 10px;">
                <span v-for="(data, index) in userInfo.roleNames" :key="index">
                  <el-tag size="medium" :type="roleType[index]">{{ data }}</el-tag>
                </span>
              </div>
            </el-dropdown-item>
            <el-dropdown-item divided disabled>
              <div class="organization">
                <div class="laber-name">{{$t('openatc.main.organization')}}</div>
                <div class="laber-value">{{userInfo.organization}}</div>
              </div>
              <div class="real-name">
                <div class="laber-name">{{$t('openatc.main.realname')}}</div>
                <div class="laber-value">{{userInfo.nick_name}}</div>
              </div>
            </el-dropdown-item>
            <el-dropdown-item divided disabled>
              <div class="organization">
                <div class="laber-name">{{$t('openatc.main.telno')}}</div>
                <div class="laber-value">{{userInfo.mobile_phone}}</div>
              </div>
              <div class="real-name">
                <div class="laber-name">{{$t('openatc.main.email')}}</div>
                <div class="laber-value">{{userInfo.email}}</div>
              </div>
            </el-dropdown-item>
            <el-dropdown-item divided command="a">{{$t('openatc.main.changepass')}}</el-dropdown-item>
            <el-dropdown-item command="b">{{$t('openatc.main.about')}}</el-dropdown-item>
            <el-dropdown-item command="c">{{$t('openatc.main.signout')}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div class="switch-language">
        <el-dropdown trigger="click" @command="switchLanguage">
          <span class="el-dropdown-link">
            {{$t('openatc.main.language')}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="Ch">中文</el-dropdown-item>
            <el-dropdown-item command="En">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
  </el-menu>
  <modifypasswd ref="modifypasswdChild"></modifypasswd>
  <versioninfo ref="versioninfoChild"></versioninfo>
  <!-- <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
    <div>123456</div>
</el-dialog> -->
  </div>
</template>
<script>
import router from '@/router'
import modifypasswd from './modifyPasswd'
import versioninfo from './versionInfo'
import { mapState } from 'vuex'
import { getInfo } from '@/api/login'
import { setLanguage } from '@/utils/auth'
export default {
  name: 'navbar',
  components: { modifypasswd, versioninfo },
  data () {
    return {
      activeIndex: '',
      routerPath: {
        home: '/home',
        gis: '/gis',
        device: '/device',
        user: '/user',
        greenwave: '/greenWaveOptimize',
        operate: '/operaterecord',
        organization: '/organization',
        dutyroute: '/dutyroute'
      },
      roleType: ['', 'success', 'warning'],
      isShow: true,
      fromKstpPath: ['/greenWaveOptimizeNew', '/deviceNew', '/operaterecordNew', '/overviewNew/index'],
      language: 'Language'
    }
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        this.setActive(val.path)
      },
      // 深度观察监听
      deep: true
    }
  },
  computed: {
    ...mapState({
      tittle: state => state.globalVariable.logo,
      userInfo: state => state.user.userInfo,
      operateId: state => state.globalVariable.operateId,
      devicePath: state => state.globalVariable.devicePath
    }),
    userInfo: {
      get: function () {
        return this.$store.state.user.userInfo
      },
      set: function (newValue) {
        this.$store.state.user.userInfo = newValue
      }
    }
  },
  created () {
    this.setActive()
    if (this.fromKstpPath.indexOf(this.devicePath) !== -1) {
      this.isShow = false
    }
  },
  methods: {
    handleJump (key) {
      router.push({
        path: this.routerPath[key]
      })
    },
    handleSelect (key) {
      this.handleSaveTscParam()
      this.handleJump(key)
      this.$store.dispatch('SetOperatedDeviceId', undefined)
    },
    handleCommand (command) {
      switch (command) {
        case 'a': this.modifyPasswd()
          break
        case 'b': this.showVersion()
          break
        case 'c': this.logout()
          break
        default: router.push({ path: '/' })
      }
    },
    showVersion () {
      let versionInfoChild = this.$refs.versioninfoChild
      versionInfoChild.showMessage()
    },
    logout () {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    modifyPasswd () {
      let modifypasswdChild = this.$refs.modifypasswdChild
      modifypasswdChild.onPasswdClick(this.userInfo.user_name)
    },
    setActive (path) {
      // 设置当前激活导航栏
      let currPath = path || this.$route.path
      for (let [key, value] of Object.entries(this.routerPath)) {
        if (currPath === value) {
          this.activeIndex = key
        }
      }
    },
    handleSaveTscParam () {
      if (!this.operateId) return
      // 存储baseEdge的参数tscParam进openATC维护的映射结构
      const tscParam = this.$store.getters.tscParam
      const curPath = this.$store.getters.curPath
      this.$store.dispatch('SaveMultDeviceData', [this.operateId, tscParam, curPath])
    },
    showInfo (val) {
      if (!val) return
      getInfo().then(data => {
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          return
        }
        this.userInfo = data.data.data
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
      location.reload()
    },
    switchToEngLish () {
      this.$i18n.locale = 'en'
      setLanguage(this.$i18n.locale)
      location.reload()
    }
  }
}
</script>
<style lang="scss">
 @import "../../../styles/theme/element-variables.scss";
.el-menu-demo {
  padding: 0 18px;
}
.openatc-main {
  float:left;
  height: 50px;
  line-height: 50px;
  font-family: MicrosoftYaHei;
  font-size: 22px;
  font-weight: bold;
  font-stretch: normal;
  letter-spacing: 0px;
  color: $--color-text-primary;
}
.openatc-line {
  float:left;
  margin-left: 20px;
  margin-top: 19px;
  width: 1px;
  height: 14px;
  background-color: $--color-text-placeholder;
}
.openatc-menu {
  float:left;
  font-family: MicrosoftYaHei;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: $--color-primary;
}
.openatc-operate {
  cursor: pointer;
  float: right;
  height: 50px;
  padding-top: 17px;
  .admin {
    float: left;
  }
}
.el-menu-demo  {
.el-dropdown-menu {
  width: 228px;
  .el-dropdown-menu__item {
    color: $--color-text-regular;
  }
  .label {
    font-size: 12px;
    color: $--color-text-secondary;
    margin-bottom: 12px;
  }
  .content {
    font-size: 12px;
    color: $--color-text-primary;
  }
  .user {
    font-family: MicrosoftYaHei;
    padding: 0 24px;
    .name {
      font-size: 20px;
      color: $--color-primary;
      margin-bottom: 14px;
      margin-top: 13px;
    }
  }
  .message {
    font-family: MicrosoftYaHei;
    padding: 16px 24px 0 24px;
    margin-bottom: 10px;
    .email {
      margin-top: 22px;
    }
    .content {
      font-size: 14px;
    }
  }
}
}
.user-name {
  width: 58px;
  height: 21px;
  margin-top: 10px;
  font-family: MicrosoftYaHei;
  font-size: 20px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: $--color-primary;
}
.organization {
  margin-top: 8px;
}
.real-name {
  margin-top: 14px;
  margin-bottom: 10px;
}
.laber-name {
  width: 48px;
  height: 13px;
  font-family: MicrosoftYaHei;
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: $--color-text-secondary;
}
.laber-value {
  margin-top: 5px;
  font-family: MicrosoftYaHei;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: $--color-text-primary;
}
.switch-language {
    cursor: pointer;
    margin-top: 17px;
    margin-right: 30px;
    float: right;
  }
</style>
