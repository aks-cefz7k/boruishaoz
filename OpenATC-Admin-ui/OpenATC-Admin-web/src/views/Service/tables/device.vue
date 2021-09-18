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
  <div>
    <el-drawer
      ref="addDrawer"
      title="新增设备"
      destroy-on-close
      :wrapperClosable="false"
      :append-to-body="true"
      :before-close="handleClose"
      :visible.sync="innerDrawer"
    >
      <DeviceAdd
        :choosedDevice="devicesTableData"
        @closeAddDrawer="closeAddDrawer"
        @addMultiDevice="addMultiDevice"
      />
    </el-drawer>
    <Messagebox
      v-if="messageboxVisible"
      :visible="messageboxVisible"
      :text="'是否删除设备' + deleteId"
      @cancle="cancleDel"
      @ok="confirmDel"
    />
    <div>
      <div style="overflow: hidden">
        <el-button class="addbtn" type="primary" @click="handleAdd"
          >添加设备</el-button
        >
      </div>
      <div class="devicePanel">
        <el-table
          class="deviceTable"
          :data="devicesTableData"
          row-key="id"
          stripe
          v-loading.body="listLoading"
          style="width: 100%"
        >
          <el-table-column type="index" label="#" align="center">
          </el-table-column>
          <el-table-column prop="agentid" label="设备ID" align="center">
          </el-table-column>
          <el-table-column prop="name" label="设备名称" align="center">
          </el-table-column>
          <el-table-column prop="type" label="类型" align="center">
          </el-table-column>
          <el-table-column
            width="120"
            prop="jsonparam.ip"
            label="IP"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="jsonparam.port" label="端口" align="center">
          </el-table-column>
          <el-table-column prop="descs" label="设备描述" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="handleDelete(scope.$index)"
                >删除</el-button
              >
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
      this.$message.success('删除成功！')
      this.messageboxVisible = false
    },
    getOptions () {
      // 获取所有设备可选项
      this.loading = true
      GetAllDevice().then(res => {
        this.loading = false
        if (!res.data.success) {
          this.$message.error(res.data.message)
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
    // this.rowDrop()
  }
}
</script>

<style>
.devicePanel .el-table td,
.el-table th {
  padding: 3px 0;
}
.deviceTable .el-table__row {
  cursor: pointer;
}
</style>

<style lang="scss" scoped>
.devicePanel {
  border: solid 1px #e6e6e6;
}
.addbtn {
  padding: 0;
  width: 12.5%;
  height: 40px;
  text-align: center;
  float: right;
  line-height: 40px;
  min-width: 66px;
  margin-bottom: 7px;
}
</style>
