<template>
<div class="expendConfig" style="margin-left:40px;">
  <div class="board-column">
    <div class="board-column-header">
      {{headerText}}
    </div>
    <div class="board-table-header">
      <el-row :gutter="13">
        <el-col :span="8">{{this.$t('edge.pattern.property')}}
        </el-col>
        <el-col :span="8">{{this.$t('edge.pattern.delaystart')}}
        </el-col>
        <el-col :span="8">{{this.$t('edge.pattern.advanceend')}}
        </el-col>
      </el-row>
    </div>
     <draggable
      class="board-column-content"
      :list="list"
      :options="options">
      <div class="board-item" v-for="element in list" :key="element.id">
        <el-row>
          <el-col :span="8">
            <el-select v-model="element.options" class="col-content" size="small" multiple collapse-tags :placeholder="$t('edge.common.select')">
              <el-option
                v-for="item in coordphaseOption"
                :key="item.value"
                :label="$t('edge.pattern.coordphaseOption' + item.value)"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="8">
            <el-input-number :controls="false" class="col-content" size="small" :min="0" :max="255" :step="1" v-model.number="element.delaystart" ref="type"></el-input-number>
          </el-col>
          <el-col :span="8">
            <el-input-number :controls="false" class="col-content" size="small" :min="0" :max="255" :step="1" v-model.number="element.advanceend" ref="type"></el-input-number>
          </el-col>
        </el-row>
      </div>
    </draggable>
  </div>
</div>
</template>

<script>
import draggable from 'vuedraggable'
export default {
  components: {
    draggable
  },
  data () {
    return {
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
    list: {
      type: Array,
      default () {
        return []
      }
    },
    index: {
      type: Number
    },
    options: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  created () {
    // this.addMinSplit()
  },
  watch: {
    // list: {
    //   handler: function () {
    //     let list = this.$refs.type
    //     let cycle = 0
    //     let j = this.index
    //     for (let i = 0; i < list.length; i++) {
    //       cycle = cycle + Number(list[i].currentValue)
    //     }
    //     const globalParamModel = this.$store.getters.globalParamModel
    //     // let MaxCycle = globalParamModel.getParamsByType('patternList')[n].cycle
    //     let pattern = globalParamModel.getParamsByType('patternList')[j]
    //     globalParamModel.getParamsByType('patternList')[j].cycle = this.getMaxCycle(pattern)
    //     this.addMinSplit()
    //     this.$emit('handleSplit', j)
    //   },
    //   deep: true
    // }
  },
  methods: {
    // addMinSplit () {
    //   const globalParamModel = this.$store.getters.globalParamModel
    //   let phaseList = globalParamModel.getParamsByType('phaseList')
    //   for (let ls of this.list) {
    //     let phase = phaseList.filter((item) => {
    //       return item.id === ls.id
    //     })[0]
    //     if (!phase.redyellow) {
    //       phase.redyellow = 0
    //     }
    //     if (!phase.yellow) {
    //       phase.yellow = 0
    //     }
    //     if (!phase.redclear) {
    //       phase.redclear = 0
    //     }
    //     if (!phase.flashgreen) {
    //       phase.flashgreen = 0
    //     }
    //     if (!phase.phasewalk) {
    //       phase.phasewalk = 0
    //     }
    //     if (!phase.pedclear) {
    //       phase.pedclear = 0
    //     }
    //     let temp1 = phase.redyellow + phase.yellow + phase.redclear + phase.flashgreen // 绿信比的最小值要大于最小绿+黄灯+全红+绿闪
    //     let temp2 = phase.phasewalk + phase.pedclear
    //     if (temp1 > temp2) {
    //       ls.minSplit = temp1
    //     } else {
    //       ls.minSplit = temp2
    //     }
    //     if (ls.mode !== 7 && ls.value < ls.minSplit) {
    //       ls.value = ls.minSplit
    //     }
    //   }
    // },
    // getMaxCycle (pattern) {
    //   let rings = pattern.rings
    //   let maxCycle = 0
    //   for (let ring of rings) {
    //     if (ring.length === 0) continue
    //     let cycle = 0
    //     for (let r of ring) {
    //       cycle = cycle + r.value
    //     }
    //     if (cycle > maxCycle) {
    //       maxCycle = cycle
    //     }
    //   }
    //   return maxCycle
    // }
  }

}
</script>

<style lang="scss" scoped>

</style>
