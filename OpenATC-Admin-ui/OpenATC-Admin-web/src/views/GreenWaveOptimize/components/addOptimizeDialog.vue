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
  <div class="addOptimize">
    <el-dialog
      :title="isedit ? $t('openatc.greenwaveoptimize.editroute') : $t('openatc.greenwaveoptimize.addroute')"
      :visible.sync="addDialogVisible"
      :close-on-click-modal="false"
      width="780px"
      height="280px"
      @close="resetForm('addForm')">
      <el-form :model="Form" ref="addForm" label-width="100px" class="demo-ruleForm">
        <el-form-item :label="$t('openatc.greenwaveoptimize.name')" prop="oldPass">
            <el-input type="text" auto-complete="off" :placeholder="$t('openatc.greenwaveoptimize.routename')" v-model="Form.name"></el-input>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer">
          <el-button @click="resetForm('addForm')">{{$t('openatc.button.Cancel')}}</el-button>
          <el-button type="primary" @click="commit()">{{$t('openatc.button.OK')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'addOptimize',
  data () {
    return {
      addDialogVisible: false,
      Form: {
        name: ''
      }
    }
  },
  watch: {
    visible: {
      handler: function (val) {
        this.addDialogVisible = val
      },
      deep: true
    },
    isedit: {
      handler: function (val) {
        if (val) {
          this.Form.name = this.name
        }
      },
      deep: true
    }
  },
  props: {
    visible: {
      type: Boolean
    },
    name: {
      type: String
    },
    isedit: {
      type: Boolean
    }
  },
  methods: {
    commit () {
      this.$emit('updateRoute', {name: this.Form.name})
    },
    resetForm (formData) {
      this.Form.name = ''
      this.$emit('closeDialog')
      this.$refs[formData].resetFields() // 表单重置
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
</style>
