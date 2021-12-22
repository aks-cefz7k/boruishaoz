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
    <el-table
      :data="formatData"
      :row-style="showRow"
      center
      v-bind="$attrs"
      border
      fit
      highlight-current-row
      style="width: 100%"
      :max-height="tableHeight"
      id="footerBtn"
    >
      <el-table-column
        v-for="(column, index) in columns"
        :key="column.value"
        :label="$t(column.text)"
        :width="column.width"
        header-align="center"
      >
        <template slot-scope="scope">
          <span
            v-for="space in scope.row._level"
            v-if="index === 0"
            :key="space"
            class="ms-tree-space"
          />
          <span
            v-if="iconShow(index, scope.row)"
            class="tree-ctrl"
            @click="toggleExpanded(scope.$index)"
          >
            <i :class="scope.row.expand ? 'el-icon-minus' : 'el-icon-plus'" />
          </span>
          {{ scope.row[column.value] }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.common.operations')" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            :disabled="scope.row.description === '*'"
            @click="editOrganization(scope.row)"
            >{{ $t("edge.common.update") }}</el-button
          >
          <el-button
            type="text"
            :disabled="isButtonDisabled(scope.row)"
            @click="removeOrganization(scope.row)"
            >{{ $t("edge.common.delete") }}</el-button
          >
          <el-button type="text" @click="AddOrganization(scope.row)">{{
            $t("edge.common.add")
          }}</el-button>
          <el-button
            type="text"
            :disabled="scope.row.description === '*'"
            @click="onRelateDeviceClick(scope.row)"
            >{{ $t("openatc.organization.relatedevice") }}</el-button
          >
          <el-button
            type="text"
            :disabled="scope.row.description === '*'"
            @click="onViewDeviceClick(scope.row)"
            >{{ $t("openatc.organization.viewdevice") }}</el-button
          >
        </template>
      </el-table-column>
      <slot />
    </el-table>
    <edit
      ref="editChild"
      :dialogFormVisible="dialogFormVisible"
      :dialogTittle="textMap[dialogStatus]"
      @onConfirm="clickOrganization"
    ></edit>
    <relate-device ref="relateDevice"></relate-device>
    <view-device ref="viewDevice"></view-device>
  </div>
</template>

<script>
import treeToArray from '@/components/TreeTable/eval'
import {
  AddOrganization,
  UpdateOrganization,
  DeleteOrganization
} from '@/api/organization'
import { GetDevicesByOrgCode } from '@/api/relate'
import edit from '@/views/Organization/components/edit'
import relateDevice from '@/views/Organization/components/relateDevice'
import viewDevice from '@/views/Organization/components/viewDevice'
export default {
  name: 'OrganizationTreeTable',
  components: {
    edit,
    relateDevice,
    viewDevice
  },
  props: {
    data: {
      type: [Array, Object],
      required: true
    },
    evalFunc: Function,
    evalArgs: Array,
    expandAll: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      dialogStatus: 'create',
      textMap: {
        update: this.$t('openatc.organization.modifyorganization'),
        create: this.$t('openatc.organization.addorganization')
      },
      tempOrganization: {
        label: '',
        name: '',
        id: '',
        code: '',
        description: ''
      },
      dialogFormVisible: false,
      rules: {
        label: [
          { required: true, message: this.$t('edge.check.input'), trigger: 'blur' }
        ],
        description: [
          { required: true, message: this.$t('edge.check.input'), trigger: 'blur' }
        ]
      },
      expand_id: [],
      columns: [
        {
          text: 'edge.common.name',
          value: 'label',
          width: 400
        },
        {
          text: 'edge.common.path',
          value: 'code'
        },
        {
          text: 'edge.common.description',
          value: 'description'
        }
      ],
      tableHeight: 700,
      screenHeight: window.innerHeight // 屏幕高度
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
  },
  computed: {
    // 格式化数据源
    formatData: function () {
      let tmp
      if (!Array.isArray(this.data)) {
        tmp = [this.data]
      } else {
        tmp = this.data
      }
      const func = this.evalFunc || treeToArray
      const args = this.evalArgs
        ? Array.concat([tmp, this.expandAll], this.evalArgs)
        : [tmp, this.expandAll]
      return func.apply(null, args)
    }
  },
  methods: {
    clickOrganization (valuemap) {
      if (!this.checkEdit(valuemap.get('name'), valuemap.get('description'))) { return }
      this.$refs.editChild.show(false)
      if (this.dialogStatus === 'create') {
        this.createOrganization(valuemap.get('name'), valuemap.get('description'))
      } else this.UpdateOrganization(valuemap.get('description'))
    },
    checkEdit (label, description) {
      if (!label) {
        this.$message.error(this.$t('edge.common.nullerror'))
        return false
      }
      if (this.dialogStatus === 'update') {
        if (label === this.tempOrganization.label &&
          description === this.tempOrganization.description) {
          this.$message.error(this.$t('edge.common.nochangeserror'))
          return false
        }
      }
      return true
    },
    showRow: function (row) {
      const show = row.row.parent
        ? row.row.parent.expand && row.row.parent._show
        : true
      row.row._show = show
      return show
        ? {
          'animation': 'treeTableShow 1s',
          '-webkit-animation': 'treeTableShow 1s'
        }
        : { 'display': 'none' }
    },
    isButtonDisabled (row) {
      let res = false
      if (row.code === '*') {
        res = true
      }
      return res
    },
    // 切换下级是否展开
    toggleExpanded: function (trIndex) {
      const record = this.formatData[trIndex]
      record.expand = !record.expand
      if (record.expand) {
        this.expand_id.push(record.id)
      } else {
        this.expand_id.splice(record.id)
      }
    },
    // 图标显示
    iconShow (index, record) {
      return index === 0 && record.children && record.children.length > 0
    },
    getDevicesByOrgCode (code) {
      return new Promise((resolve, reject) => {
        GetDevicesByOrgCode(code).then(data => {
          let list = data.data.data
          resolve(list)
        })
      })
    },
    async removeOrganization (row) {
      let id = row.id
      let _this = this
      _this.$confirm(_this.$t('openatc.greenwaveoptimize.isdelete') + ' ?', _this.$t('edge.common.alarm'), {
        confirmButtonText: _this.$t('edge.common.confirm'),
        showCancelButton: false,
        type: 'warning'
      })
        .then(() => {
          DeleteOrganization(id).then(data => {
            if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
              window.location.reload()
              return
            }
            if (data.data.success !== true) {
              _this.$message.error(this.$t('edge.common.deletefailed'))
              return
            }
            let msg = this.$t('edge.common.deletesucess')
            this.dialogFormVisible = false
            this.$message({
              message: msg,
              type: 'success',
              duration: 1 * 1000,
              onClose: () => {
                _this.$parent.getList(this.expand_id)
                _this.$parent.delete_id.push(id)
              }
            })
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.common.deletecancel')
          })
        })
    },
    editOrganization (per) {
      this.tempOrganization.label = per.label
      this.tempOrganization.name = per.name
      this.tempOrganization.code = per.code
      this.tempOrganization.id = per.id
      this.tempOrganization.description = per.description
      this.dialogStatus = 'update'
      let editObjectList = [
        {
          key: 'name',
          label: 'edge.common.name',
          type: 'text',
          keyword: true,
          default: this.tempOrganization.label,
          disable: true
        },
        {
          key: 'description',
          label: 'edge.common.description',
          type: 'text',
          keyword: false,
          default: this.tempOrganization.description
        }
      ]
      this.$refs.editChild.InitDialog(editObjectList)
      this.$refs.editChild.show(true)
    },
    AddOrganization (per) {
      this.tempOrganization.label = ''
      this.tempOrganization.name = per.name
      this.tempOrganization.code = per.code
      this.tempOrganization.id = per.id
      this.tempOrganization.description = per.description
      let editObjectList = [
        {
          key: 'name',
          label: 'edge.common.name',
          type: 'text',
          keyword: true
        },
        {
          key: 'description',
          label: 'edge.common.description',
          type: 'text',
          keyword: false
        }
      ]
      this.$refs.editChild.InitDialog(editObjectList)
      this.dialogStatus = 'create'
      this.$refs.editChild.show(true)
    },
    UpdateOrganization (description) {
      // 修改组织机构
      let _this = this
      let path = ''
      if (this.tempOrganization.code) {
        path = this.tempOrganization.code.replace(
          this.tempOrganization.name,
          this.tempOrganization.label
        )
      } else {
        path = '*'
      }
      UpdateOrganization(
        this.tempOrganization.id,
        path,
        description
      ).then(data => {
        if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
          window.location.reload()
          return
        }
        if (data.data.success !== true) {
          this.$message.error(data.data.message)
          return
        }
        let msg = this.$t('edge.common.updatesucess')
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _this.$parent.getList(this.expand_id)
          }
        })
      })
    },
    getOrganizationPath (label, code) {
      if (code === '' || label === '') { return undefined }
      let path = ''
      let newCode = label
      if (code) {
        if (this.tempOrganization.code.indexOf('*') !== -1) {
          path = this.tempOrganization.code.replace(
            '*',
            newCode
          )
        } else {
          path = this.tempOrganization.code + ':' + newCode
        }
      } else {
        path = newCode
      }
      return path
    },
    createOrganization (label, description) {
      // 添加新组织
      let _this = this
      let path = this.getOrganizationPath(label, this.tempOrganization.code)
      if (!path) {
        return false
      }
      AddOrganization(path, description).then(
        data => {
          if (data.data.code === '401') { // 判断用户是否在其他地方被踢出
            window.location.reload()
            return
          }
          if (data.data.success !== true) {
            this.$message.error(data.data.message)
            return
          }
          let msg = this.$t('edge.common.addsucess')
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              // _this.$parent.getList(this.expand_id);
              _this.$parent.new_id.push(data.data.data.id)
              _this.expand_id.push(_this.tempOrganization.id)
              _this.$parent.getList(_this.expand_id)
            }
          })
        }
      )
    },
    closeDialog () {
      this.$refs.tempOrganizationForm.resetFields() // 表单重置
    },
    resetForm () {
      this.$refs.editChild.show(false)
      this.$refs.tempOrganizationForm.resetFields() // 表单重置
    },
    onRelateDeviceClick (row) {
      let relateDevice = this.$refs.relateDevice
      relateDevice.show(row)
    },
    onViewDeviceClick (row) {
      let code = row.code
      let viewDevice = this.$refs.viewDevice
      viewDevice.show(code)
    }
  }
}
</script>
<style rel="stylesheet/css">
@keyframes treeTableShow {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@-webkit-keyframes treeTableShow {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>

<style lang="scss" rel="stylesheet/scss" scoped>
// $color-blue: #2196f3;
$space-width: 18px;
.ms-tree-space {
  position: relative;
  top: 1px;
  display: inline-block;
  font-style: normal;
  font-weight: 400;
  line-height: 1;
  width: $space-width;
  height: 14px;
  &::before {
    content: "";
  }
}
.processContainer {
  width: 100%;
  height: 100%;
}
table td {
  line-height: 26px;
}

.tree-ctrl {
  position: relative;
  cursor: pointer;
  color: $--color-primary;
  margin-left: -$space-width;
}
</style>
