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
<div class="text-page">
     <div class="container-left">
          <div class="container-left-top">
            <div class="agent-div">
              <div style="height: 210px;">
                <div class="agent-icon">
                  <div v-show="devStatus===3"><div class="yuanxing" style="background: rgba(64, 158, 255, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #409eff;"></div><div class="dev-status" style="color: #409eff; left: 40px;">{{$t('edge.overview.online')}}</div></div>
                  <div v-show="devStatus===2"><div class="yuanxing" style="background: rgba(179, 179, 179, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #b3b3b3;"></div><div class="dev-status" style="color: #b3b3b3; left: 40px;">{{$t('edge.overview.offline')}}</div></div>
                  <div v-show="devStatus===1"><div class="yuanxing" style="background: rgba(230, 162, 60, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #e6a23c;"></div><div class="dev-status" style="color: #e6a23c; left: 30px;">{{$t('edge.overview.onlineing')}}</div></div>
                </div>
                <div class="agent-num">
                  <div class="agent-id">{{$t('edge.overview.ipaddress')}}</div>
                  <div class="agent-number">{{ip}}</div>
                  <div class="agent-port">{{$t('edge.overview.deviceport')}}</div>
                  <div class="port-number">{{port}}</div>
                  <div class="agent-port">{{$t('edge.overview.agreement')}}</div>
                  <div class="port-number">{{protocol}}</div>
                </div>
              </div>
              <div style="height: 210px;">
                <div style="float: left; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.current_stage}}</div>
                  <div class="curr-num">{{$t('edge.overview.currentstage')}}</div>
                </div>
                <div style="float: right; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.total_stages}}</div>
                  <div class="curr-num">{{$t('edge.overview.allstagesnum')}}</div>
                </div>
                <div style="float: left; height: 50%; width: 50%; text-align: center;">
                  <div class="curr-grade">{{controlData.patternid}}</div>
                  <div class="curr-num">{{$t('edge.overview.patternnum')}}</div>
                </div>
                <div style="float: right; height: 50%; width: 50%; text-align: center;">
                  <!-- <div class="curr-grade">{{controlData.name}}</div>
                  <div class="curr-num">{{$t('edge.overview.patternname')}}</div> -->
                </div>
              </div>
            </div>
            <div class="other-div" v-for="(item, index) in showList" :key="index">
              <div style="height: 70px;">
                <div class="model-tupian"><svg-icon :icon-class="item.iconClass" className="model-icon"></svg-icon></div><div class="model-name">{{$t('edge.overview.showlist' + item.label)}}</div>
                <div class="to-detail" v-if="item.name === '当前/剩余时间' || item.name === '实时流量'" @click="handleChangeTable(item.name)">{{$t('edge.overview.details')}}</div>
              </div>
              <div>
                <div class="control-center" v-if="!item.insertHtml">{{item.value}}</div>
                <div class="control-time" v-if="item.insertHtml">
                  <span class="time">{{curTime}}</span><span class="time">{{syncTime}}</span>
                </div>
              </div>
            </div>
          </div>
          <!-- 实时相位表格 -->
          <div class="container-left-bottom" v-if="curTable === 'phase'">
            <CurPhase ref="currentPhase" :controlData="controlData" :tableData="tableData"/>
          </div>
          <!-- 实时流量表格 -->
          <div class="container-left-bottom" v-if="curTable === 'currentvolume'">
            <CurVolume :volumeData="volumeData"/>
          </div>
        </div>
        <div class="container-right">
          <div class="control-right">
            <el-form ref="form" :model="form" label-width="110px">
              <el-form-item :label="$t('edge.control.mode_style')" v-if="form.mode != ''">
                  <div>{{form.mode}}</div>
                </el-form-item>
                <el-form-item :label="$t('edge.control.control_style')">
                    <el-select v-model="control" :placeholder="$t('edge.common.select')">
                        <el-option :label="$t('edge.overview.autocontrol')" value="0"></el-option>
                        <el-option :label="$t('edge.overview.yellowflash')" value="1"></el-option>
                        <el-option :label="$t('edge.overview.allred')" value="2"></el-option>
                        <el-option :label="$t('edge.overview.step')" value="4"></el-option>
                        <el-option :label="$t('edge.overview.fixedperiod')" value="5"></el-option>
                        <el-option :label="$t('edge.overview.inductioncontrol')" value="6"></el-option>
                        <el-option :label="$t('edge.overview.selfadaption')" value="9"></el-option>
                        <el-option :label="$t('edge.overview.nocablecoordination')" value="10"></el-option>
                        <el-option :label="$t('edge.overview.phasewalk')" value="12"></el-option>
                        <el-option :label="$t('edge.overview.websteroptimization')" value="14"></el-option>
                        <el-option :label="$t('edge.overview.inductivePedestrianCrossControl')" value="19"></el-option>
                        <el-option :label="$t('edge.overview.custom')" value="999"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.controlmodevalue')">
                    <el-input v-model="controlNum" style="width: 70%" :disabled="control!=='999'"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.control.pattern')">
                    <el-input v-model="form.terminal" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.control.value')">
                    <el-input v-model="form.value" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.delay')">
                    <el-input v-model="form.delay" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.duration')">
                    <el-input v-model="form.duration" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('edge.overview.extendedContent')">
                    <el-input class="jsontextarea" type="textarea" v-model="form.data" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" size="small">{{$t('edge.common.setup')}}</el-button>
                    <el-button type="primary" @click="onGet" size="small">{{$t('edge.common.query')}}</el-button>
                </el-form-item>
            </el-form>
          </div>
        </div>
</div>
</template>
<script>
import { putTscControl, getTscPattern, getTscCurrentVolume } from '@/api/control'
import { getMessageByCode } from '../../../utils/responseMessage'
import CurVolume from './currentVolume'
import CurPhase from './currentPhase'
export default {
  name: 'textpage',
  components: {
    CurVolume,
    CurPhase
  },
  data () {
    return {
      loading: {},
      controlData: {},
      control: '',
      controlNum: '',
      form: {
        // control: '',
        terminal: '',
        mode: '',
        value: '',
        delay: '',
        duration: ''
      },
      tableData: [],
      curTable: 'phase',
      curTime: '--',
      syncTime: '--',
      volumeData: null,
      ParamsMap: new Map([['控制模式', 'mode'], ['周期', 'cycle'], ['控制方式', 'control'], ['相位差', 'offset'], ['当前时间', 'curTime'], ['剩余时间', 'syncTime']]),
      showList: [{
        iconClass: 'model',
        name: '控制模式',
        value: '--',
        label: '1'
      }, {
        iconClass: 'cycle',
        name: '周期',
        value: '--',
        label: '2'
      }, {
        iconClass: 'time',
        name: '当前/剩余时间',
        insertHtml: true,
        label: '3'
      },
      {
        iconClass: 'maincontrol',
        name: '控制方式',
        value: '--',
        label: '4'
      }, {
        iconClass: 'phasediff',
        name: '相位差',
        value: '--',
        label: '5'
      }, {
        iconClass: 'currentvolume',
        name: '实时流量',
        value: '--',
        label: '6'
      }],
      list: [{
        iconClass: 'model',
        name: '控制模式',
        value: '--'
      }, {
        iconClass: 'cycle',
        name: '周期',
        value: '--'
      }, {
        iconClass: 'time',
        name: '当前时间',
        value: '--'
      }, {
        iconClass: 'maincontrol',
        name: '控制方式',
        value: '--'
      }, {
        iconClass: 'phasediff',
        name: '相位差',
        value: '--'
      }, {
        iconClass: 'time',
        name: '剩余时间',
        value: '--'
      }]
    }
  },
  props: {
    agentId: {
      type: String
    },
    originControlData: {
      type: Object
    },
    devStatus: {
      type: Number
    },
    ip: {
      type: String
    },
    port: {
      type: String
    },
    protocol: {
      type: String
    }
  },
  watch: {
    control: {
      handler: function (val, oldVal) {
        if (val !== oldVal) {
          if (val !== '999') {
            this.controlNum = val
          } else {
            this.controlNum = ''
          }
        }
      }
    },
    originControlData: {
      handler: function (val) {
        this.controlData = JSON.parse(JSON.stringify(val))
        this.initData()
      }
    }
  },
  methods: {
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
    initData () {
      this.handleList(this.controlData)
      if (this.curTable === 'phase') {
        this.handleTableData(this.controlData)
      }
    },
    handleChangeTable (name) {
      switch (name) {
        case '当前/剩余时间':
          this.curTable = 'phase'
          break
        case '实时流量':
          this.curTable = 'currentvolume'
          break
        default: break
      }
    },
    onSubmit () {
      if (this.form.data && !this.isJsonString(this.form.data)) {
        this.$message.error(this.$t('edge.overview.JSONFormatError'))
        return
      }
      if (this.control === '999' && this.controlNum === '') {
        this.$message.error(this.$t('edge.overview.controlnumerrormess'))
        return
      }
      this.lockScreen()
      let control = {}
      control.control = Number(this.control === '999' ? this.controlNum : this.control)
      control.terminal = Number(this.form.terminal)
      control.value = Number(this.form.value)
      control.delay = Number(this.form.delay)
      control.duration = Number(this.form.duration)
      if (this.form.data) {
        // eslint-disable-next-line no-useless-escape
        control.data = JSON.parse(this.form.data)
      }
      // let controlObj = this.handlePutData(control)
      putTscControl(control).then(data => {
        this.unlockScreen()
        let success = 0
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        if (data.data.data && data.data.data.data) {
          success = data.data.data.data.success
          if (success !== 0) {
            let errormsg = 'edge.overview.putTscControlError' + success
            this.$message.error(this.$t(errormsg))
            return
          }
        }
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    onGet () {
      this.lockScreen()
      getTscPattern(this.agentId).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$message.success(this.$t('edge.common.querysucess'))
        let patternData = data.data.data.data
        let patternList = [0, 1, 2, 4, 5, 6, 10, 12, 14, 19]
        if (patternList.includes(patternData.control)) {
          this.control = String(patternData.control)
        } else {
          this.control = '999'
          this.controlNum = String(patternData.control)
        }
        this.form.terminal = String(patternData.terminal)
        if (this.$i18n.locale === 'en') {
          this.form.mode = this.ParamsModeEn.get(patternData.mode)
        } else if (this.$i18n.locale === 'zh') {
          this.form.mode = this.ParamsMode.get(patternData.mode)
        }
        this.form.value = patternData.value
        this.form.delay = patternData.delay
        this.form.duration = patternData.duration
        this.form.data = patternData.data
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    handleList (value) {
      for (let item of this.list) {
        let paramValue = this.ParamsMap.get(item.name)
        item.value = value[paramValue]
      }
      this.getShowList()
    },
    getShowList () {
      this.list.forEach(ele => {
        if (ele.name === '当前时间') {
          this.curTime = ele.value
        }
        if (ele.name === '剩余时间') {
          this.syncTime = ele.value
        }
        this.showList.forEach(item => {
          if (ele.name === item.name) {
            item.value = ele.value
          }
        })
      })
    },
    handleTableData (value) {
      this.tableData = []
      if (!value.phase) return
      let phaseList = value.phase
      let rings = value.rings
      for (let ring of rings) {
        let list = ring.sequence
        for (let item of list) {
          let obj = {}
          obj.ring = ring.num
          obj.phase = item
          for (let phase of phaseList) {
            let phaseId = phase.id
            if (item === phaseId) {
              obj.countdown = phase.countdown
              obj.split = phase.split
              obj.countdown = phase.countdown
              obj.id = phase.id
              obj.type = phase.type
              obj.light_countdown = phase.light_countdown
            }
          }
          this.tableData.push(obj)
        }
      }
      this.$refs.currentPhase.handleObjectSpan()
    },
    getVolume () {
      getTscCurrentVolume(this.agentId).then((data) => {
        // 与每秒获取信号机状态共用一套断线重连机制，此处不做清空定时器的处理
        if (!data.data.success) {
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          return
        }
        if (data.data.data.data) {
          this.volumeData = JSON.parse(JSON.stringify(data.data.data.data))
          this.getVolumeSum()
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getVolumeSum () {
      let sumVolume = 0
      if (!this.volumeData.detector) return
      this.volumeData.detector.forEach(currentvolume => {
        sumVolume = sumVolume + currentvolume.largevehnum + currentvolume.middlevehnum + currentvolume.smallvehnum
      })
      this.showList.forEach(item => {
        if (item.name === '实时流量') {
          item.value = sumVolume
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
