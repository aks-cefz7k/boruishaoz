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
  <div class="app-container">
        <div class="device-message">
          <div class="device-info">{{$t('edge.deviceinfo.deviceinfo')}}</div>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.addresscode')}}
                <el-button type="text"
                           class="see-cut"
                           :disabled="isSeeCutDisabled"
                           @click="onSeeCutClick">{{$t('edge.deviceinfo.seeCutEffect')}}</el-button>
              </div>
              <el-input v-model="customInfo.siteid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.areaid')}}</div>
              <el-input v-model="customInfo.areaid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.crossid')}}</div>
              <el-input v-model="customInfo.intersectionid" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.overview.crossname')}}:</div>
              <el-input v-model="customInfo.fixintersectioninfo" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.port')}}</div>
              <el-input v-model="customInfo.commuport" :placeholder="$t('edge.common.entercontent')" style="width:100%" size="small"></el-input>
            </el-col>
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.overview.type')}}:</div>
              <el-select v-model="customInfo.commutype" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in commutypeOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.stepType')}}:</div>
              <el-select v-model="customInfo.steptype" :placeholder="$t('edge.common.select')" size="small">
                <el-option
                  v-for="item in stepTypeOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row class="demo-autocomplete" :gutter="30">
            <el-col :span="4">
              <div class="sub-title">{{$t('edge.deviceinfo.selflearning')}}
                <el-switch
                  style="padding-left: 5px;"
                  v-model="customInfo.selflearning"
                  active-color="#409EFF"
                  :active-value="1"
                  :inactive-value="0">
                </el-switch>
              </div>
            </el-col>
          </el-row>
        </div>
        <seeCutEffect ref="seeCutEffect"></seeCutEffect>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import seeCutEffect from './components/seeCutEffect'
export default {
  name: 'deviceinfo',
  components: {seeCutEffect},
  data () {
    return {
      commutypeOptions: [{label: 'TCP', value: 1}, {label: 'UDP', value: 2}, {label: 'RS232', value: 3}],
      stepTypeOptions: [{label: '阶段', value: 0}, {label: '色步', value: 1}]
      // customInfo: {
      //   areaid: '',
      //   intersectionid: '',
      //   siteid: '',
      //   selflearning: 0,
      //   fixintersectioninfo: '',
      //   commuport: 0,
      //   commutype: '',
      //   steptype: ''
      // },
    }
  },
  computed: {
    ...mapState({
      customInfo: state => state.globalParam.tscParam.customInfo
    }),
    isSeeCutDisabled () {
      let res = false
      if (this.customInfo.siteid) {
        res = false
      } else {
        res = true
      }
      return res
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
  },
  methods: {
    onSeeCutClick () {
      let num = Number(this.customInfo.siteid)
      if (!num || num <= 0 || num >= 99999) {
        this.$message.error(this.$t('edge.deviceinfo.siteIdLimit'))
        return false
      }
      this.$refs.seeCutEffect.show(this.customInfo.siteid)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
