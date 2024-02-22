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
  <div class="board-column" style="width:40%;margin-left:40px;">
    <div class="board-column-header">
      {{headerText}}
    </div>
    <div class="board-table-header">
      <el-row :gutter="13">
        <el-col :span="2">{{this.$t('edge.overview.phase')}}
        </el-col>
        <el-col :span="4">{{this.$t('edge.overview.phasesplit')}}
        </el-col>
        <el-col :span="5">{{this.$t('edge.detector.mode')}}
        </el-col>
        <el-col :span="5">{{this.$t('edge.pattern.property')}}
        </el-col>
        <el-col :span="4">{{this.$t('edge.pattern.delaystart')}}
        </el-col>
        <el-col :span="4">{{this.$t('edge.pattern.advanceend')}}
        </el-col>
      </el-row>
    </div>
    <draggable
      class="board-column-content"
      :list="list"
      :options="options">
      <div class="board-item" v-for="element in list" :key="element.id">
        <el-row :gutter="13">
          <el-col :span="2">
            <el-tooltip class="item" effect="dark" placement="left">
              <div slot="content">{{element.name}}</div>
              <div class="phase-description">
                <xdrdirselector Width="40px" Height="40px" :showlist="element.desc" :ISActiveMask="ISActiveMask" :MaskColor="MaskColor"></xdrdirselector>
              </div>
            </el-tooltip>
        </el-col>
        <el-col :span="4">
          <el-input-number :controls="false" class="col-content" size="small" :min="0" :max="255" :step="1" v-model.number="element.value" ref="type" :disabled="element.mode === 7"></el-input-number>
        </el-col>
        <el-col :span="5">
          <el-select v-model="element.mode" class="col-content"  size="small" @change="doChange(element)" :placeholder="$t('edge.common.select')">
            <el-option
              v-for="item in modeOption"
              :key="item.value"
              :label="$t('edge.pattern.modeOption' + item.value)"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-select v-model="element.options" class="col-content" size="small" multiple collapse-tags :placeholder="$t('edge.common.select')">
            <el-option
              v-for="item in coordphaseOption"
              :key="item.value"
              :label="$t('edge.pattern.coordphaseOption' + item.value)"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-input-number :controls="false" class="col-content" size="small" :min="0" :max="255" :step="1" v-model.number="element.delaystart" ref="type"></el-input-number>
        </el-col>
        <el-col :span="4">
          <el-input-number :controls="false" class="col-content" size="small" :min="0" :max="255" :step="1" v-model.number="element.advanceend" ref="type"></el-input-number>
        </el-col>
        </el-row>
      </div>
    </draggable>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import xdrdirselector from '@/components/XRDDirSelector'

export default {
  name: 'dragKanban-demo',
  components: {
    draggable,
    xdrdirselector
  },
  data () {
    return {
      modeOption: [{
        value: 1
      }, {
        value: 2
      }, {
        value: 3
      }, {
        value: 4
      }, {
        value: 5
      }, {
        value: 6
      }, {
        value: 7
      }],
      coordphaseOption: [{
        value: 1
      }, {
        value: 2
      }, {
        value: 4
      }]
    }
  },
  props: {
    headerText: {
      type: String,
      default: 'Header'
    },
    options: {
      type: Object,
      default () {
        return {}
      }
    },
    list: {
      type: Array,
      default () {
        return []
      }
    },
    index: {
      type: Number
    },
    ISActiveMask: {
      type: Boolean,
      default: true
    },
    // 当phase的描述为空时，显示的图形颜色。
    MaskColor: {
      type: String,
      default: '#000000'
    }
  },
  created () {
    this.addMinSplit()
  },
  watch: {
    list: {
      handler: function () {
        let list = this.$refs.type
        let cycle = 0
        let n = this.index
        for (let i = 0; i < list.length; i++) {
          cycle = cycle + Number(list[i].currentValue)
        }
        const globalParamModel = this.$store.getters.globalParamModel
        // let MaxCycle = globalParamModel.getParamsByType('patternList')[n].cycle
        let pattern = globalParamModel.getParamsByType('patternList')[n]
        globalParamModel.getParamsByType('patternList')[n].cycle = this.getMaxCycle(pattern)
        this.addMinSplit()
        this.$emit('handleSplit', n)
      },
      deep: true
    }
  },
  methods: {
    addMinSplit () {
      const globalParamModel = this.$store.getters.globalParamModel
      let phaseList = globalParamModel.getParamsByType('phaseList')
      for (let ls of this.list) {
        let phase = phaseList.filter((item) => {
          return item.id === ls.id
        })[0]
        ls.minSplit = ls.minSplit ? ls.minSplit : 0 // 设置默认值
        let temp1 = phase.redyellow + phase.yellow + phase.redclear + phase.flashgreen // 绿信比的最小值要大于最小绿+黄灯+全红+绿闪
        let temp2 = phase.phasewalk + phase.pedclear
        if (temp1 && temp2) {
          if (temp1 > temp2) {
            ls.minSplit = temp1
          } else {
            ls.minSplit = temp2
          }
        }
        if (ls.mode !== 7 && ls.value < ls.minSplit) {
          ls.value = ls.minSplit
        }
      }
    },
    getMaxCycle (pattern) {
      let rings = pattern.rings
      let maxCycle = 0
      for (let ring of rings) {
        if (ring.length === 0) continue
        let cycle = 0
        for (let r of ring) {
          cycle = cycle + r.value
        }
        if (cycle > maxCycle) {
          maxCycle = cycle
        }
      }
      return maxCycle
    },
    doChange (val) {
      // if (val.mode === 7) {
      //   val.value = 0
      // } else {
      //   val.value = 30
      // }
    }
  }
}
</script>
<style lang="scss" scoped>
.col-content {
  width: 100%;
}
</style>
