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
  <div class="distancePanel">
    <el-table
      stripe
      highlight-current-row
      ref="distanceTable"
      class="distanceTable"
      :data="distanceData"
      v-clickoutside="cancelEdit"
      style="width: 100%">
      <el-table-column
        type="index"
        label="#"
        align="center">
      </el-table-column>
      <el-table-column
        prop="agentid"
        :label="$t('openatc.greenwaveoptimize.deviceid')"
        align="center">
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.distancemeter')"
        align="center">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" v-model.number="scope.row.distance" @change="handleEdit('distance', scope.$index, scope.row)" style="width: 142px;margin: 0 auto;"></el-input-number>
          <span>{{scope.row.distance}}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.widthmeter')"
        align="center">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" v-model.number="scope.row.width" @change="handleEdit('width', scope.$index, scope.row)" style="width: 142px;margin: 0 auto;"></el-input-number>
          <span>{{scope.row.width}}</span>
        </template>
      </el-table-column>
  </el-table>
</div>
</template>
<script>
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
  name: 'distance',
  props: {
    distanceData: {
      type: Array
    }
  },
  directives: { clickoutside },
  data () {
    return {
    }
  },
  methods: {
    cancelEdit (e) {
      this.$refs.distanceTable.setCurrentRow()
    },
    handleEdit (key, index, row) {
      const data = {
        agentid: row.agentid
      }
      data[key] = row[key]
      this.$emit('changeData', data)
    }
  }
}
</script>

<style>
.distancePanel .el-table td, .el-table th {
    padding: 7px 0;
}
.distancePanel .el-table .cell {
    line-height: 32px;
}
 .distanceTable .el-input--small .el-input__inner {
    height: 32px;
    line-height: 32px;
}
 .distanceTable .el-input-number__decrease, .el-input-number__increase {
    top: 1px;
}
 .distanceTable .el-input-number.is-controls-right .el-input-number__decrease {
    bottom: 1px;
}
 .distanceTable .el-input-number.is-controls-right .el-input-number__decrease, .el-input-number.is-controls-right .el-input-number__increase {
    height: 16px;
    line-height: 16px !important;
}
</style>

<style scoped>
.distancePanel {
  border: solid 1px #e6e6e6;
}
.distanceTable .el-input-number {
   display: none
}
.distanceTable .current-row .el-input-number {
  display: block
}
.distanceTable .current-row .el-input-number+span {
  display: none
}
</style>
