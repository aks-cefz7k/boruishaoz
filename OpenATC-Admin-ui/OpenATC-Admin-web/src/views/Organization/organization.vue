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
  <div class="app-container openatc-organization">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-input
            :placeholder="$t('openatc.main.organization')"
            @keyup.enter.native="handleFilter"
            v-model="schfilter"
            style="width: 200px;"
          />
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="handleFilter"
            >{{ $t("openatc.button.search") }}</el-button
          >
          <el-button
            type="primary"
            v-if="isOpen == true"
            icon="el-icon-circle-plus-outline"
            @click="openAllRows"
            >{{ $t("openatc.button.expandall") }}</el-button
          >
          <el-button
            type="primary"
            v-else
            icon="el-icon-remove-outline"
            @click="openAllRows"
            >{{ $t("openatc.button.collapseall") }}</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <div class="atc-table">
      <organization-tree-table :data="data" border />
    </div>
  </div>
</template>

<script>
import {
  GetOrganizationList
} from '../../api/organization'
import { elTreeModel } from '../../model/tree/eltreemodel'
import organizationTreeTable from './components/organizationTreeTable'

export default {
  name: 'organization',
  components: { organizationTreeTable },
  data () {
    return {
      data: [],
      schfilter: '',
      allOrganization: [],
      isOpen: true,
      new_id: [],
      delete_id: []
    }
  },
  watch: {
    schfilter: function (curVal, oldVal) {
      if (curVal === '') {
        this.getList()
      } else if (oldVal !== curVal) {
        this.new_id = []
        this.delete_id = []
      }
    },
    deep: true
  },
  created () {
    this.getList()
  },
  methods: {
    getList (list) {
      // 查询所有
      GetOrganizationList().then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          return
        }
        this.allOrganization = data.data.data
        this.data = elTreeModel.CreateTreeByPath(this.allOrganization)
        if (this.schfilter !== '') {
          this.handleFilter()
        } else if (!this.isOpen) {
          const funcSearchCheck = node => {
            node.expand = 'true'
          }
          for (let curNode of this.data) {
            for (let node of curNode.g_travel()) {
              funcSearchCheck(node)
            }
          }
        } else if (list) {
          const funcSetCheck = node => {
            for (let exp of list) {
              if (exp === node.id) {
                node.expand = 'true'
              }
            }
          }
          for (let curNode of this.data) {
            for (let node of curNode.g_travel()) {
              funcSetCheck(node)
            }
          }
        }
      })
      this.isOpen = true
      this.openAllRows()
    },
    handleFilter () {
      if (this.schfilter === '') {
        this.isOpen = false
      }
      let searchId = []
      let serrchPre = this.allOrganization
      let num = 0
      this.data = elTreeModel.CreateTreeByPath(serrchPre)
      const funcSearch = node => {
        if (
          node.code
            .toUpperCase()
            .indexOf(this.schfilter.toUpperCase()) !== -1 ||
          node.description
            .toUpperCase()
            .indexOf(this.schfilter.toUpperCase()) !== -1
        ) {
          searchId.push(node.id)
          num++
          if (node.parent) {
            funcSearchParentid(node.parent)
          }
        }
      }
      const funcSearchParentid = node => {
        searchId.push(node.id)
        if (node.parent) {
          funcSearchParentid(node.parent)
        }
      }
      for (let curNode of this.data) {
        for (let node of curNode.g_travel()) {
          funcSearch(node)
        }
        searchId.push(1)
        searchId = searchId.concat(this.new_id)
        for (let deleteId of this.delete_id) {
          searchId.splice(deleteId)
        }
      }

      serrchPre = serrchPre.filter(function (item) {
        return searchId.includes(item.id)
      })
      if (num > 0) {
        // 判断搜索结果是否为空
        this.data = elTreeModel.CreateTreeByPath(serrchPre)
        const funcSearchCheck = node => {
          for (let exp of searchId) {
            if (exp === node.id) {
              node.expand = 'true'
            }
          }
        }
        for (let curNode of this.data) {
          for (let node of curNode.g_travel()) {
            funcSearchCheck(node)
          }
        }
      } else {
        this.data = []
      }
    },
    openAllRows () {
      if (this.isOpen && this.schfilter !== '') {
        this.handleFilter()
        this.isOpen = !this.isOpen
        return
      }
      this.new_id = []
      this.delete_id = []
      this.data = elTreeModel.CreateTreeByPath(this.allOrganization)
      const funcSearchCheck = node => {
        node.expand = this.isOpen
      }
      for (let curNode of this.data) {
        for (let node of curNode.g_travel()) {
          funcSearchCheck(node)
        }
      }
      this.isOpen = !this.isOpen
    }
  }
}
</script>
<style lang="scss" scoped>
// .atc-table {
//   position: absolute;
//   top: 150px;
//   left: 20px;
//   right: 20px;
//   border: solid 1px $--border-color-lighter;
//   overflow: auto;
// }
// .filter-container {
//   float: right;
//   margin-top: 20px;
//   margin-right: 20px;
// }
</style>
