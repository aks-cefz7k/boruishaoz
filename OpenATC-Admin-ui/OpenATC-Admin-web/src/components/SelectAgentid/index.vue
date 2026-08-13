<template>
  <el-select
    v-model="value"
    :multiple="multiple"
    :disabled="disabled"
    collapse-tags
    clearable
    filterable
    :placeholder="$t('openatc.devicemanager.crossid')"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in Options"
      :key="index"
      :label="item.label"
      :value="item.value"
    >
      <span style="float: left">{{ item.label }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
    </el-option>
  </el-select>
</template>
<script>
import { GetAllDevice } from '@/api/device'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'SelectAgentid',
  props: {
    multiple: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      list: [],
      value: [],
      Options: {
      }
    }
  },
  created () {
    this.onLoad()
  },
  methods: {
    getAllAdevice () {
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        }
        this.list = res.data.data
        this.setOption()
      })
    },
    setOption () {
      this.Options = this.list.map(item => {
        return {
          label: item.name,
          value: item.agentid
        }
      })
    },
    onLoad () {
      this.getAllAdevice()
    },
    onChange (val) {
      this.$emit('onChange', val)
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
      for (const item of this.Options) {
        if (id === item.agentid) {
          res = item.name
          break
        }
      }
      return res
    }
  }
}
</script>
