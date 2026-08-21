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
<div class="faultList">
    <div class="total" :style="{ width: '100%', height: totalHeight }">
        <div class="Img"></div>
        <div class="text">
            {{$t('openatc.home.faultsum')}}
        </div>
        <div class="num">{{maxValue}}</div>
    </div>
    <div class="content" :style="{maxHeight: maxListHeight}">
      <el-table
        :data="faultDevsData"
        style="width: 100%"
         :height="maxListHeight"
        :default-sort = "{prop: 'm_unFaultOccurTime', order: 'descending'}"
        >
        <el-table-column
          prop="agentid"
          :label="$t('openatc.devicemanager.crowsid')"
          width="100">
        </el-table-column>
        <el-table-column
          prop="m_unFaultOccurTime"
          :label="$t('openatc.devicemanager.faultOccurtime')"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          prop="m_wFaultType"
          :label="$t('openatc.devicemanager.faultMaintype')"
          sortable
          :formatter="formatterType">
        </el-table-column>
      </el-table>
    </div>
</div>
</template>

<script>
import HorizontalChildren from '@/components/HorizontalChildren/index'
export default {
  name: 'faultList',
  props: {
    faultList: {
      type: Array
    }
  },
  watch: {
    faultList: {
      handler: function (val, oldVal) {
        this.initData()
      },
      // 深度观察监听
      deep: true
    }
  },
  data () {
    return {
      maxValue: 0,
      faultDevsData: [],
      totalMarginBottom: '24px',
      titleMarginBottom: '30px',
      maxListHeight: '330px',
      typeNameWidth: '142px',
      faultNumWidth: '295px',
      totalHeight: '54px',
      chartWidth: '400px'
    }
  },
  components: {
    HorizontalChildren
  },
  methods: {
    initData () {
      this.maxValue = this.faultList.length
      this.faultDevsData = this.faultList
    },
    formatterType (row, column) {
      let res
      let typeCode = row.m_wFaultType
      if (typeCode >= 101 && typeCode <= 199) {
        res = this.$t('edge.fault.tab1')
      } else if (typeCode >= 201 && typeCode <= 299) {
        res = this.$t('edge.fault.tab2')
      } else if (typeCode >= 301 && typeCode <= 399) {
        res = this.$t('edge.fault.tab3')
      } else if (typeCode >= 401 && typeCode <= 499) {
        res = this.$t('edge.fault.tab4')
      }
      return res
    },
    calculateChartSize () {
      let viewH = document.documentElement.clientHeight - 40
      let viewW = document.documentElement.clientWidth - 40
      this.totalMarginBottom = (24 / 1080 * viewH).toFixed(0) + 'px'
      this.titleMarginBottom = (30 / 1080 * viewH).toFixed(0) + 'px'
      this.maxListHeight = (350 / 1080 * viewH).toFixed(0) + 'px'
      this.typeNameWidth = (142 / 1920 * viewW).toFixed(0) + 'px'
      this.faultNumWidth = (295 / 1920 * viewW).toFixed(0) + 'px'
      this.chartWidth = (400 / 1920 * viewW).toFixed(0) + 'px'
      this.totalHeight = (54 / 1080 * viewH).toFixed(0) + 'px'
    }
  },
  created () {
    this.calculateChartSize()
  },
  mounted () {
    window.addEventListener('resize', () => {
      this.calculateChartSize()
    }, false)
    this.initData()
  }
}
</script>

<style lang="scss" scoped>
</style>
