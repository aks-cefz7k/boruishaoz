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
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="信息" name="information">
      <div class="system-information">
        <div class="signal-version">
            <div class="signal-version-name">信号机版本：</div>
            <div class="signal-version-value" style="height: 70px;">
                <div>
                    <div class="software" style="top: 10px;"><div style="float: left;">软件版本：</div><div>{{software}}</div></div>
                    <div class="software" style="top: 30px;"><div style="float: left;">硬件版本：</div><div>{{hardware}}</div></div>
                </div>
            </div>
        </div>
        <div class="currsystemtime" style="margin-top: 50px;">
            <div class="signal-version-name">当前系统时间：</div>
            <div class="signal-version-value" style="height: 40px;">
              <div>
                <div class="software" style="top: 15px;"><div style="float: left;">时间：</div><div>{{systemTime}}</div></div>
              </div>
            </div>
            <div class="signal-version-bottom">
              <el-button type="primary" @click="editCurrSystemTime" size="mini">设置</el-button>
            </div>
        </div>
        <div class="feature-parameter-version" style="margin-top: 50px;">
            <div class="signal-version-name">特征参数版本：</div>
            <div class="signal-version-value" style="height: 40px;">
              <div>
                <div class="software" style="top: 15px;"><div style="float: left;">版本号：</div><div>{{paramVersion}}</div></div>
              </div>
            </div>
            <div class="signal-version-bottom">
              <el-button type="primary" @click="editParamVersion" size="mini">设置</el-button>
            </div>
        </div>
        <div class="identification-code" style="margin-top: 50px;">
            <div class="signal-version-name">识别码：</div>
            <div class="signal-version-value" style="height: 40px;">
              <div>
                <div class="software" style="top: 15px;"><div style="float: left;">设备识别码：</div><div>{{code}}</div></div>
              </div>
            </div>
            <!-- <div class="signal-version-bottom">
              <el-button type="primary" @click="editSignCode" size="mini">设置</el-button>
            </div> -->
        </div>
        <div class="identification-code" style="margin-top: 50px;">
            <div class="signal-version-name">远程调试：</div>
            <div class="signal-version-value" style="height: 95px;">
              <el-form
                ref="template"
                :model="remoteDebugInfo"
                label-width="80px">
                    <el-form-item
                        label="是否开启"
                        prop="status"
                        style="margin-bottom: 5px;">
                      <el-switch
                        v-model="remoteDebugInfo.status"
                        active-color="#409EFF"
                        :active-value="1"
                        :inactive-value="0">
                      </el-switch>
                    </el-form-item>
                    <el-form-item
                        label="自动关闭"
                        prop=".duration">
                        <el-select size="small" v-model="remoteDebugInfo.duration" placeholder="" style="width: 160px">
                            <el-option v-for="item in durationList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <div class="signal-version-bottom">
              <el-button type="primary" @click="editRemoteDebug" size="mini">确定</el-button>
            </div>
        </div>
      </div>
    </el-tab-pane>
    <!-- <el-tab-pane label="IP" name="ip">
        <div class="system-information">
            <div class="currsystemtime">
                <div class="signal-version-name">IP数据设置：</div>
                <div class="signal-version-value" style="height: 110px;">
                    <div>
                        <div class="software" style="top: 20px;"><div style="float: left;">IP：</div><div style="margin-left: 70px;">{{ip}}</div></div>
                        <div class="software" style="top: 30px;"><div style="float: left;">子网掩码：</div><div style="margin-left: 70px;">{{subnetmask}}</div></div>
                        <div class="software" style="top: 40px;"><div style="float: left;">网关：</div><div style="margin-left: 70px;">{{gateway}}</div></div>
                    </div>
                </div>
                <div class="signal-version-bottom">
                    <el-button type="primary" @click="editSignIp" size="mini">设置</el-button>
                </div>
            </div>
      </div>
    </el-tab-pane> -->
    <!-- <el-tab-pane label="串口" name="serialPort">
        <div class="system-information">
            <div class="currsystemtime">
                <div class="signal-version-name">串口数据：</div>
                <div class="signal-version-value" style="height: 140px;">
                    <div>
                        <div class="software" style="top: 30px;"><div style="float: left;">波特率：</div><div style="margin-left: 85px;">{{Baudrate}}</div></div>
                        <div class="software" style="top: 40px;"><div style="float: left;">数据位：</div><div style="margin-left: 85px;">{{databit}}</div></div>
                        <div class="software" style="top: 50px;"><div style="float: left;">停止位：</div><div style="margin-left: 85px;">{{stopbit}}</div></div>
                        <div class="software" style="top: 60px;"><div style="float: left;">奇偶校验位：</div><div style="margin-left: 85px;">{{paritybit}}</div></div>
                    </div>
                </div>
                <div class="signal-version-bottom">
                    <el-button type="primary" @click="editSerialPort" size="mini">设置</el-button>
                </div>
            </div>
      </div>
    </el-tab-pane> -->
    <el-tab-pane label="远程控制" name="remote">
        <div class="system-information">
            <div class="currsystemtime">
                <div class="signal-version-name">远程控制：</div>
                <div class="signal-version-value" style="height: 70px;">
                    <div>
                        <div class="software"><div style="float: left; margin-top: 30px;">操作</div>
                          <div class="remote-select">
                            <el-select v-model="remoteControl" placeholder="请选择" size="small">
                              <el-option
                                v-for="item in remoteOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>
                          </div>
                        </div>
                    </div>
                </div>
                <div class="signal-version-bottom">
                    <el-button type="primary" @click="editRemoteControl" size="mini">确定</el-button>
                </div>
            </div>
            <div class="currsystemtime" style="float: left; margin-top: 50px;">
                <div class="signal-version-name">系统更新：</div>
                <div class="signal-version-bottom">
                    <el-button type="primary" @click="chooseFile" size="mini">选择文件</el-button>
                </div>
            </div>
            <div class="currsystemtime" style="float: left; margin-top: 50px;">
                <div class="signal-version-name">数据更新：</div>
                <div class="signal-version-bottom">
                    <el-button type="primary" @click="updateUSB" size="mini">更新U盘数据</el-button>
                </div>
            </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="通道检测" name="channel">
      <channelControl />
    </el-tab-pane>
    <!-- <el-tab-pane label="手动控制" name="manual">
      <manualControl></manualControl>
    </el-tab-pane> -->
  </el-tabs>
  <systemtime ref="editSystemtime"></systemtime>
  <paramversion ref="editParamversion"></paramversion>
  <serialport ref="editSerialport"></serialport>
  <signcode ref="editSigncode"></signcode>
  <signip ref="editSignip"></signip>
  <updatefile ref="updateFile"></updatefile>
  </div>
</template>

<script>
import systemtime from './systemDialog/systemTime'
import paramversion from './systemDialog/paramVersion'
import serialport from './systemDialog/serialPort'
import signcode from './systemDialog/signCode'
import signip from './systemDialog/signIp'
import updatefile from './systemDialog/updateFile'
import manualControl from './manualControl'
import channelControl from './channelControl'
import { getSignVersion, getSystemTime, getParamVersion, getCode, getSignIp, getSerialPort, setRemoteControl, getRemoteDebug, setRemoteDebug, udiskupdate } from '@/api/system'
export default {
  name: 'system',
  components: { systemtime, paramversion, serialport, signcode, signip, updatefile, manualControl, channelControl },
  data () {
    return {
      activeName: 'information',
      software: '--',
      hardware: '--',
      systemTime: '--',
      paramVersion: '--',
      code: '--',
      ip: '--',
      subnetmask: '--',
      gateway: '--',
      Baudrate: 0,
      databit: 0,
      stopbit: 0,
      paritybit: '--',
      remoteOptions: [{
        value: 'reboot',
        label: '重启'
      }, {
        value: 'auth',
        label: '授权'
      }, {
        value: 'custom',
        label: '自定义'
      }],
      remoteControl: 'reboot',
      remoteDebugInfo: {
        status: 0,
        duration: 0
      },
      durationList: [{
        label: '5min',
        value: 300
      }, {
        label: '60min',
        value: 3600
      }, {
        label: '无限制',
        value: 0
      }]
    }
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        console.log('进入系统页面')
        if (val.query !== undefined) {
          console.log('获取系统数据')
          this.getSignVersion() // 获取信号机版本
          this.getSystemTime() // 获取当前时间
          this.getParamVersion() // 获取特征参数版本
          this.getCode() // 获取识别码
          this.getSignIp() // 获取ip数据
          this.getSerialPort() // 获取串口数据
          // this.getRemoteDebug() // 获取远程调试信息
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    this.getSignVersion() // 获取信号机版本
    this.getSystemTime() // 获取当前时间
    this.getParamVersion() // 获取特征参数版本
    this.getCode() // 获取识别码
    this.getSignIp() // 获取ip数据
    this.getSerialPort() // 获取串口数据
    // this.getRemoteDebug() // 获取远程调试信息
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab, event)
    },
    getSignVersion () {
      getSignVersion().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.software = res.data.data.software
        this.hardware = res.data.data.hardware
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getSystemTime () {
      getSystemTime().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.systemTime = res.data.data.time
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getParamVersion () {
      getParamVersion().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.paramVersion = res.data.data.version
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getCode () {
      getCode().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.code = res.data.data.code
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getSignIp () {
      getSignIp().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.ip = res.data.data.ip
        this.subnetmask = res.data.data.subnetmask
        this.gateway = res.data.data.gateway
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getSerialPort () {
      getSerialPort().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.Baudrate = res.data.data.Baudrate
        this.databit = res.data.data.databit
        this.stopbit = res.data.data.stopbit
        this.paritybit = res.data.data.paritybit
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getRemoteDebug () {
      getRemoteDebug().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        if (res.data.data) {
          this.remoteDebugInfo.status = res.data.data.status
          this.remoteDebugInfo.duration = res.data.data.duration
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    editRemoteControl () {
      setRemoteControl(this.remoteControl).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
          // onClose: () => {
          //   this.$parent.getCode()
          // }
        })
      })
    },
    editCurrSystemTime () {
      let editSystemtime = this.$refs.editSystemtime
      editSystemtime.onEditSystem(this.systemTime)
    },
    editParamVersion () {
      let editParamversion = this.$refs.editParamversion
      editParamversion.onEditParamVersion(this.paramVersion)
    },
    editSignCode () {
      let editSigncode = this.$refs.editSigncode
      editSigncode.onEditSignCode(this.code)
    },
    editSignIp () {
      let editSignip = this.$refs.editSignip
      editSignip.onEditSignip(this.ip, this.subnetmask, this.gateway)
    },
    editSerialPort () {
      let editSerialport = this.$refs.editSerialport
      editSerialport.onEditSerialPort(this.Baudrate, this.databit, this.stopbit, this.paritybit)
    },
    chooseFile () {
      let updateFile = this.$refs.updateFile
      updateFile.onUpdateFile()
    },
    editRemoteDebug () {
      setRemoteDebug(this.remoteDebugInfo).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
        })
      })
    },
    updateUSB () {
      udiskupdate().then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '更新成功！'
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
        })
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.app-container {
  min-width: 1730px;
}
.signal-version-name {
  margin-top: 20px;
  margin-left: 30px;
  height: 18px;
  font-family: SourceHanSansCN-Regular;
  font-size: 18px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #666666;
}
.signal-version-value {
  margin-top: 10px;
  margin-left: 30px;
  background-color: rgba(236, 245, 255, 0.3);
}
.signal-version-bottom {
  margin-top: 10px;
  margin-left: 30px;
}
.software {
  position: relative;
  left: 20px;
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #303133;
}
.remote-select {
  float: left;
  margin-left: 10px;
  margin-top: 20px;
}
.currsystemtime {
  width: 100%;
}
</style>
