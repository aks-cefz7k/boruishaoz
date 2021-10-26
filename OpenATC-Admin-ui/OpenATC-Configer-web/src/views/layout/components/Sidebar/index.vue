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
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <!-- <el-menu
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      background-color="#FFFFFF"
      text-color="#000000"
      active-text-color="#409EFF"
    > -->
    <el-menu
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      :default-openeds="openeds"
    >
    <!-- background-color="#24373b"
      text-color="#0096ba"
      active-text-color="#42daff" -->
      <sidebar-item :routes="routes"></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  data () {
    return {
      // isfromatc: false,
      openeds: ['TSC']
    }
  },
  // watch: {
  //   $route (val) {
  //     if (val.query !== undefined && val.query.isfromatc !== undefined && val.query.isfromatc === true) {
  //       this.isfromatc = true
  //     }
  //   }
  // },
  computed: {
    ...mapGetters([
      'sidebar',
      'permissionRoutes',
      'roles'
    ]),
    routes () {
      this.getBaseEdgeRoute(this.$router.options.routes)
      const curRoutes = this.parentRoutes.filter(ele => ele.path !== '/overviewNew')
      return curRoutes
    },
    isCollapse () {
      return !this.sidebar.opened
    }
  },
  methods: {
    getBaseEdgeRoute (routes) {
      for (let oneroute of routes) {
        if (oneroute.path === 'edge' || oneroute.path === '/edge') {
          this.handleUserMgrRoutes(routes)
          this.parentRoutes = this.permissionRoutes
          return
        } else {
          if (!oneroute.children) continue
          this.getBaseEdgeRoute(oneroute.children)
        }
      }
    },
    handleUserMgrRoutes (curRoutes) {
      // 平台打开的配置工具，用户管理不可见（层级高）
      if (this.$route.query.isfromatc === true || this.$route.query.isfromatc === 'true') {
        const noPermission = ['User']
        this.$store.dispatch('GenerateRoutes', {curRoutes, noPermission})
        return
      }
      // 管理员用户管理可见，其余角色不可见
      if (this.roles && this.roles.length) {
        let isManager = false
        for (let role of this.roles) {
          if (role === '超级管理员' || role === '管理员') {
            isManager = true
            break
          }
        }
        if (isManager) {
          this.$store.dispatch('GenerateRoutes', {curRoutes})
        } else {
          const noPermission = ['User']
          this.$store.dispatch('GenerateRoutes', {curRoutes, noPermission})
        }
        return
      }
      // 其余情况对侧边栏显示无限制
      this.$store.dispatch('GenerateRoutes', {curRoutes, noPermission: []})
    },
    getUserInfo () {
      this.$store.dispatch('GetInfo')
    }
  },
  mounted () {
    // if (this.$route.query !== undefined && this.$route.query.isfromatc !== undefined && this.$route.query.isfromatc === true) {
    //   this.isfromatc = true
    // }
    if (!this.roles.length) {
      this.getUserInfo()
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.el-menu-item:hover, .el-menu-item:focus {
  outline: none;
  background-color: #0d404c;
}
</style>
