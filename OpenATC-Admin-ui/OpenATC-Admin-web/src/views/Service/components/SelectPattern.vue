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
    :loading="loading"
    collapse-tags
    clearable
    filterable
    :placeholder="$t('openatc.common.placeholder')"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in options"
      :key="index"
      :label="item.patterndesc"
      :value="item.patternid"
    >
    </el-option>
  </el-select>
</template>
<script>
import { getTscControl } from '@/api/route'
export default {
  name: 'SelectPattern',
  props: {
    agentid: {
      type: String,
      required: true
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
      options: []
    }
  },
  created () {
    this.onLoad()
  },
  methods: {
    onLoad () {
      this.getCurPattern(this.agentid)
    },
    getCurPattern (agentid) {
      // 获取当前设备所有可选方案
      this.loading = true
      getTscControl(agentid).then(res => {
        this.loading = false
        if (!res.data.success) {
          if (res.data.code === '4003') {
            this.$message.error(this.$t('openatc.common.devicenotonline'))
            return
          }
          this.$message.error(res.data.message)
          return
        }
        let list = res.data.data.data.patternList
        for (let item of list) {
          let res = {
            agentid: this.agentid,
            pattern: item,
            patternList: list,
            patternid: item.id,
            patterndes: item.desc,
            patterndesc: item.desc === '' ? `${this.$t('openatc.greenwaveoptimize.pattern')}${item.id}` : item.desc
          }
          this.options.push(res)
        }
        this.setData()
      })
    },
    setData () {
      for (let item of this.options) {
        if (this.defaultValue === item.patternid) {
          let res = {
            agentid: this.agentid,
            pattern: item.pattern,
            patternList: item.patternList,
            patternid: item.patternid,
            patterndes: item.patterndes,
            patterndesc: item.patterndesc
          }
          this.$emit('onChange', res)
          this.value = item.patternid
          break
        }
      }
    },
    onChange (val) {
      let res
      for (let item of this.options) {
        let patternid = item.patternid
        if (val === patternid) {
          res = {
            pattern: item.pattern,
            patternList: item.pattern,
            agentid: this.agentid,
            patternid: patternid,
            patterndes: item.patterndes,
            allPatterns: this.options
          }
          break
        }
      }
      this.$emit('onChange', res)
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
        if (id === item.label) {
          res = item.value
          break
        }
      }
      return res
    }
  }
}
</script>
