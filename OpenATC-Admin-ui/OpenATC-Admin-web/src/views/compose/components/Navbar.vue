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
      <el-menu-item index="gis" style="display:inline" v-if="isShowGisMenu">
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
            </el-dropdown-item>
            <el-dropdown-item command="switchTheme">
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
        :title="$t('openatc.faultrecord.faultlist')"
        :visible.sync="drawer"
        :modal="false"
        :with-header="true">
        <!-- <span> -->
          <div class="empty" v-show="faultData.length === 0">
            <img src="../../../../static/img/noMessage.png">
            <div class="noInfo">没有新消息</div>
          </div>
          <el-card v-for="(fault,index) in faultData" :key="index" class="box-card" style="margin:10px 16px">
            <!-- <div slot="header" class="clearfix">
              <i class="el-icon-location-outline" type="primary"></i>
              <span>{{$t('openatc.faultrecord.deviceid')}}</span>
              <el-button style="float: right; padding: 3px 0" type="text">
                <i class="el-icon-close"></i>
              </el-button>
            </div> -->
            <div class="text item">
              <el-row :gutter="20" class="row-bg">
                <el-col :span="24">
                  <el-row :gutter="0">
                    <el-col :span="4">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.eportingmodule") }}:
                      </div>
                    </el-col>
                    <el-col :span="20">
                      <div class="grid-content bg-purple">
                       {{formatterModel(fault.model)}}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row :gutter="20" class="row-bg">
                <el-col :span="24">
                  <el-row :gutter="0">
                    <el-col :span="4">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.roadname") }}:
                      </div>
                    </el-col>
                    <el-col :span="20">
                      <div class="grid-content bg-purple">
                        {{fault.name?fault.name:fault.agentid}}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row :gutter="20"  class="row-bg">
                <el-col :span="24">
                  <el-row :gutter="0">
                    <el-col :span="4">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.eventtype") }}:
                      </div>
                    </el-col>
                    <el-col :span="20">
                      <div class="grid-content bg-purple">
                        {{ formatterInfotype(fault.eventType)}}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row :gutter="20" class="row-bg">
                <el-col :span="24">
                  <el-row :gutter="0">
                    <el-col :span="4">
                      <div class="grid-content-label">
                        {{ $t("openatc.faultrecord.detaileddescription") }}:
                      </div>
                    </el-col>
                    <el-col :span="20">
                      <div class="grid-content bg-purple">
                        {{ formmatter(fault) }}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
            <div  class="btnBottom">
              <el-button size="mini" icon="el-icon-circle-check" type="text" @click="cancels(fault)">{{$t('openatc.faultrecord.confirm')}}</el-button>
              <el-button type="text" icon="el-icon-remove-outline" size="mini" @click="confirmeds(fault)">{{$t('openatc.faultrecord.neglect')}}</el-button>
            </div>
          </el-card>
        <!-- </span> -->
      </el-drawer>
    <div class="alarm-message" @click="drawer = true, isDot=false">
      <el-badge :is-dot="isDot" class="item">
        <img style ="width: 18px;" src="../../../assets/home/notice.png">
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
import { GetFaultRange, searchRoadName, enumerateCheck } from '@/api/fault.js'
import modifypasswd from './modifyPasswd'
import versioninfo from './versionInfo'
import SystemSettings from './SystemSettings'
import { mapState } from 'vuex'
import { getInfo } from '@/api/login'
import { setLanguage, getTheme, setTheme } from '@/utils/auth'
import { getMessageByCode } from '@/utils/responseMessage'
import { SystemconfigApi } from '@/api/systemconfig.js'
import { formatFaultDescValue } from '@/utils/fault.js'
export default {
  name: 'navbar',
  components: { modifypasswd, versioninfo, SystemSettings },
  data () {
    return {
      faultData: [],
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
      en_handbook: '',
      pageNum: '1',
      pageSize: '10',
      enumerate: '0',
      isCurrentFault: false,
      infotype: '',
      model: '',
      notify: undefined,
      faultDescValue: '',
      roadName: '',
      faultDatas: '',
      isDot: false,
      faultCodeMap: new Map([[101, 'can总线通信故障'], [102, '黄闪器故障'], [103, '特征参数故障'], [104, '故障检测板不在线'], [105, '继电器未吸合'], [201, '灯控板ID故障'], [202, '灯控板脱机'], [203, '无红灯亮起'], [204, '红绿同亮'], [205, '绿冲突'], [206, '红灯灯电压故障'], [207, '黄灯灯电压故障'], [208, '绿灯灯电压故障'], [209, '红灯灯功率故障'], [210, '黄灯灯功率故障'], [211, '绿灯灯功率故障'], [212, '灯组故障'], [213, '车检器故障'], [214, '灯控板插槽编码错误'], [215, '灯控板插头编码错误'], [216, '本机灯控板数量未配置'], [301, '车检板未初始化'], [302, '车检板脱机'], [303, '车辆检测器短路'], [304, '车辆检测器断路'], [401, 'I/O板未初始化'], [402, 'I/O板脱机']]),
      faultCodeMapEn: new Map([[101, 'CanBus Fault'], [102, 'Yellow Flasher Fault'], [103, 'TZParam Fault'], [104, 'FaultDet Offline'], [105, 'Relay Not Work'], [201, 'LampBoard ID Fault'], [202, 'LampBoard Offline'], [203, 'No Red Lamp Is On'], [204, 'Red And Green Conflict'], [205, 'Green Conflict'], [206, 'Red Lamp Voltage Fault'], [207, 'Yellow Lamp Voltage Fault'], [208, 'Green Lamp Voltage Fault'], [209, 'Red Lamp Lamp Power Fault'], [210, 'Yellow Lamp Lamp Power Fault'], [211, 'Green Lamp Lamp Power Fault'], [212, 'Lamp pack failure'], [213, 'Car detector failure'], [214, 'Lamp Control Board Slot Code Error'], [215, 'Code Error Of Lamp Control Board Plug'], [216, 'The Number Of Lamp Control Board Not be Configed for The Master'], [301, 'VehDetBoard Is Not Init'], [302, 'VehDetBoard Is Offline'], [303, 'VehDetector Short Circiut'], [304, 'VehDetector Open  Circiut'], [401, 'I/O Board Is Not Init'], [402, 'I/O Board Offline']]),
      // faultLevelMap: new Map([[1, '一般故障'], [2, '降级故障'], [3, '严重故障']]),
      // faultLevelMapEn: new Map([[1, 'General failure'], [2, 'Degradation failure'], [3, 'Serious failure']]),
      TZParamSubtypeMap: new Map([[0, ''], [1, '特征参数不存在'], [2, '特征参数文件不可读'], [3, '特征参数人为修改'], [4, '特征参数文件打开失败'], [5, '特征参数文件更新失败'], [6, '信号机地址码校验失败'], [7, '特征参数内容格式错误'], [8, 'USB挂载失败']]),
      TZParamSubtypeMapEn: new Map([[0, ''], [1, 'Non-existent'], [2, 'File Is Unreadable'], [3, 'File Artificial Changes'], [4, 'File Open Fail'], [5, 'File Update Fail'], [6, 'File Check SiteID Fail'], [7, 'Format Error'], [8, 'USB Mount Fail']]),
      greenLampSubtypeMap: new Map([[0, ''], [1, '未输出有效电压'], [2, '输出电压低于输入电压过多'], [3, '输出电压高于输入电压'], [4, '关闭输出但实际电压仍然输出'], [5, '关闭输出但实际电压部分输出'], [6, '线路残留电压过高']]),
      greenLampSubtypeMapEn: new Map([[0, ''], [1, 'Output Volatage Is Fail'], [2, 'Output Volatage Is Low'], [3, 'Output Volatage Is High'], [4, 'Off Output Volatage Is high'], [5, 'Off Output Volatage Is low'], [6, 'Residual Voltage Is Over-High']]),
      lampPowerSubtypeMap: new Map([[0, ''], [1, '功率异常增加'], [2, '功率异常减少'], [3, '功率无输出'], [4, '关闭状态有功率输出']]),
      lampPowerSubtypeMapEn: new Map([[0, ''], [1, 'Output Power Is Up'], [2, 'Output Power Is Down'], [3, 'Output Power Is Zero'], [4, 'Off Output Power Is High']]),
      lampgroupfailureMap: new Map([[0, ''], [1, '红灯故障'], [2, '黄灯故障'], [3, '绿灯故障']]),
      lampgroupfailureMapEn: new Map([[0, ''], [1, 'Red light failure'], [2, 'Yellow light failure'], [3, 'Green light failure']])
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
      devicePath: state => state.globalVariable.devicePath,
      isShowGisMenu: state => state.globalVariable.isShowGisMenu
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
    this.getUntreated()
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
    formatterModel (val) {
      let res = ''
      if (val === 'asc') {
        res = this.$t('openatc.faultrecord.asc')
      } else if (val === 'patterncalc') {
        res = this.$t('openatc.faultrecord.patterncalc')
      } else if (val === 'monitor') {
        res = this.$t('openatc.faultrecord.monitor')
      }
      return res
    },
    formatterInfotype (val) {
      let res = ''
      if (val === 'status/fault') {
        res = this.$t('openatc.faultrecord.statusfault')
      }
      return res
    },
    formmatter (data) {
      let value = ''
      if (this.$i18n.locale === 'en') {
        if (data.m_wFaultType === 103) {
          value = data.m_byFaultDescValue + this.faultCodeMapEn.get(data.m_wFaultType) + this.TZParamSubtypeMapEn.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 208 || data.m_wFaultType === 207 || data.m_wFaultType === 206) {
          value = data.m_byFaultDescValue + this.faultCodeMapEn.get(data.m_wFaultType) + this.greenLampSubtypeMapEn.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 211 || data.m_wFaultType === 210 || data.m_wFaultType === 209) {
          value = data.m_byFaultDescValue + this.faultCodeMapEn.get(data.m_wFaultType) + this.lampPowerSubtypeMapEn.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 212) {
          value = data.m_byFaultDescValue + this.faultCodeMapEn.get(data.m_wFaultType) + this.lampgroupfailureMapEn.get(data.m_wSubFaultType)
        } else {
          value = data.m_byFaultDescValue + this.faultCodeMapEn.get(data.m_wFaultType)
          return value
        }
      } else {
        if (data.m_wFaultType === 103) {
          value = data.m_byFaultDescValue + this.faultCodeMap.get(data.m_wFaultType) + this.TZParamSubtypeMap.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 208 || data.m_wFaultType === 207 || data.m_wFaultType === 206) {
          value = data.m_byFaultDescValue + this.faultCodeMap.get(data.m_wFaultType) + this.greenLampSubtypeMap.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 211 || data.m_wFaultType === 210 || data.m_wFaultType === 209) {
          value = data.m_byFaultDescValue + this.faultCodeMap.get(data.m_wFaultType) + this.lampPowerSubtypeMap.get(data.m_wSubFaultType)
          return value
        } else if (data.m_wFaultType === 212) {
          value = data.m_byFaultDescValue + this.faultCodeMap.get(data.m_wFaultType) + this.lampgroupfailureMap.get(data.m_wSubFaultType)
          return value
        } else {
          value = data.m_byFaultDescValue + this.faultCodeMap.get(data.m_wFaultType)
          return value
        }
      }
    },
    handleFaultEventData (data) {
      this.isDot = true
      this.faultDatas = data
      searchRoadName(data.agentid).then(j => {
        if (j.data.data.name) {
          this.roadName = j.data.data.name
        } else {
          this.roadName = j.data.data.agentid
        }
      })
      this.infotype = this.formatterInfotype(data.infotype)
      this.model = this.formatterModel(data.model)
      if (this.$i18n.locale === 'en') {
        if (data.data.m_FaultDeque[0].m_wFaultType === 103) {
          this.faultDescValue = this.faultCodeMapEn.get(data.data.m_FaultDeque[0].m_wFaultType) + this.TZParamSubtypeMapEn.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data.m_FaultDeque[0])
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 208 || data.data.m_FaultDeque[0].m_wFaultType === 207 || data.data.m_FaultDeque[0].m_wFaultType === 206) {
          this.faultDescValue = this.faultCodeMapEn.get(data.data.m_FaultDeque[0].m_wFaultType) + this.greenLampSubtypeMapEn.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data.m_FaultDeque[0])
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 211 || data.data.m_FaultDeque[0].m_wFaultType === 210 || data.data.m_FaultDeque[0].m_wFaultType === 209) {
          this.faultDescValue = this.faultCodeMapEn.get(data.data.m_FaultDeque[0].m_wFaultType) + this.lampPowerSubtypeMapEn.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data.m_FaultDeque[0])
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 212) {
          this.faultDescValue = this.faultCodeMapEn.get(data.data.m_FaultDeque[0].m_wFaultType) + this.lampgroupfailureMapEn.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data.m_FaultDeque[0])
        } else {
          this.faultDescValue = this.faultCodeMapEn.get(data.data.m_FaultDeque[0].m_wFaultType)
        }
      } else {
        if (data.data.m_FaultDeque[0].m_wFaultType === 103) {
          this.faultDescValue = this.faultCodeMap.get(data.data.m_FaultDeque[0].m_wFaultType) + this.TZParamSubtypeMap.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data)
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 208 || data.data.m_FaultDeque[0].m_wFaultType === 207 || data.data.m_FaultDeque[0].m_wFaultType === 206) {
          this.faultDescValue = this.faultCodeMap.get(data.data.m_FaultDeque[0].m_wFaultType) + this.greenLampSubtypeMap.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data)
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 211 || data.data.m_FaultDeque[0].m_wFaultType === 210 || data.data.m_FaultDeque[0].m_wFaultType === 209) {
          this.faultDescValue = this.faultCodeMap.get(data.data.m_FaultDeque[0].m_wFaultType) + this.lampPowerSubtypeMap.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data)
        } else if (data.data.m_FaultDeque[0].m_wFaultType === 212) {
          this.faultDescValue = this.faultCodeMap.get(data.data.m_FaultDeque[0].m_wFaultType) + this.lampgroupfailureMap.get(data.data.m_FaultDeque[0].m_wSubFaultType) + formatFaultDescValue(data.data)
        } else {
          this.faultDescValue = this.faultCodeMap.get(data.data.m_FaultDeque[0].m_wFaultType)
        }
      }
      if (this.notify) {
        this.notify.close()
      }
      this.open2()
    },
    open2 () {
      const h = this.$createElement
      this.notify = this.$notify({
        title: this.$t('openatc.faultrecord.realtimealert'),
        iconClass: 'warnIcon',
        onClose: () => {
        },
        dangerouslyUseHTMLString: true,
        message: h('div', {style: {'width': '236px'}}, [
          h('div', {style: {padding: '5px 0'}}, [
            h('span', {class: 'msgTitle'}, `${this.$t('openatc.faultrecord.eportingmodule')}:`),
            h('span', {class: 'msgValue'}, `${this.model}`)
          ]),
          h('div', [
            h('span', {class: 'msgTitles'}, `${this.$t('openatc.faultrecord.roadname')}:`),
            h('span', {class: 'msgValue'}, `${this.roadName}`)
          ]),
          h('div', {class: 'msgTitle'}, [
            h('span', `${this.$t('openatc.faultrecord.eventtype')}:`),
            h('span', {class: 'msgValue'}, `${this.infotype}`)
          ]),
          h('div', {class: 'msgTitleEventtype'}, [
            h('span', `${this.$t('openatc.faultrecord.detaileddescription')}:`),
            h('span', {class: 'msgValue'}, `${this.faultDescValue}`)
          ]),
          h('div', {class: 'borderStyle'}, [
            h('el-button', {props: {size: 'mini', type: 'text', icon: 'el-icon-circle-check'}, on: {click: this.confirm}}, `${this.$t('openatc.faultrecord.confirm')}`),
            h('el-button', {props: {size: 'mini', type: 'text', icon: 'el-icon-remove-outline'}, on: {click: this.cancel}}, `${this.$t('openatc.faultrecord.neglect')}`)
          ])
        ]),
        duration: 0,
        position: 'bottom-right'
      })
    },
    cancel () {
      this.enumerate = '1'
      enumerateCheck(this.faultDatas.agentid, this.faultDatas.data.m_FaultDeque[0].m_wFaultID.toString(), this.enumerate).then(data => {
      })
      this.notify.close()
    },
    confirm () {
      this.enumerate = '2'
      enumerateCheck(this.faultDatas.agentid, this.faultDatas.data.m_FaultDeque[0].m_wFaultID.toString(), this.enumerate).then(data => {
      })
      this.notify.close()
    },
    cancels (data) {
      this.enumerate = '2'
      enumerateCheck(data.agentid, data.m_wFaultID.toString(), this.enumerate).then(data => {
        if (data.data.success) {
          this.getUntreated()
        }
      })
    },
    confirmeds (data) {
      this.enumerate = '1'
      enumerateCheck(data.agentid, data.m_wFaultID.toString(), this.enumerate).then(data => {
        if (data.data.success) {
          this.getUntreated()
        }
      })
    },
    getUntreated () {
      let reqData = {
        'enumerate': '0',
        'isCurrentFault': true
      }
      GetFaultRange(reqData).then(data => {
        if (data.data.success) {
          this.faultData = data.data.data.content
        }
      })
    },
    handleJump (key) {
      if (key === 'deviceState' || key === 'dutyRoute' || key === 'coordinateRoute') {
        this.$store.dispatch('SetGisBizType', key)
        if (this.$route.path === this.routerPath.gis) {
          return false
        }
      } else {
        this.$store.dispatch('SetGisBizType', 'deviceState')
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
    getSystemConfig () {
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
      await this.getSystemConfig()
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
        this.FaultEventData.UnInit() // 取消故障事件订阅
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
.warnIcon {
  height: 24px;
  width: 24px;
  background-size: 24px 24px;
  background-repeat: no-repeat;
  background-position:center center;
  background-image: url('../../../assets/home/trouble.png');
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
