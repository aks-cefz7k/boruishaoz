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
  <el-select
    v-model="value"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    collapse-tags
    clearable
    filterable
    placeholder="请选择"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in options"
      :key="index"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select>
</template>
<script>
export default {
  name: 'SelectControl',
  props: {
    agentid: {
      type: String,
      required: false
    },
    multiple: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: 'small'
    },
    defaultValue: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      value: [],
      options: [
        {
          value: 0,
          label: '恢复自主控制'
        },
        {
          value: 1,
          label: '黄闪'
        },
        {
          value: 2,
          label: '全红'
        },
        {
          value: 3,
          label: '关灯'
        },
        {
          value: 4,
          label: '步进'
        },
        {
          value: 5,
          label: '定周期控制'
        },
        {
          value: 6,
          label: '单点感应控制'
        },
        {
          value: 7,
          label: '协调感应控制(未支持)'
        },
        {
          value: 8,
          label: '方案选择控制(未支持)'
        },
        {
          value: 9,
          label: '自适应控制(未支持)'
        },
        {
          value: 10,
          label: '无缆线控制'
        },
        {
          value: 11,
          label: '有缆线控制'
        },
        {
          value: 12,
          label: '行人过街控制'
        },
        {
          value: 13,
          label: '通道锁定'
        },
        {
          value: 14,
          label: '相位锁定'
        },
        {
          value: 100,
          label: '方案干预'
        },
        {
          value: 101,
          label: '通道检测'
        }
      ]
    }
  },
  created () {
    if (this.defaultValue) {
      this.value = this.defaultValue
    }
  },
  methods: {
    onLoad () {
    },
    onChange (val) {
      this.$emit('onChange', val, this.agentid)
    },
    init (val) {
      let target = val
      if (this.multiple) {
        target = [val]
      }
      this.value = target
    },
    getNameById (id) {
      let res = ''
      for (const item of this.options) {
        if (id === item.value) {
          res = item.label
          break
        }
      }
      return res
    }
  }
}
</script>
