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
<div class="volume-part">
<div class="current-volume">
    <div class="color-block"></div>
    <div class="curr-volume">
        <span>{{$t('edge.control.currentvolume')}}</span>
    </div>
    <div class="curr-time"><span>{{$t('edge.control.time')}}: </span><span>{{time}}</span>
    </div>
  </div>
    <div class="current-volumetable">
            <el-table
              :data="tableData"
              size="mini"
              max-height="300"
              stripe
              style="width: 100%">
              <el-table-column
                align="center"
                prop="id"
                label="检测器ID">
              </el-table-column>
              <el-table-column
                align="center"
                prop="smallvehnum"
                label="小型车数量">
              </el-table-column>
              <el-table-column
                align="center"
                prop="middlevehnum"
                label="中型车数量">
              </el-table-column>
              <el-table-column
                align="center"
                prop="largevehnum"
                label="大型车数量">
              </el-table-column>
              <el-table-column
                align="center"
                prop="totalvehtime"
                label="占有时间">
              </el-table-column>
              <el-table-column
                align="center"
                prop="occupyrate"
                label="占有率">
              </el-table-column>
            </el-table>
          </div>
</div>
</template>
<script>
export default {
  name: 'curVolume',
  data () {
    return {
      tableData: [],
      time: ''
    }
  },
  props: {
    volumeData: {
      type: Object
    }
  },
  watch: {
    volumeData: {
      handler: function (val, oldVal) {
        this.refreshData(val)
      },
      // 深度观察监听
      deep: true
    }
  },
  methods: {
    refreshData (data) {
      if (data === null) return
      if (data.time) {
        this.time = data.time
      }
      if (data.detector) {
        this.tableData = JSON.parse(JSON.stringify(data.detector))
      }
    }
  },
  mounted () {
    this.refreshData(this.volumeData)
  }
}
</script>
<style scoped>
.volume-part {
  padding: 33px 30px;
  box-sizing: border-box;
}
.current-volume {
  display: flex; /*所有子元素block或inline都变成行内块元素的样式*/
  align-items: center;
  justify-content: flex-start;
  width: 96%;
  font-family: SourceHanSansCN-Regular;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #333333;
}
.color-block {
  width: 14px;
  height: 14px;
  background-color: #459ffc;
}
.curr-volume {
  margin-left: 5px;
}
.curr-time {
  margin-left: 20px;
}
.current-volumetable {
  width: 96%;
  /* height: 380px; */
  margin-top: 40px;
}
</style>
