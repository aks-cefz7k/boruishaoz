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
  <div class="serviceroute-planmenu">
    <el-menu @select="selectPlan" v-loading="loading">
      <el-menu-item
        :index="String(plan.id)"
        v-for="(plan, index) in planData"
        :key="index"
      >
        <span slot="title">{{ plan.name }}</span>
      </el-menu-item>
    </el-menu>
    <div class="btnGroup">
      <div class="addbtn" :style="{ zIndex: zIndexObj.addZIndex }">
        <el-popover
          placement="top-start"
          trigger="manual"
          v-model="maskVisible"
          width="290"
        >
          <div class="tipContent">
            <div>
              <i class="iconfont icon-yindaoicon handicon"></i>
            </div>
            <div class="text">{{ $t("openatc.dutyroute.clickaddroute") }}</div>
          </div>
          <button slot="reference" class="btn" @click="onAdd" ref="addbtn">
            {{ $t("openatc.common.add") }}
          </button>
        </el-popover>
      </div>
      <div class="operate">
        <el-dropdown trigger="click" @command="switchOperate">
          <span class="el-dropdown-link">
            {{ $t("openatc.common.operation")
            }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-edit" command="Edit">{{
              $t("openatc.common.edit")
            }}</el-dropdown-item>
            <el-dropdown-item icon="el-icon-delete" command="Delete">{{
              $t("openatc.common.delete")
            }}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div class="mask" v-if="maskVisible" @click="clickMask"></div>
    <Messagebox
      :visible="deleteDiologVisible"
      :text="`${$t('openatc.greenwaveoptimize.isdelete')}${chooseName} ?`"
      @cancle="closeDeleteDialog"
      @ok="handleDelete"
    />
    <AddRouteDialog
      :visible="addDialogVisible"
      :isedit="isEdit"
      :name="chooseName"
      @closeDialog="closeAddDialog"
      @updateRoute="updateRoute"
    />
  </div>
</template>

<script>
import Messagebox from '../../components/MessageBox'
import AddRouteDialog from './components/addRouteDialog'
import { AddViproute, DeleteViproute, RenameViproute } from '@/api/service'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'PlanMenu',
  components: {
    Messagebox,
    AddRouteDialog
  },
  props: {
    planData: {
      type: Array
    },
    zIndexObj: {
      type: Object
    },
    loading: {
      type: Boolean
    }
  },
  data () {
    return {
      chooseId: 0, // 选中的计划id
      chooseName: '', // 选中的计划name
      deleteDiologVisible: false,
      maskVisible: false, // 引导遮罩层是否显示
      addNum: 0,
      addDialogVisible: false, // 新增弹窗是否显示
      isEdit: false
    }
  },
  watch: {
    planData: {
      handler: function (val) {
        if (this.chooseId) {
          this.chooseName = this.planData.filter(ele => ele.id === this.chooseId)[0].name
        }
        if (val && !val.length) {
          this.maskVisible = true
          // 模拟一次手动点击，触发tip显示
          this.$refs.addbtn.click()
        }
      },
      deep: true
    },
    maskVisible: {
      handler: function (val) {
        this.$emit('maskChange', val)
      },
      deep: true
    },
    addDialogVisible: {
      handler: function (val) {
        this.$emit('maskChange', val)
      },
      deep: true
    }
  },
  methods: {
    selectPlan (id) {
      // 选择某一方案
      this.chooseId = Number(id)
      this.chooseName = this.planData.filter(ele => ele.id === this.chooseId)[0].name
      this.$emit('chooseRoute', this.chooseId)
      this.$emit('maskChange', this.maskVisible)
      this.$emit('resetContentComponent')
    },
    onAdd () {
      if (this.maskVisible && this.addNum <= 1) {
        if (this.addNum === 1) {
          this.maskVisible = false
        }
        this.addNum++
      }
      if (!this.maskVisible) {
        this.isEdit = false
        this.addDialogVisible = true
      }
    },
    onEdit () {
      if (!this.chooseId) {
        this.$message({
          message: this.$t('openatc.dutyroute.nochooseplan'),
          type: 'warning'
        })
        return
      }
      this.isEdit = true
      this.addDialogVisible = true
    },
    onDelete () {
      if (!this.chooseId) {
        this.$message({
          message: this.$t('openatc.dutyroute.nochooseplan'),
          type: 'warning'
        })
        return
      }
      this.deleteDiologVisible = true
    },
    handleDelete () {
      DeleteViproute(this.chooseId).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.deleteDiologVisible = false
        this.resetChooseId()
        this.$message({
          message: this.$t('openatc.common.deletesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$emit('refresh')
          }
        })
      })
    },
    resetChooseId () {
      // 重置为未选择状态
      this.chooseId = 0
      this.$emit('chooseRoute', this.chooseId)
    },
    closeDeleteDialog () {
      this.deleteDiologVisible = false
    },
    updateRouteName (data) {
      RenameViproute(data).then(res => {
        if (!res.data.success) {
          if (res.data.code === '6001') {
            this.$message.error(this.$t('openatc.dutyroute.namerepeat'))
          } else {
            this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          }
          return
        }
        this.closeAddDialog()
        this.$message({
          message: this.$t('openatc.common.updatesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$emit('refresh')
          }
        })
      })
    },
    checkRouteName (nameObj) {
      let res = true
      let name = nameObj.name
      if (name === '' || name.trim() === '') {
        this.$message.error(this.$t('openatc.dutyroute.emptyRouteName'))
        res = false
        return res
      }
      for (let item of this.planData) {
        if (name === item.name) {
          this.$message.error(this.$t('openatc.dutyroute.namerepeat'))
          res = false
          return res
        }
      }
      return res
    },
    addRoute (data) {
      if (!this.checkRouteName(data)) {
        return false
      }
      AddViproute(data).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.closeAddDialog()
        this.$message({
          message: this.$t('openatc.common.addsuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$emit('refresh')
          }
        })
      })
    },
    updateRoute (data) {
      if (!this.checkRouteName(data)) {
        return false
      }
      if (this.isEdit) {
        const obj = {
          ...data,
          id: this.chooseId
        }
        this.updateRouteName(obj)
      } else {
        this.addRoute(data)
      }
    },
    closeAddDialog () {
      this.isEdit = false
      this.addDialogVisible = false
    },
    clickMask () {
      // 点击其他地方关闭蒙层
      this.maskVisible = false
    },
    switchOperate (command) {
      switch (command) {
        case 'Edit':
          this.onEdit()
          break
        case 'Delete':
          this.onDelete()
          break
        default:
          console.log(command)
          break
      }
    }
  }
}
</script>
