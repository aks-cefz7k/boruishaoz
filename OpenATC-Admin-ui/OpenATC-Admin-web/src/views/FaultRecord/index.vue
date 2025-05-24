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
                style="width: 200px;"
            />
            </el-form-item>
            </el-col>
            <el-col :span="14">
            <el-form-item>
                <el-date-picker
                    v-model="timeValue"
                    size="small"
                    type="datetimerange"
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
                @click="handleFilter"
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
          prop="faultid"
          :label="$t('openatc.faultrecord.faultid')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="boardcardtype"
          :label="$t('openatc.faultrecord.boardcardtype')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="faultbegintime"
          :label="$t('openatc.faultrecord.faultbegintime')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="faultendtime"
          :label="$t('openatc.faultrecord.faultendtime')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="faulttype"
            :label="$t('openatc.faultrecord.faulttype')"
            align="center"
            >
          </el-table-column>
          <el-table-column
          prop="faultchild"
          :label="$t('openatc.faultrecord.faultchild')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="faultgrade"
          :label="$t('openatc.faultrecord.faultgrade')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="faultvaluedetail"
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
import { getHisParamsList } from '../../api/table'
import { DeleteDevice } from '@/api/device'
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
      statusMode: new Map([['set-request', '请求错误'], ['set-response', '应答成功'], ['error-response', '应答错误'], ['Communication Error!', '通讯错误']]),
      statusModeEn: new Map([['set-request', 'Request error'], ['set-response', 'Successful response'], ['error-response', 'Response error'], ['Communication Error!', 'Communication error']]),
      infotypeMode: new Map([['control/pattern', '控制消息'], ['control/interrupt', '方案控制消息'], ['control/preempt', '优先控制'], ['control/channelcheck', '通道检测'], ['control/remote', '远程控制'], ['system/centerip', '中心ip地址'], ['system/ip1', '设备ip地址1'], ['system/ip2', '设备ip地址2'], ['system/serialport', '串口信息'], ['system/time', '系统时间'], ['system/remote', '远程调试'], ['system/update', '系统更新'], ['system/paramversion', '参数版本'], ['system/optstatparam', '优化统计参数'], ['system/faultclear', '故障消除'], ['system/udiskupdate', '更新U盘数据'], ['system/volumelog', '交通流量历史数据'], ['feature/channel', '通道'], ['feature/phase', '相位'], ['feature/overlap', '跟随相位'], ['feature/pattern', '方案'], ['feature/plan', '计划'], ['feature/date', '日期'], ['feature/peddetecter', '行人检测器'], ['feature/devinfo', '设备信息'], ['feature/manualpanel', '手动面板配置'], ['feature/channellock', '通道灯色锁定参数配置'], ['feature/all', '整体参数']]),
      // infotypeModeEn: new Map([['control/pattern', '控制消息'], ['control/interrupt', '方案控制消息'], ['control/preempt', '优先控制'], ['control/channelcheck', '通道检测'], ['control/remote', '远程控制'], ['system/centerip', '中心ip地址'], ['system/ip1', '设备ip地址1'], ['system/ip2', '设备ip地址2'], ['system/serialport', '串口信息'], ['system/time', '系统时间'], ['system/remote', '远程调试'], ['system/update', '系统更新'], ['system/paramversion', '参数版本'], ['system/optstatparam', '优化统计参数'], ['system/faultclear', '故障消除'], ['system/udiskupdate', '更新U盘数据'], ['system/volumelog', '交通流量历史数据'], ['feature/channel', '通道'], ['feature/phase', '相位'], ['feature/overlap', '跟随相位'], ['feature/pattern', '方案'], ['feature/plan', '计划'], ['feature/date', '日期'], ['feature/peddetecter', '行人检测器'], ['feature/devinfo', '设备信息'], ['feature/manualpanel', '手动面板配置'], ['feature/channellock', '通道灯色锁定参数配置'], ['feature/all', '整体参数']]),
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
      this.deleteId = dev.agentid
      this.messageboxVisible = true
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
    getAllRecord () {
      //     if (this.value === '' || this.value === null) {
    //     this.$message.error(this.$t('openatc.usermanager.Authorization'))
    //     return
    //   }
    //   let startDate = this.formateDate(this.timeValue[0])
    //   let endDate = this.formateDate(this.timeValue[1])
      this.listLoading = true
      getHisParamsList(this.listQuery.pageNum, this.listQuery.pageRow).then(data => {
        if (data.data.success !== true) {
          this.listLoading = false
          if (data.data.code === '20004') {
            this.$message.error('无参数记录!')
            console.log(data.data.message)
            return
          }
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        this.listLoading = false
        this.tableData = this.handleData(data.data.data.content)
        this.totalCount = data.data.data.total
      })
    },
    handleData (data) {
      let that = this
      for (let obj of data) {
        Object.keys(obj).forEach(function (key) {
          if (key === 'status') {
            if (that.$i18n.locale === 'en') {
              obj[key] = that.statusModeEn.get(obj[key])
            } else {
              obj[key] = that.statusMode.get(obj[key])
            }
          }
          if (key === 'infotype') {
            if (that.$i18n.locale === 'zh') {
              obj[key] = that.infotypeMode.get(obj[key])
            }
          }
        })
      }
      return data
    },
    handleSizeChange (val) {
      // 改变每页数量
      this.listQuery.pageRow = val
      this.handleFilter()
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
      DeleteDevice(this.deleteId).then(res => {
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
        this.getList()
      })
    }
  }
}
</script>

<style>

</style>
