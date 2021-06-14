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
    <el-button style="margin-bottom:10px" type="primary" @click="deleteAllData">{{$t('edge.common.deleteall')}}</el-button>
    <el-table class="tb-edit" ref="singleTable" :data="detectorList" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row v-clickoutside="cancelTable" :max-height="tableHeight" id="footerBtn">
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
      <el-table-column align="center" :label="$t('edge.detector.desc')">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"  @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.desc}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.callPhase')" min-width="200">
        <template slot-scope="scope">
          <el-select v-model="scope.row.callphase" :placeholder="$t('edge.common.select')" size="small" clearable>
            <el-option
              v-for="item in CallPhaseOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <!-- <span>{{scope.row.CallPhase}}</span> -->
          <span>{{getPhaseById(scope.row.callphase)}}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="CallPhase">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.CallPhase"  @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.CallPhase}}</span>
        </template>
      </el-table-column> -->
      <el-table-column align="center" :label="$t('edge.detector.noactivity')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.noactivity"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.noactivity}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.maxpresence')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.maxpresence"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.maxpresence}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.erraticCounts')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.erraticcounts"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.erraticcounts}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.failtime')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.failtime"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.failtime}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('edge.detector.enable')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.enable"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.enable}}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column align="center" :label="$t('edge.detector.mode')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model="scope.row.mode"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.mode}}</span>
        </template>
      </el-table-column> -->
      <el-table-column align="center" :label="$t('edge.detector.mode')">
        <template slot-scope="scope">
          <el-select v-model="scope.row.mode" size="small">
            <el-option
              v-for="item in modeOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <span>{{scope.row.mode}}</span>
          <!-- <span>{{getPhaseById(scope.row.callphase)}}</span> -->
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.presenttime')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="30" :max="3600" :step="1" v-model="scope.row.presenttime"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.presenttime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.sense')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="9" :step="1" v-model="scope.row.sense"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.sense}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.sampletime')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="100" :max="2000" :step="1" v-model="scope.row.sampletime"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.sampletime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.inthresh')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="20" :max="1000" :step="1" v-model="scope.row.inthresh"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.inthresh}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.outthresh')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="10" :max="500" :step="1" v-model="scope.row.outthresh"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.outthresh}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.infilter')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="10" :step="1" v-model="scope.row.infilter"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.infilter}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.outfilter')" min-width="150">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="10" :step="1" v-model="scope.row.outfilter"  @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.outfilter}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.detector.operation')">
        <template slot-scope="scope">
          <el-button type="text" @click="handleClone(scope.$index,scope.row)">{{$t('edge.common.clone')}}</el-button>
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
  name: 'detector',
  components: { },
  data () {
    return {
      tableHeight: 760,
      screenHeight: window.innerHeight, // 屏幕高度
      listLoading: false,
      CallPhaseOption: [],
      id: 1,
      modeOption: [{
        value: 0,
        label: 0
      }, {
        value: 1,
        label: 1
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
      detectorList: state => state.globalParam.tscParam.detectorList
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
    detectorList: function () {
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
      let detectorList = this.globalParamModel.getParamsByType('detectorList')
      for (let dtcl of detectorList) {
        dtcl.callphase = callPhaseList.includes(dtcl.callphase) ? dtcl.callphase : ''
      }
    },
    // increaseId () { // 实现id在之前的基础上加1
    //   let detectorList = this.globalParamModel.getParamsByType('detectorList')
    //   let i = detectorList.length - 1
    //   if (i >= 0) {
    //     this.id = detectorList[i].id + 1
    //   }
    // },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let detectorList = this.globalParamModel.getParamsByType('detectorList')
      let detectorIdList = detectorList.map(ele => ele.id)
      let i = detectorList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 72; j++) {
          if (!detectorIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleEdit (index, row) {

    },
    handleDelete (index) {
      this.$confirm(this.$t('edge.detector.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('detectorList', index, 1)
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
    deleteAllData () {
      this.$confirm(this.$t('edge.detector.deleteAlltip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        let detectorList = this.globalParamModel.getParamsByType('detectorList')
        this.globalParamModel.deleteParamsByType('detectorList', 0, detectorList.length)
        this.id = 1
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
    handleClone (index, row) {
      let data = {}
      this.increaseId()
      if (this.globalParamModel.getParamLength('detectorList') >= 72) {
        this.$message.error(
          this.$t('edge.detector.mostdata')
        )
        return
      }
      Object.assign(data, row)
      data.id = this.id
      this.globalParamModel.addParamsByType('detectorList', data)
      // this.id++
      // 重新排序相位数组
      let detectorList = this.globalParamModel.getParamsByType('detectorList')
      detectorList.sort(this.compareProperty('id'))
    },
    onAdd () {
      this.increaseId()
      // if (this.globalParamModel.getParamLength('phaseList') === 0) {
      //   this.$message.error(
      //     this.$t('edge.pattern.firstphase')
      //   )
      //   return
      // }
      if (this.globalParamModel.getParamLength('detectorList') >= 72) {
        this.$message.error(
          this.$t('edge.detector.mostdata')
        )
        return
      }
      var detectorInitData = {
        id: this.id,
        callphase: '',
        noactivity: 0,
        maxpresence: 0,
        erraticcounts: 0,
        failtime: 0,
        enable: 1,
        mode: 0,
        presenttime: 240,
        sense: 3,
        sampletime: 100,
        inthresh: 50,
        outthresh: 20,
        infilter: 3,
        outfilter: 3
      }
      this.globalParamModel.addParamsByType('detectorList', detectorInitData)
      // this.id++
      // 重新排序相位数组
      let detectorList = this.globalParamModel.getParamsByType('detectorList')
      detectorList.sort(this.compareProperty('id'))
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
