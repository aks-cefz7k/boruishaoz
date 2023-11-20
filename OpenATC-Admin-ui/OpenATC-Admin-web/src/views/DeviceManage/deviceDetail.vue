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
    <DeviceTags v-if="show" />
    <div class="adminEdge">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import DeviceTags from './deviceTags'
export default {
  components: {
    DeviceTags
  },
  data () {
    return {
      show: true
    }
  },
  mounted () {
    // 如果直接跳转到单设备详情，需要隐藏tags管理
    if (sessionStorage.getItem('toSingleEdge') === '1') {
      this.show = false
    } else {
      this.show = true
    }
  },
  methods: {
  }
}
</script>

<style lang="scss">
// #app .adminEdge {
//   border-top: 1px solid $--border-color-base;
// }
#app .sidebar-container {
    -webkit-transition: width 0.28s;
    transition: width 0.28s;
    width: 180px;
    height: 940px;;
    position:absolute;
    // border-right: 1px solid $--border-color-base;
    bottom: 0;
    left: 0;
    z-index: 1001;
}
</style>
