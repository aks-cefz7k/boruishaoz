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
  <div class="board-column">
    <div class="board-column-header">
      {{headerText}}
    </div>
    <draggable
      class="board-column-content"
      :list="list"
      :options="options">
      <div class="board-item" v-for="element in list" :key="element.id">
        <el-row :gutter="25">
          <el-col :span="4">
        <el-tooltip class="item" effect="dark" placement="left">
          <div slot="content">{{element.name}}</div>
          <div class="phase-description">
            <xdrdirselector Width="40px" Height="40px" :showlist="element.desc" :ISActiveMask="ISActiveMask" :MaskColor="MaskColor"></xdrdirselector>
          </div>
        </el-tooltip>
        </el-col>
        <el-col :span="19">
        <el-input-number controls-position="right" :min="0" :max="255" :step="1" v-model.number="element.value" ref="type"></el-input-number>
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
        let obj = {}
        obj.index = n
        obj.cycle = cycle
        this.$emit('fatherMethod', obj)
      },
      deep: true
    }
  }
}
</script>
<style lang="scss">
</style>
