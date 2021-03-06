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
<div class="components-container board">
  <div class="board-column">
    <div class="board-column-header" style="position:relative">
      <span>{{$t('edge.pattern.stage')+(subIndex+1)}}</span>
      <span v-if="contrloType" style="position: absolute;right: 0;">
        <el-button type="primary" @click="deleteStage(subIndex)" icon="el-icon-close"></el-button>
      </span>
    </div>
    <div
      class="board-column-content"
      :options="options">
      <div class="stage-item" >
        <el-row :gutter="0">
          <el-col :span="12">
            {{this.$t('edge.overview.phasesplit')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              v-if="contrloType"
              class="stage-value"
              :controls="false"
              size="small"
              :disabled="true"
              ref="type"
              v-model.number="stage.stageSplit">
            </el-input-number>
            <el-input-number
              v-if="!contrloType"
              class="stage-value"
              :controls="false"
              size="small"
              v-model.number="stage.split"
              ref="type"
              :disabled="true"
              @change="onSplitChange">
            </el-input-number>
          </el-col>
        </el-row>
        <el-row :gutter="0">
          <el-col :span="12">
            {{this.$t('edge.pattern.phase')}}
          </el-col>
          <el-col :span="12">
            <el-select v-if="contrloType" v-model="stage.phases" multiple :placeholder="$t('edge.common.select')">
              <el-option
                v-for="item in coordphaseOption"
                :key="item.value"
                :label="$t('edge.pattern.phase') + item.value"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input
              v-if="!contrloType"
              class="stage-value"
              size="small"
              :value="stage.stages.join(',')"
              ref="type"
              style="width:100%;align:left;"
              :disabled="true">
            </el-input>
          </el-col>
        </el-row>
        <el-row :gutter="0" v-if="contrloType">
          <el-col :span="12">
            {{this.$t('edge.pattern.green')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              class="stage-value"
              size="small"
              :controls="false"
              v-model.number="stage.green"
              @change="stageSplitChange">
            </el-input-number>
          </el-col>
        </el-row>
        <el-row :gutter="0" v-if="contrloType">
          <el-col :span="12">
            {{this.$t('edge.pattern.yellow')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              class="stage-value"
              size="small"
              :controls="false"
              v-model.number="stage.yellow"
              @change="stageSplitChange">
            </el-input-number>
          </el-col>
        </el-row>
        <el-row :gutter="0" v-if="contrloType">
          <el-col :span="12">
            {{this.$t('edge.pattern.red')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              class="stage-value"
              size="small"
              :controls="false"
              v-model.number="stage.red"
              @change="stageSplitChange">
            </el-input-number>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</div>
</template>
<script>
import draggable from 'vuedraggable'

export default {
  name: 'StageKanban',
  components: {
    draggable
  },
  data () {
    return {
    }
  },
  props: {
    // headerText: {
    //   type: String,
    //   default: 'Header'
    // },
    stageInfo: {
      type: Array
    },
    coordphaseOption: {
      type: Array,
      default () {
        return []
      }
    },
    contrloType: {
      type: Boolean,
      default: false
    },
    options: {
      type: Object,
      default () {
        return {}
      }
    },
    stage: {
      type: Object,
      default () {
        return {
          split: 0,
          stages: [],
          phases: [],
          stageSplit: 0,
          green: 0,
          yellow: 0,
          red: 0,
          delaystart: 0,
          advanceend: 0
        }
      }
    },
    rowIndex: {
      type: Number
    },
    subIndex: {
      type: Number
    }
  },
  created () {
  },
  watch: {
    stage: {
      handler: function () {
        if (this.contrloType) {
          let n = this.rowIndex
          const globalParamModel = this.$store.getters.globalParamModel
          let pattern = globalParamModel.getParamsByType('patternList')[n]
          globalParamModel.getParamsByType('patternList')[n].cycle = this.getMaxCycle(pattern)
        }
      },
      deep: true
    }
  },
  mounted () {
  },
  methods: {
    deleteStage (index) {
      this.stageInfo.splice(index, 1)
    },
    getMaxCycle (pattern) {
      let rings = pattern.stagesList
      let stageCycleList = rings.map(item => {
        return item.stageSplit
      })
      let maxCycle = stageCycleList.reduce((a, b) => {
        return a + b
      }, 0)
      return maxCycle
    },
    onSplitChange (newVal, oldVal) {
      let diff = newVal - (oldVal || 0)
      if (diff === 0) {
        return false
      }
      this.$emit('onStageSplitChange', diff, this.rowIndex, this.subIndex)
    },
    stageSplitChange (newVal, oldVal) {
      let diff = newVal - (oldVal || 0)
      if (diff === 0) {
        return false
      }
      this.$emit('stageSplitChange', diff, this.rowIndex, this.subIndex)
    },
    onDelaystartChange (newVal, oldVal) {
      let diff = newVal - (oldVal || 0)
      if (diff === 0) {
        return false
      }
      this.$emit('onStageDelaystartChange', diff, this.rowIndex, this.subIndex)
    },
    onAdvanceendChange (newVal, oldVal) {
      let diff = newVal - (oldVal || 0)
      if (diff === 0) {
        return false
      }
      this.$emit('onStageAdvanceendChange', diff, this.rowIndex, this.subIndex)
    }
  }
}
</script>
<style lang="scss" scoped>
  .stage-item {
    cursor: pointer;
    width: 100%;
    height: auto;
    margin: 5px 0;
    text-align: left;
    line-height: 40px;
    padding: 1px 1px;
    box-sizing: border-box;
  }
  .el-button--primary:hover, .el-button--primary:focus{
    background: #409EFF;
    border-color: #409EFF;
    color: #FFFFFF;
  }
  .stage-value {
    text-align: left;
    width: 100%;
  }
</style>
<style lang="scss">
.stage-value .el-input__inner {
    text-align: center;
  }
</style>
