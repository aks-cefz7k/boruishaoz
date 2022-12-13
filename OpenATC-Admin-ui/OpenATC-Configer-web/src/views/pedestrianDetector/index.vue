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
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-table class="tb-edit" ref="singleTable" :data="pedestrainDetectorList" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row v-clickoutside="cancelTable" :max-height="tableHeight" id="footerBtn">
      <el-table-column align="center" label='No' min-width="40">
        <template slot-scope="scope">
          <span>{{scope.$index+1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label='ID' min-width="40">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.desc')">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"  @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.desc}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.pedestrianDetector.type')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.type" size="small">
            <el-option
              v-for="item in typeOption"
              :key="item.value"
              :label="$t('edge.pedestrianDetector.typeOption' + item.value)"
              :value="item.value">
            </el-option>
          </el-select>
          <span>{{$t('edge.pedestrianDetector.typeOption' + scope.row.region)}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.pedestrianDetector.region')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.region" size="small">
            <el-option
              v-for="item in regionOption"
              :key="item.value"
              :label="$t('edge.pedestrianDetector.regionOption' + item.value)"
              :value="item.value">
            </el-option>
          </el-select>
           <span>{{$t('edge.pedestrianDetector.regionOption' + scope.row.region)}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.callPhase')">
        <template slot-scope="scope">
          <el-select v-model="scope.row.callphase" :placeholder="$t('edge.common.select')" size="small" clearable>
            <el-option
              v-for="item in CallPhaseOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <span>{{getPhaseById(scope.row.callphase)}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.noactivity')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.noactivity"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.noactivity}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.maxpresence')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.maxpresence"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxpresence}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.erraticCounts')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.erraticcounts"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.erraticcounts}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.pedestrianDetector.operation')">
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
const clickoutside = {
  // 初始化指令
  bind (el, binding, vnode) {
    function documentHandler (e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e)
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.__vueClickOutside__ = documentHandler
    document.addEventListener('click', documentHandler)
  },
  update () {},
  unbind (el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.__vueClickOutside__)
    delete el.__vueClickOutside__
  }
}
export default {
  name: 'pedestraindetector',
  components: { },
  data () {
    return {
      tableHeight: 760,
      screenHeight: window.innerHeight, // 屏幕高度
      listLoading: false,
      CallPhaseOption: [],
      id: 1,
      typeOption: [{
        value: 0
      }, {
        value: 1
      }, {
        value: 2
      }],
      regionOption: [{
        value: 0
      }, {
        value: 1
      }, {
        value: 2
      }]
    }
  },
  directives: { clickoutside },
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
    ...mapState({
      pedestrainDetectorList: state => state.globalParam.tscParam.pedestrainDetectorList
    })
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.init()
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
    },
    pedestrainDetectorList: function () {
      this.init()
    }
  },
  methods: {
    init () {
      this.initData()
      this.increaseId()
    },
    initData () {
      const phaseList = this.globalParamModel.getParamsByType('phaseList')
      this.CallPhaseOption = []
      for (let i = 0; i < phaseList.length; i++) {
        let pattern = {}
        var patternNum = phaseList[i].id
        var patternDescription
        if (phaseList[i].direction.length > 0 && phaseList[i].direction !== undefined) {
          patternDescription = patternNum + '-' + getPhaseDesc(phaseList[i].direction, this.$i18n.locale)
        } else {
          patternDescription = patternNum
        }
        pattern.value = patternNum
        pattern.label = patternDescription
        this.CallPhaseOption.push(pattern)
      }
      let callPhaseList = this.CallPhaseOption.map(ele => ele.value)
      let pedestrainDetectorList = this.globalParamModel.getParamsByType('pedestrainDetectorList')
      for (let pdl of pedestrainDetectorList) {
        pdl.callphase = callPhaseList.includes(pdl.callphase) ? pdl.callphase : ''
      }
    },
    // increaseId () { // 实现id在之前的基础上加1
    //   let pedestrainDetectorList = this.globalParamModel.getParamsByType('pedestrainDetectorList')
    //   let i = pedestrainDetectorList.length - 1
    //   if (i >= 0) {
    //     this.id = pedestrainDetectorList[i].id + 1
    //   }
    // },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let pedestrainDetectorList = this.globalParamModel.getParamsByType('pedestrainDetectorList')
      let pedestrainDetectorIdList = pedestrainDetectorList.map(ele => ele.id)
      let i = pedestrainDetectorList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 8; j++) {
          if (!pedestrainDetectorIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleEdit (index, row) {

    },
    handleDelete (index) {
      this.$confirm(this.$t('edge.pedestrianDetector.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('pedestrainDetectorList', index, 1)
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
      if (this.globalParamModel.getParamLength('pedestrainDetectorList') >= 8) {
        this.$message.error(
          this.$t('edge.pedestrianDetector.mostdata')
        )
        return
      }
      var pedDetectorInitData = {
        id: this.id,
        type: 0,
        region: 0,
        callphase: '',
        noactivity: 30,
        maxpresence: 45,
        erraticcounts: 3
      }
      this.globalParamModel.addParamsByType('pedestrainDetectorList', pedDetectorInitData)
      // this.id++
      // 重新排序相位数组
      let pedestrainDetectorList = this.globalParamModel.getParamsByType('pedestrainDetectorList')
      pedestrainDetectorList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    cancelTable (e) {
      this.$refs.singleTable.setCurrentRow()
    },
    getPhaseById (id) {
      let phaselist = this.globalParamModel.getParamsByType('phaseList')
      if (id === undefined || id === '') return ''
      let phasedesc = phaselist.filter((item) => {
        return item.id === id
      })[0].direction
      // console.log(phasedesc)
      if (phasedesc.length === 0) return id
      let description = getPhaseDesc(phasedesc, this.$i18n.locale)
      return id + '-' + description
    }
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
  .tb-edit .el-select {
    display: none
  }
  .tb-edit .current-row .el-select {
    display: block
  }
  .tb-edit .current-row .el-select+span {
    display: none
  }
  .tb-edit .el-input-number {
    display: none
}
.tb-edit .current-row .el-input-number {
  display: block;
  margin-left: 30%;
}
.tb-edit .current-row .el-input-number+span {
  display: none
}
</style>
