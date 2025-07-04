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
  <div class="openatc-device">
    <Messagebox :visible="messageboxVisible" :text="$t('openatc.devicemanager.deletedevice')" @cancle="cancle" @ok="ok"/>
    <div class="devs-container">
      <div class="tag-container">
        <DeviceTags />
      </div>
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <el-button
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd">{{$t('openatc.common.add')}}</el-button>
            <el-input
              @keyup.enter.native="handleFilter"
              v-model="devsfilter"
              :placeholder="$t('openatc.common.searchplaceholder')"
              prefix-icon="el-icon-search"
              style="width: 200px;"/>
          </el-form-item>
        </el-form>
      </div>
    <div class="devs-table">
      <el-table
          :data="computedTable"
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
          :label="$t('openatc.devicemanager.deviceid')"
          :sort-method="sortAgentId"
          sortable
          align="center">
          </el-table-column>
           <el-table-column
          prop="name"
          :label="$t('openatc.devicemanager.devicename')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="type"
          :label="$t('openatc.devicemanager.type')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="jsonparam.ip"
          :label="$t('openatc.devicemanager.IP')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="jsonparam.port"
          :label="$t('openatc.devicemanager.port')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="sockettype"
            :label="$t('openatc.devicemanager.sockettype')"
            align="center"
            :formatter="formatterSockettype">
          </el-table-column>
          <el-table-column
          prop="platform"
          :label="$t('openatc.devicemanager.platform')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="firm"
          :label="$t('openatc.devicemanager.firm')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="descs"
          :label="$t('openatc.devicemanager.describe')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="protocol"
          :label="$t('openatc.devicemanager.protocol')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="roles"
            :label="$t('openatc.devicemanager.state')"
            align="center">
              <template slot="header">
                <el-popover
                  placement="bottom"
                  trigger="click">
                  <div>
                    <template>
                      <el-checkbox @change="onOnlineChange" :checked="true" :label="$t('openatc.devicemanager.online')"></el-checkbox>
                      <el-checkbox @change="onFaultChange" :checked="true" :label="$t('openatc.devicemanager.fault')"></el-checkbox>
                      <el-checkbox @change="onOfflineChange" :checked="true" :label="$t('openatc.devicemanager.offline')"></el-checkbox>
                    </template>
                  </div>
                  <el-row type="text" slot="reference">
                    {{$t('openatc.main.devicemanager')}}<i class="el-icon-caret-bottom state-search"></i>
                  </el-row>
                </el-popover>
              </template>
              <template  slot-scope="scope">
                <div>
                    <el-tag size="medium" effect="plain" :type="getTag(scope.row).type">{{ getTag(scope.row).label }}</el-tag>
                </div>
              </template>
          </el-table-column>
          <el-table-column
          prop="lastTime"
          width="150"
          :label="$t('openatc.devicemanager.lastupdatetime')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column :label="$t('openatc.devicemanager.operation')" align="center" width="240">
          <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{$t('openatc.common.edit')}}</el-button>
              <el-button type="text" @click="handleToDetail(scope.row)">{{$t('openatc.common.detail')}}</el-button>
              <el-button type="text" @click="handleDelete(scope.row)">{{$t('openatc.common.delete')}}</el-button>
              <el-button type="text" @click="handleFault(scope.row)">{{$t('openatc.devicemanager.faultDetail')}}</el-button>
          </template>
          </el-table-column>
      </el-table>
    </div>
    <Update ref="updateChild" :childTitle="childTitle"></Update>
    <Fault-detail ref="faultDetail" :childTitle="childTitle"></Fault-detail>
  </div>
  <router-view></router-view>
</div>
</template>
<script>
import router from '@/router'
import { mapState } from 'vuex'
import Messagebox from '../../components/MessageBox'
import Update from './DeviceDialog/update'
import FaultDetail from './DeviceDialog/FaultDetail'
import DeviceTags from './deviceTags'
import { GetAllDevice, DeleteDevice } from '@/api/device'
import { GetCurrentFaultByAgentid } from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'device',
  components: { Update, Messagebox, DeviceTags, FaultDetail },
  data () {
    return {
      stateList: [],
      isOnlineChecked: true,
      isFaultChecked: true,
      isOfflineChecked: true,
      tableHeight: 700,
      devsfilter: '',
      childTitle: 'adddevice',
      messageboxVisible: false,
      tableData: [],
      listLoading: false, // 数据加载等待动画
      firmMap: new Map([['Kedacom', '科达'], ['Tyco', '泰科'], ['Huatong', '华通']])
    }
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      _this.tableHeight = window.innerHeight * 0.8
    })
  },
  computed: {
    ...mapState({
      tags: state => state.globalVariable.deviceTags
    }),
    computedTable () {
      let list = []
      list = this.tableData.filter(data =>
        !this.devsfilter ||
        (data.agentid !== undefined && data.agentid.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.jsonparam.ip !== undefined && data.jsonparam.ip.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.name !== undefined && data.name.toLowerCase().includes(this.devsfilter.toLowerCase()))
      )
      let stateList = this.stateList
      if (stateList && stateList.length > 0) {
        list = list.filter(dev => {
          return stateList.includes(dev.state)
        })
      }
      return list
    }
  },
  created () {
    this.getList()
  },
  methods: {
    onOnlineChange (val) {
      this.isOnlineChecked = val
      this.onStateChange()
    },
    onFaultChange (val) {
      this.isFaultChecked = val
      this.onStateChange()
    },
    onOfflineChange (val) {
      this.isOfflineChecked = val
      this.onStateChange()
    },
    onStateChange () {
      let stateList = []
      if (this.isOnlineChecked) {
        stateList.push('UP')
      }
      if (this.isFaultChecked) {
        stateList.push('FAULT')
      }
      if (this.isOfflineChecked) {
        stateList.push('DOWN')
      }
      this.stateList = stateList
    },
    getTag (row) {
      if (row.state === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else if (row.state === 'FAULT') {
        return {
          label: this.$t('openatc.devicemanager.fault'),
          type: 'danger'
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
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.listLoading = false
        this.tableData = res.data.data.map(data => ({
          ...data,
          firm: this.firmMap.get(data.firm)
        }))
      })
    },
    handleToDetail (row) {
      const dev = row
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
        query: {agentid: dev.agentid, isfromatc: true}
      })
    },
    handleFilter () {
    },
    handleAdd () {
      this.childTitle = 'adddevice'
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick()
    },
    handleEdit (row) {
      this.childTitle = 'editdevice'
      let dev = row
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(dev)
    },
    handleDelete (row) {
      this.childTitle = 'faultDetail'
      let dev = row
      this.deleteId = dev.agentid
      this.messageboxVisible = true
    },
    handleFault (row) {
      let _this = this
      GetCurrentFaultByAgentid(row.agentid).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        } else {
          let list = res.data.data
          if (list && list.length > 0) {
            this.childTitle = 'faultDetail'
            let component = _this.$refs.faultDetail
            component.onViewFaultClick(list)
          } else {
            this.$message.info(this.$t('openatc.common.nodata'))
          }
        }
      })
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      DeleteDevice(this.deleteId).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
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
    },
    formatterSockettype (row, column) {
      let sockettype = row.sockettype
      let res = ''
      if (sockettype === 0) {
        res = 'UDP'
      } else if (sockettype === 1) {
        res = 'TCP'
      }
      return res
    }
  }
}
</script>

<style lang="scss">
// .tag-container {
//   float: left;
//   max-width: 70%;
//   overflow: hidden;
// }
// .filter-container {
//   float: right;
//   margin-top: 20px;
//   margin-right: 20px;
// }
// .devs-table {
//   position: absolute;
//   top: 134px;
//   left: 20px;
//   right: 20px;
//   border: solid 1px $--border-color-lighter;
//   overflow: auto;
// }
</style>
