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
<div class="refresh">
  <svg
    version="1.1"
    id="图层_1"
    xmlns="http://www.w3.org/2000/svg"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    x="0px"
    y="0px"
    viewBox="0 0 68 68"
    style="enable-background:new 0 0 68 68;"
    xml:space="preserve"
    :width="Width" :height="Height"
  >
    <path
      class="st0"
      d="M63.6,34l4-18.2l-6.3,6C55.1,6.9,38.6-1,22.9,3.7C6.2,8.7-3.3,26.4,1.8,43.1c3.1,10.3,11,17.9,20.6,21
        c5.9,1.9,12.5,2.1,18.9,0.2c5.4-1.6,10.3-4.7,14.1-8.8c0.9-0.9,0.8-2.4-0.1-3.2c-0.9-0.9-2.4-0.8-3.2,0.1c-3.3,3.5-7.4,6.1-12,7.5
        c-14.3,4.3-29.4-3.8-33.7-18.1C1.8,27.5,10,12.4,24.3,8.1C37.7,4,51.9,10.9,57.1,23.9L46.9,25L63.6,34z"
    ></path>
  </svg>
</div>
</template>
<script>
export default {
  name: 'refreshIcon',
  data () {
    return {}
  },
  props: {
    Width: {
      type: String,
      default: '68px'
    },
    Height: {
      type: String,
      default: '68px'
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
 .st0 {
    fill: #409eff;
  }
.refresh {
    cursor: pointer;
    text-align: center;
}
</style>
