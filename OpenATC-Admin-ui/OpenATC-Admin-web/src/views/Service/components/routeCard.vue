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
  <el-card class="box-card" v-show="isShow">
    <div slot="header" class="clearfix">
      <span><strong>属性</strong></span>
      <el-button
        style="float: right; padding: 3px 0"
        type="text"
        icon="el-icon-close"
        @click.stop="onCloseClick"
      ></el-button>
    </div>
    <div class="text item">
      <!-- <el-tag type="info" class="tag">已执勤</el-tag> -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="12">
              <div class="grid-content bg-purple">设备ID:</div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">{{ node.agentid }}</div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="12">
              <div class="grid-content bg-purple">执行方式:</div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                {{ node.controlName }}
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="12">
              <div class="grid-content bg-purple">驻留阶段:</div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">{{ node.value }}</div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="12">
              <div class="grid-content bg-purple">方案:</div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">{{ node.terminalname }}</div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="12">
              <div class="grid-content bg-purple">剩余时间:</div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">{{ node.resttime }}</div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
    <div class="btn-bottom" v-show="tabName === 'second'">
      <el-button
        style="float: right; padding: 3px 0"
        type="text"
        v-show="node.state === 0"
        @click="executeViproute"
        :disabled="isBtnDisabled"
        >立即执行</el-button
      >
      <el-button
        style="float: right; padding: 3px 0"
        type="text"
        v-show="node.state === 1"
        @click="executeViproute"
        :disabled="isBtnDisabled"
        >取消执行</el-button
      >
    </div>
  </el-card>
</template>

<script>
import { ExecuteViproute } from '@/api/service'
export default {
  name: 'nodeCard',
  props: {
    node: {
      type: Object
    },
    tabName: {
      type: String,
      default: 'first'
    }
  },
  data () {
    return {
      isShow: false,
      intervalId: 0,
      executeAgentid: '',
      isBtnDisabled: false
    }
  },
  updated () {
    if (this.executeAgentid === this.node.agentid && this.node.state === 0) {
      clearInterval(this.intervalId)
      this.intervalId = 0
    }
  },
  methods: {
    onCloseClick () {
      this.isShow = false
    },
    show () {
      this.isShow = true
    },
    executeViproute () {
      let reqData = {
        'agentid': this.node.agentid,
        'viprouteid': this.node.viprouteid,
        'operation': 1
      }
      if (this.node.state === 1) {
        reqData.operation = 0
      }
      // this.isBtnDisabled = true
      ExecuteViproute(reqData).then(res => {
        // this.isBtnDisabled = false
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return false
        }
        if (reqData.operation === 0) { // 取消执行
          clearInterval(this.intervalId)
          this.intervalId = 0
          this.executeAgentid = ''
          this.$emit('research')
        } else {
          let _this = this
          _this.executeAgentid = reqData.agentid
          _this.intervalId = setInterval(function () {
            _this.$emit('research')
          }, 2000)
        }
      })
    }
  }
}
</script>

<style>
.btn-bottom {
  float: right;
  margin: 15px;
}

.el-col {
  border-radius: 4px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
