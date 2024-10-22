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
  <div class="container-main">
    <div class="container-left">
      <div class="container-left-top">
        <div class="agent-div">
          <div style="height: 210px;">
            <div class="agent-icon">
              <div v-show="devStatus===3"><div class="yuanxing" style="background: rgba(64, 158, 255, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #409eff;"></div><div class="dev-status" style="color: #409eff; left: 40px;">在线</div></div>
              <div v-show="devStatus===2"><div class="yuanxing" style="background: rgba(179, 179, 179, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #b3b3b3;"></div><div class="dev-status" style="color: #b3b3b3; left: 40px;">离线</div></div>
              <div v-show="devStatus===1"><div class="yuanxing" style="background: rgba(230, 162, 60, 0.6);"></div><div class="iconfont icon-ziyuan" style="color: #e6a23c;"></div><div class="dev-status" style="color: #e6a23c; left: 30px;">联机中</div></div>
            </div>
            <div class="agent-num">
              <div class="agent-id">IP地址</div>
              <div class="agent-number">{{ip}}</div>
              <div class="agent-port">设备端口</div>
              <div class="port-number">{{port}}</div>
              <div class="agent-port">协议</div>
              <div class="port-number">{{protocol}}</div>
            </div>
          </div>
          <div style="height: 210px;">
            <div style="float: left; height: 50%; width: 50%; text-align: center;">
              <div class="curr-grade">{{controlData.current_stage}}</div>
              <div class="curr-num">当前阶段</div>
            </div>
            <div style="float: right; height: 50%; width: 50%; text-align: center;">
              <div class="curr-grade">{{controlData.total_stages}}</div>
              <div class="curr-num">总阶段数</div>
            </div>
            <div style="float: left; height: 50%; width: 50%; text-align: center;">
              <div class="curr-grade">{{controlData.patternid}}</div>
              <div class="curr-num">方案编号</div>
            </div>
            <div style="float: right; height: 50%; width: 50%; text-align: center;">
              <div class="curr-grade">{{controlData.name}}</div>
              <div class="curr-num">方案名称</div>
            </div>
          </div>
        </div>
        <div class="other-div" v-for="(item, index) in list" :key="index">
          <div style="height: 70px;">
            <div class="model-tupian"><svg-icon :icon-class="item.iconClass" className="model-icon"></svg-icon></div><div class="model-name">{{item.name}}</div>
          </div>
          <div style="height: 140px;">
            <div class="control-center">{{item.value}}</div>
          </div>
        </div>
      </div>
      <div class="container-left-bottom">
        <div class="current-phase">
          <div class="color-block"></div>
          <div class="curr-phase">
            <span>{{$t('edge.control.current_phase')}}</span>
            <span v-for="(currPhase, index) in controlData.current_phase" :key="index">
              <span v-if="index === 0">{{currPhase}}</span>
              <span v-else>{{', ' + currPhase}}</span>
            </span>
          </div>
          <div class="curr-phase" v-for="(ring, index) in controlData.rings" :key="index">
            <div class="curr-ring"><span>{{$t('edge.control.ring')}}: </span><span>{{ring.num}}</span></div>
            <div class="phase-order"><span>{{$t('edge.control.sequence')}} </span><span>{{ring.sequence}}</span></div>
          </div>
        </div>
        <div class="current-phasetable">
          <el-table
            :data="tableData"
            :span-method="objectSpanMethod"
            :cell-class-name="addClass"
            size="mini"
            style="width: 100%">
            <el-table-column
              align="center"
              prop="ring"
              label="环">
            </el-table-column>
            <el-table-column
              align="center"
              prop="id"
              label="相位id">
            </el-table-column>
            <!-- <el-table-column
              align="center"
              prop="phase"
              label="相位">
            </el-table-column> -->
            <el-table-column
              align="center"
              prop="phase_countdown"
              label="相位倒计时">
            </el-table-column>
            <el-table-column
              align="center"
              prop="split"
              label="绿信比">
            </el-table-column>
            <!-- <el-table-column
              align="center"
              prop="countdown"
              label="倒计时">
            </el-table-column> -->
            <el-table-column
              align="center"
              prop="type"
              label="相位类型">
            </el-table-column>
            <!-- <el-table-column
              align="center"
              prop="light_countdown"
              label="通道倒计时">
            </el-table-column> -->
          </el-table>
        </div>
      </div>
    </div>
    <div class="container-right">
      <div class="control-right">
        <el-form ref="form" :model="form" label-width="80px">
           <el-form-item :label="$t('edge.control.mode_style')" v-if="form.mode != ''">
              <div>{{form.mode}}</div>
            </el-form-item>
            <el-form-item :label="$t('edge.control.control_style')">
                <el-select v-model="form.control" :placeholder="$t('edge.common.select')">
                    <el-option label="默认" value="0"></el-option>
                    <el-option label="黄闪" value="1"></el-option>
                    <el-option label="全红" value="2"></el-option>
                    <el-option label="关灯" value="3"></el-option>
                    <el-option label="步进" value="4"></el-option>
                    <el-option label="定周期控制" value="5"></el-option>
                    <el-option label="单点感应控制" value="6"></el-option>
                    <el-option label="协调感应控制" value="7"></el-option>
                    <el-option label="方案选择控制" value="8"></el-option>
                    <el-option label="自适应控制" value="9"></el-option>
                    <el-option label="优化控制" value="10"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item :label="$t('edge.control.pattern')">
                <el-input v-model="form.pattern" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" size="small">{{$t('edge.common.setup')}}</el-button>
                <el-button type="primary" @click="onGet" size="small">{{$t('edge.common.query')}}</el-button>
                <!-- <el-button>取消</el-button> -->
            </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getTscControl, putTscControl, getTscPattern } from '@/api/control'
import { registerMessage } from '@/api/param'
import { setIframdevid } from '@/utils/auth'
export default {
  name: 'overview',
  data () {
    return {
      controlData: {},
      form: {
        control: '',
        pattern: '',
        mode: ''
      },
      spanArr: [],
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
      }],
      tableData: [],
      phaseControlTimer: null, // 定时器
      ParamsMap: new Map([['控制模式', 'mode'], ['周期', 'cycle'], ['控制方式', 'control'], ['相位差', 'offset'], ['当前时间', 'curTime'], ['剩余时间', 'syncTime']]),
      ParamsMode: new Map([[0, '默认模式'], [1, '自主模式'], [2, '中心模式'], [3, '边缘模式'], [4, '遥控器模式'], [5, '手动面板模式'], [6, '勤务模式'], [7, '优化模式']]),
      ParamsControl: new Map([[0, '自主控制'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '优化控制']]),
      phaseType: new Map([[1, '红'], [2, '黄'], [3, '绿']]), // phaseType表示红，黄，绿
      ip: '--',
      port: '--',
      protocol: '--',
      devStatus: 1,
      mode: '2',
      loading: {},
      agentId: '0'
    }
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        if (val.query !== undefined) {
          this.agentId = this.$route.query.agentid
          setIframdevid(this.agentId)
          this.ip = this.$route.query.IP
          this.port = this.$route.query.port
          this.protocol = this.$route.query.protocol
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    // let mode = this.$route.query.mode
    // if (mode === '2') {
    //   this.$store.dispatch('SaveContromode', mode)
    // }
    if (this.$route.query !== undefined) {
      this.agentId = this.$route.query.agentid
      setIframdevid(this.agentId)
      this.ip = this.$route.query.IP
      this.port = this.$route.query.port
      this.protocol = this.$route.query.protocol
    }
    this.registerMessage() // 注册消息
  },
  mounted () {
  },
  methods: {
    registerMessage () {
      registerMessage(this.agentId).then(data => {
        if (!data.data.success) {
          this.devStatus = 2
          if (data.data.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error('通讯异常！')
          return
        }
        this.devStatus = 3
        this.clearPatternInterval() // 清除其他定时器
        this.phaseControlTimer = setInterval(() => {
          this.initData()
        }, 1000)
      })
    },
    clearPatternInterval () {
      if (this.phaseControlTimer !== null) {
        clearInterval(this.phaseControlTimer) // 清除定时器
        this.phaseControlTimer = null
      }
    },
    initData () {
      getTscControl(this.agentId).then((data) => {
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.devStatus = 2
            this.clearPatternInterval() // 清除其他定时器
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        // let param = JSON.parse(data.data.data)
        this.controlData = this.handleGetData(data.data.data.data)
        this.handleList(this.controlData)
        this.handleTableData(this.controlData)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handleObjectSpan () {
      let contactDot = 0
      this.spanArr = []
      this.tableData.forEach((item, index) => {
        item.index = index
        if (index === 0) {
          this.spanArr.push(1)
        } else {
          if (item.ring === this.tableData[index - 1].ring) {
            this.spanArr[contactDot] += 1
            this.spanArr.push(0)
          } else {
            this.spanArr.push(1)
            contactDot = index
          }
        }
      })
    },
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        const _row = this.spanArr[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
      // if (columnIndex === 1) {
      //   const _row = this.spanArr[rowIndex]
      //   const _col = _row > 0 ? 1 : 0
      //   return {
      //     rowspan: _row,
      //     colspan: _col
      //   }
      // }
    },
    onSubmit () {
      this.lockScreen()
      // let control = { ...this.controlData }
      let control = {}
      // control.mode = Number(this.mode)
      control.control = Number(this.form.control)
      control.pattern = Number(this.form.pattern)
      // let controlObj = this.handlePutData(control)
      putTscControl(control).then(data => {
        this.unlockScreen()
        if (!data.data.success) {
          this.$message.error(data.data.message)
          return
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
          this.$message.error(data.data.message)
          return
        }
        this.$message.success(this.$t('edge.common.querysucess'))
        let patternData = data.data.data.data
        this.form.control = String(patternData.control)
        this.form.pattern = String(patternData.pattern)
        this.form.mode = this.ParamsMode.get(patternData.mode)
      }).catch(error => {
        this.unlockScreen()
        this.$message.error(error)
        console.log(error)
      })
    },
    handleGetData (data) {
      let that = this
      if (data.name === '') {
        data.name = '方案' + data.patternid
      }
      Object.keys(data).forEach(function (key) {
        if (key === 'mode') {
          if (data[key] > 0 && data[key] < 6) {
            data[key] = that.ParamsMode.get(data[key])
          } else {
            data[key] = that.ParamsMode.get(0)
          }
        }
        if (key === 'control') {
          data[key] = that.ParamsControl.get(data[key])
        }
        if (key === 'phase') {
          for (let val of data[key]) {
            val.type = that.phaseType.get(val.type)
          }
        }
      })
      return data
    },
    handleList (value) {
      for (let item of this.list) {
        let paramValue = this.ParamsMap.get(item.name)
        item.value = value[paramValue]
      }
    },
    handleTableData (value) {
      this.tableData = []
      let phaseList = value.phase
      let rings = value.rings
      for (let ring of rings) {
        // let list = ring.sequence.split(' ')
        let list = ring.sequence
        for (let item of list) {
          let obj = {}
          obj.ring = ring.num
          obj.phase = item
          for (let phase of phaseList) {
            let phaseId = phase.id
            if (item === phaseId) {
              obj.phase_countdown = phase.phase_countdown
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
      this.handleObjectSpan()
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
    addClass ({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 2) {
        if (row.type === '绿') {
          return 'green'
        }
        if (row.type === '红') {
          return 'red'
        }
        if (row.type === '黄') {
          return 'yellow'
        }
      }
    }
  },
  destroyed () {
    this.clearPatternInterval() // 清除定时器
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.container-main {
  width: 100%;
  height: 880px;
  // min-width: 1250px;
  background: #f8f8f8;
}
.container-left {
  float: left;
  margin-top: 20px;
  margin-left: 20px;
  width: 76.8%;
  height: 840px;
  background-color: #ffffff;
  border: solid 1px #e6e6e6;
}
.container-right {
  float: left;
  margin-top: 20px;
  margin-left: 20px;
  width: 20%;
  height: 840px;
  background-color: #ffffff;
  border: solid 1px #e6e6e6;
}
.container-left-top {
  width: 100%;
  height: 420px;
}
.container-left-bottom {
  width: 100%;
  height: 400px;
  background-color: #ffffff;
}
.current-phase {
  width: 96%;
  height: 10px;
  margin-top: 1%;
  margin-left: 2%;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 10px;
  letter-spacing: 0px;
  color: #333333;
}
.current-phasetable {
  width: 96%;
  height: 380px;
  margin-top: 2%;
  margin-left: 2%;
}
.color-block {
  float: left;
  width: 14px;
  height: 14px;
  background-color: #459ffc;
}
.curr-phase {
  float: left;
  margin-left: 5px;
  margin-top: 1px;
}
.curr-ring {
  float: left;
  margin-left: 20px;
  margin-top: 1px;
}
.phase-order {
  float: left;
  margin-left: 20px;
  margin-top: 1px;
}
.curr-order {
  float: left;
  margin-left: 20px;
  margin-top: 1px;
}
.control-right {
  margin-top: 40px;
  margin-left: 20px;
}
.agent-div {
  float: left;
  width: 28%;
  height: 420px;
  border-bottom: solid 0.5px #e6e6e6;
}
.other-div {
  float: left;
  width: 24%;
  height: 210px;
  border-left: solid 1px #e6e6e6;
  border-bottom: solid 1px #e6e6e6;
}
.agent-icon {
  position:relative;
  float: left;
  width: 50%;
  height: 210px;
}
.agent-num {
  float: left;
  width: 50%;
  height: 210px;
}
.lianji-success {
  position:relative;
  float: right;
  right: 30px;
  top: 25px;
  text-align: center;
  width: 81px;
  height: 34px;
  background-color: #409eff;
  border-radius: 4px;
  font-size: 14px;
  padding:10px 0;
  color: #ffffff;
}
.lianji-fail {
  position:relative;
  float: right;
  right: 30px;
  top: 25px;
  text-align: center;
  width: 81px;
  height: 34px;
  background-color: #909399;
  border-radius: 4px;
  font-size: 14px;
  padding:10px 0;
  color: #ffffff;
}
.lianji-wait {
  position:relative;
  float: right;
  right: 30px;
  top: 25px;
  text-align: center;
  width: 110px;
  height: 34px;
  background-color: #e6a23c;
  border-radius: 4px;
  font-size: 14px;
  padding:10px 0;
  color: #ffffff;
}
.agent-id {
  margin-top: 20px;
  margin-right: 30px;
  text-align: right;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #999999;
}
.agent-number {
  margin-top: 10px;
  margin-right: 30px;
  text-align: right;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #333333;
}
.agent-port {
  margin-top: 20px;
  margin-right: 30px;
  text-align: right;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #999999;
}
.port-number {
  margin-top: 10px;
  margin-right: 30px;
  text-align: right;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #333333;
}
.model-name {
  float: left;
  margin-top: 32px;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #666666;
}
.model-tupian {
  float: left;
  margin-top: 20px;
  margin-left: 20px;
}
.model-icon {
  width: 40px;
  height: 40px;
}
.control-center {
  // float: left;
  text-align: center;
  margin-top: 40px;
  font-size: 30px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #333333;
}
.curr-grade {
  margin-top: 30px;
  font-size: 24px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #666666;
}
.curr-num {
  margin-top: 20px;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #999999;
}
.icon-ziyuan:before {
  content: "\e670";
  position:absolute;
  z-index:2;
  left: 45px;
  top: 72px;
  // color: #409eff;
}
.yuanxing {
  position:absolute;
  left: 20px;
  top: 50px;
  z-index:1;
  width: 90px;
  height: 90px;
  // background-color: #459ffc;
  opacity: 0.2;
  border-radius: 50%;
}
.dev-status {
  position:absolute;
  // text-align: center;
  // margin-top: 150px;
  // left: 30px;
  top: 150px;
  height: 21px;
  font-size: 22px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  // color: #409eff;
}
</style>
<style rel="stylesheet/scss" lang="scss">
.container-right .el-form-item__label {
    text-align: right;
    float: left;
    font-size: 14px;
    line-height: 40px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    font-weight: normal;
    font-stretch: normal;
    letter-spacing: 0px;
    color: #999999;
}
.container-right .el-select {
  width: 70%;
}
.current-phasetable .yellow {
  color: #ffd000;
}
.current-phasetable .red {
  color: #ff1b1b;
}
.current-phasetable .green {
  color: #20b84e;
}
</style>
