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
    <div class="total" :style="{ width: '100%', marginBottom: totalMarginBottom }">
        <div class="Img"></div>
        <div class="text">
            {{$t('openatc.home.faultsum')}}
        </div>
        <div class="num">{{maxValue}}</div>
    </div>
    <div class="title" :style="{marginBottom: titleMarginBottom}">
        <div class="faultName" :style="{width: typeNameWidth}">{{$t('openatc.home.faulttype')}}</div>
        <div class="faultNum" :style="{width: faultNumWidth}">
           {{$t('openatc.home.quantity')}}
        </div>
    </div>
    <div class="content" :style="{maxHeight: maxListHeight}">
        <div v-for="(item, index) in faultDevsData"
            :key=index
            style="height: 48px;">
          <div class="typeName" :style="{width: typeNameWidth}">{{item.typeName}}</div>
          <div class="list">
            <HorizontalChildren :name="item.typeName"
                backColor='#ebeef5'
                :isShowText="false"
                :dataList="[{value: item.number, maxNum: maxValue, color: '#e94f4f' }]"
                :mainwidth="chartWidth">
            </HorizontalChildren>
          </div>
        </div>
    </div>
</div>
</template>

<script>
import HorizontalChildren from '@/components/HorizontalChildren/index'
export default {
  name: 'faultList',
  props: {
    faultList: {
      type: Object
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
      chartWidth: '400px'
    }
  },
  components: {
    HorizontalChildren
  },
  methods: {
    initData () {
      const data = []
      this.maxValue = this.faultList.maxValue
      this.faultList.data.forEach(function (value, key) {
        data.push({
          typeName: key,
          number: value
        })
      })
      this.faultDevsData = data
    }
  },
  created () {
    let viewH = document.documentElement.clientHeight - 40
    let viewW = document.documentElement.clientWidth - 40
    this.totalMarginBottom = (24 / 1080 * viewH).toFixed(0) + 'px'
    this.titleMarginBottom = (30 / 1080 * viewH).toFixed(0) + 'px'
    this.maxListHeight = (330 / 1080 * viewH).toFixed(0) + 'px'
    this.typeNameWidth = (142 / 1920 * viewW).toFixed(0) + 'px'
    this.faultNumWidth = (295 / 1920 * viewW).toFixed(0) + 'px'
    this.chartWidth = (400 / 1920 * viewW).toFixed(0) + 'px'
  },
  mounted () {
    this.initData()
  }
}
</script>

<style lang="scss" scoped>
</style>
