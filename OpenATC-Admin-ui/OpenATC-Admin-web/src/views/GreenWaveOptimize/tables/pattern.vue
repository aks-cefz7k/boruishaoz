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
  <div class="patternPanel">
    <el-table
      highlight-current-row
      ref="patternTable"
      class="patternTable"
      :data="patternTableData"
      style="width: 100%">
      <el-table-column
        type="index"
        label="#"
        align="center">
      </el-table-column>
      <el-table-column
        prop="agentid"
        :label="$t('openatc.greenwaveoptimize.deviceid')"
        align="center">
      </el-table-column>
      <el-table-column
        :label="$t('openatc.greenwaveoptimize.pattern')"
        align="center">
        <template slot-scope="scope">
          <el-select
           v-model="scope.row.patterndesc"
           size="small"
           :loading="loading"
           @focus="getCurPattern(scope.row.agentid)"
           @change="changeCurPattern(scope.row,$event)">
              <el-option v-for="option in (scope.row.allPatterns)" :key="option.patternid" :label="option.patterndesc" :value="option.patternid">
                <span>{{ option.patterndesc }}</span>
              </el-option>
          </el-select>
          <span></span>
        </template>
      </el-table-column>
  </el-table>
</div>
</template>
<script>
import { getPatternList } from '@/api/route'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'patterns',
  props: {
    patternData: {
      type: Array
    }
  },
  watch: {
    patternData: {
      handler: function (val) {
        let pattern = JSON.parse(JSON.stringify(val))
        for (let i = 0; i < pattern.length; i++) {
          if (pattern[i].patternid !== 0) {
            pattern[i].patterndesc = pattern[i].patterndes === '' ? `${this.$t('openatc.greenwaveoptimize.pattern')}${pattern[i].patternid}` : pattern[i].patterndes
          } else {
            pattern[i].patterndesc = ''
          }
        }
        this.patternTableData = pattern
      },
      deep: true
    }
  },
  data () {
    return {
      patternTableData: [], // 方案数据
      loading: false
    }
  },
  methods: {
    changeCurPattern (row, curPatternid) {
      // 修改当前方案（allPatterns： 所有可选项）
      const data = {
        agentid: row.agentid,
        patternid: curPatternid,
        patterndes: row.allPatterns.filter(ele => ele.patternid === curPatternid)[0].patterndes,
        allPatterns: row.allPatterns
      }
      this.$emit('changeData', data)
    },
    getCurPattern (agentid) {
      // 获取当前设备所有可选方案
      this.loading = true
      getPatternList(agentid).then(res => {
        this.loading = false
        if (!res.data.success) {
          if (res.data.code === '4003') {
            this.$message.error(this.$t('openatc.common.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.patternData.forEach(ele => {
          if (ele.agentid === agentid) {
            ele.allPatterns = res.data.data.data.patternList.map(ele => {
              return {
                patternid: ele.id,
                patterndes: ele.desc,
                patterndesc: ele.desc === '' ? `${this.$t('openatc.greenwaveoptimize.pattern')}${ele.id}` : ele.desc
              }
            })
          }
        })
      })
    }
  }
}
</script>

<style>
.patternPanel .el-table td, .el-table th {
    padding: 7px 0;
}
.patternPanel .el-table .cell {
    line-height: 32px;
}
</style>

<style lang="scss" scoped>
// .patternPanel {
//   border: solid 1px $--border-color-lighter;
// }
</style>
