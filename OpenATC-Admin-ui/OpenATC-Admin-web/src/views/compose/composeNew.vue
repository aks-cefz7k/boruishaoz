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
  <div class="app-wrapper1">
    <app-main></app-main>
  </div>
</template>

<script>
import { AppMain } from './components'
import router from '@/router'
import { setToken } from '@/utils/auth'

export default {
  name: 'composeNew',
  components: {
    AppMain
  },
  data () {
    return {
      fromKstpPath: ['/greenWaveOptimizeNew', '/deviceNew', '/operaterecordNew', '/overviewNew/index']
    }
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
          let query = {agentid: currDev[0].agentid, isfromatc: true}
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
    const currRouter = this.$route.path
    if (this.fromKstpPath.indexOf(this.$store.getters.devicePath) !== -1) return
    if (this.fromKstpPath.indexOf(currRouter) !== -1) {
      this.setCurPath(currRouter)
      this.setToken()
    }
  },
  mounted () {
    const currRouter = this.$route.path
    if (currRouter === '/overviewNew/index') {
      // 直接跳转进入单设备详情，需要存储一个标识，以供后续逻辑判断使用
      sessionStorage.setItem('toSingleEdge', '1')
    } else {
      sessionStorage.setItem('toSingleEdge', '0')
    }
  },
  methods: {
    saveCurDevInfo (operateId) {
      let allDevsInfo = this.$store.getters.deviceInfos
      // 当前操作设备变化时
      const curDevInfo = allDevsInfo.filter(dev => dev.id === operateId)[0]
      let query = {agentid: curDevInfo.agentid, isfromatc: true}
      window.sessionStorage.setItem('curTagDevInfo', JSON.stringify(query))
    },
    setCurPath (currRouter) {
      this.$store.dispatch('SetDevPath', currRouter)
    },
    setToken () {
      let json = require('../../../static/token.json')
      setToken(json.TOKEN)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  // .app-wrapper1 {
  //   @include clearfix;
  //   position: relative;
  //   height: 100%;
  //   width: 100%;
  // }
</style>
