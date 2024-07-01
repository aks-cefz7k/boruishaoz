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
    <el-dialog
      :title="$t('edge.system.featureparameterversionsetting')"
      :visible.sync="dialogFormVisible"
      width="600px"
      @close='closeFormDialog'>
      <div style="text-align: center;">
        <div class="paramversion-name">{{$t('edge.system.featureparameterversion')}}</div>
        <div style="margin-right: 30px;">
            <el-select v-model="value" :placeholder="$t('edge.common.select')" style="width:75%" size="small">
                <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">{{$t('edge.common.cancel')}}</el-button>
          <el-button type="primary" @click="ok">{{$t('edge.common.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/12/11
*/
import { setParamVersion } from '@/api/system'
export default {
  data () {
    return {
      dialogFormVisible: false,
      options: [{
        value: '1.0',
        label: '1.0'
      }],
      value: ''
    }
  },
  name: 'paramversion',
  props: {},
  created () {
  },
  methods: {
    onEditParamVersion (paramVersion) {
      if (paramVersion !== '--') {
        this.value = paramVersion
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.resetForm()
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    },
    ok () {
      setParamVersion(this.value).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$parent.getParamVersion()
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.paramversion-name {
    float: left;
    margin-left: 20px;
    margin-top: 5px;
    height: 14px;
    font-size: 14px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 14px;
    letter-spacing: 0px;
    color: #999999;
}
</style>
