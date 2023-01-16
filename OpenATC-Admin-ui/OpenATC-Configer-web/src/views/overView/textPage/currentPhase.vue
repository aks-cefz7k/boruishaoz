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
        <!-- 实时相位表格 -->
          <div class="phase-part">
            <div class="current-phase">
              <div class="color-block"></div>
              <div class="curr-phase">
                <span>{{$t('edge.control.current_phase')}}</span>
                <span v-for="(currPhase, index) in controlData.current_phase" :key="index">
                  <span v-if="index === 0">{{currPhase}}</span>
                  <span v-else>{{', ' + currPhase}}</span>
                </span>
              </div>
              <div class="curr-phase" v-for="(ring, index) in controlData.rings" :key="index">
                <span class="curr-ring"><span>{{$t('edge.control.ring')}}: </span><span>{{ring.num}}</span></span>
                <span class="phase-order"><span>{{$t('edge.control.sequence')}} </span><span>{{ring.sequence}}</span></span>
              </div>
            </div>
            <div class="current-phasetable">
              <el-table
                :data="tableData"
                :span-method="objectSpanMethod"
                :cell-class-name="addClass"
                size="mini"
                style="width: 100%">
                <el-table-column
                  align="center"
                  prop="ring"
                  :label="$t('edge.overview.ring')">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="id"
                  :label="$t('edge.overview.phaseid')">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="countdown"
                  :label="$t('edge.overview.phase_countdown')">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="split"
                  :label="$t('edge.overview.split')">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="type"
                  :label="$t('edge.overview.type')">
                </el-table-column>
              </el-table>
            </div>
          </div>
</template>

<script>
export default {
  name: 'curphase',
  components: {
  },
  data () {
    return {
      spanArr: []
    }
  },
  props: {
    controlData: {
      type: Object
    },
    tableData: {
      type: Array
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    addClass ({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 2) {
        if (row.type === '绿') {
          return 'green'
        }
        if (row.type === '红') {
          return 'red'
        }
        if (row.type === '黄') {
          return 'yellow'
        }
      }
    },
    handleObjectSpan () {
      let contactDot = 0
      this.spanArr = []
      this.tableData.forEach((item, index) => {
        item.index = index
        if (index === 0) {
          this.spanArr.push(1)
        } else {
          if (item.ring === this.tableData[index - 1].ring) {
            this.spanArr[contactDot] += 1
            this.spanArr.push(0)
          } else {
            this.spanArr.push(1)
            contactDot = index
          }
        }
      })
    },
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        const _row = this.spanArr[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
      // if (columnIndex === 1) {
      //   const _row = this.spanArr[rowIndex]
      //   const _col = _row > 0 ? 1 : 0
      //   return {
      //     rowspan: _row,
      //     colspan: _col
      //   }
      // }
    }
  },
  destroyed () {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// .phase-part {
//   padding: 33px 30px;
//   box-sizing: border-box;
// }
// .current-phase {
//   display: flex; /*所有子元素block或inline都变成行内块元素的样式*/
//   align-items: center;
//   justify-content: flex-start;
//   width: 96%;
//   font-family: SourceHanSansCN-Regular;
//   font-size: 16px;
//   font-weight: normal;
//   font-stretch: normal;
//   letter-spacing: 0px;
//   color: #333333;
// }
// .current-phasetable {
//   width: 96%;
//   height: 380px;
//   margin-top: 40px;
// }
// .color-block {
//   float: left;
//   width: 14px;
//   height: 14px;
//   background-color: #459ffc;
// }
// .curr-phase {
//   margin-left: 5px;
// }
// .curr-ring {
//   margin-left: 20px;
// }
// .phase-order {
//   margin-left: 20px;
// }
</style>
<style rel="stylesheet/scss" lang="scss">
.current-phasetable .yellow {
  color: #ffd000;
}
.current-phasetable .red {
  color: #ff1b1b;
}
.current-phasetable .green {
  color: #20b84e;
}
</style>
