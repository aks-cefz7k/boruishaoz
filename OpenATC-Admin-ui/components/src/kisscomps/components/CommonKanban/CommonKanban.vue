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
  <div class="common-board-column">
    <div class="common-board-column-header">
      {{headerText}}
    </div>
    <div class="common-board-table-header">
      <div class="common-titlecolumn phase">{{this.$t('openatccomponents.overview.phase')}}</div>
      <div class="common-titlecolumn mode">
        <slot name="kanbantitle"></slot>
      </div>
    </div>
    <!-- 开启拖拽调整顺序 -->
    <draggable v-if="Draggable" class="common-board-column-content" v-model="list" @end="end">
      <div class="common-board-item" v-for="element in list" :key="element.id">
        <div class="common-contentcolumn phase">
            <el-tooltip class="item" effect="dark" placement="left">
              <div slot="content">{{element.name}}</div>
              <div class="common-phase-description">
                <xdrdirselector Width="40px" Height="40px" :showlist="element.desc" :ISActiveMask="ISActiveMask" :MaskColor="MaskColor"></xdrdirselector>
              </div>
            </el-tooltip>
        </div>
        <div class="common-contentcolumn mode">
          <slot name="kanbancontent" :element="element"></slot>
        </div>
      </div>
    </draggable>
    <!-- 关闭拖拽调整顺序 -->
    <div v-if="!Draggable" class="common-board-column-content">
      <div class="common-board-item" v-for="element in list" :key="element.id">
        <div class="common-contentcolumn phase">
            <el-tooltip class="item" effect="dark" placement="left">
              <div slot="content">{{element.name}}</div>
              <div class="common-phase-description">
                <xdrdirselector Width="40px" Height="40px" :showlist="element.desc" :ISActiveMask="ISActiveMask" :MaskColor="MaskColor"></xdrdirselector>
              </div>
            </el-tooltip>
        </div>
        <div class="common-contentcolumn mode">
          <slot name="kanbancontent" :element="element"></slot>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import xdrdirselector from '../XRDDirSelector/XRDDirSelector'
import draggable from 'vuedraggable'

export default {
  name: 'common-kanban',
  components: {
    draggable,
    xdrdirselector
  },
  props: {
    headerText: {
      type: String
    },
    list: {
      type: Array,
      default () {
        return []
      }
    },
    ISActiveMask: {
      type: Boolean,
      default: true
    },
    // 当phase的描述为空时，显示的图形颜色。
    MaskColor: {
      type: String,
      default: '#000000'
    },
    Draggable: { // 是否开启拖动调整顺序
      type: Boolean,
      default: true
    }
  },
  methods: {
    end () {
      // 拖动结束后，向父组件发送重新排序后的数据
      this.$emit('handleSort', this.list)
    }
  }
}
</script>
