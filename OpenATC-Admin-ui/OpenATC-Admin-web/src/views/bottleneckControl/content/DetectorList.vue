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
  <div class="overflow-detector">
    <div class="title">{{$t('openatc.bottleneckcontrol.bottlenecklist')}}</div>
    <el-button type="primary" class="add-btn" @click="handleAddDetector">{{$t('openatc.common.add')}}</el-button>
    <div class="overflow-detector-list">
      <el-table
        v-loading="loading"
        :data="detectorList"
        :row-style="highlightRow"
        @row-click="handleClickDetector"
        height="100%"
        style="width: 100%"
      >
        <!-- <el-table-column prop="id" :label="$t('openatc.bottleneckcontrol.detectorid')" width="120" align="center"></el-table-column> -->
        <el-table-column prop="description" :label="$t('openatc.bottleneckcontrol.detectorname')" align="center"></el-table-column>
        <el-table-column prop="type" :label="$t('openatc.bottleneckcontrol.type')" align="center"></el-table-column>
        <!-- <el-table-column prop="status" :label="$t('openatc.bottleneckcontrol.status')" align="center"></el-table-column> -->
        <el-table-column :label="$t('openatc.common.operation')" header-align="left" align="left">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="handleEditDetector(scope.row)"
              type="text"
              size="small"
            >{{$t('openatc.common.edit')}}</el-button>
            <el-button
              @click.native.prevent="handleDeleteDetector(scope.row)"
              type="text"
              size="small"
            >{{$t('openatc.common.delete')}}</el-button>
            <el-button
              @click.native.prevent="handleOpenControl(scope.row)"
              type="text"
              size="small"
            >{{$t('openatc.bottleneckcontrol.control')}}</el-button>
            <el-button
              @click.native.prevent="handleOffControl(scope.row)"
              type="text"
              size="small"
            >{{$t('openatc.bottleneckcontrol.recovery')}}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      :title="modalTitle + $t('openatc.bottleneckcontrol.bottleneckinfo')"
      :visible.sync="visible"
      :close-on-click-modal="false"
      width="37%"
      @close="oncancle"
      :destroy-on-close="true"
    >
      <UpdateDetector
        :editData="editDetector"
        @closeModal="oncancle"
        v-if="visible"
        @submitForm="submitForm"
      />
    </el-dialog>
  </div>
</template>

<script>
import UpdateDetector from './UpdateDetector'
import { OverflowDecApi } from '@/api/overflowDetector.js'
import { getMessageByCode } from '@/utils/responseMessage'
import { getTheme } from '@/utils/auth'
export default {
  name: 'overflowdectorlist',
  components: {
    UpdateDetector
  },
  props: {
    curDetectorDevs: {
      type: Object,
      default: null
    }
  },
  watch: {},
  data () {
    return {
      loading: false,
      detectorList: [],
      visible: false,
      modalTitle: this.$t('openatc.common.add'),
      editDetector: null,
      typeMap: new Map([['1', this.$t('openatc.bottleneckcontrol.overflow')], ['2', this.$t('openatc.bottleneckcontrol.queue')]]),
      statusMap: new Map([['1', this.$t('openatc.bottleneckcontrol.triggering')], ['2', this.$t('openatc.bottleneckcontrol.nottriggered')]])
    }
  },
  methods: {
    handleClickDetector (row, column, event) {
      this.$emit('handleClickDetector', row)
    },
    getData () {
      this.loading = true
      return new Promise((resolve, reject) => {
        OverflowDecApi.GetDetector()
          .then(data => {
            if (!data.data.success) {
              this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
              return
            }
            this.loading = false
            this.detectorList = data.data.data.map(ele => ({
              ...ele,
              type: this.typeMap.get(ele.type),
              status: this.statusMap.get(ele.status)
            }))
            this.loading = false
            resolve(this.detectorList)
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    handleAddDetector () {
      this.modalTitle = this.$t('openatc.common.add')
      this.visible = true
    },
    handleEditDetector (row) {
      this.modalTitle = this.$t('openatc.common.edit')
      this.editDetector = row
      this.visible = true
    },
    handleDeleteDetector (row) {
      this.$confirm(this.$t('openatc.bottleneckcontrol.isdeletedetector') + row.description, this.$t('openatc.common.tipsmodaltitle'), {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      })
        .then(() => {
          OverflowDecApi.DeleteDetectorById(row.id)
            .then(data => {
              if (!data.data.success) {
                this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
                return
              }
              this.$message({
                type: 'success',
                message: this.$t('openatc.common.deletesuccess')
              })
              // this.getData();
              this.$emit('refresh')
            })
            .catch(error => {
              this.$message.error(error)
              console.log(error)
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('openatc.common.canceloperate')
          })
        })
    },
    handleOpenControl (row) {
      this.$confirm(this.$t('openatc.bottleneckcontrol.isopencontrol') + ' ' + row.description, this.$t('openatc.common.tipsmodaltitle'), {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      })
        .then(() => {
          OverflowDecApi.openOverflowsControl(row.id)
            .then(data => {
              let success = 0
              if (!data.data.success) {
                this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
                return
              }
              success = data.data.data.data.success
              if (success !== 0) {
                let errormsg = 'openatc.bottleneckcontrol.putTscControlError' + success
                this.$message.error(this.$t(errormsg))
                return
              }
              this.$message({
                type: 'success',
                message: this.$t('openatc.bottleneckcontrol.opensuccess')
              })
              // this.getData();
              this.$emit('refresh')
            })
            .catch(error => {
              this.$message.error(error)
              console.log(error)
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('openatc.common.canceloperate')
          })
        })
    },
    handleOffControl (row) {
      this.$confirm(this.$t('openatc.bottleneckcontrol.isoffcontrol') + ' ' + row.description, this.$t('openatc.common.tipsmodaltitle'), {
        confirmButtonText: this.$t('openatc.button.OK'),
        cancelButtonText: this.$t('openatc.button.Cancel'),
        type: 'warning'
      })
        .then(() => {
          OverflowDecApi.offOverflowsControl(row.id)
            .then(data => {
              let success = 0
              if (!data.data.success) {
                this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
                return
              }
              success = data.data.data.data.success
              if (success !== 0) {
                let errormsg = 'openatc.bottleneckcontrol.putTscControlError' + success
                this.$message.error(this.$t(errormsg))
                return
              }
              this.$message({
                type: 'success',
                message: this.$t('openatc.bottleneckcontrol.offsuccess')
              })
              // this.getData();
              this.$emit('refresh')
            })
            .catch(error => {
              this.$message.error(error)
              console.log(error)
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('openatc.common.canceloperate')
          })
        })
    },
    oncancle () {
      this.visible = false
      this.editDetector = null
    },
    submitForm (formdata) {
      this.submitData = formdata
      delete this.submitData.status
      delete this.submitData.overflows
      if (this.modalTitle === this.$t('openatc.common.add')) {
        delete this.submitData.id
        this.addOverflowDetector()
      }
      if (this.modalTitle === this.$t('openatc.common.edit')) {
        this.updateOverflowDetector()
      }
    },
    addOverflowDetector () {
      OverflowDecApi.AddDetector(this.submitData)
        .then(data => {
          if (!data.data.success) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          this.$message({
            type: 'success',
            message: this.$t('openatc.common.addsuccess')
          })
          this.visible = false
          this.getData()
          this.$emit('refresh')
        })
        .catch(error => {
          this.$message.error(error)
          console.log(error)
        })
    },
    updateOverflowDetector () {
      OverflowDecApi.UpdateDetector(this.submitData)
        .then(data => {
          if (!data.data.success) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          this.$message({
            type: 'success',
            message: this.$t('openatc.common.updatesuccess')
          })
          this.visible = false
          this.getData()
          this.$emit('refresh')
        })
        .catch(error => {
          this.$message.error(error)
          console.log(error)
        })
    },
    highlightRow ({ row, rowIndex }) {
      let theme = getTheme()
      if (this.curDetectorDevs === null) return
      if (this.curDetectorDevs.id === row.id) {
        if (theme === 'dark') {
          return {
            'background-color': '#191f34'
          }
        } else {
          return {
            'background-color': '#F5F7FA'
          }
        }
      }
    }
  },
  mounted () {
    this.getData()
  }
}
</script>
