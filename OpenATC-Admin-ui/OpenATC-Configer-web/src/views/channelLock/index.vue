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
    <el-button style="margin-bottom:10px;" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-button style="margin-bottom:10px;" type="primary" @click="deleteAllData">{{$t('edge.common.deleteall')}}</el-button>
  </div>
  <div class="manual-bottom" style="display: inline-block">
    <div class="manual-control" style="display: inline-block">
      <el-table :data="channellock" fit highlight-current-row :max-height="tableHeight" border id="footerBtn" style="width: 95%; margin-left: 10px;" @row-click="handdleChannelLock">
        <el-table-column align="center" label='No' width="40">
          <template slot-scope="scope">
            {{scope.$index + 1}}
          </template>
        </el-table-column>
        <el-table-column label="开始时间" min-width="190" align="center">
        <template slot-scope="scope">
          <el-col :span="10">
            <el-select style="float:right;width:80px" v-model="scope.row.starthour" size="small">
              <el-option
                v-for="item in HoursOption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="2">
            <span>:</span>
          </el-col>
          <el-col :span="10">
          <el-select style="float:left;width:80px" v-model="scope.row.startmin" size="small">
            <el-option
              v-for="item in MinuteOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          </el-col>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" min-width="190" align="center">
        <template slot-scope="scope">
          <el-col :span="10">
            <el-select style="float:right;width:80px" v-model="scope.row.endhour" size="small">
              <el-option
                v-for="item in HoursOption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="2">
            <span>:</span>
          </el-col>
          <el-col :span="10">
          <el-select style="float:left;width:80px" v-model="scope.row.endmin" size="small">
            <el-option
              v-for="item in MinuteOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          </el-col>
        </template>
      </el-table-column>
      <el-table-column prop="greenflash" align="center" label="过渡绿闪时间" width="180">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="1000" :step="1" v-model.number="scope.row.greenflash" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="yellowlamp" align="center" label="过渡黄灯时间" width="180">
        <template slot-scope="scope">
          <el-input-number size="small" controls-position="right" :min="0" :max="1000" :step="1" v-model.number="scope.row.yellowlamp" style="width: 100px;"></el-input-number>
        </template>
      </el-table-column>
      </el-table>
    </div>
    <div class="manual-tables" style="display: inline-block">
      <div class="manual-tables-top">
        <div class="channel-status">通道状态</div>
      </div>
      <div class="manual-tables-bottom">
        <el-table :data="channelList" :max-height="tableHeight" v-loading.body="listLoading" style="width: 90%" border id="footerBtn">
          <!-- <el-table-column align="center" label='No' width="40">
            <template slot-scope="scope">
              {{scope.$index + 1}}
            </template>
          </el-table-column> -->
          <el-table-column
          prop="channelid"
          label="通道"
          align="center">
          </el-table-column>
          <el-table-column prop="desc" label="通道信息" align="center">
            <template slot-scope="scope">
              <el-input size="small" v-model="scope.row.desc"></el-input>
            </template>
          </el-table-column>
          <el-table-column
          label="状态"
          align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.lockstatus" placeholder="请选择" size="small">
              <el-option
                v-for="item in channelstatusList"
                :key="item.value"
                :label="item.label"
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
export default {
  name: 'manualcontrol',
  components: {},
  data () {
    return {
      tableHeight: 600,
      listLoading: false, // 数据加载等待动画
      channelList: [],
      id: 1,
      channelstatusList: [{
        label: '通道状态不指定状态',
        value: 0
      }, {
        label: '红灯',
        value: 1
      }, {
        label: '黄灯',
        value: 2
      }, {
        label: '绿灯',
        value: 3
      }, {
        label: '绿闪',
        value: 4
      }, {
        label: '灭灯',
        value: 5
      }, {
        label: '红闪',
        value: 6
      }],
      HoursOption: [{
        value: 0,
        label: '00'
      }, {
        value: 1,
        label: '01'
      }, {
        value: 2,
        label: '02'
      }, {
        value: 3,
        label: '03'
      }, {
        value: 4,
        label: '04'
      }, {
        value: 5,
        label: '05'
      }, {
        value: 6,
        label: '06'
      }, {
        value: 7,
        label: '07'
      }, {
        value: 8,
        label: '08'
      }, {
        value: 9,
        label: '09'
      }, {
        value: 10,
        label: '10'
      }, {
        value: 11,
        label: '11'
      }, {
        value: 12,
        label: '12'
      }, {
        value: 13,
        label: '13'
      }, {
        value: 14,
        label: '14'
      }, {
        value: 15,
        label: '15'
      }, {
        value: 16,
        label: '16'
      }, {
        value: 17,
        label: '17'
      }, {
        value: 18,
        label: '18'
      }, {
        value: 19,
        label: '19'
      }, {
        value: 20,
        label: '20'
      }, {
        value: 21,
        label: '21'
      }, {
        value: 22,
        label: '22'
      }, {
        value: 23,
        label: '23'
      }],
      MinuteOption: [{
        value: 0,
        label: '00'
      }, {
        value: 1,
        label: '01'
      }, {
        value: 2,
        label: '02'
      }, {
        value: 3,
        label: '03'
      }, {
        value: 4,
        label: '04'
      }, {
        value: 5,
        label: '05'
      }, {
        value: 6,
        label: '06'
      }, {
        value: 7,
        label: '07'
      }, {
        value: 8,
        label: '08'
      }, {
        value: 9,
        label: '09'
      }, {
        value: 10,
        label: '10'
      }, {
        value: 11,
        label: '11'
      }, {
        value: 12,
        label: '12'
      }, {
        value: 13,
        label: '13'
      }, {
        value: 14,
        label: '14'
      }, {
        value: 15,
        label: '15'
      }, {
        value: 16,
        label: '16'
      }, {
        value: 17,
        label: '17'
      }, {
        value: 18,
        label: '18'
      }, {
        value: 19,
        label: '19'
      }, {
        value: 20,
        label: '20'
      }, {
        value: 21,
        label: '21'
      }, {
        value: 22,
        label: '22'
      }, {
        value: 23,
        label: '23'
      }, {
        value: 24,
        label: '24'
      }, {
        value: 25,
        label: '25'
      }, {
        value: 26,
        label: '26'
      }, {
        value: 27,
        label: '27'
      }, {
        value: 28,
        label: '28'
      }, {
        value: 29,
        label: '29'
      }, {
        value: 30,
        label: '30'
      }, {
        value: 31,
        label: '31'
      }, {
        value: 32,
        label: '32'
      }, {
        value: 33,
        label: '33'
      }, {
        value: 34,
        label: '34'
      }, {
        value: 35,
        label: '35'
      }, {
        value: 36,
        label: '36'
      }, {
        value: 37,
        label: '37'
      }, {
        value: 38,
        label: '38'
      }, {
        value: 39,
        label: '39'
      }, {
        value: 40,
        label: '40'
      }, {
        value: 41,
        label: '41'
      }, {
        value: 42,
        label: '42'
      }, {
        value: 43,
        label: '43'
      }, {
        value: 44,
        label: '44'
      }, {
        value: 45,
        label: '45'
      }, {
        value: 46,
        label: '46'
      }, {
        value: 47,
        label: '47'
      }, {
        value: 48,
        label: '48'
      }, {
        value: 49,
        label: '49'
      }, {
        value: 50,
        label: '50'
      }, {
        value: 51,
        label: '51'
      }, {
        value: 52,
        label: '52'
      }, {
        value: 53,
        label: '53'
      }, {
        value: 54,
        label: '54'
      }, {
        value: 55,
        label: '55'
      }, {
        value: 56,
        label: '56'
      }, {
        value: 57,
        label: '57'
      }, {
        value: 58,
        label: '58'
      }, {
        value: 59,
        label: '59'
      }],
      typeOptions: new Map([[0, '不启用'], [2, '机动车相位'], [3, '行人相位'], [4, '跟随相位'], [5, '跟随行人相位']])
    }
  },
  computed: {
    ...mapState({
      channellock: state => state.globalParam.tscParam.channellock
    })
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
                150
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
                150
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    // this.increaseId()
  },
  methods: {
    handdleChannelLock (row, event, column) {
      this.channelList = row.channellocKinfo
    },
    deleteAllData () {
      this.$confirm(this.$t('edge.channellock.deleteAlltip'),
        this.$t('edge.common.alarm'), {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }).then(() => {
        let channellock = this.globalParamModel.getParamsByType('channellock')
        this.globalParamModel.deleteParamsByType('channellock', 0, channellock.length)
        this.channelList = []
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
    onAdd () {
      this.increaseId()
      let channel = this.globalParamModel.getParamsByType('channelList')
      let channellocKinfoList = []
      for (let chan of channel) {
        let obj = {}
        let type = chan.controltype
        obj.channelid = chan.id
        obj.desc = chan.desc + this.typeOptions.get(type)
        obj.lockstatus = 0
        channellocKinfoList.push(obj)
      }
      var channellockInitData = {
        id: this.id,
        starthour: 0,
        startmin: 0,
        startsec: 0,
        endhour: 0,
        endmin: 0,
        endsec: 0,
        greenflash: 3,
        yellowlamp: 2,
        channellocKinfo: channellocKinfoList
      }
      this.globalParamModel.addParamsByType('channellock', channellockInitData)
      // this.id++
      // 重新排序数组
      let channellockList = this.globalParamModel.getParamsByType('channellock')
      channellockList.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    increaseId () { // 实现id在之前的基础上寻找最小的
      let channellock = this.globalParamModel.getParamsByType('channellock')
      let channellockIdList = channellock.map(ele => ele.id)
      let i = channellock.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 40; j++) {
          if (!channellockIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.manual-record {
  position: relative;
  width: 100%;
  height: 100%;
}
.manual-top {
  margin-left: 10px;
}
.manual-bottom {
  width: 100%;
}
.manual-control {
  float: left;
  width: 60%;
}
.manual-tables {
  float: right;
  width: 40%;
}
.manual-tables-bottom {
  // width: 800px;
  // min-width: 700px;
  width: 100%;
  float: left;
  margin-top: 10px;
}
.channel-status {
  font-family: SourceHanSansCN-Regular;
  font-size: 18px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #303133;
}
</style>
<style rel="stylesheet/scss" lang="scss">
.control-model .el-input__inner {
  -webkit-appearance: none;
  background-color: #fff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 34px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  width: 90px;
}
</style>
