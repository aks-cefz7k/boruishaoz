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
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import { getLanguage, getMenuVisible } from '@/utils/auth'
export default {
  name: 'App',
  data () {
    return {
    }
  },
  mounted: function () {
    this.reloadLanguage()
    this.reloadGisMenu()
  },
  methods: {
    reloadLanguage () {
      let language = getLanguage()
      if (language === 'zh') {
        this.$i18n.locale = 'zh'
      } else if (language === 'en') {
        this.$i18n.locale = 'en'
      }
    },
    reloadGisMenu () {
      // 是否显示地图菜单
      // 从浏览器存储中取值，更新store，因为强制刷新后，store会恢复默认值
      let isShowGIS = getMenuVisible('gis')
      if (isShowGIS === 'true') {
        this.$store.dispatch('SetGisVisible', true)
      } else {
        this.$store.dispatch('SetGisVisible', false)
      }
    }
  }
}
</script>

<style lang="scss">
// @import "./styles/index.scss"; // 全局自定义的css样式
</style>
