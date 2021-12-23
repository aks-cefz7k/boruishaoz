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
  <div class="serviceroute-addDeviceDrawer">
    <div class="devs-container">
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <el-input
              v-model="devsfilter"
              :placeholder="$t('openatc.common.searchplaceholder')"
              prefix-icon="el-icon-search"
              style="width: 100%"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="devs-table">
        <el-table
          ref="addDeviceTable"
          stripe
          size="mini"
          style="width: 100%"
          v-loading.body="listLoading"
          :data="tableData"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" label="#" align="center">
          </el-table-column>
          <el-table-column
            prop="agentid"
            :label="$t('openatc.greenwaveoptimize.deviceid')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="jsonparam.ip"
            :label="$t('openatc.greenwaveoptimize.IP')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="jsonparam.port"
            :label="$t('openatc.greenwaveoptimize.port')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            :label="$t('openatc.greenwaveoptimize.devicename')"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
      <div class="btnGroup">
        <el-button class="btn" @click="handleClose">{{
          $t("openatc.button.Cancel")
        }}</el-button>
        <el-button class="btn" type="primary" @click="onOk">{{
          $t("openatc.button.OK")
        }}</el-button>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
import { GetAllDevice } from '@/api/device'
export default {
  name: 'deviceAdd',
  props: {
    choosedDevice: {
      type: Array
    }
  },
  watch: {
    devsfilter: {
      handler: function (val) {
        this.handleFilter(val)
      },
      deep: true
    }
  },
  data () {
    return {
      devsfilter: '',
      devsData: [], // 所有设备
      tableData: [], // 过滤后表格显示的设备
      multipleSelection: [], // 选中项
      listLoading: false // 数据加载等待动画
    }
  },
  created () {
    this.getList()
  },
  methods: {
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getList () {
      this.listLoading = true
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.listLoading = false
        this.filterDeviceData(res.data.data)
      })
    },
    filterDeviceData (allDevs) {
      // 数据源过滤掉已选择设备
      const agentids = this.choosedDevice.map(ele => ele.agentid)
      this.devsData = allDevs.filter(data => !agentids.includes(data.agentid))
      this.tableData = this.devsData
    },
    handleFilter (val) {
      this.tableData = this.devsData.filter(data => !this.devsfilter ||
        (data.agentid !== undefined && data.agentid.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.jsonparam.ip !== undefined && data.jsonparam.ip.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
        (data.name !== undefined && data.name.toLowerCase().includes(this.devsfilter.toLowerCase())))
      this.multipleSelection.forEach(row => {
        this.$nextTick(() => {
          this.$refs.addDeviceTable.toggleRowSelection(row, true)
        })
      })
    },
    onOk () {
      this.$emit('addMultiDevice', this.multipleSelection)
      this.$emit('closeAddDrawer')
    },
    handleClose (done) {
      this.$emit('closeAddDrawer')
    }
  }
}
</script>

<style>
.serviceroute-addDeviceDrawer .devs-table .el-table th > .cell {
  padding-left: 14px;
  padding-right: 14px;
}
</style>

<style lang="scss" scoped>
// .serviceroute-addDeviceDrawer {
//   height: 100%;
//   position: relative;
// }
// .serviceroute-addDeviceDrawer .btnGroup {
//   position: absolute;
//   bottom: 30px;
//   left: 0;
//   width: 100%;
//   padding: 0 16px;
//   display: flex;
//   align-items: center;
// }
// .serviceroute-addDeviceDrawer .btnGroup .btn {
//   width: 50%;
// }
// .serviceroute-addDeviceDrawer .devs-container {
//   padding: 0 20px;
//   overflow: hidden;
// }
// .serviceroute-addDeviceDrawer .filter-container {
//   float: right;
//   width: 100%;
// }
// .serviceroute-addDeviceDrawer .devs-table {
//   border: solid 1px $--border-color-lighter;
//   overflow: auto;
//   max-height: 590px;
// }
</style>
