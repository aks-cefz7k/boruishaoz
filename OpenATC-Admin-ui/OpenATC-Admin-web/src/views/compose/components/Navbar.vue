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
      <el-menu-item index="home" style="display:inline">
        <i class="iconfont icon-zhuye1"></i>{{$t('openatc.main.homepage')}}
      </el-menu-item>
      <el-menu-item index="device" style="display:inline">
        <i class="iconfont icon-shebeiguanli"></i>{{$t('openatc.main.devicemanager')}}
      </el-menu-item>
      <el-menu-item index="dutyroute" style="display:inline">
        <i class="iconfont icon-qinwuluxian"></i>{{$t('openatc.main.dutyroute')}}
      </el-menu-item>
      <el-menu-item index="greenwave" style="display:inline">
        <i class="iconfont icon-jingtailvbo"></i>{{$t('openatc.main.greenwaveoptimize')}}
      </el-menu-item>
      <el-menu-item index="bottleneckcontrol" style="display:inline">
        <i class="iconfont icon-pingjingkongzhi"></i>{{$t('openatc.main.bottleneckcontrol')}}
      </el-menu-item>
      <el-menu-item index="gis" style="display:inline">
        <el-dropdown trigger="click" @command="handleSelect">
          <span class="el-dropdown-link">
            <i class="iconfont icon-lukou"></i>{{$t('openatc.main.gis')}}
            <i class="el-icon-arrow-down menu-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="deviceState">{{this.$t('openatc.gis.deviceState') }}</el-dropdown-item>
            <el-dropdown-item command="dutyRoute">{{this.$t('openatc.gis.dutyRoute') }}</el-dropdown-item>
            <el-dropdown-item command="coordinateRoute">{{this.$t('openatc.gis.coordinateRoute') }}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
      <el-menu-item index="others" style="display:inline">
        <el-dropdown trigger="click" @command="handleSelect">
          <span class="el-dropdown-link">
            <i class="iconfont icon-pingjia"></i>{{$t('openatc.main.other')}}
            <i class="el-icon-arrow-down menu-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="operate">{{$t('openatc.main.operationrecord')}}</el-dropdown-item>
            <el-dropdown-item command="organization">{{$t('openatc.main.organization')}}</el-dropdown-item>
            <el-dropdown-item command="user">{{$t('openatc.main.usermanager')}}</el-dropdown-item>
            <el-dropdown-item command="jupyter">{{$t('openatc.main.script')}}</el-dropdown-item>
            <el-dropdown-item command="faultrecord">{{$t('openatc.main.faultrecord')}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
    </div>
    <div class="openatc-operate">
      <div class="admin">
        <el-dropdown trigger="click"
                     :hide-on-click= "false"
                     @command="handleCommand"
                     @visible-change="showInfo">
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
            <el-dropdown-item divided command="switchTheme">
              <el-dropdown trigger="click" @command="switchTheme">
                <span class="el-dropdown-link">
                  {{$t('openatc.main.skin')}}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="dark">{{$t('openatc.main.dark')}}</el-dropdown-item>
                  <el-dropdown-item command="light">{{$t('openatc.main.light')}}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              <br/>
              <el-dropdown trigger="click" @command="switchLanguage">
                <span class="el-dropdown-link">
                  {{$t('openatc.main.language')}}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="Ch">中文</el-dropdown-item>
                  <el-dropdown-item command="En">English</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-dropdown-item>
            <el-dropdown-item divided command="changepass">{{$t('openatc.main.changepass')}}</el-dropdown-item>
            <el-dropdown-item command="systemsettings">{{$t('openatc.main.systemsettings')}}</el-dropdown-item>
            <el-dropdown-item command="help">{{$t('openatc.main.help')}}</el-dropdown-item>
            <el-dropdown-item command="about">{{$t('openatc.main.about')}}</el-dropdown-item>
            <el-dropdown-item command="signout">{{$t('openatc.main.signout')}}</el-dropdown-item>
            <!-- <el-dropdown-item command="opentapd">跳转</el-dropdown-item> -->
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
      <el-drawer
        title="故障列表"
        :visible.sync="drawer"
        :with-header="true">
        <span>
          <el-card class="box-card" style="margin:10px 16px">
            <div slot="header" class="clearfix">
              <i class="el-icon-location-outline" type="primary"></i>
              <span>{{$t('openatc.faultrecord.deviceid')}}</span>
              <el-button style="float: right; padding: 3px 0" type="text">
                <i class="el-icon-close"></i>
              </el-button>
            </div>
            <div class="text item">
              <el-row :gutter="20" class="row-bg">
                <el-col :span="12">
                  <el-row :gutter="0">
                    <el-col :span="10">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.deviceid") }}:
                      </div>
                    </el-col>
                    <el-col :span="14">
                      <div class="grid-content bg-purple">
                        1111
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row :gutter="20"  class="row-bg">
                <el-col :span="12">
                  <el-row :gutter="0">
                    <el-col :span="10">
                      <div class="grid-content-label">
                        {{ $t("openatc.devicemanager.faultOccurtime") }}:
                      </div>
                    </el-col>
                    <el-col :span="14">
                      <div class="grid-content bg-purple">
                        1111
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row :gutter="20" class="row-bg">
                <el-col :span="12">
                  <el-row :gutter="0">
                    <el-col :span="10">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.fixdetail") }}:
                      </div>
                    </el-col>
                    <el-col :span="14">
                      <div class="grid-content bg-purple">
                        1111
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </span>
      </el-drawer>
    <div class="alarm-message">
      <el-badge is-dot class="item">
        <i class="el-icon-message-solid"></i>
      </el-badge>
    </div>
  </el-menu>
  <modifypasswd ref="modifypasswdChild"></modifypasswd>
  <versioninfo ref="versioninfoChild"></versioninfo>
  <SystemSettings ref="settinngChild"></SystemSettings>
  </div>
</template>
<script>
import router from '@/router'
import FaultEventData from '@/model/EventModal/faultData.js'
import modifypasswd from './modifyPasswd'
import versioninfo from './versionInfo'
import SystemSettings from './SystemSettings'
import { mapState } from 'vuex'
import { getInfo } from '@/api/login'
import { setLanguage, getTheme, setTheme } from '@/utils/auth'
import { getMessageByCode } from '@/utils/responseMessage'
import { SystemconfigApi } from '@/api/systemconfig.js'
export default {
  name: 'navbar',
  components: { modifypasswd, versioninfo, SystemSettings },
  data () {
    return {
      activeIndex: '',
      drawer: false,
      routerPath: {
        home: '/home',
        gis: '/gis',
        device: '/device',
        user: '/user',
        greenwave: '/greenWaveOptimize',
        operate: '/operaterecord',
        organization: '/organization',
        dutyroute: '/dutyroute',
        jupyter: '/jupyter',
        faultrecord: '/faultrecord',
        bottleneckcontrol: '/bottleneckControl'
      },
      roleType: ['', 'success', 'warning'],
      isShow: true,
      fromKstpPath: ['/greenWaveOptimizeNew', '/deviceNew', '/operaterecordNew', '/overviewNew/index'],
      language: 'Language',
      zh_handbook: '',
      en_handbook: ''
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
    this.handleFaultEventData(true)
  },
  mounted () {
    // 订阅故障测试
    this.FaultEventData = new FaultEventData()
    this.FaultEventData.Init(this.handleFaultEventData)
    if (getTheme() === 'dark') {
      require('../../../styles/dark/theme/element-dark.scss')
    } else {
      require('../../../styles/light/theme/element-light.scss')
    }
  },
  methods: {
    handleFaultEventData (data) {
      // debugger
    },
    handleJump (key) {
      if (key === 'deviceState' || key === 'dutyRoute' || key === 'coordinateRoute') {
        this.$store.dispatch('SetGisBizType', key)
        if (this.$route.path === this.routerPath.gis) {
          return false
        }
      }
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
        case 'changepass': this.modifyPasswd()
          break
        case 'help': this.showHelp()
          break
        case 'about': this.showVersion()
          break
        case 'signout': this.logout()
          break
        case 'systemsettings': this.showSettings()
          break
        case 'switchTheme':
          break
        case 'switchLanguage':
          break
        default: router.push({ path: '/' })
      }
    },
    getGisConfig () {
      return new Promise((resolve, reject) => {
        SystemconfigApi.GetSystemconfigByModule('system').then((data) => {
          if (data.data.success !== true) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          for (let config of data.data.data) {
            if (config['key'] === 'zh_handbook') {
              this.zh_handbook = config['value']
            }
            if (config['key'] === 'en_handbook') {
              this.en_handbook = config['value']
            }
          }
          resolve(data.data.data)
        })
      })
    },
    opentapd (url) {
      window.open(url)
    },
    async showHelp () {
      await this.getGisConfig()
      let url = this.zh_handbook
      if (this.$i18n.locale === 'en') {
        url = this.en_handbook
      }
      this.opentapd(url)
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
    showSettings () {
      let settinngChild = this.$refs.settinngChild
      settinngChild.show()
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
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
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
    switchTheme (command) {
      switch (command) {
        case 'dark':
          this.switchToDark()
          break
        case 'light':
          this.switchToLight()
          break
        default:
          console.log(command)
          break
      }
    },
    switchToDark () {
      setTheme('dark')
      location.reload()
    },
    switchToLight () {
      setTheme('light')
      location.reload()
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
 //  @import "../../../styles/theme/element-variables.scss";
.el-menu-demo {
  padding: 0 18px;
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
</style>
<style scoped>
.openatc-navbar >>> .iconfont {
    margin-right: 5px;
    text-align: center;
    font-size: 14px;
    vertical-align: top;
}
</style>
