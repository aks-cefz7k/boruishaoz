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
import { getLanguage } from '@/utils/auth'
export default {
  name: 'App',
  data () {
    return {
    }
  },
  mounted: function () {
    let language = getLanguage()
    console.log('the edge language is:' + language)
    if (language === 'zh') {
      this.$i18n.locale = 'zh'
    } else if (language === 'en') {
      this.$i18n.locale = 'en'
    }
  }
}
</script>

<style lang="scss">
@import "./styles/index.scss"; // 全局自定义的css样式
</style>
