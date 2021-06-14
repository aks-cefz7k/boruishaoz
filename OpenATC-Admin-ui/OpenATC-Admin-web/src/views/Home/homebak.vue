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
<div>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <!-- <el-menu-item index="0">
          <span></span>
      </el-menu-item> -->
      <el-menu-item index="1">主页</el-menu-item>
      <el-menu-item index="2">设备管理</el-menu-item>
      <el-menu-item index="3">用户管理</el-menu-item>
      <el-menu-item index="4">操作记录</el-menu-item>
      <!-- <div class="switch-language">
        <el-dropdown trigger="click" @command="switchLanguage">
          <span class="el-dropdown-link">
            language<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="Ch">中文</el-dropdown-item>
            <el-dropdown-item command="En">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div> -->
  </el-menu>
  <div style="text-align: center;">这是首页</div>
  <div style="width: 100px;">设备ID： <el-input v-model="agentid" placeholder="请输入设备id"></el-input></div>
  <el-button type="primary" @click="onSubmit">立即创建</el-button>
  </div>
</template>

<script>
import router from '@/router'
import { setLanguage } from '@/utils/auth'
export default {
  name: 'home',
  props: {
  },
  data () {
    return {
      activeIndex: '1',
      agentid: '0'
    }
  },
  created () {
  },
  methods: {
    handleSelect (key, keyPath) {
      switch (key) {
        case '1':
          router.push({
            path: '/home'
          })
          break
        case '2':
          router.push({
            path: '/'
          })
          break
        case '3':
          console.log(key)
          break
        case '4':
          console.log(key)
          break
        default:
          console.log(key)
          break
      }
    },
    switchLanguage (command) {
      switch (command) {
        case 'Ch':
          this.switchToChinese()
          break
        case 'En':
          this.switchToEngLish()
          break
        default:
          console.log(command)
          break
      }
    },
    switchToChinese () {
      this.$i18n.locale = 'zh'
      setLanguage(this.$i18n.locale)
    },
    switchToEngLish () {
      this.$i18n.locale = 'en'
      setLanguage(this.$i18n.locale)
    }
  }
}
</script>

<style lang="scss">
</style>
