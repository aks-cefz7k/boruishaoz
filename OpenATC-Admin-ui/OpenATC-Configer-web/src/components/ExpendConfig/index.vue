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
  }

}
</script>

<style lang="scss" scoped>

</style>
