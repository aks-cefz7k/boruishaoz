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
    <el-table :data="dateList" :max-height="tableHeight" id="footerBtn">
      <el-table-column align="center" label="No" min-width="40">
        <template slot-scope="scope">
          {{scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="ID" min-width="40">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.date.desc')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.desc"></el-input>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.date.month')" align="center" min-width="100">
        <template slot-scope="scope">
            <el-select multiple v-model="scope.row.month" :placeholder="$t('edge.common.select')" size="small" @change="handleMonth(scope.row.month, scope.$index)" @visible-change="initOldOptions(scope.row.month, $event)">
              <el-option
                v-for="item in months"
                :key="item.label"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.date.day')" align="center" min-width="100">
        <template slot-scope="scope">
            <el-select multiple v-model="scope.row.day" :placeholder="$t('edge.common.select')" size="small" @change="handleDay(scope.row.day, scope.$index)" @visible-change="initOldOptions(scope.row.day, $event)">
              <el-option
                v-for="item in days"
                :key="item.label"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.date.date')" align="center" min-width="100">
        <template slot-scope="scope">
            <el-select multiple v-model="scope.row.date" :placeholder="$t('edge.common.select')" size="small" @change="handleDate(scope.row.date, scope.$index)" @visible-change="initOldOptions(scope.row.date, $event)">
              <el-option
                v-for="item in dates"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.date.plan')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.plan" :placeholder="$t('edge.common.select')" size="small">
            <el-option
              v-for="item in PlanOption"
              :key="item.id"
              :label="item.desc"
              :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.date.operation')" width="120">
          <template slot-scope="scope">
            <el-button type="text"  @click="handleDelete(scope.$index)">{{$t('edge.common.delete')}}</el-button>
          </template>
        </el-table-column>
    </el-table>
  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
  .checkbox-group {
    width: 200px;
  }
</style>

<script>
import { mapState } from 'vuex'
import dateObj from './utils.js'
import Sortable from 'sortablejs'
const { monthsModel, daysModelCh, daysModelEn, datesModel } = dateObj
export default {
  name: 'date',
  data () {
    return {
      tableHeight: 760,
      screenHeight: window.innerHeight, // 屏幕高度
      months: monthsModel,
      days: [],
      dates: datesModel,
      PlanOption: [],
      id: 1,
      oldOptions: []
    }
  },
  computed: {
    ...mapState({
      dateList: state => state.globalParam.tscParam.dateList
    })
  },
  created () {
    let lan = this.$i18n.locale
    if (lan === 'en') {
      this.days = daysModelEn
    }
    if (lan === 'zh') {
      this.days = daysModelCh
    }
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
    this.rowDrop()
  },
  watch: {
    screenHeight: function () {
    // 监听屏幕高度变化
      this.tableHeight =
              window.innerHeight -
              document.querySelector('#footerBtn').offsetTop -
              50
    },
    dateList: function () {
      this.init()
    }
  },
  methods: {
    init () {
      this.initData()
      this.increaseId()
    },
    initData () {
      this.PlanOption = []
      const planList = this.globalParamModel.getParamsByType('planList')
      const dateList = this.globalParamModel.getParamsByType('dateList')
      for (let i = 0; i < planList.length; i++) {
        let desc = planList[i].desc
        let id = planList[i].id
        if (desc === undefined || id === undefined) continue
        let obj = {}
        obj.desc = desc
        obj.id = id
        this.PlanOption.push(obj)
      }
      for (let i = 0; i < dateList.length; i++) {
        if (dateList[i].month.length === 12) dateList[i].month.push(0)
        if (dateList[i].date.length === 31) dateList[i].date.push('全选')
        if (dateList[i].day.length === 7) dateList[i].day.push(8)
        let plan = dateList[i].plan
        let idList = []
        for (let option of this.PlanOption) {
          idList.push(option.id)
        }
        if (idList.includes(plan)) continue
        dateList[i].plan = ''
      }
    },
    // increaseId () { // 实现id在之前的基础上加1
    //   let dateList = this.globalParamModel.getParamsByType('dateList')
    //   let i = dateList.length - 1
    //   if (i >= 0) {
    //     this.id = dateList[i].id + 1
    //   }
    // },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let dateList = this.globalParamModel.getParamsByType('dateList')
      let dateIdList = dateList.map(ele => ele.id)
      let i = dateList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 255; j++) {
          if (!dateIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleDelete (index) {
      this.$confirm(this.$t('edge.date.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('dateList', index, 1)
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
      if (this.globalParamModel.getParamLength('dateList') >= 255) {
        this.$message.error(
          this.$t('edge.date.mostdata')
        )
        return
      }
      let newDateItem = {}
      newDateItem.id = this.id
      newDateItem.desc = ''
      newDateItem.month = []
      newDateItem.day = []
      newDateItem.date = []
      newDateItem.plan = ''
      this.globalParamModel.addParamsByType('dateList', newDateItem)
      // this.id++
      // 重新排序相位数组
      let dateList = this.globalParamModel.getParamsByType('dateList')
      dateList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    rowDrop () {
      // 行拖拽排序
      const tbody = document.querySelector('.el-table__body-wrapper tbody')
      const _this = this
      Sortable.create(tbody, {
        onEnd ({ newIndex, oldIndex }) {
          const currRow = _this.dateList.splice(oldIndex, 1)[0]
          _this.dateList.splice(newIndex, 0, currRow)
        }
      })
    },
    handleMonth (val, index) {
      let allValues = []
      // 保留所有值
      for (let item of this.months) {
        allValues.push(item.value)
      }
      // 若是全部选择
      if (val.includes(0)) this.dateList[index].month = allValues
      // 取消全部选中，上次有，当前没有，表示取消全选
      if (this.oldOptions.includes(0) && !val.includes(0)) this.dateList[index].month = []
      // 点击非全部选中，需要排除全部选中，以及，当前点击的选项
      // 新老数据都有全部选中
      if (this.oldOptions.includes(0) && val.includes(0)) {
        let tempIndex = val.indexOf(0)
        val.splice(tempIndex, 1) // 排除全选选项
        this.dateList[index].month = val
      }
      // 全选未选，但是其他选项全部选上，则全选选上，上次和当前，都没有全选
      if (!this.oldOptions.includes(0) && !val.includes(0)) {
        if (val.length === allValues.length - 1) this.dateList[index].month = [0].concat(val)
      }
      this.oldOptions = this.dateList[index].month
    },
    handleMonthInit (val, status) {
      if (status) {
        this.oldOptions = val
      } else {
        this.oldOptions = []
      }
    },
    handleDate (val, index) {
      let allValues = []
      // 保留所有值
      for (let item of this.dates) {
        allValues.push(item)
      }
      // 若是全部选择
      if (val.includes('全选')) this.dateList[index].date = allValues
      // 取消全部选中，上次有，当前没有，表示取消全选
      if (this.oldOptions.includes('全选') && !val.includes('全选')) this.dateList[index].date = []
      // 点击非全部选中，需要排除全部选中，以及，当前点击的选项
      // 新老数据都有全部选中
      if (this.oldOptions.includes('全选') && val.includes('全选')) {
        let tempIndex = val.indexOf('全选')
        val.splice(tempIndex, 1) // 排除全选选项
        this.dateList[index].date = val
      }
      // 全选未选，但是其他选项全部选上，则全选选上，上次和当前，都没有全选
      if (!this.oldOptions.includes('全选') && !val.includes('全选')) {
        if (val.length === allValues.length - 1) this.dateList[index].date = ['全选'].concat(val)
      }
      this.oldOptions = this.dateList[index].date
    },
    handleDateInit (val, status) {
      if (status) {
        this.oldOptions = val
      } else {
        this.oldOptions = []
      }
    },
    handleDay (val, index) {
      let allValues = []
      // 保留所有值
      for (let item of this.days) {
        allValues.push(item.value)
      }
      // 若是全部选择
      if (val.includes(8)) this.dateList[index].day = allValues
      // 取消全部选中，上次有，当前没有，表示取消全选
      if (this.oldOptions.includes(8) && !val.includes(8)) this.dateList[index].day = []
      // 点击非全部选中，需要排除全部选中，以及，当前点击的选项
      // 新老数据都有全部选中
      if (this.oldOptions.includes(8) && val.includes(8)) {
        let tempIndex = val.indexOf(8)
        val.splice(tempIndex, 1) // 排除全选选项
        this.dateList[index].day = val
      }
      // 全选未选，但是其他选项全部选上，则全选选上，上次和当前，都没有全选
      if (!this.oldOptions.includes(8) && !val.includes(8)) {
        if (val.length === allValues.length - 1) this.dateList[index].day = [8].concat(val)
      }
      this.oldOptions = this.dateList[index].day
    },
    initOldOptions (val, status) {
      if (status) {
        this.oldOptions = val
      } else {
        this.oldOptions = []
      }
    }
  }
}
</script>
