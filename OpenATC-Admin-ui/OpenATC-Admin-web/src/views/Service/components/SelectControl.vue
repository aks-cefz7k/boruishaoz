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
    :placeholder="$t('openatc.common.placeholder')"
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
          value: -1,
          label: this.$t('openatc.dutyroute.unknowControl')
        },
        {
          value: 0,
          label: this.$t('openatc.dutyroute.autonomouscontrol')
        },
        {
          value: 1,
          label: this.$t('openatc.dutyroute.yellowflash')
        },
        {
          value: 2,
          label: this.$t('openatc.dutyroute.allred')
        },
        {
          value: 3,
          label: this.$t('openatc.dutyroute.turnofflights')
        },
        {
          value: 4,
          label: this.$t('openatc.dutyroute.stepping')
        },
        {
          value: 5,
          label: this.$t('openatc.dutyroute.fixedcyclecontrol')
        },
        {
          value: 6,
          label: this.$t('openatc.dutyroute.singlePointInductionControl')
        },
        {
          value: 7,
          label: this.$t('openatc.dutyroute.coordinatedInductionControl')
        },
        {
          value: 8,
          label: this.$t('openatc.dutyroute.schemeSelectionControl')
        },
        {
          value: 9,
          label: this.$t('openatc.dutyroute.adaptivecontrol')
        },
        {
          value: 10,
          label: this.$t('openatc.dutyroute.nocablecontrol')
        },
        {
          value: 11,
          label: this.$t('openatc.dutyroute.cablecontrol')
        },
        {
          value: 12,
          label: this.$t('openatc.dutyroute.pedestrianCrossingControl')
        },
        {
          value: 13,
          label: this.$t('openatc.dutyroute.reserving13')
        },
        {
          value: 14,
          label: this.$t('openatc.dutyroute.reserving14')
        },
        {
          value: 15,
          label: this.$t('openatc.dutyroute.channelDetector')
        },
        {
          value: 16,
          label: this.$t('openatc.dutyroute.directionLock')
        },
        {
          value: 17,
          label: this.$t('openatc.dutyroute.reserving17')
        },
        {
          value: 18,
          label: this.$t('openatc.dutyroute.reserving18')
        },
        {
          value: 19,
          label: this.$t('openatc.dutyroute.inductivePedestrianCrossing')
        },
        {
          value: 20,
          label: this.$t('openatc.dutyroute.programIntervention')
        },
        {
          value: 21,
          label: this.$t('openatc.dutyroute.reserving21')
        },
        {
          value: 22,
          label: this.$t('openatc.dutyroute.priorityControl')
        },
        {
          value: 23,
          label: this.$t('openatc.dutyroute.phaseReleaseControl')
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
