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
<div class="daily-record">
  <div>
    <el-table
        :data="recordList"
        stripe
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
        prop="starttime"
        :label="$t('edge.dailyrecord.data')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="infotype"
        :label="$t('edge.dailyrecord.infotype')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="subject"
        :label="$t('edge.dailyrecord.subject')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="object"
        :label="$t('edge.dailyrecord.object')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="status"
        :label="$t('edge.dailyrecord.operationresults')"
        align="center">
        </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
import { getAllDailyRecord } from '@/api/param'
export default {
  name: 'dailyRecord',
  components: {},
  data () {
    return {
      tableHeight: 700,
      schfilter: '',
      recordList: [],
      // recordList: [{
      //   'id': 1,
      //   'starttime': '2019-10-23 10:12:40',
      //   'endtime': '2019-10-23 11:12:40',
      //   'subject': 'admin',
      //   'object': 'video',
      //   'infotype': 'feature/pattern',
      //   'status': '成功'
      // },
      // {
      //   'id': 2,
      //   'starttime': '2019-10-23 10:12:40',
      //   'endtime': '2019-10-23 11:12:40',
      //   'subject': 'admin',
      //   'object': 'video',
      //   'infotype': 'feature/pattern',
      //   'status': '失败'
      // }],
      listLoading: false // 数据加载等待动画
    }
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
  created () {
    this.getAllDailyRecord()
  },
  methods: {
    getAllDailyRecord () {
      this.listLoading = true
      getAllDailyRecord().then(data => {
        let res = data.data
        if (!res.success) {
          this.listLoading = false
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        this.listLoading = false
        this.recordList = this.formateDateForAllFault(res.data.data.operationrecord)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    formateDateForAllFault (datas) {
      for (let data of datas) {
        data.starttime = this.formateDate(data.starttime)
        data.endtime = this.formateDate(data.endtime)
      }
      return datas
    },
    formateDate (gmtdate) {
      let newDate = new Date(gmtdate * 1000 + 8 * 60 * 60 * 1000)
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
    handleFilter () {
    }
  }
}
</script>

<style lang="scss" scoped>
.daily-record {
  padding: 0 20px;
}
.filter-container {
  // float: right;
  text-align: right;
  margin-top: 20px;
  margin-right: 20px;
}
</style>
