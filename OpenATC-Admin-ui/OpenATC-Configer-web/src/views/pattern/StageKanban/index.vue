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
    <div class="board-column-header">
      {{headerText}}
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
            <el-input
              class="stage-value"
              size="small"
              :value="stage.stages.join(',')"
              ref="type"
              style="width:100%;align:left;"
              :disabled="true">
            </el-input>
          </el-col>
        </el-row>
        <el-row :gutter="0">
          <el-col :span="12">
            {{this.$t('edge.pattern.delaystart')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              class="stage-value"
              :controls="false"
              size="small"
              v-model.number="stage.delaystart"
              ref="type"
              :disabled="true"
              @change="onDelaystartChange">
            </el-input-number>
          </el-col>
        </el-row>
        <el-row :gutter="0">
          <el-col :span="12">
            {{this.$t('edge.pattern.advanceend')}}
          </el-col>
          <el-col :span="12">
            <el-input-number
              class="stage-value"
              :controls="false"
              size="small"
              v-model.number="stage.advanceend"
              ref="type"
              :disabled="true"
              @change="onAdvanceendChange">
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
    stage: {
      type: Object,
      default () {
        return {
          split: 0,
          stages: [],
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
  mounted () {
  },
  methods: {
    onSplitChange (newVal, oldVal) {
      let diff = newVal - (oldVal || 0)
      if (diff === 0) {
        return false
      }
      this.$emit('onStageSplitChange', diff, this.rowIndex, this.subIndex)
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
