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
        :title="$t(`openatc.devicemanager.${[childTitle]}`)"
        :visible.sync="dialogFormVisible"
        width="913px"
        :close-on-click-modal="false"
        @close='closeFormDialog'>
        <el-dialog
          width="700px"
          :title="$t('openatc.devicemanager.updateDeviceId')"
          :visible.sync="innerVisible"
          append-to-body>
          <el-form ref="deviceId" :model="innerForm">
            <el-form-item :label="$t('openatc.devicemanager.crowsid')" label-width="15%">
              <el-input v-model="innerForm.id" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div
            slot="footer"
            class="dialog-footer">
                <el-button @click="resetInnerForm('deviceId')">{{$t('openatc.button.Cancel')}}</el-button>
                <el-button type="primary" @click="submitDeviceId()">{{$t('openatc.button.OK')}}</el-button>
            </div>
        </el-dialog>
        <el-form
        class="dialog-footer"
        ref="device"
        :model="deviceInfo"
        :rules="rules"
        label-width="15%">
        <el-form-item
            :label="$t('openatc.devicemanager.crossid')"
            prop="agentid">
            <el-input
            type="text"
            v-model="deviceInfo.agentid"
            :disabled="!!deviceInfo.id"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
          :label="$t('openatc.devicemanager.tags')"
          prop="tags"
          >
          <el-tag
              :key="index"
              v-for="(tag,index) in deviceInfo.tags?deviceInfo.tags.split(','):currentTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model.trim="deviceTag"
              maxlength="10"
              ref="saveTagInput"
              size="small"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button :disabled="popovers" v-popover:popover class="button-new-tag" size="small"  @click="showInput">+ 新标签</el-button>
            <!-- 相对于button定位 button消失定位错乱 -->
          <el-popover
           ref="popover"
            placement="bottom"
            width="460"
            :popper-options="{boundariesElement:'body' }"
            v-model="popovers"
            trigger="click"
           >
            <el-tag
              :key="index"
              v-for="(select,index) in selectTags"
              :disable-transitions="false"
              :class="(deviceInfo.tags?deviceInfo.tags.split(',').includes(select):currentTags.includes(select)) ? 'tagSelected' : 'tagNoSelected'"
              @click="selectItem(select)"
              >
              {{select}}
            </el-tag>
          </el-popover>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.deviceid')"
            prop="thirdplatformid">
            <el-input
            type="text"
            v-model="deviceInfo.thirdplatformid"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.devicename')"
            prop="name">
            <el-input
            type="text"
            v-model="deviceInfo.name"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.type')"
            prop="type">
            <el-select v-model="deviceInfo.type" placeholder="" style="width:100%">
                <el-option label="asc" value="asc"></el-option>
                <el-option label="simu" value="simu"></el-option>
                <el-option label="v-atc" value="v-atc"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.protocol')"
            prop="protocol">
            <el-select v-model="deviceInfo.protocol" placeholder="" style="width:100%" @change="doChangeProtocol">
                <el-option label="scp" value="scp"></el-option>
                <el-option label="ocp" value="ocp"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.IP')"
            prop="ip">
            <el-input
            type="text"
            v-model="deviceInfo.ip"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.port')"
            prop="port" class="portLabel">
            <el-input
            type="text"
            v-model="deviceInfo.port"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.sockettype')"
            prop="sockettype">
            <el-select v-model="deviceInfo.sockettype" style="width:100%">
              <el-option
                v-for="item in sockettypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.platform')"
            prop="platform">
            <el-select v-model="deviceInfo.platform" placeholder="" style="width:100%" :disabled="platformCheck">
                <el-option v-for="firm in platformList" :key="firm.label" :label="firm.label" :value="firm.value"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.firm')"
            prop="firm">
            <el-select v-model="deviceInfo.firm" placeholder="" style="width:100%" clearable>
                <el-option v-for="firm in firmList" :key="firm.label" :label="firm.label" :value="firm.value"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.describe')"
            prop="descs">
            <el-input
            type="text"
            v-model="deviceInfo.descs"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <!-- <el-form-item
            :label="$t('openatc.devicemanager.longitude')"
            prop="lng">
            <el-input
            type="text"
            v-model="deviceInfo.lng"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item>
        <el-form-item
            :label="$t('openatc.devicemanager.latitude')"
            prop="lat">
            <el-input
            type="text"
            v-model="deviceInfo.lat"
            @keyup.enter.native="submitDeviceInfo('device')">
            </el-input>
        </el-form-item> -->
        </el-form>
        <div
        slot="footer"
        class="dialog-footer">
            <el-button @click="resetForm('device')">{{$t('openatc.button.Cancel')}}</el-button>
            <el-button type="primary" @click="submitDeviceInfo('device')">{{$t('openatc.button.OK')}}</el-button>
            <el-button type="primary" @click="innerVisible = true" class="change-deviceid" v-show="showModifyIdButton">{{$t('openatc.devicemanager.updateDeviceId')}}</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { AddDevice, UpdateDevice, UpdateDeviceId, getDict, addDict } from '@/api/device'
import { getMessageByCode } from '@/utils/responseMessage'
import { DictApi } from '@/api/dict.js'
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
  //     },
  //     // 深度观察监听
  //     deep: true
  //   }
  // },
  data () {
    // var checkIp = (rule, value, callback) => {
    //   if (value === '') {
    //     return callback(
    //       new Error(this.$t('openatc.devicemanager.enterIp'))
    //     )
    //   }
    //   const ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    //   if (ipReg.test(value)) {
    //     this.ip_status = true
    //     callback()
    //   } else {
    //     this.ip_status = false
    //     return callback(
    //       new Error(this.$t('openatc.devicemanager.correctIp'))
    //     )
    //   }
    // }
    var checkPort = (rule, value, callback) => {
      if (value === '') {
        return callback(
          new Error(this.$t('openatc.devicemanager.enterport'))
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
            new Error(this.$t('openatc.devicemanager.correctport'))
          )
        }
      }, 100)
    }
    return {
      currentTags: [],
      selectTags: [],
      inputVisible: false,
      popovers: false,
      deviceTag: '',
      // name: '',
      dialogFormVisible: false,
      innerVisible: false,
      ip_status: true,
      port_status: true,
      deviceInfo: {},
      tempDevice: {
        agentid: '',
        thirdplatformid: '',
        name: '',
        descs: '',
        tags: '',
        type: 'asc',
        protocol: 'ocp',
        ip: '',
        port: '8880',
        sockettype: 0,
        lng: 0,
        lat: 0,
        firm: '',
        platform: 'OpenATC'
      },
      rules: {
        type: [
          { required: true, message: this.$t('openatc.devicemanager.choosetype'), trigger: 'blur' }
        ],
        protocol: [
          { required: true, message: this.$t('openatc.devicemanager.chooseprotocol'), trigger: 'blur' }
        ],
        // ip: [
        //   { validator: checkIp, trigger: 'blur' }
        // ],
        port: [
          { validator: checkPort, trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: this.$t('openatc.devicemanager.enterId'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('openatc.devicemanager.entername'), trigger: 'blur' }
        ]
        // platform: [
        //   { required: true, message: this.$t('openatc.devicemanager.chooseplatform'), trigger: 'blur' }
        // ]
      },
      firmList: [{
        label: '科达',
        value: 'Kedacom'
      }, {
        label: '泰科',
        value: 'Tyco'
      }, {
        label: '华通',
        value: 'Huatong'
      }, {
        label: '海信',
        value: 'HAIXIN'
      }],
      platformList: [{
        label: 'OpenATC',
        value: 'OpenATC'
      }, {
        label: 'SCATS',
        value: 'SCATS'
      }, {
        label: 'HUATONG',
        value: 'HUATONG'
      }, {
        label: 'HiCon',
        value: 'HiCon'
      }],
      platformCheck: false,
      innerForm: {},
      showModifyIdButton: false,
      sockettypeOptions: [
        {
          label: 'UDP',
          value: 0
        }, {
          label: 'TCP',
          value: 1
        }
      ]
    }
  },
  created () {
    // this.getDicts()
    this.getAllPlatform()
  },
  methods: {
    getDicts () {
      getDict().then(res => {
        this.selectTags = Array.from(new Set(res.data.data.filter(it => it.value).map(it => it.value)))
      })
    },
    addDicts () {
      let resData = {
        type: 'tags',
        key: 'devTag',
        value: this.deviceTag
      }
      addDict(resData).then(res => {
      })
    },
    handleClose (tag) {
      if (this.deviceInfo.tags) {
        this.deviceInfo.tags = this.deviceInfo.tags.split(',').filter(item => { return item !== tag }).toString()
      } else {
        this.currentTags.splice(this.currentTags.indexOf(tag), 1)
      }
    },
    showInput () {
      this.getDicts()
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm () {
      if (!this.deviceTag) {
        this.inputVisible = false
        return false
      }
      if (this.deviceInfo.tags) {
        if (this.deviceTag && !this.deviceInfo.tags.includes(this.deviceTag)) {
          this.deviceInfo.tags = this.deviceInfo.tags + ',' + this.deviceTag
        }
        this.addDicts()
      } else {
        if (this.deviceTag && !this.currentTags.includes(this.deviceTag)) {
          this.currentTags.push(this.deviceTag)
        }
        this.addDicts()
      }
      this.deviceTag = ''
      this.inputVisible = false
    },
    selectItem (select) {
      if (this.deviceInfo.tags) {
        if (this.deviceInfo.tags.split(',').includes(select)) {
          this.deviceInfo.tags = this.deviceInfo.tags.split(',').filter(item => { return item !== select }).toString()
        } else {
          this.deviceInfo.tags = this.deviceInfo.tags + ',' + select
        }
      } else {
        if (this.currentTags.includes(select)) {
          this.currentTags.splice(this.currentTags.indexOf(select), 1)
        } else {
          this.currentTags.push(select)
        }
      }
    },
    getName () {
      let name = ''
      if (this.deviceInfo.tags) {
        for (let i = 0; i < this.deviceInfo.tags.split(',').length; i++) {
          name = name + ',' + this.deviceInfo.tags.split(',')[i]
        }
      } else {
        for (let i = 0; i < this.currentTags.length; i++) {
          name = name + ',' + this.currentTags[i]
        }
      }
      if (name !== '') {
        return name.substr(1)
      } else {
        return ''
      }
    },
    submitDeviceInfo (formName) {
      // 提交表单
      // if (this.deviceInfo.ip === '' || this.deviceInfo.port === '') {
      //   this.$message.error(this.$t('openatc.devicemanager.enterrequired'))
      //   return
      // }
      // if (!this.ip_status) {
      //   this.$message.error(this.$t('openatc.devicemanager.correctIp'))
      //   return
      // }
      if (!this.port_status) {
        this.$message.error(this.$t('openatc.devicemanager.correctport'))
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
          this.$message.error(this.$t('openatc.devicemanager.enterrequired'))
          return false
        }
      })
    },
    submitDeviceId () {
      if (this.innerForm.id === '') {
        this.$message.error(this.$t('openatc.devicemanager.deviceNotNull'))
        return
      }
      if (this.innerForm.id === this.deviceInfo.agentid) {
        this.$message.error(this.$t('openatc.devicemanager.oldIdNotConsistentNewId'))
        return
      }
      this.updateDeviceId()
    },
    updateDeviceId () {
      let _vue = this
      let data = {
        oldAgentid: _vue.deviceInfo.agentid,
        newAgentid: _vue.innerForm.id
      }
      UpdateDeviceId(data).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.innerVisible = false
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.updatesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getDeviceRanges()
          }
        })
      })
    },
    addDevice () {
      let _vue = this
      let geometry = {
        type: 'Point',
        coordinates: [this.deviceInfo.lng, this.deviceInfo.lat]
      }
      let devInfo = this.deviceInfo
      devInfo.tags = this.getName()
      this.currentTags = []
      devInfo.geometry = geometry
      delete devInfo.lng
      delete devInfo.lat
      AddDevice(devInfo).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.addsuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getDeviceRanges()
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
      devInfo.tags = this.getName()
      devInfo.geometry = geometry
      delete devInfo.lng
      delete devInfo.lat
      UpdateDevice(devInfo).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          // this.$message({
          //   message: this.$t('openatc.common.updatefailed'),
          //   type: 'error',
          //   duration: 1 * 1000
          // })
          return
        }
        this.dialogFormVisible = false
        this.$message({
          message: this.$t('openatc.common.updatesuccess'),
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            _vue.$parent.getDeviceRanges()
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
        this.currentTags = []
        this.deviceInfo = JSON.parse(JSON.stringify(this.tempDevice))
        this.showModifyIdButton = false
        return
      }
      this.showModifyIdButton = true
      if (dev.protocol === 'ocp') {
        this.platformCheck = true
        dev.platform = 'OpenATC'
      } else {
        this.platformCheck = false
        // dev.platform = ''
      }
      // 编辑
      const device = JSON.parse(JSON.stringify(dev))
      delete device.lastTime
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
      this.innerForm = {
        id: dev.agentid
      }
    },
    resetForm (formData) {
      // 表单重置
      this.dialogFormVisible = false
      this.$refs[formData].resetFields()
    },
    resetInnerForm (formData) {
      // 内层表单重置
      this.innerVisible = false
      this.$refs[formData].resetFields()
      this.innerForm = {
        id: this.deviceInfo.agentid
      }
    },
    doChangeProtocol (val) {
      if (val === 'ocp') {
        this.platformCheck = true
        this.deviceInfo.platform = 'OpenATC'
      } else {
        this.platformCheck = false
        this.deviceInfo.platform = ''
      }
    },
    getAllPlatform () {
      let tag = 'platform'
      DictApi.getDictListByTag(tag).then((data) => {
        let res = data.data
        if (!res.success) {
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return false
        }
        let list = data.data.data
        this.platformList = []
        let record = []
        for (let item of list) {
          record = {
            label: item.key,
            value: item.key
          }
          this.platformList.push(record)
        }
      })
    }
  }
}
</script>
<style lang="scss" rel="stylesheet/scss">
.popper__arrow {
  display: none !important;
}
</style>
<style lang="scss" scoped rel="stylesheet/scss">
.dev-update .el-dialog__body {
  padding: 30px 72px 30px 0;
}
.el-tag + .el-tag {
  margin: 4px 4px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.tagNoSelected{
  border-radius: 3%;
  float: left;
  margin: 1%;
  cursor: pointer;
}
.tagSelected{
  background-color: #a2cfff;
  border-radius: 3%;
  float: left;
  margin: 1%;
  cursor: pointer;
}
.el-dialog__footer {
  padding: 10px 72px 38px 0;
}
// 自定义校验规则的labal加上*号
.dev-update .ipLabel .el-form-item__label:before,  .dev-update .portLabel .el-form-item__label:before, .dev-update .portLabel .el-form-item__label:before{
    content: '*';
    color: #f56c6c;
    margin-right: 4px;
}
</style>
