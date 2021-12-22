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
  <el-dialog
    :title="$t('openatc.organization.viewdevice')"
    :visible.sync="dialogVisible"
    width="1500px"
    @close="closeDialog"
  >
    <div>
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <el-input
              @keyup.enter.native="handleFilter"
              v-model="devsfilter"
              :placeholder="$t('openatc.common.searchplaceholder')"
              prefix-icon="el-icon-search"
              style="width: 200px"
            />
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table
          :data="list"
          stripe
          size="mini"
          :max-height="tableHeight"
          v-loading.body="listLoading"
          style="width: 100%"
          id="footerBtn"
        >
          <el-table-column type="index" align="center"> </el-table-column>
          <el-table-column
            prop="agentid"
            :label="$t('openatc.devicemanager.deviceid')"
            :sort-method="sortAgentId"
            sortable
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            :label="$t('openatc.devicemanager.devicename')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="code"
            :label="$t('openatc.organization.belongorganization')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="type"
            :label="$t('openatc.devicemanager.type')"
            sortable
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="jsonparam.ip"
            :label="$t('openatc.devicemanager.IP')"
            sortable
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="jsonparam.port"
            :label="$t('openatc.devicemanager.port')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="descs"
            :label="$t('openatc.devicemanager.describe')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="protocol"
            :label="$t('openatc.devicemanager.protocol')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="roles"
            :label="$t('openatc.devicemanager.state')"
            align="center"
          >
            <template slot-scope="scope">
              <div>
                <el-tag
                  size="medium"
                  effect="plain"
                  :type="getTag(scope.row).type"
                  >{{ getTag(scope.row).label }}</el-tag
                >
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="lastTime"
            :label="$t('openatc.devicemanager.lastupdatetime')"
            sortable
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
      <router-view></router-view>
    </div>
  </el-dialog>
</template>
<script>
import router from '@/router'
import Messagebox from '@/components/MessageBox'
import { GetAllDevice, DeleteDevice } from '@/api/device'
import { GetDevicesByOrgCode } from '@/api/relate'
export default {
  name: 'device',
  components: { Messagebox },
  data () {
    return {
      dialogVisible: false,
      tableHeight: 700,
      devsfilter: '',
      messageboxVisible: false,
      tableData: [],
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
        400
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
  computed: {
    list () {
      return this.tableData.filter(data =>
        !this.devsfilter ||
        (data.agentid !== undefined && data.agentid.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.jsonparam.ip !== undefined && data.jsonparam.ip.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.name !== undefined && data.name.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.code !== undefined && data.code.toLowerCase().includes(this.devsfilter.toLowerCase()))
      )
    }
  },
  created () {
    // this.getList()
  },
  methods: {
    show (orgcd) {
      this.dialogVisible = true
      this.getListByOrgCd(orgcd)
    },
    closeDialog () {

    },
    getTag (row) {
      if (row.state === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else {
        if (row.status === 0) {
          // 数据从设备端来，暂时写死，0代表正常状态，其余数字均代表一种类型的故障
          return {
            label: this.$t('openatc.devicemanager.online'),
            type: 'success'
          }
        } else {
          return {
            label: this.$t('openatc.devicemanager.fault'),
            type: 'danger'
          }
        }
      }
    },
    getList () {
      this.listLoading = true
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.listLoading = false
        this.tableData = res.data.data
      })
    },
    getListByOrgCd (orgcd) {
      this.listLoading = true
      GetDevicesByOrgCode(orgcd).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.listLoading = false
        this.tableData = res.data.data
      })
    },
    handleToDetail ($index) {
      const dev = this.tableData[$index]
      const devId = dev.id
      this.$store.dispatch('SetOperatedDeviceId', devId)
      this.$store.dispatch('SaveDevsTags', dev)
      this.$store.dispatch('GetMultDeviceData', devId)
      const curTscParam = this.$store.getters.openedDevice
      const curPath = this.$store.getters.openedPath
      if (!curTscParam) {
        this.$store.dispatch('ResetTscParam')
        this.$store.dispatch('SaveCurPath', '/overview/index')
      } else {
        this.$store.dispatch('SaveTscParam', curTscParam)
        this.$store.dispatch('SaveCurPath', curPath)
      }
      router.push({
        path: !curPath || curPath === '/overview/index' ? '/overview/index' : curPath,
        query: { IP: dev.jsonparam.ip, port: dev.jsonparam.port, agentid: dev.agentid, protocol: dev.protocol, isfromatc: true }
      })
    },
    handleFilter () {
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
    },
    sortAgentId (obj1, obj2) {
      let val1 = obj1.agentid
      let val2 = obj2.agentid
      return val1 - val2
    }
  }
}
</script>

<style lang="scss">
.tag-container {
  float: left;
  max-width: 70%;
  overflow: hidden;
}
.filter-container {
  float: right;
  margin-top: 20px;
  margin-right: 20px;
}
.devs-table {
  position: absolute;
  top: 134px;
  left: 20px;
  right: 20px;
  border: solid 1px $--border-color-lighter;
  overflow: auto;
}
</style>
