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
              <el-input v-model.number="customInfo.faultdetect.detectgapgreenandredon" :placeholder="$t('edge.common.entercontent')" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.detectgapnoredon')}}</div>
              <el-input v-model.number="customInfo.faultdetect.detectgapnoredon" :placeholder="$t('edge.common.entercontent')" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.detectgapgreenconflict')}}</div>
              <el-input v-model.number="customInfo.faultdetect.detectgapgreenconflict" :placeholder="$t('edge.common.entercontent')" size="small"></el-input>
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
// import { mapState } from 'vuex'
import { uploadDeviceInfo, downloadDeviceInfo } from '@/api/param'
export default {
  name: 'deviceinfo',
  data () {
    return {
      lampboardsOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      detectorboardsOptions: [0, 1, 2],
      ioboardsOptions: [0, 1, 2],
      startyellowflashOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      startallredOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      greenwavecycleOptions: [0, 1, 2, 3, 4, 5],
      customInfo: {
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
        }
      },
      loading: {}
    }
  },
  computed: {
    // ...mapState({
    //   customInfo: state => state.globalParam.tscParam.customInfo
    // })
  },
  created () {
    // this.globalParamModel = this.$store.getters.globalParamModel
    // this.init()
  },
  //   watch: {
  //     customInfo: function () {
  //       this.init()
  //     }
  //   },
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
          this.$message.error(data.data.message)
          return
        }
        this.$store.state.user.route = this.$route.path
        if (Object.keys(data.data.data.data).length === 0) {
          this.$message.error(this.$t('edge.errorTip.noSchemeUpload'))
          return
        }
        // this.customInfo = {
        //   'netcard': [
        //     {
        //       'ip': '10001',
        //       'subnetmask': '8888',
        //       'gateway': '9999'
        //     },
        //     {
        //       'ip': '192.168.14.168',
        //       'subnetmask': '255.255.255.0',
        //       'gateway': '192.168.14.254'
        //     }
        //   ],
        //   'centerip': {
        //     'ip': '10.65.3.63',
        //     'port': 31003
        //   },
        //   'cascade': {
        //     'lampboards': 10,
        //     'detectorboards': 11,
        //     'ioboards': 12,
        //     'joinoffset': 13
        //   },
        //   'startsequence': {
        //     'startyellowflash': 9,
        //     'startallred': 8,
        //     'greenwavecycle': 7
        //   },
        //   'faultdetect': {
        //     'closegreenandredon': 1,
        //     'detectgapgreenandredon': 2,
        //     'closenoredon': 3,
        //     'detectgapnoredon': 4,
        //     'detectgapgreenconflict': 5
        //   }

        // }
        // debugger
        this.customInfo = data.data.data.data
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
      debugger
      this.lockScreen()
      downloadDeviceInfo(customInfo).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
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
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
