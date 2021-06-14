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
  <div class="menu-wrapper">
    <template
      v-for="item in routes"
      v-if="!item.hidden&&item.children"
    >

      <router-link
        v-if="hasOneShowingChildren(item.children) && !item.children[0].children&&!item.alwaysShow"
        :to="item.path+'/'+item.children[0].path"
        :key="item.children[0].name"
      >
        <el-menu-item
          :index="item.path+'/'+item.children[0].path"
          :class="{'submenu-title-noDropdown':!isNest}"
        >
          <svg-icon
            v-if="item.children[0].meta&&item.children[0].meta.icon"
            :icon-class="item.children[0].meta.icon"
          ></svg-icon>
          <span
            v-if="item.children[0].meta&&item.children[0].meta.title"
            slot="title"
          >{{$t(item.children[0].meta.title)}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu
        v-else
        index="TSC"
        :key="item.name"
      >
        <template slot="title">
          <svg-icon
            v-if="item.meta&&item.meta.icon"
            :icon-class="item.meta.icon"
          ></svg-icon>
          <span
            v-if="item.meta&&item.meta.title"
            slot="title"
          >{{$t(item.meta.title)}}</span>
        </template>

        <template
          v-for="child in item.children"
          v-if="!child.hidden"
        >
          <sidebar-item
            :is-nest="true"
            class="nest-menu"
            v-if="child.children&&child.children.length>0"
            :routes="[child]"
            :key="child.path"
          ></sidebar-item>

          <router-link
            v-else
            :to="item.path+'/'+child.path"
            :key="child.name"
          >
            <el-menu-item :index="item.path+'/'+child.path">
              <svg-icon
                v-if="child.meta&&child.meta.icon"
                :icon-class="child.meta.icon"
              ></svg-icon>
              <span
                v-if="child.meta&&child.meta.title"
                slot="title"
              >{{$t(child.meta.title)}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>

    </template>
  </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    },
    isNest: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    hasOneShowingChildren (children) {
      const showingChildren = children.filter(item => {
        return !item.hidden
      })
      if (showingChildren.length === 1) {
        return true
      }
      return false
    }
  }
}
</script>
