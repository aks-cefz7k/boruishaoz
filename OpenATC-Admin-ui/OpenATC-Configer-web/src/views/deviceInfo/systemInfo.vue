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
  <div class="app-container">
    <div class="device-message">
          <div class="device-info">{{$t('edge.deviceinfo.deviceinfo')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.addresscode')}}
                <el-button type="text"
                           class="see-cut"
                           :disabled="isSeeCutDisabled"
                           @click="onSeeCutClick">{{$t('edge.deviceinfo.seeCutEffect')}}</el-button>
              </div>
              <el-input v-model="customInfo.siteid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.areaid')}}</div>
              <el-input v-model="customInfo.areaid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.devid')}}</div>
              <el-input v-model="customInfo.intersectionid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.devname')}}:</div>
              <el-input v-model="customInfo.fixintersectioninfo" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.port')}}</div>
              <el-input v-model="customInfo.commuport" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.overview.type')}}:</div>
              <el-select v-model="customInfo.commutype" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in commutypeOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.stepType')}}:</div>
              <el-select v-model="customInfo.steptype" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in stepTypeOptions"
                  :key="item.value"
                  :label="$t('edge.deviceinfo.step' + item.id)"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
             <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.timezonehour')}}:</div>
              <el-select v-model="customInfo.timezone.hour" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in hourOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
             <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.timezoneminute')}}:</div>
              <el-select v-model="customInfo.timezone.minute" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in minuteOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.selflearning')}}
                <el-switch
                  style="padding-left: 5px;"
                  v-model="customInfo.selflearning"
                  active-color="#409EFF"
                  :active-value="1"
                  :inactive-value="0">
                </el-switch>
              </div>
            </el-col>
          </el-row>
        </div>
        <seeCutEffect ref="seeCutEffect"></seeCutEffect>
        <div class="device-param">
          <div class="device-info">{{$t('edge.deviceinfo.deviceparam')}}</div>
          <div class="device-second-title">{{$t('edge.deviceinfo.startOrderParams')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.startyellowflashtime')}}</div>
              <el-select v-model="customInfo.startsequence.startyellowflash" :placeholder="$t('edge.common.select')" style="100%" size="small">
                <el-option
                  v-for="item in startyellowflashOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.startredtime')}}</div>
              <el-select v-model="customInfo.startsequence.startallred" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in startallredOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.greenwavetransitionperiod')}}</div>
              <el-select v-model="customInfo.startsequence.greenwavecycle" :placeholder="$t('edge.common.select')" size="small">
                    <el-option
                      v-for="item in greenwavecycleOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
            </el-col>
          </el-row>

          <div class="device-second-title">{{$t('edge.deviceinfo.faultdetect')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.detectgapgreenandredon')}}</div>
              <el-input-number :min="0" :max="65535" :controls="false" v-model="customInfo.faultdetect.detectgapgreenandredon" :placeholder="$t('edge.common.entercontent')" size="small"></el-input-number>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.detectgapnoredon')}}</div>
              <el-input-number :min="0" :max="65535" :controls="false" v-model="customInfo.faultdetect.detectgapnoredon" :placeholder="$t('edge.common.entercontent')" size="small"></el-input-number>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.detectgapgreenconflict')}}</div>
              <el-input-number :min="0" :max="65535" :controls="false" v-model="customInfo.faultdetect.detectgapgreenconflict" :placeholder="$t('edge.common.entercontent')" size="small"></el-input-number>
            </el-col>
          </el-row>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.closegreenandredon')}}
                <el-switch
                  style="padding-left: 5px;"
                  v-model="customInfo.faultdetect.closegreenandredon"
                  active-color="#409EFF"
                  :active-value="1"
                  :inactive-value="0">
                </el-switch>
              </div>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.closenoredon')}}
                <el-switch
                  style="padding-left: 5px;"
                  v-model="customInfo.faultdetect.closenoredon"
                  active-color="#409EFF"
                  :active-value="1"
                  :inactive-value="0">
                </el-switch>
              </div>
            </el-col>
          </el-row>
          <div class="device-second-title">{{$t('edge.deviceinfo.cascade')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.lampcontrolpanel')}}</div>
              <el-select v-model="customInfo.cascade.lampboards" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in lampboardsOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.boardsformainengine')}}</div>
              <el-select v-model="customInfo.cascade.detectorboards" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in detectorboardsOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.ioboards')}}</div>
              <el-select v-model="customInfo.cascade.ioboards" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in ioboardsOptions"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.slavecascadeoffset')}}</div>
              <el-input v-model.number="customInfo.cascade.joinoffset"
                        placeholder=""
                        size="small"
                        oninput="this.value=this.value.replace(/[^\.\d]/g,'')"
                        @blur="checkJoinoffset(customInfo.cascade.joinoffset)"></el-input>
            </el-col>
          </el-row>
        </div>

        <div class="device-netcard">
          <div class="device-info">{{$t('edge.deviceinfo.netcardInfo')}}</div>
          <div v-for="(item, index) in customInfo.netcard" :key="index">
            <div class="device-second-title">IP{{index + 1}}</div>
            <el-row class="demo-autocomplete" :gutter="30">
              <el-col :span="4">
                <div class="sub-title">{{$t('edge.deviceinfo.ip')}}</div>
                <el-input v-model="item.ip"
                          :placeholder="$t('edge.common.entercontent')"
                          size="small"
                          @blur="checkIp(item.ip)"></el-input>
              </el-col>
              <el-col :span="4">
                <div class="sub-title">{{$t('edge.deviceinfo.subnetmask')}}</div>
                <el-input v-model="item.subnetmask"
                          :placeholder="$t('edge.common.entercontent')"
                          size="small"
                          @blur="checkIp(item.subnetmask)"></el-input>
              </el-col>
              <el-col :span="4">
                <div class="sub-title">{{$t('edge.deviceinfo.gateway')}}</div>
                <el-input v-model="item.gateway"
                          :placeholder="$t('edge.common.entercontent')"
                          size="small"
                          @blur="checkIp(item.gateway)"></el-input>
              </el-col>
            </el-row>
          </div>
          <div class="device-second-title">{{$t('edge.deviceinfo.centercommunicationip')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.ip')}}</div>
              <el-input v-model="customInfo.centerip.ip"
                        size="small"
                        :placeholder="$t('edge.common.entercontent')"
                        @blur="checkIp(customInfo.centerip.ip)"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.port')}}</div>
              <el-input v-model.number="customInfo.centerip.port"
                        size="small"
                        :placeholder="$t('edge.common.entercontent')"
                        oninput="this.value=this.value.replace(/[^\.\d]/g,'')"
                        @blur="checkPort(customInfo.centerip.port)"></el-input>
            </el-col>
          </el-row>
        </div>
        <div class="device-message">
          <el-button type="primary" @click="upload" size="mini">{{$t('edge.main.upload')}}</el-button>
          <el-button type="primary" @click="download" size="mini">{{$t('edge.main.download')}}</el-button>
        </div>
  </div>
</template>

<script>
import seeCutEffect from './components/seeCutEffect'
import { uploadDeviceInfo, downloadDeviceInfo } from '@/api/param'
import { getErrorMesZh, getErrorMesEn } from '../../utils/errorcode.js'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'deviceinfo',
  components: {seeCutEffect},
  data () {
    return {
      lampboardsOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      detectorboardsOptions: [0, 1, 2],
      ioboardsOptions: [0, 1, 2],
      startyellowflashOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      startallredOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      greenwavecycleOptions: [0, 1, 2, 3, 4, 5],
      customInfo: {
        areaid: '',
        intersectionid: '',
        siteid: '',
        selflearning: 0,
        fixintersectioninfo: '',
        commuport: 0,
        commutype: 2,
        steptype: 0,
        netcard: [{
          ip: '',
          subnetmask: '',
          gateway: ''
        },
        {
          ip: '',
          subnetmask: '',
          gateway: ''
        }],
        centerip: {
          ip: '',
          port: ''
        },
        cascade: {
          lampboards: 0,
          detectorboards: 0,
          ioboards: 0,
          joinoffset: 0
        },
        startsequence: {
          startyellowflash: 6,
          startallred: 6,
          greenwavecycle: 5
        },
        faultdetect: {
          closegreenandredon: 0,
          detectgapgreenandredon: 0,
          closenoredon: 0,
          detectgapnoredon: 0,
          detectgapgreenconflict: 0
        },
        timezone: {
          hour: 8,
          minute: 0
        }
      },
      loading: {},
      commutypeOptions: [{label: 'TCP', value: 1}, {label: 'UDP', value: 2}, {label: 'RS232', value: 3}],
      stepTypeOptions: [{
        value: 'stage',
        // label: '阶段',
        id: '0'
      }, {
        value: 'step',
        // label: '色步',
        id: '1'
      }],
      hourOptions: [],
      minuteOptions: []
    }
  },
  computed: {
    isSeeCutDisabled () {
      let res = false
      if (this.customInfo.siteid) {
        res = false
      } else {
        res = true
      }
      return res
    }
  },
  created () {
    // this.globalParamModel = this.$store.getters.globalParamModel
    // this.init()
    this.createTimezoneOptions()
  },
  methods: {
    init () {
      let channelList = this.globalParamModel.getParamsByType('channelList')
      let detectorList = this.globalParamModel.getParamsByType('detectorList')
      let pedestrainDetectorList = this.globalParamModel.getParamsByType('pedestrainDetectorList')
      let customInfo = this.customInfo
      customInfo.cascade.lampboards = Math.ceil(channelList.length / 4)
      customInfo.cascade.detectorboards = Math.ceil(detectorList.length / 16)
      customInfo.cascade.ioboards = Math.ceil(pedestrainDetectorList.length / 16)
    },
    checkIp (ip) {
      var ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (!ipReg.test(ip)) {
        this.$message.error('请输入合法的IP！')
      }
    },
    checkPort (port) {
      var portReg = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/
      if (!portReg.test(port)) {
        this.$message.error('请输入合法的端口！')
      }
    },
    checkJoinoffset (joinoffset) {
      if (joinoffset > 255) {
        this.customInfo.cascade.joinoffset = 255
        this.$message.error('级联偏移量不能超过255！')
      }
    },
    upload () {
      this.lockScreen()
      uploadDeviceInfo().then(data => {
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
        let allTscParam = data.data.data.data
        if (allTscParam.timezone === undefined) {
          allTscParam.timezone = {
            hour: 8,
            minute: 0
          }
        }
        if (allTscParam.steptype !== 0 && allTscParam.steptype !== 1) {
          allTscParam.steptype = ''
        }
        this.customInfo = allTscParam
        // let allTscParam = data.data.data.data
        // if (allTscParam.manualpanel === undefined) {
        //   allTscParam.manualpanel = {}
        // }
        // if (allTscParam.channellock === undefined) {
        //   allTscParam.channellock = []
        // }
        // if (allTscParam.singleoptim === undefined) {
        //   allTscParam.singleoptim = []
        // }
        // this.globalParamModel.setGlobalParams(allTscParam)
        this.$alert(this.$t('edge.common.uploadsuccess'), { type: 'success' })
      })
    },
    download () {
      let customInfo = this.customInfo
      this.handleIpPortValue()
      this.lockScreen()
      downloadDeviceInfo(customInfo).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }

          if (data.data.code === '4002') { // 错误应答
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              this.$message.error(getMessageByCode(data.data.data.errorCode, this.$i18n.locale))
            }
            if (data.data.data.errorCode === '4207') {
              // 信号机参数校验
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
            }
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
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
    onSeeCutClick () {
      let num = Number(this.customInfo.siteid)
      if (!num || num <= 0 || num >= 99999) {
        this.$message.error(this.$t('edge.deviceinfo.siteIdLimit'))
        return false
      }
      this.$refs.seeCutEffect.show(this.customInfo.siteid)
    },
    handleIpPortValue () {
      // IP，子网掩码，网关为空时，默认设为0.0.0.0
      // 端口为空时默认改为0
      let customInfo = this.customInfo
      if (!customInfo.centerip) {
        customInfo.centerip = {
          ip: '0.0.0.0',
          port: 0
        }
      }
      if (customInfo.centerip.ip === '') {
        customInfo.centerip.ip = '0.0.0.0'
      }
      if (customInfo.centerip.port === '') {
        customInfo.centerip.port = 0
      }
      customInfo.netcard.forEach(netcard => {
        if (!netcard.ip || netcard.ip === '') {
          netcard.ip = '0.0.0.0'
        }
        if (!netcard.gateway || netcard.gateway === '') {
          netcard.gateway = '0.0.0.0'
        }
        if (!netcard.subnetmask || netcard.subnetmask === '') {
          netcard.subnetmask = '0.0.0.0'
        }
      })
      if (!customInfo.commuport || customInfo.commuport === '') {
        customInfo.commuport = 0
      }
    },
    createTimezoneOptions () {
      this.hourOptions = []
      this.minuteOptions = []
      for (let i = -12; i <= 12; i++) {
        let obj = {
          label: `UTC ${i}`,
          value: i
        }
        if (i >= 0) {
          obj.label = `UTC +${i}`
        }
        this.hourOptions.push(obj)
      }
      for (let i = 0; i <= 59; i++) {
        this.minuteOptions.push(i)
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
