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
  <div class="app-container" ref="channel-container">
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-button style="margin-bottom:10px" type="primary" @click="deleteAllData">{{$t('edge.common.deleteall')}}</el-button>
    <el-table :data="channelList" v-loading.body="listLoading" element-loading-text="Loading" fit highlight-current-row :max-height="tableHeight" id="footerBtn">
      <el-table-column align="center" label='No' min-width="40">
        <template slot-scope="scope">
          <span>{{scope.$index+1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label='ID' min-width="40">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.desc')" prop="desc" min-width="100">
        <template slot-scope="scope">
          <!-- <el-input size="small" v-model="scope.row.desc"  @change="handleEdit(scope.$index, scope.row)"></el-input> -->
          <el-select v-model="scope.row.desc" :placeholder="$t('edge.common.select')" size="small" clearable>
            <el-option
              v-for="item in signLocationList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.controlType')" min-width="200" prop="typeAndSouce">
        <template slot-scope="scope">
          <el-cascader
            size="medium"
            expand-trigger="hover"
            :options="typeOptions"
            v-model="scope.row.typeAndSouce"
            class="config"
            clearable
            @change="handleChange(scope.row.typeAndSouce, scope.$index)">
          </el-cascader>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('edge.phase.lane')">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.lane" oninput="this.value=this.value.replace(/[^\d\,]/g,'')"></el-input>
          <!-- <span>{{scope.row.Lane}}</span> -->
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('edge.channel.voltthresh')">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="5" :max="200" :step="1" v-model.number="scope.row.voltthresh" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.pacthresh')">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="1" :max="2000" :step="1" v-model.number="scope.row.pacthresh" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.peakhthresh')">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="100" :max="1000" :step="1" v-model.number="scope.row.peakhthresh" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.peaklthresh')">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="100" :max="1000" :step="1" v-model.number="scope.row.peaklthresh" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('edge.channel.operation')">
        <template slot-scope="scope">
          <el-button type="text" @click="handleClone(scope.$index,scope.row)">{{$t('edge.common.clone')}}</el-button>
          <el-button type="text"  @click="handleDelete(scope.$index,scope.row)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getControSource, getOverLap, refreshChannelLockDescData, refreshControlPanelDescData, getTypeOptions, getEtypeOptions } from '@/utils/channeldesc.js'
const TypeOptions = [{
  value: 1,
  // label: 'other'
  label: 'edge.channel.other'
}, {
  value: 2,
  // label: 'phaseVehicle'
  label: 'edge.channel.phaseVehicle'
}, {
  value: 3,
  // label: 'phasePedestrian'
  label: 'edge.channel.phasePedestrian'
}, {
  value: 4,
  // label: 'overlap'
  label: 'edge.channel.overlap'
}]
export default {
  name: 'channel',
  components: { },
  data () {
    return {
      tableHeight: 760,
      listLoading: false,
      id: 1,
      typeOptions: [],
      signLocationList: ['东', '南', '西', '北', '东北', '东南', '西南', '西北'],
      desclist: [] // 当前控制类型描述map
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  computed: {
    TypeOption () {
      let arrays = []
      TypeOptions.forEach(v => {
        let obj = Object.assign({}, v)
        obj.label = this.$t(obj.label)
        arrays.push(obj)
      })
      return arrays
    },
    ...mapState({
      channelList: state => state.globalParam.tscParam.channelList,
      channelDescMap: state => state.globalParam.channelDescMap
    })
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.init()
  },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      _this.tableHeight = _this.$refs['channel-container'].offsetHeight - 80
      window.onresize = function () {
        _this.tableHeight = _this.$refs['channel-container'].offsetHeight - 80
      }
    })
  },
  watch: {
    channelList: function () {
      this.init()
      this.createCurrentDescMap()
    },
    channelDescMap: function () {
      refreshChannelLockDescData()
      refreshControlPanelDescData()
    }
  },
  methods: {
    init () {
      this.initData()
      this.increaseId()
    },
    initData () {
      let phasetype = getControSource(this.$i18n.locale)
      let patterntype = getOverLap(this.$i18n.locale)
      this.typeOptions = getTypeOptions()
      if (this.$i18n.locale === 'en') {
        this.typeOptions = getEtypeOptions()
      }
      this.typeOptions[0].children = phasetype
      this.typeOptions[1].children = phasetype
      this.typeOptions[2].children = patterntype
      this.typeOptions[3].children = patterntype
      let channel = this.globalParamModel.getParamsByType('channelList')
      for (let obj of channel) {
        let list = []
        list.push(obj.controltype)
        // 车道灯和不启用，没有下一级菜单
        if (obj.controltype !== 6 && obj.controltype !== 0) {
          list.push(obj.controlsource)
        }
        obj.typeAndSouce = list
      }
    },
    handleChange (value, index) {
      let channel = this.globalParamModel.getParamsByType('channelList')
      channel[index].controltype = value[0]
      if (value[1]) {
        channel[index].controlsource = value[1]
      }
      this.createCurrentDescMap()
    },
    createCurrentDescMap () {
      // 生成当前的控制类型描述，与通道id一一对应
      let channels = this.globalParamModel.getParamsByType('channelList')
      let desclist = new Map()
      for (let ele of channels) {
        if (!ele.typeAndSouce || ele.typeAndSouce.length === 0) continue
        let source
        let dire
        let desc = []
        if (ele.typeAndSouce[0] !== undefined) {
          source = this.typeOptions.filter(type => type.value === ele.typeAndSouce[0])[0].label
          desc[0] = source
        }
        if (ele.typeAndSouce[1] !== undefined) {
          this.typeOptions.forEach(type => {
            if (type.value === ele.typeAndSouce[0] && type.children && type.children.length) {
              dire = type.children.filter(child => child.value === ele.typeAndSouce[1])[0].label
            }
          })
          desc[1] = dire
        }
        desclist.set(ele.id, desc)
      }
      console.log(desclist)
      this.desclist = desclist
      this.$store.dispatch('SetChannelDesc', desclist)
    },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let channelList = this.globalParamModel.getParamsByType('channelList')
      let channelIdList = channelList.map(ele => ele.id)
      let i = channelList.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 40; j++) {
          if (!channelIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleEdit (index, row) {

    },
    handleDelete (index, row) {
      this.$confirm(this.$t('edge.channel.deletetip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        this.globalParamModel.deleteParamsByType('channelList', index, 1)
        // 删除对应的通道描述，以供通道锁定和手动面板更新
        delete this.desclist[row.id]
        this.$store.dispatch('SetChannelDesc', this.desclist)
        this.$message({
          type: 'success',
          message: this.$t('edge.common.deletesucess')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.common.deletecancel')
        })
      })
    },
    deleteAllData () {
      this.$confirm(this.$t('edge.channel.deleteAlltip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        let channelList = this.globalParamModel.getParamsByType('channelList')
        this.globalParamModel.deleteParamsByType('channelList', 0, channelList.length)
        this.id = 1
        this.$message({
          type: 'success',
          message: this.$t('edge.common.deletesucess')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('edge.common.deletecancel')
        })
      })
    },
    handleClone (index, row) {
      let data = {}
      this.increaseId()
      if (this.globalParamModel.getParamLength('phaseList') === 0) {
        this.$message.error(
          // 'Please create phase first !'
          this.$t('edge.pattern.firstphase')
        )
        return
      }
      if (this.globalParamModel.getParamLength('channelList') >= 40) {
        this.$message.error(
          // 'There are at most 255 data !'
          this.$t('edge.channel.mostdata')
        )
        return
      }
      Object.assign(data, row)
      data.id = this.id
      this.globalParamModel.addParamsByType('channelList', data)
      // this.id++
      // 重新排序相位数组
      let channelList = this.globalParamModel.getParamsByType('channelList')
      channelList.sort(this.compareProperty('id'))
    },
    onAdd () {
      if (this.globalParamModel.getParamLength('phaseList') === 0) {
        this.$message.error(
          // 'Please create phase first !'
          this.$t('edge.pattern.firstphase')
        )
        return
      }
      if (this.globalParamModel.getParamLength('channelList') >= 40) {
        this.$message.error(
          // 'There are at most 255 data !'
          this.$t('edge.channel.mostdata')
        )
        return
      }
      // this.getControSource()
      this.increaseId()
      var channelInitData = {
        id: this.id,
        desc: '',
        controlsource: 1,
        controltype: 2,
        typeAndSouce: [2, 1],
        voltthresh: 50,
        pacthresh: 30,
        peakhthresh: 400,
        peaklthresh: 130
      }
      this.globalParamModel.addParamsByType('channelList', channelInitData)
      // this.id++
      // 重新排序相位数组
      let channelList = this.globalParamModel.getParamsByType('channelList')
      channelList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    }
  }
}
</script>

<style scoped>
 /* * {
    margin: 0;
    padding: 0} */
  body {
      overflow: auto;
      font-weight: 400;
      -webkit-font-smoothing: antialiased;
    }
  /* .el-cascader {
    display: inline-block;
    position: relative;
    font-size: 14px;
    line-height: 40px;
    width: 100%;
  } */
  .config {
    width: 100%;
  }
</style>
