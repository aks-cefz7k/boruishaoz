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
  <div class="dev-fault-detail">
    <el-dialog
      :title="$t(`openatc.devicemanager.trafficStatistics`)"
      :visible.sync="dialogFormVisible"
      width="60%"
      :close-on-click-modal="false"
      @close='closeFormDialog'>
    <div class="content">

    </div>
    </el-dialog>
  </div>
</template>

<script>
import { DeleteFaultById } from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'TrafficStatistics',
  props: {
    childTitle: {
      type: String
    }
  },
  data () {
    return {
      dialogFormVisible: false,
      deviceInfo: {},
      faultList: []
    }
  },
  methods: {
    onView (list) {
      this.dialogFormVisible = !this.dialogFormVisible
      this.faultList = list
    },
    onIgnoreClick (id) {
      let _this = this
      DeleteFaultById(id).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          this.$message({
            message: this.$t('openatc.common.deletefailed'),
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getList()
          }
        })
      })
    },
    closeFormDialog () {

    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.dev-update .el-dialog__body {
  padding: 30px 72px 30px 0;
}
.el-dialog__footer {
  padding: 10px 72px 38px 0;
}
</style>
