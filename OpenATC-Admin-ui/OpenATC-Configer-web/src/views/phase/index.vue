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
  <div class="app-container" ref="phase-container">
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-button style="margin-bottom:10px" type="primary" @click="deleteAllData">{{$t('edge.common.deleteall')}}</el-button>
    <el-table class="tb-edit" ref="singleTable" row-key="id" :data="list" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row v-clickoutside="cancelTable" :max-height="tableHeight" id="footerBtn">
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
      <el-table-column class="table-column" :label="$t('edge.phase.desc')" min-width="150" align="center">
        <template slot-scope="scope">
          <Tankuang :list="scope.row.direction" :imgs="imgs" :index="scope.$index" :showBottomName="showBottomName" :lines="lines" :rows="rows" @finsh="handlefinsh"/>
        </template>
      </el-table-column>
      <el-table-column class="table-column" :label="$t('edge.phase.peddesc')" min-width="150" align="center">
        <template slot-scope="scope">
          <PedTankuang :list="scope.row.peddirection" :imgs="pedimgs" :index="scope.$index" :showBottomName="showBottomName" :lines="lines" :rows="rows" @finsh="handlefinshped"/>
        </template>
      </el-table-column>
      <el-table-column class="table-column" :label="$t('edge.phase.controltype')" min-width="150" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.controltype" size="small">
            <el-option v-for="item in controlTypeList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
           <span v-text="getControlTypestr(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.ring')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="1" :max="4" :step="1" v-model.number="scope.row.ring" @change="handleRingEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.ring}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.concurrent')" min-width="100">
        <template slot-scope="scope">
          <el-select multiple v-model="scope.row.concurrent" @visible-change="getConcurrent(scope.row,$event)" size="small">
            <el-option v-for="item in ConcurrentList" :key="item" :label="item" :value="item">
            </el-option>
          </el-select>
          <span v-text="getConcurrentstr(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.mingreen')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.mingreen" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.mingreen}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.max1')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.max1" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.max1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.max2')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.max2" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.max2}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="status-col" :label="$t('edge.phase.passage')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="25" :step="1" v-model.number="scope.row.passage" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.passage}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.greenclear')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.flashgreen" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.flashgreen}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.yellow')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="25" :step="1" v-model.number="scope.row.yellow" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.yellow}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.redclear')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="25" :step="1" v-model.number="scope.row.redclear" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.redclear}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="status-col" :label="$t('edge.phase.redyellow')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.redyellow" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.redyellow}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="status-col" :label="$t('edge.phase.phasewalk')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="25" :step="1" v-model.number="scope.row.phasewalk" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.phasewalk}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" :label="$t('edge.phase.pedclear')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.pedclear" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.pedclear}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" :label="$t('edge.phase.greenpulse')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.greenpulse" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.greenpulse}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" :label="$t('edge.phase.redpulse')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.redpulse" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.redpulse}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" :label="$t('edge.phase.vehiclethresh')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.vehiclethresh" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.vehiclethresh}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" :label="$t('edge.phase.pedestrianthresh')" min-width="100">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="255" :step="1" v-model.number="scope.row.pedestrianthresh" @change="handleEdit(scope.$index, scope.row)" style="width: 100px;"></el-input-number>
          <span>{{scope.row.pedestrianthresh}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.pulsetype')" min-width="100">
        <template slot-scope="scope">
            <el-select v-model="scope.row.pulsetype" :placeholder="$t('edge.common.select')" size="small">
              <el-option
                v-for="item in pulseTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <span>{{getPulsetypestr(scope.row.pulsetype)}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.phase.operation')" width="100">
        <template slot-scope="scope">
          <el-button type="text" @click="handleClone(scope.$index,scope.row)">{{$t('edge.common.clone')}}</el-button>
          <el-button type="text" @click="handleDelete(scope.$index,scope.row)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Tankuang from '@/components/Tankuang'
import PedTankuang from '@/components/PedTankuang'
import { mapState } from 'vuex'
import { getPhase, pedimages } from './utils.js'

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
  name: 'phase',
  components: {
    Tankuang,
    PedTankuang
  },
  data () {
    return {
      tableHeight: 760,
      listLoading: false,
      // descriptionOption: ['North-Straight','North-Left','North-Right','North-Back','South-Straight','South-Left','South-Right','South-Back','West-Straight','West-Left','West-Right','West-Back','East-Straight','East-Left','East-Right','East-Back'],
      ConcurrentList: [],
      selectedOptions: [],
      id: 1,
      // imgs: images,
      showBottomName: false, // 用于控制弹框里是否在底部显示文字描述。
      lines: 4, // 弹框的行数
      rows: 4, // 弹框的列数
      controlTypeList: [{
        label: this.$t('edge.phase.mainroad'),
        value: 0
      }, {
        label: this.$t('edge.phase.bypass'),
        value: 1
      }, {
        label: this.$t('edge.phase.pedestrianonly'),
        value: 2
      }, {
        label: this.$t('edge.phase.busonly'),
        value: 3
      }, {
        label: this.$t('edge.phase.BRTonly'),
        value: 4
      }, {
        label: this.$t('edge.phase.tramonly'),
        value: 5
      }],
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
      list: state => state.globalParam.tscParam.phaseList
    })
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.increaseId()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['phase-container'].offsetHeight - 80
      window.onresize = function () {
        _this.tableHeight = _this.$refs['phase-container'].offsetHeight - 80
      }
    })
  },
  methods: {
    // increaseId () { // 实现id在之前的基础上加1
    //   let phaseList = this.globalParamModel.getParamsByType('phaseList')
    //   let i = phaseList.length - 1
    //   if (i >= 0) {
    //     this.id = phaseList[i].id + 1
    //   }
    // },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let phaseIdList = phaseList.map(ele => ele.id)
      let i = phaseList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 40; j++) {
          if (!phaseIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleEdit (index, row) {

    },
    handleDelete (index, row) {
      this.$confirm(this.$t('edge.phase.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('phaseList', index, 1)
        this.deleteRing(row)
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
      if (this.globalParamModel.getParamLength('phaseList') >= 40) {
        this.$message.error(
          this.$t('edge.phase.mostdata')
        )
        return
      }
      Object.assign(data, row)
      data.id = this.id
      this.globalParamModel.addParamsByType('phaseList', data)
      // this.id++
      this.editRing(data)
      // 重新排序相位数组
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      phaseList.sort(this.compareProperty('id'))
    },
    deleteAllData () {
      this.$confirm(this.$t('edge.phase.deleteAlltip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        let phaseList = this.globalParamModel.getParamsByType('phaseList')
        let patternList = this.globalParamModel.getParamsByType('patternList')
        this.globalParamModel.deleteParamsByType('phaseList', 0, phaseList.length)
        this.globalParamModel.deleteParamsByType('patternList', 0, patternList.length)
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
    onAdd () {
      this.increaseId()
      if (this.globalParamModel.getParamLength('phaseList') >= 40) {
        this.$message.error(
          this.$t('edge.phase.mostdata')
        )
        return
      }
      var phaseInitData = {
        id: this.id,
        direction: [],
        peddirection: [], // 行人方向
        mingreen: 15,
        max1: 30,
        max2: 45,
        passage: 3,
        phasewalk: 0,
        pedclear: 12,
        yellow: 3,
        redclear: 2,
        flashgreen: 6,
        redyellow: 0,
        ring: 1,
        greenpulse: 5,
        redpulse: 10,
        vehiclethresh: 30,
        pedestrianthresh: 30,
        controltype: 0 // 控制类型
      }
      this.globalParamModel.addParamsByType('phaseList', phaseInitData)
      // this.id++
      this.editRing(phaseInitData)
      // 重新排序相位数组
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      phaseList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    handleChange (value) {
      console.log(value)
    },
    getConcurrent (value, status) {
      this.ConcurrentList = []
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      if (status) {
        for (let i = 0; i < phaseList.length; i++) {
          if (phaseList[i].ring !== value.ring) {
            this.ConcurrentList.push(phaseList[i].id)
          }
        }
      }
    },
    getConcurrentstr (val) {
      if (val.concurrent) {
        let concurrent = val.concurrent
        if (concurrent.length === 0) {
          return ''
        } else {
          let str = ''
          for (let con of concurrent) {
            str = str + ',' + con
          }
          return str.substr(1)
        }
      }
      return ''
    },
    getPulsetypestr (val) {
      if (val !== undefined) {
        let choosed = this.pulseTypeList.filter(ele => ele.value === val)
        if (choosed.length) {
          return choosed[0].label
        }
      }
      return ''
    },
    getControlTypestr (val) {
      if (val.controltype !== undefined) {
        return this.controlTypeList.filter(ele => ele.value === val.controltype)[0].label
      }
      return ''
    },
    getDescritionstr (val) {
      let desc = val.direction
      if (desc.length === 0) {
        return ''
      } else {
        let str = ''
        for (let des of desc) {
          str = str + ',' + des
        }
        return str.substr(1)
      }
    },
    editRing (value) {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let id = value.id
      for (let pattern of patternList) {
        let ring = {}
        ring.name = 'Phase ' + id
        ring.id = id
        ring.value = 30
        pattern.rings[0].push(ring)
        pattern.cycle = pattern.cycle + 30
      }
    },
    handleRingEdit (index, row) {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let id = row.id
      for (let pattern of patternList) {
        let newRing = {}
        for (let ring of pattern.rings) {
          for (let i = 0; i < ring.length; i++) {
            if (ring[i].id === id) {
              newRing.name = ring[i].name
              newRing.value = ring[i].value
              newRing.id = id
              ring.splice(i, 1)
            }
          }
        }
        let i = row.ring - 1
        pattern.rings[i].push(newRing)
      }
    },
    deleteRing (row) {
      let patternList = this.globalParamModel.getParamsByType('patternList')
      let id = row.id
      let ring = row.ring - 1
      for (let pattern of patternList) {
        for (let i = 0; i < pattern.rings[ring].length; i++) {
          if (pattern.rings[ring][i].id === id) {
            pattern.rings[ring].splice(i, 1)
            pattern.cycle = pattern.cycle - 30
          }
        }
      }
    },
    cancelTable (e) {
      this.$refs.singleTable.setCurrentRow()
    },
    checkLane (value, index) {
    },
    handlefinsh (value) {
      let index = value.index
      let status = value.status
      let list = []
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        list.push(this.imgs[i].id)
      }
      this.$store.getters.tscParam.phaseList[index].direction = list
    },
    handlefinshped (value) {
      let index = value.index
      let status = value.status
      let list = []
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        list.push(this.pedimgs[i].id)
      }
      this.$store.getters.tscParam.phaseList[index].peddirection = list
    }
  }
}
</script>

<style>
/* * {
    margin: 0;
    padding: 0;
} */
/* .main {
    background-color: #fff;
    width: 100%;
    padding-bottom: 100%;
    padding-left: 0.5%;
    padding-top: 0.5%;
} */
/* .main>div{
    width: 23%; */
    /* padding-bottom: 23%; */
    /* border: 1% solid #fff;
    background-color: #ecf5ff;
    border-radius: 3%;
    float: left;
    margin: 1%;
    cursor: pointer;
} */
/* .noSelected{
    width: 23%;
    border: 1% solid #fff;
    background-color: #ecf5ff;
    border-radius: 3%;
    float: left;
    margin: 1%;
    cursor: pointer;
}
.Selected{
    width: 23%;
    border: 1% solid #fff;
    background-color: red;
    border-radius: 3%;
    float: left;
    margin: 1%;
    cursor: pointer;
} */
/* .main>div>img {
    width: 100%;
    height: 100%;
} */
body {
    overflow: auto;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
}
.tb-edit .el-input {
    display: none;
}
.tb-edit .current-row .el-input {
    display: block;
}
.tb-edit .current-row .el-input + span {
    display: none;
}
.tb-edit .el-select {
    display: none;
}
.tb-edit .current-row .el-select {
    display: block;
}
.tb-edit .current-row .el-select + span {
    display: none;
}
.tb-edit .el-input-number {
    display: none;
}
.tb-edit .current-row .el-input-number {
    display: block;
}
.tb-edit .current-row .el-input-number + span {
    display: none;
}
.tb-edit .el-col {
    display: none;
}
.tb-edit .current-row .el-col {
    display: block;
}
.tb-edit .current-row .el-col + span {
    display: none;
}
.tb-edit .el-popover {
    display: none;
}
.tb-edit .current-row .el-popover {
    display: block;
}
/* .tb-edit .current-row .el-popover + span {
    display: none;
} */
.showSpan {
  display: block;
}
.tb-edit .current-row .showSpan {
  display: none;
}
</style>
