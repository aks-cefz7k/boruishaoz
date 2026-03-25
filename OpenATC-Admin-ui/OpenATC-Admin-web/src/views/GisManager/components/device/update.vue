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
      :title="$t(`openatc.devicemanager.editdevice`)"
      :visible.sync="dialogFormVisible"
      width="913px"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
      @close="closeFormDialog"
    >
      <el-form
        class="dialog-footer"
        ref="device"
        :model="deviceInfo"
        :rules="rules"
        label-width="15%"
      >
        <el-form-item
          :label="$t('openatc.devicemanager.deviceid')"
          prop="agentid"
        >
          <el-input
            type="text"
            v-model="deviceInfo.agentid"
            :disabled="!!deviceInfo.id"
            @keyup.enter.native="submitDeviceInfo('device')"
          >
          </el-input>
        </el-form-item>
        <el-form-item
          :label="$t('openatc.devicemanager.devicename')"
          prop="name"
        >
          <el-input
            type="text"
            v-model="deviceInfo.name"
            @keyup.enter.native="submitDeviceInfo('device')"
          >
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('openatc.devicemanager.longitude')" prop="lng">
          <el-input
            type="text"
            v-model="deviceInfo.lng"
            @keyup.enter.native="submitDeviceInfo('device')"
          >
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('openatc.devicemanager.latitude')" prop="lat">
          <el-input
            type="text"
            v-model="deviceInfo.lat"
            @keyup.enter.native="submitDeviceInfo('device')"
          >
          </el-input>
        </el-form-item>
        <el-form-item>
          <div class="icon-choose" @click="onChooseLocationClick">
            <i class="el-icon-location"></i>
            {{this.$t('openatc.gis.clickToChooseLocationInfo') }}
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('openatc.usermanager.organization')"
          prop="organization"
        >
          <el-input
            type="text"
            v-model="deviceInfo.code"
            @focus="relateOrganization(deviceInfo.code)"
            @keyup.enter.native="submitDeviceInfo('device')"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('device')">{{
          $t("openatc.button.Cancel")
        }}</el-button>
        <el-button type="primary" @click="submitDeviceInfo('device')">{{
          $t("openatc.button.OK")
        }}</el-button>
      </div>
    </el-dialog>
    <choose-organization-dialog
      ref="chooseOrganization"
      @getCheckedPath="getCheckedPath"
    ></choose-organization-dialog>
  </div>
</template>

<script>
import { UpdateDevice } from '@/api/device'
import chooseOrganizationDialog from '@/views/Organization/components/chooseOrganizationDialog'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'deviceUpdate',
  components: { chooseOrganizationDialog },
  data () {
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
        lat: 0,
        code: ''
      },
      rules: {
        type: [
          { required: true, message: this.$t('openatc.devicemanager.choosetype'), trigger: 'blur' }
        ],
        protocol: [
          { required: true, message: this.$t('openatc.devicemanager.chooseprotocol'), trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: this.$t('openatc.devicemanager.enterId'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('openatc.devicemanager.entername'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitDeviceInfo (formName) {
      // 提交表单
      if (this.deviceInfo.id) {
        this.updateDevice()
      }
    },
    updateDevice () {
      let _vue = this
      let lng = Number(this.deviceInfo.lng)
      let lat = Number(this.deviceInfo.lat)
      let geometry = {
        type: 'Point',
        coordinates: [lng, lat]
      }
      let devInfo = this.deviceInfo
      devInfo.geometry = geometry
      delete devInfo.lng
      delete devInfo.lat
      UpdateDevice(devInfo).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          this.$message({
            message: this.$t('openatc.common.updatefailed'),
            type: 'error',
            duration: 1 * 1000
          })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.updatesuccess'),
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
    onUpdateClick (row, isSelectLocation = false) {
      this.dialogFormVisible = true
      let device = row
      let lng = device.lng ? device.lng : 0
      let lat = device.lat ? device.lat : 0
      if (!isSelectLocation) { // 修改时回填
        if (device.geometry !== undefined) {
          lng = device.geometry.coordinates[0]
          lat = device.geometry.coordinates[1]
        }
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
      this.dialogFormVisible = false
      // 表单重置
      // this.$refs[formData].resetFields()
    },
    relateOrganization (orgPath) {
      let chooseOrganization = this.$refs.chooseOrganization
      chooseOrganization.onClick(orgPath)
    },
    getCheckedPath (path) {
      let temp = Object.assign({}, this.deviceInfo)
      temp.code = path
      this.deviceInfo = temp
    },
    onChooseLocationClick () {
      this.dialogFormVisible = false
      this.$parent.onLocationClick(this.deviceInfo)
    }
  }
}
</script>
