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
  <div class="choseIntersection">
    <el-dialog
      :title="$t('openatc.devicemanager.adddevice')"
      :visible.sync="dialogTableVisible"
      width="90%"
      height="650px"
      style="margin-top: -10vh;"
      @close='closeTableDialog'>
      <div class="config-table">
        <DeviceList ref="devList"
                    :isShowCheckbox="isShowCheckbox"
                    :isShowOperation="isShowOperation"
                    :isShowTag="isShowTag"
                    :isShowAdd="isShowAdd"
                    :tableHeight="tableHeight"
                    @onSelectionChange="onSelectionChange"></DeviceList>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onCancelClick">{{$t('openatc.button.Cancel')}}</el-button>
        <el-button type="primary" @click="onSureClick">{{$t('openatc.button.OK')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import DeviceList from '@/views/DeviceManage/deviceList'
export default {
  name: 'ChoseIntersection',
  components: { DeviceList },
  data () {
    return {
      multipleSelection: [],
      isShowCheckbox: true,
      isShowOperation: false,
      isShowTag: false,
      isShowAdd: false,
      tableHeight: window.innerHeight * 0.6,
      dialogTableVisible: false
    }
  },
  methods: {
    closeTableDialog () {
      this.$refs.devList.resetMultipleSelection()
      this.dialogTableVisible = false
    },
    show () {
      this.dialogTableVisible = true
    },
    onSelectionChange (list) {
      this.multipleSelection = list
    },
    onCancelClick () {
      this.closeTableDialog()
    },
    onSureClick () {
      this.$emit('onSureClick', this.multipleSelection)
      this.closeTableDialog()
    }
  }
}
</script>
<style lang="scss">
.choseIntersection .el-dialog__body {
  padding-top: 0px;
  padding-bottom: 3px;
}
</style>

<style lang="scss" scoped>
.config-table {
  padding: 0 12px;
}
</style>
