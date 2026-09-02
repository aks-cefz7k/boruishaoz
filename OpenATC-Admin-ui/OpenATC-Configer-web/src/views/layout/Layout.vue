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
  <div class="app-wrapper" :class="classObj" id="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
    <sidebar class="sidebar-container" v-if="!hideMenu && !graphicMode"></sidebar>
    <div class="edge-main-container" :class="{'changeMainPosition': hideMenu || graphicMode}">
      <navbar></navbar>
      <app-main v-if="isload"></app-main>
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
// import axios from 'axios'
import { GetLRRoadConfig } from '@/api/config'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    AppMain
  },
  data () {
    return {
      bodyDomSize: {
        width: 1920,
        height: 1080
      },
      isload: false
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
      hideMenu: state => state.globalParam.hideMenu,
      graphicMode: state => state.globalParam.graphicMode
    })
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        this.getRoadConfig()
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
    },
    bodyDomSize: {
      handler: function (val) {
        if (!document.getElementById('app')) {
          this.$store.dispatch('SaveBodyDomSize', val)
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    this.getRoadConfig()
    let globalParamsModel = new GlobalParamsModel()
    globalParamsModel.Init()
    if (this.$route.query.createdev === undefined) {
      if (this.$route.query.agentid !== undefined) {
        let agentId = this.$route.query.agentid
        setIframdevid(agentId)
      }
    }
  },
  mounted () {
    var _this = this
    _this.$nextTick(function () {
      if (!document.getElementById('app-wrapper')) {
        this.bodyDomSize.width = document.getElementById('app-wrapper').clientWidth
        this.bodyDomSize.height = document.getElementById('app-wrapper').clientHeight
        this.$store.dispatch('SaveBodyDomSize', this.bodyDomSize)
        window.addEventListener('resize', () => {
        // 定义窗口大小变更通知事件
          this.bodyDomSize.width = document.getElementById('app-wrapper').clientWidth
          this.bodyDomSize.height = document.getElementById('app-wrapper').clientHeight
        }, false)
      }
    })
  },
  methods: {
    handleClickOutside () {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    },
    getRoadConfig () {
      GetLRRoadConfig().then((data) => {
        if (!data.data.success) {
          this.$store.dispatch('SetRoadDirection', 'right')
          return
        }
        // 读取左行 右行配置文件
        let roadDir = data.data.data.roadDirection
        if (roadDir !== undefined) {
          this.$store.dispatch('SetRoadDirection', roadDir)
        } else {
          this.$store.dispatch('SetRoadDirection', 'right')
        }
        this.isload = true
      }).catch(error => {
        this.$message.error(error)
        this.$store.dispatch('SetRoadDirection', 'right')
        console.log(error)
      })
      // axios.get('./LRRoadConfig.json').then(val => {
      //   // 读取左行 右行配置文件
      //   let roadDir = val.data.roadDirection
      //   if (val.status === 200 && roadDir !== undefined) {
      //     this.$store.dispatch('SetRoadDirection', roadDir)
      //   } else {
      //     this.$store.dispatch('SetRoadDirection', 'right')
      //   }
      //   this.isload = true
      // })
    }
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
