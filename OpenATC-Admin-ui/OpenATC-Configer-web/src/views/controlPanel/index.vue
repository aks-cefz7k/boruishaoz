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
<div class="manual-record">
  <div class="manual-top">
    <div class="control-model">
      <div class="control-model-name">{{$t('edge.controlpanel.controlmode')}}：</div><div class="control-model-value">{{$t('edge.controlpanel.manualpanel' + manualpanel.controlmode)}}</div>
      <div class="control-model-name">{{$t('edge.controlpanel.duration')}}：</div><div class="control-model-input"><el-input-number :min="0" :max="65535" :controls="false" v-model="manualpanel.duration" placeholder="" size="small"></el-input-number></div>
      <div class="control-model-name">{{$t('edge.controlpanel.transitiongreenflashtime')}}：</div><div class="control-model-input"><el-input-number :min="0" :controls="false" :max="65535" v-model="manualpanel.greenflash" placeholder="" size="small"></el-input-number></div>
      <div class="control-model-name">{{$t('edge.controlpanel.transitionyellowtime')}}：</div><div class="control-model-input"><el-input-number :min="0" :controls="false" :max="65535" v-model="manualpanel.yellow" placeholder="" size="small"></el-input-number></div>
      <div class="control-model-name">{{$t('edge.controlpanel.transitionredtime')}}：</div><div class="control-model-input"><el-input-number :min="0" :controls="false" :max="65535" v-model="manualpanel.redclear" placeholder="" size="small"></el-input-number></div>
      <div class="control-model-name">{{$t('edge.controlpanel.mingreen')}}：</div><div class="control-model-input"><el-input-number :min="0" :max="65535" :controls="false" v-model="manualpanel.mingreen" placeholder="" size="small"></el-input-number></div>
    </div>
  </div>
  <div class="manual-bottom" style="display: inline-block">
    <div class="manual-control" style="display: inline-block">
      <div class="manualControl-kanban">
        <svg-icon :icon-class="$t('edge.controlpanel.manualcontrolbackground')" class="manualControl-background"></svg-icon>
        <div class="manual-button" style="left: 201px; top: 150px;"><span class="button-name">{{$t('edge.controlpanel.step')}}</span></div>
        <div class="manual-button" style="left: 393px; top: 150px;"><span class="button-name">{{$t('edge.controlpanel.allred')}}</span></div>
        <div class="manual-button" style="left: 585px; top: 150px;"><span class="button-name">{{$t('edge.controlpanel.yellowflash')}}</span></div>
        <div  v-for="(item, index)  in manualBtnList" :key="index">
          <div :class="item.id > 8 ? (item.id === currChannelId ? 'select-rectangle-button' : 'rectangle-button') : (item.id === currChannelId ? 'select-circle-button' : 'circle-button')" :style="item.style" @click ="manualBtnClicked(item)">
            <!-- <span :class="item.id > 8 ? (item.id === currChannelId ? 'select-rectangle-name' : 'rectangle-name') : (item.id === currChannelId ? 'select-cycle-name' : 'cycle-name')" :style="item.btnNameList > 4 ? 'width: 52px; left: 15px; font-size: 10px; line-height: 15px;' : ''">{{getBtnName(item.name)}}</span> -->
            <span v-if="item.id < 9" :class="item.id === currChannelId ? 'select-cycle-name' : 'cycle-name'" :style="item.btnNameList > 4 ? 'width: 52px; left: 15px; font-size: 10px; line-height: 15px;' : ''">{{getBtnName(item.name)}}</span>
            <span v-else :class="item.id === currChannelId ? 'select-rectangle-name' : 'rectangle-name'" :style="item.btnNameList > 4 ? 'top: 5px;width: 70px; left: 10px; font-size: 10px;' : ''">{{getBtnName(item.name)}}</span>
          </div>
        </div>
      </div>
      <div class="manual-control-button">
        <el-button type="primary" @click="resetDefaultManualpanel">{{$t('edge.controlpanel.defaultsettings')}}</el-button>
        <el-button type="primary" @click="allclear">{{$t('edge.controlpanel.allclear')}}</el-button>
        <el-button type="primary" @click="execute" style="margin-left: 50px;">{{$t('edge.controlpanel.execute')}}</el-button>
      </div>
    </div>
    <div class="manual-tables" style="display: inline-block">
      <div class="manual-tables-top">
        <div class="manual-tables-cycle"></div>
        <div v-show="currChannelId !== -1">
          <div  v-show="!editStatus"><div class="manual-tables-name">{{currChannelName}}</div><div class="manual-tables-edit" @click="edit"><i class="el-icon-edit"></i></div></div>
          <div class="manual-tables-name" v-show="editStatus"><el-input v-model="currChannelName" :placeholder="$t('edge.common.entercontent')" size="mini" @blur="editPanelName"></el-input></div>
          <div class="manual-tables-clear"><i class="el-icon-delete"></i><span class="manual-tables-qingkong" @click="clearChannel">{{$t('edge.controlpanel.clear')}}</span><svg-icon icon-class="huangshan" style="margin-left: 20px;"></svg-icon><span class="manual-tables-qingkong" @click="reset">{{$t('edge.controlpanel.resetstatus')}}</span></div>
        </div>
      </div>
      <div class="manual-tables-bottom">
        <el-table
          :data="manualList"
          size="small"
          :max-height="tableHeight"
          v-loading.body="listLoading"
          style="width: 90%"
          id="footerBtn">
          <el-table-column
          type="index"
          align="center">
          </el-table-column>
          <el-table-column
          prop="channelid"
          :label="$t('edge.controlpanel.channel')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="desc"
          :label="$t('edge.controlpanel.channeldesc')"
          align="center">
          </el-table-column>
          <el-table-column
          :label="$t('edge.controlpanel.status')"
          align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.channelstatus" :placeholder="$t('edge.common.select')" size="small">
              <el-option
                v-for="item in channelstatusList"
                :key="item.value"
                :label="$t('edge.controlpanel.channelstatus' + item.value)"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </div>
  </div>
</div>
</template>

<script>
import { mapState } from 'vuex'
import { getManualpanel, getChannel, getDefaultManualpanel } from '@/api/manual'
import { getMessageByCode } from '@/utils/responseMessage'
import { putTscControl } from '@/api/control'
export default {
  name: 'manualcontrol',
  components: {},
  data () {
    return {
      tableHeight: 600,
      listLoading: false, // 数据加载等待动画
      manualList: [],
      channelstatusList: [{
        label: '红灯',
        value: 1
      }, {
        label: '绿灯',
        value: 3
      }, {
        label: '灭灯',
        value: 4
      }],
      manualBtnList: [{
        id: 1,
        desc: this.$t('edge.controlpanel.manualbtn1'), // desc为默认显示的名称，不可修改
        name: this.$t('edge.controlpanel.manualbtn1'), // name为绑定数据，可修改
        btnNameList: 4,
        style: 'left: 173px; top: 243px;'
      }, {
        id: 2,
        desc: this.$t('edge.controlpanel.manualbtn2'),
        name: this.$t('edge.controlpanel.manualbtn2'),
        btnNameList: 4,
        style: 'left: 403px; top: 243px;'
      }, {
        id: 3,
        desc: this.$t('edge.controlpanel.manualbtn3'),
        name: this.$t('edge.controlpanel.manualbtn3'),
        btnNameList: 4,
        style: 'left: 632px; top: 243px;'
      }, {
        id: 4,
        desc: this.$t('edge.controlpanel.manualbtn4'),
        name: this.$t('edge.controlpanel.manualbtn4'),
        btnNameList: 4,
        style: 'left: 300px; top: 346px;'
      }, {
        id: 5,
        desc: this.$t('edge.controlpanel.manualbtn5'),
        name: this.$t('edge.controlpanel.manualbtn5'),
        btnNameList: 4,
        style: 'left: 506px; top: 346px;'
      }, {
        id: 6,
        desc: this.$t('edge.controlpanel.manualbtn6'),
        name: this.$t('edge.controlpanel.manualbtn6'),
        btnNameList: 4,
        style: 'left: 173px; top: 449px;'
      }, {
        id: 7,
        desc: this.$t('edge.controlpanel.manualbtn7'),
        name: this.$t('edge.controlpanel.manualbtn7'),
        btnNameList: 4,
        style: 'left: 403px; top: 449px;'
      }, {
        id: 8,
        desc: this.$t('edge.controlpanel.manualbtn8'),
        name: this.$t('edge.controlpanel.manualbtn8'),
        btnNameList: 4,
        style: 'left: 632px; top: 449px;'
      }, {
        id: 9,
        desc: 'Y1',
        name: 'Y1',
        btnNameList: 2,
        style: 'left: 167px; top: 562px;'
      }, {
        id: 10,
        desc: 'Y2',
        name: 'Y2',
        btnNameList: 2,
        style: 'left: 320px; top: 562px;'
      }, {
        id: 11,
        desc: 'Y3',
        name: 'Y3',
        btnNameList: 2,
        style: 'left: 473px; top: 562px;'
      }, {
        id: 12,
        desc: 'Y4',
        name: 'Y4',
        btnNameList: 2,
        style: 'left: 626px; top: 562px;'
      }],
      currChannelName: '',
      currChannelId: -1,
      editStatus: false, // 通道名称的编辑状态
      // controlMode: '',
      channelList: []
    }
  },
  computed: {
    ...mapState({
      manualpanel: state => state.globalParam.tscParam.manualpanel,
      channelDescMap: state => state.globalParam.channelDescMap
    })
  },
  watch: {
    manualpanel: function (val) {
      this.createManualList()
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.initManualPanel()
    this.getChannelList() // 获取手动控制面板所有信息
    // this.getAllManualpanel()
  },
  methods: {
    initManualPanel () {
      this.currChannelId = 1 // 默认东西直行方向
      this.createManualList()
    },
    createManualList () {
      // 生成当前的通道状态列表
      this.manualList = []
      this.contactDescAndBtn()
      let manualval = this.manualBtnList.filter(ele => ele.id === this.currChannelId)
      if (manualval.length > 0) {
        this.handleManualList(manualval[0])
        this.renderCurrManualDesc(manualval[0])
      }
    },
    getChannelList () {
      getChannel().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.channelList = res.data.data.channelList
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    getAllManualpanel () {
      getManualpanel().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        console.log(res)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    manualBtnClicked (val) {
      if (this.currChannelId === val.id) {
        this.currChannelName = ''
        this.currChannelId = -1
        this.manualList = []
      } else {
        this.handleManualList(val)
      }
    },
    contactDescAndBtn () {
      // 关联全局数据manualpanel的desc和按钮组数据manualBtnList的name
      let keyconfig = this.manualpanel.keyconfig
      if (!keyconfig) return
      for (let item of keyconfig) {
        // 上载后desc为空，则显示按钮组的默认描述
        if (item.desc === '') {
          // 此处取manualBtnList的desc，不可取name，name是变化的
          let desc = this.manualBtnList.filter(ele => ele.id === item.keynum)[0].desc
          item.desc = desc
        }
      }
      for (let btnitem of this.manualBtnList) {
        // 按钮name按照上载的描述更新
        let desc = keyconfig.filter(ele => ele.keynum === btnitem.id)[0].desc
        btnitem.name = desc
      }
    },
    renderCurrManualDesc (val) {
      // 获取当前名称
      if (!this.manualpanel.keyconfig || !this.manualpanel.keyconfig.length) {
        this.currChannelName = val.name
      } else {
        let desc = this.manualpanel.keyconfig.filter(ele => ele.keynum === this.currChannelId)[0].desc
        this.currChannelName = desc
      }
    },
    handleManualList (val) {
      this.currChannelName = val.name
      this.currChannelId = val.id
      let keyconfig = this.manualpanel.keyconfig
      if (keyconfig === undefined) return
      let currKeyconfig = keyconfig.filter((item) => {
        return item.keynum === val.id
      })
      if (currKeyconfig.length === 0) {
        this.manualList = []
        return
      }
      this.manualList = currKeyconfig[0].channel
      for (let manual of this.manualList) {
        manual.desc = this.channelDescMap.get(manual.channelid)
      }
    },
    edit () {
      this.editStatus = true
      // this.controlMode = '自定义'
      this.manualpanel.controlmode = 1
    },
    reset () {
      this.$confirm(this.$t('edge.controlpanel.changered'), this.$t('edge.controlpanel.tips'), {
        confirmButtonText: this.$t('edge.button.OK'),
        cancelButtonText: this.$t('edge.button.Cancel'),
        type: 'warning',
        center: true
      }).then(() => {
        this.manualList.map(item => {
          item.channelstatus = 1
        })
        let keyconfig = this.manualpanel.keyconfig
        for (let config of keyconfig) {
          if (config.keynum === this.currChannelId) {
            let channelList = config.channel
            channelList.map(item => {
              item.channelstatus = 1
            })
          }
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.controlpanel.cancelinit')
        })
      })
    },
    clearChannel () {
      this.$confirm(this.$t('edge.controlpanel.iscleardata'), this.$t('edge.controlpanel.tips'), {
        confirmButtonText: this.$t('edge.button.OK'),
        cancelButtonText: this.$t('edge.button.Cancel'),
        type: 'warning',
        center: true
      }).then(() => {
        let keyconfig = this.manualpanel.keyconfig
        for (let config of keyconfig) {
          if (config.keynum === this.currChannelId) {
            this.manualList = []
            config.channel = []
          }
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.controlpanel.cancelclear')
        })
      })
    },
    resetDefaultManualpanel () {
      let param = {}
      let phaseList = this.globalParamModel.getParamsByType('phaseList')
      let channel = this.globalParamModel.getParamsByType('channelList')
      if (phaseList.length === 0 || channel.length === 0) {
        this.$message.error(this.$t('edge.controlpanel.phaseanddetectornotnull'))
        return
      }
      param.phaseList = phaseList
      param.channelList = channel
      getDefaultManualpanel(param).then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.$alert(this.$t('edge.controlpanel.getdefaultmanualpaneltips'), { type: 'success' })
        this.$store.getters.tscParam.manualpanel = res.data
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    editPanelName () {
      let currChannelId = this.currChannelId
      for (let manualBtn of this.manualBtnList) {
        if (manualBtn.id === currChannelId) {
          manualBtn.name = this.currChannelName
          let str = this.currChannelName
          let strLength = [...str].length
          manualBtn.btnNameList = strLength
        }
      }
      this.editStatus = false
      if (!this.manualpanel.keyconfig) return
      for (let item of this.manualpanel.keyconfig) {
        // 修改名称后，同时改变全局参数中的对应描述
        if (item.keynum === currChannelId) {
          item.desc = this.currChannelName
        }
      }
    },
    getBtnName (name) {
      let nameLength = [...name].length
      if (nameLength < 11) {
        return name
      } else {
        return name.substring(0, 10) + '...'
      }
    },
    allclear () {
      this.$store.dispatch('ClearManualPanel')
    },
    execute () {
      if (this.currChannelId === -1 || this.manualpanel.keyconfig === undefined) {
        this.$message.error(this.$t('edge.controlpanel.lockconfigempty'))
        return
      }
      let param = {
        channellocKinfo: []
      }
      if (this.manualpanel.duration) {
        param.duration = this.manualpanel.duration
      }
      if (this.manualpanel.greenflash) {
        param.greenflash = this.manualpanel.greenflash
      }
      if (this.manualpanel.yellow) {
        param.yellow = this.manualpanel.yellow
      }
      if (this.manualpanel.redclear) {
        param.redclear = this.manualpanel.redclear
      }
      if (this.manualpanel.mingreen) {
        param.mingreen = this.manualpanel.mingreen
      }
      if (this.manualpanel.keyconfig && this.manualpanel.keyconfig.length > 0) {
        let currDirChannellockInfo = this.manualpanel.keyconfig.filter(ele => ele.keynum === this.currChannelId)[0]
        if (currDirChannellockInfo) {
          let channellocKinfo = currDirChannellockInfo.channel.map(data => {
            return {
              channelid: data.channelid,
              channelstatus: data.channelstatus
            }
          })
          param.channellocKinfo = channellocKinfo
        }
      }
      this.handleChannelLock(param)
    },
    handleChannelLock (param) {
      let control = {}
      control.control = 16
      control.data = param
      putTscControl(control).then(data => {
        let success = 0
        if (!data.data.success) {
          if (data.data.code === '4002') { // 错误应答
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              this.$message.error(getMessageByCode(data.data.data.errorCode, this.$i18n.locale))
            }
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        if (data.data.data && data.data.data.data) {
          success = data.data.data.data.success
          if (success !== 0) {
            let errormsg = 'edge.overview.putTscControlError' + success
            this.$message.error(this.$t(errormsg))
            return
          }
        }
        this.$message.success(this.$t('edge.common.download'))
      }).catch(error => {
        this.$message.error(error)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.manual-record {
  position: relative;
  width: 100%;
  height: 100%;
  min-width: 1700px;
}
.manual-bottom {
  width: 100%;
}
.manual-control {
  // float: left;
  width: 50%;
}
.manual-tables {
  float: right;
  width: 50%;
}
.control-model {
  float: left;
  max-height: 50px;
}
// .control-model-name {
//   float: left;
//   margin-left: 30px;
//   margin-top: 30px;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   color: #606266;
// }
// .control-model-value {
//   float: left;
//   margin-left: 5px;
//   margin-top: 30px;
//   font-size: 14px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 14px;
//   letter-spacing: 0px;
//   color: #303133;
// }
.control-model-input {
  float: left;
  margin-left: 5px;
  margin-top: 22px;
}
.manualControl-background {
  position: relative;
  width: 848px;
  height: 600px;
  margin-left: 30px;
  margin-top: 30px;
  // background-color: #eff7ff;
}
.manualControl-kanban {
  width: 100%;
  height: 85%;
}
.manual-button {
  position: absolute;
  width: 101px;
  height: 65px;
  background-color: rgba(48, 49, 51, 0.3);
  border-radius: 6px;
  z-index: 9;
}
.circle-button {
  position: absolute;
  width: 81px;
  height: 80px;
  background-color: #303133;
  border-radius: 60px;
  cursor:pointer;
  z-index: 9;
}
.select-circle-button {
  position: absolute;
  width: 81px;
  height: 80px;
  background-color: #303133;
  box-shadow: 0px 3px 7px 0px rgba(5, 27, 49, 0.35);
  border: solid 4px #409eff;
  border-radius: 60px;
  cursor:pointer;
  z-index: 9;
}
.rectangle-button {
  position: absolute;
  width: 93px;
  height: 45px;
  background-color: #303133;
  border-radius: 6px;
  cursor:pointer;
  z-index: 9;
}
.select-rectangle-button {
  position: absolute;
  width: 93px;
  height: 45px;
  background-color: #303133;
  box-shadow: 0px 3px 7px 0px rgba(5, 27, 49, 0.35);
  border: solid 4px #409eff;
  border-radius: 6px;
  cursor:pointer;
  z-index: 9;
}
.button-name {
  position: absolute;
  top: 24px;
  left: 32px;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #ffffff;
}
.cycle-name {
  position: absolute;
  top: 20px;
  left: 24px;
  width: 32px;
  height: 36px;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #ffffff;
}
.select-cycle-name {
  position: absolute;
  top: 16px;
  left: 20px;
  width: 32px;
  height: 36px;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #ffffff;
}
.rectangle-name {
  position: absolute;
  top: 15px;
  left: 35px;
  width: 18px;
  height: 12px;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #ffffff;
}
.select-rectangle-name {
  position: absolute;
  top: 11px;
  left: 31px;
  width: 18px;
  height: 12px;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #ffffff;
}
.manual-control-button {
  width: 848px;
  margin-top: 20px;
  text-align: center;
}
.manual-tables-cycle {
  float: left;
  margin-left: 40px;
  margin-top: 30px;
  border-radius: 60px;
  width: 8px;
  height: 8px;
  background-color: #409eff;
}
// .manual-tables-name {
//   float: left;
//   margin-left: 10px;
//   margin-top: 22px;
//   font-size: 18px;
//   font-weight: normal;
//   font-stretch: normal;
//   line-height: 20px;
//   letter-spacing: 0px;
//   color: #303133;
// }
.manual-tables-edit {
  float: left;
  margin-top: 25px;
  margin-left: 3px;
  width: 14px;
  height: 16px;
  color: #409eff;
  cursor:pointer;
  font-size: 16px;
}
.manual-tables-clear {
  position: absolute;
  right: 15px;
  margin-top: 25px;
  width: 200px;
  height: 20px;
  color: #409eff;
  font-size: 16px;
}
.manual-tables-qingkong {
  margin-left: 5px;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  cursor: pointer;
}
.manual-tables-bottom {
  // width: 800px;
  // min-width: 700px;
  width: 100%;
  float: left;
  margin-left: 40px;
  margin-top: 30px;
}
// /*当屏幕小于等于1680px的屏幕样式*/
// @media only screen and (max-width: 1680px){
//   .manual-control{
//         transform: scale(0.9);
//         transform-origin: left top;
//     }
//   .manual-tables {
//     float: left;
//     width: 50%;
//   }
//   .manual-tables-bottom {
//     width: 100%;
//     float: left;
//     margin-left: 40px;
//     margin-top: 30px;
//   }
//  }
// /*当屏幕小于等于1440px的屏幕样式*/
// @media only screen and (max-width: 1440px){
//   .manual-control{
//         transform: scale(0.8);
//         transform-origin: left top;
//     }
//   .manual-tables {
//     float: left;
//     width: 50%;
//   }
//   .manual-tables-bottom {
//     width: 100%;
//     float: left;
//     margin-left: 50px;
//     margin-top: 30px;
//   }
//  }
//  /*当屏幕小于等于1280px的屏幕样式*/
// @media only screen and (max-width: 1280px){
//   .manual-control{
//         transform: scale(0.7);
//         transform-origin: left top;
//     }
//   .manual-tables {
//     float: left;
//     width: 50%;
//   }
//   .manual-tables-bottom {
//     width: 100%;
//     float: left;
//     margin-left: 50px;
//     margin-top: 30px;
//   }
//  }
</style>
<style rel="stylesheet/scss" lang="scss">
// .control-model .el-input__inner {
//   -webkit-appearance: none;
//   background-color: #fff;
//   background-image: none;
//   border-radius: 4px;
//   border: 1px solid #dcdfe6;
//   -webkit-box-sizing: border-box;
//   box-sizing: border-box;
//   color: #606266;
//   display: inline-block;
//   font-size: inherit;
//   height: 34px;
//   line-height: 40px;
//   outline: 0;
//   padding: 0 15px;
//   -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
//   transition: border-color .2s cubic-bezier(.645,.045,.355,1);
//   width: 90px;
// }
</style>
