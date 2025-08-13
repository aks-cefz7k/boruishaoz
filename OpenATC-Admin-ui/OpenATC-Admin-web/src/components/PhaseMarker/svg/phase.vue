
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
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      width="57px" height="57px">
      <path fill-rule="evenodd"  fill-opacity="0.902" fill="rgb(255, 255, 255)"
      d="M28.376,1.489 C13.771,1.489 1.932,13.426 1.932,28.152 C1.932,42.877 13.771,54.816 28.376,54.816 C42.979,54.816 54.819,42.877 54.819,28.152 C54.819,13.426 42.979,1.489 28.376,1.489 "/>
      <path fill-rule="evenodd"  stroke="rgb(8, 116, 212)" stroke-width="2px" stroke-linecap="butt" stroke-linejoin="miter" fill="none"
      d="M28.376,1.489 C13.771,1.489 1.932,13.426 1.932,28.152 C1.932,42.877 13.771,54.816 28.376,54.816 C42.979,54.816 54.819,42.877 54.819,28.152 C54.819,13.426 42.979,1.489 28.376,1.489 "/>
    </svg>
</template>
<script>
export default {
  name: 'pattern',
  data () {
    return {
    }
  },
  props: {
    backgroundColor: {
      type: String,
      default: '#fff'
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
.cls-2 {
    opacity:0.85;
}
.cls-3 {
    fill:#4c9fe8;
}
.cls-4 {
    mask:url(#mask);
}
.cls-5 {
    opacity:0.59;
}
.cls-6 {
    fill:#4c9fe8;
}
.cls-7 {
    filter:url(#luminosity-invert);
}
</style>
