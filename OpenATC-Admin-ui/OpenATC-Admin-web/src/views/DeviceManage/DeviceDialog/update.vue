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
  <div class="dev-update">
    <el-dialog
        :title="childTitle"
        :visible.sync="dialogFormVisible"
        width="913px"
        :close-on-click-modal="false"
        @close='closeFormDialog'>
        <el-form
        class="dialog-footer"
        ref="device"
        :model="deviceInfo"
        :rules="rules"
        label-width="15%">
        <el-form-item
            label="设备ID"
            prop="agentid">
            <el-input
            type="text"
            v-model="deviceInfo.agentid"
            :disabled="!!deviceInfo.id"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="设备名称"
            prop="name">
            <el-input
            type="text"
            v-model="deviceInfo.name"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="类型"
            prop="type">
            <el-select v-model="deviceInfo.type" placeholder="" style="width:100%">
                <el-option label="asc" value="asc"></el-option>
                <el-option label="simu" value="simu"></el-option>
                <el-option label="v-atc" value="v-atc"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            label="协议"
            prop="protocol">
            <el-select v-model="deviceInfo.protocol" placeholder="" style="width:100%">
                <el-option label="kedacom" value="kedacom"></el-option>
                <el-option label="GB" value="GB"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            label="IP"
            prop="ip" class="ipLabel">
            <el-input
            type="text"
            v-model="deviceInfo.ip"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="端口"
            prop="port" class="portLabel">
            <el-input
            type="text"
            v-model="deviceInfo.port"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="描述"
            prop="descs">
            <el-input
            type="text"
            v-model="deviceInfo.descs"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="经度"
            prop="lng">
            <el-input
            type="text"
            v-model="deviceInfo.lng"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            label="纬度"
            prop="lat">
            <el-input
            type="text"
            v-model="deviceInfo.lat"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        </el-form>
        <div
        slot="footer"
        class="dialog-footer">
            <el-button @click="resetForm('device')">取消</el-button>
            <el-button type="primary" @click="submitDeviceInfo('device')">确定</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { AddDevice, UpdateDevice } from '@/api/device'
export default {
  name: 'deviceUpdate',
  props: {
    childTitle: {
      type: String
    }
  },
  // watch: {
  //   deviceInfo: {
  //     handler: function (val, oldVal) {
  //       debugger
  //     },
  //     // 深度观察监听
  //     deep: true
  //   }
  // },
  data () {
    var checkIp = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入IP')
        )
      }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value)) {
        this.ip_status = true
        callback()
      } else {
        this.ip_status = false
        return callback(
          new Error('请输入合法的IP！')
        )
      }
    }
    var checkPort = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入端口')
        )
      }
      const portReg = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/
      setTimeout(() => {
        if (portReg.test(value)) {
          this.port_status = true
          callback()
        } else {
          this.port_status = false
          callback(
            new Error('请输入正确的端口！')
          )
        }
      }, 100)
    }
    return {
      dialogFormVisible: false,
      ip_status: true,
      port_status: true,
      deviceInfo: {},
      tempDevice: {
        agentid: '',
        name: '',
        descs: '',
        type: 'asc',
        protocol: 'kedacom',
        ip: '',
        port: '',
        lng: 0,
        lat: 0
      },
      rules: {
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' }
        ],
        protocol: [
          { required: true, message: '请选择协议', trigger: 'blur' }
        ],
        ip: [
          { validator: checkIp, trigger: 'blur' }
        ],
        port: [
          { validator: checkPort, trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: '请输入设备ID', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入设备名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitDeviceInfo (formName) {
      // 提交表单
      if (this.deviceInfo.ip === '' || this.deviceInfo.port === '') {
        this.$message.error('请填写必填项！')
        return
      }
      if (!this.ip_status) {
        this.$message.error('请填写合法的IP！')
        return
      }
      if (!this.port_status) {
        this.$message.error('请填写合法的端口！')
        return
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.deviceInfo.id) {
            this.updateDevice()
          } else {
            this.addDevice()
          }
        } else {
          this.$message.error('请填写必填项！')
          return false
        }
      })
    },
    addDevice () {
      let _vue = this
      let geometry = {
        type: 'Point',
        coordinates: [this.deviceInfo.lng, this.deviceInfo.lat]
      }
      let devInfo = this.deviceInfo
      devInfo.geometry = geometry
      delete devInfo.lng
      delete devInfo.lat
      AddDevice(devInfo).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          this.$message({
            message: res.data.message,
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: '新增成功',
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getList()
          }
        })
      })
    },
    updateDevice () {
      let _vue = this
      let geometry = {
        type: 'Point',
        coordinates: [this.deviceInfo.lng, this.deviceInfo.lat]
      }
      let devInfo = this.deviceInfo
      devInfo.geometry = geometry
      delete devInfo.lng
      delete devInfo.lat
      UpdateDevice(devInfo).then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          this.$message({
            message: '编辑失败',
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: '编辑成功',
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getList()
          }
        })
      })
    },
    closeFormDialog () {
      this.resetForm('device')
    },
    onUpdateClick (dev) {
      this.dialogFormVisible = !this.dialogFormVisible
      if (!dev) {
        // 新增置空
        this.deviceInfo = JSON.parse(JSON.stringify(this.tempDevice))
        return
      }
      // 编辑
      const device = JSON.parse(JSON.stringify(dev))
      let lng = 0
      let lat = 0
      if (device.geometry !== undefined) {
        lng = device.geometry.coordinates[0]
        lat = device.geometry.coordinates[1]
      }
      this.deviceInfo = {
        ...device,
        ip: device.jsonparam.ip,
        port: device.jsonparam.port,
        lng: lng,
        lat: lat
      }
    },
    resetForm (formData) {
      // 表单重置
      this.dialogFormVisible = false
      this.$refs[formData].resetFields()
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.dev-update .el-dialog__body {
  padding: 30px 72px 30px 0;
}
.el-dialog__footer {
  padding: 10px 72px 38px 0;
}
// 自定义校验规则的labal加上*号
.dev-update .ipLabel .el-form-item__label:before,  .dev-update .portLabel .el-form-item__label:before{
    content: '*';
    color: #f56c6c;
    margin-right: 4px;
}
</style>
