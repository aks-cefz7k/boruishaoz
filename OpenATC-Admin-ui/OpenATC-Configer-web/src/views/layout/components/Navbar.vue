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
    <Messagebox :visible="readDiologVisible" :text="`${$t('edge.main.readDevice')}${copiedAgentid} ${$t('edge.main.readPattern')}`" @cancle="cancleRead" @ok="handleRead"/>
    <ImportTempDialog ref="importTemp" v-if="importVisible" :imortVisible="importVisible" @closeImportTemp="closeImportTemp"/>
    <el-menu
      v-if="!hideMenu && !graphicMode"
      class="navbar"
      mode="horizontal"
    >
      <hamburger
        class="hamburger-container"
        :toggleClick="toggleSideBar"
        :isActive="sidebar.opened"
      ></hamburger>
      <!-- <breadcrumb></breadcrumb> -->
       <!-- <div class="navbar-agentid">
        <span class="agent-id">{{'设备ID：' + agentid}}</span>
       </div> -->
      <div class="operation-button">
        <div v-show="isShowMenu" style="display: inline;">
          <el-select v-model="value" :placeholder="$t('edge.common.select')" size="mini">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="$t('edge.main.options' + item.label)"
              :value="item.value">
            </el-option>
          </el-select>
            <el-button type="primary" @click="upload" size="mini">{{$t('edge.main.upload')}}</el-button>
            <el-button type="primary" @click="download" size="mini">{{$t('edge.main.download')}}</el-button>
            <span class="dividing-line"></span>
            <el-dropdown split-button placement="bottom-end" size="mini" type="primary" @click="importtemplate" @command="handleCommand">
              {{$t('edge.main.importtemplate')}}
              <el-dropdown-menu slot="dropdown" style="width: 110px">
                <el-dropdown-item command="import" style="line-height: 32px;text-align: center;">
                  {{$t('edge.main.import')}}
                </el-dropdown-item>
                <el-dropdown-item command="export" style="line-height: 32px;text-align: center;">
                  {{$t('edge.main.export')}}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <!-- <el-button type="primary" @click="importtemplate" size="mini">{{$t('edge.main.importtemplate')}}</el-button> -->
            <el-button type="primary" @click="copy" size="mini">{{$t('edge.main.copy')}}</el-button>
            <!-- <el-dropdown split-button placement="bottom-end" size="mini" type="primary" @click="copy" @command="handleCommand">
              {{$t('edge.main.copy')}}
              <el-dropdown-menu slot="dropdown" style="width: 110px">
                <el-dropdown-item command="import" style="line-height: 32px;text-align: center;">
                  {{$t('edge.main.import')}}
                </el-dropdown-item>
                <el-dropdown-item command="export" style="line-height: 32px;text-align: center;">
                  {{$t('edge.main.export')}}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown> -->
            <el-button type="primary" @click="read" size="mini" :disabled="copiedTscParam === null">{{$t('edge.main.read')}}</el-button>
           </div>
            <el-dropdown trigger="click" @command="handleLogout" @visible-change="showInfo" style="margin-left: 15px;" v-show="isShowLogout">
              <span class="el-dropdown-link">
                {{userInfo.user_name}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item disabled>
                  <div class="user-name">{{userInfo.user_name}}</div>
                  <div class="pass-expire" v-show="passIsExpire">{{$t('edge.main.passwordexpiration')}} {{userInfo.expiration_time}}</div>
                  <div style="margin-top: 10px;">
                    <span v-for="(data, index) in userInfo.roleNames" :key="index">
                      <el-tag size="medium" :type="roleType[index]">{{ data }}</el-tag>
                    </span>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item divided disabled>
                  <div class="organization">
                    <div class="laber-name">{{$t('edge.main.organization')}}</div>
                    <div class="laber-value">{{userInfo.organization}}</div>
                  </div>
                  <div class="real-name">
                    <div class="laber-name">{{$t('edge.main.realname')}}</div>
                    <div class="laber-value">{{userInfo.nick_name}}</div>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item divided disabled>
                  <div class="organization">
                    <div class="laber-name">{{$t('edge.main.phone')}}</div>
                    <div class="laber-value">{{userInfo.mobile_phone}}</div>
                  </div>
                  <div class="real-name">
                    <div class="laber-name">{{$t('edge.main.email')}}</div>
                    <div class="laber-value">{{userInfo.email}}</div>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item divided command="a">{{$t('edge.main.changepass')}}</el-dropdown-item>
                <!-- <el-dropdown-item command="help">{{$t('edge.main.help')}}</el-dropdown-item> -->
                <el-dropdown-item command="about">{{$t('edge.main.about')}}</el-dropdown-item>
                <el-dropdown-item command="b">{{$t('edge.main.exit')}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
      </div>
      <!-- <div class="dividing-line"></div> -->
      <div class="switch-language" v-show="isShowLogout">
        <el-dropdown trigger="click" @command="switchLanguage">
          <span class="el-dropdown-link">
            {{language}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="Ch">中文</el-dropdown-item>
            <el-dropdown-item command="En">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div class="sloat-icon" v-show="isShowSwitchBtn">
        <el-tooltip :content="$t('edge.main.graphics')" placement="bottom" effect="dark">
          <el-button type="text" @click="clickSwitchIcon(true)"><i class="iconfont icon-tuxingjiemian" :class="{'choosedIcon': isShowGui, 'defaultIcon': !isShowGui}"></i></el-button>
        </el-tooltip>
        <el-tooltip :content="$t('edge.main.text')" placement="bottom" effect="dark">
          <el-button type="text" @click="clickSwitchIcon(false)"><i class="iconfont icon-wenzijiemian" :class="{'choosedIcon': !isShowGui, 'defaultIcon': isShowGui}"></i></el-button>
        </el-tooltip>
         <el-tooltip :content="$t('edge.main.help')" placement="bottom" effect="dark">
          <el-button type="text" @click="showHelp()"><i class="iconfont icon-bangzhu defaultIcon"></i></el-button>
        </el-tooltip>
      </div>
    </el-menu>
    <el-dialog
      :title="$t('edge.plan.tip')"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <input
        type="file"
        id="file"
      />
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">{{$t('edge.common.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="readAsText"
        >{{$t('edge.common.confirm')}}</el-button>
      </span>
    </el-dialog>
    <!--修改密码弹框-->
    <changePass ref="changepassChild"></changePass>
    <versioninfo ref="versioninfoChild"></versioninfo>
  </div>
</template>

<script>
import router from '@/router'
import { mapGetters, mapState } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { uploadTscParam, downloadTscParam, uploadSingleTscParam, downloadSingleTscParam, checkCode } from '@/api/param'
import { setLanguage } from '@/utils/auth'
import Messagebox from '@/components/MessageBox/index'
import ImportTempDialog from '../../importTempDialog/index'
import { getInfo } from '@/api/login'
import changePass from './ChangePass'
import versioninfo from './versionInfo'
import { getErrorMesZh, getErrorMesEn } from '../../../utils/errorcode.js'
import { getControSource, getOverLap, getTypeOptions, getEtypeOptions } from '@/utils/channeldesc.js'

import { getMessageByCode } from '@/utils/responseMessage'
export default {
  components: {
    Breadcrumb,
    Hamburger,
    Messagebox,
    ImportTempDialog,
    changePass,
    versioninfo
  },
  data () {
    return {
      dialogVisible: false,
      phaseNotZero: false, // 判断必须有一个非零相位
      phaseExceed: false, // 判断一个环最多16个相位
      planNotZero: false, // 判断必须有一个plan
      patternNotZero: false, // 判断必须有一个pattern
      patternCycleEqual: true, // 校验环周期时长是否相等
      dataNotZero: false, // 判断必须有一个data
      dateIsAll: false, // 判断日期必须为全年
      planDate: false, // 校验plan里的时刻是否合法
      patternRing: false, // 判断方案里的环的时间是否相等
      dataPlan: false, // 判断日期里的计划是否为空
      planPattern: false, // 判断计划里的方案是否为空
      overlapRules: false, // 判断跟随相位里的母相位是否为空
      manualpanelIsNull: false, // 判断手动面板数据是否为空
      deviceinfo: false, // 校验设备信息的规则
      splitCheck: true,
      singleDownloadNotZero: false, // 判断单独下载数据是否为空
      type: false,
      language: 'Language',
      loading: {},
      options: [{
        value: 'all',
        // label: '全部'
        label: '1'
      }, {
        value: 'phase',
        // label: '相位'
        field: 'phaseList',
        label: '2'
      }, {
        value: 'overlap',
        // label: '跟随相位'
        field: 'overlaplList',
        label: '3'
      }, {
        value: 'pattern',
        // label: '方案'
        field: 'patternList',
        label: '4'
      }, {
        value: 'plan',
        // label: '计划'
        field: 'planList',
        label: '5'
      }, {
        value: 'date',
        // label: '日期'
        field: 'dateList',
        label: '6'
      }
      // {
      //   value: 'channel',
      //   // label: '通道'
      //   label: '7'
      // }, {
      //   value: 'detecter',
      //   // label: '检测器'
      //   label: '8'
      // }, {
      //   value: 'peddetecter',
      //   // label: '行人检测器'
      //   label: '9'
      // }
      ],
      value: 'all',
      notify: undefined, // 用于判断关闭多个显示的提示框
      readDiologVisible: false,
      curCopyAgentid: '', // 提示框显示复制的设备id
      copiedAgentid: '', // 缓存方案的设备id
      importVisible: false,
      // userInfo: {},
      roleType: ['', 'success', 'warning'],
      passIsExpire: false, // 密码是否即将过期
      isShowLogout: true,
      isShowMenu: false,
      planName: '',
      typeOptions: [],
      isShowSwitchBtn: false
    }
  },
  computed: {
    agentid: function () {
      return this.$store.state.agent.agentid
    },
    ...mapGetters([
      'sidebar',
      'avatar'
    ]),
    ...mapState({
      copiedTscParam: state => state.globalParam.copiedTscParam,
      userInfo: state => state.user.userInfo,
      hideMenu: state => state.globalParam.hideMenu,
      graphicMode: state => state.globalParam.graphicMode,
      isShowGui: state => state.globalParam.isShowGui
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
  watch: {
    $route: {
      handler: function (val, oldVal) {
        if (val.path.includes('example')) {
          this.isShowMenu = true
        } else {
          this.isShowMenu = false
        }
        if (val.path.includes('overview')) {
          this.isShowSwitchBtn = true
        } else {
          this.isShowSwitchBtn = false
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  mounted () {
    this.globalParamModel = this.$store.getters.globalParamModel
    let path = this.$route.path
    if (path.includes('example')) {
      this.isShowMenu = true
    } else {
      this.isShowMenu = false
    }
    if (path.includes('overview')) {
      this.isShowSwitchBtn = true
    } else {
      this.isShowSwitchBtn = false
    }
    if (this.$route.query.isfromatc === true || this.$route.query.isfromatc === 'true' || sessionStorage.getItem('toSingleEdge') === '1') {
      // 增加判断toSingleEdge，解决isfromatc参数丢失问题
      this.isShowLogout = false
    }
    if (this.$route.query.hideMenu === true || this.$route.query.hideMenu === 'true') {
      // 如果传参hideMenu就隐藏菜单，其余情况均不隐藏
      this.$store.dispatch('SetMenuVisible', true)
    } else {
      this.$store.dispatch('SetMenuVisible', false)
    }
    if (this.$route.query.graphicMode === true || this.$route.query.graphicMode === 'true') {
      // 如果传参hideMenu就隐藏菜单，其余情况均不隐藏
      this.$store.dispatch('SetGraphicMode', true)
    } else {
      this.$store.dispatch('SetGraphicMode', false)
    }
    // this.extendErrorCodeMap() // 扩展错误码的map集合
  },
  methods: {
    handleCommand (command) {
      switch (command) {
        case 'import': this.leadingIn()
          break
        case 'export': this.leadingOut()
          break
        default: console.log('no command!')
      }
    },
    copy () {
      this.$store.dispatch('SetCopy', this.$store.getters.tscParam)
      this.$emit('resetNotify')
      this.createNotify()
    },
    createNotify () {
      this.curCopyAgentid = JSON.parse(window.sessionStorage.getItem('curTagDevInfo')) === null ? '' : JSON.parse(window.sessionStorage.getItem('curTagDevInfo')).agentid
      let tip = `${this.$t('edge.main.copyDevice')} ${this.curCopyAgentid} ${this.$t('edge.main.copyPattern')}`
      window.sessionStorage.setItem('copiedAgentid', this.curCopyAgentid)
      if (this.notify !== undefined) {
        // 短时间内点击两次复制，前一个复制框还未关闭的情况下，则关闭前一个复制框
        this.notify.close()
      }
      const h = this.$createElement
      this.notify = this.$notify({
        title: this.$t('edge.main.tip'),
        duration: 5000,
        offset: 40,
        onClose: () => {
          this.notify = undefined
        },
        message: h('div', {style: 'color: #909399'}, tip)
      })
    },
    read () {
      this.readDiologVisible = true
      this.copiedAgentid = window.sessionStorage.getItem('copiedAgentid')
    },
    readScheme () {
      this.globalParamModel.reset()
      setTimeout(() => {
        let result = JSON.parse(JSON.stringify(this.copiedTscParam))
        this.globalParamModel.setGlobalParams(result)
        this.$message.success(this.$t('edge.errorTip.readSchemeSucc'))
      }, 50)
    },
    cancleRead () {
      this.readDiologVisible = false
    },
    handleRead () {
      this.readScheme()
      this.readDiologVisible = false
    },
    importtemplate () {
      this.importVisible = true
    },
    normalData (tscParam) {
      let res = JSON.parse(JSON.stringify(tscParam))
      if (this.value === 'all' || this.value === 'pattern') {
        // 去除patternList里的description对象
        let patternList = res.patternList
        if (patternList !== undefined) {
          for (let pattern of patternList) {
            for (let rings of pattern.rings) {
              for (let i = 0; i < rings.length; i++) {
                rings[i] = (
                  ({ name, id, value, mode, options, minSplit, delaystart, advanceend }) =>
                    ({ name, id, value, mode, options, minSplit, delaystart, advanceend })
                )(rings[i])
              }
            }
          }
        }
      }
      return res
    },
    toggleSideBar () {
      this.$store.dispatch('ToggleSideBar')
    },
    logout () {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    initChannelData (overlaplList, phaseList) {
      let phasetype = getControSource(this.$i18n.locale, phaseList)
      let patterntype = getOverLap(this.$i18n.locale, overlaplList)
      this.typeOptions[0].children = phasetype
      this.typeOptions[1].children = phasetype
      this.typeOptions[2].children = patterntype
      this.typeOptions[3].children = patterntype
      let channel = this.globalParamModel.getParamsByType('channelList')
      for (let obj of channel) {
        let list = []
        list.push(obj.controltype)
        // 车道灯和不启用，没有下一级菜单
        if (obj.controltype !== 6 && obj.controltype !== 0) {
          list.push(obj.controlsource)
        }
        obj.typeAndSouce = list
      }
    },
    createCurrentDescMap (overlaplList, phaseList) {
      this.typeOptions = getTypeOptions()
      if (this.$i18n.locale === 'en') {
        this.typeOptions = getEtypeOptions()
      }
      this.initChannelData(overlaplList, phaseList)
      // 生成当前的控制类型描述，与通道id一一对应
      let channels = this.globalParamModel.getParamsByType('channelList')
      let desclist = new Map()
      for (let ele of channels) {
        if (!ele.typeAndSouce || ele.typeAndSouce.length === 0) continue
        let source
        let dire
        let desc = []
        if (ele.typeAndSouce[0] !== undefined) {
          source = this.typeOptions.filter(type => type.value === ele.typeAndSouce[0])[0].label
          if (source) {
            desc[0] = source
          }
        }
        if (ele.typeAndSouce[1] !== undefined) {
          this.typeOptions.forEach(type => {
            if (type.value === ele.typeAndSouce[0] && type.children && type.children.length) {
              dire = type.children.filter(child => child.value === ele.typeAndSouce[1])[0].label
            }
          })
          if (dire) {
            desc[1] = dire
          }
        }
        desclist.set(ele.id, desc)
      }
      this.$store.dispatch('SetChannelDesc', desclist)
    },
    upload () {
      this.lockScreen()
      let typeStr = this.value
      if (typeStr !== 'all') {
        this.singleUpload(typeStr)
        // this.unlockScreen()
        return
      }
      this.globalParamModel.reset()
      uploadTscParam().then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$store.state.user.route = this.$route.path
        if (Object.keys(data.data.data.data).length === 0) {
          this.$message.error(this.$t('edge.errorTip.noSchemeUpload'))
          return
        }
        // let allTscParam = data.data.data.data
        let {customInfo, ...allTscParam} = data.data.data.data
        if (allTscParam.manualpanel === undefined) {
          allTscParam.manualpanel = {}
        }
        if (allTscParam.channellock === undefined) {
          allTscParam.channellock = []
        }
        if (allTscParam.singleoptim === undefined) {
          allTscParam.singleoptim = []
        }
        this.globalParamModel.setGlobalParams(allTscParam)
        this.$alert(this.$t('edge.common.uploadsuccess'), { type: 'success' })
        // 上载成功后就生成通道描述map
        this.createCurrentDescMap()
      })
    },
    getPhaseOverlapParam () {
      return new Promise((resolve, reject) => {
        uploadTscParam().then(data => {
          if (!data.data.success) {
            if (data.data.code === '4003') {
              this.$message.error(this.$t('edge.errorTip.devicenotonline'))
              return
            }
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          if (Object.keys(data.data.data.data).length === 0) {
            this.$message.error(this.$t('edge.errorTip.noSchemeUpload'))
            return
          }
          let { phaseList, overlaplList } = data.data.data.data
          resolve({phaseList, overlaplList})
        })
      })
    },
    singleUpload (typeStr) {
      uploadSingleTscParam(typeStr).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let allTscParam = data.data.data.data
        this.$store.state.user.route = this.$route.path
        if (Object.keys(allTscParam).length === 0) {
          this.$message.error(this.$t('edge.errorTip.noSchemeUpload'))
          return
        }
        let newTscParam = this.handleSingleData(typeStr, allTscParam)
        this.globalParamModel.setGlobalParams(newTscParam)
        this.$alert(this.$t('edge.common.uploadsuccess'), { type: 'success' })
        // 上载成功后就生成通道描述map
        // if (typeStr === 'channel') {
        // // 单独上载通道，需要同时获取当前设备的相位、跟随相位，不然无法正确显示控制信息的选项
        //   this.getPhaseOverlapParam().then(res => {
        //     this.createCurrentDescMap(res.overlaplList, res.phaseList)
        //   })
        // }
      })
    },
    handleSingleData (type, TscParam) {
      // 在全局参数中，替换单独上载的数据
      let singleTscParam = JSON.parse(JSON.stringify(TscParam))
      let curTscParam = this.globalParamModel.getGlobalParams()
      // 解决相位、跟随相位单独上载，相位方向和行人相位方向组件不更新问题
      if (type === 'phase') {
        this.$store.dispatch('SetRefreshTankuang', 'phase')
      } else if (type === 'overlap') {
        this.$store.dispatch('SetRefreshTankuang', 'overlap')
      } else {
        this.$store.dispatch('SetRefreshTankuang', 'norefresh')
      }
      curTscParam = {
        ...curTscParam,
        ...singleTscParam
      }
      return curTscParam
    },
    download () {
      let typeStr = this.value
      if (typeStr !== 'all') {
        this.singleDownload(typeStr)
        return
      }
      // 下发数据前的基本校验
      if (!this.baseCheck()) {
        return
      }
      let tscParam = this.globalParamModel.getGlobalParams()
      let targetTscParam = this.normalData(tscParam) // 规范数据格式
      let newTscParam = this.handleTscParam(targetTscParam)
      // if (typeStr !== 'all') {
      //   this.lockScreen()
      //   this.singleDownload(typeStr, newTscParam)
      //   return
      // }
      // const tscParam = this.globalParamModel.getGlobalParams()
      this.lockScreen()
      downloadTscParam(this.$store.state.user.user_name, newTscParam).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4002' && data.data.data.errorCode === '4207') { // 信号机参数校验
            let codeList = data.data.data.content.errorCode
            if (codeList.length === 0) {
              this.$message.error(this.$t('edge.errorTip.saveParamFailed'))
              return
            }
            let errorMes = this.$t('edge.common.downloaderror')
            for (let code of codeList) {
              if (this.$i18n.locale === 'en') {
                errorMes = getErrorMesEn(errorMes, code)
              } else {
                errorMes = getErrorMesZh(errorMes, code)
              }
            }
            this.$message({
              message: errorMes,
              type: 'error',
              dangerouslyUseHTMLString: true
            })
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        // downloadTscParam(this.$store.state.user.name, tscParam)
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    handleTscParam (tscParam) {
      let newTscParam = this.cloneObjectFn(tscParam)
      if (this.value === 'all' || this.value === 'date') {
        let dateList = newTscParam.dateList
        if (dateList !== undefined) {
          for (let dates of dateList) {
            let date = dates.date
            let day = dates.day
            let month = dates.month
            if (date && date.includes('全选')) {
              let index = date.indexOf('全选')
              date.splice(index, 1) // 排除全选选项
            } else if (date && date.includes('All')) {
              let index = date.indexOf('All')
              date.splice(index, 1) // 排除全选选项
            }
            if (day && day.includes(8)) {
              let index = day.indexOf(8)
              day.splice(index, 1) // 排除全选选项
            }
            if (month && month.includes(0)) {
              let index = month.indexOf(0)
              month.splice(index, 1) // 排除全选选项
            }
          }
        }
      }
      if (this.value === 'all' || this.value === 'pattern') {
        let patternList = newTscParam.patternList
        if (patternList !== undefined) {
          for (let pattern of patternList) {
            let rings = pattern.rings
            for (let ring of rings) {
              if (ring.length === 0) continue
              for (let rg of ring) {
                rg.options = this.getBinarySystem(rg.options) // 转换为二进制数组
              }
            }
          }
        }
      }
      if (newTscParam.customInfo) {
        // 删除历史数据中的设备信息
        delete newTscParam.customInfo
      }
      if (newTscParam.channellock && newTscParam.channellock.length) {
        // 删除通道锁定的通道描述字段
        let channellock = newTscParam.channellock
        for (let lock of channellock) {
          lock.channellocKinfo.forEach(el => delete el.desc)
        }
      }
      if (newTscParam.channelList) {
        // 去除typeAndSouce
        let channelList = newTscParam.channelList
        for (let i = 0; i < channelList.length; i++) {
          let channel = channelList[i]
          channelList[i] = (({ desc, lane, controlsource, controltype, id, voltthresh, pacthresh, peakhthresh, peaklthresh }) => ({ desc, lane, controlsource, controltype, id, voltthresh, pacthresh, peakhthresh, peaklthresh }))(channel)
        }
      }
      return newTscParam
    },
    cloneObjectFn (obj) {
      return JSON.parse(JSON.stringify(obj))
    },
    getBinarySystem (list) {
      if (list === undefined) return
      let arr = [0, 0, 0]
      if (list.includes(1)) arr[0] = 1
      if (list.includes(2)) arr[1] = 1
      if (list.includes(4)) arr[2] = 1
      return arr
    },
    handleSingleParam (type, tscParams) {
      this.singleDownloadNotZero = true
      let allparam = JSON.parse(JSON.stringify(tscParams))
      let typeOption = this.options.filter(option => option.value === type)
      let optionobj
      let typeParams = {}
      if (typeOption.length) {
        optionobj = typeOption[0]
        let field = optionobj.field
        if (allparam[field] === undefined || !allparam[field].length) {
          this.singleDownloadNotZero = false
        }
        typeParams[field] = allparam[field]
        return typeParams
      }
    },
    singleDownload (typeStr) {
      let tscParam = this.globalParamModel.getGlobalParams()
      let targetTscParam = this.normalData(tscParam) // 规范数据格式
      let newTscParam = this.handleTscParam(targetTscParam)
      let typeParams = this.handleSingleParam(typeStr, newTscParam)
      if (!this.singleDownloadNotZero) {
        this.$message.error(
          this.$t('edge.errorTip.singleDownloadNotZero')
        )
        return
      }
      this.lockScreen()
      downloadSingleTscParam(typeStr, typeParams).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4002' && data.data.data.errorCode === '4207') { // 信号机参数校验
            let codeList = data.data.data.content.errorCode
            if (codeList.length === 0) {
              this.$message.error(this.$t('edge.errorTip.saveParamFailed'))
              return
            }
            let errorMes = this.$t('edge.common.downloaderror')
            for (let code of codeList) {
              if (this.$i18n.locale === 'en') {
                errorMes = getErrorMesEn(errorMes, code)
              } else {
                errorMes = getErrorMesZh(errorMes, code)
              }
            }
            this.$message({
              message: errorMes,
              type: 'error',
              dangerouslyUseHTMLString: true
            })
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        // downloadTscParam(this.$store.state.user.name, tscParam)
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    leadingIn () {
      this.dialogVisible = true
    },
    async leadingOut () {
      // 下载数据前的基本校验
      if (!this.baseCheck()) {
        return
      }
      let edgeParam = this.globalParamModel.getGlobalParams()
      let targetTscParam = this.normalData(edgeParam) // 规范数据格式
      let newTscParam = this.handleTscParam(targetTscParam)
      // 定义文件内容，类型必须为Blob 否则createObjectURL会报错
      // const tscParam = this.globalParamModel.getGlobalParams()
      const tscParam = {}
      let md5Param = await this.getMd5ByParams(newTscParam)
      tscParam.md5 = md5Param
      tscParam.data = newTscParam
      let content = new Blob([JSON.stringify(tscParam)])
      // 生成url对象
      let urlObject = window.URL || window.webkitURL || window
      let url = urlObject.createObjectURL(content)
      // 生成<a></a>DOM元素
      let el = document.createElement('a')
      // 链接赋值
      el.href = url
      el.download = 'OpenATCTZParam.json'
      // 必须点击否则不会下载
      el.click()
      // 移除链接释放资源
      urlObject.revokeObjectURL(url)
    },
    getMd5ByParams (param) {
      this.lockScreen()
      return new Promise((resolve, reject) => {
        checkCode(param).then((data) => {
          this.unlockScreen()
          if (data.data.success) {
            resolve(data.data.data)
          } else {
            this.$message.error(this.$t('edge.common.getmd5error'))
            reject(new Error(data.data.message))
          }
        }, () => {
          this.unlockScreen()
          console.log('checkCode Error')
          this.$message.error(this.$t('edge.common.getmd5error'))
          reject(new Error('checkCode Error'))
        })
      })
    },
    readAsText () {
      let _this = this
      var file = document.getElementById('file').files[0]
      var reader = new FileReader()
      reader.async = true
      // 将文件以文本形式读入页面
      reader.readAsText(file)
      reader.onload = function (f) {
        _this.globalParamModel.reset()
        setTimeout(() => {
          // 显示文件
          _this.$store.state.user.route = _this.$route.path
          // _this.$router.push('/')
          _this.dialogVisible = false
          // _this.$store.dispatch('SaveTscParam', JSON.parse(this.result))
          let data = JSON.parse(this.result)
          _this.globalParamModel.setGlobalParams(data.data)
          _this.$alert(_this.$t('edge.common.uploadsuccess'), { type: 'success' })
        }, 50)
      }
    },
    baseCheck () {
      this.phaseNotZero = false
      this.phaseRing = false
      this.concurrentRules = false
      this.planNotZero = false
      this.patternNotZero = false
      this.dataNotZero = false
      this.momthIsNull = false
      this.dateIsAll = false
      this.planDate = false
      this.patternRing = false
      this.dataPlan = false
      this.planPattern = false
      this.overlapRules = true
      this.deviceinfo = true
      this.splitCheck = true
      this.checkPhaseRules()
      if (!this.phaseNotZero) {
        this.$message.error(
          this.$t('edge.errorTip.phaseNotZero')
        )
        return false
      }
      this.checkPhaseRing()
      // if (!this.phaseRing) {
      //   this.$message.error(this.$t('edge.errorTip.ringErrorTip'))
      //   return false
      // }
      this.checkConcurrentRules()
      // if (!this.concurrentRules) {
      //   this.$message.error(
      //     this.$t('edge.errorTip.concurrentRules')
      //   )
      //   return false
      // }
      this.cheackPhaseRingNum()
      if (this.phaseExceed) {
        this.$message.error(
          `${this.$t(`edge.errorTip.phaseExceed`)}`
        )
        return false
      }
      this.checkOverlapRules()
      if (!this.overlapRules) {
        this.$message.error(
          this.$t('edge.errorTip.overlapRules')
        )
        return false
      }
      this.checkPatternRules()
      if (!this.patternNotZero) {
        this.$message.error(
          this.$t('edge.errorTip.patternNotZero')
        )
        return false
      }
      if (!this.patternCycleEqual) {
        let mess = `方案${this.patternmsg.toString()}中存在环周期时长不一致`
        if (this.$i18n.locale === 'en') {
          mess = `The cycle of each ring is inconsistent in scheme${this.patternmsg.toString()}`
        }
        this.$message({
          message: mess,
          type: 'error',
          dangerouslyUseHTMLString: true
        })
        return false
      }
      if (!this.splitCheck) {
        // this.$message.error(this.$t('edge.pattern.splitCheckMsg'))
        return false
      }
      this.checkDataRules()
      if (!this.dataNotZero) {
        this.$message.error(
          this.$t('edge.errorTip.dataNotZero')
        )
        return false
      }
      this.checkPlanRules()
      if (!this.planNotZero) {
        this.$message.error(
          this.$t('edge.errorTip.planNotZero')
        )
        return false
      }
      // this.checkPlan()
      // if (!this.planPattern) {
      //   this.$message.error(
      //     this.$t('edge.errorTip.planPattern')
      //   )
      //   return false
      // }
      // this.checkDateMonth()
      // if (this.momthIsNull) {
      //   this.$message.error(
      //     this.$t('edge.errorTip.momthIsNull')
      //   )
      //   return false
      // }
      this.checkDateRules()
      if (!this.dateIsAll) {
        this.$message.error(
          this.$t('edge.errorTip.dateIsAll')
        )
        return false
      }
      this.checkDatePlan()
      if (!this.dataPlan) {
        this.$message.error(
          this.$t('edge.errorTip.dataPlan')
        )
        return false
      }
      this.checkPlanTimeRules()
      if (!this.planDate) {
        this.$message.error(this.planName + this.$t('edge.errorTip.planDate'))
        return false
      }
      // this.checkPatternRing()
      // if (!this.patternRing) {
      //   this.$message.error(
      //     this.$t('edge.errorTip.patternRing')
      //   )
      //   return false
      // }
      this.checkManualpanelIsNull()
      if (!this.manualpanelIsNull) {
        this.$message.error(
          this.$t('edge.errorTip.manualpanel')
        )
        return false
      }
      // this.checkDeviceInfo()
      // if (!this.deviceinfo) {
      //   this.$message.error(this.$t('edge.errorTip.deviceinformationnotnull'))
      //   return false
      // }
      return true
    },
    checkDeviceInfo () {
      let customInfo = this.globalParamModel.getParamsByType('customInfo')
      if (customInfo.siteid === '' || customInfo.siteid === undefined) {
        this.deviceinfo = false
      }
    },
    checkManualpanelIsNull () {
      let manualpanel = this.globalParamModel.getParamsByType('manualpanel')
      if (Object.keys(manualpanel).length !== 0) {
        this.manualpanelIsNull = true
      } else {
        this.manualpanelIsNull = false
      }
    },
    checkPhaseRules () {
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      for (let i = 0; i < phaseList.length; i++) {
        if (phaseList[i].ring === 0) {
          break
        }
        this.phaseNotZero = true
      }
    },
    checkPhaseRing () {
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      if (phaseList.length > 0 && phaseList[0].ring !== 1) {
        this.phaseRing = false
        return
      }
      for (let i = 0; i < phaseList.length - 1; i++) {
        if (phaseList[i].ring > phaseList[i + 1].ring) {
          this.phaseRing = false
          return
        }
      }
      this.phaseRing = true
    },
    cheackPhaseRingNum () {
      // 校验单个环最大16个相位
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let ringNumMap = new Map()
      this.exceedRing = []
      for (let i = 0; i < phaseList.length; i++) {
        let curring = phaseList[i].ring
        if (ringNumMap.get(curring) === undefined) {
          ringNumMap.set(curring, 1)
        } else {
          let curnum = ringNumMap.get(curring) + 1
          ringNumMap.set(curring, curnum)
        }
      }
      for (let [ring, ringNum] of ringNumMap) {
        if (ringNum > 16) {
          // 环相位数超过限制的16个
          this.phaseExceed = true
          this.exceedRing.push(ring)
        } else {
          this.phaseExceed = false
        }
      }
    },
    checkConcurrentRules () {
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let ringList = []
      let concurrentIsNull = false
      for (let phase of phaseList) {
        if (phase.concurrent && phase.concurrent.length === 0) {
          concurrentIsNull = true
        }
        ringList.push(phase.ring)
      }
      let newRingList = new Set(ringList)
      if (newRingList.size > 1 && concurrentIsNull) {
        this.concurrentRules = false
      } else {
        this.concurrentRules = true
      }
    },
    checkOverlapRules () {
      let overlaplList = this.globalParamModel.getParamsByType('overlaplList')
      for (let i = 0; i < overlaplList.length; i++) {
        if (overlaplList[i].includedphases.length === 0) {
          this.overlapRules = false
          return
        }
      }
    },
    checkPatternRules () {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      for (let i = 0; i < patternList.length; i++) {
        if (patternList[i].ring === 0) {
          break
        } else {
          this.patternNotZero = true
          let ringsList = patternList[i].rings
          for (let j = 0; j < ringsList.length; j++) {
            let list = ringsList[j]
            if (list && list.length > 0) {
              for (let k = 0; k < list.length; k++) {
                let ls = list[k]
                let phase = phaseList.filter((item) => {
                  return item.id === ls.id
                })[0]
                if (!phase) {
                  continue
                }
                if (!phase.redyellow) {
                  phase.redyellow = 0
                }
                if (!phase.yellow) {
                  phase.yellow = 0
                }
                if (!phase.redclear) {
                  phase.redclear = 0
                }
                if (!phase.flashgreen) {
                  phase.flashgreen = 0
                }
                if (!phase.phasewalk) {
                  phase.phasewalk = 0
                }
                if (!phase.pedclear) {
                  phase.pedclear = 0
                }
                let temp1 = phase.yellow + phase.redclear + phase.flashgreen // 绿信比的最小值要大于最小绿+黄灯+全红+绿闪
                let temp2 = phase.yellow + phase.redclear + phase.phasewalk + phase.pedclear
                ls.minSplit = temp1 > temp2 ? temp1 : temp2
                if (ls.mode !== 7 && ls.value < ls.minSplit) {
                  // ls.value = ls.minSplit
                  this.splitCheck = false
                  // console.log('相位：', phase)
                  let phaseid = phase.id
                  let flashgreen = phase.flashgreen
                  let yellow = phase.yellow
                  let redclear = phase.redclear
                  let phasewalk = phase.phasewalk
                  let pedclear = phase.pedclear
                  let value = ls.value
                  let pattenid = i + 1
                  let msg1 = `相位[${phaseid}]的 绿闪[${flashgreen}] + 黄灯[${yellow}] + 全红[${redclear}] 时间之和大于方案[${pattenid}]中的相位配时[${value}]`
                  let msg2 = `相位[${phaseid}]的行 人过街[${phasewalk}] + 行人清空[${pedclear}] + 黄灯[${yellow}] + 全红[${redclear}]时间之和大于方案[${pattenid}]中的相位配时[${value}]`
                  if (this.$i18n.locale === 'en') {
                    msg1 = `In phase[${phaseid}], flashgreen[${flashgreen}] + yellow[${yellow}]+ redclear[${redclear}] is longgger than pattern[${pattenid}]'s phase config time'[${value}]`
                    msg2 = `In phase[${phaseid}], phasewalk[${phasewalk}]+ pedclear[${pedclear}]+ yellow[${yellow}]+ redclear[${redclear}] is longgger than pattern[${pattenid}]'s phase config time[${value}]`
                  }
                  let msg = temp1 > temp2 ? msg1 : msg2
                  this.$message.error(msg)
                  break
                }
              }
            }
            if (!this.splitCheck) {
              // console.log('环：', list)
              break
            }
          }
        }
        if (!this.splitCheck) {
          // console.log('方案', i)
          break
        }
      }
      if (!this.isRingCycleEqual(patternList)) {
        this.patternCycleEqual = false
      } else {
        this.patternCycleEqual = true
      }
    },
    isRingCycleEqual (patternlist) {
      let isequal = true
      this.patternmsg = []
      for (let pattern of patternlist) {
        let rings = pattern.rings
        let maxCycle = 0
        for (let ring of rings) {
          if (ring.length === 0) continue
          let cycle = 0
          for (let r of ring) {
            if (r.mode === 7) { // 忽略相位不计周期
              continue
            }
            cycle = cycle + r.value
          }
          if (cycle > maxCycle && maxCycle === 0) {
            maxCycle = cycle
          }
          if (cycle !== maxCycle) {
            this.patternmsg.push(pattern.id)
            isequal = false
          }
        }
      }
      return isequal
    },
    checkDataRules () {
      let dateList = this.globalParamModel.getParamsByType('dateList')
      for (let i = 0; i < dateList.length; i++) {
        if (dateList[i].ring === 0) {
          break
        }
        this.dataNotZero = true
      }
    },
    checkPlanRules () {
      let planList = this.globalParamModel.getParamsByType('planList')
      for (let i = 0; i < planList.length; i++) {
        if (planList[i].plan.length === 0) {
          break
        }
        this.planNotZero = true
      }
    },
    checkPlanTimeRules () {
      // let hour = 0
      // let newHour = 0
      // let minute = -1
      // let newMinute = 0
      let planList = this.globalParamModel.getParamsByType('planList')
      for (let i = 0; i < planList.length; i++) {
        let hour = 0
        let newHour = 0
        let minute = -1
        let newMinute = 0
        for (let j = 0; j < planList[i].plan.length; j++) {
          newHour = Number(planList[i].plan[j].hour)
          newMinute = Number(planList[i].plan[j].minute)
          if (newHour > hour) {
            hour = newHour
            minute = newMinute
            this.planDate = true
          } else if (newHour === hour) {
            if (newMinute > minute) {
              hour = newHour
              minute = newMinute
              this.planDate = true
            } else {
              this.planDate = false
              this.planName = planList[i].desc
              return
            }
          } else {
            this.planDate = false
            this.planName = planList[i].desc
            return
          }
        }
      }
    },
    checkDateMonth () {
      let dateList = this.globalParamModel.getParamsByType('dateList')
      for (let i = 0; i < dateList.length; i++) {
        if (dateList[i].month.length === 0) {
          this.momthIsNull = true
        }
      }
    },
    checkDateRules () {
      let dateList = this.globalParamModel.getParamsByType('dateList')
      let month = []
      let day = []
      let date = []
      for (let i = 0; i < dateList.length; i++) {
        month = month.concat(dateList[i].month)
        day = day.concat(dateList[i].day)
        date = date.concat(dateList[i].date)
      }
      month = Array.from(new Set(month.toString().split(',')))
      day = Array.from(new Set(day.toString().split(',')))
      date = Array.from(new Set(date))
      if (month.length === 12 || month.length === 13) { // 当带有全选，就为13
        // this.dateIsAll = true
        if ((day.length === 7 || day.length === 8) || (date.length === 31 || date.length === 32)) {
          this.dateIsAll = true
        } else {
          this.dateIsAll = false
        }
      } else {
        this.dateIsAll = false
      }
    },
    checkDatePlan () {
      let dateList = this.globalParamModel.getParamsByType('dateList')
      for (let data of dateList) {
        if (data.plan === '' || data.plan === undefined) {
          this.dataPlan = false
        } else {
          this.dataPlan = true
        }
      }
    },
    checkPlan () {
      let planList = this.globalParamModel.getParamsByType('planList')
      this.planPattern = true
      for (let data of planList) {
        for (let plan of data.plan) {
          if (plan.pattern === '' || plan.pattern === undefined) {
            this.planPattern = false
          }
        }
      }
    },
    checkPatternRing () {
      // 先判断有几个环，如果只有一个的话，就不用判断每个环的周期是否相等
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let ringNum = []
      for (let phase of phaseList) {
        ringNum.push(phase.ring)
      }
      ringNum = Array.from(new Set(ringNum))
      if (ringNum.length === 1) {
        this.patternRing = true
        return
      }
      // 如果有多个环的话，要判断每个环的时间是否相等
      let patternList = this.globalParamModel.getParamsByType('patternList')
      this.patternRing = true
      for (let pattern of patternList) {
        let list = []
        for (let rings of pattern.rings) {
          let num = 0
          for (let i = 0; i < rings.length; i++) {
            if (rings[i].length !== 0) {
              num = num + Number(rings[i].value)
            }
          }
          list.push(num)
        }
        list = list.filter(function (i) { return i !== 0 })
        if (Array.from(new Set(list)).length !== 1) {
          this.patternRing = false
        }
      }
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
    },
    lockScreen () {
      this.loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.5)'
      })
    },
    unlockScreen () {
      this.loading.close()
    },
    closeImportTemp () {
      this.importVisible = false
    },
    handleLogout (command) {
      switch (command) {
        case 'a': this.modifyPasswd()
          break
        case 'b': this.logout()
          break
        case 'about': this.showVersion()
          break
        // case 'help': this.showHelp()
        //   break
        default: router.push({ path: '/' })
      }
    },
    showVersion () {
      let versionInfoChild = this.$refs.versioninfoChild
      versionInfoChild.showMessage()
    },
    showHelp () {
      if (this.$i18n.locale === 'zh') {
        window.open('/UserManual/openatcConfig/zh/index.html')
      }
      if (this.$i18n.locale === 'en') {
        window.open('/UserManual/openatcConfig/en/index.html')
      }
    },
    showInfo (val) {
      if (!val) return
      getInfo().then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.userInfo = data.data.data
        this.passIsExpire = this.checkUserIsExpire(data.data.data.expiration_time)
      })
    },
    modifyPasswd () {
      let changepassChild = this.$refs.changepassChild
      changepassChild.onChangePassClick(this.userInfo.user_name)
    },
    checkUserIsExpire (time) {
      let expireTime = new Date(time) // 过期时间
      let nowTime = new Date() // 当前时间
      let residueDay = Math.floor((expireTime - nowTime) / 1000 / 60 / 60 / 24) // 过期剩余时间
      if (residueDay < 7) {
        return true
      } else {
        return false
      }
    },
    // extendErrorCodeMap () {
    //   let patternInitCode = 3000
    //   let channelInitCode = 1200
    //   let mes = ''
    //   let enMes = ''
    //   for (let i = 1; i < 109; i++) {
    //     patternInitCode++
    //     mes = '方案' + i + '环内存在相位并发冲突'
    //     enMes = 'There is phase concurrency conflict in pattern' + i
    //     errorCodeMap.set(patternInitCode, mes)
    //     errorCodeMapEn.set(patternInitCode, enMes)
    //   }
    //   for (let j = 1; j < 41; j++) {
    //     channelInitCode++
    //     mes = '时段' + j + '锁定通道的控制源未被忽略'
    //     enMes = 'The control source of the locked channel in period' + j + 'is not ignored'
    //     errorCodeMap.set(channelInitCode, mes)
    //     errorCodeMapEn.set(channelInitCode, enMes)
    //   }
    // }
    clickSwitchIcon (isShowGUI) {
      this.$store.dispatch('SetShowGui', isShowGUI)
    }
  }
}
</script>

<style lang="css">
.navbar .el-button {
  line-height: normal;
  margin-left: 15px;
}
.navbar .el-dropdown {
  height: 40px;
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
  .switch-language {
    cursor: pointer;
    margin-top: 0px;
    margin-right: 30px;
    float: right;
  }
  .navbar-agentid {
    cursor:pointer;
    margin-top: 0px;
    margin-right: 15px;
    float: right;
  }
  .operation-button {
    cursor: pointer;
    margin-top: 0px;
    margin-right: 22px;
    float: right;
  }
  .dividing-line {
    display: inline-block;
    width: 1px;
    height: 16px;
    background-color: #e5e5e5;
    transform: translateY(4px);
    margin-left: 15px;
  }
}
.user-name {
  width: 58px;
  height: 21px;
  margin-top: 10px;
  font-size: 20px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #409eff;
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
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #999999;
}
.laber-value {
  margin-top: 5px;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #333333;
}
.pass-expire {
  margin-top: 5px;
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #f56c6c;
}
</style>
