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
    title="组织机构"
    :visible.sync="dialogPermissionVisible"
    width="600px"
  >
    <div class="borde-style">
      <el-tree
        :data="treeData"
        show-checkbox
        node-key="id"
        ref="tree"
        check-strictly="true"
        default-expand-all
        @check="treeCheck"
        top="20"
      ></el-tree>
    </div>
    <template slot="footer" class="dialog-footer">
      <center>
        <el-button @click="dialogPermissionVisible = false">{{
          $t("edge.common.cancel")
        }}</el-button>
        <el-button type="primary" @click="onOKClick()">{{
          $t("edge.common.confirm")
        }}</el-button>
      </center>
    </template>
  </el-dialog>
</template>

<script>
import {
  GetOrganizationList
} from '@/api/organization'
import { elTreeModel } from '@/model/tree/eltreemodel'
export default {
  name: 'chooseOrganizationDialog',
  data () {
    return {
      listLoading: false, // 数据加载等待动画
      dialogPermissionVisible: false,
      targetPath: '',
      list: [],
      treeData: []
    }
  },
  methods: {
    onClick (path) {
      this.getAllOrganization(path)
    },
    getAllOrganization (path) {
      // 查询所有权限
      GetOrganizationList().then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        this.list = data.data.data
        this.treeData = elTreeModel.CreateTreeByPath(
          this.list
        )
        // set init data
        this.InitTreeState(path)
      })
      this.dialogPermissionVisible = true
    },
    InitTreeState (path) {
      if (!path) {
        return false
      }
      let checkedList = []
      for (let item of this.list) {
        let code = item.orgnization_code
        if (path === code) {
          let id = item.id
          checkedList.push(id)
        }
      }
      this.$refs.tree.setCheckedKeys(checkedList)
    },
    treeCheck (data, checkInfo) {
      this.$refs.tree.setCheckedKeys([data.id])
      this.targetPath = data.code
    },
    resetChecked () {
      this.$refs.tree.setCheckedKeys([])
    },
    onOKClick () {
      this.$emit('getCheckedPath', this.targetPath)
      this.resetChecked()
      this.dialogPermissionVisible = false
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
</style>
