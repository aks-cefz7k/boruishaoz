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
      <div class="board-item" v-for="(element,index) in list" :key="element.id">
        <el-row :gutter="13">
          <el-col :span="12">
            <el-tooltip class="item" effect="dark" placement="left">
              <div slot="content">{{element.name}}</div>
              <div class="phase-description">
                <xdrdirselector Width="40px" Height="40px" :showlist="element.desc" :ISActiveMask="ISActiveMask" :MaskColor="MaskColor"></xdrdirselector>
              </div>
            </el-tooltip>
          </el-col>
          <el-col :span="12">
            <el-input-number
              controls-position="right"
              size="small"
              :max="1"
              :min="0"
              v-model.number="subFactorList[index]"
              ref="type"
              @change="onchange">
            </el-input-number>
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
      subFactorList: []
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
    factorList: {
      type: Array,
      default () {
        return []
      }
    },
    rowIndex: {
      type: Number
    },
    subIndex: {
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
  mounted () {
    let temp = []
    let list = this.list
    for (let i = 0; i < list.length; i++) {
      temp.push(0)
    }
    if (this.factorList && this.factorList.length > 0) {
      this.subFactorList = [...this.factorList]
    } else {
      this.subFactorList = temp
    }
  },
  methods: {
    onchange (newVal, oldVal) {
      let sum = 0
      for (let i = 0; i < this.subFactorList.length; i++) {
        let num = this.subFactorList[i]
        sum = sum + num
        if (sum > 1) {
          this.$message.warning(this.$t('edge.singleoptim.gratterThanOne'))
        } else if (sum === 1) {
          this.$emit('setSubList', this.rowIndex, this.subIndex, this.subFactorList)
        }
      }
    }
  }
}
</script>
<style lang="scss">
</style>
