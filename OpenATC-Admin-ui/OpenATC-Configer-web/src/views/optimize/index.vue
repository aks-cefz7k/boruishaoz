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
  <div class="app-container" ref="opt-container">
    <el-row class="top-row">
      <div class="grid-content bg-purple">
        <span class="sysytem-strategy">{{$t('edge.optimize.strategy')}}</span>
        <el-select v-model="form.region" :placeholder="$t('edge.optimize.mathematical')" size="small">
          <el-option :label="$t('edge.optimize.mathematical')" value="数学模型"></el-option>
         <!-- <el-option label="深度学习" value="深度学习"></el-option> -->
        </el-select>
        <span style="margin-left: 15px;"><el-checkbox v-model="checked">{{$t('edge.optimize.learn')}}</el-checkbox></span>
      </div>
    </el-row>
    <el-row class="bottom-row">
         <span class="system-saturation">{{$t('edge.optimize.saturation')}}</span>
         <br/>
    </el-row>
    <el-table class="tb-edit" ref="singleTable" :data="optimizeList" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row v-clickoutside="cancelTable" :max-height="tableHeight" id="footerBtn" @row-click="handdle">
      <el-table-column align="center" label='No' min-width="40">
        <template slot-scope="scope">
          <span>{{scope.$index+1}}</span>
        </template>
      </el-table-column>
      <el-table-column class="table-column" :label="$t('edge.optimize.phase')" min-width="150" align="center" prop="phaseid">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.phaseid"  @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.phaseid}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.optimize.flow')" min-width="100" prop="flow">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.maxflow" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxflow}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.optimize.speed')" min-width="100" prop="speed">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="0.01" v-model.number="scope.row.maxspeed" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxspeed}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.optimize.queue')" min-width="100" prop="queue">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.maxqueue" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxqueue}}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column align="center" :label="$t('edge.optimize.speed')" min-width="100" prop="speed">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.maxqueue" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxqueue}}</span>
        </template>
      </el-table-column> -->
      <el-table-column align="center" class-name="status-col" :label="$t('edge.optimize.Occupancy')" min-width="100" prop="maxoccupancy">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="25" :step="0.01" v-model.number="scope.row.maxoccupancy" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxoccupancy}}</span>
        </template>
      </el-table-column>
    </el-table>

    <div style="text-align: right; margin-top: 20px;">
    <el-button class="el-icon-check" style="margin-bottom:10px; font-size: 10px;" type="primary" @click="onCommit(false)"> {{$t('edge.common.commit')}}</el-button>
    <el-button class="iconfont icon-tijiaobingzhihang" style="margin-bottom:10px; font-size: 10px;color: #b6f0ff" type="primary" @click="onCommitAndExec"> {{$t('edge.common.commitandexecute')}}</el-button>
    </div>
  </div>
</template>

<script>
import { initOptParam, uploadOptParam, downloadOptParam, runStrategyType } from '@/api/optimize'
import { getMessageByCode } from '@/utils/responseMessage'
const clickoutside = {
  // 初始化指令
  bind (el, binding, vnode) {
    function documentHandler (e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e)
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.__vueClickOutside__ = documentHandler
    document.addEventListener('click', documentHandler)
  },
  update () {},
  unbind (el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.__vueClickOutside__)
    delete el.__vueClickOutside__
  }
}
export default {
  name: 'optimize',
  data () {
    return {
      tableHeight: 760,
      listLoading: false,
      form: {
        name: '',
        region: '',
        type: []
      },
      checked: false,
      // optimizeListTest: [{ 'agentid': '22','phaseid': 1,'maxflow': 32.0,'maxspeed': 52.0,'maxqueue': 10.0,'maxoccupancy': 21.0 }, { 'agentid': '22','phaseid': 2,'maxflow': 32.0,'maxspeed': 52.0,'maxqueue': 10.0,'maxoccupancy': 21.0 }],
      optimizeList: []
    }
  },
  directives: { clickoutside },
  created () {
    this.getParams()
    // this.initOptParam()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['opt-container'].offsetHeight - 120
      window.onresize = function () {
        _this.tableHeight = _this.$refs['opt-container'].offsetHeight - 120
      }
    })
  },
  methods: {
    cancelTable (e) {
      this.$refs.singleTable.setCurrentRow()
    },
    onCommit (exec) {
      let optimize = JSON.stringify(this.optimizeList)
      let override = 0
      if (this.checked) {
        override = 1
      }
      downloadOptParam(optimize, override).then(data => {
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        if (exec) {
          this.commitAndExec()
        }
        this.$alert(this.$t('edge.common.download'), { type: 'success' })
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    onCommitAndExec () {
      // let exec = true
      this.onCommit(true)
      // this.commitAndExec()
    },
    commitAndExec () {
      runStrategyType().then(data => {
        if (!data.data.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$alert(this.$t('edge.common.downloadandrun'), { type: 'success' })
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    initOptParam () {
      initOptParam().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.getParams()
        // this.listLoading = true
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getParams () {
      uploadOptParam().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        for (let obj of res.data) {
          let paramList = JSON.parse(obj.jsonparam)
          if (obj.override) {
            this.checked = true
          }
          for (let param of paramList) {
            param.maxoccupancy = Math.floor(param.maxoccupancy * 100) / 100
            param.maxspeed = Math.floor(param.maxspeed * 100) / 100
            this.optimizeList.push(param)
          }
        }
        // this.listLoading = true
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handdle (row, event, column) {
      if (this.checked) {
        this.$refs.singleTable.setCurrentRow()
      }
    }
  }
}
</script>

<style>
/* * {
    margin: 0;
    padding: 0;
} */
.top-row {
    margin-bottom: 40px;
  }
.bottom-row {
    margin-bottom: 10px;
  }
body {
    overflow: auto;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
}
.tb-edit .el-input {
    display: none;
}
.tb-edit .current-row .el-input {
    display: block;
}
.tb-edit .current-row .el-input + span {
    display: none;
}
.tb-edit .el-select {
    display: none;
}
.tb-edit .current-row .el-select {
    display: block;
}
.tb-edit .current-row .el-select + span {
    display: none;
}
.tb-edit .el-input-number {
    display: none;
}
.tb-edit .current-row .el-input-number {
    display: block;
}
.tb-edit .current-row .el-input-number + span {
    display: none;
}
.tb-edit .el-col {
    display: none;
}
.tb-edit .current-row .el-col {
    display: block;
}
.tb-edit .current-row .el-col + span {
    display: none;
}
.tb-edit .el-popover {
    display: none;
}
.tb-edit .current-row .el-popover {
    display: block;
}
.tb-edit .current-row .el-popover + span {
    display: none;
}
.showSpan {
  display: block;
}
.tb-edit .current-row .showSpan {
  display: none;
}
</style>
