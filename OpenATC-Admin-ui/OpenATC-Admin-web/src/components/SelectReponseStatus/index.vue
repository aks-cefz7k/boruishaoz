<template>
  <el-select
    v-model="value"
    :multiple="multiple"
    :disabled="disabled"
    collapse-tags
    clearable
    filterable
    :placeholder="placeholderText"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in Options"
      :key="index"
      :label="item.label"
      :value="item.value"
    >
      <!-- <span style="float: left">{{ item.label }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span> -->
    </el-option>
  </el-select>
</template>
<script>
export default {
  name: 'SelectReponseStatus',
  props: {
    multiple: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholderText: {
      type: String,
      default: ''
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
    getList () {
      let list = [
        {
          value: 'get-request',
          label: this.$t('openatc.record.get-request')
        },
        {
          value: 'set-request',
          label: this.$t('openatc.record.set-request')
        },
        {
          value: 'report',
          label: this.$t('openatc.record.report')
        },
        {
          value: 'get-response',
          label: this.$t('openatc.record.get-response')
        },
        {
          value: 'set-response',
          label: this.$t('openatc.record.set-response')
        },
        {
          value: 'error-response',
          label: this.$t('openatc.record.error-response')
        },
        {
          value: 'error-request',
          label: this.$t('openatc.record.error-request')
        },
        {
          value: 'other',
          label: this.$t('openatc.record.other')
        }
      ]
      return list
    },
    onLoad () {
      this.Options = this.getList()
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
