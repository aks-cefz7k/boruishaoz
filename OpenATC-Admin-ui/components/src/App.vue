<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { getLanguage } from '@/utils/auth'
export default {
  name: 'App',
  mounted: function () {
    let language = getLanguage()
    console.log('the language is:' + language)
    if (language === 'en') {
      this.$i18n.locale = 'en'
    } else {
      this.$i18n.locale = 'zh'
    }
  }
}
</script>

<style>
</style>
