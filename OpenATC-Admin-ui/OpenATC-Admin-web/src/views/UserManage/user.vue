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
    <div class="filter-container">
      <el-form>
        <el-form-item>
        <el-button
        type="primary"
        icon="el-icon-plus"
        @click="add">{{$t('openatc.common.add')}}</el-button>
        <el-input
            @keyup.enter.native="handleFilter"
            v-model="schfilter"
            :placeholder="$t('openatc.common.searchplaceholder')"
            prefix-icon="el-icon-search"
            style="width: 200px;"/>
      </el-form-item>
    </el-form>
  </div>
  <div class="atc-table">
    <el-table
        :data="tableData.filter(data => !schfilter || (data.user_name !== undefined && data.user_name.toLowerCase().includes(schfilter.toLowerCase())) || (data.nick_name !== undefined && data.nick_name.toLowerCase().includes(schfilter.toLowerCase())))"
        stripe
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
        :label="$t('openatc.usermanager.username')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="login_ip_limit"
        :label="$t('openatc.usermanager.ip')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="organization"
        :label="$t('openatc.usermanager.organization')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="nick_name"
        :label="$t('openatc.usermanager.realname')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="roleNames"
        :label="$t('openatc.usermanager.role')"
        align="center">
          <template slot-scope="scope">
            <span v-for="(data, index) in scope.row.roleNames" :key="index">
                <el-tag size="mini" :type="roleType[index]">{{ data }}</el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column
        prop="mobile_phone"
        :label="$t('openatc.usermanager.telno')"
        align="center">
        </el-table-column>
        <el-table-column
        prop="email"
        :label="$t('openatc.usermanager.email')"
        align="center">
        </el-table-column>
        <el-table-column :label="$t('openatc.usermanager.operation')" align="center">
        <template slot-scope="scope">
            <el-button type="text" @click="authorize(scope.$index)">{{$t('openatc.usermanager.authorize')}}</el-button>
            <el-button type="text" @click="edit(scope.$index)">{{$t('openatc.common.edit')}}</el-button>
            <el-button type="text" @click="handleDelete(scope.$index)">{{$t('openatc.common.delete')}}</el-button>
        </template>
        </el-table-column>
    </el-table>
  </div>
  <add ref="addChild"></add>
  <update ref="updateChild"></update>
  <authorize ref="authorizeChild"></authorize>
  <Messagebox :visible="messageboxVisible" :text="$t('openatc.usermanager.deleteuser')" @cancle="cancle" @ok="ok"/>
</div>
</template>

<script>
import add from './UserDialog/add'
import update from './UserDialog/update'
import authorize from './UserDialog/authorize'
import Messagebox from '../../components/MessageBox'
import { GetUsrInfoList, DeleteUsr } from '../../api/user'
export default {
  name: 'user',
  components: { add, update, Messagebox, authorize },
  data () {
    return {
      tableHeight: 700,
      schfilter: '',
      messageboxVisible: false,
      listLoading: false, // 数据加载等待动画
      tableData: [],
      delUser: {},
      roleType: ['', 'success', 'warning']
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
            this.$message.error(this.$t('openatc.common.authtip'))
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
            _vue.$message.error(this.$t('openatc.common.authtip'))
            return
          }
          _vue.$message.error(data.data.message)
          return
        }
        this.messageboxVisible = false
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.getAllUser()
          }
        })
      })
    },
    authorize ($index) {
      let user = this.tableData[$index]
      let authorizeChild = this.$refs.authorizeChild
      authorizeChild.onAuthorizeClick(user)
    }
  }
}
</script>

<style lang="scss" scoped>
.filter-container {
  float: right;
  margin-top: 20px;
  margin-right: 20px;
}
.atc-table {
  position: absolute;
  top: 150px;
  left: 20px;
  right: 20px;
  border: solid 1px $--border-color-lighter;
  overflow: auto;
}
</style>
