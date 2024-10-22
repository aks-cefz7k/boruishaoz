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
// import { queryDevice } from '@/api/control'
export default {
  name: 'App',
  data () {
    return {
      bodyDomSize: {
        width: 1920,
        height: 1080
      }
    }
  },
  watch: {
    bodyDomSize: {
      handler: function (val) {
        this.$store.dispatch('SaveBodyDomSize', val)
      },
      // 深度观察监听
      deep: true
    }
  },
  methods: {
    // getPlatform () {
    //   queryDevice().then(res => {
    //     if (!res.data.success) {
    //       this.$message.error(res.data.message)
    //       return
    //     }
    //     let platform = res.data.data.platform
    //     let func = 'allFunc'
    //     if (platform === 'OpenATC') {
    //       func = 'allFunc'
    //     }
    //     if (platform === 'SCATS' || platform === 'HUATONG') {
    //       func = 'basicFunc'
    //     }
    //     this.$store.dispatch('SaveFunctionLevel', func)
    //   })
    // }
  },
  mounted: function () {
    let language = getLanguage()
    console.log('the edge language is:' + language)
    if (language === 'zh') {
      this.$i18n.locale = 'zh'
    } else if (language === 'en') {
      this.$i18n.locale = 'en'
    }
    var _this = this
    _this.$nextTick(function () {
      if (!document.getElementById('app')) return
      this.bodyDomSize.width = document.getElementById('app').clientWidth
      this.bodyDomSize.height = document.getElementById('app').clientHeight
      window.addEventListener('resize', () => {
        // 定义窗口大小变更通知事件
        this.bodyDomSize.width = document.getElementById('app').clientWidth
        this.bodyDomSize.height = document.getElementById('app').clientHeight
      }, false)
    })
    // this.getPlatform()
  }
}
</script>

<style lang="scss">
// @import "./styles/index.scss"; // 全局自定义的css样式
</style>
