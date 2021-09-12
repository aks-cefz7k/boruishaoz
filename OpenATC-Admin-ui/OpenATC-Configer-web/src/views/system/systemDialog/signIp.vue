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
  <div class="system-signip">
    <el-dialog
      :title="$t('edge.system.deviceidentificationcodesetting')"
      :visible.sync="dialogFormVisible"
      width="600px"
      @close='closeFormDialog'>
      <el-form
        class="dialog-footer"
        ref="signIpInfo"
        :model="signIpInfo"
        :rules="rules"
        label-width="15%">
        <el-form-item
            label="IP"
            prop="ip" class="ipLabel">
            <el-input
            type="text"
            size="small"
            style="width:90%"
            v-model="signIpInfo.ip"
            @keyup.enter.native="ok">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('edge.deviceinfo.subnetmask')"
            prop="subnetmask" class="ipLabel">
            <el-input
            type="text"
            size="small"
            style="width:90%"
            v-model="signIpInfo.subnetmask"
            @keyup.enter.native="ok">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('edge.deviceinfo.gateway')"
            prop="gateway" class="ipLabel">
            <el-input
            type="text"
            size="small"
            style="width:90%"
            v-model="signIpInfo.gateway"
            @keyup.enter.native="ok">
            </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">{{$t('edge.common.cancel')}}</el-button>
          <el-button type="primary" @click="ok">{{$t('edge.common.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/12/11
*/
import { setSignIp } from '@/api/system'
export default {
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
    var checkSubnetmask = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入子网掩码！')
        )
      }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value)) {
        this.subnetmask_status = true
        callback()
      } else {
        this.subnetmask_status = false
        return callback(
          new Error('请输入合法的子网掩码！')
        )
      }
    }
    var checkGateway = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error('请输入网关！')
        )
      }
      const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      if (ipReg.test(value)) {
        this.geteway_status = true
        callback()
      } else {
        this.geteway_status = false
        return callback(
          new Error('请输入合法的网关！')
        )
      }
    }
    return {
      dialogFormVisible: false,
      signIpInfo: {
        ip: '',
        subnetmask: '',
        gateway: ''
      },
      rules: {
        ip: [
          { validator: checkIp, trigger: 'blur' }
        ],
        subnetmask: [
          { validator: checkSubnetmask, trigger: 'blur' }
        ],
        gateway: [
          { validator: checkGateway, trigger: 'blur' }
        ]
      },
      ip_status: true,
      subnetmask_status: true,
      geteway_status: true
    }
  },
  name: 'signip',
  props: {},
  created () {
  },
  methods: {
    onEditSignip (ip, subnetmask, gateway) {
      if (ip !== '--') {
        this.signIpInfo.ip = ip
      }
      if (subnetmask !== '--') {
        this.signIpInfo.subnetmask = subnetmask
      }
      if (gateway !== '--') {
        this.signIpInfo.gateway = gateway
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.resetForm()
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    },
    ok () {
      if (!this.ip_status) {
        this.$message.error('请填写合法的IP！')
        return
      }
      if (!this.subnetmask_status) {
        this.$message.error('请填写合法的子网掩码！')
        return
      }
      if (!this.geteway_status) {
        this.$message.error('请填写合法的网关！')
        return
      }
      setSignIp(this.signIpInfo.ip, this.signIpInfo.subnetmask, this.signIpInfo.gateway).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$parent.getSignIp()
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.system-signip .el-form-item__label {
    text-align: right;
    float: left;
    font-size: 14px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    font-family: SourceHanSansCN-Regular;
    font-weight: normal;
    font-stretch: normal;
    line-height: 40px;
    letter-spacing: 0px;
    color: #999999;
}
</style>
