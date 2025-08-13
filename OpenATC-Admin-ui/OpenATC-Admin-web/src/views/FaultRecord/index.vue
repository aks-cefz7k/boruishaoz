<template>
<div class="openatc-faultrecord">
    <Messagebox :visible="messageboxVisible" :text="$t('openatc.devicemanager.deletedevice')" @cancle="cancle" @ok="ok"/>
    <div class="filter-container">
        <el-form>
            <el-col :span="7">
            <el-form-item>
            <el-input
                :placeholder="$t('openatc.common.searchdeviceid')"
                @keyup.enter.native="handleFilter"
                v-model="devsfilter"
                style="width: 200px;margin-right: 10px;"
            />
            </el-form-item>
            </el-col>
            <el-col :span="14">
            <el-form-item>
                <el-date-picker
                    v-model="timeValue"
                    size="small"
                    type="datetimerange"
                    style="height:41px;"
                    :range-separator="$t('openatc.usermanager.to')"
                    :start-placeholder="$t('openatc.usermanager.starttime')"
                    :end-placeholder="$t('openatc.usermanager.endtime')">
                </el-date-picker>
            </el-form-item>
            </el-col>
            <el-col :span="3">
            <el-button
                type="primary"
                icon="el-icon-search"
                @click="searchRecord()"
                >{{ $t("openatc.button.search") }}</el-button
            >
            </el-col>
        </el-form>
    </div>
    <div class="devs-table">
      <el-table
          :data="tableData.filter(data => !devsfilter || (data.agentid !== undefined && data.agentid.toLowerCase().includes(devsfilter.toLowerCase())) )"
          size="mini"
          :max-height="tableHeight"
          v-loading.body="listLoading"
          style="width: 100%"
          id="footerBtn">
          <el-table-column
          type="index"
          align="center">
          </el-table-column>
          <el-table-column
          prop="agentid"
          :label="$t('openatc.faultrecord.deviceid')"
          sortable
          align="center">
          </el-table-column>
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
          prop="m_unFaultOccurTime"
          :label="$t('openatc.faultrecord.faultbegintime')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
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
          prop="m_wSubFaultType"
          :label="$t('openatc.faultrecord.faultchild')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="m_byFaultLevel"
          :label="$t('openatc.faultrecord.faultgrade')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="m_byFaultDescValue"
          :label="$t('openatc.faultrecord.faultvaluedetail')"
          align="center">
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
import { GetAllFault, GetAllFaultRange, DeleteFaultById } from '@/api/fault'
import Messagebox from '../../components/MessageBox'
export default {
  components: { Messagebox },
  data () {
    return {
      timeValue: '',
      tableHeight: 700,
      screenHeight: window.innerHeight, // 屏幕高度
      schfilter: '',
      listLoading: false,
      devsfilter: '',
      messageboxVisible: false,
      tableData: [],
      deleteId: '',
      listQuery: {
        pageNum: 1, // 页码
        pageRow: 50 // 每页条数
      },
      totalCount: 0 // 分页组件--数据总条数
    }
  },
  created () {
    this.getAllRecord()
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
                200
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
                200
    }
  },
  methods: {
    handleDelete (row) {
      let dev = row
      this.deleteId = dev.id
      this.messageboxVisible = true
    },
    formatterBoardType (row, column) {
      let boardType = row.m_byFaultBoardType
      let res = ''
      if (boardType === 1) {
        res = '主控板'
      } else if (boardType === 2) {
        res = '灯控版'
      } else if (boardType === 3) {
        res = '车检板'
      } else if (boardType === 4) {
        res = 'I/O板'
      }
      return res
    },
    m_wFaultTypes (row, column) {
      let faultType = row.m_wFaultType
      let res = ''
      if (faultType >= 101 && faultType <= 199) {
        res = '主控板故障'
      } else if (faultType >= 201 && faultType <= 299) {
        res = '灯控版故障'
      } else if (faultType >= 301 && faultType <= 399) {
        res = '车检板故障'
      } else if (faultType >= 401 && faultType <= 499) {
        res = 'I/O板故障'
      }
      return res
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
    searchRecord () {
      if (this.timeValue && this.devsfilter) {
        let beginTime = this.formateDate(this.timeValue[0])
        let endTime = this.formateDate(this.timeValue[1])
        GetAllFaultRange(this.listQuery.pageNum, this.listQuery.pageRow, this.devsfilter, beginTime, endTime).then(data => {
          if (data.data.success !== true) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          this.tableData = data.data.data.content
          this.totalCount = data.data.data.total
        })
      } else {
        this.getAllRecord()
      }
    },
    getAllRecord () {
      this.listLoading = true
      GetAllFault(this.listQuery.pageNum, this.listQuery.pageRow).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        if (data.data.success) {
          this.listLoading = false
          this.tableData = data.data.data.content
          this.totalCount = data.data.data.total
        }
      })
    },
    handleSizeChange (val) {
      // 改变每页数量
      this.listQuery.pageRow = val
      this.getAllRecord()
    },
    handleCurrentChange (val) {
      // 改变页码
      this.listQuery.pageNum = val
      this.getAllRecord()
    },
    handleFilter () {
      // 查询事件
      this.listQuery.pageNum = 1
      this.getAllRecord()
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      DeleteFaultById(this.deleteId).then(res => {
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
        this.getAllRecord()
      })
    }

  }
}
</script>

<style lang="scss" scoped>
</style>
