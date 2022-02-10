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
      <div class="control-model-name">控制模式：</div><div class="control-model-value">{{controlMode}}</div>
      <div class="control-model-name">持续时间：</div><div class="control-model-input"><el-input v-model="duration" placeholder="请输入内容" size="small"></el-input></div>
      <div class="control-model-name">过渡绿闪时间：</div><div class="control-model-input"><el-input v-model="greenflash" placeholder="请输入内容" size="small"></el-input></div>
      <div class="control-model-name">过渡黄灯时间：</div><div class="control-model-input"><el-input v-model="yellow" placeholder="请输入内容" size="small"></el-input></div>
      <div class="control-model-name">过渡全红时间：</div><div class="control-model-input"><el-input v-model="redclear" placeholder="请输入内容" size="small"></el-input></div>
    </div>
  </div>
  <div class="manual-bottom">
    <div class="manual-control">
      <div class="manualControl-kanban">
        <svg-icon icon-class="manualcontrolbackground" class="manualControl-background"></svg-icon>
        <div class="manual-button" style="left: 201px; top: 150px;"><span class="button-name">步进</span></div>
        <div class="manual-button" style="left: 393px; top: 150px;"><span class="button-name">全红</span></div>
        <div class="manual-button" style="left: 585px; top: 150px;"><span class="button-name">黄闪</span></div>
        <div  v-for="(item, index)  in manualBtnList" :key="index">
          <div :class="item.id > 8 ? (item.id === currChannelId ? 'select-rectangle-button' : 'rectangle-button') : (item.id === currChannelId ? 'select-circle-button' : 'circle-button')" :style="item.style" @click ="manualBtnClicked(item)">
            <span :class="item.id > 8 ? (item.id === currChannelId ? 'select-rectangle-name' : 'rectangle-name') : (item.id === currChannelId ? 'select-cycle-name' : 'cycle-name')">{{item.name}}</span>
          </div>
        </div>
      </div>
      <div class="manual-control-button">
        <el-button type="primary">获取</el-button>
        <el-button type="primary" @click="setManual">设置</el-button>
        <el-button type="primary">生成默认设置</el-button>
      </div>
    </div>
    <div class="manual-tables">
      <div class="manual-tables-top">
        <div class="manual-tables-cycle"></div>
        <div v-show="currChannelId !== -1">
          <div  v-show="!editStatus"><div class="manual-tables-name">{{currChannelName}}</div><div class="manual-tables-edit" @click="edit"><i class="el-icon-edit"></i></div></div>
          <div class="manual-tables-name" v-show="editStatus"><el-input v-model="currChannelName" placeholder="请输入内容" size="mini"></el-input></div>
          <div class="manual-tables-clear"><i class="el-icon-delete"></i><span class="manual-tables-qingkong" @click="clearChannel">清空</span><svg-icon icon-class="huangshan" style="margin-left: 20px;"></svg-icon><span class="manual-tables-qingkong" @click="reset">状态重置</span></div>
        </div>
      </div>
      <div class="manual-tables-bottom">
        <el-table
          :data="manualList"
          stripe
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
          label="通道"
          align="center">
          </el-table-column>
          <el-table-column
          prop="desc"
          label="通道描述"
          align="center">
          </el-table-column>
          <el-table-column
          label="状态"
          align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.channelstatus" placeholder="请选择" size="small">
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
import { getManualpanel, getChannel } from '@/api/manual'
export default {
  name: 'manualcontrol',
  components: {},
  data () {
    return {
      tableHeight: 600,
      listLoading: false, // 数据加载等待动画
      duration: '0', // 持续时间
      greenflash: '0', // 过渡绿闪时间
      yellow: '0', // 过渡黄灯时间
      redclear: '0', // 过渡全红时间
      manualList: [{
        'channelid': 1,
        'desc': '通道1',
        'channelstatus': 2
      },
      {
        'channelid': 1,
        'desc': '通道2',
        'channelstatus': 3
      }],
      channelstatusList: [{
        label: '红灯',
        value: 1
      }, {
        label: '黄灯',
        value: 2
      }, {
        label: '绿灯',
        value: 3
      }, {
        label: '灭灯',
        value: 4
      }],
      manualBtnList: [{
        id: 1,
        name: '东西直行',
        style: 'left: 173px; top: 243px;'
      }, {
        id: 2,
        name: '北向通行',
        style: 'left: 403px; top: 243px;'
      }, {
        id: 3,
        name: '东西左转',
        style: 'left: 632px; top: 243px;'
      }, {
        id: 4,
        name: '西向通行',
        style: 'left: 300px; top: 346px;'
      }, {
        id: 5,
        name: '东向通行',
        style: 'left: 506px; top: 346px;'
      }, {
        id: 6,
        name: '南北直行',
        style: 'left: 173px; top: 449px;'
      }, {
        id: 7,
        name: '南向通行',
        style: 'left: 403px; top: 449px;'
      }, {
        id: 8,
        name: '南北左转',
        style: 'left: 632px; top: 449px;'
      }, {
        id: 9,
        name: 'Y1',
        style: 'left: 167px; top: 562px;'
      }, {
        id: 10,
        name: 'Y2',
        style: 'left: 320px; top: 562px;'
      }, {
        id: 11,
        name: 'Y3',
        style: 'left: 473px; top: 562px;'
      }, {
        id: 12,
        name: 'Y4',
        style: 'left: 626px; top: 562px;'
      }],
      currChannelName: '',
      currChannelId: -1,
      editStatus: false, // 通道名称的编辑状态
      controlMode: '默认',
      channelList: []
    }
  },
  created () {
    this.getChannelList() // 获取手动控制面板所有信息
    this.getAllManualpanel()
  },
  methods: {
    getChannelList () {
      getChannel().then((data) => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(data.data.message)
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
          this.$message.error(data.data.message)
          return
        }
        console.log(res)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    setManual () {

    },
    manualBtnClicked (val) {
      if (this.currChannelId === val.id) {
        this.currChannelName = ''
        this.currChannelId = -1
      } else {
        this.currChannelName = val.name
        this.currChannelId = val.id
      }
    },
    edit () {
      this.editStatus = true
      this.controlMode = '自定义'
    },
    reset () {
      this.$confirm('是否将状态全部初始化为红灯?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.manualList.map(item => {
          item.channelstatus = 1
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消初始化'
        })
      })
    },
    clearChannel () {
      this.$confirm('是否清空数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.manualList = []
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清空'
        })
      })
    }
    // editSuccess () {
    //   this.editStatus = false
    // }
  }
}
</script>

<style lang="scss" scoped>
.manual-record {
  width: 100%;
  height: 100%;
}
.manual-bottom {
  width: 100%;
}
.manual-control {
  float: left;
  width: 50%;
}
.manual-tables {
  float: left;
  width: 50%;
}
.control-model {
  float: left;
  max-height: 50px;
}
.control-model-name {
  float: left;
  margin-left: 30px;
  margin-top: 30px;
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #606266;
}
.control-model-value {
  float: left;
  margin-left: 5px;
  margin-top: 30px;
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #303133;
}
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
  background-color: #eff7ff;
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
  font-family: SourceHanSansCN-Regular;
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
  font-family: SourceHanSansCN-Regular;
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
  font-family: SourceHanSansCN-Regular;
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
  font-family: SourceHanSansCN-Regular;
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
  font-family: SourceHanSansCN-Regular;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 14px;
  letter-spacing: 0px;
  color: #ffffff;
}
.manual-control-button {
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
.manual-tables-name {
  float: left;
  margin-left: 10px;
  margin-top: 22px;
  font-family: SourceHanSansCN-Regular;
  font-size: 18px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #303133;
}
.manual-tables-edit {
  float: left;
  margin-top: 25px;
  margin-left: 3px;
  width: 14px;
  height: 16px;
  color: #409eff;
  cursor:pointer;
}
.manual-tables-clear {
  position: absolute;
  right: 15px;
  margin-top: 25px;
  width: 150px;
  height: 20px;
  color: #409eff;
}
.manual-tables-qingkong {
  margin-left: 5px;
  font-family: SourceHanSansCN-Regular;
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
