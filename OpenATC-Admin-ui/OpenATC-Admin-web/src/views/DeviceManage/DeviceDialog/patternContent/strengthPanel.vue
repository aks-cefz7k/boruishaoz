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
    <div class="strengthPanel">
      <div class="title">{{$t('openatc.patternstatistic.schemerealtimedata')}}</div>
      <div class="cross-module">
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.controlmode')}}:</div>
          <div class="cross-value">{{controlData.mode !==undefined ? $t('openatc.patternstatistic.mode' + controlData.mode) : ''}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.controltype')}}:</div>
          <div class="cross-value">{{controlData.control !==undefined ? $t('edge.overview.modelList' + controlData.control) : ''}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.controlscheme')}}:</div>
          <div class="cross-value">{{controlData.patternid !==undefined ? $t('openatc.patternstatistic.pattern') + controlData.patternid : ''}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.cycle')}}:</div>
          <div class="cross-value">{{controlData.cycle}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.phasedifference')}}:</div>
          <div class="cross-value">{{controlData.offset}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.synctime')}}:</div>
          <div class="cross-value">{{controlData.syncTime}}</div>
        </div>
        <div class="cross-content">
          <div class="cross-name">{{$t('openatc.patternstatistic.coordinationtime')}}:</div>
          <div class="cross-value">{{controlData.curTime}}</div>
        </div>
      </div>
    </div>
</template>
<script>
export default {
  name: 'strengthPanel',
  props: {
    strengthdata: {
      type: Object,
      default: undefined
    }
  },
  data () {
    return {
      controlData: {}
    }
  },
  watch: {
    strengthdata (val) {
      if (!val) return
      this.controlData = JSON.parse(JSON.stringify(val))
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>
<style lang="scss" scoped>
.strengthPanel {
  .cross-content {
    margin-bottom: 25px;
    overflow: hidden;
    .cross-name {
      float: left;
    }
    .cross-value {
      margin-left: 85px;
    }
  }
   /*当屏幕小于等于1280px的屏幕样式*/
  @media only screen and (max-height: 1000px){
    .cross-content {
      margin-bottom: 15px;
    }
   }
  @media only screen and (max-height: 830px){
    .cross-content {
      margin-bottom: 10px;
    }
   }
  @media only screen and (max-height: 720px){
    .cross-content {
      margin-bottom: 5px;
    }
   }
}
</style>
