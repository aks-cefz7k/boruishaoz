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
<div class="openatc-historyfaultrecord" id="historyfaultrecord">
    <Messagebox :visible="messageboxVisible" :text="$t('openatc.faultrecord.isdelfaultrecord')" @cancle="cancle" @ok="ok"/>
    <div class="filter-container">
        <div class="filter">
          <span class="header-span">{{$t('openatc.faultrecord.boardtype') }}：</span>
          <el-select
            style="width: 100px;"
            v-model="faultBoardType"
            @change="searchRecord('search')"
            clearable
            filterable>
            <el-option
              v-for="(item, index) in boardTypeOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div class="filter">
          <span class="header-span">{{$t('openatc.faultrecord.mainfaulttype') }}：</span>
          <el-select
            v-model="faultType"
            @change="searchRecord('search')"
            clearable
            filterable>
            <el-option
              v-for="(item, index) in mainFaultOptions"
              :key="index"
              :label="item.value"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </div>
        <div class="filter">
          <span class="header-span">{{$t('openatc.faultrecord.confirmresults') }}：</span>
          <el-select
            style="width: 100px;"
            v-model="enumerate"
            @change="searchRecord('search')"
            clearable
            filterable>
            <el-option
              v-for="(item, index) in enumerateOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div class="filter">
          <span class="header-span">{{$t('openatc.faultrecord.intersectionname') }}：</span>
          <SelectAgentid ref="selectAgentid" @onChange="onChangeAgentid"></SelectAgentid>
        </div>
        <div class="filter">
          <span class="header-span">{{$t('openatc.faultrecord.faulttime') }}：</span>
          <el-date-picker
                v-model="timeValue"
                popper-class="common-date-popper"
                size="small"
                type="datetimerange"
                style="height:40px;"
                @change="searchRecord('search')"
                :range-separator="$t('openatc.usermanager.to')"
                :start-placeholder="$t('openatc.usermanager.starttime')"
                :end-placeholder="$t('openatc.usermanager.endtime')">
            </el-date-picker>
            <!-- <el-button
              type="primary"
              icon="el-icon-search"
              @click="searchRecord"
              style="margin-left: 8px;"
              >{{ $t("openatc.button.search") }}</el-button> -->
        </div>
    </div>
    <div class="devs-table">
      <el-table
          :data="tableData"
          size="mini"
          :max-height="tableHeight"
          v-loading.body="listLoading"
          style="width: 100%"
          id="historytable">
          <el-table-column
          type="index"
          align="center">
          </el-table-column>
          <el-table-column
          prop="name"
          :label="$t('openatc.devicemanager.devicename')"
          sortable
          align="center">
          </el-table-column>
          <!-- <el-table-column
          prop="agentid"
          :label="$t('openatc.devicemanager.crossid')"
          sortable
          align="center">
          </el-table-column> -->
           <el-table-column
          prop="m_wFaultID"
          :label="$t('openatc.faultrecord.faultid')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="m_byFaultBoardType"
          :formatter="formatterBoardType"
          :label="$t('openatc.faultrecord.boardcardtype')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          width="150px"
          prop="m_unFaultOccurTime"
          :label="$t('openatc.faultrecord.faultbegintime')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          width="150px"
          prop="m_unFaultRenewTime"
          :label="$t('openatc.faultrecord.faultendtime')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="m_wFaultType"
            :formatter="m_wFaultTypes"
            :label="$t('openatc.faultrecord.faulttype')"
            align="center"
            >
          </el-table-column>
          <el-table-column
          :formatter="m_wSubFaultType"
          prop="m_wSubFaultType"
          :label="$t('openatc.faultrecord.faultchild')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          :formatter="m_byFaultLevel"
          prop="m_byFaultLevel"
          :label="$t('openatc.faultrecord.faultgrade')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="m_byFaultDescValue"
          :formatter="m_byFaultDescValue"
          :label="$t('openatc.faultrecord.faultvaluedetail')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="operator"
          :label="$t('openatc.faultrecord.operator')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="operationTime"
          width="150px"
          :label="$t('openatc.faultrecord.operationTime')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="enumerate"
          :label="$t('openatc.faultrecord.enumerate')"
          align="center">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.enumerate" :type="scope.row.enumerate === '1'?'info':scope.row.enumerate === '2'?'success':scope.row.enumerate === '0'?'':''">{{formatterEnumerate(scope.row)}}</el-tag>
          </template>
          </el-table-column>
          <el-table-column :label="$t('openatc.faultrecord.operation')" align="center" width="240">
          <template slot-scope="scope">
              <el-button type="text" @click="handleDelete(scope.row)">{{$t('openatc.common.delete')}}</el-button>
          </template>
          </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" style='margin:0px;'>
    </el-pagination>
    </div>
</div>
</template>

<script>
import { getMessageByCode } from '@/utils/responseMessage'
import { GetAllFaultRange, DeleteFault } from '@/api/fault'
import Messagebox from '../../components/MessageBox'
import { getBoardType, getAllMainFaultTypeArr, formatFaultDescValue, formatBoardType, formatEnumerate, formatSubFaultType, formatFaultLevel, formatFaultTypes } from '@/utils/fault.js'
import SelectAgentid from '@/components/SelectAgentid'
export default {
  components: { Messagebox, SelectAgentid },
  data () {
    return {
      timeValue: '',
      tableHeight: 700,
      schfilter: '',
      listLoading: false,
      messageboxVisible: false,
      tableData: [],
      delObj: {},
      listQuery: {
        pageNum: 1, // 页码
        pageRow: 50 // 每页条数
      },
      totalCount: 0, // 分页组件--数据总条数
      agentid: '',
      faultBoardType: '',
      boardTypeOptions: [],
      faultType: '',
      mainFaultOptions: [],
      enumerate: '',
      enumerateOptions: [{
        label: this.$t('openatc.faultrecord.untreated'),
        value: '0'
      }, {
        label: this.$t('openatc.faultrecord.ignored'),
        value: '1'
      }, {
        label: this.$t('openatc.faultrecord.confirmed'),
        value: '2'
      }]
    }
  },
  created () {
    this.initOptions()
    this.searchRecord()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      // this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      // 后面的50：根据需求空出的高度，自行调整
      _this.tableHeight = window.innerHeight - document.querySelector('#historytable').offsetTop - 60
      window.addEventListener(
        'resize',
        () => {
        // 定义窗口大小变更通知事件
          _this.tableHeight = window.innerHeight - document.querySelector('#historytable').offsetTop - 60
          // 用于计算按钮组距离顶部高度（因为按钮组不能用定位，会影响表格自适应高度）
          _this.$emit('changeBtnPosition', document.querySelector('#historyfaultrecord').offsetTop)
        }
      )
    })
  },
  methods: {
    initOptions () {
      this.boardTypeOptions = getBoardType()
      this.mainFaultOptions = getAllMainFaultTypeArr()
    },
    handleDelete (row) {
      this.delObj = {
        agentid: row.agentid,
        id: row.m_wFaultID
      }
      this.messageboxVisible = true
    },
    formatterBoardType (row) {
      return formatBoardType(row)
    },
    formatterEnumerate (row) {
      return formatEnumerate(row)
    },
    m_wSubFaultType (row) {
      return formatSubFaultType(row)
    },
    m_byFaultLevel (row) {
      return formatFaultLevel(row)
    },
    m_wFaultTypes (row) {
      return formatFaultTypes(row)
    },
    m_byFaultDescValue (row) {
      return formatFaultDescValue(row)
    },
    formateDate (newDate) {
      var y = newDate.getFullYear()
      var m = newDate.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      var d = newDate.getDate()
      d = d < 10 ? ('0' + d) : d
      var h = newDate.getHours()
      h = h < 10 ? ('0' + h) : h
      var minute = newDate.getMinutes()
      minute = minute < 10 ? ('0' + minute) : minute
      var second = newDate.getSeconds()
      second = second < 10 ? ('0' + second) : second
      return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
    },
    searchRecord (operate) {
      let beginTime, endTime
      this.listLoading = true
      if (this.timeValue) {
        beginTime = this.formateDate(this.timeValue[0])
        endTime = this.formateDate(this.timeValue[1])
      }
      if (operate === 'search') {
        // 搜索条件改变查询，页面返回第一页。否则可能无数据显示
        this.listQuery.pageNum = 1
      }
      GetAllFaultRange(this.listQuery.pageNum, this.listQuery.pageRow, false, this.agentid, beginTime, endTime, this.faultBoardType, this.faultType, this.enumerate).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.listLoading = false
        this.tableData = data.data.data.content
        this.totalCount = data.data.data.total
      })
    },
    handleSizeChange (val) {
      // 改变每页数量，默认返回第一页
      this.listQuery.pageNum = 1
      this.listQuery.pageRow = val
      this.searchRecord()
    },
    handleCurrentChange (val) {
      // 改变页码
      this.listQuery.pageNum = val
      this.searchRecord()
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      DeleteFault(this.delObj).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          this.$message({
            message: this.$t('openatc.common.deletefailed'),
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success'
        })
        this.messageboxVisible = false
        this.searchRecord()
      })
    },
    onChangeAgentid (agentid) {
      this.agentid = agentid
      this.searchRecord()
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
