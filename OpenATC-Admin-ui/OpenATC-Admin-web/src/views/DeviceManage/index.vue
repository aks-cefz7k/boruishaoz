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
  <div class="openatc-device">
    <!-- <SelectAgentid @onChange="onSelectAgentidChange"></SelectAgentid>
    <SelectCrossPhase :agentid="agentid" @onChange="onSelectCrossPhaseChange"></SelectCrossPhase> -->
    <Messagebox :visible="messageboxVisible" :text="$t('openatc.devicemanager.deletedevice')" @cancle="cancle" @ok="ok"/>
    <div class="devs-container" style="position:relative">
      <div style="display: inline-block;width: 100%;">
        <div class="tag-container">
          <DeviceTags />
        </div>
        <div class="filter-container">
          <div class="filter">
            <span class="header-span">{{$t('openatc.devicemanager.devicetype') }}：</span>
            <el-select
              style="width: 100px;"
              v-model="devicetypes"
              @change="getDeviceRanges('search')"
              clearable
              filterable>
              <el-option
                v-for="(item, index) in devicetype"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="filter">
            <span class="header-span">{{$t('openatc.devicemanager.plat') }}：</span>
            <el-select
              style="width: 100px;"
              v-model="plats"
              @change="getDeviceRanges('search')"
              clearable
              filterable>
              <el-option
                v-for="(item, index) in plat"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="filter">
            <span class="header-span">{{$t('openatc.devicemanager.protocol') }}：</span>
            <el-select
              style="width: 100px;"
              v-model="protocols"
              @change="getDeviceRanges('search')"
              clearable
              filterable>
              <el-option
                v-for="(item, index) in protocol"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="filter">
            <span class="header-span">{{$t('openatc.devicemanager.state') }}：</span>
            <el-select
              style="width: 100px;"
              v-model="states"
              @change="getDeviceRanges('search')"
              clearable
              filterable>
              <el-option
                v-for="(item, index) in state"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="filter">
            <span class="header-span">{{$t('openatc.devicemanager.tag') }}：</span>
            <el-popover
                placement="bottom"
                width="460"
                v-model="visible2"
                trigger="click"
              >
              <el-tag
                :key="index"
                v-for="(select,index) in selectTags"
                :disable-transitions="false"
                @click="selectItem(select)"
                >
                {{select}}
              </el-tag>
              <el-input
                v-model="selectTag"
                clearable
                @focus="getDicts()"
                @change="getDeviceRanges()"
                slot="reference"
                style="width: 100px;"/>
              </el-popover>
          </div>
          <div class="filter" style="margin-right:20px">
            <el-input
              @keyup.enter.native="getDeviceRanges()"
              @blur="getDeviceRanges()"
              v-model="devsfilter"
              :placeholder="$t('openatc.common.searchplaceholder')"
              prefix-icon="el-icon-search"
              style="width: 200px;"/>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAdd">
                {{$t('openatc.common.add')}}
              </el-button>
          </div>
        </div>
      </div>
    <div class="devs-table">
      <el-table
          :data="computedTable"
          size="mini"
          :max-height="tableHeight"
          v-loading.body="listLoading"
          style="width: 100%"
          id="footerBtn">
          <el-table-column
          type="index"
          align="center">
          </el-table-column>
          <el-table-column
          prop="agentid"
          :label="$t('openatc.devicemanager.crossid')"
          :sort-method="sortAgentId"
          sortable
          align="center">
          </el-table-column>
           <el-table-column
          prop="name"
          :label="$t('openatc.devicemanager.devicename')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="type"
          :label="$t('openatc.devicemanager.type')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="jsonparam.ip"
          :label="$t('openatc.devicemanager.IP')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="jsonparam.port"
          :label="$t('openatc.devicemanager.port')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="thirdplatformid"
            :label="$t('openatc.devicemanager.deviceid')"
            align="center">
          </el-table-column>
          <el-table-column
          prop="platform"
          :label="$t('openatc.devicemanager.platform')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="firm"
          :label="$t('openatc.devicemanager.firm')"
          sortable
          align="center">
          </el-table-column>
          <el-table-column
          prop="descs"
          :label="$t('openatc.devicemanager.describe')"
          align="center">
          </el-table-column>
          <el-table-column
          prop="protocol"
          :label="$t('openatc.devicemanager.protocol')"
          align="center">
          </el-table-column>
          <el-table-column
            prop="roles"
            :label="$t('openatc.devicemanager.state')"
            align="center">
              <!-- <template slot="header">
                <el-popover
                  placement="bottom"
                  trigger="click">
                  <div>
                    <template>
                      <el-checkbox @change="onOnlineChange" :checked="isOnlineChecked" :label="$t('openatc.devicemanager.online')"></el-checkbox>
                      <el-checkbox @change="onFaultChange" :checked="isFaultChecked" :label="$t('openatc.devicemanager.fault')"></el-checkbox>
                      <el-checkbox @change="onOfflineChange" :checked="isOfflineChecked" :label="$t('openatc.devicemanager.offline')"></el-checkbox>
                    </template>
                  </div>
                  <el-row type="text" slot="reference">
                    {{$t('openatc.devicemanager.state')}}<i class="el-icon-caret-bottom state-search"></i>
                  </el-row>
                </el-popover>
              </template> -->
              <template  slot-scope="scope">
                <div>
                  <el-tooltip placement="top-start" effect="light" popper-class="atooltip">
                    <div slot="content">{{$t('openatc.devicemanager.lastupdatetime')}}:{{scope.row.lastTime}}</div>
                    <el-tag style="cursor:pointer;"  size="medium" effect="plain" :type="getTag(scope.row).type">{{ getTag(scope.row).label }}</el-tag>
                  </el-tooltip>
                </div>
              </template>
          </el-table-column>
          <!-- <el-table-column
          prop="lastTime"
          width="150"
          :label="$t('openatc.devicemanager.lastupdatetime')"
          sortable
          align="center">
          </el-table-column> -->
          <el-table-column :label="$t('openatc.devicemanager.operation')" align="center" width="240">
          <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{$t('openatc.common.edit')}}</el-button>
              <el-button type="text" @click="handleToDetail(scope.row)">{{$t('openatc.common.detail')}}</el-button>
              <el-button type="text" @click="handleDelete(scope.row)">{{$t('openatc.common.delete')}}</el-button>
              <!-- <el-button type="text" @click="handleFault(scope.row)">{{$t('openatc.devicemanager.faultDetail')}}</el-button> -->
              <el-dropdown  @command="handleMoreCommand">
                <span class="el-dropdown-link">
                  {{$t('openatc.devicemanager.more')}}<i class="el-icon-arrow-down el-icon--left"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="fault" @click.native="handleFault(scope.row)">{{$t('openatc.devicemanager.faultDetail')}}</el-dropdown-item>
                  <el-dropdown-item command="pattern" @click.native="handlePatternClick(scope.row)">{{$t('openatc.devicemanager.patternStatistics')}}</el-dropdown-item>
                  <el-dropdown-item command="traffic" @click.native="handleTrafficClick(scope.row)">{{$t('openatc.devicemanager.trafficStatistics')}}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
          </template>
          </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" style='margin:0px;'>
      </el-pagination>
      </div>
    </div>
    <Update ref="updateChild" :childTitle="childTitle"></Update>
    <Fault-detail ref="faultDetail" :childTitle="childTitle"></Fault-detail>
    <PatternStatistics ref="patternStatistics" :childTitle="childTitle"></PatternStatistics>
    <TrafficStatistics ref="trafficStatistics" :childTitle="childTitle"></TrafficStatistics>
  <!-- </div> -->
  <router-view></router-view>
</div>
</template>
<script>
import router from '@/router'
import { mapState } from 'vuex'
import Messagebox from '../../components/MessageBox'
import Update from './DeviceDialog/update'
import FaultDetail from './DeviceDialog/FaultDetail'
import PatternStatistics from './DeviceDialog/PatternStatistics'
import TrafficStatistics from './DeviceDialog/TrafficStatistics'
import DeviceTags from './deviceTags'
import { DeleteDevice, getDeviceRange, getDict } from '@/api/device'// GetAllDevice,
import { GetFaultRange } from '@/api/fault'
import { getMessageByCode } from '@/utils/responseMessage'
import SelectAgentid from '@/components/SelectAgentid'
import SelectCrossPhase from '@/components/SelectCrossPhase'
import { setCrossFilter, getCrossFilter, setCrossTag, getCrossTag, setCrossStates, getCrossStates, setCrossProtocols, getCrossProtocols, setCrossPlats, getCrossPlats, setCrossDevicetypes, getCrossDevicetypes } from '@/utils/crossFilterMgr'
export default {
  name: 'device',
  components: { Update, Messagebox, DeviceTags, FaultDetail, PatternStatistics, TrafficStatistics, SelectAgentid, SelectCrossPhase },
  data () {
    return {
      agentid: 0,
      stateList: ['UP', 'DOWN'],
      isOnlineChecked: true,
      isFaultChecked: true,
      isOfflineChecked: true,
      tableHeight: 700,
      devsfilter: '',
      childTitle: 'adddevice',
      visible2: false,
      devicetypes: '',
      plats: '',
      protocols: '',
      states: '',
      selectTag: '',
      devicetype: [
        {
          label: 'asc',
          value: 'asc'
        },
        {
          label: 'simu',
          value: 'simu'
        },
        {
          label: 'v-atc',
          value: 'v-atc'
        }
      ],
      plat: [
        {
          label: 'OpenATC',
          value: 'OpenATC'
        },
        {
          label: 'SCATS',
          value: 'SCATS'
        },
        {
          label: 'HUATONG',
          value: 'HUATONG'
        },
        {
          label: 'HiCon',
          value: 'HiCon'
        }
      ],
      protocol: [
        {
          label: 'ocp',
          value: 'ocp'
        },
        {
          label: 'scp',
          value: 'scp'
        }
      ],
      state: [
        {
          label: this.$t('openatc.devicemanager.offline'),
          value: 'DOWN'
        },
        {
          label: this.$t('openatc.devicemanager.online'),
          value: 'UP'
        }
      ],
      listQuery: {
        pageNum: 1, // 页码
        pageRow: 50 // 每页条数
      },
      totalCount: 0, // 分页组件--数据总条数
      selectTags: [],
      messageboxVisible: false,
      tableData: [],
      listLoading: false, // 数据加载等待动画
      firmMap: new Map([['Kedacom', '科达'], ['Tyco', '泰科'], ['Huatong', '华通']])
    }
  },
  // mounted: function () {
  //   var _this = this
  //   _this.$nextTick(function () {
  //     // window.innerHeight:浏览器的可用高度
  //     _this.tableHeight = window.innerHeight * 0.8
  //   })
  // },
  mounted: function () {
    var _this = this
    _this.$nextTick(function () {
      // window.innerHeight:浏览器的可用高度
      // this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      // 后面的50：根据需求空出的高度，自行调整
      _this.tableHeight = window.innerHeight - document.querySelector('#footerBtn').offsetTop - 110
      window.addEventListener(
        'resize',
        () => {
        // 定义窗口大小变更通知事件
          _this.tableHeight = window.innerHeight - document.querySelector('#footerBtn').offsetTop - 110
          // 用于计算按钮组距离顶部高度（因为按钮组不能用定位，会影响表格自适应高度）
          // _this.$emit('changeBtnPosition', document.querySelector('#historyfaultrecord').offsetTop)
        }
      )
    })
  },
  computed: {
    ...mapState({
      tags: state => state.globalVariable.deviceTags
    }),
    computedTable () {
      let list = []
      // list = this.tableData.filter(data =>
      //   !this.devsfilter ||
      //   (data.agentid !== undefined && data.agentid.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
      //   (data.jsonparam.ip !== undefined && data.jsonparam.ip.toLowerCase().includes(this.devsfilter.toLowerCase())) ||
      //   (data.name !== undefined && data.name.toLowerCase().includes(this.devsfilter.toLowerCase()))
      // )
      list = this.tableData
      let stateList = this.stateList
      if (stateList && stateList.length >= 0) {
        list = list.filter(dev => {
          return stateList.includes(dev.state)
        })
      }
      for (let dev of list) {
        dev.platform = dev.platform ? dev.platform : ''
        dev.lastTime = dev.lastTime ? dev.lastTime : ''
      }
      return list
    }
  },
  watch: {
    devsfilter: {
      handler: (filter) => {
        setCrossFilter(filter)
      },
      deep: true
    },
    selectTag: {
      handler: (filter) => {
        setCrossTag(filter)
      },
      deep: true
    },
    states: {
      handler: (filter) => {
        setCrossStates(filter)
      },
      deep: true
    },
    protocols: {
      handler: (filter) => {
        setCrossProtocols(filter)
      },
      deep: true
    },
    plats: {
      handler: (filter) => {
        setCrossPlats(filter)
      },
      deep: true
    },
    devicetypes: {
      handler: (filter) => {
        setCrossDevicetypes(filter)
      },
      deep: true
    }
  },
  created () {
    this.selectTag = getCrossTag('selectTag')
    this.devsfilter = getCrossFilter('deviceFilter')
    this.states = getCrossStates('states')
    this.protocols = getCrossProtocols('protocols')
    this.plats = getCrossPlats('plats')
    this.devicetypes = getCrossDevicetypes('devicetypes')
    // this.getDicts()
    if (this.$route.params.filter) {
      this.getStatusFilterParams()
    } else {
      this.getDeviceRanges()
    }
  },
  methods: {
    getDicts () {
      getDict().then(res => {
        this.selectTags = Array.from(new Set(res.data.data.filter(it => it.value).map(it => it.value)))
      })
    },
    selectItem (select) {
      this.selectTag = select
      this.getDeviceRanges()
      this.visible2 = false
    },
    handleSizeChange (val) {
      // 改变每页数量，默认返回第一页
      this.listQuery.pageNum = 1
      this.listQuery.pageRow = val
      this.getDeviceRanges()
    },
    handleCurrentChange (val) {
      // 改变页码
      this.listQuery.pageNum = val
      this.getDeviceRanges()
    },
    onSelectAgentidChange (agentid) {
      this.agentid = agentid
    },
    onSelectCrossPhaseChange (dir) {
      // console.log(dir)
    },
    // onOnlineChange (val) {
    //   this.isOnlineChecked = val
    //   this.getDeviceRanges()
    //   // this.onStateChange()
    // },
    // onFaultChange (val) {
    //   this.isFaultChecked = val
    //   this.onStateChange()
    // },
    // onOfflineChange (val) {
    //   this.isOfflineChecked = val
    //   this.getDeviceRanges()
    //   // this.onStateChange()
    // },
    // onStateChange () {
    //   let stateList = []
    //   if (this.isOnlineChecked) {
    //     stateList.push('UP')
    //   }
    //   // if (this.isFaultChecked) {
    //   //   stateList.push('FAULT')
    //   // }
    //   if (this.isOfflineChecked) {
    //     stateList.push('DOWN')
    //   }
    //   this.stateList = stateList
    // },
    // else if (row.state === 'FAULT') {
    //     return {
    //       label: this.$t('openatc.devicemanager.fault'),
    //       type: 'danger'
    //     }
    //   }
    getTag (row) {
      if (row.state === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else {
        if (row.status === 0) {
          // 数据从设备端来，暂时写死，0代表正常状态，其余数字均代表一种类型的故障
          return {
            label: this.$t('openatc.devicemanager.online'),
            type: 'success'
          }
        } else {
          // return {
          //   label: this.$t('openatc.devicemanager.fault'),
          //   type: 'danger'
          // }
        }
      }
    },
    getDeviceRanges (row) {
      this.listLoading = true
      let reqData = {
        'pageNum': this.listQuery.pageNum,
        'pageRow': this.listQuery.pageRow,
        'search': this.devsfilter,
        'type': this.devicetypes,
        'platform': this.plats,
        'protocol': this.protocols,
        'state': this.states,
        'tags': this.selectTag
      }
      getDeviceRange(reqData).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.listLoading = false
        if (res.data.data.content) {
          this.tableData = res.data.data.content.map(data => ({
            ...data,
            firm: this.firmMap.get(data.firm)
          }))
        } else {
          this.tableData = []
        }
        this.totalCount = res.data.data.total
      })
    },
    handleToDetail (row) {
      const dev = row
      const devId = dev.id
      this.$store.dispatch('SetOperatedDeviceId', devId)
      this.$store.dispatch('SaveDevsTags', dev)
      this.$store.dispatch('GetMultDeviceData', devId)
      const curTscParam = this.$store.getters.openedDevice
      const curPath = this.$store.getters.openedPath
      if (!curTscParam) {
        this.$store.dispatch('ResetTscParam')
        this.$store.dispatch('SaveCurPath', '/overview/index')
      } else {
        this.$store.dispatch('SaveTscParam', curTscParam)
        this.$store.dispatch('SaveCurPath', curPath)
      }
      router.push({
        path: !curPath || curPath === '/overview/index' ? '/overview/index' : curPath,
        query: {agentid: dev.agentid, isfromatc: true}
      })
    },
    handleFilter () {
    },
    handleAdd () {
      this.childTitle = 'adddevice'
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick()
    },
    handleEdit (row) {
      this.childTitle = 'editdevice'
      let dev = row
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(dev)// 没有tags字段
    },
    handleDelete (row) {
      this.childTitle = 'faultDetail'
      let dev = row
      this.deleteId = dev.agentid
      this.messageboxVisible = true
    },
    handleFault (row) {
      let _this = this
      let reqData = {
        'agentId': row.agentid,
        'isCurrentFault': true
      }
      GetFaultRange(reqData).then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        } else {
          let list = res.data.data.content
          if (list && list.length > 0) {
            this.childTitle = 'faultDetail'
            let component = _this.$refs.faultDetail
            component.onViewFaultClick(list)
          } else {
            this.$message.info(this.$t('openatc.common.nodata'))
          }
        }
      })
    },
    handlePatternClick (row) {
      let component = this.$refs.patternStatistics
      component.onView(row)
    },
    handleTrafficClick (row) {
      let component = this.$refs.trafficStatistics
      component.onView(row)
    },
    cancle () {
      this.messageboxVisible = false
    },
    ok () {
      DeleteDevice(this.deleteId).then(res => {
        if (!res.data.success) {
          let msg = getMessageByCode(res.data.code, this.$i18n.locale)
          if (res.data.data) {
            let errorCode = res.data.data.errorCode
            if (errorCode) {
              msg = msg + ' - ' + getMessageByCode(errorCode, this.$i18n.locale)
            }
          }
          this.$message.error(msg)
          return false
          // this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          // return
        }
        this.$message.success(this.$t('openatc.common.deletesuccess'))
        // this.$message({
        //   message: this.$t('openatc.common.deletesuccess'),
        //   type: 'success'
        // })
        this.messageboxVisible = false
        this.getDeviceRanges()
      })
    },
    sortAgentId (obj1, obj2) {
      let val1 = obj1.agentid
      let val2 = obj2.agentid
      return val1 - val2
    },
    formatterSockettype (row, column) {
      let sockettype = row.sockettype
      let res = ''
      if (sockettype === 0) {
        res = 'UDP'
      } else if (sockettype === 1) {
        res = 'TCP'
      }
      return res
    },
    handleMoreCommand (command) {
      switch (command) {
        case 'fault':
          break
        case 'pattern':
          break
        case 'traffic':
          break
      }
    },
    getStatusFilterParams () {
      // 获取从首页跳转过来的设备状态过滤参数
      if (this.$route.params.filter !== undefined) {
        let stateFilter = this.$route.params.filter
        // this.states = stateFilter
        // this.getDeviceRanges()
        switch (stateFilter) {
          case 'online': this.states = 'UP'
            this.getDeviceRanges()
            // this.onOnlineChange(true)
            // this.onOfflineChange(false)
            // this.onFaultChange(false)
            break
          case 'offline': this.states = 'DOWN'
            this.getDeviceRanges()
            // this.onOfflineChange(true)
            // this.onOnlineChange(false)
            // this.onFaultChange(false)
            break
          // case 'fault': this.onFaultChange(true)
          //   this.onOnlineChange(false)
          //   this.onOfflineChange(false)
          //   break
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
// .tag-container {
//   float: left;
//   max-width: 70%;
//   overflow: hidden;
// }
// .filter-container {
//   float: right;
//   margin-top: 20px;
//   margin-right: 20px;
// }
// .devs-table {
//   position: absolute;
//   top: 134px;
//   left: 20px;
//   right: 20px;
//   border: solid 1px $--border-color-lighter;
//   overflow: auto;
// }
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
.el-dropdown-link {
    margin-left: 5px;
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
