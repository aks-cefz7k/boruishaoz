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
<div class="openatc-planmanu">
  <div class="mask" v-if="maskVisible" @click ="clickMask"></div>
  <Messagebox :visible="deleteDiologVisible" :text="`${$t('openatc.greenwaveoptimize.isdelete')}${chooseName} ?`" @cancle="closeDeleteDialog" @ok="handleDelete"/>
  <AddOptimizeDialog
    :visible="addDialogVisible"
    :isedit="isEdit"
    :name="chooseName"
    @closeDialog="closeAddDialog"
    @updateRoute="updateRoute"/>
  <el-menu @select="selectPlan" v-loading="loading">
    <el-menu-item :index="String(plan.id)" v-for="(plan, index) in planData" :key="index">
        <span slot="title">{{plan.name}}</span>
    </el-menu-item>
  </el-menu>
  <div class="btnGroup">
    <div class="addbtn" :style="{zIndex: zIndexObj.addZIndex}">
      <el-popover placement="top-start" trigger="manual" v-model="maskVisible" width="290">
        <div class="tipContent">
          <div>
            <i class="iconfont icon-yindaoicon handicon"></i>
          </div>
          <div class="text">{{$t('openatc.greenwaveoptimize.addplan')}}</div>
        </div>
        <button slot="reference" class="btn" @click="onAdd" ref="addbtn">{{$t('openatc.common.add')}}</button>
      </el-popover>
    </div>
    <div class="operate">
      <el-dropdown trigger="click" @command="switchOperate">
        <span class="el-dropdown-link">
          {{$t('openatc.greenwaveoptimize.operation')}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-edit" command="Edit">{{$t('openatc.common.edit')}}</el-dropdown-item>
          <el-dropdown-item icon="el-icon-delete" command="Delete">{{$t('openatc.common.delete')}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- <el-button class="btn deletebtn" type="primary" @click="onDelete">删除</el-button> -->
  </div>
</div>
</template>

<script>
import Messagebox from '../../components/MessageBox'
import AddOptimizeDialog from './components/addOptimizeDialog'
import { AddRoute, DeleteRoute, UpdateRouteName } from '@/api/route'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'PlanMenu',
  components: {
    Messagebox,
    AddOptimizeDialog
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
          message: this.$t('openatc.greenwaveoptimize.nochooseplan'),
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
          message: this.$t('openatc.greenwaveoptimize.nochooseplan'),
          type: 'warning'
        })
        return
      }
      this.deleteDiologVisible = true
    },
    handleDelete () {
      DeleteRoute(this.chooseId).then(res => {
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
      UpdateRouteName(data).then(res => {
        if (!res.data.success) {
          if (res.data.code === '5001') {
            this.$message.error(this.$t('openatc.greenwaveoptimize.namerepeat'))
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
    addRoute (data) {
      AddRoute(data).then(res => {
        if (!res.data.success) {
          if (res.data.code === '5001') {
            this.$message.error(this.$t('openatc.greenwaveoptimize.addfail'))
          } else {
            this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          }
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

<style lang="scss">
//  @import "../../styles/theme/element-variables.scss";
// .btnGroup .operate .el-dropdown-link {
//   color: $--color-primary;
//   cursor: pointer;
// }
</style>

<style lang="scss" scoped>
//  @import "../../styles/theme/element-variables.scss";
.el-menu {
  height: calc(100vh - 120px);
  max-height: calc(100vh - 120px);
  overflow-y: auto;
  border-right: none;
}
// .el-menu-item.is-active {
//   background: #eaeaea;
// }
/* 蒙层样式 */
// .mask {
//   position: fixed;
//   left: 0;
//   top: 0;
//   width: 100%;
//   height: 100%;
//   background: #000;
//   opacity: 0.5;
//   z-index: 97;
// }
// /* 原生按钮样式修改 */
// .btnGroup button {
//     width: 70px;
//     height: 40px;
//     padding: 0;
//     line-height: 40px;
//     border: 1px solid #dcdfe6;
//     white-space: nowrap;
//     outline: 0;
//     transition: .1s;
//     font-size: 14px;
//     border-radius: 4px;
//     color: $--button-primary-font-color;
//     background-color: $--color-primary;
//     border-color: $--color-primary;
//     cursor: pointer;
// }
// .btnGroup button:hover {
//     color: $--button-primary-font-color;
//     background-color: $--color-primary;
//     border-color: $--color-primary;
//     opacity: 0.8;
// }
// .deletebtn {
//     position: fixed;
//     bottom: 30px;
//     left: 93px;
// }
// .btnGroup .addbtn {
//     position: fixed;
//     bottom: 30px;
//     left: 17px;
//     z-index: 98;
// }
// .btnGroup .addbtn button {
//   font-weight: 500;
// }
// .btnGroup .operate{
//   position: fixed;
//   bottom: 30px;
//   left: 108px;
//   height: 40px;
//   line-height: 40px;
// }
// /* 提示框相关 */
// .tipContent > div{
//     float: left;
// }
// .tipContent div i {
//   color: $--color-primary;
//   font-size: 30px;
// }
// .tipContent .text {
//     font-size: 14px;
//     font-weight: normal;
//     font-stretch: normal;
//     line-height: 14px;
//     letter-spacing: 1px;
//     color: $--color-primary;
//     margin-left: 7px;
//     line-height: 42px;
// }
</style>
