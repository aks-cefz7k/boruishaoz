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
      <!-- <span style="float: left">{{ item.value }}</span> -->
    </el-option>
  </el-select>
</template>
<script>
export default {
  name: 'SelectInfoType',
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
      infotypeMode: new Map([['control/pattern', '控制消息'], ['control/interrupt', '方案控制消息'], ['control/preempt', '优先控制'], ['system/channelcheck', '通道检测'], ['control/remote', '远程控制'], ['system/centerip', '中心ip地址'], ['system/ip1', '设备ip地址1'], ['system/ip2', '设备ip地址2'], ['system/serialport', '串口信息'], ['system/time', '系统时间'], ['system/remote', '远程调试'], ['system/update', '系统更新'], ['system/paramversion', '参数版本'], ['system/optstatparam', '优化统计参数'], ['system/faultclear', '故障消除'], ['system/udiskupdate', '更新U盘数据'], ['system/volumelog', '交通流量历史数据'], ['feature/channel', '通道'], ['feature/phase', '相位'], ['feature/overlap', '跟随相位'], ['feature/pattern', '方案'], ['feature/plan', '计划'], ['feature/date', '日期'], ['feature/peddetecter', '行人检测器'], ['feature/devinfo', '设备信息'], ['feature/manualpanel', '手动面板配置'], ['feature/channellock', '通道灯色锁定参数配置'], ['feature/all', '整体参数']]),
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
      let list = []
      let map = this.infotypeMode
      let _this = this
      map.forEach(function (value, key) {
        let item = {
          label: value,
          value: key
        }
        if (_this.$i18n.locale === 'en') {
          item = {
            label: key,
            value: key
          }
        }
        list.push(item)
      })
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
