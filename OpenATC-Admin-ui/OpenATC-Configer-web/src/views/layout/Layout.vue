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
  <div class="app-wrapper" :class="classObj">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
    <sidebar class="sidebar-container" v-if="FuncSort === 'allFunc'"></sidebar>
    <div class="main-container" :class="{'changeMainPosition': FuncSort === 'basicFunc'}">
      <navbar></navbar>
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import GlobalParamsModel from '@/EdgeMgr/controller/globalParamsMgr'
import { setIframdevid } from '@/utils/auth'
// import { AddDevice } from '@/api/control'
import router from '@/router'
import { mapState } from 'vuex'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    AppMain
  },
  data () {
    return {
    }
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar () {
      return this.$store.state.app.sidebar
    },
    device () {
      return this.$store.state.app.device
    },
    classObj () {
      return {
        hideSidebar: !this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    ...mapState({
      FuncSort: state => state.globalParam.FuncSort
    })
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        this.$store.dispatch('SaveCurPath', val.path)
        if (this.$route.query !== undefined && this.$route.query.agentid !== undefined) {
          let agentId = this.$route.query.agentid
          setIframdevid(agentId)
        }
        let devParams = this.$store.getters.devParams
        if (val.path === '/overview/index' && Object.keys(devParams).length !== 0) {
          let query = {}
          query.IP = devParams.ip
          query.port = devParams.port
          query.protocol = devParams.protocol
          query.createdev = true
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
    let globalParamsModel = new GlobalParamsModel()
    globalParamsModel.Init()
    if (this.$route.query.createdev === undefined) {
      if (this.$route.query.agentid !== undefined) {
        let agentId = this.$route.query.agentid
        setIframdevid(agentId)
      }
    }
    //  else { // 单机设备情况下，需要先创建一个设备
    //   let deviceInfo = {}
    //   if (this.$route.query.agentid === undefined) {
    //     deviceInfo.agentid = '0'
    //     setIframdevid('0')
    //   } else {
    //     deviceInfo.agentid = this.$route.query.agentid
    //     setIframdevid(this.$route.query.agentid)
    //   }
    //   // deviceInfo.agentid = '0'
    //   deviceInfo.protocol = this.$route.query.protocol
    //   deviceInfo.ip = this.$route.query.IP
    //   deviceInfo.port = this.$route.query.port
    //   deviceInfo.type = 'asc'
    //   this.$store.dispatch('SaveDevParams', deviceInfo)
    //   this.addDevice(deviceInfo)
    // }
  },
  methods: {
    handleClickOutside () {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    }
    // addDevice (deviceInfo) {
    //   AddDevice(deviceInfo).then(res => {
    //     if (!res.data.success) {
    //       console.log(new Error(res.data.message))
    //       this.$message({
    //         message: res.data.message,
    //         type: 'error',
    //         duration: 1 * 1000
    //       })
    //     }
    //   })
    // }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  // .app-wrapper {
  //   @include clearfix;
  //   position: relative;
  //   height: 100%;
  //   width: 100%;
  //   background: red;
  // }
  // .drawer-bg {
  //   background: red;
  //   opacity: 0.3;
  //   width: 100%;
  //   top: 0;
  //   height: 100%;
  //   position: absolute;
  //   z-index: 999;
  // }
  .changeMainPosition {
    margin-left: 0px !important;
  }
</style>
