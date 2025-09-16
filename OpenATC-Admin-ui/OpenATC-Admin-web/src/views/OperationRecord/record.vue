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
  <div class="openatc-operationrecord">
    <div style="float:left;margin:20px 20px;">
      <el-tag type="info">{{$t('openatc.main.operationrecord')}}</el-tag>
    </div>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-input
            v-model="schfilter"
            :placeholder="$t('openatc.common.searchplaceholder')"
            prefix-icon="el-icon-search"
            style="width: 200px;"/>
        </el-form-item>
      </el-form>
    </div>
     <div class="atc-table">
    <el-table
        :data="tableData.filter(data => !schfilter || (data.operator !== undefined && data.operator.toLowerCase().includes(schfilter.toLowerCase())) || (data.description !== undefined && data.description.toLowerCase().includes(schfilter.toLowerCase())))"
        size="mini"
        :max-height="tableHeight"
        style="width: 100%"
        v-loading.body="listLoading"
        id="footerBtn">
        <el-table-column
        type="index"
        align="center">
        </el-table-column>
        <!-- <el-table-column
        prop="description"
        label="描述"
        align="center">
        </el-table-column> -->
        <el-table-column
        prop="operator"
        :label="$t('openatc.record.user')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="opertime"
        :label="$t('openatc.record.updatetime')"
        sortable
        align="center">
        </el-table-column>
        <el-table-column
        prop="source"
        :label="$t('openatc.record.originadress')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="agentid"
        :label="$t('openatc.record.deviceid')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="infotype"
        :label="$t('openatc.record.messagetype')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="status"
        :label="$t('openatc.record.reponsestatus')"
        align="center">
        </el-table-column>
        <el-table-column :label="$t('openatc.record.opera')" align="center">
        <template slot-scope="scope">
            <el-button type="text" @click="derive(scope.$index)">{{$t('openatc.record.export')}}</el-button>
        </template>
        </el-table-column>
    </el-table>
     <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" style='margin:0px;'>
    </el-pagination>
  </div>
  </div>
</template>

<script>
import { getHisParamsList, exportHisParams } from '../../api/table'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'user',
  components: {},
  data () {
    return {
      tableHeight: 700,
      screenHeight: window.innerHeight, // 屏幕高度
      schfilter: '',
      listLoading: false,
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
    this.getAllRecord()
  },
  methods: {
    leadingOut (data) {
      // 定义文件内容，类型必须为Blob 否则createObjectURL会报错
      // const tscParam = this.globalParamModel.getGlobalParams()
      let content = new Blob([JSON.stringify(data)])
      // 生成url对象
      let urlObject = window.URL || window.webkitURL || window
      let url = urlObject.createObjectURL(content)
      // 生成<a></a>DOM元素
      let el = document.createElement('a')
      // 链接赋值
      el.href = url
      el.download = 'export.txt'
      // 必须点击否则不会下载
      el.click()
      // 移除链接释放资源
      urlObject.revokeObjectURL(url)
    },
    getAllRecord () {
      this.listLoading = true
      getHisParamsList(this.listQuery.pageNum, this.listQuery.pageRow).then(data => {
        if (data.data.success !== true) {
          this.listLoading = false
          if (data.data.code === '20004') {
            this.$message.error('无参数记录!')
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.listLoading = false
        this.tableData = this.handleData(data.data.data.content)
        this.totalCount = data.data.data.total
      })
    },
    derive (index) {
      let id = this.tableData[index].id
      exportHisParams(id).then(data => {
        if (data.data.success !== true) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let exportData = data.data.data
        this.leadingOut(exportData)
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
    }
  }
}
</script>

<style lang="scss" scoped>
// .atc-table {
//   position: absolute;
//   top: 150px;
//   left: 20px;
//   right: 20px;
//   border: solid 1px $--border-color-lighter;
//   overflow: auto;
// }
// .filter-container {
//   float: right;
//   margin-top: 20px;
//   margin-right: 20px;
// }
</style>
