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
  <div class="openatc-greenwareoptimize-device">
     <el-drawer
      ref="addDrawer"
      :title="$t('openatc.greenwaveoptimize.adddevice')"
      destroy-on-close
      :wrapperClosable="false"
      :append-to-body="true"
      :before-close="handleClose"
      :visible.sync="innerDrawer">
      <DeviceAdd
       :choosedDevice="devicesTableData"
       @closeAddDrawer="closeAddDrawer"
       @addMultiDevice="addMultiDevice"/>
    </el-drawer>
    <Messagebox v-if="messageboxVisible" :visible="messageboxVisible" :text="$t('openatc.greenwaveoptimize.deletedevice')+deleteId" @cancle="cancleDel" @ok="confirmDel"/>
    <div>
    <div style="overflow: hidden;">
      <el-button class="addbtn" type="primary" @click="handleAdd">{{$t('openatc.greenwaveoptimize.adddevice')}}</el-button>
    </div>
    <div class="devicePanel">
      <el-table
          class="deviceTable"
          :data="devicesTableData"
          row-key="id"
          v-loading.body="listLoading"
          style="width: 100%">
          <el-table-column
            type="index"
            label="#"
            align="center">
          </el-table-column>
          <el-table-column
            prop="agentid"
            width="120"
            :label="$t('openatc.greenwaveoptimize.deviceid')"
            align="center">
          </el-table-column>
           <el-table-column
            prop="name"
            width="120"
            :label="$t('openatc.greenwaveoptimize.devicename')"
            align="center">
          </el-table-column>
          <el-table-column
            prop="type"
            :label="$t('openatc.greenwaveoptimize.type')"
            align="center">
          </el-table-column>
          <el-table-column
            width="120"
            prop="jsonparam.ip"
            :label="$t('openatc.greenwaveoptimize.IP')"
            align="center">
          </el-table-column>
          <el-table-column
            prop="jsonparam.port"
            :label="$t('openatc.greenwaveoptimize.port')"
            align="center">
          </el-table-column>
          <el-table-column
            prop="descs"
            :label="$t('openatc.greenwaveoptimize.describe')"
            align="center">
          </el-table-column>
          <el-table-column :label="$t('openatc.greenwaveoptimize.operation')" align="center" width="120">
            <template slot-scope="scope">
              <el-button type="text" @click="handleDelete(scope.$index)">{{$t('openatc.common.delete')}}</el-button>
            </template>
          </el-table-column>
      </el-table>
    </div>
  </div>
</div>
</template>
<script>
import Messagebox from '../../../components/MessageBox/index'
import DeviceAdd from './components/deviceAdd'
import { GetAllDevice } from '@/api/device'
import Sortable from 'sortablejs'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'device',
  components: { Messagebox, DeviceAdd },
  props: {
    devicesData: {
      type: Array
    }
  },
  watch: {
    devicesData: {
      handler: function (val) {
        this.devicesTableData = JSON.parse(JSON.stringify(val))
      },
      deep: true
    },
    devicesTableData: {
      handler: function (newdata) {
        const sortAgentid = newdata.map(ele => ele.agentid)
        this.$emit('handleSortRow', sortAgentid)
      },
      deep: true
    }
  },
  data () {
    return {
      devicesTableData: [],
      messageboxVisible: false,
      listLoading: false, // 表格数据加载等待动画
      allDevs: [], // 所有设备
      curDevice: undefined, // 当前所选设备信息
      loading: false,
      innerDrawer: false // 里面的drawer
    }
  },
  methods: {
    handleDelete ($index) {
      // 删除设备
      let dev = this.devicesTableData[$index]
      this.deleteId = dev.agentid
      this.messageboxVisible = true
    },
    cancleDel () {
      this.messageboxVisible = false
    },
    confirmDel () {
      this.$emit('deleteDevice', this.deleteId)
      this.$message.success(this.$t('openatc.greenwaveoptimize.deletedevsuccess'))
      this.messageboxVisible = false
    },
    getOptions () {
      // 获取所有设备可选项
      this.loading = true
      GetAllDevice().then(res => {
        this.loading = false
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.allDevs = res.data.data
      })
    },
    rowDrop () {
      // 行拖拽排序
      const tbody = document.querySelector('.el-table__body-wrapper tbody')
      const _this = this
      Sortable.create(tbody, {
        onEnd ({ newIndex, oldIndex }) {
          const currRow = _this.devicesTableData.splice(oldIndex, 1)[0]
          _this.devicesTableData.splice(newIndex, 0, currRow)
        }
      })
    },
    handleClose (done) {
      done()
    },
    handleAdd () {
      this.innerDrawer = true
    },
    addMultiDevice (multiDevs) {
      this.$emit('addDevice', multiDevs)
      this.innerDrawer = false
    },
    closeAddDrawer () {
      this.$refs.addDrawer.closeDrawer()
    }
  },
  mounted () {
    this.rowDrop()
  }
}
</script>

<style>
.devicePanel .el-table td, .el-table th {
  padding: 3px 0;
}
.deviceTable .el-table__row {
  cursor: pointer;
}
</style>

<style lang="scss" scoped>
// .devicePanel {
//   border: solid 1px $--border-color-lighter;
// }
// .addbtn {
//   padding: 0;
//   width: 12.5%;
//   height: 40px;
//   text-align: center;
//   float: right;
//   line-height: 40px;
//   min-width: 66px;
//   margin-bottom: 7px;
// }
</style>
