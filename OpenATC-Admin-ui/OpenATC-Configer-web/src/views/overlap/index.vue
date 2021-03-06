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
  <div class="app-container" ref="overlap-container">
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-table :data="overlaplList" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row :max-height="tableHeight" id="footerBtn">
      <el-table-column align="center" label='ID' min-width="30">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column class="table-column" :label="$t('edge.phase.desc')" min-width="150" align="center">
        <template slot-scope="scope">
          <Tankuang :list="scope.row.direction" :imgs="imgs" :index="scope.$index" :showBottomName="showBottomName" :lines="lines" :rows="rows" :showSpan="showSpan" :refresh="refreshTankuang" @finsh="handlefinsh"/>
        </template>
      </el-table-column>
      <el-table-column class="table-column" :label="$t('edge.phase.peddesc')" min-width="150" align="center">
        <template slot-scope="scope">
          <PedTankuang :list="scope.row.peddirection" :imgs="pedimgs" :index="scope.$index" :showBottomName="showBottomName" :lines="lines" :rows="rows" :showSpan="showSpan" :refresh="refreshTankuang" @finsh="handlefinshped"/>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.overlap.desc')">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"  @change="handleEdit(scope.$index, scope.row)"></el-input>
          <!-- <span>{{scope.row.Description}}</span> -->
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.overlap.includedPhases')">
        <template slot-scope="scope">
            <el-select multiple v-model="scope.row.includedphases" :placeholder="$t('edge.common.select')" size="small">
              <el-option
                v-for="item in includedPhasess"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.pulsetype')">
        <template slot-scope="scope">
            <el-select v-model="scope.row.pulsetype" :placeholder="$t('edge.common.select')" size="small">
              <el-option
                v-for="item in pulseTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('edge.overlap.operation')">
        <template slot-scope="scope">
          <!-- <el-button size="small" @click="handleDelete(scope.$index)">Delete</el-button> -->
          <el-button type="text"  @click="handleDelete(scope.$index)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getPhaseDesc } from '@/utils/phasedesc.js'
import { mapState } from 'vuex'
import { getPhase, pedimages } from '../phase/utils.js'
import Tankuang from '@/components/Tankuang'
import PedTankuang from '@/components/PedTankuang'
export default {
  name: 'overlap',
  components: {
    Tankuang,
    PedTankuang
  },
  data () {
    return {
      tableHeight: 760,
      listLoading: false,
      includedPhasess: [],
      id: 1,
      direction: [],
      peddirection: [], // 行人方向
      showBottomName: false, // 用于控制弹框里是否在底部显示文字描述。
      lines: 4, // 弹框的行数
      rows: 4, // 弹框的列数
      showSpan: false,
      pulseTypeList: [{
        label: this.$t('edge.phase.sendpedestriansvehiclepulse'),
        value: 0
      }, {
        label: this.$t('edge.phase.sendvehiclepulse'),
        value: 1
      }, {
        label: this.$t('edge.phase.sendpedestrianpulse'),
        value: 2
      }, {
        label: this.$t('edge.phase.offpulse'),
        value: 3
      }],
      refreshTankuang: false // 是否重新刷新弹框数据
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  computed: {
    imgs () {
      let arrays = []
      let images = getPhase()
      images.forEach(v => {
        let obj = Object.assign({}, v)
        obj.name = this.$t(obj.name)
        arrays.push(obj)
      })
      return arrays
    },
    pedimgs () {
      let arrays = []
      pedimages.forEach(v => {
        let obj = Object.assign({}, v)
        obj.name = this.$t(obj.name)
        arrays.push(obj)
      })
      return arrays
    },
    ...mapState({
      overlaplList: state => state.globalParam.tscParam.overlaplList,
      isRefreshTankuang: state => state.globalParam.isRefreshTankuang
    })
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.init()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['overlap-container'].offsetHeight - 80
      window.onresize = function () {
        _this.tableHeight = _this.$refs['overlap-container'].offsetHeight - 80
      }
    })
  },
  watch: {
    overlaplList: function () {
      this.init()
    },
    isRefreshTankuang: {
      handler: function (val) {
        if (val === 'overlap') {
          this.refreshTankuang = true
        }
      }
    }
  },
  methods: {
    init () {
      this.initData()
      this.increaseId()
    },
    initData () {
      const phaseList = JSON.parse(JSON.stringify(this.globalParamModel.getParamsByType('phaseList')))
      let overlaplList = this.globalParamModel.getParamsByType('overlaplList')
      this.includedPhasess = []
      for (let i = 0; i < phaseList.length; i++) {
        let overlap = {}
        var patternNum = phaseList[i].id
        var overlapDescription
        if (phaseList[i].desc !== '' && phaseList[i].desc !== undefined) {
          overlapDescription = patternNum + '-' + getPhaseDesc(phaseList[i].desc, this.$i18n.locale)
        } else {
          overlapDescription = patternNum
        }
        overlap.value = patternNum
        overlap.label = overlapDescription
        this.includedPhasess.push(overlap)
      }
      let includedphaseList = this.includedPhasess.map(ele => ele.value)
      for (let over of overlaplList) {
        over.includedphases = over.includedphases.filter(v => includedphaseList.includes(v))// emit
      }
    },
    // increaseId () { // 实现id在之前的基础上加1
    //   let overlaplList = this.globalParamModel.getParamsByType('overlaplList')
    //   let i = overlaplList.length - 1
    //   if (i >= 0) {
    //     this.id = overlaplList[i].id + 1
    //   }
    // },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let overlaplList = this.globalParamModel.getParamsByType('overlaplList')
      let overlapIdList = overlaplList.map(ele => ele.id)
      let i = overlaplList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 16; j++) {
          if (!overlapIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleEdit (index, row) {

    },
    handleDelete (index) {
      this.$confirm(this.$t('edge.overlap.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('overlaplList', index, 1)
        this.$message({
          type: 'success',
          message: this.$t('edge.common.deletesucess')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.common.deletecancel')
        })
      })
    },
    onAdd () {
      this.increaseId()
      if (this.globalParamModel.getParamLength('phaseList') === 0) {
        this.$message.error(
          this.$t('edge.pattern.firstphase')
        )
        return
      }
      if (this.globalParamModel.getParamLength('overlaplList') >= 16) {
        this.$message.error(
          this.$t('edge.overlap.mostdata')
        )
        return
      }
      var overlaplInitData = {
        id: this.id,
        desc: '',
        includedphases: []
      }
      this.globalParamModel.addParamsByType('overlaplList', overlaplInitData)
      // this.id++
      // 重新排序相位数组
      let overlaplList = this.globalParamModel.getParamsByType('overlaplList')
      overlaplList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    handlefinsh (value) {
      let index = value.index
      let status = value.status
      let list = []
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        list.push(this.imgs[i].id)
      }
      this.$store.getters.tscParam.overlaplList[index].direction = list
    },
    handlefinshped (value) {
      let index = value.index
      let status = value.status
      let list = []
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        list.push(this.pedimgs[i].id)
      }
      this.$store.getters.tscParam.overlaplList[index].peddirection = list
    }
    // checkLane (value, index) {
    //   this.$store.getters.tscParam.overlaplList[index].Lane = value.replace(/[^\d\,]/g, '')
    // }
    // checkNo(value) {
    //   value = value.replace(/[^\d\,]/g,'')
    // }
  }
}
</script>

<style>
  /* * {
    margin: 0;
    padding: 0} */
  body {
      overflow: auto;
      font-weight: 400;
      -webkit-font-smoothing: antialiased;
    }
  .tb-edit .el-input {
    display: none
  }
  .tb-edit .current-row .el-input {
    display: block
  }
  .tb-edit .current-row .el-input+span {
    display: none
  }
</style>
