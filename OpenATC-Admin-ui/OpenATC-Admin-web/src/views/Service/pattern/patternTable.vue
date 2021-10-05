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
    <el-table :data="newPatternList" :max-height="tableHeight" id="footerBtn">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <div class="components-container board">
            <Kanban
              v-for="n in scope.row.ringCount"
              :key="n"
              class="kanban todo"
              :list="scope.row.rings[n - 1]"
              :header-text="$t('openatc.dutyroute.ring') + n"
              :index="scope.$index"
              @fatherMethod="fatherMethod"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="No" minWidth="40">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('openatc.greenwaveoptimize.deviceid')"
        minWidth="40"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.intersectionid }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('openatc.greenwaveoptimize.pattern')"
        prop="desc"
      >
        <!-- <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"></el-input>
        </template> -->
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('openatc.greenwaveoptimize.offset')"
        prop="offset"
      >
        <template slot-scope="scope">
          <el-input size="small" v-model.number="scope.row.offset"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('openatc.greenwaveoptimize.cycle')"
        prop="cycle"
      >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Kanban from '@/components/Kanban'
export default {
  name: 'patterntable',
  components: {
    Kanban
  },
  data () {
    return {
      tableHeight: 760,
      screenHeight: window.innerHeight, // 屏幕高度
      newPatternList: []
    }
  },
  created () {
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      // this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      // 后面的50：根据需求空出的高度，自行调整
      _this.tableHeight =
        window.innerHeight -
        document.querySelector('#footerBtn').offsetTop -
        50
      window.onresize = function () {
        // 定义窗口大小变更通知事件
        _this.screenHeight = window.innerHeight // 窗口高度
      }
    })
  },
  watch: {
    screenHeight: function () {
      // 监听屏幕高度变化
      this.tableHeight =
        window.innerHeight -
        document.querySelector('#footerBtn').offsetTop -
        50
    }
  },
  methods: {
    onPatternTable (routeData, patternList, phaseList) {
      if (patternList.length === 0) {
        // this.$message.error('方案为空！')
        return
      }
      if (phaseList.length === 0) {
        // this.$message.error('相位为空！')
        return
      }
      this.handlePatternList(routeData, patternList, phaseList)
    },
    handlePatternList (routeData, patternList, phaseList) {
      this.newPatternList = []
      let intersections = routeData.intersections
      for (let pattern of patternList) {
        let obj = {}
        let id = pattern.intersectionid
        obj.intersectionid = id
        let count = [] // 计算相位有几个环
        if (pattern.feature !== undefined && pattern.feature.patternList.length !== 0) {
          let currPatternList = pattern.feature.patternList
          let patternId = intersections.filter(ist => ist.intersectionid === id)[0].patternid
          let currPattern
          if (Array.isArray(currPatternList)) {
            currPattern = currPatternList.filter(cpl => cpl.id === patternId)[0]
          } else {
            currPattern = currPatternList
          }
          // let currPattern = currPatternList.filter(cpl => cpl.id === patternId)[0]
          if (currPattern.desc === '') {
            obj.desc = this.$t('openatc.greenwaveoptimize.pattern') + currPattern.id
          } else {
            obj.desc = currPattern.desc
          }
          obj.id = currPattern.id
          obj.offset = currPattern.offset
          obj.cycle = currPattern.cycle
          obj.patternId = currPattern.id
          let rings = currPattern.rings
          let currPhase = phaseList.filter(phl => phl.intersectionid === id)[0].feature.phaseList
          // 计算有几个环
          for (let ring of rings) {
            if (ring.length === 0) continue
            for (let rr of ring) {
              rr.desc = []
              let direction = currPhase.filter(curp => curp.id === rr.id)[0].direction // 相位方向集合
              let phaseRing = currPhase.filter(curp => curp.id === rr.id)[0].ring
              count.push(phaseRing)
              for (let dir of direction) {
                let obj = {}
                obj.id = dir
                rr.desc.push(obj)
              }
            }
          }
          obj.rings = rings
        }
        let ringCount = Array.from(new Set(count)) // 去除数组重复的元素
        ringCount = ringCount.sort(this.sortNumbers) // 把数组中的值按照从小到大的顺序重新排序
        obj.ringCount = ringCount
        this.newPatternList.push(obj)
      }
    },
    sortNumbers (a, b) {
      return a - b
    },
    fatherMethod (val) {
      let index = val.index
      let cycle = val.cycle
      this.newPatternList[index].cycle = cycle
    }
  }
}
</script>

<style lang="scss">
.board {
  width: 100%;
  margin-left: 10px;
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  align-items: flex-start;
}
.kanban {
  &.todo {
    .board-column-header {
      background: #4a9ff9;
    }
  }
}
</style>
