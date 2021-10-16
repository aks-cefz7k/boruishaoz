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
        <div class="device-info">{{$t('edge.deviceinfo.deviceinfo')}}</div>
        <div class="device-message" style="height: 130px;">
            <div style="margin-left: 10px;">
              <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.addresscode')}}</div><div class="device-value" :style="$t('edge.deviceinfo.deviceinfostyle')"><el-input v-model="customInfo.siteid" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small"></el-input></div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.areaid')}}</div><div class="device-value" :style="$t('edge.deviceinfo.deviceinfostyle')"><el-input v-model.number="customInfo.areaid" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')"></el-input></div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.crossid')}}</div><div class="device-value" :style="$t('edge.deviceinfo.deviceinfostyle')"><el-input v-model.number="customInfo.intersectionid" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')"></el-input></div></div>
            </div>
        </div>

        <div class="device-info" style="margin-top: 20px;">{{$t('edge.deviceinfo.deviceparam')}}</div>
        <div class="device-message" style="height: 150px;">
            <div style="margin-left: 10px;">
              <div class="device-content">
                <div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.startyellowflashtime')}}</div>
                <div class="device-value" :style="$t('edge.deviceinfo.deviceparamstyle')">
                  <!-- <el-input v-model="customInfo.startsequence.startyellowflash" placeholder="请输入内容" style="width:30%" size="small"></el-input> -->
                  <el-select v-model="customInfo.startsequence.startyellowflash" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in startyellowflashOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div>
              </div>
              <div class="device-content">
                <div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.startredtime')}}</div>
                <div class="device-value" :style="$t('edge.deviceinfo.deviceparamstyle')">
                  <!-- <el-input v-model="customInfo.startsequence.startallred" placeholder="请输入内容" style="width:30%" size="small"></el-input> -->
                  <el-select v-model="customInfo.startsequence.startallred" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in startallredOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div>
              </div>
              <div class="device-content">
                <div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.greenwavetransitionperiod')}}</div>
                <div class="device-value" :style="$t('edge.deviceinfo.deviceparamstyle')">
                  <!-- <el-input v-model="customInfo.startsequence.greenwavecycle" placeholder="请输入内容" style="width:30%" size="small"></el-input> -->
                  <el-select v-model="customInfo.startsequence.greenwavecycle" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in greenwavecycleOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div>
              </div>
            </div>
        </div>

        <div class="device-message" style="height: 160px;" v-for="(item, index) in customInfo.netcard" :key="index">
            <div class="device-header">IP{{index + 1}}</div>
            <div style="margin-left: 10px;">
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.ip')}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" :style="$t('edge.deviceinfo.deviceinfostyle')"><el-input v-model="item.ip" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" @blur="checkIp(item.ip)"></el-input></div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.subnetmask')}}&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;"><el-input v-model="item.subnetmask" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" @blur="checkIp(item.subnetmask)"></el-input></div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.gateway')}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;"><el-input v-model="item.gateway" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" @blur="checkIp(item.gateway)"></el-input></div></div>
            </div>
        </div>
        <div class="device-message" style="height: 130px;">
            <div class="device-header">{{$t('edge.deviceinfo.centercommunicationip')}}</div>
            <div style="margin-left: 10px;">
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.ip')}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;"><el-input v-model="customInfo.centerip.ip" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" @blur="checkIp(customInfo.centerip.ip)"></el-input></div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.port')}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;"><el-input v-model.number="customInfo.centerip.port" :placeholder="$t('edge.common.entercontent')" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')" @blur="checkPort(customInfo.centerip.port)"></el-input></div></div>
            </div>
        </div>
        <div class="device-message" style="height: 200px;">
            <div class="device-header">{{$t('edge.deviceinfo.cascade')}}</div>
            <div style="margin-left: 10px;">
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.lampcontrolpanel')}}&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;">
                  <!-- <el-input v-model="customInfo.centerip.ip" placeholder="请输入内容" style="width:30%" size="small" @blur="checkIp(customInfo.centerip.ip)"></el-input> -->
                  <el-select v-model="customInfo.cascade.lampboards" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in lampboardsOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.boardsformainengine')}}&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;">
                  <!-- <el-input v-model.number="customInfo.centerip.port" placeholder="请输入内容" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')" @blur="checkPort(customInfo.centerip.port)"></el-input> -->
                  <el-select v-model="customInfo.cascade.detectorboards" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in detectorboardsOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.ioboards')}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;">
                  <!-- <el-input v-model.number="customInfo.centerip.port" placeholder="请输入内容" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')" @blur="checkPort(customInfo.centerip.port)"></el-input> -->
                  <el-select v-model="customInfo.cascade.ioboards" :placeholder="$t('edge.common.select')" style="width:30%" size="small">
                    <el-option
                      v-for="item in ioboardsOptions"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </div></div>
                <div class="device-content"><div style="float: left; margin-top:17px;">{{$t('edge.deviceinfo.slavecascadeoffset')}}&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="device-value" style="margin-left: 50px; top: 10px;"><el-input v-model.number="customInfo.cascade.joinoffset" placeholder="" style="width:30%" size="small" oninput="this.value=this.value.replace(/[^\.\d]/g,'')" @blur="checkJoinoffset(customInfo.cascade.joinoffset)"></el-input></div></div>
            </div>
        </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'deviceinfo',
  components: {},
  data () {
    return {
      lampboardsOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      detectorboardsOptions: [0, 1, 2],
      ioboardsOptions: [0, 1, 2],
      startyellowflashOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      startallredOptions: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
      greenwavecycleOptions: [0, 1, 2, 3, 4, 5]
    }
  },
  computed: {
    ...mapState({
      customInfo: state => state.globalParam.tscParam.customInfo
    })
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
      let customInfo = this.globalParamModel.getParamsByType('customInfo')
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
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.device-info {
  margin-top: 10px;
  height: 18px;
  font-family: SourceHanSansCN-Regular;
  font-size: 18px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #666666;
}
.device-message {
  margin-top: 15px;
  height: 100px;
  background-color: rgba(236, 245, 255, 0.3);
//   background-color: #ecf5ff;
//   opacity: 0.3;
}
.device-content {
  // position: relative;
  margin-top: 10px;
  left: 20px;
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #303133;
}
.device-value {
  position: relative;
}
.device-header {
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #303133;
}
</style>
