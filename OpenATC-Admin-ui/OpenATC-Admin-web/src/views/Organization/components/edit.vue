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
  <el-dialog
    :title="dialogTittle"
    :visible.sync="dialogFormVisible"
    width="600px"
  >
    <el-form
      class="small-space"
      v-model="model"
      label-position="right"
      label-width="40%"
      style="width: 70%; margin-left:50px;"
    >
      <el-form-item
        v-for="obj in list"
        :key="obj.key"
        :label="$t(obj.label)"
        :required="obj.keyword"
      >
        <el-input
          :type="obj.type"
          :disabled="obj.disable"
          v-model="model[obj.label]"
          @keyup.enter.native="onConfirm"
        >
        </el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">{{
        $t("edge.common.cancel")
      }}</el-button>
      <el-button type="primary" @click="onConfirm">{{
        $t("edge.common.confirm")
      }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      dialogFormVisible: false,
      model: {},
      list: []
    }
  },
  name: 'edit',
  props: {
    dialogTittle: {
      type: String,
      default: ''
    }
  },
  methods: {
    show (bShow = true) {
      this.dialogFormVisible = bShow
    },
    getDataModel () {
      let model = {}
      for (let obj of this.list) {
        model[obj.label] = obj.default || ''
      }
      return model
    },
    onConfirm () {
      let map = new Map()
      for (let obj of this.list) {
        map.set(obj.key, this.model[obj.label])
      }
      this.$emit('onConfirm', map)
    },
    updateRole () {

    },
    /**
     * @brief 参数列表
     * list = [paramObj]
     * paramObj = {
     * key     参数唯一标识
     * label   显示名称(传入国际化key)
     * default 默认显示
     * keyword 是否是必填项
     * type    text/number/password
     * disable 是否禁用
     * }
     */
    InitDialog (list) {
      this.list = list
      this.model = this.getDataModel()
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
</style>
