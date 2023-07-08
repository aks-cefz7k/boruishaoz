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
  <div class="gis-devicePanel">
    <div>
      <div class="devicePanel">
        <el-table
          ref="singleTable"
          class="deviceTable"
          :data="devicesTableData"
          row-key="id"
          max-height="600"
          highlight-current-row
          @current-change="handleCurrentChange"
          @row-click="onRowClick"
          v-loading.body="listLoading"
          style="width: 100%"
        >
          <el-table-column type="index" label="#" align="center" width="40">
          </el-table-column>
          <el-table-column
            prop="agentid"
            :label="$t('openatc.greenwaveoptimize.deviceid')"
            align="center"
            width="80"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            :label="$t('openatc.greenwaveoptimize.devicename')"
            align="center"
            width="80"
          >
          </el-table-column>
          <el-table-column
            prop="roles"
            :label="$t('openatc.devicemanager.state')"
            align="center"
            width="60"
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
            :label="$t('openatc.greenwaveoptimize.operation')"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{
                $t("openatc.common.edit")
              }}</el-button>
              <el-button type="text" @click="onDetailClick(scope.row)">{{
                $t("openatc.common.detail")
              }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <Update ref="updateChild"></Update>
    <EdgeModal ref="edgeModalChild"></EdgeModal>
    <router-view></router-view>
  </div>
</template>
<script>
// import router from '@/router'
import Update from './update'
import EdgeModal from './edgeModal'
export default {
  name: 'device',
  components: { Update, EdgeModal },
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
      chooseDevice: null,
      editDevicevisible: false,
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
    handleEdit (row) {
      this.curDevice = row
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(row)
    },
    setNewLocation (lngLat) {
      this.curDevice.lng = lngLat.lng
      this.curDevice.lat = lngLat.lat
      this.handleEdit(this.curDevice)
    },
    onLocationClick (row) {
      this.$emit('setDeviceLocation', row)
      this.$message.info(this.$t('openatc.gis.chooseLocationInfo'))
    },
    onDetailClick (row) {
      const dev = row
      let edgeModalChild = this.$refs.edgeModalChild
      edgeModalChild.openSingleEdge(dev)
    },
    getList () {
      this.$parent.getAllAdevice()
    },
    setCurrent (row) {
      this.$refs.singleTable.setCurrentRow(row)
    },
    handleCurrentChange (val) {
      this.currentRow = val
    },
    onRowClick (row) {
      this.$emit('setCurrent', row)
    }
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

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<style lang="scss" scoped>

 // 滚动条的宽度
  /deep/ .el-table__body-wrapper::-webkit-scrollbar {
    width: 6px; // 横向滚动条
    height: 6px; // 纵向滚动条 必写
  }
  // 滚动条的滑块
  /deep/ .el-table__body-wrapper::-webkit-scrollbar-thumb {
    background-color: #ddd;
    border-radius: 3px;
  }
</style>
