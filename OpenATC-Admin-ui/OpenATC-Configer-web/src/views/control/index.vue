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
  <div class="control-container">
    <div class="control-left">
        <div class="control-column"><span>{{$t('edge.control.mode')}}</span><span>{{controlData.mode}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.control')}}</span><span>{{controlData.control}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.cycle')}}</span><span>{{controlData.cycle}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.offset')}}</span><span>{{controlData.offset}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.curTime')}}</span><span>{{controlData.curTime}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.syncTime')}}</span><span>{{controlData.syncTime}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.score')}}</span><span>{{controlData.score}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.level')}}</span><span>{{controlData.level}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.patternid')}}</span><span>{{controlData.patternid}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.name')}}</span><span>{{controlData.name}}</span></div>
        <div class="control-column"><span>{{$t('edge.control.current_phase')}}</span><span>{{controlData.current_phase}}</span></div>
        <br/>
        <div v-for="(item, index) in controlData.phase" :key="index">
            <div class="control-column"><span>{{$t('edge.control.phase')}}{{index + 1}}:{</span>
              <span class="control-column"><span>{{$t('edge.control.phase_countdown')}}</span><span>{{item.phase_countdown}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.split')}}</span><span>{{item.split}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.countdown')}}</span><span>{{item.countdown}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.id')}}</span><span>{{item.id}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.type')}}</span><span>{{item.type}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.light_countdown')}}</span><span>{{item.light_countdown}}</span></span>
            <span>&nbsp;&nbsp;}</span></div>
        </div>
        <br/>
        <div v-for="(item, index) in controlData.rings" :key="'info2-'+index">
            <div class="control-column"><span>{{$t('edge.control.ring')}}{{index + 1}}:{</span>
              <span class="control-column"><span>{{$t('edge.control.sequence')}}</span><span>{{item.sequence}},</span></span>
              <span class="control-column"><span>{{$t('edge.control.num')}}</span><span>{{item.num}}</span></span>
            <span>&nbsp;&nbsp;}</span></div>
        </div>
    </div>
    <div class="control-right">
        <el-form ref="form" :model="form" label-width="80px">
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
                <!-- <el-input v-model="form.control" style="width: 200px"></el-input> -->
            </el-form-item>
            <el-form-item :label="$t('edge.control.pattern')">
                <el-input v-model="form.pattern" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">{{$t('edge.common.confirm')}}</el-button>
                <!-- <el-button>取消</el-button> -->
            </el-form-item>
        </el-form>
    </div>
  </div>
</template>

<script>
import { getTscControl, putTscControl } from '@/api/control'
// import { getKiss } from '@/utils/auth'
export default {
  name: 'control',
  data () {
    return {
      controlData: {},
      form: {
        control: '',
        pattern: ''
      },
      phaseControlTimer: Object,
      mode: '3',
      Mode: new Map([[0, '其他控制'], [1, '自主控制'], [2, '中心控制'], [3, '前端控制'], [4, '遥控器控制'], [5, '手动面板控制']]),
      Control: new Map([[0, '自主控制'], [1, '黄闪'], [2, '全红'], [3, '关灯'], [4, '步进'], [5, '定周期控制'], [6, '单点感应控制'], [7, '协调感应控制'], [8, '方案选择控制'], [9, '自适应控制'], [10, '优化控制']])
    }
  },
  computed: {
  },
  created () {
    this.mode = this.$store.state.agent.controlmode
    this.initData()
  },
  mounted () {
    this.phaseControlTimer = setInterval(() => {
      this.initData()
    }, 1000)
  },
  methods: {
    initData () {
      getTscControl().then((data) => {
        if (!data.data.success) {
          this.$message.error(data.data.message)
          return
        }
        this.controlData = this.handleGetData(data.data.data.data)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    onSubmit () {
      let control = { ...this.controlData }
      // if (getKiss() === 'Kiss') {
      //   control.mode = 2
      // } else {
      //   control.mode = 3 // 3 表示边缘模式
      // }
      control.mode = Number(this.mode)
      control.control = Number(this.form.control)
      control.patternid = Number(this.form.pattern)
      // control.agentid = '22'
      let controlObj = this.handlePutData(control)
      putTscControl(controlObj).then(data => {
        if (!data.data.success) {
          this.$message.error(data.data.message)
          return
        }
        // downloadTscParam(this.$store.state.user.name, this.$store.getters.tscParam)
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
      console.log('')
    },
    handleGetData (data) {
      Object.keys(data).forEach(function (key) {
        if (key === 'mode') {
          if (data[key] > 0 && data[key] < 6) {
            data[key] = this.Mode.get(data[key])
          } else {
            data[key] = this.Mode.get(0)
          }
        }
        if (key === 'control') {
          data[key] = this.Control.get(data[key])
        }
      })
      return data
    },
    handlePutData (data) {
      Object.keys(data).forEach(function (key) {
        if (key === 'control') {
          for (let [k, value] of this.Control) {
            if (data[key] === value) {
              data[key] = k
            }
          }
        }
      })
      return data
    }
  },
  destroyed () {
    clearInterval(this.phaseControlTimer)
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// .control-container {
//   font-weight: bold;
//   color: #42daff;
//   margin-top: 20px;
// }
.control-column {
    margin-left: 20px;
}
.control-left {
    float: left;
}
.control-right {
    float: right;
    margin-right: 20px;
}
</style>
