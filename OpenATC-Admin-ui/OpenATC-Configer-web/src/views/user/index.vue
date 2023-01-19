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
<div class="user-manager">
  <div class="filter-container">
      <el-form>
        <el-form-item>
        <el-button
        type="primary"
        icon="el-icon-plus"
        @click="add">新增</el-button>
        <el-input
            @keyup.enter.native="handleFilter"
            v-model="schfilter"
            placeholder="输入关键字搜索"
            prefix-icon="el-icon-search"
            style="width: 200px;"/>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-table
        :data="tableData.filter(data => !schfilter || (data.user_name !== undefined && data.user_name.toLowerCase().includes(schfilter.toLowerCase())) || (data.nick_name !== undefined && data.nick_name.toLowerCase().includes(schfilter.toLowerCase())))"
        size="mini"
        :max-height="tableHeight"
        v-loading.body="listLoading"
        style="width: 100%"
        id="footerBtn">
        <el-table-column
        type="index"
        align="center">
        </el-table-column>
        <el-table-column
        prop="user_name"
        label="用户名称"
        align="center">
        </el-table-column>
        <el-table-column
        prop="organization"
        label="组织机构"
        align="center">
        </el-table-column>
        <el-table-column
        prop="nick_name"
        label="真实姓名"
        align="center">
        </el-table-column>
        <el-table-column
        prop="roleNames"
        label="角色"
        align="center">
          <template slot-scope="scope">
            <span v-for="(data, index) in scope.row.roleNames" :key="index">
                <el-tag size="mini" :type="roleType[index]">{{ data }}</el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column
        prop="mobile_phone"
        label="电话"
        align="center">
        </el-table-column>
        <el-table-column
        prop="email"
        label="邮箱"
        align="center">
        </el-table-column>
        <el-table-column label="操作" align="center" width="250px">
        <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.$index)">编辑</el-button>
            <el-button type="text" @click="handleDelete(scope.$index)">删除</el-button>
            <el-button type="text" @click="handleUseOrStopUser(scope.$index)">{{ scope.row.status === 1 ? '停用' : '启用'}}</el-button>
            <el-button type="text" @click="handleUserResetable(scope.$index)">重置密码</el-button>
        </template>
        </el-table-column>
    </el-table>
  </div>
  <add ref="addChild"></add>
  <update ref="updateChild"></update>
  <Messagebox :visible="messageboxVisible" text="是否删除此用户?" @cancle="cancle" @ok="ok"/>
  <Messagebox :visible="isUsingVisible" :text="useOrStopText" @cancle="isUsingCancle" @ok="handleUpdateUser"/>
  <Messagebox :visible="resetPassVisible" text="是否重置此用户密码?" @cancle="resetCancle" @ok="handleResetUser"/>
</div>
</template>

<script>
import add from './UserDialog/add'
import update from './UserDialog/update'
import Messagebox from '../../components/MessageBox'
import { GetUsrInfoList, DeleteUsr, UpdateUsr, ResetUsr } from '../../api/user'
import {
  dateToString
} from '@/utils/dateFormat.js'
export default {
  name: 'user',
  components: { add, update, Messagebox },
  data () {
    return {
      tableHeight: 700,
      schfilter: '',
      messageboxVisible: false,
      isUsingVisible: false,
      resetPassVisible: false,
      listLoading: false, // 数据加载等待动画
      tableData: [],
      delUser: {},
      roleType: ['', 'success', 'warning'],
      usingOrStopedUser: {},
      useOrStopText: '',
      resetUser: {}
    }
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      // this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      // 后面的50：根据需求空出的高度，自行调整
      _this.tableHeight =
                window.innerHeight -
                document.querySelector('#footerBtn').offsetTop -
                200
      window.onresize = function () {
        // 定义窗口大小变更通知事件
        _this.screenHeight = window.innerHeight // 窗口高度
      }
    })
  },
  watch: {
    screenHeight: function () {
      // 监听屏幕高度变化
      this.tableHeight =
                window.innerHeight -
                document.querySelector('#footerBtn').offsetTop -
                200
    }
  },
  created () {
    this.getAllUser()
  },
  methods: {
    getAllUser () {
      this.listLoading = true
      GetUsrInfoList().then(data => {
        if (data.data.success !== true) {
          this.listLoading = false
          if (data.data.code === '3008') {
            this.$message.error('没有权限访问！')
            console.log(data.data.message)
            return
          }
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        this.listLoading = false
        this.tableData = data.data.data
      })
    },
    handleFilter () {
    },
    add () {
      let addChild = this.$refs.addChild
      addChild.onAddClick()
    },
    edit ($index) {
      let user = this.tableData[$index]
      if (user.expiration_time) {
        user.expirationDate = dateToString(user.expiration_time)
      }
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(user)
    },
    handleDelete ($index) {
      this.delUser = this.tableData[$index]
      this.messageboxVisible = true
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      let _vue = this
      let name = this.delUser.user_name
      DeleteUsr(name).then(data => {
        if (data.data.success !== true) {
          if (data.data.code === '3008') {
            _vue.$message.error('没有权限访问!')
            return
          }
          _vue.$message.error(data.data.message)
          return
        }
        this.messageboxVisible = false
        this.$message({
          message: '删除成功！',
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.getAllUser()
          }
        })
      })
    },
    handleUseOrStopUser ($index) {
      this.usingOrStopedUser = this.tableData[$index]
      this.useOrStopText = `是否${this.usingOrStopedUser.status === 1 ? '停用' : '启用'}此用户?`
      this.isUsingVisible = true
    },
    isUsingCancle () {
      this.isUsingVisible = false
    },
    handleUpdateUser () {
      // 停用/启用
      let _vue = this
      let user = this.usingOrStopedUser
      let param = {}
      param.status = user.status === 1 ? 0 : 1
      UpdateUsr(
        user.user_name,
        param
      ).then(data => {
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        let msg = `${this.usingOrStopedUser.status === 1 ? '停用' : '启用'}成功！`
        this.isUsingVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.getAllUser()
          }
        })
      })
    },
    handleUserResetable ($index) {
      this.resetUser = this.tableData[$index]
      this.resetPassVisible = true
    },
    resetCancle () {
      this.resetPassVisible = false
    },
    handleResetUser () {
      // 重置密码
      let _vue = this
      ResetUsr(
        this.resetUser.user_name
      ).then(data => {
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          console.log(data.data.message)
          return
        }
        let msg = '重置密码成功！'
        this.resetPassVisible = false
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.getAllUser()
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.user-manager {
  padding: 0 20px;
}
.filter-container {
  // float: right;
  text-align: right;
  margin-top: 20px;
  margin-right: 20px;
}
.atc-table {
  position: absolute;
  top: 150px;
  left: 20px;
  right: 20px;
  border: solid 1px #e6e6e6;
  overflow: auto;
}
</style>
