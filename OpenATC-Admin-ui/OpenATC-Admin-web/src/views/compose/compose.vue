/**
 * Copyright (c) September 3, 2020 kedacom
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
  <div class="app-wrapper1">
    <navbar></navbar>
    <app-main></app-main>
  </div>
</template>

<script>
import { Navbar, AppMain } from './components'
import router from '@/router'

export default {
  name: 'compose',
  components: {
    Navbar,
    AppMain
  },
  computed: {
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        if (val.path === '/overview' || val.path === '/overview/index') {
          let operateId = this.$store.getters.operateId
          this.saveCurDevInfo(operateId)
          let currDev = this.$store.getters.deviceTags.filter(dev => dev.id === operateId)
          let query = {IP: currDev[0].jsonparam.ip, port: currDev[0].jsonparam.port, agentid: currDev[0].agentid, protocol: currDev[0].protocol, isfromatc: true}
          router.push({
            path: '/overview/index',
            query
          })
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
  },
  methods: {
    saveCurDevInfo (operateId) {
      let allDevsInfo = this.$store.getters.deviceInfos
      // 当前操作设备变化时
      const curDevInfo = allDevsInfo.filter(dev => dev.id === operateId)[0]
      let query = {IP: curDevInfo.jsonparam.ip, port: curDevInfo.jsonparam.port, agentid: curDevInfo.agentid, protocol: curDevInfo.protocol, isfromatc: true}
      window.sessionStorage.setItem('curTagDevInfo', JSON.stringify(query))
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  .app-wrapper1 {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
  }
</style>
