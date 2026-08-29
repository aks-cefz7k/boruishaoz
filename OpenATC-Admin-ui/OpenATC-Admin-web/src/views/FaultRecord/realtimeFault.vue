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
<div class="openatc-realtimefaultrecord">
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
        res = this.$t('openatc.faultrecord.maincontrolboard')
      } else if (boardType === 2) {
        res = this.$t('openatc.faultrecord.lightcontrolversion')
      } else if (boardType === 3) {
        res = this.$t('openatc.faultrecord.carinspectionboard')
      } else if (boardType === 4) {
        res = this.$t('openatc.faultrecord.ioboard')
      }
      return res
    },
    formatterEnumerate (row, column) {
      let enumerate = row.enumerate
      let res = ''
      if (enumerate === '0') {
        res = this.$t('openatc.faultrecord.untreated')// 未处理
      } else if (enumerate === '1') {
        res = this.$t('openatc.faultrecord.ignored')// 忽略
      } else if (enumerate === '2') {
        res = this.$t('openatc.faultrecord.confirmed')// 确认
      }
      return res
    },
    m_wSubFaultType (row, column) {
      let wSubFaultType = row.m_wSubFaultType
      let res = ''
      if (wSubFaultType === 0) {
        res = ''
      } else if (wSubFaultType === 1) {
        res = this.$t('openatc.faultrecord.powerup')
      } else if (wSubFaultType === 2) {
        res = this.$t('openatc.faultrecord.powerdown')
      } else if (wSubFaultType === 3) {
        res = this.$t('openatc.faultrecord.powerno')
      } else if (wSubFaultType === 4) {
        res = this.$t('openatc.faultrecord.powerfault')
      }
      return res
    },
    m_byFaultLevel (row, column) {
      let byFaultLevel = row.m_byFaultLevel
      let res = ''
      if (byFaultLevel === 1) {
        res = this.$t('openatc.faultrecord.general')
      } else if (byFaultLevel === 2) {
        res = this.$t('openatc.faultrecord.degradation')
      } else if (byFaultLevel === 3) {
        res = this.$t('openatc.faultrecord.serious')
      }
      return res
    },
    m_wFaultTypes (row, column) {
      let faultType = row.m_wFaultType
      let res = ''
      if (faultType >= 101 && faultType <= 199) {
        res = this.$t('openatc.faultrecord.maincontrolboardfault')
      } else if (faultType >= 201 && faultType <= 299) {
        res = this.$t('openatc.faultrecord.lightcontrolversionfault')
      } else if (faultType >= 301 && faultType <= 399) {
        res = this.$t('openatc.faultrecord.carinspectionboardfault')
      } else if (faultType >= 401 && faultType <= 499) {
        res = this.$t('openatc.faultrecord.ioboardfault')
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
      if (this.timeValue) {
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
      } else if (this.devsfilter) {
        let beginTime = ''
        let endTime = ''
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
