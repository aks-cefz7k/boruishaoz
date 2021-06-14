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
  <div class="app-container">
    <tree-table :data="data" :evalFunc="func" :evalArgs="args" :expandAll="expandAll" border>
      <el-table-column label="Procedure" width="120">
        <template slot-scope="scope">
          <el-tag>{{scope.row.timeLine+'s'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="TimeLine">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.timeLine+'s'" placement="left">
            <div class="processContainer">
              <div class="process" :style="{ width:scope.row._width * 500+'px',
              background:scope.row._width>0.5?'rgba(64,158,255,1)':'rgba(64,158,255,0.7)',
              marginLeft:scope.row._marginLeft * 500+'px' }">
                <span style="display:inline-block"></span>
              </div>
            </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="Operate" width="100">
        <template slot-scope="scope">
          <el-button type="text" @click="message(scope.row)">Edit</el-button>
        </template>
      </el-table-column>
    </tree-table>
  </div>
</template>

<script>
/**
  Auth: Lei.j1ang
  Created: 2018/1/19-14:54
*/
import treeTable from '@/components/TreeTable'
import treeToArray from './customEval'

export default {
  name: 'customTreeTableDemo',
  components: { treeTable },
  data () {
    return {
      func: treeToArray,
      expandAll: false,
      data:
        {
          id: 1,
          event: 'Ring 1',
          timeLine: 100,
          comment: '无',
          children: [
            {
              id: 2,
              event: 'Phase 1',
              timeLine: 30,
              comment: '无'
            },
            {
              id: 3,
              event: 'Phase 2',
              timeLine: 20,
              comment: '无'
            },
            {
              id: 4,
              event: 'Phase 3',
              timeLine: 30,
              comment: '无'
            },
            {
              id: 5,
              event: 'Phase 4',
              timeLine: 20,
              comment: '无'
            }
          ]
        },
      args: [null, null, 'timeLine']
    }
  },
  methods: {
    message (row) {
      this.$message.info(row.event)
    }
  }
}
</script>
