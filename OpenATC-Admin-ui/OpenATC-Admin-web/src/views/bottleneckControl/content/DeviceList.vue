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
  <div class="overflowDeviceList">
    <el-table :data="deviceList" height="100%" style="width: 100%">
      <el-table-column prop="intersectionname" :label="$t('openatc.bottleneckcontrol.devicename')" width="90" align="center"></el-table-column>
      <el-table-column prop="phaseid" :label="$t('openatc.bottleneckcontrol.phaseid')" width="250" align="center">
        <template slot-scope="scope">
          <el-select
            v-if="isModify"
            v-model="scope.row.phasedesc"
            size="small"
            @focus="getCurPhase(scope.row.intersectionid, $event)"
            @change="changeCurPhase(scope.row, $event)"
          >
            <el-option
              v-for="(option, index) in scope.row.phaseoptions"
              :key="index"
              :label="option.phasedesc"
              :value="option.phaseid"
            >
              <span>{{ option.phasedesc }}</span>
            </el-option>
          </el-select>

          <span v-if="!isModify">{{descMap.get(scope.row.id)}}</span>
        </template>
      </el-table-column>

      <el-table-column prop="level" :label="$t('openatc.bottleneckcontrol.controllevel')" align="center">
        <template slot-scope="scope">
          <el-select v-if="isModify" v-model="scope.row.level" size="small">
            <el-option
              v-for="(option, index) in controlLevel"
              :key="index"
              :label="option.label"
              :value="option.value"
            >
              <span>{{ option.label }}</span>
            </el-option>
          </el-select>
          <span v-if="!isModify">{{controlLevelMap.get(scope.row.level)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="updowndirection" :label="$t('openatc.bottleneckcontrol.controltype')" align="center">
        <template slot-scope="scope">
          <el-select v-if="isModify" v-model="scope.row.updowndirection" size="small">
            <el-option
              v-for="(option, index) in controlType"
              :key="index"
              :label="option.label"
              :value="option.value"
            >
              <span>{{ option.label }}</span>
            </el-option>
          </el-select>
          <span v-if="!isModify">{{controlTypeMap.get(scope.row.updowndirection)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="controltime" width="200" :label="$t('openatc.bottleneckcontrol.controltime')" align="center">
        <template slot-scope="scope">
          <el-input-number
            v-if="isModify"
            size="small"
            controls-position="right"
            :min="0"
            v-model.number="scope.row.controltime"
          ></el-input-number>
          <span v-if="!isModify">{{scope.row.controltime}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" :formatter="formatState" :label="$t('openatc.bottleneckcontrol.executeresults')" v-if="!isModify" align="center"></el-table-column>
      <el-table-column :label="$t('openatc.bottleneckcontrol.executerestatus')" v-if="!isModify" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.statedata !== undefined">
            <el-tooltip v-if="scope.row.statedata.control !== 20" class="item" effect="dark"
              :content="scope.row.statedata.control > -1 ? $t('edge.overview.modelList' + scope.row.statedata.control) : $t('openatc.bottleneckcontrol.unknow')" placement="bottom">
              <el-tag size="medium" effect="plain" :type="formatCode(scope.row.statedata.control).type">{{ formatCode(scope.row.statedata.control).label }}</el-tag>
            </el-tooltip>
            <el-tag v-if="scope.row.statedata.control === 20" size="medium" effect="plain" :type="formatCode(scope.row.statedata.control).type">{{ formatCode(scope.row.statedata.control).label }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="isModify" :label="$t('openatc.common.operation')" header-align="center" width="90" align="center">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="handleDelete(scope.row.intersectionid)"
            type="text"
            :disabled="!isModify"
            size="small"
          >{{$t('openatc.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="btngroup-footer" v-show="isModify">
      <el-button
        type="primary"
        @click="onSubmit"
        size="small"
        style="margin-left: 10px;"
      >{{$t('openatc.button.submit')}}</el-button>
      <el-button type="primary" @click="onAdd" size="small">{{$t('openatc.common.add')}}</el-button>
    </div>
    <el-drawer
      ref="addDrawer"
      :title="$t('openatc.bottleneckcontrol.choosedev')"
      custom-class="overflowDeviceDrawer"
      destroy-on-close
      :wrapperClosable="false"
      :append-to-body="true"
      :before-close="handleClose"
      :visible.sync="drawerVisible"
    >
      <DeviceAdd
        :choosedDevice="deviceList"
        @closeAddDrawer="closeAddDrawer"
        @addMultiDevice="addMultiDevice"
      />
    </el-drawer>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import DeviceAdd from './deviceAdd'
import { images } from '@/utils/phaseList.js'
import { getTscPhase } from '@/api/route'
import { OverflowDecApi } from '@/api/overflowDetector.js'
import { getMessageByCode } from '@/utils/responseMessage'
const { mapState } = createNamespacedHelpers('kissStore')

export default {
  name: 'deviceList',
  components: {
    DeviceAdd
  },
  props: {
    curDetectorDevs: {
      type: Object
    },
    isModify: {
      type: Boolean
    }
  },
  watch: {
    deviceList: {
      handler: function (val) {
        val.forEach(data => {
          this.descMap.set(data.id, data.phasedesc)
        })
      },
      deep: true
    },
    'curDetectorDevs.overflows': {
      handler: function (devs) {
        if (!devs) return
        if (this.isModify) return
        this.refreshData()
      },
      deep: true,
      immediate: true
    }
  },
  data () {
    return {
      deviceList: [],
      descMap: new Map(),
      drawerVisible: false,
      controlLevelMap: new Map([[1, this.$t('openatc.bottleneckcontrol.one-level')], [2, this.$t('openatc.bottleneckcontrol.two-level')], [3, this.$t('openatc.bottleneckcontrol.three-level')]]),
      controlLevel: [
        {
          label: this.$t('openatc.bottleneckcontrol.one-level'),
          value: 1
        },
        {
          label: this.$t('openatc.bottleneckcontrol.two-level'),
          value: 2
        },
        {
          label: this.$t('openatc.bottleneckcontrol.three-level'),
          value: 3
        }
      ],
      controlType: [
        {
          label: this.$t('openatc.bottleneckcontrol.greenextension'),
          value: '1'
        },
        {
          label: this.$t('openatc.bottleneckcontrol.greenreduction'),
          value: '2'
        }
      ],
      controlTypeMap: new Map([['1', this.$t('openatc.bottleneckcontrol.greenextension')], ['2', this.$t('openatc.bottleneckcontrol.greenreduction')]]),
      statusMap: new Map([['0', this.$t('openatc.bottleneckcontrol.controlfailed')],
        ['1', this.$t('openatc.bottleneckcontrol.controlsuccess')],
        ['2', this.$t('openatc.bottleneckcontrol.recoveryfailed')],
        ['3', this.$t('openatc.bottleneckcontrol.recoverysuccess')]])
    }
  },
  computed: {
    ...mapState({
      kissModel: state => state.KissModelObject
    })
  },
  methods: {
    changeCurPhase (row, curPhaseid) {
      this.deviceList.forEach(dev => {
        if (dev.id === row.id) {
          dev.phaseid = String(curPhaseid)
          dev.description = dev.phaseoptions.filter(phase => phase.phaseid === curPhaseid)[0].phasedirection
        }
      })
    },
    isHasExecutingCross () {
      // 判断是否有正在执行的路口
      let isHasExecute = false
      let areaOverflows = JSON.parse(JSON.stringify(this.curDetectorDevs.overflows))
      if (areaOverflows && areaOverflows.length) {
        areaOverflows.forEach(cross => {
          if (cross.statedata && cross.statedata.state === 1) {
            isHasExecute = true
          }
        })
      }
      return isHasExecute
    },
    onSubmit () {
      if (this.isHasExecutingCross()) {
        this.$message.error(this.$t('openatc.bottleneckcontrol.hasexecutecross'))
        return
      }
      if (this.checkRules(this.deviceList)) {
        // 检验方案和相位是否为空
        return
      }
      const submitData = this.deviceList.map(ele => ({
        patternid: this.curDetectorDevs.id,
        isopen: ele.isopen || false,
        intersectionid: ele.intersectionid,
        phaseid: ele.phaseid,
        description: ele.phaseoptions.filter(el => el.phaseid === ele.phaseid)
          .length
          ? ele.phaseoptions.filter(el => el.phaseid === ele.phaseid)[0]
            .phasedirection
          : ele.description,
        updowndirection: ele.updowndirection,
        controltime: ele.controltime,
        level: ele.level
      }))
      let patternid = this.curDetectorDevs.id
      OverflowDecApi.UpdateOverflows(patternid, submitData)
        .then(data => {
          if (!data.data.success) {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          this.$message({
            type: 'success',
            message: this.$t('openatc.common.submitsuccess')
          })
          this.$emit('refresh')
        })
        .catch(error => {
          this.$message.error(error)
          console.log(error)
        })
    },
    onAdd () {
      this.drawerVisible = true
    },
    handleDelete (intersectionid) {
      this.deviceList = this.deviceList.filter(
        ele => ele.intersectionid !== intersectionid
      )
    },
    handleClose (done) {
      done()
    },
    addMultiDevice (multiDevs) {
      let addDevs = multiDevs.map(ele => ({
        ...ele,
        intersectionid: ele.agentid,
        intersectionname: ele.name,
        phaseoptions: []
      }))
      this.deviceList = this.deviceList.concat(addDevs)
      this.drawerVisible = false
    },
    closeAddDrawer () {
      this.$refs.addDrawer.closeDrawer()
    },
    getCurPhase (intersectionid) {
      if (!intersectionid) return
      // 获取相位选项
      this.loading = true
      getTscPhase(intersectionid).then(res => {
        this.loading = false
        if (!res.data.success) {
          if (res.data.code === '4003') {
            this.$message.error(this.$t('openatc.common.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }

        this.deviceList.forEach(ele => {
          if (ele.intersectionid === intersectionid) {
            let options
            options = res.data.data.data.phaseList.map(ele => {
              return {
                phasedirection: ele.direction,
                phasedesc: this.$t('openatc.bottleneckcontrol.phase') + ele.id + this.getPhaseName(ele.direction).name,
                phaseid: ele.id
              }
            })
            ele.phaseoptions = options
          }
        })
      })
    },
    getPhaseName (desc) {
      if (!desc) return
      // 根据相位描述数组得到相位名称
      let status = []
      for (let obj of images) {
        if (desc.length > 0 && desc.includes(obj.id)) {
          status.push(1)
        } else {
          status.push(0)
        }
      }
      return this.getName(status)
    },
    getName (status) {
      let name = ''
      for (let i = 0; i < status.length; i++) {
        if (!status[i]) continue
        name = name + ',' + this.$t(images[i].name)
      }
      if (name !== '') {
        const obj = {
          name: name.substr(1)
        }
        return obj
      } else {
        return {
          name: ''
        }
      }
    },
    checkRules (devs) {
      for (let inter of devs) {
        if (!inter.phaseid || inter.phaseid === 0) {
          this.$message.error(this.$t('openatc.greenwaveoptimize.choosephase'))
          return true
        }
      }
      return false
    },
    refreshData () {
      if (!this.curDetectorDevs) return
      if (!this.curDetectorDevs.overflows || !this.curDetectorDevs.overflows.length) return
      this.deviceList = JSON.parse(
        JSON.stringify(this.curDetectorDevs.overflows)
      ).map(ele => ({
        ...ele,
        phasedesc:
        !ele.description &&
        typeof ele.description !== 'undefined' &&
        ele.description !== 0
          ? ''
          : this.$t('openatc.bottleneckcontrol.phase') + ele.phaseid + ' ' + this.getPhaseName(ele.description).name,
        phaseoptions: []
      }))
    },
    formatState (row, column, cellValue, index) {
      return this.statusMap.get(String(cellValue))
    },
    formatCode (control) {
      if (control === 20) {
        return {
          label: this.$t('openatc.bottleneckcontrol.executed'),
          type: 'success'
        }
      } else if (control === 13) {
        return {
          label: this.$t('openatc.bottleneckcontrol.intransition')
        }
      } else {
        return {
          label: this.$t('openatc.bottleneckcontrol.unexecuted'),
          type: 'danger'
        }
      }
    }
  },
  mounted () {
    this.refreshData()
  },
  destroyed () {}
}
</script>

<style lang="scss">
.overflowDeviceDrawer {
  .el-drawer__header {
    font-size: 18px;
  }
}
</style>
